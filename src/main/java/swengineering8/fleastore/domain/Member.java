package swengineering8.fleastore.domain;

import lombok.*;
import swengineering8.fleastore.dto.MemberDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Table(name = "member")
@Entity
@NoArgsConstructor
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column
    private String password;

    @Column
    @Setter
    @Enumerated(EnumType.STRING) // enum 이름을 DB에 저장
    private Authority authority;

    @Column
    private String email;

    @Column
    private String nickname;

    @Column
    private String name;

    @Column
    private String phoneNumber;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Favorite> favorites = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Market> markets = new ArrayList<>();

    @Builder
    public Member(Long id, String password, String email, Authority authority, String nickname, String name, String phoneNumber){
        this.email = email;
        this.authority = authority;
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public void changeInfo(String nickname, String name, String phoneNumber) {
        this.nickname = nickname;
        this.name = name;
        this. phoneNumber = phoneNumber;
    }

    public boolean isFavoriteMarket(Market market){
        return favorites.stream().map(Favorite :: getMarket)
                .anyMatch(findMarket -> findMarket.getId() == market.getId());
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
