package com.tidal.pawpal.services.contracts;

import java.util.List;

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

    public abstract List<Appuntamento> cercaPerVeterinario(Long idVeterinario);
    public abstract List<Appuntamento> cercaPerCliente(Long idCliente);

}
