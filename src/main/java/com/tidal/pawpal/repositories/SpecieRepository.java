package com.tidal.pawpal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tidal.pawpal.models.Specie;

@Repository
public interface SpecieRepository extends JpaRepository<Specie, Long> {
    
    @Query("SELECT s FROM Specie s WHERE LOWER(s.nome) = LOWER(?1)")
    Specie findByNome(String nome);

}
