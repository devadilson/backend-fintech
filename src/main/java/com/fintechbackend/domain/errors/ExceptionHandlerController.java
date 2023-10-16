package com.fintechbackend.domain.errors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fintechbackend.domain.dtos.ExceptionDTO;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ExceptionHandlerController {

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity threatDuplicatEntity(DataIntegrityViolationException exception) {
    ExceptionDTO exceptionDTO = new ExceptionDTO("Usuário já cadastrado", "400");
    return ResponseEntity.badRequest().body(exceptionDTO);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity theat404(EntityNotFoundException exception) {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity theatGeneralException(Exception exception) {
    ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), "500");
    return ResponseEntity.internalServerError().body(exceptionDTO);
  }

}
