package com.tidal.pawpal.services.contracts;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.security.access.prepost.PreAuthorize;

import com.tidal.pawpal.models.Veterinario;
import com.tidal.pawpal.services.abstractions.CreateService;
import com.tidal.pawpal.services.abstractions.DeleteService;
import com.tidal.pawpal.services.abstractions.GenericService;
import com.tidal.pawpal.services.abstractions.ReadService;
import com.tidal.pawpal.services.abstractions.UpdateService;

import jakarta.transaction.Transactional;

public abstract class VeterinarioServiceContract extends GenericService<Veterinario, Long> implements
    CreateService<Veterinario, Long>, 
    ReadService<Veterinario, Long>,
    UpdateService<Veterinario, Long>,
    DeleteService<Veterinario, Long> {

    public VeterinarioServiceContract() {
        super(Veterinario.class, Long.class);
    }

    @Override
    @Transactional
    @PreAuthorize("permitAll")
    public Veterinario registra(Map<String, String> data, Consumer<Veterinario> consumer) {
        return CreateService.super.registra(data, consumer);
    }

    @Override
    @Transactional
    @PreAuthorize("permitAll")
    public Veterinario registra(Map<String, String> data) {
        return CreateService.super.registra(data);
    }

    @Override
    @PreAuthorize("permitAll")
    public List<Veterinario> elencaTutti() {
        return ReadService.super.elencaTutti();
    }

    @Override
    @PreAuthorize("permitAll")
    public Veterinario cercaPerId(Long id) {
        return ReadService.super.cercaPerId(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') || #id == authentication.principal.id")
    public Veterinario modifica(Long id, Map<String, String> data) {
        return UpdateService.super.modifica(id, data);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') || #id == authentication.principal.id")
    public void elimina(Long id, Consumer<Veterinario> consumer) {
        DeleteService.super.elimina(id, consumer);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') || #id == authentication.principal.id")
    public void elimina(Long id) {
        DeleteService.super.elimina(id);
    }

    @PreAuthorize("permitAll")
    public abstract List<Veterinario> cercaConFiltri(Map<String, String> filtri);

    @PreAuthorize("permitAll")
    public abstract List<Veterinario> cercaPerSpecie(String nomeSpecie);

    @PreAuthorize("permitAll")
    public abstract List<Veterinario> cercaPerPrestazione(Long idPrestazione);

    @PreAuthorize("permitAll")
    public abstract List<Veterinario> cercaPerIndirizzoStudio(String indirizzoStudio);

    @PreAuthorize("permitAll")
    public abstract List<Veterinario> cercaPerCitta(String citta);

    @PreAuthorize("hasRole('ADMIN')")
    public abstract Veterinario cercaPerEmail(String email);

    @PreAuthorize("hasRole('ADMIN')")
    public abstract Veterinario cercaPerTelefono(String telefono);

}
