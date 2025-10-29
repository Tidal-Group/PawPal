package com.tidal.pawpal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tidal.pawpal.services.contracts.PrestazioneServiceContract;
import com.tidal.pawpal.services.contracts.SpecieServiceContract;

@Controller
public class AppController {

    @Autowired
    private PrestazioneServiceContract prestazioneService;

    @Autowired
    private SpecieServiceContract specieService;

    // PROBABILMENTE DA TOGLIERE
    @Value("${app.pawpal.nome:Pawpal}")
    private String appName;

    @GetMapping("/")
    public String homepage(Model model) {
        // PROBABILMENTE DA TOGLIERE
        model.addAttribute("app-name", appName);
        model.addAttribute("lista_specie", specieService.elencaTutti());
        model.addAttribute("lista_prestazioni", prestazioneService.elencaTutti());
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
