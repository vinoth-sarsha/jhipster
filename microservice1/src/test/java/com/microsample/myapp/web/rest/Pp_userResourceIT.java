package com.microsample.myapp.web.rest;

import com.microsample.myapp.Microservice1App;
import com.microsample.myapp.domain.Pp_user;
import com.microsample.myapp.repository.Pp_userRepository;
import com.microsample.myapp.service.Pp_userService;
import com.microsample.myapp.service.dto.Pp_userDTO;
import com.microsample.myapp.service.mapper.Pp_userMapper;
import com.microsample.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.microsample.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link Pp_userResource} REST controller.
 */
@SpringBootTest(classes = Microservice1App.class)
public class Pp_userResourceIT {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_ABOUT_ME = "AAAAAAAAAA";
    private static final String UPDATED_ABOUT_ME = "BBBBBBBBBB";

    @Autowired
    private Pp_userRepository pp_userRepository;

    @Autowired
    private Pp_userMapper pp_userMapper;

    @Autowired
    private Pp_userService pp_userService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPp_userMockMvc;

    private Pp_user pp_user;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Pp_userResource pp_userResource = new Pp_userResource(pp_userService);
        this.restPp_userMockMvc = MockMvcBuilders.standaloneSetup(pp_userResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pp_user createEntity(EntityManager em) {
        Pp_user pp_user = new Pp_user()
            .user_name(DEFAULT_USER_NAME)
            .dob(DEFAULT_DOB)
            .role(DEFAULT_ROLE)
            .location(DEFAULT_LOCATION)
            .about_me(DEFAULT_ABOUT_ME);
        return pp_user;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pp_user createUpdatedEntity(EntityManager em) {
        Pp_user pp_user = new Pp_user()
            .user_name(UPDATED_USER_NAME)
            .dob(UPDATED_DOB)
            .role(UPDATED_ROLE)
            .location(UPDATED_LOCATION)
            .about_me(UPDATED_ABOUT_ME);
        return pp_user;
    }

    @BeforeEach
    public void initTest() {
        pp_user = createEntity(em);
    }

    @Test
    @Transactional
    public void createPp_user() throws Exception {
        int databaseSizeBeforeCreate = pp_userRepository.findAll().size();

        // Create the Pp_user
        Pp_userDTO pp_userDTO = pp_userMapper.toDto(pp_user);
        restPp_userMockMvc.perform(post("/api/pp-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pp_userDTO)))
            .andExpect(status().isCreated());

        // Validate the Pp_user in the database
        List<Pp_user> pp_userList = pp_userRepository.findAll();
        assertThat(pp_userList).hasSize(databaseSizeBeforeCreate + 1);
        Pp_user testPp_user = pp_userList.get(pp_userList.size() - 1);
        assertThat(testPp_user.getUser_name()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testPp_user.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testPp_user.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testPp_user.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testPp_user.getAbout_me()).isEqualTo(DEFAULT_ABOUT_ME);
    }

    @Test
    @Transactional
    public void createPp_userWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pp_userRepository.findAll().size();

        // Create the Pp_user with an existing ID
        pp_user.setId(1L);
        Pp_userDTO pp_userDTO = pp_userMapper.toDto(pp_user);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPp_userMockMvc.perform(post("/api/pp-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pp_userDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pp_user in the database
        List<Pp_user> pp_userList = pp_userRepository.findAll();
        assertThat(pp_userList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUser_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pp_userRepository.findAll().size();
        // set the field null
        pp_user.setUser_name(null);

        // Create the Pp_user, which fails.
        Pp_userDTO pp_userDTO = pp_userMapper.toDto(pp_user);

        restPp_userMockMvc.perform(post("/api/pp-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pp_userDTO)))
            .andExpect(status().isBadRequest());

        List<Pp_user> pp_userList = pp_userRepository.findAll();
        assertThat(pp_userList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPp_users() throws Exception {
        // Initialize the database
        pp_userRepository.saveAndFlush(pp_user);

        // Get all the pp_userList
        restPp_userMockMvc.perform(get("/api/pp-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pp_user.getId().intValue())))
            .andExpect(jsonPath("$.[*].user_name").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].about_me").value(hasItem(DEFAULT_ABOUT_ME.toString())));
    }
    
    @Test
    @Transactional
    public void getPp_user() throws Exception {
        // Initialize the database
        pp_userRepository.saveAndFlush(pp_user);

        // Get the pp_user
        restPp_userMockMvc.perform(get("/api/pp-users/{id}", pp_user.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pp_user.getId().intValue()))
            .andExpect(jsonPath("$.user_name").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.about_me").value(DEFAULT_ABOUT_ME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPp_user() throws Exception {
        // Get the pp_user
        restPp_userMockMvc.perform(get("/api/pp-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePp_user() throws Exception {
        // Initialize the database
        pp_userRepository.saveAndFlush(pp_user);

        int databaseSizeBeforeUpdate = pp_userRepository.findAll().size();

        // Update the pp_user
        Pp_user updatedPp_user = pp_userRepository.findById(pp_user.getId()).get();
        // Disconnect from session so that the updates on updatedPp_user are not directly saved in db
        em.detach(updatedPp_user);
        updatedPp_user
            .user_name(UPDATED_USER_NAME)
            .dob(UPDATED_DOB)
            .role(UPDATED_ROLE)
            .location(UPDATED_LOCATION)
            .about_me(UPDATED_ABOUT_ME);
        Pp_userDTO pp_userDTO = pp_userMapper.toDto(updatedPp_user);

        restPp_userMockMvc.perform(put("/api/pp-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pp_userDTO)))
            .andExpect(status().isOk());

        // Validate the Pp_user in the database
        List<Pp_user> pp_userList = pp_userRepository.findAll();
        assertThat(pp_userList).hasSize(databaseSizeBeforeUpdate);
        Pp_user testPp_user = pp_userList.get(pp_userList.size() - 1);
        assertThat(testPp_user.getUser_name()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testPp_user.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testPp_user.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testPp_user.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testPp_user.getAbout_me()).isEqualTo(UPDATED_ABOUT_ME);
    }

    @Test
    @Transactional
    public void updateNonExistingPp_user() throws Exception {
        int databaseSizeBeforeUpdate = pp_userRepository.findAll().size();

        // Create the Pp_user
        Pp_userDTO pp_userDTO = pp_userMapper.toDto(pp_user);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPp_userMockMvc.perform(put("/api/pp-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pp_userDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pp_user in the database
        List<Pp_user> pp_userList = pp_userRepository.findAll();
        assertThat(pp_userList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePp_user() throws Exception {
        // Initialize the database
        pp_userRepository.saveAndFlush(pp_user);

        int databaseSizeBeforeDelete = pp_userRepository.findAll().size();

        // Delete the pp_user
        restPp_userMockMvc.perform(delete("/api/pp-users/{id}", pp_user.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pp_user> pp_userList = pp_userRepository.findAll();
        assertThat(pp_userList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pp_user.class);
        Pp_user pp_user1 = new Pp_user();
        pp_user1.setId(1L);
        Pp_user pp_user2 = new Pp_user();
        pp_user2.setId(pp_user1.getId());
        assertThat(pp_user1).isEqualTo(pp_user2);
        pp_user2.setId(2L);
        assertThat(pp_user1).isNotEqualTo(pp_user2);
        pp_user1.setId(null);
        assertThat(pp_user1).isNotEqualTo(pp_user2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pp_userDTO.class);
        Pp_userDTO pp_userDTO1 = new Pp_userDTO();
        pp_userDTO1.setId(1L);
        Pp_userDTO pp_userDTO2 = new Pp_userDTO();
        assertThat(pp_userDTO1).isNotEqualTo(pp_userDTO2);
        pp_userDTO2.setId(pp_userDTO1.getId());
        assertThat(pp_userDTO1).isEqualTo(pp_userDTO2);
        pp_userDTO2.setId(2L);
        assertThat(pp_userDTO1).isNotEqualTo(pp_userDTO2);
        pp_userDTO1.setId(null);
        assertThat(pp_userDTO1).isNotEqualTo(pp_userDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pp_userMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pp_userMapper.fromId(null)).isNull();
    }
}
