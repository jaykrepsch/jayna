package me.jayna.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import me.jayna.domain.Contact;
import me.jayna.domain.Contract;
import me.jayna.domain.ContactContract;
import me.jayna.repository.ContactContractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ContactContract}.
 */
@Service
@Transactional
public class ContactContractService {

  private final Logger log = LoggerFactory.getLogger(ContactContractService.class);

  private final ContactContractRepository contactContractRepository;

  public ContactContractService(ContactContractRepository contactContractRepository) {
    this.contactContractRepository = contactContractRepository;
  }

  public ContactContract save(ContactContract contractRealestate) {
    log.debug("Request to save ContactContract : {}", contractRealestate);
    return contactContractRepository.save(contractRealestate);
  }

  public ContactContract update(ContactContract contractRealestate) {
    log.debug("Request to save ContactContract : {}", contractRealestate);
    return contactContractRepository.save(contractRealestate);
  }

  @Transactional(readOnly = true)
  public Page<ContactContract> findAll(Pageable pageable) {
    log.debug("Request to get all ContactContracts");
    return contactContractRepository.findAll(pageable);
  }

  @Transactional(readOnly = true)
  public Optional<ContactContract> findByContact(Contact contact) {
    log.debug("Request to get ContactContract by contact");
    return contactContractRepository.findOneByContact(contact);
  }

  @Transactional(readOnly = true)
  public Optional<ContactContract> findByContract(Contract contract) {
    log.debug("Request to get ContactContract by fincontract");
    return contactContractRepository.findOneByContract(contract);
  }

  @Transactional(readOnly = true)
  public Set<ContactContract> findAllByContact(Contact contact) {
    log.debug("Request to get ContactContract by contact");
    return contactContractRepository.findAllByContact(contact);
  }

  @Transactional(readOnly = true)
  public Set<ContactContract> findAllByContract(Contract contract) {
    log.debug("Request to get ContactContract by contract");
    return contactContractRepository.findAllByContract(contract);
  }

  @Transactional(readOnly = true)
  public Optional<ContactContract> findOne(Long id) {
    log.debug("Request to get ContactContract : {}", id);
    return contactContractRepository.findById(id);
  }

  public void delete(Long id) {
    log.debug("Request to delete ContactContract : {}", id);
    contactContractRepository.deleteById(id);
  }

  public void deleteAll(Iterable<ContactContract> collection) {
    log.debug("Request to delete all ContactContracts : {}", collection);
    contactContractRepository.deleteAll(collection);
  }

  public void saveContactContractRelationships(Set<ContactContract> contactContracts, Object entity) {
    contactContracts
        .forEach(cr -> {
          if (entity instanceof Contact) {
            final Contact contact = (Contact) entity;
            cr.setContact(contact);
            contact.getContactContracts().add(cr);
          } else if (entity instanceof Contract) {
            final Contract contract = (Contract) entity;
            cr.setContract(contract);
            contract.getContactContracts().add(cr);
          }
          save(cr);
        });
  }

  public void updateContactContractRelationships(Set<ContactContract> requestContactContracts,
      Object entity) {
    final Set<ContactContract> contactContracts = new HashSet<>();

    if (entity instanceof Contact) {
      final Contact contact = (Contact) entity;
      contactContracts.addAll(findAllByContact(contact));
    } else if (entity instanceof Contract) {
      final Contract contract = (Contract) entity;
      contactContracts.addAll(findAllByContract(contract));
    }

    final Set<ContactContract> contactContractsToDelete = new HashSet<>();

    contactContractsToDelete.addAll(
        contactContracts.stream()
            .filter(cr -> requestContactContracts.stream().filter(item -> cr.getId().equals(item.getId()))
                .count() == 0)
            .collect(Collectors.toSet()));

    if (contactContractsToDelete.size() > 0) {
      deleteAll(contactContractsToDelete);
    }

    requestContactContracts
        .forEach(cr -> {
          if (cr.getId() == null) {
            if (entity instanceof Contact) {
              final Contact contact = (Contact) entity;
              cr.setContact(contact);
            } else if (entity instanceof Contract) {
              final Contract contract = (Contract) entity;
              cr.setContract(contract);
            }
            final ContactContract result = save(cr);
            cr.setId(result.getId());
          }
        });
  }

  public void deleteRelationships(Object entity) {
    if (entity instanceof Contact) {
      final Contact contact = (Contact) entity;
      deleteAll(findAllByContact(contact));
    } else if (entity instanceof Contract) {
      final Contract contract = (Contract) entity;
      deleteAll(findAllByContract(contract));
    }
  }
}
