package swengineering8.fleastore.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "auth_code")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_code_id")
    private Long id;

    @Column
    private String authCode;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
