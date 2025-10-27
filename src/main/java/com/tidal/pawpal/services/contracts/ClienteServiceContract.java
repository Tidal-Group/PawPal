package com.tidal.pawpal.services.contracts;

import com.tidal.pawpal.models.Cliente;
import com.tidal.pawpal.services.abstractions.CreateService;
import com.tidal.pawpal.services.abstractions.DeleteService;
import com.tidal.pawpal.services.abstractions.GenericService;
import com.tidal.pawpal.services.abstractions.ReadService;
import com.tidal.pawpal.services.abstractions.UpdateService;

public abstract class ClienteServiceContract extends GenericService<Cliente, Long> implements
    CreateService<Cliente, Long>, 
    ReadService<Cliente, Long>,
    UpdateService<Cliente, Long>,
    DeleteService<Cliente, Long> {

    public ClienteServiceContract() {
        super(Cliente.class, Long.class);
    }

}
