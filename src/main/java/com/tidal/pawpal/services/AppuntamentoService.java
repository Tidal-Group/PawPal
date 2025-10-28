package com.tidal.pawpal.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tidal.pawpal.models.Appuntamento;
import com.tidal.pawpal.models.Cliente;
import com.tidal.pawpal.models.Veterinario;
import com.tidal.pawpal.services.contracts.AppuntamentoServiceContract;
import com.tidal.pawpal.services.contracts.ClienteServiceContract;
import com.tidal.pawpal.services.contracts.VeterinarioServiceContract;
@Service
public class AppuntamentoService extends AppuntamentoServiceContract {

    @Autowired
    public VeterinarioServiceContract veterinarioService;

    @Autowired
    public ClienteServiceContract clienteService;

    @Override
    public Appuntamento registra(Map<String, String> data) {
        Appuntamento appuntamento = super.registra(data);
        Veterinario v = veterinarioService.cercaPerId(Long.parseLong(data.get("idVeterinario")));
        appuntamento.setVeterinario(v);
        Cliente c = clienteService.cercaPerId(Long.parseLong(data.get("idCliente")));
        appuntamento.setCliente(c);
        return appuntamento;
    }

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
