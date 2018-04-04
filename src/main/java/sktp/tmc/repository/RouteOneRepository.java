package sktp.tmc.repository;

import sktp.tmc.domain.RouteOne;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RouteOne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RouteOneRepository extends JpaRepository<RouteOne, Long> {

}
