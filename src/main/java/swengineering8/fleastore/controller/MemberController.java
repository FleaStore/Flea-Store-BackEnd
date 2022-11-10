package swengineering8.fleastore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swengineering8.fleastore.dto.MemberDto;
import swengineering8.fleastore.dto.Response;
import swengineering8.fleastore.service.MemberService;

import java.security.Principal;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    /**
     * 유저 정보 가져오기
     */
    @GetMapping("/user")
    public ResponseEntity memberInfo(Principal principal) {

        long memberId = Long.parseLong(principal.getName());

        return memberService.memberInfo(memberId);
    }

    /**
     * 유저 정보 변경
     */
    @PutMapping("/user")
    public ResponseEntity<?> updateMember(@RequestBody MemberDto memberDto, Principal principal) {

        long memberId = Long.parseLong(principal.getName());

        return memberService.memberInfo(memberId);
    }

}
