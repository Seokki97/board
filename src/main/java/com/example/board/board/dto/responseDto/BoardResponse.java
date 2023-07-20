package com.example.board.board.dto.responseDto;

import com.example.board.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardResponse {

    private Long boardId;

    private String writer;

    private String title;

    private String content;

    private String createDateTime;

    private String updateDateTime;

    @Builder
    public BoardResponse(Long boardId, String writer, String title, String content, String createDateTime, String updateDateTime) {
        this.boardId = boardId;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createDateTime = createDateTime;
        this.updateDateTime = updateDateTime;
    }
}
