package me.jayna.repository;

import me.jayna.domain.Category;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Category entity.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  public Optional<Category> findOneByName(String name);

  public Optional<Category> findOneByEntityName(String entityName);

}
