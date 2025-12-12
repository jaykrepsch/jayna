package me.jayna.service;

import java.util.List;
import java.util.Optional;
import me.jayna.domain.FinanceAccount;
import me.jayna.domain.ApplicationUser;
import me.jayna.repository.FinanceAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FinanceAccount}.
 */
@Service
@Transactional
public class FinanceAccountService {

  private final Logger log = LoggerFactory.getLogger(
      FinanceAccountService.class);

  private final FinanceAccountRepository financeAccountRepository;

  public FinanceAccountService(
      FinanceAccountRepository financeAccountRepository) {
    this.financeAccountRepository = financeAccountRepository;
  }

  /**
   * Save a financeAccount.
   *
   * @param financeAccount the entity to save.
   * @return the persisted entity.
   */
  public FinanceAccount save(FinanceAccount financeAccount) {
    log.debug("Request to save FinanceAccount : {}", financeAccount);
    return financeAccountRepository.save(financeAccount);
  }

  /**
   * Update a financeAccount.
   *
   * @param financeAccount the entity to save.
   * @return the persisted entity.
   */
  public FinanceAccount update(FinanceAccount financeAccount) {
    log.debug("Request to save FinanceAccount : {}", financeAccount);
    return financeAccountRepository.save(financeAccount);
  }

  /**
   * Partially update a financeAccount.
   *
   * @param financeAccount the entity to update partially.
   * @return the persisted entity.
   */
  public Optional<FinanceAccount> partialUpdate(FinanceAccount financeAccount) {
    log.debug(
        "Request to partially update FinanceAccount : {}",
        financeAccount);

    return financeAccountRepository
        .findById(financeAccount.getId())
        .map(existingFinanceAccount -> {
          if (financeAccount.getStartDate() != null) {
            existingFinanceAccount.setStartDate(financeAccount.getStartDate());
          }
          if (financeAccount.getEndDate() != null) {
            existingFinanceAccount.setEndDate(financeAccount.getEndDate());
          }
          if (financeAccount.getAccountGiver() != null) {
            existingFinanceAccount.setAccountGiver(
                financeAccount.getAccountGiver());
          }
          if (financeAccount.getAccountUser() != null) {
            existingFinanceAccount.setAccountUser(
                financeAccount.getAccountUser());
          }
          if (financeAccount.getIban() != null) {
            existingFinanceAccount.setIban(financeAccount.getIban());
          }
          if (financeAccount.getBic() != null) {
            existingFinanceAccount.setBic(financeAccount.getBic());
          }
          if (financeAccount.getAccountCoRent() != null) {
            existingFinanceAccount.setAccountCoRent(
                financeAccount.getAccountCoRent());
          }
          if (financeAccount.getOverdraftInterest() != null) {
            existingFinanceAccount.setOverdraftInterest(
                financeAccount.getOverdraftInterest());
          }
          if (financeAccount.getLimit() != null) {
            existingFinanceAccount.setLimit(financeAccount.getLimit());
          }
          if (financeAccount.getPaymentUntil() != null) {
            existingFinanceAccount.setPaymentUntil(
                financeAccount.getPaymentUntil());
          }
          if (financeAccount.getCardFee() != null) {
            existingFinanceAccount.setCardFee(financeAccount.getCardFee());
          }
          if (financeAccount.getLoanInterest() != null) {
            existingFinanceAccount.setLoanInterest(
                financeAccount.getLoanInterest());
          }
          if (financeAccount.getAccountMaintenanceFee() != null) {
            existingFinanceAccount.setAccountMaintenanceFee(
                financeAccount.getAccountMaintenanceFee());
          }
          if (financeAccount.getAccountNumber() != null) {
            existingFinanceAccount.setAccountNumber(
                financeAccount.getAccountNumber());
          }
          if (financeAccount.getBankNumber() != null) {
            existingFinanceAccount.setBankNumber(financeAccount.getBankNumber());
          }
          if (financeAccount.getComment() != null) {
            existingFinanceAccount.setComment(financeAccount.getComment());
          }

          return existingFinanceAccount;
        })
        .map(financeAccountRepository::save);
  }

  /**
   * Get all the financeAccounts.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Page<FinanceAccount> findAll(Pageable pageable) {
    log.debug("Request to get all FinanceAccounts");
    return financeAccountRepository.findAll(pageable);
  }

  /**
   * Get one financeAccount by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Optional<FinanceAccount> findOne(Long id) {
    log.debug("Request to get FinanceAccount : {}", id);
    return financeAccountRepository.findById(id);
  }

  /**
   * Delete the financeAccount by id.
   *
   * @param id the id of the entity.
   */
  public void delete(Long id) {
    log.debug("Request to delete FinanceAccount : {}", id);
    financeAccountRepository.deleteById(id);
  }

  /**
   * Get all financeAccounts for an application user with full relationships.
   *
   * @param applicationUser the application user.
   * @return the list of financeAccounts with full relationships.
   */
  @Transactional(readOnly = true)
  public List<FinanceAccount> findAllByApplicationUser(ApplicationUser applicationUser) {
    log.debug("Request to get all FinanceAccounts for ApplicationUser : {}", applicationUser.getId());
    return financeAccountRepository.findAllByApplicationUser(applicationUser);
  }

  /**
   * Get all financeAccounts with category relations for the given application user.
   *
   * @param applicationUser the application user.
   * @return the financeAccounts with category relations.
   */
  @Transactional(readOnly = true)
  public List<FinanceAccount> findAllWithCategoryRelationsByApplicationUser(ApplicationUser applicationUser) {
    log.debug("find all financeAccounts with category relations for application user: {}", applicationUser.getId());
    return financeAccountRepository.findAllWithCategoryRelationsByApplicationUser(applicationUser);
  }
}
