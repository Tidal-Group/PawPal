package com.tidal.pawpal.services.contracts;

import java.util.List;

import com.tidal.pawpal.models.Specie;
import com.tidal.pawpal.services.abstractions.CreateService;
import com.tidal.pawpal.services.abstractions.DeleteService;
import com.tidal.pawpal.services.abstractions.GenericService;
import com.tidal.pawpal.services.abstractions.ReadService;
import com.tidal.pawpal.services.abstractions.UpdateService;

public abstract class SpecieServiceContract extends GenericService<Specie, Long> implements
    CreateService<Specie, Long>, 
    ReadService<Specie, Long>,
    UpdateService<Specie, Long>,
    DeleteService<Specie, Long> {

    public SpecieServiceContract() {
        super(Specie.class, Long.class);
    }

    public abstract List<Specie> cercaPerNomeSpecie(String nomeSpecie);

}
