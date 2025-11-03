package com.tidal.pawpal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tidal.pawpal.services.PrestazioneService;
// import com.tidal.pawpal.services.RecensioneService;
import com.tidal.pawpal.services.SpecieService;
import com.tidal.pawpal.services.VeterinarioService;

@Controller
public class AppController {

    @Autowired
    private PrestazioneService prestazioneService;

    @Autowired
    private SpecieService specieService;



    @Autowired
    private VeterinarioService veterinarioService; 

    // @Autowired
    // private RecensioneService recensioneService; 

    @GetMapping("/")
    public String homepage(Model model) {
        model.addAttribute("lista_specie", specieService.elencaTutti());
        model.addAttribute("lista_prestazioni", prestazioneService.elencaTutti());

        model.addAttribute("lista_specializzazioni_popolari", veterinarioService.cercaSpecializzazioniPiuPopolari());
        model.addAttribute("lista_veterinari_popolari", veterinarioService.cercaVeterinariPiuPopolari());
        // model.addAttribute("lista_recensioni", recensioneService.elencaTutti());

        return "homepage";
    }

    @GetMapping("/403")
    public String forbidden(){
        return "403";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }

}
