package me.jayna.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import me.jayna.domain.enumeration.DesignTypeClass;
import me.jayna.domain.enumeration.Direction;
import me.jayna.domain.enumeration.HeatingType;
import me.jayna.domain.enumeration.RoofType;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link me.jayna.domain.RealEstate} entity. This class
 * is used
 * in {@link me.jayna.web.rest.RealEstateResource} to receive all the po ssible
 * filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /real-estates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific
 * {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class RealEstateCriteria implements Serializable, Criteria {

  /**
   * Class for filtering DesignTypeClass
   */
  public static class DesignTypeClassFilter extends Filter<DesignTypeClass> {

    public DesignTypeClassFilter() {
    }

    public DesignTypeClassFilter(DesignTypeClassFilter filter) {
      super(filter);
    }

    @Override
    public DesignTypeClassFilter copy() {
      return new DesignTypeClassFilter(this);
    }
  }

  /**
   * Class for filtering HeatingType
   */
  public static class HeatingTypeFilter extends Filter<HeatingType> {

    public HeatingTypeFilter() {
    }

    public HeatingTypeFilter(HeatingTypeFilter filter) {
      super(filter);
    }

    @Override
    public HeatingTypeFilter copy() {
      return new HeatingTypeFilter(this);
    }
  }

  /**
   * Class for filtering RoofType
   */
  public static class RoofTypeFilter extends Filter<RoofType> {

    public RoofTypeFilter() {
    }

    public RoofTypeFilter(RoofTypeFilter filter) {
      super(filter);
    }

    @Override
    public RoofTypeFilter copy() {
      return new RoofTypeFilter(this);
    }
  }

  /**
   * Class for filtering Direction
   */
  public static class DirectionFilter extends Filter<Direction> {

    public DirectionFilter() {
    }

    public DirectionFilter(DirectionFilter filter) {
      super(filter);
    }

    @Override
    public DirectionFilter copy() {
      return new DirectionFilter(this);
    }
  }

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter street;

  private StringFilter streetNumber;

  private StringFilter postalCode;

  private StringFilter city;

  private StringFilter state;

  private StringFilter country;

  private DoubleFilter propertyWidth;

  private DoubleFilter propertyLength;

  private DoubleFilter propertyArea;

  private DoubleFilter area;

  private IntegerFilter noHouses;

  private IntegerFilter constructionYear;

  private StringFilter designType;

  private DesignTypeClassFilter designTypeClass;

  private DoubleFilter builtUpArea;

  private DoubleFilter sealtArea;

  private DoubleFilter undevelopedArea;

  private IntegerFilter noSmokeDetectors;

  private IntegerFilter noUnits;

  private IntegerFilter noFloors;

  private IntegerFilter noPowerOutlets;

  private IntegerFilter noNetworkSockets;

  private IntegerFilter noLightSwitches;

  private IntegerFilter noAntennas;

  private IntegerFilter noShutterSwitches;

  private IntegerFilter noRadiators;

  private IntegerFilter noParkings;

  private IntegerFilter noGarages;

  private IntegerFilter noCarports;

  private IntegerFilter noWindows;

  private DoubleFilter windowArea;

  private IntegerFilter noElevators;

  private DoubleFilter corridorArea;

  private IntegerFilter noBasementRooms;

  private BooleanFilter monumentProtected;

  private HeatingTypeFilter heatingType;

  private DoubleFilter roofPitch;

  private RoofTypeFilter roofType;

  private DirectionFilter gableAlignment;

  private DoubleFilter roofArea;

  private StringFilter longitude;

  private StringFilter latitude;

  private StringFilter comment;

  private LongFilter applicationUserId;

  private LongFilter groupTypeId;

  private LongFilter contractRealestatesId;

  private Boolean distinct;

  public RealEstateCriteria() {
  }

  public RealEstateCriteria(RealEstateCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.street = other.street == null ? null : other.street.copy();
    this.streetNumber = other.streetNumber == null ? null : other.streetNumber.copy();
    this.postalCode = other.postalCode == null ? null : other.postalCode.copy();
    this.city = other.city == null ? null : other.city.copy();
    this.state = other.state == null ? null : other.state.copy();
    this.country = other.country == null ? null : other.country.copy();
    this.propertyWidth = other.propertyWidth == null ? null : other.propertyWidth.copy();
    this.propertyLength = other.propertyLength == null ? null : other.propertyLength.copy();
    this.propertyArea = other.propertyArea == null ? null : other.propertyArea.copy();
    this.area = other.area == null ? null : other.area.copy();
    this.noHouses = other.noHouses == null ? null : other.noHouses.copy();
    this.constructionYear = other.constructionYear == null ? null : other.constructionYear.copy();
    this.designType = other.designType == null ? null : other.designType.copy();
    this.designTypeClass = other.designTypeClass == null ? null : other.designTypeClass.copy();
    this.builtUpArea = other.builtUpArea == null ? null : other.builtUpArea.copy();
    this.sealtArea = other.sealtArea == null ? null : other.sealtArea.copy();
    this.undevelopedArea = other.undevelopedArea == null ? null : other.undevelopedArea.copy();
    this.noSmokeDetectors = other.noSmokeDetectors == null ? null : other.noSmokeDetectors.copy();
    this.noUnits = other.noUnits == null ? null : other.noUnits.copy();
    this.noFloors = other.noFloors == null ? null : other.noFloors.copy();
    this.noPowerOutlets = other.noPowerOutlets == null ? null : other.noPowerOutlets.copy();
    this.noNetworkSockets = other.noNetworkSockets == null ? null : other.noNetworkSockets.copy();
    this.noLightSwitches = other.noLightSwitches == null ? null : other.noLightSwitches.copy();
    this.noAntennas = other.noAntennas == null ? null : other.noAntennas.copy();
    this.noShutterSwitches = other.noShutterSwitches == null ? null : other.noShutterSwitches.copy();
    this.noRadiators = other.noRadiators == null ? null : other.noRadiators.copy();
    this.noParkings = other.noParkings == null ? null : other.noParkings.copy();
    this.noGarages = other.noGarages == null ? null : other.noGarages.copy();
    this.noCarports = other.noCarports == null ? null : other.noCarports.copy();
    this.noWindows = other.noWindows == null ? null : other.noWindows.copy();
    this.windowArea = other.windowArea == null ? null : other.windowArea.copy();
    this.noElevators = other.noElevators == null ? null : other.noElevators.copy();
    this.corridorArea = other.corridorArea == null ? null : other.corridorArea.copy();
    this.noBasementRooms = other.noBasementRooms == null ? null : other.noBasementRooms.copy();
    this.monumentProtected = other.monumentProtected == null ? null : other.monumentProtected.copy();
    this.heatingType = other.heatingType == null ? null : other.heatingType.copy();
    this.roofPitch = other.roofPitch == null ? null : other.roofPitch.copy();
    this.roofType = other.roofType == null ? null : other.roofType.copy();
    this.gableAlignment = other.gableAlignment == null ? null : other.gableAlignment.copy();
    this.roofArea = other.roofArea == null ? null : other.roofArea.copy();
    this.longitude = other.longitude == null ? null : other.longitude.copy();
    this.latitude = other.latitude == null ? null : other.latitude.copy();
    this.comment = other.comment == null ? null : other.comment.copy();
    this.applicationUserId = other.applicationUserId == null ? null : other.applicationUserId.copy();
    this.groupTypeId = other.groupTypeId == null ? null : other.groupTypeId.copy();
    this.contractRealestatesId = other.contractRealestatesId == null
        ? null
        : other.contractRealestatesId.copy();
    this.distinct = other.distinct;
  }

  @Override
  public RealEstateCriteria copy() {
    return new RealEstateCriteria(this);
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

  public StringFilter getStreet() {
    return street;
  }

  public StringFilter street() {
    if (street == null) {
      street = new StringFilter();
    }
    return street;
  }

  public void setStreet(StringFilter street) {
    this.street = street;
  }

  public StringFilter getStreetNumber() {
    return streetNumber;
  }

  public StringFilter streetNumber() {
    if (streetNumber == null) {
      streetNumber = new StringFilter();
    }
    return streetNumber;
  }

  public void setStreetNumber(StringFilter streetNumber) {
    this.streetNumber = streetNumber;
  }

  public StringFilter getPostalCode() {
    return postalCode;
  }

  public StringFilter postalCode() {
    if (postalCode == null) {
      postalCode = new StringFilter();
    }
    return postalCode;
  }

  public void setPostalCode(StringFilter postalCode) {
    this.postalCode = postalCode;
  }

  public StringFilter getCity() {
    return city;
  }

  public StringFilter city() {
    if (city == null) {
      city = new StringFilter();
    }
    return city;
  }

  public void setCity(StringFilter city) {
    this.city = city;
  }

  public StringFilter getState() {
    return state;
  }

  public StringFilter state() {
    if (state == null) {
      state = new StringFilter();
    }
    return state;
  }

  public void setState(StringFilter state) {
    this.state = state;
  }

  public StringFilter getCountry() {
    return country;
  }

  public StringFilter country() {
    if (country == null) {
      country = new StringFilter();
    }
    return country;
  }

  public void setCountry(StringFilter country) {
    this.country = country;
  }

  public DoubleFilter getPropertyWidth() {
    return propertyWidth;
  }

  public DoubleFilter propertyWidth() {
    if (propertyWidth == null) {
      propertyWidth = new DoubleFilter();
    }
    return propertyWidth;
  }

  public void setPropertyWidth(DoubleFilter propertyWidth) {
    this.propertyWidth = propertyWidth;
  }

  public DoubleFilter getPropertyLength() {
    return propertyLength;
  }

  public DoubleFilter propertyLength() {
    if (propertyLength == null) {
      propertyLength = new DoubleFilter();
    }
    return propertyLength;
  }

  public void setPropertyLength(DoubleFilter propertyLength) {
    this.propertyLength = propertyLength;
  }

  public DoubleFilter getPropertyArea() {
    return propertyArea;
  }

  public DoubleFilter propertyArea() {
    if (propertyArea == null) {
      propertyArea = new DoubleFilter();
    }
    return propertyArea;
  }

  public void setPropertyArea(DoubleFilter propertyArea) {
    this.propertyArea = propertyArea;
  }

  public DoubleFilter getArea() {
    return area;
  }

  public DoubleFilter area() {
    if (area == null) {
      area = new DoubleFilter();
    }
    return area;
  }

  public void setArea(DoubleFilter area) {
    this.area = area;
  }

  public IntegerFilter getNoHouses() {
    return noHouses;
  }

  public IntegerFilter noHouses() {
    if (noHouses == null) {
      noHouses = new IntegerFilter();
    }
    return noHouses;
  }

  public void setNoHouses(IntegerFilter noHouses) {
    this.noHouses = noHouses;
  }

  public IntegerFilter getConstructionYear() {
    return constructionYear;
  }

  public IntegerFilter constructionYear() {
    if (constructionYear == null) {
      constructionYear = new IntegerFilter();
    }
    return constructionYear;
  }

  public void setConstructionYear(IntegerFilter constructionYear) {
    this.constructionYear = constructionYear;
  }

  public StringFilter getDesignType() {
    return designType;
  }

  public StringFilter designType() {
    if (designType == null) {
      designType = new StringFilter();
    }
    return designType;
  }

  public void setDesignType(StringFilter designType) {
    this.designType = designType;
  }

  public DesignTypeClassFilter getDesignTypeClass() {
    return designTypeClass;
  }

  public DesignTypeClassFilter designTypeClass() {
    if (designTypeClass == null) {
      designTypeClass = new DesignTypeClassFilter();
    }
    return designTypeClass;
  }

  public void setDesignTypeClass(DesignTypeClassFilter designTypeClass) {
    this.designTypeClass = designTypeClass;
  }

  public DoubleFilter getBuiltUpArea() {
    return builtUpArea;
  }

  public DoubleFilter builtUpArea() {
    if (builtUpArea == null) {
      builtUpArea = new DoubleFilter();
    }
    return builtUpArea;
  }

  public void setBuiltUpArea(DoubleFilter builtUpArea) {
    this.builtUpArea = builtUpArea;
  }

  public DoubleFilter getSealtArea() {
    return sealtArea;
  }

  public DoubleFilter sealtArea() {
    if (sealtArea == null) {
      sealtArea = new DoubleFilter();
    }
    return sealtArea;
  }

  public void setSealtArea(DoubleFilter sealtArea) {
    this.sealtArea = sealtArea;
  }

  public DoubleFilter getUndevelopedArea() {
    return undevelopedArea;
  }

  public DoubleFilter undevelopedArea() {
    if (undevelopedArea == null) {
      undevelopedArea = new DoubleFilter();
    }
    return undevelopedArea;
  }

  public void setUndevelopedArea(DoubleFilter undevelopedArea) {
    this.undevelopedArea = undevelopedArea;
  }

  public IntegerFilter getNoSmokeDetectors() {
    return noSmokeDetectors;
  }

  public IntegerFilter noSmokeDetectors() {
    if (noSmokeDetectors == null) {
      noSmokeDetectors = new IntegerFilter();
    }
    return noSmokeDetectors;
  }

  public void setNoSmokeDetectors(IntegerFilter noSmokeDetectors) {
    this.noSmokeDetectors = noSmokeDetectors;
  }

  public IntegerFilter getNoUnits() {
    return noUnits;
  }

  public IntegerFilter noUnits() {
    if (noUnits == null) {
      noUnits = new IntegerFilter();
    }
    return noUnits;
  }

  public void setNoUnits(IntegerFilter noUnits) {
    this.noUnits = noUnits;
  }

  public IntegerFilter getNoFloors() {
    return noFloors;
  }

  public IntegerFilter noFloors() {
    if (noFloors == null) {
      noFloors = new IntegerFilter();
    }
    return noFloors;
  }

  public void setNoFloors(IntegerFilter noFloors) {
    this.noFloors = noFloors;
  }

  public IntegerFilter getNoPowerOutlets() {
    return noPowerOutlets;
  }

  public IntegerFilter noPowerOutlets() {
    if (noPowerOutlets == null) {
      noPowerOutlets = new IntegerFilter();
    }
    return noPowerOutlets;
  }

  public void setNoPowerOutlets(IntegerFilter noPowerOutlets) {
    this.noPowerOutlets = noPowerOutlets;
  }

  public IntegerFilter getNoNetworkSockets() {
    return noNetworkSockets;
  }

  public IntegerFilter noNetworkSockets() {
    if (noNetworkSockets == null) {
      noNetworkSockets = new IntegerFilter();
    }
    return noNetworkSockets;
  }

  public void setNoNetworkSockets(IntegerFilter noNetworkSockets) {
    this.noNetworkSockets = noNetworkSockets;
  }

  public IntegerFilter getNoLightSwitches() {
    return noLightSwitches;
  }

  public IntegerFilter noLightSwitches() {
    if (noLightSwitches == null) {
      noLightSwitches = new IntegerFilter();
    }
    return noLightSwitches;
  }

  public void setNoLightSwitches(IntegerFilter noLightSwitches) {
    this.noLightSwitches = noLightSwitches;
  }

  public IntegerFilter getNoAntennas() {
    return noAntennas;
  }

  public IntegerFilter noAntennas() {
    if (noAntennas == null) {
      noAntennas = new IntegerFilter();
    }
    return noAntennas;
  }

  public void setNoAntennas(IntegerFilter noAntennas) {
    this.noAntennas = noAntennas;
  }

  public IntegerFilter getNoShutterSwitches() {
    return noShutterSwitches;
  }

  public IntegerFilter noShutterSwitches() {
    if (noShutterSwitches == null) {
      noShutterSwitches = new IntegerFilter();
    }
    return noShutterSwitches;
  }

  public void setNoShutterSwitches(IntegerFilter noShutterSwitches) {
    this.noShutterSwitches = noShutterSwitches;
  }

  public IntegerFilter getNoRadiators() {
    return noRadiators;
  }

  public IntegerFilter noRadiators() {
    if (noRadiators == null) {
      noRadiators = new IntegerFilter();
    }
    return noRadiators;
  }

  public void setNoRadiators(IntegerFilter noRadiators) {
    this.noRadiators = noRadiators;
  }

  public IntegerFilter getNoParkings() {
    return noParkings;
  }

  public IntegerFilter noParkings() {
    if (noParkings == null) {
      noParkings = new IntegerFilter();
    }
    return noParkings;
  }

  public void setNoParkings(IntegerFilter noParkings) {
    this.noParkings = noParkings;
  }

  public IntegerFilter getNoGarages() {
    return noGarages;
  }

  public IntegerFilter noGarages() {
    if (noGarages == null) {
      noGarages = new IntegerFilter();
    }
    return noGarages;
  }

  public void setNoGarages(IntegerFilter noGarages) {
    this.noGarages = noGarages;
  }

  public IntegerFilter getNoCarports() {
    return noCarports;
  }

  public IntegerFilter noCarports() {
    if (noCarports == null) {
      noCarports = new IntegerFilter();
    }
    return noCarports;
  }

  public void setNoCarports(IntegerFilter noCarports) {
    this.noCarports = noCarports;
  }

  public IntegerFilter getNoWindows() {
    return noWindows;
  }

  public IntegerFilter noWindows() {
    if (noWindows == null) {
      noWindows = new IntegerFilter();
    }
    return noWindows;
  }

  public void setNoWindows(IntegerFilter noWindows) {
    this.noWindows = noWindows;
  }

  public DoubleFilter getWindowArea() {
    return windowArea;
  }

  public DoubleFilter windowArea() {
    if (windowArea == null) {
      windowArea = new DoubleFilter();
    }
    return windowArea;
  }

  public void setWindowArea(DoubleFilter windowArea) {
    this.windowArea = windowArea;
  }

  public IntegerFilter getNoElevators() {
    return noElevators;
  }

  public IntegerFilter noElevators() {
    if (noElevators == null) {
      noElevators = new IntegerFilter();
    }
    return noElevators;
  }

  public void setNoElevators(IntegerFilter noElevators) {
    this.noElevators = noElevators;
  }

  public DoubleFilter getCorridorArea() {
    return corridorArea;
  }

  public DoubleFilter corridorArea() {
    if (corridorArea == null) {
      corridorArea = new DoubleFilter();
    }
    return corridorArea;
  }

  public void setCorridorArea(DoubleFilter corridorArea) {
    this.corridorArea = corridorArea;
  }

  public IntegerFilter getNoBasementRooms() {
    return noBasementRooms;
  }

  public IntegerFilter noBasementRooms() {
    if (noBasementRooms == null) {
      noBasementRooms = new IntegerFilter();
    }
    return noBasementRooms;
  }

  public void setNoBasementRooms(IntegerFilter noBasementRooms) {
    this.noBasementRooms = noBasementRooms;
  }

  public BooleanFilter getMonumentProtected() {
    return monumentProtected;
  }

  public BooleanFilter monumentProtected() {
    if (monumentProtected == null) {
      monumentProtected = new BooleanFilter();
    }
    return monumentProtected;
  }

  public void setMonumentProtected(BooleanFilter monumentProtected) {
    this.monumentProtected = monumentProtected;
  }

  public HeatingTypeFilter getHeatingType() {
    return heatingType;
  }

  public HeatingTypeFilter heatingType() {
    if (heatingType == null) {
      heatingType = new HeatingTypeFilter();
    }
    return heatingType;
  }

  public void setHeatingType(HeatingTypeFilter heatingType) {
    this.heatingType = heatingType;
  }

  public DoubleFilter getRoofPitch() {
    return roofPitch;
  }

  public DoubleFilter roofPitch() {
    if (roofPitch == null) {
      roofPitch = new DoubleFilter();
    }
    return roofPitch;
  }

  public void setRoofPitch(DoubleFilter roofPitch) {
    this.roofPitch = roofPitch;
  }

  public RoofTypeFilter getRoofType() {
    return roofType;
  }

  public RoofTypeFilter roofType() {
    if (roofType == null) {
      roofType = new RoofTypeFilter();
    }
    return roofType;
  }

  public void setRoofType(RoofTypeFilter roofType) {
    this.roofType = roofType;
  }

  public DirectionFilter getGableAlignment() {
    return gableAlignment;
  }

  public DirectionFilter gableAlignment() {
    if (gableAlignment == null) {
      gableAlignment = new DirectionFilter();
    }
    return gableAlignment;
  }

  public void setGableAlignment(DirectionFilter gableAlignment) {
    this.gableAlignment = gableAlignment;
  }

  public DoubleFilter getRoofArea() {
    return roofArea;
  }

  public DoubleFilter roofArea() {
    if (roofArea == null) {
      roofArea = new DoubleFilter();
    }
    return roofArea;
  }

  public void setRoofArea(DoubleFilter roofArea) {
    this.roofArea = roofArea;
  }

  public StringFilter getLongitude() {
    return longitude;
  }

  public StringFilter longitude() {
    if (longitude == null) {
      longitude = new StringFilter();
    }
    return longitude;
  }

  public void setLongitude(StringFilter longitude) {
    this.longitude = longitude;
  }

  public StringFilter getLatitude() {
    return latitude;
  }

  public StringFilter latitude() {
    if (latitude == null) {
      latitude = new StringFilter();
    }
    return latitude;
  }

  public void setLatitude(StringFilter latitude) {
    this.latitude = latitude;
  }

  public StringFilter getComment() {
    return comment;
  }

  public StringFilter comment() {
    if (comment == null) {
      comment = new StringFilter();
    }
    return comment;
  }

  public void setComment(StringFilter comment) {
    this.comment = comment;
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

  public LongFilter getGroupTypeId() {
    return groupTypeId;
  }

  public LongFilter groupTypeId() {
    if (groupTypeId == null) {
      groupTypeId = new LongFilter();
    }
    return groupTypeId;
  }

  public void setGroupTypeId(LongFilter groupTypeId) {
    this.groupTypeId = groupTypeId;
  }

  public LongFilter getContractRealestatesId() {
    return contractRealestatesId;
  }

  public LongFilter contractRealestatesId() {
    if (contractRealestatesId == null) {
      contractRealestatesId = new LongFilter();
    }
    return contractRealestatesId;
  }

  public void setContractRealestatesId(LongFilter contractRealestatesId) {
    this.contractRealestatesId = contractRealestatesId;
  }

  public Boolean getDistinct() {
    return distinct;
  }

  public void setDistinct(Boolean distinct) {
    this.distinct = distinct;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final RealEstateCriteria that = (RealEstateCriteria) o;
    return (Objects.equals(id, that.id) &&
        Objects.equals(street, that.street) &&
        Objects.equals(streetNumber, that.streetNumber) &&
        Objects.equals(postalCode, that.postalCode) &&
        Objects.equals(city, that.city) &&
        Objects.equals(state, that.state) &&
        Objects.equals(country, that.country) &&
        Objects.equals(propertyWidth, that.propertyWidth) &&
        Objects.equals(propertyLength, that.propertyLength) &&
        Objects.equals(propertyArea, that.propertyArea) &&
        Objects.equals(area, that.area) &&
        Objects.equals(noHouses, that.noHouses) &&
        Objects.equals(constructionYear, that.constructionYear) &&
        Objects.equals(designType, that.designType) &&
        Objects.equals(designTypeClass, that.designTypeClass) &&
        Objects.equals(builtUpArea, that.builtUpArea) &&
        Objects.equals(sealtArea, that.sealtArea) &&
        Objects.equals(undevelopedArea, that.undevelopedArea) &&
        Objects.equals(noSmokeDetectors, that.noSmokeDetectors) &&
        Objects.equals(noUnits, that.noUnits) &&
        Objects.equals(noFloors, that.noFloors) &&
        Objects.equals(noPowerOutlets, that.noPowerOutlets) &&
        Objects.equals(noNetworkSockets, that.noNetworkSockets) &&
        Objects.equals(noLightSwitches, that.noLightSwitches) &&
        Objects.equals(noAntennas, that.noAntennas) &&
        Objects.equals(noShutterSwitches, that.noShutterSwitches) &&
        Objects.equals(noRadiators, that.noRadiators) &&
        Objects.equals(noParkings, that.noParkings) &&
        Objects.equals(noGarages, that.noGarages) &&
        Objects.equals(noCarports, that.noCarports) &&
        Objects.equals(noWindows, that.noWindows) &&
        Objects.equals(windowArea, that.windowArea) &&
        Objects.equals(noElevators, that.noElevators) &&
        Objects.equals(corridorArea, that.corridorArea) &&
        Objects.equals(noBasementRooms, that.noBasementRooms) &&
        Objects.equals(monumentProtected, that.monumentProtected) &&
        Objects.equals(heatingType, that.heatingType) &&
        Objects.equals(roofPitch, that.roofPitch) &&
        Objects.equals(roofType, that.roofType) &&
        Objects.equals(gableAlignment, that.gableAlignment) &&
        Objects.equals(roofArea, that.roofArea) &&
        Objects.equals(longitude, that.longitude) &&
        Objects.equals(latitude, that.latitude) &&
        Objects.equals(comment, that.comment) &&
        Objects.equals(applicationUserId, that.applicationUserId) &&
        Objects.equals(groupTypeId, that.groupTypeId) &&
        Objects.equals(contractRealestatesId, that.contractRealestatesId) &&
        Objects.equals(distinct, that.distinct));
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        street,
        streetNumber,
        postalCode,
        city,
        state,
        country,
        propertyWidth,
        propertyLength,
        propertyArea,
        area,
        noHouses,
        constructionYear,
        designType,
        designTypeClass,
        builtUpArea,
        sealtArea,
        undevelopedArea,
        noSmokeDetectors,
        noUnits,
        noFloors,
        noPowerOutlets,
        noNetworkSockets,
        noLightSwitches,
        noAntennas,
        noShutterSwitches,
        noRadiators,
        noParkings,
        noGarages,
        noCarports,
        noWindows,
        windowArea,
        noElevators,
        corridorArea,
        noBasementRooms,
        monumentProtected,
        heatingType,
        roofPitch,
        roofType,
        gableAlignment,
        roofArea,
        longitude,
        latitude,
        comment,
        applicationUserId,
        groupTypeId,
        contractRealestatesId,
        distinct);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "RealEstateCriteria{" +
        (id != null ? "id=" + id + ", " : "") +
        (street != null ? "street=" + street + ", " : "") +
        (streetNumber != null ? "streetNumber=" + streetNumber + ", " : "") +
        (postalCode != null ? "postalCode=" + postalCode + ", " : "") +
        (city != null ? "city=" + city + ", " : "") +
        (state != null ? "state=" + state + ", " : "") +
        (country != null ? "country=" + country + ", " : "") +
        (propertyWidth != null ? "propertyWidth=" + propertyWidth + ", " : "") +
        (propertyLength != null ? "propertyLength=" + propertyLength + ", " : "") +
        (propertyArea != null ? "propertyArea=" + propertyArea + ", " : "") +
        (area != null ? "area=" + area + ", " : "") +
        (noHouses != null ? "noHouses=" + noHouses + ", " : "") +
        (constructionYear != null ? "constructionYear=" + constructionYear + ", " : "") +
        (designType != null ? "designType=" + designType + ", " : "") +
        (designTypeClass != null ? "designTypeClass=" + designTypeClass + ", " : "") +
        (builtUpArea != null ? "builtUpArea=" + builtUpArea + ", " : "") +
        (sealtArea != null ? "sealtArea=" + sealtArea + ", " : "") +
        (undevelopedArea != null ? "undevelopedArea=" + undevelopedArea + ", " : "") +
        (noSmokeDetectors != null ? "noSmokeDetectors=" + noSmokeDetectors + ", " : "") +
        (noUnits != null ? "noUnits=" + noUnits + ", " : "") +
        (noFloors != null ? "noFloors=" + noFloors + ", " : "") +
        (noPowerOutlets != null ? "noPowerOutlets=" + noPowerOutlets + ", " : "") +
        (noNetworkSockets != null ? "noNetworkSockets=" + noNetworkSockets + ", " : "") +
        (noLightSwitches != null ? "noLightSwitches=" + noLightSwitches + ", " : "") +
        (noAntennas != null ? "noAntennas=" + noAntennas + ", " : "") +
        (noShutterSwitches != null ? "noShutterSwitches=" + noShutterSwitches + ", " : "") +
        (noRadiators != null ? "noRadiators=" + noRadiators + ", " : "") +
        (noParkings != null ? "noParkings=" + noParkings + ", " : "") +
        (noGarages != null ? "noGarages=" + noGarages + ", " : "") +
        (noCarports != null ? "noCarports=" + noCarports + ", " : "") +
        (noWindows != null ? "noWindows=" + noWindows + ", " : "") +
        (windowArea != null ? "windowArea=" + windowArea + ", " : "") +
        (noElevators != null ? "noElevators=" + noElevators + ", " : "") +
        (corridorArea != null ? "corridorArea=" + corridorArea + ", " : "") +
        (noBasementRooms != null ? "noBasementRooms=" + noBasementRooms + ", " : "") +
        (monumentProtected != null ? "monumentProtected=" + monumentProtected + ", " : "") +
        (heatingType != null ? "heatingType=" + heatingType + ", " : "") +
        (roofPitch != null ? "roofPitch=" + roofPitch + ", " : "") +
        (roofType != null ? "roofType=" + roofType + ", " : "") +
        (gableAlignment != null ? "gableAlignment=" + gableAlignment + ", " : "") +
        (roofArea != null ? "roofArea=" + roofArea + ", " : "") +
        (longitude != null ? "longitude=" + longitude + ", " : "") +
        (latitude != null ? "latitude=" + latitude + ", " : "") +
        (comment != null ? "comment=" + comment + ", " : "") +
        (applicationUserId != null ? "applicationUserId=" + applicationUserId + ", " : "") +
        (groupTypeId != null ? "groupTypeId=" + groupTypeId + ", " : "") +
        (contractRealestatesId != null ? "contractRealestatesId=" + contractRealestatesId + ", " : "") +
        (distinct != null ? "distinct=" + distinct + ", " : "") +
        "}";
  }
}
