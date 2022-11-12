package swengineering8.fleastore.dto;

import lombok.*;
import swengineering8.fleastore.domain.Member;



@Getter
@ToString
public class MemberDto {

    private String email;

    private String name;

    private String nickname;

    private String password;

    private String phone_number;
    @Builder
    public Member toEntity() {
        return Member.builder()
                .email(email)
                .name(name)
                .nickname(nickname)
                .password(password)
                .phone_number(phone_number)
                .build();
    }
}
