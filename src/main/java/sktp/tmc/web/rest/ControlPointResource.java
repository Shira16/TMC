package sktp.tmc.web.rest;

import com.codahale.metrics.annotation.Timed;
import sktp.tmc.service.ControlPointService;
import sktp.tmc.web.rest.errors.BadRequestAlertException;
import sktp.tmc.web.rest.util.HeaderUtil;
import sktp.tmc.service.dto.ControlPointDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ControlPoint.
 */
@RestController
@RequestMapping("/api")
public class ControlPointResource {

    private final Logger log = LoggerFactory.getLogger(ControlPointResource.class);

    private static final String ENTITY_NAME = "controlPoint";

    private final ControlPointService controlPointService;

    public ControlPointResource(ControlPointService controlPointService) {
        this.controlPointService = controlPointService;
    }

    /**
     * POST  /control-points : Create a new controlPoint.
     *
     * @param controlPointDTO the controlPointDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new controlPointDTO, or with status 400 (Bad Request) if the controlPoint has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/control-points")
    @Timed
    public ResponseEntity<ControlPointDTO> createControlPoint(@RequestBody ControlPointDTO controlPointDTO) throws URISyntaxException {
        log.debug("REST request to save ControlPoint : {}", controlPointDTO);
        if (controlPointDTO.getId() != null) {
            throw new BadRequestAlertException("A new controlPoint cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ControlPointDTO result = controlPointService.save(controlPointDTO);
        return ResponseEntity.created(new URI("/api/control-points/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /control-points : Updates an existing controlPoint.
     *
     * @param controlPointDTO the controlPointDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated controlPointDTO,
     * or with status 400 (Bad Request) if the controlPointDTO is not valid,
     * or with status 500 (Internal Server Error) if the controlPointDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/control-points")
    @Timed
    public ResponseEntity<ControlPointDTO> updateControlPoint(@RequestBody ControlPointDTO controlPointDTO) throws URISyntaxException {
        log.debug("REST request to update ControlPoint : {}", controlPointDTO);
        if (controlPointDTO.getId() == null) {
            return createControlPoint(controlPointDTO);
        }
        ControlPointDTO result = controlPointService.save(controlPointDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, controlPointDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /control-points : get all the controlPoints.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of controlPoints in body
     */
    @GetMapping("/control-points")
    @Timed
    public List<ControlPointDTO> getAllControlPoints() {
        log.debug("REST request to get all ControlPoints");
        return controlPointService.findAll();
        }

    /**
     * GET  /control-points/:id : get the "id" controlPoint.
     *
     * @param id the id of the controlPointDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the controlPointDTO, or with status 404 (Not Found)
     */
    @GetMapping("/control-points/{id}")
    @Timed
    public ResponseEntity<ControlPointDTO> getControlPoint(@PathVariable Long id) {
        log.debug("REST request to get ControlPoint : {}", id);
        ControlPointDTO controlPointDTO = controlPointService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(controlPointDTO));
    }

    /**
     * DELETE  /control-points/:id : delete the "id" controlPoint.
     *
     * @param id the id of the controlPointDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/control-points/{id}")
    @Timed
    public ResponseEntity<Void> deleteControlPoint(@PathVariable Long id) {
        log.debug("REST request to delete ControlPoint : {}", id);
        controlPointService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
