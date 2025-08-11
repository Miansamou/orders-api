package com.acme.orders_api.infrastructure.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
class GlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<ProblemDetail> handleValidation(MethodArgumentNotValidException ex){
    var pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    pd.setTitle("Validation failed");
    pd.setProperty("errors", ex.getBindingResult().getFieldErrors().stream()
      .map(fe -> {
        assert fe.getDefaultMessage() != null;
        return Map.of("field", fe.getField(), "message", fe.getDefaultMessage());
      }).toList());
    return ResponseEntity.of(pd).build();
  }
  @ExceptionHandler(NoSuchElementException.class)
  ProblemDetail notFound(){ return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,"Resource not found"); }
}
