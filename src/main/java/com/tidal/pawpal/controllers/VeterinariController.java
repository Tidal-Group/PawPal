package com.tidal.pawpal.controllers;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tidal.pawpal.models.Veterinario;
import com.tidal.pawpal.services.AppuntamentoService;
import com.tidal.pawpal.services.PrestazioneService;
import com.tidal.pawpal.services.RecensioneService;
import com.tidal.pawpal.services.SpecieService;
import com.tidal.pawpal.services.VeterinarioService;
import com.tidal.pawpal.utils.ControllerUtils;


@Controller
@RequestMapping("/veterinari")
public class VeterinariController extends AuthenticatedController {

    @Autowired
    private VeterinarioService veterinarioService;

    @Autowired
    private AppuntamentoService appuntamentoService;

    @Autowired
    private RecensioneService recensioneService;

    @Autowired
    private SpecieService specieService;

    @Autowired
    private PrestazioneService prestazioneService;
    
    @GetMapping("/lista_veterinari")
    public String showListaVeterinari(
        Model model,
        @RequestParam Optional<String> filtroNome,
        @RequestParam Optional<String> filtroCognome,
        @RequestParam Optional<String> filtroCitta,
        @RequestParam Optional<Long> filtroPrestazione,
        @RequestParam Optional<Long> filtroSpecie
    ) {
        try {
            Map<String, Object> filtriRicerca = new HashMap<>();

            if(filtroNome.isPresent()) {
                String nome = filtroNome.get();
                filtriRicerca.put("nome", nome);
                model.addAttribute("filtroNome", nome);
            }

            if(filtroCognome.isPresent()) {
                String cognome = filtroCognome.get();
                filtriRicerca.put("cognome", cognome);
                model.addAttribute("filtroCognome", cognome);
            }

            if(filtroCitta.isPresent()) {
                String citta = filtroCitta.get();
                filtriRicerca.put("citta", citta);
                model.addAttribute("filtroCitta", citta);
            }

            if(filtroPrestazione.isPresent()) {
                Long prestazione = filtroPrestazione.get();
                filtriRicerca.put("prestazione", prestazione);
                model.addAttribute("filtroPrestazione", prestazione);
            }

            if(filtroSpecie.isPresent()) {
                Long specie = filtroSpecie.get();
                filtriRicerca.put("specie", specie);
                model.addAttribute("filtroSpecie", specie);
            }

            List<Veterinario> listaVeterinari;
            if(filtriRicerca.isEmpty()) listaVeterinari = veterinarioService.elencaTutti();
            else listaVeterinari = veterinarioService.cercaConFiltri(filtriRicerca);

            model.addAttribute("lista_veterinari", listaVeterinari);
            model.addAttribute("lista_specie", specieService.elencaTutti());
            model.addAttribute("lista_prestazioni", prestazioneService.elencaTutti());

            return "veterinari";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    @PostMapping("/lista_veterinari/filtra")
    public String filterListaVeterinari(
        @RequestParam String redirectUrl,
        @RequestParam Optional<String> filtroNome,
        @RequestParam Optional<String> filtroCognome,
        @RequestParam Optional<String> filtroCitta,
        @RequestParam Optional<Long> filtroPrestazione,
        @RequestParam Optional<Long> filtroSpecie
    ) {
        Map<String, String> queryParams = new HashMap<>();

        queryParams.put("filtroNome", filtroNome.isPresent() ? filtroNome.get() : "");
        queryParams.put("filtroCognome", filtroCognome.isPresent() ? filtroCognome.get() : "");
        queryParams.put("filtroCitta", filtroCitta.isPresent() ? filtroCitta.get() : "");
        queryParams.put("filtroPrestazione", filtroPrestazione.isPresent() ? String.valueOf(filtroPrestazione.get()) : "");
        queryParams.put("filtroSpecie", filtroSpecie.isPresent() ? String.valueOf(filtroSpecie.get()) : "");

        return ControllerUtils.redirectToQueryParams(redirectUrl, queryParams);
    }    

    @GetMapping("/lista_veterinari/{idVeterinario}")
    @ResponseBody
    public Veterinario getVetProfileDataForModal(
        @PathVariable Long idVeterinario
    ) {
        return veterinarioService.cercaPerId(idVeterinario);
    }
    
    @PostMapping("/inserisci_appuntamento")
    public String sendAppuntamentoData(
        @RequestParam Map<String, String> data,
        Principal principal
    ) {        
        try {
            acceptAuthenticated(principal, (authentication, utente) -> {
                // nel frontend, passare l'idCliente con {#authentication.id}
                // passare anche l'id del veterinario selezionato come idVeterinario
                appuntamentoService.registra(data);
            });
            return "redirect:/dash/appuntamenti";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

    @PostMapping("/inserisci_recensione")
    public String sendRecensioneData(
        @RequestParam Map<String, String> data,
        Principal principal
    ) {        
        try {
            acceptAuthenticated(principal, (authentication, utente) -> {
                // nel frontend, passare l'idCliente con {#authentication.id}
                // passare anche l'id del veterinario selezionato come idVeterinario
                recensioneService.registra(data);
            });
            return "redirect:/dash/recensioni";
        } catch(Exception exception) {
            // IMPLEMENT CUSTOM ERROR HANDLING
            exception.printStackTrace();
            return "redirect:/error";
        }
    }

}
