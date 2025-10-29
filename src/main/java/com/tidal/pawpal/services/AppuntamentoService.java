package com.tidal.pawpal.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tidal.pawpal.models.Appuntamento;
import com.tidal.pawpal.models.Cliente;
import com.tidal.pawpal.models.Veterinario;
import com.tidal.pawpal.repositories.AppuntamentoRepository;
import com.tidal.pawpal.services.contracts.AppuntamentoServiceContract;

@Service
public class AppuntamentoService extends AppuntamentoServiceContract {

    @Autowired
    private AppuntamentoRepository appuntamentoRepository;

    @Autowired
    private VeterinarioService veterinarioService;

    @Autowired
    private ClienteService clienteService;

    @Override
    public Appuntamento registra(Map<String, String> data) {
        return super.registra(data, (appuntamento) -> {
            Veterinario veterinario = veterinarioService.cercaPerId(Long.parseLong(data.get("idVeterinario")));
            appuntamento.setVeterinario(veterinario);
            Cliente cliente = clienteService.cercaPerId(Long.parseLong(data.get("idCliente")));
            appuntamento.setCliente(cliente);
        });
    }

    @Override
    public List<Appuntamento> cercaPerVeterinario(Long idVeterinario) {
        return appuntamentoRepository.findByVeterinario(idVeterinario);
    }

    @Override
    public List<Appuntamento> cercaPerCliente(Long idCliente) {
        return appuntamentoRepository.findByCliente(idCliente);
    }

}
