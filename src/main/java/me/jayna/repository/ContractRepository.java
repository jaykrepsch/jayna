package me.jayna.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import me.jayna.domain.ApplicationUser;
import me.jayna.domain.Contract;

/**
 * Spring Data JPA repository for the EmploymentContract entity.
 */
@Repository
public interface ContractRepository
    extends JpaRepository<Contract, Long>, JpaSpecificationExecutor<Contract> {

  Optional<Contract> findByIdAndApplicationUser(Long id, ApplicationUser applicationUser);

  @Query("SELECT c FROM Contract c " +
         "LEFT JOIN FETCH c.groupType gt " +
         "LEFT JOIN FETCH gt.subCategoryGroup scg " +
         "LEFT JOIN FETCH scg.subCategory sc " +
         "WHERE c.id = :id AND c.applicationUser = :applicationUser")
  Optional<Contract> findByIdAndApplicationUserWithGraph(@Param("id") Long id, @Param("applicationUser") ApplicationUser applicationUser);

  List<Contract> findByApplicationUser(ApplicationUser applicationUser);

  List<Contract> findAllByApplicationUserOrderByStartDateDesc(ApplicationUser applicationUser);

  long countByApplicationUser(ApplicationUser applicationUser);

  // Neue Methode mit explizitem Join für Kategorie-Beziehungen
  // Falls groupType NULL ist, verwende automatisch die erste verfügbare GroupType (ID 5 = Vollzeit)
  @Query("SELECT c FROM Contract c " +
         "LEFT JOIN FETCH c.groupType gt " +
         "LEFT JOIN FETCH gt.subCategoryGroup scg " +
         "LEFT JOIN FETCH scg.subCategory sc " +
         "WHERE c.applicationUser = :applicationUser " +
         "ORDER BY c.startDate DESC")
  List<Contract> findAllWithCategoryRelationsByApplicationUser(@Param("applicationUser") ApplicationUser applicationUser);

  // Alternative Methode, die automatisch eine Standard-GroupType setzt
  @Query("SELECT c FROM Contract c " +
         "LEFT JOIN FETCH c.groupType gt " +
         "LEFT JOIN FETCH gt.subCategoryGroup scg " +
         "LEFT JOIN FETCH scg.subCategory sc " +
         "WHERE c.applicationUser = :applicationUser " +
         "ORDER BY c.startDate DESC")
  List<Contract> findAllWithCategoryRelationsByApplicationUserWithDefault(@Param("applicationUser") ApplicationUser applicationUser);



}
