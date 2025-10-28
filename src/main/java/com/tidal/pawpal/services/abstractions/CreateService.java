package com.tidal.pawpal.services.abstractions;

import java.util.Map;

import com.tidal.pawpal.models.GenericEntity;

public interface CreateService<E extends GenericEntity, ID> extends RepositoryAccess<E, ID>, ContextAccess, ClassAccess<E, ID> {

    default E registra(Map<String, String> data) {

        // DEBUG: error handling
        E entity = getContext().getBean(getEntityType(), data);

        // makes sure the entity is created and not updated by mistake
        // useful in case a service allows object creation, but not updating
        entity.setId(null);

        return getRepository().save(entity);

    }

    default E registra(Map<String, String> data, boolean flag) {

        // DEBUG: error handling
        E entity = getContext().getBean(getEntityType(), data);

        // makes sure the entity is created and not updated by mistake
        // useful in case a service allows object creation, but not updating
        entity.setId(null);

        return getRepository().save(entity);

    }

}
