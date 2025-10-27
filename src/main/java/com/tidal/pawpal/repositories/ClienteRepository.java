package com.tidal.pawpal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.tidal.pawpal.models.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c WHERE c.user.id = ?1")
    Cliente findByUserId(Long userId);


    @Query("SELECT c FROM Cliente c WHERE c.email = ?1")
    Cliente findByEmail(String email);

    @Query("SELECT c FROM Cliente c WHERE c.telefono = ?1")
    Cliente findByTelefono(String telefono);




}
