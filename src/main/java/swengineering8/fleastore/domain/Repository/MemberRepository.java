package swengineering8.fleastore.domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swengineering8.fleastore.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
