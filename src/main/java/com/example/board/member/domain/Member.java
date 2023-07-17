package com.example.board.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
