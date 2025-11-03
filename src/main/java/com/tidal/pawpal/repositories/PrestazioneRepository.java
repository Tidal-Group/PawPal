package com.tidal.pawpal.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tidal.pawpal.dto.PrestazioneDto;
import com.tidal.pawpal.models.Prestazione;

@Repository
public interface PrestazioneRepository extends JpaRepository<Prestazione, Long> {

    @Query("SELECT p FROM Prestazione p WHERE p.prezzo BETWEEN ?1 AND ?2")
    Set<Prestazione> findByRangePrezzo(Double prezzoMin, Double prezzoMax);


    @Query(
        "SELECT new com.tidal.pawpal.dto.PrestazioneDto(p.id, p.descrizione, p.prezzo, p.durataVisita) " + 
        "FROM Veterinario v JOIN v.prestazioniOfferte p WHERE v.id = :id_veterinario"
    )
    Set<PrestazioneDto> findByIdVeterinario(@Param("id_veterinario") Long idVeterinario);

}
