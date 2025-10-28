package com.tidal.pawpal.services.abstractions;

import com.tidal.pawpal.models.GenericEntity;

public interface DeleteService<E extends GenericEntity, ID> extends RepositoryAccess<E, ID> {

    default void elimina(ID id) {

        // DEBUG: error handling
        getRepository().deleteById(id);

    }

}
