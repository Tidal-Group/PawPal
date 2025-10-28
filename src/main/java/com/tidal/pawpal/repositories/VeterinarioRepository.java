package com.tidal.pawpal.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.stereotype.Repository;

import com.tidal.pawpal.models.Veterinario;
@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario, Long>, JpaSpecificationExecutor<Veterinario> {
    // @Query ("SELECT v FROM Veterinario v JOIN VeterinarioSpecie s on v.specie")
    List<Veterinario> findBySpecie(String specie);

    List<Veterinario> findByPrestazioneVeterinario(String prestazione);

    List<Veterinario> findByRecensioniRating(Double rating);

    List<Veterinario> findByIndirizzo(String indirizzo);

    Veterinario findByEmail(String email);

    Veterinario findById(Long userId);
}
