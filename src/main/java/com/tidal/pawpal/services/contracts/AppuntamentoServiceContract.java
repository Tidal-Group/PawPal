package com.tidal.pawpal.services.contracts;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.context.event.EventListener;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tidal.pawpal.dto.AppuntamentoDto;
import com.tidal.pawpal.events.UserDeletionEvent;
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
    @PreAuthorize("hasRole('CLIENTE')")
    // DEBUG: SICUREZZA! Si possono modificare gli appuntamenti di tutti!
    public Appuntamento modifica(Long id, Map<String, String> data, Consumer<Appuntamento> consumer) {
        return UpdateService.super.modifica(id, data, consumer);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('CLIENTE')")
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

    @Transactional
    @EventListener
    @PreAuthorize("isAuthenticated()")
    public abstract Integer gestisciEliminazioneCliente(UserDeletionEvent event);

    @Transactional
    @EventListener
    @PreAuthorize("isAuthenticated()")
    public abstract Integer gestisciEliminazioneVeterinario(UserDeletionEvent event);

    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    public abstract List<AppuntamentoDto> cercaPerVeterinario(Long idVeterinario);

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    public abstract List<AppuntamentoDto> cercaPerCliente(Long idCliente);

    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    public abstract List<AppuntamentoDto> cercaPerVeterinarioConFiltri(Long idVeterinario, Map<String, Object> filtri);

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    public abstract List<AppuntamentoDto> cercaPerClienteConFiltri(Long idCliente, Map<String, Object> filtri);
    
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    public abstract Long contaAppuntamentiPerCliente(Long idCliente);

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    public abstract Optional<Appuntamento> cercaProssimoAppuntamento(Long idCliente);

    // @PreAuthorize("hasRole('ADMIN') || #idVeterinario == authentication.principal.id")
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    public abstract List<AppuntamentoDto> cercaAppuntamentiDiOggi(Long idVeterinario);

}
