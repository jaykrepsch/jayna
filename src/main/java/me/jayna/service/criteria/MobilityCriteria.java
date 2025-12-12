package me.jayna.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Mobility} entity.
 * This class is used
 * in {@link com.mycompany.myapp.web.rest.MobilityResource} to receive all the
 * possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /mobilities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific
 * {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class MobilityCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter status;

  private StringFilter owner;

  private StringFilter user;

  private LocalDateFilter dateFirstRegistration;

  private StringFilter brand;

  private StringFilter type;

  private StringFilter tradeName;

  private StringFilter vehicleIdNumber;

  private IntegerFilter technicallyPermissibleMaximumMassInKg;

  private IntegerFilter maximumPermissibleMassKgRegistrationState;

  private IntegerFilter emptyMass;

  private StringFilter periodValidity;

  private LocalDateFilter approvalDate;

  private StringFilter vehicleClass;

  private StringFilter ecTypeApprovalNumberABE;

  private IntegerFilter noAxes;

  private IntegerFilter technicallyPermissibleTrailerLoadBrakedKg;

  private IntegerFilter technicallyPermissibleTrailerLoadUnbrakedKg;

  private IntegerFilter displacement;

  private IntegerFilter nominalPowerKwKg;

  private StringFilter typeFuelEnergySource;

  private IntegerFilter nominalNumberMin;

  private IntegerFilter powerWeightRatioKwKg;

  private StringFilter vehicleColour;

  private IntegerFilter noSeats;

  private IntegerFilter noStandingPlaces;

  private IntegerFilter maximumSpeedKmh;

  private IntegerFilter stationaryNoiseDb;

  private IntegerFilter noRevolutions;

  private IntegerFilter drivingNoiseDb;

  private IntegerFilter co2GKm;

  private StringFilter pollutantClassRelevantEg;

  private StringFilter manufacturerAbbreviation;

  private StringFilter manufacturingNumbercodeTo2;

  private StringFilter typeKeyNumberD2;

  private StringFilter checkDigitVehicleIdNumber;

  private StringFilter typeOfConstruction;

  private StringFilter designationVehicleClassBodywork;

  private LocalDateFilter dateToK;

  private IntegerFilter technicallyPermissibleMaxAxleLoad;

  private IntegerFilter axis1Tech;

  private IntegerFilter axis2Tech;

  private IntegerFilter axis3Tech;

  private IntegerFilter maximumPermissibleAxleLoadMemberStateKg;

  private IntegerFilter axis1Ms;

  private IntegerFilter axis2Ms;

  private IntegerFilter axis3Ms;

  private IntegerFilter numberDriveAxles;

  private StringFilter codeToP3;

  private StringFilter codeToR;

  private IntegerFilter volumeTankTankersCcm;

  private IntegerFilter verticalLoadKg;

  private StringFilter designationNationalEmissionClass;

  private StringFilter tyres;

  private IntegerFilter onAxis1;

  private IntegerFilter onAxis2;

  private IntegerFilter onAxis3;

  private StringFilter registrationCertificateNumberPartII;

  private StringFilter featuresOfTheTypeApproval;

  private IntegerFilter lengthMm;

  private IntegerFilter widthMm;

  private IntegerFilter heightMm;

  private StringFilter otherEndorsements;

  private StringFilter remarksAndExceptions;

  private IntegerFilter numberDoors;

  private IntegerFilter horsepower;

  private IntegerFilter draft;

  private LocalDateFilter mileageDate;

  private IntegerFilter mileage;

  private LocalDateFilter lastGeneralInspection;

  private LocalDateFilter finalEmissionsTest;

  private LocalDateFilter nextEmissionsTest;

  private LocalDateFilter nextGeneralInspection;

  private StringFilter frameType;

  private StringFilter frameMaterial;

  private StringFilter circuit;

  private IntegerFilter wheelSize;

  private IntegerFilter frameHeight;

  private StringFilter color;

  private StringFilter frameNo;

  private StringFilter manufacturer;

  private StringFilter originalYardNumberIIYardNumber;

  private StringFilter mainBuildingMaterial;

  private StringFilter previousLicensePlate;

  private StringFilter engineNumber;

  private StringFilter engineManufacturers;

  private StringFilter engineMakeType;

  private StringFilter driveType;

  private StringFilter zDriveNumber;

  private LocalDateFilter idCardIssuedDate;

  private StringFilter carLicensePlates;

  private Boolean distinct;

  private LongFilter applicationUserId;

  public MobilityCriteria() {
  }

  public MobilityCriteria(MobilityCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.status = other.status == null ? null : other.status.copy();
    this.owner = other.owner == null ? null : other.owner.copy();
    this.user = other.user == null ? null : other.user.copy();
    this.dateFirstRegistration = other.dateFirstRegistration == null ? null : other.dateFirstRegistration.copy();
    this.brand = other.brand == null ? null : other.brand.copy();
    this.type = other.type == null ? null : other.type.copy();
    this.tradeName = other.tradeName == null ? null : other.tradeName.copy();
    this.vehicleIdNumber = other.vehicleIdNumber == null ? null : other.vehicleIdNumber.copy();
    this.technicallyPermissibleMaximumMassInKg = other.technicallyPermissibleMaximumMassInKg == null ? null
        : other.technicallyPermissibleMaximumMassInKg.copy();
    this.maximumPermissibleMassKgRegistrationState = other.maximumPermissibleMassKgRegistrationState == null ? null
        : other.maximumPermissibleMassKgRegistrationState.copy();
    this.emptyMass = other.emptyMass == null ? null : other.emptyMass.copy();
    this.periodValidity = other.periodValidity == null ? null : other.periodValidity.copy();
    this.approvalDate = other.approvalDate == null ? null : other.approvalDate.copy();
    this.vehicleClass = other.vehicleClass == null ? null : other.vehicleClass.copy();
    this.ecTypeApprovalNumberABE = other.ecTypeApprovalNumberABE == null ? null : other.ecTypeApprovalNumberABE.copy();
    this.noAxes = other.noAxes == null ? null : other.noAxes.copy();
    this.technicallyPermissibleTrailerLoadBrakedKg = other.technicallyPermissibleTrailerLoadBrakedKg == null ? null
        : other.technicallyPermissibleTrailerLoadBrakedKg.copy();
    this.technicallyPermissibleTrailerLoadUnbrakedKg = other.technicallyPermissibleTrailerLoadUnbrakedKg == null ? null
        : other.technicallyPermissibleTrailerLoadUnbrakedKg.copy();
    this.displacement = other.displacement == null ? null : other.displacement.copy();
    this.nominalPowerKwKg = other.nominalPowerKwKg == null ? null : other.nominalPowerKwKg.copy();
    this.typeFuelEnergySource = other.typeFuelEnergySource == null ? null : other.typeFuelEnergySource.copy();
    this.nominalNumberMin = other.nominalNumberMin == null ? null : other.nominalNumberMin.copy();
    this.powerWeightRatioKwKg = other.powerWeightRatioKwKg == null ? null : other.powerWeightRatioKwKg.copy();
    this.vehicleColour = other.vehicleColour == null ? null : other.vehicleColour.copy();
    this.noSeats = other.noSeats == null ? null : other.noSeats.copy();
    this.noStandingPlaces = other.noStandingPlaces == null ? null : other.noStandingPlaces.copy();
    this.maximumSpeedKmh = other.maximumSpeedKmh == null ? null : other.maximumSpeedKmh.copy();
    this.stationaryNoiseDb = other.stationaryNoiseDb == null ? null : other.stationaryNoiseDb.copy();
    this.noRevolutions = other.noRevolutions == null ? null : other.noRevolutions.copy();
    this.drivingNoiseDb = other.drivingNoiseDb == null ? null : other.drivingNoiseDb.copy();
    this.co2GKm = other.co2GKm == null ? null : other.co2GKm.copy();
    this.pollutantClassRelevantEg = other.pollutantClassRelevantEg == null ? null
        : other.pollutantClassRelevantEg.copy();
    this.manufacturerAbbreviation = other.manufacturerAbbreviation == null ? null
        : other.manufacturerAbbreviation.copy();
    this.manufacturingNumbercodeTo2 = other.manufacturingNumbercodeTo2 == null ? null
        : other.manufacturingNumbercodeTo2.copy();
    this.typeKeyNumberD2 = other.typeKeyNumberD2 == null ? null : other.typeKeyNumberD2.copy();
    this.checkDigitVehicleIdNumber = other.checkDigitVehicleIdNumber == null ? null
        : other.checkDigitVehicleIdNumber.copy();
    this.typeOfConstruction = other.typeOfConstruction == null ? null : other.typeOfConstruction.copy();
    this.designationVehicleClassBodywork = other.designationVehicleClassBodywork == null ? null
        : other.designationVehicleClassBodywork.copy();
    this.dateToK = other.dateToK == null ? null : other.dateToK.copy();
    this.technicallyPermissibleMaxAxleLoad = other.technicallyPermissibleMaxAxleLoad == null ? null
        : other.technicallyPermissibleMaxAxleLoad.copy();
    this.axis1Tech = other.axis1Tech == null ? null : other.axis1Tech.copy();
    this.axis2Tech = other.axis2Tech == null ? null : other.axis2Tech.copy();
    this.axis3Tech = other.axis3Tech == null ? null : other.axis3Tech.copy();
    this.maximumPermissibleAxleLoadMemberStateKg = other.maximumPermissibleAxleLoadMemberStateKg == null ? null
        : other.maximumPermissibleAxleLoadMemberStateKg.copy();
    this.axis1Ms = other.axis1Ms == null ? null : other.axis1Ms.copy();
    this.axis2Ms = other.axis2Ms == null ? null : other.axis2Ms.copy();
    this.axis3Ms = other.axis3Ms == null ? null : other.axis3Ms.copy();
    this.numberDriveAxles = other.numberDriveAxles == null ? null : other.numberDriveAxles.copy();
    this.codeToP3 = other.codeToP3 == null ? null : other.codeToP3.copy();
    this.codeToR = other.codeToR == null ? null : other.codeToR.copy();
    this.volumeTankTankersCcm = other.volumeTankTankersCcm == null ? null : other.volumeTankTankersCcm.copy();
    this.verticalLoadKg = other.verticalLoadKg == null ? null : other.verticalLoadKg.copy();
    this.designationNationalEmissionClass = other.designationNationalEmissionClass == null ? null
        : other.designationNationalEmissionClass.copy();
    this.tyres = other.tyres == null ? null : other.tyres.copy();
    this.onAxis1 = other.onAxis1 == null ? null : other.onAxis1.copy();
    this.onAxis2 = other.onAxis2 == null ? null : other.onAxis2.copy();
    this.onAxis3 = other.onAxis3 == null ? null : other.onAxis3.copy();
    this.registrationCertificateNumberPartII = other.registrationCertificateNumberPartII == null ? null
        : other.registrationCertificateNumberPartII.copy();
    this.featuresOfTheTypeApproval = other.featuresOfTheTypeApproval == null ? null
        : other.featuresOfTheTypeApproval.copy();
    this.lengthMm = other.lengthMm == null ? null : other.lengthMm.copy();
    this.widthMm = other.widthMm == null ? null : other.widthMm.copy();
    this.heightMm = other.heightMm == null ? null : other.heightMm.copy();
    this.otherEndorsements = other.otherEndorsements == null ? null : other.otherEndorsements.copy();
    this.remarksAndExceptions = other.remarksAndExceptions == null ? null : other.remarksAndExceptions.copy();
    this.numberDoors = other.numberDoors == null ? null : other.numberDoors.copy();
    this.horsepower = other.horsepower == null ? null : other.horsepower.copy();
    this.draft = other.draft == null ? null : other.draft.copy();
    this.mileageDate = other.mileageDate == null ? null : other.mileageDate.copy();
    this.mileage = other.mileage == null ? null : other.mileage.copy();
    this.lastGeneralInspection = other.lastGeneralInspection == null ? null : other.lastGeneralInspection.copy();
    this.finalEmissionsTest = other.finalEmissionsTest == null ? null : other.finalEmissionsTest.copy();
    this.nextEmissionsTest = other.nextEmissionsTest == null ? null : other.nextEmissionsTest.copy();
    this.nextGeneralInspection = other.nextGeneralInspection == null ? null : other.nextGeneralInspection.copy();
    this.frameType = other.frameType == null ? null : other.frameType.copy();
    this.frameMaterial = other.frameMaterial == null ? null : other.frameMaterial.copy();
    this.circuit = other.circuit == null ? null : other.circuit.copy();
    this.wheelSize = other.wheelSize == null ? null : other.wheelSize.copy();
    this.frameHeight = other.frameHeight == null ? null : other.frameHeight.copy();
    this.color = other.color == null ? null : other.color.copy();
    this.frameNo = other.frameNo == null ? null : other.frameNo.copy();
    this.manufacturer = other.manufacturer == null ? null : other.manufacturer.copy();
    this.originalYardNumberIIYardNumber = other.originalYardNumberIIYardNumber == null ? null
        : other.originalYardNumberIIYardNumber.copy();
    this.mainBuildingMaterial = other.mainBuildingMaterial == null ? null : other.mainBuildingMaterial.copy();
    this.previousLicensePlate = other.previousLicensePlate == null ? null : other.previousLicensePlate.copy();
    this.engineNumber = other.engineNumber == null ? null : other.engineNumber.copy();
    this.engineManufacturers = other.engineManufacturers == null ? null : other.engineManufacturers.copy();
    this.engineMakeType = other.engineMakeType == null ? null : other.engineMakeType.copy();
    this.driveType = other.driveType == null ? null : other.driveType.copy();
    this.zDriveNumber = other.zDriveNumber == null ? null : other.zDriveNumber.copy();
    this.idCardIssuedDate = other.idCardIssuedDate == null ? null : other.idCardIssuedDate.copy();
    this.carLicensePlates = other.carLicensePlates == null ? null : other.carLicensePlates.copy();
    this.distinct = other.distinct;
    this.applicationUserId = other.applicationUserId == null ? null : other.applicationUserId.copy();
  }

  @Override
  public MobilityCriteria copy() {
    return new MobilityCriteria(this);
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

  public StringFilter getStatus() {
    return status;
  }

  public StringFilter status() {
    if (status == null) {
      status = new StringFilter();
    }
    return status;
  }

  public void setStatus(StringFilter status) {
    this.status = status;
  }

  public StringFilter getOwner() {
    return owner;
  }

  public StringFilter owner() {
    if (owner == null) {
      owner = new StringFilter();
    }
    return owner;
  }

  public void setOwner(StringFilter owner) {
    this.owner = owner;
  }

  public StringFilter getUser() {
    return user;
  }

  public StringFilter user() {
    if (user == null) {
      user = new StringFilter();
    }
    return user;
  }

  public void setUser(StringFilter user) {
    this.user = user;
  }

  public LocalDateFilter getDateFirstRegistration() {
    return dateFirstRegistration;
  }

  public LocalDateFilter dateFirstRegistration() {
    if (dateFirstRegistration == null) {
      dateFirstRegistration = new LocalDateFilter();
    }
    return dateFirstRegistration;
  }

  public void setDateFirstRegistration(LocalDateFilter dateFirstRegistration) {
    this.dateFirstRegistration = dateFirstRegistration;
  }

  public StringFilter getBrand() {
    return brand;
  }

  public StringFilter brand() {
    if (brand == null) {
      brand = new StringFilter();
    }
    return brand;
  }

  public void setBrand(StringFilter brand) {
    this.brand = brand;
  }

  public StringFilter getType() {
    return type;
  }

  public StringFilter type() {
    if (type == null) {
      type = new StringFilter();
    }
    return type;
  }

  public void setType(StringFilter type) {
    this.type = type;
  }

  public StringFilter getTradeName() {
    return tradeName;
  }

  public StringFilter tradeName() {
    if (tradeName == null) {
      tradeName = new StringFilter();
    }
    return tradeName;
  }

  public void setTradeName(StringFilter tradeName) {
    this.tradeName = tradeName;
  }

  public StringFilter getVehicleIdNumber() {
    return vehicleIdNumber;
  }

  public StringFilter vehicleIdNumber() {
    if (vehicleIdNumber == null) {
      vehicleIdNumber = new StringFilter();
    }
    return vehicleIdNumber;
  }

  public void setVehicleIdNumber(StringFilter vehicleIdNumber) {
    this.vehicleIdNumber = vehicleIdNumber;
  }

  public IntegerFilter getTechnicallyPermissibleMaximumMassInKg() {
    return technicallyPermissibleMaximumMassInKg;
  }

  public IntegerFilter technicallyPermissibleMaximumMassInKg() {
    if (technicallyPermissibleMaximumMassInKg == null) {
      technicallyPermissibleMaximumMassInKg = new IntegerFilter();
    }
    return technicallyPermissibleMaximumMassInKg;
  }

  public void setTechnicallyPermissibleMaximumMassInKg(IntegerFilter technicallyPermissibleMaximumMassInKg) {
    this.technicallyPermissibleMaximumMassInKg = technicallyPermissibleMaximumMassInKg;
  }

  public IntegerFilter getMaximumPermissibleMassKgRegistrationState() {
    return maximumPermissibleMassKgRegistrationState;
  }

  public IntegerFilter maximumPermissibleMassKgRegistrationState() {
    if (maximumPermissibleMassKgRegistrationState == null) {
      maximumPermissibleMassKgRegistrationState = new IntegerFilter();
    }
    return maximumPermissibleMassKgRegistrationState;
  }

  public void setMaximumPermissibleMassKgRegistrationState(IntegerFilter maximumPermissibleMassKgRegistrationState) {
    this.maximumPermissibleMassKgRegistrationState = maximumPermissibleMassKgRegistrationState;
  }

  public IntegerFilter getEmptyMass() {
    return emptyMass;
  }

  public IntegerFilter emptyMass() {
    if (emptyMass == null) {
      emptyMass = new IntegerFilter();
    }
    return emptyMass;
  }

  public void setEmptyMass(IntegerFilter emptyMass) {
    this.emptyMass = emptyMass;
  }

  public StringFilter getPeriodValidity() {
    return periodValidity;
  }

  public StringFilter periodValidity() {
    if (periodValidity == null) {
      periodValidity = new StringFilter();
    }
    return periodValidity;
  }

  public void setPeriodValidity(StringFilter periodValidity) {
    this.periodValidity = periodValidity;
  }

  public LocalDateFilter getApprovalDate() {
    return approvalDate;
  }

  public LocalDateFilter approvalDate() {
    if (approvalDate == null) {
      approvalDate = new LocalDateFilter();
    }
    return approvalDate;
  }

  public void setApprovalDate(LocalDateFilter approvalDate) {
    this.approvalDate = approvalDate;
  }

  public StringFilter getVehicleClass() {
    return vehicleClass;
  }

  public StringFilter vehicleClass() {
    if (vehicleClass == null) {
      vehicleClass = new StringFilter();
    }
    return vehicleClass;
  }

  public void setVehicleClass(StringFilter vehicleClass) {
    this.vehicleClass = vehicleClass;
  }

  public StringFilter getEcTypeApprovalNumberABE() {
    return ecTypeApprovalNumberABE;
  }

  public StringFilter ecTypeApprovalNumberABE() {
    if (ecTypeApprovalNumberABE == null) {
      ecTypeApprovalNumberABE = new StringFilter();
    }
    return ecTypeApprovalNumberABE;
  }

  public void setEcTypeApprovalNumberABE(StringFilter ecTypeApprovalNumberABE) {
    this.ecTypeApprovalNumberABE = ecTypeApprovalNumberABE;
  }

  public IntegerFilter getNoAxes() {
    return noAxes;
  }

  public IntegerFilter noAxes() {
    if (noAxes == null) {
      noAxes = new IntegerFilter();
    }
    return noAxes;
  }

  public void setNoAxes(IntegerFilter noAxes) {
    this.noAxes = noAxes;
  }

  public IntegerFilter getTechnicallyPermissibleTrailerLoadBrakedKg() {
    return technicallyPermissibleTrailerLoadBrakedKg;
  }

  public IntegerFilter technicallyPermissibleTrailerLoadBrakedKg() {
    if (technicallyPermissibleTrailerLoadBrakedKg == null) {
      technicallyPermissibleTrailerLoadBrakedKg = new IntegerFilter();
    }
    return technicallyPermissibleTrailerLoadBrakedKg;
  }

  public void setTechnicallyPermissibleTrailerLoadBrakedKg(IntegerFilter technicallyPermissibleTrailerLoadBrakedKg) {
    this.technicallyPermissibleTrailerLoadBrakedKg = technicallyPermissibleTrailerLoadBrakedKg;
  }

  public IntegerFilter getTechnicallyPermissibleTrailerLoadUnbrakedKg() {
    return technicallyPermissibleTrailerLoadUnbrakedKg;
  }

  public IntegerFilter technicallyPermissibleTrailerLoadUnbrakedKg() {
    if (technicallyPermissibleTrailerLoadUnbrakedKg == null) {
      technicallyPermissibleTrailerLoadUnbrakedKg = new IntegerFilter();
    }
    return technicallyPermissibleTrailerLoadUnbrakedKg;
  }

  public void setTechnicallyPermissibleTrailerLoadUnbrakedKg(
      IntegerFilter technicallyPermissibleTrailerLoadUnbrakedKg) {
    this.technicallyPermissibleTrailerLoadUnbrakedKg = technicallyPermissibleTrailerLoadUnbrakedKg;
  }

  public IntegerFilter getDisplacement() {
    return displacement;
  }

  public IntegerFilter displacement() {
    if (displacement == null) {
      displacement = new IntegerFilter();
    }
    return displacement;
  }

  public void setDisplacement(IntegerFilter displacement) {
    this.displacement = displacement;
  }

  public IntegerFilter getNominalPowerKwKg() {
    return nominalPowerKwKg;
  }

  public IntegerFilter nominalPowerKwKg() {
    if (nominalPowerKwKg == null) {
      nominalPowerKwKg = new IntegerFilter();
    }
    return nominalPowerKwKg;
  }

  public void setNominalPowerKwKg(IntegerFilter nominalPowerKwKg) {
    this.nominalPowerKwKg = nominalPowerKwKg;
  }

  public StringFilter getTypeFuelEnergySource() {
    return typeFuelEnergySource;
  }

  public StringFilter typeFuelEnergySource() {
    if (typeFuelEnergySource == null) {
      typeFuelEnergySource = new StringFilter();
    }
    return typeFuelEnergySource;
  }

  public void setTypeFuelEnergySource(StringFilter typeFuelEnergySource) {
    this.typeFuelEnergySource = typeFuelEnergySource;
  }

  public IntegerFilter getNominalNumberMin() {
    return nominalNumberMin;
  }

  public IntegerFilter nominalNumberMin() {
    if (nominalNumberMin == null) {
      nominalNumberMin = new IntegerFilter();
    }
    return nominalNumberMin;
  }

  public void setNominalNumberMin(IntegerFilter nominalNumberMin) {
    this.nominalNumberMin = nominalNumberMin;
  }

  public IntegerFilter getPowerWeightRatioKwKg() {
    return powerWeightRatioKwKg;
  }

  public IntegerFilter powerWeightRatioKwKg() {
    if (powerWeightRatioKwKg == null) {
      powerWeightRatioKwKg = new IntegerFilter();
    }
    return powerWeightRatioKwKg;
  }

  public void setPowerWeightRatioKwKg(IntegerFilter powerWeightRatioKwKg) {
    this.powerWeightRatioKwKg = powerWeightRatioKwKg;
  }

  public StringFilter getVehicleColour() {
    return vehicleColour;
  }

  public StringFilter vehicleColour() {
    if (vehicleColour == null) {
      vehicleColour = new StringFilter();
    }
    return vehicleColour;
  }

  public void setVehicleColour(StringFilter vehicleColour) {
    this.vehicleColour = vehicleColour;
  }

  public IntegerFilter getNoSeats() {
    return noSeats;
  }

  public IntegerFilter noSeats() {
    if (noSeats == null) {
      noSeats = new IntegerFilter();
    }
    return noSeats;
  }

  public void setNoSeats(IntegerFilter noSeats) {
    this.noSeats = noSeats;
  }

  public IntegerFilter getNoStandingPlaces() {
    return noStandingPlaces;
  }

  public IntegerFilter noStandingPlaces() {
    if (noStandingPlaces == null) {
      noStandingPlaces = new IntegerFilter();
    }
    return noStandingPlaces;
  }

  public void setNoStandingPlaces(IntegerFilter noStandingPlaces) {
    this.noStandingPlaces = noStandingPlaces;
  }

  public IntegerFilter getMaximumSpeedKmh() {
    return maximumSpeedKmh;
  }

  public IntegerFilter maximumSpeedKmh() {
    if (maximumSpeedKmh == null) {
      maximumSpeedKmh = new IntegerFilter();
    }
    return maximumSpeedKmh;
  }

  public void setMaximumSpeedKmh(IntegerFilter maximumSpeedKmh) {
    this.maximumSpeedKmh = maximumSpeedKmh;
  }

  public IntegerFilter getStationaryNoiseDb() {
    return stationaryNoiseDb;
  }

  public IntegerFilter stationaryNoiseDb() {
    if (stationaryNoiseDb == null) {
      stationaryNoiseDb = new IntegerFilter();
    }
    return stationaryNoiseDb;
  }

  public void setStationaryNoiseDb(IntegerFilter stationaryNoiseDb) {
    this.stationaryNoiseDb = stationaryNoiseDb;
  }

  public IntegerFilter getNoRevolutions() {
    return noRevolutions;
  }

  public IntegerFilter noRevolutions() {
    if (noRevolutions == null) {
      noRevolutions = new IntegerFilter();
    }
    return noRevolutions;
  }

  public void setNoRevolutions(IntegerFilter noRevolutions) {
    this.noRevolutions = noRevolutions;
  }

  public IntegerFilter getDrivingNoiseDb() {
    return drivingNoiseDb;
  }

  public IntegerFilter drivingNoiseDb() {
    if (drivingNoiseDb == null) {
      drivingNoiseDb = new IntegerFilter();
    }
    return drivingNoiseDb;
  }

  public void setDrivingNoiseDb(IntegerFilter drivingNoiseDb) {
    this.drivingNoiseDb = drivingNoiseDb;
  }

  public IntegerFilter getCo2GKm() {
    return co2GKm;
  }

  public IntegerFilter co2GKm() {
    if (co2GKm == null) {
      co2GKm = new IntegerFilter();
    }
    return co2GKm;
  }

  public void setCo2GKm(IntegerFilter co2GKm) {
    this.co2GKm = co2GKm;
  }

  public StringFilter getPollutantClassRelevantEg() {
    return pollutantClassRelevantEg;
  }

  public StringFilter pollutantClassRelevantEg() {
    if (pollutantClassRelevantEg == null) {
      pollutantClassRelevantEg = new StringFilter();
    }
    return pollutantClassRelevantEg;
  }

  public void setPollutantClassRelevantEg(StringFilter pollutantClassRelevantEg) {
    this.pollutantClassRelevantEg = pollutantClassRelevantEg;
  }

  public StringFilter getManufacturerAbbreviation() {
    return manufacturerAbbreviation;
  }

  public StringFilter manufacturerAbbreviation() {
    if (manufacturerAbbreviation == null) {
      manufacturerAbbreviation = new StringFilter();
    }
    return manufacturerAbbreviation;
  }

  public void setManufacturerAbbreviation(StringFilter manufacturerAbbreviation) {
    this.manufacturerAbbreviation = manufacturerAbbreviation;
  }

  public StringFilter getManufacturingNumbercodeTo2() {
    return manufacturingNumbercodeTo2;
  }

  public StringFilter manufacturingNumbercodeTo2() {
    if (manufacturingNumbercodeTo2 == null) {
      manufacturingNumbercodeTo2 = new StringFilter();
    }
    return manufacturingNumbercodeTo2;
  }

  public void setManufacturingNumbercodeTo2(StringFilter manufacturingNumbercodeTo2) {
    this.manufacturingNumbercodeTo2 = manufacturingNumbercodeTo2;
  }

  public StringFilter getTypeKeyNumberD2() {
    return typeKeyNumberD2;
  }

  public StringFilter typeKeyNumberD2() {
    if (typeKeyNumberD2 == null) {
      typeKeyNumberD2 = new StringFilter();
    }
    return typeKeyNumberD2;
  }

  public void setTypeKeyNumberD2(StringFilter typeKeyNumberD2) {
    this.typeKeyNumberD2 = typeKeyNumberD2;
  }

  public StringFilter getCheckDigitVehicleIdNumber() {
    return checkDigitVehicleIdNumber;
  }

  public StringFilter checkDigitVehicleIdNumber() {
    if (checkDigitVehicleIdNumber == null) {
      checkDigitVehicleIdNumber = new StringFilter();
    }
    return checkDigitVehicleIdNumber;
  }

  public void setCheckDigitVehicleIdNumber(StringFilter checkDigitVehicleIdNumber) {
    this.checkDigitVehicleIdNumber = checkDigitVehicleIdNumber;
  }

  public StringFilter getTypeOfConstruction() {
    return typeOfConstruction;
  }

  public StringFilter typeOfConstruction() {
    if (typeOfConstruction == null) {
      typeOfConstruction = new StringFilter();
    }
    return typeOfConstruction;
  }

  public void setTypeOfConstruction(StringFilter typeOfConstruction) {
    this.typeOfConstruction = typeOfConstruction;
  }

  public StringFilter getDesignationVehicleClassBodywork() {
    return designationVehicleClassBodywork;
  }

  public StringFilter designationVehicleClassBodywork() {
    if (designationVehicleClassBodywork == null) {
      designationVehicleClassBodywork = new StringFilter();
    }
    return designationVehicleClassBodywork;
  }

  public void setDesignationVehicleClassBodywork(StringFilter designationVehicleClassBodywork) {
    this.designationVehicleClassBodywork = designationVehicleClassBodywork;
  }

  public LocalDateFilter getDateToK() {
    return dateToK;
  }

  public LocalDateFilter dateToK() {
    if (dateToK == null) {
      dateToK = new LocalDateFilter();
    }
    return dateToK;
  }

  public void setDateToK(LocalDateFilter dateToK) {
    this.dateToK = dateToK;
  }

  public IntegerFilter getTechnicallyPermissibleMaxAxleLoad() {
    return technicallyPermissibleMaxAxleLoad;
  }

  public IntegerFilter technicallyPermissibleMaxAxleLoad() {
    if (technicallyPermissibleMaxAxleLoad == null) {
      technicallyPermissibleMaxAxleLoad = new IntegerFilter();
    }
    return technicallyPermissibleMaxAxleLoad;
  }

  public void setTechnicallyPermissibleMaxAxleLoad(IntegerFilter technicallyPermissibleMaxAxleLoad) {
    this.technicallyPermissibleMaxAxleLoad = technicallyPermissibleMaxAxleLoad;
  }

  public IntegerFilter getAxis1Tech() {
    return axis1Tech;
  }

  public IntegerFilter axis1Tech() {
    if (axis1Tech == null) {
      axis1Tech = new IntegerFilter();
    }
    return axis1Tech;
  }

  public void setAxis1Tech(IntegerFilter axis1Tech) {
    this.axis1Tech = axis1Tech;
  }

  public IntegerFilter getAxis2Tech() {
    return axis2Tech;
  }

  public IntegerFilter axis2Tech() {
    if (axis2Tech == null) {
      axis2Tech = new IntegerFilter();
    }
    return axis2Tech;
  }

  public void setAxis2Tech(IntegerFilter axis2Tech) {
    this.axis2Tech = axis2Tech;
  }

  public IntegerFilter getAxis3Tech() {
    return axis3Tech;
  }

  public IntegerFilter axis3Tech() {
    if (axis3Tech == null) {
      axis3Tech = new IntegerFilter();
    }
    return axis3Tech;
  }

  public void setAxis3Tech(IntegerFilter axis3Tech) {
    this.axis3Tech = axis3Tech;
  }

  public IntegerFilter getMaximumPermissibleAxleLoadMemberStateKg() {
    return maximumPermissibleAxleLoadMemberStateKg;
  }

  public IntegerFilter maximumPermissibleAxleLoadMemberStateKg() {
    if (maximumPermissibleAxleLoadMemberStateKg == null) {
      maximumPermissibleAxleLoadMemberStateKg = new IntegerFilter();
    }
    return maximumPermissibleAxleLoadMemberStateKg;
  }

  public void setMaximumPermissibleAxleLoadMemberStateKg(IntegerFilter maximumPermissibleAxleLoadMemberStateKg) {
    this.maximumPermissibleAxleLoadMemberStateKg = maximumPermissibleAxleLoadMemberStateKg;
  }

  public IntegerFilter getAxis1Ms() {
    return axis1Ms;
  }

  public IntegerFilter axis1Ms() {
    if (axis1Ms == null) {
      axis1Ms = new IntegerFilter();
    }
    return axis1Ms;
  }

  public void setAxis1Ms(IntegerFilter axis1Ms) {
    this.axis1Ms = axis1Ms;
  }

  public IntegerFilter getAxis2Ms() {
    return axis2Ms;
  }

  public IntegerFilter axis2Ms() {
    if (axis2Ms == null) {
      axis2Ms = new IntegerFilter();
    }
    return axis2Ms;
  }

  public void setAxis2Ms(IntegerFilter axis2Ms) {
    this.axis2Ms = axis2Ms;
  }

  public IntegerFilter getAxis3Ms() {
    return axis3Ms;
  }

  public IntegerFilter axis3Ms() {
    if (axis3Ms == null) {
      axis3Ms = new IntegerFilter();
    }
    return axis3Ms;
  }

  public void setAxis3Ms(IntegerFilter axis3Ms) {
    this.axis3Ms = axis3Ms;
  }

  public IntegerFilter getNumberDriveAxles() {
    return numberDriveAxles;
  }

  public IntegerFilter numberDriveAxles() {
    if (numberDriveAxles == null) {
      numberDriveAxles = new IntegerFilter();
    }
    return numberDriveAxles;
  }

  public void setNumberDriveAxles(IntegerFilter numberDriveAxles) {
    this.numberDriveAxles = numberDriveAxles;
  }

  public StringFilter getCodeToP3() {
    return codeToP3;
  }

  public StringFilter codeToP3() {
    if (codeToP3 == null) {
      codeToP3 = new StringFilter();
    }
    return codeToP3;
  }

  public void setCodeToP3(StringFilter codeToP3) {
    this.codeToP3 = codeToP3;
  }

  public StringFilter getCodeToR() {
    return codeToR;
  }

  public StringFilter codeToR() {
    if (codeToR == null) {
      codeToR = new StringFilter();
    }
    return codeToR;
  }

  public void setCodeToR(StringFilter codeToR) {
    this.codeToR = codeToR;
  }

  public IntegerFilter getVolumeTankTankersCcm() {
    return volumeTankTankersCcm;
  }

  public IntegerFilter volumeTankTankersCcm() {
    if (volumeTankTankersCcm == null) {
      volumeTankTankersCcm = new IntegerFilter();
    }
    return volumeTankTankersCcm;
  }

  public void setVolumeTankTankersCcm(IntegerFilter volumeTankTankersCcm) {
    this.volumeTankTankersCcm = volumeTankTankersCcm;
  }

  public IntegerFilter getVerticalLoadKg() {
    return verticalLoadKg;
  }

  public IntegerFilter verticalLoadKg() {
    if (verticalLoadKg == null) {
      verticalLoadKg = new IntegerFilter();
    }
    return verticalLoadKg;
  }

  public void setVerticalLoadKg(IntegerFilter verticalLoadKg) {
    this.verticalLoadKg = verticalLoadKg;
  }

  public StringFilter getDesignationNationalEmissionClass() {
    return designationNationalEmissionClass;
  }

  public StringFilter designationNationalEmissionClass() {
    if (designationNationalEmissionClass == null) {
      designationNationalEmissionClass = new StringFilter();
    }
    return designationNationalEmissionClass;
  }

  public void setDesignationNationalEmissionClass(StringFilter designationNationalEmissionClass) {
    this.designationNationalEmissionClass = designationNationalEmissionClass;
  }

  public StringFilter getTyres() {
    return tyres;
  }

  public StringFilter tyres() {
    if (tyres == null) {
      tyres = new StringFilter();
    }
    return tyres;
  }

  public void setTyres(StringFilter tyres) {
    this.tyres = tyres;
  }

  public IntegerFilter getOnAxis1() {
    return onAxis1;
  }

  public IntegerFilter onAxis1() {
    if (onAxis1 == null) {
      onAxis1 = new IntegerFilter();
    }
    return onAxis1;
  }

  public void setOnAxis1(IntegerFilter onAxis1) {
    this.onAxis1 = onAxis1;
  }

  public IntegerFilter getOnAxis2() {
    return onAxis2;
  }

  public IntegerFilter onAxis2() {
    if (onAxis2 == null) {
      onAxis2 = new IntegerFilter();
    }
    return onAxis2;
  }

  public void setOnAxis2(IntegerFilter onAxis2) {
    this.onAxis2 = onAxis2;
  }

  public IntegerFilter getOnAxis3() {
    return onAxis3;
  }

  public IntegerFilter onAxis3() {
    if (onAxis3 == null) {
      onAxis3 = new IntegerFilter();
    }
    return onAxis3;
  }

  public void setOnAxis3(IntegerFilter onAxis3) {
    this.onAxis3 = onAxis3;
  }

  public StringFilter getRegistrationCertificateNumberPartII() {
    return registrationCertificateNumberPartII;
  }

  public StringFilter registrationCertificateNumberPartII() {
    if (registrationCertificateNumberPartII == null) {
      registrationCertificateNumberPartII = new StringFilter();
    }
    return registrationCertificateNumberPartII;
  }

  public void setRegistrationCertificateNumberPartII(StringFilter registrationCertificateNumberPartII) {
    this.registrationCertificateNumberPartII = registrationCertificateNumberPartII;
  }

  public StringFilter getFeaturesOfTheTypeApproval() {
    return featuresOfTheTypeApproval;
  }

  public StringFilter featuresOfTheTypeApproval() {
    if (featuresOfTheTypeApproval == null) {
      featuresOfTheTypeApproval = new StringFilter();
    }
    return featuresOfTheTypeApproval;
  }

  public void setFeaturesOfTheTypeApproval(StringFilter featuresOfTheTypeApproval) {
    this.featuresOfTheTypeApproval = featuresOfTheTypeApproval;
  }

  public IntegerFilter getLengthMm() {
    return lengthMm;
  }

  public IntegerFilter lengthMm() {
    if (lengthMm == null) {
      lengthMm = new IntegerFilter();
    }
    return lengthMm;
  }

  public void setLengthMm(IntegerFilter lengthMm) {
    this.lengthMm = lengthMm;
  }

  public IntegerFilter getWidthMm() {
    return widthMm;
  }

  public IntegerFilter widthMm() {
    if (widthMm == null) {
      widthMm = new IntegerFilter();
    }
    return widthMm;
  }

  public void setWidthMm(IntegerFilter widthMm) {
    this.widthMm = widthMm;
  }

  public IntegerFilter getHeightMm() {
    return heightMm;
  }

  public IntegerFilter heightMm() {
    if (heightMm == null) {
      heightMm = new IntegerFilter();
    }
    return heightMm;
  }

  public void setHeightMm(IntegerFilter heightMm) {
    this.heightMm = heightMm;
  }

  public StringFilter getOtherEndorsements() {
    return otherEndorsements;
  }

  public StringFilter otherEndorsements() {
    if (otherEndorsements == null) {
      otherEndorsements = new StringFilter();
    }
    return otherEndorsements;
  }

  public void setOtherEndorsements(StringFilter otherEndorsements) {
    this.otherEndorsements = otherEndorsements;
  }

  public StringFilter getRemarksAndExceptions() {
    return remarksAndExceptions;
  }

  public StringFilter remarksAndExceptions() {
    if (remarksAndExceptions == null) {
      remarksAndExceptions = new StringFilter();
    }
    return remarksAndExceptions;
  }

  public void setRemarksAndExceptions(StringFilter remarksAndExceptions) {
    this.remarksAndExceptions = remarksAndExceptions;
  }

  public IntegerFilter getNumberDoors() {
    return numberDoors;
  }

  public IntegerFilter numberDoors() {
    if (numberDoors == null) {
      numberDoors = new IntegerFilter();
    }
    return numberDoors;
  }

  public void setNumberDoors(IntegerFilter numberDoors) {
    this.numberDoors = numberDoors;
  }

  public IntegerFilter getHorsepower() {
    return horsepower;
  }

  public IntegerFilter horsepower() {
    if (horsepower == null) {
      horsepower = new IntegerFilter();
    }
    return horsepower;
  }

  public void setHorsepower(IntegerFilter horsepower) {
    this.horsepower = horsepower;
  }

  public IntegerFilter getDraft() {
    return draft;
  }

  public IntegerFilter draft() {
    if (draft == null) {
      draft = new IntegerFilter();
    }
    return draft;
  }

  public void setDraft(IntegerFilter draft) {
    this.draft = draft;
  }

  public LocalDateFilter getMileageDate() {
    return mileageDate;
  }

  public LocalDateFilter mileageDate() {
    if (mileageDate == null) {
      mileageDate = new LocalDateFilter();
    }
    return mileageDate;
  }

  public void setMileageDate(LocalDateFilter mileageDate) {
    this.mileageDate = mileageDate;
  }

  public IntegerFilter getMileage() {
    return mileage;
  }

  public IntegerFilter mileage() {
    if (mileage == null) {
      mileage = new IntegerFilter();
    }
    return mileage;
  }

  public void setMileage(IntegerFilter mileage) {
    this.mileage = mileage;
  }

  public LocalDateFilter getLastGeneralInspection() {
    return lastGeneralInspection;
  }

  public LocalDateFilter lastGeneralInspection() {
    if (lastGeneralInspection == null) {
      lastGeneralInspection = new LocalDateFilter();
    }
    return lastGeneralInspection;
  }

  public void setLastGeneralInspection(LocalDateFilter lastGeneralInspection) {
    this.lastGeneralInspection = lastGeneralInspection;
  }

  public LocalDateFilter getFinalEmissionsTest() {
    return finalEmissionsTest;
  }

  public LocalDateFilter finalEmissionsTest() {
    if (finalEmissionsTest == null) {
      finalEmissionsTest = new LocalDateFilter();
    }
    return finalEmissionsTest;
  }

  public void setFinalEmissionsTest(LocalDateFilter finalEmissionsTest) {
    this.finalEmissionsTest = finalEmissionsTest;
  }

  public LocalDateFilter getNextEmissionsTest() {
    return nextEmissionsTest;
  }

  public LocalDateFilter nextEmissionsTest() {
    if (nextEmissionsTest == null) {
      nextEmissionsTest = new LocalDateFilter();
    }
    return nextEmissionsTest;
  }

  public void setNextEmissionsTest(LocalDateFilter nextEmissionsTest) {
    this.nextEmissionsTest = nextEmissionsTest;
  }

  public LocalDateFilter getNextGeneralInspection() {
    return nextGeneralInspection;
  }

  public LocalDateFilter nextGeneralInspection() {
    if (nextGeneralInspection == null) {
      nextGeneralInspection = new LocalDateFilter();
    }
    return nextGeneralInspection;
  }

  public void setNextGeneralInspection(LocalDateFilter nextGeneralInspection) {
    this.nextGeneralInspection = nextGeneralInspection;
  }

  public StringFilter getFrameType() {
    return frameType;
  }

  public StringFilter frameType() {
    if (frameType == null) {
      frameType = new StringFilter();
    }
    return frameType;
  }

  public void setFrameType(StringFilter frameType) {
    this.frameType = frameType;
  }

  public StringFilter getFrameMaterial() {
    return frameMaterial;
  }

  public StringFilter frameMaterial() {
    if (frameMaterial == null) {
      frameMaterial = new StringFilter();
    }
    return frameMaterial;
  }

  public void setFrameMaterial(StringFilter frameMaterial) {
    this.frameMaterial = frameMaterial;
  }

  public StringFilter getCircuit() {
    return circuit;
  }

  public StringFilter circuit() {
    if (circuit == null) {
      circuit = new StringFilter();
    }
    return circuit;
  }

  public void setCircuit(StringFilter circuit) {
    this.circuit = circuit;
  }

  public IntegerFilter getWheelSize() {
    return wheelSize;
  }

  public IntegerFilter wheelSize() {
    if (wheelSize == null) {
      wheelSize = new IntegerFilter();
    }
    return wheelSize;
  }

  public void setWheelSize(IntegerFilter wheelSize) {
    this.wheelSize = wheelSize;
  }

  public IntegerFilter getFrameHeight() {
    return frameHeight;
  }

  public IntegerFilter frameHeight() {
    if (frameHeight == null) {
      frameHeight = new IntegerFilter();
    }
    return frameHeight;
  }

  public void setFrameHeight(IntegerFilter frameHeight) {
    this.frameHeight = frameHeight;
  }

  public StringFilter getColor() {
    return color;
  }

  public StringFilter color() {
    if (color == null) {
      color = new StringFilter();
    }
    return color;
  }

  public void setColor(StringFilter color) {
    this.color = color;
  }

  public StringFilter getFrameNo() {
    return frameNo;
  }

  public StringFilter frameNo() {
    if (frameNo == null) {
      frameNo = new StringFilter();
    }
    return frameNo;
  }

  public void setFrameNo(StringFilter frameNo) {
    this.frameNo = frameNo;
  }

  public StringFilter getManufacturer() {
    return manufacturer;
  }

  public StringFilter manufacturer() {
    if (manufacturer == null) {
      manufacturer = new StringFilter();
    }
    return manufacturer;
  }

  public void setManufacturer(StringFilter manufacturer) {
    this.manufacturer = manufacturer;
  }

  public StringFilter getOriginalYardNumberIIYardNumber() {
    return originalYardNumberIIYardNumber;
  }

  public StringFilter originalYardNumberIIYardNumber() {
    if (originalYardNumberIIYardNumber == null) {
      originalYardNumberIIYardNumber = new StringFilter();
    }
    return originalYardNumberIIYardNumber;
  }

  public void setOriginalYardNumberIIYardNumber(StringFilter originalYardNumberIIYardNumber) {
    this.originalYardNumberIIYardNumber = originalYardNumberIIYardNumber;
  }

  public StringFilter getMainBuildingMaterial() {
    return mainBuildingMaterial;
  }

  public StringFilter mainBuildingMaterial() {
    if (mainBuildingMaterial == null) {
      mainBuildingMaterial = new StringFilter();
    }
    return mainBuildingMaterial;
  }

  public void setMainBuildingMaterial(StringFilter mainBuildingMaterial) {
    this.mainBuildingMaterial = mainBuildingMaterial;
  }

  public StringFilter getPreviousLicensePlate() {
    return previousLicensePlate;
  }

  public StringFilter previousLicensePlate() {
    if (previousLicensePlate == null) {
      previousLicensePlate = new StringFilter();
    }
    return previousLicensePlate;
  }

  public void setPreviousLicensePlate(StringFilter previousLicensePlate) {
    this.previousLicensePlate = previousLicensePlate;
  }

  public StringFilter getEngineNumber() {
    return engineNumber;
  }

  public StringFilter engineNumber() {
    if (engineNumber == null) {
      engineNumber = new StringFilter();
    }
    return engineNumber;
  }

  public void setEngineNumber(StringFilter engineNumber) {
    this.engineNumber = engineNumber;
  }

  public StringFilter getEngineManufacturers() {
    return engineManufacturers;
  }

  public StringFilter engineManufacturers() {
    if (engineManufacturers == null) {
      engineManufacturers = new StringFilter();
    }
    return engineManufacturers;
  }

  public void setEngineManufacturers(StringFilter engineManufacturers) {
    this.engineManufacturers = engineManufacturers;
  }

  public StringFilter getEngineMakeType() {
    return engineMakeType;
  }

  public StringFilter engineMakeType() {
    if (engineMakeType == null) {
      engineMakeType = new StringFilter();
    }
    return engineMakeType;
  }

  public void setEngineMakeType(StringFilter engineMakeType) {
    this.engineMakeType = engineMakeType;
  }

  public StringFilter getDriveType() {
    return driveType;
  }

  public StringFilter driveType() {
    if (driveType == null) {
      driveType = new StringFilter();
    }
    return driveType;
  }

  public void setDriveType(StringFilter driveType) {
    this.driveType = driveType;
  }

  public StringFilter getzDriveNumber() {
    return zDriveNumber;
  }

  public StringFilter zDriveNumber() {
    if (zDriveNumber == null) {
      zDriveNumber = new StringFilter();
    }
    return zDriveNumber;
  }

  public void setzDriveNumber(StringFilter zDriveNumber) {
    this.zDriveNumber = zDriveNumber;
  }

  public LocalDateFilter getIdCardIssuedDate() {
    return idCardIssuedDate;
  }

  public LocalDateFilter idCardIssuedDate() {
    if (idCardIssuedDate == null) {
      idCardIssuedDate = new LocalDateFilter();
    }
    return idCardIssuedDate;
  }

  public void setIdCardIssuedDate(LocalDateFilter idCardIssuedDate) {
    this.idCardIssuedDate = idCardIssuedDate;
  }

  public StringFilter getCarLicensePlates() {
    return carLicensePlates;
  }

  public StringFilter carLicensePlates() {
    if (carLicensePlates == null) {
      carLicensePlates = new StringFilter();
    }
    return carLicensePlates;
  }

  public void setCarLicensePlates(StringFilter carLicensePlates) {
    this.carLicensePlates = carLicensePlates;
  }

  public Boolean getDistinct() {
    return distinct;
  }

  public void setDistinct(Boolean distinct) {
    this.distinct = distinct;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final MobilityCriteria that = (MobilityCriteria) o;
    return (Objects.equals(id, that.id) &&
        Objects.equals(status, that.status) &&
        Objects.equals(owner, that.owner) &&
        Objects.equals(user, that.user) &&
        Objects.equals(dateFirstRegistration, that.dateFirstRegistration) &&
        Objects.equals(brand, that.brand) &&
        Objects.equals(type, that.type) &&
        Objects.equals(tradeName, that.tradeName) &&
        Objects.equals(vehicleIdNumber, that.vehicleIdNumber) &&
        Objects.equals(technicallyPermissibleMaximumMassInKg, that.technicallyPermissibleMaximumMassInKg) &&
        Objects.equals(maximumPermissibleMassKgRegistrationState, that.maximumPermissibleMassKgRegistrationState) &&
        Objects.equals(emptyMass, that.emptyMass) &&
        Objects.equals(periodValidity, that.periodValidity) &&
        Objects.equals(approvalDate, that.approvalDate) &&
        Objects.equals(vehicleClass, that.vehicleClass) &&
        Objects.equals(ecTypeApprovalNumberABE, that.ecTypeApprovalNumberABE) &&
        Objects.equals(noAxes, that.noAxes) &&
        Objects.equals(technicallyPermissibleTrailerLoadBrakedKg, that.technicallyPermissibleTrailerLoadBrakedKg) &&
        Objects.equals(technicallyPermissibleTrailerLoadUnbrakedKg, that.technicallyPermissibleTrailerLoadUnbrakedKg) &&
        Objects.equals(displacement, that.displacement) &&
        Objects.equals(nominalPowerKwKg, that.nominalPowerKwKg) &&
        Objects.equals(typeFuelEnergySource, that.typeFuelEnergySource) &&
        Objects.equals(nominalNumberMin, that.nominalNumberMin) &&
        Objects.equals(powerWeightRatioKwKg, that.powerWeightRatioKwKg) &&
        Objects.equals(vehicleColour, that.vehicleColour) &&
        Objects.equals(noSeats, that.noSeats) &&
        Objects.equals(noStandingPlaces, that.noStandingPlaces) &&
        Objects.equals(maximumSpeedKmh, that.maximumSpeedKmh) &&
        Objects.equals(stationaryNoiseDb, that.stationaryNoiseDb) &&
        Objects.equals(noRevolutions, that.noRevolutions) &&
        Objects.equals(drivingNoiseDb, that.drivingNoiseDb) &&
        Objects.equals(co2GKm, that.co2GKm) &&
        Objects.equals(pollutantClassRelevantEg, that.pollutantClassRelevantEg) &&
        Objects.equals(manufacturerAbbreviation, that.manufacturerAbbreviation) &&
        Objects.equals(manufacturingNumbercodeTo2, that.manufacturingNumbercodeTo2) &&
        Objects.equals(typeKeyNumberD2, that.typeKeyNumberD2) &&
        Objects.equals(checkDigitVehicleIdNumber, that.checkDigitVehicleIdNumber) &&
        Objects.equals(typeOfConstruction, that.typeOfConstruction) &&
        Objects.equals(designationVehicleClassBodywork, that.designationVehicleClassBodywork) &&
        Objects.equals(dateToK, that.dateToK) &&
        Objects.equals(technicallyPermissibleMaxAxleLoad, that.technicallyPermissibleMaxAxleLoad) &&
        Objects.equals(axis1Tech, that.axis1Tech) &&
        Objects.equals(axis2Tech, that.axis2Tech) &&
        Objects.equals(axis3Tech, that.axis3Tech) &&
        Objects.equals(maximumPermissibleAxleLoadMemberStateKg, that.maximumPermissibleAxleLoadMemberStateKg) &&
        Objects.equals(axis1Ms, that.axis1Ms) &&
        Objects.equals(axis2Ms, that.axis2Ms) &&
        Objects.equals(axis3Ms, that.axis3Ms) &&
        Objects.equals(numberDriveAxles, that.numberDriveAxles) &&
        Objects.equals(codeToP3, that.codeToP3) &&
        Objects.equals(codeToR, that.codeToR) &&
        Objects.equals(volumeTankTankersCcm, that.volumeTankTankersCcm) &&
        Objects.equals(verticalLoadKg, that.verticalLoadKg) &&
        Objects.equals(designationNationalEmissionClass, that.designationNationalEmissionClass) &&
        Objects.equals(tyres, that.tyres) &&
        Objects.equals(onAxis1, that.onAxis1) &&
        Objects.equals(onAxis2, that.onAxis2) &&
        Objects.equals(onAxis3, that.onAxis3) &&
        Objects.equals(registrationCertificateNumberPartII, that.registrationCertificateNumberPartII) &&
        Objects.equals(featuresOfTheTypeApproval, that.featuresOfTheTypeApproval) &&
        Objects.equals(lengthMm, that.lengthMm) &&
        Objects.equals(widthMm, that.widthMm) &&
        Objects.equals(heightMm, that.heightMm) &&
        Objects.equals(otherEndorsements, that.otherEndorsements) &&
        Objects.equals(remarksAndExceptions, that.remarksAndExceptions) &&
        Objects.equals(numberDoors, that.numberDoors) &&
        Objects.equals(horsepower, that.horsepower) &&
        Objects.equals(draft, that.draft) &&
        Objects.equals(mileageDate, that.mileageDate) &&
        Objects.equals(mileage, that.mileage) &&
        Objects.equals(lastGeneralInspection, that.lastGeneralInspection) &&
        Objects.equals(finalEmissionsTest, that.finalEmissionsTest) &&
        Objects.equals(nextEmissionsTest, that.nextEmissionsTest) &&
        Objects.equals(nextGeneralInspection, that.nextGeneralInspection) &&
        Objects.equals(frameType, that.frameType) &&
        Objects.equals(frameMaterial, that.frameMaterial) &&
        Objects.equals(circuit, that.circuit) &&
        Objects.equals(wheelSize, that.wheelSize) &&
        Objects.equals(frameHeight, that.frameHeight) &&
        Objects.equals(color, that.color) &&
        Objects.equals(frameNo, that.frameNo) &&
        Objects.equals(manufacturer, that.manufacturer) &&
        Objects.equals(originalYardNumberIIYardNumber, that.originalYardNumberIIYardNumber) &&
        Objects.equals(mainBuildingMaterial, that.mainBuildingMaterial) &&
        Objects.equals(previousLicensePlate, that.previousLicensePlate) &&
        Objects.equals(engineNumber, that.engineNumber) &&
        Objects.equals(engineManufacturers, that.engineManufacturers) &&
        Objects.equals(engineMakeType, that.engineMakeType) &&
        Objects.equals(driveType, that.driveType) &&
        Objects.equals(zDriveNumber, that.zDriveNumber) &&
        Objects.equals(idCardIssuedDate, that.idCardIssuedDate) &&
        Objects.equals(carLicensePlates, that.carLicensePlates) &&
        Objects.equals(distinct, that.distinct));
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        status,
        owner,
        user,
        dateFirstRegistration,
        brand,
        type,
        tradeName,
        vehicleIdNumber,
        technicallyPermissibleMaximumMassInKg,
        maximumPermissibleMassKgRegistrationState,
        emptyMass,
        periodValidity,
        approvalDate,
        vehicleClass,
        ecTypeApprovalNumberABE,
        noAxes,
        technicallyPermissibleTrailerLoadBrakedKg,
        technicallyPermissibleTrailerLoadUnbrakedKg,
        displacement,
        nominalPowerKwKg,
        typeFuelEnergySource,
        nominalNumberMin,
        powerWeightRatioKwKg,
        vehicleColour,
        noSeats,
        noStandingPlaces,
        maximumSpeedKmh,
        stationaryNoiseDb,
        noRevolutions,
        drivingNoiseDb,
        co2GKm,
        pollutantClassRelevantEg,
        manufacturerAbbreviation,
        manufacturingNumbercodeTo2,
        typeKeyNumberD2,
        checkDigitVehicleIdNumber,
        typeOfConstruction,
        designationVehicleClassBodywork,
        dateToK,
        technicallyPermissibleMaxAxleLoad,
        axis1Tech,
        axis2Tech,
        axis3Tech,
        maximumPermissibleAxleLoadMemberStateKg,
        axis1Ms,
        axis2Ms,
        axis3Ms,
        numberDriveAxles,
        codeToP3,
        codeToR,
        volumeTankTankersCcm,
        verticalLoadKg,
        designationNationalEmissionClass,
        tyres,
        onAxis1,
        onAxis2,
        onAxis3,
        registrationCertificateNumberPartII,
        featuresOfTheTypeApproval,
        lengthMm,
        widthMm,
        heightMm,
        otherEndorsements,
        remarksAndExceptions,
        numberDoors,
        horsepower,
        draft,
        mileageDate,
        mileage,
        lastGeneralInspection,
        finalEmissionsTest,
        nextEmissionsTest,
        nextGeneralInspection,
        frameType,
        frameMaterial,
        circuit,
        wheelSize,
        frameHeight,
        color,
        frameNo,
        manufacturer,
        originalYardNumberIIYardNumber,
        mainBuildingMaterial,
        previousLicensePlate,
        engineNumber,
        engineManufacturers,
        engineMakeType,
        driveType,
        zDriveNumber,
        idCardIssuedDate,
        carLicensePlates,
        distinct);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "MobilityCriteria{" +
        (id != null ? "id=" + id + ", " : "") +
        (status != null ? "status=" + status + ", " : "") +
        (owner != null ? "owner=" + owner + ", " : "") +
        (user != null ? "user=" + user + ", " : "") +
        (dateFirstRegistration != null ? "dateFirstRegistration=" + dateFirstRegistration + ", " : "") +
        (brand != null ? "brand=" + brand + ", " : "") +
        (type != null ? "type=" + type + ", " : "") +
        (tradeName != null ? "tradeName=" + tradeName + ", " : "") +
        (vehicleIdNumber != null ? "vehicleIdNumber=" + vehicleIdNumber + ", " : "") +
        (technicallyPermissibleMaximumMassInKg != null
            ? "technicallyPermissibleMaximumMassInKg=" + technicallyPermissibleMaximumMassInKg + ", "
            : "")
        +
        (maximumPermissibleMassKgRegistrationState != null
            ? "maximumPermissibleMassKgRegistrationState=" + maximumPermissibleMassKgRegistrationState + ", "
            : "")
        +
        (emptyMass != null ? "emptyMass=" + emptyMass + ", " : "") +
        (periodValidity != null ? "periodValidity=" + periodValidity + ", " : "") +
        (approvalDate != null ? "approvalDate=" + approvalDate + ", " : "") +
        (vehicleClass != null ? "vehicleClass=" + vehicleClass + ", " : "") +
        (ecTypeApprovalNumberABE != null ? "ecTypeApprovalNumberABE=" + ecTypeApprovalNumberABE + ", " : "") +
        (noAxes != null ? "noAxes=" + noAxes + ", " : "") +
        (technicallyPermissibleTrailerLoadBrakedKg != null
            ? "technicallyPermissibleTrailerLoadBrakedKg=" + technicallyPermissibleTrailerLoadBrakedKg + ", "
            : "")
        +
        (technicallyPermissibleTrailerLoadUnbrakedKg != null
            ? "technicallyPermissibleTrailerLoadUnbrakedKg=" + technicallyPermissibleTrailerLoadUnbrakedKg + ", "
            : "")
        +
        (displacement != null ? "displacement=" + displacement + ", " : "") +
        (nominalPowerKwKg != null ? "nominalPowerKwKg=" + nominalPowerKwKg + ", " : "") +
        (typeFuelEnergySource != null ? "typeFuelEnergySource=" + typeFuelEnergySource + ", " : "") +
        (nominalNumberMin != null ? "nominalNumberMin=" + nominalNumberMin + ", " : "") +
        (powerWeightRatioKwKg != null ? "powerWeightRatioKwKg=" + powerWeightRatioKwKg + ", " : "") +
        (vehicleColour != null ? "vehicleColour=" + vehicleColour + ", " : "") +
        (noSeats != null ? "noSeats=" + noSeats + ", " : "") +
        (noStandingPlaces != null ? "noStandingPlaces=" + noStandingPlaces + ", " : "") +
        (maximumSpeedKmh != null ? "maximumSpeedKmh=" + maximumSpeedKmh + ", " : "") +
        (stationaryNoiseDb != null ? "stationaryNoiseDb=" + stationaryNoiseDb + ", " : "") +
        (noRevolutions != null ? "noRevolutions=" + noRevolutions + ", " : "") +
        (drivingNoiseDb != null ? "drivingNoiseDb=" + drivingNoiseDb + ", " : "") +
        (co2GKm != null ? "co2GKm=" + co2GKm + ", " : "") +
        (pollutantClassRelevantEg != null ? "pollutantClassRelevantEg=" + pollutantClassRelevantEg + ", " : "") +
        (manufacturerAbbreviation != null ? "manufacturerAbbreviation=" + manufacturerAbbreviation + ", " : "") +
        (manufacturingNumbercodeTo2 != null ? "manufacturingNumbercodeTo2=" + manufacturingNumbercodeTo2 + ", " : "") +
        (typeKeyNumberD2 != null ? "typeKeyNumberD2=" + typeKeyNumberD2 + ", " : "") +
        (checkDigitVehicleIdNumber != null ? "checkDigitVehicleIdNumber=" + checkDigitVehicleIdNumber + ", " : "") +
        (typeOfConstruction != null ? "typeOfConstruction=" + typeOfConstruction + ", " : "") +
        (designationVehicleClassBodywork != null
            ? "designationVehicleClassBodywork=" + designationVehicleClassBodywork + ", "
            : "")
        +
        (dateToK != null ? "dateToK=" + dateToK + ", " : "") +
        (technicallyPermissibleMaxAxleLoad != null
            ? "technicallyPermissibleMaxAxleLoad=" + technicallyPermissibleMaxAxleLoad + ", "
            : "")
        +
        (axis1Tech != null ? "axis1Tech=" + axis1Tech + ", " : "") +
        (axis2Tech != null ? "axis2Tech=" + axis2Tech + ", " : "") +
        (axis3Tech != null ? "axis3Tech=" + axis3Tech + ", " : "") +
        (maximumPermissibleAxleLoadMemberStateKg != null
            ? "maximumPermissibleAxleLoadMemberStateKg=" + maximumPermissibleAxleLoadMemberStateKg + ", "
            : "")
        +
        (axis1Ms != null ? "axis1Ms=" + axis1Ms + ", " : "") +
        (axis2Ms != null ? "axis2Ms=" + axis2Ms + ", " : "") +
        (axis3Ms != null ? "axis3Ms=" + axis3Ms + ", " : "") +
        (numberDriveAxles != null ? "numberDriveAxles=" + numberDriveAxles + ", " : "") +
        (codeToP3 != null ? "codeToP3=" + codeToP3 + ", " : "") +
        (codeToR != null ? "codeToR=" + codeToR + ", " : "") +
        (volumeTankTankersCcm != null ? "volumeTankTankersCcm=" + volumeTankTankersCcm + ", " : "") +
        (verticalLoadKg != null ? "verticalLoadKg=" + verticalLoadKg + ", " : "") +
        (designationNationalEmissionClass != null
            ? "designationNationalEmissionClass=" + designationNationalEmissionClass + ", "
            : "")
        +
        (tyres != null ? "tyres=" + tyres + ", " : "") +
        (onAxis1 != null ? "onAxis1=" + onAxis1 + ", " : "") +
        (onAxis2 != null ? "onAxis2=" + onAxis2 + ", " : "") +
        (onAxis3 != null ? "onAxis3=" + onAxis3 + ", " : "") +
        (registrationCertificateNumberPartII != null
            ? "registrationCertificateNumberPartII=" + registrationCertificateNumberPartII + ", "
            : "")
        +
        (featuresOfTheTypeApproval != null ? "featuresOfTheTypeApproval=" + featuresOfTheTypeApproval + ", " : "") +
        (lengthMm != null ? "lengthMm=" + lengthMm + ", " : "") +
        (widthMm != null ? "widthMm=" + widthMm + ", " : "") +
        (heightMm != null ? "heightMm=" + heightMm + ", " : "") +
        (otherEndorsements != null ? "otherEndorsements=" + otherEndorsements + ", " : "") +
        (remarksAndExceptions != null ? "remarksAndExceptions=" + remarksAndExceptions + ", " : "") +
        (numberDoors != null ? "numberDoors=" + numberDoors + ", " : "") +
        (horsepower != null ? "horsepower=" + horsepower + ", " : "") +
        (draft != null ? "draft=" + draft + ", " : "") +
        (mileageDate != null ? "mileageDate=" + mileageDate + ", " : "") +
        (mileage != null ? "mileage=" + mileage + ", " : "") +
        (lastGeneralInspection != null ? "lastGeneralInspection=" + lastGeneralInspection + ", " : "") +
        (finalEmissionsTest != null ? "finalEmissionsTest=" + finalEmissionsTest + ", " : "") +
        (nextEmissionsTest != null ? "nextEmissionsTest=" + nextEmissionsTest + ", " : "") +
        (nextGeneralInspection != null ? "nextGeneralInspection=" + nextGeneralInspection + ", " : "") +
        (frameType != null ? "frameType=" + frameType + ", " : "") +
        (frameMaterial != null ? "frameMaterial=" + frameMaterial + ", " : "") +
        (circuit != null ? "circuit=" + circuit + ", " : "") +
        (wheelSize != null ? "wheelSize=" + wheelSize + ", " : "") +
        (frameHeight != null ? "frameHeight=" + frameHeight + ", " : "") +
        (color != null ? "color=" + color + ", " : "") +
        (frameNo != null ? "frameNo=" + frameNo + ", " : "") +
        (manufacturer != null ? "manufacturer=" + manufacturer + ", " : "") +
        (originalYardNumberIIYardNumber != null
            ? "originalYardNumberIIYardNumber=" + originalYardNumberIIYardNumber + ", "
            : "")
        +
        (mainBuildingMaterial != null ? "mainBuildingMaterial=" + mainBuildingMaterial + ", " : "") +
        (previousLicensePlate != null ? "previousLicensePlate=" + previousLicensePlate + ", " : "") +
        (engineNumber != null ? "engineNumber=" + engineNumber + ", " : "") +
        (engineManufacturers != null ? "engineManufacturers=" + engineManufacturers + ", " : "") +
        (engineMakeType != null ? "engineMakeType=" + engineMakeType + ", " : "") +
        (driveType != null ? "driveType=" + driveType + ", " : "") +
        (zDriveNumber != null ? "zDriveNumber=" + zDriveNumber + ", " : "") +
        (idCardIssuedDate != null ? "idCardIssuedDate=" + idCardIssuedDate + ", " : "") +
        (carLicensePlates != null ? "carLicensePlates=" + carLicensePlates + ", " : "") +
        (distinct != null ? "distinct=" + distinct + ", " : "") +
        "}";
  }
}
