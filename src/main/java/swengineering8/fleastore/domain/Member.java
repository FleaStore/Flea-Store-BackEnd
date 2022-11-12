package swengineering8.fleastore.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swengineering8.fleastore.service.MemberService;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Table(name = "member")
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column
    private String password;

    @Column
    @Enumerated(EnumType.STRING) // enum 이름을 DB에 저장
    private Authority authority;

    @Column
    private String email;

    @Column
    private String nickname;

    @Column
    private String name;

    @Column
    private String phone_number;

    @Column
    @OneToMany(mappedBy = "like", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @Column
    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL)
    private List<Market> markets = new ArrayList<>();

    @Builder
    public Member(Long id, String password, String email, Authority authority, String nickname, String name, String phone_number){
        this.email = email;
        this.authority = authority;
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.phone_number = phone_number;
    }
}
