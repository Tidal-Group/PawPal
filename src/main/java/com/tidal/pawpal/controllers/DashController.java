package com.tidal.pawpal.controllers;

import java.security.Principal;
import java.util.ArrayList;
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

import com.tidal.pawpal.models.Appuntamento;
import com.tidal.pawpal.models.Recensione;
import com.tidal.pawpal.models.User;
import com.tidal.pawpal.services.contracts.AppuntamentoServiceContract;
import com.tidal.pawpal.services.contracts.ClienteServiceContract;
import com.tidal.pawpal.services.contracts.RecensioneServiceContract;
import com.tidal.pawpal.services.contracts.UserServiceContract;
import com.tidal.pawpal.services.contracts.VeterinarioServiceContract;

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
    public ClienteServiceContract clienteService;

    @Autowired
    public VeterinarioServiceContract veterinarioService;

    @Autowired
    public AppuntamentoServiceContract appuntamentoService;

    @Autowired
    public RecensioneServiceContract recensioneService;

    @Autowired
    public UserServiceContract userService;

    @GetMapping("")
    public String showDashboard(Principal principal, Model model) {
        acceptAuthenticated(principal, (authentication, utente) -> {
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
            if(isCliente(authentication))
                utente = clienteService.cercaPerId(utente.getId());
            else if(isVeterinario(authentication))
                utente = veterinarioService.cercaPerId(utente.getId());
            model.addAttribute("user", utente);
            model.addAttribute("lista_recensioni", listaRecensioni);
            model.addAttribute("lista_appuntamenti", listaAppuntamenti);
            model.addAttribute("ruolo", utente.getRuolo());
        });
        return "dashboard_utente";
    }

    @GetMapping("/profilo")
    public String showProfilo(Model model, Principal principal) {
        // DEBUG: Problema di sicurezza: tra i campi di utente, c'è anche la sua password
        try {
            acceptAuthenticated(principal, (authentication, utente) -> {
                if(isCliente(authentication))
                    utente = clienteService.cercaPerId(utente.getId());
                else if(isVeterinario(authentication))
                    utente = veterinarioService.cercaPerId(utente.getId());
                model.addAttribute("utente", utente);
                model.addAttribute("ruolo", utente.getRuolo());
            });
            return "profilo";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    @PostMapping("/profilo/modifica_username")
    public String sendUsername(@RequestParam String username, Principal principal) {        
        try {
            acceptAuthenticated(principal, (authentication, utente) -> {
                userService.modificaUsername(utente.getId(), username);
            });
            return "redirect:/dash/profilo";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    @PostMapping("/profilo/modifica_password")
    public String sendPassword(@RequestParam String password, Principal principal) {        
        try {
            acceptAuthenticated(principal, (authentication, utente) -> {
                userService.modificaPassword(utente.getId(), password);
            });
            return "redirect:/dash/profilo";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
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
            return "redirect:/dash/profilo";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    @GetMapping("/appuntamenti")
    public String mostraAppuntamenti(Model model, Principal principal) {
        try {
            acceptAuthenticated(principal, (authentication, utente) -> {
                List<Appuntamento> listaAppuntamenti = new ArrayList<>();
                if(isCliente(authentication))
                    listaAppuntamenti = appuntamentoService.cercaPerCliente(utente.getId());
                else if(isVeterinario(authentication))
                    listaAppuntamenti = appuntamentoService.cercaPerVeterinario(utente.getId());
                model.addAttribute("lista_appuntamenti", listaAppuntamenti);
                model.addAttribute("ruolo", utente.getRuolo());
            });
            return "appuntamenti";
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
    public String showRecensioni(Model model, Principal principal) {
        try {
            acceptAuthenticated(principal, (authentication, utente) -> {
                List<Recensione> listaRecensioni = new ArrayList<>();
                if(isCliente(authentication))
                    listaRecensioni = recensioneService.cercaPerCliente(utente.getId());
                else if(isVeterinario(authentication))
                    listaRecensioni = recensioneService.cercaPerVeterinario(utente.getId());
                model.addAttribute("lista_recensioni", listaRecensioni);
                model.addAttribute("ruolo", utente.getRuolo());
            });
            return "recensioni";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }

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
    
    @GetMapping("/linee_guida")
    public String showLineeGuida() {
        return "linee_guida";
    }
    

}
