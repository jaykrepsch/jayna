package me.jayna.repository;

import me.jayna.domain.Contact;
import me.jayna.domain.ContactMobility;
import me.jayna.domain.Mobility;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ContactMobility entity.
 */
@Repository
public interface ContactMobilityRepository
    extends JpaRepository<ContactMobility, Long> {

  Optional<ContactMobility> findOneByContact(Contact contact);

  Optional<ContactMobility> findOneByMobility(Mobility mobility);

  Set<ContactMobility> findAllByContact(Contact contact);

  Set<ContactMobility> findAllByMobility(Mobility mobility);

}
