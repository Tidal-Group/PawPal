package com.tidal.pawpal.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tidal.pawpal.exceptions.MissingRequiredFieldException;
import com.tidal.pawpal.services.AuthService;
import com.tidal.pawpal.utils.ControllerUtils;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register_veterinario")
    public String submitVeterinarioRegistrationData(
        RedirectAttributes redirectAttributes,
        @RequestParam String redirectUrl,
        @RequestParam("specie") List<Long> listaIdSpecie,
        @RequestParam("prestazioni") List<Long> listaIdPrestazioni,
        @RequestParam Map<String, String> veterinarioData
    ) {
        try {
            authService.registraVeterinario(listaIdSpecie, listaIdPrestazioni, veterinarioData);
            redirectAttributes.addFlashAttribute("successMessage", "Registrazione effettuata con successo");
            return ControllerUtils.redirectToModal(redirectUrl, "loginModal");
        } catch(MissingRequiredFieldException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return ControllerUtils.redirectToModal(redirectUrl, "registrazioneVeterinarioModal");
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
    }

    @PostMapping("/register_cliente")
    public String submitClienteRegistrationData(
        RedirectAttributes redirectAttributes,
        @RequestParam String redirectUrl,
        @RequestParam Map<String, String> clienteData
    ) {
        try {
            authService.registraCliente(clienteData);
            redirectAttributes.addFlashAttribute("successMessage", "Registrazione effettuata con successo");
            return ControllerUtils.redirectToModal(redirectUrl, "loginModal");
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

}
