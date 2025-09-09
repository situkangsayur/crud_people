package com.jejakin.crud.abstraction.service;

import com.jejakin.crud.abstraction.model.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CrudService<T extends BaseEntity, ID extends Serializable> {
    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
    T update(ID id, T entity);
}