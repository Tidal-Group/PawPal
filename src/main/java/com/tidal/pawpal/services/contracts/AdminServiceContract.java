package com.tidal.pawpal.services.contracts;

import org.springframework.beans.factory.annotation.Autowired;

import com.tidal.pawpal.models.Amministratore;
import com.tidal.pawpal.models.User;

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

    public boolean hasAdminPermissions(User user) {
        return user instanceof Amministratore || user.getRuolo().equalsIgnoreCase("admin");
    }

}
