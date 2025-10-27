package com.tidal.pawpal.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tidal.pawpal.models.Specie;
import com.tidal.pawpal.services.contracts.SpecieServiceContract;
@Service
public class SpecieService extends SpecieServiceContract {

    @Override
    public List<Specie> cercaPerNomeSpecie(String nomeSpecie) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cercaPerNomeSpecie'");
    }

}
