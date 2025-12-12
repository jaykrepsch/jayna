package me.jayna.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import me.jayna.domain.*; // for static metamodels
import me.jayna.domain.Contact;
import me.jayna.repository.ContactRepository;
import me.jayna.service.criteria.ContactCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Contact} entities in the database.
 * The main input is a {@link ContactCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Contact} or a {@link Page} of {@link Contact} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContactQueryService extends QueryService<Contact> {

  private final Logger log = LoggerFactory.getLogger(ContactQueryService.class);

  private final ContactRepository contactRepository;

  public ContactQueryService(ContactRepository contactRepository) {
    this.contactRepository = contactRepository;
  }

  /**
   * Return a {@link List} of {@link Contact} which matches the criteria from the database.
   * @param criteria The object which holds all the filters, which the entities should match.
   * @return the matching entities.
   */
  @Transactional(readOnly = true)
  public List<Contact> findByCriteria(ContactCriteria criteria) {
    log.debug(
      "find by criteria : {}",
      criteria.toString().replaceAll("[\n\r\t]", "_")
    );
    final Specification<Contact> specification = createSpecification(criteria);
    return contactRepository.findAll(specification);
  }

  /**
   * Return a {@link Page} of {@link Contact} which matches the criteria from the database.
   * @param criteria The object which holds all the filters, which the entities should match.
   * @param page The page, which should be returned.
   * @return the matching entities.
   */
  @Transactional(readOnly = true)
  public Page<Contact> findByCriteria(ContactCriteria criteria, Pageable page) {
    log.debug(
      "find by criteria : {}, page: {}",
      criteria.toString().replaceAll("[\n\r\t]", "_"),
      page
    );
    final Specification<Contact> specification = createSpecification(criteria);
    return contactRepository.findAll(specification, page);
  }

  /**
   * Return the number of matching entities in the database.
   * @param criteria The object which holds all the filters, which the entities should match.
   * @return the number of matching entities.
   */
  @Transactional(readOnly = true)
  public long countByCriteria(ContactCriteria criteria) {
    log.debug(
      "count by criteria : {}",
      criteria.toString().replaceAll("[\n\r\t]", "_")
    );
    final Specification<Contact> specification = createSpecification(criteria);
    return contactRepository.count(specification);
  }

  /**
   * Function to convert {@link ContactCriteria} to a {@link Specification}
   * @param criteria The object which holds all the filters, which the entities should match.
   * @return the matching {@link Specification} of the entity.
   */
  protected Specification<Contact> createSpecification(
    ContactCriteria criteria
  ) {
    Specification<Contact> specification = Specification.where(null);
    if (criteria != null) {
      // This has to be called first, because the distinct method returns null
      if (criteria.getDistinct() != null) {
        specification = specification.and(distinct(criteria.getDistinct()));
      }
      if (criteria.getId() != null) {
        specification =
          specification.and(
            buildRangeSpecification(criteria.getId(), Contact_.id)
          );
      }
      if (criteria.getTitle() != null) {
        specification =
          specification.and(
            buildSpecification(criteria.getTitle(), Contact_.title)
          );
      }
      if (criteria.getSalutation() != null) {
        specification =
          specification.and(
            buildSpecification(criteria.getSalutation(), Contact_.salutation)
          );
      }
      if (criteria.getRank() != null) {
        specification =
          specification.and(
            buildSpecification(criteria.getRank(), Contact_.rank)
          );
      }
      if (criteria.getGender() != null) {
        specification =
          specification.and(
            buildSpecification(criteria.getGender(), Contact_.gender)
          );
      }
      if (criteria.getSurName() != null) {
        specification =
          specification.and(
            buildStringSpecification(criteria.getSurName(), Contact_.surName)
          );
      }
      if (criteria.getFirstName() != null) {
        specification =
          specification.and(
            buildStringSpecification(
              criteria.getFirstName(),
              Contact_.firstName
            )
          );
      }
      if (criteria.getOtherFirstNames() != null) {
        specification =
          specification.and(
            buildStringSpecification(
              criteria.getOtherFirstNames(),
              Contact_.otherFirstNames
            )
          );
      }
      if (criteria.getBirthName() != null) {
        specification =
          specification.and(
            buildStringSpecification(
              criteria.getBirthName(),
              Contact_.birthName
            )
          );
      }
      if (criteria.getMaritalStatus() != null) {
        specification =
          specification.and(
            buildSpecification(
              criteria.getMaritalStatus(),
              Contact_.maritalStatus
            )
          );
      }
      if (criteria.getNickName() != null) {
        specification =
          specification.and(
            buildStringSpecification(criteria.getNickName(), Contact_.nickName)
          );
      }
      if (criteria.getBirthDate() != null) {
        specification =
          specification.and(
            buildRangeSpecification(criteria.getBirthDate(), Contact_.birthDate)
          );
      }
      if (criteria.getBirthCity() != null) {
        specification =
          specification.and(
            buildStringSpecification(
              criteria.getBirthCity(),
              Contact_.birthCity
            )
          );
      }
      if (criteria.getBirthCountry() != null) {
        specification =
          specification.and(
            buildStringSpecification(
              criteria.getBirthCountry(),
              Contact_.birthCountry
            )
          );
      }
      if (criteria.getNationality() != null) {
        specification =
          specification.and(
            buildStringSpecification(
              criteria.getNationality(),
              Contact_.nationality
            )
          );
      }
      if (criteria.getAddressType() != null) {
        specification =
          specification.and(
            buildSpecification(criteria.getAddressType(), Contact_.addressType)
          );
      }
      if (criteria.getStreet() != null) {
        specification =
          specification.and(
            buildStringSpecification(criteria.getStreet(), Contact_.street)
          );
      }
      if (criteria.getStreetNumber() != null) {
        specification =
          specification.and(
            buildStringSpecification(
              criteria.getStreetNumber(),
              Contact_.streetNumber
            )
          );
      }
      if (criteria.getPostalCode() != null) {
        specification =
          specification.and(
            buildStringSpecification(
              criteria.getPostalCode(),
              Contact_.postalCode
            )
          );
      }
      if (criteria.getCity() != null) {
        specification =
          specification.and(
            buildStringSpecification(criteria.getCity(), Contact_.city)
          );
      }
      if (criteria.getAdditionalAddressField1() != null) {
        specification =
          specification.and(
            buildStringSpecification(
              criteria.getAdditionalAddressField1(),
              Contact_.additionalAddressField1
            )
          );
      }
      if (criteria.getAdditionalAddressField2() != null) {
        specification =
          specification.and(
            buildStringSpecification(
              criteria.getAdditionalAddressField2(),
              Contact_.additionalAddressField2
            )
          );
      }
      if (criteria.getCommunicationType() != null) {
        specification =
          specification.and(
            buildSpecification(
              criteria.getCommunicationType(),
              Contact_.communicationType
            )
          );
      }
      if (criteria.getPhoneCountryCode() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getPhoneCountryCode(),
              Contact_.phoneCountryCode
            )
          );
      }
      if (criteria.getPhonePrefix() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getPhonePrefix(),
              Contact_.phonePrefix
            )
          );
      }
      if (criteria.getPhoneNumber() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getPhoneNumber(),
              Contact_.phoneNumber
            )
          );
      }
      if (criteria.getComment() != null) {
        specification =
          specification.and(
            buildStringSpecification(criteria.getComment(), Contact_.comment)
          );
      }
      if (criteria.getApplicationUserId() != null) {
        specification =
          specification.and(
            buildSpecification(
              criteria.getApplicationUserId(),
              root ->
                root
                  .join(Contact_.applicationUser, JoinType.LEFT)
                  .get(ApplicationUser_.id)
            )
          );
      }
      if (criteria.getGroupTypeId() != null) {
        specification =
          specification.and(
            buildSpecification(
              criteria.getGroupTypeId(),
              root ->
                root.join(Contact_.groupType, JoinType.LEFT).get(GroupType_.id)
            )
          );
      }
      if (criteria.getContactFinanceAccountId() != null) {
        specification =
          specification.and(
            buildSpecification(
              criteria.getContactFinanceAccountId(),
              root ->
                root
                  .join(Contact_.contactFinanceAccounts, JoinType.LEFT)
                  .get(ContactFinanceAccount_.id)
            )
          );
      }
    }
    return specification;
  }
}
