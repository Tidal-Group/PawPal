package com.tidal.pawpal.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tidal.pawpal.models.Cliente;
import com.tidal.pawpal.models.Recensione;
import com.tidal.pawpal.models.Veterinario;
import com.tidal.pawpal.repositories.RecensioneRepository;
import com.tidal.pawpal.services.contracts.ClienteServiceContract;
import com.tidal.pawpal.services.contracts.VeterinarioServiceContract;
import com.tidal.pawpal.services.contracts.RecensioneServiceContract;

@Service
public class RecensioneService extends RecensioneServiceContract {

    @Autowired
    private VeterinarioServiceContract veterinarioService;

    @Autowired
    private ClienteServiceContract clienteService;
   
    @Autowired
    private RecensioneRepository recensioneRepository;

    @Override
    public Recensione registra (Map<String, String> data) {
        return super.registra(data, (recensione) -> {
            Veterinario veterinario = veterinarioService.cercaPerId(Long.parseLong(data.get("idVeterinario")));
            recensione.setVeterinario(veterinario);
            Cliente cliente = clienteService.cercaPerId(Long.parseLong(data.get("idCliente")));
            recensione.setCliente(cliente);
        });
    }

    @Override
    public List<Recensione> cercaPerVeterinario(Long Veterinario) {
        return recensioneRepository.findByVeterinario(Veterinario);
    }

    @Override
    public List<Recensione> cercaPerCliente(Long idCliente) {
        return recensioneRepository.findByCliente(idCliente);
    }

    @Override
    public Double calcolaVotoMedioVeterinario(Long idVeterinario) {
        return recensioneRepository.calculateAverageRatingVeterinario(idVeterinario);
    }

}
