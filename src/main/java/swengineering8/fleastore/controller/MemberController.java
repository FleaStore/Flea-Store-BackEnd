package swengineering8.fleastore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import swengineering8.fleastore.domain.Authority;
import swengineering8.fleastore.dto.MemberDto;
import swengineering8.fleastore.dto.Response;
import swengineering8.fleastore.service.MemberService;
import swengineering8.fleastore.util.Helper;

import java.security.Principal;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {

    private final MemberService memberService;
    private final Response response;

    /**
     * 유저 정보 가져오기 Principal : JWT Token의 담겨있는 정보를
     */
    @GetMapping("")
    public ResponseEntity memberInfo(Principal principal) {

        long memberId = Long.parseLong(principal.getName());

        return memberService.memberInfo(memberId);
    }

    /**
     * 유저 정보 변경
     */
    @PutMapping("")
    public ResponseEntity<?> updateMember(@RequestBody MemberDto memberDto, Principal principal) {
        Long memberId = Long.parseLong(principal.getName());
        return memberService.updateMember(memberDto, memberId);
    }

    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> param, Principal principal,Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        long memberId = Long.parseLong(principal.getName());
        String newPassword = param.get("newPassword");
        return memberService.changePassword(memberId, newPassword);
    }

    @PostMapping("/like")
    public ResponseEntity<?> toggleLike(@RequestParam Long marketId, Principal principal) {
        Long memberId = Long.parseLong(principal.getName());

        return memberService.toggleLike(marketId, memberId);
    }

    /**
     * 유저 권한 변경
     */
    @PutMapping("/auth")
    public ResponseEntity<?> changeAuthority(@RequestParam Authority authority, Principal principal){
        Long memberId = Long.parseLong(principal.getName());
        return memberService.changeAuthority(memberId,authority);
    }



}
