package me.jayna.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ContractFinanceAccount.
 */
@Entity
@Table(name = "contract_financeaccount")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContractFinanceAccount implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "contract_id")
  @JsonIgnoreProperties(value = { "applicationUser", "contractFinanceaccounts" }, allowSetters = true)
  private Contract contract;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "finance_account_id")
  @JsonIgnoreProperties(value = { "applicationUser", "contractFinanceAccounts" }, allowSetters = true)
  private FinanceAccount financeaccount;

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public ContractFinanceAccount id(Long id) {
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

  public ContractFinanceAccount contract(Contract contract) {
    this.setContract(contract);
    return this;
  }

  public FinanceAccount getFinanceaccount() {
    return this.financeaccount;
  }

  public void setFinanceaccount(FinanceAccount financeaccount) {
    this.financeaccount = financeaccount;
  }

  public ContractFinanceAccount financeaccount(FinanceAccount financeaccount) {
    this.setFinanceaccount(financeaccount);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
  // setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ContractFinanceAccount)) {
      return false;
    }
    return id != null && id.equals(((ContractFinanceAccount) o).id);
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
    return "ContractFinanceAccount{" +
        "id=" + getId() +
        "}";
  }
}
