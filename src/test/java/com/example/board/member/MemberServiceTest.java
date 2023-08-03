package com.example.board.member;

import com.example.board.member.domain.Member;
import com.example.board.member.dto.requestDto.MemberRequest;
import com.example.board.member.dto.responseDto.MemberResponse;
import com.example.board.member.repository.MemberRepository;
import com.example.board.member.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //
public class MemberServiceTest {

    @InjectMocks
    //@InjectMocks: 이 어노테이션은 MemberService 클래스의 인스턴스를 자동으로 생성하고,
    // 해당 인스턴스 내에서 @Mock으로 선언된 Mock 객체들을 주입
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

//    @BeforeEach -> 필요없음.
//    //각 테스트 메서드가 실행되기 전에 초기화 작업을 수행, 여기서는 Mockito를 초기화하는데 사용.
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

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

        /*
        여기서 사용된 any(Member.class)는 Mockito의 any 메서드로,
        어떤 Member 객체를 인자로 받아도 모두 mockMember 객체를 반환하도록 지정.
        이렇게 함으로써 특정 인자에 의존하지 않고 모든 Member 객체에 대해 테스트를 수행할 수 있음
         */
        when(memberRepository.save(any(Member.class))).thenReturn(mockMember);

        memberService.saveMemberProfile(memberRequest);

        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @DisplayName("id로 회원을 찾는다.")
    @Test
    void findMemberById(){

        //given
        Long memberId = 1L;

        Member mockMember = Member.builder()
                .memberId(memberId)
                .email("테스팅")
                .nickname("동")
                .build();

        // Mock (memberRepository) 객체의 동작 정의
        // memberRepository의 findByMemberId 메서드가 memberId를 인자로 호출할 때 mockMember 객체를 반환하도록 지정
        when(memberRepository.findByMemberId(memberId)).thenReturn(Optional.of(mockMember));

        //when
        // 테스트하려는 memberService의 findMemberById 메서드를 호출
        MemberResponse result = memberService.findMemberById(memberId);

        // then
        // memberRepository의 findByMemberId 메소드가 올바른 파라미터로 호출되었는지 검증합니다.
        verify(memberRepository, times(1)).findByMemberId(memberId);

        //memberResponse 객체의 값들이 예상한 값과 일치하는지를 검증
        assertEquals(memberId, result.getMemberId());
        assertEquals("테스팅", result.getEmail());
        assertEquals("동", result.getNickname());
    }

    @DisplayName("닉네임으로 회원을 찾는다")
    @Test
    void findMemberByNickname(){
        String memberNickname = "동";

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
