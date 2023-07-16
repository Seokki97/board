package com.example.board.member.dto.requestDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberRequest {

    private String nickname;

    private String email;


    @Builder
    public MemberRequest(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }

}
