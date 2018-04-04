package sktp.tmc.service;

import sktp.tmc.service.dto.MarkerDTO;
import java.util.List;

/**
 * Service Interface for managing Marker.
 */
public interface MarkerService {

    /**
     * Save a marker.
     *
     * @param markerDTO the entity to save
     * @return the persisted entity
     */
    MarkerDTO save(MarkerDTO markerDTO);

    /**
     * Get all the markers.
     *
     * @return the list of entities
     */
    List<MarkerDTO> findAll();

    /**
     * Get the "id" marker.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MarkerDTO findOne(Long id);

    /**
     * Delete the "id" marker.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
