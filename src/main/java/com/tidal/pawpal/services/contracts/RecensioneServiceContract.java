package com.tidal.pawpal.services.contracts;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.security.access.prepost.PreAuthorize;

import com.tidal.pawpal.models.Recensione;

import com.tidal.pawpal.services.abstractions.GenericService;

import com.tidal.pawpal.services.abstractions.CreateService;
import com.tidal.pawpal.services.abstractions.ReadService;
import com.tidal.pawpal.services.abstractions.DeleteService;

public abstract class RecensioneServiceContract extends GenericService<Recensione, Long> implements
    CreateService<Recensione, Long>, 
    ReadService<Recensione, Long>,
    DeleteService<Recensione, Long> {

    public RecensioneServiceContract() {
        super(Recensione.class, Long.class);
    }

    @Override
    @PreAuthorize("hasRole('CLIENTE')")
    public Recensione registra(Map<String, String> data, Consumer<Recensione> consumer) {
        return CreateService.super.registra(data, consumer);
    }

    @Override
    @PreAuthorize("hasRole('CLIENTE')")
    public Recensione registra(Map<String, String> data) {
        return CreateService.super.registra(data);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<Recensione> elencaTutti() {
        return ReadService.super.elencaTutti();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Recensione cercaPerId(Long id) {
        return ReadService.super.cercaPerId(id);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    // DEBUG: SICUREZZA! Si possono eliminare le recensioni di tutti!
    public void elimina(Long id) {
        DeleteService.super.elimina(id);
    }

    @PreAuthorize("permitAll")
    public abstract List<Recensione> cercaPerVeterinario(Long idVeterinario);

    @PreAuthorize("hasRole('ADMIN') || #idCliente == authentication.principal.id")
    public abstract List<Recensione> cercaPerCliente(Long idCliente);

    @PreAuthorize("permitAll")
    public abstract Double calcolaVotoMedioVeterinario(Long idVeterinario);

}
