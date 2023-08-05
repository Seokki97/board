package com.example.board.board.service.modify;

import com.example.board.board.domain.Board;
import com.example.board.board.dto.requestDto.BoardRequest;
import com.example.board.board.dto.responseDto.UpdateBoardResponse;
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

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

//Todo : 게시판 수정 테스트
public class BoardModifyTest {

    @InjectMocks
    private BoardService boardService;

    @Mock
    private BoardRepository boardRepository;

    @Test
    @DisplayName("게시판 수정 (정상)")
    void UpdateBoardTest(){
        Long memberId = 1L;
        Long boardId = 123L;

        BoardRequest mockBoardRequest = BoardRequest.builder()
                .boardId(1L)
                .title("수정")
                .content("잘 되었슴")
                .build();

        Member member = mock(Member.class);
        when(member.getMemberId()).thenReturn(memberId);

        Board board = mock(Board.class);
        when(board.getBoardId()).thenReturn(boardId);
        when(board.getMember()).thenReturn(member);

        LocalDateTime now = LocalDateTime.now();

//        when(board.update("수정","잘 되었슴",now)).thenReturn()
    }
}

/*

    @Transactional
    public UpdateBoardResponse modifyPost(Long boardId, Long memberId, BoardRequest boardRequest){
        if(!boardRepository.existsById(boardId)){
            throw new BoardNotFoundException();
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
 */