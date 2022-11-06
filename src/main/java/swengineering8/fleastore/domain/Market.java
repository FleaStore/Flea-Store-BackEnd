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

    @Column
    @OneToMany(mappedBy = "like", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @Column
    @OneToMany(mappedBy = "market_img_file", cascade = CascadeType.ALL)
    private List<MarketImgFile> imgFiles = new ArrayList<>();

    @Column
    @OneToMany(mappedBy = "booth", cascade = CascadeType.ALL)
    private List<Booth> booths = new ArrayList<>();

}
