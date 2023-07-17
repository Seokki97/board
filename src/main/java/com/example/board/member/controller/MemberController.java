package com.example.board.member.controller;

import com.example.board.member.dto.requestDto.MemberRequest;
import com.example.board.member.dto.responseDto.MemberResponse;
import com.example.board.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveMember(@RequestBody MemberRequest memberRequest) {
        memberService.saveMemberProfile(memberRequest);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/find/{memberId}")
    public ResponseEntity<MemberResponse> findMemberById(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(memberService.findMemberById(memberId));
    }

    @GetMapping("/find")
    public ResponseEntity<MemberResponse> findMemberByNickname(@RequestParam("nickname") String nickname){
        return ResponseEntity.ok(memberService.findMemberByNickname(nickname));
    }
}
