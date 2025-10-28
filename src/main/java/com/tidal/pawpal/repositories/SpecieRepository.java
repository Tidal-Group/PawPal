package com.tidal.pawpal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import com.tidal.pawpal.models.Specie;

@Repository
public interface SpecieRepository extends JpaRepository<Specie, Long> {

    Specie findByNome(String nome);

}
