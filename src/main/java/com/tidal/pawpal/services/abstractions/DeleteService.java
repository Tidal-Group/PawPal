package com.tidal.pawpal.services.abstractions;

import java.util.function.Consumer;

import com.tidal.pawpal.exceptions.NotFoundException;
import com.tidal.pawpal.models.GenericEntity;

public interface DeleteService<E extends GenericEntity, ID> extends RepositoryAccess<E, ID> {

    default void elimina(ID id, Consumer<E> consumer) {

        E entity = getRepository().findById(id).orElseThrow(() -> new NotFoundException());

        consumer.accept(entity);
        
        // DEBUG: error handling
        getRepository().deleteById(id);

    }

    default void elimina(ID id) {
        elimina(id, (entity) -> {});
    }

}
