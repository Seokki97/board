package com.example.board.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final int BAD_REQUEST_ERROR = 400;
    private static final int NOT_FOUND_ERROR = 404;


}
