package com.tidal.pawpal.services.contracts;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.springframework.security.access.prepost.PreAuthorize;

import com.tidal.pawpal.models.Prestazione;
import com.tidal.pawpal.services.abstractions.CreateService;
import com.tidal.pawpal.services.abstractions.DeleteService;
import com.tidal.pawpal.services.abstractions.GenericService;
import com.tidal.pawpal.services.abstractions.ReadService;
import com.tidal.pawpal.services.abstractions.UpdateService;

public abstract class PrestazioneServiceContract extends GenericService<Prestazione, Long> implements
    CreateService<Prestazione, Long>, 
    ReadService<Prestazione, Long>,
    UpdateService<Prestazione, Long>,
    DeleteService<Prestazione, Long> {

    public PrestazioneServiceContract() {
        super(Prestazione.class, Long.class);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Prestazione registra(Map<String, String> data, Consumer<Prestazione> consumer) {
        return CreateService.super.registra(data, consumer);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Prestazione registra(Map<String, String> data) {
        return CreateService.super.registra(data);
    }

    @Override
    @PreAuthorize("permitAll")
    public List<Prestazione> elencaTutti() {
        return ReadService.super.elencaTutti();
    }

    @Override
    @PreAuthorize("permitAll")
    public Prestazione cercaPerId(Long id) {
        return ReadService.super.cercaPerId(id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Prestazione modifica(Long id, Map<String, String> data) {
        return UpdateService.super.modifica(id, data);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void elimina(Long id) {
        DeleteService.super.elimina(id);
    }

    @PreAuthorize("permitAll")
    public abstract Set<Prestazione> cercaPerRangePrezzo(Double prezzoMin, Double prezzoMax);

}
