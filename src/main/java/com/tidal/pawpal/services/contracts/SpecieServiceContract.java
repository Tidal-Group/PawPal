package com.tidal.pawpal.services.contracts;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.security.access.prepost.PreAuthorize;

import com.tidal.pawpal.models.Specie;
import com.tidal.pawpal.services.abstractions.CreateService;
import com.tidal.pawpal.services.abstractions.DeleteService;
import com.tidal.pawpal.services.abstractions.GenericService;
import com.tidal.pawpal.services.abstractions.ReadService;
import com.tidal.pawpal.services.abstractions.UpdateService;

import jakarta.transaction.Transactional;

public abstract class SpecieServiceContract extends GenericService<Specie, Long> implements
    CreateService<Specie, Long>, 
    ReadService<Specie, Long>,
    UpdateService<Specie, Long>,
    DeleteService<Specie, Long> {

    public SpecieServiceContract() {
        super(Specie.class, Long.class);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Specie registra(Map<String, String> data, Consumer<Specie> consumer) {
        return CreateService.super.registra(data, consumer);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Specie registra(Map<String, String> data) {
        return CreateService.super.registra(data);
    }

    @Override
    @PreAuthorize("permitAll")
    public List<Specie> elencaTutti() {
        return ReadService.super.elencaTutti();
    }

    @Override
    @PreAuthorize("permitAll")
    public Specie cercaPerId(Long id) {
        return ReadService.super.cercaPerId(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Specie modifica(Long id, Map<String, String> data, Consumer<Specie> consumer) {
        return UpdateService.super.modifica(id, data, consumer);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Specie modifica(Long id, Map<String, String> data) {
        return UpdateService.super.modifica(id, data);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void elimina(Long id, Consumer<Specie> consumer) {
        DeleteService.super.elimina(id, consumer);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void elimina(Long id) {
        DeleteService.super.elimina(id);
    }

    @PreAuthorize("permitAll")
    public abstract Specie cercaPerNomeSpecie(String nomeSpecie);

}
