package ru.yandex.backend.school2.megamarket.mapper;

import java.util.List;

public interface EntityDtoMapper<E, D> {

    E toEntity(D source, Class<E> destinationType);

    List<E> toEntity(List<D> source, Class<E> destinationType);

    D toDto(E source, Class<D> destinationType);

    List<D> toDto(List<E> source, Class<D> destinationType);

}
