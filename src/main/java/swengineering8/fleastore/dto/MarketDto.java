package swengineering8.fleastore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.annotations.ApiIgnore;
import swengineering8.fleastore.domain.Market;
import swengineering8.fleastore.domain.MarketImgFile;
import swengineering8.fleastore.domain.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@ApiModel(value = "마켓 조회,추가 정보", description = "마켓 조회,추가에 필요한 데이터")
@Getter
@ToString
public class MarketDto {

    private Long marketId;
    @ApiModelProperty(value = "마켓 이름", required = true, example = "Flea store")
    private String name;
    @ApiModelProperty(value = "마켓 주", required = true, example = "경기도 수원시")
    private String address;

    @ApiModelProperty(value = "시작날짜", required = true, example = "2022/12/01")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "/Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate startDate;

    @ApiModelProperty(value = "마감날짜", required = true, example = "2022/12/01")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "/Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate endDate;

    @ApiModelProperty(value = "마켓 설명", required = true, example = "이러이러한 마켓입니다")
    private String info;

    @ApiModelProperty(value = "SNS 주소", required = true, example = "www.naver.com")
    private String relatedUrl;

    private List<String> existingImages;

    public void setMarketId(Long marketId){
        marketId = marketId;
    }


    @Builder
    public MarketDto(Long marketId, String name, String address, LocalDate startDate,
                     LocalDate endDate, String info, String relatedUrl, List<String> existingImages) {
        this.marketId = marketId;
        this.name = name;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.info = info;
        this.relatedUrl = relatedUrl;
        this.existingImages = existingImages;
    }
}
