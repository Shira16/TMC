package sktp.tmc.repository;

import sktp.tmc.domain.Marker;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Marker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarkerRepository extends JpaRepository<Marker, Long> {

}
