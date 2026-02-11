package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException e) {

        String objectName = e.getBindingResult()
                .getObjectName();

        if ("userDTO".equalsIgnoreCase(objectName)) {
            return ResponseEntity.badRequest()
                    .body("Логин или пароль некорректны");
        }

        if ("pointDTO".equalsIgnoreCase(objectName)) {
            return ResponseEntity.badRequest()
                    .body("Координаты точки вне допустимого диапазона");
        }

        return ResponseEntity.badRequest()
                .body("Некорректные данные");
    }
}

