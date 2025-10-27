package com.tidal.pawpal.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.stereotype.Repository;

import com.tidal.pawpal.models.Veterinario;
@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario, Long>, JpaSpecificationExecutor<Veterinario> {
 
    Veterinario findBySpecie(String specie);
    
    Veterinario findByPrestazioneVeterinario(String prestazione);

    
    Veterinario findByRecensioniRating(Double rating);

    
    Veterinario findByIndirizzo(String indirizzo);

   
    Veterinario findByEmail(String email);  

    
    Veterinario findByUserId(Long userId);
}
