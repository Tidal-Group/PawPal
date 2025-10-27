package com.tidal.pawpal.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tidal.pawpal.models.Veterinario;
import com.tidal.pawpal.services.contracts.VeterinarioServiceContract;
@Service
public class VeterinarioService extends VeterinarioServiceContract {

    @Override
    public List<Veterinario> cercaPerSpecie(Long idSpecie) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cercaPerSpecie'");
    }

    @Override
    public List<Veterinario> cercaPerPrestazione(Long idPrestazione) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cercaPerPrestazione'");
    }

    @Override
    public List<Veterinario> cercaPerNominativo(String nome, String cognome) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cercaPerNominativo'");
    }

    @Override
    public List<Veterinario> cercaPerIndirizzo(String indirizzo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cercaPerIndirizzo'");
    }

}
