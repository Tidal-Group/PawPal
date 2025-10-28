package com.tidal.pawpal.services.contracts;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;

import com.tidal.pawpal.models.Appuntamento;
import com.tidal.pawpal.services.abstractions.CreateService;
import com.tidal.pawpal.services.abstractions.DeleteService;
import com.tidal.pawpal.services.abstractions.GenericService;
import com.tidal.pawpal.services.abstractions.ReadService;
import com.tidal.pawpal.services.abstractions.UpdateService;

public abstract class AppuntamentoServiceContract extends GenericService<Appuntamento, Long> implements
    CreateService<Appuntamento, Long>, 
    ReadService<Appuntamento, Long>,
    UpdateService<Appuntamento, Long>,
    DeleteService<Appuntamento, Long>  {

    public AppuntamentoServiceContract() {
        super(Appuntamento.class, Long.class);
    }

    @Override
    public Appuntamento registra(Map<String, String> data) {
        return CreateService.super.registra(data);
    }

    @PreAuthorize("isAuthenticated()")
    public abstract List<Appuntamento> cercaPerVeterinario(Long idVeterinario);

    @PreAuthorize("isAuthenticated()")
    public abstract List<Appuntamento> cercaPerCliente(Long idCliente);

}
