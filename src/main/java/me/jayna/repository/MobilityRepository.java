package me.jayna.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import me.jayna.domain.ApplicationUser;
import me.jayna.domain.Mobility;

/**
 * Spring Data JPA repository for the Mobility entity.
 */

@Repository
public interface MobilityRepository extends JpaRepository<Mobility, Long>, JpaSpecificationExecutor<Mobility> {

  @EntityGraph("Mobility.graph")
  Optional<Mobility> findWithRelationsByIdAndApplicationUser(Long id, ApplicationUser applicationUser);

  // Neue Methode mit explizitem Join für Kategorie-Beziehungen
  @Query("SELECT m FROM Mobility m " +
         "LEFT JOIN FETCH m.groupType gt " +
         "LEFT JOIN FETCH gt.subCategoryGroup scg " +
         "LEFT JOIN FETCH scg.subCategory sc " +
         "WHERE m.id = :id AND m.applicationUser = :applicationUser")
  Optional<Mobility> findByIdAndApplicationUserWithGraph(@Param("id") Long id, @Param("applicationUser") ApplicationUser applicationUser);

  Optional<Mobility> findWithoutRelationsByIdAndApplicationUser(Long id, ApplicationUser applicationUser);

  int countByApplicationUser(ApplicationUser applicationUser);

  @EntityGraph("Mobility.graph")
  List<Mobility> findAllByApplicationUser(ApplicationUser applicationUser);

  @EntityGraph("Mobility.overview")
  @Query("SELECT m FROM Mobility m WHERE m.applicationUser = :applicationUser")
  List<Mobility> findAllByApplicationUserForOverview(@Param("applicationUser") ApplicationUser applicationUser);

  // Optimierte Methode mit explizitem Join für Kategorie-Beziehungen
  @Query("SELECT m FROM Mobility m " +
         "LEFT JOIN FETCH m.groupType gt " +
         "LEFT JOIN FETCH gt.subCategoryGroup scg " +
         "LEFT JOIN FETCH scg.subCategory sc " +
         "WHERE m.applicationUser = :applicationUser " +
         "ORDER BY m.id DESC")
  List<Mobility> findAllWithCategoryRelationsByApplicationUser(@Param("applicationUser") ApplicationUser applicationUser);
}
