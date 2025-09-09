package com.jejakin.crud.rest.util;

import com.jejakin.crud.rest.response.BaseResponse;
import com.jejakin.crud.rest.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static <T> ResponseEntity<BaseResponse<T>> buildSuccessResponse(T data) {
        return new ResponseEntity<>(BaseResponse.success(data), HttpStatus.OK);
    }

    public static <T> ResponseEntity<BaseResponse<T>> buildSuccessResponse(String message, T data) {
        return new ResponseEntity<>(BaseResponse.success(message, data), HttpStatus.OK);
    }

    public static <T> ResponseEntity<BaseResponse<T>> buildErrorResponse(HttpStatus status, String message) {
        return new ResponseEntity<>(BaseResponse.error(message), status);
    }

    public static <T> ResponseEntity<BaseResponse<T>> buildErrorResponse(HttpStatus status, String message, T data) {
        return new ResponseEntity<>(BaseResponse.error(message, data), status);
    }

    // New Success Response Utility Methods
    public static <T> ResponseEntity<BaseResponse<T>> buildCreatedResponse(T data) {
        return new ResponseEntity<>(BaseResponse.created(data), HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<BaseResponse<T>> buildNoContentResponse() {
        return new ResponseEntity<>(BaseResponse.noContent(), HttpStatus.NO_CONTENT);
    }

    // New Error Response Utility Methods
    public static <T> ResponseEntity<ErrorResponse> buildBadRequestResponse(String message) {
        return new ResponseEntity<>(BaseResponse.badRequest(message), HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<ErrorResponse> buildUnauthorizedResponse(String message) {
        return new ResponseEntity<>(BaseResponse.unauthorized(message), HttpStatus.UNAUTHORIZED);
    }

    public static <T> ResponseEntity<ErrorResponse> buildForbiddenResponse(String message) {
        return new ResponseEntity<>(BaseResponse.forbidden(message), HttpStatus.FORBIDDEN);
    }

    public static <T> ResponseEntity<ErrorResponse> buildNotFoundResponse(String message) {
        return new ResponseEntity<>(BaseResponse.notFound(message), HttpStatus.NOT_FOUND);
    }

    public static <T> ResponseEntity<ErrorResponse> buildMethodNotAllowedResponse(String message) {
        return new ResponseEntity<>(BaseResponse.methodNotAllowed(message), HttpStatus.METHOD_NOT_ALLOWED);
    }

    public static <T> ResponseEntity<ErrorResponse> buildConflictResponse(String message) {
        return new ResponseEntity<>(BaseResponse.conflict(message), HttpStatus.CONFLICT);
    }

    public static <T> ResponseEntity<ErrorResponse> buildInternalServerErrorResponse(String message) {
        return new ResponseEntity<>(BaseResponse.internalServerError(message), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static <T> ResponseEntity<ErrorResponse> buildServiceUnavailableResponse(String message) {
        return new ResponseEntity<>(BaseResponse.serviceUnavailable(message), HttpStatus.SERVICE_UNAVAILABLE);
    }
}