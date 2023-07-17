package com.example.board.member.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //생성자
@Entity(name = "members")        // member : database error
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1부터 자동 증분
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @NotNull
    private String nickname;

    @NotNull
    private String email;

    @Builder
    public Member(final Long memberId, final String nickname, final String email) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.email = email;
    }
}
