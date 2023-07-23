package com.example.board.member;

import com.example.board.member.domain.Member;
import com.example.board.member.dto.requestDto.MemberRequest;
import com.example.board.member.dto.responseDto.MemberResponse;
import com.example.board.member.repository.MemberRepository;
import com.example.board.member.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
//@Transactional //Test코드에 붙으면 임시공간 생겨서 종료 후 롤백
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

        when(memberRepository.save(any(Member.class))).thenReturn(mockMember);

        memberService.saveMemberProfile(memberRequest);

        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @DisplayName("id로 회원을 찾는다.")
    @Test
    void findMemberById(){

        Long memberId = 1L;

        MemberResponse memberResponse = MemberResponse.builder()
                .memberId(memberId)
                .email("테스팅")
                .nickname("동")
                .build();

        Member mockMember = Member.builder()
                .memberId(memberId)
                .email("테스팅")
                .nickname("동")
                .build();

        // Mock (memberRepository) 객체의 동작 정의
        when(memberRepository.findByMemberId(memberId)).thenReturn(Optional.of(mockMember));

       // 테스트하려는 메소드를 호출
        MemberResponse result = memberService.findMemberById(memberId);
//
//        // 예상한 동작을 검증
//        assertEquals(memberId, result.getMemberId());
//        assertEquals("테스팅", result.getEmail());
//        assertEquals("동", result.getNickname());

        // memberRepository의 findByMemberId 메소드가 올바른 파라미터로 호출되었는지 검증합니다.
        verify(memberRepository, times(1)).findByMemberId(memberId);
    }

    @DisplayName("닉네임으로 회원을 찾는다")
    @Test
    void findMemberByNickname(){
        String memberNickname = "동";

        MemberResponse memberResponse = MemberResponse.builder()
                .memberId(1L)
                .email("테스팅")
                .nickname("동")
                .build();

        Member mockMember = Member.builder()
                .memberId(1L)
                .email("테스팅")
                .nickname("동")
                .build();

        when(memberRepository.findByNickname(memberNickname)).thenReturn(Optional.of(mockMember));

        memberService.findMemberByNickname(memberNickname);

        verify(memberRepository,times(1)).findByNickname(memberNickname);
    }
}
