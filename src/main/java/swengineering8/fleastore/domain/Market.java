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
    private LocalDate start_date;

    @Column
    private LocalDate end_date;

    @Column
    private String info;

    @Column
    private String related_url;

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
    public void updateMarket(String name, String info, String address, LocalDate start_date, LocalDate end_date, String related_url){
        this.name = name;
        this.info = info;
        this.address = address;
        this.start_date = start_date;
        this.end_date = end_date;
        this.related_url = related_url;
    }

//    @Builder
//    public Market(String name, String address, String info, String related_url, LocalDateTime start_date, LocalDateTime end_date, Member member){
//        this.name = name;
//        this.address = address;
//        this.info = info;
//        this.related_url = related_url;
//        this.start_date = start_date;
//        this.end_date = end_date;
//        this.member = member;
//    }

}
