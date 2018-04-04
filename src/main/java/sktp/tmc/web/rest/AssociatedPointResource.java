package sktp.tmc.web.rest;

import com.codahale.metrics.annotation.Timed;
import sktp.tmc.service.AssociatedPointService;
import sktp.tmc.web.rest.errors.BadRequestAlertException;
import sktp.tmc.web.rest.util.HeaderUtil;
import sktp.tmc.service.dto.AssociatedPointDTO;
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
 * REST controller for managing AssociatedPoint.
 */
@RestController
@RequestMapping("/api")
public class AssociatedPointResource {

    private final Logger log = LoggerFactory.getLogger(AssociatedPointResource.class);

    private static final String ENTITY_NAME = "associatedPoint";

    private final AssociatedPointService associatedPointService;

    public AssociatedPointResource(AssociatedPointService associatedPointService) {
        this.associatedPointService = associatedPointService;
    }

    /**
     * POST  /associated-points : Create a new associatedPoint.
     *
     * @param associatedPointDTO the associatedPointDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new associatedPointDTO, or with status 400 (Bad Request) if the associatedPoint has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/associated-points")
    @Timed
    public ResponseEntity<AssociatedPointDTO> createAssociatedPoint(@RequestBody AssociatedPointDTO associatedPointDTO) throws URISyntaxException {
        log.debug("REST request to save AssociatedPoint : {}", associatedPointDTO);
        if (associatedPointDTO.getId() != null) {
            throw new BadRequestAlertException("A new associatedPoint cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssociatedPointDTO result = associatedPointService.save(associatedPointDTO);
        return ResponseEntity.created(new URI("/api/associated-points/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /associated-points : Updates an existing associatedPoint.
     *
     * @param associatedPointDTO the associatedPointDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated associatedPointDTO,
     * or with status 400 (Bad Request) if the associatedPointDTO is not valid,
     * or with status 500 (Internal Server Error) if the associatedPointDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/associated-points")
    @Timed
    public ResponseEntity<AssociatedPointDTO> updateAssociatedPoint(@RequestBody AssociatedPointDTO associatedPointDTO) throws URISyntaxException {
        log.debug("REST request to update AssociatedPoint : {}", associatedPointDTO);
        if (associatedPointDTO.getId() == null) {
            return createAssociatedPoint(associatedPointDTO);
        }
        AssociatedPointDTO result = associatedPointService.save(associatedPointDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, associatedPointDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /associated-points : get all the associatedPoints.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of associatedPoints in body
     */
    @GetMapping("/associated-points")
    @Timed
    public List<AssociatedPointDTO> getAllAssociatedPoints() {
        log.debug("REST request to get all AssociatedPoints");
        return associatedPointService.findAll();
        }

    /**
     * GET  /associated-points/:id : get the "id" associatedPoint.
     *
     * @param id the id of the associatedPointDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the associatedPointDTO, or with status 404 (Not Found)
     */
    @GetMapping("/associated-points/{id}")
    @Timed
    public ResponseEntity<AssociatedPointDTO> getAssociatedPoint(@PathVariable Long id) {
        log.debug("REST request to get AssociatedPoint : {}", id);
        AssociatedPointDTO associatedPointDTO = associatedPointService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(associatedPointDTO));
    }

    /**
     * DELETE  /associated-points/:id : delete the "id" associatedPoint.
     *
     * @param id the id of the associatedPointDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/associated-points/{id}")
    @Timed
    public ResponseEntity<Void> deleteAssociatedPoint(@PathVariable Long id) {
        log.debug("REST request to delete AssociatedPoint : {}", id);
        associatedPointService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
