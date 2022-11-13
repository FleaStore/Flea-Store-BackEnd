package swengineering8.fleastore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swengineering8.fleastore.domain.Repository.AuthCodeRepository;
import swengineering8.fleastore.domain.Repository.MemberRepository;
import swengineering8.fleastore.dto.LoginDto;
import swengineering8.fleastore.dto.Response;
import swengineering8.fleastore.dto.MemberDto;
import swengineering8.fleastore.dto.TokenDto;
import swengineering8.fleastore.util.TokenProvider;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final AuthCodeRepository authCodeRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final Response response;

    public boolean signUp(MemberDto memberDto) {

        memberRepository.save(memberDto.toEntity());
        return true;
    }

    public ResponseEntity<?> login(LoginDto loginDto) {

        /* Login id/pw를 기반으로 Authentication 객체 생성 */
        /* 이때 authentication는 인증 여부를 확인하는 authenticated 값이 false */
        UsernamePasswordAuthenticationToken authenticationToken = loginDto.toAuthentication();

        /* 실제 검증(사용자 비밀번호 체크)이 이루어지는 부분
         * authenticate 메서드가 실행될 때 CustomUserDetailService에서 만든 loadUserByUserName 메서드 실행
         */

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        /* 인증 정보를 기반으로 JWT 토큰 생성 */
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        return response.success(tokenDto, "로그인에 성공했습니다.", HttpStatus.OK);

    }

    public ResponseEntity<?> logout(TokenDto tokenDto) {

        return response.success();
    }

    public ResponseEntity<?> checkEmail(String email) {

        memberRepository.existsByEmail(email);
        return response.success();
    }

    public ResponseEntity<?> checkNickname(String nickname) {

        memberRepository.existsByNickname(nickname);

        return response.success();
    }

    public ResponseEntity<?> checkAuthentication(Integer number){

        memberRepository.existsByNumber(number);
        return response.success();
    }

    public ResponseEntity<?> emailAuthentication(String email) {
        return response.success();
    }
}
