package me.jayna.repository;

import me.jayna.domain.ApplicationUser;
import me.jayna.domain.FinanceAccount;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FinanceAccount entity.
 */
@Repository
public interface FinanceAccountRepository
    extends
    JpaRepository<FinanceAccount, Long>,
    JpaSpecificationExecutor<FinanceAccount> {

  @EntityGraph("FinanceAccount.graph")
  Optional<FinanceAccount> findWithRelationsByIdAndApplicationUser(Long id, ApplicationUser applicationUser);

  // Neue Methode mit explizitem Join für Kategorie-Beziehungen
  @Query("SELECT f FROM FinanceAccount f " +
         "LEFT JOIN FETCH f.groupType gt " +
         "LEFT JOIN FETCH gt.subCategoryGroup scg " +
         "LEFT JOIN FETCH scg.subCategory sc " +
         "WHERE f.id = :id AND f.applicationUser = :applicationUser")
  Optional<FinanceAccount> findByIdAndApplicationUserWithGraph(@Param("id") Long id, @Param("applicationUser") ApplicationUser applicationUser);

  Optional<FinanceAccount> findWithoutRelationsByIdAndApplicationUser(Long id, ApplicationUser applicationUser);

  int countByApplicationUser(ApplicationUser applicationUser);

  @EntityGraph("FinanceAccount.graph")
  List<FinanceAccount> findAllByApplicationUser(ApplicationUser applicationUser);

  // Optimierte Methode mit explizitem Join für Kategorie-Beziehungen
  @Query("SELECT f FROM FinanceAccount f " +
         "LEFT JOIN FETCH f.groupType gt " +
         "LEFT JOIN FETCH gt.subCategoryGroup scg " +
         "LEFT JOIN FETCH scg.subCategory sc " +
         "WHERE f.applicationUser = :applicationUser " +
         "ORDER BY f.id DESC")
  List<FinanceAccount> findAllWithCategoryRelationsByApplicationUser(@Param("applicationUser") ApplicationUser applicationUser);

  @EntityGraph("FinanceAccount.overview")
  @Query("SELECT f FROM FinanceAccount f WHERE f.applicationUser = :applicationUser")
  List<FinanceAccount> findAllByApplicationUserForOverview(@Param("applicationUser") ApplicationUser applicationUser);
}
