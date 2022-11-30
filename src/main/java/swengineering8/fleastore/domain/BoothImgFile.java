package swengineering8.fleastore.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "booth_img_file")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BoothImgFile {

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
    @JoinColumn(name = "booth_id")
    private Booth booth;

    @Builder
    public BoothImgFile(Booth booth, String fileName, String originalName, String imgUrl){
        this.booth = booth;
        this.fileName = fileName;
        this.originalName = originalName;
        this.imgUrl = imgUrl;
    }

}
