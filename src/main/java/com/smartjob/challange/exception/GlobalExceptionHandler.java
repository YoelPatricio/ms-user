package com.smartjob.challange.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler  {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {

    FieldError fieldError = ex.getBindingResult().getFieldErrors().stream().findFirst().orElse(null);
    String errorMessage = "Error de validación";

    if (fieldError != null) {
      errorMessage = fieldError.getDefaultMessage();
    }

    ErrorResponse errorResponse = new ErrorResponse(errorMessage);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

  }

}
