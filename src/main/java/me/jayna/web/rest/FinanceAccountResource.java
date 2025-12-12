package me.jayna.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import me.jayna.domain.ApplicationUser;
import me.jayna.domain.FinanceAccount;
import me.jayna.domain.RealEstate;
import me.jayna.repository.FinanceAccountRepository;
import me.jayna.service.AuthorityConfigService;
import me.jayna.service.ContactFinanceAccountService;
import me.jayna.service.ContractFinanceAccountService;
import me.jayna.service.FinanceAccountMobilityService;
import me.jayna.service.FinanceAccountQueryService;
import me.jayna.service.FinanceAccountRealestateService;
import me.jayna.service.FinanceAccountService;
import me.jayna.service.UserService;
import me.jayna.service.criteria.FinanceAccountCriteria;
import me.jayna.web.rest.errors.BadRequestAlertException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link me.jayna.domain.FinanceAccount}.
 */
@RestController
@RequestMapping("/api")
public class FinanceAccountResource {

  private final Logger log = LoggerFactory.getLogger(
      FinanceAccountResource.class);

  private static final String ENTITY_NAME = "financeAccount";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final FinanceAccountService financeAccountService;

  private final FinanceAccountRepository financeAccountRepository;

  private final FinanceAccountQueryService financeAccountQueryService;

  private final AuthorityConfigService authorityConfigService;

  private final UserService userService;

  private final ContactFinanceAccountService contactFinanceAccountService;

  private final FinanceAccountMobilityService financeAccountMobilityService;

  private final FinanceAccountRealestateService financeAccountRealestateService;

  private final ContractFinanceAccountService contractFinanceAccountService;

  public FinanceAccountResource(
      FinanceAccountService financeAccountService,
      FinanceAccountRepository financeAccountRepository,
      FinanceAccountQueryService financeAccountQueryService,
      AuthorityConfigService authorityConfigService,
      UserService userService,
      ContactFinanceAccountService contactFinanceAccountService,
      FinanceAccountMobilityService financeAccountMobilityService,
      FinanceAccountRealestateService financeAccountRealestateService,
      ContractFinanceAccountService contractFinanceAccountService) {
    this.financeAccountService = financeAccountService;
    this.financeAccountRepository = financeAccountRepository;
    this.financeAccountQueryService = financeAccountQueryService;
    this.authorityConfigService = authorityConfigService;
    this.userService = userService;
    this.contactFinanceAccountService = contactFinanceAccountService;
    this.financeAccountMobilityService = financeAccountMobilityService;
    this.financeAccountRealestateService = financeAccountRealestateService;
    this.contractFinanceAccountService = contractFinanceAccountService;
  }

  /**
   * {@code POST  /finance-accounts} : Create a new financeAccount.
   *
   * @param financeAccount the financeAccount to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
   *         body the new financeAccount, or with status {@code 400 (Bad Request)}
   *         if the financeAccount has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/finance-accounts")
  public ResponseEntity<FinanceAccount> createFinanceAccount(
      @RequestBody FinanceAccount financeAccount) throws URISyntaxException {
    log.debug("REST request to save FinanceAccount : {}", financeAccount);
    if (financeAccount.getId() != null) {
      throw new BadRequestAlertException(
          "A new financeAccount cannot already have an ID",
          ENTITY_NAME,
          "idexists");
    }

    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
    financeAccount.setApplicationUser(applicationUser);

    if (StringUtils.isBlank(financeAccount.getLabel())) {
      financeAccount.setLabel(financeAccount.getGroupType().getName());
    }

    FinanceAccount result = financeAccountService.save(financeAccount);

    contactFinanceAccountService.saveContactFinanceAccountRelationships(financeAccount.getContactFinanceAccounts(),
        result);
    financeAccountMobilityService.saveFinanceAccountMobilityRelationships(financeAccount.getFinanceaccountMobilities(),
        result);
    financeAccountRealestateService
        .saveFinanceAccountRealestateRelationships(financeAccount.getFinanceaccountRealestates(), result);
    contractFinanceAccountService.saveContractFinanceAccountRelationships(financeAccount.getContractFinanceAccounts(),
        result);

    return ResponseEntity
        .created(new URI("/api/finance-accounts/" + result.getId()))
        .headers(
            HeaderUtil.createEntityCreationAlert(
                applicationName,
                true,
                ENTITY_NAME,
                result.getId().toString()))
        .body(result);
  }

  /**
   * {@code PUT  /finance-accounts/:id} : Updates an existing financeAccount.
   *
   * @param id             the id of the financeAccount to save.
   * @param financeAccount the financeAccount to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the updated financeAccount,
   *         or with status {@code 400 (Bad Request)} if the financeAccount is not
   *         valid,
   *         or with status {@code 500 (Internal Server Error)} if the
   *         financeAccount couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/finance-accounts/{id}")
  public ResponseEntity<FinanceAccount> updateFinanceAccount(
      @PathVariable(value = "id", required = false) final Long id,
      @RequestBody FinanceAccount financeAccount) throws URISyntaxException {
    log.debug(
        "REST request to update FinanceAccount : {}, {}",
        id,
        financeAccount);
    if (financeAccount.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, financeAccount.getId())) {
      throw new BadRequestAlertException(
          "Invalid ID",
          ENTITY_NAME,
          "idinvalid");
    }

    if (!financeAccountRepository.existsById(id)) {
      throw new BadRequestAlertException(
          "Entity not found",
          ENTITY_NAME,
          "idnotfound");
    }

    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
    if (!financeAccountService.findOne(id).orElseThrow().getApplicationUser().getId().equals(applicationUser.getId())) {
      throw new BadRequestAlertException("Not allowed", ENTITY_NAME, "notallowed");
    }
    financeAccount.setApplicationUser(applicationUser);

    contactFinanceAccountService.updateContactFinanceAccountRelationships(financeAccount.getContactFinanceAccounts(),
        financeAccount);
    financeAccountMobilityService
        .updateFinanceAccountMobilityRelationships(financeAccount.getFinanceaccountMobilities(), financeAccount);
    financeAccountRealestateService
        .updateFinanceAccountRealestateRelationships(financeAccount.getFinanceaccountRealestates(), financeAccount);
    contractFinanceAccountService.updateContractFinanceAccountRelationships(financeAccount.getContractFinanceAccounts(),
        financeAccount);

    if (StringUtils.isBlank(financeAccount.getLabel())) {
      financeAccount.setLabel(financeAccount.getGroupType().getName());
    }

    FinanceAccount result = financeAccountService.update(financeAccount);
    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .headers(
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                true,
                ENTITY_NAME,
                financeAccount.getId().toString()))
        .body(result);
  }

  /**
   * {@code PATCH  /finance-accounts/:id} : Partial updates given fields of an
   * existing financeAccount, field will ignore if it is null
   *
   * @param id             the id of the financeAccount to save.
   * @param financeAccount the financeAccount to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the updated financeAccount,
   *         or with status {@code 400 (Bad Request)} if the financeAccount is not
   *         valid,
   *         or with status {@code 404 (Not Found)} if the financeAccount is not
   *         found,
   *         or with status {@code 500 (Internal Server Error)} if the
   *         financeAccount couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/finance-accounts/{id}", consumes = { "application/json", "application/merge-patch+json" })
  public ResponseEntity<FinanceAccount> partialUpdateFinanceAccount(
      @PathVariable(value = "id", required = false) final Long id,
      @RequestBody FinanceAccount financeAccount) throws URISyntaxException {
    log.debug(
        "REST request to partial update FinanceAccount partially : {}, {}",
        id,
        financeAccount);
    if (financeAccount.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, financeAccount.getId())) {
      throw new BadRequestAlertException(
          "Invalid ID",
          ENTITY_NAME,
          "idinvalid");
    }

    if (!financeAccountRepository.existsById(id)) {
      throw new BadRequestAlertException(
          "Entity not found",
          ENTITY_NAME,
          "idnotfound");
    }

    Optional<FinanceAccount> result = financeAccountService.partialUpdate(
        financeAccount);

    return ResponseUtil.wrapOrNotFound(
        result,
        HeaderUtil.createEntityUpdateAlert(
            applicationName,
            true,
            ENTITY_NAME,
            financeAccount.getId().toString()));
  }

  /**
   * {@code GET  /finance-accounts} : get all the financeAccounts.
   *
   * @param pageable the pagination information.
   * @param criteria the criteria which the requested entities should match.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
   *         of financeAccounts in body.
   */
  @GetMapping("/finance-accounts")
  public ResponseEntity<List<FinanceAccount>> getAllFinanceAccounts(
      FinanceAccountCriteria criteria,
      @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
    log.debug(
        "REST request to get FinanceAccounts by criteria: {}",
        criteria.toString().replaceAll("[\n\r\t]", "_"));

    criteria.setApplicationUserId(userService.getApplicationUserFilter());

    Page<FinanceAccount> page = financeAccountQueryService.findByCriteria(
        criteria,
        pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
        ServletUriComponentsBuilder.fromCurrentRequest(),
        page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /finance-accounts/count} : count all the financeAccounts.
   *
   * @param criteria the criteria which the requested entities should match.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
   *         in body.
   */
  @GetMapping("/finance-accounts/count")
  public ResponseEntity<Long> countFinanceAccounts(
      FinanceAccountCriteria criteria) {
    log.debug(
        "REST request to count FinanceAccounts by criteria: {}",
        criteria.toString().replaceAll("[\n\r\t]", "_"));
    return ResponseEntity
        .ok()
        .body(financeAccountQueryService.countByCriteria(criteria));
  }

  /**
   * {@code GET  /finance-accounts/:id} : get the "id" financeAccount.
   *
   * @param id the id of the financeAccount to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the financeAccount, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/finance-accounts/{id}")
  public ResponseEntity<FinanceAccount> getFinanceAccount(
      @PathVariable Long id, @RequestParam(defaultValue = "true") boolean relations) {

    log.debug("REST request to get FinanceAccount : {}", id);
    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();

    Optional<FinanceAccount> financeAccount = Optional.empty();
    if (relations) {
      financeAccount = financeAccountRepository.findByIdAndApplicationUserWithGraph(id,
          applicationUser);
    } else {
      financeAccount = financeAccountRepository.findWithoutRelationsByIdAndApplicationUser(id,
          applicationUser);
    }

    return ResponseUtil.wrapOrNotFound(financeAccount);
  }

  /**
   * {@code DELETE  /finance-accounts/:id} : delete the "id" financeAccount.
   *
   * @param id the id of the financeAccount to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/finance-accounts/{id}")
  public ResponseEntity<Void> deleteFinanceAccount(@PathVariable Long id) {
    log.debug("REST request to delete FinanceAccount : {}", id);
    financeAccountService.delete(id);
    return ResponseEntity
        .noContent()
        .headers(
            HeaderUtil.createEntityDeletionAlert(
                applicationName,
                true,
                ENTITY_NAME,
                id.toString()))
        .build();
  }

  @GetMapping("/finance-accounts/check")
  @ResponseStatus(HttpStatus.OK)
  public void checkIfUserIsAllowedToAdd() {
    if (!authorityConfigService.isUserAllowedToAddFinanceAccount()) {
      throw new BadRequestAlertException(
          "The user reached the maximum entities to add.",
          ENTITY_NAME,
          "insufficient_role");
    }
  }

  /**
   * {@code GET  /finance-accounts/all} : get all financeAccounts for the current user with full relationships.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
   *         of financeAccounts with full relationships in body.
   */
  @GetMapping("/finance-accounts/all")
  public ResponseEntity<List<FinanceAccount>> getAllFinanceAccountsWithRelations() {
    log.debug("REST request to get all FinanceAccounts with full relationships for current user");

    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
    List<FinanceAccount> financeAccounts = financeAccountService.findAllWithCategoryRelationsByApplicationUser(applicationUser);

    return ResponseEntity.ok().body(financeAccounts);
  }

  /**
   * {@code GET  /finance-accounts/overview} : get all financeAccounts for the current user optimized for overview display.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
   *         of financeAccounts with minimal relationships for overview in body.
   */
  @GetMapping("/finance-accounts/overview")
  public ResponseEntity<List<FinanceAccount>> getAllFinanceAccountsForOverview() {
    log.debug("REST request to get all FinanceAccounts for overview for current user");

    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
    List<FinanceAccount> financeAccounts = financeAccountRepository.findAllByApplicationUserForOverview(applicationUser);

    return ResponseEntity.ok().body(financeAccounts);
  }


}
