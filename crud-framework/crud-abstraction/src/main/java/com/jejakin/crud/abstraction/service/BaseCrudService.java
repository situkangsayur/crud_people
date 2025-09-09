package com.jejakin.crud.abstraction.service;

import com.jejakin.crud.abstraction.model.BaseEntity;
import com.jejakin.crud.abstraction.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseCrudService<T extends BaseEntity, ID extends Serializable> implements CrudService<T, ID> {

    protected final CrudRepository<T, ID> repository;

    protected BaseCrudService(CrudRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public T update(ID id, T entity) {
        if (repository.existsById(id)) {
            entity.setId((Long) id); // Ensure the ID is set for update
            return repository.save(entity);
        }
        // Handle not found case, e.g., throw an exception
        throw new RuntimeException("Entity with id " + id + " not found for update.");
    }
}