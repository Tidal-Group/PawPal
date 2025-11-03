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
    
    @Query("SELECT v FROM Veterinario v JOIN v.specieTrattate s WHERE s.nomeSpecie = :specie")
    List<Veterinario> findByNomeSpecie(@Param("specie") String specie);

    @Query("SELECT v FROM Veterinario v JOIN v.prestazioniOfferte p WHERE p.id = :id_prestazione")
    List<Veterinario> findByIdPrestazione(@Param("id_prestazione") Long idPrestazione);

    @Query("SELECT v FROM Veterinario v WHERE v.indirizzoStudio = :indirizzo")
    List<Veterinario> findByIndirizzoStudio(@Param("indirizzo") String indirizzo);

    @Query("SELECT v FROM Veterinario v WHERE LOWER(v.indirizzoStudio) LIKE CONCAT('%', LOWER(:citta), '%')")
    List<Veterinario> findByCitta(String citta);
    
  @Query(value = 
        "WITH SpecializationCounts AS (" +
        "    SELECT v.specializzazione, COUNT(v.veterinario_id) AS vet_count " +
        "    FROM veterinari v " + // <-- CORRETTO (nome tabella plurale)
        "    WHERE v.specializzazione IS NOT NULL " +
        "    GROUP BY v.specializzazione" +
        ") " +
        "SELECT sc.specializzazione " +
        "FROM SpecializationCounts sc " +
        "ORDER BY sc.vet_count DESC",
        nativeQuery = true)
List<String> findPopularSpecializationNamesNative();

    @Query("SELECT v " +
           "FROM Veterinario v JOIN v.recensioni r " +
           "GROUP BY v " +
           "ORDER BY AVG(r.voto) DESC, COUNT(r) DESC")
    List<Veterinario> findTopVeterinariansByAverageRating();

    Veterinario findByEmail(String email);
    Veterinario findByTelefono(String telefono);

}
