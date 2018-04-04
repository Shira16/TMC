package sktp.tmc.web.rest;

import com.codahale.metrics.annotation.Timed;
import sktp.tmc.service.RouteOneService;
import sktp.tmc.web.rest.errors.BadRequestAlertException;
import sktp.tmc.web.rest.util.HeaderUtil;
import sktp.tmc.service.dto.RouteOneDTO;
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
 * REST controller for managing RouteOne.
 */
@RestController
@RequestMapping("/api")
public class RouteOneResource {

    private final Logger log = LoggerFactory.getLogger(RouteOneResource.class);

    private static final String ENTITY_NAME = "routeOne";

    private final RouteOneService routeOneService;

    public RouteOneResource(RouteOneService routeOneService) {
        this.routeOneService = routeOneService;
    }

    /**
     * POST  /route-ones : Create a new routeOne.
     *
     * @param routeOneDTO the routeOneDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new routeOneDTO, or with status 400 (Bad Request) if the routeOne has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/route-ones")
    @Timed
    public ResponseEntity<RouteOneDTO> createRouteOne(@RequestBody RouteOneDTO routeOneDTO) throws URISyntaxException {
        log.debug("REST request to save RouteOne : {}", routeOneDTO);
        if (routeOneDTO.getId() != null) {
            throw new BadRequestAlertException("A new routeOne cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RouteOneDTO result = routeOneService.save(routeOneDTO);
        return ResponseEntity.created(new URI("/api/route-ones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /route-ones : Updates an existing routeOne.
     *
     * @param routeOneDTO the routeOneDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated routeOneDTO,
     * or with status 400 (Bad Request) if the routeOneDTO is not valid,
     * or with status 500 (Internal Server Error) if the routeOneDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/route-ones")
    @Timed
    public ResponseEntity<RouteOneDTO> updateRouteOne(@RequestBody RouteOneDTO routeOneDTO) throws URISyntaxException {
        log.debug("REST request to update RouteOne : {}", routeOneDTO);
        if (routeOneDTO.getId() == null) {
            return createRouteOne(routeOneDTO);
        }
        RouteOneDTO result = routeOneService.save(routeOneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, routeOneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /route-ones : get all the routeOnes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of routeOnes in body
     */
    @GetMapping("/route-ones")
    @Timed
    public List<RouteOneDTO> getAllRouteOnes() {
        log.debug("REST request to get all RouteOnes");
        return routeOneService.findAll();
        }

    /**
     * GET  /route-ones/:id : get the "id" routeOne.
     *
     * @param id the id of the routeOneDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the routeOneDTO, or with status 404 (Not Found)
     */
    @GetMapping("/route-ones/{id}")
    @Timed
    public ResponseEntity<RouteOneDTO> getRouteOne(@PathVariable Long id) {
        log.debug("REST request to get RouteOne : {}", id);
        RouteOneDTO routeOneDTO = routeOneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(routeOneDTO));
    }

    /**
     * DELETE  /route-ones/:id : delete the "id" routeOne.
     *
     * @param id the id of the routeOneDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/route-ones/{id}")
    @Timed
    public ResponseEntity<Void> deleteRouteOne(@PathVariable Long id) {
        log.debug("REST request to delete RouteOne : {}", id);
        routeOneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
