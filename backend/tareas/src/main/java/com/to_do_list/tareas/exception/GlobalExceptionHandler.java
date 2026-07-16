package com.to_do_list.tareas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

    @ExceptionHandler(TareaNotFoundException.class)
    public ResponseEntity<?> manejoTareaNotFoundException(TareaNotFoundException e){
        Map<String, Object> error = new LinkedHashMap<>();

        error.put("Fecha:", timestamp);
        error.put("Estado:", 404);
        error.put("Tipo de error:", "Not Found");
        error.put("Mensaje:", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> manejoValidaciones(MethodArgumentNotValidException e){
        Map<String, Object> error = new LinkedHashMap<>();
        Map<String, Object> errors = new LinkedHashMap<>();
        error.put("Fecha:", timestamp);
        error.put("Estado:", 400);
        error.put("Tipo de error:", "Bad Request");
        e.getBindingResult().getFieldErrors().forEach(errores -> errors.put(errores.getField(), errores.getDefaultMessage()));
        error.put("Errores:", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> manejoGeneral(Exception e){
        Map<String, Object> error = new LinkedHashMap<>();

        error.put("Fecha:", timestamp);
        error.put("Estado:", 500);
        error.put("Tipo de error:", "Internal Server Error");
        error.put("Mensaje:", "Error interno en el servidor");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
