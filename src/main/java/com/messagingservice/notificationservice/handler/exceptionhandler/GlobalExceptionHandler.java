package com.messagingservice.notificationservice.handler.exceptionhandler;

import com.messagingservice.notificationservice.exception.InvalidAuthHeaderException;
import com.messagingservice.notificationservice.models.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .findFirst().orElse("Validation Error");
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(ErrorResponse.Error.builder().message(errorMessage).code("INVALID_REQUEST").build())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(ErrorResponse.Error.builder().code("INTERNAL_SERVER_ERROR").message(ex.getMessage()).build())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidAuthHeaderException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleInvalidAuthHeaderException(InvalidAuthHeaderException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(ErrorResponse.Error.builder().code("UNAUTHORIZED").message(ex.getMessage()).build())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
