package com.tidal.pawpal.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    // PROBABILMENTE DA TOGLIERE
    @Value("${app.pawpal.nome:Pawpal}")
    private String appName;

    // PROBABILMENTE DA TOGLIERE
    @GetMapping("/")
    public String homepage(Model model) {
        model.addAttribute("app-name", appName);
        return "index";
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
