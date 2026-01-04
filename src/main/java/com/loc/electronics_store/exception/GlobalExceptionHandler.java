package com.loc.electronics_store.exception;

import com.loc.electronics_store.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Object>> handingValidation(MethodArgumentNotValidException exception) {
        Map<Object, Object> errors = new HashMap<>();
        ApiResponse<Object> apiResponse = new ApiResponse<>();

        exception.getBindingResult().getAllErrors()
                .forEach(error -> {
                    String name = ((FieldError)error).getField();
                    String message = error.getDefaultMessage();

                    ErrorCode errorCode = ErrorCode.valueOf(message);

                    errors.put(name, errorCode.getMessage());
                });

        apiResponse.setCode(ErrorCode.BAD_REQUEST.getCode());
        apiResponse.setResult(errors);

        return ResponseEntity.badRequest().body(apiResponse);
    }
}
