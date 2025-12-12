package me.jayna.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import me.jayna.domain.Contact;
import me.jayna.domain.RealEstate;
import me.jayna.domain.ContactRealestate;
import me.jayna.repository.ContactRealestateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ContactRealestate}.
 */
@Service
@Transactional
public class ContactRealestateService {

  private final Logger log = LoggerFactory.getLogger(ContactRealestateService.class);

  private final ContactRealestateRepository contactRealestateRepository;

  public ContactRealestateService(ContactRealestateRepository contactRealestateRepository) {
    this.contactRealestateRepository = contactRealestateRepository;
  }

  public ContactRealestate save(ContactRealestate contractRealestate) {
    log.debug("Request to save ContactRealestate : {}", contractRealestate);
    return contactRealestateRepository.save(contractRealestate);
  }

  public ContactRealestate update(ContactRealestate contractRealestate) {
    log.debug("Request to save ContactRealestate : {}", contractRealestate);
    return contactRealestateRepository.save(contractRealestate);
  }

  @Transactional(readOnly = true)
  public Page<ContactRealestate> findAll(Pageable pageable) {
    log.debug("Request to get all ContactRealestates");
    return contactRealestateRepository.findAll(pageable);
  }

  @Transactional(readOnly = true)
  public Optional<ContactRealestate> findByContact(Contact contact) {
    log.debug("Request to get ContactRealestate by contact");
    return contactRealestateRepository.findOneByContact(contact);
  }

  @Transactional(readOnly = true)
  public Optional<ContactRealestate> findByRealestate(RealEstate realestate) {
    log.debug("Request to get ContactRealestate by finrealestate");
    return contactRealestateRepository.findOneByRealestate(realestate);
  }

  @Transactional(readOnly = true)
  public Set<ContactRealestate> findAllByContact(Contact contact) {
    log.debug("Request to get ContactRealestate by contact");
    return contactRealestateRepository.findAllByContact(contact);
  }

  @Transactional(readOnly = true)
  public Set<ContactRealestate> findAllByRealestate(RealEstate realestate) {
    log.debug("Request to get ContactRealestate by realestate");
    return contactRealestateRepository.findAllByRealestate(realestate);
  }

  @Transactional(readOnly = true)
  public Optional<ContactRealestate> findOne(Long id) {
    log.debug("Request to get ContactRealestate : {}", id);
    return contactRealestateRepository.findById(id);
  }

  public void delete(Long id) {
    log.debug("Request to delete ContactRealestate : {}", id);
    contactRealestateRepository.deleteById(id);
  }

  public void deleteAll(Iterable<ContactRealestate> collection) {
    log.debug("Request to delete all ContactRealestates : {}", collection);
    contactRealestateRepository.deleteAll(collection);
  }

  public void saveContactRealestateRelationships(Set<ContactRealestate> contactRealestates, Object entity) {
    contactRealestates
        .forEach(cr -> {
          if (entity instanceof Contact) {
            final Contact contact = (Contact) entity;
            cr.setContact(contact);
            contact.getContactRealestates().add(cr);
          } else if (entity instanceof RealEstate) {
            final RealEstate realestate = (RealEstate) entity;
            cr.setRealestate(realestate);
            realestate.getContactRealestates().add(cr);
          }
          save(cr);
        });
  }

  public void updateContactRealestateRelationships(Set<ContactRealestate> requestContactRealestates,
      Object entity) {
    final Set<ContactRealestate> contactRealestates = new HashSet<>();

    if (entity instanceof Contact) {
      final Contact contact = (Contact) entity;
      contactRealestates.addAll(findAllByContact(contact));
    } else if (entity instanceof RealEstate) {
      final RealEstate realestate = (RealEstate) entity;
      contactRealestates.addAll(findAllByRealestate(realestate));
    }

    final Set<ContactRealestate> contactRealestatesToDelete = new HashSet<>();

    contactRealestatesToDelete.addAll(
        contactRealestates.stream()
            .filter(cr -> requestContactRealestates.stream().filter(item -> cr.getId().equals(item.getId()))
                .count() == 0)
            .collect(Collectors.toSet()));

    if (contactRealestatesToDelete.size() > 0) {
      deleteAll(contactRealestatesToDelete);
    }

    requestContactRealestates
        .forEach(cr -> {
          if (cr.getId() == null) {
            if (entity instanceof Contact) {
              final Contact contact = (Contact) entity;
              cr.setContact(contact);
            } else if (entity instanceof RealEstate) {
              final RealEstate realestate = (RealEstate) entity;
              cr.setRealestate(realestate);
            }
            final ContactRealestate result = save(cr);
            cr.setId(result.getId());
          }
        });
  }

  public void deleteRelationships(Object entity) {
    try {
      if (entity instanceof Contact) {
        final Contact contact = (Contact) entity;
        Set<ContactRealestate> relationships = findAllByContact(contact);
        if (!relationships.isEmpty()) {
          deleteAll(relationships);
        }
      } else if (entity instanceof RealEstate) {
        final RealEstate realestate = (RealEstate) entity;
        Set<ContactRealestate> relationships = findAllByRealestate(realestate);
        if (!relationships.isEmpty()) {
          deleteAll(relationships);
        }
      }
    } catch (Exception e) {
      log.warn("Error deleting ContactRealestate relationships: {}", e.getMessage());
      // Versuche einzelne Löschungen, falls die Batch-Löschung fehlschlägt
      try {
        if (entity instanceof Contact) {
          final Contact contact = (Contact) entity;
          Set<ContactRealestate> relationships = findAllByContact(contact);
          for (ContactRealestate relationship : relationships) {
            try {
              delete(relationship.getId());
            } catch (Exception ex) {
              log.warn("Error deleting individual ContactRealestate relationship {}: {}", relationship.getId(), ex.getMessage());
            }
          }
        } else if (entity instanceof RealEstate) {
          final RealEstate realestate = (RealEstate) entity;
          Set<ContactRealestate> relationships = findAllByRealestate(realestate);
          for (ContactRealestate relationship : relationships) {
            try {
              delete(relationship.getId());
            } catch (Exception ex) {
              log.warn("Error deleting individual ContactRealestate relationship {}: {}", relationship.getId(), ex.getMessage());
            }
          }
        }
      } catch (Exception ex) {
        log.error("Error during individual ContactRealestate relationship deletion: {}", ex.getMessage());
      }
    }
  }
}
