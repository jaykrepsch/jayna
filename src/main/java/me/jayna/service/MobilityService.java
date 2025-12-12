package me.jayna.service;

import me.jayna.domain.Mobility;
import me.jayna.repository.MobilityRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import me.jayna.domain.ApplicationUser;
import java.util.List;

/**
 * Service Implementation for managing {@link Mobility}.
 */
@Service
@Transactional
public class MobilityService {

  private final Logger log = LoggerFactory.getLogger(MobilityService.class);

  private final MobilityRepository mobilityRepository;

  public MobilityService(MobilityRepository mobilityRepository) {
    this.mobilityRepository = mobilityRepository;
  }

  /**
   * Save a mobility.
   *
   * @param mobility the entity to save.
   * @return the persisted entity.
   */
  public Mobility save(Mobility mobility) {
    log.debug("Request to save Mobility : {}", mobility);
    return mobilityRepository.save(mobility);
  }

  /**
   * Update a mobility.
   *
   * @param mobility the entity to save.
   * @return the persisted entity.
   */
  public Mobility update(Mobility mobility) {
    log.debug("Request to save Mobility : {}", mobility);
    return mobilityRepository.save(mobility);
  }

  /**
   * Partially update a mobility.
   *
   * @param mobility the entity to update partially.
   * @return the persisted entity.
   */
  public Optional<Mobility> partialUpdate(Mobility mobility) {
    log.debug("Request to partially update Mobility : {}", mobility);

    return mobilityRepository
        .findById(mobility.getId())
        .map(existingMobility -> {
          if (mobility.getStatus() != null) {
            existingMobility.setStatus(mobility.getStatus());
          }
          if (mobility.getOwner() != null) {
            existingMobility.setOwner(mobility.getOwner());
          }
          if (mobility.getUser() != null) {
            existingMobility.setUser(mobility.getUser());
          }
          if (mobility.getDateFirstRegistration() != null) {
            existingMobility.setDateFirstRegistration(mobility.getDateFirstRegistration());
          }
          if (mobility.getBrand() != null) {
            existingMobility.setBrand(mobility.getBrand());
          }
          if (mobility.getType() != null) {
            existingMobility.setType(mobility.getType());
          }
          if (mobility.getTradeName() != null) {
            existingMobility.setTradeName(mobility.getTradeName());
          }
          if (mobility.getVehicleIdNumber() != null) {
            existingMobility.setVehicleIdNumber(mobility.getVehicleIdNumber());
          }
          if (mobility.getTechnicallyPermissibleMaximumMassInKg() != null) {
            existingMobility
                .setTechnicallyPermissibleMaximumMassInKg(mobility.getTechnicallyPermissibleMaximumMassInKg());
          }
          if (mobility.getMaximumPermissibleMassKgRegistrationState() != null) {
            existingMobility
                .setMaximumPermissibleMassKgRegistrationState(mobility.getMaximumPermissibleMassKgRegistrationState());
          }
          if (mobility.getEmptyMass() != null) {
            existingMobility.setEmptyMass(mobility.getEmptyMass());
          }
          if (mobility.getPeriodValidity() != null) {
            existingMobility.setPeriodValidity(mobility.getPeriodValidity());
          }
          if (mobility.getApprovalDate() != null) {
            existingMobility.setApprovalDate(mobility.getApprovalDate());
          }
          if (mobility.getVehicleClass() != null) {
            existingMobility.setVehicleClass(mobility.getVehicleClass());
          }
          if (mobility.getEcTypeApprovalNumberABE() != null) {
            existingMobility.setEcTypeApprovalNumberABE(mobility.getEcTypeApprovalNumberABE());
          }
          if (mobility.getNoAxes() != null) {
            existingMobility.setNoAxes(mobility.getNoAxes());
          }
          if (mobility.getTechnicallyPermissibleTrailerLoadBrakedKg() != null) {
            existingMobility
                .setTechnicallyPermissibleTrailerLoadBrakedKg(mobility.getTechnicallyPermissibleTrailerLoadBrakedKg());
          }
          if (mobility.getTechnicallyPermissibleTrailerLoadUnbrakedKg() != null) {
            existingMobility.setTechnicallyPermissibleTrailerLoadUnbrakedKg(
                mobility.getTechnicallyPermissibleTrailerLoadUnbrakedKg());
          }
          if (mobility.getDisplacement() != null) {
            existingMobility.setDisplacement(mobility.getDisplacement());
          }
          if (mobility.getNominalPowerKwKg() != null) {
            existingMobility.setNominalPowerKwKg(mobility.getNominalPowerKwKg());
          }
          if (mobility.getTypeFuelEnergySource() != null) {
            existingMobility.setTypeFuelEnergySource(mobility.getTypeFuelEnergySource());
          }
          if (mobility.getNominalNumberMin() != null) {
            existingMobility.setNominalNumberMin(mobility.getNominalNumberMin());
          }
          if (mobility.getPowerWeightRatioKwKg() != null) {
            existingMobility.setPowerWeightRatioKwKg(mobility.getPowerWeightRatioKwKg());
          }
          if (mobility.getVehicleColour() != null) {
            existingMobility.setVehicleColour(mobility.getVehicleColour());
          }
          if (mobility.getNoSeats() != null) {
            existingMobility.setNoSeats(mobility.getNoSeats());
          }
          if (mobility.getNoStandingPlaces() != null) {
            existingMobility.setNoStandingPlaces(mobility.getNoStandingPlaces());
          }
          if (mobility.getMaximumSpeedKmh() != null) {
            existingMobility.setMaximumSpeedKmh(mobility.getMaximumSpeedKmh());
          }
          if (mobility.getStationaryNoiseDb() != null) {
            existingMobility.setStationaryNoiseDb(mobility.getStationaryNoiseDb());
          }
          if (mobility.getNoRevolutions() != null) {
            existingMobility.setNoRevolutions(mobility.getNoRevolutions());
          }
          if (mobility.getDrivingNoiseDb() != null) {
            existingMobility.setDrivingNoiseDb(mobility.getDrivingNoiseDb());
          }
          if (mobility.getCo2GKm() != null) {
            existingMobility.setCo2GKm(mobility.getCo2GKm());
          }
          if (mobility.getPollutantClassRelevantEg() != null) {
            existingMobility.setPollutantClassRelevantEg(mobility.getPollutantClassRelevantEg());
          }
          if (mobility.getManufacturerAbbreviation() != null) {
            existingMobility.setManufacturerAbbreviation(mobility.getManufacturerAbbreviation());
          }
          if (mobility.getManufacturingNumbercodeTo2() != null) {
            existingMobility.setManufacturingNumbercodeTo2(mobility.getManufacturingNumbercodeTo2());
          }
          if (mobility.getTypeKeyNumberD2() != null) {
            existingMobility.setTypeKeyNumberD2(mobility.getTypeKeyNumberD2());
          }
          if (mobility.getCheckDigitVehicleIdNumber() != null) {
            existingMobility.setCheckDigitVehicleIdNumber(mobility.getCheckDigitVehicleIdNumber());
          }
          if (mobility.getTypeOfConstruction() != null) {
            existingMobility.setTypeOfConstruction(mobility.getTypeOfConstruction());
          }
          if (mobility.getDesignationVehicleClassBodywork() != null) {
            existingMobility.setDesignationVehicleClassBodywork(mobility.getDesignationVehicleClassBodywork());
          }
          if (mobility.getDateToK() != null) {
            existingMobility.setDateToK(mobility.getDateToK());
          }
          if (mobility.getTechnicallyPermissibleMaxAxleLoad() != null) {
            existingMobility.setTechnicallyPermissibleMaxAxleLoad(mobility.getTechnicallyPermissibleMaxAxleLoad());
          }
          if (mobility.getAxis1Tech() != null) {
            existingMobility.setAxis1Tech(mobility.getAxis1Tech());
          }
          if (mobility.getAxis2Tech() != null) {
            existingMobility.setAxis2Tech(mobility.getAxis2Tech());
          }
          if (mobility.getAxis3Tech() != null) {
            existingMobility.setAxis3Tech(mobility.getAxis3Tech());
          }
          if (mobility.getMaximumPermissibleAxleLoadMemberStateKg() != null) {
            existingMobility
                .setMaximumPermissibleAxleLoadMemberStateKg(mobility.getMaximumPermissibleAxleLoadMemberStateKg());
          }
          if (mobility.getAxis1Ms() != null) {
            existingMobility.setAxis1Ms(mobility.getAxis1Ms());
          }
          if (mobility.getAxis2Ms() != null) {
            existingMobility.setAxis2Ms(mobility.getAxis2Ms());
          }
          if (mobility.getAxis3Ms() != null) {
            existingMobility.setAxis3Ms(mobility.getAxis3Ms());
          }
          if (mobility.getNumberDriveAxles() != null) {
            existingMobility.setNumberDriveAxles(mobility.getNumberDriveAxles());
          }
          if (mobility.getCodeToP3() != null) {
            existingMobility.setCodeToP3(mobility.getCodeToP3());
          }
          if (mobility.getCodeToR() != null) {
            existingMobility.setCodeToR(mobility.getCodeToR());
          }
          if (mobility.getVolumeTankTankersCcm() != null) {
            existingMobility.setVolumeTankTankersCcm(mobility.getVolumeTankTankersCcm());
          }
          if (mobility.getVerticalLoadKg() != null) {
            existingMobility.setVerticalLoadKg(mobility.getVerticalLoadKg());
          }
          if (mobility.getDesignationNationalEmissionClass() != null) {
            existingMobility.setDesignationNationalEmissionClass(mobility.getDesignationNationalEmissionClass());
          }
          if (mobility.getTyres() != null) {
            existingMobility.setTyres(mobility.getTyres());
          }
          if (mobility.getOnAxis1() != null) {
            existingMobility.setOnAxis1(mobility.getOnAxis1());
          }
          if (mobility.getOnAxis2() != null) {
            existingMobility.setOnAxis2(mobility.getOnAxis2());
          }
          if (mobility.getOnAxis3() != null) {
            existingMobility.setOnAxis3(mobility.getOnAxis3());
          }
          if (mobility.getRegistrationCertificateNumberPartII() != null) {
            existingMobility.setRegistrationCertificateNumberPartII(mobility.getRegistrationCertificateNumberPartII());
          }
          if (mobility.getFeaturesOfTheTypeApproval() != null) {
            existingMobility.setFeaturesOfTheTypeApproval(mobility.getFeaturesOfTheTypeApproval());
          }
          if (mobility.getLengthMm() != null) {
            existingMobility.setLengthMm(mobility.getLengthMm());
          }
          if (mobility.getWidthMm() != null) {
            existingMobility.setWidthMm(mobility.getWidthMm());
          }
          if (mobility.getHeightMm() != null) {
            existingMobility.setHeightMm(mobility.getHeightMm());
          }
          if (mobility.getOtherEndorsements() != null) {
            existingMobility.setOtherEndorsements(mobility.getOtherEndorsements());
          }
          if (mobility.getRemarksAndExceptions() != null) {
            existingMobility.setRemarksAndExceptions(mobility.getRemarksAndExceptions());
          }
          if (mobility.getNumberDoors() != null) {
            existingMobility.setNumberDoors(mobility.getNumberDoors());
          }
          if (mobility.getHorsepower() != null) {
            existingMobility.setHorsepower(mobility.getHorsepower());
          }
          if (mobility.getDraft() != null) {
            existingMobility.setDraft(mobility.getDraft());
          }
          if (mobility.getMileageDate() != null) {
            existingMobility.setMileageDate(mobility.getMileageDate());
          }
          if (mobility.getMileage() != null) {
            existingMobility.setMileage(mobility.getMileage());
          }
          if (mobility.getLastGeneralInspection() != null) {
            existingMobility.setLastGeneralInspection(mobility.getLastGeneralInspection());
          }
          if (mobility.getFinalEmissionsTest() != null) {
            existingMobility.setFinalEmissionsTest(mobility.getFinalEmissionsTest());
          }
          if (mobility.getNextEmissionsTest() != null) {
            existingMobility.setNextEmissionsTest(mobility.getNextEmissionsTest());
          }
          if (mobility.getNextGeneralInspection() != null) {
            existingMobility.setNextGeneralInspection(mobility.getNextGeneralInspection());
          }
          if (mobility.getFrameType() != null) {
            existingMobility.setFrameType(mobility.getFrameType());
          }
          if (mobility.getFrameMaterial() != null) {
            existingMobility.setFrameMaterial(mobility.getFrameMaterial());
          }
          if (mobility.getCircuit() != null) {
            existingMobility.setCircuit(mobility.getCircuit());
          }
          if (mobility.getWheelSize() != null) {
            existingMobility.setWheelSize(mobility.getWheelSize());
          }
          if (mobility.getFrameHeight() != null) {
            existingMobility.setFrameHeight(mobility.getFrameHeight());
          }
          if (mobility.getColor() != null) {
            existingMobility.setColor(mobility.getColor());
          }
          if (mobility.getFrameNo() != null) {
            existingMobility.setFrameNo(mobility.getFrameNo());
          }
          if (mobility.getManufacturer() != null) {
            existingMobility.setManufacturer(mobility.getManufacturer());
          }
          if (mobility.getOriginalYardNumberIIYardNumber() != null) {
            existingMobility.setOriginalYardNumberIIYardNumber(mobility.getOriginalYardNumberIIYardNumber());
          }
          if (mobility.getMainBuildingMaterial() != null) {
            existingMobility.setMainBuildingMaterial(mobility.getMainBuildingMaterial());
          }
          if (mobility.getPreviousLicensePlate() != null) {
            existingMobility.setPreviousLicensePlate(mobility.getPreviousLicensePlate());
          }
          if (mobility.getEngineNumber() != null) {
            existingMobility.setEngineNumber(mobility.getEngineNumber());
          }
          if (mobility.getEngineManufacturers() != null) {
            existingMobility.setEngineManufacturers(mobility.getEngineManufacturers());
          }
          if (mobility.getEngineMakeType() != null) {
            existingMobility.setEngineMakeType(mobility.getEngineMakeType());
          }
          if (mobility.getDriveType() != null) {
            existingMobility.setDriveType(mobility.getDriveType());
          }
          if (mobility.getzDriveNumber() != null) {
            existingMobility.setzDriveNumber(mobility.getzDriveNumber());
          }
          if (mobility.getIdCardIssuedDate() != null) {
            existingMobility.setIdCardIssuedDate(mobility.getIdCardIssuedDate());
          }
          if (mobility.getCarLicensePlates() != null) {
            existingMobility.setCarLicensePlates(mobility.getCarLicensePlates());
          }

          return existingMobility;
        })
        .map(mobilityRepository::save);
  }

  /**
   * Get all the mobilities.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Page<Mobility> findAll(Pageable pageable) {
    log.debug("Request to get all Mobilities");
    return mobilityRepository.findAll(pageable);
  }

  /**
   * Get one mobility by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Optional<Mobility> findOne(Long id) {
    log.debug("Request to get Mobility : {}", id);
    return mobilityRepository.findById(id);
  }

  /**
   * Delete the mobility by id.
   *
   * @param id the id of the entity.
   */
  public void delete(Long id) {
    log.debug("Request to delete Mobility : {}", id);
    mobilityRepository.deleteById(id);
  }

  /**
   * Get all mobilities for an application user with full relationships.
   *
   * @param applicationUser the application user.
   * @return the list of mobilities with full relationships.
   */
  @Transactional(readOnly = true)
  public List<Mobility> findAllByApplicationUser(ApplicationUser applicationUser) {
    log.debug("Request to get all Mobilities for ApplicationUser : {}", applicationUser.getId());
    return mobilityRepository.findAllByApplicationUser(applicationUser);
  }

  /**
   * Get all mobilities with category relations for the given application user.
   *
   * @param applicationUser the application user.
   * @return the mobilities with category relations.
   */
  @Transactional(readOnly = true)
  public List<Mobility> findAllWithCategoryRelationsByApplicationUser(ApplicationUser applicationUser) {
    log.debug("find all mobilities with category relations for application user: {}", applicationUser.getId());
    return mobilityRepository.findAllWithCategoryRelationsByApplicationUser(applicationUser);
  }
}
