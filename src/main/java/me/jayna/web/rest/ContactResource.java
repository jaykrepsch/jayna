package me.jayna.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import me.jayna.domain.ApplicationUser;
import me.jayna.domain.Contact;
import me.jayna.domain.RealEstate;
import me.jayna.repository.ContactRepository;
import me.jayna.service.ContactContractService;
import me.jayna.service.ContactFinanceAccountService;
import me.jayna.service.ContactMobilityService;
import me.jayna.service.ContactQueryService;
import me.jayna.service.ContactRealestateService;
import me.jayna.service.ContactService;
import me.jayna.service.UserService;
import me.jayna.service.criteria.ContactCriteria;
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
 * REST controller for managing {@link me.jayna.domain.Contact}.
 */
@RestController
@RequestMapping("/api")
public class ContactResource {

  private final Logger log = LoggerFactory.getLogger(ContactResource.class);

  private static final String ENTITY_NAME = "contact";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final ContactService contactService;

  private final ContactRepository contactRepository;

  private final ContactQueryService contactQueryService;

  private final UserService userService;

  private final ContactFinanceAccountService contactFinanceAccountService;

  private final ContactContractService contactContractService;

  private final ContactMobilityService contactMobilityService;

  private final ContactRealestateService contactRealestateService;

  public ContactResource(
      ContactService contactService,
      ContactRepository contactRepository,
      ContactQueryService contactQueryService,
      UserService userService,
      ContactFinanceAccountService contactFinanceAccountService,
      ContactContractService contactContractService,
      ContactMobilityService contactMobilityService,
      ContactRealestateService contactRealestateService) {
    this.contactService = contactService;
    this.contactRepository = contactRepository;
    this.contactQueryService = contactQueryService;
    this.userService = userService;
    this.contactFinanceAccountService = contactFinanceAccountService;
    this.contactContractService = contactContractService;
    this.contactMobilityService = contactMobilityService;
    this.contactRealestateService = contactRealestateService;
  }

  /**
   * {@code POST  /contacts} : Create a new contact.
   *
   * @param contact the contact to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
   *         body the new contact, or with status {@code 400 (Bad Request)} if the
   *         contact has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/contacts")
  public ResponseEntity<Contact> createContact(@RequestBody Contact contact)
      throws URISyntaxException {
    log.debug("REST request to save Contact : {}", contact);
    if (contact.getId() != null) {
      throw new BadRequestAlertException(
          "A new contact cannot already have an ID",
          ENTITY_NAME,
          "idexists");
    }

    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
    contact.setApplicationUser(applicationUser);

    if (StringUtils.isBlank(contact.getLabel())) {
      contact.setLabel(contact.getFirstName() + " " + contact.getSurName());
    }

    Contact result = contactService.save(contact);

    contactFinanceAccountService.saveContactFinanceAccountRelationships(contact.getContactFinanceAccounts(), result);
    contactContractService.saveContactContractRelationships(contact.getContactContracts(), result);
    contactMobilityService.saveContactMobilityRelationships(contact.getContactMobilities(), result);
    contactRealestateService.saveContactRealestateRelationships(contact.getContactRealestates(), result);

    return ResponseEntity
        .created(new URI("/api/contacts/" + result.getId()))
        .headers(
            HeaderUtil.createEntityCreationAlert(
                applicationName,
                true,
                ENTITY_NAME,
                result.getId().toString()))
        .body(result);
  }

  /**
   * {@code PUT  /contacts/:id} : Updates an existing contact.
   *
   * @param id      the id of the contact to save.
   * @param contact the contact to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the updated contact,
   *         or with status {@code 400 (Bad Request)} if the contact is not valid,
   *         or with status {@code 500 (Internal Server Error)} if the contact
   *         couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/contacts/{id}")
  public ResponseEntity<Contact> updateContact(
      @PathVariable(value = "id", required = false) final Long id,
      @RequestBody Contact contact) throws URISyntaxException {
    log.debug("REST request to update Contact : {}, {}", id, contact);
    if (contact.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, contact.getId())) {
      throw new BadRequestAlertException(
          "Invalid ID",
          ENTITY_NAME,
          "idinvalid");
    }

    if (!contactRepository.existsById(id)) {
      throw new BadRequestAlertException(
          "Entity not found",
          ENTITY_NAME,
          "idnotfound");
    }

    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
    if (!contactService.findOne(id).orElseThrow().getApplicationUser().getId().equals(applicationUser.getId())) {
      throw new BadRequestAlertException("Not allowed", ENTITY_NAME, "notallowed");
    }
    contact.setApplicationUser(applicationUser);

    contactFinanceAccountService.updateContactFinanceAccountRelationships(contact.getContactFinanceAccounts(), contact);
    contactContractService.updateContactContractRelationships(contact.getContactContracts(), contact);
    contactMobilityService.updateContactMobilityRelationships(contact.getContactMobilities(), contact);
    contactRealestateService.updateContactRealestateRelationships(contact.getContactRealestates(), contact);

    if (StringUtils.isBlank(contact.getLabel())) {
      contact.setLabel(contact.getFirstName() + " " + contact.getSurName());
    }

    Contact result = contactService.update(contact);
    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .headers(
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                true,
                ENTITY_NAME,
                contact.getId().toString()))
        .body(result);
  }

  /**
   * {@code PATCH  /contacts/:id} : Partial updates given fields of an existing
   * contact, field will ignore if it is null
   *
   * @param id      the id of the contact to save.
   * @param contact the contact to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the updated contact,
   *         or with status {@code 400 (Bad Request)} if the contact is not valid,
   *         or with status {@code 404 (Not Found)} if the contact is not found,
   *         or with status {@code 500 (Internal Server Error)} if the contact
   *         couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/contacts/{id}", consumes = { "application/json", "application/merge-patch+json" })
  public ResponseEntity<Contact> partialUpdateContact(
      @PathVariable(value = "id", required = false) final Long id,
      @RequestBody Contact contact) throws URISyntaxException {
    log.debug(
        "REST request to partial update Contact partially : {}, {}",
        id,
        contact);
    if (contact.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, contact.getId())) {
      throw new BadRequestAlertException(
          "Invalid ID",
          ENTITY_NAME,
          "idinvalid");
    }

    if (!contactRepository.existsById(id)) {
      throw new BadRequestAlertException(
          "Entity not found",
          ENTITY_NAME,
          "idnotfound");
    }

    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
    if (!contactService.findOne(id).orElseThrow().getApplicationUser().getId().equals(applicationUser.getId())) {
      throw new BadRequestAlertException("Not allowed", ENTITY_NAME, "notallowed");
    }

    Optional<Contact> result = contactService.partialUpdate(contact);

    return ResponseUtil.wrapOrNotFound(
        result,
        HeaderUtil.createEntityUpdateAlert(
            applicationName,
            true,
            ENTITY_NAME,
            contact.getId().toString()));
  }

  /**
   * {@code GET  /contacts} : get all the contacts.
   *
   * @param pageable the pagination information.
   * @param criteria the criteria which the requested entities should match.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
   *         of contacts in body.
   */
  @GetMapping("/contacts")
  public ResponseEntity<List<Contact>> getAllContacts(
      ContactCriteria criteria,
      @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
    log.debug(
        "REST request to get Contacts by criteria: {}",
        criteria.toString().replaceAll("[\n\r\t]", "_"));

    criteria.setApplicationUserId(userService.getApplicationUserFilter());

    Page<Contact> page = contactQueryService.findByCriteria(criteria, pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
        ServletUriComponentsBuilder.fromCurrentRequest(),
        page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /contacts/count} : count all the contacts.
   *
   * @param criteria the criteria which the requested entities should match.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
   *         in body.
   */
  @GetMapping("/contacts/count")
  public ResponseEntity<Long> countContacts(ContactCriteria criteria) {
    log.debug(
        "REST request to count Contacts by criteria: {}",
        criteria.toString().replaceAll("[\n\r\t]", "_"));

    criteria.setApplicationUserId(userService.getApplicationUserFilter());

    return ResponseEntity
        .ok()
        .body(contactQueryService.countByCriteria(criteria));
  }

  /**
   * {@code GET  /contacts/:id} : get the "id" contact.
   *
   * @param id the id of the contact to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the contact, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/contacts/{id}")
  public ResponseEntity<Contact> getContact(@PathVariable Long id,
      @RequestParam(defaultValue = "true") boolean relations) {

    log.debug("REST request to get Contact : {}", id);

    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();

    Optional<Contact> contact = Optional.empty();
    if (relations) {
      contact = contactRepository.findWithRelationsByIdAndApplicationUser(id,
          applicationUser);
    } else {
      contact = contactRepository.findWithoutRelationsByIdAndApplicationUser(id,
          applicationUser);
    }

    return ResponseUtil.wrapOrNotFound(contact);
  }

  /**
   * {@code DELETE  /contacts/:id} : delete the "id" contact.
   *
   * @param id the id of the contact to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/contacts/{id}")
  public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
    log.debug("REST request to delete Contact : {}", id);

    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
    if (!contactService.findOne(id).orElseThrow().getApplicationUser().getId().equals(applicationUser.getId())) {
      throw new BadRequestAlertException("Not allowed", ENTITY_NAME, "notallowed");
    }

    contactService.delete(id);
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

  @GetMapping("/contacts/check")
  @ResponseStatus(HttpStatus.OK)
  public void checkIfUserIsAllowedToAdd() {
    // if (!authorityConfigService.isUserAllowedToAddContract()) {
    // throw new BadRequestAlertException(
    // "The user reached the maximum entities to add.",
    // ENTITY_NAME,
    // "insufficient_role");
    // }
  }

  /**
   * {@code GET  /contacts/all} : get all contacts for the current user with full relationships.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
   *         of contacts with full relationships in body.
   */
  @GetMapping("/contacts/all")
  public ResponseEntity<List<Contact>> getAllContactsWithRelations() {
    log.debug("REST request to get all Contacts with full relationships for current user");

    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
    List<Contact> contacts = contactService.findAllByApplicationUser(applicationUser);

    return ResponseEntity.ok().body(contacts);
  }

  /**
   * {@code GET  /contacts/all/overview} : get all contacts for the current user optimized for overview.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
   *         of contacts optimized for overview in body.
   */
  @GetMapping("/contacts/all/overview")
  public ResponseEntity<List<Contact>> getAllContactsForOverview() {
    log.debug("REST request to get all Contacts optimized for overview for current user");

    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
    List<Contact> contacts = contactService.findAllByApplicationUserForOverview(applicationUser);

    return ResponseEntity.ok().body(contacts);
  }
}
