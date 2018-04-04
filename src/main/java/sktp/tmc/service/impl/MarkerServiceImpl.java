package sktp.tmc.service.impl;

import sktp.tmc.service.MarkerService;
import sktp.tmc.domain.Marker;
import sktp.tmc.repository.MarkerRepository;
import sktp.tmc.service.dto.MarkerDTO;
import sktp.tmc.service.mapper.MarkerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Marker.
 */
@Service
@Transactional
public class MarkerServiceImpl implements MarkerService {

    private final Logger log = LoggerFactory.getLogger(MarkerServiceImpl.class);

    private final MarkerRepository markerRepository;

    private final MarkerMapper markerMapper;

    public MarkerServiceImpl(MarkerRepository markerRepository, MarkerMapper markerMapper) {
        this.markerRepository = markerRepository;
        this.markerMapper = markerMapper;
    }

    /**
     * Save a marker.
     *
     * @param markerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MarkerDTO save(MarkerDTO markerDTO) {
        log.debug("Request to save Marker : {}", markerDTO);
        Marker marker = markerMapper.toEntity(markerDTO);
        marker = markerRepository.save(marker);
        return markerMapper.toDto(marker);
    }

    /**
     * Get all the markers.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MarkerDTO> findAll() {
        log.debug("Request to get all Markers");
        return markerRepository.findAll().stream()
            .map(markerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one marker by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MarkerDTO findOne(Long id) {
        log.debug("Request to get Marker : {}", id);
        Marker marker = markerRepository.findOne(id);
        return markerMapper.toDto(marker);
    }

    /**
     * Delete the marker by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Marker : {}", id);
        markerRepository.delete(id);
    }
}
