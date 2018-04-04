package sktp.tmc.service;

import sktp.tmc.service.dto.RouteOneDTO;
import java.util.List;

/**
 * Service Interface for managing RouteOne.
 */
public interface RouteOneService {

    /**
     * Save a routeOne.
     *
     * @param routeOneDTO the entity to save
     * @return the persisted entity
     */
    RouteOneDTO save(RouteOneDTO routeOneDTO);

    /**
     * Get all the routeOnes.
     *
     * @return the list of entities
     */
    List<RouteOneDTO> findAll();

    /**
     * Get the "id" routeOne.
     *
     * @param id the id of the entity
     * @return the entity
     */
    RouteOneDTO findOne(Long id);

    /**
     * Delete the "id" routeOne.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
