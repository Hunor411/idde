package edu.bbte.idde.dhim2228.service;

import edu.bbte.idde.dhim2228.model.BaseEntity;

import java.util.Collection;

public interface BaseService<T extends BaseEntity> {
    void create(T entity);

    void update(Long id, T entity);

    Collection<T> findAll();

    T findById(Long id);

    void deleteById(Long id);
}
