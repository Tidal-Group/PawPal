package com.tidal.pawpal.services.abstractions;

public interface ClassAccess<E extends Entity, ID> {

    Class<E> getEntityType();
    Class<ID> getIdType();

}
