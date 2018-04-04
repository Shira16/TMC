package sktp.tmc.web.rest;

import sktp.tmc.TmcApp;

import sktp.tmc.domain.Marker;
import sktp.tmc.repository.MarkerRepository;
import sktp.tmc.service.MarkerService;
import sktp.tmc.service.dto.MarkerDTO;
import sktp.tmc.service.mapper.MarkerMapper;
import sktp.tmc.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static sktp.tmc.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MarkerResource REST controller.
 *
 * @see MarkerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TmcApp.class)
public class MarkerResourceIntTest {

    private static final Double DEFAULT_POINT_X = 1D;
    private static final Double UPDATED_POINT_X = 2D;

    private static final Double DEFAULT_POINT_Y = 1D;
    private static final Double UPDATED_POINT_Y = 2D;

    private static final String DEFAULT_MARKER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MARKER_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BEACON_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BEACON_CODE = "BBBBBBBBBB";

    @Autowired
    private MarkerRepository markerRepository;

    @Autowired
    private MarkerMapper markerMapper;

    @Autowired
    private MarkerService markerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarkerMockMvc;

    private Marker marker;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MarkerResource markerResource = new MarkerResource(markerService);
        this.restMarkerMockMvc = MockMvcBuilders.standaloneSetup(markerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Marker createEntity(EntityManager em) {
        Marker marker = new Marker()
            .pointX(DEFAULT_POINT_X)
            .pointY(DEFAULT_POINT_Y)
            .markerCode(DEFAULT_MARKER_CODE)
            .beaconCode(DEFAULT_BEACON_CODE);
        return marker;
    }

    @Before
    public void initTest() {
        marker = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarker() throws Exception {
        int databaseSizeBeforeCreate = markerRepository.findAll().size();

        // Create the Marker
        MarkerDTO markerDTO = markerMapper.toDto(marker);
        restMarkerMockMvc.perform(post("/api/markers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(markerDTO)))
            .andExpect(status().isCreated());

        // Validate the Marker in the database
        List<Marker> markerList = markerRepository.findAll();
        assertThat(markerList).hasSize(databaseSizeBeforeCreate + 1);
        Marker testMarker = markerList.get(markerList.size() - 1);
        assertThat(testMarker.getPointX()).isEqualTo(DEFAULT_POINT_X);
        assertThat(testMarker.getPointY()).isEqualTo(DEFAULT_POINT_Y);
        assertThat(testMarker.getMarkerCode()).isEqualTo(DEFAULT_MARKER_CODE);
        assertThat(testMarker.getBeaconCode()).isEqualTo(DEFAULT_BEACON_CODE);
    }

    @Test
    @Transactional
    public void createMarkerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = markerRepository.findAll().size();

        // Create the Marker with an existing ID
        marker.setId(1L);
        MarkerDTO markerDTO = markerMapper.toDto(marker);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarkerMockMvc.perform(post("/api/markers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(markerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Marker in the database
        List<Marker> markerList = markerRepository.findAll();
        assertThat(markerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMarkers() throws Exception {
        // Initialize the database
        markerRepository.saveAndFlush(marker);

        // Get all the markerList
        restMarkerMockMvc.perform(get("/api/markers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marker.getId().intValue())))
            .andExpect(jsonPath("$.[*].pointX").value(hasItem(DEFAULT_POINT_X.doubleValue())))
            .andExpect(jsonPath("$.[*].pointY").value(hasItem(DEFAULT_POINT_Y.doubleValue())))
            .andExpect(jsonPath("$.[*].markerCode").value(hasItem(DEFAULT_MARKER_CODE.toString())))
            .andExpect(jsonPath("$.[*].beaconCode").value(hasItem(DEFAULT_BEACON_CODE.toString())));
    }

    @Test
    @Transactional
    public void getMarker() throws Exception {
        // Initialize the database
        markerRepository.saveAndFlush(marker);

        // Get the marker
        restMarkerMockMvc.perform(get("/api/markers/{id}", marker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marker.getId().intValue()))
            .andExpect(jsonPath("$.pointX").value(DEFAULT_POINT_X.doubleValue()))
            .andExpect(jsonPath("$.pointY").value(DEFAULT_POINT_Y.doubleValue()))
            .andExpect(jsonPath("$.markerCode").value(DEFAULT_MARKER_CODE.toString()))
            .andExpect(jsonPath("$.beaconCode").value(DEFAULT_BEACON_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMarker() throws Exception {
        // Get the marker
        restMarkerMockMvc.perform(get("/api/markers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarker() throws Exception {
        // Initialize the database
        markerRepository.saveAndFlush(marker);
        int databaseSizeBeforeUpdate = markerRepository.findAll().size();

        // Update the marker
        Marker updatedMarker = markerRepository.findOne(marker.getId());
        // Disconnect from session so that the updates on updatedMarker are not directly saved in db
        em.detach(updatedMarker);
        updatedMarker
            .pointX(UPDATED_POINT_X)
            .pointY(UPDATED_POINT_Y)
            .markerCode(UPDATED_MARKER_CODE)
            .beaconCode(UPDATED_BEACON_CODE);
        MarkerDTO markerDTO = markerMapper.toDto(updatedMarker);

        restMarkerMockMvc.perform(put("/api/markers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(markerDTO)))
            .andExpect(status().isOk());

        // Validate the Marker in the database
        List<Marker> markerList = markerRepository.findAll();
        assertThat(markerList).hasSize(databaseSizeBeforeUpdate);
        Marker testMarker = markerList.get(markerList.size() - 1);
        assertThat(testMarker.getPointX()).isEqualTo(UPDATED_POINT_X);
        assertThat(testMarker.getPointY()).isEqualTo(UPDATED_POINT_Y);
        assertThat(testMarker.getMarkerCode()).isEqualTo(UPDATED_MARKER_CODE);
        assertThat(testMarker.getBeaconCode()).isEqualTo(UPDATED_BEACON_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingMarker() throws Exception {
        int databaseSizeBeforeUpdate = markerRepository.findAll().size();

        // Create the Marker
        MarkerDTO markerDTO = markerMapper.toDto(marker);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarkerMockMvc.perform(put("/api/markers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(markerDTO)))
            .andExpect(status().isCreated());

        // Validate the Marker in the database
        List<Marker> markerList = markerRepository.findAll();
        assertThat(markerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMarker() throws Exception {
        // Initialize the database
        markerRepository.saveAndFlush(marker);
        int databaseSizeBeforeDelete = markerRepository.findAll().size();

        // Get the marker
        restMarkerMockMvc.perform(delete("/api/markers/{id}", marker.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Marker> markerList = markerRepository.findAll();
        assertThat(markerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Marker.class);
        Marker marker1 = new Marker();
        marker1.setId(1L);
        Marker marker2 = new Marker();
        marker2.setId(marker1.getId());
        assertThat(marker1).isEqualTo(marker2);
        marker2.setId(2L);
        assertThat(marker1).isNotEqualTo(marker2);
        marker1.setId(null);
        assertThat(marker1).isNotEqualTo(marker2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarkerDTO.class);
        MarkerDTO markerDTO1 = new MarkerDTO();
        markerDTO1.setId(1L);
        MarkerDTO markerDTO2 = new MarkerDTO();
        assertThat(markerDTO1).isNotEqualTo(markerDTO2);
        markerDTO2.setId(markerDTO1.getId());
        assertThat(markerDTO1).isEqualTo(markerDTO2);
        markerDTO2.setId(2L);
        assertThat(markerDTO1).isNotEqualTo(markerDTO2);
        markerDTO1.setId(null);
        assertThat(markerDTO1).isNotEqualTo(markerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(markerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(markerMapper.fromId(null)).isNull();
    }
}
