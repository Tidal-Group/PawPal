package com.tidal.pawpal.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tidal.pawpal.services.AuthService;
import com.tidal.pawpal.services.PrestazioneService;
import com.tidal.pawpal.services.SpecieService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private String redirectToPath(String refererUrl) {
       String requestPath = "/";
        try {
            URI refererUri = new URI(refererUrl);
            requestPath = refererUri.getPath();
        } catch (URISyntaxException | NullPointerException exception) {

        }
        return "redirect:" + requestPath;
    }

    public String redirectToLoginModal(String refererUrl) {
       return redirectToPath(refererUrl) + "#loginModal";
    }

    public String redirectToRegisterClienteModal(String refererUrl) {
        return redirectToPath(refererUrl) + "#registrazioneClienteModal";
    }

    public String redirectToRegisterVeterinarioModal(String refererUrl) {
        return redirectToPath(refererUrl) + "#registrazioneVeterinarioModal";
    }

    @Autowired
    public AuthService authService;

    @Autowired
    public SpecieService specieService;

    @Autowired
    public PrestazioneService prestazioneService;

    @PostMapping("/register_veterinario")
    public String sendRegisterVeterinarioData(
        @RequestHeader(value="Referer", required=false) String refererUrl,
        RedirectAttributes redirectAttributes,
        @RequestParam("specie") List<Long> listaIdSpecie,
        @RequestParam("prestazioni") List<Long> listaIdPrestazioni,
        @RequestParam Map<String, String> veterinarioData
    ) {
        try {
            authService.registraVeterinario(listaIdSpecie, listaIdPrestazioni, veterinarioData);
            redirectAttributes.addFlashAttribute("successMessage", "Registrazione effettuata con successo");
            return redirectToLoginModal(refererUrl);
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            return "redirect:/error";
        }
    }

    @PostMapping("/register_cliente")
    public String sendRegisterClienteData(
        @RequestHeader(value="Referer", required=false) String refererUrl,
        RedirectAttributes redirectAttributes,
        @RequestParam Map<String, String> clienteData
    ) {
        try {
            authService.registraCliente(clienteData);
            redirectAttributes.addFlashAttribute("successMessage", "Registrazione effettuata con successo");
            return redirectToLoginModal(refererUrl);
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

}
