package com.example.board.board.service;

import com.example.board.board.domain.Board;
import com.example.board.board.dto.requestDto.BoardRequestDto;
import com.example.board.board.repository.BoardRepository;
import com.example.board.member.domain.Member;
import com.example.board.member.repository.MemberRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor        //Todo private final 생성자 ?
public class BoardService {

    private final MemberRepository memberRepository;

    private final BoardRepository boardRepository;

    String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    public Board writeBoard(Long memberId , BoardRequestDto boardRequestDto){

        Member member = memberRepository.findByMemberId(memberId).get();

        boardRequestDto.setCreateDateTime(formatDate);

        //Todo
//        Board board = boardRepository.save();
    }
}
