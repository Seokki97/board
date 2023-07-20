package com.example.board.board.repository;

import com.example.board.board.domain.Board;
import com.example.board.board.dto.requestDto.BoardRequest;
import com.example.board.board.dto.responseDto.BoardResponse;
import com.example.board.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select b from board b where b.member.memberId = ?1") //?1 -> parameter 첫번째 자리에 있는걸 넣겠다는 뜻.
    List<Board> findAllByMemberId(Long memberId);

//    boolean existsByMember(Member member);
//
//    boolean existsByBoardId(Long boardId);

//    // 삭제.. 이렇게 쿼리문으로 처리는 안되나?
//    @Query("delete from board b where b.boardId=?1 and b.member.memberId=?2")
//    void deleteByBoardIdAndMemberId(Long boardId, Long memberId);
}
