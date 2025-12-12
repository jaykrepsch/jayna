package me.jayna.repository;

import me.jayna.domain.Contact;
import me.jayna.domain.ContactContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactContactRepository extends JpaRepository<ContactContact, Long> {
    List<ContactContact> findAllByContact(Contact contact);
    List<ContactContact> findAllByRelatedContact(Contact contact);
    void deleteAllByContact(Contact contact);
    void deleteAllByRelatedContact(Contact contact);
} 