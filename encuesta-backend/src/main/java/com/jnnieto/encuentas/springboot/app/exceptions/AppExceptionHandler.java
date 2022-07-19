package com.jnnieto.encuentas.springboot.app.exceptions;

import com.jnnieto.encuentas.springboot.app.models.responses.ValidationErrors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Es una clase manejadora de errores de validación
 */
@ControllerAdvice
public class AppExceptionHandler {

    /**
     * Método que lanza una excepción cuando se envían mal los campos de una petición
     * @param ex es la excepción que se dispara cuando se envían mal los campos de una petición
     * @param webRequest
     * @return Devuelve la respuesta de error mapeada en la clase wrapper ValidationError
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleValidationErrorException(MethodArgumentNotValidException ex, WebRequest webRequest) {

        Map<String, String> errors = new HashMap<>();

        for (ObjectError error : ex.getBindingResult().getFieldErrors()) {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
;        }

        ValidationErrors validationErrors = new ValidationErrors(errors, new Date());

        return new ResponseEntity<>(validationErrors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
