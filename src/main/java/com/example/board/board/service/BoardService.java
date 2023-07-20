package com.example.board.board.service;

import com.example.board.board.domain.Board;
import com.example.board.board.dto.requestDto.BoardRequest;
import com.example.board.board.dto.responseDto.BoardResponse;
import com.example.board.board.repository.BoardRepository;
import com.example.board.member.domain.Member;
import com.example.board.member.exception.MemberNotFoundException;
import com.example.board.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final MemberRepository memberRepository;

    private final BoardRepository boardRepository;

    String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
//    String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

    /*
        작성 API
    */

    // 게시물 작성 기능
    public void writePost(BoardRequest boardRequest, Long id){
        Board board = Board.builder()
                .member(memberRepository.findByMemberId(id).get())
                .title(boardRequest.getTitle())
                .content(boardRequest.getContent())
                .createDateTime(formatDate)
                .build();

        boardRepository.save(board);
    }


    /*
    *    조회 API
    *    1) 게시물 ID로 단일조회
    *    2) 회원 ID 로 해당 ID의 게시물 전체조회
    *    3) 게시물 전체조회
    */
    // 게시물 ID 로 단일조회
    public Board showPostById(Long id){
        Board board = boardRepository.findById(id).orElseThrow();

        return board;
    }

    // 회원 ID 로 해당 ID 게시물 전체 조회
    public List<Board> showAllPostByMemberId(Long id){
        List<Board> boardList = boardRepository.findAllByMemberId(id);
        return boardList;
    }

    // 게시물 전체 조회
    public List<Board> showAllPost(){
        List<Board> boardList = boardRepository.findAll();

        return boardList;
    }

    /*Todo
     *   수정 API
    *   (미완료) : updateDateTime가 DB에 반영 x -> 저장을 어떻게 시키지..?
    *   title 과 content를 repository 안에서 해결시켜서 난항.....
    * */

    public BoardResponse modifyPost(Long boardId, Long memberId, BoardRequest boardRequest){
        if(!boardRepository.existsById(boardId)){
            throw new IllegalArgumentException("해당 게시물을 찾을 수 없습니다");
        }

        if(!showPostById(boardId).getMember().getMemberId().equals(memberId)){
            throw new MemberNotFoundException();
        }

        Board board = showPostById(boardId);


        Board modifiedBoard =  board.builder()
                    .boardId(board.getBoardId())
                    .member(memberRepository.findByMemberId(memberId).get())
                    .title(boardRequest.getTitle())
                    .content(boardRequest.getContent())
                    .createDateTime(board.getCreateDateTime())
                    .updateDateTime(formatDate)
                    .build();

        boardRepository.save(modifiedBoard);

        return BoardResponse.builder()
                .boardId(modifiedBoard.getBoardId())
                .writer(modifiedBoard.getWriter())
                .title(modifiedBoard.getTitle())
                .content(modifiedBoard.getContent())
                .createDateTime(modifiedBoard.getCreateDateTime())
                .updateDateTime(modifiedBoard.getUpdateDateTime())
                .build();

    }

    /*
    *    삭제 API
    *    1) 게시글 ID 는 불일치         : 해당 게시물 찾을 수 없습니다.
    *    2) 게시글ID는 존재하지만, 회원ID와 불일치    : 해당 회원을 찾을 수 없습니다.
    */

    public void deletePost(Long boardId, Long memberId){

        if(!boardRepository.existsById(boardId)){
            throw new IllegalArgumentException("해당 게시물을 찾을 수 없습니다.");
        }

        Board board = boardRepository.findById(boardId).get();

        if(!board.getMember().getMemberId().equals(memberId)){
            throw new MemberNotFoundException();
        }

        boardRepository.delete(board);
    }
}
