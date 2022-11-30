package swengineering8.fleastore.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "favorite")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "market_id")
    private Market market;

    public Favorite(Member member, Market market) {
        this.member = member;
        this.market = market;
    }
}
