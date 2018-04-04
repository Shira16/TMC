package sktp.tmc.service.impl;

import sktp.tmc.service.AssociatedPointService;
import sktp.tmc.domain.AssociatedPoint;
import sktp.tmc.repository.AssociatedPointRepository;
import sktp.tmc.service.dto.AssociatedPointDTO;
import sktp.tmc.service.mapper.AssociatedPointMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing AssociatedPoint.
 */
@Service
@Transactional
public class AssociatedPointServiceImpl implements AssociatedPointService {

    private final Logger log = LoggerFactory.getLogger(AssociatedPointServiceImpl.class);

    private final AssociatedPointRepository associatedPointRepository;

    private final AssociatedPointMapper associatedPointMapper;

    public AssociatedPointServiceImpl(AssociatedPointRepository associatedPointRepository, AssociatedPointMapper associatedPointMapper) {
        this.associatedPointRepository = associatedPointRepository;
        this.associatedPointMapper = associatedPointMapper;
    }

    /**
     * Save a associatedPoint.
     *
     * @param associatedPointDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AssociatedPointDTO save(AssociatedPointDTO associatedPointDTO) {
        log.debug("Request to save AssociatedPoint : {}", associatedPointDTO);
        AssociatedPoint associatedPoint = associatedPointMapper.toEntity(associatedPointDTO);
        associatedPoint = associatedPointRepository.save(associatedPoint);
        return associatedPointMapper.toDto(associatedPoint);
    }

    /**
     * Get all the associatedPoints.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AssociatedPointDTO> findAll() {
        log.debug("Request to get all AssociatedPoints");
        return associatedPointRepository.findAll().stream()
            .map(associatedPointMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one associatedPoint by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AssociatedPointDTO findOne(Long id) {
        log.debug("Request to get AssociatedPoint : {}", id);
        AssociatedPoint associatedPoint = associatedPointRepository.findOne(id);
        return associatedPointMapper.toDto(associatedPoint);
    }

    /**
     * Delete the associatedPoint by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AssociatedPoint : {}", id);
        associatedPointRepository.delete(id);
    }
}
