package com.tidal.pawpal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.tidal.pawpal.events.UserDeletionEvent;
import com.tidal.pawpal.models.Cliente;
import com.tidal.pawpal.repositories.ClienteRepository;
import com.tidal.pawpal.services.contracts.ClienteServiceContract;

@Service
public class ClienteService extends ClienteServiceContract {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public void elimina(Long id) {
        super.elimina(id, (cliente) -> {
            eventPublisher.publishEvent(new UserDeletionEvent(this, id));
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
