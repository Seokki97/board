package com.example.board.board.dto.requestDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequestDto {

    private Long boardId;

    private String title;

    private String content;

    private String createDateTime;

    @Builder
    BoardRequestDto(Long boardId, String title, String content, String createDateTime){
        this.boardId = boardId;
//        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.createDateTime = createDateTime;
    }
}
