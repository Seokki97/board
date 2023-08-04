package com.example.board.board.exception;

import com.example.board.advice.BadRequestException;

public class BoardNotFoundException extends BadRequestException {

    private static final String message = "해당 게시판을 찾을 수 없습니다.";

    public BoardNotFoundException() {
        super(message);
    }
}
