package com.jejakin.crud.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import com.jejakin.crud.rest.response.ErrorResponse;

@Data
@NoArgsConstructor
public class BaseResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public BaseResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<T>(true, "Success", data);
    }

    public static <T> BaseResponse<T> success(String message, T data) {
        return new BaseResponse<T>(true, message, data);
    }

    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<T>(false, message, null);
    }

    public static <T> BaseResponse<T> error(String message, T data) {
        return new BaseResponse<T>(false, message, data);
    }

    // Success responses
    public static <T> BaseResponse<T> created(T data) {
        return new BaseResponse<T>(true, "Created", data);
    }

    public static <T> BaseResponse<T> noContent() {
        return new BaseResponse<T>(true, "No Content", null);
    }

    // Error responses (returning ErrorResponse for more detail)
    public static <T> ErrorResponse badRequest(String message) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, message, String.valueOf(HttpStatus.BAD_REQUEST.value()));
    }

    public static <T> ErrorResponse unauthorized(String message) {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED, message, String.valueOf(HttpStatus.UNAUTHORIZED.value()));
    }

    public static <T> ErrorResponse forbidden(String message) {
        return new ErrorResponse(HttpStatus.FORBIDDEN, message, String.valueOf(HttpStatus.FORBIDDEN.value()));
    }

    public static <T> ErrorResponse notFound(String message) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, message, String.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    public static <T> ErrorResponse methodNotAllowed(String message) {
        return new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, message, String.valueOf(HttpStatus.METHOD_NOT_ALLOWED.value()));
    }

    public static <T> ErrorResponse conflict(String message) {
        return new ErrorResponse(HttpStatus.CONFLICT, message, String.valueOf(HttpStatus.CONFLICT.value()));
    }

    public static <T> ErrorResponse internalServerError(String message) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    public static <T> ErrorResponse serviceUnavailable(String message) {
        return new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, message, String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()));
    }
}