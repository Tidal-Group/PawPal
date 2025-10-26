package com.tidal.pawpal.services.contracts;

import com.tidal.pawpal.models.Veterinario;

import com.tidal.pawpal.services.abstractions.GenericService;

import com.tidal.pawpal.services.abstractions.CreateService;
import com.tidal.pawpal.services.abstractions.ReadService;
import com.tidal.pawpal.services.abstractions.UpdateService;
import com.tidal.pawpal.services.abstractions.DeleteService;

public abstract class VeterinarioServiceContract extends GenericService<Veterinario, Long> implements
    CreateService<Veterinario, Long>, 
    ReadService<Veterinario, Long>,
    UpdateService<Veterinario, Long>,
    DeleteService<Veterinario, Long> {

    public VeterinarioServiceContract() {
        super(Veterinario.class, Long.class);
    }

}
