package com.tidal.pawpal.services.contracts;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.security.access.prepost.PreAuthorize;

import com.tidal.pawpal.models.Appuntamento;
import com.tidal.pawpal.services.abstractions.CreateService;
import com.tidal.pawpal.services.abstractions.DeleteService;
import com.tidal.pawpal.services.abstractions.GenericService;
import com.tidal.pawpal.services.abstractions.ReadService;
import com.tidal.pawpal.services.abstractions.UpdateService;

import jakarta.transaction.Transactional;

public abstract class AppuntamentoServiceContract extends GenericService<Appuntamento, Long> implements
    CreateService<Appuntamento, Long>, 
    ReadService<Appuntamento, Long>,
    UpdateService<Appuntamento, Long>,
    DeleteService<Appuntamento, Long>  {

    public AppuntamentoServiceContract() {
        super(Appuntamento.class, Long.class);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('CLIENTE')")
    public Appuntamento registra(Map<String, String> data, Consumer<Appuntamento> consumer) {
        return CreateService.super.registra(data, consumer);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('CLIENTE')")
    public Appuntamento registra(Map<String, String> data) {
        return CreateService.super.registra(data);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<Appuntamento> elencaTutti() {
        return ReadService.super.elencaTutti();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Appuntamento cercaPerId(Long id) {
        return ReadService.super.cercaPerId(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('CLIENTE', 'VETERINARIO')")
    // DEBUG: SICUREZZA! Si possono modificare gli appuntamenti di tutti!
    public Appuntamento modifica(Long id, Map<String, String> data, Consumer<Appuntamento> consumer) {
        return UpdateService.super.modifica(id, data, consumer);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('CLIENTE', 'VETERINARIO')")
    // DEBUG: SICUREZZA! Si possono modificare gli appuntamenti di tutti!
    public Appuntamento modifica(Long id, Map<String, String> data) {
        return UpdateService.super.modifica(id, data);
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    // DEBUG: SICUREZZA! Si possono eliminare gli appuntamenti di tutti!
    public void elimina(Long id, Consumer<Appuntamento> consumer) {
        DeleteService.super.elimina(id, consumer);
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    // DEBUG: SICUREZZA! Si possono eliminare gli appuntamenti di tutti!
    public void elimina(Long id) {
        DeleteService.super.elimina(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    public abstract List<Appuntamento> cercaPerVeterinario(Long idVeterinario);

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    public abstract List<Appuntamento> cercaPerCliente(Long idCliente);

}
