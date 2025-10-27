package com.tidal.pawpal.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tidal.pawpal.models.Cliente;
import com.tidal.pawpal.models.Veterinario;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
@RequestMapping("/dash")
public class DashController {

    @Autowired
    public ClienteServiceContract clienteService;

    @Autowired
    public VeterinarioServiceContract veterinarioService;

    @Autowired
    public AppuntamentoServiceContract appuntamentoService;

    @Autowired
    public RecensioneServiceContract recensioneService;

    @GetMapping("/profilo")
    public String showProfilo() {
        return "profilo";
    }

    @PostMapping("/profilo/modifica_dati")
    public String sendDatiProfilo(@RequestParam Map<String, String> data, HttpSession session) {
        if(session == null) return "redirect:/auth/login";

        try {
            if(session.getAttribute("utente") instanceof Cliente cliente) {
                Cliente clienteAggiornato = clienteService.modifica(cliente.getId(), data);
                session.setAttribute("utente", clienteAggiornato);
            } else if(session.getAttribute("utente") instanceof Veterinario veterinario) {
                Veterinario veterinarioAggiornato = veterinarioService.modifica(veterinario.getId(), data);
                session.setAttribute("utente", veterinarioAggiornato);
            }
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
        return "redirect:/dash/profilo";
    }

    @GetMapping("/appuntamenti")
    public String mostraAppuntamenti() {
        return "appuntamenti";
    }

    @PostMapping("/appuntamenti/modifica_appuntamento")
    public String handleAppuntamentoUpdate(@RequestParam Map<String, String> data, HttpSession session) {

        if(session == null) return "redirect:/auth/login";

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

        if(session == null) return "redirect:/auth/login";

        try {
            appuntamentoService.elimina(id, session.getAttribute("utente"));
            return "redirect:/dash/appuntamenti";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }

    }

    @GetMapping("/recensioni")
    public String showRecensioni() {
        return "recensioni";
    }

    @PostMapping("/recensioni/elimina_recensione")
    public String postMethodName(@RequestParam Long id, HttpSession session) {

        if(session == null) return "redirect:/auth/login";

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
        return new String();
    }
    

}
