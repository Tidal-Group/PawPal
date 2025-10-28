package com.tidal.pawpal.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tidal.pawpal.models.Cliente;
import com.tidal.pawpal.models.Recensione;
import com.tidal.pawpal.models.Veterinario;
import com.tidal.pawpal.services.contracts.ClienteServiceContract;
import com.tidal.pawpal.services.contracts.VeterinarioServiceContract;
import com.tidal.pawpal.services.contracts.RecensioneServiceContract;
@Service
public class RecensioneService extends RecensioneServiceContract {
    @Autowired
    public RecensioneServiceContract recensioneService;
    @Autowired
    public VeterinarioServiceContract veterinarioService;
    @Autowired
    public ClienteServiceContract clienteService;
   

    @Override
    public Recensione registra (Map<String, String> data) {
        Recensione recensione = super.registra(data);

    Veterinario v = veterinarioService.cercaPerId(Long.parseLong(data.get("idVeterinario")));
        recensione.setVeterinario(v);
    Cliente c = clienteService.cercaPerId(Long.parseLong(data.get("idCliente")));
        recensione.setCliente(c);
        return recensione;
    }   
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
