package com.tidal.pawpal.services.abstractions;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tidal.pawpal.models.Entity;

public interface RepositoryAccess<E extends Entity, ID> {

    JpaRepository<E, ID> getRepository();

}
