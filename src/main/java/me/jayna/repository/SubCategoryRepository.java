package me.jayna.repository;

import me.jayna.domain.Category;
import me.jayna.domain.SubCategory;
import me.jayna.domain.SubCategoryGroup;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SubCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
  
  List<SubCategory> findAllByCategoryOrderByNameAsc(Category category);
  
}
