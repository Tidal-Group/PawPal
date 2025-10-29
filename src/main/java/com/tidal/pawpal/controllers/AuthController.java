package com.tidal.pawpal.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tidal.pawpal.services.contracts.AuthServiceContract;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    public AuthServiceContract authService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/register_veterinario")
    public String getVeterinarioRegisterPage() {
        return "register_veterinario";
    }

    @PostMapping("/register_veterinario/form")
    public String sendRegisterVeterinarioData(@RequestParam Map<String, String> veterinarioData) {
        try {
            authService.registraVeterinario(veterinarioData);
            // inviare specie come "specie"
            // inviare prestazioni come "prestazioni"

            // recuperare come listaIdSpecie
            // recuperare come listaIdPrestazioni
            return "redirect:/login";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
    }

    @GetMapping("/register_cliente")
    public String getClienteRegisterPage() {
        return "register_cliente";
    }

    @PostMapping("/register_cliente/form")
    public String sendRegisterClienteData(@RequestParam Map<String, String> clienteData) {
        try {
            authService.registraCliente(clienteData);
            return "redirect:/login";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

}
