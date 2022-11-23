package swengineering8.fleastore.util;

import org.apache.catalina.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Random;

public class AuthUtil {
    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);


    public static Long getCurrentMemberEmail(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication.getName() == null){
            logger.debug("Security Context에 인증 정보가 없습니다.");
            return null;
        }

        String username = null;
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            username = springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            username = (String) authentication.getPrincipal();
        }

        return Long.parseLong(username);
    }

    public static String generateCode() {
        Random random = new Random();
        String code = "";

        int numIndex = random.nextInt(99999 - 10000 + 1) + 10000;
        code +=numIndex;

        return code;
    }

    public static String getEmailContent(String code) {
        String content="";
        content+= "<div style='margin:100px;'>";
        content+= "<h1> 안녕하세요 Ari입니다. </h1>";
        content+= "<br>";
        content+= "<p>아래 코드를 인증 창으로 돌아가 입력해주세요<p>";
        content+= "<br>";
        content+= "<p>감사합니다!<p>";
        content+= "<br>";
        content+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        content+= "<h3 style='color:blue;'>인증 코드입니다.</h3>";
        content+= "<div style='font-size:130%'>";
        content+= "CODE : <strong>";
        content+= code+"</strong><div><br/> ";
        content+= "</div>";

        return content;
    }
}
