package com.tidal.pawpal.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.tidal.pawpal.models.Recensione;

@Repository
public interface RecensioneRepository extends JpaRepository<Recensione, Long> {
    @Query("SELECT r FROM Recensione r WHERE r.veterinario.id = ?1")
    Recensione findByVeterinario(Long veterinarioId);
     
    @Query("SELECT r FROM Recensione r WHERE r.cliente.id = ?1")
    Recensione findByCliente(Long clienteId);

    @Query("SELECT r FROM Recensione r WHERE r.votoMedio = ?1")
    Recensione findByVotoMedio(Double votoMedio);

    

}
