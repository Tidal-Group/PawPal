package com.tidal.pawpal.services.abstractions;

import com.tidal.pawpal.models.Entity;

public interface ClassAccess<E extends Entity, ID> {

    Class<E> getEntityType();
    Class<ID> getIdType();

}
