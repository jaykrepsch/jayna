package me.jayna.repository;

import me.jayna.domain.GroupType;
import me.jayna.domain.SubCategoryGroup;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GroupType entity.
 */
@Repository
public interface GroupTypeRepository extends JpaRepository<GroupType, Long> {

  List<GroupType> findAllBySubCategoryGroupOrderByNameAsc(SubCategoryGroup subCategoryGroup);

  List<GroupType> findAllBySubCategoryGroupAndCreatedByInOrderByNameAsc(SubCategoryGroup subCategoryGroup,
      Collection<String> createdBys);

  List<GroupType> findAllByCreatedByInOrderByNameAsc(Collection<String> createdBys);

  List<GroupType> findAllByEntityName(String entityName);

  @EntityGraph("GroupType.graph")
  Optional<GroupType> findWithRelationsById(Long id);
}
