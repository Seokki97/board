package com.example.board.board.controller;

import com.example.board.board.domain.Board;
import com.example.board.board.dto.requestDto.BoardRequest;
import com.example.board.board.dto.responseDto.BoardResponse;
import com.example.board.board.repository.BoardRepository;
import com.example.board.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    /*
        <게시글 작성 api>
        게시글 작성 -> Request를 title content member로 받아서 void로 반환
    */
    @PostMapping("/write/{memberId}")
    public ResponseEntity<Void> writeBoard(@RequestBody BoardRequest boardRequest,
                                           @PathVariable("memberId") Long memberId){

        boardService.writeBoard(boardRequest,memberId);

        return ResponseEntity.noContent().build();  // Void 반환
    }

    /*
        <게시글 조회 api>
        Request를 board pk로 받아서 컬럼에 있는 모든 값 반환
    */

    //ID로 단일조회
    @GetMapping("/show/{boardId}")
    public ResponseEntity<Board> showBoardById(@PathVariable("boardId")Long boardId){
        return ResponseEntity.ok().body(boardService.showBoardById(boardId));
    }

    //게시글 전체조회
    @GetMapping("show/all")
    public ResponseEntity<List<Board>> findAll(){
        return ResponseEntity.ok().body(boardService.showAllPost());
    }


    // <Update Board>
    // 게시글 수정 -> Request로 member의 pk 와 board의 pk title content를 받아서 수정된 부분을 반환


    // <Delete Board>
    // 게시글 삭제 -> request로 board pk + member pk 받아서 void로 반환


    // <Search>
    // 게시글 검색 -> 게시글 제목으로 찾기 (일부만 입력해도 찾아짐) request는 String ~~로 응답은 해당 게시물 pk반환
    //           -> 내용으로 찾기

}
