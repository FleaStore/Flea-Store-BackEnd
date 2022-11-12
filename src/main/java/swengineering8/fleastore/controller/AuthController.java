package swengineering8.fleastore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import swengineering8.fleastore.dto.LoginDto;
import swengineering8.fleastore.dto.Response;
import swengineering8.fleastore.dto.MemberDto;
import swengineering8.fleastore.dto.TokenDto;
import swengineering8.fleastore.service.AuthService;
import swengineering8.fleastore.util.Helper;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final Response response;

    /**
     * 회원가입
     */
    @PostMapping("/user/signup")
    public ResponseEntity<?> signUp(@RequestBody MemberDto memberDto, Errors errors) {

        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }

        if (authService.signUp(memberDto))
            return response.success("회원가입에 성공했습니다.");


        return response.fail("이미 사용 중인 이메일입니다.", HttpStatus.BAD_REQUEST);
    }

    /**
     * 로그인
     */
    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto, Errors errors) {

        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }

        return authService.login(loginDto);
    }

    /**
     * 로그아웃
     */
    public ResponseEntity<?> logout(@RequestBody TokenDto tokenDto) {

        return authService.logout(tokenDto);
    }

    /**
     * 이메일 중복 체크
     */
    @GetMapping("/users/email")
    public ResponseEntity<?> checkEmail(@RequestBody Map<String, String> param) {

        String email = param.get("email");

        return authService.checkEmail(email);
    }

    @GetMapping("/users/nickname")
    public ResponseEntity<?> checkNickname(@RequestBody Map<String, String> param) {

        String Nickname = param.get("Nickname");

        return authService.checkNickname(Nickname);
    }

    public ResponseEntity<?> checkAuthenticationNumber(@RequestBody Map<Integer, Integer> param) {

        Integer Number = param.get("Number");

        return authService.checkAuthenticationNumber(Number);
    }
}
