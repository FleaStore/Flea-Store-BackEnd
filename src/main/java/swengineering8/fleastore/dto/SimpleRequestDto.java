package swengineering8.fleastore.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class SimpleRequestDto {

    private Long requestId;

    private String name;

    private String email;

    private String phoneNumber;

    public SimpleRequestDto(Long requestId, String name, String email, String phoneNumber) {
        this.requestId = requestId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
