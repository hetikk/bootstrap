package com.github.hetikk.bootstrap.repository;

import com.github.hetikk.bootstrap.common.model.Dto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Objects.requireNonNull;

public abstract class AbstractMapper<D extends Dto, E extends BaseEntity> {

    @Autowired
    protected ModelMapper mapper;

    private final Class<D> schemaClass;
    private final Class<E> entityClass;

    public AbstractMapper(Class<D> schemaClass, Class<E> entityClass) {
        this.schemaClass = schemaClass;
        this.entityClass = entityClass;
    }

    public D toSchema(E entity) {
        requireNonNull(entity);
        return mapper.map(entity, schemaClass);
    }

    public void enrichFromSchema(D schema, E entity) {
        requireNonNull(schema);
        requireNonNull(entity);
        mapper.map(schema, entity);
    }

    protected Converter<E, D> toDtoConverter() {
        return context -> {
            E source = context.getSource();
            D destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    protected Converter<D, E> toEntityConverter() {
        return context -> {
            D source = context.getSource();
            E destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    protected void mapSpecificFields(E source, D destination) {
    }

    protected void mapSpecificFields(D source, E destination) {
    }

}
