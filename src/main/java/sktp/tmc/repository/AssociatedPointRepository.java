package sktp.tmc.repository;

import sktp.tmc.domain.AssociatedPoint;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AssociatedPoint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssociatedPointRepository extends JpaRepository<AssociatedPoint, Long> {

}
