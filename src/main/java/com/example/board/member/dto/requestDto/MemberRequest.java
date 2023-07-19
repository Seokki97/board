package com.example.board.member.dto.requestDto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRequest {

    private String nickname;

    private String email;


    @Builder
    public MemberRequest(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
}
