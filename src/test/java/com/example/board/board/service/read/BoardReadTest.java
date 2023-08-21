package com.example.board.board.service.read;

import com.example.board.board.domain.Board;
import com.example.board.board.exception.BoardNotFoundException;
import com.example.board.board.repository.BoardRepository;
import com.example.board.board.service.BoardService;
import com.example.board.member.domain.Member;
import com.example.board.member.dto.responseDto.MemberResponse;
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
        Member member1 = mock(Member.class);
        when(member1.getMemberId()).thenReturn(memberId1);       //멤버1 주입

        Long memberId2 = 2L;
        Member member2 = mock(Member.class);
        when(member2.getMemberId()).thenReturn(memberId2);

        Board board1= mock(Board.class);
        when(board1.getBoardId()).thenReturn(1L);
        Board board2= mock(Board.class);
        when(board2.getBoardId()).thenReturn(2L);
        Board board3= mock(Board.class);
        when(board3.getBoardId()).thenReturn(3L);
                                                            //board boardId memberId
        when(board1.getMember()).thenReturn(member1);       //1     1       1
        when(board2.getMember()).thenReturn(member2);       //2     2       2
        when(board3.getMember()).thenReturn(member1);       //3     3       1

        assertEquals(1L,board1.getBoardId());
        assertEquals(2L,board2.getBoardId());
        assertEquals(3L,board3.getBoardId());
        assertEquals(1L,board1.getMember().getMemberId());
        assertEquals(2L,board2.getMember().getMemberId());
        assertEquals(1L,board3.getMember().getMemberId());

        List<Board> mockListAll = new ArrayList<>();
        mockListAll.add(board1); mockListAll.add(board2); mockListAll.add(board3);

        List<Board> matchingList = new ArrayList<>();
        matchingList.add(board1); matchingList.add(board3);

//        when(boardRepository.findAll()).thenReturn(mockListAll);
        when(boardRepository.findAllByMemberId(1L)).thenReturn(mockListAll);

        List<Board> result = boardService.showAllPostByMemberId(1L);

        verify(boardRepository,times(1)).findAllByMemberId(1L);

        assertEquals(2,result.size());


    }

//    public List<Board> showAllPostByMemberId(Long id) throws MemberNotFoundException{
//        List<Board> boardList = boardRepository.findAllByMemberId(id);
//        if(boardList.size()==0){
//            throw new MemberNotFoundException();
//        }
//        return boardList;
//    }


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

