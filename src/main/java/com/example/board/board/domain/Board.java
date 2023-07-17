package com.example.board.board.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter
@Entity(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @Column(name = "board_id")
    private Long boardId;

    //Todo : JoinColumn 확실히 짚고가기.
//    @JoinColumn(name = "member_id")
//    private Long memberId;

    private String title;

    private String content;

    @Column(name = "create_date_time")
    private String createDateTime;

    @Builder
    public Board(Long boardId, String title, String content, String createDateTime){
        this.boardId = boardId;
//        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.createDateTime = createDateTime;
    }
}
