package com.tidal.pawpal.services;

import org.springframework.stereotype.Service;

import com.tidal.pawpal.models.Cliente;
import com.tidal.pawpal.services.contracts.ClienteServiceContract;

@Service
public class ClienteService extends ClienteServiceContract {

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
