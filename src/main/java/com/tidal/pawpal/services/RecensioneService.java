package com.tidal.pawpal.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tidal.pawpal.models.Recensione;
import com.tidal.pawpal.services.contracts.RecensioneServiceContract;
@Service
public class RecensioneService extends RecensioneServiceContract {

    @Override
    public List<Recensione> cercaPerVeterinario(Long idVeterinario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cercaPerVeterinario'");
    }

    @Override
    public List<Recensione> cercaPerCliente(Long idCliente) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cercaPerCliente'");
    }

    @Override
    public Double calcolaVotoMedioVeterinario(Long idVeterinario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calcolaVotoMedioVeterinario'");
    }

}
