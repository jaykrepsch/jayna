package me.jayna.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DocumentContact.
 */
// @Entity
// @Table(name = "document_contact")
// @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DocumentContact implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "document_id")
  @JsonIgnoreProperties(value = { "applicationUser", "documentContacts" }, allowSetters = true)
  private Document document;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "contact_id")
  @JsonIgnoreProperties(value = { "applicationUser", "contactDocuments" }, allowSetters = true)
  private Contact contact;

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public DocumentContact id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Document getDocument() {
    return this.document;
  }

  public void setDocument(Document document) {
    this.document = document;
  }

  public DocumentContact document(Document document) {
    this.setDocument(document);
    return this;
  }

  public Contact getContact() {
    return this.contact;
  }

  public void setContact(Contact contact) {
    this.contact = contact;
  }

  public DocumentContact contact(Contact contact) {
    this.setContact(contact);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
  // setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DocumentContact)) {
      return false;
    }
    return id != null && id.equals(((DocumentContact) o).id);
  }

  @Override
  public int hashCode() {
    // see
    // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "DocumentContact{" +
        "id=" + getId() +
        "}";
  }
}
