package sktp.tmc.web.rest;

import sktp.tmc.TmcApp;

import sktp.tmc.domain.ControlPoint;
import sktp.tmc.repository.ControlPointRepository;
import sktp.tmc.service.ControlPointService;
import sktp.tmc.service.dto.ControlPointDTO;
import sktp.tmc.service.mapper.ControlPointMapper;
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
 * Test class for the ControlPointResource REST controller.
 *
 * @see ControlPointResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TmcApp.class)
public class ControlPointResourceIntTest {

    private static final String DEFAULT_ORDINAL = "AAAAAAAAAA";
    private static final String UPDATED_ORDINAL = "BBBBBBBBBB";

    @Autowired
    private ControlPointRepository controlPointRepository;

    @Autowired
    private ControlPointMapper controlPointMapper;

    @Autowired
    private ControlPointService controlPointService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restControlPointMockMvc;

    private ControlPoint controlPoint;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ControlPointResource controlPointResource = new ControlPointResource(controlPointService);
        this.restControlPointMockMvc = MockMvcBuilders.standaloneSetup(controlPointResource)
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
    public static ControlPoint createEntity(EntityManager em) {
        ControlPoint controlPoint = new ControlPoint()
            .ordinal(DEFAULT_ORDINAL);
        return controlPoint;
    }

    @Before
    public void initTest() {
        controlPoint = createEntity(em);
    }

    @Test
    @Transactional
    public void createControlPoint() throws Exception {
        int databaseSizeBeforeCreate = controlPointRepository.findAll().size();

        // Create the ControlPoint
        ControlPointDTO controlPointDTO = controlPointMapper.toDto(controlPoint);
        restControlPointMockMvc.perform(post("/api/control-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(controlPointDTO)))
            .andExpect(status().isCreated());

        // Validate the ControlPoint in the database
        List<ControlPoint> controlPointList = controlPointRepository.findAll();
        assertThat(controlPointList).hasSize(databaseSizeBeforeCreate + 1);
        ControlPoint testControlPoint = controlPointList.get(controlPointList.size() - 1);
        assertThat(testControlPoint.getOrdinal()).isEqualTo(DEFAULT_ORDINAL);
    }

    @Test
    @Transactional
    public void createControlPointWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = controlPointRepository.findAll().size();

        // Create the ControlPoint with an existing ID
        controlPoint.setId(1L);
        ControlPointDTO controlPointDTO = controlPointMapper.toDto(controlPoint);

        // An entity with an existing ID cannot be created, so this API call must fail
        restControlPointMockMvc.perform(post("/api/control-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(controlPointDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ControlPoint in the database
        List<ControlPoint> controlPointList = controlPointRepository.findAll();
        assertThat(controlPointList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllControlPoints() throws Exception {
        // Initialize the database
        controlPointRepository.saveAndFlush(controlPoint);

        // Get all the controlPointList
        restControlPointMockMvc.perform(get("/api/control-points?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(controlPoint.getId().intValue())))
            .andExpect(jsonPath("$.[*].ordinal").value(hasItem(DEFAULT_ORDINAL.toString())));
    }

    @Test
    @Transactional
    public void getControlPoint() throws Exception {
        // Initialize the database
        controlPointRepository.saveAndFlush(controlPoint);

        // Get the controlPoint
        restControlPointMockMvc.perform(get("/api/control-points/{id}", controlPoint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(controlPoint.getId().intValue()))
            .andExpect(jsonPath("$.ordinal").value(DEFAULT_ORDINAL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingControlPoint() throws Exception {
        // Get the controlPoint
        restControlPointMockMvc.perform(get("/api/control-points/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateControlPoint() throws Exception {
        // Initialize the database
        controlPointRepository.saveAndFlush(controlPoint);
        int databaseSizeBeforeUpdate = controlPointRepository.findAll().size();

        // Update the controlPoint
        ControlPoint updatedControlPoint = controlPointRepository.findOne(controlPoint.getId());
        // Disconnect from session so that the updates on updatedControlPoint are not directly saved in db
        em.detach(updatedControlPoint);
        updatedControlPoint
            .ordinal(UPDATED_ORDINAL);
        ControlPointDTO controlPointDTO = controlPointMapper.toDto(updatedControlPoint);

        restControlPointMockMvc.perform(put("/api/control-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(controlPointDTO)))
            .andExpect(status().isOk());

        // Validate the ControlPoint in the database
        List<ControlPoint> controlPointList = controlPointRepository.findAll();
        assertThat(controlPointList).hasSize(databaseSizeBeforeUpdate);
        ControlPoint testControlPoint = controlPointList.get(controlPointList.size() - 1);
        assertThat(testControlPoint.getOrdinal()).isEqualTo(UPDATED_ORDINAL);
    }

    @Test
    @Transactional
    public void updateNonExistingControlPoint() throws Exception {
        int databaseSizeBeforeUpdate = controlPointRepository.findAll().size();

        // Create the ControlPoint
        ControlPointDTO controlPointDTO = controlPointMapper.toDto(controlPoint);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restControlPointMockMvc.perform(put("/api/control-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(controlPointDTO)))
            .andExpect(status().isCreated());

        // Validate the ControlPoint in the database
        List<ControlPoint> controlPointList = controlPointRepository.findAll();
        assertThat(controlPointList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteControlPoint() throws Exception {
        // Initialize the database
        controlPointRepository.saveAndFlush(controlPoint);
        int databaseSizeBeforeDelete = controlPointRepository.findAll().size();

        // Get the controlPoint
        restControlPointMockMvc.perform(delete("/api/control-points/{id}", controlPoint.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ControlPoint> controlPointList = controlPointRepository.findAll();
        assertThat(controlPointList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ControlPoint.class);
        ControlPoint controlPoint1 = new ControlPoint();
        controlPoint1.setId(1L);
        ControlPoint controlPoint2 = new ControlPoint();
        controlPoint2.setId(controlPoint1.getId());
        assertThat(controlPoint1).isEqualTo(controlPoint2);
        controlPoint2.setId(2L);
        assertThat(controlPoint1).isNotEqualTo(controlPoint2);
        controlPoint1.setId(null);
        assertThat(controlPoint1).isNotEqualTo(controlPoint2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ControlPointDTO.class);
        ControlPointDTO controlPointDTO1 = new ControlPointDTO();
        controlPointDTO1.setId(1L);
        ControlPointDTO controlPointDTO2 = new ControlPointDTO();
        assertThat(controlPointDTO1).isNotEqualTo(controlPointDTO2);
        controlPointDTO2.setId(controlPointDTO1.getId());
        assertThat(controlPointDTO1).isEqualTo(controlPointDTO2);
        controlPointDTO2.setId(2L);
        assertThat(controlPointDTO1).isNotEqualTo(controlPointDTO2);
        controlPointDTO1.setId(null);
        assertThat(controlPointDTO1).isNotEqualTo(controlPointDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(controlPointMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(controlPointMapper.fromId(null)).isNull();
    }
}
