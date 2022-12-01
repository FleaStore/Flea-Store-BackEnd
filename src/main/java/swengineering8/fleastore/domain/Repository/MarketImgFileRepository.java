package swengineering8.fleastore.domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swengineering8.fleastore.domain.MarketImgFile;

@Repository
public interface MarketImgFileRepository extends JpaRepository<MarketImgFile, Long> {
    @Override
    void deleteById(Long id);
}
