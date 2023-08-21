package com.example.board.board.service.create;

import com.example.board.board.domain.Board;
import com.example.board.board.dto.requestDto.BoardRequest;
import com.example.board.board.repository.BoardRepository;
import com.example.board.board.service.BoardService;
import com.example.board.member.dto.responseDto.MemberResponse;
import com.example.board.member.exception.MemberNotFoundException;
import com.example.board.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BoardCreateTest {

    @InjectMocks
    private BoardService boardService;

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private MemberService memberService;


    @DisplayName("글 작성 테스트 - 회원아이디 존재")
    @Test
    public void writePostTest(){
        Long memberId = 1L;

        MemberResponse mockMemberResponse = MemberResponse.builder()
                .memberId(memberId)
                .email("테스트")
                .nickname("동")
                .build();

        BoardRequest boardRequest = BoardRequest.builder()
                .title("제목스껄")
                .content("내용스껄")
                .build();

        Board mockBoard = Board.builder()
                .member(mockMemberResponse.toMember())
                .boardId(123L)
                .title(boardRequest.getTitle())
                .content(boardRequest.getContent())
                .build();

        // < 회원아이디 존재할 때 >
        when(memberService.findMemberById(memberId)).thenReturn(mockMemberResponse);

        when(boardRepository.save(any(Board.class))).thenReturn(mockBoard);

        boardService.writePost(boardRequest,memberId);

        verify(boardRepository,times(1)).save(any(Board.class));

    }

    @DisplayName("글 작성 테스트(예외) - 회원아이디 미존재")
    @Test
    void writePostTestException(){
        Long memberId = 1L;

        BoardRequest boardRequest = BoardRequest.builder()
                .title("제목스껄")
                .content("내용스껄")
                .build();

        // < 회원아이디 존재하지 않을 때 >
        when(memberService.findMemberById(memberId)).thenThrow(MemberNotFoundException.class);

        // 메서드 호출 시 예외가 발생하는지 검증 (MemberNotFoundException이 발생해야 함)
        assertThrows(MemberNotFoundException.class, () -> boardService.writePost(boardRequest, memberId));

        // boardRepository.save가 호출되지 않아야 함
        verify(boardRepository, never()).save(any(Board.class));
    }


}



