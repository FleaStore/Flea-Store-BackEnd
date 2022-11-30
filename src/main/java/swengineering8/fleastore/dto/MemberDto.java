package swengineering8.fleastore.dto;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import swengineering8.fleastore.domain.Member;




@Getter
@NoArgsConstructor
@ToString
public class MemberDto {

    private String email;

    private String name;

    private String nickname;

    private String password;

    private String phoneNumber;
    @Builder
    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .name(name)
                .nickname(nickname)
                .password(passwordEncoder.encode(password))
                .phoneNumber(phoneNumber)
                .build();
    }

    public MemberDto(String email, String name, String nickname, String phoneNumber) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }
}
