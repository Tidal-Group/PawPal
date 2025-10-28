package com.tidal.pawpal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tidal.pawpal.models.Specie;
import com.tidal.pawpal.repositories.SpecieRepository;
import com.tidal.pawpal.services.contracts.SpecieServiceContract;

@Service
public class SpecieService extends SpecieServiceContract {

    @Autowired
    private SpecieRepository specieRepository;

    @Override
    public Specie cercaPerNomeSpecie(String nomeSpecie) {
        return specieRepository.findByNomeSpecie(nomeSpecie);
    }

}
