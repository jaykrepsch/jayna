package me.jayna.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import me.jayna.domain.ApplicationUser;
import me.jayna.domain.RealEstate;

/**
 * Spring Data JPA repository for the RealEstate entity.
 */
@Repository
public interface RealEstateRepository
    extends
    JpaRepository<RealEstate, Long>, JpaSpecificationExecutor<RealEstate> {

  @EntityGraph("RealEstate.graph")
  Optional<RealEstate> findWithRelationsByIdAndApplicationUser(Long id, ApplicationUser applicationUser);

  Optional<RealEstate> findWithoutRelationsByIdAndApplicationUser(Long id, ApplicationUser applicationUser);

  int countByApplicationUser(ApplicationUser applicationUser);

  @EntityGraph("RealEstate.graph")
  List<RealEstate> findAllByApplicationUser(ApplicationUser applicationUser);

  // Optimierte Methode mit explizitem Join für Kategorie-Beziehungen
  @Query("SELECT r FROM RealEstate r " +
         "LEFT JOIN FETCH r.groupType gt " +
         "LEFT JOIN FETCH gt.subCategoryGroup scg " +
         "LEFT JOIN FETCH scg.subCategory sc " +
         "WHERE r.applicationUser = :applicationUser " +
         "ORDER BY r.id DESC")
  List<RealEstate> findAllWithCategoryRelationsByApplicationUser(@Param("applicationUser") ApplicationUser applicationUser);

  Optional<RealEstate> findByIdAndApplicationUser(Long id, ApplicationUser applicationUser);
}
