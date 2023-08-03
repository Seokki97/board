package com.example.board.board.service;

import com.example.board.board.domain.Board;
import com.example.board.board.dto.requestDto.BoardRequest;
import com.example.board.board.dto.responseDto.BoardResponse;
import com.example.board.board.dto.responseDto.UpdateBoardResponse;
import com.example.board.board.repository.BoardRepository;
import com.example.board.member.domain.Member;
import com.example.board.member.exception.MemberNotFoundException;
import com.example.board.member.repository.MemberRepository;
import com.example.board.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final MemberService memberService;


    /*
        작성 API
    */

    // 게시물 작성 기능
    public void writePost(BoardRequest boardRequest, Long id){
        LocalDateTime formatDate = LocalDateTime.now();

        Board board = Board.builder()
                .member(memberService.findMemberById(id).toMember())
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

    /*
     *   수정 API
     */

    @Transactional
    public UpdateBoardResponse modifyPost(Long boardId, Long memberId, BoardRequest boardRequest){
        if(!boardRepository.existsById(boardId)){
            throw new IllegalArgumentException("해당 게시물을 찾을 수 없습니다");
        }

        if(!showPostById(boardId).getMember().getMemberId().equals(memberId)){
            throw new MemberNotFoundException();
        }

        LocalDateTime formatDate = LocalDateTime.now();

        Board board = showPostById(boardId);

        board.update(boardRequest.getTitle(),
                boardRequest.getContent(),
                formatDate);

        return UpdateBoardResponse.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .updateDateTime(board.getUpdateDateTime())
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

    /*
    *   조회 API
    *   게시글 제목으로 찾기 (일부만 입력해도 찾아짐)
    * */

    public List<Long> searchPostByTitle(String title){
        List<Board> getBoard = boardRepository.findByTitleContaining(title);

        return getBoard.stream()
                .map(Board::getBoardId)
                .collect(Collectors.toList());
    }

    public List<Long> searchPostByContent(String content){
        List<Board> getBoard = boardRepository.findByContentContaining(content);

        return getBoard.stream()
                .map(Board::getBoardId)
                .collect(Collectors.toList());
    }
}
