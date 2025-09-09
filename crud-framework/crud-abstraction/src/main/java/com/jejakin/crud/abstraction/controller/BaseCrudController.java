package com.jejakin.crud.abstraction.controller;

import com.jejakin.crud.abstraction.model.BaseEntity;
import com.jejakin.crud.abstraction.service.CrudService;
import com.jejakin.crud.rest.response.BaseResponse;
import com.jejakin.crud.rest.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseCrudController<T extends BaseEntity, ID extends Serializable> implements CrudController<T, ID> {

    protected final CrudService<T, ID> service;

    protected BaseCrudController(CrudService<T, ID> service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<BaseResponse<T>> create(@RequestBody T entity) {
        T savedEntity = service.save(entity);
        return ResponseUtil.buildSuccessResponse(savedEntity);
    }

    @Override
    public ResponseEntity<BaseResponse<T>> getById(@PathVariable ID id) {
        Optional<T> entity = service.findById(id);
        return entity.map(ResponseUtil::buildSuccessResponse)
                .orElseGet(() -> ResponseUtil.buildErrorResponse(HttpStatus.NOT_FOUND, "Entity not found with id: " + id));
    }

    @Override
    public ResponseEntity<BaseResponse<List<T>>> getAll() {
        List<T> entities = service.findAll();
        return ResponseUtil.buildSuccessResponse(entities);
    }

    @Override
    public ResponseEntity<BaseResponse<T>> update(@PathVariable ID id, @RequestBody T entity) {
        try {
            T updatedEntity = service.update(id, entity);
            return ResponseUtil.buildSuccessResponse(updatedEntity);
        } catch (RuntimeException e) {
            return ResponseUtil.buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable ID id) {
        try {
            service.deleteById(id);
            return ResponseUtil.buildSuccessResponse("Entity with id " + id + " deleted successfully.", null);
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete entity with id " + id + ": " + e.getMessage());
        }
    }
}