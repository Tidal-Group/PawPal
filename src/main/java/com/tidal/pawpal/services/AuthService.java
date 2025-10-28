package com.tidal.pawpal.services;

import java.util.Map;

import com.tidal.pawpal.models.Cliente;
import com.tidal.pawpal.models.User;
import com.tidal.pawpal.models.Veterinario;
import com.tidal.pawpal.services.contracts.AuthServiceContract;

public class AuthService extends AuthServiceContract {

    @Override
    public User validaCredenziali(String username, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validaCredenziali'");
    }

    @Override
    public Cliente registraCliente(Map<String, String> dati) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registraCliente'");
    }

    @Override
    public Veterinario registraVeterinario(Map<String, String> dati) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registraVeterinario'");
    }
    
}
