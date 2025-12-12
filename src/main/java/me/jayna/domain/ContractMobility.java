package me.jayna.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ContractMobility.
 */
@Entity
@Table(name = "contract_mobility")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContractMobility implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "contract_id")
  @JsonIgnoreProperties(value = { "applicationUser", "contractMobilities" }, allowSetters = true)
  private Contract contract;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mobility_id")
  @JsonIgnoreProperties(value = { "applicationUser", "contractMobilities" }, allowSetters = true)
  private Mobility mobility;

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public ContractMobility id(Long id) {
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

  public ContractMobility contract(Contract contract) {
    this.setContract(contract);
    return this;
  }

  public Mobility getMobility() {
    return this.mobility;
  }

  public void setMobility(Mobility mobility) {
    this.mobility = mobility;
  }

  public ContractMobility mobility(Mobility mobility) {
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
    if (!(o instanceof ContractMobility)) {
      return false;
    }
    return id != null && id.equals(((ContractMobility) o).id);
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
    return "ContractMobility{" +
        "id=" + getId() +
        "}";
  }
}
