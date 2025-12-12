package me.jayna.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import me.jayna.domain.enumeration.PaymentPattern;
import me.jayna.domain.enumeration.PaymentType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Contract.
 */
@Entity
@Table(name = "contract")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedEntityGraph(name = "Contract.graph", attributeNodes = {
    @NamedAttributeNode("groupType"),
    @NamedAttributeNode("applicationUser")
})

public class Contract extends AbstractCategoryEntity {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @Column(name = "contractor")
  private String contractor;

  @Column(name = "contract_owner")
  private String contractOwner;

  @Column(name = "contract_user")
  private String contractUser;

  @Column(name = "contract_number")
  private String contractNumber;

  @Column(name = "start_date")
  private LocalDate startDate;

  @Column(name = "end_date")
  private LocalDate endDate;

  @Column(name = "application_date")
  private LocalDate applicationDate;

  @Column(name = "confirmation_date")
  private LocalDate confirmationDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_pattern")
  private PaymentPattern paymentPattern;

  @Column(name = "payer")
  private String payer;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_type")
  private PaymentType paymentType;

  @Column(name = "vacation_days")
  private Double vacationDays;

  @Column(name = "occupation")
  private String occupation;

  @Column(name = "activities")
  private String activities;

  @Column(name = "loan_value")
  private Double loanValue;

  @Column(name = "interest_rate_percent")
  private Double interestRatePercent;

  @Column(name = "interest_rate_currency")
  private Double interestRateCurrency;

  @Column(name = "amortization_rate_percent")
  private Double amortizationRatePercent;

  @Column(name = "amortization_rate_currency")
  private Double amortizationRateCurrency;

  @Column(name = "tee_amount")
  private Double teeAmount;

  @Column(name = "tee_interval")
  private Integer teeInterval;

  @Column(name = "acquisition_distribution_costs")
  private Double acquisitionDistributionCosts;

  @Column(name = "annual_fee")
  private Double annualFee;

  @Column(name = "retirement_start")
  private LocalDate retirementStart;

  @Column(name = "trailer_load")
  private Double trailerLoad;

  @Column(name = "no_tees")
  private Integer noTees;

  @Column(name = "employee_number")
  private String employeeNumber;

  @Column(name = "unemployment_insurance_amount")
  private Double unemploymentInsuranceAmount;

  @Column(name = "unemployment_insurance_employer")
  private String unemploymentInsuranceEmployer;

  @Column(name = "price_of_work")
  private Double priceOfWork;

  @Column(name = "total_energy_price")
  private Double totalEnergyPrice;

  @Column(name = "energy_price_kwh")
  private Double energyPriceKwh;

  @Column(name = "workplace")
  private String workplace;

  @Column(name = "working_hours_per_month")
  private Double workingHoursPerMonth;

  @Column(name = "working_hours_perweek")
  private Double workingHoursPerweek;

  @Column(name = "working_days_per_week")
  private Double workingDaysPerWeek;

  @Column(name = "foreign_assignment_fees")
  private Double foreignAssignmentFees;

  @Column(name = "maximum_bandwidth")
  private Integer maximumBandwidth;

  @Column(name = "cash_withdrawal_worldwide")
  private Integer cashWithdrawalWorldwide;

  @Column(name = "temporary_until")
  private LocalDate temporaryUntil;

  @Column(name = "aid_entitled")
  private Boolean aidEntitled;

  @Column(name = "bic")
  private String bic;

  @Column(name = "occupational_disability_pension_monthly")
  private Double occupationalDisabilityPensionMonthly;

  @Column(name = "loan_number")
  private String loanNumber;

  @Column(name = "data_volume_per_month")
  private Integer dataVolumePerMonth;

  @Column(name = "date")
  private LocalDate date;

  @Column(name = "dispolimit")
  private Integer dispolimit;

  @Column(name = "dispolimit_valid_from")
  private LocalDate dispolimitValidFrom;

  @Column(name = "company_establishment")
  private LocalDate companyEstablishment;

  @Column(name = "dynamic")
  private Integer dynamic;

  @Column(name = "contributions_paid_last_year")
  private Double contributionsPaidLastYear;

  @Column(name = "registration_fees")
  private Double registrationFees;

  @Column(name = "current_pay_points")
  private Double currentPayPoints;

  @Column(name = "hazard_group")
  private String hazardGroup;

  @Column(name = "number_of_salaries")
  private Double numberOfSalaries;

  @Column(name = "salary_bonus")
  private Double salaryBonus;

  @Column(name = "total_deductions")
  private Double totalDeductions;

  @Column(name = "total_price_sale")
  private Double totalPriceSale;

  @Column(name = "creditor_id_number")
  private String creditorIDNumber;

  @Column(name = "realestate_transfer_tax")
  private Double realestateTransferTax;

  @Column(name = "realestate_transfer_tax_percent")
  private Double realestateTransferTaxPercent;

  @Column(name = "basic_charge")
  private Double basicCharge;

  @Column(name = "basic_price")
  private Double basicPrice;

  @Column(name = "property_tax_percent")
  private Double propertyTaxPercent;

  @Column(name = "property_tax")
  private Double propertyTax;

  @Column(name = "group_contract")
  private Boolean groupContract;

  @Column(name = "iban")
  private String iban;

  @Column(name = "buyer")
  private String buyer;

  @Column(name = "bail")
  private Integer bail;

  @Column(name = "mark")
  private String mark;

  @Column(name = "car_brand")
  private String carBrand;

  @Column(name = "church_tax")
  private Double churchTax;

  @Column(name = "loan_amount")
  private Double loanAmount;

  @Column(name = "customer_number")
  private String customerNumber;

  @Column(name = "account_management_fee")
  private Double accountManagementFee;

  @Column(name = "discount_percent")
  private Double discountPercent;

  @Column(name = "discount")
  private Double discount;

  @Column(name = "period_of_notice")
  private Integer periodOfNotice;

  @Column(name = "runtime")
  private Integer runtime;

  @Column(name = "duration_in_months")
  private Integer durationInMonths;

  @Column(name = "last_termination_date")
  private LocalDate lastTerminationDate;

  @Column(name = "gross")
  private Double gross;

  @Column(name = "net")
  private Double net;

  @Column(name = "wage_tax")
  private Double wageTax;

  @Column(name = "tax_bracket")
  private Integer taxBracket;

  @Column(name = "brokerprov_percent")
  private Double brokerprovPercent;

  @Column(name = "brokerprov")
  private Double brokerprov;

  @Column(name = "mobile_phone_number")
  private Integer mobilePhoneNumber;

  @Column(name = "monthly_fee")
  private Double monthlyFee;

  @Column(name = "notary")
  private String notary;

  @Column(name = "notary_fee_percent")
  private Double notaryFeePercent;

  @Column(name = "notary_fee")
  private Double notaryFee;

  @Column(name = "online_tariff")
  private Boolean onlineTariff;

  @Column(name = "longterm_care_insurance_employer")
  private String longtermCareInsuranceEmployer;

  @Column(name = "longterm_care_insurance_employee")
  private String longtermCareInsuranceEmployee;

  @Column(name = "probationary_period")
  private Integer probationaryPeriod;

  @Column(name = "product_name")
  private String productName;

  @Column(name = "progression")
  private Integer progression;

  @Column(name = "square_metre")
  private Double squareMetre;

  @Column(name = "standard_old_age_pension")
  private Double standardOldAgePension;

  @Column(name = "standard_old_age_pension_date")
  private LocalDate standardOldAgePensionDate;

  @Column(name = "remaining_debt_date")
  private LocalDate remainingDebtDate;

  @Column(name = "surrender_value")
  private Double surrenderValue;

  @Column(name = "deductible")
  private Integer deductible;

  @Column(name = "sms_flat")
  private Boolean smsFlat;

  @Column(name = "debit_interest_year")
  private Double debitInterestYear;

  @Column(name = "total_social_security_contributions")
  private Double totalSocialSecurityContributions;

  @Column(name = "tax_rate")
  private Double taxRate;

  @Column(name = "tariff_name")
  private String tariffName;

  @Column(name = "phone_flat")
  private Boolean phoneFlat;

  @Column(name = "interest_on_excess")
  private Double interestOnExcess;

  @Column(name = "interest_on_excess_year_percent")
  private Double interestOnExcessYearPercent;

  @Column(name = "holiday_money")
  private Double holidayMoney;

  @Column(name = "sum_insured")
  private Double sumInsured;

  @Column(name = "contract_extension_possible")
  private LocalDate contractExtensionPossible;

  @Column(name = "maintenance_interval")
  private Double maintenanceInterval;

  @Column(name = "christmas_bonus")
  private Double christmasBonus;

  @Column(name = "amount")
  private Double amount;

  @Column(name = "contract_creator")
  private String contractCreator;

  @OneToMany(mappedBy = "contract", fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "contract" }, allowSetters = true)
  private Set<ContractRealestate> contractRealestates = new HashSet<>();

  @OneToMany(mappedBy = "contract", fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "contract" }, allowSetters = true)
  private Set<ContactContract> contactContracts = new HashSet<>();

  @OneToMany(mappedBy = "contract", fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "contract" }, allowSetters = true)
  private Set<ContractFinanceAccount> contractFinanceAccounts = new HashSet<>();

  @OneToMany(mappedBy = "contract", fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "contract" }, allowSetters = true)
  private Set<ContractMobility> contractMobilities = new HashSet<>();

  @OneToMany(mappedBy = "contract", fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "contract" }, allowSetters = true)
  private Set<ContractContract> contractContracts = new HashSet<>();

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public Contract id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getContractor() {
    return this.contractor;
  }

  public Contract contractor(String contractor) {
    this.setContractor(contractor);
    return this;
  }

  public void setContractor(String contractor) {
    this.contractor = contractor;
  }

  public String getContractOwner() {
    return this.contractOwner;
  }

  public Contract contractOwner(String contractOwner) {
    this.setContractOwner(contractOwner);
    return this;
  }

  public void setContractOwner(String contractOwner) {
    this.contractOwner = contractOwner;
  }

  public String getContractUser() {
    return this.contractUser;
  }

  public Contract contractUser(String contractUser) {
    this.setContractUser(contractUser);
    return this;
  }

  public void setContractUser(String contractUser) {
    this.contractUser = contractUser;
  }

  public String getContractNumber() {
    return this.contractNumber;
  }

  public Contract contractNumber(String contractNumber) {
    this.setContractNumber(contractNumber);
    return this;
  }

  public void setContractNumber(String contractNumber) {
    this.contractNumber = contractNumber;
  }

  public LocalDate getStartDate() {
    return this.startDate;
  }

  public Contract startDate(LocalDate startDate) {
    this.setStartDate(startDate);
    return this;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return this.endDate;
  }

  public Contract endDate(LocalDate endDate) {
    this.setEndDate(endDate);
    return this;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public LocalDate getApplicationDate() {
    return this.applicationDate;
  }

  public Contract applicationDate(LocalDate applicationDate) {
    this.setApplicationDate(applicationDate);
    return this;
  }

  public void setApplicationDate(LocalDate applicationDate) {
    this.applicationDate = applicationDate;
  }

  public LocalDate getConfirmationDate() {
    return this.confirmationDate;
  }

  public Contract confirmationDate(LocalDate confirmationDate) {
    this.setConfirmationDate(confirmationDate);
    return this;
  }

  public void setConfirmationDate(LocalDate confirmationDate) {
    this.confirmationDate = confirmationDate;
  }

  public PaymentPattern getPaymentPattern() {
    return this.paymentPattern;
  }

  public Contract paymentPattern(PaymentPattern paymentPattern) {
    this.setPaymentPattern(paymentPattern);
    return this;
  }

  public void setPaymentPattern(PaymentPattern paymentPattern) {
    this.paymentPattern = paymentPattern;
  }

  public String getPayer() {
    return this.payer;
  }

  public Contract payer(String payer) {
    this.setPayer(payer);
    return this;
  }

  public void setPayer(String payer) {
    this.payer = payer;
  }

  public PaymentType getPaymentType() {
    return this.paymentType;
  }

  public Contract paymentType(PaymentType paymentType) {
    this.setPaymentType(paymentType);
    return this;
  }

  public void setPaymentType(PaymentType paymentType) {
    this.paymentType = paymentType;
  }

  public Double getVacationDays() {
    return this.vacationDays;
  }

  public Contract vacationDays(Double vacationDays) {
    this.setVacationDays(vacationDays);
    return this;
  }

  public void setVacationDays(Double vacationDays) {
    this.vacationDays = vacationDays;
  }

  public String getOccupation() {
    return this.occupation;
  }

  public Contract occupation(String occupation) {
    this.setOccupation(occupation);
    return this;
  }

  public void setOccupation(String occupation) {
    this.occupation = occupation;
  }

  public String getActivities() {
    return this.activities;
  }

  public Contract activities(String activities) {
    this.setActivities(activities);
    return this;
  }

  public void setActivities(String activities) {
    this.activities = activities;
  }

  public Double getLoanValue() {
    return this.loanValue;
  }

  public Contract loanValue(Double loanValue) {
    this.setLoanValue(loanValue);
    return this;
  }

  public void setLoanValue(Double loanValue) {
    this.loanValue = loanValue;
  }

  public Double getInterestRatePercent() {
    return this.interestRatePercent;
  }

  public Contract interestRatePercent(Double interestRatePercent) {
    this.setInterestRatePercent(interestRatePercent);
    return this;
  }

  public void setInterestRatePercent(Double interestRatePercent) {
    this.interestRatePercent = interestRatePercent;
  }

  public Double getInterestRateCurrency() {
    return this.interestRateCurrency;
  }

  public Contract interestRateCurrency(Double interestRateCurrency) {
    this.setInterestRateCurrency(interestRateCurrency);
    return this;
  }

  public void setInterestRateCurrency(Double interestRateCurrency) {
    this.interestRateCurrency = interestRateCurrency;
  }

  public Double getAmortizationRatePercent() {
    return this.amortizationRatePercent;
  }

  public Contract amortizationRatePercent(Double amortizationRatePercent) {
    this.setAmortizationRatePercent(amortizationRatePercent);
    return this;
  }

  public void setAmortizationRatePercent(Double amortizationRatePercent) {
    this.amortizationRatePercent = amortizationRatePercent;
  }

  public Double getAmortizationRateCurrency() {
    return this.amortizationRateCurrency;
  }

  public Contract amortizationRateCurrency(Double amortizationRateCurrency) {
    this.setAmortizationRateCurrency(amortizationRateCurrency);
    return this;
  }

  public void setAmortizationRateCurrency(Double amortizationRateCurrency) {
    this.amortizationRateCurrency = amortizationRateCurrency;
  }

  public Double getTeeAmount() {
    return this.teeAmount;
  }

  public Contract teeAmount(Double teeAmount) {
    this.setTeeAmount(teeAmount);
    return this;
  }

  public void setTeeAmount(Double teeAmount) {
    this.teeAmount = teeAmount;
  }

  public Integer getTeeInterval() {
    return this.teeInterval;
  }

  public Contract teeInterval(Integer teeInterval) {
    this.setTeeInterval(teeInterval);
    return this;
  }

  public void setTeeInterval(Integer teeInterval) {
    this.teeInterval = teeInterval;
  }

  public Double getAcquisitionDistributionCosts() {
    return this.acquisitionDistributionCosts;
  }

  public Contract acquisitionDistributionCosts(Double acquisitionDistributionCosts) {
    this.setAcquisitionDistributionCosts(acquisitionDistributionCosts);
    return this;
  }

  public void setAcquisitionDistributionCosts(Double acquisitionDistributionCosts) {
    this.acquisitionDistributionCosts = acquisitionDistributionCosts;
  }

  public Double getAnnualFee() {
    return this.annualFee;
  }

  public Contract annualFee(Double annualFee) {
    this.setAnnualFee(annualFee);
    return this;
  }

  public void setAnnualFee(Double annualFee) {
    this.annualFee = annualFee;
  }

  public LocalDate getRetirementStart() {
    return this.retirementStart;
  }

  public Contract retirementStart(LocalDate retirementStart) {
    this.setRetirementStart(retirementStart);
    return this;
  }

  public void setRetirementStart(LocalDate retirementStart) {
    this.retirementStart = retirementStart;
  }

  public Double getTrailerLoad() {
    return this.trailerLoad;
  }

  public Contract trailerLoad(Double trailerLoad) {
    this.setTrailerLoad(trailerLoad);
    return this;
  }

  public void setTrailerLoad(Double trailerLoad) {
    this.trailerLoad = trailerLoad;
  }

  public Integer getNoTees() {
    return this.noTees;
  }

  public Contract noTees(Integer noTees) {
    this.setNoTees(noTees);
    return this;
  }

  public void setNoTees(Integer noTees) {
    this.noTees = noTees;
  }

  public String getEmployeeNumber() {
    return this.employeeNumber;
  }

  public Contract employeeNumber(String employeeNumber) {
    this.setEmployeeNumber(employeeNumber);
    return this;
  }

  public void setEmployeeNumber(String employeeNumber) {
    this.employeeNumber = employeeNumber;
  }

  public Double getUnemploymentInsuranceAmount() {
    return this.unemploymentInsuranceAmount;
  }

  public Contract unemploymentInsuranceAmount(Double unemploymentInsuranceAmount) {
    this.setUnemploymentInsuranceAmount(unemploymentInsuranceAmount);
    return this;
  }

  public void setUnemploymentInsuranceAmount(Double unemploymentInsuranceAmount) {
    this.unemploymentInsuranceAmount = unemploymentInsuranceAmount;
  }

  public String getUnemploymentInsuranceEmployer() {
    return this.unemploymentInsuranceEmployer;
  }

  public Contract unemploymentInsuranceEmployer(String unemploymentInsuranceEmployer) {
    this.setUnemploymentInsuranceEmployer(unemploymentInsuranceEmployer);
    return this;
  }

  public void setUnemploymentInsuranceEmployer(String unemploymentInsuranceEmployer) {
    this.unemploymentInsuranceEmployer = unemploymentInsuranceEmployer;
  }

  public Double getPriceOfWork() {
    return this.priceOfWork;
  }

  public Contract priceOfWork(Double priceOfWork) {
    this.setPriceOfWork(priceOfWork);
    return this;
  }

  public void setPriceOfWork(Double priceOfWork) {
    this.priceOfWork = priceOfWork;
  }

  public Double getTotalEnergyPrice() {
    return this.totalEnergyPrice;
  }

  public Contract totalEnergyPrice(Double totalEnergyPrice) {
    this.setTotalEnergyPrice(totalEnergyPrice);
    return this;
  }

  public void setTotalEnergyPrice(Double totalEnergyPrice) {
    this.totalEnergyPrice = totalEnergyPrice;
  }

  public Double getEnergyPriceKwh() {
    return this.energyPriceKwh;
  }

  public Contract energyPriceKwh(Double energyPriceKwh) {
    this.setEnergyPriceKwh(energyPriceKwh);
    return this;
  }

  public void setEnergyPriceKwh(Double energyPriceKwh) {
    this.energyPriceKwh = energyPriceKwh;
  }

  public String getWorkplace() {
    return this.workplace;
  }

  public Contract workplace(String workplace) {
    this.setWorkplace(workplace);
    return this;
  }

  public void setWorkplace(String workplace) {
    this.workplace = workplace;
  }

  public Double getWorkingHoursPerMonth() {
    return this.workingHoursPerMonth;
  }

  public Contract workingHoursPerMonth(Double workingHoursPerMonth) {
    this.setWorkingHoursPerMonth(workingHoursPerMonth);
    return this;
  }

  public void setWorkingHoursPerMonth(Double workingHoursPerMonth) {
    this.workingHoursPerMonth = workingHoursPerMonth;
  }

  public Double getWorkingHoursPerweek() {
    return this.workingHoursPerweek;
  }

  public Contract workingHoursPerweek(Double workingHoursPerweek) {
    this.setWorkingHoursPerweek(workingHoursPerweek);
    return this;
  }

  public void setWorkingHoursPerweek(Double workingHoursPerweek) {
    this.workingHoursPerweek = workingHoursPerweek;
  }

  public Double getWorkingDaysPerWeek() {
    return this.workingDaysPerWeek;
  }

  public Contract workingDaysPerWeek(Double workingDaysPerWeek) {
    this.setWorkingDaysPerWeek(workingDaysPerWeek);
    return this;
  }

  public void setWorkingDaysPerWeek(Double workingDaysPerWeek) {
    this.workingDaysPerWeek = workingDaysPerWeek;
  }

  public Double getForeignAssignmentFees() {
    return this.foreignAssignmentFees;
  }

  public Contract foreignAssignmentFees(Double foreignAssignmentFees) {
    this.setForeignAssignmentFees(foreignAssignmentFees);
    return this;
  }

  public void setForeignAssignmentFees(Double foreignAssignmentFees) {
    this.foreignAssignmentFees = foreignAssignmentFees;
  }

  public Integer getMaximumBandwidth() {
    return this.maximumBandwidth;
  }

  public Contract maximumBandwidth(Integer maximumBandwidth) {
    this.setMaximumBandwidth(maximumBandwidth);
    return this;
  }

  public void setMaximumBandwidth(Integer maximumBandwidth) {
    this.maximumBandwidth = maximumBandwidth;
  }

  public Integer getCashWithdrawalWorldwide() {
    return this.cashWithdrawalWorldwide;
  }

  public Contract cashWithdrawalWorldwide(Integer cashWithdrawalWorldwide) {
    this.setCashWithdrawalWorldwide(cashWithdrawalWorldwide);
    return this;
  }

  public void setCashWithdrawalWorldwide(Integer cashWithdrawalWorldwide) {
    this.cashWithdrawalWorldwide = cashWithdrawalWorldwide;
  }

  public LocalDate getTemporaryUntil() {
    return this.temporaryUntil;
  }

  public Contract temporaryUntil(LocalDate temporaryUntil) {
    this.setTemporaryUntil(temporaryUntil);
    return this;
  }

  public void setTemporaryUntil(LocalDate temporaryUntil) {
    this.temporaryUntil = temporaryUntil;
  }

  public Boolean getAidEntitled() {
    return this.aidEntitled;
  }

  public Contract aidEntitled(Boolean aidEntitled) {
    this.setAidEntitled(aidEntitled);
    return this;
  }

  public void setAidEntitled(Boolean aidEntitled) {
    this.aidEntitled = aidEntitled;
  }

  public String getBic() {
    return this.bic;
  }

  public Contract bic(String bic) {
    this.setBic(bic);
    return this;
  }

  public void setBic(String bic) {
    this.bic = bic;
  }

  public Double getOccupationalDisabilityPensionMonthly() {
    return this.occupationalDisabilityPensionMonthly;
  }

  public Contract occupationalDisabilityPensionMonthly(Double occupationalDisabilityPensionMonthly) {
    this.setOccupationalDisabilityPensionMonthly(occupationalDisabilityPensionMonthly);
    return this;
  }

  public void setOccupationalDisabilityPensionMonthly(Double occupationalDisabilityPensionMonthly) {
    this.occupationalDisabilityPensionMonthly = occupationalDisabilityPensionMonthly;
  }

  public String getLoanNumber() {
    return this.loanNumber;
  }

  public Contract loanNumber(String loanNumber) {
    this.setLoanNumber(loanNumber);
    return this;
  }

  public void setLoanNumber(String loanNumber) {
    this.loanNumber = loanNumber;
  }

  public Integer getDataVolumePerMonth() {
    return this.dataVolumePerMonth;
  }

  public Contract dataVolumePerMonth(Integer dataVolumePerMonth) {
    this.setDataVolumePerMonth(dataVolumePerMonth);
    return this;
  }

  public void setDataVolumePerMonth(Integer dataVolumePerMonth) {
    this.dataVolumePerMonth = dataVolumePerMonth;
  }

  public LocalDate getDate() {
    return this.date;
  }

  public Contract date(LocalDate date) {
    this.setDate(date);
    return this;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Integer getDispolimit() {
    return this.dispolimit;
  }

  public Contract dispolimit(Integer dispolimit) {
    this.setDispolimit(dispolimit);
    return this;
  }

  public void setDispolimit(Integer dispolimit) {
    this.dispolimit = dispolimit;
  }

  public LocalDate getDispolimitValidFrom() {
    return this.dispolimitValidFrom;
  }

  public Contract dispolimitValidFrom(LocalDate dispolimitValidFrom) {
    this.setDispolimitValidFrom(dispolimitValidFrom);
    return this;
  }

  public void setDispolimitValidFrom(LocalDate dispolimitValidFrom) {
    this.dispolimitValidFrom = dispolimitValidFrom;
  }

  public LocalDate getCompanyEstablishment() {
    return this.companyEstablishment;
  }

  public Contract companyEstablishment(LocalDate companyEstablishment) {
    this.setCompanyEstablishment(companyEstablishment);
    return this;
  }

  public void setCompanyEstablishment(LocalDate companyEstablishment) {
    this.companyEstablishment = companyEstablishment;
  }

  public Integer getDynamic() {
    return this.dynamic;
  }

  public Contract dynamic(Integer dynamic) {
    this.setDynamic(dynamic);
    return this;
  }

  public void setDynamic(Integer dynamic) {
    this.dynamic = dynamic;
  }

  public Double getContributionsPaidLastYear() {
    return this.contributionsPaidLastYear;
  }

  public Contract contributionsPaidLastYear(Double contributionsPaidLastYear) {
    this.setContributionsPaidLastYear(contributionsPaidLastYear);
    return this;
  }

  public void setContributionsPaidLastYear(Double contributionsPaidLastYear) {
    this.contributionsPaidLastYear = contributionsPaidLastYear;
  }

  public Double getRegistrationFees() {
    return this.registrationFees;
  }

  public Contract registrationFees(Double registrationFees) {
    this.setRegistrationFees(registrationFees);
    return this;
  }

  public void setRegistrationFees(Double registrationFees) {
    this.registrationFees = registrationFees;
  }

  public Double getCurrentPayPoints() {
    return this.currentPayPoints;
  }

  public Contract currentPayPoints(Double currentPayPoints) {
    this.setCurrentPayPoints(currentPayPoints);
    return this;
  }

  public void setCurrentPayPoints(Double currentPayPoints) {
    this.currentPayPoints = currentPayPoints;
  }

  public String getHazardGroup() {
    return this.hazardGroup;
  }

  public Contract hazardGroup(String hazardGroup) {
    this.setHazardGroup(hazardGroup);
    return this;
  }

  public void setHazardGroup(String hazardGroup) {
    this.hazardGroup = hazardGroup;
  }

  public Double getNumberOfSalaries() {
    return this.numberOfSalaries;
  }

  public Contract numberOfSalaries(Double numberOfSalaries) {
    this.setNumberOfSalaries(numberOfSalaries);
    return this;
  }

  public void setNumberOfSalaries(Double numberOfSalaries) {
    this.numberOfSalaries = numberOfSalaries;
  }

  public Double getSalaryBonus() {
    return this.salaryBonus;
  }

  public Contract salaryBonus(Double salaryBonus) {
    this.setSalaryBonus(salaryBonus);
    return this;
  }

  public void setSalaryBonus(Double salaryBonus) {
    this.salaryBonus = salaryBonus;
  }

  public Double getTotalDeductions() {
    return this.totalDeductions;
  }

  public Contract totalDeductions(Double totalDeductions) {
    this.setTotalDeductions(totalDeductions);
    return this;
  }

  public void setTotalDeductions(Double totalDeductions) {
    this.totalDeductions = totalDeductions;
  }

  public Double getTotalPriceSale() {
    return this.totalPriceSale;
  }

  public Contract totalPriceSale(Double totalPriceSale) {
    this.setTotalPriceSale(totalPriceSale);
    return this;
  }

  public void setTotalPriceSale(Double totalPriceSale) {
    this.totalPriceSale = totalPriceSale;
  }

  public String getCreditorIDNumber() {
    return this.creditorIDNumber;
  }

  public Contract creditorIDNumber(String creditorIDNumber) {
    this.setCreditorIDNumber(creditorIDNumber);
    return this;
  }

  public void setCreditorIDNumber(String creditorIDNumber) {
    this.creditorIDNumber = creditorIDNumber;
  }

  public Double getRealestateTransferTax() {
    return this.realestateTransferTax;
  }

  public Contract realestateTransferTax(Double realestateTransferTax) {
    this.setRealestateTransferTax(realestateTransferTax);
    return this;
  }

  public void setRealestateTransferTax(Double realestateTransferTax) {
    this.realestateTransferTax = realestateTransferTax;
  }

  public Double getRealestateTransferTaxPercent() {
    return this.realestateTransferTaxPercent;
  }

  public Contract realestateTransferTaxPercent(Double realestateTransferTaxPercent) {
    this.setRealestateTransferTaxPercent(realestateTransferTaxPercent);
    return this;
  }

  public void setRealestateTransferTaxPercent(Double realestateTransferTaxPercent) {
    this.realestateTransferTaxPercent = realestateTransferTaxPercent;
  }

  public Double getBasicCharge() {
    return this.basicCharge;
  }

  public Contract basicCharge(Double basicCharge) {
    this.setBasicCharge(basicCharge);
    return this;
  }

  public void setBasicCharge(Double basicCharge) {
    this.basicCharge = basicCharge;
  }

  public Double getBasicPrice() {
    return this.basicPrice;
  }

  public Contract basicPrice(Double basicPrice) {
    this.setBasicPrice(basicPrice);
    return this;
  }

  public void setBasicPrice(Double basicPrice) {
    this.basicPrice = basicPrice;
  }

  public Double getPropertyTaxPercent() {
    return this.propertyTaxPercent;
  }

  public Contract propertyTaxPercent(Double propertyTaxPercent) {
    this.setPropertyTaxPercent(propertyTaxPercent);
    return this;
  }

  public void setPropertyTaxPercent(Double propertyTaxPercent) {
    this.propertyTaxPercent = propertyTaxPercent;
  }

  public Double getPropertyTax() {
    return this.propertyTax;
  }

  public Contract propertyTax(Double propertyTax) {
    this.setPropertyTax(propertyTax);
    return this;
  }

  public void setPropertyTax(Double propertyTax) {
    this.propertyTax = propertyTax;
  }

  public Boolean getGroupContract() {
    return this.groupContract;
  }

  public Contract groupContract(Boolean groupContract) {
    this.setGroupContract(groupContract);
    return this;
  }

  public void setGroupContract(Boolean groupContract) {
    this.groupContract = groupContract;
  }

  public String getIban() {
    return this.iban;
  }

  public Contract iban(String iban) {
    this.setIban(iban);
    return this;
  }

  public void setIban(String iban) {
    this.iban = iban;
  }

  public String getBuyer() {
    return this.buyer;
  }

  public Contract buyer(String buyer) {
    this.setBuyer(buyer);
    return this;
  }

  public void setBuyer(String buyer) {
    this.buyer = buyer;
  }

  public Integer getBail() {
    return this.bail;
  }

  public Contract bail(Integer bail) {
    this.setBail(bail);
    return this;
  }

  public void setBail(Integer bail) {
    this.bail = bail;
  }

  public String getMark() {
    return this.mark;
  }

  public Contract mark(String mark) {
    this.setMark(mark);
    return this;
  }

  public void setMark(String mark) {
    this.mark = mark;
  }

  public String getCarBrand() {
    return this.carBrand;
  }

  public Contract carBrand(String carBrand) {
    this.setCarBrand(carBrand);
    return this;
  }

  public void setCarBrand(String carBrand) {
    this.carBrand = carBrand;
  }

  public Double getChurchTax() {
    return this.churchTax;
  }

  public Contract churchTax(Double churchTax) {
    this.setChurchTax(churchTax);
    return this;
  }

  public void setChurchTax(Double churchTax) {
    this.churchTax = churchTax;
  }

  public Double getLoanAmount() {
    return this.loanAmount;
  }

  public Contract loanAmount(Double loanAmount) {
    this.setLoanAmount(loanAmount);
    return this;
  }

  public void setLoanAmount(Double loanAmount) {
    this.loanAmount = loanAmount;
  }

  public String getCustomerNumber() {
    return this.customerNumber;
  }

  public Contract customerNumber(String customerNumber) {
    this.setCustomerNumber(customerNumber);
    return this;
  }

  public void setCustomerNumber(String customerNumber) {
    this.customerNumber = customerNumber;
  }

  public Double getAccountManagementFee() {
    return this.accountManagementFee;
  }

  public Contract accountManagementFee(Double accountManagementFee) {
    this.setAccountManagementFee(accountManagementFee);
    return this;
  }

  public void setAccountManagementFee(Double accountManagementFee) {
    this.accountManagementFee = accountManagementFee;
  }

  public Double getDiscountPercent() {
    return this.discountPercent;
  }

  public Contract discountPercent(Double discountPercent) {
    this.setDiscountPercent(discountPercent);
    return this;
  }

  public void setDiscountPercent(Double discountPercent) {
    this.discountPercent = discountPercent;
  }

  public Double getDiscount() {
    return this.discount;
  }

  public Contract discount(Double discount) {
    this.setDiscount(discount);
    return this;
  }

  public void setDiscount(Double discount) {
    this.discount = discount;
  }

  public Integer getPeriodOfNotice() {
    return this.periodOfNotice;
  }

  public Contract periodOfNotice(Integer periodOfNotice) {
    this.setPeriodOfNotice(periodOfNotice);
    return this;
  }

  public void setPeriodOfNotice(Integer periodOfNotice) {
    this.periodOfNotice = periodOfNotice;
  }

  public Integer getRuntime() {
    return this.runtime;
  }

  public Contract runtime(Integer runtime) {
    this.setRuntime(runtime);
    return this;
  }

  public void setRuntime(Integer runtime) {
    this.runtime = runtime;
  }

  public Integer getDurationInMonths() {
    return this.durationInMonths;
  }

  public Contract durationInMonths(Integer durationInMonths) {
    this.setDurationInMonths(durationInMonths);
    return this;
  }

  public void setDurationInMonths(Integer durationInMonths) {
    this.durationInMonths = durationInMonths;
  }

  public LocalDate getLastTerminationDate() {
    return this.lastTerminationDate;
  }

  public Contract lastTerminationDate(LocalDate lastTerminationDate) {
    this.setLastTerminationDate(lastTerminationDate);
    return this;
  }

  public void setLastTerminationDate(LocalDate lastTerminationDate) {
    this.lastTerminationDate = lastTerminationDate;
  }

  public Double getGross() {
    return this.gross;
  }

  public Contract gross(Double gross) {
    this.setGross(gross);
    return this;
  }

  public void setGross(Double gross) {
    this.gross = gross;
  }

  public Double getNet() {
    return this.net;
  }

  public Contract net(Double net) {
    this.setNet(net);
    return this;
  }

  public void setNet(Double net) {
    this.net = net;
  }

  public Double getWageTax() {
    return this.wageTax;
  }

  public Contract wageTax(Double wageTax) {
    this.setWageTax(wageTax);
    return this;
  }

  public void setWageTax(Double wageTax) {
    this.wageTax = wageTax;
  }

  public Integer getTaxBracket() {
    return this.taxBracket;
  }

  public Contract taxBracket(Integer taxBracket) {
    this.setTaxBracket(taxBracket);
    return this;
  }

  public void setTaxBracket(Integer taxBracket) {
    this.taxBracket = taxBracket;
  }

  public Double getBrokerprovPercent() {
    return this.brokerprovPercent;
  }

  public Contract brokerprovPercent(Double brokerprovPercent) {
    this.setBrokerprovPercent(brokerprovPercent);
    return this;
  }

  public void setBrokerprovPercent(Double brokerprovPercent) {
    this.brokerprovPercent = brokerprovPercent;
  }

  public Double getBrokerprov() {
    return this.brokerprov;
  }

  public Contract brokerprov(Double brokerprov) {
    this.setBrokerprov(brokerprov);
    return this;
  }

  public void setBrokerprov(Double brokerprov) {
    this.brokerprov = brokerprov;
  }

  public Integer getMobilePhoneNumber() {
    return this.mobilePhoneNumber;
  }

  public Contract mobilePhoneNumber(Integer mobilePhoneNumber) {
    this.setMobilePhoneNumber(mobilePhoneNumber);
    return this;
  }

  public void setMobilePhoneNumber(Integer mobilePhoneNumber) {
    this.mobilePhoneNumber = mobilePhoneNumber;
  }

  public Double getMonthlyFee() {
    return this.monthlyFee;
  }

  public Contract monthlyFee(Double monthlyFee) {
    this.setMonthlyFee(monthlyFee);
    return this;
  }

  public void setMonthlyFee(Double monthlyFee) {
    this.monthlyFee = monthlyFee;
  }

  public String getNotary() {
    return this.notary;
  }

  public Contract notary(String notary) {
    this.setNotary(notary);
    return this;
  }

  public void setNotary(String notary) {
    this.notary = notary;
  }

  public Double getNotaryFeePercent() {
    return this.notaryFeePercent;
  }

  public Contract notaryFeePercent(Double notaryFeePercent) {
    this.setNotaryFeePercent(notaryFeePercent);
    return this;
  }

  public void setNotaryFeePercent(Double notaryFeePercent) {
    this.notaryFeePercent = notaryFeePercent;
  }

  public Double getNotaryFee() {
    return this.notaryFee;
  }

  public Contract notaryFee(Double notaryFee) {
    this.setNotaryFee(notaryFee);
    return this;
  }

  public void setNotaryFee(Double notaryFee) {
    this.notaryFee = notaryFee;
  }

  public Boolean getOnlineTariff() {
    return this.onlineTariff;
  }

  public Contract onlineTariff(Boolean onlineTariff) {
    this.setOnlineTariff(onlineTariff);
    return this;
  }

  public void setOnlineTariff(Boolean onlineTariff) {
    this.onlineTariff = onlineTariff;
  }

  public String getLongtermCareInsuranceEmployer() {
    return this.longtermCareInsuranceEmployer;
  }

  public Contract longtermCareInsuranceEmployer(String longtermCareInsuranceEmployer) {
    this.setLongtermCareInsuranceEmployer(longtermCareInsuranceEmployer);
    return this;
  }

  public void setLongtermCareInsuranceEmployer(String longtermCareInsuranceEmployer) {
    this.longtermCareInsuranceEmployer = longtermCareInsuranceEmployer;
  }

  public String getLongtermCareInsuranceEmployee() {
    return this.longtermCareInsuranceEmployee;
  }

  public Contract longtermCareInsuranceEmployee(String longtermCareInsuranceEmployee) {
    this.setLongtermCareInsuranceEmployee(longtermCareInsuranceEmployee);
    return this;
  }

  public void setLongtermCareInsuranceEmployee(String longtermCareInsuranceEmployee) {
    this.longtermCareInsuranceEmployee = longtermCareInsuranceEmployee;
  }

  public Integer getProbationaryPeriod() {
    return this.probationaryPeriod;
  }

  public Contract probationaryPeriod(Integer probationaryPeriod) {
    this.setProbationaryPeriod(probationaryPeriod);
    return this;
  }

  public void setProbationaryPeriod(Integer probationaryPeriod) {
    this.probationaryPeriod = probationaryPeriod;
  }

  public String getProductName() {
    return this.productName;
  }

  public Contract productName(String productName) {
    this.setProductName(productName);
    return this;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public Integer getProgression() {
    return this.progression;
  }

  public Contract progression(Integer progression) {
    this.setProgression(progression);
    return this;
  }

  public void setProgression(Integer progression) {
    this.progression = progression;
  }

  public Double getSquareMetre() {
    return this.squareMetre;
  }

  public Contract squareMetre(Double squareMetre) {
    this.setSquareMetre(squareMetre);
    return this;
  }

  public void setSquareMetre(Double squareMetre) {
    this.squareMetre = squareMetre;
  }

  public Double getStandardOldAgePension() {
    return this.standardOldAgePension;
  }

  public Contract standardOldAgePension(Double standardOldAgePension) {
    this.setStandardOldAgePension(standardOldAgePension);
    return this;
  }

  public void setStandardOldAgePension(Double standardOldAgePension) {
    this.standardOldAgePension = standardOldAgePension;
  }

  public LocalDate getStandardOldAgePensionDate() {
    return this.standardOldAgePensionDate;
  }

  public Contract standardOldAgePensionDate(LocalDate standardOldAgePensionDate) {
    this.setStandardOldAgePensionDate(standardOldAgePensionDate);
    return this;
  }

  public void setStandardOldAgePensionDate(LocalDate standardOldAgePensionDate) {
    this.standardOldAgePensionDate = standardOldAgePensionDate;
  }

  public LocalDate getRemainingDebtDate() {
    return this.remainingDebtDate;
  }

  public Contract remainingDebtDate(LocalDate remainingDebtDate) {
    this.setRemainingDebtDate(remainingDebtDate);
    return this;
  }

  public void setRemainingDebtDate(LocalDate remainingDebtDate) {
    this.remainingDebtDate = remainingDebtDate;
  }

  public Double getSurrenderValue() {
    return this.surrenderValue;
  }

  public Contract surrenderValue(Double surrenderValue) {
    this.setSurrenderValue(surrenderValue);
    return this;
  }

  public void setSurrenderValue(Double surrenderValue) {
    this.surrenderValue = surrenderValue;
  }

  public Integer getDeductible() {
    return this.deductible;
  }

  public Contract deductible(Integer deductible) {
    this.setDeductible(deductible);
    return this;
  }

  public void setDeductible(Integer deductible) {
    this.deductible = deductible;
  }

  public Boolean getSmsFlat() {
    return this.smsFlat;
  }

  public Contract smsFlat(Boolean smsFlat) {
    this.setSmsFlat(smsFlat);
    return this;
  }

  public void setSmsFlat(Boolean smsFlat) {
    this.smsFlat = smsFlat;
  }

  public Double getDebitInterestYear() {
    return this.debitInterestYear;
  }

  public Contract debitInterestYear(Double debitInterestYear) {
    this.setDebitInterestYear(debitInterestYear);
    return this;
  }

  public void setDebitInterestYear(Double debitInterestYear) {
    this.debitInterestYear = debitInterestYear;
  }

  public Double getTotalSocialSecurityContributions() {
    return this.totalSocialSecurityContributions;
  }

  public Contract totalSocialSecurityContributions(Double totalSocialSecurityContributions) {
    this.setTotalSocialSecurityContributions(totalSocialSecurityContributions);
    return this;
  }

  public void setTotalSocialSecurityContributions(Double totalSocialSecurityContributions) {
    this.totalSocialSecurityContributions = totalSocialSecurityContributions;
  }

  public Double getTaxRate() {
    return this.taxRate;
  }

  public Contract taxRate(Double taxRate) {
    this.setTaxRate(taxRate);
    return this;
  }

  public void setTaxRate(Double taxRate) {
    this.taxRate = taxRate;
  }

  public String getTariffName() {
    return this.tariffName;
  }

  public Contract tariffName(String tariffName) {
    this.setTariffName(tariffName);
    return this;
  }

  public void setTariffName(String tariffName) {
    this.tariffName = tariffName;
  }

  public Boolean getPhoneFlat() {
    return this.phoneFlat;
  }

  public Contract phoneFlat(Boolean phoneFlat) {
    this.setPhoneFlat(phoneFlat);
    return this;
  }

  public void setPhoneFlat(Boolean phoneFlat) {
    this.phoneFlat = phoneFlat;
  }

  public Double getInterestOnExcess() {
    return this.interestOnExcess;
  }

  public Contract interestOnExcess(Double interestOnExcess) {
    this.setInterestOnExcess(interestOnExcess);
    return this;
  }

  public void setInterestOnExcess(Double interestOnExcess) {
    this.interestOnExcess = interestOnExcess;
  }

  public Double getInterestOnExcessYearPercent() {
    return this.interestOnExcessYearPercent;
  }

  public Contract interestOnExcessYearPercent(Double interestOnExcessYearPercent) {
    this.setInterestOnExcessYearPercent(interestOnExcessYearPercent);
    return this;
  }

  public void setInterestOnExcessYearPercent(Double interestOnExcessYearPercent) {
    this.interestOnExcessYearPercent = interestOnExcessYearPercent;
  }

  public Double getHolidayMoney() {
    return this.holidayMoney;
  }

  public Contract holidayMoney(Double holidayMoney) {
    this.setHolidayMoney(holidayMoney);
    return this;
  }

  public void setHolidayMoney(Double holidayMoney) {
    this.holidayMoney = holidayMoney;
  }

  public Double getSumInsured() {
    return this.sumInsured;
  }

  public Contract sumInsured(Double sumInsured) {
    this.setSumInsured(sumInsured);
    return this;
  }

  public void setSumInsured(Double sumInsured) {
    this.sumInsured = sumInsured;
  }

  public LocalDate getContractExtensionPossible() {
    return this.contractExtensionPossible;
  }

  public Contract contractExtensionPossible(LocalDate contractExtensionPossible) {
    this.setContractExtensionPossible(contractExtensionPossible);
    return this;
  }

  public void setContractExtensionPossible(LocalDate contractExtensionPossible) {
    this.contractExtensionPossible = contractExtensionPossible;
  }

  public Double getMaintenanceInterval() {
    return this.maintenanceInterval;
  }

  public Contract maintenanceInterval(Double maintenanceInterval) {
    this.setMaintenanceInterval(maintenanceInterval);
    return this;
  }

  public void setMaintenanceInterval(Double maintenanceInterval) {
    this.maintenanceInterval = maintenanceInterval;
  }

  public Double getChristmasBonus() {
    return this.christmasBonus;
  }

  public Contract christmasBonus(Double christmasBonus) {
    this.setChristmasBonus(christmasBonus);
    return this;
  }

  public void setChristmasBonus(Double christmasBonus) {
    this.christmasBonus = christmasBonus;
  }

  public Double getAmount() {
    return this.amount;
  }

  public Contract amount(Double amount) {
    this.setAmount(amount);
    return this;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public String getContractCreator() {
    return this.contractCreator;
  }

  public Contract contractCreator(String contractCreator) {
    this.setContractCreator(contractCreator);
    return this;
  }

  public void setContractCreator(String contractCreator) {
    this.contractCreator = contractCreator;
  }

  public Boolean isAidEntitled() {
    return this.aidEntitled;
  }

  public Boolean isGroupContract() {
    return this.groupContract;
  }

  public Boolean isOnlineTariff() {
    return this.onlineTariff;
  }

  public Boolean isSmsFlat() {
    return this.smsFlat;
  }

  public Boolean isPhoneFlat() {
    return this.phoneFlat;
  }

  public Set<ContractRealestate> getContractRealestates() {
    return this.contractRealestates;
  }

  public void setContractRealestates(
      Set<ContractRealestate> contractRealestates) {
    if (this.contractRealestates != null) {
      this.contractRealestates.forEach(i -> i.setContract(null));
    }
    if (contractRealestates != null) {
      contractRealestates.forEach(i -> i.setContract(this));
    }
    this.contractRealestates = contractRealestates;
  }

  public Contract contractRealestates(
      Set<ContractRealestate> contractRealestates) {
    this.setContractRealestates(contractRealestates);
    return this;
  }

  public Contract addContractRealestates(
      ContractRealestate contractRealestate) {
    this.contractRealestates.add(contractRealestate);
    contractRealestate.setContract(this);
    return this;
  }

  public Contract removeContractRealestates(
      ContractRealestate contractRealestate) {
    this.contractRealestates.remove(contractRealestate);
    contractRealestate.setContract(null);
    return this;
  }

  public Set<ContactContract> getContactContracts() {
    return this.contactContracts;
  }

  public void setContactContracts(Set<ContactContract> contactContracts) {
    this.contactContracts = contactContracts;
  }

  public Contract contactContracts(
      Set<ContactContract> contactContracts) {
    this.setContactContracts(contactContracts);
    return this;
  }

  public Contract addContactContracts(
      ContactContract contactContracts) {
    this.contactContracts.add(contactContracts);
    contactContracts.setContract(this);
    return this;
  }

  public Contract removeContactContracts(
      ContactContract contactContract) {
    this.contactContracts.remove(contactContract);
    contactContract.setContract(null);
    return this;
  }

  public Set<ContractFinanceAccount> getContractFinanceAccounts() {
    return this.contractFinanceAccounts;
  }

  public void setContractFinanceAccounts(Set<ContractFinanceAccount> contractFinanceAccounts) {
    this.contractFinanceAccounts = contractFinanceAccounts;
  }

  public Contract contractFinanceAccounts(
      Set<ContractFinanceAccount> contractFinanceAccounts) {
    this.setContractFinanceAccounts(contractFinanceAccounts);
    return this;
  }

  public Contract addcontractFinanceAccounts(
      ContractFinanceAccount contractFinanceAccount) {
    this.contractFinanceAccounts.add(contractFinanceAccount);
    contractFinanceAccount.setContract(this);
    return this;
  }

  public Contract removeContractFinanceAccounts(
      ContractFinanceAccount contractFinanceAccount) {
    this.contractFinanceAccounts.remove(contractFinanceAccount);
    contractFinanceAccount.setContract(null);
    return this;
  }

  public Set<ContractMobility> getContractMobilities() {
    return this.contractMobilities;
  }

  public void setContractMobilities(Set<ContractMobility> contractMobilities) {
    this.contractMobilities = contractMobilities;
  }

  public Contract contractMobilities(
      Set<ContractMobility> contractMobilities) {
    this.setContractMobilities(contractMobilities);
    return this;
  }

  public Contract addContractMobility(
      ContractMobility contractMobility) {
    this.contractMobilities.add(contractMobility);
    contractMobility.setContract(this);
    return this;
  }

  public Contract removeContractMobilities(
      ContractMobility contractMobility) {
    this.contractMobilities.remove(contractMobility);
    contractMobility.setContract(null);
    return this;
  }

  public Set<ContractContract> getContractContracts() {
    return this.contractContracts;
  }

  public void setContractContracts(Set<ContractContract> contractContracts) {
    this.contractContracts = contractContracts;
  }

  public Contract contractContracts(
      Set<ContractContract> contractContracts) {
    this.setContractContracts(contractContracts);
    return this;
  }

  public Contract addContractContracts(
      ContractContract contractContract) {
    this.contractContracts.add(contractContract);
    contractContract.setContract(this);
    return this;
  }

  public Contract removeContractContracts(
      ContractContract contractContract) {
    this.contractContracts.remove(contractContract);
    contractContract.setContract(null);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
  // setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Contract)) {
      return false;
    }
    return id != null && id.equals(((Contract) o).id);
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
    return "Contract{" +
        "id=" + getId() +
        ", contractor='" + getContractor() + "'" +
        ", contractOwner='" + getContractOwner() + "'" +
        ", contractUser='" + getContractUser() + "'" +
        ", contractNumber='" + getContractNumber() + "'" +
        ", startDate='" + getStartDate() + "'" +
        ", endDate='" + getEndDate() + "'" +
        ", applicationDate='" + getApplicationDate() + "'" +
        ", confirmationDate='" + getConfirmationDate() + "'" +
        ", paymentPattern='" + getPaymentPattern() + "'" +
        ", payer='" + getPayer() + "'" +
        ", paymentType='" + getPaymentType() + "'" +
        ", vacationDays=" + getVacationDays() +
        ", occupation='" + getOccupation() + "'" +
        ", activities='" + getActivities() + "'" +
        ", loanValue=" + getLoanValue() +
        ", interestRatePercent=" + getInterestRatePercent() +
        ", interestRateCurrency=" + getInterestRateCurrency() +
        ", amortizationRatePercent=" + getAmortizationRatePercent() +
        ", amortizationRateCurrency=" + getAmortizationRateCurrency() +
        "}";
  }
}
