package sktp.tmc.web.rest;

import sktp.tmc.TmcApp;

import sktp.tmc.domain.RouteOne;
import sktp.tmc.repository.RouteOneRepository;
import sktp.tmc.service.RouteOneService;
import sktp.tmc.service.dto.RouteOneDTO;
import sktp.tmc.service.mapper.RouteOneMapper;
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
 * Test class for the RouteOneResource REST controller.
 *
 * @see RouteOneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TmcApp.class)
public class RouteOneResourceIntTest {

    private static final String DEFAULT_ABBR = "AAAAAAAAAA";
    private static final String UPDATED_ABBR = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private RouteOneRepository routeOneRepository;

    @Autowired
    private RouteOneMapper routeOneMapper;

    @Autowired
    private RouteOneService routeOneService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRouteOneMockMvc;

    private RouteOne routeOne;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RouteOneResource routeOneResource = new RouteOneResource(routeOneService);
        this.restRouteOneMockMvc = MockMvcBuilders.standaloneSetup(routeOneResource)
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
    public static RouteOne createEntity(EntityManager em) {
        RouteOne routeOne = new RouteOne()
            .abbr(DEFAULT_ABBR)
            .name(DEFAULT_NAME);
        return routeOne;
    }

    @Before
    public void initTest() {
        routeOne = createEntity(em);
    }

    @Test
    @Transactional
    public void createRouteOne() throws Exception {
        int databaseSizeBeforeCreate = routeOneRepository.findAll().size();

        // Create the RouteOne
        RouteOneDTO routeOneDTO = routeOneMapper.toDto(routeOne);
        restRouteOneMockMvc.perform(post("/api/route-ones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(routeOneDTO)))
            .andExpect(status().isCreated());

        // Validate the RouteOne in the database
        List<RouteOne> routeOneList = routeOneRepository.findAll();
        assertThat(routeOneList).hasSize(databaseSizeBeforeCreate + 1);
        RouteOne testRouteOne = routeOneList.get(routeOneList.size() - 1);
        assertThat(testRouteOne.getAbbr()).isEqualTo(DEFAULT_ABBR);
        assertThat(testRouteOne.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createRouteOneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = routeOneRepository.findAll().size();

        // Create the RouteOne with an existing ID
        routeOne.setId(1L);
        RouteOneDTO routeOneDTO = routeOneMapper.toDto(routeOne);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRouteOneMockMvc.perform(post("/api/route-ones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(routeOneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RouteOne in the database
        List<RouteOne> routeOneList = routeOneRepository.findAll();
        assertThat(routeOneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRouteOnes() throws Exception {
        // Initialize the database
        routeOneRepository.saveAndFlush(routeOne);

        // Get all the routeOneList
        restRouteOneMockMvc.perform(get("/api/route-ones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(routeOne.getId().intValue())))
            .andExpect(jsonPath("$.[*].abbr").value(hasItem(DEFAULT_ABBR.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getRouteOne() throws Exception {
        // Initialize the database
        routeOneRepository.saveAndFlush(routeOne);

        // Get the routeOne
        restRouteOneMockMvc.perform(get("/api/route-ones/{id}", routeOne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(routeOne.getId().intValue()))
            .andExpect(jsonPath("$.abbr").value(DEFAULT_ABBR.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRouteOne() throws Exception {
        // Get the routeOne
        restRouteOneMockMvc.perform(get("/api/route-ones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRouteOne() throws Exception {
        // Initialize the database
        routeOneRepository.saveAndFlush(routeOne);
        int databaseSizeBeforeUpdate = routeOneRepository.findAll().size();

        // Update the routeOne
        RouteOne updatedRouteOne = routeOneRepository.findOne(routeOne.getId());
        // Disconnect from session so that the updates on updatedRouteOne are not directly saved in db
        em.detach(updatedRouteOne);
        updatedRouteOne
            .abbr(UPDATED_ABBR)
            .name(UPDATED_NAME);
        RouteOneDTO routeOneDTO = routeOneMapper.toDto(updatedRouteOne);

        restRouteOneMockMvc.perform(put("/api/route-ones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(routeOneDTO)))
            .andExpect(status().isOk());

        // Validate the RouteOne in the database
        List<RouteOne> routeOneList = routeOneRepository.findAll();
        assertThat(routeOneList).hasSize(databaseSizeBeforeUpdate);
        RouteOne testRouteOne = routeOneList.get(routeOneList.size() - 1);
        assertThat(testRouteOne.getAbbr()).isEqualTo(UPDATED_ABBR);
        assertThat(testRouteOne.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingRouteOne() throws Exception {
        int databaseSizeBeforeUpdate = routeOneRepository.findAll().size();

        // Create the RouteOne
        RouteOneDTO routeOneDTO = routeOneMapper.toDto(routeOne);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRouteOneMockMvc.perform(put("/api/route-ones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(routeOneDTO)))
            .andExpect(status().isCreated());

        // Validate the RouteOne in the database
        List<RouteOne> routeOneList = routeOneRepository.findAll();
        assertThat(routeOneList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRouteOne() throws Exception {
        // Initialize the database
        routeOneRepository.saveAndFlush(routeOne);
        int databaseSizeBeforeDelete = routeOneRepository.findAll().size();

        // Get the routeOne
        restRouteOneMockMvc.perform(delete("/api/route-ones/{id}", routeOne.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RouteOne> routeOneList = routeOneRepository.findAll();
        assertThat(routeOneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RouteOne.class);
        RouteOne routeOne1 = new RouteOne();
        routeOne1.setId(1L);
        RouteOne routeOne2 = new RouteOne();
        routeOne2.setId(routeOne1.getId());
        assertThat(routeOne1).isEqualTo(routeOne2);
        routeOne2.setId(2L);
        assertThat(routeOne1).isNotEqualTo(routeOne2);
        routeOne1.setId(null);
        assertThat(routeOne1).isNotEqualTo(routeOne2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RouteOneDTO.class);
        RouteOneDTO routeOneDTO1 = new RouteOneDTO();
        routeOneDTO1.setId(1L);
        RouteOneDTO routeOneDTO2 = new RouteOneDTO();
        assertThat(routeOneDTO1).isNotEqualTo(routeOneDTO2);
        routeOneDTO2.setId(routeOneDTO1.getId());
        assertThat(routeOneDTO1).isEqualTo(routeOneDTO2);
        routeOneDTO2.setId(2L);
        assertThat(routeOneDTO1).isNotEqualTo(routeOneDTO2);
        routeOneDTO1.setId(null);
        assertThat(routeOneDTO1).isNotEqualTo(routeOneDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(routeOneMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(routeOneMapper.fromId(null)).isNull();
    }
}
