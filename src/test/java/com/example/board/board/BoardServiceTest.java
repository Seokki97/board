package com.example.board.board;

import com.example.board.board.domain.Board;
import com.example.board.board.dto.requestDto.BoardRequest;
import com.example.board.board.repository.BoardRepository;
import com.example.board.board.service.BoardService;
import com.example.board.member.domain.Member;
import com.example.board.member.dto.responseDto.MemberResponse;
import com.example.board.member.exception.MemberNotFoundException;
import com.example.board.member.repository.MemberRepository;
import com.example.board.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @InjectMocks
    private BoardService boardService;

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;


    @DisplayName("글 작성 테스트 - 회원아이디 존재")
    @Test
    //회원이 발견되지 않는 경우 (주어진 ID로 회원을 찾을 수 없는 경우)와 같은 다양한 시나리오를 커버하도록 추가적인 테스트 케이스를 고려해보세요.
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
                .createDateTime(LocalDateTime.now())
                .updateDateTime(LocalDateTime.now())
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
}

/*

    // 회원 ID 로 해당 ID 게시물 전체 조회
    public List<Board> showAllPostByMemberId(Long id){
        List<Board> boardList = boardRepository.findAllByMemberId(id);
        return boardList;
    }


 */