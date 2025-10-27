package com.tidal.pawpal.services.contracts;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.tidal.pawpal.models.User;
import com.tidal.pawpal.models.Cliente;
import com.tidal.pawpal.models.Veterinario;

// Façade
public abstract class AuthServiceContract {

    @Autowired
    protected UserServiceContract userService;

    @Autowired
    protected ClienteServiceContract clienteService;

    @Autowired
    protected VeterinarioServiceContract veterinarioService;

    public abstract User validaCredenziali(String username, String password);

    public abstract Cliente registraCliente(Map<String, String> dati);
    public abstract Veterinario registraVeterinario(Map<String, String> dati);

    public abstract void cambiaPassword(Long id, String password);

}