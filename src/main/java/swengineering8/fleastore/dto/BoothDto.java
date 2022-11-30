package swengineering8.fleastore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import swengineering8.fleastore.domain.Category;

import java.util.List;

@AllArgsConstructor
@Getter
public class
BoothDto {
    private Long BoothId;

    private String name;

    private String info;

    private Category category;

    private List<String> existingImages;

    public void setBoothId(Long boothId) {
        BoothId = boothId;
    }

}
