package com.jejakin.crud.abstraction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface CrudRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
    // Custom CRUD operations can be defined here if needed,
    // but JpaRepository already provides basic CRUD.
}