package com.tidal.pawpal.services.abstractions;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryAccess<E extends Entity, ID> {

    JpaRepository<E, ID> getRepository();

}
