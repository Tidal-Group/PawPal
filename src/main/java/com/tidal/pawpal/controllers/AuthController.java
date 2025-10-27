package com.tidal.pawpal.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tidal.pawpal.models.User;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    public AuthServiceContract authService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }
    
    @PostMapping("/login/form")
    public String sendLoginData(@RequestParam String username, @RequestParam String password, HttpSession session) {
        try {
            User user = authService.validaCredenziali(username, password);
            session.setAttribute("utente", user);
            session.setAttribute("ruolo", user.getRuolo());
            return "redirect:/";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
    }

    @GetMapping("/register_veterinario")
    public String getVeterinarioRegisterPage() {
        return "register_veterinario";
    }

    @PostMapping("/register_veterinario/form")
    public String sendRegisterVeterinarioData(@RequestParam Map<String, String> veterinarioData) {
        try {
            authService.registraVeterinario(veterinarioData);
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
            return "redirect:/error";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
    

}
