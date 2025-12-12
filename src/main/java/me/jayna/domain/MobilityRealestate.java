package me.jayna.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FinanceAccountRealEstate.
 */
@Entity
@Table(name = "mobility_real_estate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MobilityRealestate implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mobility_id")
  @JsonIgnoreProperties(value = { "applicationUser", "mobilityRealestates" }, allowSetters = true)
  private Mobility mobility;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "realestate_id")
  @JsonIgnoreProperties(value = { "applicationUser", "mobilityRealestates" }, allowSetters = true)
  private RealEstate realestate;

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public MobilityRealestate id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Mobility getMobility() {
    return this.mobility;
  }

  public void setMobility(Mobility mobility) {
    this.mobility = mobility;
  }

  public MobilityRealestate mobility(Mobility mobility) {
    this.setMobility(mobility);
    return this;
  }

  public RealEstate getRealestate() {
    return this.realestate;
  }

  public void setRealestate(RealEstate realestate) {
    this.realestate = realestate;
  }

  public MobilityRealestate realestate(RealEstate realestate) {
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
    if (!(o instanceof MobilityRealestate)) {
      return false;
    }
    return id != null && id.equals(((MobilityRealestate) o).id);
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
    return "MobilityRealestate{" +
        "id=" + getId() +
        "}";
  }
}
