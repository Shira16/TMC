package sktp.tmc.repository;

import sktp.tmc.domain.ControlPoint;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ControlPoint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ControlPointRepository extends JpaRepository<ControlPoint, Long> {

}
