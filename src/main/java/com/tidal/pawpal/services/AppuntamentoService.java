package com.tidal.pawpal.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tidal.pawpal.models.Appuntamento;
import com.tidal.pawpal.services.contracts.AppuntamentoServiceContract;
@Service
public class AppuntamentoService extends AppuntamentoServiceContract {

    @Override
    public List<Appuntamento> cercaPerVeterinario(Long idVeterinario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cercaPerVeterinario'");
    }

    @Override
    public List<Appuntamento> cercaPerCliente(Long idCliente) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cercaPerCliente'");
    }

}
