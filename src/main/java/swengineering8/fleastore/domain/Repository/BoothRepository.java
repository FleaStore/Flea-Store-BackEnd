package swengineering8.fleastore.domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swengineering8.fleastore.domain.Booth;
import swengineering8.fleastore.domain.Market;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoothRepository extends JpaRepository<Booth, Long> {
    List<Booth> findAllByMarket(Market market);

    @Override
    Optional<Booth> findById(Long aLong);
}
