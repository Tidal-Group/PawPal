package com.tidal.pawpal.services.abstractions;

import com.tidal.pawpal.models.GenericEntity;

public interface ClassAccess<E extends GenericEntity, ID> {

    Class<E> getEntityType();
    Class<ID> getIdType();

}
