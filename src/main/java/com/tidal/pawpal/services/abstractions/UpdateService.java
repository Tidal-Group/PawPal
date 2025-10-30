package com.tidal.pawpal.services.abstractions;

import java.util.Map;

import com.tidal.pawpal.exceptions.NotFoundException;
import com.tidal.pawpal.models.GenericEntity;

public interface UpdateService<E extends GenericEntity, ID> extends RepositoryAccess<E, ID>, ContextAccess, ClassAccess<E, ID>{

    default E modifica(ID id, Map<String, String> data) {

        // DEBUG: error handling
        if(id == null) throw new IllegalArgumentException("cannot update where id is null");

        E existingEntity = getRepository().findById(id).orElseThrow(() -> new NotFoundException());

        existingEntity.fromMap(data, false);

        return getRepository().save(existingEntity);
        
    }

}
