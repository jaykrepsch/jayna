package me.jayna.repository;

import me.jayna.domain.SubCategory;
import me.jayna.domain.SubCategoryGroup;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SubCategoryGroup entity.
 */
@Repository
public interface SubCategoryGroupRepository extends JpaRepository<SubCategoryGroup, Long> {

  List<SubCategoryGroup> findAllByCreatedByInOrderByNameAsc(Collection<String> createdBys);

  List<SubCategoryGroup> findAllBySubCategoryAndCreatedByInOrderByNameAsc(SubCategory subCategory,
      Collection<String> createdBys);
}
