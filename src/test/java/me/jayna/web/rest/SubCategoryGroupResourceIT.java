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
import me.jayna.domain.SubCategoryGroup;
import me.jayna.repository.SubCategoryGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SubCategoryGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubCategoryGroupResourceIT {

  private static final String DEFAULT_NAME = "AAAAAAAAAA";
  private static final String UPDATED_NAME = "BBBBBBBBBB";

  private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(
      0L);
  private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(
      ZoneId.systemDefault());

  private static final String DEFAULT_TRANSLATION_KEY = "AAAAAAAAAA";
  private static final String UPDATED_TRANSLATION_KEY = "BBBBBBBBBB";

  private static final String ENTITY_API_URL = "/api/sub-category-groups";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(
      random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private SubCategoryGroupRepository subCategoryGroupRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restSubCategoryGroupMockMvc;

  private SubCategoryGroup subCategoryGroup;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static SubCategoryGroup createEntity(EntityManager em) {
    SubCategoryGroup subCategoryGroup = new SubCategoryGroup()
        .name(DEFAULT_NAME)
        .createdDate(DEFAULT_CREATED_DATE)
        .translationKey(DEFAULT_TRANSLATION_KEY);
    return subCategoryGroup;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static SubCategoryGroup createUpdatedEntity(EntityManager em) {
    SubCategoryGroup subCategoryGroup = new SubCategoryGroup()
        .name(UPDATED_NAME)
        .createdDate(UPDATED_CREATED_DATE)
        .translationKey(UPDATED_TRANSLATION_KEY);
    return subCategoryGroup;
  }

  @BeforeEach
  public void initTest() {
    subCategoryGroup = createEntity(em);
  }

  @Test
  @Transactional
  void createSubCategoryGroup() throws Exception {
    int databaseSizeBeforeCreate = subCategoryGroupRepository.findAll().size();
    // Create the SubCategoryGroup
    restSubCategoryGroupMockMvc
        .perform(
            post(ENTITY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(subCategoryGroup)))
        .andExpect(status().isCreated());

    // Validate the SubCategoryGroup in the database
    List<SubCategoryGroup> subCategoryGroupList = subCategoryGroupRepository.findAll();
    assertThat(subCategoryGroupList).hasSize(databaseSizeBeforeCreate + 1);
    SubCategoryGroup testSubCategoryGroup = subCategoryGroupList.get(
        subCategoryGroupList.size() - 1);
    assertThat(testSubCategoryGroup.getName()).isEqualTo(DEFAULT_NAME);
    assertThat(testSubCategoryGroup.getCreatedDate())
        .isEqualTo(DEFAULT_CREATED_DATE);
    assertThat(testSubCategoryGroup.getTranslationKey())
        .isEqualTo(DEFAULT_TRANSLATION_KEY);
  }

  @Test
  @Transactional
  void createSubCategoryGroupWithExistingId() throws Exception {
    // Create the SubCategoryGroup with an existing ID
    subCategoryGroup.setId(1L);

    int databaseSizeBeforeCreate = subCategoryGroupRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restSubCategoryGroupMockMvc
        .perform(
            post(ENTITY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(subCategoryGroup)))
        .andExpect(status().isBadRequest());

    // Validate the SubCategoryGroup in the database
    List<SubCategoryGroup> subCategoryGroupList = subCategoryGroupRepository.findAll();
    assertThat(subCategoryGroupList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void getAllSubCategoryGroups() throws Exception {
    // Initialize the database
    subCategoryGroupRepository.saveAndFlush(subCategoryGroup);

    // Get all the subCategoryGroupList
    restSubCategoryGroupMockMvc
        .perform(get(ENTITY_API_URL + "?sort=id,desc"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(
            jsonPath("$.[*].id").value(hasItem(subCategoryGroup.getId().intValue())))
        .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
        .andExpect(
            jsonPath("$.[*].createdDate")
                .value(hasItem(DEFAULT_CREATED_DATE.toString())))
        .andExpect(
            jsonPath("$.[*].translationKey").value(hasItem(DEFAULT_TRANSLATION_KEY)));
  }

  @Test
  @Transactional
  void getSubCategoryGroup() throws Exception {
    // Initialize the database
    subCategoryGroupRepository.saveAndFlush(subCategoryGroup);

    // Get the subCategoryGroup
    restSubCategoryGroupMockMvc
        .perform(get(ENTITY_API_URL_ID, subCategoryGroup.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.id").value(subCategoryGroup.getId().intValue()))
        .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
        .andExpect(
            jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
        .andExpect(jsonPath("$.translationKey").value(DEFAULT_TRANSLATION_KEY));
  }

  @Test
  @Transactional
  void getNonExistingSubCategoryGroup() throws Exception {
    // Get the subCategoryGroup
    restSubCategoryGroupMockMvc
        .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
        .andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewSubCategoryGroup() throws Exception {
    // Initialize the database
    subCategoryGroupRepository.saveAndFlush(subCategoryGroup);

    int databaseSizeBeforeUpdate = subCategoryGroupRepository.findAll().size();

    // Update the subCategoryGroup
    SubCategoryGroup updatedSubCategoryGroup = subCategoryGroupRepository
        .findById(subCategoryGroup.getId())
        .orElseThrow();
    // Disconnect from session so that the updates on updatedSubCategoryGroup are
    // not directly saved in db
    em.detach(updatedSubCategoryGroup);
    updatedSubCategoryGroup
        .name(UPDATED_NAME)
        .createdDate(UPDATED_CREATED_DATE)
        .translationKey(UPDATED_TRANSLATION_KEY);

    restSubCategoryGroupMockMvc
        .perform(
            put(ENTITY_API_URL_ID, updatedSubCategoryGroup.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(updatedSubCategoryGroup)))
        .andExpect(status().isOk());

    // Validate the SubCategoryGroup in the database
    List<SubCategoryGroup> subCategoryGroupList = subCategoryGroupRepository.findAll();
    assertThat(subCategoryGroupList).hasSize(databaseSizeBeforeUpdate);
    SubCategoryGroup testSubCategoryGroup = subCategoryGroupList.get(
        subCategoryGroupList.size() - 1);
    assertThat(testSubCategoryGroup.getName()).isEqualTo(UPDATED_NAME);
    assertThat(testSubCategoryGroup.getCreatedDate())
        .isEqualTo(UPDATED_CREATED_DATE);
    assertThat(testSubCategoryGroup.getTranslationKey())
        .isEqualTo(UPDATED_TRANSLATION_KEY);
  }

  @Test
  @Transactional
  void putNonExistingSubCategoryGroup() throws Exception {
    int databaseSizeBeforeUpdate = subCategoryGroupRepository.findAll().size();
    subCategoryGroup.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restSubCategoryGroupMockMvc
        .perform(
            put(ENTITY_API_URL_ID, subCategoryGroup.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(subCategoryGroup)))
        .andExpect(status().isBadRequest());

    // Validate the SubCategoryGroup in the database
    List<SubCategoryGroup> subCategoryGroupList = subCategoryGroupRepository.findAll();
    assertThat(subCategoryGroupList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchSubCategoryGroup() throws Exception {
    int databaseSizeBeforeUpdate = subCategoryGroupRepository.findAll().size();
    subCategoryGroup.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restSubCategoryGroupMockMvc
        .perform(
            put(ENTITY_API_URL_ID, count.incrementAndGet())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(subCategoryGroup)))
        .andExpect(status().isBadRequest());

    // Validate the SubCategoryGroup in the database
    List<SubCategoryGroup> subCategoryGroupList = subCategoryGroupRepository.findAll();
    assertThat(subCategoryGroupList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamSubCategoryGroup() throws Exception {
    int databaseSizeBeforeUpdate = subCategoryGroupRepository.findAll().size();
    subCategoryGroup.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restSubCategoryGroupMockMvc
        .perform(
            put(ENTITY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(subCategoryGroup)))
        .andExpect(status().isMethodNotAllowed());

    // Validate the SubCategoryGroup in the database
    List<SubCategoryGroup> subCategoryGroupList = subCategoryGroupRepository.findAll();
    assertThat(subCategoryGroupList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateSubCategoryGroupWithPatch() throws Exception {
    // Initialize the database
    subCategoryGroupRepository.saveAndFlush(subCategoryGroup);

    int databaseSizeBeforeUpdate = subCategoryGroupRepository.findAll().size();

    // Update the subCategoryGroup using partial update
    SubCategoryGroup partialUpdatedSubCategoryGroup = new SubCategoryGroup();
    partialUpdatedSubCategoryGroup.setId(subCategoryGroup.getId());

    partialUpdatedSubCategoryGroup
        .name(UPDATED_NAME)
        .createdDate(UPDATED_CREATED_DATE);

    restSubCategoryGroupMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, partialUpdatedSubCategoryGroup.getId())
                .contentType("application/merge-patch+json")
                .content(
                    TestUtil.convertObjectToJsonBytes(partialUpdatedSubCategoryGroup)))
        .andExpect(status().isOk());

    // Validate the SubCategoryGroup in the database
    List<SubCategoryGroup> subCategoryGroupList = subCategoryGroupRepository.findAll();
    assertThat(subCategoryGroupList).hasSize(databaseSizeBeforeUpdate);
    SubCategoryGroup testSubCategoryGroup = subCategoryGroupList.get(
        subCategoryGroupList.size() - 1);
    assertThat(testSubCategoryGroup.getName()).isEqualTo(UPDATED_NAME);
    assertThat(testSubCategoryGroup.getCreatedDate())
        .isEqualTo(UPDATED_CREATED_DATE);
    assertThat(testSubCategoryGroup.getTranslationKey())
        .isEqualTo(DEFAULT_TRANSLATION_KEY);
  }

  @Test
  @Transactional
  void fullUpdateSubCategoryGroupWithPatch() throws Exception {
    // Initialize the database
    subCategoryGroupRepository.saveAndFlush(subCategoryGroup);

    int databaseSizeBeforeUpdate = subCategoryGroupRepository.findAll().size();

    // Update the subCategoryGroup using partial update
    SubCategoryGroup partialUpdatedSubCategoryGroup = new SubCategoryGroup();
    partialUpdatedSubCategoryGroup.setId(subCategoryGroup.getId());

    partialUpdatedSubCategoryGroup
        .name(UPDATED_NAME)
        .createdDate(UPDATED_CREATED_DATE)
        .translationKey(UPDATED_TRANSLATION_KEY);

    restSubCategoryGroupMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, partialUpdatedSubCategoryGroup.getId())
                .contentType("application/merge-patch+json")
                .content(
                    TestUtil.convertObjectToJsonBytes(partialUpdatedSubCategoryGroup)))
        .andExpect(status().isOk());

    // Validate the SubCategoryGroup in the database
    List<SubCategoryGroup> subCategoryGroupList = subCategoryGroupRepository.findAll();
    assertThat(subCategoryGroupList).hasSize(databaseSizeBeforeUpdate);
    SubCategoryGroup testSubCategoryGroup = subCategoryGroupList.get(
        subCategoryGroupList.size() - 1);
    assertThat(testSubCategoryGroup.getName()).isEqualTo(UPDATED_NAME);
    assertThat(testSubCategoryGroup.getCreatedDate())
        .isEqualTo(UPDATED_CREATED_DATE);
    assertThat(testSubCategoryGroup.getTranslationKey())
        .isEqualTo(UPDATED_TRANSLATION_KEY);
  }

  @Test
  @Transactional
  void patchNonExistingSubCategoryGroup() throws Exception {
    int databaseSizeBeforeUpdate = subCategoryGroupRepository.findAll().size();
    subCategoryGroup.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restSubCategoryGroupMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, subCategoryGroup.getId())
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(subCategoryGroup)))
        .andExpect(status().isBadRequest());

    // Validate the SubCategoryGroup in the database
    List<SubCategoryGroup> subCategoryGroupList = subCategoryGroupRepository.findAll();
    assertThat(subCategoryGroupList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchSubCategoryGroup() throws Exception {
    int databaseSizeBeforeUpdate = subCategoryGroupRepository.findAll().size();
    subCategoryGroup.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restSubCategoryGroupMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, count.incrementAndGet())
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(subCategoryGroup)))
        .andExpect(status().isBadRequest());

    // Validate the SubCategoryGroup in the database
    List<SubCategoryGroup> subCategoryGroupList = subCategoryGroupRepository.findAll();
    assertThat(subCategoryGroupList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamSubCategoryGroup() throws Exception {
    int databaseSizeBeforeUpdate = subCategoryGroupRepository.findAll().size();
    subCategoryGroup.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restSubCategoryGroupMockMvc
        .perform(
            patch(ENTITY_API_URL)
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(subCategoryGroup)))
        .andExpect(status().isMethodNotAllowed());

    // Validate the SubCategoryGroup in the database
    List<SubCategoryGroup> subCategoryGroupList = subCategoryGroupRepository.findAll();
    assertThat(subCategoryGroupList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteSubCategoryGroup() throws Exception {
    // Initialize the database
    subCategoryGroupRepository.saveAndFlush(subCategoryGroup);

    int databaseSizeBeforeDelete = subCategoryGroupRepository.findAll().size();

    // Delete the subCategoryGroup
    restSubCategoryGroupMockMvc
        .perform(
            delete(ENTITY_API_URL_ID, subCategoryGroup.getId())
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<SubCategoryGroup> subCategoryGroupList = subCategoryGroupRepository.findAll();
    assertThat(subCategoryGroupList).hasSize(databaseSizeBeforeDelete - 1);
  }
}
