package com.example.board.board.service;

import com.example.board.board.domain.Board;
import com.example.board.board.dto.requestDto.BoardRequest;
import com.example.board.board.dto.responseDto.BoardResponse;
import com.example.board.board.repository.BoardRepository;
import com.example.board.member.domain.Member;
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

    String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

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
}
