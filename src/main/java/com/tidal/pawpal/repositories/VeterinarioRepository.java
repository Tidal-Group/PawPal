package com.tidal.pawpal.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tidal.pawpal.models.Veterinario;
@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario, Long>, JpaSpecificationExecutor<Veterinario> {
 
    @Query("SELECT v FROM Veterinario v WHERE v.user.id = ?1")
    Veterinario findBySpecie(String specie);
    
    @Query("SELECT v FROM Veterinario v JOIN v.prestazioni p WHERE p.nome = ?1")
    Veterinario findByPrestazioneVeterinario(String prestazione);

    @Query("SELECT v FROM Veterinario v JOIN v.recensioni r WHERE r.rating >= ?1")
    Veterinario findByRecensioniRating(Double rating);

    @Query("SELECT v FROM Veterinario v WHERE v.indirizzo = ?1")
    Veterinario findByIndirizzo(String indirizzo);

    @Query("SELECT v FROM Veterinario v WHERE v.email = ?1")
    Veterinario findByEmail(String email);  

    @Query("SELECT v FROM Veterinario v WHERE v.user.id = ?1")
    Veterinario findByUserId(Long userId);
}
