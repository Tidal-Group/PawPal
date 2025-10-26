package com.tidal.pawpal.services.abstractions;

public interface DeleteService<E extends Entity, ID> extends RepositoryAccess<E, ID> {

    default void elimina(ID id) {

        // DEBUG: error handling
        getRepository().deleteById(id);

    }

}
