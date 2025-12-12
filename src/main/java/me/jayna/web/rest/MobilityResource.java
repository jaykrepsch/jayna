package me.jayna.web.rest;

import me.jayna.domain.ApplicationUser;
import me.jayna.domain.Mobility;
import me.jayna.domain.RealEstate;
import me.jayna.repository.MobilityRepository;
import me.jayna.service.AuthorityConfigService;
import me.jayna.service.ContactMobilityService;
import me.jayna.service.ContractMobilityService;
import me.jayna.service.FinanceAccountMobilityService;
import me.jayna.service.MobilityQueryService;
import me.jayna.service.MobilityRealestateService;
import me.jayna.service.MobilityService;
import me.jayna.service.UserService;
import me.jayna.service.criteria.MobilityCriteria;
import me.jayna.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
 * REST controller for managing {@link me.jayna.domain.Mobility}.
 */
@RestController
@RequestMapping("/api")
public class MobilityResource {

  private final Logger log = LoggerFactory.getLogger(MobilityResource.class);

  private static final String ENTITY_NAME = "mobility";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final MobilityService mobilityService;

  private final MobilityRepository mobilityRepository;

  private final MobilityQueryService mobilityQueryService;

  private final AuthorityConfigService authorityConfigService;

  private final UserService userService;

  private final ContactMobilityService contactMobilityService;

  private final ContractMobilityService contractMobilityService;

  private final MobilityRealestateService mobilityRealestateService;

  private final FinanceAccountMobilityService financeAccountMobilityService;

  public MobilityResource(
      MobilityService mobilityService,
      MobilityRepository mobilityRepository,
      MobilityQueryService mobilityQueryService,
      AuthorityConfigService authorityConfigService,
      UserService userService,
      ContactMobilityService contactMobilityService,
      ContractMobilityService contractMobilityService,
      MobilityRealestateService mobilityRealestateService,
      FinanceAccountMobilityService financeAccountMobilityService) {
    this.mobilityService = mobilityService;
    this.mobilityRepository = mobilityRepository;
    this.mobilityQueryService = mobilityQueryService;
    this.authorityConfigService = authorityConfigService;
    this.userService = userService;
    this.contactMobilityService = contactMobilityService;
    this.contractMobilityService = contractMobilityService;
    this.mobilityRealestateService = mobilityRealestateService;
    this.financeAccountMobilityService = financeAccountMobilityService;
  }

  /**
   * {@code POST  /mobilities} : Create a new mobility.
   *
   * @param mobility the mobility to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
   *         body the new mobility, or with status {@code 400 (Bad Request)} if
   *         the mobility has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/mobilities")
  public ResponseEntity<Mobility> createMobility(@RequestBody Mobility mobility) throws URISyntaxException {
    log.debug("REST request to save Mobility : {}", mobility);
    if (mobility.getId() != null) {
      throw new BadRequestAlertException("A new mobility cannot already have an ID", ENTITY_NAME, "idexists");
    }

    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
    mobility.setApplicationUser(applicationUser);

    if (StringUtils.isBlank(mobility.getLabel())) {
      mobility.setLabel(mobility.getGroupType().getName());
    }

    Mobility result = mobilityService.save(mobility);

    contactMobilityService.saveContactMobilityRelationships(mobility.getContactMobilities(), result);
    contractMobilityService.saveContractMobilityRelationships(mobility.getContractMobilities(), result);
    mobilityRealestateService.saveMobilityRealestateRelationships(mobility.getMobilityRealestates(), result);
    financeAccountMobilityService.saveFinanceAccountMobilityRelationships(mobility.getFinanceaccountMobilities(),
        result);

    return ResponseEntity
        .created(new URI("/api/mobilities/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  /**
   * {@code PUT  /mobilities/:id} : Updates an existing mobility.
   *
   * @param id       the id of the mobility to save.
   * @param mobility the mobility to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the updated mobility,
   *         or with status {@code 400 (Bad Request)} if the mobility is not
   *         valid,
   *         or with status {@code 500 (Internal Server Error)} if the mobility
   *         couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/mobilities/{id}")
  public ResponseEntity<Mobility> updateMobility(
      @PathVariable(value = "id", required = false) final Long id,
      @RequestBody Mobility mobility) throws URISyntaxException {
    log.debug("REST request to update Mobility : {}, {}", id, mobility);
    if (mobility.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, mobility.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!mobilityRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
    if (!mobilityService.findOne(id).orElseThrow().getApplicationUser().getId().equals(applicationUser.getId())) {
      throw new BadRequestAlertException("Not allowed", ENTITY_NAME, "notallowed");
    }
    mobility.setApplicationUser(applicationUser);

    contactMobilityService.updateContactMobilityRelationships(mobility.getContactMobilities(), mobility);
    contractMobilityService.updateContractMobilityRelationships(mobility.getContractMobilities(), mobility);
    mobilityRealestateService.updateMobilityRealestateRelationships(mobility.getMobilityRealestates(), mobility);
    financeAccountMobilityService.updateFinanceAccountMobilityRelationships(mobility.getFinanceaccountMobilities(),
        mobility);

    Mobility result = mobilityService.update(mobility);
    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mobility.getId().toString()))
        .body(result);
  }

  /**
   * {@code PATCH  /mobilities/:id} : Partial updates given fields of an existing
   * mobility, field will ignore if it is null
   *
   * @param id       the id of the mobility to save.
   * @param mobility the mobility to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the updated mobility,
   *         or with status {@code 400 (Bad Request)} if the mobility is not
   *         valid,
   *         or with status {@code 404 (Not Found)} if the mobility is not found,
   *         or with status {@code 500 (Internal Server Error)} if the mobility
   *         couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/mobilities/{id}", consumes = { "application/json", "application/merge-patch+json" })
  public ResponseEntity<Mobility> partialUpdateMobility(
      @PathVariable(value = "id", required = false) final Long id,
      @RequestBody Mobility mobility) throws URISyntaxException {
    log.debug("REST request to partial update Mobility partially : {}, {}", id, mobility);
    if (mobility.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, mobility.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!mobilityRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<Mobility> result = mobilityService.partialUpdate(mobility);

    return ResponseUtil.wrapOrNotFound(
        result,
        HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mobility.getId().toString()));
  }

  /**
   * {@code GET  /mobilities} : get all the mobilities.
   *
   * @param pageable the pagination information.
   * @param criteria the criteria which the requested entities should match.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
   *         of mobilities in body.
   */
  @GetMapping("/mobilities")
  public ResponseEntity<List<Mobility>> getAllMobilities(
      MobilityCriteria criteria,
      @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
    log.debug("REST request to get Mobilities by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));

    criteria.setApplicationUserId(userService.getApplicationUserFilter());

    Page<Mobility> page = mobilityQueryService.findByCriteria(criteria, pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(),
        page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /mobilities/count} : count all the mobilities.
   *
   * @param criteria the criteria which the requested entities should match.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
   *         in body.
   */
  @GetMapping("/mobilities/count")
  public ResponseEntity<Long> countMobilities(MobilityCriteria criteria) {
    log.debug("REST request to count Mobilities by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
    return ResponseEntity.ok().body(mobilityQueryService.countByCriteria(criteria));
  }

  /**
   * {@code GET  /mobilities/:id} : get the "id" mobility.
   *
   * @param id the id of the mobility to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the mobility, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/mobilities/{id}")
  public ResponseEntity<Mobility> getMobility(@PathVariable Long id,
      @RequestParam(defaultValue = "true") boolean relations) {

    log.debug("REST request to get Mobility : {}", id);

    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();

    Optional<Mobility> mobility = Optional.empty();
    if (relations) {
      mobility = mobilityRepository.findByIdAndApplicationUserWithGraph(id,
          applicationUser);
    } else {
      mobility = mobilityRepository.findWithoutRelationsByIdAndApplicationUser(id,
          applicationUser);
    }

    return ResponseUtil.wrapOrNotFound(mobility);
  }

  /**
   * {@code DELETE  /mobilities/:id} : delete the "id" mobility.
   *
   * @param id the id of the mobility to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/mobilities/{id}")
  public ResponseEntity<Void> deleteMobility(@PathVariable Long id) {
    log.debug("REST request to delete Mobility : {}", id);
    mobilityService.delete(id);
    return ResponseEntity
        .noContent()
        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
        .build();
  }

  @GetMapping("/mobilities/check")
  @ResponseStatus(HttpStatus.OK)
  public void checkIfUserIsAllowedToAdd() {
    if (!authorityConfigService.isUserAllowedToAddMobility()) {
      throw new BadRequestAlertException(
          "The user reached the maximum entities to add.",
          ENTITY_NAME,
          "insufficient_role");
    }
  }

  /**
   * {@code GET  /mobilities/all} : get all mobilities for the current user with full relationships.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
   *         of mobilities with full relationships in body.
   */
  @GetMapping("/mobilities/all")
  public ResponseEntity<List<Mobility>> getAllMobilitiesWithRelations() {
    log.debug("REST request to get all Mobilities with full relationships for current user");

    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
    List<Mobility> mobilities = mobilityService.findAllWithCategoryRelationsByApplicationUser(applicationUser);

    return ResponseEntity.ok().body(mobilities);
  }

  /**
   * {@code GET  /mobilities/overview} : get all mobilities for the current user optimized for overview display.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
   *         of mobilities with minimal relationships for overview in body.
   */
  @GetMapping("/mobilities/overview")
  public ResponseEntity<List<Mobility>> getAllMobilitiesForOverview() {
    log.debug("REST request to get all Mobilities for overview for current user");

    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
    List<Mobility> mobilities = mobilityRepository.findAllByApplicationUserForOverview(applicationUser);

    return ResponseEntity.ok().body(mobilities);
  }
}
