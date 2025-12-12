package me.jayna.repository;

import me.jayna.domain.Contact;
import me.jayna.domain.ContactFinanceAccount;
import me.jayna.domain.FinanceAccount;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ContactFinanceAccount entity.
 */
@Repository
public interface ContactFinanceAccountRepository
    extends JpaRepository<ContactFinanceAccount, Long> {

  Optional<ContactFinanceAccount> findOneByContact(Contact contact);

  Optional<ContactFinanceAccount> findOneByFinanceaccount(FinanceAccount financeaccount);

  Set<ContactFinanceAccount> findAllByContact(Contact contact);

  Set<ContactFinanceAccount> findAllByFinanceaccount(FinanceAccount financeaccount);

}
