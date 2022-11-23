package swengineering8.fleastore.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "market")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private LocalDateTime start_date;

    @Column
    private LocalDateTime end_date;

    @Column
    private String info;

    @Column
    private String related_url;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column
    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL)
    private List<Favorite> favorites = new ArrayList<>();

    @Column
    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL)
    private List<MarketImgFile> imgFiles = new ArrayList<>();


}
