package me.jayna.service;

import java.util.List;
import java.util.Optional;
import me.jayna.domain.GroupType;
import me.jayna.repository.GroupTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GroupType}.
 */
@Service
@Transactional
public class GroupTypeService {

  private final Logger log = LoggerFactory.getLogger(GroupTypeService.class);

  private final GroupTypeRepository groupTypeRepository;

  public GroupTypeService(GroupTypeRepository groupTypeRepository) {
    this.groupTypeRepository = groupTypeRepository;
  }

  /**
   * Save a groupType.
   *
   * @param groupType the entity to save.
   * @return the persisted entity.
   */
  public GroupType save(GroupType groupType) {
    log.debug("Request to save GroupType : {}", groupType);
    return groupTypeRepository.save(groupType);
  }

  /**
   * Update a groupType.
   *
   * @param groupType the entity to save.
   * @return the persisted entity.
   */
  public GroupType update(GroupType groupType) {
    log.debug("Request to save GroupType : {}", groupType);
    return groupTypeRepository.save(groupType);
  }

  /**
   * Partially update a groupType.
   *
   * @param groupType the entity to update partially.
   * @return the persisted entity.
   */
  public Optional<GroupType> partialUpdate(GroupType groupType) {
    log.debug("Request to partially update GroupType : {}", groupType);

    return groupTypeRepository
        .findById(groupType.getId())
        .map(existingGroupType -> {
          if (groupType.getName() != null) {
            existingGroupType.setName(groupType.getName());
          }
          if (groupType.getCreatedDate() != null) {
            existingGroupType.setCreatedDate(groupType.getCreatedDate());
          }
          if (groupType.getTranslationKey() != null) {
            existingGroupType.setTranslationKey(groupType.getTranslationKey());
          }
          if (groupType.getEntityName() != null) {
            existingGroupType.setEntityName(groupType.getEntityName());
          }
          if (groupType.getFormName() != null) {
            existingGroupType.setFormName(groupType.getFormName());
          }
          if (groupType.getAbbreviation() != null) {
            existingGroupType.setAbbreviation(groupType.getAbbreviation());
          }

          return existingGroupType;
        })
        .map(groupTypeRepository::save);
  }

  /**
   * Get all the groupTypes.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public List<GroupType> findAll() {
    log.debug("Request to get all GroupTypes");
    return groupTypeRepository.findAll();
  }

  /**
   * Get one groupType by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Optional<GroupType> findOne(Long id) {
    log.debug("Request to get GroupType : {}", id);
    return groupTypeRepository.findWithRelationsById(id);
  }

  /**
   * Delete the groupType by id.
   *
   * @param id the id of the entity.
   */
  public void delete(Long id) {
    log.debug("Request to delete GroupType : {}", id);
    groupTypeRepository.deleteById(id);
  }

  public List<GroupType> findByEntityName(String entityName) {
    return groupTypeRepository.findAllByEntityName(entityName);
  }
}
