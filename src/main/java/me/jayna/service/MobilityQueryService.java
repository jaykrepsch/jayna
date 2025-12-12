package me.jayna.service;

import me.jayna.domain.*; // for static metamodels
import me.jayna.repository.MobilityRepository;
import me.jayna.service.criteria.MobilityCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Mobility} entities in the
 * database.
 * The main input is a {@link MobilityCriteria} which gets converted to
 * {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Mobility} or a {@link Page} of
 * {@link Mobility} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MobilityQueryService extends QueryService<Mobility> {

  private final Logger log = LoggerFactory.getLogger(MobilityQueryService.class);

  private final MobilityRepository mobilityRepository;

  public MobilityQueryService(MobilityRepository mobilityRepository) {
    this.mobilityRepository = mobilityRepository;
  }

  /**
   * Return a {@link List} of {@link Mobility} which matches the criteria from the
   * database.
   * 
   * @param criteria The object which holds all the filters, which the entities
   *                 should match.
   * @return the matching entities.
   */
  @Transactional(readOnly = true)
  public List<Mobility> findByCriteria(MobilityCriteria criteria) {
    log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
    final Specification<Mobility> specification = createSpecification(criteria);
    return mobilityRepository.findAll(specification);
  }

  /**
   * Return a {@link Page} of {@link Mobility} which matches the criteria from the
   * database.
   * 
   * @param criteria The object which holds all the filters, which the entities
   *                 should match.
   * @param page     The page, which should be returned.
   * @return the matching entities.
   */
  @Transactional(readOnly = true)
  public Page<Mobility> findByCriteria(MobilityCriteria criteria, Pageable page) {
    log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
    final Specification<Mobility> specification = createSpecification(criteria);
    return mobilityRepository.findAll(specification, page);
  }

  /**
   * Return the number of matching entities in the database.
   * 
   * @param criteria The object which holds all the filters, which the entities
   *                 should match.
   * @return the number of matching entities.
   */
  @Transactional(readOnly = true)
  public long countByCriteria(MobilityCriteria criteria) {
    log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
    final Specification<Mobility> specification = createSpecification(criteria);
    return mobilityRepository.count(specification);
  }

  /**
   * Function to convert {@link MobilityCriteria} to a {@link Specification}
   * 
   * @param criteria The object which holds all the filters, which the entities
   *                 should match.
   * @return the matching {@link Specification} of the entity.
   */
  protected Specification<Mobility> createSpecification(MobilityCriteria criteria) {
    Specification<Mobility> specification = Specification.where(null);
    if (criteria != null) {
      // This has to be called first, because the distinct method returns null
      if (criteria.getDistinct() != null) {
        specification = specification.and(distinct(criteria.getDistinct()));
      }
      if (criteria.getId() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getId(), Mobility_.id));
      }
      if (criteria.getStatus() != null) {
        specification = specification.and(buildStringSpecification(criteria.getStatus(), Mobility_.status));
      }
      if (criteria.getOwner() != null) {
        specification = specification.and(buildStringSpecification(criteria.getOwner(), Mobility_.owner));
      }
      if (criteria.getUser() != null) {
        specification = specification.and(buildStringSpecification(criteria.getUser(), Mobility_.user));
      }
      if (criteria.getDateFirstRegistration() != null) {
        specification = specification
            .and(buildRangeSpecification(criteria.getDateFirstRegistration(), Mobility_.dateFirstRegistration));
      }
      if (criteria.getBrand() != null) {
        specification = specification.and(buildStringSpecification(criteria.getBrand(), Mobility_.brand));
      }
      if (criteria.getType() != null) {
        specification = specification.and(buildStringSpecification(criteria.getType(), Mobility_.type));
      }
      if (criteria.getTradeName() != null) {
        specification = specification.and(buildStringSpecification(criteria.getTradeName(), Mobility_.tradeName));
      }
      if (criteria.getVehicleIdNumber() != null) {
        specification = specification
            .and(buildStringSpecification(criteria.getVehicleIdNumber(), Mobility_.vehicleIdNumber));
      }
      if (criteria.getTechnicallyPermissibleMaximumMassInKg() != null) {
        specification = specification.and(
            buildRangeSpecification(
                criteria.getTechnicallyPermissibleMaximumMassInKg(),
                Mobility_.technicallyPermissibleMaximumMassInKg));
      }
      if (criteria.getMaximumPermissibleMassKgRegistrationState() != null) {
        specification = specification.and(
            buildRangeSpecification(
                criteria.getMaximumPermissibleMassKgRegistrationState(),
                Mobility_.maximumPermissibleMassKgRegistrationState));
      }
      if (criteria.getEmptyMass() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getEmptyMass(), Mobility_.emptyMass));
      }
      if (criteria.getPeriodValidity() != null) {
        specification = specification
            .and(buildStringSpecification(criteria.getPeriodValidity(), Mobility_.periodValidity));
      }
      if (criteria.getApprovalDate() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getApprovalDate(), Mobility_.approvalDate));
      }
      if (criteria.getVehicleClass() != null) {
        specification = specification.and(buildStringSpecification(criteria.getVehicleClass(), Mobility_.vehicleClass));
      }
      if (criteria.getEcTypeApprovalNumberABE() != null) {
        specification = specification
            .and(buildStringSpecification(criteria.getEcTypeApprovalNumberABE(), Mobility_.ecTypeApprovalNumberABE));
      }
      if (criteria.getNoAxes() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getNoAxes(), Mobility_.noAxes));
      }
      if (criteria.getTechnicallyPermissibleTrailerLoadBrakedKg() != null) {
        specification = specification.and(
            buildRangeSpecification(
                criteria.getTechnicallyPermissibleTrailerLoadBrakedKg(),
                Mobility_.technicallyPermissibleTrailerLoadBrakedKg));
      }
      if (criteria.getTechnicallyPermissibleTrailerLoadUnbrakedKg() != null) {
        specification = specification.and(
            buildRangeSpecification(
                criteria.getTechnicallyPermissibleTrailerLoadUnbrakedKg(),
                Mobility_.technicallyPermissibleTrailerLoadUnbrakedKg));
      }
      if (criteria.getDisplacement() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getDisplacement(), Mobility_.displacement));
      }
      if (criteria.getNominalPowerKwKg() != null) {
        specification = specification
            .and(buildRangeSpecification(criteria.getNominalPowerKwKg(), Mobility_.nominalPowerKwKg));
      }
      if (criteria.getTypeFuelEnergySource() != null) {
        specification = specification
            .and(buildStringSpecification(criteria.getTypeFuelEnergySource(), Mobility_.typeFuelEnergySource));
      }
      if (criteria.getNominalNumberMin() != null) {
        specification = specification
            .and(buildRangeSpecification(criteria.getNominalNumberMin(), Mobility_.nominalNumberMin));
      }
      if (criteria.getPowerWeightRatioKwKg() != null) {
        specification = specification
            .and(buildRangeSpecification(criteria.getPowerWeightRatioKwKg(), Mobility_.powerWeightRatioKwKg));
      }
      if (criteria.getVehicleColour() != null) {
        specification = specification
            .and(buildStringSpecification(criteria.getVehicleColour(), Mobility_.vehicleColour));
      }
      if (criteria.getNoSeats() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getNoSeats(), Mobility_.noSeats));
      }
      if (criteria.getNoStandingPlaces() != null) {
        specification = specification
            .and(buildRangeSpecification(criteria.getNoStandingPlaces(), Mobility_.noStandingPlaces));
      }
      if (criteria.getMaximumSpeedKmh() != null) {
        specification = specification
            .and(buildRangeSpecification(criteria.getMaximumSpeedKmh(), Mobility_.maximumSpeedKmh));
      }
      if (criteria.getStationaryNoiseDb() != null) {
        specification = specification
            .and(buildRangeSpecification(criteria.getStationaryNoiseDb(), Mobility_.stationaryNoiseDb));
      }
      if (criteria.getNoRevolutions() != null) {
        specification = specification
            .and(buildRangeSpecification(criteria.getNoRevolutions(), Mobility_.noRevolutions));
      }
      if (criteria.getDrivingNoiseDb() != null) {
        specification = specification
            .and(buildRangeSpecification(criteria.getDrivingNoiseDb(), Mobility_.drivingNoiseDb));
      }
      if (criteria.getCo2GKm() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getCo2GKm(), Mobility_.co2GKm));
      }
      if (criteria.getPollutantClassRelevantEg() != null) {
        specification = specification
            .and(buildStringSpecification(criteria.getPollutantClassRelevantEg(), Mobility_.pollutantClassRelevantEg));
      }
      if (criteria.getManufacturerAbbreviation() != null) {
        specification = specification
            .and(buildStringSpecification(criteria.getManufacturerAbbreviation(), Mobility_.manufacturerAbbreviation));
      }
      if (criteria.getManufacturingNumbercodeTo2() != null) {
        specification = specification.and(
            buildStringSpecification(criteria.getManufacturingNumbercodeTo2(), Mobility_.manufacturingNumbercodeTo2));
      }
      if (criteria.getTypeKeyNumberD2() != null) {
        specification = specification
            .and(buildStringSpecification(criteria.getTypeKeyNumberD2(), Mobility_.typeKeyNumberD2));
      }
      if (criteria.getCheckDigitVehicleIdNumber() != null) {
        specification = specification.and(
            buildStringSpecification(criteria.getCheckDigitVehicleIdNumber(), Mobility_.checkDigitVehicleIdNumber));
      }
      if (criteria.getTypeOfConstruction() != null) {
        specification = specification
            .and(buildStringSpecification(criteria.getTypeOfConstruction(), Mobility_.typeOfConstruction));
      }
      if (criteria.getDesignationVehicleClassBodywork() != null) {
        specification = specification.and(
            buildStringSpecification(criteria.getDesignationVehicleClassBodywork(),
                Mobility_.designationVehicleClassBodywork));
      }
      if (criteria.getDateToK() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getDateToK(), Mobility_.dateToK));
      }
      if (criteria.getTechnicallyPermissibleMaxAxleLoad() != null) {
        specification = specification.and(
            buildRangeSpecification(
                criteria.getTechnicallyPermissibleMaxAxleLoad(),
                Mobility_.technicallyPermissibleMaxAxleLoad));
      }
      if (criteria.getAxis1Tech() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getAxis1Tech(), Mobility_.axis1Tech));
      }
      if (criteria.getAxis2Tech() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getAxis2Tech(), Mobility_.axis2Tech));
      }
      if (criteria.getAxis3Tech() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getAxis3Tech(), Mobility_.axis3Tech));
      }
      if (criteria.getMaximumPermissibleAxleLoadMemberStateKg() != null) {
        specification = specification.and(
            buildRangeSpecification(
                criteria.getMaximumPermissibleAxleLoadMemberStateKg(),
                Mobility_.maximumPermissibleAxleLoadMemberStateKg));
      }
      if (criteria.getAxis1Ms() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getAxis1Ms(), Mobility_.axis1Ms));
      }
      if (criteria.getAxis2Ms() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getAxis2Ms(), Mobility_.axis2Ms));
      }
      if (criteria.getAxis3Ms() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getAxis3Ms(), Mobility_.axis3Ms));
      }
      if (criteria.getNumberDriveAxles() != null) {
        specification = specification
            .and(buildRangeSpecification(criteria.getNumberDriveAxles(), Mobility_.numberDriveAxles));
      }
      if (criteria.getCodeToP3() != null) {
        specification = specification.and(buildStringSpecification(criteria.getCodeToP3(), Mobility_.codeToP3));
      }
      if (criteria.getCodeToR() != null) {
        specification = specification.and(buildStringSpecification(criteria.getCodeToR(), Mobility_.codeToR));
      }
      if (criteria.getVolumeTankTankersCcm() != null) {
        specification = specification
            .and(buildRangeSpecification(criteria.getVolumeTankTankersCcm(), Mobility_.volumeTankTankersCcm));
      }
      if (criteria.getVerticalLoadKg() != null) {
        specification = specification
            .and(buildRangeSpecification(criteria.getVerticalLoadKg(), Mobility_.verticalLoadKg));
      }
      if (criteria.getDesignationNationalEmissionClass() != null) {
        specification = specification.and(
            buildStringSpecification(criteria.getDesignationNationalEmissionClass(),
                Mobility_.designationNationalEmissionClass));
      }
      if (criteria.getTyres() != null) {
        specification = specification.and(buildStringSpecification(criteria.getTyres(), Mobility_.tyres));
      }
      if (criteria.getOnAxis1() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getOnAxis1(), Mobility_.onAxis1));
      }
      if (criteria.getOnAxis2() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getOnAxis2(), Mobility_.onAxis2));
      }
      if (criteria.getOnAxis3() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getOnAxis3(), Mobility_.onAxis3));
      }
      if (criteria.getRegistrationCertificateNumberPartII() != null) {
        specification = specification.and(
            buildStringSpecification(
                criteria.getRegistrationCertificateNumberPartII(),
                Mobility_.registrationCertificateNumberPartII));
      }
      if (criteria.getFeaturesOfTheTypeApproval() != null) {
        specification = specification.and(
            buildStringSpecification(criteria.getFeaturesOfTheTypeApproval(), Mobility_.featuresOfTheTypeApproval));
      }
      if (criteria.getLengthMm() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getLengthMm(), Mobility_.lengthMm));
      }
      if (criteria.getWidthMm() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getWidthMm(), Mobility_.widthMm));
      }
      if (criteria.getHeightMm() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getHeightMm(), Mobility_.heightMm));
      }
      if (criteria.getOtherEndorsements() != null) {
        specification = specification
            .and(buildStringSpecification(criteria.getOtherEndorsements(), Mobility_.otherEndorsements));
      }
      if (criteria.getRemarksAndExceptions() != null) {
        specification = specification
            .and(buildStringSpecification(criteria.getRemarksAndExceptions(), Mobility_.remarksAndExceptions));
      }
      if (criteria.getNumberDoors() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getNumberDoors(), Mobility_.numberDoors));
      }
      if (criteria.getHorsepower() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getHorsepower(), Mobility_.horsepower));
      }
      if (criteria.getDraft() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getDraft(), Mobility_.draft));
      }
      if (criteria.getMileageDate() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getMileageDate(), Mobility_.mileageDate));
      }
      if (criteria.getMileage() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getMileage(), Mobility_.mileage));
      }
      if (criteria.getLastGeneralInspection() != null) {
        specification = specification
            .and(buildRangeSpecification(criteria.getLastGeneralInspection(), Mobility_.lastGeneralInspection));
      }
      if (criteria.getFinalEmissionsTest() != null) {
        specification = specification
            .and(buildRangeSpecification(criteria.getFinalEmissionsTest(), Mobility_.finalEmissionsTest));
      }
      if (criteria.getNextEmissionsTest() != null) {
        specification = specification
            .and(buildRangeSpecification(criteria.getNextEmissionsTest(), Mobility_.nextEmissionsTest));
      }
      if (criteria.getNextGeneralInspection() != null) {
        specification = specification
            .and(buildRangeSpecification(criteria.getNextGeneralInspection(), Mobility_.nextGeneralInspection));
      }
      if (criteria.getFrameType() != null) {
        specification = specification.and(buildStringSpecification(criteria.getFrameType(), Mobility_.frameType));
      }
      if (criteria.getFrameMaterial() != null) {
        specification = specification
            .and(buildStringSpecification(criteria.getFrameMaterial(), Mobility_.frameMaterial));
      }
      if (criteria.getCircuit() != null) {
        specification = specification.and(buildStringSpecification(criteria.getCircuit(), Mobility_.circuit));
      }
      if (criteria.getWheelSize() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getWheelSize(), Mobility_.wheelSize));
      }
      if (criteria.getFrameHeight() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getFrameHeight(), Mobility_.frameHeight));
      }
      if (criteria.getColor() != null) {
        specification = specification.and(buildStringSpecification(criteria.getColor(), Mobility_.color));
      }
      if (criteria.getFrameNo() != null) {
        specification = specification.and(buildStringSpecification(criteria.getFrameNo(), Mobility_.frameNo));
      }
      if (criteria.getManufacturer() != null) {
        specification = specification.and(buildStringSpecification(criteria.getManufacturer(), Mobility_.manufacturer));
      }
      if (criteria.getOriginalYardNumberIIYardNumber() != null) {
        specification = specification.and(
            buildStringSpecification(criteria.getOriginalYardNumberIIYardNumber(),
                Mobility_.originalYardNumberIIYardNumber));
      }
      if (criteria.getMainBuildingMaterial() != null) {
        specification = specification
            .and(buildStringSpecification(criteria.getMainBuildingMaterial(), Mobility_.mainBuildingMaterial));
      }
      if (criteria.getPreviousLicensePlate() != null) {
        specification = specification
            .and(buildStringSpecification(criteria.getPreviousLicensePlate(), Mobility_.previousLicensePlate));
      }
      if (criteria.getEngineNumber() != null) {
        specification = specification.and(buildStringSpecification(criteria.getEngineNumber(), Mobility_.engineNumber));
      }
      if (criteria.getEngineManufacturers() != null) {
        specification = specification
            .and(buildStringSpecification(criteria.getEngineManufacturers(), Mobility_.engineManufacturers));
      }
      if (criteria.getEngineMakeType() != null) {
        specification = specification
            .and(buildStringSpecification(criteria.getEngineMakeType(), Mobility_.engineMakeType));
      }
      if (criteria.getDriveType() != null) {
        specification = specification.and(buildStringSpecification(criteria.getDriveType(), Mobility_.driveType));
      }
      if (criteria.getzDriveNumber() != null) {
        specification = specification.and(buildStringSpecification(criteria.getzDriveNumber(), Mobility_.zDriveNumber));
      }
      if (criteria.getIdCardIssuedDate() != null) {
        specification = specification
            .and(buildRangeSpecification(criteria.getIdCardIssuedDate(), Mobility_.idCardIssuedDate));
      }
      if (criteria.getCarLicensePlates() != null) {
        specification = specification
            .and(buildStringSpecification(criteria.getCarLicensePlates(), Mobility_.carLicensePlates));
      }
      if (criteria.getApplicationUserId() != null) {
        specification = specification.and(
            buildSpecification(
                criteria.getApplicationUserId(),
                root -> root
                    .join(Mobility_.applicationUser, JoinType.LEFT)
                    .get(ApplicationUser_.id)));
      }
    }
    return specification;
  }
}
