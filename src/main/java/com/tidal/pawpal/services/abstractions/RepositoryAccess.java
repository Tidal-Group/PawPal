package com.tidal.pawpal.services.abstractions;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tidal.pawpal.models.GenericEntity;

public interface RepositoryAccess<E extends GenericEntity, ID> {

    JpaRepository<E, ID> getRepository();

}
