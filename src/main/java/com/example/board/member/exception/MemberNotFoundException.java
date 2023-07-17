package com.example.board.member.exception;

import com.example.board.advice.BadRequestException;

public class MemberNotFoundException extends BadRequestException {

    private static final String message = "해당 회원을 찾을 수 없습니다.";

    public MemberNotFoundException() {
        super(message);
    }
}
