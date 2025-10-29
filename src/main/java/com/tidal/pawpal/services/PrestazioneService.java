package com.tidal.pawpal.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tidal.pawpal.models.Prestazione;
import com.tidal.pawpal.repositories.PrestazioneRepository;
import com.tidal.pawpal.services.contracts.PrestazioneServiceContract;


@Service

public class PrestazioneService extends PrestazioneServiceContract {

    @Autowired
    private PrestazioneRepository prestazioneRepository;

    @Override
    public Set<Prestazione> cercaPerRangePrezzo(Double prezzoMin, Double prezzoMax) {
        return prestazioneRepository.findByRangePrezzo(prezzoMin, prezzoMax);
    }

}
