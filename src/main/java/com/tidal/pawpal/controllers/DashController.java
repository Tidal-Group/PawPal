package com.tidal.pawpal.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
import com.tidal.pawpal.services.ClienteService;
import com.tidal.pawpal.services.CustomUserDetailsService;
import com.tidal.pawpal.services.PrestazioneService;
import com.tidal.pawpal.services.RecensioneService;
import com.tidal.pawpal.services.SpecieService;
import com.tidal.pawpal.services.UserService;
import com.tidal.pawpal.services.VeterinarioService;

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

    private static final Set<String> MODAL_IDS = Set.of(
        "emailModal", 
        "usernameModal",
        "passwordModal"
    );

    private String extractPathAndQuery(String refererUrl) {
        try {
            URI refererUri = new URI(refererUrl);
            String path = refererUri.getPath();
            String query = refererUri.getQuery();
            if(query  != null && !query.isEmpty()) path += "?" + query;
            return path;
        } catch (URISyntaxException | NullPointerException exception) {
            return "/";
        }
    }

    private String getRedirectHash(String existingFragment, String newModalId) {
        if (existingFragment == null || existingFragment.isEmpty()) return "#" + newModalId;

        List<String> hashIDs = Arrays
                                .stream(existingFragment.split("#"))
                                .filter(id -> !id.isEmpty())
                                .collect(Collectors.toList()); 

        if (hashIDs.isEmpty()) return "#" + newModalId;
        
        String lastHashId = hashIDs.get(hashIDs.size() - 1);
        
        if (MODAL_IDS.contains(lastHashId)) hashIDs.set(hashIDs.size() - 1, newModalId);
        else hashIDs.add(newModalId);

        return "#" + String.join("#", hashIDs);
    }

    public String redirectToReferer(String refererUrl) {
        String pathAndQuery = extractPathAndQuery(refererUrl);
        return "redirect:" + pathAndQuery;
    }

    public String redirectToEmailModal(String refererUrl, String existingHash) {
        String pathAndQuery = extractPathAndQuery(refererUrl);
        String hash = getRedirectHash(existingHash, "emailModal");
        return "redirect:" + pathAndQuery + hash;
    }

    public String redirectToUsernameModal(String refererUrl, String existingHash) {
        String pathAndQuery = extractPathAndQuery(refererUrl);
        String hash = getRedirectHash(existingHash, "usernameModal");
        return "redirect:" + pathAndQuery + hash;
    }

    public String redirectToPasswordModal(String refererUrl, String existingHash) {
        String pathAndQuery = extractPathAndQuery(refererUrl);
        String hash = getRedirectHash(existingHash, "passwordModal");
        return "redirect:" + pathAndQuery + hash;
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

    @GetMapping("")
    public String showDashboard(
        Principal principal,
        Model model
    ) {
        try {
            acceptAuthenticated(principal, (authentication, utente) -> {

                // passo i dati dell'utente
                model.addAttribute("user", utente);

                // passo i dati relativi alle relazioni comuni a utente e veterinario
                List<AppuntamentoDto> listaAppuntamenti = new ArrayList<>();
                List<RecensioneDto> listaRecensioni = new ArrayList<>();
                if(isCliente(authentication)) {
                    listaAppuntamenti = appuntamentoService.cercaPerCliente(utente.getId());
                    listaRecensioni = recensioneService.cercaPerCliente(utente.getId());
                } else if(isVeterinario(authentication)) {
                    listaAppuntamenti = appuntamentoService.cercaPerVeterinario(utente.getId());
                    listaRecensioni = recensioneService.cercaPerVeterinario(utente.getId());
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
        @RequestHeader(value="Referer", required=false) String refererUrl,
        RedirectAttributes redirectAttributes,
        @RequestParam("existing_hash") String existingHash,
        @RequestParam("new_username") String newUsername,
        @RequestParam("confirm_password") String confirmPassword
    ) {        
        try {
            acceptAuthenticated(principal, (authentication, utente) -> {
                userService.modificaUsername(utente.getId(), newUsername, confirmPassword);
            });
            redirectAttributes.addFlashAttribute("successMessage", "Username modificato con successo");
            return redirectToReferer(refererUrl);
        } catch(ExistingUsernameException | AuthenticationFailureException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return redirectToUsernameModal(refererUrl, existingHash);
        } catch(Exception exception) {
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    @PostMapping("/modifica_email")
    public String submitNewEmail(
        Principal principal,
        @RequestHeader(value="Referer", required=false) String refererUrl,
        RedirectAttributes redirectAttributes,
        @RequestParam("existing_hash") String existingHash,
        @RequestParam("new_email") String newEmail,
        @RequestParam("confirm_password") String confirmPassword
    ) {        
        try {
            acceptAuthenticated(principal, (authentication, utente) -> {
                userService.modificaEmail(utente.getId(), newEmail, confirmPassword);
            });
            redirectAttributes.addFlashAttribute("successMessage", "Email modificata con successo");
            return redirectToReferer(refererUrl);
        } catch(ExistingEmailException | AuthenticationFailureException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return redirectToEmailModal(refererUrl, existingHash);
        } catch(Exception exception) {
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    @PostMapping("/modifica_password")
    public String submitNewPassword(
        Principal principal,
        @RequestHeader(value="Referer", required=false) String refererUrl,
        RedirectAttributes redirectAttributes,
        @RequestParam("existing_hash") String existingHash,
        @RequestParam("current_password") String currentPassword,
        @RequestParam("new_password") String newPassword
    ) {        
        try {
            acceptAuthenticated(principal, (authentication, utente) -> {
                userService.modificaPassword(utente.getId(), currentPassword, newPassword);
            });
            redirectAttributes.addFlashAttribute("successMessage", "Password modificata con successo");
            return redirectToReferer(refererUrl);
        } catch(AuthenticationFailureException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return redirectToPasswordModal(refererUrl, existingHash);
        } catch(Exception exception) {
            return "redirect:/error";
        }
    }

    @PostMapping("/profilo/modifica_dati")
    public String handleProfileUpdate(
        Principal principal,
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
            return "redirect:/dash#modifica_dati";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    // DEBUG: risolvere problema riferimenti circolari
    @PostMapping("/profilo/elimina_account")
    public String handleAccountDeletion(
        Principal principal,
        @RequestParam String password
    ) {        
        try {
            // TODO IMPLEMENT
            return "redirect:/dash#elimina_account";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    @PostMapping("/appuntamenti/modifica_appuntamento")
    public String handleAppuntamentoUpdate(@RequestParam Map<String, String> data) {
        try {
            appuntamentoService.modifica(Long.parseLong(data.get("id")), data);
            // IMPLEMENT: aggiungere un modo per essere riportati
            // alla posizione dell'appuntamento nella lista
            return "redirect:/dash#appuntamenti";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    @PostMapping("/appuntamenti/elimina_appuntamento")
    public String handleAppuntamentoDeletion(
        @RequestParam Long idAppuntamento
    ) {
        try {
            appuntamentoService.elimina(idAppuntamento);
            return "redirect:/dash#appuntamenti";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    @PostMapping("/recensioni/elimina_recensione")
    public String handleRecensioneDeletion(@RequestParam Long id) {
        try {
            recensioneService.elimina(id);
            return "redirect:/dash#recensioni";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

}
