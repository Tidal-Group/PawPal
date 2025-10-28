package com.tidal.pawpal.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tidal.pawpal.models.Appuntamento;
import com.tidal.pawpal.models.Cliente;
import com.tidal.pawpal.models.Recensione;
import com.tidal.pawpal.models.User;
import com.tidal.pawpal.models.Veterinario;
import com.tidal.pawpal.services.contracts.AppuntamentoServiceContract;
import com.tidal.pawpal.services.contracts.ClienteServiceContract;
import com.tidal.pawpal.services.contracts.RecensioneServiceContract;
import com.tidal.pawpal.services.contracts.UserServiceContract;
import com.tidal.pawpal.services.contracts.VeterinarioServiceContract;

import jakarta.servlet.http.HttpSession;




@Controller
@RequestMapping("/dash")
public class DashController {

    private static boolean isCliente(Authentication authentication) {
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("CLIENTE"));
    }

    private static boolean isVeterinario(Authentication authentication) {
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("VETERINARIO"));
    }

    @Autowired
    public ClienteServiceContract clienteService;

    @Autowired
    public VeterinarioServiceContract veterinarioService;

    @Autowired
    public AppuntamentoServiceContract appuntamentoService;

    @Autowired
    public RecensioneServiceContract recensioneService;

    @Autowired
    public UserServiceContract userService;

    @GetMapping("/profilo")
    public String showProfilo(Model model, Principal principal) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // DEBUG: Problema di sicurezza: tra i campi di utente, c'è anche la sua password
        try {
            User utente = userService.cercaPerUsername(principal.getName());
            if(isCliente(authentication))
                utente = clienteService.cercaPerId(utente.getId());
            else if(isVeterinario(authentication))
                utente = veterinarioService.cercaPerId(utente.getId());
            model.addAttribute("utente", utente);
            model.addAttribute("ruolo", utente.getRuolo());
            return "profilo";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }

    }

    @PostMapping("/profilo/modifica_username")
    public String sendUsername(@RequestParam String username, Principal principal) {        
        try {
            User utente = userService.cercaPerUsername(principal.getName());
            userService.modificaUsername(utente.getId(), username);
            return "redirect:/dash/profilo";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
    }

    @PostMapping("/profilo/modifica_password")
    public String sendPassword(@RequestParam String password, Principal principal) {        
        try {
            User utente = userService.cercaPerUsername(principal.getName());
            userService.modificaPassword(utente.getId(), password);
            return "redirect:/dash/profilo";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
    }

    @PostMapping("/profilo/modifica_dati")
    public String sendDatiProfilo(@RequestParam Map<String, String> data, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            User utente = userService.cercaPerUsername(principal.getName());
            if(isCliente(authentication))
                clienteService.modifica(utente.getId(), data);
            else if(isVeterinario(authentication))
                veterinarioService.modifica(utente.getId(), data);
            return "redirect:/dash/profilo";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
    }

    @GetMapping("/appuntamenti")
    public String mostraAppuntamenti(Model model, HttpSession session) {
        if(session == null || session.getAttribute("utente") == null) return "redirect:/login";

        try {
            List<Appuntamento> listaAppuntamenti;
            if(session.getAttribute("utente") instanceof Cliente cliente)
                listaAppuntamenti = appuntamentoService.cercaPerCliente(cliente.getId());
            else if(session.getAttribute("utente") instanceof Veterinario veterinario)
                listaAppuntamenti = appuntamentoService.cercaPerVeterinario(veterinario.getId());

            model.addAttribute("lista_appuntamenti", listaAppuntamenti);

            return "appuntamenti";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }

    }

    @PostMapping("/appuntamenti/modifica_appuntamento")
    public String handleAppuntamentoUpdate(@RequestParam Map<String, String> data, HttpSession session) {

        if(session == null || session.getAttribute("utente") == null) return "redirect:/auth/login";

        try {
            appuntamentoService.modifica(data, session.getAttribute("utente"));
            return "redirect:/dash/appuntamenti";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }

    }

    @PostMapping("/appuntamenti/elimina_appuntamento")
    public String handleAppuntamentoDeletion(@RequestParam Long id, HttpSession session) {

        if(session == null || session.getAttribute("utente") == null) return "redirect:/auth/login";

        try {
            appuntamentoService.elimina(id, session.getAttribute("utente"));
            return "redirect:/dash/appuntamenti";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }

    }

    @GetMapping("/recensioni")
    public String showRecensioni(Model model, HttpSession session) {

        if(session == null || session.getAttribute("utente") == null) return "redirect:/login";

        try {
            List<Recensione> listaRecensioni;
            if(session.getAttribute("utente") instanceof Cliente cliente)
                listaRecensioni = recensioneService.cercaPerCliente(cliente.getId());
            else if(session.getAttribute("utente") instanceof Veterinario veterinario)
                listaRecensioni = recensioneService.cercaPerVeterinario(veterinario.getId());

            model.addAttribute("lista_recensioni", listaRecensioni);

            return "recensioni";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }

    }

    @PostMapping("/recensioni/elimina_recensione")
    public String postMethodName(@RequestParam Long id, HttpSession session) {

        if(session == null || session.getAttribute("utente") == null) return "redirect:/auth/login";

        try {
            recensioneService.elimina(id, session.getAttribute("utente"));
            return "redirect:/dash/recensioni";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }

    }
    
    @GetMapping("/linee_guida")
    public String showLineeGuida() {
        return "linee_guida";
    }
    

}
