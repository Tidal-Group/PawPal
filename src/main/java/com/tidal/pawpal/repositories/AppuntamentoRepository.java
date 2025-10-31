package com.tidal.pawpal.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tidal.pawpal.dto.AppuntamentoDto;
import com.tidal.pawpal.models.Appuntamento;

@Repository
public interface AppuntamentoRepository extends JpaRepository<Appuntamento, Long> {

    @Query(
        "SELECT new com.tidal.pawpal.dto.AppuntamentoDto(a.id, c.id, a.dataOra, a.note, c.nome, c.cognome, c.telefono, NULL) " + 
        "FROM Appuntamento a JOIN a.veterinario v JOIN a.cliente c WHERE v.id = :id_veterinario"
    )
    List<AppuntamentoDto> findByVeterinario(@Param("id_veterinario") Long veterinarioId);

    @Query(
        "SELECT new com.tidal.pawpal.dto.AppuntamentoDto(a.id, v.id, a.dataOra, a.note, v.nome, v.cognome, v.telefono, v.indirizzoStudio) " + 
        "FROM Appuntamento a JOIN a.cliente c JOIN a.veterinario v WHERE c.id = :id_cliente"
    )
    List<AppuntamentoDto> findByCliente(@Param("id_cliente") Long clienteId);

}
