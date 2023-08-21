package com.example.board.board.controller;

import com.example.board.board.domain.Board;
import com.example.board.board.dto.requestDto.BoardRequest;
import com.example.board.board.dto.responseDto.BoardResponse;
import com.example.board.board.dto.responseDto.UpdateBoardResponse;
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
         Request를 title content member로 받아서 void로 반환
    */
    @PostMapping("/write/{memberId}")
    public ResponseEntity<Void> writeBoard(@RequestBody BoardRequest boardRequest,
                                           @PathVariable("memberId") Long memberId){

        boardService.writePost(boardRequest,memberId);

        return ResponseEntity.noContent().build();  // Void 반환
    }

    /*
        <게시글 조회 api>
         Request를 board pk로 받아서 컬럼에 있는 모든 값 반환
    */

    // 게시글 ID로 단일조회
    @GetMapping("/show/{boardId}")
    public ResponseEntity<Board> showBoardById(@PathVariable("boardId")Long boardId){
        return ResponseEntity.ok().body(boardService.showPostById(boardId));
    }

    // 회원 ID로 해당 ID의 게시글 전체조회
    @GetMapping("/show")
    public ResponseEntity<List<Board>> showBoardByMemberId(@RequestParam("memberId")Long memberId){
        return ResponseEntity.ok().body(boardService.showAllPostByMemberId(memberId));
    }

    // 게시글 전체조회
    @GetMapping("show/all")
    public ResponseEntity<List<Board>> showPostAll(){
        return ResponseEntity.ok().body(boardService.showAllPost());
    }

    /*
        <Update Board>
        게시글 수정 -> Request로 member의 pk 와 board의 pk title content를 받아서 수정된 부분을 반환
    */

    @PutMapping("/modify/{boardId}/{memberId}")
    public ResponseEntity<UpdateBoardResponse> modifyPost(@PathVariable("boardId")Long boardId,
                                                          @PathVariable("memberId")Long memberId,
                                                          @RequestBody BoardRequest boardRequest){

        return ResponseEntity.ok().body(boardService.modifyPost(boardId,memberId,boardRequest));
    }


    /*
        <게시글 삭제 api>
        request로 board pk + member pk 받아서 void로 반환
    */

    @DeleteMapping("/delete/{boardId}/{memberId}")
    public ResponseEntity<Void> deletePost(@PathVariable("boardId")Long boardId,
                                           @PathVariable("memberId")Long memberId){
        boardService.deletePost(boardId,memberId);
        return ResponseEntity.noContent().build();
    }

    /*
        <Search>
        게시글 검색 -> 게시글 제목으로 찾기 (일부만 입력해도 찾아짐) request는 String ~~로 응답은 해당 게시물 pk반환
                  -> 내용으로 찾기
    */

    @GetMapping("/search/title/{title}")
    public ResponseEntity<List<Long>> searchPostByTitle(@PathVariable("title")String title){
        List<Long> getBoardId = boardService.searchPostByTitle(title);
        return ResponseEntity.ok().body(getBoardId);
    }

    @GetMapping("/search/content/{content}")
    public ResponseEntity<List<Long>> searchPostByContent(@PathVariable("content")String content){
        List<Long> getBoardId = boardService.searchPostByContent(content);
        return ResponseEntity.ok().body(getBoardId);
    }
}
