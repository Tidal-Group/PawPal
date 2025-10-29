package com.tidal.pawpal.services.contracts;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.security.access.prepost.PreAuthorize;

import com.tidal.pawpal.models.Cliente;
import com.tidal.pawpal.services.abstractions.CreateService;
import com.tidal.pawpal.services.abstractions.DeleteService;
import com.tidal.pawpal.services.abstractions.GenericService;
import com.tidal.pawpal.services.abstractions.ReadService;
import com.tidal.pawpal.services.abstractions.UpdateService;

import jakarta.transaction.Transactional;

public abstract class ClienteServiceContract extends GenericService<Cliente, Long> implements
    CreateService<Cliente, Long>, 
    ReadService<Cliente, Long>,
    UpdateService<Cliente, Long>,
    DeleteService<Cliente, Long> {

    public ClienteServiceContract() {
        super(Cliente.class, Long.class);
    }

    @Override
    @Transactional
    @PreAuthorize("permitAll")
    public Cliente registra(Map<String, String> data, Consumer<Cliente> consumer) {
        return CreateService.super.registra(data, consumer);
    }

    @Override
    @Transactional
    @PreAuthorize("permitAll")
    public Cliente registra(Map<String, String> data) {
        return CreateService.super.registra(data);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<Cliente> elencaTutti() {
        return ReadService.super.elencaTutti();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || #id == authentication.principal.id")
    public Cliente cercaPerId(Long id) {
        return ReadService.super.cercaPerId(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') || #id == authentication.principal.id")
    public Cliente modifica(Long id, Map<String, String> data) {
        return UpdateService.super.modifica(id, data);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') || #id == authentication.principal.id")
    public void elimina(Long id, Consumer<Cliente> consumer) {
        DeleteService.super.elimina(id, consumer);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') || #id == authentication.principal.id")
    public void elimina(Long id) {
        DeleteService.super.elimina(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public abstract Cliente cercaPerEmail(String email);

    @PreAuthorize("hasRole('ADMIN')")
    public abstract Cliente cercaPerTelefono(String telefono);

}
