package com.example.board.board.dto.requestDto;

import com.example.board.member.domain.Member;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequest {

    private String title;

    private String content;

    @Builder
    BoardRequest(Member member, Long boardId, String title, String content){
        this.title = title;
        this.content = content;
    }
}
