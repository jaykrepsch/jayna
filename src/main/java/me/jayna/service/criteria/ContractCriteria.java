package me.jayna.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import me.jayna.domain.enumeration.PaymentPattern;
import me.jayna.domain.enumeration.PaymentType;
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
 * Criteria class for the {@link me.jayna.domain.Contract} entity. This class is used
 * in {@link me.jayna.web.rest.ContractResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /contracts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ContractCriteria implements Serializable, Criteria {

  /**
   * Class for filtering PaymentPattern
   */
  public static class PaymentPatternFilter extends Filter<PaymentPattern> {

    public PaymentPatternFilter() {}

    public PaymentPatternFilter(PaymentPatternFilter filter) {
      super(filter);
    }

    @Override
    public PaymentPatternFilter copy() {
      return new PaymentPatternFilter(this);
    }
  }

  /**
   * Class for filtering PaymentType
   */
  public static class PaymentTypeFilter extends Filter<PaymentType> {

    public PaymentTypeFilter() {}

    public PaymentTypeFilter(PaymentTypeFilter filter) {
      super(filter);
    }

    @Override
    public PaymentTypeFilter copy() {
      return new PaymentTypeFilter(this);
    }
  }

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter contractor;

  private StringFilter contractOwner;

  private StringFilter contractUser;

  private StringFilter contractNumber;

  private LocalDateFilter startDate;

  private LocalDateFilter endDate;

  private LocalDateFilter applicationDate;

  private LocalDateFilter confirmationDate;

  private PaymentPatternFilter paymentPattern;

  private StringFilter comment;

  private StringFilter payer;

  private PaymentTypeFilter paymentType;

  private DoubleFilter salary;

  private IntegerFilter vacationDays;

  private StringFilter occupation;

  private StringFilter activities;

  private DoubleFilter loanValue;

  private DoubleFilter interestRatePercent;

  private DoubleFilter interestRateCurrency;

  private DoubleFilter amortizationRatePercent;

  private DoubleFilter amortizationRateCurrency;

  private LongFilter applicationUserId;

  private LongFilter groupTypeId;

  private LongFilter contractRealestatesId;

  private Boolean distinct;

  public ContractCriteria() {}

  public ContractCriteria(ContractCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.contractor = other.contractor == null ? null : other.contractor.copy();
    this.contractOwner =
      other.contractOwner == null ? null : other.contractOwner.copy();
    this.contractUser =
      other.contractUser == null ? null : other.contractUser.copy();
    this.contractNumber =
      other.contractNumber == null ? null : other.contractNumber.copy();
    this.startDate = other.startDate == null ? null : other.startDate.copy();
    this.endDate = other.endDate == null ? null : other.endDate.copy();
    this.applicationDate =
      other.applicationDate == null ? null : other.applicationDate.copy();
    this.confirmationDate =
      other.confirmationDate == null ? null : other.confirmationDate.copy();
    this.paymentPattern =
      other.paymentPattern == null ? null : other.paymentPattern.copy();
    this.comment = other.comment == null ? null : other.comment.copy();
    this.payer = other.payer == null ? null : other.payer.copy();
    this.paymentType =
      other.paymentType == null ? null : other.paymentType.copy();
    this.salary = other.salary == null ? null : other.salary.copy();
    this.vacationDays =
      other.vacationDays == null ? null : other.vacationDays.copy();
    this.occupation = other.occupation == null ? null : other.occupation.copy();
    this.activities = other.activities == null ? null : other.activities.copy();
    this.loanValue = other.loanValue == null ? null : other.loanValue.copy();
    this.interestRatePercent =
      other.interestRatePercent == null
        ? null
        : other.interestRatePercent.copy();
    this.interestRateCurrency =
      other.interestRateCurrency == null
        ? null
        : other.interestRateCurrency.copy();
    this.amortizationRatePercent =
      other.amortizationRatePercent == null
        ? null
        : other.amortizationRatePercent.copy();
    this.amortizationRateCurrency =
      other.amortizationRateCurrency == null
        ? null
        : other.amortizationRateCurrency.copy();
    this.applicationUserId =
      other.applicationUserId == null ? null : other.applicationUserId.copy();
    this.groupTypeId =
      other.groupTypeId == null ? null : other.groupTypeId.copy();
    this.contractRealestatesId =
      other.contractRealestatesId == null
        ? null
        : other.contractRealestatesId.copy();
    this.distinct = other.distinct;
  }

  @Override
  public ContractCriteria copy() {
    return new ContractCriteria(this);
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

  public StringFilter getContractor() {
    return contractor;
  }

  public StringFilter contractor() {
    if (contractor == null) {
      contractor = new StringFilter();
    }
    return contractor;
  }

  public void setContractor(StringFilter contractor) {
    this.contractor = contractor;
  }

  public StringFilter getContractOwner() {
    return contractOwner;
  }

  public StringFilter contractOwner() {
    if (contractOwner == null) {
      contractOwner = new StringFilter();
    }
    return contractOwner;
  }

  public void setContractOwner(StringFilter contractOwner) {
    this.contractOwner = contractOwner;
  }

  public StringFilter getContractUser() {
    return contractUser;
  }

  public StringFilter contractUser() {
    if (contractUser == null) {
      contractUser = new StringFilter();
    }
    return contractUser;
  }

  public void setContractUser(StringFilter contractUser) {
    this.contractUser = contractUser;
  }

  public StringFilter getContractNumber() {
    return contractNumber;
  }

  public StringFilter contractNumber() {
    if (contractNumber == null) {
      contractNumber = new StringFilter();
    }
    return contractNumber;
  }

  public void setContractNumber(StringFilter contractNumber) {
    this.contractNumber = contractNumber;
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

  public LocalDateFilter getApplicationDate() {
    return applicationDate;
  }

  public LocalDateFilter applicationDate() {
    if (applicationDate == null) {
      applicationDate = new LocalDateFilter();
    }
    return applicationDate;
  }

  public void setApplicationDate(LocalDateFilter applicationDate) {
    this.applicationDate = applicationDate;
  }

  public LocalDateFilter getConfirmationDate() {
    return confirmationDate;
  }

  public LocalDateFilter confirmationDate() {
    if (confirmationDate == null) {
      confirmationDate = new LocalDateFilter();
    }
    return confirmationDate;
  }

  public void setConfirmationDate(LocalDateFilter confirmationDate) {
    this.confirmationDate = confirmationDate;
  }

  public PaymentPatternFilter getPaymentPattern() {
    return paymentPattern;
  }

  public PaymentPatternFilter paymentPattern() {
    if (paymentPattern == null) {
      paymentPattern = new PaymentPatternFilter();
    }
    return paymentPattern;
  }

  public void setPaymentPattern(PaymentPatternFilter paymentPattern) {
    this.paymentPattern = paymentPattern;
  }

  public StringFilter getComment() {
    return comment;
  }

  public StringFilter comment() {
    if (comment == null) {
      comment = new StringFilter();
    }
    return comment;
  }

  public void setComment(StringFilter comment) {
    this.comment = comment;
  }

  public StringFilter getPayer() {
    return payer;
  }

  public StringFilter payer() {
    if (payer == null) {
      payer = new StringFilter();
    }
    return payer;
  }

  public void setPayer(StringFilter payer) {
    this.payer = payer;
  }

  public PaymentTypeFilter getPaymentType() {
    return paymentType;
  }

  public PaymentTypeFilter paymentType() {
    if (paymentType == null) {
      paymentType = new PaymentTypeFilter();
    }
    return paymentType;
  }

  public void setPaymentType(PaymentTypeFilter paymentType) {
    this.paymentType = paymentType;
  }

  public DoubleFilter getSalary() {
    return salary;
  }

  public DoubleFilter salary() {
    if (salary == null) {
      salary = new DoubleFilter();
    }
    return salary;
  }

  public void setSalary(DoubleFilter salary) {
    this.salary = salary;
  }

  public IntegerFilter getVacationDays() {
    return vacationDays;
  }

  public IntegerFilter vacationDays() {
    if (vacationDays == null) {
      vacationDays = new IntegerFilter();
    }
    return vacationDays;
  }

  public void setVacationDays(IntegerFilter vacationDays) {
    this.vacationDays = vacationDays;
  }

  public StringFilter getOccupation() {
    return occupation;
  }

  public StringFilter occupation() {
    if (occupation == null) {
      occupation = new StringFilter();
    }
    return occupation;
  }

  public void setOccupation(StringFilter occupation) {
    this.occupation = occupation;
  }

  public StringFilter getActivities() {
    return activities;
  }

  public StringFilter activities() {
    if (activities == null) {
      activities = new StringFilter();
    }
    return activities;
  }

  public void setActivities(StringFilter activities) {
    this.activities = activities;
  }

  public DoubleFilter getLoanValue() {
    return loanValue;
  }

  public DoubleFilter loanValue() {
    if (loanValue == null) {
      loanValue = new DoubleFilter();
    }
    return loanValue;
  }

  public void setLoanValue(DoubleFilter loanValue) {
    this.loanValue = loanValue;
  }

  public DoubleFilter getInterestRatePercent() {
    return interestRatePercent;
  }

  public DoubleFilter interestRatePercent() {
    if (interestRatePercent == null) {
      interestRatePercent = new DoubleFilter();
    }
    return interestRatePercent;
  }

  public void setInterestRatePercent(DoubleFilter interestRatePercent) {
    this.interestRatePercent = interestRatePercent;
  }

  public DoubleFilter getInterestRateCurrency() {
    return interestRateCurrency;
  }

  public DoubleFilter interestRateCurrency() {
    if (interestRateCurrency == null) {
      interestRateCurrency = new DoubleFilter();
    }
    return interestRateCurrency;
  }

  public void setInterestRateCurrency(DoubleFilter interestRateCurrency) {
    this.interestRateCurrency = interestRateCurrency;
  }

  public DoubleFilter getAmortizationRatePercent() {
    return amortizationRatePercent;
  }

  public DoubleFilter amortizationRatePercent() {
    if (amortizationRatePercent == null) {
      amortizationRatePercent = new DoubleFilter();
    }
    return amortizationRatePercent;
  }

  public void setAmortizationRatePercent(DoubleFilter amortizationRatePercent) {
    this.amortizationRatePercent = amortizationRatePercent;
  }

  public DoubleFilter getAmortizationRateCurrency() {
    return amortizationRateCurrency;
  }

  public DoubleFilter amortizationRateCurrency() {
    if (amortizationRateCurrency == null) {
      amortizationRateCurrency = new DoubleFilter();
    }
    return amortizationRateCurrency;
  }

  public void setAmortizationRateCurrency(
    DoubleFilter amortizationRateCurrency
  ) {
    this.amortizationRateCurrency = amortizationRateCurrency;
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

  public LongFilter getContractRealestatesId() {
    return contractRealestatesId;
  }

  public LongFilter contractRealestatesId() {
    if (contractRealestatesId == null) {
      contractRealestatesId = new LongFilter();
    }
    return contractRealestatesId;
  }

  public void setContractRealestatesId(LongFilter contractRealestatesId) {
    this.contractRealestatesId = contractRealestatesId;
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
    final ContractCriteria that = (ContractCriteria) o;
    return (
      Objects.equals(id, that.id) &&
      Objects.equals(contractor, that.contractor) &&
      Objects.equals(contractOwner, that.contractOwner) &&
      Objects.equals(contractUser, that.contractUser) &&
      Objects.equals(contractNumber, that.contractNumber) &&
      Objects.equals(startDate, that.startDate) &&
      Objects.equals(endDate, that.endDate) &&
      Objects.equals(applicationDate, that.applicationDate) &&
      Objects.equals(confirmationDate, that.confirmationDate) &&
      Objects.equals(paymentPattern, that.paymentPattern) &&
      Objects.equals(comment, that.comment) &&
      Objects.equals(payer, that.payer) &&
      Objects.equals(paymentType, that.paymentType) &&
      Objects.equals(salary, that.salary) &&
      Objects.equals(vacationDays, that.vacationDays) &&
      Objects.equals(occupation, that.occupation) &&
      Objects.equals(activities, that.activities) &&
      Objects.equals(loanValue, that.loanValue) &&
      Objects.equals(interestRatePercent, that.interestRatePercent) &&
      Objects.equals(interestRateCurrency, that.interestRateCurrency) &&
      Objects.equals(amortizationRatePercent, that.amortizationRatePercent) &&
      Objects.equals(amortizationRateCurrency, that.amortizationRateCurrency) &&
      Objects.equals(applicationUserId, that.applicationUserId) &&
      Objects.equals(groupTypeId, that.groupTypeId) &&
      Objects.equals(contractRealestatesId, that.contractRealestatesId) &&
      Objects.equals(distinct, that.distinct)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      id,
      contractor,
      contractOwner,
      contractUser,
      contractNumber,
      startDate,
      endDate,
      applicationDate,
      confirmationDate,
      paymentPattern,
      comment,
      payer,
      paymentType,
      salary,
      vacationDays,
      occupation,
      activities,
      loanValue,
      interestRatePercent,
      interestRateCurrency,
      amortizationRatePercent,
      amortizationRateCurrency,
      applicationUserId,
      groupTypeId,
      contractRealestatesId,
      distinct
    );
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "ContractCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (contractor != null ? "contractor=" + contractor + ", " : "") +
            (contractOwner != null ? "contractOwner=" + contractOwner + ", " : "") +
            (contractUser != null ? "contractUser=" + contractUser + ", " : "") +
            (contractNumber != null ? "contractNumber=" + contractNumber + ", " : "") +
            (startDate != null ? "startDate=" + startDate + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (applicationDate != null ? "applicationDate=" + applicationDate + ", " : "") +
            (confirmationDate != null ? "confirmationDate=" + confirmationDate + ", " : "") +
            (paymentPattern != null ? "paymentPattern=" + paymentPattern + ", " : "") +
            (comment != null ? "comment=" + comment + ", " : "") +
            (payer != null ? "payer=" + payer + ", " : "") +
            (paymentType != null ? "paymentType=" + paymentType + ", " : "") +
            (salary != null ? "salary=" + salary + ", " : "") +
            (vacationDays != null ? "vacationDays=" + vacationDays + ", " : "") +
            (occupation != null ? "occupation=" + occupation + ", " : "") +
            (activities != null ? "activities=" + activities + ", " : "") +
            (loanValue != null ? "loanValue=" + loanValue + ", " : "") +
            (interestRatePercent != null ? "interestRatePercent=" + interestRatePercent + ", " : "") +
            (interestRateCurrency != null ? "interestRateCurrency=" + interestRateCurrency + ", " : "") +
            (amortizationRatePercent != null ? "amortizationRatePercent=" + amortizationRatePercent + ", " : "") +
            (amortizationRateCurrency != null ? "amortizationRateCurrency=" + amortizationRateCurrency + ", " : "") +
            (applicationUserId != null ? "applicationUserId=" + applicationUserId + ", " : "") +
            (groupTypeId != null ? "groupTypeId=" + groupTypeId + ", " : "") +
            (contractRealestatesId != null ? "contractRealestatesId=" + contractRealestatesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
