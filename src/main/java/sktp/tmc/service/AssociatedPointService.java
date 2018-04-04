package sktp.tmc.service;

import sktp.tmc.service.dto.AssociatedPointDTO;
import java.util.List;

/**
 * Service Interface for managing AssociatedPoint.
 */
public interface AssociatedPointService {

    /**
     * Save a associatedPoint.
     *
     * @param associatedPointDTO the entity to save
     * @return the persisted entity
     */
    AssociatedPointDTO save(AssociatedPointDTO associatedPointDTO);

    /**
     * Get all the associatedPoints.
     *
     * @return the list of entities
     */
    List<AssociatedPointDTO> findAll();

    /**
     * Get the "id" associatedPoint.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AssociatedPointDTO findOne(Long id);

    /**
     * Delete the "id" associatedPoint.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
