package sktp.tmc.service;

import sktp.tmc.service.dto.ControlPointDTO;
import java.util.List;

/**
 * Service Interface for managing ControlPoint.
 */
public interface ControlPointService {

    /**
     * Save a controlPoint.
     *
     * @param controlPointDTO the entity to save
     * @return the persisted entity
     */
    ControlPointDTO save(ControlPointDTO controlPointDTO);

    /**
     * Get all the controlPoints.
     *
     * @return the list of entities
     */
    List<ControlPointDTO> findAll();

    /**
     * Get the "id" controlPoint.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ControlPointDTO findOne(Long id);

    /**
     * Delete the "id" controlPoint.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
