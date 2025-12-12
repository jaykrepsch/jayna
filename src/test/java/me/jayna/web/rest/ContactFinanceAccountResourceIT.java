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
import me.jayna.domain.ContactFinanceAccount;
import me.jayna.repository.ContactFinanceAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ContactFinanceAccountResource} REST
 * controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContactFinanceAccountResourceIT {

  private static final String ENTITY_API_URL = "/api/contact-finance-accounts";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(
      random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private ContactFinanceAccountRepository contactFinanceAccountRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restContactFinanceAccountMockMvc;

  private ContactFinanceAccount contactFinanceAccount;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static ContactFinanceAccount createEntity(EntityManager em) {
    ContactFinanceAccount contactFinanceAccount = new ContactFinanceAccount();
    return contactFinanceAccount;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static ContactFinanceAccount createUpdatedEntity(EntityManager em) {
    ContactFinanceAccount contactFinanceAccount = new ContactFinanceAccount();
    return contactFinanceAccount;
  }

  @BeforeEach
  public void initTest() {
    contactFinanceAccount = createEntity(em);
  }

  @Test
  @Transactional
  void createContactFinanceAccount() throws Exception {
    int databaseSizeBeforeCreate = contactFinanceAccountRepository
        .findAll()
        .size();
    // Create the ContactFinanceAccount
    restContactFinanceAccountMockMvc
        .perform(
            post(ENTITY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(contactFinanceAccount)))
        .andExpect(status().isCreated());

    // Validate the ContactFinanceAccount in the database
    List<ContactFinanceAccount> contactFinanceAccountList = contactFinanceAccountRepository.findAll();
    assertThat(contactFinanceAccountList).hasSize(databaseSizeBeforeCreate + 1);
    ContactFinanceAccount testContactFinanceAccount = contactFinanceAccountList.get(
        contactFinanceAccountList.size() - 1);
  }

  @Test
  @Transactional
  void createContactFinanceAccountWithExistingId() throws Exception {
    // Create the ContactFinanceAccount with an existing ID
    contactFinanceAccount.setId(1L);

    int databaseSizeBeforeCreate = contactFinanceAccountRepository
        .findAll()
        .size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restContactFinanceAccountMockMvc
        .perform(
            post(ENTITY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(contactFinanceAccount)))
        .andExpect(status().isBadRequest());

    // Validate the ContactFinanceAccount in the database
    List<ContactFinanceAccount> contactFinanceAccountList = contactFinanceAccountRepository.findAll();
    assertThat(contactFinanceAccountList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void getAllContactFinanceAccounts() throws Exception {
    // Initialize the database
    contactFinanceAccountRepository.saveAndFlush(contactFinanceAccount);

    // Get all the contactFinanceAccountList
    restContactFinanceAccountMockMvc
        .perform(get(ENTITY_API_URL + "?sort=id,desc"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(
            jsonPath("$.[*].id")
                .value(hasItem(contactFinanceAccount.getId().intValue())));
  }

  @Test
  @Transactional
  void getContactFinanceAccount() throws Exception {
    // Initialize the database
    contactFinanceAccountRepository.saveAndFlush(contactFinanceAccount);

    // Get the contactFinanceAccount
    restContactFinanceAccountMockMvc
        .perform(get(ENTITY_API_URL_ID, contactFinanceAccount.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(
            jsonPath("$.id").value(contactFinanceAccount.getId().intValue()));
  }

  @Test
  @Transactional
  void getNonExistingContactFinanceAccount() throws Exception {
    // Get the contactFinanceAccount
    restContactFinanceAccountMockMvc
        .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
        .andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewContactFinanceAccount() throws Exception {
    // Initialize the database
    contactFinanceAccountRepository.saveAndFlush(contactFinanceAccount);

    int databaseSizeBeforeUpdate = contactFinanceAccountRepository
        .findAll()
        .size();

    // Update the contactFinanceAccount
    ContactFinanceAccount updatedContactFinanceAccount = contactFinanceAccountRepository
        .findById(contactFinanceAccount.getId())
        .orElseThrow();
    // Disconnect from session so that the updates on updatedContactFinanceAccount
    // are not directly saved in db
    em.detach(updatedContactFinanceAccount);

    restContactFinanceAccountMockMvc
        .perform(
            put(ENTITY_API_URL_ID, updatedContactFinanceAccount.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    TestUtil.convertObjectToJsonBytes(updatedContactFinanceAccount)))
        .andExpect(status().isOk());

    // Validate the ContactFinanceAccount in the database
    List<ContactFinanceAccount> contactFinanceAccountList = contactFinanceAccountRepository.findAll();
    assertThat(contactFinanceAccountList).hasSize(databaseSizeBeforeUpdate);
    ContactFinanceAccount testContactFinanceAccount = contactFinanceAccountList.get(
        contactFinanceAccountList.size() - 1);
  }

  @Test
  @Transactional
  void putNonExistingContactFinanceAccount() throws Exception {
    int databaseSizeBeforeUpdate = contactFinanceAccountRepository
        .findAll()
        .size();
    contactFinanceAccount.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restContactFinanceAccountMockMvc
        .perform(
            put(ENTITY_API_URL_ID, contactFinanceAccount.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(contactFinanceAccount)))
        .andExpect(status().isBadRequest());

    // Validate the ContactFinanceAccount in the database
    List<ContactFinanceAccount> contactFinanceAccountList = contactFinanceAccountRepository.findAll();
    assertThat(contactFinanceAccountList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchContactFinanceAccount() throws Exception {
    int databaseSizeBeforeUpdate = contactFinanceAccountRepository
        .findAll()
        .size();
    contactFinanceAccount.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restContactFinanceAccountMockMvc
        .perform(
            put(ENTITY_API_URL_ID, count.incrementAndGet())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(contactFinanceAccount)))
        .andExpect(status().isBadRequest());

    // Validate the ContactFinanceAccount in the database
    List<ContactFinanceAccount> contactFinanceAccountList = contactFinanceAccountRepository.findAll();
    assertThat(contactFinanceAccountList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamContactFinanceAccount() throws Exception {
    int databaseSizeBeforeUpdate = contactFinanceAccountRepository
        .findAll()
        .size();
    contactFinanceAccount.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restContactFinanceAccountMockMvc
        .perform(
            put(ENTITY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(contactFinanceAccount)))
        .andExpect(status().isMethodNotAllowed());

    // Validate the ContactFinanceAccount in the database
    List<ContactFinanceAccount> contactFinanceAccountList = contactFinanceAccountRepository.findAll();
    assertThat(contactFinanceAccountList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateContactFinanceAccountWithPatch() throws Exception {
    // Initialize the database
    contactFinanceAccountRepository.saveAndFlush(contactFinanceAccount);

    int databaseSizeBeforeUpdate = contactFinanceAccountRepository
        .findAll()
        .size();

    // Update the contactFinanceAccount using partial update
    ContactFinanceAccount partialUpdatedContactFinanceAccount = new ContactFinanceAccount();
    partialUpdatedContactFinanceAccount.setId(contactFinanceAccount.getId());

    restContactFinanceAccountMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, partialUpdatedContactFinanceAccount.getId())
                .contentType("application/merge-patch+json")
                .content(
                    TestUtil.convertObjectToJsonBytes(
                        partialUpdatedContactFinanceAccount)))
        .andExpect(status().isOk());

    // Validate the ContactFinanceAccount in the database
    List<ContactFinanceAccount> contactFinanceAccountList = contactFinanceAccountRepository.findAll();
    assertThat(contactFinanceAccountList).hasSize(databaseSizeBeforeUpdate);
    ContactFinanceAccount testContactFinanceAccount = contactFinanceAccountList.get(
        contactFinanceAccountList.size() - 1);
  }

  @Test
  @Transactional
  void fullUpdateContactFinanceAccountWithPatch() throws Exception {
    // Initialize the database
    contactFinanceAccountRepository.saveAndFlush(contactFinanceAccount);

    int databaseSizeBeforeUpdate = contactFinanceAccountRepository
        .findAll()
        .size();

    // Update the contactFinanceAccount using partial update
    ContactFinanceAccount partialUpdatedContactFinanceAccount = new ContactFinanceAccount();
    partialUpdatedContactFinanceAccount.setId(contactFinanceAccount.getId());

    restContactFinanceAccountMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, partialUpdatedContactFinanceAccount.getId())
                .contentType("application/merge-patch+json")
                .content(
                    TestUtil.convertObjectToJsonBytes(
                        partialUpdatedContactFinanceAccount)))
        .andExpect(status().isOk());

    // Validate the ContactFinanceAccount in the database
    List<ContactFinanceAccount> contactFinanceAccountList = contactFinanceAccountRepository.findAll();
    assertThat(contactFinanceAccountList).hasSize(databaseSizeBeforeUpdate);
    ContactFinanceAccount testContactFinanceAccount = contactFinanceAccountList.get(
        contactFinanceAccountList.size() - 1);
  }

  @Test
  @Transactional
  void patchNonExistingContactFinanceAccount() throws Exception {
    int databaseSizeBeforeUpdate = contactFinanceAccountRepository
        .findAll()
        .size();
    contactFinanceAccount.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restContactFinanceAccountMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, contactFinanceAccount.getId())
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(contactFinanceAccount)))
        .andExpect(status().isBadRequest());

    // Validate the ContactFinanceAccount in the database
    List<ContactFinanceAccount> contactFinanceAccountList = contactFinanceAccountRepository.findAll();
    assertThat(contactFinanceAccountList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchContactFinanceAccount() throws Exception {
    int databaseSizeBeforeUpdate = contactFinanceAccountRepository
        .findAll()
        .size();
    contactFinanceAccount.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restContactFinanceAccountMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, count.incrementAndGet())
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(contactFinanceAccount)))
        .andExpect(status().isBadRequest());

    // Validate the ContactFinanceAccount in the database
    List<ContactFinanceAccount> contactFinanceAccountList = contactFinanceAccountRepository.findAll();
    assertThat(contactFinanceAccountList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamContactFinanceAccount() throws Exception {
    int databaseSizeBeforeUpdate = contactFinanceAccountRepository
        .findAll()
        .size();
    contactFinanceAccount.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restContactFinanceAccountMockMvc
        .perform(
            patch(ENTITY_API_URL)
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(contactFinanceAccount)))
        .andExpect(status().isMethodNotAllowed());

    // Validate the ContactFinanceAccount in the database
    List<ContactFinanceAccount> contactFinanceAccountList = contactFinanceAccountRepository.findAll();
    assertThat(contactFinanceAccountList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteContactFinanceAccount() throws Exception {
    // Initialize the database
    contactFinanceAccountRepository.saveAndFlush(contactFinanceAccount);

    int databaseSizeBeforeDelete = contactFinanceAccountRepository
        .findAll()
        .size();

    // Delete the contactFinanceAccount
    restContactFinanceAccountMockMvc
        .perform(
            delete(ENTITY_API_URL_ID, contactFinanceAccount.getId())
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<ContactFinanceAccount> contactFinanceAccountList = contactFinanceAccountRepository.findAll();
    assertThat(contactFinanceAccountList).hasSize(databaseSizeBeforeDelete - 1);
  }
}
