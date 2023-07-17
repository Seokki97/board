package com.example.board.member.dto.responseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponse {

    private Long memberId;

    private String nickname;

    private String email;

    @Builder
    public MemberResponse(Long memberId, String nickname, String email) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.email = email;
    }
}
