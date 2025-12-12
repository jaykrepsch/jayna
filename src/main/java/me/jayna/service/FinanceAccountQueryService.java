package me.jayna.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import me.jayna.domain.*; // for static metamodels
import me.jayna.domain.FinanceAccount;
import me.jayna.repository.FinanceAccountRepository;
import me.jayna.service.criteria.FinanceAccountCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link FinanceAccount} entities in
 * the database.
 * The main input is a {@link FinanceAccountCriteria} which gets converted to
 * {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FinanceAccount} or a {@link Page} of
 * {@link FinanceAccount} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FinanceAccountQueryService extends QueryService<FinanceAccount> {

  private final Logger log = LoggerFactory.getLogger(
      FinanceAccountQueryService.class);

  private final FinanceAccountRepository financeAccountRepository;

  public FinanceAccountQueryService(
      FinanceAccountRepository financeAccountRepository) {
    this.financeAccountRepository = financeAccountRepository;
  }

  /**
   * Return a {@link List} of {@link FinanceAccount} which matches the criteria
   * from the database.
   * 
   * @param criteria The object which holds all the filters, which the entities
   *                 should match.
   * @return the matching entities.
   */
  @Transactional(readOnly = true)
  public List<FinanceAccount> findByCriteria(FinanceAccountCriteria criteria) {
    log.debug(
        "find by criteria : {}",
        criteria.toString().replaceAll("[\n\r\t]", "_"));
    final Specification<FinanceAccount> specification = createSpecification(
        criteria);
    return financeAccountRepository.findAll(specification);
  }

  /**
   * Return a {@link Page} of {@link FinanceAccount} which matches the criteria
   * from the database.
   * 
   * @param criteria The object which holds all the filters, which the entities
   *                 should match.
   * @param page     The page, which should be returned.
   * @return the matching entities.
   */
  @Transactional(readOnly = true)
  public Page<FinanceAccount> findByCriteria(
      FinanceAccountCriteria criteria,
      Pageable page) {
    log.debug(
        "find by criteria : {}, page: {}",
        criteria.toString().replaceAll("[\n\r\t]", "_"),
        page);
    final Specification<FinanceAccount> specification = createSpecification(
        criteria);
    return financeAccountRepository.findAll(specification, page);
  }

  /**
   * Return the number of matching entities in the database.
   * 
   * @param criteria The object which holds all the filters, which the entities
   *                 should match.
   * @return the number of matching entities.
   */
  @Transactional(readOnly = true)
  public long countByCriteria(FinanceAccountCriteria criteria) {
    log.debug(
        "count by criteria : {}",
        criteria.toString().replaceAll("[\n\r\t]", "_"));
    final Specification<FinanceAccount> specification = createSpecification(
        criteria);
    return financeAccountRepository.count(specification);
  }

  /**
   * Function to convert {@link FinanceAccountCriteria} to a {@link Specification}
   * 
   * @param criteria The object which holds all the filters, which the entities
   *                 should match.
   * @return the matching {@link Specification} of the entity.
   */
  protected Specification<FinanceAccount> createSpecification(
      FinanceAccountCriteria criteria) {
    Specification<FinanceAccount> specification = Specification.where(null);
    if (criteria != null) {
      // This has to be called first, because the distinct method returns null
      if (criteria.getDistinct() != null) {
        specification = specification.and(distinct(criteria.getDistinct()));
      }
      if (criteria.getId() != null) {
        specification = specification.and(
            buildRangeSpecification(criteria.getId(), FinanceAccount_.id));
      }
      if (criteria.getStartDate() != null) {
        specification = specification.and(
            buildRangeSpecification(
                criteria.getStartDate(),
                FinanceAccount_.startDate));
      }
      if (criteria.getEndDate() != null) {
        specification = specification.and(
            buildRangeSpecification(
                criteria.getEndDate(),
                FinanceAccount_.endDate));
      }
      if (criteria.getAccountGiver() != null) {
        specification = specification.and(
            buildStringSpecification(
                criteria.getAccountGiver(),
                FinanceAccount_.accountGiver));
      }
      if (criteria.getAccountUser() != null) {
        specification = specification.and(
            buildStringSpecification(
                criteria.getAccountUser(),
                FinanceAccount_.accountUser));
      }
      if (criteria.getIban() != null) {
        specification = specification.and(
            buildStringSpecification(criteria.getIban(), FinanceAccount_.iban));
      }
      if (criteria.getBic() != null) {
        specification = specification.and(
            buildStringSpecification(criteria.getBic(), FinanceAccount_.bic));
      }
      if (criteria.getAccountCoRent() != null) {
        specification = specification.and(
            buildStringSpecification(
                criteria.getAccountCoRent(),
                FinanceAccount_.accountCoRent));
      }
      if (criteria.getOverdraftInterest() != null) {
        specification = specification.and(
            buildRangeSpecification(
                criteria.getOverdraftInterest(),
                FinanceAccount_.overdraftInterest));
      }
      if (criteria.getLimit() != null) {
        specification = specification.and(
            buildRangeSpecification(criteria.getLimit(), FinanceAccount_.limit));
      }
      if (criteria.getPaymentUntil() != null) {
        specification = specification.and(
            buildRangeSpecification(
                criteria.getPaymentUntil(),
                FinanceAccount_.paymentUntil));
      }
      if (criteria.getCardFee() != null) {
        specification = specification.and(
            buildRangeSpecification(
                criteria.getCardFee(),
                FinanceAccount_.cardFee));
      }
      if (criteria.getLoanInterest() != null) {
        specification = specification.and(
            buildRangeSpecification(
                criteria.getLoanInterest(),
                FinanceAccount_.loanInterest));
      }
      if (criteria.getAccountMaintenanceFee() != null) {
        specification = specification.and(
            buildRangeSpecification(
                criteria.getAccountMaintenanceFee(),
                FinanceAccount_.accountMaintenanceFee));
      }
      if (criteria.getAccountNumber() != null) {
        specification = specification.and(
            buildStringSpecification(
                criteria.getAccountNumber(),
                FinanceAccount_.accountNumber));
      }
      if (criteria.getBankNumber() != null) {
        specification = specification.and(
            buildStringSpecification(
                criteria.getBankNumber(),
                FinanceAccount_.bankNumber));
      }
      if (criteria.getComment() != null) {
        specification = specification.and(
            buildStringSpecification(
                criteria.getComment(),
                FinanceAccount_.comment));
      }
      if (criteria.getApplicationUserId() != null) {
        specification = specification.and(
            buildSpecification(
                criteria.getApplicationUserId(),
                root -> root
                    .join(FinanceAccount_.applicationUser, JoinType.LEFT)
                    .get(ApplicationUser_.id)));
      }
      if (criteria.getGroupTypeId() != null) {
        specification = specification.and(
            buildSpecification(
                criteria.getGroupTypeId(),
                root -> root
                    .join(FinanceAccount_.groupType, JoinType.LEFT)
                    .get(GroupType_.id)));
      }
      if (criteria.getContactFinanceAccountId() != null) {
        specification = specification.and(
            buildSpecification(
                criteria.getContactFinanceAccountId(),
                root -> root
                    .join(FinanceAccount_.contactFinanceAccounts, JoinType.LEFT)
                    .get(ContactFinanceAccount_.id)));
      }
    }
    return specification;
  }
}
