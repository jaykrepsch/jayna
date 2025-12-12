package me.jayna.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import me.jayna.IntegrationTest;
import me.jayna.domain.ContractRealestate;
import me.jayna.repository.ContractRealestateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ContractRealestateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContractRealestateResourceIT {

  private static final String ENTITY_API_URL = "/api/contract-realestates";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(
      random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private ContractRealestateRepository contractRealestateRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restContractRealestateMockMvc;

  private ContractRealestate contractRealestate;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static ContractRealestate createEntity(EntityManager em) {
    ContractRealestate contractRealestate = new ContractRealestate();
    return contractRealestate;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static ContractRealestate createUpdatedEntity(EntityManager em) {
    ContractRealestate contractRealestate = new ContractRealestate();
    return contractRealestate;
  }

  @BeforeEach
  public void initTest() {
    contractRealestate = createEntity(em);
  }

  @Test
  @Transactional
  void createContractRealestate() throws Exception {
    int databaseSizeBeforeCreate = contractRealestateRepository
        .findAll()
        .size();
    // Create the ContractRealestate
    restContractRealestateMockMvc
        .perform(
            post(ENTITY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(contractRealestate)))
        .andExpect(status().isCreated());

    // Validate the ContractRealestate in the database
    List<ContractRealestate> contractRealestateList = contractRealestateRepository.findAll();
    assertThat(contractRealestateList).hasSize(databaseSizeBeforeCreate + 1);
    ContractRealestate testContractRealestate = contractRealestateList.get(
        contractRealestateList.size() - 1);
  }

  @Test
  @Transactional
  void createContractRealestateWithExistingId() throws Exception {
    // Create the ContractRealestate with an existing ID
    contractRealestate.setId(1L);

    int databaseSizeBeforeCreate = contractRealestateRepository
        .findAll()
        .size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restContractRealestateMockMvc
        .perform(
            post(ENTITY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(contractRealestate)))
        .andExpect(status().isBadRequest());

    // Validate the ContractRealestate in the database
    List<ContractRealestate> contractRealestateList = contractRealestateRepository.findAll();
    assertThat(contractRealestateList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void getAllContractRealestates() throws Exception {
    // Initialize the database
    contractRealestateRepository.saveAndFlush(contractRealestate);

    // Get all the contractRealestateList
    restContractRealestateMockMvc
        .perform(get(ENTITY_API_URL + "?sort=id,desc"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(
            jsonPath("$.[*].id")
                .value(hasItem(contractRealestate.getId().intValue())));
  }

  @Test
  @Transactional
  void getContractRealestate() throws Exception {
    // Initialize the database
    contractRealestateRepository.saveAndFlush(contractRealestate);

    // Get the contractRealestate
    restContractRealestateMockMvc
        .perform(get(ENTITY_API_URL_ID, contractRealestate.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.id").value(contractRealestate.getId().intValue()));
  }

  @Test
  @Transactional
  void getNonExistingContractRealestate() throws Exception {
    // Get the contractRealestate
    restContractRealestateMockMvc
        .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
        .andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewContractRealestate() throws Exception {
    // Initialize the database
    contractRealestateRepository.saveAndFlush(contractRealestate);

    int databaseSizeBeforeUpdate = contractRealestateRepository
        .findAll()
        .size();

    // Update the contractRealestate
    ContractRealestate updatedContractRealestate = contractRealestateRepository
        .findById(contractRealestate.getId())
        .orElseThrow();
    // Disconnect from session so that the updates on updatedContractRealestate are
    // not directly saved in db
    em.detach(updatedContractRealestate);

    restContractRealestateMockMvc
        .perform(
            put(ENTITY_API_URL_ID, updatedContractRealestate.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(updatedContractRealestate)))
        .andExpect(status().isOk());

    // Validate the ContractRealestate in the database
    List<ContractRealestate> contractRealestateList = contractRealestateRepository.findAll();
    assertThat(contractRealestateList).hasSize(databaseSizeBeforeUpdate);
    ContractRealestate testContractRealestate = contractRealestateList.get(
        contractRealestateList.size() - 1);
  }

  @Test
  @Transactional
  void putNonExistingContractRealestate() throws Exception {
    int databaseSizeBeforeUpdate = contractRealestateRepository
        .findAll()
        .size();
    contractRealestate.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restContractRealestateMockMvc
        .perform(
            put(ENTITY_API_URL_ID, contractRealestate.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(contractRealestate)))
        .andExpect(status().isBadRequest());

    // Validate the ContractRealestate in the database
    List<ContractRealestate> contractRealestateList = contractRealestateRepository.findAll();
    assertThat(contractRealestateList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchContractRealestate() throws Exception {
    int databaseSizeBeforeUpdate = contractRealestateRepository
        .findAll()
        .size();
    contractRealestate.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restContractRealestateMockMvc
        .perform(
            put(ENTITY_API_URL_ID, count.incrementAndGet())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(contractRealestate)))
        .andExpect(status().isBadRequest());

    // Validate the ContractRealestate in the database
    List<ContractRealestate> contractRealestateList = contractRealestateRepository.findAll();
    assertThat(contractRealestateList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamContractRealestate() throws Exception {
    int databaseSizeBeforeUpdate = contractRealestateRepository
        .findAll()
        .size();
    contractRealestate.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restContractRealestateMockMvc
        .perform(
            put(ENTITY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(contractRealestate)))
        .andExpect(status().isMethodNotAllowed());

    // Validate the ContractRealestate in the database
    List<ContractRealestate> contractRealestateList = contractRealestateRepository.findAll();
    assertThat(contractRealestateList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateContractRealestateWithPatch() throws Exception {
    // Initialize the database
    contractRealestateRepository.saveAndFlush(contractRealestate);

    int databaseSizeBeforeUpdate = contractRealestateRepository
        .findAll()
        .size();

    // Update the contractRealestate using partial update
    ContractRealestate partialUpdatedContractRealestate = new ContractRealestate();
    partialUpdatedContractRealestate.setId(contractRealestate.getId());

    restContractRealestateMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, partialUpdatedContractRealestate.getId())
                .contentType("application/merge-patch+json")
                .content(
                    TestUtil.convertObjectToJsonBytes(partialUpdatedContractRealestate)))
        .andExpect(status().isOk());

    // Validate the ContractRealestate in the database
    List<ContractRealestate> contractRealestateList = contractRealestateRepository.findAll();
    assertThat(contractRealestateList).hasSize(databaseSizeBeforeUpdate);
    ContractRealestate testContractRealestate = contractRealestateList.get(
        contractRealestateList.size() - 1);
  }

  @Test
  @Transactional
  void fullUpdateContractRealestateWithPatch() throws Exception {
    // Initialize the database
    contractRealestateRepository.saveAndFlush(contractRealestate);

    int databaseSizeBeforeUpdate = contractRealestateRepository
        .findAll()
        .size();

    // Update the contractRealestate using partial update
    ContractRealestate partialUpdatedContractRealestate = new ContractRealestate();
    partialUpdatedContractRealestate.setId(contractRealestate.getId());

    restContractRealestateMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, partialUpdatedContractRealestate.getId())
                .contentType("application/merge-patch+json")
                .content(
                    TestUtil.convertObjectToJsonBytes(partialUpdatedContractRealestate)))
        .andExpect(status().isOk());

    // Validate the ContractRealestate in the database
    List<ContractRealestate> contractRealestateList = contractRealestateRepository.findAll();
    assertThat(contractRealestateList).hasSize(databaseSizeBeforeUpdate);
    ContractRealestate testContractRealestate = contractRealestateList.get(
        contractRealestateList.size() - 1);
  }

  @Test
  @Transactional
  void patchNonExistingContractRealestate() throws Exception {
    int databaseSizeBeforeUpdate = contractRealestateRepository
        .findAll()
        .size();
    contractRealestate.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restContractRealestateMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, contractRealestate.getId())
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(contractRealestate)))
        .andExpect(status().isBadRequest());

    // Validate the ContractRealestate in the database
    List<ContractRealestate> contractRealestateList = contractRealestateRepository.findAll();
    assertThat(contractRealestateList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchContractRealestate() throws Exception {
    int databaseSizeBeforeUpdate = contractRealestateRepository
        .findAll()
        .size();
    contractRealestate.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restContractRealestateMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, count.incrementAndGet())
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(contractRealestate)))
        .andExpect(status().isBadRequest());

    // Validate the ContractRealestate in the database
    List<ContractRealestate> contractRealestateList = contractRealestateRepository.findAll();
    assertThat(contractRealestateList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamContractRealestate() throws Exception {
    int databaseSizeBeforeUpdate = contractRealestateRepository
        .findAll()
        .size();
    contractRealestate.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restContractRealestateMockMvc
        .perform(
            patch(ENTITY_API_URL)
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(contractRealestate)))
        .andExpect(status().isMethodNotAllowed());

    // Validate the ContractRealestate in the database
    List<ContractRealestate> contractRealestateList = contractRealestateRepository.findAll();
    assertThat(contractRealestateList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteContractRealestate() throws Exception {
    // Initialize the database
    contractRealestateRepository.saveAndFlush(contractRealestate);

    int databaseSizeBeforeDelete = contractRealestateRepository
        .findAll()
        .size();

    // Delete the contractRealestate
    restContractRealestateMockMvc
        .perform(
            delete(ENTITY_API_URL_ID, contractRealestate.getId())
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<ContractRealestate> contractRealestateList = contractRealestateRepository.findAll();
    assertThat(contractRealestateList).hasSize(databaseSizeBeforeDelete - 1);
  }
}
