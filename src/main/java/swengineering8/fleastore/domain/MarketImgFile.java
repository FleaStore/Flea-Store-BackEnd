package swengineering8.fleastore.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "market_img_file")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MarketImgFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_id")
    private Long id;

    @Column
    private String imgUrl;

    @Column
    private String fileName;

    @Column
    private String originalName;

    @ManyToOne
    @JoinColumn(name = "market_id")
    private Market market;

    @Builder
    public MarketImgFile(Market market, String fileName, String originalName, String imgUrl){
        this.market = market;
        this.fileName = fileName;
        this.originalName = originalName;
        this.imgUrl = imgUrl;
    }
}
