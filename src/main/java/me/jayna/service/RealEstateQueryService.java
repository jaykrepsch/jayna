package me.jayna.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import me.jayna.domain.*; // for static metamodels
import me.jayna.domain.RealEstate;
import me.jayna.repository.RealEstateRepository;
import me.jayna.service.criteria.RealEstateCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link RealEstate} entities in the database.
 * The main input is a {@link RealEstateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RealEstate} or a {@link Page} of {@link RealEstate} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RealEstateQueryService extends QueryService<RealEstate> {

  private final Logger log = LoggerFactory.getLogger(
    RealEstateQueryService.class
  );

  private final RealEstateRepository realEstateRepository;

  public RealEstateQueryService(RealEstateRepository realEstateRepository) {
    this.realEstateRepository = realEstateRepository;
  }

  /**
   * Return a {@link List} of {@link RealEstate} which matches the criteria from the database.
   * @param criteria The object which holds all the filters, which the entities should match.
   * @return the matching entities.
   */
  @Transactional(readOnly = true)
  public List<RealEstate> findByCriteria(RealEstateCriteria criteria) {
    log.debug(
      "find by criteria : {}",
      criteria.toString().replaceAll("[\n\r\t]", "_")
    );
    final Specification<RealEstate> specification = createSpecification(
      criteria
    );
    return realEstateRepository.findAll(specification);
  }

  /**
   * Return a {@link Page} of {@link RealEstate} which matches the criteria from the database.
   * @param criteria The object which holds all the filters, which the entities should match.
   * @param page The page, which should be returned.
   * @return the matching entities.
   */
  @Transactional(readOnly = true)
  public Page<RealEstate> findByCriteria(
    RealEstateCriteria criteria,
    Pageable page
  ) {
    log.debug(
      "find by criteria : {}, page: {}",
      criteria.toString().replaceAll("[\n\r\t]", "_"),
      page
    );
    final Specification<RealEstate> specification = createSpecification(
      criteria
    );
    return realEstateRepository.findAll(specification, page);
  }

  /**
   * Return the number of matching entities in the database.
   * @param criteria The object which holds all the filters, which the entities should match.
   * @return the number of matching entities.
   */
  @Transactional(readOnly = true)
  public long countByCriteria(RealEstateCriteria criteria) {
    log.debug(
      "count by criteria : {}",
      criteria.toString().replaceAll("[\n\r\t]", "_")
    );
    final Specification<RealEstate> specification = createSpecification(
      criteria
    );
    return realEstateRepository.count(specification);
  }

  /**
   * Function to convert {@link RealEstateCriteria} to a {@link Specification}
   * @param criteria The object which holds all the filters, which the entities should match.
   * @return the matching {@link Specification} of the entity.
   */
  protected Specification<RealEstate> createSpecification(
    RealEstateCriteria criteria
  ) {
    Specification<RealEstate> specification = Specification.where(null);
    if (criteria != null) {
      // This has to be called first, because the distinct method returns null
      if (criteria.getDistinct() != null) {
        specification = specification.and(distinct(criteria.getDistinct()));
      }
      if (criteria.getId() != null) {
        specification =
          specification.and(
            buildRangeSpecification(criteria.getId(), RealEstate_.id)
          );
      }
      if (criteria.getStreet() != null) {
        specification =
          specification.and(
            buildStringSpecification(criteria.getStreet(), RealEstate_.street)
          );
      }
      if (criteria.getStreetNumber() != null) {
        specification =
          specification.and(
            buildStringSpecification(
              criteria.getStreetNumber(),
              RealEstate_.streetNumber
            )
          );
      }
      if (criteria.getPostalCode() != null) {
        specification =
          specification.and(
            buildStringSpecification(
              criteria.getPostalCode(),
              RealEstate_.postalCode
            )
          );
      }
      if (criteria.getCity() != null) {
        specification =
          specification.and(
            buildStringSpecification(criteria.getCity(), RealEstate_.city)
          );
      }
      if (criteria.getState() != null) {
        specification =
          specification.and(
            buildStringSpecification(criteria.getState(), RealEstate_.state)
          );
      }
      if (criteria.getCountry() != null) {
        specification =
          specification.and(
            buildStringSpecification(criteria.getCountry(), RealEstate_.country)
          );
      }
      if (criteria.getPropertyWidth() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getPropertyWidth(),
              RealEstate_.propertyWidth
            )
          );
      }
      if (criteria.getPropertyLength() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getPropertyLength(),
              RealEstate_.propertyLength
            )
          );
      }
      if (criteria.getPropertyArea() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getPropertyArea(),
              RealEstate_.propertyArea
            )
          );
      }
      if (criteria.getArea() != null) {
        specification =
          specification.and(
            buildRangeSpecification(criteria.getArea(), RealEstate_.area)
          );
      }
      if (criteria.getNoHouses() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getNoHouses(),
              RealEstate_.noHouses
            )
          );
      }
      if (criteria.getConstructionYear() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getConstructionYear(),
              RealEstate_.constructionYear
            )
          );
      }
      if (criteria.getDesignType() != null) {
        specification =
          specification.and(
            buildStringSpecification(
              criteria.getDesignType(),
              RealEstate_.designType
            )
          );
      }
      if (criteria.getDesignTypeClass() != null) {
        specification =
          specification.and(
            buildSpecification(
              criteria.getDesignTypeClass(),
              RealEstate_.designTypeClass
            )
          );
      }
      if (criteria.getBuiltUpArea() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getBuiltUpArea(),
              RealEstate_.builtUpArea
            )
          );
      }
      if (criteria.getSealtArea() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getSealtArea(),
              RealEstate_.sealtArea
            )
          );
      }
      if (criteria.getUndevelopedArea() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getUndevelopedArea(),
              RealEstate_.undevelopedArea
            )
          );
      }
      if (criteria.getNoSmokeDetectors() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getNoSmokeDetectors(),
              RealEstate_.noSmokeDetectors
            )
          );
      }
      if (criteria.getNoUnits() != null) {
        specification =
          specification.and(
            buildRangeSpecification(criteria.getNoUnits(), RealEstate_.noUnits)
          );
      }
      if (criteria.getNoFloors() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getNoFloors(),
              RealEstate_.noFloors
            )
          );
      }
      if (criteria.getNoPowerOutlets() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getNoPowerOutlets(),
              RealEstate_.noPowerOutlets
            )
          );
      }
      if (criteria.getNoNetworkSockets() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getNoNetworkSockets(),
              RealEstate_.noNetworkSockets
            )
          );
      }
      if (criteria.getNoLightSwitches() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getNoLightSwitches(),
              RealEstate_.noLightSwitches
            )
          );
      }
      if (criteria.getNoAntennas() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getNoAntennas(),
              RealEstate_.noAntennas
            )
          );
      }
      if (criteria.getNoShutterSwitches() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getNoShutterSwitches(),
              RealEstate_.noShutterSwitches
            )
          );
      }
      if (criteria.getNoRadiators() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getNoRadiators(),
              RealEstate_.noRadiators
            )
          );
      }
      if (criteria.getNoParkings() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getNoParkings(),
              RealEstate_.noParkings
            )
          );
      }
      if (criteria.getNoGarages() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getNoGarages(),
              RealEstate_.noGarages
            )
          );
      }
      if (criteria.getNoCarports() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getNoCarports(),
              RealEstate_.noCarports
            )
          );
      }
      if (criteria.getNoWindows() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getNoWindows(),
              RealEstate_.noWindows
            )
          );
      }
      if (criteria.getWindowArea() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getWindowArea(),
              RealEstate_.windowArea
            )
          );
      }
      if (criteria.getNoElevators() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getNoElevators(),
              RealEstate_.noElevators
            )
          );
      }
      if (criteria.getCorridorArea() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getCorridorArea(),
              RealEstate_.corridorArea
            )
          );
      }
      if (criteria.getNoBasementRooms() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getNoBasementRooms(),
              RealEstate_.noBasementRooms
            )
          );
      }
      if (criteria.getMonumentProtected() != null) {
        specification =
          specification.and(
            buildSpecification(
              criteria.getMonumentProtected(),
              RealEstate_.monumentProtected
            )
          );
      }
      if (criteria.getHeatingType() != null) {
        specification =
          specification.and(
            buildSpecification(
              criteria.getHeatingType(),
              RealEstate_.heatingType
            )
          );
      }
      if (criteria.getRoofPitch() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getRoofPitch(),
              RealEstate_.roofPitch
            )
          );
      }
      if (criteria.getRoofType() != null) {
        specification =
          specification.and(
            buildSpecification(criteria.getRoofType(), RealEstate_.roofType)
          );
      }
      if (criteria.getGableAlignment() != null) {
        specification =
          specification.and(
            buildSpecification(
              criteria.getGableAlignment(),
              RealEstate_.gableAlignment
            )
          );
      }
      if (criteria.getRoofArea() != null) {
        specification =
          specification.and(
            buildRangeSpecification(
              criteria.getRoofArea(),
              RealEstate_.roofArea
            )
          );
      }
      if (criteria.getLongitude() != null) {
        specification =
          specification.and(
            buildStringSpecification(
              criteria.getLongitude(),
              RealEstate_.longitude
            )
          );
      }
      if (criteria.getLatitude() != null) {
        specification =
          specification.and(
            buildStringSpecification(
              criteria.getLatitude(),
              RealEstate_.latitude
            )
          );
      }
      if (criteria.getComment() != null) {
        specification =
          specification.and(
            buildStringSpecification(criteria.getComment(), RealEstate_.comment)
          );
      }
      if (criteria.getApplicationUserId() != null) {
        specification =
          specification.and(
            buildSpecification(
              criteria.getApplicationUserId(),
              root ->
                root
                  .join(RealEstate_.applicationUser, JoinType.LEFT)
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
                root
                  .join(RealEstate_.groupType, JoinType.LEFT)
                  .get(GroupType_.id)
            )
          );
      }
      if (criteria.getContractRealestatesId() != null) {
        specification =
          specification.and(
            buildSpecification(
              criteria.getContractRealestatesId(),
              root ->
                root
                  .join(RealEstate_.contractRealestates, JoinType.LEFT)
                  .get(ContractRealestate_.id)
            )
          );
      }
    }
    return specification;
  }
}
