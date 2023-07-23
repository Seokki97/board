package com.example.board.board.domain;

import com.example.board.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    //Todo : JoinColumn 확실히 짚고가기.

    // 이렇게 객체 자체의 primary 키를 받아서 객체로 저장 가능
    @ManyToOne
    @JoinColumn(name = "memberId", referencedColumnName = "member_id")
    private Member member;


    private String writer;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @Column(name = "create_date_time")
    private String createDateTime;

    @Column(name = "update_date_time")
    private String updateDateTime;

    @Builder
    public Board(Long boardId,Member member, String title, String content, String createDateTime,String updateDateTime){
        this.boardId = boardId;
        this.member = member;
        this.writer = member.getNickname();
        this.title = title;
        this.content = content;
        this.createDateTime = createDateTime;
        this.updateDateTime = updateDateTime;
    }
}
