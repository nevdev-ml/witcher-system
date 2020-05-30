package com.nevdev.witcher.contracts;

public interface IService<T> {
    T create(T model);

    void delete(T model);

    T edit(T model);

    T get(Long id);

    Iterable<T> getAll();
}
