package com.example.board.member.service;

import com.example.board.member.domain.Member;
import com.example.board.member.dto.requestDto.MemberRequest;
import com.example.board.member.dto.responseDto.MemberResponse;
import com.example.board.member.exception.MemberNotFoundException;
import com.example.board.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
//@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void saveMemberProfile(MemberRequest memberRequest) {
        Member member = Member.builder()
                .email(memberRequest.getEmail())
                .nickname(memberRequest.getNickname())
                .build();

        memberRepository.save(member);
    }

    public MemberResponse findMemberById(Long id) {
        Member member = memberRepository.findByMemberId(id).orElseThrow(MemberNotFoundException::new);

        return MemberResponse.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }

    public MemberResponse findMemberByNickname(String nickname){
        Member member = memberRepository.findByNickname(nickname).orElseThrow(MemberNotFoundException::new);

        return MemberResponse.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }

}
