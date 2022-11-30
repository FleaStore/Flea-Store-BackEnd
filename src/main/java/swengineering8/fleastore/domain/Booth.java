package swengineering8.fleastore.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "booth")
@Entity
@NoArgsConstructor
@Getter
public class Booth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booth_id")
    private Long id;

    @Column
    private String name;

    @Column
    private String info;

    @Enumerated(EnumType.STRING)
    @Column
    private Category category;

    @ManyToOne
    @JoinColumn(name = "market_id")
    private Market market;

    @Column
    @OneToMany(mappedBy = "booth", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoothImgFile> imgFiles = new ArrayList<>();

    public void addImgFile(BoothImgFile boothImgFile) {
        this.imgFiles.add(boothImgFile);
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public void updateBooth(String name, String info, Category category) {
        this.name = name;
        this.info = info;
        this.category = category;
    }

}
