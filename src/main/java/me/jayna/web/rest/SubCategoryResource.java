package me.jayna.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.Normalizer;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import me.jayna.domain.Category;
import me.jayna.domain.SubCategory;
import me.jayna.repository.SubCategoryRepository;
import me.jayna.service.CategoryService;
import me.jayna.service.SubCategoryService;
import me.jayna.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link me.jayna.domain.SubCategory}.
 */
@RestController
@RequestMapping("/api")
public class SubCategoryResource {

  private final Logger log = LoggerFactory.getLogger(SubCategoryResource.class);

  private static final String ENTITY_NAME = "subCategory";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final SubCategoryService subCategoryService;

  private final SubCategoryRepository subCategoryRepository;

  private final CategoryService categoryService;

  public SubCategoryResource(SubCategoryService subCategoryService, SubCategoryRepository subCategoryRepository,
      CategoryService categoryService) {
    this.subCategoryService = subCategoryService;
    this.subCategoryRepository = subCategoryRepository;
    this.categoryService = categoryService;
  }

  /**
   * {@code POST  /sub-categories} : Create a new subCategory.
   *
   * @param subCategory the subCategory to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
   *         body the new subCategory, or with status {@code 400 (Bad Request)} if
   *         the subCategory has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/sub-categories")
  public ResponseEntity<SubCategory> createSubCategory(@RequestBody SubCategory subCategory) throws URISyntaxException {
    log.debug("REST request to save SubCategory : {}", subCategory);
    if (subCategory.getId() != null) {
      throw new BadRequestAlertException("A new subCategory cannot already have an ID", ENTITY_NAME, "idexists");
    }
    // Setze den translationKey automatisch, falls nicht vorhanden, um i18n zu ermöglichen
    if (subCategory.getTranslationKey() == null || subCategory.getTranslationKey().isBlank()) {
      final Category category = categoryService.findOne(subCategory.getCategory().getId()).orElseThrow();
      final String entityName = category.getEntityName();
      final String slug = toSlug(subCategory.getName());
      subCategory.setTranslationKey("jaynaApp." + entityName + ".sub-category." + slug);
    }
    SubCategory result = subCategoryService.save(subCategory);
    return ResponseEntity
        .created(new URI("/api/sub-categories/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  /**
   * {@code PUT  /sub-categories/:id} : Updates an existing subCategory.
   *
   * @param id          the id of the subCategory to save.
   * @param subCategory the subCategory to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the updated subCategory,
   *         or with status {@code 400 (Bad Request)} if the subCategory is not
   *         valid,
   *         or with status {@code 500 (Internal Server Error)} if the subCategory
   *         couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/sub-categories/{id}")
  public ResponseEntity<SubCategory> updateSubCategory(
      @PathVariable(value = "id", required = false) final Long id,
      @RequestBody SubCategory subCategory) throws URISyntaxException {
    log.debug("REST request to update SubCategory : {}, {}", id, subCategory);
    if (subCategory.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, subCategory.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!subCategoryRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    SubCategory result = subCategoryService.update(subCategory);
    return ResponseEntity
        .ok()
        .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subCategory.getId().toString()))
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
   * {@code PATCH  /sub-categories/:id} : Partial updates given fields of an
   * existing subCategory, field will ignore if it is null
   *
   * @param id          the id of the subCategory to save.
   * @param subCategory the subCategory to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the updated subCategory,
   *         or with status {@code 400 (Bad Request)} if the subCategory is not
   *         valid,
   *         or with status {@code 404 (Not Found)} if the subCategory is not
   *         found,
   *         or with status {@code 500 (Internal Server Error)} if the subCategory
   *         couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/sub-categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
  public ResponseEntity<SubCategory> partialUpdateSubCategory(
      @PathVariable(value = "id", required = false) final Long id,
      @RequestBody SubCategory subCategory) throws URISyntaxException {
    log.debug("REST request to partial update SubCategory partially : {}, {}", id, subCategory);
    if (subCategory.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, subCategory.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!subCategoryRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<SubCategory> result = subCategoryService.partialUpdate(subCategory);

    return ResponseUtil.wrapOrNotFound(
        result,
        HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subCategory.getId().toString()));
  }

  /**
   * {@code GET  /sub-categories} : get all the subCategories.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
   *         of subCategories in body.
   */
  @GetMapping("/sub-categories")
  public List<SubCategory> getAllSubCategories(@RequestParam(name = "parentId", required = false) Long parentId) {
    log.debug("REST request to get all SubCategories");

    if (parentId != null) {
      final Optional<Category> maybeCategory = categoryService.findOne(parentId);
      if (maybeCategory.isEmpty()) {
        throw new BadRequestAlertException("Entity not found", "category", "idnotfound");
      }
      return subCategoryRepository.findAllByCategoryOrderByNameAsc(maybeCategory.orElseThrow());
    }

    return subCategoryService.findAll();
  }

  /**
   * {@code GET  /sub-categories/:id} : get the "id" subCategory.
   *
   * @param id the id of the subCategory to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the subCategory, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/sub-categories/{id}")
  public ResponseEntity<SubCategory> getSubCategory(@PathVariable Long id) {
    log.debug("REST request to get SubCategory : {}", id);
    Optional<SubCategory> subCategory = subCategoryService.findOne(id);
    return ResponseUtil.wrapOrNotFound(subCategory);
  }

  /**
   * {@code DELETE  /sub-categories/:id} : delete the "id" subCategory.
   *
   * @param id the id of the subCategory to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/sub-categories/{id}")
  public ResponseEntity<Void> deleteSubCategory(@PathVariable Long id) {
    log.debug("REST request to delete SubCategory : {}", id);
    subCategoryService.delete(id);
    return ResponseEntity
        .noContent()
        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
        .build();
  }
}
