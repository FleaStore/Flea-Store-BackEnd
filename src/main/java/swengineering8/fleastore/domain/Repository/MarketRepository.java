package swengineering8.fleastore.domain.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swengineering8.fleastore.domain.Market;

import java.util.Optional;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {

    //Page<Market> findAll(Pageable pageable);
}
