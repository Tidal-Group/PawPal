package com.tidal.pawpal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tidal.pawpal.models.Cliente;
import com.tidal.pawpal.repositories.ClienteRepository;
import com.tidal.pawpal.services.contracts.ClienteServiceContract;

@Service
public class ClienteService extends ClienteServiceContract {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Cliente cercaPerEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cercaPerEmail'");
    }

    @Override
    public Cliente cercaPerTelefono(String telefono) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cercaPerTelefono'");
    }

    
}
