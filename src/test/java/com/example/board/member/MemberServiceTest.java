package com.example.board.member;

import com.example.board.member.domain.Member;
import com.example.board.member.dto.requestDto.MemberRequest;
import com.example.board.member.repository.MemberRepository;
import com.example.board.member.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;


    @DisplayName("회원을 DB에 저장한다.")
    @Test
    void saveMember(){
        MemberRequest memberRequest = MemberRequest.builder()
                .email("테스팅")
                .nickname("석")
                .build();

        Member mockMember = Member.builder()
                .memberId(1L)
                .email("테스팅")
                .nickname("석")
                .build();

        Mockito.when(memberRepository.save(any(Member.class))).thenReturn(mockMember);

        memberService.saveMemberProfile(memberRequest);

        Mockito.verify(memberRepository, Mockito.times(1)).save(any(Member.class));
    }

    @DisplayName("id로 회원을 찾는다.")
    @Test
    void findMemberById(){

    }

    @DisplayName("닉네임으로 회원을 찾는다")
    @Test
    void findMemberByNickname(){

    }
}
