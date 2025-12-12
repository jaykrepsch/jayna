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
import me.jayna.domain.ApplicationUser;
import me.jayna.domain.Contact;
import me.jayna.domain.ContactFinanceAccount;
import me.jayna.domain.GroupType;
import me.jayna.domain.enumeration.AddressType;
import me.jayna.domain.enumeration.CommunicationType;
import me.jayna.domain.enumeration.Gender;
import me.jayna.domain.enumeration.MaritalStatus;
import me.jayna.domain.enumeration.Rank;
import me.jayna.domain.enumeration.Salutation;
import me.jayna.domain.enumeration.Title;
import me.jayna.repository.ContactRepository;
import me.jayna.service.criteria.ContactCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ContactResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContactResourceIT {

  private static final Title DEFAULT_TITLE = Title.DR;
  private static final Title UPDATED_TITLE = Title.PROF;

  private static final Salutation DEFAULT_SALUTATION = Salutation.MR;
  private static final Salutation UPDATED_SALUTATION = Salutation.MRS;

  private static final Rank DEFAULT_RANK = Rank.COLONEL;
  private static final Rank UPDATED_RANK = Rank.LIEUTENANT;

  private static final Gender DEFAULT_GENDER = Gender.MALE;
  private static final Gender UPDATED_GENDER = Gender.FEMALE;

  private static final String DEFAULT_SUR_NAME = "AAAAAAAAAA";
  private static final String UPDATED_SUR_NAME = "BBBBBBBBBB";

  private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
  private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

  private static final String DEFAULT_OTHER_FIRST_NAMES = "AAAAAAAAAA";
  private static final String UPDATED_OTHER_FIRST_NAMES = "BBBBBBBBBB";

  private static final String DEFAULT_BIRTH_NAME = "AAAAAAAAAA";
  private static final String UPDATED_BIRTH_NAME = "BBBBBBBBBB";

  private static final MaritalStatus DEFAULT_MARITAL_STATUS = MaritalStatus.SINGLE;
  private static final MaritalStatus UPDATED_MARITAL_STATUS = MaritalStatus.MARRIED;

  private static final String DEFAULT_NICK_NAME = "AAAAAAAAAA";
  private static final String UPDATED_NICK_NAME = "BBBBBBBBBB";

  private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
  private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(
      ZoneId.systemDefault());
  private static final LocalDate SMALLER_BIRTH_DATE = LocalDate.ofEpochDay(-1L);

  private static final String DEFAULT_BIRTH_CITY = "AAAAAAAAAA";
  private static final String UPDATED_BIRTH_CITY = "BBBBBBBBBB";

  private static final String DEFAULT_BIRTH_COUNTRY = "AAAAAAAAAA";
  private static final String UPDATED_BIRTH_COUNTRY = "BBBBBBBBBB";

  private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
  private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

  private static final AddressType DEFAULT_ADDRESS_TYPE = AddressType.PRIVATE;
  private static final AddressType UPDATED_ADDRESS_TYPE = AddressType.BUSINESS;

  private static final String DEFAULT_STREET = "AAAAAAAAAA";
  private static final String UPDATED_STREET = "BBBBBBBBBB";

  private static final String DEFAULT_STREET_NUMBER = "AAAAAAAAAA";
  private static final String UPDATED_STREET_NUMBER = "BBBBBBBBBB";

  private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
  private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

  private static final String DEFAULT_CITY = "AAAAAAAAAA";
  private static final String UPDATED_CITY = "BBBBBBBBBB";

  private static final String DEFAULT_ADDITIONAL_ADDRESS_FIELD_1 = "AAAAAAAAAA";
  private static final String UPDATED_ADDITIONAL_ADDRESS_FIELD_1 = "BBBBBBBBBB";

  private static final String DEFAULT_ADDITIONAL_ADDRESS_FIELD_2 = "AAAAAAAAAA";
  private static final String UPDATED_ADDITIONAL_ADDRESS_FIELD_2 = "BBBBBBBBBB";

  private static final CommunicationType DEFAULT_COMMUNICATION_TYPE = CommunicationType.PHONE;
  private static final CommunicationType UPDATED_COMMUNICATION_TYPE = CommunicationType.FAX;

  private static final Integer DEFAULT_PHONE_COUNTRY_CODE = 1;
  private static final Integer UPDATED_PHONE_COUNTRY_CODE = 2;
  private static final Integer SMALLER_PHONE_COUNTRY_CODE = 1 - 1;

  private static final Integer DEFAULT_PHONE_PREFIX = 1;
  private static final Integer UPDATED_PHONE_PREFIX = 2;
  private static final Integer SMALLER_PHONE_PREFIX = 1 - 1;

  private static final Integer DEFAULT_PHONE_NUMBER = 1;
  private static final Integer UPDATED_PHONE_NUMBER = 2;
  private static final Integer SMALLER_PHONE_NUMBER = 1 - 1;

  private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
  private static final String UPDATED_COMMENT = "BBBBBBBBBB";

  private static final String ENTITY_API_URL = "/api/contacts";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(
      random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private ContactRepository contactRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restContactMockMvc;

  private Contact contact;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Contact createEntity(EntityManager em) {
    Contact contact = new Contact()
        .title(DEFAULT_TITLE)
        .salutation(DEFAULT_SALUTATION)
        .rank(DEFAULT_RANK)
        .gender(DEFAULT_GENDER)
        .surName(DEFAULT_SUR_NAME)
        .firstName(DEFAULT_FIRST_NAME)
        .otherFirstNames(DEFAULT_OTHER_FIRST_NAMES)
        .birthName(DEFAULT_BIRTH_NAME)
        .maritalStatus(DEFAULT_MARITAL_STATUS)
        .nickName(DEFAULT_NICK_NAME)
        .birthDate(DEFAULT_BIRTH_DATE)
        .birthCity(DEFAULT_BIRTH_CITY)
        .birthCountry(DEFAULT_BIRTH_COUNTRY)
        .nationality(DEFAULT_NATIONALITY)
        .addressType(DEFAULT_ADDRESS_TYPE)
        .street(DEFAULT_STREET)
        .streetNumber(DEFAULT_STREET_NUMBER)
        .postalCode(DEFAULT_POSTAL_CODE)
        .city(DEFAULT_CITY)
        .additionalAddressField1(DEFAULT_ADDITIONAL_ADDRESS_FIELD_1)
        .additionalAddressField2(DEFAULT_ADDITIONAL_ADDRESS_FIELD_2)
        .communicationType(DEFAULT_COMMUNICATION_TYPE)
        .phoneCountryCode(DEFAULT_PHONE_COUNTRY_CODE)
        .phonePrefix(DEFAULT_PHONE_PREFIX)
        .phoneNumber(DEFAULT_PHONE_NUMBER);
    return contact;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Contact createUpdatedEntity(EntityManager em) {
    Contact contact = new Contact()
        .title(UPDATED_TITLE)
        .salutation(UPDATED_SALUTATION)
        .rank(UPDATED_RANK)
        .gender(UPDATED_GENDER)
        .surName(UPDATED_SUR_NAME)
        .firstName(UPDATED_FIRST_NAME)
        .otherFirstNames(UPDATED_OTHER_FIRST_NAMES)
        .birthName(UPDATED_BIRTH_NAME)
        .maritalStatus(UPDATED_MARITAL_STATUS)
        .nickName(UPDATED_NICK_NAME)
        .birthDate(UPDATED_BIRTH_DATE)
        .birthCity(UPDATED_BIRTH_CITY)
        .birthCountry(UPDATED_BIRTH_COUNTRY)
        .nationality(UPDATED_NATIONALITY)
        .addressType(UPDATED_ADDRESS_TYPE)
        .street(UPDATED_STREET)
        .streetNumber(UPDATED_STREET_NUMBER)
        .postalCode(UPDATED_POSTAL_CODE)
        .city(UPDATED_CITY)
        .additionalAddressField1(UPDATED_ADDITIONAL_ADDRESS_FIELD_1)
        .additionalAddressField2(UPDATED_ADDITIONAL_ADDRESS_FIELD_2)
        .communicationType(UPDATED_COMMUNICATION_TYPE)
        .phoneCountryCode(UPDATED_PHONE_COUNTRY_CODE)
        .phonePrefix(UPDATED_PHONE_PREFIX)
        .phoneNumber(UPDATED_PHONE_NUMBER);
    return contact;
  }

  @BeforeEach
  public void initTest() {
    contact = createEntity(em);
  }

  @Test
  @Transactional
  void createContact() throws Exception {
    int databaseSizeBeforeCreate = contactRepository.findAll().size();
    // Create the Contact
    restContactMockMvc
        .perform(
            post(ENTITY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(contact)))
        .andExpect(status().isCreated());

    // Validate the Contact in the database
    List<Contact> contactList = contactRepository.findAll();
    assertThat(contactList).hasSize(databaseSizeBeforeCreate + 1);
    Contact testContact = contactList.get(contactList.size() - 1);
    assertThat(testContact.getTitle()).isEqualTo(DEFAULT_TITLE);
    assertThat(testContact.getSalutation()).isEqualTo(DEFAULT_SALUTATION);
    assertThat(testContact.getRank()).isEqualTo(DEFAULT_RANK);
    assertThat(testContact.getGender()).isEqualTo(DEFAULT_GENDER);
    assertThat(testContact.getSurName()).isEqualTo(DEFAULT_SUR_NAME);
    assertThat(testContact.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
    assertThat(testContact.getOtherFirstNames())
        .isEqualTo(DEFAULT_OTHER_FIRST_NAMES);
    assertThat(testContact.getBirthName()).isEqualTo(DEFAULT_BIRTH_NAME);
    assertThat(testContact.getMaritalStatus())
        .isEqualTo(DEFAULT_MARITAL_STATUS);
    assertThat(testContact.getNickName()).isEqualTo(DEFAULT_NICK_NAME);
    assertThat(testContact.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
    assertThat(testContact.getBirthCity()).isEqualTo(DEFAULT_BIRTH_CITY);
    assertThat(testContact.getBirthCountry()).isEqualTo(DEFAULT_BIRTH_COUNTRY);
    assertThat(testContact.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
    assertThat(testContact.getAddressType()).isEqualTo(DEFAULT_ADDRESS_TYPE);
    assertThat(testContact.getStreet()).isEqualTo(DEFAULT_STREET);
    assertThat(testContact.getStreetNumber()).isEqualTo(DEFAULT_STREET_NUMBER);
    assertThat(testContact.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
    assertThat(testContact.getCity()).isEqualTo(DEFAULT_CITY);
    assertThat(testContact.getAdditionalAddressField1())
        .isEqualTo(DEFAULT_ADDITIONAL_ADDRESS_FIELD_1);
    assertThat(testContact.getAdditionalAddressField2())
        .isEqualTo(DEFAULT_ADDITIONAL_ADDRESS_FIELD_2);
    assertThat(testContact.getCommunicationType())
        .isEqualTo(DEFAULT_COMMUNICATION_TYPE);
    assertThat(testContact.getPhoneCountryCode())
        .isEqualTo(DEFAULT_PHONE_COUNTRY_CODE);
    assertThat(testContact.getPhonePrefix()).isEqualTo(DEFAULT_PHONE_PREFIX);
    assertThat(testContact.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    assertThat(testContact.getComment()).isEqualTo(DEFAULT_COMMENT);
  }

  @Test
  @Transactional
  void createContactWithExistingId() throws Exception {
    // Create the Contact with an existing ID
    contact.setId(1L);

    int databaseSizeBeforeCreate = contactRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restContactMockMvc
        .perform(
            post(ENTITY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(contact)))
        .andExpect(status().isBadRequest());

    // Validate the Contact in the database
    List<Contact> contactList = contactRepository.findAll();
    assertThat(contactList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void getAllContacts() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList
    restContactMockMvc
        .perform(get(ENTITY_API_URL + "?sort=id,desc"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(
            jsonPath("$.[*].id").value(hasItem(contact.getId().intValue())))
        .andExpect(
            jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
        .andExpect(
            jsonPath("$.[*].salutation")
                .value(hasItem(DEFAULT_SALUTATION.toString())))
        .andExpect(jsonPath("$.[*].rank").value(hasItem(DEFAULT_RANK.toString())))
        .andExpect(
            jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
        .andExpect(jsonPath("$.[*].surName").value(hasItem(DEFAULT_SUR_NAME)))
        .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
        .andExpect(
            jsonPath("$.[*].otherFirstNames")
                .value(hasItem(DEFAULT_OTHER_FIRST_NAMES)))
        .andExpect(jsonPath("$.[*].birthName").value(hasItem(DEFAULT_BIRTH_NAME)))
        .andExpect(
            jsonPath("$.[*].maritalStatus")
                .value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
        .andExpect(jsonPath("$.[*].nickName").value(hasItem(DEFAULT_NICK_NAME)))
        .andExpect(
            jsonPath("$.[*].birthDate")
                .value(hasItem(DEFAULT_BIRTH_DATE.toString())))
        .andExpect(jsonPath("$.[*].birthCity").value(hasItem(DEFAULT_BIRTH_CITY)))
        .andExpect(
            jsonPath("$.[*].birthCountry").value(hasItem(DEFAULT_BIRTH_COUNTRY)))
        .andExpect(
            jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
        .andExpect(
            jsonPath("$.[*].addressType")
                .value(hasItem(DEFAULT_ADDRESS_TYPE.toString())))
        .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
        .andExpect(
            jsonPath("$.[*].streetNumber").value(hasItem(DEFAULT_STREET_NUMBER)))
        .andExpect(
            jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
        .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
        .andExpect(
            jsonPath("$.[*].additionalAddressField1")
                .value(hasItem(DEFAULT_ADDITIONAL_ADDRESS_FIELD_1)))
        .andExpect(
            jsonPath("$.[*].additionalAddressField2")
                .value(hasItem(DEFAULT_ADDITIONAL_ADDRESS_FIELD_2)))
        .andExpect(
            jsonPath("$.[*].communicationType")
                .value(hasItem(DEFAULT_COMMUNICATION_TYPE.toString())))
        .andExpect(
            jsonPath("$.[*].phoneCountryCode")
                .value(hasItem(DEFAULT_PHONE_COUNTRY_CODE)))
        .andExpect(
            jsonPath("$.[*].phonePrefix").value(hasItem(DEFAULT_PHONE_PREFIX)))
        .andExpect(
            jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
        .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));
  }

  @Test
  @Transactional
  void getContact() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get the contact
    restContactMockMvc
        .perform(get(ENTITY_API_URL_ID, contact.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.id").value(contact.getId().intValue()))
        .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
        .andExpect(jsonPath("$.salutation").value(DEFAULT_SALUTATION.toString()))
        .andExpect(jsonPath("$.rank").value(DEFAULT_RANK.toString()))
        .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
        .andExpect(jsonPath("$.surName").value(DEFAULT_SUR_NAME))
        .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
        .andExpect(jsonPath("$.otherFirstNames").value(DEFAULT_OTHER_FIRST_NAMES))
        .andExpect(jsonPath("$.birthName").value(DEFAULT_BIRTH_NAME))
        .andExpect(
            jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()))
        .andExpect(jsonPath("$.nickName").value(DEFAULT_NICK_NAME))
        .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
        .andExpect(jsonPath("$.birthCity").value(DEFAULT_BIRTH_CITY))
        .andExpect(jsonPath("$.birthCountry").value(DEFAULT_BIRTH_COUNTRY))
        .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
        .andExpect(
            jsonPath("$.addressType").value(DEFAULT_ADDRESS_TYPE.toString()))
        .andExpect(jsonPath("$.street").value(DEFAULT_STREET))
        .andExpect(jsonPath("$.streetNumber").value(DEFAULT_STREET_NUMBER))
        .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
        .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
        .andExpect(
            jsonPath("$.additionalAddressField1")
                .value(DEFAULT_ADDITIONAL_ADDRESS_FIELD_1))
        .andExpect(
            jsonPath("$.additionalAddressField2")
                .value(DEFAULT_ADDITIONAL_ADDRESS_FIELD_2))
        .andExpect(
            jsonPath("$.communicationType")
                .value(DEFAULT_COMMUNICATION_TYPE.toString()))
        .andExpect(
            jsonPath("$.phoneCountryCode").value(DEFAULT_PHONE_COUNTRY_CODE))
        .andExpect(jsonPath("$.phonePrefix").value(DEFAULT_PHONE_PREFIX))
        .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
        .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT));
  }

  @Test
  @Transactional
  void getContactsByIdFiltering() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    Long id = contact.getId();

    defaultContactShouldBeFound("id.equals=" + id);
    defaultContactShouldNotBeFound("id.notEquals=" + id);

    defaultContactShouldBeFound("id.greaterThanOrEqual=" + id);
    defaultContactShouldNotBeFound("id.greaterThan=" + id);

    defaultContactShouldBeFound("id.lessThanOrEqual=" + id);
    defaultContactShouldNotBeFound("id.lessThan=" + id);
  }

  @Test
  @Transactional
  void getAllContactsByTitleIsEqualToSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where title equals to DEFAULT_TITLE
    defaultContactShouldBeFound("title.equals=" + DEFAULT_TITLE);

    // Get all the contactList where title equals to UPDATED_TITLE
    defaultContactShouldNotBeFound("title.equals=" + UPDATED_TITLE);
  }

  @Test
  @Transactional
  void getAllContactsByTitleIsInShouldWork() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where title in DEFAULT_TITLE or UPDATED_TITLE
    defaultContactShouldBeFound(
        "title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

    // Get all the contactList where title equals to UPDATED_TITLE
    defaultContactShouldNotBeFound("title.in=" + UPDATED_TITLE);
  }

  @Test
  @Transactional
  void getAllContactsByTitleIsNullOrNotNull() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where title is not null
    defaultContactShouldBeFound("title.specified=true");

    // Get all the contactList where title is null
    defaultContactShouldNotBeFound("title.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsBySalutationIsEqualToSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where salutation equals to DEFAULT_SALUTATION
    defaultContactShouldBeFound("salutation.equals=" + DEFAULT_SALUTATION);

    // Get all the contactList where salutation equals to UPDATED_SALUTATION
    defaultContactShouldNotBeFound("salutation.equals=" + UPDATED_SALUTATION);
  }

  @Test
  @Transactional
  void getAllContactsBySalutationIsInShouldWork() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where salutation in DEFAULT_SALUTATION or
    // UPDATED_SALUTATION
    defaultContactShouldBeFound(
        "salutation.in=" + DEFAULT_SALUTATION + "," + UPDATED_SALUTATION);

    // Get all the contactList where salutation equals to UPDATED_SALUTATION
    defaultContactShouldNotBeFound("salutation.in=" + UPDATED_SALUTATION);
  }

  @Test
  @Transactional
  void getAllContactsBySalutationIsNullOrNotNull() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where salutation is not null
    defaultContactShouldBeFound("salutation.specified=true");

    // Get all the contactList where salutation is null
    defaultContactShouldNotBeFound("salutation.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsByRankIsEqualToSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where rank equals to DEFAULT_RANK
    defaultContactShouldBeFound("rank.equals=" + DEFAULT_RANK);

    // Get all the contactList where rank equals to UPDATED_RANK
    defaultContactShouldNotBeFound("rank.equals=" + UPDATED_RANK);
  }

  @Test
  @Transactional
  void getAllContactsByRankIsInShouldWork() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where rank in DEFAULT_RANK or UPDATED_RANK
    defaultContactShouldBeFound("rank.in=" + DEFAULT_RANK + "," + UPDATED_RANK);

    // Get all the contactList where rank equals to UPDATED_RANK
    defaultContactShouldNotBeFound("rank.in=" + UPDATED_RANK);
  }

  @Test
  @Transactional
  void getAllContactsByRankIsNullOrNotNull() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where rank is not null
    defaultContactShouldBeFound("rank.specified=true");

    // Get all the contactList where rank is null
    defaultContactShouldNotBeFound("rank.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsByGenderIsEqualToSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where gender equals to DEFAULT_GENDER
    defaultContactShouldBeFound("gender.equals=" + DEFAULT_GENDER);

    // Get all the contactList where gender equals to UPDATED_GENDER
    defaultContactShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
  }

  @Test
  @Transactional
  void getAllContactsByGenderIsInShouldWork() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where gender in DEFAULT_GENDER or UPDATED_GENDER
    defaultContactShouldBeFound(
        "gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

    // Get all the contactList where gender equals to UPDATED_GENDER
    defaultContactShouldNotBeFound("gender.in=" + UPDATED_GENDER);
  }

  @Test
  @Transactional
  void getAllContactsByGenderIsNullOrNotNull() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where gender is not null
    defaultContactShouldBeFound("gender.specified=true");

    // Get all the contactList where gender is null
    defaultContactShouldNotBeFound("gender.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsBySurNameIsEqualToSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where surName equals to DEFAULT_SUR_NAME
    defaultContactShouldBeFound("surName.equals=" + DEFAULT_SUR_NAME);

    // Get all the contactList where surName equals to UPDATED_SUR_NAME
    defaultContactShouldNotBeFound("surName.equals=" + UPDATED_SUR_NAME);
  }

  @Test
  @Transactional
  void getAllContactsBySurNameIsInShouldWork() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where surName in DEFAULT_SUR_NAME or UPDATED_SUR_NAME
    defaultContactShouldBeFound(
        "surName.in=" + DEFAULT_SUR_NAME + "," + UPDATED_SUR_NAME);

    // Get all the contactList where surName equals to UPDATED_SUR_NAME
    defaultContactShouldNotBeFound("surName.in=" + UPDATED_SUR_NAME);
  }

  @Test
  @Transactional
  void getAllContactsBySurNameIsNullOrNotNull() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where surName is not null
    defaultContactShouldBeFound("surName.specified=true");

    // Get all the contactList where surName is null
    defaultContactShouldNotBeFound("surName.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsBySurNameContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where surName contains DEFAULT_SUR_NAME
    defaultContactShouldBeFound("surName.contains=" + DEFAULT_SUR_NAME);

    // Get all the contactList where surName contains UPDATED_SUR_NAME
    defaultContactShouldNotBeFound("surName.contains=" + UPDATED_SUR_NAME);
  }

  @Test
  @Transactional
  void getAllContactsBySurNameNotContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where surName does not contain DEFAULT_SUR_NAME
    defaultContactShouldNotBeFound(
        "surName.doesNotContain=" + DEFAULT_SUR_NAME);

    // Get all the contactList where surName does not contain UPDATED_SUR_NAME
    defaultContactShouldBeFound("surName.doesNotContain=" + UPDATED_SUR_NAME);
  }

  @Test
  @Transactional
  void getAllContactsByFirstNameIsEqualToSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where firstName equals to DEFAULT_FIRST_NAME
    defaultContactShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

    // Get all the contactList where firstName equals to UPDATED_FIRST_NAME
    defaultContactShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
  }

  @Test
  @Transactional
  void getAllContactsByFirstNameIsInShouldWork() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where firstName in DEFAULT_FIRST_NAME or
    // UPDATED_FIRST_NAME
    defaultContactShouldBeFound(
        "firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

    // Get all the contactList where firstName equals to UPDATED_FIRST_NAME
    defaultContactShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
  }

  @Test
  @Transactional
  void getAllContactsByFirstNameIsNullOrNotNull() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where firstName is not null
    defaultContactShouldBeFound("firstName.specified=true");

    // Get all the contactList where firstName is null
    defaultContactShouldNotBeFound("firstName.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsByFirstNameContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where firstName contains DEFAULT_FIRST_NAME
    defaultContactShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

    // Get all the contactList where firstName contains UPDATED_FIRST_NAME
    defaultContactShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
  }

  @Test
  @Transactional
  void getAllContactsByFirstNameNotContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where firstName does not contain DEFAULT_FIRST_NAME
    defaultContactShouldNotBeFound(
        "firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

    // Get all the contactList where firstName does not contain UPDATED_FIRST_NAME
    defaultContactShouldBeFound(
        "firstName.doesNotContain=" + UPDATED_FIRST_NAME);
  }

  @Test
  @Transactional
  void getAllContactsByOtherFirstNamesIsEqualToSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where otherFirstNames equals to
    // DEFAULT_OTHER_FIRST_NAMES
    defaultContactShouldBeFound(
        "otherFirstNames.equals=" + DEFAULT_OTHER_FIRST_NAMES);

    // Get all the contactList where otherFirstNames equals to
    // UPDATED_OTHER_FIRST_NAMES
    defaultContactShouldNotBeFound(
        "otherFirstNames.equals=" + UPDATED_OTHER_FIRST_NAMES);
  }

  @Test
  @Transactional
  void getAllContactsByOtherFirstNamesIsInShouldWork() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where otherFirstNames in DEFAULT_OTHER_FIRST_NAMES or
    // UPDATED_OTHER_FIRST_NAMES
    defaultContactShouldBeFound(
        "otherFirstNames.in=" +
            DEFAULT_OTHER_FIRST_NAMES +
            "," +
            UPDATED_OTHER_FIRST_NAMES);

    // Get all the contactList where otherFirstNames equals to
    // UPDATED_OTHER_FIRST_NAMES
    defaultContactShouldNotBeFound(
        "otherFirstNames.in=" + UPDATED_OTHER_FIRST_NAMES);
  }

  @Test
  @Transactional
  void getAllContactsByOtherFirstNamesIsNullOrNotNull() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where otherFirstNames is not null
    defaultContactShouldBeFound("otherFirstNames.specified=true");

    // Get all the contactList where otherFirstNames is null
    defaultContactShouldNotBeFound("otherFirstNames.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsByOtherFirstNamesContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where otherFirstNames contains
    // DEFAULT_OTHER_FIRST_NAMES
    defaultContactShouldBeFound(
        "otherFirstNames.contains=" + DEFAULT_OTHER_FIRST_NAMES);

    // Get all the contactList where otherFirstNames contains
    // UPDATED_OTHER_FIRST_NAMES
    defaultContactShouldNotBeFound(
        "otherFirstNames.contains=" + UPDATED_OTHER_FIRST_NAMES);
  }

  @Test
  @Transactional
  void getAllContactsByOtherFirstNamesNotContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where otherFirstNames does not contain
    // DEFAULT_OTHER_FIRST_NAMES
    defaultContactShouldNotBeFound(
        "otherFirstNames.doesNotContain=" + DEFAULT_OTHER_FIRST_NAMES);

    // Get all the contactList where otherFirstNames does not contain
    // UPDATED_OTHER_FIRST_NAMES
    defaultContactShouldBeFound(
        "otherFirstNames.doesNotContain=" + UPDATED_OTHER_FIRST_NAMES);
  }

  @Test
  @Transactional
  void getAllContactsByBirthNameIsEqualToSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where birthName equals to DEFAULT_BIRTH_NAME
    defaultContactShouldBeFound("birthName.equals=" + DEFAULT_BIRTH_NAME);

    // Get all the contactList where birthName equals to UPDATED_BIRTH_NAME
    defaultContactShouldNotBeFound("birthName.equals=" + UPDATED_BIRTH_NAME);
  }

  @Test
  @Transactional
  void getAllContactsByBirthNameIsInShouldWork() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where birthName in DEFAULT_BIRTH_NAME or
    // UPDATED_BIRTH_NAME
    defaultContactShouldBeFound(
        "birthName.in=" + DEFAULT_BIRTH_NAME + "," + UPDATED_BIRTH_NAME);

    // Get all the contactList where birthName equals to UPDATED_BIRTH_NAME
    defaultContactShouldNotBeFound("birthName.in=" + UPDATED_BIRTH_NAME);
  }

  @Test
  @Transactional
  void getAllContactsByBirthNameIsNullOrNotNull() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where birthName is not null
    defaultContactShouldBeFound("birthName.specified=true");

    // Get all the contactList where birthName is null
    defaultContactShouldNotBeFound("birthName.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsByBirthNameContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where birthName contains DEFAULT_BIRTH_NAME
    defaultContactShouldBeFound("birthName.contains=" + DEFAULT_BIRTH_NAME);

    // Get all the contactList where birthName contains UPDATED_BIRTH_NAME
    defaultContactShouldNotBeFound("birthName.contains=" + UPDATED_BIRTH_NAME);
  }

  @Test
  @Transactional
  void getAllContactsByBirthNameNotContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where birthName does not contain DEFAULT_BIRTH_NAME
    defaultContactShouldNotBeFound(
        "birthName.doesNotContain=" + DEFAULT_BIRTH_NAME);

    // Get all the contactList where birthName does not contain UPDATED_BIRTH_NAME
    defaultContactShouldBeFound(
        "birthName.doesNotContain=" + UPDATED_BIRTH_NAME);
  }

  @Test
  @Transactional
  void getAllContactsByMaritalStatusIsEqualToSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where maritalStatus equals to DEFAULT_MARITAL_STATUS
    defaultContactShouldBeFound(
        "maritalStatus.equals=" + DEFAULT_MARITAL_STATUS);

    // Get all the contactList where maritalStatus equals to UPDATED_MARITAL_STATUS
    defaultContactShouldNotBeFound(
        "maritalStatus.equals=" + UPDATED_MARITAL_STATUS);
  }

  @Test
  @Transactional
  void getAllContactsByMaritalStatusIsInShouldWork() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where maritalStatus in DEFAULT_MARITAL_STATUS or
    // UPDATED_MARITAL_STATUS
    defaultContactShouldBeFound(
        "maritalStatus.in=" +
            DEFAULT_MARITAL_STATUS +
            "," +
            UPDATED_MARITAL_STATUS);

    // Get all the contactList where maritalStatus equals to UPDATED_MARITAL_STATUS
    defaultContactShouldNotBeFound(
        "maritalStatus.in=" + UPDATED_MARITAL_STATUS);
  }

  @Test
  @Transactional
  void getAllContactsByMaritalStatusIsNullOrNotNull() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where maritalStatus is not null
    defaultContactShouldBeFound("maritalStatus.specified=true");

    // Get all the contactList where maritalStatus is null
    defaultContactShouldNotBeFound("maritalStatus.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsByNickNameIsEqualToSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where nickName equals to DEFAULT_NICK_NAME
    defaultContactShouldBeFound("nickName.equals=" + DEFAULT_NICK_NAME);

    // Get all the contactList where nickName equals to UPDATED_NICK_NAME
    defaultContactShouldNotBeFound("nickName.equals=" + UPDATED_NICK_NAME);
  }

  @Test
  @Transactional
  void getAllContactsByNickNameIsInShouldWork() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where nickName in DEFAULT_NICK_NAME or
    // UPDATED_NICK_NAME
    defaultContactShouldBeFound(
        "nickName.in=" + DEFAULT_NICK_NAME + "," + UPDATED_NICK_NAME);

    // Get all the contactList where nickName equals to UPDATED_NICK_NAME
    defaultContactShouldNotBeFound("nickName.in=" + UPDATED_NICK_NAME);
  }

  @Test
  @Transactional
  void getAllContactsByNickNameIsNullOrNotNull() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where nickName is not null
    defaultContactShouldBeFound("nickName.specified=true");

    // Get all the contactList where nickName is null
    defaultContactShouldNotBeFound("nickName.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsByNickNameContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where nickName contains DEFAULT_NICK_NAME
    defaultContactShouldBeFound("nickName.contains=" + DEFAULT_NICK_NAME);

    // Get all the contactList where nickName contains UPDATED_NICK_NAME
    defaultContactShouldNotBeFound("nickName.contains=" + UPDATED_NICK_NAME);
  }

  @Test
  @Transactional
  void getAllContactsByNickNameNotContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where nickName does not contain DEFAULT_NICK_NAME
    defaultContactShouldNotBeFound(
        "nickName.doesNotContain=" + DEFAULT_NICK_NAME);

    // Get all the contactList where nickName does not contain UPDATED_NICK_NAME
    defaultContactShouldBeFound("nickName.doesNotContain=" + UPDATED_NICK_NAME);
  }

  @Test
  @Transactional
  void getAllContactsByBirthDateIsEqualToSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where birthDate equals to DEFAULT_BIRTH_DATE
    defaultContactShouldBeFound("birthDate.equals=" + DEFAULT_BIRTH_DATE);

    // Get all the contactList where birthDate equals to UPDATED_BIRTH_DATE
    defaultContactShouldNotBeFound("birthDate.equals=" + UPDATED_BIRTH_DATE);
  }

  @Test
  @Transactional
  void getAllContactsByBirthDateIsInShouldWork() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where birthDate in DEFAULT_BIRTH_DATE or
    // UPDATED_BIRTH_DATE
    defaultContactShouldBeFound(
        "birthDate.in=" + DEFAULT_BIRTH_DATE + "," + UPDATED_BIRTH_DATE);

    // Get all the contactList where birthDate equals to UPDATED_BIRTH_DATE
    defaultContactShouldNotBeFound("birthDate.in=" + UPDATED_BIRTH_DATE);
  }

  @Test
  @Transactional
  void getAllContactsByBirthDateIsNullOrNotNull() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where birthDate is not null
    defaultContactShouldBeFound("birthDate.specified=true");

    // Get all the contactList where birthDate is null
    defaultContactShouldNotBeFound("birthDate.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsByBirthDateIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where birthDate is greater than or equal to
    // DEFAULT_BIRTH_DATE
    defaultContactShouldBeFound(
        "birthDate.greaterThanOrEqual=" + DEFAULT_BIRTH_DATE);

    // Get all the contactList where birthDate is greater than or equal to
    // UPDATED_BIRTH_DATE
    defaultContactShouldNotBeFound(
        "birthDate.greaterThanOrEqual=" + UPDATED_BIRTH_DATE);
  }

  @Test
  @Transactional
  void getAllContactsByBirthDateIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where birthDate is less than or equal to
    // DEFAULT_BIRTH_DATE
    defaultContactShouldBeFound(
        "birthDate.lessThanOrEqual=" + DEFAULT_BIRTH_DATE);

    // Get all the contactList where birthDate is less than or equal to
    // SMALLER_BIRTH_DATE
    defaultContactShouldNotBeFound(
        "birthDate.lessThanOrEqual=" + SMALLER_BIRTH_DATE);
  }

  @Test
  @Transactional
  void getAllContactsByBirthDateIsLessThanSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where birthDate is less than DEFAULT_BIRTH_DATE
    defaultContactShouldNotBeFound("birthDate.lessThan=" + DEFAULT_BIRTH_DATE);

    // Get all the contactList where birthDate is less than UPDATED_BIRTH_DATE
    defaultContactShouldBeFound("birthDate.lessThan=" + UPDATED_BIRTH_DATE);
  }

  @Test
  @Transactional
  void getAllContactsByBirthDateIsGreaterThanSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where birthDate is greater than DEFAULT_BIRTH_DATE
    defaultContactShouldNotBeFound(
        "birthDate.greaterThan=" + DEFAULT_BIRTH_DATE);

    // Get all the contactList where birthDate is greater than SMALLER_BIRTH_DATE
    defaultContactShouldBeFound("birthDate.greaterThan=" + SMALLER_BIRTH_DATE);
  }

  @Test
  @Transactional
  void getAllContactsByBirthCityIsEqualToSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where birthCity equals to DEFAULT_BIRTH_CITY
    defaultContactShouldBeFound("birthCity.equals=" + DEFAULT_BIRTH_CITY);

    // Get all the contactList where birthCity equals to UPDATED_BIRTH_CITY
    defaultContactShouldNotBeFound("birthCity.equals=" + UPDATED_BIRTH_CITY);
  }

  @Test
  @Transactional
  void getAllContactsByBirthCityIsInShouldWork() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where birthCity in DEFAULT_BIRTH_CITY or
    // UPDATED_BIRTH_CITY
    defaultContactShouldBeFound(
        "birthCity.in=" + DEFAULT_BIRTH_CITY + "," + UPDATED_BIRTH_CITY);

    // Get all the contactList where birthCity equals to UPDATED_BIRTH_CITY
    defaultContactShouldNotBeFound("birthCity.in=" + UPDATED_BIRTH_CITY);
  }

  @Test
  @Transactional
  void getAllContactsByBirthCityIsNullOrNotNull() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where birthCity is not null
    defaultContactShouldBeFound("birthCity.specified=true");

    // Get all the contactList where birthCity is null
    defaultContactShouldNotBeFound("birthCity.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsByBirthCityContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where birthCity contains DEFAULT_BIRTH_CITY
    defaultContactShouldBeFound("birthCity.contains=" + DEFAULT_BIRTH_CITY);

    // Get all the contactList where birthCity contains UPDATED_BIRTH_CITY
    defaultContactShouldNotBeFound("birthCity.contains=" + UPDATED_BIRTH_CITY);
  }

  @Test
  @Transactional
  void getAllContactsByBirthCityNotContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where birthCity does not contain DEFAULT_BIRTH_CITY
    defaultContactShouldNotBeFound(
        "birthCity.doesNotContain=" + DEFAULT_BIRTH_CITY);

    // Get all the contactList where birthCity does not contain UPDATED_BIRTH_CITY
    defaultContactShouldBeFound(
        "birthCity.doesNotContain=" + UPDATED_BIRTH_CITY);
  }

  @Test
  @Transactional
  void getAllContactsByBirthCountryIsEqualToSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where birthCountry equals to DEFAULT_BIRTH_COUNTRY
    defaultContactShouldBeFound("birthCountry.equals=" + DEFAULT_BIRTH_COUNTRY);

    // Get all the contactList where birthCountry equals to UPDATED_BIRTH_COUNTRY
    defaultContactShouldNotBeFound(
        "birthCountry.equals=" + UPDATED_BIRTH_COUNTRY);
  }

  @Test
  @Transactional
  void getAllContactsByBirthCountryIsInShouldWork() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where birthCountry in DEFAULT_BIRTH_COUNTRY or
    // UPDATED_BIRTH_COUNTRY
    defaultContactShouldBeFound(
        "birthCountry.in=" + DEFAULT_BIRTH_COUNTRY + "," + UPDATED_BIRTH_COUNTRY);

    // Get all the contactList where birthCountry equals to UPDATED_BIRTH_COUNTRY
    defaultContactShouldNotBeFound("birthCountry.in=" + UPDATED_BIRTH_COUNTRY);
  }

  @Test
  @Transactional
  void getAllContactsByBirthCountryIsNullOrNotNull() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where birthCountry is not null
    defaultContactShouldBeFound("birthCountry.specified=true");

    // Get all the contactList where birthCountry is null
    defaultContactShouldNotBeFound("birthCountry.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsByBirthCountryContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where birthCountry contains DEFAULT_BIRTH_COUNTRY
    defaultContactShouldBeFound(
        "birthCountry.contains=" + DEFAULT_BIRTH_COUNTRY);

    // Get all the contactList where birthCountry contains UPDATED_BIRTH_COUNTRY
    defaultContactShouldNotBeFound(
        "birthCountry.contains=" + UPDATED_BIRTH_COUNTRY);
  }

  @Test
  @Transactional
  void getAllContactsByBirthCountryNotContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where birthCountry does not contain
    // DEFAULT_BIRTH_COUNTRY
    defaultContactShouldNotBeFound(
        "birthCountry.doesNotContain=" + DEFAULT_BIRTH_COUNTRY);

    // Get all the contactList where birthCountry does not contain
    // UPDATED_BIRTH_COUNTRY
    defaultContactShouldBeFound(
        "birthCountry.doesNotContain=" + UPDATED_BIRTH_COUNTRY);
  }

  @Test
  @Transactional
  void getAllContactsByNationalityIsEqualToSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where nationality equals to DEFAULT_NATIONALITY
    defaultContactShouldBeFound("nationality.equals=" + DEFAULT_NATIONALITY);

    // Get all the contactList where nationality equals to UPDATED_NATIONALITY
    defaultContactShouldNotBeFound("nationality.equals=" + UPDATED_NATIONALITY);
  }

  @Test
  @Transactional
  void getAllContactsByNationalityIsInShouldWork() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where nationality in DEFAULT_NATIONALITY or
    // UPDATED_NATIONALITY
    defaultContactShouldBeFound(
        "nationality.in=" + DEFAULT_NATIONALITY + "," + UPDATED_NATIONALITY);

    // Get all the contactList where nationality equals to UPDATED_NATIONALITY
    defaultContactShouldNotBeFound("nationality.in=" + UPDATED_NATIONALITY);
  }

  @Test
  @Transactional
  void getAllContactsByNationalityIsNullOrNotNull() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where nationality is not null
    defaultContactShouldBeFound("nationality.specified=true");

    // Get all the contactList where nationality is null
    defaultContactShouldNotBeFound("nationality.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsByNationalityContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where nationality contains DEFAULT_NATIONALITY
    defaultContactShouldBeFound("nationality.contains=" + DEFAULT_NATIONALITY);

    // Get all the contactList where nationality contains UPDATED_NATIONALITY
    defaultContactShouldNotBeFound(
        "nationality.contains=" + UPDATED_NATIONALITY);
  }

  @Test
  @Transactional
  void getAllContactsByNationalityNotContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where nationality does not contain
    // DEFAULT_NATIONALITY
    defaultContactShouldNotBeFound(
        "nationality.doesNotContain=" + DEFAULT_NATIONALITY);

    // Get all the contactList where nationality does not contain
    // UPDATED_NATIONALITY
    defaultContactShouldBeFound(
        "nationality.doesNotContain=" + UPDATED_NATIONALITY);
  }

  @Test
  @Transactional
  void getAllContactsByAddressTypeIsEqualToSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where addressType equals to DEFAULT_ADDRESS_TYPE
    defaultContactShouldBeFound("addressType.equals=" + DEFAULT_ADDRESS_TYPE);

    // Get all the contactList where addressType equals to UPDATED_ADDRESS_TYPE
    defaultContactShouldNotBeFound(
        "addressType.equals=" + UPDATED_ADDRESS_TYPE);
  }

  @Test
  @Transactional
  void getAllContactsByAddressTypeIsInShouldWork() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where addressType in DEFAULT_ADDRESS_TYPE or
    // UPDATED_ADDRESS_TYPE
    defaultContactShouldBeFound(
        "addressType.in=" + DEFAULT_ADDRESS_TYPE + "," + UPDATED_ADDRESS_TYPE);

    // Get all the contactList where addressType equals to UPDATED_ADDRESS_TYPE
    defaultContactShouldNotBeFound("addressType.in=" + UPDATED_ADDRESS_TYPE);
  }

  @Test
  @Transactional
  void getAllContactsByAddressTypeIsNullOrNotNull() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where addressType is not null
    defaultContactShouldBeFound("addressType.specified=true");

    // Get all the contactList where addressType is null
    defaultContactShouldNotBeFound("addressType.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsByStreetIsEqualToSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where street equals to DEFAULT_STREET
    defaultContactShouldBeFound("street.equals=" + DEFAULT_STREET);

    // Get all the contactList where street equals to UPDATED_STREET
    defaultContactShouldNotBeFound("street.equals=" + UPDATED_STREET);
  }

  @Test
  @Transactional
  void getAllContactsByStreetIsInShouldWork() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where street in DEFAULT_STREET or UPDATED_STREET
    defaultContactShouldBeFound(
        "street.in=" + DEFAULT_STREET + "," + UPDATED_STREET);

    // Get all the contactList where street equals to UPDATED_STREET
    defaultContactShouldNotBeFound("street.in=" + UPDATED_STREET);
  }

  @Test
  @Transactional
  void getAllContactsByStreetIsNullOrNotNull() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where street is not null
    defaultContactShouldBeFound("street.specified=true");

    // Get all the contactList where street is null
    defaultContactShouldNotBeFound("street.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsByStreetContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where street contains DEFAULT_STREET
    defaultContactShouldBeFound("street.contains=" + DEFAULT_STREET);

    // Get all the contactList where street contains UPDATED_STREET
    defaultContactShouldNotBeFound("street.contains=" + UPDATED_STREET);
  }

  @Test
  @Transactional
  void getAllContactsByStreetNotContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where street does not contain DEFAULT_STREET
    defaultContactShouldNotBeFound("street.doesNotContain=" + DEFAULT_STREET);

    // Get all the contactList where street does not contain UPDATED_STREET
    defaultContactShouldBeFound("street.doesNotContain=" + UPDATED_STREET);
  }

  @Test
  @Transactional
  void getAllContactsByStreetNumberIsEqualToSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where streetNumber equals to DEFAULT_STREET_NUMBER
    defaultContactShouldBeFound("streetNumber.equals=" + DEFAULT_STREET_NUMBER);

    // Get all the contactList where streetNumber equals to UPDATED_STREET_NUMBER
    defaultContactShouldNotBeFound(
        "streetNumber.equals=" + UPDATED_STREET_NUMBER);
  }

  @Test
  @Transactional
  void getAllContactsByStreetNumberIsInShouldWork() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where streetNumber in DEFAULT_STREET_NUMBER or
    // UPDATED_STREET_NUMBER
    defaultContactShouldBeFound(
        "streetNumber.in=" + DEFAULT_STREET_NUMBER + "," + UPDATED_STREET_NUMBER);

    // Get all the contactList where streetNumber equals to UPDATED_STREET_NUMBER
    defaultContactShouldNotBeFound("streetNumber.in=" + UPDATED_STREET_NUMBER);
  }

  @Test
  @Transactional
  void getAllContactsByStreetNumberIsNullOrNotNull() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where streetNumber is not null
    defaultContactShouldBeFound("streetNumber.specified=true");

    // Get all the contactList where streetNumber is null
    defaultContactShouldNotBeFound("streetNumber.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsByStreetNumberContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where streetNumber contains DEFAULT_STREET_NUMBER
    defaultContactShouldBeFound(
        "streetNumber.contains=" + DEFAULT_STREET_NUMBER);

    // Get all the contactList where streetNumber contains UPDATED_STREET_NUMBER
    defaultContactShouldNotBeFound(
        "streetNumber.contains=" + UPDATED_STREET_NUMBER);
  }

  @Test
  @Transactional
  void getAllContactsByStreetNumberNotContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where streetNumber does not contain
    // DEFAULT_STREET_NUMBER
    defaultContactShouldNotBeFound(
        "streetNumber.doesNotContain=" + DEFAULT_STREET_NUMBER);

    // Get all the contactList where streetNumber does not contain
    // UPDATED_STREET_NUMBER
    defaultContactShouldBeFound(
        "streetNumber.doesNotContain=" + UPDATED_STREET_NUMBER);
  }

  @Test
  @Transactional
  void getAllContactsByPostalCodeIsEqualToSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where postalCode equals to DEFAULT_POSTAL_CODE
    defaultContactShouldBeFound("postalCode.equals=" + DEFAULT_POSTAL_CODE);

    // Get all the contactList where postalCode equals to UPDATED_POSTAL_CODE
    defaultContactShouldNotBeFound("postalCode.equals=" + UPDATED_POSTAL_CODE);
  }

  @Test
  @Transactional
  void getAllContactsByPostalCodeIsInShouldWork() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where postalCode in DEFAULT_POSTAL_CODE or
    // UPDATED_POSTAL_CODE
    defaultContactShouldBeFound(
        "postalCode.in=" + DEFAULT_POSTAL_CODE + "," + UPDATED_POSTAL_CODE);

    // Get all the contactList where postalCode equals to UPDATED_POSTAL_CODE
    defaultContactShouldNotBeFound("postalCode.in=" + UPDATED_POSTAL_CODE);
  }

  @Test
  @Transactional
  void getAllContactsByPostalCodeIsNullOrNotNull() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where postalCode is not null
    defaultContactShouldBeFound("postalCode.specified=true");

    // Get all the contactList where postalCode is null
    defaultContactShouldNotBeFound("postalCode.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsByPostalCodeContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where postalCode contains DEFAULT_POSTAL_CODE
    defaultContactShouldBeFound("postalCode.contains=" + DEFAULT_POSTAL_CODE);

    // Get all the contactList where postalCode contains UPDATED_POSTAL_CODE
    defaultContactShouldNotBeFound(
        "postalCode.contains=" + UPDATED_POSTAL_CODE);
  }

  @Test
  @Transactional
  void getAllContactsByPostalCodeNotContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where postalCode does not contain DEFAULT_POSTAL_CODE
    defaultContactShouldNotBeFound(
        "postalCode.doesNotContain=" + DEFAULT_POSTAL_CODE);

    // Get all the contactList where postalCode does not contain UPDATED_POSTAL_CODE
    defaultContactShouldBeFound(
        "postalCode.doesNotContain=" + UPDATED_POSTAL_CODE);
  }

  @Test
  @Transactional
  void getAllContactsByCityIsEqualToSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where city equals to DEFAULT_CITY
    defaultContactShouldBeFound("city.equals=" + DEFAULT_CITY);

    // Get all the contactList where city equals to UPDATED_CITY
    defaultContactShouldNotBeFound("city.equals=" + UPDATED_CITY);
  }

  @Test
  @Transactional
  void getAllContactsByCityIsInShouldWork() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where city in DEFAULT_CITY or UPDATED_CITY
    defaultContactShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

    // Get all the contactList where city equals to UPDATED_CITY
    defaultContactShouldNotBeFound("city.in=" + UPDATED_CITY);
  }

  @Test
  @Transactional
  void getAllContactsByCityIsNullOrNotNull() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where city is not null
    defaultContactShouldBeFound("city.specified=true");

    // Get all the contactList where city is null
    defaultContactShouldNotBeFound("city.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsByCityContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where city contains DEFAULT_CITY
    defaultContactShouldBeFound("city.contains=" + DEFAULT_CITY);

    // Get all the contactList where city contains UPDATED_CITY
    defaultContactShouldNotBeFound("city.contains=" + UPDATED_CITY);
  }

  @Test
  @Transactional
  void getAllContactsByCityNotContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where city does not contain DEFAULT_CITY
    defaultContactShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

    // Get all the contactList where city does not contain UPDATED_CITY
    defaultContactShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
  }

  @Test
  @Transactional
  void getAllContactsByAdditionalAddressField1IsEqualToSomething()
      throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where additionalAddressField1 equals to
    // DEFAULT_ADDITIONAL_ADDRESS_FIELD_1
    defaultContactShouldBeFound(
        "additionalAddressField1.equals=" + DEFAULT_ADDITIONAL_ADDRESS_FIELD_1);

    // Get all the contactList where additionalAddressField1 equals to
    // UPDATED_ADDITIONAL_ADDRESS_FIELD_1
    defaultContactShouldNotBeFound(
        "additionalAddressField1.equals=" + UPDATED_ADDITIONAL_ADDRESS_FIELD_1);
  }

  @Test
  @Transactional
  void getAllContactsByAdditionalAddressField1IsInShouldWork()
      throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where additionalAddressField1 in
    // DEFAULT_ADDITIONAL_ADDRESS_FIELD_1 or UPDATED_ADDITIONAL_ADDRESS_FIELD_1
    defaultContactShouldBeFound(
        "additionalAddressField1.in=" +
            DEFAULT_ADDITIONAL_ADDRESS_FIELD_1 +
            "," +
            UPDATED_ADDITIONAL_ADDRESS_FIELD_1);

    // Get all the contactList where additionalAddressField1 equals to
    // UPDATED_ADDITIONAL_ADDRESS_FIELD_1
    defaultContactShouldNotBeFound(
        "additionalAddressField1.in=" + UPDATED_ADDITIONAL_ADDRESS_FIELD_1);
  }

  @Test
  @Transactional
  void getAllContactsByAdditionalAddressField1IsNullOrNotNull()
      throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where additionalAddressField1 is not null
    defaultContactShouldBeFound("additionalAddressField1.specified=true");

    // Get all the contactList where additionalAddressField1 is null
    defaultContactShouldNotBeFound("additionalAddressField1.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsByAdditionalAddressField1ContainsSomething()
      throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where additionalAddressField1 contains
    // DEFAULT_ADDITIONAL_ADDRESS_FIELD_1
    defaultContactShouldBeFound(
        "additionalAddressField1.contains=" + DEFAULT_ADDITIONAL_ADDRESS_FIELD_1);

    // Get all the contactList where additionalAddressField1 contains
    // UPDATED_ADDITIONAL_ADDRESS_FIELD_1
    defaultContactShouldNotBeFound(
        "additionalAddressField1.contains=" + UPDATED_ADDITIONAL_ADDRESS_FIELD_1);
  }

  @Test
  @Transactional
  void getAllContactsByAdditionalAddressField1NotContainsSomething()
      throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where additionalAddressField1 does not contain
    // DEFAULT_ADDITIONAL_ADDRESS_FIELD_1
    defaultContactShouldNotBeFound(
        "additionalAddressField1.doesNotContain=" +
            DEFAULT_ADDITIONAL_ADDRESS_FIELD_1);

    // Get all the contactList where additionalAddressField1 does not contain
    // UPDATED_ADDITIONAL_ADDRESS_FIELD_1
    defaultContactShouldBeFound(
        "additionalAddressField1.doesNotContain=" +
            UPDATED_ADDITIONAL_ADDRESS_FIELD_1);
  }

  @Test
  @Transactional
  void getAllContactsByAdditionalAddressField2IsEqualToSomething()
      throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where additionalAddressField2 equals to
    // DEFAULT_ADDITIONAL_ADDRESS_FIELD_2
    defaultContactShouldBeFound(
        "additionalAddressField2.equals=" + DEFAULT_ADDITIONAL_ADDRESS_FIELD_2);

    // Get all the contactList where additionalAddressField2 equals to
    // UPDATED_ADDITIONAL_ADDRESS_FIELD_2
    defaultContactShouldNotBeFound(
        "additionalAddressField2.equals=" + UPDATED_ADDITIONAL_ADDRESS_FIELD_2);
  }

  @Test
  @Transactional
  void getAllContactsByAdditionalAddressField2IsInShouldWork()
      throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where additionalAddressField2 in
    // DEFAULT_ADDITIONAL_ADDRESS_FIELD_2 or UPDATED_ADDITIONAL_ADDRESS_FIELD_2
    defaultContactShouldBeFound(
        "additionalAddressField2.in=" +
            DEFAULT_ADDITIONAL_ADDRESS_FIELD_2 +
            "," +
            UPDATED_ADDITIONAL_ADDRESS_FIELD_2);

    // Get all the contactList where additionalAddressField2 equals to
    // UPDATED_ADDITIONAL_ADDRESS_FIELD_2
    defaultContactShouldNotBeFound(
        "additionalAddressField2.in=" + UPDATED_ADDITIONAL_ADDRESS_FIELD_2);
  }

  @Test
  @Transactional
  void getAllContactsByAdditionalAddressField2IsNullOrNotNull()
      throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where additionalAddressField2 is not null
    defaultContactShouldBeFound("additionalAddressField2.specified=true");

    // Get all the contactList where additionalAddressField2 is null
    defaultContactShouldNotBeFound("additionalAddressField2.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsByAdditionalAddressField2ContainsSomething()
      throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where additionalAddressField2 contains
    // DEFAULT_ADDITIONAL_ADDRESS_FIELD_2
    defaultContactShouldBeFound(
        "additionalAddressField2.contains=" + DEFAULT_ADDITIONAL_ADDRESS_FIELD_2);

    // Get all the contactList where additionalAddressField2 contains
    // UPDATED_ADDITIONAL_ADDRESS_FIELD_2
    defaultContactShouldNotBeFound(
        "additionalAddressField2.contains=" + UPDATED_ADDITIONAL_ADDRESS_FIELD_2);
  }

  @Test
  @Transactional
  void getAllContactsByAdditionalAddressField2NotContainsSomething()
      throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where additionalAddressField2 does not contain
    // DEFAULT_ADDITIONAL_ADDRESS_FIELD_2
    defaultContactShouldNotBeFound(
        "additionalAddressField2.doesNotContain=" +
            DEFAULT_ADDITIONAL_ADDRESS_FIELD_2);

    // Get all the contactList where additionalAddressField2 does not contain
    // UPDATED_ADDITIONAL_ADDRESS_FIELD_2
    defaultContactShouldBeFound(
        "additionalAddressField2.doesNotContain=" +
            UPDATED_ADDITIONAL_ADDRESS_FIELD_2);
  }

  @Test
  @Transactional
  void getAllContactsByCommunicationTypeIsEqualToSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where communicationType equals to
    // DEFAULT_COMMUNICATION_TYPE
    defaultContactShouldBeFound(
        "communicationType.equals=" + DEFAULT_COMMUNICATION_TYPE);

    // Get all the contactList where communicationType equals to
    // UPDATED_COMMUNICATION_TYPE
    defaultContactShouldNotBeFound(
        "communicationType.equals=" + UPDATED_COMMUNICATION_TYPE);
  }

  @Test
  @Transactional
  void getAllContactsByCommunicationTypeIsInShouldWork() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where communicationType in DEFAULT_COMMUNICATION_TYPE
    // or UPDATED_COMMUNICATION_TYPE
    defaultContactShouldBeFound(
        "communicationType.in=" +
            DEFAULT_COMMUNICATION_TYPE +
            "," +
            UPDATED_COMMUNICATION_TYPE);

    // Get all the contactList where communicationType equals to
    // UPDATED_COMMUNICATION_TYPE
    defaultContactShouldNotBeFound(
        "communicationType.in=" + UPDATED_COMMUNICATION_TYPE);
  }

  @Test
  @Transactional
  void getAllContactsByCommunicationTypeIsNullOrNotNull() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where communicationType is not null
    defaultContactShouldBeFound("communicationType.specified=true");

    // Get all the contactList where communicationType is null
    defaultContactShouldNotBeFound("communicationType.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsByPhoneCountryCodeIsEqualToSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where phoneCountryCode equals to
    // DEFAULT_PHONE_COUNTRY_CODE
    defaultContactShouldBeFound(
        "phoneCountryCode.equals=" + DEFAULT_PHONE_COUNTRY_CODE);

    // Get all the contactList where phoneCountryCode equals to
    // UPDATED_PHONE_COUNTRY_CODE
    defaultContactShouldNotBeFound(
        "phoneCountryCode.equals=" + UPDATED_PHONE_COUNTRY_CODE);
  }

  @Test
  @Transactional
  void getAllContactsByPhoneCountryCodeIsInShouldWork() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where phoneCountryCode in DEFAULT_PHONE_COUNTRY_CODE
    // or UPDATED_PHONE_COUNTRY_CODE
    defaultContactShouldBeFound(
        "phoneCountryCode.in=" +
            DEFAULT_PHONE_COUNTRY_CODE +
            "," +
            UPDATED_PHONE_COUNTRY_CODE);

    // Get all the contactList where phoneCountryCode equals to
    // UPDATED_PHONE_COUNTRY_CODE
    defaultContactShouldNotBeFound(
        "phoneCountryCode.in=" + UPDATED_PHONE_COUNTRY_CODE);
  }

  @Test
  @Transactional
  void getAllContactsByPhoneCountryCodeIsNullOrNotNull() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where phoneCountryCode is not null
    defaultContactShouldBeFound("phoneCountryCode.specified=true");

    // Get all the contactList where phoneCountryCode is null
    defaultContactShouldNotBeFound("phoneCountryCode.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsByPhoneCountryCodeIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where phoneCountryCode is greater than or equal to
    // DEFAULT_PHONE_COUNTRY_CODE
    defaultContactShouldBeFound(
        "phoneCountryCode.greaterThanOrEqual=" + DEFAULT_PHONE_COUNTRY_CODE);

    // Get all the contactList where phoneCountryCode is greater than or equal to
    // UPDATED_PHONE_COUNTRY_CODE
    defaultContactShouldNotBeFound(
        "phoneCountryCode.greaterThanOrEqual=" + UPDATED_PHONE_COUNTRY_CODE);
  }

  @Test
  @Transactional
  void getAllContactsByPhoneCountryCodeIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where phoneCountryCode is less than or equal to
    // DEFAULT_PHONE_COUNTRY_CODE
    defaultContactShouldBeFound(
        "phoneCountryCode.lessThanOrEqual=" + DEFAULT_PHONE_COUNTRY_CODE);

    // Get all the contactList where phoneCountryCode is less than or equal to
    // SMALLER_PHONE_COUNTRY_CODE
    defaultContactShouldNotBeFound(
        "phoneCountryCode.lessThanOrEqual=" + SMALLER_PHONE_COUNTRY_CODE);
  }

  @Test
  @Transactional
  void getAllContactsByPhoneCountryCodeIsLessThanSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where phoneCountryCode is less than
    // DEFAULT_PHONE_COUNTRY_CODE
    defaultContactShouldNotBeFound(
        "phoneCountryCode.lessThan=" + DEFAULT_PHONE_COUNTRY_CODE);

    // Get all the contactList where phoneCountryCode is less than
    // UPDATED_PHONE_COUNTRY_CODE
    defaultContactShouldBeFound(
        "phoneCountryCode.lessThan=" + UPDATED_PHONE_COUNTRY_CODE);
  }

  @Test
  @Transactional
  void getAllContactsByPhoneCountryCodeIsGreaterThanSomething()
      throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where phoneCountryCode is greater than
    // DEFAULT_PHONE_COUNTRY_CODE
    defaultContactShouldNotBeFound(
        "phoneCountryCode.greaterThan=" + DEFAULT_PHONE_COUNTRY_CODE);

    // Get all the contactList where phoneCountryCode is greater than
    // SMALLER_PHONE_COUNTRY_CODE
    defaultContactShouldBeFound(
        "phoneCountryCode.greaterThan=" + SMALLER_PHONE_COUNTRY_CODE);
  }

  @Test
  @Transactional
  void getAllContactsByPhonePrefixIsEqualToSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where phonePrefix equals to DEFAULT_PHONE_PREFIX
    defaultContactShouldBeFound("phonePrefix.equals=" + DEFAULT_PHONE_PREFIX);

    // Get all the contactList where phonePrefix equals to UPDATED_PHONE_PREFIX
    defaultContactShouldNotBeFound(
        "phonePrefix.equals=" + UPDATED_PHONE_PREFIX);
  }

  @Test
  @Transactional
  void getAllContactsByPhonePrefixIsInShouldWork() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where phonePrefix in DEFAULT_PHONE_PREFIX or
    // UPDATED_PHONE_PREFIX
    defaultContactShouldBeFound(
        "phonePrefix.in=" + DEFAULT_PHONE_PREFIX + "," + UPDATED_PHONE_PREFIX);

    // Get all the contactList where phonePrefix equals to UPDATED_PHONE_PREFIX
    defaultContactShouldNotBeFound("phonePrefix.in=" + UPDATED_PHONE_PREFIX);
  }

  @Test
  @Transactional
  void getAllContactsByPhonePrefixIsNullOrNotNull() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where phonePrefix is not null
    defaultContactShouldBeFound("phonePrefix.specified=true");

    // Get all the contactList where phonePrefix is null
    defaultContactShouldNotBeFound("phonePrefix.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsByPhonePrefixIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where phonePrefix is greater than or equal to
    // DEFAULT_PHONE_PREFIX
    defaultContactShouldBeFound(
        "phonePrefix.greaterThanOrEqual=" + DEFAULT_PHONE_PREFIX);

    // Get all the contactList where phonePrefix is greater than or equal to
    // UPDATED_PHONE_PREFIX
    defaultContactShouldNotBeFound(
        "phonePrefix.greaterThanOrEqual=" + UPDATED_PHONE_PREFIX);
  }

  @Test
  @Transactional
  void getAllContactsByPhonePrefixIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where phonePrefix is less than or equal to
    // DEFAULT_PHONE_PREFIX
    defaultContactShouldBeFound(
        "phonePrefix.lessThanOrEqual=" + DEFAULT_PHONE_PREFIX);

    // Get all the contactList where phonePrefix is less than or equal to
    // SMALLER_PHONE_PREFIX
    defaultContactShouldNotBeFound(
        "phonePrefix.lessThanOrEqual=" + SMALLER_PHONE_PREFIX);
  }

  @Test
  @Transactional
  void getAllContactsByPhonePrefixIsLessThanSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where phonePrefix is less than DEFAULT_PHONE_PREFIX
    defaultContactShouldNotBeFound(
        "phonePrefix.lessThan=" + DEFAULT_PHONE_PREFIX);

    // Get all the contactList where phonePrefix is less than UPDATED_PHONE_PREFIX
    defaultContactShouldBeFound("phonePrefix.lessThan=" + UPDATED_PHONE_PREFIX);
  }

  @Test
  @Transactional
  void getAllContactsByPhonePrefixIsGreaterThanSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where phonePrefix is greater than
    // DEFAULT_PHONE_PREFIX
    defaultContactShouldNotBeFound(
        "phonePrefix.greaterThan=" + DEFAULT_PHONE_PREFIX);

    // Get all the contactList where phonePrefix is greater than
    // SMALLER_PHONE_PREFIX
    defaultContactShouldBeFound(
        "phonePrefix.greaterThan=" + SMALLER_PHONE_PREFIX);
  }

  @Test
  @Transactional
  void getAllContactsByPhoneNumberIsEqualToSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where phoneNumber equals to DEFAULT_PHONE_NUMBER
    defaultContactShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

    // Get all the contactList where phoneNumber equals to UPDATED_PHONE_NUMBER
    defaultContactShouldNotBeFound(
        "phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
  }

  @Test
  @Transactional
  void getAllContactsByPhoneNumberIsInShouldWork() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where phoneNumber in DEFAULT_PHONE_NUMBER or
    // UPDATED_PHONE_NUMBER
    defaultContactShouldBeFound(
        "phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

    // Get all the contactList where phoneNumber equals to UPDATED_PHONE_NUMBER
    defaultContactShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
  }

  @Test
  @Transactional
  void getAllContactsByPhoneNumberIsNullOrNotNull() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where phoneNumber is not null
    defaultContactShouldBeFound("phoneNumber.specified=true");

    // Get all the contactList where phoneNumber is null
    defaultContactShouldNotBeFound("phoneNumber.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsByPhoneNumberIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where phoneNumber is greater than or equal to
    // DEFAULT_PHONE_NUMBER
    defaultContactShouldBeFound(
        "phoneNumber.greaterThanOrEqual=" + DEFAULT_PHONE_NUMBER);

    // Get all the contactList where phoneNumber is greater than or equal to
    // UPDATED_PHONE_NUMBER
    defaultContactShouldNotBeFound(
        "phoneNumber.greaterThanOrEqual=" + UPDATED_PHONE_NUMBER);
  }

  @Test
  @Transactional
  void getAllContactsByPhoneNumberIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where phoneNumber is less than or equal to
    // DEFAULT_PHONE_NUMBER
    defaultContactShouldBeFound(
        "phoneNumber.lessThanOrEqual=" + DEFAULT_PHONE_NUMBER);

    // Get all the contactList where phoneNumber is less than or equal to
    // SMALLER_PHONE_NUMBER
    defaultContactShouldNotBeFound(
        "phoneNumber.lessThanOrEqual=" + SMALLER_PHONE_NUMBER);
  }

  @Test
  @Transactional
  void getAllContactsByPhoneNumberIsLessThanSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where phoneNumber is less than DEFAULT_PHONE_NUMBER
    defaultContactShouldNotBeFound(
        "phoneNumber.lessThan=" + DEFAULT_PHONE_NUMBER);

    // Get all the contactList where phoneNumber is less than UPDATED_PHONE_NUMBER
    defaultContactShouldBeFound("phoneNumber.lessThan=" + UPDATED_PHONE_NUMBER);
  }

  @Test
  @Transactional
  void getAllContactsByPhoneNumberIsGreaterThanSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where phoneNumber is greater than
    // DEFAULT_PHONE_NUMBER
    defaultContactShouldNotBeFound(
        "phoneNumber.greaterThan=" + DEFAULT_PHONE_NUMBER);

    // Get all the contactList where phoneNumber is greater than
    // SMALLER_PHONE_NUMBER
    defaultContactShouldBeFound(
        "phoneNumber.greaterThan=" + SMALLER_PHONE_NUMBER);
  }

  @Test
  @Transactional
  void getAllContactsByCommentIsEqualToSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where comment equals to DEFAULT_COMMENT
    defaultContactShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

    // Get all the contactList where comment equals to UPDATED_COMMENT
    defaultContactShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
  }

  @Test
  @Transactional
  void getAllContactsByCommentIsInShouldWork() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
    defaultContactShouldBeFound(
        "comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

    // Get all the contactList where comment equals to UPDATED_COMMENT
    defaultContactShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
  }

  @Test
  @Transactional
  void getAllContactsByCommentIsNullOrNotNull() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where comment is not null
    defaultContactShouldBeFound("comment.specified=true");

    // Get all the contactList where comment is null
    defaultContactShouldNotBeFound("comment.specified=false");
  }

  @Test
  @Transactional
  void getAllContactsByCommentContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where comment contains DEFAULT_COMMENT
    defaultContactShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

    // Get all the contactList where comment contains UPDATED_COMMENT
    defaultContactShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
  }

  @Test
  @Transactional
  void getAllContactsByCommentNotContainsSomething() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    // Get all the contactList where comment does not contain DEFAULT_COMMENT
    defaultContactShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

    // Get all the contactList where comment does not contain UPDATED_COMMENT
    defaultContactShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
  }

  @Test
  @Transactional
  void getAllContactsByApplicationUserIsEqualToSomething() throws Exception {
    ApplicationUser applicationUser;
    if (TestUtil.findAll(em, ApplicationUser.class).isEmpty()) {
      contactRepository.saveAndFlush(contact);
      applicationUser = ApplicationUserResourceIT.createEntity(em);
    } else {
      applicationUser = TestUtil.findAll(em, ApplicationUser.class).get(0);
    }
    em.persist(applicationUser);
    em.flush();
    contact.setApplicationUser(applicationUser);
    contactRepository.saveAndFlush(contact);
    Long applicationUserId = applicationUser.getId();

    // Get all the contactList where applicationUser equals to applicationUserId
    defaultContactShouldBeFound(
        "applicationUserId.equals=" + applicationUserId);

    // Get all the contactList where applicationUser equals to (applicationUserId +
    // 1)
    defaultContactShouldNotBeFound(
        "applicationUserId.equals=" + (applicationUserId + 1));
  }

  @Test
  @Transactional
  void getAllContactsByGroupTypeIsEqualToSomething() throws Exception {
    GroupType groupType;
    if (TestUtil.findAll(em, GroupType.class).isEmpty()) {
      contactRepository.saveAndFlush(contact);
      groupType = GroupTypeResourceIT.createEntity(em);
    } else {
      groupType = TestUtil.findAll(em, GroupType.class).get(0);
    }
    em.persist(groupType);
    em.flush();
    contact.setGroupType(groupType);
    contactRepository.saveAndFlush(contact);
    Long groupTypeId = groupType.getId();

    // Get all the contactList where groupType equals to groupTypeId
    defaultContactShouldBeFound("groupTypeId.equals=" + groupTypeId);

    // Get all the contactList where groupType equals to (groupTypeId + 1)
    defaultContactShouldNotBeFound("groupTypeId.equals=" + (groupTypeId + 1));
  }

  @Test
  @Transactional
  void getAllContactsByContactFinanceAccountIsEqualToSomething()
      throws Exception {
    ContactFinanceAccount contactFinanceAccount;
    if (TestUtil.findAll(em, ContactFinanceAccount.class).isEmpty()) {
      contactRepository.saveAndFlush(contact);
      contactFinanceAccount = ContactFinanceAccountResourceIT.createEntity(em);
    } else {
      contactFinanceAccount = TestUtil.findAll(em, ContactFinanceAccount.class).get(0);
    }
    em.persist(contactFinanceAccount);
    em.flush();
    contact.addContactFinanceAccount(contactFinanceAccount);
    contactRepository.saveAndFlush(contact);
    Long contactFinanceAccountId = contactFinanceAccount.getId();

    // Get all the contactList where contactFinanceAccount equals to
    // contactFinanceAccountId
    defaultContactShouldBeFound(
        "contactFinanceAccountId.equals=" + contactFinanceAccountId);

    // Get all the contactList where contactFinanceAccount equals to
    // (contactFinanceAccountId + 1)
    defaultContactShouldNotBeFound(
        "contactFinanceAccountId.equals=" + (contactFinanceAccountId + 1));
  }

  /**
   * Executes the search, and checks that the default entity is returned.
   */
  private void defaultContactShouldBeFound(String filter) throws Exception {
    restContactMockMvc
        .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(
            jsonPath("$.[*].id").value(hasItem(contact.getId().intValue())))
        .andExpect(
            jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
        .andExpect(
            jsonPath("$.[*].salutation")
                .value(hasItem(DEFAULT_SALUTATION.toString())))
        .andExpect(jsonPath("$.[*].rank").value(hasItem(DEFAULT_RANK.toString())))
        .andExpect(
            jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
        .andExpect(jsonPath("$.[*].surName").value(hasItem(DEFAULT_SUR_NAME)))
        .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
        .andExpect(
            jsonPath("$.[*].otherFirstNames")
                .value(hasItem(DEFAULT_OTHER_FIRST_NAMES)))
        .andExpect(jsonPath("$.[*].birthName").value(hasItem(DEFAULT_BIRTH_NAME)))
        .andExpect(
            jsonPath("$.[*].maritalStatus")
                .value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
        .andExpect(jsonPath("$.[*].nickName").value(hasItem(DEFAULT_NICK_NAME)))
        .andExpect(
            jsonPath("$.[*].birthDate")
                .value(hasItem(DEFAULT_BIRTH_DATE.toString())))
        .andExpect(jsonPath("$.[*].birthCity").value(hasItem(DEFAULT_BIRTH_CITY)))
        .andExpect(
            jsonPath("$.[*].birthCountry").value(hasItem(DEFAULT_BIRTH_COUNTRY)))
        .andExpect(
            jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
        .andExpect(
            jsonPath("$.[*].addressType")
                .value(hasItem(DEFAULT_ADDRESS_TYPE.toString())))
        .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
        .andExpect(
            jsonPath("$.[*].streetNumber").value(hasItem(DEFAULT_STREET_NUMBER)))
        .andExpect(
            jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
        .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
        .andExpect(
            jsonPath("$.[*].additionalAddressField1")
                .value(hasItem(DEFAULT_ADDITIONAL_ADDRESS_FIELD_1)))
        .andExpect(
            jsonPath("$.[*].additionalAddressField2")
                .value(hasItem(DEFAULT_ADDITIONAL_ADDRESS_FIELD_2)))
        .andExpect(
            jsonPath("$.[*].communicationType")
                .value(hasItem(DEFAULT_COMMUNICATION_TYPE.toString())))
        .andExpect(
            jsonPath("$.[*].phoneCountryCode")
                .value(hasItem(DEFAULT_PHONE_COUNTRY_CODE)))
        .andExpect(
            jsonPath("$.[*].phonePrefix").value(hasItem(DEFAULT_PHONE_PREFIX)))
        .andExpect(
            jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
        .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));

    // Check, that the count call also returns 1
    restContactMockMvc
        .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().string("1"));
  }

  /**
   * Executes the search, and checks that the default entity is not returned.
   */
  private void defaultContactShouldNotBeFound(String filter) throws Exception {
    restContactMockMvc
        .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$").isEmpty());

    // Check, that the count call also returns 0
    restContactMockMvc
        .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().string("0"));
  }

  @Test
  @Transactional
  void getNonExistingContact() throws Exception {
    // Get the contact
    restContactMockMvc
        .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
        .andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewContact() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    int databaseSizeBeforeUpdate = contactRepository.findAll().size();

    // Update the contact
    Contact updatedContact = contactRepository.findById(contact.getId()).orElseThrow();
    // Disconnect from session so that the updates on updatedContact are not
    // directly saved in db
    em.detach(updatedContact);
    updatedContact
        .title(UPDATED_TITLE)
        .salutation(UPDATED_SALUTATION)
        .rank(UPDATED_RANK)
        .gender(UPDATED_GENDER)
        .surName(UPDATED_SUR_NAME)
        .firstName(UPDATED_FIRST_NAME)
        .otherFirstNames(UPDATED_OTHER_FIRST_NAMES)
        .birthName(UPDATED_BIRTH_NAME)
        .maritalStatus(UPDATED_MARITAL_STATUS)
        .nickName(UPDATED_NICK_NAME)
        .birthDate(UPDATED_BIRTH_DATE)
        .birthCity(UPDATED_BIRTH_CITY)
        .birthCountry(UPDATED_BIRTH_COUNTRY)
        .nationality(UPDATED_NATIONALITY)
        .addressType(UPDATED_ADDRESS_TYPE)
        .street(UPDATED_STREET)
        .streetNumber(UPDATED_STREET_NUMBER)
        .postalCode(UPDATED_POSTAL_CODE)
        .city(UPDATED_CITY)
        .additionalAddressField1(UPDATED_ADDITIONAL_ADDRESS_FIELD_1)
        .additionalAddressField2(UPDATED_ADDITIONAL_ADDRESS_FIELD_2)
        .communicationType(UPDATED_COMMUNICATION_TYPE)
        .phoneCountryCode(UPDATED_PHONE_COUNTRY_CODE)
        .phonePrefix(UPDATED_PHONE_PREFIX)
        .phoneNumber(UPDATED_PHONE_NUMBER);

    restContactMockMvc
        .perform(
            put(ENTITY_API_URL_ID, updatedContact.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(updatedContact)))
        .andExpect(status().isOk());

    // Validate the Contact in the database
    List<Contact> contactList = contactRepository.findAll();
    assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    Contact testContact = contactList.get(contactList.size() - 1);
    assertThat(testContact.getTitle()).isEqualTo(UPDATED_TITLE);
    assertThat(testContact.getSalutation()).isEqualTo(UPDATED_SALUTATION);
    assertThat(testContact.getRank()).isEqualTo(UPDATED_RANK);
    assertThat(testContact.getGender()).isEqualTo(UPDATED_GENDER);
    assertThat(testContact.getSurName()).isEqualTo(UPDATED_SUR_NAME);
    assertThat(testContact.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
    assertThat(testContact.getOtherFirstNames())
        .isEqualTo(UPDATED_OTHER_FIRST_NAMES);
    assertThat(testContact.getBirthName()).isEqualTo(UPDATED_BIRTH_NAME);
    assertThat(testContact.getMaritalStatus())
        .isEqualTo(UPDATED_MARITAL_STATUS);
    assertThat(testContact.getNickName()).isEqualTo(UPDATED_NICK_NAME);
    assertThat(testContact.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
    assertThat(testContact.getBirthCity()).isEqualTo(UPDATED_BIRTH_CITY);
    assertThat(testContact.getBirthCountry()).isEqualTo(UPDATED_BIRTH_COUNTRY);
    assertThat(testContact.getNationality()).isEqualTo(UPDATED_NATIONALITY);
    assertThat(testContact.getAddressType()).isEqualTo(UPDATED_ADDRESS_TYPE);
    assertThat(testContact.getStreet()).isEqualTo(UPDATED_STREET);
    assertThat(testContact.getStreetNumber()).isEqualTo(UPDATED_STREET_NUMBER);
    assertThat(testContact.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
    assertThat(testContact.getCity()).isEqualTo(UPDATED_CITY);
    assertThat(testContact.getAdditionalAddressField1())
        .isEqualTo(UPDATED_ADDITIONAL_ADDRESS_FIELD_1);
    assertThat(testContact.getAdditionalAddressField2())
        .isEqualTo(UPDATED_ADDITIONAL_ADDRESS_FIELD_2);
    assertThat(testContact.getCommunicationType())
        .isEqualTo(UPDATED_COMMUNICATION_TYPE);
    assertThat(testContact.getPhoneCountryCode())
        .isEqualTo(UPDATED_PHONE_COUNTRY_CODE);
    assertThat(testContact.getPhonePrefix()).isEqualTo(UPDATED_PHONE_PREFIX);
    assertThat(testContact.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    assertThat(testContact.getComment()).isEqualTo(UPDATED_COMMENT);
  }

  @Test
  @Transactional
  void putNonExistingContact() throws Exception {
    int databaseSizeBeforeUpdate = contactRepository.findAll().size();
    contact.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restContactMockMvc
        .perform(
            put(ENTITY_API_URL_ID, contact.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(contact)))
        .andExpect(status().isBadRequest());

    // Validate the Contact in the database
    List<Contact> contactList = contactRepository.findAll();
    assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchContact() throws Exception {
    int databaseSizeBeforeUpdate = contactRepository.findAll().size();
    contact.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restContactMockMvc
        .perform(
            put(ENTITY_API_URL_ID, count.incrementAndGet())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(contact)))
        .andExpect(status().isBadRequest());

    // Validate the Contact in the database
    List<Contact> contactList = contactRepository.findAll();
    assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamContact() throws Exception {
    int databaseSizeBeforeUpdate = contactRepository.findAll().size();
    contact.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restContactMockMvc
        .perform(
            put(ENTITY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(contact)))
        .andExpect(status().isMethodNotAllowed());

    // Validate the Contact in the database
    List<Contact> contactList = contactRepository.findAll();
    assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateContactWithPatch() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    int databaseSizeBeforeUpdate = contactRepository.findAll().size();

    // Update the contact using partial update
    Contact partialUpdatedContact = new Contact();
    partialUpdatedContact.setId(contact.getId());

    partialUpdatedContact
        .title(UPDATED_TITLE)
        .rank(UPDATED_RANK)
        .gender(UPDATED_GENDER)
        .otherFirstNames(UPDATED_OTHER_FIRST_NAMES)
        .birthName(UPDATED_BIRTH_NAME)
        .maritalStatus(UPDATED_MARITAL_STATUS)
        .nickName(UPDATED_NICK_NAME)
        .addressType(UPDATED_ADDRESS_TYPE)
        .city(UPDATED_CITY);

    restContactMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, partialUpdatedContact.getId())
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContact)))
        .andExpect(status().isOk());

    // Validate the Contact in the database
    List<Contact> contactList = contactRepository.findAll();
    assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    Contact testContact = contactList.get(contactList.size() - 1);
    assertThat(testContact.getTitle()).isEqualTo(UPDATED_TITLE);
    assertThat(testContact.getSalutation()).isEqualTo(DEFAULT_SALUTATION);
    assertThat(testContact.getRank()).isEqualTo(UPDATED_RANK);
    assertThat(testContact.getGender()).isEqualTo(UPDATED_GENDER);
    assertThat(testContact.getSurName()).isEqualTo(DEFAULT_SUR_NAME);
    assertThat(testContact.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
    assertThat(testContact.getOtherFirstNames())
        .isEqualTo(UPDATED_OTHER_FIRST_NAMES);
    assertThat(testContact.getBirthName()).isEqualTo(UPDATED_BIRTH_NAME);
    assertThat(testContact.getMaritalStatus())
        .isEqualTo(UPDATED_MARITAL_STATUS);
    assertThat(testContact.getNickName()).isEqualTo(UPDATED_NICK_NAME);
    assertThat(testContact.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
    assertThat(testContact.getBirthCity()).isEqualTo(DEFAULT_BIRTH_CITY);
    assertThat(testContact.getBirthCountry()).isEqualTo(DEFAULT_BIRTH_COUNTRY);
    assertThat(testContact.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
    assertThat(testContact.getAddressType()).isEqualTo(UPDATED_ADDRESS_TYPE);
    assertThat(testContact.getStreet()).isEqualTo(DEFAULT_STREET);
    assertThat(testContact.getStreetNumber()).isEqualTo(DEFAULT_STREET_NUMBER);
    assertThat(testContact.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
    assertThat(testContact.getCity()).isEqualTo(UPDATED_CITY);
    assertThat(testContact.getAdditionalAddressField1())
        .isEqualTo(DEFAULT_ADDITIONAL_ADDRESS_FIELD_1);
    assertThat(testContact.getAdditionalAddressField2())
        .isEqualTo(DEFAULT_ADDITIONAL_ADDRESS_FIELD_2);
    assertThat(testContact.getCommunicationType())
        .isEqualTo(DEFAULT_COMMUNICATION_TYPE);
    assertThat(testContact.getPhoneCountryCode())
        .isEqualTo(DEFAULT_PHONE_COUNTRY_CODE);
    assertThat(testContact.getPhonePrefix()).isEqualTo(DEFAULT_PHONE_PREFIX);
    assertThat(testContact.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    assertThat(testContact.getComment()).isEqualTo(DEFAULT_COMMENT);
  }

  @Test
  @Transactional
  void fullUpdateContactWithPatch() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    int databaseSizeBeforeUpdate = contactRepository.findAll().size();

    // Update the contact using partial update
    Contact partialUpdatedContact = new Contact();
    partialUpdatedContact.setId(contact.getId());

    partialUpdatedContact
        .title(UPDATED_TITLE)
        .salutation(UPDATED_SALUTATION)
        .rank(UPDATED_RANK)
        .gender(UPDATED_GENDER)
        .surName(UPDATED_SUR_NAME)
        .firstName(UPDATED_FIRST_NAME)
        .otherFirstNames(UPDATED_OTHER_FIRST_NAMES)
        .birthName(UPDATED_BIRTH_NAME)
        .maritalStatus(UPDATED_MARITAL_STATUS)
        .nickName(UPDATED_NICK_NAME)
        .birthDate(UPDATED_BIRTH_DATE)
        .birthCity(UPDATED_BIRTH_CITY)
        .birthCountry(UPDATED_BIRTH_COUNTRY)
        .nationality(UPDATED_NATIONALITY)
        .addressType(UPDATED_ADDRESS_TYPE)
        .street(UPDATED_STREET)
        .streetNumber(UPDATED_STREET_NUMBER)
        .postalCode(UPDATED_POSTAL_CODE)
        .city(UPDATED_CITY)
        .additionalAddressField1(UPDATED_ADDITIONAL_ADDRESS_FIELD_1)
        .additionalAddressField2(UPDATED_ADDITIONAL_ADDRESS_FIELD_2)
        .communicationType(UPDATED_COMMUNICATION_TYPE)
        .phoneCountryCode(UPDATED_PHONE_COUNTRY_CODE)
        .phonePrefix(UPDATED_PHONE_PREFIX)
        .phoneNumber(UPDATED_PHONE_NUMBER);

    restContactMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, partialUpdatedContact.getId())
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContact)))
        .andExpect(status().isOk());

    // Validate the Contact in the database
    List<Contact> contactList = contactRepository.findAll();
    assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    Contact testContact = contactList.get(contactList.size() - 1);
    assertThat(testContact.getTitle()).isEqualTo(UPDATED_TITLE);
    assertThat(testContact.getSalutation()).isEqualTo(UPDATED_SALUTATION);
    assertThat(testContact.getRank()).isEqualTo(UPDATED_RANK);
    assertThat(testContact.getGender()).isEqualTo(UPDATED_GENDER);
    assertThat(testContact.getSurName()).isEqualTo(UPDATED_SUR_NAME);
    assertThat(testContact.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
    assertThat(testContact.getOtherFirstNames())
        .isEqualTo(UPDATED_OTHER_FIRST_NAMES);
    assertThat(testContact.getBirthName()).isEqualTo(UPDATED_BIRTH_NAME);
    assertThat(testContact.getMaritalStatus())
        .isEqualTo(UPDATED_MARITAL_STATUS);
    assertThat(testContact.getNickName()).isEqualTo(UPDATED_NICK_NAME);
    assertThat(testContact.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
    assertThat(testContact.getBirthCity()).isEqualTo(UPDATED_BIRTH_CITY);
    assertThat(testContact.getBirthCountry()).isEqualTo(UPDATED_BIRTH_COUNTRY);
    assertThat(testContact.getNationality()).isEqualTo(UPDATED_NATIONALITY);
    assertThat(testContact.getAddressType()).isEqualTo(UPDATED_ADDRESS_TYPE);
    assertThat(testContact.getStreet()).isEqualTo(UPDATED_STREET);
    assertThat(testContact.getStreetNumber()).isEqualTo(UPDATED_STREET_NUMBER);
    assertThat(testContact.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
    assertThat(testContact.getCity()).isEqualTo(UPDATED_CITY);
    assertThat(testContact.getAdditionalAddressField1())
        .isEqualTo(UPDATED_ADDITIONAL_ADDRESS_FIELD_1);
    assertThat(testContact.getAdditionalAddressField2())
        .isEqualTo(UPDATED_ADDITIONAL_ADDRESS_FIELD_2);
    assertThat(testContact.getCommunicationType())
        .isEqualTo(UPDATED_COMMUNICATION_TYPE);
    assertThat(testContact.getPhoneCountryCode())
        .isEqualTo(UPDATED_PHONE_COUNTRY_CODE);
    assertThat(testContact.getPhonePrefix()).isEqualTo(UPDATED_PHONE_PREFIX);
    assertThat(testContact.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    assertThat(testContact.getComment()).isEqualTo(UPDATED_COMMENT);
  }

  @Test
  @Transactional
  void patchNonExistingContact() throws Exception {
    int databaseSizeBeforeUpdate = contactRepository.findAll().size();
    contact.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restContactMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, contact.getId())
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(contact)))
        .andExpect(status().isBadRequest());

    // Validate the Contact in the database
    List<Contact> contactList = contactRepository.findAll();
    assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchContact() throws Exception {
    int databaseSizeBeforeUpdate = contactRepository.findAll().size();
    contact.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restContactMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, count.incrementAndGet())
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(contact)))
        .andExpect(status().isBadRequest());

    // Validate the Contact in the database
    List<Contact> contactList = contactRepository.findAll();
    assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamContact() throws Exception {
    int databaseSizeBeforeUpdate = contactRepository.findAll().size();
    contact.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restContactMockMvc
        .perform(
            patch(ENTITY_API_URL)
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(contact)))
        .andExpect(status().isMethodNotAllowed());

    // Validate the Contact in the database
    List<Contact> contactList = contactRepository.findAll();
    assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteContact() throws Exception {
    // Initialize the database
    contactRepository.saveAndFlush(contact);

    int databaseSizeBeforeDelete = contactRepository.findAll().size();

    // Delete the contact
    restContactMockMvc
        .perform(
            delete(ENTITY_API_URL_ID, contact.getId())
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<Contact> contactList = contactRepository.findAll();
    assertThat(contactList).hasSize(databaseSizeBeforeDelete - 1);
  }
}
