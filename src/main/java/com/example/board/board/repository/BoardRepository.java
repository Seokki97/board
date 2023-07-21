package com.example.board.board.repository;

import com.example.board.board.domain.Board;
import com.example.board.board.dto.requestDto.BoardRequest;
import com.example.board.board.dto.responseDto.BoardResponse;
import com.example.board.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select b from board b where b.member.memberId = ?1") //?1 -> parameter 첫번째 자리에 있는걸 넣겠다는 뜻.
    List<Board> findAllByMemberId(Long memberId);

    @Query("select b.boardId from board b where b.title = ?1")
    Long findByTitle(String title);

}






//    @Query(value= "UPDATE board b SET b.title = :#{#requestInfo.title}, b.content = :#{#requestInfo.content} " +
//            "WHERE b.board_id = :#{#boardId} AND b.member_id = :#{#memberId}", nativeQuery = true)
//    @Modifying
//    @Transactional
//    void modifyTitleAndContent(@Param(value = "boardId")Long boardId,
//                                @Param(value="memberId")Long memberId,
//                                @Param(value = "requestInfo")BoardRequest boardRequest);