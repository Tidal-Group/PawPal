package com.tidal.pawpal.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

import com.tidal.pawpal.exceptions.AuthenticationFailureException;
import com.tidal.pawpal.models.Appuntamento;
import com.tidal.pawpal.models.Prestazione;
import com.tidal.pawpal.models.Recensione;
import com.tidal.pawpal.models.Specie;
import com.tidal.pawpal.models.User;
import com.tidal.pawpal.services.AppuntamentoService;
import com.tidal.pawpal.services.ClienteService;
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
        User utente = userService.cercaPerUsername(principal.getName());
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

    @GetMapping("")
    public String showDashboard(Principal principal, Model model) {
        try {
            acceptAuthenticated(principal, (authentication, utente) -> {

                Set<Specie> listaSpecieSelezionate = new HashSet<>();
                if(isVeterinario(authentication))
                    listaSpecieSelezionate = specieService.cercaPerVeterinario(utente.getId());

                Set<Prestazione> listaPrestazioniSelezionate = new HashSet<>();
                if(isVeterinario(authentication))
                    listaPrestazioniSelezionate = prestazioneService.cercaPerVeterinario(utente.getId());

                if(isCliente(authentication))
                    utente = clienteService.cercaPerId(utente.getId());
                else if(isVeterinario(authentication))
                    utente = veterinarioService.cercaPerId(utente.getId());

                List<Appuntamento> listaAppuntamenti = new ArrayList<>();
                if(isCliente(authentication))
                    listaAppuntamenti = appuntamentoService.cercaPerCliente(utente.getId());
                else if(isVeterinario(authentication))
                    listaAppuntamenti = appuntamentoService.cercaPerVeterinario(utente.getId());

                List<Recensione> listaRecensioni = new ArrayList<>();
                if(isCliente(authentication))
                    listaRecensioni = recensioneService.cercaPerCliente(utente.getId());
                else if(isVeterinario(authentication))
                    listaRecensioni = recensioneService.cercaPerVeterinario(utente.getId());

                model.addAttribute("lista_specie", specieService.elencaTutti());
                model.addAttribute("lista_specie_selezionate", listaSpecieSelezionate);
                model.addAttribute("lista_prestazioni_selezionate", listaPrestazioniSelezionate);
                model.addAttribute("lista_prestazioni", prestazioneService.elencaTutti());
                model.addAttribute("user", utente);
                model.addAttribute("lista_recensioni", listaRecensioni);
                model.addAttribute("lista_appuntamenti", listaAppuntamenti);
            });
            return "dashboard_utente";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    @GetMapping("/profilo")
    public String showProfilo(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("dashboardPage", "modifica_dati");
        return "redirect:/dash";
    }
    

    @PostMapping("/profilo/modifica_username")
    public String sendUsername(@RequestParam String username, Principal principal) {        
        try {
            acceptAuthenticated(principal, (authentication, utente) -> {
                userService.modificaUsername(utente.getId(), username);
            });
            return "redirect:/dash/#modifica_account";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    @PostMapping("/profilo/modifica_email")
    public String sendEmail(@RequestParam String email, Principal principal) {        
        try {
            acceptAuthenticated(principal, (authentication, utente) -> {
                userService.modificaEmail(utente.getId(), email);
            });
            return "redirect:/dash#modifica_account";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    @PostMapping("/profilo/modifica_password")
    public String sendPassword(
        Principal principal,
        RedirectAttributes redirectAttributes,
        @RequestParam("current_password") String currentPassword,
        @RequestParam("new_password") String newPassword
    ) {        
        try {
            acceptAuthenticated(principal, (authentication, utente) -> {
                userService.modificaPassword(utente.getId(), currentPassword, newPassword);
            });
            return "redirect:/dash#modifica_account";
        } catch(AuthenticationFailureException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            redirectAttributes.addFlashAttribute("openModal", "modal-password");
            return "redirect:/dash#modifica_account";
        } catch(Exception exception) {
            return "redirect:/error";
        }
    }

    @PostMapping("/profilo/modifica_dati")
    public String sendDatiProfilo(@RequestParam Map<String, String> data, Principal principal) {
        try {
            acceptAuthenticated(principal, (authentication, utente) -> {
                if(isCliente(authentication))
                    clienteService.modifica(utente.getId(), data);
                else if(isVeterinario(authentication))
                    veterinarioService.modifica(utente.getId(), data);
            });
            return "redirect:/dash#modifica_dati";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    @PostMapping("/profilo/elimina_account")
    public String deleteAccount(@RequestParam String password, Principal principal) {        
        try {
            // TODO IMPLEMENT
            return "redirect:/dash#elimina_account";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    @GetMapping("/appuntamenti")
    public String showAppuntamenti(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("dashboardPage", "appuntamenti");
        return "redirect:/dash";
    }

    @PostMapping("/appuntamenti/modifica_appuntamento")
    public String handleAppuntamentoUpdate(@RequestParam Map<String, String> data) {
        try {
            appuntamentoService.modifica(Long.parseLong(data.get("id")), data);
            return "redirect:/dash/appuntamenti";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
    }

    @PostMapping("/appuntamenti/elimina_appuntamento")
    public String handleAppuntamentoDeletion(@RequestParam Long id) {
        try {
            appuntamentoService.elimina(id);
            return "redirect:/dash/appuntamenti";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    @GetMapping("/recensioni")
    public String showRecensioni(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("dashboardPage", "recensioni");
        return "redirect:/dash";
    }

    @PostMapping("/recensioni/elimina_recensione")
    public String postMethodName(@RequestParam Long id) {
        try {
            recensioneService.elimina(id);
            return "redirect:/dash/recensioni";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

}
