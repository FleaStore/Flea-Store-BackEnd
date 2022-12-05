package swengineering8.fleastore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;


@ApiModel(value = "마켓 수정 정보", description = "마켓 수정에 필요한 데이터")
@Getter
@AllArgsConstructor
@ToString
public class MarketUpdateDto {

    @ApiModelProperty(value = "마켓 이름", required = true, example = "Flea store")
    private String name;
    @ApiModelProperty(value = "마켓 주", required = true, example = "경기도 수원시")
    private String address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "/Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate startDate;

    @ApiModelProperty(value = "마감날짜", required = true, example = "2022/12/01")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "Asia/Seoul")
    private LocalDate endDate;

    @ApiModelProperty(value = "마켓 설명", required = true, example = "이러이러한 마켓입니다")
    private String info;

    @ApiModelProperty(value = "SNS 주소", required = true, example = "www.naver.com")
    private String relatedUrl;

    //private List<String> existingImages;

}
