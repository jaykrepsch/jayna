package me.jayna.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ContractRealestate.
 */
@Entity
@Table(name = "contract_realestate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContractRealestate implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "applicationUser", "contractRealestates" }, allowSetters = true)
  private Contract contract;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "applicationUser", "contractRealestates" }, allowSetters = true)
  private RealEstate realestate;

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public ContractRealestate id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Contract getContract() {
    return this.contract;
  }

  public void setContract(Contract contract) {
    this.contract = contract;
  }

  public ContractRealestate contract(Contract contract) {
    this.setContract(contract);
    return this;
  }

  public RealEstate getRealestate() {
    return this.realestate;
  }

  public void setRealestate(RealEstate realEstate) {
    this.realestate = realEstate;
  }

  public ContractRealestate realestate(RealEstate realEstate) {
    this.setRealestate(realEstate);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
  // setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ContractRealestate)) {
      return false;
    }
    return id != null && id.equals(((ContractRealestate) o).id);
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
    return "ContractRealestate{" +
        "id=" + getId() +
        "}";
  }
}
