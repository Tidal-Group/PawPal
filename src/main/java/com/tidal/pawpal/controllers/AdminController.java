package com.tidal.pawpal.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;
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

import com.tidal.pawpal.models.Prestazione;
import com.tidal.pawpal.models.Specie;
import com.tidal.pawpal.models.User;

import com.tidal.pawpal.services.contracts.PrestazioneServiceContract;
import com.tidal.pawpal.services.contracts.SpecieServiceContract;
import com.tidal.pawpal.services.contracts.UserServiceContract;

import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/admin")
public class AdminController {

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
    public UserServiceContract userService;

    @Autowired
    public PrestazioneServiceContract prestazioneService;

    @Autowired
    public SpecieServiceContract specieService;

    @GetMapping("utenti")
    public String showUtenti(@RequestParam Map<String, String> params, Model model) {
        try {
            List<User> listaUtenti = userService.cercaConFiltri(params);
            model.addAttribute("lista_utenti", listaUtenti);
            return "admin_utenti";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
    }

    @PostMapping("/utenti/inserisci_utente")
    public String handleUtenteInsertion(@RequestParam Map<String, String> data) {
        try {
            // DEBUG: potrebbe servire far fare queste operazioni direttamente a
            // AuthService o a ClienteService e VeterinarioService
            User utente = userService.registra(data);
            // riaggiorna la lista
            // DEBUG: inefficiente, perché rieffettua la query ogni volta
            return "redirect:/admin/utenti";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
    }

    @PostMapping("/utenti/modifica_utente")
    public String handleUtenteUpdate(@RequestParam Map<String, String> data) {
        try {
            // DEBUG: potrebbe servire far fare queste operazioni direttamente a
            // AuthService o a ClienteService e VeterinarioService
            User utente = userService.modifica(Long.parseLong(data.get("id")), data);
            // riaggiorna la lista
            // DEBUG: inefficiente, perché rieffettua la query ogni volta
            return "redirect:/admin/utenti";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
    }

    @PostMapping("/utenti/elimina_utente")
    public String handleUtenteUpdate(@RequestParam Long id) {
        try {
            // DEBUG: potrebbe servire far fare queste operazioni direttamente a
            // AuthService o a ClienteService e VeterinarioService
            userService.elimina(id);
            // riaggiorna la lista
            // DEBUG: inefficiente, perché rieffettua la query ogni volta
            return "redirect:/admin/utenti";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
    }
    

    @GetMapping("prestazioni")
    public String showPrestazioni(@RequestParam Map<String, String> params, Model model) {
        try {
            // IMPLEMENT: PrestazioneService verificherà che la richiesta provenga da un account con permessi da admin
            List<Prestazione> listaPrestazioni = prestazioneService.cercaConFiltri(params);
            model.addAttribute("lista_prestazioni", listaPrestazioni);
            return "admin_prestazioni";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
        
    }

    @PostMapping("/prestazioni/inserisci_prestazione")
    public String handlePrestazioneInsertion(@RequestParam Map<String, String> data) {
        try {
            Prestazione prestazione = prestazioneService.registra(data);
            // riaggiorna la lista
            // DEBUG: inefficiente, perché rieffettua la query ogni volta
            return "redirect:/admin/prestazioni";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
    }

    @PostMapping("/prestazioni/modifica_prestazione")
    public String handlePrestazioneUpdate(@RequestParam Map<String, String> data) {
        try {
            Prestazione prestazione = prestazioneService.modifica(Long.parseLong(data.get("id")), data);
            // riaggiorna la lista
            // DEBUG: inefficiente, perché rieffettua la query ogni volta
            return "redirect:/admin/prestazioni";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
    }

    @PostMapping("/prestazioni/elimina_prestazione")
    public String handlePrestazioneUpdate(@RequestParam Long id) {
        try {
            prestazioneService.elimina(id);
            // riaggiorna la lista
            // DEBUG: inefficiente, perché rieffettua la query ogni volta
            return "redirect:/admin/prestazioni";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
    }

    @GetMapping("specie")
    public String showSpecie(@RequestParam Map<String, String> params, Model model) {
        try {
            // IMPLEMENT: PrestazioneService verificherà che la richiesta provenga da un account con permessi da admin
            List<Specie> listaSpecie = specieService.cercaConFiltri(params);
            model.addAttribute("lista_specie", listaSpecie);
            return "admin_specie";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
        
    }

    @PostMapping("/specie/inserisci_specie")
    public String handlePrestazioneInsertion(@RequestParam Map<String, String> data) {
        try {
            Specie specie = specieService.registra(data);
            // riaggiorna la lista
            // DEBUG: inefficiente, perché rieffettua la query ogni volta
            return "redirect:/admin/specie";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
    }

    @PostMapping("/specie/modifica_specie")
    public String handlePrestazioneUpdate(@RequestParam Map<String, String> data) {
        try {
            Specie specie = specieService.modifica(Long.parseLong(data.get("id")), data);
            // riaggiorna la lista
            // DEBUG: inefficiente, perché rieffettua la query ogni volta
            return "redirect:/admin/specie";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
    }

    @PostMapping("/specie/elimina_specie")
    public String handlePrestazioneUpdate(@RequestParam Long id) {
        try {
            specieService.elimina(id);
            // riaggiorna la lista
            // DEBUG: inefficiente, perché rieffettua la query ogni volta
            return "redirect:/admin/specie";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
    }
    
}
