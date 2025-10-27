package com.tidal.pawpal.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tidal.pawpal.models.Recensione;

@Repository
public interface RecensioneRepository extends JpaRepository<Recensione, Long> {
    
    Recensione findByVeterinario(Long veterinarioId);
     
   
    Recensione findByCliente(Long clienteId);

    Recensione findByVotoMedio(Double votoMedio);

    

}
