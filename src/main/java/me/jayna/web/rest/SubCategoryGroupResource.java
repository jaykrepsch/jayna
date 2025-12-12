package me.jayna.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import me.jayna.domain.ApplicationUser;
import me.jayna.domain.SubCategory;
import me.jayna.domain.SubCategoryGroup;
import me.jayna.domain.User;
import me.jayna.domain.enumeration.CreationType;
import me.jayna.repository.SubCategoryGroupRepository;
import me.jayna.service.SubCategoryGroupService;
import me.jayna.service.SubCategoryService;
import me.jayna.service.UserService;
import me.jayna.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link me.jayna.domain.SubCategoryGroup}.
 */
@RestController
@RequestMapping("/api")
public class SubCategoryGroupResource {

  private final Logger log = LoggerFactory.getLogger(SubCategoryGroupResource.class);

  private static final String ENTITY_NAME = "subCategoryGroup";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final SubCategoryGroupService subCategoryGroupService;

  private final SubCategoryGroupRepository subCategoryGroupRepository;

  private final SubCategoryService subCategoryService;

  private final UserService userService;

  public SubCategoryGroupResource(
      SubCategoryGroupService subCategoryGroupService,
      SubCategoryGroupRepository subCategoryGroupRepository,
      SubCategoryService subCategoryService,
      UserService userService) {
    this.subCategoryGroupService = subCategoryGroupService;
    this.subCategoryGroupRepository = subCategoryGroupRepository;
    this.subCategoryService = subCategoryService;
    this.userService = userService;
  }

  /**
   * {@code POST  /sub-category-groups} : Create a new subCategoryGroup.
   *
   * @param subCategoryGroup the subCategoryGroup to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
   *         body the new subCategoryGroup, or with status
   *         {@code 400 (Bad Request)} if the subCategoryGroup has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/sub-category-groups")
  public ResponseEntity<SubCategoryGroup> createSubCategoryGroup(@RequestBody SubCategoryGroup subCategoryGroup)
      throws URISyntaxException {
    log.debug("REST request to save SubCategoryGroup : {}", subCategoryGroup);
    if (subCategoryGroup.getId() != null) {
      throw new BadRequestAlertException("A new subCategoryGroup cannot already have an ID", ENTITY_NAME, "idexists");
    }

    final SubCategory subCategory = subCategoryService.findOne(subCategoryGroup.getSubCategory().getId()).orElseThrow();
    final User user = userService.getUserWithAuthorities().orElseThrow();

    subCategoryGroup.setCreatedDate(LocalDate.now());
    // Setze den translationKey automatisch, falls nicht vorhanden, um i18n zu ermöglichen
    if (subCategoryGroup.getTranslationKey() == null || subCategoryGroup.getTranslationKey().isBlank()) {
      final String entityName = subCategory.getCategory().getEntityName();
      final String slug = toSlug(subCategoryGroup.getName());
      subCategoryGroup.setTranslationKey("jaynaApp." + entityName + ".sub-category-group." + slug);
    }
    subCategoryGroup.setSubCategory(subCategory);
    subCategoryGroup.setCreatedBy(user.getLogin());
    subCategoryGroup.setCreationType(CreationType.CUSTOM);

    SubCategoryGroup result = subCategoryGroupService.save(subCategoryGroup);
    return ResponseEntity
        .created(new URI("/api/sub-category-groups/" + result.getId()))
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
   * {@code PUT  /sub-category-groups/:id} : Updates an existing subCategoryGroup.
   *
   * @param id               the id of the subCategoryGroup to save.
   * @param subCategoryGroup the subCategoryGroup to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the updated subCategoryGroup,
   *         or with status {@code 400 (Bad Request)} if the subCategoryGroup is
   *         not valid,
   *         or with status {@code 500 (Internal Server Error)} if the
   *         subCategoryGroup couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/sub-category-groups/{id}")
  public ResponseEntity<SubCategoryGroup> updateSubCategoryGroup(
      @PathVariable(value = "id", required = false) final Long id,
      @RequestBody SubCategoryGroup subCategoryGroup) throws URISyntaxException {
    log.debug("REST request to update SubCategoryGroup : {}, {}", id, subCategoryGroup);
    if (subCategoryGroup.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, subCategoryGroup.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!subCategoryGroupRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    SubCategoryGroup result = subCategoryGroupService.update(subCategoryGroup);
    return ResponseEntity
        .ok()
        .headers(
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subCategoryGroup.getId().toString()))
        .body(result);
  }

  /**
   * {@code PATCH  /sub-category-groups/:id} : Partial updates given fields of an
   * existing subCategoryGroup, field will ignore if it is null
   *
   * @param id               the id of the subCategoryGroup to save.
   * @param subCategoryGroup the subCategoryGroup to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the updated subCategoryGroup,
   *         or with status {@code 400 (Bad Request)} if the subCategoryGroup is
   *         not valid,
   *         or with status {@code 404 (Not Found)} if the subCategoryGroup is not
   *         found,
   *         or with status {@code 500 (Internal Server Error)} if the
   *         subCategoryGroup couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/sub-category-groups/{id}", consumes = { "application/json", "application/merge-patch+json" })
  public ResponseEntity<SubCategoryGroup> partialUpdateSubCategoryGroup(
      @PathVariable(value = "id", required = false) final Long id,
      @RequestBody SubCategoryGroup subCategoryGroup) throws URISyntaxException {
    log.debug("REST request to partial update SubCategoryGroup partially : {}, {}", id, subCategoryGroup);
    if (subCategoryGroup.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, subCategoryGroup.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!subCategoryGroupRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<SubCategoryGroup> result = subCategoryGroupService.partialUpdate(subCategoryGroup);

    return ResponseUtil.wrapOrNotFound(
        result,
        HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subCategoryGroup.getId().toString()));
  }

  // /**
  // * {@code GET /sub-category-groups} : get all the subCategoryGroups.
  // *
  // * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the
  // list of subCategoryGroups in body.
  // */
  // @GetMapping("/sub-category-groups")
  // public List<SubCategoryGroup> getAllSubCategoryGroups() {
  // log.debug("REST request to get all SubCategoryGroups");
  // return subCategoryGroupService.findAll();
  // }

  /**
   * {@code GET  /sub-category-groups} : get all the subCategoryGroups.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
   *         of subCategoryGroups in body.
   */
  @GetMapping("/sub-category-groups")
  public List<SubCategoryGroup> getAllSubCategoryGroups(
      @RequestParam(name = "parentId", required = false) Long parentId,
      @RequestParam(name = "childId", required = false) Long childId) {
    log.debug("REST request to get all SubCategoryGroups");

    final User user = userService.getUserWithAuthorities().orElseThrow();
    final List<String> allowedCreatedByNames = Arrays.asList("system", user.getLogin());

    if (parentId == null) {
      return subCategoryGroupRepository.findAllByCreatedByInOrderByNameAsc(allowedCreatedByNames);
    }

    final Optional<SubCategory> maybeSubCategory = subCategoryService.findOne(parentId);
    if (maybeSubCategory.isEmpty()) {
      throw new BadRequestAlertException("Entity not found", "subCategory", "idnotfound");
    }

    final List<SubCategoryGroup> result = subCategoryGroupRepository
        .findAllBySubCategoryAndCreatedByInOrderByNameAsc(maybeSubCategory.orElseThrow(), allowedCreatedByNames);

    return result;
  }

  /**
   * {@code GET  /sub-category-groups/:id} : get the "id" subCategoryGroup.
   *
   * @param id the id of the subCategoryGroup to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the subCategoryGroup, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/sub-category-groups/{id}")
  public ResponseEntity<SubCategoryGroup> getSubCategoryGroup(@PathVariable Long id) {
    log.debug("REST request to get SubCategoryGroup : {}", id);
    Optional<SubCategoryGroup> subCategoryGroup = subCategoryGroupService.findOne(id);
    return ResponseUtil.wrapOrNotFound(subCategoryGroup);
  }

  /**
   * {@code DELETE  /sub-category-groups/:id} : delete the "id" subCategoryGroup.
   *
   * @param id the id of the subCategoryGroup to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/sub-category-groups/{id}")
  public ResponseEntity<Void> deleteSubCategoryGroup(@PathVariable Long id) {
    log.debug("REST request to delete SubCategoryGroup : {}", id);
    subCategoryGroupService.delete(id);
    return ResponseEntity
        .noContent()
        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
        .build();
  }
}
