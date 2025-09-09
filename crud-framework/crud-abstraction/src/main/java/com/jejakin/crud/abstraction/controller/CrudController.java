package com.jejakin.crud.abstraction.controller;

import com.jejakin.crud.abstraction.model.BaseEntity;
import com.jejakin.crud.rest.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

public interface CrudController<T extends BaseEntity, ID extends Serializable> {

    @PostMapping
    ResponseEntity<BaseResponse<T>> create(@RequestBody T entity);

    @GetMapping("/{id}")
    ResponseEntity<BaseResponse<T>> getById(@PathVariable ID id);

    @GetMapping
    ResponseEntity<BaseResponse<List<T>>> getAll();

    @PutMapping("/{id}")
    ResponseEntity<BaseResponse<T>> update(@PathVariable ID id, @RequestBody T entity);

    @DeleteMapping("/{id}")
    ResponseEntity<BaseResponse<Void>> delete(@PathVariable ID id);
}