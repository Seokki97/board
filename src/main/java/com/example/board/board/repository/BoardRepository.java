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

    @Query("select u from board u where u.member.memberId = ?1") //?1 -> parameter 첫번째 자리에 있는걸 넣겠다는 뜻.
    List<Board> findAllByMemberId(Long id);
}
