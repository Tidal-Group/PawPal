package com.tidal.pawpal.services.contracts;

import java.util.List;
import java.util.Map;

import com.tidal.pawpal.models.Veterinario;
import com.tidal.pawpal.services.abstractions.CreateService;
import com.tidal.pawpal.services.abstractions.DeleteService;
import com.tidal.pawpal.services.abstractions.GenericService;
import com.tidal.pawpal.services.abstractions.ReadService;
import com.tidal.pawpal.services.abstractions.UpdateService;

public abstract class VeterinarioServiceContract extends GenericService<Veterinario, Long> implements
    CreateService<Veterinario, Long>, 
    ReadService<Veterinario, Long>,
    UpdateService<Veterinario, Long>,
    DeleteService<Veterinario, Long> {

    public VeterinarioServiceContract() {
        super(Veterinario.class, Long.class);
    }

    public abstract List<Veterinario> cercaConFiltri(Map<String, String> filtri);
    public abstract List<Veterinario> cercaPerSpecie(Long idSpecie);
    public abstract List<Veterinario> cercaPerPrestazione(Long idPrestazione);
    public abstract List<Veterinario> cercaPerNominativo(String nome, String cognome);
    public abstract List<Veterinario> cercaPerIndirizzo(String indirizzo);

}
