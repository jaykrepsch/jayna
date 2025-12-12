package me.jayna.repository;

import me.jayna.domain.ApplicationUser;
import me.jayna.domain.Contact;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Contact entity.
 */
@Repository
public interface ContactRepository
    extends JpaRepository<Contact, Long>, JpaSpecificationExecutor<Contact> {

  @EntityGraph("Contact.graph")
  Optional<Contact> findWithRelationsByIdAndApplicationUser(Long id, ApplicationUser applicationUser);

  Optional<Contact> findWithoutRelationsByIdAndApplicationUser(Long id, ApplicationUser applicationUser);

  @EntityGraph("Contact.graph")
  List<Contact> findAllByApplicationUser(ApplicationUser applicationUser);

  @EntityGraph("Contact.overview")
  @Query("SELECT c FROM Contact c WHERE c.applicationUser = :applicationUser")
  List<Contact> findAllByApplicationUserForOverview(@Param("applicationUser") ApplicationUser applicationUser);
}
