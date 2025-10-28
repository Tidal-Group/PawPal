package com.tidal.pawpal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tidal.pawpal.models.Prestazione;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
@Repository
public interface PrestazioneRepository extends JpaRepository<Prestazione, Long> {

    @Query("SELECT p FROM Prestazione p WHERE p.prezzo BETWEEN ?1 AND ?2")
    List<Prestazione> findByRangePrezzo(Double prezzoMin, Double prezzoMax);

}
