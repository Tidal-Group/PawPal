package com.tidal.pawpal.services.contracts;

import java.util.List;

import com.tidal.pawpal.models.Prestazione;

import com.tidal.pawpal.services.abstractions.GenericService;

import com.tidal.pawpal.services.abstractions.CreateService;
import com.tidal.pawpal.services.abstractions.ReadService;
import com.tidal.pawpal.services.abstractions.UpdateService;
import com.tidal.pawpal.services.abstractions.DeleteService;

public abstract class PrestazioneServiceContract extends GenericService<Prestazione, Long> implements
    CreateService<Prestazione, Long>, 
    ReadService<Prestazione, Long>,
    UpdateService<Prestazione, Long>,
    DeleteService<Prestazione, Long> {

    public PrestazioneServiceContract() {
        super(Prestazione.class, Long.class);
    }

    public abstract List<Prestazione> cercaPerRangePrezzo(Double prezzoMin, Double prezzoMax);

}
