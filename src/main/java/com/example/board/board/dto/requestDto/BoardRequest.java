package com.example.board.board.dto.requestDto;

import com.example.board.member.domain.Member;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequest {

    private Member member;

    private String title;

    private String content;

    private String createDateTime;

    @Builder
    BoardRequest(Member member, Long boardId, String title, String content, String createDateTime){
        this.member = member;
        this.title = title;
        this.content = content;
        this.createDateTime = createDateTime;
    }
}
