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

import com.tidal.pawpal.models.User;
import com.tidal.pawpal.models.Veterinario;
import com.tidal.pawpal.services.AppuntamentoService;
import com.tidal.pawpal.services.PrestazioneService;
import com.tidal.pawpal.services.RecensioneService;
import com.tidal.pawpal.services.SpecieService;
import com.tidal.pawpal.services.UserService;
import com.tidal.pawpal.services.VeterinarioService;

@Controller
@RequestMapping("/veterinari")
public class VeterinariController {

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
    private VeterinarioService veterinarioService;

    @Autowired
    private AppuntamentoService appuntamentoService;

    @Autowired
    private RecensioneService recensioneService;

    @Autowired
    private SpecieService specieService;

    @Autowired
    private PrestazioneService prestazioneService;

    @Autowired
    private UserService userService;
    
    @GetMapping("/lista_veterinari")
    public String showListaVeterinari(@RequestParam Map<String, String> data, Model model) {
        try {
            List<Veterinario> listaVeterinari = veterinarioService.cercaConFiltri(data);
            model.addAttribute("lista_veterinari", listaVeterinari);
            model.addAttribute("lista_specie", specieService.elencaTutti());
            model.addAttribute("lista_prestazioni", prestazioneService.elencaTutti());
            return "veterinari";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    @PostMapping("/inserisci_appuntamento")
    public String sendAppuntamentoData(@RequestParam Map<String, String> data, Principal principal) {        
        try {
            acceptAuthenticated(principal, (authentication, utente) -> {
                // nel frontend, passare l'idCliente con {#authentication.id}
                // passare anche l'id del veterinario selezionato come idVeterinario
                appuntamentoService.registra(data);
            });
            return "redirect:/dash/appuntamenti";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    @PostMapping("/inserisci_recensione")
    public String sendRecensioneData(@RequestParam Map<String, String> data, Principal principal) {        
        try {
            acceptAuthenticated(principal, (authentication, utente) -> {
                // nel frontend, passare l'idCliente con {#authentication.id}
                // passare anche l'id del veterinario selezionato come idVeterinario
                recensioneService.registra(data);
            });
            return "redirect:/dash/recensioni";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

}
