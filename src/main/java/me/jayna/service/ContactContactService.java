package me.jayna.service;

import me.jayna.domain.Contact;
import me.jayna.domain.ContactContact;
import me.jayna.repository.ContactContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContactContactService {

    private final Logger log = LoggerFactory.getLogger(ContactContactService.class);

    private final ContactContactRepository contactContactRepository;

    public ContactContactService(ContactContactRepository contactContactRepository) {
        this.contactContactRepository = contactContactRepository;
    }

    public ContactContact save(ContactContact contactContact) {
        log.debug("Request to save ContactContact : {}", contactContact);
        return contactContactRepository.save(contactContact);
    }

    public List<ContactContact> findAllByContact(Contact contact) {
        log.debug("Request to get all ContactContacts for Contact : {}", contact);
        return contactContactRepository.findAllByContact(contact);
    }

    public List<ContactContact> findAllByRelatedContact(Contact contact) {
        log.debug("Request to get all ContactContacts for Related Contact : {}", contact);
        return contactContactRepository.findAllByRelatedContact(contact);
    }

    public Optional<ContactContact> findOne(Long id) {
        log.debug("Request to get ContactContact : {}", id);
        return contactContactRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete ContactContact : {}", id);
        contactContactRepository.deleteById(id);
    }

    public void deleteAllByContact(Contact contact) {
        log.debug("Request to delete all ContactContacts for Contact : {}", contact);
        contactContactRepository.deleteAllByContact(contact);
    }

    public void deleteAllByRelatedContact(Contact contact) {
        log.debug("Request to delete all ContactContacts for Related Contact : {}", contact);
        contactContactRepository.deleteAllByRelatedContact(contact);
    }
} 