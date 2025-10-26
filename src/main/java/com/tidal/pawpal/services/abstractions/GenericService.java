package com.tidal.pawpal.services.abstractions;

import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tidal.pawpal.models.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class GenericService<E extends Entity, ID> implements RepositoryAccess<E, ID>, ContextAccess, ClassAccess<E, ID> {

    private final JpaRepository<E, ID> repository;

    private final ApplicationContext context;

    private final Class<E> entityType;

    private final Class<ID> idType;

}
