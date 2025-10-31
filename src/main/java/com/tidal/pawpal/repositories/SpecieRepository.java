package com.tidal.pawpal.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tidal.pawpal.models.Specie;

@Repository
public interface SpecieRepository extends JpaRepository<Specie, Long> {

    Specie findByNomeSpecie(String nomeSpecie);

    @Query("SELECT s FROM Veterinario v JOIN v.specieTrattate s WHERE v.id = :id_veterinario")
    Set<Specie> findByIdVeterinario(@Param("id_veterinario") Long idVeterinario);

}
