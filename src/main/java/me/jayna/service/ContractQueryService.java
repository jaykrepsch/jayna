package me.jayna.service;

import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.JoinType;
import me.jayna.domain.*; // for static metamodels
import me.jayna.domain.Contract;
import me.jayna.domain.GroupType;
import me.jayna.repository.ContractRepository;
import me.jayna.repository.GroupTypeRepository;
import me.jayna.service.criteria.ContractCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Contract} entities in the
 * database.
 * The main input is a {@link ContractCriteria} which gets converted to
 * {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Contract} or a {@link Page} of
 * {@link Contract} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContractQueryService extends QueryService<Contract> {

  private final Logger log = LoggerFactory.getLogger(
      ContractQueryService.class);

  private final ContractRepository contractRepository;
  private final GroupTypeRepository groupTypeRepository;

  public ContractQueryService(ContractRepository contractRepository, GroupTypeRepository groupTypeRepository) {
    this.contractRepository = contractRepository;
    this.groupTypeRepository = groupTypeRepository;
  }

  /**
   * Return a {@link List} of {@link Contract} with category relations for the given application user.
   * Automatically sets a default GroupType if none is assigned.
   * 
   * @param applicationUser The application user to filter by.
   * @return the contracts with category relations.
   */
  @Transactional(readOnly = true)
  public List<Contract> findAllWithCategoryRelationsByApplicationUser(ApplicationUser applicationUser) {
    log.debug("find all contracts with category relations for application user: {}", applicationUser.getId());
    
    // Verwende eine einfachere Abfrage ohne komplexe Joins
    List<Contract> contracts = contractRepository.findByApplicationUser(applicationUser);
    
    // Lade die GroupType-Beziehungen manuell
    for (Contract contract : contracts) {
      if (contract.getGroupType() != null) {
        // Lade die GroupType mit ihren Beziehungen
        groupTypeRepository.findById(contract.getGroupType().getId()).ifPresent(groupType -> {
          contract.setGroupType(groupType);
        });
      }
    }
    
    return contracts;
  }

  /**
   * Return a {@link List} of {@link Contract} which matches the criteria from the
   * database.
   * 
   * @param criteria The object which holds all the filters, which the entities
   *                 should match.
   * @return the matching entities.
   */
  @Transactional(readOnly = true)
  public List<Contract> findByCriteria(ContractCriteria criteria) {
    log.debug(
        "find by criteria : {}",
        criteria.toString().replaceAll("[\n\r\t]", "_"));
    final Specification<Contract> specification = createSpecification(criteria);
    return contractRepository.findAll(specification);
  }

  /**
   * Return a {@link Page} of {@link Contract} which matches the criteria from the
   * database.
   * 
   * @param criteria The object which holds all the filters, which the entities
   *                 should match.
   * @param page     The page, which should be returned.
   * @return the matching entities.
   */
  @Transactional(readOnly = true)
  public Page<Contract> findByCriteria(
      ContractCriteria criteria,
      Pageable page) {
    log.debug(
        "find by criteria : {}, page: {}",
        criteria.toString().replaceAll("[\n\r\t]", "_"),
        page);
    final Specification<Contract> specification = createSpecification(criteria);
    return contractRepository.findAll(specification, page);
  }

  /**
   * Return the number of matching entities in the database.
   * 
   * @param criteria The object which holds all the filters, which the entities
   *                 should match.
   * @return the number of matching entities.
   */
  @Transactional(readOnly = true)
  public long countByCriteria(ContractCriteria criteria) {
    log.debug(
        "count by criteria : {}",
        criteria.toString().replaceAll("[\n\r\t]", "_"));
    final Specification<Contract> specification = createSpecification(criteria);
    return contractRepository.count(specification);
  }

  /**
   * Function to convert {@link ContractCriteria} to a {@link Specification}
   * 
   * @param criteria The object which holds all the filters, which the entities
   *                 should match.
   * @return the matching {@link Specification} of the entity.
   */
  protected Specification<Contract> createSpecification(
      ContractCriteria criteria) {
    Specification<Contract> specification = Specification.where(null);
    if (criteria != null) {
      // This has to be called first, because the distinct method returns null
      if (criteria.getDistinct() != null) {
        specification = specification.and(distinct(criteria.getDistinct()));
      }
      if (criteria.getId() != null) {
        specification = specification.and(
            buildRangeSpecification(criteria.getId(), Contract_.id));
      }
      if (criteria.getContractor() != null) {
        specification = specification.and(
            buildStringSpecification(
                criteria.getContractor(),
                Contract_.contractor));
      }
      if (criteria.getContractOwner() != null) {
        specification = specification.and(
            buildStringSpecification(
                criteria.getContractOwner(),
                Contract_.contractOwner));
      }
      if (criteria.getContractUser() != null) {
        specification = specification.and(
            buildStringSpecification(
                criteria.getContractUser(),
                Contract_.contractUser));
      }
      if (criteria.getContractNumber() != null) {
        specification = specification.and(
            buildStringSpecification(
                criteria.getContractNumber(),
                Contract_.contractNumber));
      }
      if (criteria.getStartDate() != null) {
        specification = specification.and(
            buildRangeSpecification(
                criteria.getStartDate(),
                Contract_.startDate));
      }
      if (criteria.getEndDate() != null) {
        specification = specification.and(
            buildRangeSpecification(criteria.getEndDate(), Contract_.endDate));
      }
      if (criteria.getApplicationDate() != null) {
        specification = specification.and(
            buildRangeSpecification(
                criteria.getApplicationDate(),
                Contract_.applicationDate));
      }
      if (criteria.getConfirmationDate() != null) {
        specification = specification.and(
            buildRangeSpecification(
                criteria.getConfirmationDate(),
                Contract_.confirmationDate));
      }
      if (criteria.getPaymentPattern() != null) {
        specification = specification.and(
            buildSpecification(
                criteria.getPaymentPattern(),
                Contract_.paymentPattern));
      }
      if (criteria.getComment() != null) {
        specification = specification.and(
            buildStringSpecification(criteria.getComment(), Contract_.comment));
      }
      if (criteria.getPayer() != null) {
        specification = specification.and(
            buildStringSpecification(criteria.getPayer(), Contract_.payer));
      }
      if (criteria.getPaymentType() != null) {
        specification = specification.and(
            buildSpecification(criteria.getPaymentType(), Contract_.paymentType));
      }
      if (criteria.getOccupation() != null) {
        specification = specification.and(
            buildStringSpecification(
                criteria.getOccupation(),
                Contract_.occupation));
      }
      if (criteria.getActivities() != null) {
        specification = specification.and(
            buildStringSpecification(
                criteria.getActivities(),
                Contract_.activities));
      }
      if (criteria.getLoanValue() != null) {
        specification = specification.and(
            buildRangeSpecification(
                criteria.getLoanValue(),
                Contract_.loanValue));
      }
      if (criteria.getInterestRatePercent() != null) {
        specification = specification.and(
            buildRangeSpecification(
                criteria.getInterestRatePercent(),
                Contract_.interestRatePercent));
      }
      if (criteria.getInterestRateCurrency() != null) {
        specification = specification.and(
            buildRangeSpecification(
                criteria.getInterestRateCurrency(),
                Contract_.interestRateCurrency));
      }
      if (criteria.getAmortizationRatePercent() != null) {
        specification = specification.and(
            buildRangeSpecification(
                criteria.getAmortizationRatePercent(),
                Contract_.amortizationRatePercent));
      }
      if (criteria.getAmortizationRateCurrency() != null) {
        specification = specification.and(
            buildRangeSpecification(
                criteria.getAmortizationRateCurrency(),
                Contract_.amortizationRateCurrency));
      }
      if (criteria.getApplicationUserId() != null) {
        specification = specification.and(
            buildSpecification(
                criteria.getApplicationUserId(),
                root -> root
                    .join(Contract_.applicationUser, JoinType.LEFT)
                    .get(ApplicationUser_.id)));
      }
      if (criteria.getGroupTypeId() != null) {
        specification = specification.and(
            buildSpecification(
                criteria.getGroupTypeId(),
                root -> root.join(Contract_.groupType, JoinType.LEFT).get(GroupType_.id)));
      }
      if (criteria.getContractRealestatesId() != null) {
        specification = specification.and(
            buildSpecification(
                criteria.getContractRealestatesId(),
                root -> root
                    .join(Contract_.contractRealestates, JoinType.LEFT)
                    .get(ContractRealestate_.id)));
      }
    }
    return specification;
  }
}
