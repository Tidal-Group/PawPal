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
import com.tidal.pawpal.services.contracts.AppuntamentoServiceContract;
import com.tidal.pawpal.services.contracts.RecensioneServiceContract;
import com.tidal.pawpal.services.contracts.UserServiceContract;
import com.tidal.pawpal.services.contracts.VeterinarioServiceContract;

import jakarta.servlet.http.HttpSession;



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
    public VeterinarioServiceContract veterinarioService;

    @Autowired
    public AppuntamentoServiceContract appuntamentoService;

    @Autowired
    public RecensioneServiceContract recensioneService;

    @Autowired
    public UserServiceContract userService;
    
    @GetMapping("/lista_veterinari")
    public String showListaVeterinari(@RequestParam Map<String, String> data, Model model) {
        try {
            List<Veterinario> listaVeterinari = veterinarioService.cercaConFiltri(data);
            model.addAttribute("lista_veterinari", listaVeterinari);
            return "veterinari";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
    }

    @PostMapping("/inserisci_appuntamento")
    public String sendAppuntamentoData(@RequestParam Map<String, String> data, Principal principal) {        
        try {
            acceptAuthenticated(principal, (authentication, utente) -> {
                // IMPLEMENT: che dati servono per registrare l'appuntamento?
                // come può il service sapere qual è il cliente e quale il veterinario?
                appuntamentoService.registra(data, utente.getId());
            });
            return "redirect:/dash/appuntamenti";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
    }

    @PostMapping("/inserisci_recensione")
    public String sendRecensioneData(@RequestParam Map<String, String> data, Principal principal) {        
        try {
            acceptAuthenticated(principal, (authentication, utente) -> {
                // IMPLEMENT: che dati servono per registrare l'appuntamento?
                // come può il service sapere qual è il cliente e quale il veterinario?
                recensioneService.registra(data, utente.getId());
            });
            return "redirect:/dash/recensioni";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
    }

}
