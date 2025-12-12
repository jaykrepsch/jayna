package me.jayna.repository;

import me.jayna.domain.Contact;
import me.jayna.domain.ContactContract;
import me.jayna.domain.Contract;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ContactFinanceAccount entity.
 */
@Repository
public interface ContactContractRepository
    extends JpaRepository<ContactContract, Long> {

  Optional<ContactContract> findOneByContact(Contact contact);

  Optional<ContactContract> findOneByContract(Contract contract);

  Set<ContactContract> findAllByContact(Contact contact);

  Set<ContactContract> findAllByContract(Contract contract);

}
