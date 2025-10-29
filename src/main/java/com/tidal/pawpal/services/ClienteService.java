package com.tidal.pawpal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tidal.pawpal.models.Appuntamento;
import com.tidal.pawpal.models.Cliente;
import com.tidal.pawpal.models.Recensione;
import com.tidal.pawpal.repositories.ClienteRepository;
import com.tidal.pawpal.services.contracts.AppuntamentoServiceContract;
import com.tidal.pawpal.services.contracts.ClienteServiceContract;
import com.tidal.pawpal.services.contracts.RecensioneServiceContract;

import jakarta.transaction.Transactional;

@Service
public class ClienteService extends ClienteServiceContract {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AppuntamentoServiceContract appuntamentoService;

    @Autowired
    private RecensioneServiceContract recensioneService;

    @Override
    @Transactional
    public void elimina(Long id) {
        super.elimina(id, (cliente) -> {
            List<Appuntamento> listaAppuntamenti = appuntamentoService.cercaPerCliente(id);
            listaAppuntamenti.forEach((appuntamento) -> appuntamento.setCliente(null));
            appuntamentoService.getRepository().saveAll(listaAppuntamenti);
            List<Recensione> listaRecensioni = recensioneService.cercaPerCliente(id);
            listaRecensioni.forEach((recensione) -> recensione.setCliente(null));
            recensioneService.getRepository().saveAll(listaRecensioni);
        });
    }

    @Override
    public Cliente cercaPerEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    @Override
    public Cliente cercaPerTelefono(String telefono) {
        return clienteRepository.findByTelefono(telefono);
    }
    
}
