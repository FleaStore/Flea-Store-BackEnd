package swengineering8.fleastore.domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swengineering8.fleastore.domain.Favorite;
import swengineering8.fleastore.domain.Market;
import swengineering8.fleastore.domain.Member;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findFavoriteByMemberAndMarket(Member member, Market market);
}
