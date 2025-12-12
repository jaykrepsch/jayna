package me.jayna.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import me.jayna.domain.GroupType;
import me.jayna.domain.SubCategoryGroup;
import me.jayna.domain.User;
import me.jayna.domain.enumeration.CreationType;
import me.jayna.repository.GroupTypeRepository;
import me.jayna.service.GroupTypeService;
import me.jayna.service.SubCategoryGroupService;
import me.jayna.service.UserService;
import me.jayna.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link me.jayna.domain.GroupType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GroupTypeResource {

  private final Logger log = LoggerFactory.getLogger(GroupTypeResource.class);

  private static final String ENTITY_NAME = "groupType";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final GroupTypeService groupTypeService;

  private final GroupTypeRepository groupTypeRepository;

  private final SubCategoryGroupService subCategoryGroupService;

  private final UserService userService;

  public GroupTypeResource(
      GroupTypeService groupTypeService,
      GroupTypeRepository groupTypeRepository,
      SubCategoryGroupService subCategoryGroupService,
      UserService userService) {
    this.groupTypeService = groupTypeService;
    this.groupTypeRepository = groupTypeRepository;
    this.subCategoryGroupService = subCategoryGroupService;
    this.userService = userService;
  }

  /**
   * {@code POST  /group-types} : Create a new groupType.
   *
   * @param groupType the groupType to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
   *         body the new groupType, or with status {@code 400 (Bad Request)} if
   *         the groupType has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/group-types")
  public ResponseEntity<GroupType> createGroupType(@RequestBody GroupType groupType) throws URISyntaxException {
    log.debug("REST request to save GroupType : {}", groupType);
    if (groupType.getId() != null) {
      throw new BadRequestAlertException("A new groupType cannot already have an ID", ENTITY_NAME, "idexists");
    }

    final SubCategoryGroup subCategoryGroup = subCategoryGroupService.findOne(groupType.getSubCategoryGroup().getId())
        .orElseThrow();
    final User user = userService.getUserWithAuthorities().orElseThrow();

    // groupType.setFormName(groupType.getName().toLowerCase());
    groupType.setCreatedDate(LocalDate.now());
    // Setze den translationKey automatisch, falls nicht vorhanden, um i18n zu ermöglichen
    if (groupType.getTranslationKey() == null || groupType.getTranslationKey().isBlank()) {
      final String entityName = subCategoryGroup.getSubCategory().getCategory().getEntityName();
      final String slug = toSlug(groupType.getName());
      groupType.setTranslationKey("jaynaApp." + entityName + ".group-type." + slug);
    }
    groupType.setSubCategoryGroup(subCategoryGroup);
    groupType.setCreatedBy(user.getLogin());
    groupType.setCreationType(CreationType.CUSTOM);
    groupType.setEntityName(subCategoryGroup.getSubCategory().getCategory().getEntityName());

    GroupType result = groupTypeService.save(groupType);

    subCategoryGroup.getGroupTypes().add(result);

    return ResponseEntity
        .created(new URI("/api/group-types/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  private String toSlug(String input) {
    if (input == null) return "";
    // Entferne Akzente/Diakritika und normalisiere
    String normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
      .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    // Erzeuge kebab-case Slug (nur a-z, 0-9 und '-')
    String slug = normalized
      .toLowerCase()
      .replaceAll("[^a-z0-9\\s-]", "")
      .replaceAll("\\s+", "-")
      .replaceAll("-+", "-")
      .replaceAll("^-|-$", "");
    return slug;
  }

  /**
   * {@code PUT  /group-types/:id} : Updates an existing groupType.
   *
   * @param id        the id of the groupType to save.
   * @param groupType the groupType to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the updated groupType,
   *         or with status {@code 400 (Bad Request)} if the groupType is not
   *         valid,
   *         or with status {@code 500 (Internal Server Error)} if the groupType
   *         couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/group-types/{id}")
  public ResponseEntity<GroupType> updateGroupType(
      @PathVariable(value = "id", required = false) final Long id,
      @RequestBody GroupType groupType) throws URISyntaxException {
    log.debug("REST request to update GroupType : {}, {}", id, groupType);
    if (groupType.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, groupType.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!groupTypeRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    GroupType result = groupTypeService.update(groupType);
    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groupType.getId().toString()))
        .body(result);
  }

  /**
   * {@code PATCH  /group-types/:id} : Partial updates given fields of an existing
   * groupType, field will ignore if it is null
   *
   * @param id        the id of the groupType to save.
   * @param groupType the groupType to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the updated groupType,
   *         or with status {@code 400 (Bad Request)} if the groupType is not
   *         valid,
   *         or with status {@code 404 (Not Found)} if the groupType is not found,
   *         or with status {@code 500 (Internal Server Error)} if the groupType
   *         couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/group-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
  public ResponseEntity<GroupType> partialUpdateGroupType(
      @PathVariable(value = "id", required = false) final Long id,
      @RequestBody GroupType groupType) throws URISyntaxException {
    log.debug("REST request to partial update GroupType partially : {}, {}", id, groupType);
    if (groupType.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, groupType.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!groupTypeRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<GroupType> result = groupTypeService.partialUpdate(groupType);

    return ResponseUtil.wrapOrNotFound(
        result,
        HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groupType.getId().toString()));
  }

  /**
   * {@code GET  /group-types} : get all the groupTypes.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
   *         of groupTypes in body.
   */
  @GetMapping("/group-types")
  public List<GroupType> getAllGroupTypes(@RequestParam(name = "parentId", required = false) Long parentId) {
    log.debug("REST request to get all GroupTypes");

    // Removed noisy stdout output of working directory

    final User user = userService.getUserWithAuthorities().orElseThrow();
    final List<String> allowedCreatedByNames = Arrays.asList("system", user.getLogin());

    if (parentId == null) {
      return groupTypeRepository.findAllByCreatedByInOrderByNameAsc(allowedCreatedByNames);
    }

    final Optional<SubCategoryGroup> maybeSubCategoryGroup = subCategoryGroupService.findOne(parentId);
    if (maybeSubCategoryGroup.isEmpty()) {
      throw new BadRequestAlertException("Entity not found", "SubCategoryGroup", "idnotfound");
    }

    final List<GroupType> groupTypes = groupTypeRepository
        .findAllBySubCategoryGroupAndCreatedByInOrderByNameAsc(maybeSubCategoryGroup.orElseThrow(),
            allowedCreatedByNames);

    return groupTypes;
  }

  /**
   * {@code GET  /group-types/:id} : get the "id" groupType.
   *
   * @param id the id of the groupType to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the groupType, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/group-types/{id}")
  public ResponseEntity<GroupType> getGroupType(@PathVariable Long id) {
    log.debug("REST request to get GroupType : {}", id);
    Optional<GroupType> groupType = groupTypeService.findOne(id);
    return ResponseUtil.wrapOrNotFound(groupType);
  }

  /**
   * {@code DELETE  /group-types/:id} : delete the "id" groupType.
   *
   * @param id the id of the groupType to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/group-types/{id}")
  public ResponseEntity<Void> deleteGroupType(@PathVariable Long id) {
    log.debug("REST request to delete GroupType : {}", id);
    groupTypeService.delete(id);
    return ResponseEntity
        .noContent()
        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
        .build();
  }

  /**
   * {@code GET  /group-types/:entityName} : get the "entityName" groupType.
   *
   * @param id the id of the groupType to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the groupType, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/group-types/entity-name/{entityName}")
  public List<GroupType> getGroupTypesByEntityName(@PathVariable String entityName) {
    log.debug("REST request to get GroupType : {}", entityName);
    return groupTypeService.findByEntityName(entityName);
  }
}
