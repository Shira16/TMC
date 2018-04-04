package sktp.tmc.service.impl;

import sktp.tmc.service.RouteOneService;
import sktp.tmc.domain.RouteOne;
import sktp.tmc.repository.RouteOneRepository;
import sktp.tmc.service.dto.RouteOneDTO;
import sktp.tmc.service.mapper.RouteOneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing RouteOne.
 */
@Service
@Transactional
public class RouteOneServiceImpl implements RouteOneService {

    private final Logger log = LoggerFactory.getLogger(RouteOneServiceImpl.class);

    private final RouteOneRepository routeOneRepository;

    private final RouteOneMapper routeOneMapper;

    public RouteOneServiceImpl(RouteOneRepository routeOneRepository, RouteOneMapper routeOneMapper) {
        this.routeOneRepository = routeOneRepository;
        this.routeOneMapper = routeOneMapper;
    }

    /**
     * Save a routeOne.
     *
     * @param routeOneDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RouteOneDTO save(RouteOneDTO routeOneDTO) {
        log.debug("Request to save RouteOne : {}", routeOneDTO);
        RouteOne routeOne = routeOneMapper.toEntity(routeOneDTO);
        routeOne = routeOneRepository.save(routeOne);
        return routeOneMapper.toDto(routeOne);
    }

    /**
     * Get all the routeOnes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RouteOneDTO> findAll() {
        log.debug("Request to get all RouteOnes");
        return routeOneRepository.findAll().stream()
            .map(routeOneMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one routeOne by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RouteOneDTO findOne(Long id) {
        log.debug("Request to get RouteOne : {}", id);
        RouteOne routeOne = routeOneRepository.findOne(id);
        return routeOneMapper.toDto(routeOne);
    }

    /**
     * Delete the routeOne by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RouteOne : {}", id);
        routeOneRepository.delete(id);
    }
}
