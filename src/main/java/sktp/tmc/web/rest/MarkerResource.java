package sktp.tmc.web.rest;

import com.codahale.metrics.annotation.Timed;
import sktp.tmc.service.MarkerService;
import sktp.tmc.web.rest.errors.BadRequestAlertException;
import sktp.tmc.web.rest.util.HeaderUtil;
import sktp.tmc.service.dto.MarkerDTO;
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
 * REST controller for managing Marker.
 */
@RestController
@RequestMapping("/api")
public class MarkerResource {

    private final Logger log = LoggerFactory.getLogger(MarkerResource.class);

    private static final String ENTITY_NAME = "marker";

    private final MarkerService markerService;

    public MarkerResource(MarkerService markerService) {
        this.markerService = markerService;
    }

    /**
     * POST  /markers : Create a new marker.
     *
     * @param markerDTO the markerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new markerDTO, or with status 400 (Bad Request) if the marker has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/markers")
    @Timed
    public ResponseEntity<MarkerDTO> createMarker(@RequestBody MarkerDTO markerDTO) throws URISyntaxException {
        log.debug("REST request to save Marker : {}", markerDTO);
        if (markerDTO.getId() != null) {
            throw new BadRequestAlertException("A new marker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MarkerDTO result = markerService.save(markerDTO);
        return ResponseEntity.created(new URI("/api/markers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /markers : Updates an existing marker.
     *
     * @param markerDTO the markerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated markerDTO,
     * or with status 400 (Bad Request) if the markerDTO is not valid,
     * or with status 500 (Internal Server Error) if the markerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/markers")
    @Timed
    public ResponseEntity<MarkerDTO> updateMarker(@RequestBody MarkerDTO markerDTO) throws URISyntaxException {
        log.debug("REST request to update Marker : {}", markerDTO);
        if (markerDTO.getId() == null) {
            return createMarker(markerDTO);
        }
        MarkerDTO result = markerService.save(markerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, markerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /markers : get all the markers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of markers in body
     */
    @GetMapping("/markers")
    @Timed
    public List<MarkerDTO> getAllMarkers() {
        log.debug("REST request to get all Markers");
        return markerService.findAll();
        }

    /**
     * GET  /markers/:id : get the "id" marker.
     *
     * @param id the id of the markerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the markerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/markers/{id}")
    @Timed
    public ResponseEntity<MarkerDTO> getMarker(@PathVariable Long id) {
        log.debug("REST request to get Marker : {}", id);
        MarkerDTO markerDTO = markerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(markerDTO));
    }

    /**
     * DELETE  /markers/:id : delete the "id" marker.
     *
     * @param id the id of the markerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/markers/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarker(@PathVariable Long id) {
        log.debug("REST request to delete Marker : {}", id);
        markerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
