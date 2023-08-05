package com.example.board.board.service.delete;

import com.example.board.board.domain.Board;
import com.example.board.board.exception.BoardNotFoundException;
import com.example.board.board.repository.BoardRepository;
import com.example.board.board.service.BoardService;
import com.example.board.member.domain.Member;
import com.example.board.member.exception.MemberNotFoundException;
import com.example.board.member.repository.MemberRepository;
import com.example.board.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BoardDeleteTest {

    @InjectMocks
    private BoardService boardService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BoardRepository boardRepository;

    @Test
    @DisplayName("게시물 삭제 (정상)")
    void deletePostTest(){

        Long memberId = 1L;
        Member member = mock(Member.class);
        when(member.getMemberId()).thenReturn(memberId);

        Long boardId = 123L;
        Board board = mock(Board.class);
        when(board.getBoardId()).thenReturn(boardId);
        when(board.getMember()).thenReturn(member);

        assertEquals(1L,board.getMember().getMemberId());
        assertEquals(123L,board.getBoardId());

        when(boardRepository.existsById(123L)).thenReturn(true);
        when(boardRepository.findById(123L)).thenReturn(Optional.of(board));

        boardService.deletePost(boardId,memberId);

        verify(boardRepository,times(1)).delete(board);

    }

    @DisplayName("게시글 삭제 (예외) - 게시글 못찾음")
    @Test
    void deleteBoardExceptionTest(){
        Long memberId = 1L;
        Long boardId = 123L;

        when(boardRepository.existsById(boardId)).thenThrow(BoardNotFoundException.class);

        assertThrows(BoardNotFoundException.class, () -> boardService.deletePost(boardId,memberId));

    }

    @DisplayName("게시글 삭제 (예외) - 회원 못찾음")
    @Test
    void deleteMemberExceptionTest(){
        Long memberId = 1L;
        Long wrongMemberId = 2L;
        Member mockMember = mock(Member.class);
        when(mockMember.getMemberId()).thenReturn(memberId);

        Long boardId = 123L;
        Board mockBoard = mock(Board.class);
        when(mockBoard.getMember()).thenReturn(mockMember);

        when(boardRepository.existsById(boardId)).thenReturn(true);

        when(boardRepository.findById(123L)).thenReturn(Optional.of(mockBoard));

        when(!mockBoard.getMember().getMemberId().equals(wrongMemberId)).thenThrow(MemberNotFoundException.class);

        assertThrows(MemberNotFoundException.class, () -> boardService.deletePost(boardId,memberId));
    }
}
