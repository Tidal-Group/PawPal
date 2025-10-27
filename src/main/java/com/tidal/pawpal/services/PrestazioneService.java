package com.tidal.pawpal.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tidal.pawpal.models.Prestazione;
import com.tidal.pawpal.services.contracts.PrestazioneServiceContract;


@Service

public class PrestazioneService extends PrestazioneServiceContract {

    @Override
    public List<Prestazione> cercaPerRangePrezzo(Double prezzoMin, Double prezzoMax) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cercaPerRangePrezzo'");
    }

}
