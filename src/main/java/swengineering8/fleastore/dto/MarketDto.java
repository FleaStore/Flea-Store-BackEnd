package swengineering8.fleastore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import swengineering8.fleastore.domain.Market;
import swengineering8.fleastore.domain.MarketImgFile;
import swengineering8.fleastore.domain.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class MarketDto {

    private Long MarketId;
    private String name;

    private String address;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "/Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate startDate;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "/Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate endDate;

    private String info;

    private String relatedUrl;

    private List<String> existingImages;

    public void setMarketId(Long marketId){
        MarketId = marketId;
    }


//    @Builder
//    public Market toEntity() {
//        return Market.builder()
//                .name(name)
//                .address(address)
//                .startDate(startDate)
//                .endDate(endDate)
//                .info(info)
//                .build();
//    }
}
