package me.jayna.service;

import java.util.List;
import java.util.Optional;
import me.jayna.domain.SubCategory;
import me.jayna.repository.SubCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SubCategory}.
 */
@Service
@Transactional
public class SubCategoryService {

  private final Logger log = LoggerFactory.getLogger(SubCategoryService.class);

  private final SubCategoryRepository subCategoryRepository;

  public SubCategoryService(SubCategoryRepository subCategoryRepository) {
    this.subCategoryRepository = subCategoryRepository;
  }

  /**
   * Save a subCategory.
   *
   * @param subCategory the entity to save.
   * @return the persisted entity.
   */
  public SubCategory save(SubCategory subCategory) {
    log.debug("Request to save SubCategory : {}", subCategory);
    return subCategoryRepository.save(subCategory);
  }

  /**
   * Update a subCategory.
   *
   * @param subCategory the entity to save.
   * @return the persisted entity.
   */
  public SubCategory update(SubCategory subCategory) {
    log.debug("Request to save SubCategory : {}", subCategory);
    return subCategoryRepository.save(subCategory);
  }

  /**
   * Partially update a subCategory.
   *
   * @param subCategory the entity to update partially.
   * @return the persisted entity.
   */
  public Optional<SubCategory> partialUpdate(SubCategory subCategory) {
    log.debug("Request to partially update SubCategory : {}", subCategory);

    return subCategoryRepository
      .findById(subCategory.getId())
      .map(existingSubCategory -> {
        if (subCategory.getName() != null) {
          existingSubCategory.setName(subCategory.getName());
        }
        if (subCategory.getCreatedDate() != null) {
          existingSubCategory.setCreatedDate(subCategory.getCreatedDate());
        }
        if (subCategory.getTranslationKey() != null) {
          existingSubCategory.setTranslationKey(
            subCategory.getTranslationKey()
          );
        }

        return existingSubCategory;
      })
      .map(subCategoryRepository::save);
  }

  /**
   * Get all the subCategories.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public List<SubCategory> findAll() {
    log.debug("Request to get all SubCategories");
    return subCategoryRepository.findAll();
  }

  /**
   * Get one subCategory by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Optional<SubCategory> findOne(Long id) {
    log.debug("Request to get SubCategory : {}", id);
    return subCategoryRepository.findById(id);
  }

  /**
   * Delete the subCategory by id.
   *
   * @param id the id of the entity.
   */
  public void delete(Long id) {
    log.debug("Request to delete SubCategory : {}", id);
    subCategoryRepository.deleteById(id);
  }
}
