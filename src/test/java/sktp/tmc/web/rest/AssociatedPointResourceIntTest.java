package sktp.tmc.web.rest;

import sktp.tmc.TmcApp;

import sktp.tmc.domain.AssociatedPoint;
import sktp.tmc.repository.AssociatedPointRepository;
import sktp.tmc.service.AssociatedPointService;
import sktp.tmc.service.dto.AssociatedPointDTO;
import sktp.tmc.service.mapper.AssociatedPointMapper;
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
 * Test class for the AssociatedPointResource REST controller.
 *
 * @see AssociatedPointResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TmcApp.class)
public class AssociatedPointResourceIntTest {

    @Autowired
    private AssociatedPointRepository associatedPointRepository;

    @Autowired
    private AssociatedPointMapper associatedPointMapper;

    @Autowired
    private AssociatedPointService associatedPointService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAssociatedPointMockMvc;

    private AssociatedPoint associatedPoint;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AssociatedPointResource associatedPointResource = new AssociatedPointResource(associatedPointService);
        this.restAssociatedPointMockMvc = MockMvcBuilders.standaloneSetup(associatedPointResource)
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
    public static AssociatedPoint createEntity(EntityManager em) {
        AssociatedPoint associatedPoint = new AssociatedPoint();
        return associatedPoint;
    }

    @Before
    public void initTest() {
        associatedPoint = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssociatedPoint() throws Exception {
        int databaseSizeBeforeCreate = associatedPointRepository.findAll().size();

        // Create the AssociatedPoint
        AssociatedPointDTO associatedPointDTO = associatedPointMapper.toDto(associatedPoint);
        restAssociatedPointMockMvc.perform(post("/api/associated-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(associatedPointDTO)))
            .andExpect(status().isCreated());

        // Validate the AssociatedPoint in the database
        List<AssociatedPoint> associatedPointList = associatedPointRepository.findAll();
        assertThat(associatedPointList).hasSize(databaseSizeBeforeCreate + 1);
        AssociatedPoint testAssociatedPoint = associatedPointList.get(associatedPointList.size() - 1);
    }

    @Test
    @Transactional
    public void createAssociatedPointWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = associatedPointRepository.findAll().size();

        // Create the AssociatedPoint with an existing ID
        associatedPoint.setId(1L);
        AssociatedPointDTO associatedPointDTO = associatedPointMapper.toDto(associatedPoint);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssociatedPointMockMvc.perform(post("/api/associated-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(associatedPointDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AssociatedPoint in the database
        List<AssociatedPoint> associatedPointList = associatedPointRepository.findAll();
        assertThat(associatedPointList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAssociatedPoints() throws Exception {
        // Initialize the database
        associatedPointRepository.saveAndFlush(associatedPoint);

        // Get all the associatedPointList
        restAssociatedPointMockMvc.perform(get("/api/associated-points?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(associatedPoint.getId().intValue())));
    }

    @Test
    @Transactional
    public void getAssociatedPoint() throws Exception {
        // Initialize the database
        associatedPointRepository.saveAndFlush(associatedPoint);

        // Get the associatedPoint
        restAssociatedPointMockMvc.perform(get("/api/associated-points/{id}", associatedPoint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(associatedPoint.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAssociatedPoint() throws Exception {
        // Get the associatedPoint
        restAssociatedPointMockMvc.perform(get("/api/associated-points/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssociatedPoint() throws Exception {
        // Initialize the database
        associatedPointRepository.saveAndFlush(associatedPoint);
        int databaseSizeBeforeUpdate = associatedPointRepository.findAll().size();

        // Update the associatedPoint
        AssociatedPoint updatedAssociatedPoint = associatedPointRepository.findOne(associatedPoint.getId());
        // Disconnect from session so that the updates on updatedAssociatedPoint are not directly saved in db
        em.detach(updatedAssociatedPoint);
        AssociatedPointDTO associatedPointDTO = associatedPointMapper.toDto(updatedAssociatedPoint);

        restAssociatedPointMockMvc.perform(put("/api/associated-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(associatedPointDTO)))
            .andExpect(status().isOk());

        // Validate the AssociatedPoint in the database
        List<AssociatedPoint> associatedPointList = associatedPointRepository.findAll();
        assertThat(associatedPointList).hasSize(databaseSizeBeforeUpdate);
        AssociatedPoint testAssociatedPoint = associatedPointList.get(associatedPointList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingAssociatedPoint() throws Exception {
        int databaseSizeBeforeUpdate = associatedPointRepository.findAll().size();

        // Create the AssociatedPoint
        AssociatedPointDTO associatedPointDTO = associatedPointMapper.toDto(associatedPoint);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAssociatedPointMockMvc.perform(put("/api/associated-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(associatedPointDTO)))
            .andExpect(status().isCreated());

        // Validate the AssociatedPoint in the database
        List<AssociatedPoint> associatedPointList = associatedPointRepository.findAll();
        assertThat(associatedPointList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAssociatedPoint() throws Exception {
        // Initialize the database
        associatedPointRepository.saveAndFlush(associatedPoint);
        int databaseSizeBeforeDelete = associatedPointRepository.findAll().size();

        // Get the associatedPoint
        restAssociatedPointMockMvc.perform(delete("/api/associated-points/{id}", associatedPoint.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AssociatedPoint> associatedPointList = associatedPointRepository.findAll();
        assertThat(associatedPointList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssociatedPoint.class);
        AssociatedPoint associatedPoint1 = new AssociatedPoint();
        associatedPoint1.setId(1L);
        AssociatedPoint associatedPoint2 = new AssociatedPoint();
        associatedPoint2.setId(associatedPoint1.getId());
        assertThat(associatedPoint1).isEqualTo(associatedPoint2);
        associatedPoint2.setId(2L);
        assertThat(associatedPoint1).isNotEqualTo(associatedPoint2);
        associatedPoint1.setId(null);
        assertThat(associatedPoint1).isNotEqualTo(associatedPoint2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssociatedPointDTO.class);
        AssociatedPointDTO associatedPointDTO1 = new AssociatedPointDTO();
        associatedPointDTO1.setId(1L);
        AssociatedPointDTO associatedPointDTO2 = new AssociatedPointDTO();
        assertThat(associatedPointDTO1).isNotEqualTo(associatedPointDTO2);
        associatedPointDTO2.setId(associatedPointDTO1.getId());
        assertThat(associatedPointDTO1).isEqualTo(associatedPointDTO2);
        associatedPointDTO2.setId(2L);
        assertThat(associatedPointDTO1).isNotEqualTo(associatedPointDTO2);
        associatedPointDTO1.setId(null);
        assertThat(associatedPointDTO1).isNotEqualTo(associatedPointDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(associatedPointMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(associatedPointMapper.fromId(null)).isNull();
    }
}
