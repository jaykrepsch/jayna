package me.jayna.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import me.jayna.domain.Contact;
import me.jayna.domain.FinanceAccount;
import me.jayna.domain.ContactFinanceAccount;
import me.jayna.repository.ContactFinanceAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ContactFinanceAccount}.
 */
@Service
@Transactional
public class ContactFinanceAccountService {

  private final Logger log = LoggerFactory.getLogger(ContactFinanceAccountService.class);

  private final ContactFinanceAccountRepository contactFinanceAccountRepository;

  public ContactFinanceAccountService(ContactFinanceAccountRepository contactFinanceAccountRepository) {
    this.contactFinanceAccountRepository = contactFinanceAccountRepository;
  }

  public ContactFinanceAccount save(ContactFinanceAccount contactFinanceAccount) {
    log.debug("Request to save ContactFinanceAccount : {}", contactFinanceAccount);
    return contactFinanceAccountRepository.save(contactFinanceAccount);
  }

  public ContactFinanceAccount update(ContactFinanceAccount contactFinanceAccount) {
    log.debug("Request to save ContactFinanceAccount : {}", contactFinanceAccount);
    return contactFinanceAccountRepository.save(contactFinanceAccount);
  }

  @Transactional(readOnly = true)
  public Page<ContactFinanceAccount> findAll(Pageable pageable) {
    log.debug("Request to get all ContactFinanceAccounts");
    return contactFinanceAccountRepository.findAll(pageable);
  }

  @Transactional(readOnly = true)
  public Optional<ContactFinanceAccount> findByContact(Contact contact) {
    log.debug("Request to get ContactFinanceAccount by contact");
    return contactFinanceAccountRepository.findOneByContact(contact);
  }

  @Transactional(readOnly = true)
  public Optional<ContactFinanceAccount> findByFinanceAccount(FinanceAccount financeAccount) {
    log.debug("Request to get ContactFinanceAccount by finfinanceAccount");
    return contactFinanceAccountRepository.findOneByFinanceaccount(financeAccount);
  }

  @Transactional(readOnly = true)
  public Set<ContactFinanceAccount> findAllByContact(Contact contact) {
    log.debug("Request to get ContactFinanceAccount by contact");
    return contactFinanceAccountRepository.findAllByContact(contact);
  }

  @Transactional(readOnly = true)
  public Set<ContactFinanceAccount> findAllByFinanceAccount(FinanceAccount financeAccount) {
    log.debug("Request to get ContactFinanceAccount by financeAccount");
    return contactFinanceAccountRepository.findAllByFinanceaccount(financeAccount);
  }

  @Transactional(readOnly = true)
  public Optional<ContactFinanceAccount> findOne(Long id) {
    log.debug("Request to get ContactFinanceAccount : {}", id);
    return contactFinanceAccountRepository.findById(id);
  }

  public void delete(Long id) {
    log.debug("Request to delete ContactFinanceAccount : {}", id);
    contactFinanceAccountRepository.deleteById(id);
  }

  public void deleteAll(Iterable<ContactFinanceAccount> collection) {
    log.debug("Request to delete all ContactFinanceAccounts : {}", collection);
    contactFinanceAccountRepository.deleteAll(collection);
  }

  public void saveContactFinanceAccountRelationships(Set<ContactFinanceAccount> contactFinanceAccounts, Object entity) {
    contactFinanceAccounts
        .forEach(cr -> {
          if (entity instanceof Contact) {
            final Contact contact = (Contact) entity;
            cr.setContact(contact);
            contact.getContactFinanceAccounts().add(cr);
          } else if (entity instanceof FinanceAccount) {
            final FinanceAccount financeAccount = (FinanceAccount) entity;
            cr.setFinanceaccount(financeAccount);
            financeAccount.getContactFinanceAccounts().add(cr);
          }
          save(cr);
        });
  }

  public void updateContactFinanceAccountRelationships(Set<ContactFinanceAccount> requestContactFinanceAccounts,
      Object entity) {
    final Set<ContactFinanceAccount> contactFinanceAccounts = new HashSet<>();

    if (entity instanceof Contact) {
      final Contact contact = (Contact) entity;
      contactFinanceAccounts.addAll(findAllByContact(contact));
    } else if (entity instanceof FinanceAccount) {
      final FinanceAccount financeAccount = (FinanceAccount) entity;
      contactFinanceAccounts.addAll(findAllByFinanceAccount(financeAccount));
    }

    final Set<ContactFinanceAccount> contactFinanceAccountsToDelete = new HashSet<>();

    contactFinanceAccountsToDelete.addAll(
        contactFinanceAccounts.stream()
            .filter(cr -> requestContactFinanceAccounts.stream().filter(item -> cr.getId().equals(item.getId()))
                .count() == 0)
            .collect(Collectors.toSet()));

    if (contactFinanceAccountsToDelete.size() > 0) {
      deleteAll(contactFinanceAccountsToDelete);
    }

    requestContactFinanceAccounts
        .forEach(cr -> {
          if (cr.getId() == null) {
            if (entity instanceof Contact) {
              final Contact contact = (Contact) entity;
              cr.setContact(contact);
            } else if (entity instanceof FinanceAccount) {
              final FinanceAccount financeAccount = (FinanceAccount) entity;
              cr.setFinanceaccount(financeAccount);
            }
            final ContactFinanceAccount result = save(cr);
            cr.setId(result.getId());
          }
        });
  }

  public void deleteRelationships(Object entity) {
    if (entity instanceof Contact) {
      final Contact contact = (Contact) entity;
      deleteAll(findAllByContact(contact));
    } else if (entity instanceof FinanceAccount) {
      final FinanceAccount financeAccount = (FinanceAccount) entity;
      deleteAll(findAllByFinanceAccount(financeAccount));
    }
  }
}
