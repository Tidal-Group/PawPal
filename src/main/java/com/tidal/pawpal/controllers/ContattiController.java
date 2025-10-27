package com.tidal.pawpal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ContattiController {

    @GetMapping("/contatti")
    public String getContatti() {
        return "contatti";
    }

}
