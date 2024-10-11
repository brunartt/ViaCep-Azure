package unicap.si.microservicos.api.buscaCep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicap.si.microservicos.api.buscaCep.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
