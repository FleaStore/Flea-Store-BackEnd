package swengineering8.fleastore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    private String grantType;
    private String accessToken;
    private Long accessTokenExpireIn;
    private String authority;
    private String info;

    public void setInfo(String info) {
        this.info = info;
    }
}
