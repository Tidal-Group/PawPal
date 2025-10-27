package com.tidal.pawpal.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tidal.pawpal.models.Prestazione;
import com.tidal.pawpal.models.Specie;
import com.tidal.pawpal.models.User;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    public UserServiceContract userService;

    @Autowired
    public PrestazioneServiceContract prestazioneService;

    @Autowired
    public SpecieServiceContract specieService;

    @GetMapping("utenti")
    public String showUtenti(@RequestParam Map<String, String> params, Model model, HttpSession session) {
        if(session == null || session.getAttribute("utente") == null) return "redirect:/auth/login";

        try {
            // IMPLEMENT: UserService verificherà che la richiesta provenga da un account con permessi da admin
            List<User> listaUtenti = userService.cercaConFiltri(params, session.getAttribute("utente"));
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
            User utente = userService.registra(data);
            // riaggiorna la lista
            // DEBUG: inefficiente, perché rieffettua la query ogni volta
            return "redirect:/admin/utenti";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
    }
    

    @GetMapping("prestazioni")
    public String showPrestazioni(@RequestParam Map<String, String> params, Model model, HttpSession session) {
        if(session == null || session.getAttribute("utente") == null) return "redirect:/auth/login";

        try {
            // IMPLEMENT: PrestazioneService verificherà che la richiesta provenga da un account con permessi da admin
            List<Prestazione> listaPrestazioni = prestazioneService.cercaConFiltri(params, session.getAttribute("utente"));
            model.addAttribute("lista_prestazioni", listaPrestazioni);
            return "admin_prestazioni";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
        
    }

    @GetMapping("specie")
    public String showSpecie(@RequestParam Map<String, String> params, Model model, HttpSession session) {
        if(session == null || session.getAttribute("utente") == null) return "redirect:/auth/login";

        try {
            // IMPLEMENT: PrestazioneService verificherà che la richiesta provenga da un account con permessi da admin
            List<Specie> listaSpecie = specieService.cercaConFiltri(params, session.getAttribute("utente"));
            model.addAttribute("lista_specie", listaSpecie);
            return "admin_specie";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
        
    }
    
}
