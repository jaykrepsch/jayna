package me.jayna.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FinanceAccount.
 */
@Entity
@Table(name = "finance_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedEntityGraph(name = "FinanceAccount.graph", attributeNodes = {
    @NamedAttributeNode(value = "groupType", subgraph = "groupType-subgraph"),
    @NamedAttributeNode(value = "contactFinanceAccounts", subgraph = "contactFinanceAccounts-subgraph"),
    @NamedAttributeNode(value = "contractFinanceAccounts", subgraph = "contractFinanceAccounts-subgraph"),
    @NamedAttributeNode(value = "financeaccountMobilities", subgraph = "financeaccountMobilities-subgraph"),
    @NamedAttributeNode(value = "financeaccountRealestates", subgraph = "financeaccountRealestates-subgraph"),
}, subgraphs = {
    @NamedSubgraph(name = "groupType-subgraph", attributeNodes = {
        @NamedAttributeNode(value = "subCategoryGroup", subgraph = "subCategoryGroup-subgraph")
    }),
    @NamedSubgraph(name = "subCategoryGroup-subgraph", attributeNodes = {
        @NamedAttributeNode("subCategory")
    }),
    @NamedSubgraph(name = "contactFinanceAccounts-subgraph", attributeNodes = {
        @NamedAttributeNode("contact")
    }),
    @NamedSubgraph(name = "contractFinanceAccounts-subgraph", attributeNodes = {
        @NamedAttributeNode("contract")
    }),
    @NamedSubgraph(name = "financeaccountMobilities-subgraph", attributeNodes = {
        @NamedAttributeNode("mobility")
    }),
    @NamedSubgraph(name = "financeaccountRealestates-subgraph", attributeNodes = {
        @NamedAttributeNode("realestate")
    })
})
@NamedEntityGraph(name = "FinanceAccount.overview", attributeNodes = {
    @NamedAttributeNode(value = "groupType", subgraph = "groupType-overview-subgraph")
}, subgraphs = {
    @NamedSubgraph(name = "groupType-overview-subgraph", attributeNodes = {
        @NamedAttributeNode(value = "subCategoryGroup", subgraph = "subCategoryGroup-overview-subgraph")
    }),
    @NamedSubgraph(name = "subCategoryGroup-overview-subgraph", attributeNodes = {
        @NamedAttributeNode(value = "subCategory", subgraph = "subCategory-overview-subgraph")
    }),
    @NamedSubgraph(name = "subCategory-overview-subgraph", attributeNodes = {
        @NamedAttributeNode("category")
    })
})

public class FinanceAccount extends AbstractCategoryEntity {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @Column(name = "start_date")
  private LocalDate startDate;

  @Column(name = "end_date")
  private LocalDate endDate;

  @Column(name = "account_giver")
  private String accountGiver;

  @Column(name = "account_user")
  private String accountUser;

  @Column(name = "iban")
  private String iban;

  @Column(name = "bic")
  private String bic;

  @Column(name = "account_co_rent")
  private String accountCoRent;

  @Column(name = "overdraft_interest")
  private Double overdraftInterest;

  @Column(name = "jhi_limit")
  private Double limit;

  @Column(name = "payment_until")
  private LocalDate paymentUntil;

  @Column(name = "card_fee")
  private Double cardFee;

  @Column(name = "loan_interest")
  private Double loanInterest;

  @Column(name = "account_maintenance_fee")
  private Double accountMaintenanceFee;

  @Column(name = "account_number")
  private String accountNumber;

  @Column(name = "bank_number")
  private String bankNumber;

  @OneToMany(mappedBy = "financeaccount", fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "financeaccount" }, allowSetters = true)
  private Set<ContactFinanceAccount> contactFinanceAccounts = new HashSet<>();

  @OneToMany(mappedBy = "financeaccount", fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "financeaccount" }, allowSetters = true)
  private Set<ContractFinanceAccount> contractFinanceAccounts = new HashSet<>();

  @OneToMany(mappedBy = "financeaccount", fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "financeaccount" }, allowSetters = true)
  private Set<FinanceAccountMobility> financeaccountMobilities = new HashSet<>();

  @OneToMany(mappedBy = "financeaccount", fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "financeaccount" }, allowSetters = true)
  private Set<FinanceAccountRealestate> financeaccountRealestates = new HashSet<>();

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public FinanceAccount id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getStartDate() {
    return this.startDate;
  }

  public FinanceAccount startDate(LocalDate startDate) {
    this.setStartDate(startDate);
    return this;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return this.endDate;
  }

  public FinanceAccount endDate(LocalDate endDate) {
    this.setEndDate(endDate);
    return this;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public String getAccountGiver() {
    return this.accountGiver;
  }

  public FinanceAccount accountGiver(String accountGiver) {
    this.setAccountGiver(accountGiver);
    return this;
  }

  public void setAccountGiver(String accountGiver) {
    this.accountGiver = accountGiver;
  }

  public String getAccountUser() {
    return this.accountUser;
  }

  public FinanceAccount accountUser(String accountUser) {
    this.setAccountUser(accountUser);
    return this;
  }

  public void setAccountUser(String accountUser) {
    this.accountUser = accountUser;
  }

  public String getIban() {
    return this.iban;
  }

  public FinanceAccount iban(String iban) {
    this.setIban(iban);
    return this;
  }

  public void setIban(String iban) {
    this.iban = iban;
  }

  public String getBic() {
    return this.bic;
  }

  public FinanceAccount bic(String bic) {
    this.setBic(bic);
    return this;
  }

  public void setBic(String bic) {
    this.bic = bic;
  }

  public String getAccountCoRent() {
    return this.accountCoRent;
  }

  public void setAccountCoRent(String accountCoRent) {
    this.accountCoRent = accountCoRent;
  }

  public Double getOverdraftInterest() {
    return this.overdraftInterest;
  }

  public FinanceAccount overdraftInterest(Double overdraftInterest) {
    this.setOverdraftInterest(overdraftInterest);
    return this;
  }

  public void setOverdraftInterest(Double overdraftInterest) {
    this.overdraftInterest = overdraftInterest;
  }

  public Double getLimit() {
    return this.limit;
  }

  public FinanceAccount limit(Double limit) {
    this.setLimit(limit);
    return this;
  }

  public void setLimit(Double limit) {
    this.limit = limit;
  }

  public LocalDate getPaymentUntil() {
    return this.paymentUntil;
  }

  public FinanceAccount paymentUntil(LocalDate paymentUntil) {
    this.setPaymentUntil(paymentUntil);
    return this;
  }

  public void setPaymentUntil(LocalDate paymentUntil) {
    this.paymentUntil = paymentUntil;
  }

  public Double getCardFee() {
    return this.cardFee;
  }

  public FinanceAccount cardFee(Double cardFee) {
    this.setCardFee(cardFee);
    return this;
  }

  public void setCardFee(Double cardFee) {
    this.cardFee = cardFee;
  }

  public Double getLoanInterest() {
    return this.loanInterest;
  }

  public FinanceAccount loanInterest(Double loanInterest) {
    this.setLoanInterest(loanInterest);
    return this;
  }

  public void setLoanInterest(Double loanInterest) {
    this.loanInterest = loanInterest;
  }

  public Double getAccountMaintenanceFee() {
    return this.accountMaintenanceFee;
  }

  public FinanceAccount accountMaintenanceFee(Double accountMaintenanceFee) {
    this.setAccountMaintenanceFee(accountMaintenanceFee);
    return this;
  }

  public void setAccountMaintenanceFee(Double accountMaintenanceFee) {
    this.accountMaintenanceFee = accountMaintenanceFee;
  }

  public String getAccountNumber() {
    return this.accountNumber;
  }

  public FinanceAccount accountNumber(String accountNumber) {
    this.setAccountNumber(accountNumber);
    return this;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getBankNumber() {
    return this.bankNumber;
  }

  public FinanceAccount bankNumber(String bankNumber) {
    this.setBankNumber(bankNumber);
    return this;
  }

  public void setBankNumber(String bankNumber) {
    this.bankNumber = bankNumber;
  }

  public Set<ContactFinanceAccount> getContactFinanceAccounts() {
    return this.contactFinanceAccounts;
  }

  public void setContactFinanceAccounts(
      Set<ContactFinanceAccount> contactFinanceAccounts) {
    if (this.contactFinanceAccounts != null) {
      this.contactFinanceAccounts.forEach(i -> i.setFinanceaccount(null));
    }
    if (contactFinanceAccounts != null) {
      contactFinanceAccounts.forEach(i -> i.setFinanceaccount(this));
    }
    this.contactFinanceAccounts = contactFinanceAccounts;
  }

  public FinanceAccount contactFinanceAccounts(
      Set<ContactFinanceAccount> contactFinanceAccounts) {
    this.setContactFinanceAccounts(contactFinanceAccounts);
    return this;
  }

  public FinanceAccount addContactFinanceAccount(
      ContactFinanceAccount contactFinanceAccount) {
    this.contactFinanceAccounts.add(contactFinanceAccount);
    contactFinanceAccount.setFinanceaccount(this);
    return this;
  }

  public FinanceAccount removeContactFinanceAccount(
      ContactFinanceAccount contactFinanceAccount) {
    this.contactFinanceAccounts.remove(contactFinanceAccount);
    contactFinanceAccount.setFinanceaccount(null);
    return this;
  }

  public Set<ContractFinanceAccount> getContractFinanceAccounts() {
    return this.contractFinanceAccounts;
  }

  public void setContractFinanceAccounts(
      Set<ContractFinanceAccount> contractFinanceAccounts) {
    if (this.contractFinanceAccounts != null) {
      this.contractFinanceAccounts.forEach(i -> i.setFinanceaccount(null));
    }
    if (contractFinanceAccounts != null) {
      contractFinanceAccounts.forEach(i -> i.setFinanceaccount(this));
    }
    this.contractFinanceAccounts = contractFinanceAccounts;
  }

  public FinanceAccount contractFinanceAccounts(
      Set<ContractFinanceAccount> contractFinanceAccounts) {
    this.setContractFinanceAccounts(contractFinanceAccounts);
    return this;
  }

  public FinanceAccount addContractFinanceAccount(
      ContractFinanceAccount contractFinanceAccount) {
    this.contractFinanceAccounts.add(contractFinanceAccount);
    contractFinanceAccount.setFinanceaccount(this);
    return this;
  }

  public FinanceAccount removeContractFinanceAccount(
      ContractFinanceAccount contractFinanceAccount) {
    this.contractFinanceAccounts.remove(contractFinanceAccount);
    contractFinanceAccount.setFinanceaccount(null);
    return this;
  }

  public Set<FinanceAccountMobility> getFinanceaccountMobilities() {
    return this.financeaccountMobilities;
  }

  public void setFinanceaccountMobilities(
      Set<FinanceAccountMobility> financeaccountMobilities) {
    if (this.financeaccountMobilities != null) {
      this.financeaccountMobilities.forEach(i -> i.setFinanceaccount(null));
    }
    if (financeaccountMobilities != null) {
      financeaccountMobilities.forEach(i -> i.setFinanceaccount(this));
    }
    this.financeaccountMobilities = financeaccountMobilities;
  }

  public FinanceAccount financeaccountMobilities(
      Set<FinanceAccountMobility> financeaccountMobilities) {
    this.setFinanceaccountMobilities(financeaccountMobilities);
    return this;
  }

  public FinanceAccount addFinanceaccountMobilities(
      FinanceAccountMobility financeaccountMobility) {
    this.financeaccountMobilities.add(financeaccountMobility);
    financeaccountMobility.setFinanceaccount(this);
    return this;
  }

  public FinanceAccount removeFinanceaccountMobilities(
      FinanceAccountMobility financeaccountMobility) {
    this.financeaccountMobilities.remove(financeaccountMobility);
    financeaccountMobility.setFinanceaccount(null);
    return this;
  }

  public Set<FinanceAccountRealestate> getFinanceaccountRealestates() {
    return this.financeaccountRealestates;
  }

  public void setFinanceaccountRealestates(
      Set<FinanceAccountRealestate> financeaccountRealestates) {
    if (this.financeaccountRealestates != null) {
      this.financeaccountRealestates.forEach(i -> i.setFinanceaccount(null));
    }
    if (financeaccountRealestates != null) {
      financeaccountRealestates.forEach(i -> i.setFinanceaccount(this));
    }
    this.financeaccountRealestates = financeaccountRealestates;
  }

  public FinanceAccount financeaccountRealestates(
      Set<FinanceAccountRealestate> financeaccountRealestates) {
    this.setFinanceaccountRealestates(financeaccountRealestates);
    return this;
  }

  public FinanceAccount addFinanceaccountMobilities(
      FinanceAccountRealestate financeaccountRealestate) {
    this.financeaccountRealestates.add(financeaccountRealestate);
    financeaccountRealestate.setFinanceaccount(this);
    return this;
  }

  public FinanceAccount removeFinanceaccountMobilities(
      FinanceAccountRealestate financeaccountRealestate) {
    this.financeaccountRealestates.remove(financeaccountRealestate);
    financeaccountRealestate.setFinanceaccount(null);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
  // setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof FinanceAccount)) {
      return false;
    }
    return id != null && id.equals(((FinanceAccount) o).id);
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
    return "FinanceAccount{" +
        "id=" + getId() +
        ", startDate='" + getStartDate() + "'" +
        ", endDate='" + getEndDate() + "'" +
        ", accountGiver='" + getAccountGiver() + "'" +
        ", accountUser='" + getAccountUser() + "'" +
        ", iban='" + getIban() + "'" +
        ", bic='" + getBic() + "'" +
        ", overdraftInterest=" + getOverdraftInterest() +
        ", limit=" + getLimit() +
        ", paymentUntil='" + getPaymentUntil() + "'" +
        ", cardFee=" + getCardFee() +
        ", loanInterest=" + getLoanInterest() +
        ", accountMaintenanceFee=" + getAccountMaintenanceFee() +
        ", accountNumber='" + getAccountNumber() + "'" +
        ", bankNumber='" + getBankNumber() + "'" +
        "}";
  }
}
