package me.jayna.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Mobility.
 */
@Entity
@Table(name = "mobility")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedEntityGraph(name = "Mobility.graph", attributeNodes = {
    @NamedAttributeNode("groupType"),
    @NamedAttributeNode(value = "contractMobilities", subgraph = "contractMobilities-subgraph"),
    @NamedAttributeNode(value = "financeaccountMobilities", subgraph = "financeaccountMobilities-subgraph"),
    @NamedAttributeNode(value = "mobilityRealestates", subgraph = "mobilityRealestates-subgraph"),
    @NamedAttributeNode(value = "contactMobilities", subgraph = "contactMobilities-subgraph"),
}, subgraphs = {
    @NamedSubgraph(name = "contractMobilities-subgraph", attributeNodes = {
        @NamedAttributeNode("contract")
    }),
    @NamedSubgraph(name = "financeaccountMobilities-subgraph", attributeNodes = {
        @NamedAttributeNode("financeaccount")
    }),
    @NamedSubgraph(name = "mobilityRealestates-subgraph", attributeNodes = {
        @NamedAttributeNode("realestate")
    }),
    @NamedSubgraph(name = "contactMobilities-subgraph", attributeNodes = {
        @NamedAttributeNode("contact")
    })
})
@NamedEntityGraph(name = "Mobility.overview", attributeNodes = {
    @NamedAttributeNode(value = "groupType", subgraph = "groupType-overview-subgraph")
}, subgraphs = {
    @NamedSubgraph(name = "groupType-overview-subgraph", attributeNodes = {
        @NamedAttributeNode(value = "subCategoryGroup", subgraph = "subCategoryGroup-overview-subgraph")
    }),
    @NamedSubgraph(name = "subCategoryGroup-overview-subgraph", attributeNodes = {
        @NamedAttributeNode(value = "subCategory", subgraph = "subCategory-overview-subgraph")
    }),
    @NamedSubgraph(name = "subCategory-overview-subgraph", attributeNodes = {
        @NamedAttributeNode("category")
    })
})
public class Mobility extends AbstractCategoryEntity {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @Column(name = "status")
  private String status;

  @Column(name = "owner")
  private String owner;

  @Column(name = "jhi_user")
  private String user;

  @Column(name = "date_first_registration")
  private LocalDate dateFirstRegistration;

  @Column(name = "brand")
  private String brand;

  @Column(name = "type")
  private String type;

  @Column(name = "trade_name")
  private String tradeName;

  @Column(name = "vehicle_id_number")
  private String vehicleIdNumber;

  @Column(name = "technically_permissible_maximum_mass_in_kg")
  private Integer technicallyPermissibleMaximumMassInKg;

  @Column(name = "maximum_permissible_mass_kg_registration_state")
  private Integer maximumPermissibleMassKgRegistrationState;

  @Column(name = "empty_mass")
  private Integer emptyMass;

  @Column(name = "period_validity")
  private String periodValidity;

  @Column(name = "approval_date")
  private LocalDate approvalDate;

  @Column(name = "vehicle_class")
  private String vehicleClass;

  @Column(name = "ec_type_approval_number_abe")
  private String ecTypeApprovalNumberABE;

  @Column(name = "no_axes")
  private Integer noAxes;

  @Column(name = "technically_permissible_trailer_load_braked_kg")
  private Integer technicallyPermissibleTrailerLoadBrakedKg;

  @Column(name = "technically_permissible_trailer_load_unbraked_kg")
  private Integer technicallyPermissibleTrailerLoadUnbrakedKg;

  @Column(name = "displacement")
  private Integer displacement;

  @Column(name = "nominal_power_kw_kg")
  private Integer nominalPowerKwKg;

  @Column(name = "type_fuel_energy_source")
  private String typeFuelEnergySource;

  @Column(name = "nominal_number_min")
  private Integer nominalNumberMin;

  @Column(name = "power_weight_ratio_kw_kg")
  private Integer powerWeightRatioKwKg;

  @Column(name = "vehicle_colour")
  private String vehicleColour;

  @Column(name = "no_seats")
  private Integer noSeats;

  @Column(name = "no_standing_places")
  private Integer noStandingPlaces;

  @Column(name = "maximum_speed_kmh")
  private Integer maximumSpeedKmh;

  @Column(name = "stationary_noise_db")
  private Integer stationaryNoiseDb;

  @Column(name = "no_revolutions")
  private Integer noRevolutions;

  @Column(name = "driving_noise_db")
  private Integer drivingNoiseDb;

  @Column(name = "co_2_g_km")
  private Integer co2GKm;

  @Column(name = "pollutant_class_relevant_eg")
  private String pollutantClassRelevantEg;

  @Column(name = "manufacturer_abbreviation")
  private String manufacturerAbbreviation;

  @Column(name = "manufacturing_numbercode_to_2")
  private String manufacturingNumbercodeTo2;

  @Column(name = "type_key_number_d_2")
  private String typeKeyNumberD2;

  @Column(name = "check_digit_vehicle_id_number")
  private String checkDigitVehicleIdNumber;

  @Column(name = "type_of_construction")
  private String typeOfConstruction;

  @Column(name = "designation_vehicle_class_bodywork")
  private String designationVehicleClassBodywork;

  @Column(name = "date_to_k")
  private LocalDate dateToK;

  @Column(name = "technically_permissible_max_axle_load")
  private Integer technicallyPermissibleMaxAxleLoad;

  @Column(name = "axis_1_tech")
  private Integer axis1Tech;

  @Column(name = "axis_2_tech")
  private Integer axis2Tech;

  @Column(name = "axis_3_tech")
  private Integer axis3Tech;

  @Column(name = "maximum_permissible_axle_load_member_state_kg")
  private Integer maximumPermissibleAxleLoadMemberStateKg;

  @Column(name = "axis_1_ms")
  private Integer axis1Ms;

  @Column(name = "axis_2_ms")
  private Integer axis2Ms;

  @Column(name = "axis_3_ms")
  private Integer axis3Ms;

  @Column(name = "number_drive_axles")
  private Integer numberDriveAxles;

  @Column(name = "code_to_p_3")
  private String codeToP3;

  @Column(name = "code_to_r")
  private String codeToR;

  @Column(name = "volume_tank_tankers_ccm")
  private Integer volumeTankTankersCcm;

  @Column(name = "vertical_load_kg")
  private Integer verticalLoadKg;

  @Column(name = "designation_national_emission_class")
  private String designationNationalEmissionClass;

  @Column(name = "tyres")
  private String tyres;

  @Column(name = "on_axis_1")
  private Integer onAxis1;

  @Column(name = "on_axis_2")
  private Integer onAxis2;

  @Column(name = "on_axis_3")
  private Integer onAxis3;

  @Column(name = "registration_certificate_number_part_ii")
  private String registrationCertificateNumberPartII;

  @Column(name = "features_of_the_type_approval")
  private String featuresOfTheTypeApproval;

  @Column(name = "length_mm")
  private Integer lengthMm;

  @Column(name = "width_mm")
  private Integer widthMm;

  @Column(name = "height_mm")
  private Integer heightMm;

  @Column(name = "other_endorsements")
  private String otherEndorsements;

  @Column(name = "remarks_and_exceptions")
  private String remarksAndExceptions;

  @Column(name = "number_doors")
  private Integer numberDoors;

  @Column(name = "horsepower")
  private Integer horsepower;

  @Column(name = "draft")
  private Integer draft;

  @Column(name = "mileage_date")
  private LocalDate mileageDate;

  @Column(name = "mileage")
  private Integer mileage;

  @Column(name = "last_general_inspection")
  private LocalDate lastGeneralInspection;

  @Column(name = "final_emissions_test")
  private LocalDate finalEmissionsTest;

  @Column(name = "next_emissions_test")
  private LocalDate nextEmissionsTest;

  @Column(name = "next_general_inspection")
  private LocalDate nextGeneralInspection;

  @Column(name = "frame_type")
  private String frameType;

  @Column(name = "frame_material")
  private String frameMaterial;

  @Column(name = "circuit")
  private String circuit;

  @Column(name = "wheel_size")
  private Integer wheelSize;

  @Column(name = "frame_height")
  private Integer frameHeight;

  @Column(name = "color")
  private String color;

  @Column(name = "frame_no")
  private String frameNo;

  @Column(name = "manufacturer")
  private String manufacturer;

  @Column(name = "original_yard_number_ii_yard_number")
  private String originalYardNumberIIYardNumber;

  @Column(name = "main_building_material")
  private String mainBuildingMaterial;

  @Column(name = "previous_license_plate")
  private String previousLicensePlate;

  @Column(name = "engine_number")
  private String engineNumber;

  @Column(name = "engine_manufacturers")
  private String engineManufacturers;

  @Column(name = "engine_make_type")
  private String engineMakeType;

  @Column(name = "drive_type")
  private String driveType;

  @Column(name = "z_drive_number")
  private String zDriveNumber;

  @Column(name = "id_card_issued_date")
  private LocalDate idCardIssuedDate;

  @Column(name = "car_license_plates")
  private String carLicensePlates;

  @OneToMany(mappedBy = "mobility", fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "mobility" }, allowSetters = true)
  private Set<ContactMobility> contactMobilities = new HashSet<>();

  @OneToMany(mappedBy = "mobility", fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "mobility" }, allowSetters = true)
  private Set<ContractMobility> contractMobilities = new HashSet<>();

  @OneToMany(mappedBy = "mobility", fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "mobility" }, allowSetters = true)
  private Set<FinanceAccountMobility> financeaccountMobilities = new HashSet<>();

  @OneToMany(mappedBy = "mobility", fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "mobility" }, allowSetters = true)
  private Set<MobilityRealestate> mobilityRealestates = new HashSet<>();

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public Mobility id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getStatus() {
    return this.status;
  }

  public Mobility status(String status) {
    this.setStatus(status);
    return this;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getOwner() {
    return this.owner;
  }

  public Mobility owner(String owner) {
    this.setOwner(owner);
    return this;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getUser() {
    return this.user;
  }

  public Mobility user(String user) {
    this.setUser(user);
    return this;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public LocalDate getDateFirstRegistration() {
    return this.dateFirstRegistration;
  }

  public Mobility dateFirstRegistration(LocalDate dateFirstRegistration) {
    this.setDateFirstRegistration(dateFirstRegistration);
    return this;
  }

  public void setDateFirstRegistration(LocalDate dateFirstRegistration) {
    this.dateFirstRegistration = dateFirstRegistration;
  }

  public String getBrand() {
    return this.brand;
  }

  public Mobility brand(String brand) {
    this.setBrand(brand);
    return this;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getType() {
    return this.type;
  }

  public Mobility type(String type) {
    this.setType(type);
    return this;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getTradeName() {
    return this.tradeName;
  }

  public Mobility tradeName(String tradeName) {
    this.setTradeName(tradeName);
    return this;
  }

  public void setTradeName(String tradeName) {
    this.tradeName = tradeName;
  }

  public String getVehicleIdNumber() {
    return this.vehicleIdNumber;
  }

  public Mobility vehicleIdNumber(String vehicleIdNumber) {
    this.setVehicleIdNumber(vehicleIdNumber);
    return this;
  }

  public void setVehicleIdNumber(String vehicleIdNumber) {
    this.vehicleIdNumber = vehicleIdNumber;
  }

  public Integer getTechnicallyPermissibleMaximumMassInKg() {
    return this.technicallyPermissibleMaximumMassInKg;
  }

  public Mobility technicallyPermissibleMaximumMassInKg(Integer technicallyPermissibleMaximumMassInKg) {
    this.setTechnicallyPermissibleMaximumMassInKg(technicallyPermissibleMaximumMassInKg);
    return this;
  }

  public void setTechnicallyPermissibleMaximumMassInKg(Integer technicallyPermissibleMaximumMassInKg) {
    this.technicallyPermissibleMaximumMassInKg = technicallyPermissibleMaximumMassInKg;
  }

  public Integer getMaximumPermissibleMassKgRegistrationState() {
    return this.maximumPermissibleMassKgRegistrationState;
  }

  public Mobility maximumPermissibleMassKgRegistrationState(Integer maximumPermissibleMassKgRegistrationState) {
    this.setMaximumPermissibleMassKgRegistrationState(maximumPermissibleMassKgRegistrationState);
    return this;
  }

  public void setMaximumPermissibleMassKgRegistrationState(Integer maximumPermissibleMassKgRegistrationState) {
    this.maximumPermissibleMassKgRegistrationState = maximumPermissibleMassKgRegistrationState;
  }

  public Integer getEmptyMass() {
    return this.emptyMass;
  }

  public Mobility emptyMass(Integer emptyMass) {
    this.setEmptyMass(emptyMass);
    return this;
  }

  public void setEmptyMass(Integer emptyMass) {
    this.emptyMass = emptyMass;
  }

  public String getPeriodValidity() {
    return this.periodValidity;
  }

  public Mobility periodValidity(String periodValidity) {
    this.setPeriodValidity(periodValidity);
    return this;
  }

  public void setPeriodValidity(String periodValidity) {
    this.periodValidity = periodValidity;
  }

  public LocalDate getApprovalDate() {
    return this.approvalDate;
  }

  public Mobility approvalDate(LocalDate approvalDate) {
    this.setApprovalDate(approvalDate);
    return this;
  }

  public void setApprovalDate(LocalDate approvalDate) {
    this.approvalDate = approvalDate;
  }

  public String getVehicleClass() {
    return this.vehicleClass;
  }

  public Mobility vehicleClass(String vehicleClass) {
    this.setVehicleClass(vehicleClass);
    return this;
  }

  public void setVehicleClass(String vehicleClass) {
    this.vehicleClass = vehicleClass;
  }

  public String getEcTypeApprovalNumberABE() {
    return this.ecTypeApprovalNumberABE;
  }

  public Mobility ecTypeApprovalNumberABE(String ecTypeApprovalNumberABE) {
    this.setEcTypeApprovalNumberABE(ecTypeApprovalNumberABE);
    return this;
  }

  public void setEcTypeApprovalNumberABE(String ecTypeApprovalNumberABE) {
    this.ecTypeApprovalNumberABE = ecTypeApprovalNumberABE;
  }

  public Integer getNoAxes() {
    return this.noAxes;
  }

  public Mobility noAxes(Integer noAxes) {
    this.setNoAxes(noAxes);
    return this;
  }

  public void setNoAxes(Integer noAxes) {
    this.noAxes = noAxes;
  }

  public Integer getTechnicallyPermissibleTrailerLoadBrakedKg() {
    return this.technicallyPermissibleTrailerLoadBrakedKg;
  }

  public Mobility technicallyPermissibleTrailerLoadBrakedKg(Integer technicallyPermissibleTrailerLoadBrakedKg) {
    this.setTechnicallyPermissibleTrailerLoadBrakedKg(technicallyPermissibleTrailerLoadBrakedKg);
    return this;
  }

  public void setTechnicallyPermissibleTrailerLoadBrakedKg(Integer technicallyPermissibleTrailerLoadBrakedKg) {
    this.technicallyPermissibleTrailerLoadBrakedKg = technicallyPermissibleTrailerLoadBrakedKg;
  }

  public Integer getTechnicallyPermissibleTrailerLoadUnbrakedKg() {
    return this.technicallyPermissibleTrailerLoadUnbrakedKg;
  }

  public Mobility technicallyPermissibleTrailerLoadUnbrakedKg(Integer technicallyPermissibleTrailerLoadUnbrakedKg) {
    this.setTechnicallyPermissibleTrailerLoadUnbrakedKg(technicallyPermissibleTrailerLoadUnbrakedKg);
    return this;
  }

  public void setTechnicallyPermissibleTrailerLoadUnbrakedKg(Integer technicallyPermissibleTrailerLoadUnbrakedKg) {
    this.technicallyPermissibleTrailerLoadUnbrakedKg = technicallyPermissibleTrailerLoadUnbrakedKg;
  }

  public Integer getDisplacement() {
    return this.displacement;
  }

  public Mobility displacement(Integer displacement) {
    this.setDisplacement(displacement);
    return this;
  }

  public void setDisplacement(Integer displacement) {
    this.displacement = displacement;
  }

  public Integer getNominalPowerKwKg() {
    return this.nominalPowerKwKg;
  }

  public Mobility nominalPowerKwKg(Integer nominalPowerKwKg) {
    this.setNominalPowerKwKg(nominalPowerKwKg);
    return this;
  }

  public void setNominalPowerKwKg(Integer nominalPowerKwKg) {
    this.nominalPowerKwKg = nominalPowerKwKg;
  }

  public String getTypeFuelEnergySource() {
    return this.typeFuelEnergySource;
  }

  public Mobility typeFuelEnergySource(String typeFuelEnergySource) {
    this.setTypeFuelEnergySource(typeFuelEnergySource);
    return this;
  }

  public void setTypeFuelEnergySource(String typeFuelEnergySource) {
    this.typeFuelEnergySource = typeFuelEnergySource;
  }

  public Integer getNominalNumberMin() {
    return this.nominalNumberMin;
  }

  public Mobility nominalNumberMin(Integer nominalNumberMin) {
    this.setNominalNumberMin(nominalNumberMin);
    return this;
  }

  public void setNominalNumberMin(Integer nominalNumberMin) {
    this.nominalNumberMin = nominalNumberMin;
  }

  public Integer getPowerWeightRatioKwKg() {
    return this.powerWeightRatioKwKg;
  }

  public Mobility powerWeightRatioKwKg(Integer powerWeightRatioKwKg) {
    this.setPowerWeightRatioKwKg(powerWeightRatioKwKg);
    return this;
  }

  public void setPowerWeightRatioKwKg(Integer powerWeightRatioKwKg) {
    this.powerWeightRatioKwKg = powerWeightRatioKwKg;
  }

  public String getVehicleColour() {
    return this.vehicleColour;
  }

  public Mobility vehicleColour(String vehicleColour) {
    this.setVehicleColour(vehicleColour);
    return this;
  }

  public void setVehicleColour(String vehicleColour) {
    this.vehicleColour = vehicleColour;
  }

  public Integer getNoSeats() {
    return this.noSeats;
  }

  public Mobility noSeats(Integer noSeats) {
    this.setNoSeats(noSeats);
    return this;
  }

  public void setNoSeats(Integer noSeats) {
    this.noSeats = noSeats;
  }

  public Integer getNoStandingPlaces() {
    return this.noStandingPlaces;
  }

  public Mobility noStandingPlaces(Integer noStandingPlaces) {
    this.setNoStandingPlaces(noStandingPlaces);
    return this;
  }

  public void setNoStandingPlaces(Integer noStandingPlaces) {
    this.noStandingPlaces = noStandingPlaces;
  }

  public Integer getMaximumSpeedKmh() {
    return this.maximumSpeedKmh;
  }

  public Mobility maximumSpeedKmh(Integer maximumSpeedKmh) {
    this.setMaximumSpeedKmh(maximumSpeedKmh);
    return this;
  }

  public void setMaximumSpeedKmh(Integer maximumSpeedKmh) {
    this.maximumSpeedKmh = maximumSpeedKmh;
  }

  public Integer getStationaryNoiseDb() {
    return this.stationaryNoiseDb;
  }

  public Mobility stationaryNoiseDb(Integer stationaryNoiseDb) {
    this.setStationaryNoiseDb(stationaryNoiseDb);
    return this;
  }

  public void setStationaryNoiseDb(Integer stationaryNoiseDb) {
    this.stationaryNoiseDb = stationaryNoiseDb;
  }

  public Integer getNoRevolutions() {
    return this.noRevolutions;
  }

  public Mobility noRevolutions(Integer noRevolutions) {
    this.setNoRevolutions(noRevolutions);
    return this;
  }

  public void setNoRevolutions(Integer noRevolutions) {
    this.noRevolutions = noRevolutions;
  }

  public Integer getDrivingNoiseDb() {
    return this.drivingNoiseDb;
  }

  public Mobility drivingNoiseDb(Integer drivingNoiseDb) {
    this.setDrivingNoiseDb(drivingNoiseDb);
    return this;
  }

  public void setDrivingNoiseDb(Integer drivingNoiseDb) {
    this.drivingNoiseDb = drivingNoiseDb;
  }

  public Integer getCo2GKm() {
    return this.co2GKm;
  }

  public Mobility co2GKm(Integer co2GKm) {
    this.setCo2GKm(co2GKm);
    return this;
  }

  public void setCo2GKm(Integer co2GKm) {
    this.co2GKm = co2GKm;
  }

  public String getPollutantClassRelevantEg() {
    return this.pollutantClassRelevantEg;
  }

  public Mobility pollutantClassRelevantEg(String pollutantClassRelevantEg) {
    this.setPollutantClassRelevantEg(pollutantClassRelevantEg);
    return this;
  }

  public void setPollutantClassRelevantEg(String pollutantClassRelevantEg) {
    this.pollutantClassRelevantEg = pollutantClassRelevantEg;
  }

  public String getManufacturerAbbreviation() {
    return this.manufacturerAbbreviation;
  }

  public Mobility manufacturerAbbreviation(String manufacturerAbbreviation) {
    this.setManufacturerAbbreviation(manufacturerAbbreviation);
    return this;
  }

  public void setManufacturerAbbreviation(String manufacturerAbbreviation) {
    this.manufacturerAbbreviation = manufacturerAbbreviation;
  }

  public String getManufacturingNumbercodeTo2() {
    return this.manufacturingNumbercodeTo2;
  }

  public Mobility manufacturingNumbercodeTo2(String manufacturingNumbercodeTo2) {
    this.setManufacturingNumbercodeTo2(manufacturingNumbercodeTo2);
    return this;
  }

  public void setManufacturingNumbercodeTo2(String manufacturingNumbercodeTo2) {
    this.manufacturingNumbercodeTo2 = manufacturingNumbercodeTo2;
  }

  public String getTypeKeyNumberD2() {
    return this.typeKeyNumberD2;
  }

  public Mobility typeKeyNumberD2(String typeKeyNumberD2) {
    this.setTypeKeyNumberD2(typeKeyNumberD2);
    return this;
  }

  public void setTypeKeyNumberD2(String typeKeyNumberD2) {
    this.typeKeyNumberD2 = typeKeyNumberD2;
  }

  public String getCheckDigitVehicleIdNumber() {
    return this.checkDigitVehicleIdNumber;
  }

  public Mobility checkDigitVehicleIdNumber(String checkDigitVehicleIdNumber) {
    this.setCheckDigitVehicleIdNumber(checkDigitVehicleIdNumber);
    return this;
  }

  public void setCheckDigitVehicleIdNumber(String checkDigitVehicleIdNumber) {
    this.checkDigitVehicleIdNumber = checkDigitVehicleIdNumber;
  }

  public String getTypeOfConstruction() {
    return this.typeOfConstruction;
  }

  public Mobility typeOfConstruction(String typeOfConstruction) {
    this.setTypeOfConstruction(typeOfConstruction);
    return this;
  }

  public void setTypeOfConstruction(String typeOfConstruction) {
    this.typeOfConstruction = typeOfConstruction;
  }

  public String getDesignationVehicleClassBodywork() {
    return this.designationVehicleClassBodywork;
  }

  public Mobility designationVehicleClassBodywork(String designationVehicleClassBodywork) {
    this.setDesignationVehicleClassBodywork(designationVehicleClassBodywork);
    return this;
  }

  public void setDesignationVehicleClassBodywork(String designationVehicleClassBodywork) {
    this.designationVehicleClassBodywork = designationVehicleClassBodywork;
  }

  public LocalDate getDateToK() {
    return this.dateToK;
  }

  public Mobility dateToK(LocalDate dateToK) {
    this.setDateToK(dateToK);
    return this;
  }

  public void setDateToK(LocalDate dateToK) {
    this.dateToK = dateToK;
  }

  public Integer getTechnicallyPermissibleMaxAxleLoad() {
    return this.technicallyPermissibleMaxAxleLoad;
  }

  public Mobility technicallyPermissibleMaxAxleLoad(Integer technicallyPermissibleMaxAxleLoad) {
    this.setTechnicallyPermissibleMaxAxleLoad(technicallyPermissibleMaxAxleLoad);
    return this;
  }

  public void setTechnicallyPermissibleMaxAxleLoad(Integer technicallyPermissibleMaxAxleLoad) {
    this.technicallyPermissibleMaxAxleLoad = technicallyPermissibleMaxAxleLoad;
  }

  public Integer getAxis1Tech() {
    return this.axis1Tech;
  }

  public Mobility axis1Tech(Integer axis1Tech) {
    this.setAxis1Tech(axis1Tech);
    return this;
  }

  public void setAxis1Tech(Integer axis1Tech) {
    this.axis1Tech = axis1Tech;
  }

  public Integer getAxis2Tech() {
    return this.axis2Tech;
  }

  public Mobility axis2Tech(Integer axis2Tech) {
    this.setAxis2Tech(axis2Tech);
    return this;
  }

  public void setAxis2Tech(Integer axis2Tech) {
    this.axis2Tech = axis2Tech;
  }

  public Integer getAxis3Tech() {
    return this.axis3Tech;
  }

  public Mobility axis3Tech(Integer axis3Tech) {
    this.setAxis3Tech(axis3Tech);
    return this;
  }

  public void setAxis3Tech(Integer axis3Tech) {
    this.axis3Tech = axis3Tech;
  }

  public Integer getMaximumPermissibleAxleLoadMemberStateKg() {
    return this.maximumPermissibleAxleLoadMemberStateKg;
  }

  public Mobility maximumPermissibleAxleLoadMemberStateKg(Integer maximumPermissibleAxleLoadMemberStateKg) {
    this.setMaximumPermissibleAxleLoadMemberStateKg(maximumPermissibleAxleLoadMemberStateKg);
    return this;
  }

  public void setMaximumPermissibleAxleLoadMemberStateKg(Integer maximumPermissibleAxleLoadMemberStateKg) {
    this.maximumPermissibleAxleLoadMemberStateKg = maximumPermissibleAxleLoadMemberStateKg;
  }

  public Integer getAxis1Ms() {
    return this.axis1Ms;
  }

  public Mobility axis1Ms(Integer axis1Ms) {
    this.setAxis1Ms(axis1Ms);
    return this;
  }

  public void setAxis1Ms(Integer axis1Ms) {
    this.axis1Ms = axis1Ms;
  }

  public Integer getAxis2Ms() {
    return this.axis2Ms;
  }

  public Mobility axis2Ms(Integer axis2Ms) {
    this.setAxis2Ms(axis2Ms);
    return this;
  }

  public void setAxis2Ms(Integer axis2Ms) {
    this.axis2Ms = axis2Ms;
  }

  public Integer getAxis3Ms() {
    return this.axis3Ms;
  }

  public Mobility axis3Ms(Integer axis3Ms) {
    this.setAxis3Ms(axis3Ms);
    return this;
  }

  public void setAxis3Ms(Integer axis3Ms) {
    this.axis3Ms = axis3Ms;
  }

  public Integer getNumberDriveAxles() {
    return this.numberDriveAxles;
  }

  public Mobility numberDriveAxles(Integer numberDriveAxles) {
    this.setNumberDriveAxles(numberDriveAxles);
    return this;
  }

  public void setNumberDriveAxles(Integer numberDriveAxles) {
    this.numberDriveAxles = numberDriveAxles;
  }

  public String getCodeToP3() {
    return this.codeToP3;
  }

  public Mobility codeToP3(String codeToP3) {
    this.setCodeToP3(codeToP3);
    return this;
  }

  public void setCodeToP3(String codeToP3) {
    this.codeToP3 = codeToP3;
  }

  public String getCodeToR() {
    return this.codeToR;
  }

  public Mobility codeToR(String codeToR) {
    this.setCodeToR(codeToR);
    return this;
  }

  public void setCodeToR(String codeToR) {
    this.codeToR = codeToR;
  }

  public Integer getVolumeTankTankersCcm() {
    return this.volumeTankTankersCcm;
  }

  public Mobility volumeTankTankersCcm(Integer volumeTankTankersCcm) {
    this.setVolumeTankTankersCcm(volumeTankTankersCcm);
    return this;
  }

  public void setVolumeTankTankersCcm(Integer volumeTankTankersCcm) {
    this.volumeTankTankersCcm = volumeTankTankersCcm;
  }

  public Integer getVerticalLoadKg() {
    return this.verticalLoadKg;
  }

  public Mobility verticalLoadKg(Integer verticalLoadKg) {
    this.setVerticalLoadKg(verticalLoadKg);
    return this;
  }

  public void setVerticalLoadKg(Integer verticalLoadKg) {
    this.verticalLoadKg = verticalLoadKg;
  }

  public String getDesignationNationalEmissionClass() {
    return this.designationNationalEmissionClass;
  }

  public Mobility designationNationalEmissionClass(String designationNationalEmissionClass) {
    this.setDesignationNationalEmissionClass(designationNationalEmissionClass);
    return this;
  }

  public void setDesignationNationalEmissionClass(String designationNationalEmissionClass) {
    this.designationNationalEmissionClass = designationNationalEmissionClass;
  }

  public String getTyres() {
    return this.tyres;
  }

  public Mobility tyres(String tyres) {
    this.setTyres(tyres);
    return this;
  }

  public void setTyres(String tyres) {
    this.tyres = tyres;
  }

  public Integer getOnAxis1() {
    return this.onAxis1;
  }

  public Mobility onAxis1(Integer onAxis1) {
    this.setOnAxis1(onAxis1);
    return this;
  }

  public void setOnAxis1(Integer onAxis1) {
    this.onAxis1 = onAxis1;
  }

  public Integer getOnAxis2() {
    return this.onAxis2;
  }

  public Mobility onAxis2(Integer onAxis2) {
    this.setOnAxis2(onAxis2);
    return this;
  }

  public void setOnAxis2(Integer onAxis2) {
    this.onAxis2 = onAxis2;
  }

  public Integer getOnAxis3() {
    return this.onAxis3;
  }

  public Mobility onAxis3(Integer onAxis3) {
    this.setOnAxis3(onAxis3);
    return this;
  }

  public void setOnAxis3(Integer onAxis3) {
    this.onAxis3 = onAxis3;
  }

  public String getRegistrationCertificateNumberPartII() {
    return this.registrationCertificateNumberPartII;
  }

  public Mobility registrationCertificateNumberPartII(String registrationCertificateNumberPartII) {
    this.setRegistrationCertificateNumberPartII(registrationCertificateNumberPartII);
    return this;
  }

  public void setRegistrationCertificateNumberPartII(String registrationCertificateNumberPartII) {
    this.registrationCertificateNumberPartII = registrationCertificateNumberPartII;
  }

  public String getFeaturesOfTheTypeApproval() {
    return this.featuresOfTheTypeApproval;
  }

  public Mobility featuresOfTheTypeApproval(String featuresOfTheTypeApproval) {
    this.setFeaturesOfTheTypeApproval(featuresOfTheTypeApproval);
    return this;
  }

  public void setFeaturesOfTheTypeApproval(String featuresOfTheTypeApproval) {
    this.featuresOfTheTypeApproval = featuresOfTheTypeApproval;
  }

  public Integer getLengthMm() {
    return this.lengthMm;
  }

  public Mobility lengthMm(Integer lengthMm) {
    this.setLengthMm(lengthMm);
    return this;
  }

  public void setLengthMm(Integer lengthMm) {
    this.lengthMm = lengthMm;
  }

  public Integer getWidthMm() {
    return this.widthMm;
  }

  public Mobility widthMm(Integer widthMm) {
    this.setWidthMm(widthMm);
    return this;
  }

  public void setWidthMm(Integer widthMm) {
    this.widthMm = widthMm;
  }

  public Integer getHeightMm() {
    return this.heightMm;
  }

  public Mobility heightMm(Integer heightMm) {
    this.setHeightMm(heightMm);
    return this;
  }

  public void setHeightMm(Integer heightMm) {
    this.heightMm = heightMm;
  }

  public String getOtherEndorsements() {
    return this.otherEndorsements;
  }

  public Mobility otherEndorsements(String otherEndorsements) {
    this.setOtherEndorsements(otherEndorsements);
    return this;
  }

  public void setOtherEndorsements(String otherEndorsements) {
    this.otherEndorsements = otherEndorsements;
  }

  public String getRemarksAndExceptions() {
    return this.remarksAndExceptions;
  }

  public Mobility remarksAndExceptions(String remarksAndExceptions) {
    this.setRemarksAndExceptions(remarksAndExceptions);
    return this;
  }

  public void setRemarksAndExceptions(String remarksAndExceptions) {
    this.remarksAndExceptions = remarksAndExceptions;
  }

  public Integer getNumberDoors() {
    return this.numberDoors;
  }

  public Mobility numberDoors(Integer numberDoors) {
    this.setNumberDoors(numberDoors);
    return this;
  }

  public void setNumberDoors(Integer numberDoors) {
    this.numberDoors = numberDoors;
  }

  public Integer getHorsepower() {
    return this.horsepower;
  }

  public Mobility horsepower(Integer horsepower) {
    this.setHorsepower(horsepower);
    return this;
  }

  public void setHorsepower(Integer horsepower) {
    this.horsepower = horsepower;
  }

  public Integer getDraft() {
    return this.draft;
  }

  public Mobility draft(Integer draft) {
    this.setDraft(draft);
    return this;
  }

  public void setDraft(Integer draft) {
    this.draft = draft;
  }

  public LocalDate getMileageDate() {
    return this.mileageDate;
  }

  public Mobility mileageDate(LocalDate mileageDate) {
    this.setMileageDate(mileageDate);
    return this;
  }

  public void setMileageDate(LocalDate mileageDate) {
    this.mileageDate = mileageDate;
  }

  public Integer getMileage() {
    return this.mileage;
  }

  public Mobility mileage(Integer mileage) {
    this.setMileage(mileage);
    return this;
  }

  public void setMileage(Integer mileage) {
    this.mileage = mileage;
  }

  public LocalDate getLastGeneralInspection() {
    return this.lastGeneralInspection;
  }

  public Mobility lastGeneralInspection(LocalDate lastGeneralInspection) {
    this.setLastGeneralInspection(lastGeneralInspection);
    return this;
  }

  public void setLastGeneralInspection(LocalDate lastGeneralInspection) {
    this.lastGeneralInspection = lastGeneralInspection;
  }

  public LocalDate getFinalEmissionsTest() {
    return this.finalEmissionsTest;
  }

  public Mobility finalEmissionsTest(LocalDate finalEmissionsTest) {
    this.setFinalEmissionsTest(finalEmissionsTest);
    return this;
  }

  public void setFinalEmissionsTest(LocalDate finalEmissionsTest) {
    this.finalEmissionsTest = finalEmissionsTest;
  }

  public LocalDate getNextEmissionsTest() {
    return this.nextEmissionsTest;
  }

  public Mobility nextEmissionsTest(LocalDate nextEmissionsTest) {
    this.setNextEmissionsTest(nextEmissionsTest);
    return this;
  }

  public void setNextEmissionsTest(LocalDate nextEmissionsTest) {
    this.nextEmissionsTest = nextEmissionsTest;
  }

  public LocalDate getNextGeneralInspection() {
    return this.nextGeneralInspection;
  }

  public Mobility nextGeneralInspection(LocalDate nextGeneralInspection) {
    this.setNextGeneralInspection(nextGeneralInspection);
    return this;
  }

  public void setNextGeneralInspection(LocalDate nextGeneralInspection) {
    this.nextGeneralInspection = nextGeneralInspection;
  }

  public String getFrameType() {
    return this.frameType;
  }

  public Mobility frameType(String frameType) {
    this.setFrameType(frameType);
    return this;
  }

  public void setFrameType(String frameType) {
    this.frameType = frameType;
  }

  public String getFrameMaterial() {
    return this.frameMaterial;
  }

  public Mobility frameMaterial(String frameMaterial) {
    this.setFrameMaterial(frameMaterial);
    return this;
  }

  public void setFrameMaterial(String frameMaterial) {
    this.frameMaterial = frameMaterial;
  }

  public String getCircuit() {
    return this.circuit;
  }

  public Mobility circuit(String circuit) {
    this.setCircuit(circuit);
    return this;
  }

  public void setCircuit(String circuit) {
    this.circuit = circuit;
  }

  public Integer getWheelSize() {
    return this.wheelSize;
  }

  public Mobility wheelSize(Integer wheelSize) {
    this.setWheelSize(wheelSize);
    return this;
  }

  public void setWheelSize(Integer wheelSize) {
    this.wheelSize = wheelSize;
  }

  public Integer getFrameHeight() {
    return this.frameHeight;
  }

  public Mobility frameHeight(Integer frameHeight) {
    this.setFrameHeight(frameHeight);
    return this;
  }

  public void setFrameHeight(Integer frameHeight) {
    this.frameHeight = frameHeight;
  }

  public String getColor() {
    return this.color;
  }

  public Mobility color(String color) {
    this.setColor(color);
    return this;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getFrameNo() {
    return this.frameNo;
  }

  public Mobility frameNo(String frameNo) {
    this.setFrameNo(frameNo);
    return this;
  }

  public void setFrameNo(String frameNo) {
    this.frameNo = frameNo;
  }

  public String getManufacturer() {
    return this.manufacturer;
  }

  public Mobility manufacturer(String manufacturer) {
    this.setManufacturer(manufacturer);
    return this;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getOriginalYardNumberIIYardNumber() {
    return this.originalYardNumberIIYardNumber;
  }

  public Mobility originalYardNumberIIYardNumber(String originalYardNumberIIYardNumber) {
    this.setOriginalYardNumberIIYardNumber(originalYardNumberIIYardNumber);
    return this;
  }

  public void setOriginalYardNumberIIYardNumber(String originalYardNumberIIYardNumber) {
    this.originalYardNumberIIYardNumber = originalYardNumberIIYardNumber;
  }

  public String getMainBuildingMaterial() {
    return this.mainBuildingMaterial;
  }

  public Mobility mainBuildingMaterial(String mainBuildingMaterial) {
    this.setMainBuildingMaterial(mainBuildingMaterial);
    return this;
  }

  public void setMainBuildingMaterial(String mainBuildingMaterial) {
    this.mainBuildingMaterial = mainBuildingMaterial;
  }

  public String getPreviousLicensePlate() {
    return this.previousLicensePlate;
  }

  public Mobility previousLicensePlate(String previousLicensePlate) {
    this.setPreviousLicensePlate(previousLicensePlate);
    return this;
  }

  public void setPreviousLicensePlate(String previousLicensePlate) {
    this.previousLicensePlate = previousLicensePlate;
  }

  public String getEngineNumber() {
    return this.engineNumber;
  }

  public Mobility engineNumber(String engineNumber) {
    this.setEngineNumber(engineNumber);
    return this;
  }

  public void setEngineNumber(String engineNumber) {
    this.engineNumber = engineNumber;
  }

  public String getEngineManufacturers() {
    return this.engineManufacturers;
  }

  public Mobility engineManufacturers(String engineManufacturers) {
    this.setEngineManufacturers(engineManufacturers);
    return this;
  }

  public void setEngineManufacturers(String engineManufacturers) {
    this.engineManufacturers = engineManufacturers;
  }

  public String getEngineMakeType() {
    return this.engineMakeType;
  }

  public Mobility engineMakeType(String engineMakeType) {
    this.setEngineMakeType(engineMakeType);
    return this;
  }

  public void setEngineMakeType(String engineMakeType) {
    this.engineMakeType = engineMakeType;
  }

  public String getDriveType() {
    return this.driveType;
  }

  public Mobility driveType(String driveType) {
    this.setDriveType(driveType);
    return this;
  }

  public void setDriveType(String driveType) {
    this.driveType = driveType;
  }

  public String getzDriveNumber() {
    return this.zDriveNumber;
  }

  public Mobility zDriveNumber(String zDriveNumber) {
    this.setzDriveNumber(zDriveNumber);
    return this;
  }

  public void setzDriveNumber(String zDriveNumber) {
    this.zDriveNumber = zDriveNumber;
  }

  public LocalDate getIdCardIssuedDate() {
    return this.idCardIssuedDate;
  }

  public Mobility idCardIssuedDate(LocalDate idCardIssuedDate) {
    this.setIdCardIssuedDate(idCardIssuedDate);
    return this;
  }

  public void setIdCardIssuedDate(LocalDate idCardIssuedDate) {
    this.idCardIssuedDate = idCardIssuedDate;
  }

  public String getCarLicensePlates() {
    return this.carLicensePlates;
  }

  public Mobility carLicensePlates(String carLicensePlates) {
    this.setCarLicensePlates(carLicensePlates);
    return this;
  }

  public void setCarLicensePlates(String carLicensePlates) {
    this.carLicensePlates = carLicensePlates;
  }

  public Set<ContactMobility> getContactMobilities() {
    return this.contactMobilities;
  }

  public void setContactMobilities(Set<ContactMobility> contactMobilities) {
    this.contactMobilities = contactMobilities;
  }

  public Mobility contactMobilities(
      Set<ContactMobility> contactMobilities) {
    this.setContactMobilities(contactMobilities);
    return this;
  }

  public Mobility addContactMobility(
      ContactMobility contactMobility) {
    this.contactMobilities.add(contactMobility);
    contactMobility.setMobility(this);
    return this;
  }

  public Mobility removeContactMobility(
      ContactMobility contactMobility) {
    this.contactMobilities.remove(contactMobility);
    contactMobility.setMobility(null);
    return this;
  }

  public Set<ContractMobility> getContractMobilities() {
    return this.contractMobilities;
  }

  public void setContractMobilities(Set<ContractMobility> contractMobilities) {
    this.contractMobilities = contractMobilities;
  }

  public Mobility contractMobilities(
      Set<ContractMobility> contractMobilities) {
    this.setContractMobilities(contractMobilities);
    return this;
  }

  public Mobility addContractMobility(
      ContractMobility contractMobility) {
    this.contractMobilities.add(contractMobility);
    contractMobility.setMobility(this);
    return this;
  }

  public Mobility removeContractMobilities(
      ContractMobility contractMobility) {
    this.contractMobilities.remove(contractMobility);
    contractMobility.setMobility(null);
    return this;
  }

  public Set<FinanceAccountMobility> getFinanceaccountMobilities() {
    return this.financeaccountMobilities;
  }

  public void setFinanceaccountMobilities(
      Set<FinanceAccountMobility> financeaccountMobilities) {
    if (this.financeaccountMobilities != null) {
      this.financeaccountMobilities.forEach(i -> i.setMobility(null));
    }
    if (financeaccountMobilities != null) {
      financeaccountMobilities.forEach(i -> i.setMobility(this));
    }
    this.financeaccountMobilities = financeaccountMobilities;
  }

  public Mobility financeaccountMobilities(
      Set<FinanceAccountMobility> financeaccountMobilities) {
    this.setFinanceaccountMobilities(financeaccountMobilities);
    return this;
  }

  public Mobility addFinanceaccountMobilities(
      FinanceAccountMobility financeaccountMobility) {
    this.financeaccountMobilities.add(financeaccountMobility);
    financeaccountMobility.setMobility(this);
    return this;
  }

  public Mobility removeFinanceaccountMobilities(
      FinanceAccountMobility financeaccountMobility) {
    this.financeaccountMobilities.remove(financeaccountMobility);
    financeaccountMobility.setMobility(null);
    return this;
  }

  public Set<MobilityRealestate> getMobilityRealestates() {
    return this.mobilityRealestates;
  }

  public void setMobilityRealestates(
      Set<MobilityRealestate> mobilityRealestates) {
    if (this.mobilityRealestates != null) {
      this.mobilityRealestates.forEach(i -> i.setMobility(null));
    }
    if (mobilityRealestates != null) {
      mobilityRealestates.forEach(i -> i.setMobility(this));
    }
    this.mobilityRealestates = mobilityRealestates;
  }

  public Mobility mobilityRealestates(
      Set<MobilityRealestate> mobilityRealestates) {
    this.setMobilityRealestates(mobilityRealestates);
    return this;
  }

  public Mobility addFinanceaccountMobilities(
      MobilityRealestate mobilityRealestate) {
    this.mobilityRealestates.add(mobilityRealestate);
    mobilityRealestate.setMobility(this);
    return this;
  }

  public Mobility removeFinanceaccountMobilities(
      MobilityRealestate mobilityRealestate) {
    this.mobilityRealestates.remove(mobilityRealestate);
    mobilityRealestate.setMobility(null);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
  // setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Mobility)) {
      return false;
    }
    return id != null && id.equals(((Mobility) o).id);
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
    return "Mobility{" +
        "id=" + getId() +
        ", status='" + getStatus() + "'" +
        ", owner='" + getOwner() + "'" +
        ", user='" + getUser() + "'" +
        ", dateFirstRegistration='" + getDateFirstRegistration() + "'" +
        ", brand='" + getBrand() + "'" +
        ", type='" + getType() + "'" +
        ", tradeName='" + getTradeName() + "'" +
        ", vehicleIdNumber='" + getVehicleIdNumber() + "'" +
        ", technicallyPermissibleMaximumMassInKg=" + getTechnicallyPermissibleMaximumMassInKg() +
        ", maximumPermissibleMassKgRegistrationState=" + getMaximumPermissibleMassKgRegistrationState() +
        ", emptyMass=" + getEmptyMass() +
        ", periodValidity='" + getPeriodValidity() + "'" +
        ", approvalDate='" + getApprovalDate() + "'" +
        ", vehicleClass='" + getVehicleClass() + "'" +
        ", ecTypeApprovalNumberABE='" + getEcTypeApprovalNumberABE() + "'" +
        ", noAxes=" + getNoAxes() +
        ", technicallyPermissibleTrailerLoadBrakedKg=" + getTechnicallyPermissibleTrailerLoadBrakedKg() +
        ", technicallyPermissibleTrailerLoadUnbrakedKg=" + getTechnicallyPermissibleTrailerLoadUnbrakedKg() +
        ", displacement=" + getDisplacement() +
        ", nominalPowerKwKg=" + getNominalPowerKwKg() +
        ", typeFuelEnergySource='" + getTypeFuelEnergySource() + "'" +
        ", nominalNumberMin=" + getNominalNumberMin() +
        ", powerWeightRatioKwKg=" + getPowerWeightRatioKwKg() +
        ", vehicleColour='" + getVehicleColour() + "'" +
        ", noSeats=" + getNoSeats() +
        ", noStandingPlaces=" + getNoStandingPlaces() +
        ", maximumSpeedKmh=" + getMaximumSpeedKmh() +
        ", stationaryNoiseDb=" + getStationaryNoiseDb() +
        ", noRevolutions=" + getNoRevolutions() +
        ", drivingNoiseDb=" + getDrivingNoiseDb() +
        ", co2GKm=" + getCo2GKm() +
        ", pollutantClassRelevantEg='" + getPollutantClassRelevantEg() + "'" +
        ", manufacturerAbbreviation='" + getManufacturerAbbreviation() + "'" +
        ", manufacturingNumbercodeTo2='" + getManufacturingNumbercodeTo2() + "'" +
        ", typeKeyNumberD2='" + getTypeKeyNumberD2() + "'" +
        ", checkDigitVehicleIdNumber='" + getCheckDigitVehicleIdNumber() + "'" +
        ", typeOfConstruction='" + getTypeOfConstruction() + "'" +
        ", designationVehicleClassBodywork='" + getDesignationVehicleClassBodywork() + "'" +
        ", dateToK='" + getDateToK() + "'" +
        ", technicallyPermissibleMaxAxleLoad=" + getTechnicallyPermissibleMaxAxleLoad() +
        ", axis1Tech=" + getAxis1Tech() +
        ", axis2Tech=" + getAxis2Tech() +
        ", axis3Tech=" + getAxis3Tech() +
        ", maximumPermissibleAxleLoadMemberStateKg=" + getMaximumPermissibleAxleLoadMemberStateKg() +
        ", axis1Ms=" + getAxis1Ms() +
        ", axis2Ms=" + getAxis2Ms() +
        ", axis3Ms=" + getAxis3Ms() +
        ", numberDriveAxles=" + getNumberDriveAxles() +
        ", codeToP3='" + getCodeToP3() + "'" +
        ", codeToR='" + getCodeToR() + "'" +
        ", volumeTankTankersCcm=" + getVolumeTankTankersCcm() +
        ", verticalLoadKg=" + getVerticalLoadKg() +
        ", designationNationalEmissionClass='" + getDesignationNationalEmissionClass() + "'" +
        ", tyres='" + getTyres() + "'" +
        ", onAxis1=" + getOnAxis1() +
        ", onAxis2=" + getOnAxis2() +
        ", onAxis3=" + getOnAxis3() +
        ", registrationCertificateNumberPartII='" + getRegistrationCertificateNumberPartII() + "'" +
        ", featuresOfTheTypeApproval='" + getFeaturesOfTheTypeApproval() + "'" +
        ", lengthMm=" + getLengthMm() +
        ", widthMm=" + getWidthMm() +
        ", heightMm=" + getHeightMm() +
        ", otherEndorsements='" + getOtherEndorsements() + "'" +
        ", remarksAndExceptions='" + getRemarksAndExceptions() + "'" +
        ", numberDoors=" + getNumberDoors() +
        ", horsepower=" + getHorsepower() +
        ", draft=" + getDraft() +
        ", mileageDate='" + getMileageDate() + "'" +
        ", mileage=" + getMileage() +
        ", lastGeneralInspection='" + getLastGeneralInspection() + "'" +
        ", finalEmissionsTest='" + getFinalEmissionsTest() + "'" +
        ", nextEmissionsTest='" + getNextEmissionsTest() + "'" +
        ", nextGeneralInspection='" + getNextGeneralInspection() + "'" +
        ", frameType='" + getFrameType() + "'" +
        ", frameMaterial='" + getFrameMaterial() + "'" +
        ", circuit='" + getCircuit() + "'" +
        ", wheelSize=" + getWheelSize() +
        ", frameHeight=" + getFrameHeight() +
        ", color='" + getColor() + "'" +
        ", frameNo='" + getFrameNo() + "'" +
        ", manufacturer='" + getManufacturer() + "'" +
        ", originalYardNumberIIYardNumber='" + getOriginalYardNumberIIYardNumber() + "'" +
        ", mainBuildingMaterial='" + getMainBuildingMaterial() + "'" +
        ", previousLicensePlate='" + getPreviousLicensePlate() + "'" +
        ", engineNumber='" + getEngineNumber() + "'" +
        ", engineManufacturers='" + getEngineManufacturers() + "'" +
        ", engineMakeType='" + getEngineMakeType() + "'" +
        ", driveType='" + getDriveType() + "'" +
        ", zDriveNumber='" + getzDriveNumber() + "'" +
        ", idCardIssuedDate='" + getIdCardIssuedDate() + "'" +
        ", carLicensePlates='" + getCarLicensePlates() + "'" +
        "}";
  }
}
