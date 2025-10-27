package com.tidal.pawpal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tidal.pawpal.models.Appuntamento;
import org.springframework.stereotype.Repository;

@Repository
public interface AppuntamentoRepository extends JpaRepository<Appuntamento, Long> {

    
    @Query("SELECT a FROM Appuntamento a WHERE a.veterinario.id = ?1")
    Appuntamento findByVeterinarioId(Long veterinarioId);

    @Query("SELECT a FROM Appuntamento a WHERE a.cliente.id = ?1")
    Appuntamento findByClienteId(Long clienteId);



}
