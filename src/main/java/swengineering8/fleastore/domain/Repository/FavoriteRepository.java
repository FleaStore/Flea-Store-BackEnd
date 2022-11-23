package swengineering8.fleastore.domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swengineering8.fleastore.domain.Favorite;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
