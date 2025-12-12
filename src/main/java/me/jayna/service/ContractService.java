package me.jayna.service;

import java.util.List;
import java.util.Optional;
import me.jayna.domain.Contract;
import me.jayna.domain.ApplicationUser;
import me.jayna.repository.ContractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Contract}.
 */
@Service
@Transactional
public class ContractService {

  private final Logger log = LoggerFactory.getLogger(ContractService.class);

  private final ContractRepository contractRepository;

  public ContractService(ContractRepository contractRepository) {
    this.contractRepository = contractRepository;
  }

  /**
   * Save a contract.
   *
   * @param contract the entity to save.
   * @return the persisted entity.
   */
  public Contract save(Contract contract) {
    log.debug("Request to save Contract : {}", contract);
    return contractRepository.save(contract);
  }

  /**
   * Update a contract.
   *
   * @param contract the entity to save.
   * @return the persisted entity.
   */
  public Contract update(Contract contract) {
    log.debug("Request to save Contract : {}", contract);
    return contractRepository.save(contract);
  }

  /**
   * Partially update a contract.
   *
   * @param contract the entity to update partially.
   * @return the persisted entity.
   */
  public Optional<Contract> partialUpdate(Contract contract) {
    log.debug("Request to partially update Contract : {}", contract);

    return contractRepository
        .findById(contract.getId())
        .map(existingContract -> {
          if (contract.getContractor() != null) {
            existingContract.setContractor(contract.getContractor());
          }
          if (contract.getContractOwner() != null) {
            existingContract.setContractOwner(contract.getContractOwner());
          }
          if (contract.getContractUser() != null) {
            existingContract.setContractUser(contract.getContractUser());
          }
          if (contract.getContractNumber() != null) {
            existingContract.setContractNumber(contract.getContractNumber());
          }
          if (contract.getStartDate() != null) {
            existingContract.setStartDate(contract.getStartDate());
          }
          if (contract.getEndDate() != null) {
            existingContract.setEndDate(contract.getEndDate());
          }
          if (contract.getApplicationDate() != null) {
            existingContract.setApplicationDate(contract.getApplicationDate());
          }
          if (contract.getConfirmationDate() != null) {
            existingContract.setConfirmationDate(contract.getConfirmationDate());
          }
          if (contract.getPaymentPattern() != null) {
            existingContract.setPaymentPattern(contract.getPaymentPattern());
          }
          if (contract.getComment() != null) {
            existingContract.setComment(contract.getComment());
          }
          if (contract.getPayer() != null) {
            existingContract.setPayer(contract.getPayer());
          }
          if (contract.getPaymentType() != null) {
            existingContract.setPaymentType(contract.getPaymentType());
          }
          if (contract.getVacationDays() != null) {
            existingContract.setVacationDays(contract.getVacationDays());
          }
          if (contract.getOccupation() != null) {
            existingContract.setOccupation(contract.getOccupation());
          }
          if (contract.getActivities() != null) {
            existingContract.setActivities(contract.getActivities());
          }
          if (contract.getLoanValue() != null) {
            existingContract.setLoanValue(contract.getLoanValue());
          }
          if (contract.getInterestRatePercent() != null) {
            existingContract.setInterestRatePercent(
                contract.getInterestRatePercent());
          }
          if (contract.getInterestRateCurrency() != null) {
            existingContract.setInterestRateCurrency(
                contract.getInterestRateCurrency());
          }
          if (contract.getAmortizationRatePercent() != null) {
            existingContract.setAmortizationRatePercent(
                contract.getAmortizationRatePercent());
          }
          if (contract.getAmortizationRateCurrency() != null) {
            existingContract.setAmortizationRateCurrency(
                contract.getAmortizationRateCurrency());
          }
          if (contract.getTeeAmount() != null) {
            existingContract.setTeeAmount(contract.getTeeAmount());
          }
          if (contract.getTeeInterval() != null) {
            existingContract.setTeeInterval(contract.getTeeInterval());
          }
          if (contract.getAcquisitionDistributionCosts() != null) {
            existingContract.setAcquisitionDistributionCosts(contract.getAcquisitionDistributionCosts());
          }
          if (contract.getAnnualFee() != null) {
            existingContract.setAnnualFee(contract.getAnnualFee());
          }
          if (contract.getRetirementStart() != null) {
            existingContract.setRetirementStart(contract.getRetirementStart());
          }
          if (contract.getTrailerLoad() != null) {
            existingContract.setTrailerLoad(contract.getTrailerLoad());
          }
          if (contract.getNoTees() != null) {
            existingContract.setNoTees(contract.getNoTees());
          }
          if (contract.getEmployeeNumber() != null) {
            existingContract.setEmployeeNumber(contract.getEmployeeNumber());
          }
          if (contract.getUnemploymentInsuranceAmount() != null) {
            existingContract.setUnemploymentInsuranceAmount(contract.getUnemploymentInsuranceAmount());
          }
          if (contract.getUnemploymentInsuranceEmployer() != null) {
            existingContract.setUnemploymentInsuranceEmployer(contract.getUnemploymentInsuranceEmployer());
          }
          if (contract.getPriceOfWork() != null) {
            existingContract.setPriceOfWork(contract.getPriceOfWork());
          }
          if (contract.getTotalEnergyPrice() != null) {
            existingContract.setTotalEnergyPrice(contract.getTotalEnergyPrice());
          }
          if (contract.getEnergyPriceKwh() != null) {
            existingContract.setEnergyPriceKwh(contract.getEnergyPriceKwh());
          }
          if (contract.getWorkplace() != null) {
            existingContract.setWorkplace(contract.getWorkplace());
          }
          if (contract.getWorkingHoursPerMonth() != null) {
            existingContract.setWorkingHoursPerMonth(contract.getWorkingHoursPerMonth());
          }
          if (contract.getWorkingHoursPerweek() != null) {
            existingContract.setWorkingHoursPerweek(contract.getWorkingHoursPerweek());
          }
          if (contract.getWorkingDaysPerWeek() != null) {
            existingContract.setWorkingDaysPerWeek(contract.getWorkingDaysPerWeek());
          }
          if (contract.getForeignAssignmentFees() != null) {
            existingContract.setForeignAssignmentFees(contract.getForeignAssignmentFees());
          }
          if (contract.getMaximumBandwidth() != null) {
            existingContract.setMaximumBandwidth(contract.getMaximumBandwidth());
          }
          if (contract.getCashWithdrawalWorldwide() != null) {
            existingContract.setCashWithdrawalWorldwide(contract.getCashWithdrawalWorldwide());
          }
          if (contract.getTemporaryUntil() != null) {
            existingContract.setTemporaryUntil(contract.getTemporaryUntil());
          }
          if (contract.getAidEntitled() != null) {
            existingContract.setAidEntitled(contract.getAidEntitled());
          }
          if (contract.getBic() != null) {
            existingContract.setBic(contract.getBic());
          }
          if (contract.getOccupationalDisabilityPensionMonthly() != null) {
            existingContract
                .setOccupationalDisabilityPensionMonthly(contract.getOccupationalDisabilityPensionMonthly());
          }
          if (contract.getLoanNumber() != null) {
            existingContract.setLoanNumber(contract.getLoanNumber());
          }
          if (contract.getDataVolumePerMonth() != null) {
            existingContract.setDataVolumePerMonth(contract.getDataVolumePerMonth());
          }
          if (contract.getDate() != null) {
            existingContract.setDate(contract.getDate());
          }
          if (contract.getDispolimit() != null) {
            existingContract.setDispolimit(contract.getDispolimit());
          }
          if (contract.getDispolimitValidFrom() != null) {
            existingContract.setDispolimitValidFrom(contract.getDispolimitValidFrom());
          }
          if (contract.getCompanyEstablishment() != null) {
            existingContract.setCompanyEstablishment(contract.getCompanyEstablishment());
          }
          if (contract.getDynamic() != null) {
            existingContract.setDynamic(contract.getDynamic());
          }
          if (contract.getContributionsPaidLastYear() != null) {
            existingContract.setContributionsPaidLastYear(contract.getContributionsPaidLastYear());
          }
          if (contract.getRegistrationFees() != null) {
            existingContract.setRegistrationFees(contract.getRegistrationFees());
          }
          if (contract.getCurrentPayPoints() != null) {
            existingContract.setCurrentPayPoints(contract.getCurrentPayPoints());
          }
          if (contract.getHazardGroup() != null) {
            existingContract.setHazardGroup(contract.getHazardGroup());
          }
          if (contract.getNumberOfSalaries() != null) {
            existingContract.setNumberOfSalaries(contract.getNumberOfSalaries());
          }
          if (contract.getSalaryBonus() != null) {
            existingContract.setSalaryBonus(contract.getSalaryBonus());
          }
          if (contract.getTotalDeductions() != null) {
            existingContract.setTotalDeductions(contract.getTotalDeductions());
          }
          if (contract.getTotalPriceSale() != null) {
            existingContract.setTotalPriceSale(contract.getTotalPriceSale());
          }
          if (contract.getCreditorIDNumber() != null) {
            existingContract.setCreditorIDNumber(contract.getCreditorIDNumber());
          }
          if (contract.getRealestateTransferTax() != null) {
            existingContract.setRealestateTransferTax(contract.getRealestateTransferTax());
          }
          if (contract.getRealestateTransferTaxPercent() != null) {
            existingContract.setRealestateTransferTaxPercent(contract.getRealestateTransferTaxPercent());
          }
          if (contract.getBasicCharge() != null) {
            existingContract.setBasicCharge(contract.getBasicCharge());
          }
          if (contract.getBasicPrice() != null) {
            existingContract.setBasicPrice(contract.getBasicPrice());
          }
          if (contract.getPropertyTaxPercent() != null) {
            existingContract.setPropertyTaxPercent(contract.getPropertyTaxPercent());
          }
          if (contract.getPropertyTax() != null) {
            existingContract.setPropertyTax(contract.getPropertyTax());
          }
          if (contract.getGroupContract() != null) {
            existingContract.setGroupContract(contract.getGroupContract());
          }
          if (contract.getIban() != null) {
            existingContract.setIban(contract.getIban());
          }
          if (contract.getBuyer() != null) {
            existingContract.setBuyer(contract.getBuyer());
          }
          if (contract.getBail() != null) {
            existingContract.setBail(contract.getBail());
          }
          if (contract.getMark() != null) {
            existingContract.setMark(contract.getMark());
          }
          if (contract.getCarBrand() != null) {
            existingContract.setCarBrand(contract.getCarBrand());
          }
          if (contract.getChurchTax() != null) {
            existingContract.setChurchTax(contract.getChurchTax());
          }
          if (contract.getLoanAmount() != null) {
            existingContract.setLoanAmount(contract.getLoanAmount());
          }
          if (contract.getCustomerNumber() != null) {
            existingContract.setCustomerNumber(contract.getCustomerNumber());
          }
          if (contract.getAccountManagementFee() != null) {
            existingContract.setAccountManagementFee(contract.getAccountManagementFee());
          }
          if (contract.getDiscountPercent() != null) {
            existingContract.setDiscountPercent(contract.getDiscountPercent());
          }
          if (contract.getDiscount() != null) {
            existingContract.setDiscount(contract.getDiscount());
          }
          if (contract.getPeriodOfNotice() != null) {
            existingContract.setPeriodOfNotice(contract.getPeriodOfNotice());
          }
          if (contract.getRuntime() != null) {
            existingContract.setRuntime(contract.getRuntime());
          }
          if (contract.getDurationInMonths() != null) {
            existingContract.setDurationInMonths(contract.getDurationInMonths());
          }
          if (contract.getLastTerminationDate() != null) {
            existingContract.setLastTerminationDate(contract.getLastTerminationDate());
          }
          if (contract.getGross() != null) {
            existingContract.setGross(contract.getGross());
          }
          if (contract.getNet() != null) {
            existingContract.setNet(contract.getNet());
          }
          if (contract.getWageTax() != null) {
            existingContract.setWageTax(contract.getWageTax());
          }
          if (contract.getTaxBracket() != null) {
            existingContract.setTaxBracket(contract.getTaxBracket());
          }
          if (contract.getBrokerprovPercent() != null) {
            existingContract.setBrokerprovPercent(contract.getBrokerprovPercent());
          }
          if (contract.getBrokerprov() != null) {
            existingContract.setBrokerprov(contract.getBrokerprov());
          }
          if (contract.getMobilePhoneNumber() != null) {
            existingContract.setMobilePhoneNumber(contract.getMobilePhoneNumber());
          }
          if (contract.getMonthlyFee() != null) {
            existingContract.setMonthlyFee(contract.getMonthlyFee());
          }
          if (contract.getNotary() != null) {
            existingContract.setNotary(contract.getNotary());
          }
          if (contract.getNotaryFeePercent() != null) {
            existingContract.setNotaryFeePercent(contract.getNotaryFeePercent());
          }
          if (contract.getNotaryFee() != null) {
            existingContract.setNotaryFee(contract.getNotaryFee());
          }
          if (contract.getOnlineTariff() != null) {
            existingContract.setOnlineTariff(contract.getOnlineTariff());
          }
          if (contract.getLongtermCareInsuranceEmployer() != null) {
            existingContract.setLongtermCareInsuranceEmployer(contract.getLongtermCareInsuranceEmployer());
          }
          if (contract.getLongtermCareInsuranceEmployee() != null) {
            existingContract.setLongtermCareInsuranceEmployee(contract.getLongtermCareInsuranceEmployee());
          }
          if (contract.getProbationaryPeriod() != null) {
            existingContract.setProbationaryPeriod(contract.getProbationaryPeriod());
          }
          if (contract.getProductName() != null) {
            existingContract.setProductName(contract.getProductName());
          }
          if (contract.getProgression() != null) {
            existingContract.setProgression(contract.getProgression());
          }
          if (contract.getSquareMetre() != null) {
            existingContract.setSquareMetre(contract.getSquareMetre());
          }
          if (contract.getStandardOldAgePension() != null) {
            existingContract.setStandardOldAgePension(contract.getStandardOldAgePension());
          }
          if (contract.getStandardOldAgePensionDate() != null) {
            existingContract.setStandardOldAgePensionDate(contract.getStandardOldAgePensionDate());
          }
          if (contract.getRemainingDebtDate() != null) {
            existingContract.setRemainingDebtDate(contract.getRemainingDebtDate());
          }
          if (contract.getSurrenderValue() != null) {
            existingContract.setSurrenderValue(contract.getSurrenderValue());
          }
          if (contract.getDeductible() != null) {
            existingContract.setDeductible(contract.getDeductible());
          }
          if (contract.getSmsFlat() != null) {
            existingContract.setSmsFlat(contract.getSmsFlat());
          }
          if (contract.getDebitInterestYear() != null) {
            existingContract.setDebitInterestYear(contract.getDebitInterestYear());
          }
          if (contract.getTotalSocialSecurityContributions() != null) {
            existingContract.setTotalSocialSecurityContributions(contract.getTotalSocialSecurityContributions());
          }
          if (contract.getTaxRate() != null) {
            existingContract.setTaxRate(contract.getTaxRate());
          }
          if (contract.getTariffName() != null) {
            existingContract.setTariffName(contract.getTariffName());
          }
          if (contract.getPhoneFlat() != null) {
            existingContract.setPhoneFlat(contract.getPhoneFlat());
          }
          if (contract.getInterestOnExcess() != null) {
            existingContract.setInterestOnExcess(contract.getInterestOnExcess());
          }
          if (contract.getInterestOnExcessYearPercent() != null) {
            existingContract.setInterestOnExcessYearPercent(contract.getInterestOnExcessYearPercent());
          }
          if (contract.getHolidayMoney() != null) {
            existingContract.setHolidayMoney(contract.getHolidayMoney());
          }
          if (contract.getSumInsured() != null) {
            existingContract.setSumInsured(contract.getSumInsured());
          }
          if (contract.getContractExtensionPossible() != null) {
            existingContract.setContractExtensionPossible(contract.getContractExtensionPossible());
          }
          if (contract.getMaintenanceInterval() != null) {
            existingContract.setMaintenanceInterval(contract.getMaintenanceInterval());
          }
          if (contract.getChristmasBonus() != null) {
            existingContract.setChristmasBonus(contract.getChristmasBonus());
          }
          if (contract.getAmount() != null) {
            existingContract.setAmount(contract.getAmount());
          }
          if (contract.getContractCreator() != null) {
            existingContract.setContractCreator(contract.getContractCreator());
          }

          return existingContract;
        })
        .map(contractRepository::save);
  }

  /**
   * Get all the contracts.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Page<Contract> findAll(Pageable pageable) {
    log.debug("Request to get all Contracts");
    return contractRepository.findAll(pageable);
  }

  /**
   * Get one contract by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Optional<Contract> findOne(Long id) {
    log.debug("Request to get Contract : {}", id);
    return contractRepository.findById(id);
  }

  /**
   * Delete the contract by id.
   *
   * @param id the id of the entity.
   */
  public void delete(Long id) {
    log.debug("Request to delete Contract : {}", id);
    contractRepository.deleteById(id);
  }

  /**
   * Get all contracts for an application user with full relationships.
   *
   * @param applicationUser the application user.
   * @return the list of contracts with full relationships.
   */
  @Transactional(readOnly = true)
  public List<Contract> findAllByApplicationUser(ApplicationUser applicationUser) {
    log.debug("Request to get all Contracts for ApplicationUser : {}", applicationUser.getId());
    return contractRepository.findAllWithCategoryRelationsByApplicationUser(applicationUser);
  }
}
