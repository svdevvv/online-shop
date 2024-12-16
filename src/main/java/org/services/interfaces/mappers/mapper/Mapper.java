package org.services.interfaces.mappers.mapper;

public interface Mapper<F,T> {
T mapFrom(F object);
}
