package com.tidal.pawpal.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tidal.pawpal.models.Recensione;

@Repository
public interface RecensioneRepository extends JpaRepository<Recensione, Long> {
    
    @Query("SELECT r FROM Recensione r JOIN r.veterinari v WHERE v.id = :id_veterinario")
    List<Recensione> findByVeterinario(@Param("id_veterinario") Long idVeterinario);

    @Query("SELECT r FROM Recensione r JOIN r.clienti c WHERE c.id = :id_cliente")
    List<Recensione> findByCliente(@Param("id_cliente") Long idCliente);

    Recensione findByVoto(Integer voto);

}
