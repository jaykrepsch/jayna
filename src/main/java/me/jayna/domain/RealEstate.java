package me.jayna.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import me.jayna.domain.enumeration.DesignTypeClass;
import me.jayna.domain.enumeration.Direction;
import me.jayna.domain.enumeration.HeatingType;
import me.jayna.domain.enumeration.RoofType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RealEstate.
 */
@Entity
@Table(name = "real_estate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedEntityGraph(name = "RealEstate.graph", attributeNodes = {
    @NamedAttributeNode(value = "groupType", subgraph = "groupType-subgraph"),
    @NamedAttributeNode(value = "contactRealestates", subgraph = "contactRealestates-subgraph"),
    @NamedAttributeNode(value = "contractRealestates", subgraph = "contractRealestates-subgraph"),
    @NamedAttributeNode(value = "financeaccountRealestates", subgraph = "financeaccountRealestates-subgraph"),
    @NamedAttributeNode(value = "mobilityRealestates", subgraph = "mobilityRealestates-subgraph"),
    @NamedAttributeNode(value = "realEstateRealEstates", subgraph = "realEstateRealEstates-subgraph"),
}, subgraphs = {
    @NamedSubgraph(name = "groupType-subgraph", attributeNodes = {
        @NamedAttributeNode(value = "subCategoryGroup", subgraph = "subCategoryGroup-subgraph")
    }),
    @NamedSubgraph(name = "subCategoryGroup-subgraph", attributeNodes = {
        @NamedAttributeNode("subCategory")
    }),
    @NamedSubgraph(name = "contactRealestates-subgraph", attributeNodes = {
        @NamedAttributeNode("contact")
    }),
    @NamedSubgraph(name = "contractRealestates-subgraph", attributeNodes = {
        @NamedAttributeNode("contract")
    }),
    @NamedSubgraph(name = "financeaccountRealestates-subgraph", attributeNodes = {
        @NamedAttributeNode("financeaccount")
    }),
    @NamedSubgraph(name = "mobilityRealestates-subgraph", attributeNodes = {
        @NamedAttributeNode("mobility")
    }),
    @NamedSubgraph(name = "realEstateRealEstates-subgraph", attributeNodes = {
        @NamedAttributeNode("relatedRealEstate")
    })
})

public class RealEstate extends AbstractCategoryEntity {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @Column(name = "street")
  private String street;

  @Column(name = "street_number")
  private String streetNumber;

  @Column(name = "postal_code")
  private String postalCode;

  @Column(name = "city")
  private String city;

  @Column(name = "state")
  private String state;

  @Column(name = "country")
  private String country;

  @Column(name = "property_width")
  private Double propertyWidth;

  @Column(name = "property_length")
  private Double propertyLength;

  @Column(name = "property_area")
  private Double propertyArea;

  @Column(name = "area")
  private Double area;

  @Column(name = "no_houses")
  private Integer noHouses;

  @Column(name = "construction_year")
  private Integer constructionYear;

  @Column(name = "design_type")
  private String designType;

  @Enumerated(EnumType.STRING)
  @Column(name = "design_type_class")
  private DesignTypeClass designTypeClass;

  @Column(name = "built_up_area")
  private Double builtUpArea;

  @Column(name = "sealt_area")
  private Double sealtArea;

  @Column(name = "undeveloped_area")
  private Double undevelopedArea;

  @Column(name = "no_smoke_detectors")
  private Integer noSmokeDetectors;

  @Column(name = "no_units")
  private Integer noUnits;

  @Column(name = "no_floors")
  private Integer noFloors;

  @Column(name = "no_power_outlets")
  private Integer noPowerOutlets;

  @Column(name = "no_network_sockets")
  private Integer noNetworkSockets;

  @Column(name = "no_light_switches")
  private Integer noLightSwitches;

  @Column(name = "no_antennas")
  private Integer noAntennas;

  @Column(name = "no_shutter_switches")
  private Integer noShutterSwitches;

  @Column(name = "no_radiators")
  private Integer noRadiators;

  @Column(name = "no_parkings")
  private Integer noParkings;

  @Column(name = "no_garages")
  private Integer noGarages;

  @Column(name = "no_carports")
  private Integer noCarports;

  @Column(name = "no_windows")
  private Integer noWindows;

  @Column(name = "window_area")
  private Double windowArea;

  @Column(name = "no_elevators")
  private Integer noElevators;

  @Column(name = "corridor_area")
  private Double corridorArea;

  @Column(name = "no_basement_rooms")
  private Integer noBasementRooms;

  @Column(name = "monument_protected")
  private Boolean monumentProtected;

  @Enumerated(EnumType.STRING)
  @Column(name = "heating_type")
  private HeatingType heatingType;

  @Column(name = "roof_pitch")
  private Double roofPitch;

  @Enumerated(EnumType.STRING)
  @Column(name = "roof_type")
  private RoofType roofType;

  @Enumerated(EnumType.STRING)
  @Column(name = "gable_alignment")
  private Direction gableAlignment;

  @Column(name = "roof_area")
  private Double roofArea;

  @Column(name = "longitude")
  private String longitude;

  @Column(name = "latitude")
  private String latitude;

  @OneToMany(mappedBy = "realestate", fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "realestate" }, allowSetters = true)
  private Set<ContractRealestate> contractRealestates = new HashSet<>();

  @OneToMany(mappedBy = "realestate", fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "realestate" }, allowSetters = true)
  private Set<ContactRealestate> contactRealestates = new HashSet<>();

  @OneToMany(mappedBy = "realestate", fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "realestate" }, allowSetters = true)
  private Set<FinanceAccountRealestate> financeaccountRealestates = new HashSet<>();

  @OneToMany(mappedBy = "realestate", fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "realestate" }, allowSetters = true)
  private Set<MobilityRealestate> mobilityRealestates = new HashSet<>();

  @OneToMany(mappedBy = "realestate", fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "realestate" }, allowSetters = true)
  private Set<RealEstateRealEstate> realEstateRealEstates = new HashSet<>();

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public RealEstate id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getStreet() {
    return this.street;
  }

  public RealEstate street(String street) {
    this.setStreet(street);
    return this;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getStreetNumber() {
    return this.streetNumber;
  }

  public RealEstate streetNumber(String streetNumber) {
    this.setStreetNumber(streetNumber);
    return this;
  }

  public void setStreetNumber(String streetNumber) {
    this.streetNumber = streetNumber;
  }

  public String getPostalCode() {
    return this.postalCode;
  }

  public RealEstate postalCode(String postalCode) {
    this.setPostalCode(postalCode);
    return this;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCity() {
    return this.city;
  }

  public RealEstate city(String city) {
    this.setCity(city);
    return this;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return this.state;
  }

  public RealEstate state(String state) {
    this.setState(state);
    return this;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCountry() {
    return this.country;
  }

  public RealEstate country(String country) {
    this.setCountry(country);
    return this;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public Double getPropertyWidth() {
    return this.propertyWidth;
  }

  public RealEstate propertyWidth(Double propertyWidth) {
    this.setPropertyWidth(propertyWidth);
    return this;
  }

  public void setPropertyWidth(Double propertyWidth) {
    this.propertyWidth = propertyWidth;
  }

  public Double getPropertyLength() {
    return this.propertyLength;
  }

  public RealEstate propertyLength(Double propertyLength) {
    this.setPropertyLength(propertyLength);
    return this;
  }

  public void setPropertyLength(Double propertyLength) {
    this.propertyLength = propertyLength;
  }

  public Double getPropertyArea() {
    return this.propertyArea;
  }

  public RealEstate propertyArea(Double propertyArea) {
    this.setPropertyArea(propertyArea);
    return this;
  }

  public void setPropertyArea(Double propertyArea) {
    this.propertyArea = propertyArea;
  }

  public Double getArea() {
    return this.area;
  }

  public RealEstate area(Double area) {
    this.setArea(area);
    return this;
  }

  public void setArea(Double area) {
    this.area = area;
  }

  public Integer getNoHouses() {
    return this.noHouses;
  }

  public RealEstate noHouses(Integer noHouses) {
    this.setNoHouses(noHouses);
    return this;
  }

  public void setNoHouses(Integer noHouses) {
    this.noHouses = noHouses;
  }

  public Integer getConstructionYear() {
    return this.constructionYear;
  }

  public RealEstate constructionYear(Integer constructionYear) {
    this.setConstructionYear(constructionYear);
    return this;
  }

  public void setConstructionYear(Integer constructionYear) {
    this.constructionYear = constructionYear;
  }

  public String getDesignType() {
    return this.designType;
  }

  public RealEstate designType(String designType) {
    this.setDesignType(designType);
    return this;
  }

  public void setDesignType(String designType) {
    this.designType = designType;
  }

  public DesignTypeClass getDesignTypeClass() {
    return this.designTypeClass;
  }

  public RealEstate designTypeClass(DesignTypeClass designTypeClass) {
    this.setDesignTypeClass(designTypeClass);
    return this;
  }

  public void setDesignTypeClass(DesignTypeClass designTypeClass) {
    this.designTypeClass = designTypeClass;
  }

  public Double getBuiltUpArea() {
    return this.builtUpArea;
  }

  public RealEstate builtUpArea(Double builtUpArea) {
    this.setBuiltUpArea(builtUpArea);
    return this;
  }

  public void setBuiltUpArea(Double builtUpArea) {
    this.builtUpArea = builtUpArea;
  }

  public Double getSealtArea() {
    return this.sealtArea;
  }

  public RealEstate sealtArea(Double sealtArea) {
    this.setSealtArea(sealtArea);
    return this;
  }

  public void setSealtArea(Double sealtArea) {
    this.sealtArea = sealtArea;
  }

  public Double getUndevelopedArea() {
    return this.undevelopedArea;
  }

  public RealEstate undevelopedArea(Double undevelopedArea) {
    this.setUndevelopedArea(undevelopedArea);
    return this;
  }

  public void setUndevelopedArea(Double undevelopedArea) {
    this.undevelopedArea = undevelopedArea;
  }

  public Integer getNoSmokeDetectors() {
    return this.noSmokeDetectors;
  }

  public RealEstate noSmokeDetectors(Integer noSmokeDetectors) {
    this.setNoSmokeDetectors(noSmokeDetectors);
    return this;
  }

  public void setNoSmokeDetectors(Integer noSmokeDetectors) {
    this.noSmokeDetectors = noSmokeDetectors;
  }

  public Integer getNoUnits() {
    return this.noUnits;
  }

  public RealEstate noUnits(Integer noUnits) {
    this.setNoUnits(noUnits);
    return this;
  }

  public void setNoUnits(Integer noUnits) {
    this.noUnits = noUnits;
  }

  public Integer getNoFloors() {
    return this.noFloors;
  }

  public RealEstate noFloors(Integer noFloors) {
    this.setNoFloors(noFloors);
    return this;
  }

  public void setNoFloors(Integer noFloors) {
    this.noFloors = noFloors;
  }

  public Integer getNoPowerOutlets() {
    return this.noPowerOutlets;
  }

  public RealEstate noPowerOutlets(Integer noPowerOutlets) {
    this.setNoPowerOutlets(noPowerOutlets);
    return this;
  }

  public void setNoPowerOutlets(Integer noPowerOutlets) {
    this.noPowerOutlets = noPowerOutlets;
  }

  public Integer getNoNetworkSockets() {
    return this.noNetworkSockets;
  }

  public RealEstate noNetworkSockets(Integer noNetworkSockets) {
    this.setNoNetworkSockets(noNetworkSockets);
    return this;
  }

  public void setNoNetworkSockets(Integer noNetworkSockets) {
    this.noNetworkSockets = noNetworkSockets;
  }

  public Integer getNoLightSwitches() {
    return this.noLightSwitches;
  }

  public RealEstate noLightSwitches(Integer noLightSwitches) {
    this.setNoLightSwitches(noLightSwitches);
    return this;
  }

  public void setNoLightSwitches(Integer noLightSwitches) {
    this.noLightSwitches = noLightSwitches;
  }

  public Integer getNoAntennas() {
    return this.noAntennas;
  }

  public RealEstate noAntennas(Integer noAntennas) {
    this.setNoAntennas(noAntennas);
    return this;
  }

  public void setNoAntennas(Integer noAntennas) {
    this.noAntennas = noAntennas;
  }

  public Integer getNoShutterSwitches() {
    return this.noShutterSwitches;
  }

  public RealEstate noShutterSwitches(Integer noShutterSwitches) {
    this.setNoShutterSwitches(noShutterSwitches);
    return this;
  }

  public void setNoShutterSwitches(Integer noShutterSwitches) {
    this.noShutterSwitches = noShutterSwitches;
  }

  public Integer getNoRadiators() {
    return this.noRadiators;
  }

  public RealEstate noRadiators(Integer noRadiators) {
    this.setNoRadiators(noRadiators);
    return this;
  }

  public void setNoRadiators(Integer noRadiators) {
    this.noRadiators = noRadiators;
  }

  public Integer getNoParkings() {
    return this.noParkings;
  }

  public RealEstate noParkings(Integer noParkings) {
    this.setNoParkings(noParkings);
    return this;
  }

  public void setNoParkings(Integer noParkings) {
    this.noParkings = noParkings;
  }

  public Integer getNoGarages() {
    return this.noGarages;
  }

  public RealEstate noGarages(Integer noGarages) {
    this.setNoGarages(noGarages);
    return this;
  }

  public void setNoGarages(Integer noGarages) {
    this.noGarages = noGarages;
  }

  public Integer getNoCarports() {
    return this.noCarports;
  }

  public RealEstate noCarports(Integer noCarports) {
    this.setNoCarports(noCarports);
    return this;
  }

  public void setNoCarports(Integer noCarports) {
    this.noCarports = noCarports;
  }

  public Integer getNoWindows() {
    return this.noWindows;
  }

  public RealEstate noWindows(Integer noWindows) {
    this.setNoWindows(noWindows);
    return this;
  }

  public void setNoWindows(Integer noWindows) {
    this.noWindows = noWindows;
  }

  public Double getWindowArea() {
    return this.windowArea;
  }

  public RealEstate windowArea(Double windowArea) {
    this.setWindowArea(windowArea);
    return this;
  }

  public void setWindowArea(Double windowArea) {
    this.windowArea = windowArea;
  }

  public Integer getNoElevators() {
    return this.noElevators;
  }

  public RealEstate noElevators(Integer noElevators) {
    this.setNoElevators(noElevators);
    return this;
  }

  public void setNoElevators(Integer noElevators) {
    this.noElevators = noElevators;
  }

  public Double getCorridorArea() {
    return this.corridorArea;
  }

  public RealEstate corridorArea(Double corridorArea) {
    this.setCorridorArea(corridorArea);
    return this;
  }

  public void setCorridorArea(Double corridorArea) {
    this.corridorArea = corridorArea;
  }

  public Integer getNoBasementRooms() {
    return this.noBasementRooms;
  }

  public RealEstate noBasementRooms(Integer noBasementRooms) {
    this.setNoBasementRooms(noBasementRooms);
    return this;
  }

  public void setNoBasementRooms(Integer noBasementRooms) {
    this.noBasementRooms = noBasementRooms;
  }

  public Boolean getMonumentProtected() {
    return this.monumentProtected;
  }

  public RealEstate monumentProtected(Boolean monumentProtected) {
    this.setMonumentProtected(monumentProtected);
    return this;
  }

  public void setMonumentProtected(Boolean monumentProtected) {
    this.monumentProtected = monumentProtected;
  }

  public HeatingType getHeatingType() {
    return this.heatingType;
  }

  public RealEstate heatingType(HeatingType heatingType) {
    this.setHeatingType(heatingType);
    return this;
  }

  public void setHeatingType(HeatingType heatingType) {
    this.heatingType = heatingType;
  }

  public Double getRoofPitch() {
    return this.roofPitch;
  }

  public RealEstate roofPitch(Double roofPitch) {
    this.setRoofPitch(roofPitch);
    return this;
  }

  public void setRoofPitch(Double roofPitch) {
    this.roofPitch = roofPitch;
  }

  public RoofType getRoofType() {
    return this.roofType;
  }

  public RealEstate roofType(RoofType roofType) {
    this.setRoofType(roofType);
    return this;
  }

  public void setRoofType(RoofType roofType) {
    this.roofType = roofType;
  }

  public Direction getGableAlignment() {
    return this.gableAlignment;
  }

  public RealEstate gableAlignment(Direction gableAlignment) {
    this.setGableAlignment(gableAlignment);
    return this;
  }

  public void setGableAlignment(Direction gableAlignment) {
    this.gableAlignment = gableAlignment;
  }

  public Double getRoofArea() {
    return this.roofArea;
  }

  public RealEstate roofArea(Double roofArea) {
    this.setRoofArea(roofArea);
    return this;
  }

  public void setRoofArea(Double roofArea) {
    this.roofArea = roofArea;
  }

  public String getLongitude() {
    return this.longitude;
  }

  public RealEstate longitude(String longitude) {
    this.setLongitude(longitude);
    return this;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getLatitude() {
    return this.latitude;
  }

  public RealEstate latitude(String latitude) {
    this.setLatitude(latitude);
    return this;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public Set<ContractRealestate> getContractRealestates() {
    return this.contractRealestates;
  }

  public void setContractRealestates(
      Set<ContractRealestate> contractRealestates) {
    if (this.contractRealestates != null) {
      this.contractRealestates.forEach(i -> i.setRealestate(null));
    }
    if (contractRealestates != null) {
      contractRealestates.forEach(i -> i.setRealestate(this));
    }
    this.contractRealestates = contractRealestates;
  }

  public RealEstate contractRealestates(
      Set<ContractRealestate> contractRealestates) {
    this.setContractRealestates(contractRealestates);
    return this;
  }

  public RealEstate addContractRealestates(
      ContractRealestate contractRealestate) {
    this.contractRealestates.add(contractRealestate);
    contractRealestate.setRealestate(this);
    return this;
  }

  public RealEstate removeContractRealestates(
      ContractRealestate contractRealestate) {
    this.contractRealestates.remove(contractRealestate);
    contractRealestate.setRealestate(null);
    return this;
  }

  public Set<ContactRealestate> getContactRealestates() {
    return this.contactRealestates;
  }

  public void setContactRealestates(
      Set<ContactRealestate> contactRealestates) {
    if (this.contactRealestates != null) {
      this.contactRealestates.forEach(i -> i.setRealestate(null));
    }
    if (contactRealestates != null) {
      contactRealestates.forEach(i -> i.setRealestate(this));
    }
    this.contactRealestates = contactRealestates;
  }

  public RealEstate contactRealestates(
      Set<ContactRealestate> contactRealestates) {
    this.setContactRealestates(contactRealestates);
    return this;
  }

  public RealEstate addContactRealestates(
      ContactRealestate contactRealestate) {
    this.contactRealestates.add(contactRealestate);
    contactRealestate.setRealestate(this);
    return this;
  }

  public RealEstate removeContactRealestates(
      ContactRealestate contactRealestate) {
    this.contactRealestates.remove(contactRealestate);
    contactRealestate.setRealestate(null);
    return this;
  }

  public Set<FinanceAccountRealestate> getFinanceaccountRealestates() {
    return this.financeaccountRealestates;
  }

  public void setFinanceaccountRealestates(
      Set<FinanceAccountRealestate> financeaccountRealestates) {
    if (this.financeaccountRealestates != null) {
      this.financeaccountRealestates.forEach(i -> i.setRealestate(null));
    }
    if (financeaccountRealestates != null) {
      financeaccountRealestates.forEach(i -> i.setRealestate(this));
    }
    this.financeaccountRealestates = financeaccountRealestates;
  }

  public RealEstate financeaccountRealestates(
      Set<FinanceAccountRealestate> financeaccountRealestates) {
    this.setFinanceaccountRealestates(financeaccountRealestates);
    return this;
  }

  public RealEstate addFinanceaccountMobilities(
      FinanceAccountRealestate financeaccountRealestate) {
    this.financeaccountRealestates.add(financeaccountRealestate);
    financeaccountRealestate.setRealestate(this);
    return this;
  }

  public RealEstate removeFinanceaccountMobilities(
      FinanceAccountRealestate financeaccountRealestate) {
    this.financeaccountRealestates.remove(financeaccountRealestate);
    financeaccountRealestate.setRealestate(null);
    return this;
  }

  public Set<MobilityRealestate> getMobilityRealestates() {
    return this.mobilityRealestates;
  }

  public void setMobilityRealestates(
      Set<MobilityRealestate> mobilityRealestates) {
    if (this.mobilityRealestates != null) {
      this.mobilityRealestates.forEach(i -> i.setRealestate(null));
    }
    if (mobilityRealestates != null) {
      mobilityRealestates.forEach(i -> i.setRealestate(this));
    }
    this.mobilityRealestates = mobilityRealestates;
  }

  public RealEstate mobilityRealestates(
      Set<MobilityRealestate> mobilityRealestates) {
    this.setMobilityRealestates(mobilityRealestates);
    return this;
  }

  public RealEstate addMobilityMobilities(
      MobilityRealestate mobilityRealestate) {
    this.mobilityRealestates.add(mobilityRealestate);
    mobilityRealestate.setRealestate(this);
    return this;
  }

  public RealEstate removeMobilityMobilities(
      MobilityRealestate mobilityRealestate) {
    this.mobilityRealestates.remove(mobilityRealestate);
    mobilityRealestate.setRealestate(null);
    return this;
  }

  public Set<RealEstateRealEstate> getRealEstateRealEstates() {
    return this.realEstateRealEstates;
  }

  public void setRealEstateRealEstates(
      Set<RealEstateRealEstate> realEstateRealEstates) {
    if (this.realEstateRealEstates != null) {
      this.realEstateRealEstates.forEach(i -> i.setRealestate(null));
    }
    if (realEstateRealEstates != null) {
      realEstateRealEstates.forEach(i -> i.setRealestate(this));
    }
    this.realEstateRealEstates = realEstateRealEstates;
  }

  public RealEstate realEstateRealEstates(
      Set<RealEstateRealEstate> realEstateRealEstates) {
    this.setRealEstateRealEstates(realEstateRealEstates);
    return this;
  }

  public RealEstate addRealEstateRealEstates(
      RealEstateRealEstate realEstateRealEstate) {
    this.realEstateRealEstates.add(realEstateRealEstate);
    realEstateRealEstate.setRealestate(this);
    return this;
  }

  public RealEstate removeRealEstateRealEstates(
      RealEstateRealEstate realEstateRealEstate) {
    this.realEstateRealEstates.remove(realEstateRealEstate);
    realEstateRealEstate.setRealestate(null);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
  // setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof RealEstate)) {
      return false;
    }
    return id != null && id.equals(((RealEstate) o).id);
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
    return "RealEstate{" +
        "id=" + getId() +
        ", street='" + getStreet() + "'" +
        ", streetNumber='" + getStreetNumber() + "'" +
        ", postalCode='" + getPostalCode() + "'" +
        ", city='" + getCity() + "'" +
        ", state='" + getState() + "'" +
        ", country='" + getCountry() + "'" +
        ", propertyWidth=" + getPropertyWidth() +
        ", propertyLength=" + getPropertyLength() +
        ", propertyArea=" + getPropertyArea() +
        ", area=" + getArea() +
        ", noHouses=" + getNoHouses() +
        ", constructionYear=" + getConstructionYear() +
        ", designType='" + getDesignType() + "'" +
        ", designTypeClass='" + getDesignTypeClass() + "'" +
        ", builtUpArea=" + getBuiltUpArea() +
        ", sealtArea=" + getSealtArea() +
        ", undevelopedArea=" + getUndevelopedArea() +
        ", noSmokeDetectors=" + getNoSmokeDetectors() +
        ", noUnits=" + getNoUnits() +
        ", noFloors=" + getNoFloors() +
        ", noPowerOutlets=" + getNoPowerOutlets() +
        ", noNetworkSockets=" + getNoNetworkSockets() +
        ", noLightSwitches=" + getNoLightSwitches() +
        ", noAntennas=" + getNoAntennas() +
        ", noShutterSwitches=" + getNoShutterSwitches() +
        ", noRadiators=" + getNoRadiators() +
        ", noParkings=" + getNoParkings() +
        ", noGarages=" + getNoGarages() +
        ", noCarports=" + getNoCarports() +
        ", noWindows=" + getNoWindows() +
        ", windowArea=" + getWindowArea() +
        ", noElevators=" + getNoElevators() +
        ", corridorArea=" + getCorridorArea() +
        ", noBasementRooms=" + getNoBasementRooms() +
        ", monumentProtected='" + getMonumentProtected() + "'" +
        ", heatingType='" + getHeatingType() + "'" +
        ", roofPitch=" + getRoofPitch() +
        ", roofType='" + getRoofType() + "'" +
        ", gableAlignment='" + getGableAlignment() + "'" +
        ", roofArea=" + getRoofArea() +
        ", longitude='" + getLongitude() + "'" +
        ", latitude='" + getLatitude() + "'" +
        "}";
  }
}
