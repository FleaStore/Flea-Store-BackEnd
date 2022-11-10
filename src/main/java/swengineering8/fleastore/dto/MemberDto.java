package swengineering8.fleastore.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class MemberDto {

    private String email;

    private String name;

    private String nickName;

    private String password;

    private String PhoneNumber;
}
