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

import com.tidal.pawpal.services.contracts.AuthServiceContract;
import com.tidal.pawpal.services.contracts.PrestazioneServiceContract;
import com.tidal.pawpal.services.contracts.SpecieServiceContract;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    public AuthServiceContract authService;

    @Autowired
    public SpecieServiceContract specieService;

    @Autowired
    public PrestazioneServiceContract prestazioneService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/register_veterinario")
    public String getVeterinarioRegisterPage(Model model) {
        model.addAttribute("lista_specie", specieService.elencaTutti());
        model.addAttribute("lista_prestazioni", prestazioneService.elencaTutti());
        return "register_veterinario";
    }

    @PostMapping("/register_veterinario/form")
    public String sendRegisterVeterinarioData(@RequestParam("specie") List<Long> listaIdSpecie, @RequestParam("prestazioni") List<Long> listaIdPrestazioni, @RequestParam Map<String, String> veterinarioData) {
        try {
            System.out.println(listaIdSpecie);
            System.out.println(listaIdPrestazioni);
            System.out.println(veterinarioData);
            authService.registraVeterinario(listaIdSpecie, listaIdPrestazioni, veterinarioData);
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
