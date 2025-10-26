package com.tidal.pawpal.services.abstractions;

import java.util.Map;

import com.tidal.pawpal.models.Entity;

public interface CreateService<E extends Entity, ID> extends RepositoryAccess<E, ID>, ContextAccess, ClassAccess<E, ID> {

    // IMPLEMENT: should return ID or E?
    default E registra(Map<String, String> data) {

        // DEBUG: error handling
        E entity = getContext().getBean(getEntityType(), data);

        // makes sure the entity is created and not updated by mistake
        // useful in case a service allows object creation, but not updating
        entity.setId(null);

        return getRepository().save(entity);

    }

}
