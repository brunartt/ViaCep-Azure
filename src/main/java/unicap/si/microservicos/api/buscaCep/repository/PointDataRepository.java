package unicap.si.microservicos.api.buscaCep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicap.si.microservicos.api.buscaCep.model.PointData;

import java.util.Optional;

@Repository
public interface PointDataRepository extends JpaRepository<PointData, Long> {

    Optional<PointData> findByCep(String cep);
}
