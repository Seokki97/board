package com.example.board.advice;

public class BadRequestException extends BusinessException{

    public BadRequestException(String message) {
        super(message);
    }
}
