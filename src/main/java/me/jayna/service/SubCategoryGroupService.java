package me.jayna.service;

import java.util.List;
import java.util.Optional;
import me.jayna.domain.SubCategoryGroup;
import me.jayna.repository.SubCategoryGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SubCategoryGroup}.
 */
@Service
@Transactional
public class SubCategoryGroupService {

  private final Logger log = LoggerFactory.getLogger(
    SubCategoryGroupService.class
  );

  private final SubCategoryGroupRepository subCategoryGroupRepository;

  public SubCategoryGroupService(
    SubCategoryGroupRepository subCategoryGroupRepository
  ) {
    this.subCategoryGroupRepository = subCategoryGroupRepository;
  }

  /**
   * Save a subCategoryGroup.
   *
   * @param subCategoryGroup the entity to save.
   * @return the persisted entity.
   */
  public SubCategoryGroup save(SubCategoryGroup subCategoryGroup) {
    log.debug("Request to save SubCategoryGroup : {}", subCategoryGroup);
    return subCategoryGroupRepository.save(subCategoryGroup);
  }

  /**
   * Update a subCategoryGroup.
   *
   * @param subCategoryGroup the entity to save.
   * @return the persisted entity.
   */
  public SubCategoryGroup update(SubCategoryGroup subCategoryGroup) {
    log.debug("Request to save SubCategoryGroup : {}", subCategoryGroup);
    return subCategoryGroupRepository.save(subCategoryGroup);
  }

  /**
   * Partially update a subCategoryGroup.
   *
   * @param subCategoryGroup the entity to update partially.
   * @return the persisted entity.
   */
  public Optional<SubCategoryGroup> partialUpdate(
    SubCategoryGroup subCategoryGroup
  ) {
    log.debug(
      "Request to partially update SubCategoryGroup : {}",
      subCategoryGroup
    );

    return subCategoryGroupRepository
      .findById(subCategoryGroup.getId())
      .map(existingSubCategoryGroup -> {
        if (subCategoryGroup.getName() != null) {
          existingSubCategoryGroup.setName(subCategoryGroup.getName());
        }
        if (subCategoryGroup.getCreatedDate() != null) {
          existingSubCategoryGroup.setCreatedDate(
            subCategoryGroup.getCreatedDate()
          );
        }
        if (subCategoryGroup.getTranslationKey() != null) {
          existingSubCategoryGroup.setTranslationKey(
            subCategoryGroup.getTranslationKey()
          );
        }

        return existingSubCategoryGroup;
      })
      .map(subCategoryGroupRepository::save);
  }

  /**
   * Get all the subCategoryGroups.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public List<SubCategoryGroup> findAll() {
    log.debug("Request to get all SubCategoryGroups");
    return subCategoryGroupRepository.findAll();
  }

  /**
   * Get one subCategoryGroup by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Optional<SubCategoryGroup> findOne(Long id) {
    log.debug("Request to get SubCategoryGroup : {}", id);
    return subCategoryGroupRepository.findById(id);
  }

  /**
   * Delete the subCategoryGroup by id.
   *
   * @param id the id of the entity.
   */
  public void delete(Long id) {
    log.debug("Request to delete SubCategoryGroup : {}", id);
    subCategoryGroupRepository.deleteById(id);
  }
}
