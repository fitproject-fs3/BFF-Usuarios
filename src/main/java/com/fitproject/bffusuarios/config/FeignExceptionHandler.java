package com.fitproject.bffusuarios.config;

import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FeignExceptionHandler {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeignException(FeignException ex) {
        int status = ex.status() > 0 ? ex.status() : 500;
        String body = ex.contentUTF8();
        return ResponseEntity.status(status).body(body.isBlank() ? ex.getMessage() : body);
    }
}
