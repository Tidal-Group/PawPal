package com.tidal.pawpal.services.abstractions;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadService<E extends Entity, ID> extends RepositoryAccess<E, ID> {
    
    default List<E> elencaTutti() {
        return getRepository().findAll();
    }

    default E cercaPerId(ID id) {
        // DEBUG: if object isn't found, will throw a NoSuchElementException
        return getRepository().findById(id).get();
    }

}
