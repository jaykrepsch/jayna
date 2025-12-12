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
import me.jayna.domain.ContactFinanceAccount;
import me.jayna.domain.FinanceAccount;
import me.jayna.domain.GroupType;
import me.jayna.repository.FinanceAccountRepository;
import me.jayna.service.criteria.FinanceAccountCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FinanceAccountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FinanceAccountResourceIT {

  private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
  private static final LocalDate UPDATED_START_DATE = LocalDate.now(
      ZoneId.systemDefault());
  private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

  private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
  private static final LocalDate UPDATED_END_DATE = LocalDate.now(
      ZoneId.systemDefault());
  private static final LocalDate SMALLER_END_DATE = LocalDate.ofEpochDay(-1L);

  private static final String DEFAULT_ACCOUNT_GIVER = "AAAAAAAAAA";
  private static final String UPDATED_ACCOUNT_GIVER = "BBBBBBBBBB";

  private static final String DEFAULT_ACCOUNT_USER = "AAAAAAAAAA";
  private static final String UPDATED_ACCOUNT_USER = "BBBBBBBBBB";

  private static final String DEFAULT_IBAN = "AAAAAAAAAA";
  private static final String UPDATED_IBAN = "BBBBBBBBBB";

  private static final String DEFAULT_BIC = "AAAAAAAAAA";
  private static final String UPDATED_BIC = "BBBBBBBBBB";

  private static final String DEFAULT_CURRENT_ACCOUNT = "AAAAAAAAAA";
  private static final String UPDATED_CURRENT_ACCOUNT = "BBBBBBBBBB";

  private static final Double DEFAULT_OVERDRAFT_INTEREST = 1D;
  private static final Double UPDATED_OVERDRAFT_INTEREST = 2D;
  private static final Double SMALLER_OVERDRAFT_INTEREST = 1D - 1D;

  private static final Double DEFAULT_LIMIT = 1D;
  private static final Double UPDATED_LIMIT = 2D;
  private static final Double SMALLER_LIMIT = 1D - 1D;

  private static final LocalDate DEFAULT_PAYMENT_UNTIL = LocalDate.ofEpochDay(
      0L);
  private static final LocalDate UPDATED_PAYMENT_UNTIL = LocalDate.now(
      ZoneId.systemDefault());
  private static final LocalDate SMALLER_PAYMENT_UNTIL = LocalDate.ofEpochDay(
      -1L);

  private static final Double DEFAULT_CARD_FEE = 1D;
  private static final Double UPDATED_CARD_FEE = 2D;
  private static final Double SMALLER_CARD_FEE = 1D - 1D;

  private static final Double DEFAULT_LOAN_INTEREST = 1D;
  private static final Double UPDATED_LOAN_INTEREST = 2D;
  private static final Double SMALLER_LOAN_INTEREST = 1D - 1D;

  private static final Double DEFAULT_ACCOUNT_MAINTENANCE_FEE = 1D;
  private static final Double UPDATED_ACCOUNT_MAINTENANCE_FEE = 2D;
  private static final Double SMALLER_ACCOUNT_MAINTENANCE_FEE = 1D - 1D;

  private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
  private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

  private static final String DEFAULT_BANK_NUMBER = "AAAAAAAAAA";
  private static final String UPDATED_BANK_NUMBER = "BBBBBBBBBB";

  private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
  private static final String UPDATED_COMMENT = "BBBBBBBBBB";

  private static final String ENTITY_API_URL = "/api/finance-accounts";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(
      random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private FinanceAccountRepository financeAccountRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restFinanceAccountMockMvc;

  private FinanceAccount financeAccount;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static FinanceAccount createEntity(EntityManager em) {
    FinanceAccount financeAccount = new FinanceAccount()
        .startDate(DEFAULT_START_DATE)
        .endDate(DEFAULT_END_DATE)
        .accountGiver(DEFAULT_ACCOUNT_GIVER)
        .accountUser(DEFAULT_ACCOUNT_USER)
        .iban(DEFAULT_IBAN)
        .bic(DEFAULT_BIC)
        .overdraftInterest(DEFAULT_OVERDRAFT_INTEREST)
        .limit(DEFAULT_LIMIT)
        .paymentUntil(DEFAULT_PAYMENT_UNTIL)
        .cardFee(DEFAULT_CARD_FEE)
        .loanInterest(DEFAULT_LOAN_INTEREST)
        .accountMaintenanceFee(DEFAULT_ACCOUNT_MAINTENANCE_FEE)
        .accountNumber(DEFAULT_ACCOUNT_NUMBER)
        .bankNumber(DEFAULT_BANK_NUMBER);
    return financeAccount;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static FinanceAccount createUpdatedEntity(EntityManager em) {
    FinanceAccount financeAccount = new FinanceAccount()
        .startDate(UPDATED_START_DATE)
        .endDate(UPDATED_END_DATE)
        .accountGiver(UPDATED_ACCOUNT_GIVER)
        .accountUser(UPDATED_ACCOUNT_USER)
        .iban(UPDATED_IBAN)
        .bic(UPDATED_BIC)
        .overdraftInterest(UPDATED_OVERDRAFT_INTEREST)
        .limit(UPDATED_LIMIT)
        .paymentUntil(UPDATED_PAYMENT_UNTIL)
        .cardFee(UPDATED_CARD_FEE)
        .loanInterest(UPDATED_LOAN_INTEREST)
        .accountMaintenanceFee(UPDATED_ACCOUNT_MAINTENANCE_FEE)
        .accountNumber(UPDATED_ACCOUNT_NUMBER)
        .bankNumber(UPDATED_BANK_NUMBER);
    return financeAccount;
  }

  @BeforeEach
  public void initTest() {
    financeAccount = createEntity(em);
  }

  @Test
  @Transactional
  void createFinanceAccount() throws Exception {
    int databaseSizeBeforeCreate = financeAccountRepository.findAll().size();
    // Create the FinanceAccount
    restFinanceAccountMockMvc
        .perform(
            post(ENTITY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(financeAccount)))
        .andExpect(status().isCreated());

    // Validate the FinanceAccount in the database
    List<FinanceAccount> financeAccountList = financeAccountRepository.findAll();
    assertThat(financeAccountList).hasSize(databaseSizeBeforeCreate + 1);
    FinanceAccount testFinanceAccount = financeAccountList.get(
        financeAccountList.size() - 1);
    assertThat(testFinanceAccount.getStartDate()).isEqualTo(DEFAULT_START_DATE);
    assertThat(testFinanceAccount.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    assertThat(testFinanceAccount.getAccountGiver())
        .isEqualTo(DEFAULT_ACCOUNT_GIVER);
    assertThat(testFinanceAccount.getAccountUser())
        .isEqualTo(DEFAULT_ACCOUNT_USER);
    assertThat(testFinanceAccount.getIban()).isEqualTo(DEFAULT_IBAN);
    assertThat(testFinanceAccount.getBic()).isEqualTo(DEFAULT_BIC);
    assertThat(testFinanceAccount.getOverdraftInterest())
        .isEqualTo(DEFAULT_OVERDRAFT_INTEREST);
    assertThat(testFinanceAccount.getLimit()).isEqualTo(DEFAULT_LIMIT);
    assertThat(testFinanceAccount.getPaymentUntil())
        .isEqualTo(DEFAULT_PAYMENT_UNTIL);
    assertThat(testFinanceAccount.getCardFee()).isEqualTo(DEFAULT_CARD_FEE);
    assertThat(testFinanceAccount.getLoanInterest())
        .isEqualTo(DEFAULT_LOAN_INTEREST);
    assertThat(testFinanceAccount.getAccountMaintenanceFee())
        .isEqualTo(DEFAULT_ACCOUNT_MAINTENANCE_FEE);
    assertThat(testFinanceAccount.getAccountNumber())
        .isEqualTo(DEFAULT_ACCOUNT_NUMBER);
    assertThat(testFinanceAccount.getBankNumber())
        .isEqualTo(DEFAULT_BANK_NUMBER);
    assertThat(testFinanceAccount.getComment()).isEqualTo(DEFAULT_COMMENT);
  }

  @Test
  @Transactional
  void createFinanceAccountWithExistingId() throws Exception {
    // Create the FinanceAccount with an existing ID
    financeAccount.setId(1L);

    int databaseSizeBeforeCreate = financeAccountRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restFinanceAccountMockMvc
        .perform(
            post(ENTITY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(financeAccount)))
        .andExpect(status().isBadRequest());

    // Validate the FinanceAccount in the database
    List<FinanceAccount> financeAccountList = financeAccountRepository.findAll();
    assertThat(financeAccountList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void getAllFinanceAccounts() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList
    restFinanceAccountMockMvc
        .perform(get(ENTITY_API_URL + "?sort=id,desc"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(
            jsonPath("$.[*].id").value(hasItem(financeAccount.getId().intValue())))
        .andExpect(
            jsonPath("$.[*].startDate")
                .value(hasItem(DEFAULT_START_DATE.toString())))
        .andExpect(
            jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
        .andExpect(
            jsonPath("$.[*].accountGiver").value(hasItem(DEFAULT_ACCOUNT_GIVER)))
        .andExpect(
            jsonPath("$.[*].accountUser").value(hasItem(DEFAULT_ACCOUNT_USER)))
        .andExpect(jsonPath("$.[*].iban").value(hasItem(DEFAULT_IBAN)))
        .andExpect(jsonPath("$.[*].bic").value(hasItem(DEFAULT_BIC)))
        .andExpect(
            jsonPath("$.[*].currentAccount").value(hasItem(DEFAULT_CURRENT_ACCOUNT)))
        .andExpect(
            jsonPath("$.[*].overdraftInterest")
                .value(hasItem(DEFAULT_OVERDRAFT_INTEREST.doubleValue())))
        .andExpect(
            jsonPath("$.[*].limit").value(hasItem(DEFAULT_LIMIT.doubleValue())))
        .andExpect(
            jsonPath("$.[*].paymentUntil")
                .value(hasItem(DEFAULT_PAYMENT_UNTIL.toString())))
        .andExpect(
            jsonPath("$.[*].cardFee").value(hasItem(DEFAULT_CARD_FEE.doubleValue())))
        .andExpect(
            jsonPath("$.[*].loanInterest")
                .value(hasItem(DEFAULT_LOAN_INTEREST.doubleValue())))
        .andExpect(
            jsonPath("$.[*].accountMaintenanceFee")
                .value(hasItem(DEFAULT_ACCOUNT_MAINTENANCE_FEE.doubleValue())))
        .andExpect(
            jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
        .andExpect(
            jsonPath("$.[*].bankNumber").value(hasItem(DEFAULT_BANK_NUMBER)))
        .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));
  }

  @Test
  @Transactional
  void getFinanceAccount() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get the financeAccount
    restFinanceAccountMockMvc
        .perform(get(ENTITY_API_URL_ID, financeAccount.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.id").value(financeAccount.getId().intValue()))
        .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
        .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
        .andExpect(jsonPath("$.accountGiver").value(DEFAULT_ACCOUNT_GIVER))
        .andExpect(jsonPath("$.accountUser").value(DEFAULT_ACCOUNT_USER))
        .andExpect(jsonPath("$.iban").value(DEFAULT_IBAN))
        .andExpect(jsonPath("$.bic").value(DEFAULT_BIC))
        .andExpect(jsonPath("$.currentAccount").value(DEFAULT_CURRENT_ACCOUNT))
        .andExpect(
            jsonPath("$.overdraftInterest")
                .value(DEFAULT_OVERDRAFT_INTEREST.doubleValue()))
        .andExpect(jsonPath("$.limit").value(DEFAULT_LIMIT.doubleValue()))
        .andExpect(
            jsonPath("$.paymentUntil").value(DEFAULT_PAYMENT_UNTIL.toString()))
        .andExpect(jsonPath("$.cardFee").value(DEFAULT_CARD_FEE.doubleValue()))
        .andExpect(
            jsonPath("$.loanInterest").value(DEFAULT_LOAN_INTEREST.doubleValue()))
        .andExpect(
            jsonPath("$.accountMaintenanceFee")
                .value(DEFAULT_ACCOUNT_MAINTENANCE_FEE.doubleValue()))
        .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
        .andExpect(jsonPath("$.bankNumber").value(DEFAULT_BANK_NUMBER))
        .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT));
  }

  @Test
  @Transactional
  void getFinanceAccountsByIdFiltering() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    Long id = financeAccount.getId();

    defaultFinanceAccountShouldBeFound("id.equals=" + id);
    defaultFinanceAccountShouldNotBeFound("id.notEquals=" + id);

    defaultFinanceAccountShouldBeFound("id.greaterThanOrEqual=" + id);
    defaultFinanceAccountShouldNotBeFound("id.greaterThan=" + id);

    defaultFinanceAccountShouldBeFound("id.lessThanOrEqual=" + id);
    defaultFinanceAccountShouldNotBeFound("id.lessThan=" + id);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByStartDateIsEqualToSomething() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where startDate equals to DEFAULT_START_DATE
    defaultFinanceAccountShouldBeFound(
        "startDate.equals=" + DEFAULT_START_DATE);

    // Get all the financeAccountList where startDate equals to UPDATED_START_DATE
    defaultFinanceAccountShouldNotBeFound(
        "startDate.equals=" + UPDATED_START_DATE);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByStartDateIsInShouldWork() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where startDate in DEFAULT_START_DATE or
    // UPDATED_START_DATE
    defaultFinanceAccountShouldBeFound(
        "startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

    // Get all the financeAccountList where startDate equals to UPDATED_START_DATE
    defaultFinanceAccountShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByStartDateIsNullOrNotNull() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where startDate is not null
    defaultFinanceAccountShouldBeFound("startDate.specified=true");

    // Get all the financeAccountList where startDate is null
    defaultFinanceAccountShouldNotBeFound("startDate.specified=false");
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByStartDateIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where startDate is greater than or equal to
    // DEFAULT_START_DATE
    defaultFinanceAccountShouldBeFound(
        "startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

    // Get all the financeAccountList where startDate is greater than or equal to
    // UPDATED_START_DATE
    defaultFinanceAccountShouldNotBeFound(
        "startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByStartDateIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where startDate is less than or equal to
    // DEFAULT_START_DATE
    defaultFinanceAccountShouldBeFound(
        "startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

    // Get all the financeAccountList where startDate is less than or equal to
    // SMALLER_START_DATE
    defaultFinanceAccountShouldNotBeFound(
        "startDate.lessThanOrEqual=" + SMALLER_START_DATE);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByStartDateIsLessThanSomething() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where startDate is less than
    // DEFAULT_START_DATE
    defaultFinanceAccountShouldNotBeFound(
        "startDate.lessThan=" + DEFAULT_START_DATE);

    // Get all the financeAccountList where startDate is less than
    // UPDATED_START_DATE
    defaultFinanceAccountShouldBeFound(
        "startDate.lessThan=" + UPDATED_START_DATE);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByStartDateIsGreaterThanSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where startDate is greater than
    // DEFAULT_START_DATE
    defaultFinanceAccountShouldNotBeFound(
        "startDate.greaterThan=" + DEFAULT_START_DATE);

    // Get all the financeAccountList where startDate is greater than
    // SMALLER_START_DATE
    defaultFinanceAccountShouldBeFound(
        "startDate.greaterThan=" + SMALLER_START_DATE);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByEndDateIsEqualToSomething() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where endDate equals to DEFAULT_END_DATE
    defaultFinanceAccountShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

    // Get all the financeAccountList where endDate equals to UPDATED_END_DATE
    defaultFinanceAccountShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByEndDateIsInShouldWork() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where endDate in DEFAULT_END_DATE or
    // UPDATED_END_DATE
    defaultFinanceAccountShouldBeFound(
        "endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

    // Get all the financeAccountList where endDate equals to UPDATED_END_DATE
    defaultFinanceAccountShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByEndDateIsNullOrNotNull() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where endDate is not null
    defaultFinanceAccountShouldBeFound("endDate.specified=true");

    // Get all the financeAccountList where endDate is null
    defaultFinanceAccountShouldNotBeFound("endDate.specified=false");
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByEndDateIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where endDate is greater than or equal to
    // DEFAULT_END_DATE
    defaultFinanceAccountShouldBeFound(
        "endDate.greaterThanOrEqual=" + DEFAULT_END_DATE);

    // Get all the financeAccountList where endDate is greater than or equal to
    // UPDATED_END_DATE
    defaultFinanceAccountShouldNotBeFound(
        "endDate.greaterThanOrEqual=" + UPDATED_END_DATE);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByEndDateIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where endDate is less than or equal to
    // DEFAULT_END_DATE
    defaultFinanceAccountShouldBeFound(
        "endDate.lessThanOrEqual=" + DEFAULT_END_DATE);

    // Get all the financeAccountList where endDate is less than or equal to
    // SMALLER_END_DATE
    defaultFinanceAccountShouldNotBeFound(
        "endDate.lessThanOrEqual=" + SMALLER_END_DATE);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByEndDateIsLessThanSomething() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where endDate is less than DEFAULT_END_DATE
    defaultFinanceAccountShouldNotBeFound(
        "endDate.lessThan=" + DEFAULT_END_DATE);

    // Get all the financeAccountList where endDate is less than UPDATED_END_DATE
    defaultFinanceAccountShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByEndDateIsGreaterThanSomething() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where endDate is greater than DEFAULT_END_DATE
    defaultFinanceAccountShouldNotBeFound(
        "endDate.greaterThan=" + DEFAULT_END_DATE);

    // Get all the financeAccountList where endDate is greater than SMALLER_END_DATE
    defaultFinanceAccountShouldBeFound(
        "endDate.greaterThan=" + SMALLER_END_DATE);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByAccountGiverIsEqualToSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where accountGiver equals to
    // DEFAULT_ACCOUNT_GIVER
    defaultFinanceAccountShouldBeFound(
        "accountGiver.equals=" + DEFAULT_ACCOUNT_GIVER);

    // Get all the financeAccountList where accountGiver equals to
    // UPDATED_ACCOUNT_GIVER
    defaultFinanceAccountShouldNotBeFound(
        "accountGiver.equals=" + UPDATED_ACCOUNT_GIVER);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByAccountGiverIsInShouldWork() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where accountGiver in DEFAULT_ACCOUNT_GIVER or
    // UPDATED_ACCOUNT_GIVER
    defaultFinanceAccountShouldBeFound(
        "accountGiver.in=" + DEFAULT_ACCOUNT_GIVER + "," + UPDATED_ACCOUNT_GIVER);

    // Get all the financeAccountList where accountGiver equals to
    // UPDATED_ACCOUNT_GIVER
    defaultFinanceAccountShouldNotBeFound(
        "accountGiver.in=" + UPDATED_ACCOUNT_GIVER);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByAccountGiverIsNullOrNotNull() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where accountGiver is not null
    defaultFinanceAccountShouldBeFound("accountGiver.specified=true");

    // Get all the financeAccountList where accountGiver is null
    defaultFinanceAccountShouldNotBeFound("accountGiver.specified=false");
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByAccountGiverContainsSomething() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where accountGiver contains
    // DEFAULT_ACCOUNT_GIVER
    defaultFinanceAccountShouldBeFound(
        "accountGiver.contains=" + DEFAULT_ACCOUNT_GIVER);

    // Get all the financeAccountList where accountGiver contains
    // UPDATED_ACCOUNT_GIVER
    defaultFinanceAccountShouldNotBeFound(
        "accountGiver.contains=" + UPDATED_ACCOUNT_GIVER);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByAccountGiverNotContainsSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where accountGiver does not contain
    // DEFAULT_ACCOUNT_GIVER
    defaultFinanceAccountShouldNotBeFound(
        "accountGiver.doesNotContain=" + DEFAULT_ACCOUNT_GIVER);

    // Get all the financeAccountList where accountGiver does not contain
    // UPDATED_ACCOUNT_GIVER
    defaultFinanceAccountShouldBeFound(
        "accountGiver.doesNotContain=" + UPDATED_ACCOUNT_GIVER);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByAccountUserIsEqualToSomething() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where accountUser equals to
    // DEFAULT_ACCOUNT_USER
    defaultFinanceAccountShouldBeFound(
        "accountUser.equals=" + DEFAULT_ACCOUNT_USER);

    // Get all the financeAccountList where accountUser equals to
    // UPDATED_ACCOUNT_USER
    defaultFinanceAccountShouldNotBeFound(
        "accountUser.equals=" + UPDATED_ACCOUNT_USER);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByAccountUserIsInShouldWork() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where accountUser in DEFAULT_ACCOUNT_USER or
    // UPDATED_ACCOUNT_USER
    defaultFinanceAccountShouldBeFound(
        "accountUser.in=" + DEFAULT_ACCOUNT_USER + "," + UPDATED_ACCOUNT_USER);

    // Get all the financeAccountList where accountUser equals to
    // UPDATED_ACCOUNT_USER
    defaultFinanceAccountShouldNotBeFound(
        "accountUser.in=" + UPDATED_ACCOUNT_USER);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByAccountUserIsNullOrNotNull() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where accountUser is not null
    defaultFinanceAccountShouldBeFound("accountUser.specified=true");

    // Get all the financeAccountList where accountUser is null
    defaultFinanceAccountShouldNotBeFound("accountUser.specified=false");
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByAccountUserContainsSomething() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where accountUser contains
    // DEFAULT_ACCOUNT_USER
    defaultFinanceAccountShouldBeFound(
        "accountUser.contains=" + DEFAULT_ACCOUNT_USER);

    // Get all the financeAccountList where accountUser contains
    // UPDATED_ACCOUNT_USER
    defaultFinanceAccountShouldNotBeFound(
        "accountUser.contains=" + UPDATED_ACCOUNT_USER);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByAccountUserNotContainsSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where accountUser does not contain
    // DEFAULT_ACCOUNT_USER
    defaultFinanceAccountShouldNotBeFound(
        "accountUser.doesNotContain=" + DEFAULT_ACCOUNT_USER);

    // Get all the financeAccountList where accountUser does not contain
    // UPDATED_ACCOUNT_USER
    defaultFinanceAccountShouldBeFound(
        "accountUser.doesNotContain=" + UPDATED_ACCOUNT_USER);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByIbanIsEqualToSomething() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where iban equals to DEFAULT_IBAN
    defaultFinanceAccountShouldBeFound("iban.equals=" + DEFAULT_IBAN);

    // Get all the financeAccountList where iban equals to UPDATED_IBAN
    defaultFinanceAccountShouldNotBeFound("iban.equals=" + UPDATED_IBAN);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByIbanIsInShouldWork() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where iban in DEFAULT_IBAN or UPDATED_IBAN
    defaultFinanceAccountShouldBeFound(
        "iban.in=" + DEFAULT_IBAN + "," + UPDATED_IBAN);

    // Get all the financeAccountList where iban equals to UPDATED_IBAN
    defaultFinanceAccountShouldNotBeFound("iban.in=" + UPDATED_IBAN);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByIbanIsNullOrNotNull() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where iban is not null
    defaultFinanceAccountShouldBeFound("iban.specified=true");

    // Get all the financeAccountList where iban is null
    defaultFinanceAccountShouldNotBeFound("iban.specified=false");
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByIbanContainsSomething() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where iban contains DEFAULT_IBAN
    defaultFinanceAccountShouldBeFound("iban.contains=" + DEFAULT_IBAN);

    // Get all the financeAccountList where iban contains UPDATED_IBAN
    defaultFinanceAccountShouldNotBeFound("iban.contains=" + UPDATED_IBAN);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByIbanNotContainsSomething() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where iban does not contain DEFAULT_IBAN
    defaultFinanceAccountShouldNotBeFound(
        "iban.doesNotContain=" + DEFAULT_IBAN);

    // Get all the financeAccountList where iban does not contain UPDATED_IBAN
    defaultFinanceAccountShouldBeFound("iban.doesNotContain=" + UPDATED_IBAN);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByBicIsEqualToSomething() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where bic equals to DEFAULT_BIC
    defaultFinanceAccountShouldBeFound("bic.equals=" + DEFAULT_BIC);

    // Get all the financeAccountList where bic equals to UPDATED_BIC
    defaultFinanceAccountShouldNotBeFound("bic.equals=" + UPDATED_BIC);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByBicIsInShouldWork() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where bic in DEFAULT_BIC or UPDATED_BIC
    defaultFinanceAccountShouldBeFound(
        "bic.in=" + DEFAULT_BIC + "," + UPDATED_BIC);

    // Get all the financeAccountList where bic equals to UPDATED_BIC
    defaultFinanceAccountShouldNotBeFound("bic.in=" + UPDATED_BIC);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByBicIsNullOrNotNull() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where bic is not null
    defaultFinanceAccountShouldBeFound("bic.specified=true");

    // Get all the financeAccountList where bic is null
    defaultFinanceAccountShouldNotBeFound("bic.specified=false");
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByBicContainsSomething() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where bic contains DEFAULT_BIC
    defaultFinanceAccountShouldBeFound("bic.contains=" + DEFAULT_BIC);

    // Get all the financeAccountList where bic contains UPDATED_BIC
    defaultFinanceAccountShouldNotBeFound("bic.contains=" + UPDATED_BIC);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByBicNotContainsSomething() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where bic does not contain DEFAULT_BIC
    defaultFinanceAccountShouldNotBeFound("bic.doesNotContain=" + DEFAULT_BIC);

    // Get all the financeAccountList where bic does not contain UPDATED_BIC
    defaultFinanceAccountShouldBeFound("bic.doesNotContain=" + UPDATED_BIC);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByCurrentAccountIsEqualToSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where currentAccount equals to
    // DEFAULT_CURRENT_ACCOUNT
    defaultFinanceAccountShouldBeFound(
        "currentAccount.equals=" + DEFAULT_CURRENT_ACCOUNT);

    // Get all the financeAccountList where currentAccount equals to
    // UPDATED_CURRENT_ACCOUNT
    defaultFinanceAccountShouldNotBeFound(
        "currentAccount.equals=" + UPDATED_CURRENT_ACCOUNT);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByCurrentAccountIsInShouldWork() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where currentAccount in
    // DEFAULT_CURRENT_ACCOUNT or UPDATED_CURRENT_ACCOUNT
    defaultFinanceAccountShouldBeFound(
        "currentAccount.in=" +
            DEFAULT_CURRENT_ACCOUNT +
            "," +
            UPDATED_CURRENT_ACCOUNT);

    // Get all the financeAccountList where currentAccount equals to
    // UPDATED_CURRENT_ACCOUNT
    defaultFinanceAccountShouldNotBeFound(
        "currentAccount.in=" + UPDATED_CURRENT_ACCOUNT);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByCurrentAccountIsNullOrNotNull() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where currentAccount is not null
    defaultFinanceAccountShouldBeFound("currentAccount.specified=true");

    // Get all the financeAccountList where currentAccount is null
    defaultFinanceAccountShouldNotBeFound("currentAccount.specified=false");
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByCurrentAccountContainsSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where currentAccount contains
    // DEFAULT_CURRENT_ACCOUNT
    defaultFinanceAccountShouldBeFound(
        "currentAccount.contains=" + DEFAULT_CURRENT_ACCOUNT);

    // Get all the financeAccountList where currentAccount contains
    // UPDATED_CURRENT_ACCOUNT
    defaultFinanceAccountShouldNotBeFound(
        "currentAccount.contains=" + UPDATED_CURRENT_ACCOUNT);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByCurrentAccountNotContainsSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where currentAccount does not contain
    // DEFAULT_CURRENT_ACCOUNT
    defaultFinanceAccountShouldNotBeFound(
        "currentAccount.doesNotContain=" + DEFAULT_CURRENT_ACCOUNT);

    // Get all the financeAccountList where currentAccount does not contain
    // UPDATED_CURRENT_ACCOUNT
    defaultFinanceAccountShouldBeFound(
        "currentAccount.doesNotContain=" + UPDATED_CURRENT_ACCOUNT);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByOverdraftInterestIsEqualToSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where overdraftInterest equals to
    // DEFAULT_OVERDRAFT_INTEREST
    defaultFinanceAccountShouldBeFound(
        "overdraftInterest.equals=" + DEFAULT_OVERDRAFT_INTEREST);

    // Get all the financeAccountList where overdraftInterest equals to
    // UPDATED_OVERDRAFT_INTEREST
    defaultFinanceAccountShouldNotBeFound(
        "overdraftInterest.equals=" + UPDATED_OVERDRAFT_INTEREST);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByOverdraftInterestIsInShouldWork()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where overdraftInterest in
    // DEFAULT_OVERDRAFT_INTEREST or UPDATED_OVERDRAFT_INTEREST
    defaultFinanceAccountShouldBeFound(
        "overdraftInterest.in=" +
            DEFAULT_OVERDRAFT_INTEREST +
            "," +
            UPDATED_OVERDRAFT_INTEREST);

    // Get all the financeAccountList where overdraftInterest equals to
    // UPDATED_OVERDRAFT_INTEREST
    defaultFinanceAccountShouldNotBeFound(
        "overdraftInterest.in=" + UPDATED_OVERDRAFT_INTEREST);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByOverdraftInterestIsNullOrNotNull()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where overdraftInterest is not null
    defaultFinanceAccountShouldBeFound("overdraftInterest.specified=true");

    // Get all the financeAccountList where overdraftInterest is null
    defaultFinanceAccountShouldNotBeFound("overdraftInterest.specified=false");
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByOverdraftInterestIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where overdraftInterest is greater than or
    // equal to DEFAULT_OVERDRAFT_INTEREST
    defaultFinanceAccountShouldBeFound(
        "overdraftInterest.greaterThanOrEqual=" + DEFAULT_OVERDRAFT_INTEREST);

    // Get all the financeAccountList where overdraftInterest is greater than or
    // equal to UPDATED_OVERDRAFT_INTEREST
    defaultFinanceAccountShouldNotBeFound(
        "overdraftInterest.greaterThanOrEqual=" + UPDATED_OVERDRAFT_INTEREST);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByOverdraftInterestIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where overdraftInterest is less than or equal
    // to DEFAULT_OVERDRAFT_INTEREST
    defaultFinanceAccountShouldBeFound(
        "overdraftInterest.lessThanOrEqual=" + DEFAULT_OVERDRAFT_INTEREST);

    // Get all the financeAccountList where overdraftInterest is less than or equal
    // to SMALLER_OVERDRAFT_INTEREST
    defaultFinanceAccountShouldNotBeFound(
        "overdraftInterest.lessThanOrEqual=" + SMALLER_OVERDRAFT_INTEREST);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByOverdraftInterestIsLessThanSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where overdraftInterest is less than
    // DEFAULT_OVERDRAFT_INTEREST
    defaultFinanceAccountShouldNotBeFound(
        "overdraftInterest.lessThan=" + DEFAULT_OVERDRAFT_INTEREST);

    // Get all the financeAccountList where overdraftInterest is less than
    // UPDATED_OVERDRAFT_INTEREST
    defaultFinanceAccountShouldBeFound(
        "overdraftInterest.lessThan=" + UPDATED_OVERDRAFT_INTEREST);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByOverdraftInterestIsGreaterThanSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where overdraftInterest is greater than
    // DEFAULT_OVERDRAFT_INTEREST
    defaultFinanceAccountShouldNotBeFound(
        "overdraftInterest.greaterThan=" + DEFAULT_OVERDRAFT_INTEREST);

    // Get all the financeAccountList where overdraftInterest is greater than
    // SMALLER_OVERDRAFT_INTEREST
    defaultFinanceAccountShouldBeFound(
        "overdraftInterest.greaterThan=" + SMALLER_OVERDRAFT_INTEREST);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByLimitIsEqualToSomething() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where limit equals to DEFAULT_LIMIT
    defaultFinanceAccountShouldBeFound("limit.equals=" + DEFAULT_LIMIT);

    // Get all the financeAccountList where limit equals to UPDATED_LIMIT
    defaultFinanceAccountShouldNotBeFound("limit.equals=" + UPDATED_LIMIT);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByLimitIsInShouldWork() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where limit in DEFAULT_LIMIT or UPDATED_LIMIT
    defaultFinanceAccountShouldBeFound(
        "limit.in=" + DEFAULT_LIMIT + "," + UPDATED_LIMIT);

    // Get all the financeAccountList where limit equals to UPDATED_LIMIT
    defaultFinanceAccountShouldNotBeFound("limit.in=" + UPDATED_LIMIT);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByLimitIsNullOrNotNull() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where limit is not null
    defaultFinanceAccountShouldBeFound("limit.specified=true");

    // Get all the financeAccountList where limit is null
    defaultFinanceAccountShouldNotBeFound("limit.specified=false");
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByLimitIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where limit is greater than or equal to
    // DEFAULT_LIMIT
    defaultFinanceAccountShouldBeFound(
        "limit.greaterThanOrEqual=" + DEFAULT_LIMIT);

    // Get all the financeAccountList where limit is greater than or equal to
    // UPDATED_LIMIT
    defaultFinanceAccountShouldNotBeFound(
        "limit.greaterThanOrEqual=" + UPDATED_LIMIT);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByLimitIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where limit is less than or equal to
    // DEFAULT_LIMIT
    defaultFinanceAccountShouldBeFound(
        "limit.lessThanOrEqual=" + DEFAULT_LIMIT);

    // Get all the financeAccountList where limit is less than or equal to
    // SMALLER_LIMIT
    defaultFinanceAccountShouldNotBeFound(
        "limit.lessThanOrEqual=" + SMALLER_LIMIT);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByLimitIsLessThanSomething() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where limit is less than DEFAULT_LIMIT
    defaultFinanceAccountShouldNotBeFound("limit.lessThan=" + DEFAULT_LIMIT);

    // Get all the financeAccountList where limit is less than UPDATED_LIMIT
    defaultFinanceAccountShouldBeFound("limit.lessThan=" + UPDATED_LIMIT);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByLimitIsGreaterThanSomething() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where limit is greater than DEFAULT_LIMIT
    defaultFinanceAccountShouldNotBeFound("limit.greaterThan=" + DEFAULT_LIMIT);

    // Get all the financeAccountList where limit is greater than SMALLER_LIMIT
    defaultFinanceAccountShouldBeFound("limit.greaterThan=" + SMALLER_LIMIT);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByPaymentUntilIsEqualToSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where paymentUntil equals to
    // DEFAULT_PAYMENT_UNTIL
    defaultFinanceAccountShouldBeFound(
        "paymentUntil.equals=" + DEFAULT_PAYMENT_UNTIL);

    // Get all the financeAccountList where paymentUntil equals to
    // UPDATED_PAYMENT_UNTIL
    defaultFinanceAccountShouldNotBeFound(
        "paymentUntil.equals=" + UPDATED_PAYMENT_UNTIL);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByPaymentUntilIsInShouldWork() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where paymentUntil in DEFAULT_PAYMENT_UNTIL or
    // UPDATED_PAYMENT_UNTIL
    defaultFinanceAccountShouldBeFound(
        "paymentUntil.in=" + DEFAULT_PAYMENT_UNTIL + "," + UPDATED_PAYMENT_UNTIL);

    // Get all the financeAccountList where paymentUntil equals to
    // UPDATED_PAYMENT_UNTIL
    defaultFinanceAccountShouldNotBeFound(
        "paymentUntil.in=" + UPDATED_PAYMENT_UNTIL);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByPaymentUntilIsNullOrNotNull() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where paymentUntil is not null
    defaultFinanceAccountShouldBeFound("paymentUntil.specified=true");

    // Get all the financeAccountList where paymentUntil is null
    defaultFinanceAccountShouldNotBeFound("paymentUntil.specified=false");
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByPaymentUntilIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where paymentUntil is greater than or equal to
    // DEFAULT_PAYMENT_UNTIL
    defaultFinanceAccountShouldBeFound(
        "paymentUntil.greaterThanOrEqual=" + DEFAULT_PAYMENT_UNTIL);

    // Get all the financeAccountList where paymentUntil is greater than or equal to
    // UPDATED_PAYMENT_UNTIL
    defaultFinanceAccountShouldNotBeFound(
        "paymentUntil.greaterThanOrEqual=" + UPDATED_PAYMENT_UNTIL);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByPaymentUntilIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where paymentUntil is less than or equal to
    // DEFAULT_PAYMENT_UNTIL
    defaultFinanceAccountShouldBeFound(
        "paymentUntil.lessThanOrEqual=" + DEFAULT_PAYMENT_UNTIL);

    // Get all the financeAccountList where paymentUntil is less than or equal to
    // SMALLER_PAYMENT_UNTIL
    defaultFinanceAccountShouldNotBeFound(
        "paymentUntil.lessThanOrEqual=" + SMALLER_PAYMENT_UNTIL);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByPaymentUntilIsLessThanSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where paymentUntil is less than
    // DEFAULT_PAYMENT_UNTIL
    defaultFinanceAccountShouldNotBeFound(
        "paymentUntil.lessThan=" + DEFAULT_PAYMENT_UNTIL);

    // Get all the financeAccountList where paymentUntil is less than
    // UPDATED_PAYMENT_UNTIL
    defaultFinanceAccountShouldBeFound(
        "paymentUntil.lessThan=" + UPDATED_PAYMENT_UNTIL);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByPaymentUntilIsGreaterThanSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where paymentUntil is greater than
    // DEFAULT_PAYMENT_UNTIL
    defaultFinanceAccountShouldNotBeFound(
        "paymentUntil.greaterThan=" + DEFAULT_PAYMENT_UNTIL);

    // Get all the financeAccountList where paymentUntil is greater than
    // SMALLER_PAYMENT_UNTIL
    defaultFinanceAccountShouldBeFound(
        "paymentUntil.greaterThan=" + SMALLER_PAYMENT_UNTIL);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByCardFeeIsEqualToSomething() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where cardFee equals to DEFAULT_CARD_FEE
    defaultFinanceAccountShouldBeFound("cardFee.equals=" + DEFAULT_CARD_FEE);

    // Get all the financeAccountList where cardFee equals to UPDATED_CARD_FEE
    defaultFinanceAccountShouldNotBeFound("cardFee.equals=" + UPDATED_CARD_FEE);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByCardFeeIsInShouldWork() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where cardFee in DEFAULT_CARD_FEE or
    // UPDATED_CARD_FEE
    defaultFinanceAccountShouldBeFound(
        "cardFee.in=" + DEFAULT_CARD_FEE + "," + UPDATED_CARD_FEE);

    // Get all the financeAccountList where cardFee equals to UPDATED_CARD_FEE
    defaultFinanceAccountShouldNotBeFound("cardFee.in=" + UPDATED_CARD_FEE);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByCardFeeIsNullOrNotNull() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where cardFee is not null
    defaultFinanceAccountShouldBeFound("cardFee.specified=true");

    // Get all the financeAccountList where cardFee is null
    defaultFinanceAccountShouldNotBeFound("cardFee.specified=false");
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByCardFeeIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where cardFee is greater than or equal to
    // DEFAULT_CARD_FEE
    defaultFinanceAccountShouldBeFound(
        "cardFee.greaterThanOrEqual=" + DEFAULT_CARD_FEE);

    // Get all the financeAccountList where cardFee is greater than or equal to
    // UPDATED_CARD_FEE
    defaultFinanceAccountShouldNotBeFound(
        "cardFee.greaterThanOrEqual=" + UPDATED_CARD_FEE);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByCardFeeIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where cardFee is less than or equal to
    // DEFAULT_CARD_FEE
    defaultFinanceAccountShouldBeFound(
        "cardFee.lessThanOrEqual=" + DEFAULT_CARD_FEE);

    // Get all the financeAccountList where cardFee is less than or equal to
    // SMALLER_CARD_FEE
    defaultFinanceAccountShouldNotBeFound(
        "cardFee.lessThanOrEqual=" + SMALLER_CARD_FEE);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByCardFeeIsLessThanSomething() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where cardFee is less than DEFAULT_CARD_FEE
    defaultFinanceAccountShouldNotBeFound(
        "cardFee.lessThan=" + DEFAULT_CARD_FEE);

    // Get all the financeAccountList where cardFee is less than UPDATED_CARD_FEE
    defaultFinanceAccountShouldBeFound("cardFee.lessThan=" + UPDATED_CARD_FEE);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByCardFeeIsGreaterThanSomething() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where cardFee is greater than DEFAULT_CARD_FEE
    defaultFinanceAccountShouldNotBeFound(
        "cardFee.greaterThan=" + DEFAULT_CARD_FEE);

    // Get all the financeAccountList where cardFee is greater than SMALLER_CARD_FEE
    defaultFinanceAccountShouldBeFound(
        "cardFee.greaterThan=" + SMALLER_CARD_FEE);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByLoanInterestIsEqualToSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where loanInterest equals to
    // DEFAULT_LOAN_INTEREST
    defaultFinanceAccountShouldBeFound(
        "loanInterest.equals=" + DEFAULT_LOAN_INTEREST);

    // Get all the financeAccountList where loanInterest equals to
    // UPDATED_LOAN_INTEREST
    defaultFinanceAccountShouldNotBeFound(
        "loanInterest.equals=" + UPDATED_LOAN_INTEREST);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByLoanInterestIsInShouldWork() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where loanInterest in DEFAULT_LOAN_INTEREST or
    // UPDATED_LOAN_INTEREST
    defaultFinanceAccountShouldBeFound(
        "loanInterest.in=" + DEFAULT_LOAN_INTEREST + "," + UPDATED_LOAN_INTEREST);

    // Get all the financeAccountList where loanInterest equals to
    // UPDATED_LOAN_INTEREST
    defaultFinanceAccountShouldNotBeFound(
        "loanInterest.in=" + UPDATED_LOAN_INTEREST);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByLoanInterestIsNullOrNotNull() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where loanInterest is not null
    defaultFinanceAccountShouldBeFound("loanInterest.specified=true");

    // Get all the financeAccountList where loanInterest is null
    defaultFinanceAccountShouldNotBeFound("loanInterest.specified=false");
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByLoanInterestIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where loanInterest is greater than or equal to
    // DEFAULT_LOAN_INTEREST
    defaultFinanceAccountShouldBeFound(
        "loanInterest.greaterThanOrEqual=" + DEFAULT_LOAN_INTEREST);

    // Get all the financeAccountList where loanInterest is greater than or equal to
    // UPDATED_LOAN_INTEREST
    defaultFinanceAccountShouldNotBeFound(
        "loanInterest.greaterThanOrEqual=" + UPDATED_LOAN_INTEREST);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByLoanInterestIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where loanInterest is less than or equal to
    // DEFAULT_LOAN_INTEREST
    defaultFinanceAccountShouldBeFound(
        "loanInterest.lessThanOrEqual=" + DEFAULT_LOAN_INTEREST);

    // Get all the financeAccountList where loanInterest is less than or equal to
    // SMALLER_LOAN_INTEREST
    defaultFinanceAccountShouldNotBeFound(
        "loanInterest.lessThanOrEqual=" + SMALLER_LOAN_INTEREST);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByLoanInterestIsLessThanSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where loanInterest is less than
    // DEFAULT_LOAN_INTEREST
    defaultFinanceAccountShouldNotBeFound(
        "loanInterest.lessThan=" + DEFAULT_LOAN_INTEREST);

    // Get all the financeAccountList where loanInterest is less than
    // UPDATED_LOAN_INTEREST
    defaultFinanceAccountShouldBeFound(
        "loanInterest.lessThan=" + UPDATED_LOAN_INTEREST);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByLoanInterestIsGreaterThanSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where loanInterest is greater than
    // DEFAULT_LOAN_INTEREST
    defaultFinanceAccountShouldNotBeFound(
        "loanInterest.greaterThan=" + DEFAULT_LOAN_INTEREST);

    // Get all the financeAccountList where loanInterest is greater than
    // SMALLER_LOAN_INTEREST
    defaultFinanceAccountShouldBeFound(
        "loanInterest.greaterThan=" + SMALLER_LOAN_INTEREST);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByAccountMaintenanceFeeIsEqualToSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where accountMaintenanceFee equals to
    // DEFAULT_ACCOUNT_MAINTENANCE_FEE
    defaultFinanceAccountShouldBeFound(
        "accountMaintenanceFee.equals=" + DEFAULT_ACCOUNT_MAINTENANCE_FEE);

    // Get all the financeAccountList where accountMaintenanceFee equals to
    // UPDATED_ACCOUNT_MAINTENANCE_FEE
    defaultFinanceAccountShouldNotBeFound(
        "accountMaintenanceFee.equals=" + UPDATED_ACCOUNT_MAINTENANCE_FEE);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByAccountMaintenanceFeeIsInShouldWork()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where accountMaintenanceFee in
    // DEFAULT_ACCOUNT_MAINTENANCE_FEE or UPDATED_ACCOUNT_MAINTENANCE_FEE
    defaultFinanceAccountShouldBeFound(
        "accountMaintenanceFee.in=" +
            DEFAULT_ACCOUNT_MAINTENANCE_FEE +
            "," +
            UPDATED_ACCOUNT_MAINTENANCE_FEE);

    // Get all the financeAccountList where accountMaintenanceFee equals to
    // UPDATED_ACCOUNT_MAINTENANCE_FEE
    defaultFinanceAccountShouldNotBeFound(
        "accountMaintenanceFee.in=" + UPDATED_ACCOUNT_MAINTENANCE_FEE);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByAccountMaintenanceFeeIsNullOrNotNull()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where accountMaintenanceFee is not null
    defaultFinanceAccountShouldBeFound("accountMaintenanceFee.specified=true");

    // Get all the financeAccountList where accountMaintenanceFee is null
    defaultFinanceAccountShouldNotBeFound(
        "accountMaintenanceFee.specified=false");
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByAccountMaintenanceFeeIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where accountMaintenanceFee is greater than or
    // equal to DEFAULT_ACCOUNT_MAINTENANCE_FEE
    defaultFinanceAccountShouldBeFound(
        "accountMaintenanceFee.greaterThanOrEqual=" +
            DEFAULT_ACCOUNT_MAINTENANCE_FEE);

    // Get all the financeAccountList where accountMaintenanceFee is greater than or
    // equal to UPDATED_ACCOUNT_MAINTENANCE_FEE
    defaultFinanceAccountShouldNotBeFound(
        "accountMaintenanceFee.greaterThanOrEqual=" +
            UPDATED_ACCOUNT_MAINTENANCE_FEE);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByAccountMaintenanceFeeIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where accountMaintenanceFee is less than or
    // equal to DEFAULT_ACCOUNT_MAINTENANCE_FEE
    defaultFinanceAccountShouldBeFound(
        "accountMaintenanceFee.lessThanOrEqual=" + DEFAULT_ACCOUNT_MAINTENANCE_FEE);

    // Get all the financeAccountList where accountMaintenanceFee is less than or
    // equal to SMALLER_ACCOUNT_MAINTENANCE_FEE
    defaultFinanceAccountShouldNotBeFound(
        "accountMaintenanceFee.lessThanOrEqual=" + SMALLER_ACCOUNT_MAINTENANCE_FEE);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByAccountMaintenanceFeeIsLessThanSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where accountMaintenanceFee is less than
    // DEFAULT_ACCOUNT_MAINTENANCE_FEE
    defaultFinanceAccountShouldNotBeFound(
        "accountMaintenanceFee.lessThan=" + DEFAULT_ACCOUNT_MAINTENANCE_FEE);

    // Get all the financeAccountList where accountMaintenanceFee is less than
    // UPDATED_ACCOUNT_MAINTENANCE_FEE
    defaultFinanceAccountShouldBeFound(
        "accountMaintenanceFee.lessThan=" + UPDATED_ACCOUNT_MAINTENANCE_FEE);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByAccountMaintenanceFeeIsGreaterThanSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where accountMaintenanceFee is greater than
    // DEFAULT_ACCOUNT_MAINTENANCE_FEE
    defaultFinanceAccountShouldNotBeFound(
        "accountMaintenanceFee.greaterThan=" + DEFAULT_ACCOUNT_MAINTENANCE_FEE);

    // Get all the financeAccountList where accountMaintenanceFee is greater than
    // SMALLER_ACCOUNT_MAINTENANCE_FEE
    defaultFinanceAccountShouldBeFound(
        "accountMaintenanceFee.greaterThan=" + SMALLER_ACCOUNT_MAINTENANCE_FEE);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByAccountNumberIsEqualToSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where accountNumber equals to
    // DEFAULT_ACCOUNT_NUMBER
    defaultFinanceAccountShouldBeFound(
        "accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER);

    // Get all the financeAccountList where accountNumber equals to
    // UPDATED_ACCOUNT_NUMBER
    defaultFinanceAccountShouldNotBeFound(
        "accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByAccountNumberIsInShouldWork() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where accountNumber in DEFAULT_ACCOUNT_NUMBER
    // or UPDATED_ACCOUNT_NUMBER
    defaultFinanceAccountShouldBeFound(
        "accountNumber.in=" +
            DEFAULT_ACCOUNT_NUMBER +
            "," +
            UPDATED_ACCOUNT_NUMBER);

    // Get all the financeAccountList where accountNumber equals to
    // UPDATED_ACCOUNT_NUMBER
    defaultFinanceAccountShouldNotBeFound(
        "accountNumber.in=" + UPDATED_ACCOUNT_NUMBER);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByAccountNumberIsNullOrNotNull() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where accountNumber is not null
    defaultFinanceAccountShouldBeFound("accountNumber.specified=true");

    // Get all the financeAccountList where accountNumber is null
    defaultFinanceAccountShouldNotBeFound("accountNumber.specified=false");
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByAccountNumberContainsSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where accountNumber contains
    // DEFAULT_ACCOUNT_NUMBER
    defaultFinanceAccountShouldBeFound(
        "accountNumber.contains=" + DEFAULT_ACCOUNT_NUMBER);

    // Get all the financeAccountList where accountNumber contains
    // UPDATED_ACCOUNT_NUMBER
    defaultFinanceAccountShouldNotBeFound(
        "accountNumber.contains=" + UPDATED_ACCOUNT_NUMBER);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByAccountNumberNotContainsSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where accountNumber does not contain
    // DEFAULT_ACCOUNT_NUMBER
    defaultFinanceAccountShouldNotBeFound(
        "accountNumber.doesNotContain=" + DEFAULT_ACCOUNT_NUMBER);

    // Get all the financeAccountList where accountNumber does not contain
    // UPDATED_ACCOUNT_NUMBER
    defaultFinanceAccountShouldBeFound(
        "accountNumber.doesNotContain=" + UPDATED_ACCOUNT_NUMBER);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByBankNumberIsEqualToSomething() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where bankNumber equals to DEFAULT_BANK_NUMBER
    defaultFinanceAccountShouldBeFound(
        "bankNumber.equals=" + DEFAULT_BANK_NUMBER);

    // Get all the financeAccountList where bankNumber equals to UPDATED_BANK_NUMBER
    defaultFinanceAccountShouldNotBeFound(
        "bankNumber.equals=" + UPDATED_BANK_NUMBER);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByBankNumberIsInShouldWork() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where bankNumber in DEFAULT_BANK_NUMBER or
    // UPDATED_BANK_NUMBER
    defaultFinanceAccountShouldBeFound(
        "bankNumber.in=" + DEFAULT_BANK_NUMBER + "," + UPDATED_BANK_NUMBER);

    // Get all the financeAccountList where bankNumber equals to UPDATED_BANK_NUMBER
    defaultFinanceAccountShouldNotBeFound(
        "bankNumber.in=" + UPDATED_BANK_NUMBER);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByBankNumberIsNullOrNotNull() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where bankNumber is not null
    defaultFinanceAccountShouldBeFound("bankNumber.specified=true");

    // Get all the financeAccountList where bankNumber is null
    defaultFinanceAccountShouldNotBeFound("bankNumber.specified=false");
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByBankNumberContainsSomething() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where bankNumber contains DEFAULT_BANK_NUMBER
    defaultFinanceAccountShouldBeFound(
        "bankNumber.contains=" + DEFAULT_BANK_NUMBER);

    // Get all the financeAccountList where bankNumber contains UPDATED_BANK_NUMBER
    defaultFinanceAccountShouldNotBeFound(
        "bankNumber.contains=" + UPDATED_BANK_NUMBER);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByBankNumberNotContainsSomething()
      throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where bankNumber does not contain
    // DEFAULT_BANK_NUMBER
    defaultFinanceAccountShouldNotBeFound(
        "bankNumber.doesNotContain=" + DEFAULT_BANK_NUMBER);

    // Get all the financeAccountList where bankNumber does not contain
    // UPDATED_BANK_NUMBER
    defaultFinanceAccountShouldBeFound(
        "bankNumber.doesNotContain=" + UPDATED_BANK_NUMBER);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByCommentIsEqualToSomething() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where comment equals to DEFAULT_COMMENT
    defaultFinanceAccountShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

    // Get all the financeAccountList where comment equals to UPDATED_COMMENT
    defaultFinanceAccountShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByCommentIsInShouldWork() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where comment in DEFAULT_COMMENT or
    // UPDATED_COMMENT
    defaultFinanceAccountShouldBeFound(
        "comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

    // Get all the financeAccountList where comment equals to UPDATED_COMMENT
    defaultFinanceAccountShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByCommentIsNullOrNotNull() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where comment is not null
    defaultFinanceAccountShouldBeFound("comment.specified=true");

    // Get all the financeAccountList where comment is null
    defaultFinanceAccountShouldNotBeFound("comment.specified=false");
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByCommentContainsSomething() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where comment contains DEFAULT_COMMENT
    defaultFinanceAccountShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

    // Get all the financeAccountList where comment contains UPDATED_COMMENT
    defaultFinanceAccountShouldNotBeFound(
        "comment.contains=" + UPDATED_COMMENT);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByCommentNotContainsSomething() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    // Get all the financeAccountList where comment does not contain DEFAULT_COMMENT
    defaultFinanceAccountShouldNotBeFound(
        "comment.doesNotContain=" + DEFAULT_COMMENT);

    // Get all the financeAccountList where comment does not contain UPDATED_COMMENT
    defaultFinanceAccountShouldBeFound(
        "comment.doesNotContain=" + UPDATED_COMMENT);
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByApplicationUserIsEqualToSomething()
      throws Exception {
    ApplicationUser applicationUser;
    if (TestUtil.findAll(em, ApplicationUser.class).isEmpty()) {
      financeAccountRepository.saveAndFlush(financeAccount);
      applicationUser = ApplicationUserResourceIT.createEntity(em);
    } else {
      applicationUser = TestUtil.findAll(em, ApplicationUser.class).get(0);
    }
    em.persist(applicationUser);
    em.flush();
    financeAccount.setApplicationUser(applicationUser);
    financeAccountRepository.saveAndFlush(financeAccount);
    Long applicationUserId = applicationUser.getId();

    // Get all the financeAccountList where applicationUser equals to
    // applicationUserId
    defaultFinanceAccountShouldBeFound(
        "applicationUserId.equals=" + applicationUserId);

    // Get all the financeAccountList where applicationUser equals to
    // (applicationUserId + 1)
    defaultFinanceAccountShouldNotBeFound(
        "applicationUserId.equals=" + (applicationUserId + 1));
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByGroupTypeIsEqualToSomething() throws Exception {
    GroupType groupType;
    if (TestUtil.findAll(em, GroupType.class).isEmpty()) {
      financeAccountRepository.saveAndFlush(financeAccount);
      groupType = GroupTypeResourceIT.createEntity(em);
    } else {
      groupType = TestUtil.findAll(em, GroupType.class).get(0);
    }
    em.persist(groupType);
    em.flush();
    financeAccount.setGroupType(groupType);
    financeAccountRepository.saveAndFlush(financeAccount);
    Long groupTypeId = groupType.getId();

    // Get all the financeAccountList where groupType equals to groupTypeId
    defaultFinanceAccountShouldBeFound("groupTypeId.equals=" + groupTypeId);

    // Get all the financeAccountList where groupType equals to (groupTypeId + 1)
    defaultFinanceAccountShouldNotBeFound(
        "groupTypeId.equals=" + (groupTypeId + 1));
  }

  @Test
  @Transactional
  void getAllFinanceAccountsByContactFinanceAccountIsEqualToSomething()
      throws Exception {
    ContactFinanceAccount contactFinanceAccount;
    if (TestUtil.findAll(em, ContactFinanceAccount.class).isEmpty()) {
      financeAccountRepository.saveAndFlush(financeAccount);
      contactFinanceAccount = ContactFinanceAccountResourceIT.createEntity(em);
    } else {
      contactFinanceAccount = TestUtil.findAll(em, ContactFinanceAccount.class).get(0);
    }
    em.persist(contactFinanceAccount);
    em.flush();
    financeAccount.addContactFinanceAccount(contactFinanceAccount);
    financeAccountRepository.saveAndFlush(financeAccount);
    Long contactFinanceAccountId = contactFinanceAccount.getId();

    // Get all the financeAccountList where contactFinanceAccount equals to
    // contactFinanceAccountId
    defaultFinanceAccountShouldBeFound(
        "contactFinanceAccountId.equals=" + contactFinanceAccountId);

    // Get all the financeAccountList where contactFinanceAccount equals to
    // (contactFinanceAccountId + 1)
    defaultFinanceAccountShouldNotBeFound(
        "contactFinanceAccountId.equals=" + (contactFinanceAccountId + 1));
  }

  /**
   * Executes the search, and checks that the default entity is returned.
   */
  private void defaultFinanceAccountShouldBeFound(String filter)
      throws Exception {
    restFinanceAccountMockMvc
        .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(
            jsonPath("$.[*].id").value(hasItem(financeAccount.getId().intValue())))
        .andExpect(
            jsonPath("$.[*].startDate")
                .value(hasItem(DEFAULT_START_DATE.toString())))
        .andExpect(
            jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
        .andExpect(
            jsonPath("$.[*].accountGiver").value(hasItem(DEFAULT_ACCOUNT_GIVER)))
        .andExpect(
            jsonPath("$.[*].accountUser").value(hasItem(DEFAULT_ACCOUNT_USER)))
        .andExpect(jsonPath("$.[*].iban").value(hasItem(DEFAULT_IBAN)))
        .andExpect(jsonPath("$.[*].bic").value(hasItem(DEFAULT_BIC)))
        .andExpect(
            jsonPath("$.[*].currentAccount").value(hasItem(DEFAULT_CURRENT_ACCOUNT)))
        .andExpect(
            jsonPath("$.[*].overdraftInterest")
                .value(hasItem(DEFAULT_OVERDRAFT_INTEREST.doubleValue())))
        .andExpect(
            jsonPath("$.[*].limit").value(hasItem(DEFAULT_LIMIT.doubleValue())))
        .andExpect(
            jsonPath("$.[*].paymentUntil")
                .value(hasItem(DEFAULT_PAYMENT_UNTIL.toString())))
        .andExpect(
            jsonPath("$.[*].cardFee").value(hasItem(DEFAULT_CARD_FEE.doubleValue())))
        .andExpect(
            jsonPath("$.[*].loanInterest")
                .value(hasItem(DEFAULT_LOAN_INTEREST.doubleValue())))
        .andExpect(
            jsonPath("$.[*].accountMaintenanceFee")
                .value(hasItem(DEFAULT_ACCOUNT_MAINTENANCE_FEE.doubleValue())))
        .andExpect(
            jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
        .andExpect(
            jsonPath("$.[*].bankNumber").value(hasItem(DEFAULT_BANK_NUMBER)))
        .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));

    // Check, that the count call also returns 1
    restFinanceAccountMockMvc
        .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().string("1"));
  }

  /**
   * Executes the search, and checks that the default entity is not returned.
   */
  private void defaultFinanceAccountShouldNotBeFound(String filter)
      throws Exception {
    restFinanceAccountMockMvc
        .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$").isEmpty());

    // Check, that the count call also returns 0
    restFinanceAccountMockMvc
        .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().string("0"));
  }

  @Test
  @Transactional
  void getNonExistingFinanceAccount() throws Exception {
    // Get the financeAccount
    restFinanceAccountMockMvc
        .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
        .andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewFinanceAccount() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    int databaseSizeBeforeUpdate = financeAccountRepository.findAll().size();

    // Update the financeAccount
    FinanceAccount updatedFinanceAccount = financeAccountRepository
        .findById(financeAccount.getId())
        .orElseThrow();
    // Disconnect from session so that the updates on updatedFinanceAccount are not
    // directly saved in db
    em.detach(updatedFinanceAccount);
    updatedFinanceAccount
        .startDate(UPDATED_START_DATE)
        .endDate(UPDATED_END_DATE)
        .accountGiver(UPDATED_ACCOUNT_GIVER)
        .accountUser(UPDATED_ACCOUNT_USER)
        .iban(UPDATED_IBAN)
        .bic(UPDATED_BIC)
        .overdraftInterest(UPDATED_OVERDRAFT_INTEREST)
        .limit(UPDATED_LIMIT)
        .paymentUntil(UPDATED_PAYMENT_UNTIL)
        .cardFee(UPDATED_CARD_FEE)
        .loanInterest(UPDATED_LOAN_INTEREST)
        .accountMaintenanceFee(UPDATED_ACCOUNT_MAINTENANCE_FEE)
        .accountNumber(UPDATED_ACCOUNT_NUMBER)
        .bankNumber(UPDATED_BANK_NUMBER);

    restFinanceAccountMockMvc
        .perform(
            put(ENTITY_API_URL_ID, updatedFinanceAccount.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(updatedFinanceAccount)))
        .andExpect(status().isOk());

    // Validate the FinanceAccount in the database
    List<FinanceAccount> financeAccountList = financeAccountRepository.findAll();
    assertThat(financeAccountList).hasSize(databaseSizeBeforeUpdate);
    FinanceAccount testFinanceAccount = financeAccountList.get(
        financeAccountList.size() - 1);
    assertThat(testFinanceAccount.getStartDate()).isEqualTo(UPDATED_START_DATE);
    assertThat(testFinanceAccount.getEndDate()).isEqualTo(UPDATED_END_DATE);
    assertThat(testFinanceAccount.getAccountGiver())
        .isEqualTo(UPDATED_ACCOUNT_GIVER);
    assertThat(testFinanceAccount.getAccountUser())
        .isEqualTo(UPDATED_ACCOUNT_USER);
    assertThat(testFinanceAccount.getIban()).isEqualTo(UPDATED_IBAN);
    assertThat(testFinanceAccount.getBic()).isEqualTo(UPDATED_BIC);
    assertThat(testFinanceAccount.getOverdraftInterest())
        .isEqualTo(UPDATED_OVERDRAFT_INTEREST);
    assertThat(testFinanceAccount.getLimit()).isEqualTo(UPDATED_LIMIT);
    assertThat(testFinanceAccount.getPaymentUntil())
        .isEqualTo(UPDATED_PAYMENT_UNTIL);
    assertThat(testFinanceAccount.getCardFee()).isEqualTo(UPDATED_CARD_FEE);
    assertThat(testFinanceAccount.getLoanInterest())
        .isEqualTo(UPDATED_LOAN_INTEREST);
    assertThat(testFinanceAccount.getAccountMaintenanceFee())
        .isEqualTo(UPDATED_ACCOUNT_MAINTENANCE_FEE);
    assertThat(testFinanceAccount.getAccountNumber())
        .isEqualTo(UPDATED_ACCOUNT_NUMBER);
    assertThat(testFinanceAccount.getBankNumber())
        .isEqualTo(UPDATED_BANK_NUMBER);
    assertThat(testFinanceAccount.getComment()).isEqualTo(UPDATED_COMMENT);
  }

  @Test
  @Transactional
  void putNonExistingFinanceAccount() throws Exception {
    int databaseSizeBeforeUpdate = financeAccountRepository.findAll().size();
    financeAccount.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restFinanceAccountMockMvc
        .perform(
            put(ENTITY_API_URL_ID, financeAccount.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(financeAccount)))
        .andExpect(status().isBadRequest());

    // Validate the FinanceAccount in the database
    List<FinanceAccount> financeAccountList = financeAccountRepository.findAll();
    assertThat(financeAccountList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchFinanceAccount() throws Exception {
    int databaseSizeBeforeUpdate = financeAccountRepository.findAll().size();
    financeAccount.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restFinanceAccountMockMvc
        .perform(
            put(ENTITY_API_URL_ID, count.incrementAndGet())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(financeAccount)))
        .andExpect(status().isBadRequest());

    // Validate the FinanceAccount in the database
    List<FinanceAccount> financeAccountList = financeAccountRepository.findAll();
    assertThat(financeAccountList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamFinanceAccount() throws Exception {
    int databaseSizeBeforeUpdate = financeAccountRepository.findAll().size();
    financeAccount.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restFinanceAccountMockMvc
        .perform(
            put(ENTITY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(financeAccount)))
        .andExpect(status().isMethodNotAllowed());

    // Validate the FinanceAccount in the database
    List<FinanceAccount> financeAccountList = financeAccountRepository.findAll();
    assertThat(financeAccountList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateFinanceAccountWithPatch() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    int databaseSizeBeforeUpdate = financeAccountRepository.findAll().size();

    // Update the financeAccount using partial update
    FinanceAccount partialUpdatedFinanceAccount = new FinanceAccount();
    partialUpdatedFinanceAccount.setId(financeAccount.getId());

    partialUpdatedFinanceAccount
        .endDate(UPDATED_END_DATE)
        .accountUser(UPDATED_ACCOUNT_USER)
        .cardFee(UPDATED_CARD_FEE)
        .loanInterest(UPDATED_LOAN_INTEREST)
        .accountNumber(UPDATED_ACCOUNT_NUMBER)
        .bankNumber(UPDATED_BANK_NUMBER);

    restFinanceAccountMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, partialUpdatedFinanceAccount.getId())
                .contentType("application/merge-patch+json")
                .content(
                    TestUtil.convertObjectToJsonBytes(partialUpdatedFinanceAccount)))
        .andExpect(status().isOk());

    // Validate the FinanceAccount in the database
    List<FinanceAccount> financeAccountList = financeAccountRepository.findAll();
    assertThat(financeAccountList).hasSize(databaseSizeBeforeUpdate);
    FinanceAccount testFinanceAccount = financeAccountList.get(
        financeAccountList.size() - 1);
    assertThat(testFinanceAccount.getStartDate()).isEqualTo(DEFAULT_START_DATE);
    assertThat(testFinanceAccount.getEndDate()).isEqualTo(UPDATED_END_DATE);
    assertThat(testFinanceAccount.getAccountGiver())
        .isEqualTo(DEFAULT_ACCOUNT_GIVER);
    assertThat(testFinanceAccount.getAccountUser())
        .isEqualTo(UPDATED_ACCOUNT_USER);
    assertThat(testFinanceAccount.getIban()).isEqualTo(DEFAULT_IBAN);
    assertThat(testFinanceAccount.getBic()).isEqualTo(DEFAULT_BIC);
    assertThat(testFinanceAccount.getOverdraftInterest())
        .isEqualTo(DEFAULT_OVERDRAFT_INTEREST);
    assertThat(testFinanceAccount.getLimit()).isEqualTo(DEFAULT_LIMIT);
    assertThat(testFinanceAccount.getPaymentUntil())
        .isEqualTo(DEFAULT_PAYMENT_UNTIL);
    assertThat(testFinanceAccount.getCardFee()).isEqualTo(UPDATED_CARD_FEE);
    assertThat(testFinanceAccount.getLoanInterest())
        .isEqualTo(UPDATED_LOAN_INTEREST);
    assertThat(testFinanceAccount.getAccountMaintenanceFee())
        .isEqualTo(DEFAULT_ACCOUNT_MAINTENANCE_FEE);
    assertThat(testFinanceAccount.getAccountNumber())
        .isEqualTo(UPDATED_ACCOUNT_NUMBER);
    assertThat(testFinanceAccount.getBankNumber())
        .isEqualTo(UPDATED_BANK_NUMBER);
    assertThat(testFinanceAccount.getComment()).isEqualTo(DEFAULT_COMMENT);
  }

  @Test
  @Transactional
  void fullUpdateFinanceAccountWithPatch() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    int databaseSizeBeforeUpdate = financeAccountRepository.findAll().size();

    // Update the financeAccount using partial update
    FinanceAccount partialUpdatedFinanceAccount = new FinanceAccount();
    partialUpdatedFinanceAccount.setId(financeAccount.getId());

    partialUpdatedFinanceAccount
        .startDate(UPDATED_START_DATE)
        .endDate(UPDATED_END_DATE)
        .accountGiver(UPDATED_ACCOUNT_GIVER)
        .accountUser(UPDATED_ACCOUNT_USER)
        .iban(UPDATED_IBAN)
        .bic(UPDATED_BIC)
        .overdraftInterest(UPDATED_OVERDRAFT_INTEREST)
        .limit(UPDATED_LIMIT)
        .paymentUntil(UPDATED_PAYMENT_UNTIL)
        .cardFee(UPDATED_CARD_FEE)
        .loanInterest(UPDATED_LOAN_INTEREST)
        .accountMaintenanceFee(UPDATED_ACCOUNT_MAINTENANCE_FEE)
        .accountNumber(UPDATED_ACCOUNT_NUMBER)
        .bankNumber(UPDATED_BANK_NUMBER);

    restFinanceAccountMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, partialUpdatedFinanceAccount.getId())
                .contentType("application/merge-patch+json")
                .content(
                    TestUtil.convertObjectToJsonBytes(partialUpdatedFinanceAccount)))
        .andExpect(status().isOk());

    // Validate the FinanceAccount in the database
    List<FinanceAccount> financeAccountList = financeAccountRepository.findAll();
    assertThat(financeAccountList).hasSize(databaseSizeBeforeUpdate);
    FinanceAccount testFinanceAccount = financeAccountList.get(
        financeAccountList.size() - 1);
    assertThat(testFinanceAccount.getStartDate()).isEqualTo(UPDATED_START_DATE);
    assertThat(testFinanceAccount.getEndDate()).isEqualTo(UPDATED_END_DATE);
    assertThat(testFinanceAccount.getAccountGiver())
        .isEqualTo(UPDATED_ACCOUNT_GIVER);
    assertThat(testFinanceAccount.getAccountUser())
        .isEqualTo(UPDATED_ACCOUNT_USER);
    assertThat(testFinanceAccount.getIban()).isEqualTo(UPDATED_IBAN);
    assertThat(testFinanceAccount.getBic()).isEqualTo(UPDATED_BIC);
    assertThat(testFinanceAccount.getOverdraftInterest())
        .isEqualTo(UPDATED_OVERDRAFT_INTEREST);
    assertThat(testFinanceAccount.getLimit()).isEqualTo(UPDATED_LIMIT);
    assertThat(testFinanceAccount.getPaymentUntil())
        .isEqualTo(UPDATED_PAYMENT_UNTIL);
    assertThat(testFinanceAccount.getCardFee()).isEqualTo(UPDATED_CARD_FEE);
    assertThat(testFinanceAccount.getLoanInterest())
        .isEqualTo(UPDATED_LOAN_INTEREST);
    assertThat(testFinanceAccount.getAccountMaintenanceFee())
        .isEqualTo(UPDATED_ACCOUNT_MAINTENANCE_FEE);
    assertThat(testFinanceAccount.getAccountNumber())
        .isEqualTo(UPDATED_ACCOUNT_NUMBER);
    assertThat(testFinanceAccount.getBankNumber())
        .isEqualTo(UPDATED_BANK_NUMBER);
    assertThat(testFinanceAccount.getComment()).isEqualTo(UPDATED_COMMENT);
  }

  @Test
  @Transactional
  void patchNonExistingFinanceAccount() throws Exception {
    int databaseSizeBeforeUpdate = financeAccountRepository.findAll().size();
    financeAccount.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restFinanceAccountMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, financeAccount.getId())
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(financeAccount)))
        .andExpect(status().isBadRequest());

    // Validate the FinanceAccount in the database
    List<FinanceAccount> financeAccountList = financeAccountRepository.findAll();
    assertThat(financeAccountList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchFinanceAccount() throws Exception {
    int databaseSizeBeforeUpdate = financeAccountRepository.findAll().size();
    financeAccount.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restFinanceAccountMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, count.incrementAndGet())
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(financeAccount)))
        .andExpect(status().isBadRequest());

    // Validate the FinanceAccount in the database
    List<FinanceAccount> financeAccountList = financeAccountRepository.findAll();
    assertThat(financeAccountList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamFinanceAccount() throws Exception {
    int databaseSizeBeforeUpdate = financeAccountRepository.findAll().size();
    financeAccount.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restFinanceAccountMockMvc
        .perform(
            patch(ENTITY_API_URL)
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(financeAccount)))
        .andExpect(status().isMethodNotAllowed());

    // Validate the FinanceAccount in the database
    List<FinanceAccount> financeAccountList = financeAccountRepository.findAll();
    assertThat(financeAccountList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteFinanceAccount() throws Exception {
    // Initialize the database
    financeAccountRepository.saveAndFlush(financeAccount);

    int databaseSizeBeforeDelete = financeAccountRepository.findAll().size();

    // Delete the financeAccount
    restFinanceAccountMockMvc
        .perform(
            delete(ENTITY_API_URL_ID, financeAccount.getId())
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<FinanceAccount> financeAccountList = financeAccountRepository.findAll();
    assertThat(financeAccountList).hasSize(databaseSizeBeforeDelete - 1);
  }
}
