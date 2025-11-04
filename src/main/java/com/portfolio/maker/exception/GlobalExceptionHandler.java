package com.portfolio.maker.exception;

import com.portfolio.maker.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// --- IMPORTACIONES NUEVAS ---
import org.springframework.http.converter.HttpMessageNotReadableException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
// ----------------------------
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        // 1. Recolectar todos los mensajes de error
        String allErrors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    return fieldName + ": " + errorMessage;
                })
                .collect(Collectors.joining("\n")); // 2. Unirlos con SALTO DE LÍNEA

        // 3. Crear el DTO de ErrorResponse
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                allErrors, // 4. Asignar el string de errores al campo 'message'
                System.currentTimeMillis()
        );

        // 5. Devolver la respuesta
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * MANEJADOR #2: Errores de Lógica de Negocio (Email duplicado, etc.)
     * (Este se mantiene igual)
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {

        String errorMessage = ex.getReason();
        int status = ex.getStatusCode().value();

        ErrorResponse errorResponse = new ErrorResponse(
                status,
                errorMessage,
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    /**
     * MANEJADOR #3: Errores Inesperados (Red de seguridad)
     * (Este se mantiene igual)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocurrió un error interno: " + ex.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * MANEJADOR NUEVO: Para credenciales incorrectas (Login)
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(), // 401
                "Email o contraseña incorrectos.",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}