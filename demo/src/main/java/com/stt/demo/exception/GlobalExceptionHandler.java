package com.stt.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Validation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getAllErrors()
                .forEach(error -> {

                    String fieldName =
                            ((FieldError) error).getField();

                    String errorMessage =
                            error.getDefaultMessage();

                    errors.put(fieldName, errorMessage);
                });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Invalid File Errors
    @ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<Map<String, String>> handleInvalidFileException(
            InvalidFileException ex) {

        Map<String, String> error = new HashMap<>();

        error.put("error", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // User Not Found
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(
            UserNotFoundException ex) {

        Map<String, String> error = new HashMap<>();

        error.put("error", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Invalid Credentials
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleInvalidCredentialsException(
            InvalidCredentialsException ex) {

        Map<String, String> error = new HashMap<>();

        error.put("error", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    // Generic Errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(
            Exception ex) {

        Map<String, String> error = new HashMap<>();

        error.put("error", ex.getMessage());

        return new ResponseEntity<>(error,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(TranscriptNotFoundException.class)
    public ResponseEntity<Map<String,String>>
    handleTranscriptNotFoundException(
            TranscriptNotFoundException ex){

        Map<String,String> error = new HashMap<>();

        error.put("error", ex.getMessage());

        return new ResponseEntity<>(
                error,
                HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>>
    handleEmailAlreadyExistsException(
            EmailAlreadyExistsException ex){

        Map<String, String> error = new HashMap<>();

        error.put("error", ex.getMessage());

        return new ResponseEntity<>(
                error,
                HttpStatus.CONFLICT
        );
    }
}