package swengineering8.fleastore.domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swengineering8.fleastore.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
