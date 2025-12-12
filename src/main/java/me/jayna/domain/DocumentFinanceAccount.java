package me.jayna.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DocumentFinanceAccount.
 */
// @Entity
// @Table(name = "document_finance_account")
// @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DocumentFinanceAccount implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "document_id")
  @JsonIgnoreProperties(value = { "applicationUser", "documentFinanceAccounts" }, allowSetters = true)
  private Document document;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "finance_account_id")
  @JsonIgnoreProperties(value = { "applicationUser", "financeAccountDocuments" }, allowSetters = true)
  private FinanceAccount financeAccount;

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public DocumentFinanceAccount id(Long id) {
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

  public DocumentFinanceAccount document(Document document) {
    this.setDocument(document);
    return this;
  }

  public FinanceAccount getFinanceAccount() {
    return this.financeAccount;
  }

  public void setFinanceAccount(FinanceAccount financeAccount) {
    this.financeAccount = financeAccount;
  }

  public DocumentFinanceAccount financeAccount(FinanceAccount financeAccount) {
    this.setFinanceAccount(financeAccount);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
  // setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DocumentFinanceAccount)) {
      return false;
    }
    return id != null && id.equals(((DocumentFinanceAccount) o).id);
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
    return "DocumentFinanceAccount{" +
        "id=" + getId() +
        "}";
  }
}
