package com.example.board.board.dto.responseDto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateBoardResponse {

    private String title;

    private String content;

    private LocalDateTime updateDateTime;

    @Builder
    UpdateBoardResponse(String title, String content, LocalDateTime updateDateTime){
        this.title = title;
        this.content = content;
        this.updateDateTime = updateDateTime;
    }
}
