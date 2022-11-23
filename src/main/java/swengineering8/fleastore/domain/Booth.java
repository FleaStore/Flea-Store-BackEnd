package swengineering8.fleastore.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "booth")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column
    private Category category;

    @Column
    @OneToMany()
    private List<Market> markets = new ArrayList<>();

    @Column
    @OneToMany(mappedBy = "booth", cascade = CascadeType.ALL)
    private List<BoothImgFile> imgFiles = new ArrayList<>();

}
