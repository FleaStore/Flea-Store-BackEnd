package swengineering8.fleastore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import swengineering8.fleastore.domain.Member;
import swengineering8.fleastore.domain.PermissionRequest;

import javax.persistence.Column;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@ToString
public class PermissionRequestDto {

    private String marketName;

    private String address;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "Asia/Seoul")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "Asia/Seoul")
    private LocalDate endDate;

    private String info;

    private String relatedUrl;

    private String reason;

    @Builder
    public PermissionRequestDto(String marketName, String address, LocalDate startDate,
                             LocalDate endDate, String info, String relatedUrl, String reason) {
        this.marketName = marketName;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.info = info;
        this.relatedUrl = relatedUrl;
        this.reason = reason;
    }

    public PermissionRequest toEntity() {
        return PermissionRequest.builder()
                .marketName(this.marketName)
                .address(this.address)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .info(this.info)
                .relatedUrl(this.relatedUrl)
                .reason(this.reason)
                .build();
    }

}
