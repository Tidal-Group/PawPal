package com.tidal.pawpal.services.contracts;

import org.springframework.beans.factory.annotation.Autowired;

// Façade
public abstract class AdminServiceContract {

    @Autowired
    protected ClienteServiceContract clienteService;

    @Autowired
    protected VeterinarioServiceContract veterinarioService;

    @Autowired
    protected SpecieServiceContract specieService;

    @Autowired
    protected PrestazioneServiceContract prestazioneService;

}
