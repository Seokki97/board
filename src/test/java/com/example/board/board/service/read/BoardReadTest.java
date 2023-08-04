package com.example.board.board.service.read;

import com.example.board.board.domain.Board;
import com.example.board.board.exception.BoardNotFoundException;
import com.example.board.board.repository.BoardRepository;
import com.example.board.board.service.BoardService;
import com.example.board.member.domain.Member;
import com.example.board.member.exception.MemberNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class BoardReadTest {

    @InjectMocks
    private BoardService boardService;

    @Mock
    private BoardRepository boardRepository;

    @DisplayName("게시물 ID 로 단일조회")
    @Test
    void showPostByIdTest(){
        Long boardId = 1L;

        Member mockMember = mock(Member.class);

        Board mockBoard = Board.builder()
//                .member(any(Member.class))        // any(Member.class)를 사용하면 Mockito의 any() 메서드가 Member 클래스의 Mock 객체를 생성하게 되며,
//                                                  // 실제 Member 객체가 아닌 Mock 객체를 Board 객체의 member 필드에 할당하게 됩니다.
//                                                  // 결과적으로 Board 객체에서 member 필드가 null이 되어,
//                                                  // 이후에 해당 필드를 접근할 때 NullPointerException 이 발생합니다.
                .member(mockMember)
                .boardId(boardId)
                .title("제목")
                .content("내용")
                .build();

        when(boardRepository.findById(boardId)).thenReturn(Optional.of(mockBoard));

        Board result = boardService.showPostById(boardId);

        verify(boardRepository,times(1)).findById(boardId);

        // 테스트 결과 검증
        assertEquals(boardId, result.getBoardId());
        assertEquals(mockMember, result.getMember()); // Member 객체까지 검증
        assertEquals("제목", result.getTitle());
        assertEquals("내용", result.getContent());

    }

    @DisplayName("게시물 전체 조회")
    @Test
    void showAllPostTest(){

        List<Board> boardList=  new ArrayList<>();

        Board board1 = mock(Board.class);
        Board board2 = mock(Board.class);

        boardList.add(board1);
        boardList.add(board2);

        when(boardRepository.findAll()).thenReturn(boardList);

        List<Board> result = boardService.showAllPost();

        verify(boardRepository,times(1)).findAll();

        assertEquals(2,result.size());
    }

    @Test
    @DisplayName("회원 ID 로 해당 ID 게시물 전체 조회")
        //Todo : 총 3개의 게시글 중에서 memberId가 일치하는 2개의 게시물을 찾아야 하는데... 어떻게 짜지?
    void showAllPostByMemberIdTest(){
        Long memberId1 = 1L;
        Long memberId2 = 2L;

        Member member1 = Member.builder()
                .memberId(memberId1)
                .email("동")
                .nickname("동동")
                .build();

        Member member2 = Member.builder()
                .memberId(memberId2)
                .email("구리")
                .nickname("구리구리")
                .build();

        Board board1 = mock(Board.class);
        Board board2 = mock(Board.class);
        Board board3 = mock(Board.class);


//        List<Board> matchingBoardList = new ArrayList<>();
//        matchingBoardList.add(board2);
//        matchingBoardList.add(board3);

        //원하는 값을 주입가능.
        when(board1.getMember()).thenReturn(member1);
        when(board2.getMember()).thenReturn(member2);
        when(board3.getMember()).thenReturn(member2);

        List<Board> listAll = new ArrayList<>();
        listAll.add(board1);
        listAll.add(board2);
        listAll.add(board3);

        when(boardRepository.findAll()).thenReturn(listAll);
//        when(boardRepository.findAllByMemberId(memberId2)).thenReturn(matchingBoardList);

        List<Board> result = boardService.showAllPostByMemberId(memberId2);

        verify(boardRepository,times(1)).findAllByMemberId(memberId2);

        assertEquals(2,result.size());

    }

    @DisplayName("조회 예외발생-> 게시판 ID 없음")
    @Test
    void BoardIdExceptionTest(){
        Long boardId = 1L;

        when(boardRepository.findById(boardId)).thenThrow(BoardNotFoundException.class);

        assertThrows(BoardNotFoundException.class, () -> boardService.showPostById(boardId));
    }

    @DisplayName("조회 예외발생-> 회원 ID 없음")
    @Test
    void MemberIdExceptionTest(){
        Long exceptId = 1L;

        //회원 아이디를 조회 했을때 반환되는 값이 하나도 없음.
        when(boardRepository.findAllByMemberId(exceptId)).thenReturn(Collections.emptyList());

        //예외 발생
        assertThrows(MemberNotFoundException.class, () -> boardService.showAllPostByMemberId(exceptId));

        verify(boardRepository,times(1)).findAllByMemberId(exceptId);
    }


}

