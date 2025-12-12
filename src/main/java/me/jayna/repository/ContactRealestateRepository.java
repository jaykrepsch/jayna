package me.jayna.repository;

import me.jayna.domain.Contact;
import me.jayna.domain.ContactRealestate;
import me.jayna.domain.RealEstate;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ContactRealestate entity.
 */
@Repository
public interface ContactRealestateRepository
    extends JpaRepository<ContactRealestate, Long> {

  Optional<ContactRealestate> findOneByContact(Contact contact);

  Optional<ContactRealestate> findOneByRealestate(RealEstate realestate);

  Set<ContactRealestate> findAllByContact(Contact contact);

  Set<ContactRealestate> findAllByRealestate(RealEstate realestate);

}
