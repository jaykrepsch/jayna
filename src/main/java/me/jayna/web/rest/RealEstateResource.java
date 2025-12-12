package me.jayna.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import me.jayna.domain.ApplicationUser;
import me.jayna.domain.Contact;
import me.jayna.domain.ContactRealestate;
import me.jayna.domain.Contract;
import me.jayna.domain.ContractRealestate;
import me.jayna.domain.FinanceAccount;
import me.jayna.domain.FinanceAccountRealestate;
import me.jayna.domain.Mobility;
import me.jayna.domain.MobilityRealestate;
import me.jayna.domain.RealEstate;
import me.jayna.domain.RealEstateRealEstate;
import me.jayna.repository.ContactRepository;
import me.jayna.repository.ContractRepository;
import me.jayna.repository.FinanceAccountRepository;
import me.jayna.repository.MobilityRepository;
import me.jayna.repository.RealEstateRepository;
import me.jayna.service.RealEstateRealEstateService;
import me.jayna.service.AuthorityConfigService;
import me.jayna.service.ContactRealestateService;
import me.jayna.service.ContractRealestateService;
import me.jayna.service.FinanceAccountRealestateService;
import me.jayna.service.MobilityRealestateService;
import me.jayna.service.RealEstateQueryService;
import me.jayna.service.RealEstateService;
import me.jayna.service.UserService;
import me.jayna.service.criteria.RealEstateCriteria;
import me.jayna.service.dto.NewRelationDTO;
import me.jayna.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link me.jayna.domain.RealEstate}.
 */
@RestController
@RequestMapping("/api")
public class RealEstateResource {

  private final Logger log = LoggerFactory.getLogger(RealEstateResource.class);

  private static final String ENTITY_NAME = "realestate";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final RealEstateService realEstateService;

  private final RealEstateRepository realEstateRepository;

  private final RealEstateQueryService realEstateQueryService;

  private final UserService userService;

  private final ContractRealestateService contractRealestateService;

  private final AuthorityConfigService authorityConfigService;

  private final ContactRealestateService contactRealestateService;

  private final FinanceAccountRealestateService financeAccountRealestateService;

  private final MobilityRealestateService mobilityRealestateService;
  
  private final RealEstateRealEstateService realEstateRealEstateService;
  
  private final ContractRepository contractRepository;
  private final ContactRepository contactRepository;
  private final FinanceAccountRepository financeAccountRepository;
  private final MobilityRepository mobilityRepository;
  
  private final com.fasterxml.jackson.databind.ObjectMapper objectMapper;

  public RealEstateResource(
      RealEstateService realEstateService,
      RealEstateRepository realEstateRepository,
      RealEstateQueryService realEstateQueryService,
      UserService userService,
      ContractRealestateService contractRealestateService,
      AuthorityConfigService authorityConfigService,
      ContactRealestateService contactRealestateService,
      FinanceAccountRealestateService financeAccountRealestateService,
      MobilityRealestateService mobilityRealestateService,
      RealEstateRealEstateService realEstateRealEstateService,
      ContractRepository contractRepository,
      ContactRepository contactRepository,
      FinanceAccountRepository financeAccountRepository,
      MobilityRepository mobilityRepository,
      com.fasterxml.jackson.databind.ObjectMapper objectMapper) {

    this.realEstateService = realEstateService;
    this.realEstateRepository = realEstateRepository;
    this.realEstateQueryService = realEstateQueryService;
    this.userService = userService;
    this.contractRealestateService = contractRealestateService;
    this.authorityConfigService = authorityConfigService;
    this.contactRealestateService = contactRealestateService;
    this.financeAccountRealestateService = financeAccountRealestateService;
    this.mobilityRealestateService = mobilityRealestateService;
    this.realEstateRealEstateService = realEstateRealEstateService;
    this.contractRepository = contractRepository;
    this.contactRepository = contactRepository;
    this.financeAccountRepository = financeAccountRepository;
    this.mobilityRepository = mobilityRepository;
    this.objectMapper = objectMapper;
  }

  /**
   * {@code POST  /realestates} : Create a new realEstate.
   *
   * @param realEstate the realEstate to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
   *         body the new realEstate, or with status {@code 400 (Bad Request)} if
   *         the realEstate has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/realestates")
  @Transactional
  public ResponseEntity<RealEstate> createRealEstate(
      @RequestBody RealEstate realEstate) throws URISyntaxException {
    log.debug("REST request to save RealEstate : {}", realEstate);
    if (realEstate.getId() != null) {
      throw new BadRequestAlertException(
          "A new realEstate cannot already have an ID",
          ENTITY_NAME,
          "idexists");
    }

    if (!authorityConfigService.isUserAllowedToAddRealEstate()) {
      throw new BadRequestAlertException(
          "The user reached the maximum entities to add.",
          ENTITY_NAME,
          "insufficient_role");
    }

    realEstate.setApplicationUser(userService.getApplicationUser().orElseThrow());
    RealEstate result = realEstateService.save(realEstate);

    contractRealestateService.saveContractRealestateRelationships(realEstate.getContractRealestates(), result);
    contactRealestateService.saveContactRealestateRelationships(realEstate.getContactRealestates(), result);
    financeAccountRealestateService.saveFinanceAccountRealestateRelationships(realEstate.getFinanceaccountRealestates(),
        result);
    mobilityRealestateService.saveMobilityRealestateRelationships(realEstate.getMobilityRealestates(), result);

    return ResponseEntity
        .created(new URI("/api/realestates/" + result.getId()))
        .headers(
            HeaderUtil.createEntityCreationAlert(
                applicationName,
                true,
                ENTITY_NAME,
                result.getId().toString()))
        .body(result);
  }

  /**
   * {@code PUT  /realestates/:id} : Updates an existing realEstate.
   *
   * @param id         the id of the realEstate to save.
   * @param realEstate the realEstate to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the updated realEstate,
   *         or with status {@code 400 (Bad Request)} if the realEstate is not
   *         valid,
   *         or with status {@code 500 (Internal Server Error)} if the realEstate
   *         couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/realestates/{id}")
  public ResponseEntity<RealEstate> updateRealEstate(
      @PathVariable(value = "id", required = false) final Long id,
      @RequestBody RealEstate realEstate) throws URISyntaxException {
    log.debug("REST request to update RealEstate : {}, {}", id, realEstate);
    if (realEstate.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, realEstate.getId())) {
      throw new BadRequestAlertException(
          "Invalid ID",
          ENTITY_NAME,
          "idinvalid");
    }
    if (!realEstateRepository.existsById(id)) {
      throw new BadRequestAlertException(
          "Entity not found",
          ENTITY_NAME,
          "idnotfound");
    }

    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
    if (!realEstateService.findOne(id).orElseThrow().getApplicationUser().getId().equals(applicationUser.getId())) {
      throw new BadRequestAlertException("Not allowed", ENTITY_NAME, "notallowed");
    }
    realEstate.setApplicationUser(userService.getApplicationUser().orElseThrow());

    contractRealestateService.updateContractRealestateRelationships(realEstate.getContractRealestates(), realEstate);
    contactRealestateService.updateContactRealestateRelationships(realEstate.getContactRealestates(), realEstate);
    financeAccountRealestateService.updateFinanceAccountRealestateRelationships(
        realEstate.getFinanceaccountRealestates(),
        realEstate);
    mobilityRealestateService.updateMobilityRealestateRelationships(realEstate.getMobilityRealestates(), realEstate);

    RealEstate result = realEstateService.update(realEstate);
    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .headers(
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                true,
                ENTITY_NAME,
                realEstate.getId().toString()))
        .body(result);
  }

  /**
   * {@code PATCH  /realestates/:id} : Partial updates given fields of an
   * existing realEstate, field will ignore if it is null
   *
   * @param id         the id of the realEstate to save.
   * @param realEstate the realEstate to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the updated realEstate,
   *         or with status {@code 400 (Bad Request)} if the realEstate is not
   *         valid,
   *         or with status {@code 404 (Not Found)} if the realEstate is not
   *         found,
   *         or with status {@code 500 (Internal Server Error)} if the realEstate
   *         couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/realestates/{id}", consumes = { "application/json", "application/merge-patch+json" })
  public ResponseEntity<RealEstate> partialUpdateRealEstate(
      @PathVariable(value = "id", required = false) final Long id,
      @RequestBody RealEstate realEstate) throws URISyntaxException {
    log.debug(
        "REST request to partial update RealEstate partially : {}, {}",
        id,
        realEstate);
    if (realEstate.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, realEstate.getId())) {
      throw new BadRequestAlertException(
          "Invalid ID",
          ENTITY_NAME,
          "idinvalid");
    }

    if (!realEstateRepository.existsById(id)) {
      throw new BadRequestAlertException(
          "Entity not found",
          ENTITY_NAME,
          "idnotfound");
    }

    Optional<RealEstate> result = realEstateService.partialUpdate(realEstate);

    return ResponseUtil.wrapOrNotFound(
        result,
        HeaderUtil.createEntityUpdateAlert(
            applicationName,
            true,
            ENTITY_NAME,
            realEstate.getId().toString()));
  }

  /**
   * {@code GET  /realestates} : get all the realEstates.
   *
   * @param pageable the pagination information.
   * @param criteria the criteria which the requested entities should match.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
   *         of realEstates in body.
   */
  @GetMapping("/realestates")
  @Transactional
  public ResponseEntity<List<RealEstate>> getAllRealEstates(
      RealEstateCriteria criteria,
      @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
    log.debug(
        "REST request to get RealEstates by criteria: {}",
        criteria.toString().replaceAll("[\n\r\t]", "_"));

    criteria.setApplicationUserId(userService.getApplicationUserFilter());

    Page<RealEstate> page = realEstateQueryService.findByCriteria(
        criteria,
        pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
        ServletUriComponentsBuilder.fromCurrentRequest(),
        page);

    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /realestates/count} : count all the realEstates.
   *
   * @param criteria the criteria which the requested entities should match.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
   *         in body.
   */
  @GetMapping("/realestates/count")
  public ResponseEntity<Long> countRealEstates(RealEstateCriteria criteria) {
    log.debug(
        "REST request to count RealEstates by criteria: {}",
        criteria.toString().replaceAll("[\n\r\t]", "_"));
    return ResponseEntity
        .ok()
        .body(realEstateQueryService.countByCriteria(criteria));
  }

  /**
   * {@code GET  /realestates/:id} : get the "id" realEstate.
   *
   * @param id the id of the realEstate to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the realEstate, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/realestates/{id}")
  @Transactional
  public ResponseEntity<RealEstate> getRealEstate(@PathVariable Long id,
      @RequestParam(defaultValue = "true") boolean relations) {

    log.debug("REST request to get RealEstate : {}", id);
    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();

    Optional<RealEstate> realEstateOpt = realEstateService.findOne(id);

    // Prüfe Berechtigung
    if (realEstateOpt.isPresent()) {
      RealEstate realEstate = realEstateOpt.get();
      if (!realEstate.getApplicationUser().getId().equals(applicationUser.getId())) {
        throw new BadRequestAlertException("Not allowed", ENTITY_NAME, "notallowed");
      }
      
      if (relations) {
        // Lade alle Verknüpfungen für die RealEstate
        Set<ContractRealestate> contractRealestates = contractRealestateService.findAllByRealEstate(realEstate);
        for (ContractRealestate cr : contractRealestates) {
          if (cr.getContract() != null && cr.getContract().getId() != null) {
            // Lade vollständige Contract-Daten
            cr.setContract(contractRepository.findById(cr.getContract().getId()).orElse(cr.getContract()));
          }
        }
        realEstate.setContractRealestates(contractRealestates);
        
        Set<ContactRealestate> contactRealestates = contactRealestateService.findAllByRealestate(realEstate);
        for (ContactRealestate cr : contactRealestates) {
          if (cr.getContact() != null && cr.getContact().getId() != null) {
            // Lade vollständige Contact-Daten
            cr.setContact(contactRepository.findById(cr.getContact().getId()).orElse(cr.getContact()));
          }
        }
        realEstate.setContactRealestates(contactRealestates);
        
        Set<FinanceAccountRealestate> financeAccountRealestates = financeAccountRealestateService.findAllByRealestate(realEstate);
        for (FinanceAccountRealestate far : financeAccountRealestates) {
          if (far.getFinanceaccount() != null && far.getFinanceaccount().getId() != null) {
            // Lade vollständige FinanceAccount-Daten
            far.setFinanceaccount(financeAccountRepository.findById(far.getFinanceaccount().getId()).orElse(far.getFinanceaccount()));
          }
        }
        realEstate.setFinanceaccountRealestates(financeAccountRealestates);
        
        Set<MobilityRealestate> mobilityRealestates = mobilityRealestateService.findAllByRealestate(realEstate);
        for (MobilityRealestate mr : mobilityRealestates) {
          if (mr.getMobility() != null && mr.getMobility().getId() != null) {
            // Lade vollständige Mobility-Daten
            mr.setMobility(mobilityRepository.findById(mr.getMobility().getId()).orElse(mr.getMobility()));
          }
        }
        realEstate.setMobilityRealestates(mobilityRealestates);
        
        // Temporär auskommentiert wegen Datenbankproblem
        // List<RealEstateRealEstate> realEstateRealEstatesList = realEstateRealEstateService.findAllByRealEstate(realEstate);
        // for (RealEstateRealEstate rer : realEstateRealEstatesList) {
        //   if (rer.getRelatedRealEstate() != null && rer.getRelatedRealEstate().getId() != null) {
        //     // Lade vollständige RealEstate-Daten
        //     rer.setRelatedRealEstate(realEstateRepository.findById(rer.getRelatedRealEstate().getId()).orElse(rer.getRelatedRealEstate()));
        //   }
        // }
        // Set<RealEstateRealEstate> realEstateRealEstates = new HashSet<>(realEstateRealEstatesList);
        // realEstate.setRealEstateRealEstates(realEstateRealEstates);
      }
      
      return ResponseEntity.ok().body(realEstate);
    }

    return ResponseUtil.wrapOrNotFound(realEstateOpt);
  }

  /**
   * {@code DELETE  /realestates/:id} : delete the "id" realEstate.
   *
   * @param id the id of the realEstate to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/realestates/{id}")
  public ResponseEntity<Void> deleteRealEstate(@PathVariable Long id) {
    log.debug("REST request to delete RealEstate : {}", id);
    RealEstate realEstate = realEstateRepository
        .findById(id)
        .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
    if (!realEstate.getApplicationUser().getId().equals(applicationUser.getId())) {
      throw new BadRequestAlertException("Not allowed", ENTITY_NAME, "notallowed");
    }
    
    // Lösche alle Beziehungen mit Fehlerbehandlung
    try {
      contractRealestateService.deleteRelationships(realEstate);
    } catch (Exception e) {
      log.warn("Error deleting contract relationships for RealEstate {}: {}", id, e.getMessage());
    }
    
    try {
      contactRealestateService.deleteRelationships(realEstate);
    } catch (Exception e) {
      log.warn("Error deleting contact relationships for RealEstate {}: {}", id, e.getMessage());
    }
    
    try {
      financeAccountRealestateService.deleteRelationships(realEstate);
    } catch (Exception e) {
      log.warn("Error deleting finance account relationships for RealEstate {}: {}", id, e.getMessage());
    }
    
    try {
      mobilityRealestateService.deleteRelationships(realEstate);
    } catch (Exception e) {
      log.warn("Error deleting mobility relationships for RealEstate {}: {}", id, e.getMessage());
    }
    
    try {
      realEstateRealEstateService.deleteAllByRealEstate(realEstate);
    } catch (Exception e) {
      log.warn("Error deleting real estate relationships for RealEstate {}: {}", id, e.getMessage());
    }
    
    try {
      realEstateRealEstateService.deleteAllByRelatedRealEstate(realEstate);
    } catch (Exception e) {
      log.warn("Error deleting related real estate relationships for RealEstate {}: {}", id, e.getMessage());
    }
    
    realEstateService.delete(id);
    return ResponseEntity
        .noContent()
        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
        .build();
  }

  @GetMapping("/realestates/check")
  @ResponseStatus(HttpStatus.OK)
  public void checkIfUserIsAllowedToAdd() {
    if (!authorityConfigService.isUserAllowedToAddRealEstate()) {
      throw new BadRequestAlertException(
          "The user reached the maximum entities to add.",
          ENTITY_NAME,
          "insufficient_role");
    }
  }

  /**
   * {@code DELETE  /realestates/{id}/relate} : Delete a relation from a realEstate.
   *
   * @param id the id of the realEstate to delete relation from.
   * @param deleteRelation the relation to delete.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated realEstate.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @DeleteMapping("/realestates/{id}/relate")
  public ResponseEntity<RealEstate> deleteRelation(@PathVariable(value = "id") final Long id, @RequestBody NewRelationDTO deleteRelation)
      throws URISyntaxException {
    log.debug("REST request to delete relation for RealEstate : {}", id);
    
    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
    RealEstate realEstate = realEstateRepository
        .findById(id)
        .orElseThrow(() -> new BadRequestAlertException("RealEstate not found", ENTITY_NAME, "realestatenotfound"));
    
    if (!realEstate.getApplicationUser().getId().equals(applicationUser.getId())) {
      throw new BadRequestAlertException("Not allowed", ENTITY_NAME, "notallowed");
    }

    String entityName = deleteRelation.getEntityName();
    Object relationData = deleteRelation.getRelationData();

    try {
      switch (entityName) {
        case "contract":
          ContractRealestate cr = objectMapper.convertValue(relationData, ContractRealestate.class);
          if (cr.getId() != null) {
            try {
              contractRealestateService.delete(cr.getId());
              log.debug("Successfully deleted contract relation with ID: {}", cr.getId());
            } catch (Exception e) {
              log.warn("Failed to delete contract relation with ID: {} - may not exist: {}", cr.getId(), e.getMessage());
            }
          }
          break;
        case "contact":
          ContactRealestate cr2 = objectMapper.convertValue(relationData, ContactRealestate.class);
          if (cr2.getId() != null) {
            try {
              contactRealestateService.delete(cr2.getId());
              log.debug("Successfully deleted contact relation with ID: {}", cr2.getId());
            } catch (Exception e) {
              log.warn("Failed to delete contact relation with ID: {} - may not exist: {}", cr2.getId(), e.getMessage());
            }
          }
          break;
        case "financeaccount":
          FinanceAccountRealestate far = objectMapper.convertValue(relationData, FinanceAccountRealestate.class);
          if (far.getId() != null) {
            try {
              financeAccountRealestateService.delete(far.getId());
              log.debug("Successfully deleted financeaccount relation with ID: {}", far.getId());
            } catch (Exception e) {
              log.warn("Failed to delete financeaccount relation with ID: {} - may not exist: {}", far.getId(), e.getMessage());
            }
          }
          break;
        case "mobility":
          MobilityRealestate mr = objectMapper.convertValue(relationData, MobilityRealestate.class);
          if (mr.getId() != null) {
            try {
              mobilityRealestateService.delete(mr.getId());
              log.debug("Successfully deleted mobility relation with ID: {}", mr.getId());
            } catch (Exception e) {
              log.warn("Failed to delete mobility relation with ID: {} - may not exist: {}", mr.getId(), e.getMessage());
            }
          }
          break;
        case "realestate":
          RealEstateRealEstate rer = objectMapper.convertValue(relationData, RealEstateRealEstate.class);
          if (rer.getId() != null) {
            try {
              realEstateRealEstateService.delete(rer.getId());
              log.debug("Successfully deleted realestate relation with ID: {}", rer.getId());
            } catch (Exception e) {
              log.warn("Failed to delete realestate relation with ID: {} - may not exist: {}", rer.getId(), e.getMessage());
            }
          }
          break;
        default:
          log.warn("Unknown relation entity name: {}", entityName);
          break;
      }
    } catch (Exception e) {
      log.error("Error during relation deletion: {}", e.getMessage());
    }

    return getRealEstate(id, true);
  }

  /**
   * {@code POST  /realestates/{id}/relate} : Add a relation to a realEstate.
   *
   * @param id the id of the realEstate to add relation to.
   * @param newRelation the relation to add.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated realEstate.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/realestates/{id}/relate")
  public ResponseEntity<RealEstate> addRelation(@PathVariable(value = "id") final Long id, @RequestBody NewRelationDTO newRelation)
      throws URISyntaxException {
    log.debug("REST request to add relation to RealEstate : {}, {}", id, newRelation);

    final RealEstate realEstate = realEstateRepository
        .findById(id)
        .orElseThrow(() -> new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull"));

    String entityName = newRelation.getEntityName();
    Object relationData = newRelation.getRelationData();

    switch (entityName) {
      case "contract":
        ContractRealestate cr = objectMapper.convertValue(relationData, ContractRealestate.class);
        cr.setRealestate(realEstate);
        // Stelle sicher, dass das contract gesetzt ist
        if (cr.getContract() != null && cr.getContract().getId() != null) {
          Contract contract = contractRepository.findById(cr.getContract().getId())
              .orElseThrow(() -> new BadRequestAlertException("Contract not found", ENTITY_NAME, "contractnotfound"));
          cr.setContract(contract);
        } else {
          throw new BadRequestAlertException("Contract ID is required", ENTITY_NAME, "contractidrequired");
        }
        contractRealestateService.save(cr);
        break;
      case "contact":
        ContactRealestate cr2 = objectMapper.convertValue(relationData, ContactRealestate.class);
        cr2.setRealestate(realEstate);
        // Stelle sicher, dass das contact gesetzt ist
        if (cr2.getContact() != null && cr2.getContact().getId() != null) {
          Contact contact = contactRepository.findById(cr2.getContact().getId())
              .orElseThrow(() -> new BadRequestAlertException("Contact not found", ENTITY_NAME, "contactnotfound"));
          cr2.setContact(contact);
        } else {
          throw new BadRequestAlertException("Contact ID is required", ENTITY_NAME, "contactidrequired");
        }
        contactRealestateService.save(cr2);
        break;
      case "financeaccount":
        FinanceAccountRealestate far = objectMapper.convertValue(relationData, FinanceAccountRealestate.class);
        far.setRealestate(realEstate);
        // Stelle sicher, dass das financeaccount gesetzt ist
        if (far.getFinanceaccount() != null && far.getFinanceaccount().getId() != null) {
          FinanceAccount financeAccount = financeAccountRepository.findById(far.getFinanceaccount().getId())
              .orElseThrow(() -> new BadRequestAlertException("FinanceAccount not found", ENTITY_NAME, "financeaccountnotfound"));
          far.setFinanceaccount(financeAccount);
        } else {
          throw new BadRequestAlertException("FinanceAccount ID is required", ENTITY_NAME, "financeaccountidrequired");
        }
        financeAccountRealestateService.save(far);
        break;
      case "mobility":
        MobilityRealestate mr = objectMapper.convertValue(relationData, MobilityRealestate.class);
        mr.setRealestate(realEstate);
        // Stelle sicher, dass das mobility gesetzt ist
        if (mr.getMobility() != null && mr.getMobility().getId() != null) {
          Mobility mobility = mobilityRepository.findById(mr.getMobility().getId())
              .orElseThrow(() -> new BadRequestAlertException("Mobility not found", ENTITY_NAME, "mobilitynotfound"));
          mr.setMobility(mobility);
        } else {
          throw new BadRequestAlertException("Mobility ID is required", ENTITY_NAME, "mobilityidrequired");
        }
        mobilityRealestateService.save(mr);
        break;
      case "realestate":
        RealEstateRealEstate rer = objectMapper.convertValue(relationData, RealEstateRealEstate.class);
        rer.setRealestate(realEstate);
        // Stelle sicher, dass das relatedRealEstate gesetzt ist
        if (rer.getRelatedRealEstate() != null && rer.getRelatedRealEstate().getId() != null) {
          RealEstate relatedRealEstate = realEstateRepository.findById(rer.getRelatedRealEstate().getId())
              .orElseThrow(() -> new BadRequestAlertException("Related RealEstate not found", ENTITY_NAME, "relatedrealestatenotfound"));
          rer.setRelatedRealEstate(relatedRealEstate);
        } else {
          throw new BadRequestAlertException("Related RealEstate ID is required", ENTITY_NAME, "relatedrealestateidrequired");
        }
        realEstateRealEstateService.save(rer);
        break;
      default:
        throw new BadRequestAlertException("Unknown relation entity name: " + entityName, ENTITY_NAME, "invalidrelation");
    }

    return getRealEstate(id, true);
  }

  /**
   * {@code GET  /realestates/all} : get all realEstates for the current user with full relationships.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
   *         of realEstates with full relationships in body.
   */
  @GetMapping("/realestates/all")
  public ResponseEntity<List<RealEstate>> getAllRealEstatesWithRelations() {
    log.debug("REST request to get all RealEstates with full relationships for current user");

    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
    List<RealEstate> realEstates = realEstateService.findAllWithCategoryRelationsByApplicationUser(applicationUser);

    return ResponseEntity.ok().body(realEstates);
  }


}
