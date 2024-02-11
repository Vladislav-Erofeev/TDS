package vlad.erofeev.layerservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vlad.erofeev.layerservice.domain.entities.Code;

@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {
}
