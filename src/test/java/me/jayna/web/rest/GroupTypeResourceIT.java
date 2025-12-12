package me.jayna.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import me.jayna.IntegrationTest;
import me.jayna.domain.GroupType;
import me.jayna.repository.GroupTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GroupTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GroupTypeResourceIT {

  private static final String DEFAULT_NAME = "AAAAAAAAAA";
  private static final String UPDATED_NAME = "BBBBBBBBBB";

  private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(
      0L);
  private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(
      ZoneId.systemDefault());

  private static final String DEFAULT_TRANSLATION_KEY = "AAAAAAAAAA";
  private static final String UPDATED_TRANSLATION_KEY = "BBBBBBBBBB";

  private static final String DEFAULT_ENTITY_NAME = "AAAAAAAAAA";
  private static final String UPDATED_ENTITY_NAME = "BBBBBBBBBB";

  private static final String DEFAULT_FORM_NAME = "AAAAAAAAAA";
  private static final String UPDATED_FORM_NAME = "BBBBBBBBBB";

  private static final String DEFAULT_ABBREVIATION = "AAAAAAAAAA";
  private static final String UPDATED_ABBREVIATION = "BBBBBBBBBB";

  private static final String ENTITY_API_URL = "/api/group-types";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(
      random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private GroupTypeRepository groupTypeRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restGroupTypeMockMvc;

  private GroupType groupType;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static GroupType createEntity(EntityManager em) {
    GroupType groupType = new GroupType()
        .name(DEFAULT_NAME)
        .createdDate(DEFAULT_CREATED_DATE)
        .translationKey(DEFAULT_TRANSLATION_KEY)
        .entityName(DEFAULT_ENTITY_NAME)
        .formName(DEFAULT_FORM_NAME)
        .abbreviation(DEFAULT_ABBREVIATION);
    return groupType;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static GroupType createUpdatedEntity(EntityManager em) {
    GroupType groupType = new GroupType()
        .name(UPDATED_NAME)
        .createdDate(UPDATED_CREATED_DATE)
        .translationKey(UPDATED_TRANSLATION_KEY)
        .entityName(UPDATED_ENTITY_NAME)
        .formName(UPDATED_FORM_NAME)
        .abbreviation(UPDATED_ABBREVIATION);
    return groupType;
  }

  @BeforeEach
  public void initTest() {
    groupType = createEntity(em);
  }

  @Test
  @Transactional
  void createGroupType() throws Exception {
    int databaseSizeBeforeCreate = groupTypeRepository.findAll().size();
    // Create the GroupType
    restGroupTypeMockMvc
        .perform(
            post(ENTITY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(groupType)))
        .andExpect(status().isCreated());

    // Validate the GroupType in the database
    List<GroupType> groupTypeList = groupTypeRepository.findAll();
    assertThat(groupTypeList).hasSize(databaseSizeBeforeCreate + 1);
    GroupType testGroupType = groupTypeList.get(groupTypeList.size() - 1);
    assertThat(testGroupType.getName()).isEqualTo(DEFAULT_NAME);
    assertThat(testGroupType.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    assertThat(testGroupType.getTranslationKey())
        .isEqualTo(DEFAULT_TRANSLATION_KEY);
    assertThat(testGroupType.getEntityName()).isEqualTo(DEFAULT_ENTITY_NAME);
    assertThat(testGroupType.getFormName()).isEqualTo(DEFAULT_FORM_NAME);
    assertThat(testGroupType.getAbbreviation()).isEqualTo(DEFAULT_ABBREVIATION);
  }

  @Test
  @Transactional
  void createGroupTypeWithExistingId() throws Exception {
    // Create the GroupType with an existing ID
    groupType.setId(1L);

    int databaseSizeBeforeCreate = groupTypeRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restGroupTypeMockMvc
        .perform(
            post(ENTITY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(groupType)))
        .andExpect(status().isBadRequest());

    // Validate the GroupType in the database
    List<GroupType> groupTypeList = groupTypeRepository.findAll();
    assertThat(groupTypeList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void getAllGroupTypes() throws Exception {
    // Initialize the database
    groupTypeRepository.saveAndFlush(groupType);

    // Get all the groupTypeList
    restGroupTypeMockMvc
        .perform(get(ENTITY_API_URL + "?sort=id,desc"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(
            jsonPath("$.[*].id").value(hasItem(groupType.getId().intValue())))
        .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
        .andExpect(
            jsonPath("$.[*].createdDate")
                .value(hasItem(DEFAULT_CREATED_DATE.toString())))
        .andExpect(
            jsonPath("$.[*].translationKey").value(hasItem(DEFAULT_TRANSLATION_KEY)))
        .andExpect(
            jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME)))
        .andExpect(jsonPath("$.[*].formName").value(hasItem(DEFAULT_FORM_NAME)))
        .andExpect(
            jsonPath("$.[*].abbreviation").value(hasItem(DEFAULT_ABBREVIATION)));
  }

  @Test
  @Transactional
  void getGroupType() throws Exception {
    // Initialize the database
    groupTypeRepository.saveAndFlush(groupType);

    // Get the groupType
    restGroupTypeMockMvc
        .perform(get(ENTITY_API_URL_ID, groupType.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.id").value(groupType.getId().intValue()))
        .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
        .andExpect(
            jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
        .andExpect(jsonPath("$.translationKey").value(DEFAULT_TRANSLATION_KEY))
        .andExpect(jsonPath("$.entityName").value(DEFAULT_ENTITY_NAME))
        .andExpect(jsonPath("$.formName").value(DEFAULT_FORM_NAME))
        .andExpect(jsonPath("$.abbreviation").value(DEFAULT_ABBREVIATION));
  }

  @Test
  @Transactional
  void getNonExistingGroupType() throws Exception {
    // Get the groupType
    restGroupTypeMockMvc
        .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
        .andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewGroupType() throws Exception {
    // Initialize the database
    groupTypeRepository.saveAndFlush(groupType);

    int databaseSizeBeforeUpdate = groupTypeRepository.findAll().size();

    // Update the groupType
    GroupType updatedGroupType = groupTypeRepository
        .findById(groupType.getId())
        .orElseThrow();
    // Disconnect from session so that the updates on updatedGroupType are not
    // directly saved in db
    em.detach(updatedGroupType);
    updatedGroupType
        .name(UPDATED_NAME)
        .createdDate(UPDATED_CREATED_DATE)
        .translationKey(UPDATED_TRANSLATION_KEY)
        .entityName(UPDATED_ENTITY_NAME)
        .formName(UPDATED_FORM_NAME)
        .abbreviation(UPDATED_ABBREVIATION);

    restGroupTypeMockMvc
        .perform(
            put(ENTITY_API_URL_ID, updatedGroupType.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(updatedGroupType)))
        .andExpect(status().isOk());

    // Validate the GroupType in the database
    List<GroupType> groupTypeList = groupTypeRepository.findAll();
    assertThat(groupTypeList).hasSize(databaseSizeBeforeUpdate);
    GroupType testGroupType = groupTypeList.get(groupTypeList.size() - 1);
    assertThat(testGroupType.getName()).isEqualTo(UPDATED_NAME);
    assertThat(testGroupType.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    assertThat(testGroupType.getTranslationKey())
        .isEqualTo(UPDATED_TRANSLATION_KEY);
    assertThat(testGroupType.getEntityName()).isEqualTo(UPDATED_ENTITY_NAME);
    assertThat(testGroupType.getFormName()).isEqualTo(UPDATED_FORM_NAME);
    assertThat(testGroupType.getAbbreviation()).isEqualTo(UPDATED_ABBREVIATION);
  }

  @Test
  @Transactional
  void putNonExistingGroupType() throws Exception {
    int databaseSizeBeforeUpdate = groupTypeRepository.findAll().size();
    groupType.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restGroupTypeMockMvc
        .perform(
            put(ENTITY_API_URL_ID, groupType.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(groupType)))
        .andExpect(status().isBadRequest());

    // Validate the GroupType in the database
    List<GroupType> groupTypeList = groupTypeRepository.findAll();
    assertThat(groupTypeList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchGroupType() throws Exception {
    int databaseSizeBeforeUpdate = groupTypeRepository.findAll().size();
    groupType.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restGroupTypeMockMvc
        .perform(
            put(ENTITY_API_URL_ID, count.incrementAndGet())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(groupType)))
        .andExpect(status().isBadRequest());

    // Validate the GroupType in the database
    List<GroupType> groupTypeList = groupTypeRepository.findAll();
    assertThat(groupTypeList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamGroupType() throws Exception {
    int databaseSizeBeforeUpdate = groupTypeRepository.findAll().size();
    groupType.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restGroupTypeMockMvc
        .perform(
            put(ENTITY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(groupType)))
        .andExpect(status().isMethodNotAllowed());

    // Validate the GroupType in the database
    List<GroupType> groupTypeList = groupTypeRepository.findAll();
    assertThat(groupTypeList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateGroupTypeWithPatch() throws Exception {
    // Initialize the database
    groupTypeRepository.saveAndFlush(groupType);

    int databaseSizeBeforeUpdate = groupTypeRepository.findAll().size();

    // Update the groupType using partial update
    GroupType partialUpdatedGroupType = new GroupType();
    partialUpdatedGroupType.setId(groupType.getId());

    partialUpdatedGroupType
        .name(UPDATED_NAME)
        .translationKey(UPDATED_TRANSLATION_KEY)
        .entityName(UPDATED_ENTITY_NAME);

    restGroupTypeMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, partialUpdatedGroupType.getId())
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGroupType)))
        .andExpect(status().isOk());

    // Validate the GroupType in the database
    List<GroupType> groupTypeList = groupTypeRepository.findAll();
    assertThat(groupTypeList).hasSize(databaseSizeBeforeUpdate);
    GroupType testGroupType = groupTypeList.get(groupTypeList.size() - 1);
    assertThat(testGroupType.getName()).isEqualTo(UPDATED_NAME);
    assertThat(testGroupType.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    assertThat(testGroupType.getTranslationKey())
        .isEqualTo(UPDATED_TRANSLATION_KEY);
    assertThat(testGroupType.getEntityName()).isEqualTo(UPDATED_ENTITY_NAME);
    assertThat(testGroupType.getFormName()).isEqualTo(DEFAULT_FORM_NAME);
    assertThat(testGroupType.getAbbreviation()).isEqualTo(DEFAULT_ABBREVIATION);
  }

  @Test
  @Transactional
  void fullUpdateGroupTypeWithPatch() throws Exception {
    // Initialize the database
    groupTypeRepository.saveAndFlush(groupType);

    int databaseSizeBeforeUpdate = groupTypeRepository.findAll().size();

    // Update the groupType using partial update
    GroupType partialUpdatedGroupType = new GroupType();
    partialUpdatedGroupType.setId(groupType.getId());

    partialUpdatedGroupType
        .name(UPDATED_NAME)
        .createdDate(UPDATED_CREATED_DATE)
        .translationKey(UPDATED_TRANSLATION_KEY)
        .entityName(UPDATED_ENTITY_NAME)
        .formName(UPDATED_FORM_NAME)
        .abbreviation(UPDATED_ABBREVIATION);

    restGroupTypeMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, partialUpdatedGroupType.getId())
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGroupType)))
        .andExpect(status().isOk());

    // Validate the GroupType in the database
    List<GroupType> groupTypeList = groupTypeRepository.findAll();
    assertThat(groupTypeList).hasSize(databaseSizeBeforeUpdate);
    GroupType testGroupType = groupTypeList.get(groupTypeList.size() - 1);
    assertThat(testGroupType.getName()).isEqualTo(UPDATED_NAME);
    assertThat(testGroupType.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    assertThat(testGroupType.getTranslationKey())
        .isEqualTo(UPDATED_TRANSLATION_KEY);
    assertThat(testGroupType.getEntityName()).isEqualTo(UPDATED_ENTITY_NAME);
    assertThat(testGroupType.getFormName()).isEqualTo(UPDATED_FORM_NAME);
    assertThat(testGroupType.getAbbreviation()).isEqualTo(UPDATED_ABBREVIATION);
  }

  @Test
  @Transactional
  void patchNonExistingGroupType() throws Exception {
    int databaseSizeBeforeUpdate = groupTypeRepository.findAll().size();
    groupType.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restGroupTypeMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, groupType.getId())
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(groupType)))
        .andExpect(status().isBadRequest());

    // Validate the GroupType in the database
    List<GroupType> groupTypeList = groupTypeRepository.findAll();
    assertThat(groupTypeList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchGroupType() throws Exception {
    int databaseSizeBeforeUpdate = groupTypeRepository.findAll().size();
    groupType.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restGroupTypeMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, count.incrementAndGet())
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(groupType)))
        .andExpect(status().isBadRequest());

    // Validate the GroupType in the database
    List<GroupType> groupTypeList = groupTypeRepository.findAll();
    assertThat(groupTypeList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamGroupType() throws Exception {
    int databaseSizeBeforeUpdate = groupTypeRepository.findAll().size();
    groupType.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restGroupTypeMockMvc
        .perform(
            patch(ENTITY_API_URL)
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(groupType)))
        .andExpect(status().isMethodNotAllowed());

    // Validate the GroupType in the database
    List<GroupType> groupTypeList = groupTypeRepository.findAll();
    assertThat(groupTypeList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteGroupType() throws Exception {
    // Initialize the database
    groupTypeRepository.saveAndFlush(groupType);

    int databaseSizeBeforeDelete = groupTypeRepository.findAll().size();

    // Delete the groupType
    restGroupTypeMockMvc
        .perform(
            delete(ENTITY_API_URL_ID, groupType.getId())
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<GroupType> groupTypeList = groupTypeRepository.findAll();
    assertThat(groupTypeList).hasSize(databaseSizeBeforeDelete - 1);
  }
}
