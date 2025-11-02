package com.tidal.pawpal.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tidal.pawpal.dto.AppuntamentoDto;
import com.tidal.pawpal.dto.RecensioneDto;
import com.tidal.pawpal.exceptions.AuthenticationFailureException;
import com.tidal.pawpal.exceptions.ExistingEmailException;
import com.tidal.pawpal.exceptions.ExistingUsernameException;
import com.tidal.pawpal.models.Prestazione;
import com.tidal.pawpal.models.Specie;
import com.tidal.pawpal.models.User;
import com.tidal.pawpal.services.AppuntamentoService;
import com.tidal.pawpal.services.AuthService;
import com.tidal.pawpal.services.ClienteService;
import com.tidal.pawpal.services.CustomUserDetailsService;
import com.tidal.pawpal.services.PrestazioneService;
import com.tidal.pawpal.services.RecensioneService;
import com.tidal.pawpal.services.SpecieService;
import com.tidal.pawpal.services.UserService;
import com.tidal.pawpal.services.VeterinarioService;
import com.tidal.pawpal.utils.ControllerUtils;


@Controller
@RequestMapping("/dash")
public class DashController {

    private static boolean isCliente(Authentication authentication) {
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE"));
    }

    private static boolean isVeterinario(Authentication authentication) {
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_VETERINARIO"));
    }

    private void acceptAuthenticated(Principal principal, BiConsumer<Authentication, User> consumer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService.SecuredUser securedUser = (CustomUserDetailsService.SecuredUser) authentication.getPrincipal();
        // IMPLEMENT: sostituire con un DTO
        User utente = userService.cercaPerId(securedUser.getId());
        consumer.accept(authentication, utente);
    }

    @Autowired
    public ClienteService clienteService;

    @Autowired
    public VeterinarioService veterinarioService;

    @Autowired
    private PrestazioneService prestazioneService;

    @Autowired
    private SpecieService specieService;

    @Autowired
    public AppuntamentoService appuntamentoService;

    @Autowired
    public RecensioneService recensioneService;

    @Autowired
    public UserService userService;

    @Autowired
    public AuthService authService;

    @GetMapping("")
    public String showDashboard(
        Principal principal,
        Model model,
        @RequestParam Optional<LocalDate> filtroData,
        @RequestParam Optional<String> filtroNominativo
    ) {
        try {
            Map<String, Object> filtriAppuntamento = new HashMap<>();

            if(filtroData.isPresent()) {
                LocalDate date = filtroData.get();
                filtriAppuntamento.put("data", date);
                model.addAttribute("filtroData", date.format(DateTimeFormatter.ISO_DATE));
            }

            if(filtroNominativo.isPresent()) {
                String nominativo = filtroNominativo.get();
                filtriAppuntamento.put("nominativo", nominativo);
                model.addAttribute("filtroNominativo", nominativo);
            }

            acceptAuthenticated(principal, (authentication, utente) -> {

                // passo i dati dell'utente
                model.addAttribute("user", utente);

                // passo i dati relativi alle relazioni comuni a utente e veterinario
                List<AppuntamentoDto> listaAppuntamenti = new ArrayList<>();
                List<RecensioneDto> listaRecensioni = new ArrayList<>();
                if(filtriAppuntamento.isEmpty()) {
                    if(isCliente(authentication)) listaAppuntamenti = appuntamentoService.cercaPerCliente(utente.getId());
                    if(isVeterinario(authentication)) listaAppuntamenti = appuntamentoService.cercaPerVeterinario(utente.getId());
                } else {
                    if(isCliente(authentication)) listaAppuntamenti = appuntamentoService.cercaPerClienteConFiltri(utente.getId(), filtriAppuntamento);
                    if(isVeterinario(authentication)) listaAppuntamenti = appuntamentoService.cercaPerVeterinarioConFiltri(utente.getId(), filtriAppuntamento);
                }

                model.addAttribute("lista_appuntamenti", listaAppuntamenti);
                model.addAttribute("lista_recensioni", listaRecensioni);

                // passo i dati relativi alle relazioni specfiche del veterinario
                if(isVeterinario(authentication)) {
                    Set<Specie> listaSpecieSelezionate = specieService.cercaPerVeterinario(utente.getId());
                    Set<Prestazione> listaPrestazioniSelezionate = prestazioneService.cercaPerVeterinario(utente.getId());

                    model.addAttribute("lista_specie", specieService.elencaTutti());
                    model.addAttribute("lista_prestazioni", prestazioneService.elencaTutti());
                    model.addAttribute("lista_specie_selezionate", listaSpecieSelezionate);
                    model.addAttribute("lista_prestazioni_selezionate", listaPrestazioniSelezionate);
                }
            });

            return "dashboard_utente";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    @PostMapping("/modifica_username")
    public String submitNewUsername(
        Principal principal,
        @RequestParam String redirectUrl,
        RedirectAttributes redirectAttributes,
        @RequestParam("new_username") String newUsername,
        @RequestParam("confirm_password") String confirmPassword
    ) {        
        try {
            acceptAuthenticated(principal, (authentication, utente) -> {
                userService.modificaUsername(utente.getId(), newUsername, confirmPassword);
            });
            redirectAttributes.addFlashAttribute("successMessage", "Username modificato con successo");
            return ControllerUtils.redirectToSame(redirectUrl);
        } catch(ExistingUsernameException | AuthenticationFailureException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return ControllerUtils.redirectToModal(redirectUrl, "usernameModal");
        } catch(Exception exception) {
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    @PostMapping("/modifica_email")
    public String submitNewEmail(
        Principal principal,
        @RequestParam String redirectUrl,
        RedirectAttributes redirectAttributes,
        @RequestParam("new_email") String newEmail,
        @RequestParam("confirm_password") String confirmPassword
    ) {        
        try {
            acceptAuthenticated(principal, (authentication, utente) -> {
                userService.modificaEmail(utente.getId(), newEmail, confirmPassword);
            });
            redirectAttributes.addFlashAttribute("successMessage", "Email modificata con successo");
            return ControllerUtils.redirectToSame(redirectUrl);
        } catch(ExistingEmailException | AuthenticationFailureException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return ControllerUtils.redirectToModal(redirectUrl, "emailModal");
        } catch(Exception exception) {
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    @PostMapping("/modifica_password")
    public String submitNewPassword(
        Principal principal,
        @RequestParam String redirectUrl,
        RedirectAttributes redirectAttributes,
        @RequestParam("current_password") String currentPassword,
        @RequestParam("new_password") String newPassword
    ) {        
        try {
            acceptAuthenticated(principal, (authentication, utente) -> {
                userService.modificaPassword(utente.getId(), currentPassword, newPassword);
            });
            redirectAttributes.addFlashAttribute("successMessage", "Password modificata con successo");
            return ControllerUtils.redirectToSame(redirectUrl);
        } catch(AuthenticationFailureException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return ControllerUtils.redirectToModal(redirectUrl, "passwordModal");
        } catch(Exception exception) {
            return "redirect:/error";
        }
    }

    @PostMapping("/profilo/modifica_dati")
    public String handleProfileUpdate(
        Principal principal,
        @RequestParam String redirectUrl,
        RedirectAttributes redirectAttributes,
        @RequestParam(name="specie", required=false) List<Long> listaIdSpecie,
        @RequestParam(name="prestazioni", required=false) List<Long> listaIdPrestazioni,
        @RequestParam Map<String, String> data
    ) {
        try {
            acceptAuthenticated(principal, (authentication, utente) -> {
                if(isCliente(authentication))
                    clienteService.modifica(utente.getId(), data);
                else if(isVeterinario(authentication))
                    veterinarioService.modifica(utente.getId(), listaIdSpecie, listaIdPrestazioni, data);
            });
            redirectAttributes.addFlashAttribute("successMessage", "Modifiche effettuate con successo");
            return ControllerUtils.redirectToView(redirectUrl, "modifica_dati");
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    @PostMapping("/profilo/elimina_account")
    public String handleAccountDeletion(
        Principal principal,
        @RequestParam String password,
        @RequestParam String redirectUrl,
        RedirectAttributes redirectAttributes
    ) {
        try {
            acceptAuthenticated(principal, (authentication, utente) -> {
                if(isCliente(authentication)) authService.eliminaCliente(utente.getId(), password);
                else if(isVeterinario(authentication)) authService.eliminaVeterinario(utente.getId(), password);
            });
            return "redirect:/logout";
        } catch(AuthenticationFailureException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return ControllerUtils.redirectToSame(redirectUrl);
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    @PostMapping("/appuntamenti/filtra")
    public String filterAppuntamenti(
        @RequestParam String redirectUrl,
        RedirectAttributes redirectAttributes,
        @RequestParam(required=false) Optional<LocalDate> filtroData,
        @RequestParam(required=false) Optional<String> filtroNominativo
    ) {
        Map<String, String> queryParams = new HashMap<>();

        queryParams.put("filtroData", filtroData.isPresent() ? filtroData.get().format(DateTimeFormatter.ISO_DATE) : "");
        queryParams.put("filtroNominativo", filtroNominativo.isPresent() ? filtroNominativo.get() : "");

        return ControllerUtils.redirectToQueryParams(redirectUrl, queryParams);
    }
    

    @PostMapping("/appuntamenti/modifica_appuntamento")
    public String handleAppuntamentoUpdate(
        @RequestParam String redirectUrl,
        RedirectAttributes redirectAttributes,
        @RequestParam Map<String, String> data
    ) {
        try {
            appuntamentoService.modifica(Long.parseLong(data.get("id")), data);
            // riporta all'# dell'appuntamento modificato
            return ControllerUtils.redirectToFragment(redirectUrl, "modificaAppuntamento" + data.get("id"));
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            // invece di riportare all'id dell'appuntamento modificato,
            // porta all'inizio della pagina e mostra un messaggio di errore
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    @PostMapping("/appuntamenti/elimina_appuntamento")
    public String handleAppuntamentoDeletion(
        @RequestParam String redirectUrl,
        RedirectAttributes redirectAttributes,
        @RequestParam Long idAppuntamento
    ) {
        try {
            appuntamentoService.elimina(idAppuntamento);
            redirectAttributes.addFlashAttribute("successMessage", "Annullamento effettuato con successo");
            return ControllerUtils.redirectToSame(redirectUrl);
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    @PostMapping("/recensioni/elimina_recensione")
    public String handleRecensioneDeletion(
        @RequestParam String redirectUrl,
        RedirectAttributes redirectAttributes,
        @RequestParam Long idRecensione
    ) {
        try {
            recensioneService.elimina(idRecensione);
            redirectAttributes.addFlashAttribute("successMessage", "Eliminazione effettuata con successo");
            return ControllerUtils.redirectToSame(redirectUrl);
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

}
