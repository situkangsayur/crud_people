package com.jejakin.crud.rest.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ErrorResponse extends BaseResponse<Object> {
    private HttpStatus status;
    private String errorCode;
    private LocalDateTime timestamp;
    private List<String> errors;

    public ErrorResponse(HttpStatus status, String message, String errorCode, List<String> errors) {
        super(false, message, null);
        this.status = status;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }

    public ErrorResponse(HttpStatus status, String message, String errorCode) {
        this(status, message, errorCode, null);
    }
}