package com.example.board.board.repository;

import com.example.board.board.domain.Board;
import com.example.board.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

}
