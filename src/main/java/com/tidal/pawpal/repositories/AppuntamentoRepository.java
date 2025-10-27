package com.tidal.pawpal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tidal.pawpal.models.Appuntamento;
import org.springframework.stereotype.Repository;

@Repository
public interface AppuntamentoRepository extends JpaRepository<Appuntamento, Long> {

    
    Appuntamento findByVeterinarioId(Long veterinarioId);

    Appuntamento findByClienteId(Long clienteId);



}
