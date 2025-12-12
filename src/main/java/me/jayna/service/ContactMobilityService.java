package me.jayna.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import me.jayna.domain.Contact;
import me.jayna.domain.Mobility;
import me.jayna.domain.ContactMobility;
import me.jayna.repository.ContactMobilityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ContactMobility}.
 */
@Service
@Transactional
public class ContactMobilityService {

  private final Logger log = LoggerFactory.getLogger(ContactMobilityService.class);

  private final ContactMobilityRepository contactMobilityRepository;

  public ContactMobilityService(ContactMobilityRepository contactMobilityRepository) {
    this.contactMobilityRepository = contactMobilityRepository;
  }

  public ContactMobility save(ContactMobility contractRealestate) {
    log.debug("Request to save ContactMobility : {}", contractRealestate);
    return contactMobilityRepository.save(contractRealestate);
  }

  public ContactMobility update(ContactMobility contractRealestate) {
    log.debug("Request to save ContactMobility : {}", contractRealestate);
    return contactMobilityRepository.save(contractRealestate);
  }

  @Transactional(readOnly = true)
  public Page<ContactMobility> findAll(Pageable pageable) {
    log.debug("Request to get all ContactMobilities");
    return contactMobilityRepository.findAll(pageable);
  }

  @Transactional(readOnly = true)
  public Optional<ContactMobility> findByContact(Contact contact) {
    log.debug("Request to get ContactMobility by contact");
    return contactMobilityRepository.findOneByContact(contact);
  }

  @Transactional(readOnly = true)
  public Optional<ContactMobility> findByMobility(Mobility mobility) {
    log.debug("Request to get ContactMobility by finmobility");
    return contactMobilityRepository.findOneByMobility(mobility);
  }

  @Transactional(readOnly = true)
  public Set<ContactMobility> findAllByContact(Contact contact) {
    log.debug("Request to get ContactMobility by contact");
    return contactMobilityRepository.findAllByContact(contact);
  }

  @Transactional(readOnly = true)
  public Set<ContactMobility> findAllByMobility(Mobility mobility) {
    log.debug("Request to get ContactMobility by mobility");
    return contactMobilityRepository.findAllByMobility(mobility);
  }

  @Transactional(readOnly = true)
  public Optional<ContactMobility> findOne(Long id) {
    log.debug("Request to get ContactMobility : {}", id);
    return contactMobilityRepository.findById(id);
  }

  public void delete(Long id) {
    log.debug("Request to delete ContactMobility : {}", id);
    contactMobilityRepository.deleteById(id);
  }

  public void deleteAll(Iterable<ContactMobility> collection) {
    log.debug("Request to delete all ContactMobilities : {}", collection);
    contactMobilityRepository.deleteAll(collection);
  }

  public void saveContactMobilityRelationships(Set<ContactMobility> contactMobilities, Object entity) {
    contactMobilities
        .forEach(cr -> {
          if (entity instanceof Contact) {
            final Contact contact = (Contact) entity;
            cr.setContact(contact);
            contact.getContactMobilities().add(cr);
          } else if (entity instanceof Mobility) {
            final Mobility mobility = (Mobility) entity;
            cr.setMobility(mobility);
            mobility.getContactMobilities().add(cr);
          }
          save(cr);
        });
  }

  public void updateContactMobilityRelationships(Set<ContactMobility> requestContactMobilities,
      Object entity) {
    final Set<ContactMobility> contactMobilities = new HashSet<>();

    if (entity instanceof Contact) {
      final Contact contact = (Contact) entity;
      contactMobilities.addAll(findAllByContact(contact));
    } else if (entity instanceof Mobility) {
      final Mobility mobility = (Mobility) entity;
      contactMobilities.addAll(findAllByMobility(mobility));
    }

    final Set<ContactMobility> contactMobilitiesToDelete = new HashSet<>();

    contactMobilitiesToDelete.addAll(
        contactMobilities.stream()
            .filter(cr -> requestContactMobilities.stream().filter(item -> cr.getId().equals(item.getId()))
                .count() == 0)
            .collect(Collectors.toSet()));

    if (contactMobilitiesToDelete.size() > 0) {
      deleteAll(contactMobilitiesToDelete);
    }

    requestContactMobilities
        .forEach(cr -> {
          if (cr.getId() == null) {
            if (entity instanceof Contact) {
              final Contact contact = (Contact) entity;
              cr.setContact(contact);
            } else if (entity instanceof Mobility) {
              final Mobility mobility = (Mobility) entity;
              cr.setMobility(mobility);
            }
            final ContactMobility result = save(cr);
            cr.setId(result.getId());
          }
        });
  }

  public void deleteRelationships(Object entity) {
    if (entity instanceof Contact) {
      final Contact contact = (Contact) entity;
      deleteAll(findAllByContact(contact));
    } else if (entity instanceof Mobility) {
      final Mobility mobility = (Mobility) entity;
      deleteAll(findAllByMobility(mobility));
    }
  }
}
