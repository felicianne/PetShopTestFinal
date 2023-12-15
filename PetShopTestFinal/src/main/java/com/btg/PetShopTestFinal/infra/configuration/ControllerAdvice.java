package com.btg.PetShopTestFinal.infra.configuration;

import com.btg.PetShopTestFinal.infra.exception.ClientBadRequest;
import com.btg.PetShopTestFinal.infra.exception.PasswordValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            WebRequest request
    ) {
        log.warn("Unhandled exception:", ex);
        var errors = new ArrayList<Map<String, String>>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(Map.of(error.getField(), error.getDefaultMessage()));
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(Map.of(error.getObjectName(), error.getDefaultMessage()));
        }

        var body = Map.of(
                "code", HttpStatus.BAD_REQUEST,
                "errors", errors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordValidationError.class)
    public List<String> handlerPassword(PasswordValidationError exception){
        return Collections.singletonList(exception.getMessage());
    }

    @ExceptionHandler(ClientBadRequest.class)
    public ResponseEntity<Object> BadRequest(ClientBadRequest exception){
        var body = Map.of(
                "code", HttpStatus.BAD_REQUEST,
                "errors", exception.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }
}
