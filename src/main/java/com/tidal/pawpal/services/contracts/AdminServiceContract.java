package com.tidal.pawpal.services.contracts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tidal.pawpal.models.User;

// Façade
public abstract class AdminServiceContract {

    @Autowired
    protected ClienteServiceContract clienteService;

    @Autowired
    protected VeterinarioServiceContract veterinarioService;

    @Autowired
    protected SpecieServiceContract specieService;

    public abstract List<User> cercaUtentiPerNome();

}
