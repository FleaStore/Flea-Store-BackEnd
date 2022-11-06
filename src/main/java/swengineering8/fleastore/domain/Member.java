package swengineering8.fleastore.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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
    private String phone_number;

    @Column
    @OneToMany(mappedBy = "like", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @Column
    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL)
    private List<Market> markets = new ArrayList<>();


}
