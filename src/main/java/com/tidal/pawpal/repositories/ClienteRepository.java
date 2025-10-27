package com.tidal.pawpal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tidal.pawpal.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
