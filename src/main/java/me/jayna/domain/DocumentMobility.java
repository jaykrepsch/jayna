package me.jayna.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DocumentMobility.
 */
// @Entity
// @Table(name = "document_mobility")
// @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DocumentMobility implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "document_id")
  @JsonIgnoreProperties(value = { "applicationUser", "documentMobilities" }, allowSetters = true)
  private Document document;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mobility_id")
  @JsonIgnoreProperties(value = { "applicationUser", "mobilityDocuments" }, allowSetters = true)
  private Mobility mobility;

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public DocumentMobility id(Long id) {
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

  public DocumentMobility document(Document document) {
    this.setDocument(document);
    return this;
  }

  public Mobility getMobility() {
    return this.mobility;
  }

  public void setMobility(Mobility mobility) {
    this.mobility = mobility;
  }

  public DocumentMobility mobility(Mobility mobility) {
    this.setMobility(mobility);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
  // setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DocumentMobility)) {
      return false;
    }
    return id != null && id.equals(((DocumentMobility) o).id);
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
    return "DocumentMobility{" +
        "id=" + getId() +
        "}";
  }
}
