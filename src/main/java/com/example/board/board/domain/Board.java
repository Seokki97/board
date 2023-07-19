package com.example.board.board.domain;

import com.example.board.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Entity(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @Column(name = "board_id")
    private Long boardId;

    //Todo : JoinColumn 확실히 짚고가기.
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "memberId")
//    private Long memberId;          //이렇게 바로 받아오면 안되고안되고 Member 객체를 받아와야 하는가?
                                        //멤버 객체 만을 받아오는것만으로 column 키설정이 가능?
    private Member member;

    private String writer;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @Column(name = "create_date_time")
    private String createDateTime;

    @Builder
    public Board(Long boardId,Member member, String title, String content, String createDateTime){
        this.boardId = boardId;
        this.member = member;
        this.writer = member.getNickname();
        this.title = title;
        this.content = content;
        this.createDateTime = createDateTime;
    }
}