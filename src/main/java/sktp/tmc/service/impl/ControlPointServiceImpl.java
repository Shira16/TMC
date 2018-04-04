package sktp.tmc.service.impl;

import sktp.tmc.service.ControlPointService;
import sktp.tmc.domain.ControlPoint;
import sktp.tmc.repository.ControlPointRepository;
import sktp.tmc.service.dto.ControlPointDTO;
import sktp.tmc.service.mapper.ControlPointMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ControlPoint.
 */
@Service
@Transactional
public class ControlPointServiceImpl implements ControlPointService {

    private final Logger log = LoggerFactory.getLogger(ControlPointServiceImpl.class);

    private final ControlPointRepository controlPointRepository;

    private final ControlPointMapper controlPointMapper;

    public ControlPointServiceImpl(ControlPointRepository controlPointRepository, ControlPointMapper controlPointMapper) {
        this.controlPointRepository = controlPointRepository;
        this.controlPointMapper = controlPointMapper;
    }

    /**
     * Save a controlPoint.
     *
     * @param controlPointDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ControlPointDTO save(ControlPointDTO controlPointDTO) {
        log.debug("Request to save ControlPoint : {}", controlPointDTO);
        ControlPoint controlPoint = controlPointMapper.toEntity(controlPointDTO);
        controlPoint = controlPointRepository.save(controlPoint);
        return controlPointMapper.toDto(controlPoint);
    }

    /**
     * Get all the controlPoints.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ControlPointDTO> findAll() {
        log.debug("Request to get all ControlPoints");
        return controlPointRepository.findAll().stream()
            .map(controlPointMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one controlPoint by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ControlPointDTO findOne(Long id) {
        log.debug("Request to get ControlPoint : {}", id);
        ControlPoint controlPoint = controlPointRepository.findOne(id);
        return controlPointMapper.toDto(controlPoint);
    }

    /**
     * Delete the controlPoint by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ControlPoint : {}", id);
        controlPointRepository.delete(id);
    }
}
