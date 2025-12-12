package me.jayna.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link me.jayna.domain.BankAccount} entity. This class is used
 * in {@link me.jayna.web.rest.BankAccountResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bank-accounts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class BankAccountCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private LocalDateFilter startDate;

  private LocalDateFilter endDate;

  private StringFilter accountGiver;

  private StringFilter accountUser;

  private StringFilter iban;

  private StringFilter bic;

  private StringFilter currentAccount;

  private DoubleFilter overdraftInterest;

  private DoubleFilter limit;

  private LocalDateFilter paymentUntil;

  private DoubleFilter cardFee;

  private DoubleFilter loanInterest;

  private DoubleFilter accountMaintenanceFee;

  private StringFilter accountNumber;

  private StringFilter bankNumber;

  private LongFilter applicationUserId;

  private LongFilter groupTypeId;

  private LongFilter contactBankAccountId;

  private Boolean distinct;

  public BankAccountCriteria() {}

  public BankAccountCriteria(BankAccountCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.startDate = other.startDate == null ? null : other.startDate.copy();
    this.endDate = other.endDate == null ? null : other.endDate.copy();
    this.accountGiver =
      other.accountGiver == null ? null : other.accountGiver.copy();
    this.accountUser =
      other.accountUser == null ? null : other.accountUser.copy();
    this.iban = other.iban == null ? null : other.iban.copy();
    this.bic = other.bic == null ? null : other.bic.copy();
    this.currentAccount =
      other.currentAccount == null ? null : other.currentAccount.copy();
    this.overdraftInterest =
      other.overdraftInterest == null ? null : other.overdraftInterest.copy();
    this.limit = other.limit == null ? null : other.limit.copy();
    this.paymentUntil =
      other.paymentUntil == null ? null : other.paymentUntil.copy();
    this.cardFee = other.cardFee == null ? null : other.cardFee.copy();
    this.loanInterest =
      other.loanInterest == null ? null : other.loanInterest.copy();
    this.accountMaintenanceFee =
      other.accountMaintenanceFee == null
        ? null
        : other.accountMaintenanceFee.copy();
    this.accountNumber =
      other.accountNumber == null ? null : other.accountNumber.copy();
    this.bankNumber = other.bankNumber == null ? null : other.bankNumber.copy();
    this.applicationUserId =
      other.applicationUserId == null ? null : other.applicationUserId.copy();
    this.groupTypeId =
      other.groupTypeId == null ? null : other.groupTypeId.copy();
    this.contactBankAccountId =
      other.contactBankAccountId == null
        ? null
        : other.contactBankAccountId.copy();
    this.distinct = other.distinct;
  }

  @Override
  public BankAccountCriteria copy() {
    return new BankAccountCriteria(this);
  }

  public LongFilter getId() {
    return id;
  }

  public LongFilter id() {
    if (id == null) {
      id = new LongFilter();
    }
    return id;
  }

  public void setId(LongFilter id) {
    this.id = id;
  }

  public LocalDateFilter getStartDate() {
    return startDate;
  }

  public LocalDateFilter startDate() {
    if (startDate == null) {
      startDate = new LocalDateFilter();
    }
    return startDate;
  }

  public void setStartDate(LocalDateFilter startDate) {
    this.startDate = startDate;
  }

  public LocalDateFilter getEndDate() {
    return endDate;
  }

  public LocalDateFilter endDate() {
    if (endDate == null) {
      endDate = new LocalDateFilter();
    }
    return endDate;
  }

  public void setEndDate(LocalDateFilter endDate) {
    this.endDate = endDate;
  }

  public StringFilter getAccountGiver() {
    return accountGiver;
  }

  public StringFilter accountGiver() {
    if (accountGiver == null) {
      accountGiver = new StringFilter();
    }
    return accountGiver;
  }

  public void setAccountGiver(StringFilter accountGiver) {
    this.accountGiver = accountGiver;
  }

  public StringFilter getAccountUser() {
    return accountUser;
  }

  public StringFilter accountUser() {
    if (accountUser == null) {
      accountUser = new StringFilter();
    }
    return accountUser;
  }

  public void setAccountUser(StringFilter accountUser) {
    this.accountUser = accountUser;
  }

  public StringFilter getIban() {
    return iban;
  }

  public StringFilter iban() {
    if (iban == null) {
      iban = new StringFilter();
    }
    return iban;
  }

  public void setIban(StringFilter iban) {
    this.iban = iban;
  }

  public StringFilter getBic() {
    return bic;
  }

  public StringFilter bic() {
    if (bic == null) {
      bic = new StringFilter();
    }
    return bic;
  }

  public void setBic(StringFilter bic) {
    this.bic = bic;
  }

  public StringFilter getCurrentAccount() {
    return currentAccount;
  }

  public StringFilter currentAccount() {
    if (currentAccount == null) {
      currentAccount = new StringFilter();
    }
    return currentAccount;
  }

  public void setCurrentAccount(StringFilter currentAccount) {
    this.currentAccount = currentAccount;
  }

  public DoubleFilter getOverdraftInterest() {
    return overdraftInterest;
  }

  public DoubleFilter overdraftInterest() {
    if (overdraftInterest == null) {
      overdraftInterest = new DoubleFilter();
    }
    return overdraftInterest;
  }

  public void setOverdraftInterest(DoubleFilter overdraftInterest) {
    this.overdraftInterest = overdraftInterest;
  }

  public DoubleFilter getLimit() {
    return limit;
  }

  public DoubleFilter limit() {
    if (limit == null) {
      limit = new DoubleFilter();
    }
    return limit;
  }

  public void setLimit(DoubleFilter limit) {
    this.limit = limit;
  }

  public LocalDateFilter getPaymentUntil() {
    return paymentUntil;
  }

  public LocalDateFilter paymentUntil() {
    if (paymentUntil == null) {
      paymentUntil = new LocalDateFilter();
    }
    return paymentUntil;
  }

  public void setPaymentUntil(LocalDateFilter paymentUntil) {
    this.paymentUntil = paymentUntil;
  }

  public DoubleFilter getCardFee() {
    return cardFee;
  }

  public DoubleFilter cardFee() {
    if (cardFee == null) {
      cardFee = new DoubleFilter();
    }
    return cardFee;
  }

  public void setCardFee(DoubleFilter cardFee) {
    this.cardFee = cardFee;
  }

  public DoubleFilter getLoanInterest() {
    return loanInterest;
  }

  public DoubleFilter loanInterest() {
    if (loanInterest == null) {
      loanInterest = new DoubleFilter();
    }
    return loanInterest;
  }

  public void setLoanInterest(DoubleFilter loanInterest) {
    this.loanInterest = loanInterest;
  }

  public DoubleFilter getAccountMaintenanceFee() {
    return accountMaintenanceFee;
  }

  public DoubleFilter accountMaintenanceFee() {
    if (accountMaintenanceFee == null) {
      accountMaintenanceFee = new DoubleFilter();
    }
    return accountMaintenanceFee;
  }

  public void setAccountMaintenanceFee(DoubleFilter accountMaintenanceFee) {
    this.accountMaintenanceFee = accountMaintenanceFee;
  }

  public StringFilter getAccountNumber() {
    return accountNumber;
  }

  public StringFilter accountNumber() {
    if (accountNumber == null) {
      accountNumber = new StringFilter();
    }
    return accountNumber;
  }

  public void setAccountNumber(StringFilter accountNumber) {
    this.accountNumber = accountNumber;
  }

  public StringFilter getBankNumber() {
    return bankNumber;
  }

  public StringFilter bankNumber() {
    if (bankNumber == null) {
      bankNumber = new StringFilter();
    }
    return bankNumber;
  }

  public void setBankNumber(StringFilter bankNumber) {
    this.bankNumber = bankNumber;
  }

  public LongFilter getApplicationUserId() {
    return applicationUserId;
  }

  public LongFilter applicationUserId() {
    if (applicationUserId == null) {
      applicationUserId = new LongFilter();
    }
    return applicationUserId;
  }

  public void setApplicationUserId(LongFilter applicationUserId) {
    this.applicationUserId = applicationUserId;
  }

  public LongFilter getGroupTypeId() {
    return groupTypeId;
  }

  public LongFilter groupTypeId() {
    if (groupTypeId == null) {
      groupTypeId = new LongFilter();
    }
    return groupTypeId;
  }

  public void setGroupTypeId(LongFilter groupTypeId) {
    this.groupTypeId = groupTypeId;
  }

  public LongFilter getContactBankAccountId() {
    return contactBankAccountId;
  }

  public LongFilter contactBankAccountId() {
    if (contactBankAccountId == null) {
      contactBankAccountId = new LongFilter();
    }
    return contactBankAccountId;
  }

  public void setContactBankAccountId(LongFilter contactBankAccountId) {
    this.contactBankAccountId = contactBankAccountId;
  }

  public Boolean getDistinct() {
    return distinct;
  }

  public void setDistinct(Boolean distinct) {
    this.distinct = distinct;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final BankAccountCriteria that = (BankAccountCriteria) o;
    return (
      Objects.equals(id, that.id) &&
      Objects.equals(startDate, that.startDate) &&
      Objects.equals(endDate, that.endDate) &&
      Objects.equals(accountGiver, that.accountGiver) &&
      Objects.equals(accountUser, that.accountUser) &&
      Objects.equals(iban, that.iban) &&
      Objects.equals(bic, that.bic) &&
      Objects.equals(currentAccount, that.currentAccount) &&
      Objects.equals(overdraftInterest, that.overdraftInterest) &&
      Objects.equals(limit, that.limit) &&
      Objects.equals(paymentUntil, that.paymentUntil) &&
      Objects.equals(cardFee, that.cardFee) &&
      Objects.equals(loanInterest, that.loanInterest) &&
      Objects.equals(accountMaintenanceFee, that.accountMaintenanceFee) &&
      Objects.equals(accountNumber, that.accountNumber) &&
      Objects.equals(bankNumber, that.bankNumber) &&
      Objects.equals(applicationUserId, that.applicationUserId) &&
      Objects.equals(groupTypeId, that.groupTypeId) &&
      Objects.equals(contactBankAccountId, that.contactBankAccountId) &&
      Objects.equals(distinct, that.distinct)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      id,
      startDate,
      endDate,
      accountGiver,
      accountUser,
      iban,
      bic,
      currentAccount,
      overdraftInterest,
      limit,
      paymentUntil,
      cardFee,
      loanInterest,
      accountMaintenanceFee,
      accountNumber,
      bankNumber,
      applicationUserId,
      groupTypeId,
      contactBankAccountId,
      distinct
    );
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "BankAccountCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (startDate != null ? "startDate=" + startDate + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (accountGiver != null ? "accountGiver=" + accountGiver + ", " : "") +
            (accountUser != null ? "accountUser=" + accountUser + ", " : "") +
            (iban != null ? "iban=" + iban + ", " : "") +
            (bic != null ? "bic=" + bic + ", " : "") +
            (currentAccount != null ? "currentAccount=" + currentAccount + ", " : "") +
            (overdraftInterest != null ? "overdraftInterest=" + overdraftInterest + ", " : "") +
            (limit != null ? "limit=" + limit + ", " : "") +
            (paymentUntil != null ? "paymentUntil=" + paymentUntil + ", " : "") +
            (cardFee != null ? "cardFee=" + cardFee + ", " : "") +
            (loanInterest != null ? "loanInterest=" + loanInterest + ", " : "") +
            (accountMaintenanceFee != null ? "accountMaintenanceFee=" + accountMaintenanceFee + ", " : "") +
            (accountNumber != null ? "accountNumber=" + accountNumber + ", " : "") +
            (bankNumber != null ? "bankNumber=" + bankNumber + ", " : "") +
            (applicationUserId != null ? "applicationUserId=" + applicationUserId + ", " : "") +
            (groupTypeId != null ? "groupTypeId=" + groupTypeId + ", " : "") +
            (contactBankAccountId != null ? "contactBankAccountId=" + contactBankAccountId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
