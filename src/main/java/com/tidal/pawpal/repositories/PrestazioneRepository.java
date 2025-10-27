package com.tidal.pawpal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tidal.pawpal.models.Prestazione;
@Repository
public interface PrestazioneRepository extends JpaRepository<Prestazione, Long> {

    
    Prestazione findByRangePrezzo(String rangePrezzo);

}
