package com.tidal.pawpal.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tidal.pawpal.models.Veterinario;
@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario, Long>, JpaSpecificationExecutor<Veterinario> {
    
    @Query("SELECT v FROM Veterinario v JOIN v.specie s WHERE s.nome = :specie")
    List<Veterinario> findBySpecie(@Param("specie") String specie);

    @Query("SELECT v FROM Veterinario v JOIN v.prestazione p WHERE p.nome = :prestazione")
    List<Veterinario> findByPrestazione(@Param("prestazione") String prestazione);

    @Query("SELECT v FROM Veterinario v WHERE v.indirizzo = :indirizzo")
    List<Veterinario> findByIndirizzo(@Param("indirizzo") String indirizzo);

    @Query("SELECT v FROM Veterinario v WHERE LOWER(v.indirizzo) LIKE CONCAT('%', LOWER(:citta), '%')")
    List<Veterinario> findByCitta(String citta);

    Veterinario findByEmail(String email);

}
