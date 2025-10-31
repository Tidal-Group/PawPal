package com.tidal.pawpal.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tidal.pawpal.services.AuthService;
import com.tidal.pawpal.services.PrestazioneService;
import com.tidal.pawpal.services.SpecieService;


@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    public AuthService authService;

    @Autowired
    public SpecieService specieService;

    @Autowired
    public PrestazioneService prestazioneService;

    @GetMapping("/login")
    public String openModalLogin(Model model) {
        model.addAttribute("openModal", "auth/login");
        return "homepage";
    }

    @GetMapping("/register_veterinario")
    public String openModalRegisterVeterinario(Model model) {
        model.addAttribute("openModal", "auth/register_veterinario");
        return "homepage";
    }

    @GetMapping("/register_cliente")
    public String openModalRegisterCliente(Model model) {
        model.addAttribute("openModal", "auth/register_cliente");
        return "homepage";
    }

    @PostMapping("/register_veterinario")
    public String sendRegisterVeterinarioData(
        RedirectAttributes redirectAttributes,
        @RequestParam("specie") List<Long> listaIdSpecie,
        @RequestParam("prestazioni") List<Long> listaIdPrestazioni,
        @RequestParam Map<String, String> veterinarioData
    ) {
        try {
            authService.registraVeterinario(listaIdSpecie, listaIdPrestazioni, veterinarioData);
            redirectAttributes.addFlashAttribute("successMessage", "Registrazione effettuata con successo");
            return "redirect:/auth/login";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
    }

    @PostMapping("/register_cliente")
    public String sendRegisterClienteData(
        RedirectAttributes redirectAttributes,
        @RequestParam Map<String, String> clienteData
    ) {
        try {
            authService.registraCliente(clienteData);
            redirectAttributes.addFlashAttribute("successMessage", "Registrazione effettuata con successo");
            return "redirect:/auth/login";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

}
