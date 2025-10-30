package com.tidal.pawpal.services.abstractions;

import java.util.Map;
import java.util.function.Consumer;

import com.tidal.pawpal.exceptions.NotFoundException;
import com.tidal.pawpal.models.GenericEntity;

public interface UpdateService<E extends GenericEntity, ID> extends RepositoryAccess<E, ID>, ContextAccess, ClassAccess<E, ID>{

    default E modifica(ID id, Map<String, String> data) {
        return modifica(id, data, (entity) -> {});
    }

    default E modifica(ID id, Map<String, String> data, Consumer<E> consumer) {

        // DEBUG: error handling
        if(id == null) throw new IllegalArgumentException("cannot update where id is null");

        E existingEntity = getRepository().findById(id).orElseThrow(() -> new NotFoundException());

        consumer.accept(existingEntity);

        existingEntity.fromMap(data, false);

        return getRepository().save(existingEntity);
        
    }

}
