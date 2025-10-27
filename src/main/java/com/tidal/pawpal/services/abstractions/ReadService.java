package com.tidal.pawpal.services.abstractions;

import java.util.List;

import com.tidal.pawpal.models.GenericEntity;

public interface ReadService<E extends GenericEntity, ID> extends RepositoryAccess<E, ID> {
    
    default List<E> elencaTutti() {
        return getRepository().findAll();
    }

    default E cercaPerId(ID id) {
        // DEBUG: if object isn't found, will throw a NoSuchElementException
        return getRepository().findById(id).get();
    }

}
