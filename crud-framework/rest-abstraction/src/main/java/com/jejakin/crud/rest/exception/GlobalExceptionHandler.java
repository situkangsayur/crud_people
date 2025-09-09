package com.jejakin.crud.rest.exception;

import com.jejakin.crud.rest.response.ErrorResponse;
import com.jejakin.crud.rest.response.BaseResponse;
import com.jejakin.crud.rest.response.ResponseList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final ResponseList responseList = new ResponseList();

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<BaseResponse<String>> handleResponseStatusException(ResponseStatusException ex) {
        HttpStatus status = (HttpStatus) ex.getStatusCode();
        BaseResponse<String> response = responseList.getResponse(status.value());
        if (response == null) {
            response = new BaseResponse<>(false, ex.getReason(), null);
        } else {
            response.setMessage(ex.getReason());
        }
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<String>> handleAllUncaughtException(Exception ex) {
        BaseResponse<String> response = responseList.getResponse(HttpStatus.INTERNAL_SERVER_ERROR.value());
        if (response == null) {
            response = new BaseResponse<>(false, "An unexpected error occurred: " + ex.getMessage(), null);
        } else {
            response.setMessage("An unexpected error occurred: " + ex.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // You can add more specific exception handlers here
    // For example, for validation errors, not found errors, etc.
}