package com.tidal.pawpal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tidal.pawpal.models.Specie;

public interface SpecieRepository extends JpaRepository<Specie, Long> {

}
