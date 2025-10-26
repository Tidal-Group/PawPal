package com.tidal.pawpal.services.abstractions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tidal.pawpal.models.Entity;

import lombok.Getter;

@Getter
public abstract class GenericService<E extends Entity, ID> implements RepositoryAccess<E, ID>, ContextAccess, ClassAccess<E, ID> {

    @Autowired
    private JpaRepository<E, ID> repository;

    @Autowired
    private ApplicationContext context;

    private final Class<E> entityType;

    private final Class<ID> idType;

    public GenericService(Class<E> entityType, Class<ID> idType) {
        this.entityType = entityType;
        this.idType = idType;
    }

}
