package com.tidal.pawpal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tidal.pawpal.models.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByUserId(Long userId);


   
    Cliente findByEmail(String email);

    
    Cliente findByTelefono(String telefono);




}
