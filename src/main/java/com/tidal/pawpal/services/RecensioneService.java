package com.tidal.pawpal.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tidal.pawpal.dto.RecensioneDto;
import com.tidal.pawpal.events.UserDeletionEvent;
import com.tidal.pawpal.models.Cliente;
import com.tidal.pawpal.models.Recensione;
import com.tidal.pawpal.models.Veterinario;
import com.tidal.pawpal.repositories.RecensioneRepository;
import com.tidal.pawpal.services.contracts.RecensioneServiceContract;

@Service
public class RecensioneService extends RecensioneServiceContract {

    @Autowired
    private RecensioneRepository recensioneRepository;

    @Autowired
    private VeterinarioService veterinarioService;

    @Autowired
    private ClienteService clienteService;

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
    public void elimina(Long id) {
        super.elimina(id, (recensione) -> {
            recensione.detachFromParents();
        });
    }

    @Override
    public Integer gestisciEliminazioneCliente(UserDeletionEvent event) {
        return recensioneRepository.clearClienteForeignKey(event.getUserId());
    }

    @Override
    public Integer gestisciEliminazioneVeterinario(UserDeletionEvent event) {
        return recensioneRepository.clearVeterinarioForeignKey(event.getUserId());
    }

    @Override
    public List<RecensioneDto> cercaPerVeterinario(Long Veterinario) {
        return recensioneRepository.findByVeterinario(Veterinario);
    }

    @Override
    public List<RecensioneDto> cercaPerCliente(Long idCliente) {
        return recensioneRepository.findByCliente(idCliente);
    }

    @Override
    public Long contaRecensioniPerCliente(Long idCliente) {
        return recensioneRepository.countByClienteId(idCliente);
    }

    @Override
    public Long contaRecensioniPerVeterinario(Long veterinarioId) {
        return recensioneRepository.countByVeterinarioId(veterinarioId);
    }

    @Override
    public Double calcolaVotoMedioVeterinario(Long veterinarioId) {
        Double avg = recensioneRepository.calculateAverageRatingVeterinario(veterinarioId);
        if (avg == null) return 0.0;
        return Math.round(avg * 10.0) / 10.0;
    }

}
