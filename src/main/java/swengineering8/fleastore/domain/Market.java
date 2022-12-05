package swengineering8.fleastore.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "market")
@Entity
@NoArgsConstructor
@Getter
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "market_id")
    private Long id;

    @Column
    private String name;

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

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "member_id")
    private Member member;

    @Column
    @JsonBackReference
    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL)
    private List<Favorite> favorites = new ArrayList<>();

    @Column
    @JsonBackReference
    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MarketImgFile> imgFiles = new ArrayList<>();

    public void setUser(Member member){
        this.member = member;
    }

    public void addImgFile(MarketImgFile marketImgFile){
        this.imgFiles.add(marketImgFile);
    }
    public void updateMarket(String name, String info, String address, LocalDate startDate, LocalDate endDate, String relatedUrl){
        this.name = name;
        this.info = info;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.relatedUrl = relatedUrl;
    }

    @Builder
    public Market(String name, String address, String info, String relatedUrl, LocalDate startDate, LocalDate endDate, Member member){
        this.name = name;
        this.address = address;
        this.info = info;
        this.relatedUrl = relatedUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.member = member;
    }

}
