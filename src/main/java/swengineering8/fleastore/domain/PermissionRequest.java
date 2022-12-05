package swengineering8.fleastore.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.yaml.snakeyaml.error.Mark;
import swengineering8.fleastore.dto.PermissionRequestDto;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "permission_request")
@Entity
@NoArgsConstructor
@Getter
public class PermissionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;

    @Column
    private String marketName;

    @Column
    private String address;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private String info;

    @Column
    private String relatedUrl;

    @Column
    private String reason;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public PermissionRequest(Long id, String marketName, String address, LocalDate startDate,
                             LocalDate endDate, String info, String relatedUrl, String reason, Member member) {
        this.id = id;
        this.marketName = marketName;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.info = info;
        this.relatedUrl = relatedUrl;
        this.reason = reason;
        this.member = member;
    }

    public PermissionRequestDto toDto() {
        return PermissionRequestDto.builder()
                .marketName(this.marketName)
                .address(this.address)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .info(this.info)
                .relatedUrl(this.relatedUrl)
                .reason(this.reason)
                .build();
    }

    public Market toMarket() {
        return Market.builder()
                .name(this.marketName)
                .address(this.address)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .info(this.info)
                .relatedUrl(this.relatedUrl)
                .member(this.member)
                .build();
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
