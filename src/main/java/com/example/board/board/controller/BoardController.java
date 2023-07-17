package com.example.board.board.controller;

import com.example.board.board.dto.requestDto.BoardRequestDto;
import com.example.board.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // <Create>
    // 게시글 작성 -> Request를 title content member로 받아서 void로 반환
    @PostMapping("/write/{memberId}")
    public ResponseEntity<Void> writeBoard(@RequestBody BoardRequestDto boardRequestDto,
                                           @PathVariable("memberId") Long memberId){
        boardService.writeBoard(memberId, boardRequestDto);
    }

    // <Read Board>
    // 게시글 조회 -> Request를 board pk로 받아서 컬럼에 있는 모든 값 반환


    // <Update Board>
    // 게시글 수정 -> Request로 member의 pk 와 board의 pk title content를 받아서 수정된 부분을 반환


    // <Delete Board>
    // 게시글 삭제 -> request로 board pk + member pk 받아서 void로 반환


    // <Search>
    // 게시글 검색 -> 게시글 제목으로 찾기 (일부만 입력해도 찾아짐) request는 String ~~로 응답은 해당 게시물 pk반환
    //           -> 내용으로 찾기

}
