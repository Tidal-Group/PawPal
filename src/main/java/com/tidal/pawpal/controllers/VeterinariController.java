package com.tidal.pawpal.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tidal.pawpal.models.Veterinario;

import jakarta.servlet.http.HttpSession;



@Controller
public class VeterinariController {

    @Autowired
    public VeterinarioServiceContract veterinarioService;

    @Autowired
    public AppuntamentoServiceContract appuntamentoService;

    @Autowired
    public RecensioneServiceContract recensioneService;
    
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
    public String sendAppuntamentoData(@RequestParam Map<String, String> data, HttpSession session) {
        if(session == null || session.getAttribute("utente") == null) return "redirect:/auth/login";
        
        try {
            // IMPLEMENT: il Service verificherà che l'utente abbia i permessi per registrare l'appuntamento
            appuntamentoService.registra(data, session.getAttribute("utente"));
            return "redirect:/dash/appuntamenti";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
    }

    @PostMapping("/inserisci_recensione")
    public String sendRecensioneData(@RequestParam Map<String, String> data, HttpSession session) {
        if(session == null || session.getAttribute("utente") == null) return "redirect:/auth/login";
        
        try {
            // IMPLEMENT: il Service verificherà che l'utente abbia i permessi per inserire la recensione
            recensioneService.registra(data, session.getAttribute("utente"));
            return "redirect:/dash/recensioni";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
    }

}
