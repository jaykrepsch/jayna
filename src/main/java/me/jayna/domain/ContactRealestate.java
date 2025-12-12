package me.jayna.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ContactRealestate.
 */
@Entity
@Table(name = "contact_realestate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContactRealestate implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "contact_id")
  @JsonIgnoreProperties(value = { "applicationUser", "contactRealestates" }, allowSetters = true)
  private Contact contact;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "realestate_id")
  @JsonIgnoreProperties(value = { "applicationUser", "contactRealestates" }, allowSetters = true)
  private RealEstate realestate;

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public ContactRealestate id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Contact getContact() {
    return this.contact;
  }

  public void setContact(Contact contact) {
    this.contact = contact;
  }

  public ContactRealestate contact(Contact contact) {
    this.setContact(contact);
    return this;
  }

  public RealEstate getRealestate() {
    return this.realestate;
  }

  public void setRealestate(RealEstate realestate) {
    this.realestate = realestate;
  }

  public ContactRealestate contract(RealEstate realestate) {
    this.setRealestate(realestate);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
  // setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ContactRealestate)) {
      return false;
    }
    return id != null && id.equals(((ContactRealestate) o).id);
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
    return "ContactRealestate{" +
        "id=" + getId() +
        "}";
  }
}
