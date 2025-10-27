package com.tidal.pawpal.services.contracts;

import java.util.List;

import com.tidal.pawpal.models.Recensione;

import com.tidal.pawpal.services.abstractions.GenericService;

import com.tidal.pawpal.services.abstractions.CreateService;
import com.tidal.pawpal.services.abstractions.ReadService;
import com.tidal.pawpal.services.abstractions.DeleteService;

public abstract class RecensioneServiceContract extends GenericService<Recensione, Long> implements
    CreateService<Recensione, Long>, 
    ReadService<Recensione, Long>,
    DeleteService<Recensione, Long> {

    public RecensioneServiceContract() {
        super(Recensione.class, Long.class);
    }

    public abstract List<Recensione> cercaPerVeterinario(Long idVeterinario);
    public abstract List<Recensione> cercaPerCliente(Long idCliente);

    public abstract Double calcolaVotoMedioVeterinario(Long idVeterinario);

}
