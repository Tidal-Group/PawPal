package com.tidal.pawpal.services.abstractions;

import java.util.Map;

import com.tidal.pawpal.exceptions.NotFoundException;
import com.tidal.pawpal.models.GenericEntity;

public interface UpdateService<E extends GenericEntity, ID> extends RepositoryAccess<E, ID>, ContextAccess, ClassAccess<E, ID>{

    // IMPLEMENT: should return ID or E?
    default E modifica(ID id, Map<String, String> data) {

        // DEBUG: error handling
        if(id == null) throw new IllegalArgumentException("cannot update where id is null");

        // DEBUG: if object isn't found, will throw a NoSuchElementException
        E existingEntity = getRepository().findById(id).orElseThrow(() -> new NotFoundException());

        // naming convention: all beans must have a "merger method"
        // String beanName = getEntityType().getSimpleName().toLowerCase() + "Merger";
        // E mergedEntity = getEntityType().cast(getContext().getBean(beanName, getEntityType(), existingEntity, data));

        existingEntity.fromMap(data, false);

        return getRepository().save(existingEntity);
        
    }

}
