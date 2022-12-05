package swengineering8.fleastore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SimpleFavoriteDto {

    private Long id;
    private String nickname;
    private String marketName;
    private String image;


}
