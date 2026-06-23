package com.M3Tours.empresas.Exceptions;



import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.resource.NoResourceFoundException;

import com.M3Tours.empresas.DTO.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(NoResourceFoundException.class) // 404 - Datos no encontrados
    public ResponseEntity<ErrorResponse> handleNoResourceFound(NoResourceFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND; 
        log.warn("Ruta o recurso estático no encontrado");
        ErrorResponse error = new ErrorResponse();
        error.setMensaje("Ruta no encontrada");
        error.setDetalle("El endpoint '" + request.getRequestURI() + "' no existe en este servidor o el recurso estático no fue encontrado.");
        error.setStatus(status.value());
        error.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // 400 - Datos invallidos
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String detalle = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(err -> err.getField() + ":" + err.getDefaultMessage())
            .collect(Collectors.joining(","));
        log.warn("Fallo de validación en tiempo de persistencia");
        ErrorResponse error = new ErrorResponse();
        error.setMensaje("Errores de validacion");
        error.setDetalle(detalle);
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setTimeStamp(LocalDateTime.now());
        return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(error);
    }

    @ExceptionHandler(Exception.class) // 500 - Excepción generica
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        log.warn("Fallo Generico en la aplicacion");
        ErrorResponse error = new ErrorResponse();
        error.setMensaje("Errores interno del servidor");
        error.setDetalle(ex.getMessage());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setTimeStamp(LocalDateTime.now());
        return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(error);
    }
}   
