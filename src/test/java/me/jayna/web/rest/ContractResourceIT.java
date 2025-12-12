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
import me.jayna.domain.Contract;
import me.jayna.domain.ContractRealestate;
import me.jayna.domain.GroupType;
import me.jayna.domain.enumeration.PaymentPattern;
import me.jayna.domain.enumeration.PaymentType;
import me.jayna.repository.ContractRepository;
import me.jayna.service.criteria.ContractCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ContractResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContractResourceIT {

  private static final String DEFAULT_CONTRACTOR = "AAAAAAAAAA";
  private static final String UPDATED_CONTRACTOR = "BBBBBBBBBB";

  private static final String DEFAULT_CONTRACT_OWNER = "AAAAAAAAAA";
  private static final String UPDATED_CONTRACT_OWNER = "BBBBBBBBBB";

  private static final String DEFAULT_CONTRACT_USER = "AAAAAAAAAA";
  private static final String UPDATED_CONTRACT_USER = "BBBBBBBBBB";

  private static final String DEFAULT_CONTRACT_NUMBER = "AAAAAAAAAA";
  private static final String UPDATED_CONTRACT_NUMBER = "BBBBBBBBBB";

  private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
  private static final LocalDate UPDATED_START_DATE = LocalDate.now(
      ZoneId.systemDefault());
  private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

  private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
  private static final LocalDate UPDATED_END_DATE = LocalDate.now(
      ZoneId.systemDefault());
  private static final LocalDate SMALLER_END_DATE = LocalDate.ofEpochDay(-1L);

  private static final LocalDate DEFAULT_APPLICATION_DATE = LocalDate.ofEpochDay(
      0L);
  private static final LocalDate UPDATED_APPLICATION_DATE = LocalDate.now(
      ZoneId.systemDefault());
  private static final LocalDate SMALLER_APPLICATION_DATE = LocalDate.ofEpochDay(
      -1L);

  private static final LocalDate DEFAULT_CONFIRMATION_DATE = LocalDate.ofEpochDay(
      0L);
  private static final LocalDate UPDATED_CONFIRMATION_DATE = LocalDate.now(
      ZoneId.systemDefault());
  private static final LocalDate SMALLER_CONFIRMATION_DATE = LocalDate.ofEpochDay(
      -1L);

  private static final PaymentPattern DEFAULT_PAYMENT_PATTERN = PaymentPattern.ONETIME;
  private static final PaymentPattern UPDATED_PAYMENT_PATTERN = PaymentPattern.WEEKLY;

  private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
  private static final String UPDATED_COMMENT = "BBBBBBBBBB";

  private static final String DEFAULT_PAYER = "AAAAAAAAAA";
  private static final String UPDATED_PAYER = "BBBBBBBBBB";

  private static final PaymentType DEFAULT_PAYMENT_TYPE = PaymentType.TRANSFER;
  private static final PaymentType UPDATED_PAYMENT_TYPE = PaymentType.SEPA_DEBIT;

  private static final Double DEFAULT_SALARY = 1D;
  private static final Double UPDATED_SALARY = 2D;
  private static final Double SMALLER_SALARY = 1D - 1D;

  private static final Integer DEFAULT_VACATION_DAYS = 1;
  private static final Integer UPDATED_VACATION_DAYS = 2;
  private static final Integer SMALLER_VACATION_DAYS = 1 - 1;

  private static final String DEFAULT_OCCUPATION = "AAAAAAAAAA";
  private static final String UPDATED_OCCUPATION = "BBBBBBBBBB";

  private static final String DEFAULT_ACTIVITIES = "AAAAAAAAAA";
  private static final String UPDATED_ACTIVITIES = "BBBBBBBBBB";

  private static final Double DEFAULT_LOAN_VALUE = 1D;
  private static final Double UPDATED_LOAN_VALUE = 2D;
  private static final Double SMALLER_LOAN_VALUE = 1D - 1D;

  private static final Double DEFAULT_INTEREST_RATE_PERCENT = 1D;
  private static final Double UPDATED_INTEREST_RATE_PERCENT = 2D;
  private static final Double SMALLER_INTEREST_RATE_PERCENT = 1D - 1D;

  private static final Double DEFAULT_INTEREST_RATE_CURRENCY = 1D;
  private static final Double UPDATED_INTEREST_RATE_CURRENCY = 2D;
  private static final Double SMALLER_INTEREST_RATE_CURRENCY = 1D - 1D;

  private static final Double DEFAULT_AMORTIZATION_RATE_PERCENT = 1D;
  private static final Double UPDATED_AMORTIZATION_RATE_PERCENT = 2D;
  private static final Double SMALLER_AMORTIZATION_RATE_PERCENT = 1D - 1D;

  private static final Double DEFAULT_AMORTIZATION_RATE_CURRENCY = 1D;
  private static final Double UPDATED_AMORTIZATION_RATE_CURRENCY = 2D;
  private static final Double SMALLER_AMORTIZATION_RATE_CURRENCY = 1D - 1D;

  private static final String ENTITY_API_URL = "/api/contracts";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(
      random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private ContractRepository contractRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restContractMockMvc;

  private Contract contract;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Contract createEntity(EntityManager em) {
    Contract contract = new Contract()
        .contractor(DEFAULT_CONTRACTOR)
        .contractOwner(DEFAULT_CONTRACT_OWNER)
        .contractUser(DEFAULT_CONTRACT_USER)
        .contractNumber(DEFAULT_CONTRACT_NUMBER)
        .startDate(DEFAULT_START_DATE)
        .endDate(DEFAULT_END_DATE)
        .applicationDate(DEFAULT_APPLICATION_DATE)
        .confirmationDate(DEFAULT_CONFIRMATION_DATE)
        .paymentPattern(DEFAULT_PAYMENT_PATTERN)
        .payer(DEFAULT_PAYER)
        .paymentType(DEFAULT_PAYMENT_TYPE)
        .occupation(DEFAULT_OCCUPATION)
        .activities(DEFAULT_ACTIVITIES)
        .loanValue(DEFAULT_LOAN_VALUE)
        .interestRatePercent(DEFAULT_INTEREST_RATE_PERCENT)
        .interestRateCurrency(DEFAULT_INTEREST_RATE_CURRENCY)
        .amortizationRatePercent(DEFAULT_AMORTIZATION_RATE_PERCENT)
        .amortizationRateCurrency(DEFAULT_AMORTIZATION_RATE_CURRENCY);
    return contract;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Contract createUpdatedEntity(EntityManager em) {
    Contract contract = new Contract()
        .contractor(UPDATED_CONTRACTOR)
        .contractOwner(UPDATED_CONTRACT_OWNER)
        .contractUser(UPDATED_CONTRACT_USER)
        .contractNumber(UPDATED_CONTRACT_NUMBER)
        .startDate(UPDATED_START_DATE)
        .endDate(UPDATED_END_DATE)
        .applicationDate(UPDATED_APPLICATION_DATE)
        .confirmationDate(UPDATED_CONFIRMATION_DATE)
        .paymentPattern(UPDATED_PAYMENT_PATTERN)
        .payer(UPDATED_PAYER)
        .paymentType(UPDATED_PAYMENT_TYPE)
        .occupation(UPDATED_OCCUPATION)
        .activities(UPDATED_ACTIVITIES)
        .loanValue(UPDATED_LOAN_VALUE)
        .interestRatePercent(UPDATED_INTEREST_RATE_PERCENT)
        .interestRateCurrency(UPDATED_INTEREST_RATE_CURRENCY)
        .amortizationRatePercent(UPDATED_AMORTIZATION_RATE_PERCENT)
        .amortizationRateCurrency(UPDATED_AMORTIZATION_RATE_CURRENCY);
    return contract;
  }

  @BeforeEach
  public void initTest() {
    contract = createEntity(em);
  }

  @Test
  @Transactional
  void createContract() throws Exception {
    int databaseSizeBeforeCreate = contractRepository.findAll().size();
    // Create the Contract
    restContractMockMvc
        .perform(
            post(ENTITY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(contract)))
        .andExpect(status().isCreated());

    // Validate the Contract in the database
    List<Contract> contractList = contractRepository.findAll();
    assertThat(contractList).hasSize(databaseSizeBeforeCreate + 1);
    Contract testContract = contractList.get(contractList.size() - 1);
    assertThat(testContract.getContractor()).isEqualTo(DEFAULT_CONTRACTOR);
    assertThat(testContract.getContractOwner())
        .isEqualTo(DEFAULT_CONTRACT_OWNER);
    assertThat(testContract.getContractUser()).isEqualTo(DEFAULT_CONTRACT_USER);
    assertThat(testContract.getContractNumber())
        .isEqualTo(DEFAULT_CONTRACT_NUMBER);
    assertThat(testContract.getStartDate()).isEqualTo(DEFAULT_START_DATE);
    assertThat(testContract.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    assertThat(testContract.getApplicationDate())
        .isEqualTo(DEFAULT_APPLICATION_DATE);
    assertThat(testContract.getConfirmationDate())
        .isEqualTo(DEFAULT_CONFIRMATION_DATE);
    assertThat(testContract.getPaymentPattern())
        .isEqualTo(DEFAULT_PAYMENT_PATTERN);
    assertThat(testContract.getComment()).isEqualTo(DEFAULT_COMMENT);
    assertThat(testContract.getPayer()).isEqualTo(DEFAULT_PAYER);
    assertThat(testContract.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
    assertThat(testContract.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
    assertThat(testContract.getActivities()).isEqualTo(DEFAULT_ACTIVITIES);
    assertThat(testContract.getLoanValue()).isEqualTo(DEFAULT_LOAN_VALUE);
    assertThat(testContract.getInterestRatePercent())
        .isEqualTo(DEFAULT_INTEREST_RATE_PERCENT);
    assertThat(testContract.getInterestRateCurrency())
        .isEqualTo(DEFAULT_INTEREST_RATE_CURRENCY);
    assertThat(testContract.getAmortizationRatePercent())
        .isEqualTo(DEFAULT_AMORTIZATION_RATE_PERCENT);
    assertThat(testContract.getAmortizationRateCurrency())
        .isEqualTo(DEFAULT_AMORTIZATION_RATE_CURRENCY);
  }

  @Test
  @Transactional
  void createContractWithExistingId() throws Exception {
    // Create the Contract with an existing ID
    contract.setId(1L);

    int databaseSizeBeforeCreate = contractRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restContractMockMvc
        .perform(
            post(ENTITY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(contract)))
        .andExpect(status().isBadRequest());

    // Validate the Contract in the database
    List<Contract> contractList = contractRepository.findAll();
    assertThat(contractList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void getAllContracts() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList
    restContractMockMvc
        .perform(get(ENTITY_API_URL + "?sort=id,desc"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(
            jsonPath("$.[*].id").value(hasItem(contract.getId().intValue())))
        .andExpect(
            jsonPath("$.[*].contractor").value(hasItem(DEFAULT_CONTRACTOR)))
        .andExpect(
            jsonPath("$.[*].contractOwner").value(hasItem(DEFAULT_CONTRACT_OWNER)))
        .andExpect(
            jsonPath("$.[*].contractUser").value(hasItem(DEFAULT_CONTRACT_USER)))
        .andExpect(
            jsonPath("$.[*].contractNumber").value(hasItem(DEFAULT_CONTRACT_NUMBER)))
        .andExpect(
            jsonPath("$.[*].startDate")
                .value(hasItem(DEFAULT_START_DATE.toString())))
        .andExpect(
            jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
        .andExpect(
            jsonPath("$.[*].applicationDate")
                .value(hasItem(DEFAULT_APPLICATION_DATE.toString())))
        .andExpect(
            jsonPath("$.[*].confirmationDate")
                .value(hasItem(DEFAULT_CONFIRMATION_DATE.toString())))
        .andExpect(
            jsonPath("$.[*].paymentPattern")
                .value(hasItem(DEFAULT_PAYMENT_PATTERN.toString())))
        .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
        .andExpect(jsonPath("$.[*].payer").value(hasItem(DEFAULT_PAYER)))
        .andExpect(
            jsonPath("$.[*].paymentType")
                .value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
        .andExpect(
            jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.doubleValue())))
        .andExpect(
            jsonPath("$.[*].vacationDays").value(hasItem(DEFAULT_VACATION_DAYS)))
        .andExpect(
            jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION)))
        .andExpect(
            jsonPath("$.[*].activities").value(hasItem(DEFAULT_ACTIVITIES)))
        .andExpect(
            jsonPath("$.[*].loanValue")
                .value(hasItem(DEFAULT_LOAN_VALUE.doubleValue())))
        .andExpect(
            jsonPath("$.[*].interestRatePercent")
                .value(hasItem(DEFAULT_INTEREST_RATE_PERCENT.doubleValue())))
        .andExpect(
            jsonPath("$.[*].interestRateCurrency")
                .value(hasItem(DEFAULT_INTEREST_RATE_CURRENCY.doubleValue())))
        .andExpect(
            jsonPath("$.[*].amortizationRatePercent")
                .value(hasItem(DEFAULT_AMORTIZATION_RATE_PERCENT.doubleValue())))
        .andExpect(
            jsonPath("$.[*].amortizationRateCurrency")
                .value(hasItem(DEFAULT_AMORTIZATION_RATE_CURRENCY.doubleValue())));
  }

  @Test
  @Transactional
  void getContract() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get the contract
    restContractMockMvc
        .perform(get(ENTITY_API_URL_ID, contract.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.id").value(contract.getId().intValue()))
        .andExpect(jsonPath("$.contractor").value(DEFAULT_CONTRACTOR))
        .andExpect(jsonPath("$.contractOwner").value(DEFAULT_CONTRACT_OWNER))
        .andExpect(jsonPath("$.contractUser").value(DEFAULT_CONTRACT_USER))
        .andExpect(jsonPath("$.contractNumber").value(DEFAULT_CONTRACT_NUMBER))
        .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
        .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
        .andExpect(
            jsonPath("$.applicationDate").value(DEFAULT_APPLICATION_DATE.toString()))
        .andExpect(
            jsonPath("$.confirmationDate")
                .value(DEFAULT_CONFIRMATION_DATE.toString()))
        .andExpect(
            jsonPath("$.paymentPattern").value(DEFAULT_PAYMENT_PATTERN.toString()))
        .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
        .andExpect(jsonPath("$.payer").value(DEFAULT_PAYER))
        .andExpect(
            jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE.toString()))
        .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY.doubleValue()))
        .andExpect(jsonPath("$.vacationDays").value(DEFAULT_VACATION_DAYS))
        .andExpect(jsonPath("$.occupation").value(DEFAULT_OCCUPATION))
        .andExpect(jsonPath("$.activities").value(DEFAULT_ACTIVITIES))
        .andExpect(
            jsonPath("$.loanValue").value(DEFAULT_LOAN_VALUE.doubleValue()))
        .andExpect(
            jsonPath("$.interestRatePercent")
                .value(DEFAULT_INTEREST_RATE_PERCENT.doubleValue()))
        .andExpect(
            jsonPath("$.interestRateCurrency")
                .value(DEFAULT_INTEREST_RATE_CURRENCY.doubleValue()))
        .andExpect(
            jsonPath("$.amortizationRatePercent")
                .value(DEFAULT_AMORTIZATION_RATE_PERCENT.doubleValue()))
        .andExpect(
            jsonPath("$.amortizationRateCurrency")
                .value(DEFAULT_AMORTIZATION_RATE_CURRENCY.doubleValue()));
  }

  @Test
  @Transactional
  void getContractsByIdFiltering() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    Long id = contract.getId();

    defaultContractShouldBeFound("id.equals=" + id);
    defaultContractShouldNotBeFound("id.notEquals=" + id);

    defaultContractShouldBeFound("id.greaterThanOrEqual=" + id);
    defaultContractShouldNotBeFound("id.greaterThan=" + id);

    defaultContractShouldBeFound("id.lessThanOrEqual=" + id);
    defaultContractShouldNotBeFound("id.lessThan=" + id);
  }

  @Test
  @Transactional
  void getAllContractsByContractorIsEqualToSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where contractor equals to DEFAULT_CONTRACTOR
    defaultContractShouldBeFound("contractor.equals=" + DEFAULT_CONTRACTOR);

    // Get all the contractList where contractor equals to UPDATED_CONTRACTOR
    defaultContractShouldNotBeFound("contractor.equals=" + UPDATED_CONTRACTOR);
  }

  @Test
  @Transactional
  void getAllContractsByContractorIsInShouldWork() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where contractor in DEFAULT_CONTRACTOR or
    // UPDATED_CONTRACTOR
    defaultContractShouldBeFound(
        "contractor.in=" + DEFAULT_CONTRACTOR + "," + UPDATED_CONTRACTOR);

    // Get all the contractList where contractor equals to UPDATED_CONTRACTOR
    defaultContractShouldNotBeFound("contractor.in=" + UPDATED_CONTRACTOR);
  }

  @Test
  @Transactional
  void getAllContractsByContractorIsNullOrNotNull() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where contractor is not null
    defaultContractShouldBeFound("contractor.specified=true");

    // Get all the contractList where contractor is null
    defaultContractShouldNotBeFound("contractor.specified=false");
  }

  @Test
  @Transactional
  void getAllContractsByContractorContainsSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where contractor contains DEFAULT_CONTRACTOR
    defaultContractShouldBeFound("contractor.contains=" + DEFAULT_CONTRACTOR);

    // Get all the contractList where contractor contains UPDATED_CONTRACTOR
    defaultContractShouldNotBeFound(
        "contractor.contains=" + UPDATED_CONTRACTOR);
  }

  @Test
  @Transactional
  void getAllContractsByContractorNotContainsSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where contractor does not contain DEFAULT_CONTRACTOR
    defaultContractShouldNotBeFound(
        "contractor.doesNotContain=" + DEFAULT_CONTRACTOR);

    // Get all the contractList where contractor does not contain UPDATED_CONTRACTOR
    defaultContractShouldBeFound(
        "contractor.doesNotContain=" + UPDATED_CONTRACTOR);
  }

  @Test
  @Transactional
  void getAllContractsByContractOwnerIsEqualToSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where contractOwner equals to DEFAULT_CONTRACT_OWNER
    defaultContractShouldBeFound(
        "contractOwner.equals=" + DEFAULT_CONTRACT_OWNER);

    // Get all the contractList where contractOwner equals to UPDATED_CONTRACT_OWNER
    defaultContractShouldNotBeFound(
        "contractOwner.equals=" + UPDATED_CONTRACT_OWNER);
  }

  @Test
  @Transactional
  void getAllContractsByContractOwnerIsInShouldWork() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where contractOwner in DEFAULT_CONTRACT_OWNER or
    // UPDATED_CONTRACT_OWNER
    defaultContractShouldBeFound(
        "contractOwner.in=" +
            DEFAULT_CONTRACT_OWNER +
            "," +
            UPDATED_CONTRACT_OWNER);

    // Get all the contractList where contractOwner equals to UPDATED_CONTRACT_OWNER
    defaultContractShouldNotBeFound(
        "contractOwner.in=" + UPDATED_CONTRACT_OWNER);
  }

  @Test
  @Transactional
  void getAllContractsByContractOwnerIsNullOrNotNull() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where contractOwner is not null
    defaultContractShouldBeFound("contractOwner.specified=true");

    // Get all the contractList where contractOwner is null
    defaultContractShouldNotBeFound("contractOwner.specified=false");
  }

  @Test
  @Transactional
  void getAllContractsByContractOwnerContainsSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where contractOwner contains DEFAULT_CONTRACT_OWNER
    defaultContractShouldBeFound(
        "contractOwner.contains=" + DEFAULT_CONTRACT_OWNER);

    // Get all the contractList where contractOwner contains UPDATED_CONTRACT_OWNER
    defaultContractShouldNotBeFound(
        "contractOwner.contains=" + UPDATED_CONTRACT_OWNER);
  }

  @Test
  @Transactional
  void getAllContractsByContractOwnerNotContainsSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where contractOwner does not contain
    // DEFAULT_CONTRACT_OWNER
    defaultContractShouldNotBeFound(
        "contractOwner.doesNotContain=" + DEFAULT_CONTRACT_OWNER);

    // Get all the contractList where contractOwner does not contain
    // UPDATED_CONTRACT_OWNER
    defaultContractShouldBeFound(
        "contractOwner.doesNotContain=" + UPDATED_CONTRACT_OWNER);
  }

  @Test
  @Transactional
  void getAllContractsByContractUserIsEqualToSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where contractUser equals to DEFAULT_CONTRACT_USER
    defaultContractShouldBeFound(
        "contractUser.equals=" + DEFAULT_CONTRACT_USER);

    // Get all the contractList where contractUser equals to UPDATED_CONTRACT_USER
    defaultContractShouldNotBeFound(
        "contractUser.equals=" + UPDATED_CONTRACT_USER);
  }

  @Test
  @Transactional
  void getAllContractsByContractUserIsInShouldWork() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where contractUser in DEFAULT_CONTRACT_USER or
    // UPDATED_CONTRACT_USER
    defaultContractShouldBeFound(
        "contractUser.in=" + DEFAULT_CONTRACT_USER + "," + UPDATED_CONTRACT_USER);

    // Get all the contractList where contractUser equals to UPDATED_CONTRACT_USER
    defaultContractShouldNotBeFound("contractUser.in=" + UPDATED_CONTRACT_USER);
  }

  @Test
  @Transactional
  void getAllContractsByContractUserIsNullOrNotNull() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where contractUser is not null
    defaultContractShouldBeFound("contractUser.specified=true");

    // Get all the contractList where contractUser is null
    defaultContractShouldNotBeFound("contractUser.specified=false");
  }

  @Test
  @Transactional
  void getAllContractsByContractUserContainsSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where contractUser contains DEFAULT_CONTRACT_USER
    defaultContractShouldBeFound(
        "contractUser.contains=" + DEFAULT_CONTRACT_USER);

    // Get all the contractList where contractUser contains UPDATED_CONTRACT_USER
    defaultContractShouldNotBeFound(
        "contractUser.contains=" + UPDATED_CONTRACT_USER);
  }

  @Test
  @Transactional
  void getAllContractsByContractUserNotContainsSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where contractUser does not contain
    // DEFAULT_CONTRACT_USER
    defaultContractShouldNotBeFound(
        "contractUser.doesNotContain=" + DEFAULT_CONTRACT_USER);

    // Get all the contractList where contractUser does not contain
    // UPDATED_CONTRACT_USER
    defaultContractShouldBeFound(
        "contractUser.doesNotContain=" + UPDATED_CONTRACT_USER);
  }

  @Test
  @Transactional
  void getAllContractsByContractNumberIsEqualToSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where contractNumber equals to
    // DEFAULT_CONTRACT_NUMBER
    defaultContractShouldBeFound(
        "contractNumber.equals=" + DEFAULT_CONTRACT_NUMBER);

    // Get all the contractList where contractNumber equals to
    // UPDATED_CONTRACT_NUMBER
    defaultContractShouldNotBeFound(
        "contractNumber.equals=" + UPDATED_CONTRACT_NUMBER);
  }

  @Test
  @Transactional
  void getAllContractsByContractNumberIsInShouldWork() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where contractNumber in DEFAULT_CONTRACT_NUMBER or
    // UPDATED_CONTRACT_NUMBER
    defaultContractShouldBeFound(
        "contractNumber.in=" +
            DEFAULT_CONTRACT_NUMBER +
            "," +
            UPDATED_CONTRACT_NUMBER);

    // Get all the contractList where contractNumber equals to
    // UPDATED_CONTRACT_NUMBER
    defaultContractShouldNotBeFound(
        "contractNumber.in=" + UPDATED_CONTRACT_NUMBER);
  }

  @Test
  @Transactional
  void getAllContractsByContractNumberIsNullOrNotNull() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where contractNumber is not null
    defaultContractShouldBeFound("contractNumber.specified=true");

    // Get all the contractList where contractNumber is null
    defaultContractShouldNotBeFound("contractNumber.specified=false");
  }

  @Test
  @Transactional
  void getAllContractsByContractNumberContainsSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where contractNumber contains
    // DEFAULT_CONTRACT_NUMBER
    defaultContractShouldBeFound(
        "contractNumber.contains=" + DEFAULT_CONTRACT_NUMBER);

    // Get all the contractList where contractNumber contains
    // UPDATED_CONTRACT_NUMBER
    defaultContractShouldNotBeFound(
        "contractNumber.contains=" + UPDATED_CONTRACT_NUMBER);
  }

  @Test
  @Transactional
  void getAllContractsByContractNumberNotContainsSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where contractNumber does not contain
    // DEFAULT_CONTRACT_NUMBER
    defaultContractShouldNotBeFound(
        "contractNumber.doesNotContain=" + DEFAULT_CONTRACT_NUMBER);

    // Get all the contractList where contractNumber does not contain
    // UPDATED_CONTRACT_NUMBER
    defaultContractShouldBeFound(
        "contractNumber.doesNotContain=" + UPDATED_CONTRACT_NUMBER);
  }

  @Test
  @Transactional
  void getAllContractsByStartDateIsEqualToSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where startDate equals to DEFAULT_START_DATE
    defaultContractShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

    // Get all the contractList where startDate equals to UPDATED_START_DATE
    defaultContractShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
  }

  @Test
  @Transactional
  void getAllContractsByStartDateIsInShouldWork() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where startDate in DEFAULT_START_DATE or
    // UPDATED_START_DATE
    defaultContractShouldBeFound(
        "startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

    // Get all the contractList where startDate equals to UPDATED_START_DATE
    defaultContractShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
  }

  @Test
  @Transactional
  void getAllContractsByStartDateIsNullOrNotNull() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where startDate is not null
    defaultContractShouldBeFound("startDate.specified=true");

    // Get all the contractList where startDate is null
    defaultContractShouldNotBeFound("startDate.specified=false");
  }

  @Test
  @Transactional
  void getAllContractsByStartDateIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where startDate is greater than or equal to
    // DEFAULT_START_DATE
    defaultContractShouldBeFound(
        "startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

    // Get all the contractList where startDate is greater than or equal to
    // UPDATED_START_DATE
    defaultContractShouldNotBeFound(
        "startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
  }

  @Test
  @Transactional
  void getAllContractsByStartDateIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where startDate is less than or equal to
    // DEFAULT_START_DATE
    defaultContractShouldBeFound(
        "startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

    // Get all the contractList where startDate is less than or equal to
    // SMALLER_START_DATE
    defaultContractShouldNotBeFound(
        "startDate.lessThanOrEqual=" + SMALLER_START_DATE);
  }

  @Test
  @Transactional
  void getAllContractsByStartDateIsLessThanSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where startDate is less than DEFAULT_START_DATE
    defaultContractShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

    // Get all the contractList where startDate is less than UPDATED_START_DATE
    defaultContractShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
  }

  @Test
  @Transactional
  void getAllContractsByStartDateIsGreaterThanSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where startDate is greater than DEFAULT_START_DATE
    defaultContractShouldNotBeFound(
        "startDate.greaterThan=" + DEFAULT_START_DATE);

    // Get all the contractList where startDate is greater than SMALLER_START_DATE
    defaultContractShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
  }

  @Test
  @Transactional
  void getAllContractsByEndDateIsEqualToSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where endDate equals to DEFAULT_END_DATE
    defaultContractShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

    // Get all the contractList where endDate equals to UPDATED_END_DATE
    defaultContractShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
  }

  @Test
  @Transactional
  void getAllContractsByEndDateIsInShouldWork() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where endDate in DEFAULT_END_DATE or
    // UPDATED_END_DATE
    defaultContractShouldBeFound(
        "endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

    // Get all the contractList where endDate equals to UPDATED_END_DATE
    defaultContractShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
  }

  @Test
  @Transactional
  void getAllContractsByEndDateIsNullOrNotNull() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where endDate is not null
    defaultContractShouldBeFound("endDate.specified=true");

    // Get all the contractList where endDate is null
    defaultContractShouldNotBeFound("endDate.specified=false");
  }

  @Test
  @Transactional
  void getAllContractsByEndDateIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where endDate is greater than or equal to
    // DEFAULT_END_DATE
    defaultContractShouldBeFound(
        "endDate.greaterThanOrEqual=" + DEFAULT_END_DATE);

    // Get all the contractList where endDate is greater than or equal to
    // UPDATED_END_DATE
    defaultContractShouldNotBeFound(
        "endDate.greaterThanOrEqual=" + UPDATED_END_DATE);
  }

  @Test
  @Transactional
  void getAllContractsByEndDateIsLessThanOrEqualToSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where endDate is less than or equal to
    // DEFAULT_END_DATE
    defaultContractShouldBeFound("endDate.lessThanOrEqual=" + DEFAULT_END_DATE);

    // Get all the contractList where endDate is less than or equal to
    // SMALLER_END_DATE
    defaultContractShouldNotBeFound(
        "endDate.lessThanOrEqual=" + SMALLER_END_DATE);
  }

  @Test
  @Transactional
  void getAllContractsByEndDateIsLessThanSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where endDate is less than DEFAULT_END_DATE
    defaultContractShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

    // Get all the contractList where endDate is less than UPDATED_END_DATE
    defaultContractShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
  }

  @Test
  @Transactional
  void getAllContractsByEndDateIsGreaterThanSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where endDate is greater than DEFAULT_END_DATE
    defaultContractShouldNotBeFound("endDate.greaterThan=" + DEFAULT_END_DATE);

    // Get all the contractList where endDate is greater than SMALLER_END_DATE
    defaultContractShouldBeFound("endDate.greaterThan=" + SMALLER_END_DATE);
  }

  @Test
  @Transactional
  void getAllContractsByApplicationDateIsEqualToSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where applicationDate equals to
    // DEFAULT_APPLICATION_DATE
    defaultContractShouldBeFound(
        "applicationDate.equals=" + DEFAULT_APPLICATION_DATE);

    // Get all the contractList where applicationDate equals to
    // UPDATED_APPLICATION_DATE
    defaultContractShouldNotBeFound(
        "applicationDate.equals=" + UPDATED_APPLICATION_DATE);
  }

  @Test
  @Transactional
  void getAllContractsByApplicationDateIsInShouldWork() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where applicationDate in DEFAULT_APPLICATION_DATE or
    // UPDATED_APPLICATION_DATE
    defaultContractShouldBeFound(
        "applicationDate.in=" +
            DEFAULT_APPLICATION_DATE +
            "," +
            UPDATED_APPLICATION_DATE);

    // Get all the contractList where applicationDate equals to
    // UPDATED_APPLICATION_DATE
    defaultContractShouldNotBeFound(
        "applicationDate.in=" + UPDATED_APPLICATION_DATE);
  }

  @Test
  @Transactional
  void getAllContractsByApplicationDateIsNullOrNotNull() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where applicationDate is not null
    defaultContractShouldBeFound("applicationDate.specified=true");

    // Get all the contractList where applicationDate is null
    defaultContractShouldNotBeFound("applicationDate.specified=false");
  }

  @Test
  @Transactional
  void getAllContractsByApplicationDateIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where applicationDate is greater than or equal to
    // DEFAULT_APPLICATION_DATE
    defaultContractShouldBeFound(
        "applicationDate.greaterThanOrEqual=" + DEFAULT_APPLICATION_DATE);

    // Get all the contractList where applicationDate is greater than or equal to
    // UPDATED_APPLICATION_DATE
    defaultContractShouldNotBeFound(
        "applicationDate.greaterThanOrEqual=" + UPDATED_APPLICATION_DATE);
  }

  @Test
  @Transactional
  void getAllContractsByApplicationDateIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where applicationDate is less than or equal to
    // DEFAULT_APPLICATION_DATE
    defaultContractShouldBeFound(
        "applicationDate.lessThanOrEqual=" + DEFAULT_APPLICATION_DATE);

    // Get all the contractList where applicationDate is less than or equal to
    // SMALLER_APPLICATION_DATE
    defaultContractShouldNotBeFound(
        "applicationDate.lessThanOrEqual=" + SMALLER_APPLICATION_DATE);
  }

  @Test
  @Transactional
  void getAllContractsByApplicationDateIsLessThanSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where applicationDate is less than
    // DEFAULT_APPLICATION_DATE
    defaultContractShouldNotBeFound(
        "applicationDate.lessThan=" + DEFAULT_APPLICATION_DATE);

    // Get all the contractList where applicationDate is less than
    // UPDATED_APPLICATION_DATE
    defaultContractShouldBeFound(
        "applicationDate.lessThan=" + UPDATED_APPLICATION_DATE);
  }

  @Test
  @Transactional
  void getAllContractsByApplicationDateIsGreaterThanSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where applicationDate is greater than
    // DEFAULT_APPLICATION_DATE
    defaultContractShouldNotBeFound(
        "applicationDate.greaterThan=" + DEFAULT_APPLICATION_DATE);

    // Get all the contractList where applicationDate is greater than
    // SMALLER_APPLICATION_DATE
    defaultContractShouldBeFound(
        "applicationDate.greaterThan=" + SMALLER_APPLICATION_DATE);
  }

  @Test
  @Transactional
  void getAllContractsByConfirmationDateIsEqualToSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where confirmationDate equals to
    // DEFAULT_CONFIRMATION_DATE
    defaultContractShouldBeFound(
        "confirmationDate.equals=" + DEFAULT_CONFIRMATION_DATE);

    // Get all the contractList where confirmationDate equals to
    // UPDATED_CONFIRMATION_DATE
    defaultContractShouldNotBeFound(
        "confirmationDate.equals=" + UPDATED_CONFIRMATION_DATE);
  }

  @Test
  @Transactional
  void getAllContractsByConfirmationDateIsInShouldWork() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where confirmationDate in DEFAULT_CONFIRMATION_DATE
    // or UPDATED_CONFIRMATION_DATE
    defaultContractShouldBeFound(
        "confirmationDate.in=" +
            DEFAULT_CONFIRMATION_DATE +
            "," +
            UPDATED_CONFIRMATION_DATE);

    // Get all the contractList where confirmationDate equals to
    // UPDATED_CONFIRMATION_DATE
    defaultContractShouldNotBeFound(
        "confirmationDate.in=" + UPDATED_CONFIRMATION_DATE);
  }

  @Test
  @Transactional
  void getAllContractsByConfirmationDateIsNullOrNotNull() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where confirmationDate is not null
    defaultContractShouldBeFound("confirmationDate.specified=true");

    // Get all the contractList where confirmationDate is null
    defaultContractShouldNotBeFound("confirmationDate.specified=false");
  }

  @Test
  @Transactional
  void getAllContractsByConfirmationDateIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where confirmationDate is greater than or equal to
    // DEFAULT_CONFIRMATION_DATE
    defaultContractShouldBeFound(
        "confirmationDate.greaterThanOrEqual=" + DEFAULT_CONFIRMATION_DATE);

    // Get all the contractList where confirmationDate is greater than or equal to
    // UPDATED_CONFIRMATION_DATE
    defaultContractShouldNotBeFound(
        "confirmationDate.greaterThanOrEqual=" + UPDATED_CONFIRMATION_DATE);
  }

  @Test
  @Transactional
  void getAllContractsByConfirmationDateIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where confirmationDate is less than or equal to
    // DEFAULT_CONFIRMATION_DATE
    defaultContractShouldBeFound(
        "confirmationDate.lessThanOrEqual=" + DEFAULT_CONFIRMATION_DATE);

    // Get all the contractList where confirmationDate is less than or equal to
    // SMALLER_CONFIRMATION_DATE
    defaultContractShouldNotBeFound(
        "confirmationDate.lessThanOrEqual=" + SMALLER_CONFIRMATION_DATE);
  }

  @Test
  @Transactional
  void getAllContractsByConfirmationDateIsLessThanSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where confirmationDate is less than
    // DEFAULT_CONFIRMATION_DATE
    defaultContractShouldNotBeFound(
        "confirmationDate.lessThan=" + DEFAULT_CONFIRMATION_DATE);

    // Get all the contractList where confirmationDate is less than
    // UPDATED_CONFIRMATION_DATE
    defaultContractShouldBeFound(
        "confirmationDate.lessThan=" + UPDATED_CONFIRMATION_DATE);
  }

  @Test
  @Transactional
  void getAllContractsByConfirmationDateIsGreaterThanSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where confirmationDate is greater than
    // DEFAULT_CONFIRMATION_DATE
    defaultContractShouldNotBeFound(
        "confirmationDate.greaterThan=" + DEFAULT_CONFIRMATION_DATE);

    // Get all the contractList where confirmationDate is greater than
    // SMALLER_CONFIRMATION_DATE
    defaultContractShouldBeFound(
        "confirmationDate.greaterThan=" + SMALLER_CONFIRMATION_DATE);
  }

  @Test
  @Transactional
  void getAllContractsByPaymentPatternIsEqualToSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where paymentPattern equals to
    // DEFAULT_PAYMENT_PATTERN
    defaultContractShouldBeFound(
        "paymentPattern.equals=" + DEFAULT_PAYMENT_PATTERN);

    // Get all the contractList where paymentPattern equals to
    // UPDATED_PAYMENT_PATTERN
    defaultContractShouldNotBeFound(
        "paymentPattern.equals=" + UPDATED_PAYMENT_PATTERN);
  }

  @Test
  @Transactional
  void getAllContractsByPaymentPatternIsInShouldWork() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where paymentPattern in DEFAULT_PAYMENT_PATTERN or
    // UPDATED_PAYMENT_PATTERN
    defaultContractShouldBeFound(
        "paymentPattern.in=" +
            DEFAULT_PAYMENT_PATTERN +
            "," +
            UPDATED_PAYMENT_PATTERN);

    // Get all the contractList where paymentPattern equals to
    // UPDATED_PAYMENT_PATTERN
    defaultContractShouldNotBeFound(
        "paymentPattern.in=" + UPDATED_PAYMENT_PATTERN);
  }

  @Test
  @Transactional
  void getAllContractsByPaymentPatternIsNullOrNotNull() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where paymentPattern is not null
    defaultContractShouldBeFound("paymentPattern.specified=true");

    // Get all the contractList where paymentPattern is null
    defaultContractShouldNotBeFound("paymentPattern.specified=false");
  }

  @Test
  @Transactional
  void getAllContractsByCommentIsEqualToSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where comment equals to DEFAULT_COMMENT
    defaultContractShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

    // Get all the contractList where comment equals to UPDATED_COMMENT
    defaultContractShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
  }

  @Test
  @Transactional
  void getAllContractsByCommentIsInShouldWork() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
    defaultContractShouldBeFound(
        "comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

    // Get all the contractList where comment equals to UPDATED_COMMENT
    defaultContractShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
  }

  @Test
  @Transactional
  void getAllContractsByCommentIsNullOrNotNull() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where comment is not null
    defaultContractShouldBeFound("comment.specified=true");

    // Get all the contractList where comment is null
    defaultContractShouldNotBeFound("comment.specified=false");
  }

  @Test
  @Transactional
  void getAllContractsByCommentContainsSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where comment contains DEFAULT_COMMENT
    defaultContractShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

    // Get all the contractList where comment contains UPDATED_COMMENT
    defaultContractShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
  }

  @Test
  @Transactional
  void getAllContractsByCommentNotContainsSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where comment does not contain DEFAULT_COMMENT
    defaultContractShouldNotBeFound(
        "comment.doesNotContain=" + DEFAULT_COMMENT);

    // Get all the contractList where comment does not contain UPDATED_COMMENT
    defaultContractShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
  }

  @Test
  @Transactional
  void getAllContractsByPayerIsEqualToSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where payer equals to DEFAULT_PAYER
    defaultContractShouldBeFound("payer.equals=" + DEFAULT_PAYER);

    // Get all the contractList where payer equals to UPDATED_PAYER
    defaultContractShouldNotBeFound("payer.equals=" + UPDATED_PAYER);
  }

  @Test
  @Transactional
  void getAllContractsByPayerIsInShouldWork() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where payer in DEFAULT_PAYER or UPDATED_PAYER
    defaultContractShouldBeFound(
        "payer.in=" + DEFAULT_PAYER + "," + UPDATED_PAYER);

    // Get all the contractList where payer equals to UPDATED_PAYER
    defaultContractShouldNotBeFound("payer.in=" + UPDATED_PAYER);
  }

  @Test
  @Transactional
  void getAllContractsByPayerIsNullOrNotNull() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where payer is not null
    defaultContractShouldBeFound("payer.specified=true");

    // Get all the contractList where payer is null
    defaultContractShouldNotBeFound("payer.specified=false");
  }

  @Test
  @Transactional
  void getAllContractsByPayerContainsSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where payer contains DEFAULT_PAYER
    defaultContractShouldBeFound("payer.contains=" + DEFAULT_PAYER);

    // Get all the contractList where payer contains UPDATED_PAYER
    defaultContractShouldNotBeFound("payer.contains=" + UPDATED_PAYER);
  }

  @Test
  @Transactional
  void getAllContractsByPayerNotContainsSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where payer does not contain DEFAULT_PAYER
    defaultContractShouldNotBeFound("payer.doesNotContain=" + DEFAULT_PAYER);

    // Get all the contractList where payer does not contain UPDATED_PAYER
    defaultContractShouldBeFound("payer.doesNotContain=" + UPDATED_PAYER);
  }

  @Test
  @Transactional
  void getAllContractsByPaymentTypeIsEqualToSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where paymentType equals to DEFAULT_PAYMENT_TYPE
    defaultContractShouldBeFound("paymentType.equals=" + DEFAULT_PAYMENT_TYPE);

    // Get all the contractList where paymentType equals to UPDATED_PAYMENT_TYPE
    defaultContractShouldNotBeFound(
        "paymentType.equals=" + UPDATED_PAYMENT_TYPE);
  }

  @Test
  @Transactional
  void getAllContractsByPaymentTypeIsInShouldWork() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where paymentType in DEFAULT_PAYMENT_TYPE or
    // UPDATED_PAYMENT_TYPE
    defaultContractShouldBeFound(
        "paymentType.in=" + DEFAULT_PAYMENT_TYPE + "," + UPDATED_PAYMENT_TYPE);

    // Get all the contractList where paymentType equals to UPDATED_PAYMENT_TYPE
    defaultContractShouldNotBeFound("paymentType.in=" + UPDATED_PAYMENT_TYPE);
  }

  @Test
  @Transactional
  void getAllContractsByPaymentTypeIsNullOrNotNull() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where paymentType is not null
    defaultContractShouldBeFound("paymentType.specified=true");

    // Get all the contractList where paymentType is null
    defaultContractShouldNotBeFound("paymentType.specified=false");
  }

  @Test
  @Transactional
  void getAllContractsBySalaryIsEqualToSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where salary equals to DEFAULT_SALARY
    defaultContractShouldBeFound("salary.equals=" + DEFAULT_SALARY);

    // Get all the contractList where salary equals to UPDATED_SALARY
    defaultContractShouldNotBeFound("salary.equals=" + UPDATED_SALARY);
  }

  @Test
  @Transactional
  void getAllContractsBySalaryIsInShouldWork() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where salary in DEFAULT_SALARY or UPDATED_SALARY
    defaultContractShouldBeFound(
        "salary.in=" + DEFAULT_SALARY + "," + UPDATED_SALARY);

    // Get all the contractList where salary equals to UPDATED_SALARY
    defaultContractShouldNotBeFound("salary.in=" + UPDATED_SALARY);
  }

  @Test
  @Transactional
  void getAllContractsBySalaryIsNullOrNotNull() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where salary is not null
    defaultContractShouldBeFound("salary.specified=true");

    // Get all the contractList where salary is null
    defaultContractShouldNotBeFound("salary.specified=false");
  }

  @Test
  @Transactional
  void getAllContractsBySalaryIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where salary is greater than or equal to
    // DEFAULT_SALARY
    defaultContractShouldBeFound("salary.greaterThanOrEqual=" + DEFAULT_SALARY);

    // Get all the contractList where salary is greater than or equal to
    // UPDATED_SALARY
    defaultContractShouldNotBeFound(
        "salary.greaterThanOrEqual=" + UPDATED_SALARY);
  }

  @Test
  @Transactional
  void getAllContractsBySalaryIsLessThanOrEqualToSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where salary is less than or equal to DEFAULT_SALARY
    defaultContractShouldBeFound("salary.lessThanOrEqual=" + DEFAULT_SALARY);

    // Get all the contractList where salary is less than or equal to SMALLER_SALARY
    defaultContractShouldNotBeFound("salary.lessThanOrEqual=" + SMALLER_SALARY);
  }

  @Test
  @Transactional
  void getAllContractsBySalaryIsLessThanSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where salary is less than DEFAULT_SALARY
    defaultContractShouldNotBeFound("salary.lessThan=" + DEFAULT_SALARY);

    // Get all the contractList where salary is less than UPDATED_SALARY
    defaultContractShouldBeFound("salary.lessThan=" + UPDATED_SALARY);
  }

  @Test
  @Transactional
  void getAllContractsBySalaryIsGreaterThanSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where salary is greater than DEFAULT_SALARY
    defaultContractShouldNotBeFound("salary.greaterThan=" + DEFAULT_SALARY);

    // Get all the contractList where salary is greater than SMALLER_SALARY
    defaultContractShouldBeFound("salary.greaterThan=" + SMALLER_SALARY);
  }

  @Test
  @Transactional
  void getAllContractsByVacationDaysIsEqualToSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where vacationDays equals to DEFAULT_VACATION_DAYS
    defaultContractShouldBeFound(
        "vacationDays.equals=" + DEFAULT_VACATION_DAYS);

    // Get all the contractList where vacationDays equals to UPDATED_VACATION_DAYS
    defaultContractShouldNotBeFound(
        "vacationDays.equals=" + UPDATED_VACATION_DAYS);
  }

  @Test
  @Transactional
  void getAllContractsByVacationDaysIsInShouldWork() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where vacationDays in DEFAULT_VACATION_DAYS or
    // UPDATED_VACATION_DAYS
    defaultContractShouldBeFound(
        "vacationDays.in=" + DEFAULT_VACATION_DAYS + "," + UPDATED_VACATION_DAYS);

    // Get all the contractList where vacationDays equals to UPDATED_VACATION_DAYS
    defaultContractShouldNotBeFound("vacationDays.in=" + UPDATED_VACATION_DAYS);
  }

  @Test
  @Transactional
  void getAllContractsByVacationDaysIsNullOrNotNull() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where vacationDays is not null
    defaultContractShouldBeFound("vacationDays.specified=true");

    // Get all the contractList where vacationDays is null
    defaultContractShouldNotBeFound("vacationDays.specified=false");
  }

  @Test
  @Transactional
  void getAllContractsByVacationDaysIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where vacationDays is greater than or equal to
    // DEFAULT_VACATION_DAYS
    defaultContractShouldBeFound(
        "vacationDays.greaterThanOrEqual=" + DEFAULT_VACATION_DAYS);

    // Get all the contractList where vacationDays is greater than or equal to
    // UPDATED_VACATION_DAYS
    defaultContractShouldNotBeFound(
        "vacationDays.greaterThanOrEqual=" + UPDATED_VACATION_DAYS);
  }

  @Test
  @Transactional
  void getAllContractsByVacationDaysIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where vacationDays is less than or equal to
    // DEFAULT_VACATION_DAYS
    defaultContractShouldBeFound(
        "vacationDays.lessThanOrEqual=" + DEFAULT_VACATION_DAYS);

    // Get all the contractList where vacationDays is less than or equal to
    // SMALLER_VACATION_DAYS
    defaultContractShouldNotBeFound(
        "vacationDays.lessThanOrEqual=" + SMALLER_VACATION_DAYS);
  }

  @Test
  @Transactional
  void getAllContractsByVacationDaysIsLessThanSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where vacationDays is less than
    // DEFAULT_VACATION_DAYS
    defaultContractShouldNotBeFound(
        "vacationDays.lessThan=" + DEFAULT_VACATION_DAYS);

    // Get all the contractList where vacationDays is less than
    // UPDATED_VACATION_DAYS
    defaultContractShouldBeFound(
        "vacationDays.lessThan=" + UPDATED_VACATION_DAYS);
  }

  @Test
  @Transactional
  void getAllContractsByVacationDaysIsGreaterThanSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where vacationDays is greater than
    // DEFAULT_VACATION_DAYS
    defaultContractShouldNotBeFound(
        "vacationDays.greaterThan=" + DEFAULT_VACATION_DAYS);

    // Get all the contractList where vacationDays is greater than
    // SMALLER_VACATION_DAYS
    defaultContractShouldBeFound(
        "vacationDays.greaterThan=" + SMALLER_VACATION_DAYS);
  }

  @Test
  @Transactional
  void getAllContractsByOccupationIsEqualToSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where occupation equals to DEFAULT_OCCUPATION
    defaultContractShouldBeFound("occupation.equals=" + DEFAULT_OCCUPATION);

    // Get all the contractList where occupation equals to UPDATED_OCCUPATION
    defaultContractShouldNotBeFound("occupation.equals=" + UPDATED_OCCUPATION);
  }

  @Test
  @Transactional
  void getAllContractsByOccupationIsInShouldWork() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where occupation in DEFAULT_OCCUPATION or
    // UPDATED_OCCUPATION
    defaultContractShouldBeFound(
        "occupation.in=" + DEFAULT_OCCUPATION + "," + UPDATED_OCCUPATION);

    // Get all the contractList where occupation equals to UPDATED_OCCUPATION
    defaultContractShouldNotBeFound("occupation.in=" + UPDATED_OCCUPATION);
  }

  @Test
  @Transactional
  void getAllContractsByOccupationIsNullOrNotNull() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where occupation is not null
    defaultContractShouldBeFound("occupation.specified=true");

    // Get all the contractList where occupation is null
    defaultContractShouldNotBeFound("occupation.specified=false");
  }

  @Test
  @Transactional
  void getAllContractsByOccupationContainsSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where occupation contains DEFAULT_OCCUPATION
    defaultContractShouldBeFound("occupation.contains=" + DEFAULT_OCCUPATION);

    // Get all the contractList where occupation contains UPDATED_OCCUPATION
    defaultContractShouldNotBeFound(
        "occupation.contains=" + UPDATED_OCCUPATION);
  }

  @Test
  @Transactional
  void getAllContractsByOccupationNotContainsSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where occupation does not contain DEFAULT_OCCUPATION
    defaultContractShouldNotBeFound(
        "occupation.doesNotContain=" + DEFAULT_OCCUPATION);

    // Get all the contractList where occupation does not contain UPDATED_OCCUPATION
    defaultContractShouldBeFound(
        "occupation.doesNotContain=" + UPDATED_OCCUPATION);
  }

  @Test
  @Transactional
  void getAllContractsByActivitiesIsEqualToSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where activities equals to DEFAULT_ACTIVITIES
    defaultContractShouldBeFound("activities.equals=" + DEFAULT_ACTIVITIES);

    // Get all the contractList where activities equals to UPDATED_ACTIVITIES
    defaultContractShouldNotBeFound("activities.equals=" + UPDATED_ACTIVITIES);
  }

  @Test
  @Transactional
  void getAllContractsByActivitiesIsInShouldWork() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where activities in DEFAULT_ACTIVITIES or
    // UPDATED_ACTIVITIES
    defaultContractShouldBeFound(
        "activities.in=" + DEFAULT_ACTIVITIES + "," + UPDATED_ACTIVITIES);

    // Get all the contractList where activities equals to UPDATED_ACTIVITIES
    defaultContractShouldNotBeFound("activities.in=" + UPDATED_ACTIVITIES);
  }

  @Test
  @Transactional
  void getAllContractsByActivitiesIsNullOrNotNull() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where activities is not null
    defaultContractShouldBeFound("activities.specified=true");

    // Get all the contractList where activities is null
    defaultContractShouldNotBeFound("activities.specified=false");
  }

  @Test
  @Transactional
  void getAllContractsByActivitiesContainsSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where activities contains DEFAULT_ACTIVITIES
    defaultContractShouldBeFound("activities.contains=" + DEFAULT_ACTIVITIES);

    // Get all the contractList where activities contains UPDATED_ACTIVITIES
    defaultContractShouldNotBeFound(
        "activities.contains=" + UPDATED_ACTIVITIES);
  }

  @Test
  @Transactional
  void getAllContractsByActivitiesNotContainsSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where activities does not contain DEFAULT_ACTIVITIES
    defaultContractShouldNotBeFound(
        "activities.doesNotContain=" + DEFAULT_ACTIVITIES);

    // Get all the contractList where activities does not contain UPDATED_ACTIVITIES
    defaultContractShouldBeFound(
        "activities.doesNotContain=" + UPDATED_ACTIVITIES);
  }

  @Test
  @Transactional
  void getAllContractsByLoanValueIsEqualToSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where loanValue equals to DEFAULT_LOAN_VALUE
    defaultContractShouldBeFound("loanValue.equals=" + DEFAULT_LOAN_VALUE);

    // Get all the contractList where loanValue equals to UPDATED_LOAN_VALUE
    defaultContractShouldNotBeFound("loanValue.equals=" + UPDATED_LOAN_VALUE);
  }

  @Test
  @Transactional
  void getAllContractsByLoanValueIsInShouldWork() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where loanValue in DEFAULT_LOAN_VALUE or
    // UPDATED_LOAN_VALUE
    defaultContractShouldBeFound(
        "loanValue.in=" + DEFAULT_LOAN_VALUE + "," + UPDATED_LOAN_VALUE);

    // Get all the contractList where loanValue equals to UPDATED_LOAN_VALUE
    defaultContractShouldNotBeFound("loanValue.in=" + UPDATED_LOAN_VALUE);
  }

  @Test
  @Transactional
  void getAllContractsByLoanValueIsNullOrNotNull() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where loanValue is not null
    defaultContractShouldBeFound("loanValue.specified=true");

    // Get all the contractList where loanValue is null
    defaultContractShouldNotBeFound("loanValue.specified=false");
  }

  @Test
  @Transactional
  void getAllContractsByLoanValueIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where loanValue is greater than or equal to
    // DEFAULT_LOAN_VALUE
    defaultContractShouldBeFound(
        "loanValue.greaterThanOrEqual=" + DEFAULT_LOAN_VALUE);

    // Get all the contractList where loanValue is greater than or equal to
    // UPDATED_LOAN_VALUE
    defaultContractShouldNotBeFound(
        "loanValue.greaterThanOrEqual=" + UPDATED_LOAN_VALUE);
  }

  @Test
  @Transactional
  void getAllContractsByLoanValueIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where loanValue is less than or equal to
    // DEFAULT_LOAN_VALUE
    defaultContractShouldBeFound(
        "loanValue.lessThanOrEqual=" + DEFAULT_LOAN_VALUE);

    // Get all the contractList where loanValue is less than or equal to
    // SMALLER_LOAN_VALUE
    defaultContractShouldNotBeFound(
        "loanValue.lessThanOrEqual=" + SMALLER_LOAN_VALUE);
  }

  @Test
  @Transactional
  void getAllContractsByLoanValueIsLessThanSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where loanValue is less than DEFAULT_LOAN_VALUE
    defaultContractShouldNotBeFound("loanValue.lessThan=" + DEFAULT_LOAN_VALUE);

    // Get all the contractList where loanValue is less than UPDATED_LOAN_VALUE
    defaultContractShouldBeFound("loanValue.lessThan=" + UPDATED_LOAN_VALUE);
  }

  @Test
  @Transactional
  void getAllContractsByLoanValueIsGreaterThanSomething() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where loanValue is greater than DEFAULT_LOAN_VALUE
    defaultContractShouldNotBeFound(
        "loanValue.greaterThan=" + DEFAULT_LOAN_VALUE);

    // Get all the contractList where loanValue is greater than SMALLER_LOAN_VALUE
    defaultContractShouldBeFound("loanValue.greaterThan=" + SMALLER_LOAN_VALUE);
  }

  @Test
  @Transactional
  void getAllContractsByInterestRatePercentIsEqualToSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where interestRatePercent equals to
    // DEFAULT_INTEREST_RATE_PERCENT
    defaultContractShouldBeFound(
        "interestRatePercent.equals=" + DEFAULT_INTEREST_RATE_PERCENT);

    // Get all the contractList where interestRatePercent equals to
    // UPDATED_INTEREST_RATE_PERCENT
    defaultContractShouldNotBeFound(
        "interestRatePercent.equals=" + UPDATED_INTEREST_RATE_PERCENT);
  }

  @Test
  @Transactional
  void getAllContractsByInterestRatePercentIsInShouldWork() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where interestRatePercent in
    // DEFAULT_INTEREST_RATE_PERCENT or UPDATED_INTEREST_RATE_PERCENT
    defaultContractShouldBeFound(
        "interestRatePercent.in=" +
            DEFAULT_INTEREST_RATE_PERCENT +
            "," +
            UPDATED_INTEREST_RATE_PERCENT);

    // Get all the contractList where interestRatePercent equals to
    // UPDATED_INTEREST_RATE_PERCENT
    defaultContractShouldNotBeFound(
        "interestRatePercent.in=" + UPDATED_INTEREST_RATE_PERCENT);
  }

  @Test
  @Transactional
  void getAllContractsByInterestRatePercentIsNullOrNotNull() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where interestRatePercent is not null
    defaultContractShouldBeFound("interestRatePercent.specified=true");

    // Get all the contractList where interestRatePercent is null
    defaultContractShouldNotBeFound("interestRatePercent.specified=false");
  }

  @Test
  @Transactional
  void getAllContractsByInterestRatePercentIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where interestRatePercent is greater than or equal
    // to DEFAULT_INTEREST_RATE_PERCENT
    defaultContractShouldBeFound(
        "interestRatePercent.greaterThanOrEqual=" + DEFAULT_INTEREST_RATE_PERCENT);

    // Get all the contractList where interestRatePercent is greater than or equal
    // to UPDATED_INTEREST_RATE_PERCENT
    defaultContractShouldNotBeFound(
        "interestRatePercent.greaterThanOrEqual=" + UPDATED_INTEREST_RATE_PERCENT);
  }

  @Test
  @Transactional
  void getAllContractsByInterestRatePercentIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where interestRatePercent is less than or equal to
    // DEFAULT_INTEREST_RATE_PERCENT
    defaultContractShouldBeFound(
        "interestRatePercent.lessThanOrEqual=" + DEFAULT_INTEREST_RATE_PERCENT);

    // Get all the contractList where interestRatePercent is less than or equal to
    // SMALLER_INTEREST_RATE_PERCENT
    defaultContractShouldNotBeFound(
        "interestRatePercent.lessThanOrEqual=" + SMALLER_INTEREST_RATE_PERCENT);
  }

  @Test
  @Transactional
  void getAllContractsByInterestRatePercentIsLessThanSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where interestRatePercent is less than
    // DEFAULT_INTEREST_RATE_PERCENT
    defaultContractShouldNotBeFound(
        "interestRatePercent.lessThan=" + DEFAULT_INTEREST_RATE_PERCENT);

    // Get all the contractList where interestRatePercent is less than
    // UPDATED_INTEREST_RATE_PERCENT
    defaultContractShouldBeFound(
        "interestRatePercent.lessThan=" + UPDATED_INTEREST_RATE_PERCENT);
  }

  @Test
  @Transactional
  void getAllContractsByInterestRatePercentIsGreaterThanSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where interestRatePercent is greater than
    // DEFAULT_INTEREST_RATE_PERCENT
    defaultContractShouldNotBeFound(
        "interestRatePercent.greaterThan=" + DEFAULT_INTEREST_RATE_PERCENT);

    // Get all the contractList where interestRatePercent is greater than
    // SMALLER_INTEREST_RATE_PERCENT
    defaultContractShouldBeFound(
        "interestRatePercent.greaterThan=" + SMALLER_INTEREST_RATE_PERCENT);
  }

  @Test
  @Transactional
  void getAllContractsByInterestRateCurrencyIsEqualToSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where interestRateCurrency equals to
    // DEFAULT_INTEREST_RATE_CURRENCY
    defaultContractShouldBeFound(
        "interestRateCurrency.equals=" + DEFAULT_INTEREST_RATE_CURRENCY);

    // Get all the contractList where interestRateCurrency equals to
    // UPDATED_INTEREST_RATE_CURRENCY
    defaultContractShouldNotBeFound(
        "interestRateCurrency.equals=" + UPDATED_INTEREST_RATE_CURRENCY);
  }

  @Test
  @Transactional
  void getAllContractsByInterestRateCurrencyIsInShouldWork() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where interestRateCurrency in
    // DEFAULT_INTEREST_RATE_CURRENCY or UPDATED_INTEREST_RATE_CURRENCY
    defaultContractShouldBeFound(
        "interestRateCurrency.in=" +
            DEFAULT_INTEREST_RATE_CURRENCY +
            "," +
            UPDATED_INTEREST_RATE_CURRENCY);

    // Get all the contractList where interestRateCurrency equals to
    // UPDATED_INTEREST_RATE_CURRENCY
    defaultContractShouldNotBeFound(
        "interestRateCurrency.in=" + UPDATED_INTEREST_RATE_CURRENCY);
  }

  @Test
  @Transactional
  void getAllContractsByInterestRateCurrencyIsNullOrNotNull() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where interestRateCurrency is not null
    defaultContractShouldBeFound("interestRateCurrency.specified=true");

    // Get all the contractList where interestRateCurrency is null
    defaultContractShouldNotBeFound("interestRateCurrency.specified=false");
  }

  @Test
  @Transactional
  void getAllContractsByInterestRateCurrencyIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where interestRateCurrency is greater than or equal
    // to DEFAULT_INTEREST_RATE_CURRENCY
    defaultContractShouldBeFound(
        "interestRateCurrency.greaterThanOrEqual=" +
            DEFAULT_INTEREST_RATE_CURRENCY);

    // Get all the contractList where interestRateCurrency is greater than or equal
    // to UPDATED_INTEREST_RATE_CURRENCY
    defaultContractShouldNotBeFound(
        "interestRateCurrency.greaterThanOrEqual=" +
            UPDATED_INTEREST_RATE_CURRENCY);
  }

  @Test
  @Transactional
  void getAllContractsByInterestRateCurrencyIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where interestRateCurrency is less than or equal to
    // DEFAULT_INTEREST_RATE_CURRENCY
    defaultContractShouldBeFound(
        "interestRateCurrency.lessThanOrEqual=" + DEFAULT_INTEREST_RATE_CURRENCY);

    // Get all the contractList where interestRateCurrency is less than or equal to
    // SMALLER_INTEREST_RATE_CURRENCY
    defaultContractShouldNotBeFound(
        "interestRateCurrency.lessThanOrEqual=" + SMALLER_INTEREST_RATE_CURRENCY);
  }

  @Test
  @Transactional
  void getAllContractsByInterestRateCurrencyIsLessThanSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where interestRateCurrency is less than
    // DEFAULT_INTEREST_RATE_CURRENCY
    defaultContractShouldNotBeFound(
        "interestRateCurrency.lessThan=" + DEFAULT_INTEREST_RATE_CURRENCY);

    // Get all the contractList where interestRateCurrency is less than
    // UPDATED_INTEREST_RATE_CURRENCY
    defaultContractShouldBeFound(
        "interestRateCurrency.lessThan=" + UPDATED_INTEREST_RATE_CURRENCY);
  }

  @Test
  @Transactional
  void getAllContractsByInterestRateCurrencyIsGreaterThanSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where interestRateCurrency is greater than
    // DEFAULT_INTEREST_RATE_CURRENCY
    defaultContractShouldNotBeFound(
        "interestRateCurrency.greaterThan=" + DEFAULT_INTEREST_RATE_CURRENCY);

    // Get all the contractList where interestRateCurrency is greater than
    // SMALLER_INTEREST_RATE_CURRENCY
    defaultContractShouldBeFound(
        "interestRateCurrency.greaterThan=" + SMALLER_INTEREST_RATE_CURRENCY);
  }

  @Test
  @Transactional
  void getAllContractsByAmortizationRatePercentIsEqualToSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where amortizationRatePercent equals to
    // DEFAULT_AMORTIZATION_RATE_PERCENT
    defaultContractShouldBeFound(
        "amortizationRatePercent.equals=" + DEFAULT_AMORTIZATION_RATE_PERCENT);

    // Get all the contractList where amortizationRatePercent equals to
    // UPDATED_AMORTIZATION_RATE_PERCENT
    defaultContractShouldNotBeFound(
        "amortizationRatePercent.equals=" + UPDATED_AMORTIZATION_RATE_PERCENT);
  }

  @Test
  @Transactional
  void getAllContractsByAmortizationRatePercentIsInShouldWork()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where amortizationRatePercent in
    // DEFAULT_AMORTIZATION_RATE_PERCENT or UPDATED_AMORTIZATION_RATE_PERCENT
    defaultContractShouldBeFound(
        "amortizationRatePercent.in=" +
            DEFAULT_AMORTIZATION_RATE_PERCENT +
            "," +
            UPDATED_AMORTIZATION_RATE_PERCENT);

    // Get all the contractList where amortizationRatePercent equals to
    // UPDATED_AMORTIZATION_RATE_PERCENT
    defaultContractShouldNotBeFound(
        "amortizationRatePercent.in=" + UPDATED_AMORTIZATION_RATE_PERCENT);
  }

  @Test
  @Transactional
  void getAllContractsByAmortizationRatePercentIsNullOrNotNull()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where amortizationRatePercent is not null
    defaultContractShouldBeFound("amortizationRatePercent.specified=true");

    // Get all the contractList where amortizationRatePercent is null
    defaultContractShouldNotBeFound("amortizationRatePercent.specified=false");
  }

  @Test
  @Transactional
  void getAllContractsByAmortizationRatePercentIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where amortizationRatePercent is greater than or
    // equal to DEFAULT_AMORTIZATION_RATE_PERCENT
    defaultContractShouldBeFound(
        "amortizationRatePercent.greaterThanOrEqual=" +
            DEFAULT_AMORTIZATION_RATE_PERCENT);

    // Get all the contractList where amortizationRatePercent is greater than or
    // equal to UPDATED_AMORTIZATION_RATE_PERCENT
    defaultContractShouldNotBeFound(
        "amortizationRatePercent.greaterThanOrEqual=" +
            UPDATED_AMORTIZATION_RATE_PERCENT);
  }

  @Test
  @Transactional
  void getAllContractsByAmortizationRatePercentIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where amortizationRatePercent is less than or equal
    // to DEFAULT_AMORTIZATION_RATE_PERCENT
    defaultContractShouldBeFound(
        "amortizationRatePercent.lessThanOrEqual=" +
            DEFAULT_AMORTIZATION_RATE_PERCENT);

    // Get all the contractList where amortizationRatePercent is less than or equal
    // to SMALLER_AMORTIZATION_RATE_PERCENT
    defaultContractShouldNotBeFound(
        "amortizationRatePercent.lessThanOrEqual=" +
            SMALLER_AMORTIZATION_RATE_PERCENT);
  }

  @Test
  @Transactional
  void getAllContractsByAmortizationRatePercentIsLessThanSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where amortizationRatePercent is less than
    // DEFAULT_AMORTIZATION_RATE_PERCENT
    defaultContractShouldNotBeFound(
        "amortizationRatePercent.lessThan=" + DEFAULT_AMORTIZATION_RATE_PERCENT);

    // Get all the contractList where amortizationRatePercent is less than
    // UPDATED_AMORTIZATION_RATE_PERCENT
    defaultContractShouldBeFound(
        "amortizationRatePercent.lessThan=" + UPDATED_AMORTIZATION_RATE_PERCENT);
  }

  @Test
  @Transactional
  void getAllContractsByAmortizationRatePercentIsGreaterThanSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where amortizationRatePercent is greater than
    // DEFAULT_AMORTIZATION_RATE_PERCENT
    defaultContractShouldNotBeFound(
        "amortizationRatePercent.greaterThan=" + DEFAULT_AMORTIZATION_RATE_PERCENT);

    // Get all the contractList where amortizationRatePercent is greater than
    // SMALLER_AMORTIZATION_RATE_PERCENT
    defaultContractShouldBeFound(
        "amortizationRatePercent.greaterThan=" + SMALLER_AMORTIZATION_RATE_PERCENT);
  }

  @Test
  @Transactional
  void getAllContractsByAmortizationRateCurrencyIsEqualToSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where amortizationRateCurrency equals to
    // DEFAULT_AMORTIZATION_RATE_CURRENCY
    defaultContractShouldBeFound(
        "amortizationRateCurrency.equals=" + DEFAULT_AMORTIZATION_RATE_CURRENCY);

    // Get all the contractList where amortizationRateCurrency equals to
    // UPDATED_AMORTIZATION_RATE_CURRENCY
    defaultContractShouldNotBeFound(
        "amortizationRateCurrency.equals=" + UPDATED_AMORTIZATION_RATE_CURRENCY);
  }

  @Test
  @Transactional
  void getAllContractsByAmortizationRateCurrencyIsInShouldWork()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where amortizationRateCurrency in
    // DEFAULT_AMORTIZATION_RATE_CURRENCY or UPDATED_AMORTIZATION_RATE_CURRENCY
    defaultContractShouldBeFound(
        "amortizationRateCurrency.in=" +
            DEFAULT_AMORTIZATION_RATE_CURRENCY +
            "," +
            UPDATED_AMORTIZATION_RATE_CURRENCY);

    // Get all the contractList where amortizationRateCurrency equals to
    // UPDATED_AMORTIZATION_RATE_CURRENCY
    defaultContractShouldNotBeFound(
        "amortizationRateCurrency.in=" + UPDATED_AMORTIZATION_RATE_CURRENCY);
  }

  @Test
  @Transactional
  void getAllContractsByAmortizationRateCurrencyIsNullOrNotNull()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where amortizationRateCurrency is not null
    defaultContractShouldBeFound("amortizationRateCurrency.specified=true");

    // Get all the contractList where amortizationRateCurrency is null
    defaultContractShouldNotBeFound("amortizationRateCurrency.specified=false");
  }

  @Test
  @Transactional
  void getAllContractsByAmortizationRateCurrencyIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where amortizationRateCurrency is greater than or
    // equal to DEFAULT_AMORTIZATION_RATE_CURRENCY
    defaultContractShouldBeFound(
        "amortizationRateCurrency.greaterThanOrEqual=" +
            DEFAULT_AMORTIZATION_RATE_CURRENCY);

    // Get all the contractList where amortizationRateCurrency is greater than or
    // equal to UPDATED_AMORTIZATION_RATE_CURRENCY
    defaultContractShouldNotBeFound(
        "amortizationRateCurrency.greaterThanOrEqual=" +
            UPDATED_AMORTIZATION_RATE_CURRENCY);
  }

  @Test
  @Transactional
  void getAllContractsByAmortizationRateCurrencyIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where amortizationRateCurrency is less than or equal
    // to DEFAULT_AMORTIZATION_RATE_CURRENCY
    defaultContractShouldBeFound(
        "amortizationRateCurrency.lessThanOrEqual=" +
            DEFAULT_AMORTIZATION_RATE_CURRENCY);

    // Get all the contractList where amortizationRateCurrency is less than or equal
    // to SMALLER_AMORTIZATION_RATE_CURRENCY
    defaultContractShouldNotBeFound(
        "amortizationRateCurrency.lessThanOrEqual=" +
            SMALLER_AMORTIZATION_RATE_CURRENCY);
  }

  @Test
  @Transactional
  void getAllContractsByAmortizationRateCurrencyIsLessThanSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where amortizationRateCurrency is less than
    // DEFAULT_AMORTIZATION_RATE_CURRENCY
    defaultContractShouldNotBeFound(
        "amortizationRateCurrency.lessThan=" + DEFAULT_AMORTIZATION_RATE_CURRENCY);

    // Get all the contractList where amortizationRateCurrency is less than
    // UPDATED_AMORTIZATION_RATE_CURRENCY
    defaultContractShouldBeFound(
        "amortizationRateCurrency.lessThan=" + UPDATED_AMORTIZATION_RATE_CURRENCY);
  }

  @Test
  @Transactional
  void getAllContractsByAmortizationRateCurrencyIsGreaterThanSomething()
      throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    // Get all the contractList where amortizationRateCurrency is greater than
    // DEFAULT_AMORTIZATION_RATE_CURRENCY
    defaultContractShouldNotBeFound(
        "amortizationRateCurrency.greaterThan=" +
            DEFAULT_AMORTIZATION_RATE_CURRENCY);

    // Get all the contractList where amortizationRateCurrency is greater than
    // SMALLER_AMORTIZATION_RATE_CURRENCY
    defaultContractShouldBeFound(
        "amortizationRateCurrency.greaterThan=" +
            SMALLER_AMORTIZATION_RATE_CURRENCY);
  }

  @Test
  @Transactional
  void getAllContractsByApplicationUserIsEqualToSomething() throws Exception {
    ApplicationUser applicationUser;
    if (TestUtil.findAll(em, ApplicationUser.class).isEmpty()) {
      contractRepository.saveAndFlush(contract);
      applicationUser = ApplicationUserResourceIT.createEntity(em);
    } else {
      applicationUser = TestUtil.findAll(em, ApplicationUser.class).get(0);
    }
    em.persist(applicationUser);
    em.flush();
    contract.setApplicationUser(applicationUser);
    contractRepository.saveAndFlush(contract);
    Long applicationUserId = applicationUser.getId();

    // Get all the contractList where applicationUser equals to applicationUserId
    defaultContractShouldBeFound(
        "applicationUserId.equals=" + applicationUserId);

    // Get all the contractList where applicationUser equals to (applicationUserId +
    // 1)
    defaultContractShouldNotBeFound(
        "applicationUserId.equals=" + (applicationUserId + 1));
  }

  @Test
  @Transactional
  void getAllContractsByGroupTypeIsEqualToSomething() throws Exception {
    GroupType groupType;
    if (TestUtil.findAll(em, GroupType.class).isEmpty()) {
      contractRepository.saveAndFlush(contract);
      groupType = GroupTypeResourceIT.createEntity(em);
    } else {
      groupType = TestUtil.findAll(em, GroupType.class).get(0);
    }
    em.persist(groupType);
    em.flush();
    contract.setGroupType(groupType);
    contractRepository.saveAndFlush(contract);
    Long groupTypeId = groupType.getId();

    // Get all the contractList where groupType equals to groupTypeId
    defaultContractShouldBeFound("groupTypeId.equals=" + groupTypeId);

    // Get all the contractList where groupType equals to (groupTypeId + 1)
    defaultContractShouldNotBeFound("groupTypeId.equals=" + (groupTypeId + 1));
  }

  @Test
  @Transactional
  void getAllContractsByContractRealestatesIsEqualToSomething()
      throws Exception {
    ContractRealestate contractRealestates;
    if (TestUtil.findAll(em, ContractRealestate.class).isEmpty()) {
      contractRepository.saveAndFlush(contract);
      contractRealestates = ContractRealestateResourceIT.createEntity(em);
    } else {
      contractRealestates = TestUtil.findAll(em, ContractRealestate.class).get(0);
    }
    em.persist(contractRealestates);
    em.flush();
    contract.addContractRealestates(contractRealestates);
    contractRepository.saveAndFlush(contract);
    Long contractRealestatesId = contractRealestates.getId();

    // Get all the contractList where contractRealestates equals to
    // contractRealestatesId
    defaultContractShouldBeFound(
        "contractRealestatesId.equals=" + contractRealestatesId);

    // Get all the contractList where contractRealestates equals to
    // (contractRealestatesId + 1)
    defaultContractShouldNotBeFound(
        "contractRealestatesId.equals=" + (contractRealestatesId + 1));
  }

  /**
   * Executes the search, and checks that the default entity is returned.
   */
  private void defaultContractShouldBeFound(String filter) throws Exception {
    restContractMockMvc
        .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(
            jsonPath("$.[*].id").value(hasItem(contract.getId().intValue())))
        .andExpect(
            jsonPath("$.[*].contractor").value(hasItem(DEFAULT_CONTRACTOR)))
        .andExpect(
            jsonPath("$.[*].contractOwner").value(hasItem(DEFAULT_CONTRACT_OWNER)))
        .andExpect(
            jsonPath("$.[*].contractUser").value(hasItem(DEFAULT_CONTRACT_USER)))
        .andExpect(
            jsonPath("$.[*].contractNumber").value(hasItem(DEFAULT_CONTRACT_NUMBER)))
        .andExpect(
            jsonPath("$.[*].startDate")
                .value(hasItem(DEFAULT_START_DATE.toString())))
        .andExpect(
            jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
        .andExpect(
            jsonPath("$.[*].applicationDate")
                .value(hasItem(DEFAULT_APPLICATION_DATE.toString())))
        .andExpect(
            jsonPath("$.[*].confirmationDate")
                .value(hasItem(DEFAULT_CONFIRMATION_DATE.toString())))
        .andExpect(
            jsonPath("$.[*].paymentPattern")
                .value(hasItem(DEFAULT_PAYMENT_PATTERN.toString())))
        .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
        .andExpect(jsonPath("$.[*].payer").value(hasItem(DEFAULT_PAYER)))
        .andExpect(
            jsonPath("$.[*].paymentType")
                .value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
        .andExpect(
            jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.doubleValue())))
        .andExpect(
            jsonPath("$.[*].vacationDays").value(hasItem(DEFAULT_VACATION_DAYS)))
        .andExpect(
            jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION)))
        .andExpect(
            jsonPath("$.[*].activities").value(hasItem(DEFAULT_ACTIVITIES)))
        .andExpect(
            jsonPath("$.[*].loanValue")
                .value(hasItem(DEFAULT_LOAN_VALUE.doubleValue())))
        .andExpect(
            jsonPath("$.[*].interestRatePercent")
                .value(hasItem(DEFAULT_INTEREST_RATE_PERCENT.doubleValue())))
        .andExpect(
            jsonPath("$.[*].interestRateCurrency")
                .value(hasItem(DEFAULT_INTEREST_RATE_CURRENCY.doubleValue())))
        .andExpect(
            jsonPath("$.[*].amortizationRatePercent")
                .value(hasItem(DEFAULT_AMORTIZATION_RATE_PERCENT.doubleValue())))
        .andExpect(
            jsonPath("$.[*].amortizationRateCurrency")
                .value(hasItem(DEFAULT_AMORTIZATION_RATE_CURRENCY.doubleValue())));

    // Check, that the count call also returns 1
    restContractMockMvc
        .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().string("1"));
  }

  /**
   * Executes the search, and checks that the default entity is not returned.
   */
  private void defaultContractShouldNotBeFound(String filter) throws Exception {
    restContractMockMvc
        .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$").isEmpty());

    // Check, that the count call also returns 0
    restContractMockMvc
        .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().string("0"));
  }

  @Test
  @Transactional
  void getNonExistingContract() throws Exception {
    // Get the contract
    restContractMockMvc
        .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
        .andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewContract() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    int databaseSizeBeforeUpdate = contractRepository.findAll().size();

    // Update the contract
    Contract updatedContract = contractRepository
        .findById(contract.getId())
        .orElseThrow();
    // Disconnect from session so that the updates on updatedContract are not
    // directly saved in db
    em.detach(updatedContract);
    updatedContract
        .contractor(UPDATED_CONTRACTOR)
        .contractOwner(UPDATED_CONTRACT_OWNER)
        .contractUser(UPDATED_CONTRACT_USER)
        .contractNumber(UPDATED_CONTRACT_NUMBER)
        .startDate(UPDATED_START_DATE)
        .endDate(UPDATED_END_DATE)
        .applicationDate(UPDATED_APPLICATION_DATE)
        .confirmationDate(UPDATED_CONFIRMATION_DATE)
        .paymentPattern(UPDATED_PAYMENT_PATTERN)
        .payer(UPDATED_PAYER)
        .paymentType(UPDATED_PAYMENT_TYPE)
        .occupation(UPDATED_OCCUPATION)
        .activities(UPDATED_ACTIVITIES)
        .loanValue(UPDATED_LOAN_VALUE)
        .interestRatePercent(UPDATED_INTEREST_RATE_PERCENT)
        .interestRateCurrency(UPDATED_INTEREST_RATE_CURRENCY)
        .amortizationRatePercent(UPDATED_AMORTIZATION_RATE_PERCENT)
        .amortizationRateCurrency(UPDATED_AMORTIZATION_RATE_CURRENCY);

    restContractMockMvc
        .perform(
            put(ENTITY_API_URL_ID, updatedContract.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(updatedContract)))
        .andExpect(status().isOk());

    // Validate the Contract in the database
    List<Contract> contractList = contractRepository.findAll();
    assertThat(contractList).hasSize(databaseSizeBeforeUpdate);
    Contract testContract = contractList.get(contractList.size() - 1);
    assertThat(testContract.getContractor()).isEqualTo(UPDATED_CONTRACTOR);
    assertThat(testContract.getContractOwner())
        .isEqualTo(UPDATED_CONTRACT_OWNER);
    assertThat(testContract.getContractUser()).isEqualTo(UPDATED_CONTRACT_USER);
    assertThat(testContract.getContractNumber())
        .isEqualTo(UPDATED_CONTRACT_NUMBER);
    assertThat(testContract.getStartDate()).isEqualTo(UPDATED_START_DATE);
    assertThat(testContract.getEndDate()).isEqualTo(UPDATED_END_DATE);
    assertThat(testContract.getApplicationDate())
        .isEqualTo(UPDATED_APPLICATION_DATE);
    assertThat(testContract.getConfirmationDate())
        .isEqualTo(UPDATED_CONFIRMATION_DATE);
    assertThat(testContract.getPaymentPattern())
        .isEqualTo(UPDATED_PAYMENT_PATTERN);
    assertThat(testContract.getComment()).isEqualTo(UPDATED_COMMENT);
    assertThat(testContract.getPayer()).isEqualTo(UPDATED_PAYER);
    assertThat(testContract.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
    assertThat(testContract.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
    assertThat(testContract.getActivities()).isEqualTo(UPDATED_ACTIVITIES);
    assertThat(testContract.getLoanValue()).isEqualTo(UPDATED_LOAN_VALUE);
    assertThat(testContract.getInterestRatePercent())
        .isEqualTo(UPDATED_INTEREST_RATE_PERCENT);
    assertThat(testContract.getInterestRateCurrency())
        .isEqualTo(UPDATED_INTEREST_RATE_CURRENCY);
    assertThat(testContract.getAmortizationRatePercent())
        .isEqualTo(UPDATED_AMORTIZATION_RATE_PERCENT);
    assertThat(testContract.getAmortizationRateCurrency())
        .isEqualTo(UPDATED_AMORTIZATION_RATE_CURRENCY);
  }

  @Test
  @Transactional
  void putNonExistingContract() throws Exception {
    int databaseSizeBeforeUpdate = contractRepository.findAll().size();
    contract.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restContractMockMvc
        .perform(
            put(ENTITY_API_URL_ID, contract.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(contract)))
        .andExpect(status().isBadRequest());

    // Validate the Contract in the database
    List<Contract> contractList = contractRepository.findAll();
    assertThat(contractList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchContract() throws Exception {
    int databaseSizeBeforeUpdate = contractRepository.findAll().size();
    contract.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restContractMockMvc
        .perform(
            put(ENTITY_API_URL_ID, count.incrementAndGet())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(contract)))
        .andExpect(status().isBadRequest());

    // Validate the Contract in the database
    List<Contract> contractList = contractRepository.findAll();
    assertThat(contractList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamContract() throws Exception {
    int databaseSizeBeforeUpdate = contractRepository.findAll().size();
    contract.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restContractMockMvc
        .perform(
            put(ENTITY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(contract)))
        .andExpect(status().isMethodNotAllowed());

    // Validate the Contract in the database
    List<Contract> contractList = contractRepository.findAll();
    assertThat(contractList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateContractWithPatch() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    int databaseSizeBeforeUpdate = contractRepository.findAll().size();

    // Update the contract using partial update
    Contract partialUpdatedContract = new Contract();
    partialUpdatedContract.setId(contract.getId());

    partialUpdatedContract
        .contractOwner(UPDATED_CONTRACT_OWNER)
        .contractUser(UPDATED_CONTRACT_USER)
        .endDate(UPDATED_END_DATE)
        .paymentPattern(UPDATED_PAYMENT_PATTERN)
        .paymentType(UPDATED_PAYMENT_TYPE)
        .activities(UPDATED_ACTIVITIES)
        .loanValue(UPDATED_LOAN_VALUE)
        .interestRatePercent(UPDATED_INTEREST_RATE_PERCENT)
        .amortizationRatePercent(UPDATED_AMORTIZATION_RATE_PERCENT);

    restContractMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, partialUpdatedContract.getId())
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContract)))
        .andExpect(status().isOk());

    // Validate the Contract in the database
    List<Contract> contractList = contractRepository.findAll();
    assertThat(contractList).hasSize(databaseSizeBeforeUpdate);
    Contract testContract = contractList.get(contractList.size() - 1);
    assertThat(testContract.getContractor()).isEqualTo(DEFAULT_CONTRACTOR);
    assertThat(testContract.getContractOwner())
        .isEqualTo(UPDATED_CONTRACT_OWNER);
    assertThat(testContract.getContractUser()).isEqualTo(UPDATED_CONTRACT_USER);
    assertThat(testContract.getContractNumber())
        .isEqualTo(DEFAULT_CONTRACT_NUMBER);
    assertThat(testContract.getStartDate()).isEqualTo(DEFAULT_START_DATE);
    assertThat(testContract.getEndDate()).isEqualTo(UPDATED_END_DATE);
    assertThat(testContract.getApplicationDate())
        .isEqualTo(DEFAULT_APPLICATION_DATE);
    assertThat(testContract.getConfirmationDate())
        .isEqualTo(DEFAULT_CONFIRMATION_DATE);
    assertThat(testContract.getPaymentPattern())
        .isEqualTo(UPDATED_PAYMENT_PATTERN);
    assertThat(testContract.getComment()).isEqualTo(DEFAULT_COMMENT);
    assertThat(testContract.getPayer()).isEqualTo(DEFAULT_PAYER);
    assertThat(testContract.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
    assertThat(testContract.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
    assertThat(testContract.getActivities()).isEqualTo(UPDATED_ACTIVITIES);
    assertThat(testContract.getLoanValue()).isEqualTo(UPDATED_LOAN_VALUE);
    assertThat(testContract.getInterestRatePercent())
        .isEqualTo(UPDATED_INTEREST_RATE_PERCENT);
    assertThat(testContract.getInterestRateCurrency())
        .isEqualTo(DEFAULT_INTEREST_RATE_CURRENCY);
    assertThat(testContract.getAmortizationRatePercent())
        .isEqualTo(UPDATED_AMORTIZATION_RATE_PERCENT);
    assertThat(testContract.getAmortizationRateCurrency())
        .isEqualTo(DEFAULT_AMORTIZATION_RATE_CURRENCY);
  }

  @Test
  @Transactional
  void fullUpdateContractWithPatch() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    int databaseSizeBeforeUpdate = contractRepository.findAll().size();

    // Update the contract using partial update
    Contract partialUpdatedContract = new Contract();
    partialUpdatedContract.setId(contract.getId());

    partialUpdatedContract
        .contractor(UPDATED_CONTRACTOR)
        .contractOwner(UPDATED_CONTRACT_OWNER)
        .contractUser(UPDATED_CONTRACT_USER)
        .contractNumber(UPDATED_CONTRACT_NUMBER)
        .startDate(UPDATED_START_DATE)
        .endDate(UPDATED_END_DATE)
        .applicationDate(UPDATED_APPLICATION_DATE)
        .confirmationDate(UPDATED_CONFIRMATION_DATE)
        .paymentPattern(UPDATED_PAYMENT_PATTERN)
        .payer(UPDATED_PAYER)
        .paymentType(UPDATED_PAYMENT_TYPE)
        .occupation(UPDATED_OCCUPATION)
        .activities(UPDATED_ACTIVITIES)
        .loanValue(UPDATED_LOAN_VALUE)
        .interestRatePercent(UPDATED_INTEREST_RATE_PERCENT)
        .interestRateCurrency(UPDATED_INTEREST_RATE_CURRENCY)
        .amortizationRatePercent(UPDATED_AMORTIZATION_RATE_PERCENT)
        .amortizationRateCurrency(UPDATED_AMORTIZATION_RATE_CURRENCY);

    restContractMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, partialUpdatedContract.getId())
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContract)))
        .andExpect(status().isOk());

    // Validate the Contract in the database
    List<Contract> contractList = contractRepository.findAll();
    assertThat(contractList).hasSize(databaseSizeBeforeUpdate);
    Contract testContract = contractList.get(contractList.size() - 1);
    assertThat(testContract.getContractor()).isEqualTo(UPDATED_CONTRACTOR);
    assertThat(testContract.getContractOwner())
        .isEqualTo(UPDATED_CONTRACT_OWNER);
    assertThat(testContract.getContractUser()).isEqualTo(UPDATED_CONTRACT_USER);
    assertThat(testContract.getContractNumber())
        .isEqualTo(UPDATED_CONTRACT_NUMBER);
    assertThat(testContract.getStartDate()).isEqualTo(UPDATED_START_DATE);
    assertThat(testContract.getEndDate()).isEqualTo(UPDATED_END_DATE);
    assertThat(testContract.getApplicationDate())
        .isEqualTo(UPDATED_APPLICATION_DATE);
    assertThat(testContract.getConfirmationDate())
        .isEqualTo(UPDATED_CONFIRMATION_DATE);
    assertThat(testContract.getPaymentPattern())
        .isEqualTo(UPDATED_PAYMENT_PATTERN);
    assertThat(testContract.getComment()).isEqualTo(UPDATED_COMMENT);
    assertThat(testContract.getPayer()).isEqualTo(UPDATED_PAYER);
    assertThat(testContract.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
    assertThat(testContract.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
    assertThat(testContract.getActivities()).isEqualTo(UPDATED_ACTIVITIES);
    assertThat(testContract.getLoanValue()).isEqualTo(UPDATED_LOAN_VALUE);
    assertThat(testContract.getInterestRatePercent())
        .isEqualTo(UPDATED_INTEREST_RATE_PERCENT);
    assertThat(testContract.getInterestRateCurrency())
        .isEqualTo(UPDATED_INTEREST_RATE_CURRENCY);
    assertThat(testContract.getAmortizationRatePercent())
        .isEqualTo(UPDATED_AMORTIZATION_RATE_PERCENT);
    assertThat(testContract.getAmortizationRateCurrency())
        .isEqualTo(UPDATED_AMORTIZATION_RATE_CURRENCY);
  }

  @Test
  @Transactional
  void patchNonExistingContract() throws Exception {
    int databaseSizeBeforeUpdate = contractRepository.findAll().size();
    contract.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restContractMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, contract.getId())
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(contract)))
        .andExpect(status().isBadRequest());

    // Validate the Contract in the database
    List<Contract> contractList = contractRepository.findAll();
    assertThat(contractList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchContract() throws Exception {
    int databaseSizeBeforeUpdate = contractRepository.findAll().size();
    contract.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restContractMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, count.incrementAndGet())
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(contract)))
        .andExpect(status().isBadRequest());

    // Validate the Contract in the database
    List<Contract> contractList = contractRepository.findAll();
    assertThat(contractList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamContract() throws Exception {
    int databaseSizeBeforeUpdate = contractRepository.findAll().size();
    contract.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restContractMockMvc
        .perform(
            patch(ENTITY_API_URL)
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(contract)))
        .andExpect(status().isMethodNotAllowed());

    // Validate the Contract in the database
    List<Contract> contractList = contractRepository.findAll();
    assertThat(contractList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteContract() throws Exception {
    // Initialize the database
    contractRepository.saveAndFlush(contract);

    int databaseSizeBeforeDelete = contractRepository.findAll().size();

    // Delete the contract
    restContractMockMvc
        .perform(
            delete(ENTITY_API_URL_ID, contract.getId())
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<Contract> contractList = contractRepository.findAll();
    assertThat(contractList).hasSize(databaseSizeBeforeDelete - 1);
  }
}
