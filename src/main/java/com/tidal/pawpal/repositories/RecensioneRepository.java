package com.tidal.pawpal.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tidal.pawpal.dto.RecensioneDto;
import com.tidal.pawpal.models.Recensione;

import jakarta.transaction.Transactional;

@Repository
public interface RecensioneRepository extends JpaRepository<Recensione, Long> {
    
    @Query(
        "SELECT new com.tidal.pawpal.dto.RecensioneDto(r.id, c.id, r.voto, r.commento, r.dataRecensione, c.username, NULL, NULL) " + 
        "FROM Recensione r LEFT JOIN r.veterinario v LEFT JOIN r.cliente c WHERE v.id = :id_veterinario"
    )
    List<RecensioneDto> findByVeterinario(@Param("id_veterinario") Long idVeterinario);

    @Query(
        "SELECT new com.tidal.pawpal.dto.RecensioneDto(r.id, c.id, r.voto, r.commento, r.dataRecensione, NULL, v.nome, v.cognome) " + 
        "FROM Recensione r LEFT JOIN r.veterinario v LEFT JOIN r.cliente c WHERE c.id = :id_cliente"
    )
    List<RecensioneDto> findByCliente(@Param("id_cliente") Long idCliente);

    @Query("SELECT avg(r.voto) FROM Recensione r JOIN r.veterinario v WHERE v.id = :id_veterinario")
    Double calculateAverageRatingVeterinario(@Param("id_veterinario") Long idVeterinario);

    @Modifying
    @Transactional
    @Query("UPDATE Recensione r SET r.cliente = NULL WHERE r.cliente.id = :id_cliente")
    Integer clearClienteForeignKey(@Param("id_cliente") Long clienteId);

    @Modifying
    @Transactional
    @Query("UPDATE Recensione r SET r.veterinario = NULL WHERE r.veterinario.id = :id_veterinario")
    Integer clearVeterinarioForeignKey(@Param("id_veterinario") Long veterinarioId);

    Long countByClienteId(Long clienteId);

    Long countByVeterinarioId(Long veterinarioId);

}
