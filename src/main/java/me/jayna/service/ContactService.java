package me.jayna.service;

import java.util.List;
import java.util.Optional;
import me.jayna.domain.Contact;
import me.jayna.domain.ApplicationUser;
import me.jayna.repository.ContactRepository;
import me.jayna.service.ContactContractService;
import me.jayna.service.ContactRealestateService;
import me.jayna.service.ContactFinanceAccountService;
import me.jayna.service.ContactMobilityService;
import me.jayna.service.ContactContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Contact}.
 */
@Service
@Transactional
public class ContactService {

  private final Logger log = LoggerFactory.getLogger(ContactService.class);

  private final ContactRepository contactRepository;
  private final ContactContractService contactContractService;
  private final ContactRealestateService contactRealestateService;
  private final ContactFinanceAccountService contactFinanceAccountService;
  private final ContactMobilityService contactMobilityService;
  private final ContactContactService contactContactService;

  public ContactService(
      ContactRepository contactRepository,
      ContactContractService contactContractService,
      ContactRealestateService contactRealestateService,
      ContactFinanceAccountService contactFinanceAccountService,
      ContactMobilityService contactMobilityService,
      ContactContactService contactContactService) {
    this.contactRepository = contactRepository;
    this.contactContractService = contactContractService;
    this.contactRealestateService = contactRealestateService;
    this.contactFinanceAccountService = contactFinanceAccountService;
    this.contactMobilityService = contactMobilityService;
    this.contactContactService = contactContactService;
  }

  /**
   * Save a contact.
   *
   * @param contact the entity to save.
   * @return the persisted entity.
   */
  public Contact save(Contact contact) {
    log.debug("Request to save Contact : {}", contact);
    return contactRepository.save(contact);
  }

  /**
   * Update a contact.
   *
   * @param contact the entity to save.
   * @return the persisted entity.
   */
  public Contact update(Contact contact) {
    log.debug("Request to save Contact : {}", contact);
    return contactRepository.save(contact);
  }

  /**
   * Partially update a contact.
   *
   * @param contact the entity to update partially.
   * @return the persisted entity.
   */
  public Optional<Contact> partialUpdate(Contact contact) {
    log.debug("Request to partially update Contact : {}", contact);

    return contactRepository
      .findById(contact.getId())
      .map(existingContact -> {
        if (contact.getTitle() != null) {
          existingContact.setTitle(contact.getTitle());
        }
        if (contact.getSalutation() != null) {
          existingContact.setSalutation(contact.getSalutation());
        }
        if (contact.getRank() != null) {
          existingContact.setRank(contact.getRank());
        }
        if (contact.getGender() != null) {
          existingContact.setGender(contact.getGender());
        }
        if (contact.getSurName() != null) {
          existingContact.setSurName(contact.getSurName());
        }
        if (contact.getFirstName() != null) {
          existingContact.setFirstName(contact.getFirstName());
        }
        if (contact.getOtherFirstNames() != null) {
          existingContact.setOtherFirstNames(contact.getOtherFirstNames());
        }
        if (contact.getBirthName() != null) {
          existingContact.setBirthName(contact.getBirthName());
        }
        if (contact.getMaritalStatus() != null) {
          existingContact.setMaritalStatus(contact.getMaritalStatus());
        }
        if (contact.getNickName() != null) {
          existingContact.setNickName(contact.getNickName());
        }
        if (contact.getBirthDate() != null) {
          existingContact.setBirthDate(contact.getBirthDate());
        }
        if (contact.getBirthCity() != null) {
          existingContact.setBirthCity(contact.getBirthCity());
        }
        if (contact.getBirthCountry() != null) {
          existingContact.setBirthCountry(contact.getBirthCountry());
        }
        if (contact.getNationality() != null) {
          existingContact.setNationality(contact.getNationality());
        }
        if (contact.getAddressType() != null) {
          existingContact.setAddressType(contact.getAddressType());
        }
        if (contact.getStreet() != null) {
          existingContact.setStreet(contact.getStreet());
        }
        if (contact.getStreetNumber() != null) {
          existingContact.setStreetNumber(contact.getStreetNumber());
        }
        if (contact.getPostalCode() != null) {
          existingContact.setPostalCode(contact.getPostalCode());
        }
        if (contact.getCity() != null) {
          existingContact.setCity(contact.getCity());
        }
        if (contact.getAdditionalAddressField1() != null) {
          existingContact.setAdditionalAddressField1(
            contact.getAdditionalAddressField1()
          );
        }
        if (contact.getAdditionalAddressField2() != null) {
          existingContact.setAdditionalAddressField2(
            contact.getAdditionalAddressField2()
          );
        }
        if (contact.getCommunicationType() != null) {
          existingContact.setCommunicationType(contact.getCommunicationType());
        }
        if (contact.getPhoneCountryCode() != null) {
          existingContact.setPhoneCountryCode(contact.getPhoneCountryCode());
        }
        if (contact.getPhonePrefix() != null) {
          existingContact.setPhonePrefix(contact.getPhonePrefix());
        }
        if (contact.getPhoneNumber() != null) {
          existingContact.setPhoneNumber(contact.getPhoneNumber());
        }
        if (contact.getComment() != null) {
          existingContact.setComment(contact.getComment());
        }

        return existingContact;
      })
      .map(contactRepository::save);
  }

  /**
   * Get all the contacts.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Page<Contact> findAll(Pageable pageable) {
    log.debug("Request to get all Contacts");
    return contactRepository.findAll(pageable);
  }

  /**
   * Get one contact by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Optional<Contact> findOne(Long id) {
    log.debug("Request to get Contact : {}", id);
    return contactRepository.findById(id);
  }

  /**
   * Delete the contact by id.
   *
   * @param id the id of the entity.
   */
  @Transactional
  public void delete(Long id) {
    log.debug("Request to delete Contact : {}", id);
    
    // Finde den Kontakt mit allen Beziehungen
    Contact contact = contactRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Contact not found with id: " + id));
    
    // Lösche alle Beziehungen vor dem Löschen des Kontakts
    deleteContactRelationships(contact);
    
    // Lösche den Kontakt
    contactRepository.deleteById(id);
  }
  
  /**
   * Delete all relationships for a contact.
   *
   * @param contact the contact to delete relationships for.
   */
  private void deleteContactRelationships(Contact contact) {
    log.debug("Request to delete all relationships for Contact : {}", contact.getId());
    
    // Lösche ContactContract-Beziehungen
    try {
      contactContractService.deleteRelationships(contact);
    } catch (Exception e) {
      log.warn("Error deleting ContactContract relationships for Contact {}: {}", contact.getId(), e.getMessage());
    }
    
    // Lösche ContactRealestate-Beziehungen
    try {
      contactRealestateService.deleteRelationships(contact);
    } catch (Exception e) {
      log.warn("Error deleting ContactRealestate relationships for Contact {}: {}", contact.getId(), e.getMessage());
    }
    
    // Lösche ContactFinanceAccount-Beziehungen
    try {
      contactFinanceAccountService.deleteRelationships(contact);
    } catch (Exception e) {
      log.warn("Error deleting ContactFinanceAccount relationships for Contact {}: {}", contact.getId(), e.getMessage());
    }
    
    // Lösche ContactMobility-Beziehungen
    try {
      contactMobilityService.deleteRelationships(contact);
    } catch (Exception e) {
      log.warn("Error deleting ContactMobility relationships for Contact {}: {}", contact.getId(), e.getMessage());
    }
    
    // ContactContact-Beziehungen werden nicht gelöscht, da die Tabelle contact_contact nicht existiert
    log.debug("Skipping ContactContact relationship deletion - table does not exist");
  }

  /**
   * Get all contacts for an application user with full relationships.
   *
   * @param applicationUser the application user.
   * @return the list of contacts with full relationships.
   */
  @Transactional(readOnly = true)
  public List<Contact> findAllByApplicationUser(ApplicationUser applicationUser) {
    log.debug("Request to get all Contacts for ApplicationUser : {}", applicationUser.getId());
    return contactRepository.findAllByApplicationUser(applicationUser);
  }

  /**
   * Get all contacts for an application user optimized for overview (without heavy relationships).
   *
   * @param applicationUser the application user.
   * @return the list of contacts optimized for overview.
   */
  @Transactional(readOnly = true)
  public List<Contact> findAllByApplicationUserForOverview(ApplicationUser applicationUser) {
    log.debug("Request to get all Contacts for overview for ApplicationUser : {}", applicationUser.getId());
    return contactRepository.findAllByApplicationUserForOverview(applicationUser);
  }
}
