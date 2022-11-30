package swengineering8.fleastore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import swengineering8.fleastore.domain.Authority;
import swengineering8.fleastore.domain.Member;
import swengineering8.fleastore.domain.Repository.MemberRepository;
import swengineering8.fleastore.dto.LoginDto;
import swengineering8.fleastore.dto.Response;
import swengineering8.fleastore.dto.MemberDto;
import swengineering8.fleastore.dto.TokenDto;
import swengineering8.fleastore.jwt.TokenProvider;
import swengineering8.fleastore.util.AuthUtil;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final Response response;
    private final RedisTemplate redisTemplate;
    private final JavaMailSender javaMailSender;

    public boolean signUp(MemberDto memberDto) {

        if (memberRepository.existsByEmail(memberDto.getEmail())) {
            return false;
        }

        /* SignupDto를 통해 추가할 Member 객체 생성 및 저장 */
        Member member = memberDto.toEntity(passwordEncoder);
        member.setAuthority(Authority.ROLE_USER);
        memberRepository.save(member);

        return true;
    }

    public ResponseEntity<?> login(LoginDto loginDto) {

        Member member = memberRepository.findByEmail(loginDto.getEmail()).orElse(null);

        if (member == null){
            return response.fail("아이디 또는 비밀번호를 잘못 입력하셨습니다.", HttpStatus.BAD_REQUEST);
        }
        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())){
            return response.fail("아이디 또는 비밀번호를 잘못 입력하셨습니다.", HttpStatus.BAD_REQUEST);
        }
        log.info("1");

        /* Login id/pw를 기반으로 Authentication 객체 생성 */
        /* 이때 authentication는 인증 여부를 확인하는 authenticated 값이 false */

        UsernamePasswordAuthenticationToken authenticationToken = loginDto.toAuthentication();

        log.info("2");
        /* 실제 검증(사용자 비밀번호 체크)이 이루어지는 부분
         * authenticate 메서드가 실행될 때 CustomUserDetailService에서 만든 loadUserByUserName 메서드 실행
         */
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        log.info("3");

        /* 인증 정보를 기반으로 JWT 토큰 생성 */
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        /* user/owner에 따라 닉네임or가게이름 tokenDto에 추가 */
        tokenDto.setInfo(member.getNickname()); // 닉네임


        return response.success(tokenDto, "로그인 성공!", HttpStatus.OK);

    }

    public ResponseEntity<?> logout(String accessToken) {

        //1. Access Token 검증
        if (!tokenProvider.validateToken(accessToken)) {
            return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }

        //2. Access Token에서 User id를 가져옴
        Authentication authentication = tokenProvider.getAuthentication(accessToken);

        //3. 해당 access token 유효시간 가지고 와서 BlackList로 저장
        Long expiration = tokenProvider.getExpiration(accessToken);
        redisTemplate.opsForValue()
                .set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);

        return response.success("로그아웃 되었습니다");
    }

    public ResponseEntity<?> checkEmail(String email) {

        boolean result = memberRepository.existsByEmail(email);

        if (!result) {
            return response.success("사용 가능한 이메일입니다.");
        } else {
            return response.fail("이미 존재하는 이메일입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> checkNickname(String nickname) {

        boolean result = memberRepository.existsByNickname(nickname);

        if (!result) {
            return response.success("사용 가능한 닉네임입니다.");
        } else {
            return response.fail("이미 존재하는 닉네임입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> checkAuthentication(String code){

        String result = (String) redisTemplate.opsForValue().get(code); //redis에 입력한 코드가 있는지 확인

        /* 입력한 코드가 존재한다면 성공 메세지 반환 */
        if (result != null) {
            //이메일 확인용. 나중에 삭제해야함
            log.info("인증코드 해당 이메일: {}", result);
            return response.success("메일 인증에 성공했습니다.");
        }

        return response.fail("인증코드가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> emailAuthentication(String email) {
        String code = AuthUtil.generateCode(); // 가입코드 생성

        String subject = "벼룩창고 인증을 위한 인증번호입니다.";
        String content = AuthUtil.getEmailContent(code);

        /* JavaMailSender를 이용해 인증코드 전송 */
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setTo(email);
            helper.setFrom("ckd0325@ajou.ac.kr");
            helper.setSubject(subject);
            helper.setText(content, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        log.info("인증코드: {}", code); //코드 확인용. 나중에 삭제해야함

        /* redis에 인증코드 5분간 보관 */
        redisTemplate.opsForValue()
                .set(code, email, 5*60000, TimeUnit.MILLISECONDS);

        return response.success();
    }
}
