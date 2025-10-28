package com.tidal.pawpal.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tidal.pawpal.models.Appuntamento;

@Repository
public interface AppuntamentoRepository extends JpaRepository<Appuntamento, Long> {

    @Query("SELECT a FROM Appuntamento a JOIN a.veterinari v WHERE v.id = :id_veterinario")
    List<Appuntamento> findByVeterinario(@Param("id_veterinario") Long veterinarioId);

    @Query("SELECT a FROM Appuntamento a JOIN a.clienti v WHERE v.id = :id_cliente")
    List<Appuntamento> findByCliente(@Param("id_cliente") Long clienteId);

}
