package me.jayna.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import me.jayna.IntegrationTest;
import me.jayna.domain.ApplicationUser;
import me.jayna.domain.ContractRealestate;
import me.jayna.domain.GroupType;
import me.jayna.domain.RealEstate;
import me.jayna.domain.enumeration.DesignTypeClass;
import me.jayna.domain.enumeration.Direction;
import me.jayna.domain.enumeration.HeatingType;
import me.jayna.domain.enumeration.RoofType;
import me.jayna.repository.RealEstateRepository;
import me.jayna.service.criteria.RealEstateCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RealEstateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RealEstateResourceIT {

  private static final String DEFAULT_STREET = "AAAAAAAAAA";
  private static final String UPDATED_STREET = "BBBBBBBBBB";

  private static final String DEFAULT_STREET_NUMBER = "AAAAAAAAAA";
  private static final String UPDATED_STREET_NUMBER = "BBBBBBBBBB";

  private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
  private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

  private static final String DEFAULT_CITY = "AAAAAAAAAA";
  private static final String UPDATED_CITY = "BBBBBBBBBB";

  private static final String DEFAULT_STATE = "AAAAAAAAAA";
  private static final String UPDATED_STATE = "BBBBBBBBBB";

  private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
  private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

  private static final Double DEFAULT_PROPERTY_WIDTH = 1D;
  private static final Double UPDATED_PROPERTY_WIDTH = 2D;
  private static final Double SMALLER_PROPERTY_WIDTH = 1D - 1D;

  private static final Double DEFAULT_PROPERTY_LENGTH = 1D;
  private static final Double UPDATED_PROPERTY_LENGTH = 2D;
  private static final Double SMALLER_PROPERTY_LENGTH = 1D - 1D;

  private static final Double DEFAULT_PROPERTY_AREA = 1D;
  private static final Double UPDATED_PROPERTY_AREA = 2D;
  private static final Double SMALLER_PROPERTY_AREA = 1D - 1D;

  private static final Double DEFAULT_AREA = 1D;
  private static final Double UPDATED_AREA = 2D;
  private static final Double SMALLER_AREA = 1D - 1D;

  private static final Integer DEFAULT_NO_HOUSES = 1;
  private static final Integer UPDATED_NO_HOUSES = 2;
  private static final Integer SMALLER_NO_HOUSES = 1 - 1;

  private static final Integer DEFAULT_CONSTRUCTION_YEAR = 1;
  private static final Integer UPDATED_CONSTRUCTION_YEAR = 2;
  private static final Integer SMALLER_CONSTRUCTION_YEAR = 1 - 1;

  private static final String DEFAULT_DESIGN_TYPE = "AAAAAAAAAA";
  private static final String UPDATED_DESIGN_TYPE = "BBBBBBBBBB";

  private static final DesignTypeClass DEFAULT_DESIGN_TYPE_CLASS = DesignTypeClass.BAK1;
  private static final DesignTypeClass UPDATED_DESIGN_TYPE_CLASS = DesignTypeClass.BAK2;

  private static final Double DEFAULT_BUILT_UP_AREA = 1D;
  private static final Double UPDATED_BUILT_UP_AREA = 2D;
  private static final Double SMALLER_BUILT_UP_AREA = 1D - 1D;

  private static final Double DEFAULT_SEALT_AREA = 1D;
  private static final Double UPDATED_SEALT_AREA = 2D;
  private static final Double SMALLER_SEALT_AREA = 1D - 1D;

  private static final Double DEFAULT_UNDEVELOPED_AREA = 1D;
  private static final Double UPDATED_UNDEVELOPED_AREA = 2D;
  private static final Double SMALLER_UNDEVELOPED_AREA = 1D - 1D;

  private static final Integer DEFAULT_NO_SMOKE_DETECTORS = 1;
  private static final Integer UPDATED_NO_SMOKE_DETECTORS = 2;
  private static final Integer SMALLER_NO_SMOKE_DETECTORS = 1 - 1;

  private static final Integer DEFAULT_NO_UNITS = 1;
  private static final Integer UPDATED_NO_UNITS = 2;
  private static final Integer SMALLER_NO_UNITS = 1 - 1;

  private static final Integer DEFAULT_NO_FLOORS = 1;
  private static final Integer UPDATED_NO_FLOORS = 2;
  private static final Integer SMALLER_NO_FLOORS = 1 - 1;

  private static final Integer DEFAULT_NO_POWER_OUTLETS = 1;
  private static final Integer UPDATED_NO_POWER_OUTLETS = 2;
  private static final Integer SMALLER_NO_POWER_OUTLETS = 1 - 1;

  private static final Integer DEFAULT_NO_NETWORK_SOCKETS = 1;
  private static final Integer UPDATED_NO_NETWORK_SOCKETS = 2;
  private static final Integer SMALLER_NO_NETWORK_SOCKETS = 1 - 1;

  private static final Integer DEFAULT_NO_LIGHT_SWITCHES = 1;
  private static final Integer UPDATED_NO_LIGHT_SWITCHES = 2;
  private static final Integer SMALLER_NO_LIGHT_SWITCHES = 1 - 1;

  private static final Integer DEFAULT_NO_ANTENNAS = 1;
  private static final Integer UPDATED_NO_ANTENNAS = 2;
  private static final Integer SMALLER_NO_ANTENNAS = 1 - 1;

  private static final Integer DEFAULT_NO_SHUTTER_SWITCHES = 1;
  private static final Integer UPDATED_NO_SHUTTER_SWITCHES = 2;
  private static final Integer SMALLER_NO_SHUTTER_SWITCHES = 1 - 1;

  private static final Integer DEFAULT_NO_RADIATORS = 1;
  private static final Integer UPDATED_NO_RADIATORS = 2;
  private static final Integer SMALLER_NO_RADIATORS = 1 - 1;

  private static final Integer DEFAULT_NO_PARKINGS = 1;
  private static final Integer UPDATED_NO_PARKINGS = 2;
  private static final Integer SMALLER_NO_PARKINGS = 1 - 1;

  private static final Integer DEFAULT_NO_GARAGES = 1;
  private static final Integer UPDATED_NO_GARAGES = 2;
  private static final Integer SMALLER_NO_GARAGES = 1 - 1;

  private static final Integer DEFAULT_NO_CARPORTS = 1;
  private static final Integer UPDATED_NO_CARPORTS = 2;
  private static final Integer SMALLER_NO_CARPORTS = 1 - 1;

  private static final Integer DEFAULT_NO_WINDOWS = 1;
  private static final Integer UPDATED_NO_WINDOWS = 2;
  private static final Integer SMALLER_NO_WINDOWS = 1 - 1;

  private static final Double DEFAULT_WINDOW_AREA = 1D;
  private static final Double UPDATED_WINDOW_AREA = 2D;
  private static final Double SMALLER_WINDOW_AREA = 1D - 1D;

  private static final Integer DEFAULT_NO_ELEVATORS = 1;
  private static final Integer UPDATED_NO_ELEVATORS = 2;
  private static final Integer SMALLER_NO_ELEVATORS = 1 - 1;

  private static final Double DEFAULT_CORRIDOR_AREA = 1D;
  private static final Double UPDATED_CORRIDOR_AREA = 2D;
  private static final Double SMALLER_CORRIDOR_AREA = 1D - 1D;

  private static final Integer DEFAULT_NO_BASEMENT_ROOMS = 1;
  private static final Integer UPDATED_NO_BASEMENT_ROOMS = 2;
  private static final Integer SMALLER_NO_BASEMENT_ROOMS = 1 - 1;

  private static final Boolean DEFAULT_MONUMENT_PROTECTED = false;
  private static final Boolean UPDATED_MONUMENT_PROTECTED = true;

  private static final HeatingType DEFAULT_HEATING_TYPE = HeatingType.GAS;
  private static final HeatingType UPDATED_HEATING_TYPE = HeatingType.PELLET;

  private static final Double DEFAULT_ROOF_PITCH = 1D;
  private static final Double UPDATED_ROOF_PITCH = 2D;
  private static final Double SMALLER_ROOF_PITCH = 1D - 1D;

  private static final RoofType DEFAULT_ROOF_TYPE = RoofType.FLAT;
  private static final RoofType UPDATED_ROOF_TYPE = RoofType.HIPPED;

  private static final Direction DEFAULT_GABLE_ALIGNMENT = Direction.NORTH;
  private static final Direction UPDATED_GABLE_ALIGNMENT = Direction.EAST;

  private static final Double DEFAULT_ROOF_AREA = 1D;
  private static final Double UPDATED_ROOF_AREA = 2D;
  private static final Double SMALLER_ROOF_AREA = 1D - 1D;

  private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
  private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

  private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
  private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

  private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
  private static final String UPDATED_COMMENT = "BBBBBBBBBB";

  private static final String ENTITY_API_URL = "/api/real-estates";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(
      random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private RealEstateRepository realEstateRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restRealEstateMockMvc;

  private RealEstate realEstate;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static RealEstate createEntity(EntityManager em) {
    RealEstate realEstate = new RealEstate()
        .street(DEFAULT_STREET)
        .streetNumber(DEFAULT_STREET_NUMBER)
        .postalCode(DEFAULT_POSTAL_CODE)
        .city(DEFAULT_CITY)
        .state(DEFAULT_STATE)
        .country(DEFAULT_COUNTRY)
        .propertyWidth(DEFAULT_PROPERTY_WIDTH)
        .propertyLength(DEFAULT_PROPERTY_LENGTH)
        .propertyArea(DEFAULT_PROPERTY_AREA)
        .area(DEFAULT_AREA)
        .noHouses(DEFAULT_NO_HOUSES)
        .constructionYear(DEFAULT_CONSTRUCTION_YEAR)
        .designType(DEFAULT_DESIGN_TYPE)
        .designTypeClass(DEFAULT_DESIGN_TYPE_CLASS)
        .builtUpArea(DEFAULT_BUILT_UP_AREA)
        .sealtArea(DEFAULT_SEALT_AREA)
        .undevelopedArea(DEFAULT_UNDEVELOPED_AREA)
        .noSmokeDetectors(DEFAULT_NO_SMOKE_DETECTORS)
        .noUnits(DEFAULT_NO_UNITS)
        .noFloors(DEFAULT_NO_FLOORS)
        .noPowerOutlets(DEFAULT_NO_POWER_OUTLETS)
        .noNetworkSockets(DEFAULT_NO_NETWORK_SOCKETS)
        .noLightSwitches(DEFAULT_NO_LIGHT_SWITCHES)
        .noAntennas(DEFAULT_NO_ANTENNAS)
        .noShutterSwitches(DEFAULT_NO_SHUTTER_SWITCHES)
        .noRadiators(DEFAULT_NO_RADIATORS)
        .noParkings(DEFAULT_NO_PARKINGS)
        .noGarages(DEFAULT_NO_GARAGES)
        .noCarports(DEFAULT_NO_CARPORTS)
        .noWindows(DEFAULT_NO_WINDOWS)
        .windowArea(DEFAULT_WINDOW_AREA)
        .noElevators(DEFAULT_NO_ELEVATORS)
        .corridorArea(DEFAULT_CORRIDOR_AREA)
        .noBasementRooms(DEFAULT_NO_BASEMENT_ROOMS)
        .monumentProtected(DEFAULT_MONUMENT_PROTECTED)
        .heatingType(DEFAULT_HEATING_TYPE)
        .roofPitch(DEFAULT_ROOF_PITCH)
        .roofType(DEFAULT_ROOF_TYPE)
        .gableAlignment(DEFAULT_GABLE_ALIGNMENT)
        .roofArea(DEFAULT_ROOF_AREA)
        .longitude(DEFAULT_LONGITUDE)
        .latitude(DEFAULT_LATITUDE);
    return realEstate;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static RealEstate createUpdatedEntity(EntityManager em) {
    RealEstate realEstate = new RealEstate()
        .street(UPDATED_STREET)
        .streetNumber(UPDATED_STREET_NUMBER)
        .postalCode(UPDATED_POSTAL_CODE)
        .city(UPDATED_CITY)
        .state(UPDATED_STATE)
        .country(UPDATED_COUNTRY)
        .propertyWidth(UPDATED_PROPERTY_WIDTH)
        .propertyLength(UPDATED_PROPERTY_LENGTH)
        .propertyArea(UPDATED_PROPERTY_AREA)
        .area(UPDATED_AREA)
        .noHouses(UPDATED_NO_HOUSES)
        .constructionYear(UPDATED_CONSTRUCTION_YEAR)
        .designType(UPDATED_DESIGN_TYPE)
        .designTypeClass(UPDATED_DESIGN_TYPE_CLASS)
        .builtUpArea(UPDATED_BUILT_UP_AREA)
        .sealtArea(UPDATED_SEALT_AREA)
        .undevelopedArea(UPDATED_UNDEVELOPED_AREA)
        .noSmokeDetectors(UPDATED_NO_SMOKE_DETECTORS)
        .noUnits(UPDATED_NO_UNITS)
        .noFloors(UPDATED_NO_FLOORS)
        .noPowerOutlets(UPDATED_NO_POWER_OUTLETS)
        .noNetworkSockets(UPDATED_NO_NETWORK_SOCKETS)
        .noLightSwitches(UPDATED_NO_LIGHT_SWITCHES)
        .noAntennas(UPDATED_NO_ANTENNAS)
        .noShutterSwitches(UPDATED_NO_SHUTTER_SWITCHES)
        .noRadiators(UPDATED_NO_RADIATORS)
        .noParkings(UPDATED_NO_PARKINGS)
        .noGarages(UPDATED_NO_GARAGES)
        .noCarports(UPDATED_NO_CARPORTS)
        .noWindows(UPDATED_NO_WINDOWS)
        .windowArea(UPDATED_WINDOW_AREA)
        .noElevators(UPDATED_NO_ELEVATORS)
        .corridorArea(UPDATED_CORRIDOR_AREA)
        .noBasementRooms(UPDATED_NO_BASEMENT_ROOMS)
        .monumentProtected(UPDATED_MONUMENT_PROTECTED)
        .heatingType(UPDATED_HEATING_TYPE)
        .roofPitch(UPDATED_ROOF_PITCH)
        .roofType(UPDATED_ROOF_TYPE)
        .gableAlignment(UPDATED_GABLE_ALIGNMENT)
        .roofArea(UPDATED_ROOF_AREA)
        .longitude(UPDATED_LONGITUDE)
        .latitude(UPDATED_LATITUDE);
    return realEstate;
  }

  @BeforeEach
  public void initTest() {
    realEstate = createEntity(em);
  }

  @Test
  @Transactional
  void createRealEstate() throws Exception {
    int databaseSizeBeforeCreate = realEstateRepository.findAll().size();
    // Create the RealEstate
    restRealEstateMockMvc
        .perform(
            post(ENTITY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(realEstate)))
        .andExpect(status().isCreated());

    // Validate the RealEstate in the database
    List<RealEstate> realEstateList = realEstateRepository.findAll();
    assertThat(realEstateList).hasSize(databaseSizeBeforeCreate + 1);
    RealEstate testRealEstate = realEstateList.get(realEstateList.size() - 1);
    assertThat(testRealEstate.getStreet()).isEqualTo(DEFAULT_STREET);
    assertThat(testRealEstate.getStreetNumber())
        .isEqualTo(DEFAULT_STREET_NUMBER);
    assertThat(testRealEstate.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
    assertThat(testRealEstate.getCity()).isEqualTo(DEFAULT_CITY);
    assertThat(testRealEstate.getState()).isEqualTo(DEFAULT_STATE);
    assertThat(testRealEstate.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    assertThat(testRealEstate.getPropertyWidth())
        .isEqualTo(DEFAULT_PROPERTY_WIDTH);
    assertThat(testRealEstate.getPropertyLength())
        .isEqualTo(DEFAULT_PROPERTY_LENGTH);
    assertThat(testRealEstate.getPropertyArea())
        .isEqualTo(DEFAULT_PROPERTY_AREA);
    assertThat(testRealEstate.getArea()).isEqualTo(DEFAULT_AREA);
    assertThat(testRealEstate.getNoHouses()).isEqualTo(DEFAULT_NO_HOUSES);
    assertThat(testRealEstate.getConstructionYear())
        .isEqualTo(DEFAULT_CONSTRUCTION_YEAR);
    assertThat(testRealEstate.getDesignType()).isEqualTo(DEFAULT_DESIGN_TYPE);
    assertThat(testRealEstate.getDesignTypeClass())
        .isEqualTo(DEFAULT_DESIGN_TYPE_CLASS);
    assertThat(testRealEstate.getBuiltUpArea())
        .isEqualTo(DEFAULT_BUILT_UP_AREA);
    assertThat(testRealEstate.getSealtArea()).isEqualTo(DEFAULT_SEALT_AREA);
    assertThat(testRealEstate.getUndevelopedArea())
        .isEqualTo(DEFAULT_UNDEVELOPED_AREA);
    assertThat(testRealEstate.getNoSmokeDetectors())
        .isEqualTo(DEFAULT_NO_SMOKE_DETECTORS);
    assertThat(testRealEstate.getNoUnits()).isEqualTo(DEFAULT_NO_UNITS);
    assertThat(testRealEstate.getNoFloors()).isEqualTo(DEFAULT_NO_FLOORS);
    assertThat(testRealEstate.getNoPowerOutlets())
        .isEqualTo(DEFAULT_NO_POWER_OUTLETS);
    assertThat(testRealEstate.getNoNetworkSockets())
        .isEqualTo(DEFAULT_NO_NETWORK_SOCKETS);
    assertThat(testRealEstate.getNoLightSwitches())
        .isEqualTo(DEFAULT_NO_LIGHT_SWITCHES);
    assertThat(testRealEstate.getNoAntennas()).isEqualTo(DEFAULT_NO_ANTENNAS);
    assertThat(testRealEstate.getNoShutterSwitches())
        .isEqualTo(DEFAULT_NO_SHUTTER_SWITCHES);
    assertThat(testRealEstate.getNoRadiators()).isEqualTo(DEFAULT_NO_RADIATORS);
    assertThat(testRealEstate.getNoParkings()).isEqualTo(DEFAULT_NO_PARKINGS);
    assertThat(testRealEstate.getNoGarages()).isEqualTo(DEFAULT_NO_GARAGES);
    assertThat(testRealEstate.getNoCarports()).isEqualTo(DEFAULT_NO_CARPORTS);
    assertThat(testRealEstate.getNoWindows()).isEqualTo(DEFAULT_NO_WINDOWS);
    assertThat(testRealEstate.getWindowArea()).isEqualTo(DEFAULT_WINDOW_AREA);
    assertThat(testRealEstate.getNoElevators()).isEqualTo(DEFAULT_NO_ELEVATORS);
    assertThat(testRealEstate.getCorridorArea())
        .isEqualTo(DEFAULT_CORRIDOR_AREA);
    assertThat(testRealEstate.getNoBasementRooms())
        .isEqualTo(DEFAULT_NO_BASEMENT_ROOMS);
    assertThat(testRealEstate.getMonumentProtected())
        .isEqualTo(DEFAULT_MONUMENT_PROTECTED);
    assertThat(testRealEstate.getHeatingType()).isEqualTo(DEFAULT_HEATING_TYPE);
    assertThat(testRealEstate.getRoofPitch()).isEqualTo(DEFAULT_ROOF_PITCH);
    assertThat(testRealEstate.getRoofType()).isEqualTo(DEFAULT_ROOF_TYPE);
    assertThat(testRealEstate.getGableAlignment())
        .isEqualTo(DEFAULT_GABLE_ALIGNMENT);
    assertThat(testRealEstate.getRoofArea()).isEqualTo(DEFAULT_ROOF_AREA);
    assertThat(testRealEstate.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
    assertThat(testRealEstate.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
    assertThat(testRealEstate.getComment()).isEqualTo(DEFAULT_COMMENT);
  }

  @Test
  @Transactional
  void createRealEstateWithExistingId() throws Exception {
    // Create the RealEstate with an existing ID
    realEstate.setId(1L);

    int databaseSizeBeforeCreate = realEstateRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restRealEstateMockMvc
        .perform(
            post(ENTITY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(realEstate)))
        .andExpect(status().isBadRequest());

    // Validate the RealEstate in the database
    List<RealEstate> realEstateList = realEstateRepository.findAll();
    assertThat(realEstateList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void getAllRealEstates() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList
    restRealEstateMockMvc
        .perform(get(ENTITY_API_URL + "?sort=id,desc"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(
            jsonPath("$.[*].id").value(hasItem(realEstate.getId().intValue())))
        .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
        .andExpect(
            jsonPath("$.[*].streetNumber").value(hasItem(DEFAULT_STREET_NUMBER)))
        .andExpect(
            jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
        .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
        .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
        .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
        .andExpect(
            jsonPath("$.[*].propertyWidth")
                .value(hasItem(DEFAULT_PROPERTY_WIDTH.doubleValue())))
        .andExpect(
            jsonPath("$.[*].propertyLength")
                .value(hasItem(DEFAULT_PROPERTY_LENGTH.doubleValue())))
        .andExpect(
            jsonPath("$.[*].propertyArea")
                .value(hasItem(DEFAULT_PROPERTY_AREA.doubleValue())))
        .andExpect(
            jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA.doubleValue())))
        .andExpect(jsonPath("$.[*].noHouses").value(hasItem(DEFAULT_NO_HOUSES)))
        .andExpect(
            jsonPath("$.[*].constructionYear")
                .value(hasItem(DEFAULT_CONSTRUCTION_YEAR)))
        .andExpect(
            jsonPath("$.[*].designType").value(hasItem(DEFAULT_DESIGN_TYPE)))
        .andExpect(
            jsonPath("$.[*].designTypeClass")
                .value(hasItem(DEFAULT_DESIGN_TYPE_CLASS.toString())))
        .andExpect(
            jsonPath("$.[*].builtUpArea")
                .value(hasItem(DEFAULT_BUILT_UP_AREA.doubleValue())))
        .andExpect(
            jsonPath("$.[*].sealtArea")
                .value(hasItem(DEFAULT_SEALT_AREA.doubleValue())))
        .andExpect(
            jsonPath("$.[*].undevelopedArea")
                .value(hasItem(DEFAULT_UNDEVELOPED_AREA.doubleValue())))
        .andExpect(
            jsonPath("$.[*].noSmokeDetectors")
                .value(hasItem(DEFAULT_NO_SMOKE_DETECTORS)))
        .andExpect(jsonPath("$.[*].noUnits").value(hasItem(DEFAULT_NO_UNITS)))
        .andExpect(jsonPath("$.[*].noFloors").value(hasItem(DEFAULT_NO_FLOORS)))
        .andExpect(
            jsonPath("$.[*].noPowerOutlets")
                .value(hasItem(DEFAULT_NO_POWER_OUTLETS)))
        .andExpect(
            jsonPath("$.[*].noNetworkSockets")
                .value(hasItem(DEFAULT_NO_NETWORK_SOCKETS)))
        .andExpect(
            jsonPath("$.[*].noLightSwitches")
                .value(hasItem(DEFAULT_NO_LIGHT_SWITCHES)))
        .andExpect(
            jsonPath("$.[*].noAntennas").value(hasItem(DEFAULT_NO_ANTENNAS)))
        .andExpect(
            jsonPath("$.[*].noShutterSwitches")
                .value(hasItem(DEFAULT_NO_SHUTTER_SWITCHES)))
        .andExpect(
            jsonPath("$.[*].noRadiators").value(hasItem(DEFAULT_NO_RADIATORS)))
        .andExpect(
            jsonPath("$.[*].noParkings").value(hasItem(DEFAULT_NO_PARKINGS)))
        .andExpect(jsonPath("$.[*].noGarages").value(hasItem(DEFAULT_NO_GARAGES)))
        .andExpect(
            jsonPath("$.[*].noCarports").value(hasItem(DEFAULT_NO_CARPORTS)))
        .andExpect(jsonPath("$.[*].noWindows").value(hasItem(DEFAULT_NO_WINDOWS)))
        .andExpect(
            jsonPath("$.[*].windowArea")
                .value(hasItem(DEFAULT_WINDOW_AREA.doubleValue())))
        .andExpect(
            jsonPath("$.[*].noElevators").value(hasItem(DEFAULT_NO_ELEVATORS)))
        .andExpect(
            jsonPath("$.[*].corridorArea")
                .value(hasItem(DEFAULT_CORRIDOR_AREA.doubleValue())))
        .andExpect(
            jsonPath("$.[*].noBasementRooms")
                .value(hasItem(DEFAULT_NO_BASEMENT_ROOMS)))
        .andExpect(
            jsonPath("$.[*].monumentProtected")
                .value(hasItem(DEFAULT_MONUMENT_PROTECTED.booleanValue())))
        .andExpect(
            jsonPath("$.[*].heatingType")
                .value(hasItem(DEFAULT_HEATING_TYPE.toString())))
        .andExpect(
            jsonPath("$.[*].roofPitch")
                .value(hasItem(DEFAULT_ROOF_PITCH.doubleValue())))
        .andExpect(
            jsonPath("$.[*].roofType").value(hasItem(DEFAULT_ROOF_TYPE.toString())))
        .andExpect(
            jsonPath("$.[*].gableAlignment")
                .value(hasItem(DEFAULT_GABLE_ALIGNMENT.toString())))
        .andExpect(
            jsonPath("$.[*].roofArea")
                .value(hasItem(DEFAULT_ROOF_AREA.doubleValue())))
        .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
        .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
        .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));
  }

  @Test
  @Transactional
  void getRealEstate() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get the realEstate
    restRealEstateMockMvc
        .perform(get(ENTITY_API_URL_ID, realEstate.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.id").value(realEstate.getId().intValue()))
        .andExpect(jsonPath("$.street").value(DEFAULT_STREET))
        .andExpect(jsonPath("$.streetNumber").value(DEFAULT_STREET_NUMBER))
        .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
        .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
        .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
        .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
        .andExpect(
            jsonPath("$.propertyWidth").value(DEFAULT_PROPERTY_WIDTH.doubleValue()))
        .andExpect(
            jsonPath("$.propertyLength")
                .value(DEFAULT_PROPERTY_LENGTH.doubleValue()))
        .andExpect(
            jsonPath("$.propertyArea").value(DEFAULT_PROPERTY_AREA.doubleValue()))
        .andExpect(jsonPath("$.area").value(DEFAULT_AREA.doubleValue()))
        .andExpect(jsonPath("$.noHouses").value(DEFAULT_NO_HOUSES))
        .andExpect(
            jsonPath("$.constructionYear").value(DEFAULT_CONSTRUCTION_YEAR))
        .andExpect(jsonPath("$.designType").value(DEFAULT_DESIGN_TYPE))
        .andExpect(
            jsonPath("$.designTypeClass")
                .value(DEFAULT_DESIGN_TYPE_CLASS.toString()))
        .andExpect(
            jsonPath("$.builtUpArea").value(DEFAULT_BUILT_UP_AREA.doubleValue()))
        .andExpect(
            jsonPath("$.sealtArea").value(DEFAULT_SEALT_AREA.doubleValue()))
        .andExpect(
            jsonPath("$.undevelopedArea")
                .value(DEFAULT_UNDEVELOPED_AREA.doubleValue()))
        .andExpect(
            jsonPath("$.noSmokeDetectors").value(DEFAULT_NO_SMOKE_DETECTORS))
        .andExpect(jsonPath("$.noUnits").value(DEFAULT_NO_UNITS))
        .andExpect(jsonPath("$.noFloors").value(DEFAULT_NO_FLOORS))
        .andExpect(jsonPath("$.noPowerOutlets").value(DEFAULT_NO_POWER_OUTLETS))
        .andExpect(
            jsonPath("$.noNetworkSockets").value(DEFAULT_NO_NETWORK_SOCKETS))
        .andExpect(jsonPath("$.noLightSwitches").value(DEFAULT_NO_LIGHT_SWITCHES))
        .andExpect(jsonPath("$.noAntennas").value(DEFAULT_NO_ANTENNAS))
        .andExpect(
            jsonPath("$.noShutterSwitches").value(DEFAULT_NO_SHUTTER_SWITCHES))
        .andExpect(jsonPath("$.noRadiators").value(DEFAULT_NO_RADIATORS))
        .andExpect(jsonPath("$.noParkings").value(DEFAULT_NO_PARKINGS))
        .andExpect(jsonPath("$.noGarages").value(DEFAULT_NO_GARAGES))
        .andExpect(jsonPath("$.noCarports").value(DEFAULT_NO_CARPORTS))
        .andExpect(jsonPath("$.noWindows").value(DEFAULT_NO_WINDOWS))
        .andExpect(
            jsonPath("$.windowArea").value(DEFAULT_WINDOW_AREA.doubleValue()))
        .andExpect(jsonPath("$.noElevators").value(DEFAULT_NO_ELEVATORS))
        .andExpect(
            jsonPath("$.corridorArea").value(DEFAULT_CORRIDOR_AREA.doubleValue()))
        .andExpect(jsonPath("$.noBasementRooms").value(DEFAULT_NO_BASEMENT_ROOMS))
        .andExpect(
            jsonPath("$.monumentProtected")
                .value(DEFAULT_MONUMENT_PROTECTED.booleanValue()))
        .andExpect(
            jsonPath("$.heatingType").value(DEFAULT_HEATING_TYPE.toString()))
        .andExpect(
            jsonPath("$.roofPitch").value(DEFAULT_ROOF_PITCH.doubleValue()))
        .andExpect(jsonPath("$.roofType").value(DEFAULT_ROOF_TYPE.toString()))
        .andExpect(
            jsonPath("$.gableAlignment").value(DEFAULT_GABLE_ALIGNMENT.toString()))
        .andExpect(jsonPath("$.roofArea").value(DEFAULT_ROOF_AREA.doubleValue()))
        .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE))
        .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE))
        .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT));
  }

  @Test
  @Transactional
  void getRealEstatesByIdFiltering() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    Long id = realEstate.getId();

    defaultRealEstateShouldBeFound("id.equals=" + id);
    defaultRealEstateShouldNotBeFound("id.notEquals=" + id);

    defaultRealEstateShouldBeFound("id.greaterThanOrEqual=" + id);
    defaultRealEstateShouldNotBeFound("id.greaterThan=" + id);

    defaultRealEstateShouldBeFound("id.lessThanOrEqual=" + id);
    defaultRealEstateShouldNotBeFound("id.lessThan=" + id);
  }

  @Test
  @Transactional
  void getAllRealEstatesByStreetIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where street equals to DEFAULT_STREET
    defaultRealEstateShouldBeFound("street.equals=" + DEFAULT_STREET);

    // Get all the realEstateList where street equals to UPDATED_STREET
    defaultRealEstateShouldNotBeFound("street.equals=" + UPDATED_STREET);
  }

  @Test
  @Transactional
  void getAllRealEstatesByStreetIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where street in DEFAULT_STREET or UPDATED_STREET
    defaultRealEstateShouldBeFound(
        "street.in=" + DEFAULT_STREET + "," + UPDATED_STREET);

    // Get all the realEstateList where street equals to UPDATED_STREET
    defaultRealEstateShouldNotBeFound("street.in=" + UPDATED_STREET);
  }

  @Test
  @Transactional
  void getAllRealEstatesByStreetIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where street is not null
    defaultRealEstateShouldBeFound("street.specified=true");

    // Get all the realEstateList where street is null
    defaultRealEstateShouldNotBeFound("street.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByStreetContainsSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where street contains DEFAULT_STREET
    defaultRealEstateShouldBeFound("street.contains=" + DEFAULT_STREET);

    // Get all the realEstateList where street contains UPDATED_STREET
    defaultRealEstateShouldNotBeFound("street.contains=" + UPDATED_STREET);
  }

  @Test
  @Transactional
  void getAllRealEstatesByStreetNotContainsSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where street does not contain DEFAULT_STREET
    defaultRealEstateShouldNotBeFound(
        "street.doesNotContain=" + DEFAULT_STREET);

    // Get all the realEstateList where street does not contain UPDATED_STREET
    defaultRealEstateShouldBeFound("street.doesNotContain=" + UPDATED_STREET);
  }

  @Test
  @Transactional
  void getAllRealEstatesByStreetNumberIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where streetNumber equals to DEFAULT_STREET_NUMBER
    defaultRealEstateShouldBeFound(
        "streetNumber.equals=" + DEFAULT_STREET_NUMBER);

    // Get all the realEstateList where streetNumber equals to UPDATED_STREET_NUMBER
    defaultRealEstateShouldNotBeFound(
        "streetNumber.equals=" + UPDATED_STREET_NUMBER);
  }

  @Test
  @Transactional
  void getAllRealEstatesByStreetNumberIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where streetNumber in DEFAULT_STREET_NUMBER or
    // UPDATED_STREET_NUMBER
    defaultRealEstateShouldBeFound(
        "streetNumber.in=" + DEFAULT_STREET_NUMBER + "," + UPDATED_STREET_NUMBER);

    // Get all the realEstateList where streetNumber equals to UPDATED_STREET_NUMBER
    defaultRealEstateShouldNotBeFound(
        "streetNumber.in=" + UPDATED_STREET_NUMBER);
  }

  @Test
  @Transactional
  void getAllRealEstatesByStreetNumberIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where streetNumber is not null
    defaultRealEstateShouldBeFound("streetNumber.specified=true");

    // Get all the realEstateList where streetNumber is null
    defaultRealEstateShouldNotBeFound("streetNumber.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByStreetNumberContainsSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where streetNumber contains DEFAULT_STREET_NUMBER
    defaultRealEstateShouldBeFound(
        "streetNumber.contains=" + DEFAULT_STREET_NUMBER);

    // Get all the realEstateList where streetNumber contains UPDATED_STREET_NUMBER
    defaultRealEstateShouldNotBeFound(
        "streetNumber.contains=" + UPDATED_STREET_NUMBER);
  }

  @Test
  @Transactional
  void getAllRealEstatesByStreetNumberNotContainsSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where streetNumber does not contain
    // DEFAULT_STREET_NUMBER
    defaultRealEstateShouldNotBeFound(
        "streetNumber.doesNotContain=" + DEFAULT_STREET_NUMBER);

    // Get all the realEstateList where streetNumber does not contain
    // UPDATED_STREET_NUMBER
    defaultRealEstateShouldBeFound(
        "streetNumber.doesNotContain=" + UPDATED_STREET_NUMBER);
  }

  @Test
  @Transactional
  void getAllRealEstatesByPostalCodeIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where postalCode equals to DEFAULT_POSTAL_CODE
    defaultRealEstateShouldBeFound("postalCode.equals=" + DEFAULT_POSTAL_CODE);

    // Get all the realEstateList where postalCode equals to UPDATED_POSTAL_CODE
    defaultRealEstateShouldNotBeFound(
        "postalCode.equals=" + UPDATED_POSTAL_CODE);
  }

  @Test
  @Transactional
  void getAllRealEstatesByPostalCodeIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where postalCode in DEFAULT_POSTAL_CODE or
    // UPDATED_POSTAL_CODE
    defaultRealEstateShouldBeFound(
        "postalCode.in=" + DEFAULT_POSTAL_CODE + "," + UPDATED_POSTAL_CODE);

    // Get all the realEstateList where postalCode equals to UPDATED_POSTAL_CODE
    defaultRealEstateShouldNotBeFound("postalCode.in=" + UPDATED_POSTAL_CODE);
  }

  @Test
  @Transactional
  void getAllRealEstatesByPostalCodeIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where postalCode is not null
    defaultRealEstateShouldBeFound("postalCode.specified=true");

    // Get all the realEstateList where postalCode is null
    defaultRealEstateShouldNotBeFound("postalCode.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByPostalCodeContainsSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where postalCode contains DEFAULT_POSTAL_CODE
    defaultRealEstateShouldBeFound(
        "postalCode.contains=" + DEFAULT_POSTAL_CODE);

    // Get all the realEstateList where postalCode contains UPDATED_POSTAL_CODE
    defaultRealEstateShouldNotBeFound(
        "postalCode.contains=" + UPDATED_POSTAL_CODE);
  }

  @Test
  @Transactional
  void getAllRealEstatesByPostalCodeNotContainsSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where postalCode does not contain
    // DEFAULT_POSTAL_CODE
    defaultRealEstateShouldNotBeFound(
        "postalCode.doesNotContain=" + DEFAULT_POSTAL_CODE);

    // Get all the realEstateList where postalCode does not contain
    // UPDATED_POSTAL_CODE
    defaultRealEstateShouldBeFound(
        "postalCode.doesNotContain=" + UPDATED_POSTAL_CODE);
  }

  @Test
  @Transactional
  void getAllRealEstatesByCityIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where city equals to DEFAULT_CITY
    defaultRealEstateShouldBeFound("city.equals=" + DEFAULT_CITY);

    // Get all the realEstateList where city equals to UPDATED_CITY
    defaultRealEstateShouldNotBeFound("city.equals=" + UPDATED_CITY);
  }

  @Test
  @Transactional
  void getAllRealEstatesByCityIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where city in DEFAULT_CITY or UPDATED_CITY
    defaultRealEstateShouldBeFound(
        "city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

    // Get all the realEstateList where city equals to UPDATED_CITY
    defaultRealEstateShouldNotBeFound("city.in=" + UPDATED_CITY);
  }

  @Test
  @Transactional
  void getAllRealEstatesByCityIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where city is not null
    defaultRealEstateShouldBeFound("city.specified=true");

    // Get all the realEstateList where city is null
    defaultRealEstateShouldNotBeFound("city.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByCityContainsSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where city contains DEFAULT_CITY
    defaultRealEstateShouldBeFound("city.contains=" + DEFAULT_CITY);

    // Get all the realEstateList where city contains UPDATED_CITY
    defaultRealEstateShouldNotBeFound("city.contains=" + UPDATED_CITY);
  }

  @Test
  @Transactional
  void getAllRealEstatesByCityNotContainsSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where city does not contain DEFAULT_CITY
    defaultRealEstateShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

    // Get all the realEstateList where city does not contain UPDATED_CITY
    defaultRealEstateShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
  }

  @Test
  @Transactional
  void getAllRealEstatesByStateIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where state equals to DEFAULT_STATE
    defaultRealEstateShouldBeFound("state.equals=" + DEFAULT_STATE);

    // Get all the realEstateList where state equals to UPDATED_STATE
    defaultRealEstateShouldNotBeFound("state.equals=" + UPDATED_STATE);
  }

  @Test
  @Transactional
  void getAllRealEstatesByStateIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where state in DEFAULT_STATE or UPDATED_STATE
    defaultRealEstateShouldBeFound(
        "state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

    // Get all the realEstateList where state equals to UPDATED_STATE
    defaultRealEstateShouldNotBeFound("state.in=" + UPDATED_STATE);
  }

  @Test
  @Transactional
  void getAllRealEstatesByStateIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where state is not null
    defaultRealEstateShouldBeFound("state.specified=true");

    // Get all the realEstateList where state is null
    defaultRealEstateShouldNotBeFound("state.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByStateContainsSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where state contains DEFAULT_STATE
    defaultRealEstateShouldBeFound("state.contains=" + DEFAULT_STATE);

    // Get all the realEstateList where state contains UPDATED_STATE
    defaultRealEstateShouldNotBeFound("state.contains=" + UPDATED_STATE);
  }

  @Test
  @Transactional
  void getAllRealEstatesByStateNotContainsSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where state does not contain DEFAULT_STATE
    defaultRealEstateShouldNotBeFound("state.doesNotContain=" + DEFAULT_STATE);

    // Get all the realEstateList where state does not contain UPDATED_STATE
    defaultRealEstateShouldBeFound("state.doesNotContain=" + UPDATED_STATE);
  }

  @Test
  @Transactional
  void getAllRealEstatesByCountryIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where country equals to DEFAULT_COUNTRY
    defaultRealEstateShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

    // Get all the realEstateList where country equals to UPDATED_COUNTRY
    defaultRealEstateShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
  }

  @Test
  @Transactional
  void getAllRealEstatesByCountryIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where country in DEFAULT_COUNTRY or
    // UPDATED_COUNTRY
    defaultRealEstateShouldBeFound(
        "country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

    // Get all the realEstateList where country equals to UPDATED_COUNTRY
    defaultRealEstateShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
  }

  @Test
  @Transactional
  void getAllRealEstatesByCountryIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where country is not null
    defaultRealEstateShouldBeFound("country.specified=true");

    // Get all the realEstateList where country is null
    defaultRealEstateShouldNotBeFound("country.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByCountryContainsSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where country contains DEFAULT_COUNTRY
    defaultRealEstateShouldBeFound("country.contains=" + DEFAULT_COUNTRY);

    // Get all the realEstateList where country contains UPDATED_COUNTRY
    defaultRealEstateShouldNotBeFound("country.contains=" + UPDATED_COUNTRY);
  }

  @Test
  @Transactional
  void getAllRealEstatesByCountryNotContainsSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where country does not contain DEFAULT_COUNTRY
    defaultRealEstateShouldNotBeFound(
        "country.doesNotContain=" + DEFAULT_COUNTRY);

    // Get all the realEstateList where country does not contain UPDATED_COUNTRY
    defaultRealEstateShouldBeFound("country.doesNotContain=" + UPDATED_COUNTRY);
  }

  @Test
  @Transactional
  void getAllRealEstatesByPropertyWidthIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where propertyWidth equals to
    // DEFAULT_PROPERTY_WIDTH
    defaultRealEstateShouldBeFound(
        "propertyWidth.equals=" + DEFAULT_PROPERTY_WIDTH);

    // Get all the realEstateList where propertyWidth equals to
    // UPDATED_PROPERTY_WIDTH
    defaultRealEstateShouldNotBeFound(
        "propertyWidth.equals=" + UPDATED_PROPERTY_WIDTH);
  }

  @Test
  @Transactional
  void getAllRealEstatesByPropertyWidthIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where propertyWidth in DEFAULT_PROPERTY_WIDTH or
    // UPDATED_PROPERTY_WIDTH
    defaultRealEstateShouldBeFound(
        "propertyWidth.in=" +
            DEFAULT_PROPERTY_WIDTH +
            "," +
            UPDATED_PROPERTY_WIDTH);

    // Get all the realEstateList where propertyWidth equals to
    // UPDATED_PROPERTY_WIDTH
    defaultRealEstateShouldNotBeFound(
        "propertyWidth.in=" + UPDATED_PROPERTY_WIDTH);
  }

  @Test
  @Transactional
  void getAllRealEstatesByPropertyWidthIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where propertyWidth is not null
    defaultRealEstateShouldBeFound("propertyWidth.specified=true");

    // Get all the realEstateList where propertyWidth is null
    defaultRealEstateShouldNotBeFound("propertyWidth.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByPropertyWidthIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where propertyWidth is greater than or equal to
    // DEFAULT_PROPERTY_WIDTH
    defaultRealEstateShouldBeFound(
        "propertyWidth.greaterThanOrEqual=" + DEFAULT_PROPERTY_WIDTH);

    // Get all the realEstateList where propertyWidth is greater than or equal to
    // UPDATED_PROPERTY_WIDTH
    defaultRealEstateShouldNotBeFound(
        "propertyWidth.greaterThanOrEqual=" + UPDATED_PROPERTY_WIDTH);
  }

  @Test
  @Transactional
  void getAllRealEstatesByPropertyWidthIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where propertyWidth is less than or equal to
    // DEFAULT_PROPERTY_WIDTH
    defaultRealEstateShouldBeFound(
        "propertyWidth.lessThanOrEqual=" + DEFAULT_PROPERTY_WIDTH);

    // Get all the realEstateList where propertyWidth is less than or equal to
    // SMALLER_PROPERTY_WIDTH
    defaultRealEstateShouldNotBeFound(
        "propertyWidth.lessThanOrEqual=" + SMALLER_PROPERTY_WIDTH);
  }

  @Test
  @Transactional
  void getAllRealEstatesByPropertyWidthIsLessThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where propertyWidth is less than
    // DEFAULT_PROPERTY_WIDTH
    defaultRealEstateShouldNotBeFound(
        "propertyWidth.lessThan=" + DEFAULT_PROPERTY_WIDTH);

    // Get all the realEstateList where propertyWidth is less than
    // UPDATED_PROPERTY_WIDTH
    defaultRealEstateShouldBeFound(
        "propertyWidth.lessThan=" + UPDATED_PROPERTY_WIDTH);
  }

  @Test
  @Transactional
  void getAllRealEstatesByPropertyWidthIsGreaterThanSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where propertyWidth is greater than
    // DEFAULT_PROPERTY_WIDTH
    defaultRealEstateShouldNotBeFound(
        "propertyWidth.greaterThan=" + DEFAULT_PROPERTY_WIDTH);

    // Get all the realEstateList where propertyWidth is greater than
    // SMALLER_PROPERTY_WIDTH
    defaultRealEstateShouldBeFound(
        "propertyWidth.greaterThan=" + SMALLER_PROPERTY_WIDTH);
  }

  @Test
  @Transactional
  void getAllRealEstatesByPropertyLengthIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where propertyLength equals to
    // DEFAULT_PROPERTY_LENGTH
    defaultRealEstateShouldBeFound(
        "propertyLength.equals=" + DEFAULT_PROPERTY_LENGTH);

    // Get all the realEstateList where propertyLength equals to
    // UPDATED_PROPERTY_LENGTH
    defaultRealEstateShouldNotBeFound(
        "propertyLength.equals=" + UPDATED_PROPERTY_LENGTH);
  }

  @Test
  @Transactional
  void getAllRealEstatesByPropertyLengthIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where propertyLength in DEFAULT_PROPERTY_LENGTH or
    // UPDATED_PROPERTY_LENGTH
    defaultRealEstateShouldBeFound(
        "propertyLength.in=" +
            DEFAULT_PROPERTY_LENGTH +
            "," +
            UPDATED_PROPERTY_LENGTH);

    // Get all the realEstateList where propertyLength equals to
    // UPDATED_PROPERTY_LENGTH
    defaultRealEstateShouldNotBeFound(
        "propertyLength.in=" + UPDATED_PROPERTY_LENGTH);
  }

  @Test
  @Transactional
  void getAllRealEstatesByPropertyLengthIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where propertyLength is not null
    defaultRealEstateShouldBeFound("propertyLength.specified=true");

    // Get all the realEstateList where propertyLength is null
    defaultRealEstateShouldNotBeFound("propertyLength.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByPropertyLengthIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where propertyLength is greater than or equal to
    // DEFAULT_PROPERTY_LENGTH
    defaultRealEstateShouldBeFound(
        "propertyLength.greaterThanOrEqual=" + DEFAULT_PROPERTY_LENGTH);

    // Get all the realEstateList where propertyLength is greater than or equal to
    // UPDATED_PROPERTY_LENGTH
    defaultRealEstateShouldNotBeFound(
        "propertyLength.greaterThanOrEqual=" + UPDATED_PROPERTY_LENGTH);
  }

  @Test
  @Transactional
  void getAllRealEstatesByPropertyLengthIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where propertyLength is less than or equal to
    // DEFAULT_PROPERTY_LENGTH
    defaultRealEstateShouldBeFound(
        "propertyLength.lessThanOrEqual=" + DEFAULT_PROPERTY_LENGTH);

    // Get all the realEstateList where propertyLength is less than or equal to
    // SMALLER_PROPERTY_LENGTH
    defaultRealEstateShouldNotBeFound(
        "propertyLength.lessThanOrEqual=" + SMALLER_PROPERTY_LENGTH);
  }

  @Test
  @Transactional
  void getAllRealEstatesByPropertyLengthIsLessThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where propertyLength is less than
    // DEFAULT_PROPERTY_LENGTH
    defaultRealEstateShouldNotBeFound(
        "propertyLength.lessThan=" + DEFAULT_PROPERTY_LENGTH);

    // Get all the realEstateList where propertyLength is less than
    // UPDATED_PROPERTY_LENGTH
    defaultRealEstateShouldBeFound(
        "propertyLength.lessThan=" + UPDATED_PROPERTY_LENGTH);
  }

  @Test
  @Transactional
  void getAllRealEstatesByPropertyLengthIsGreaterThanSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where propertyLength is greater than
    // DEFAULT_PROPERTY_LENGTH
    defaultRealEstateShouldNotBeFound(
        "propertyLength.greaterThan=" + DEFAULT_PROPERTY_LENGTH);

    // Get all the realEstateList where propertyLength is greater than
    // SMALLER_PROPERTY_LENGTH
    defaultRealEstateShouldBeFound(
        "propertyLength.greaterThan=" + SMALLER_PROPERTY_LENGTH);
  }

  @Test
  @Transactional
  void getAllRealEstatesByPropertyAreaIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where propertyArea equals to DEFAULT_PROPERTY_AREA
    defaultRealEstateShouldBeFound(
        "propertyArea.equals=" + DEFAULT_PROPERTY_AREA);

    // Get all the realEstateList where propertyArea equals to UPDATED_PROPERTY_AREA
    defaultRealEstateShouldNotBeFound(
        "propertyArea.equals=" + UPDATED_PROPERTY_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByPropertyAreaIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where propertyArea in DEFAULT_PROPERTY_AREA or
    // UPDATED_PROPERTY_AREA
    defaultRealEstateShouldBeFound(
        "propertyArea.in=" + DEFAULT_PROPERTY_AREA + "," + UPDATED_PROPERTY_AREA);

    // Get all the realEstateList where propertyArea equals to UPDATED_PROPERTY_AREA
    defaultRealEstateShouldNotBeFound(
        "propertyArea.in=" + UPDATED_PROPERTY_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByPropertyAreaIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where propertyArea is not null
    defaultRealEstateShouldBeFound("propertyArea.specified=true");

    // Get all the realEstateList where propertyArea is null
    defaultRealEstateShouldNotBeFound("propertyArea.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByPropertyAreaIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where propertyArea is greater than or equal to
    // DEFAULT_PROPERTY_AREA
    defaultRealEstateShouldBeFound(
        "propertyArea.greaterThanOrEqual=" + DEFAULT_PROPERTY_AREA);

    // Get all the realEstateList where propertyArea is greater than or equal to
    // UPDATED_PROPERTY_AREA
    defaultRealEstateShouldNotBeFound(
        "propertyArea.greaterThanOrEqual=" + UPDATED_PROPERTY_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByPropertyAreaIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where propertyArea is less than or equal to
    // DEFAULT_PROPERTY_AREA
    defaultRealEstateShouldBeFound(
        "propertyArea.lessThanOrEqual=" + DEFAULT_PROPERTY_AREA);

    // Get all the realEstateList where propertyArea is less than or equal to
    // SMALLER_PROPERTY_AREA
    defaultRealEstateShouldNotBeFound(
        "propertyArea.lessThanOrEqual=" + SMALLER_PROPERTY_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByPropertyAreaIsLessThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where propertyArea is less than
    // DEFAULT_PROPERTY_AREA
    defaultRealEstateShouldNotBeFound(
        "propertyArea.lessThan=" + DEFAULT_PROPERTY_AREA);

    // Get all the realEstateList where propertyArea is less than
    // UPDATED_PROPERTY_AREA
    defaultRealEstateShouldBeFound(
        "propertyArea.lessThan=" + UPDATED_PROPERTY_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByPropertyAreaIsGreaterThanSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where propertyArea is greater than
    // DEFAULT_PROPERTY_AREA
    defaultRealEstateShouldNotBeFound(
        "propertyArea.greaterThan=" + DEFAULT_PROPERTY_AREA);

    // Get all the realEstateList where propertyArea is greater than
    // SMALLER_PROPERTY_AREA
    defaultRealEstateShouldBeFound(
        "propertyArea.greaterThan=" + SMALLER_PROPERTY_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByAreaIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where area equals to DEFAULT_AREA
    defaultRealEstateShouldBeFound("area.equals=" + DEFAULT_AREA);

    // Get all the realEstateList where area equals to UPDATED_AREA
    defaultRealEstateShouldNotBeFound("area.equals=" + UPDATED_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByAreaIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where area in DEFAULT_AREA or UPDATED_AREA
    defaultRealEstateShouldBeFound(
        "area.in=" + DEFAULT_AREA + "," + UPDATED_AREA);

    // Get all the realEstateList where area equals to UPDATED_AREA
    defaultRealEstateShouldNotBeFound("area.in=" + UPDATED_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByAreaIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where area is not null
    defaultRealEstateShouldBeFound("area.specified=true");

    // Get all the realEstateList where area is null
    defaultRealEstateShouldNotBeFound("area.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByAreaIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where area is greater than or equal to
    // DEFAULT_AREA
    defaultRealEstateShouldBeFound("area.greaterThanOrEqual=" + DEFAULT_AREA);

    // Get all the realEstateList where area is greater than or equal to
    // UPDATED_AREA
    defaultRealEstateShouldNotBeFound(
        "area.greaterThanOrEqual=" + UPDATED_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByAreaIsLessThanOrEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where area is less than or equal to DEFAULT_AREA
    defaultRealEstateShouldBeFound("area.lessThanOrEqual=" + DEFAULT_AREA);

    // Get all the realEstateList where area is less than or equal to SMALLER_AREA
    defaultRealEstateShouldNotBeFound("area.lessThanOrEqual=" + SMALLER_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByAreaIsLessThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where area is less than DEFAULT_AREA
    defaultRealEstateShouldNotBeFound("area.lessThan=" + DEFAULT_AREA);

    // Get all the realEstateList where area is less than UPDATED_AREA
    defaultRealEstateShouldBeFound("area.lessThan=" + UPDATED_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByAreaIsGreaterThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where area is greater than DEFAULT_AREA
    defaultRealEstateShouldNotBeFound("area.greaterThan=" + DEFAULT_AREA);

    // Get all the realEstateList where area is greater than SMALLER_AREA
    defaultRealEstateShouldBeFound("area.greaterThan=" + SMALLER_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoHousesIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noHouses equals to DEFAULT_NO_HOUSES
    defaultRealEstateShouldBeFound("noHouses.equals=" + DEFAULT_NO_HOUSES);

    // Get all the realEstateList where noHouses equals to UPDATED_NO_HOUSES
    defaultRealEstateShouldNotBeFound("noHouses.equals=" + UPDATED_NO_HOUSES);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoHousesIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noHouses in DEFAULT_NO_HOUSES or
    // UPDATED_NO_HOUSES
    defaultRealEstateShouldBeFound(
        "noHouses.in=" + DEFAULT_NO_HOUSES + "," + UPDATED_NO_HOUSES);

    // Get all the realEstateList where noHouses equals to UPDATED_NO_HOUSES
    defaultRealEstateShouldNotBeFound("noHouses.in=" + UPDATED_NO_HOUSES);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoHousesIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noHouses is not null
    defaultRealEstateShouldBeFound("noHouses.specified=true");

    // Get all the realEstateList where noHouses is null
    defaultRealEstateShouldNotBeFound("noHouses.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoHousesIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noHouses is greater than or equal to
    // DEFAULT_NO_HOUSES
    defaultRealEstateShouldBeFound(
        "noHouses.greaterThanOrEqual=" + DEFAULT_NO_HOUSES);

    // Get all the realEstateList where noHouses is greater than or equal to
    // UPDATED_NO_HOUSES
    defaultRealEstateShouldNotBeFound(
        "noHouses.greaterThanOrEqual=" + UPDATED_NO_HOUSES);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoHousesIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noHouses is less than or equal to
    // DEFAULT_NO_HOUSES
    defaultRealEstateShouldBeFound(
        "noHouses.lessThanOrEqual=" + DEFAULT_NO_HOUSES);

    // Get all the realEstateList where noHouses is less than or equal to
    // SMALLER_NO_HOUSES
    defaultRealEstateShouldNotBeFound(
        "noHouses.lessThanOrEqual=" + SMALLER_NO_HOUSES);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoHousesIsLessThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noHouses is less than DEFAULT_NO_HOUSES
    defaultRealEstateShouldNotBeFound("noHouses.lessThan=" + DEFAULT_NO_HOUSES);

    // Get all the realEstateList where noHouses is less than UPDATED_NO_HOUSES
    defaultRealEstateShouldBeFound("noHouses.lessThan=" + UPDATED_NO_HOUSES);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoHousesIsGreaterThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noHouses is greater than DEFAULT_NO_HOUSES
    defaultRealEstateShouldNotBeFound(
        "noHouses.greaterThan=" + DEFAULT_NO_HOUSES);

    // Get all the realEstateList where noHouses is greater than SMALLER_NO_HOUSES
    defaultRealEstateShouldBeFound("noHouses.greaterThan=" + SMALLER_NO_HOUSES);
  }

  @Test
  @Transactional
  void getAllRealEstatesByConstructionYearIsEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where constructionYear equals to
    // DEFAULT_CONSTRUCTION_YEAR
    defaultRealEstateShouldBeFound(
        "constructionYear.equals=" + DEFAULT_CONSTRUCTION_YEAR);

    // Get all the realEstateList where constructionYear equals to
    // UPDATED_CONSTRUCTION_YEAR
    defaultRealEstateShouldNotBeFound(
        "constructionYear.equals=" + UPDATED_CONSTRUCTION_YEAR);
  }

  @Test
  @Transactional
  void getAllRealEstatesByConstructionYearIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where constructionYear in
    // DEFAULT_CONSTRUCTION_YEAR or UPDATED_CONSTRUCTION_YEAR
    defaultRealEstateShouldBeFound(
        "constructionYear.in=" +
            DEFAULT_CONSTRUCTION_YEAR +
            "," +
            UPDATED_CONSTRUCTION_YEAR);

    // Get all the realEstateList where constructionYear equals to
    // UPDATED_CONSTRUCTION_YEAR
    defaultRealEstateShouldNotBeFound(
        "constructionYear.in=" + UPDATED_CONSTRUCTION_YEAR);
  }

  @Test
  @Transactional
  void getAllRealEstatesByConstructionYearIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where constructionYear is not null
    defaultRealEstateShouldBeFound("constructionYear.specified=true");

    // Get all the realEstateList where constructionYear is null
    defaultRealEstateShouldNotBeFound("constructionYear.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByConstructionYearIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where constructionYear is greater than or equal to
    // DEFAULT_CONSTRUCTION_YEAR
    defaultRealEstateShouldBeFound(
        "constructionYear.greaterThanOrEqual=" + DEFAULT_CONSTRUCTION_YEAR);

    // Get all the realEstateList where constructionYear is greater than or equal to
    // UPDATED_CONSTRUCTION_YEAR
    defaultRealEstateShouldNotBeFound(
        "constructionYear.greaterThanOrEqual=" + UPDATED_CONSTRUCTION_YEAR);
  }

  @Test
  @Transactional
  void getAllRealEstatesByConstructionYearIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where constructionYear is less than or equal to
    // DEFAULT_CONSTRUCTION_YEAR
    defaultRealEstateShouldBeFound(
        "constructionYear.lessThanOrEqual=" + DEFAULT_CONSTRUCTION_YEAR);

    // Get all the realEstateList where constructionYear is less than or equal to
    // SMALLER_CONSTRUCTION_YEAR
    defaultRealEstateShouldNotBeFound(
        "constructionYear.lessThanOrEqual=" + SMALLER_CONSTRUCTION_YEAR);
  }

  @Test
  @Transactional
  void getAllRealEstatesByConstructionYearIsLessThanSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where constructionYear is less than
    // DEFAULT_CONSTRUCTION_YEAR
    defaultRealEstateShouldNotBeFound(
        "constructionYear.lessThan=" + DEFAULT_CONSTRUCTION_YEAR);

    // Get all the realEstateList where constructionYear is less than
    // UPDATED_CONSTRUCTION_YEAR
    defaultRealEstateShouldBeFound(
        "constructionYear.lessThan=" + UPDATED_CONSTRUCTION_YEAR);
  }

  @Test
  @Transactional
  void getAllRealEstatesByConstructionYearIsGreaterThanSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where constructionYear is greater than
    // DEFAULT_CONSTRUCTION_YEAR
    defaultRealEstateShouldNotBeFound(
        "constructionYear.greaterThan=" + DEFAULT_CONSTRUCTION_YEAR);

    // Get all the realEstateList where constructionYear is greater than
    // SMALLER_CONSTRUCTION_YEAR
    defaultRealEstateShouldBeFound(
        "constructionYear.greaterThan=" + SMALLER_CONSTRUCTION_YEAR);
  }

  @Test
  @Transactional
  void getAllRealEstatesByDesignTypeIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where designType equals to DEFAULT_DESIGN_TYPE
    defaultRealEstateShouldBeFound("designType.equals=" + DEFAULT_DESIGN_TYPE);

    // Get all the realEstateList where designType equals to UPDATED_DESIGN_TYPE
    defaultRealEstateShouldNotBeFound(
        "designType.equals=" + UPDATED_DESIGN_TYPE);
  }

  @Test
  @Transactional
  void getAllRealEstatesByDesignTypeIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where designType in DEFAULT_DESIGN_TYPE or
    // UPDATED_DESIGN_TYPE
    defaultRealEstateShouldBeFound(
        "designType.in=" + DEFAULT_DESIGN_TYPE + "," + UPDATED_DESIGN_TYPE);

    // Get all the realEstateList where designType equals to UPDATED_DESIGN_TYPE
    defaultRealEstateShouldNotBeFound("designType.in=" + UPDATED_DESIGN_TYPE);
  }

  @Test
  @Transactional
  void getAllRealEstatesByDesignTypeIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where designType is not null
    defaultRealEstateShouldBeFound("designType.specified=true");

    // Get all the realEstateList where designType is null
    defaultRealEstateShouldNotBeFound("designType.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByDesignTypeContainsSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where designType contains DEFAULT_DESIGN_TYPE
    defaultRealEstateShouldBeFound(
        "designType.contains=" + DEFAULT_DESIGN_TYPE);

    // Get all the realEstateList where designType contains UPDATED_DESIGN_TYPE
    defaultRealEstateShouldNotBeFound(
        "designType.contains=" + UPDATED_DESIGN_TYPE);
  }

  @Test
  @Transactional
  void getAllRealEstatesByDesignTypeNotContainsSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where designType does not contain
    // DEFAULT_DESIGN_TYPE
    defaultRealEstateShouldNotBeFound(
        "designType.doesNotContain=" + DEFAULT_DESIGN_TYPE);

    // Get all the realEstateList where designType does not contain
    // UPDATED_DESIGN_TYPE
    defaultRealEstateShouldBeFound(
        "designType.doesNotContain=" + UPDATED_DESIGN_TYPE);
  }

  @Test
  @Transactional
  void getAllRealEstatesByDesignTypeClassIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where designTypeClass equals to
    // DEFAULT_DESIGN_TYPE_CLASS
    defaultRealEstateShouldBeFound(
        "designTypeClass.equals=" + DEFAULT_DESIGN_TYPE_CLASS);

    // Get all the realEstateList where designTypeClass equals to
    // UPDATED_DESIGN_TYPE_CLASS
    defaultRealEstateShouldNotBeFound(
        "designTypeClass.equals=" + UPDATED_DESIGN_TYPE_CLASS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByDesignTypeClassIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where designTypeClass in DEFAULT_DESIGN_TYPE_CLASS
    // or UPDATED_DESIGN_TYPE_CLASS
    defaultRealEstateShouldBeFound(
        "designTypeClass.in=" +
            DEFAULT_DESIGN_TYPE_CLASS +
            "," +
            UPDATED_DESIGN_TYPE_CLASS);

    // Get all the realEstateList where designTypeClass equals to
    // UPDATED_DESIGN_TYPE_CLASS
    defaultRealEstateShouldNotBeFound(
        "designTypeClass.in=" + UPDATED_DESIGN_TYPE_CLASS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByDesignTypeClassIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where designTypeClass is not null
    defaultRealEstateShouldBeFound("designTypeClass.specified=true");

    // Get all the realEstateList where designTypeClass is null
    defaultRealEstateShouldNotBeFound("designTypeClass.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByBuiltUpAreaIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where builtUpArea equals to DEFAULT_BUILT_UP_AREA
    defaultRealEstateShouldBeFound(
        "builtUpArea.equals=" + DEFAULT_BUILT_UP_AREA);

    // Get all the realEstateList where builtUpArea equals to UPDATED_BUILT_UP_AREA
    defaultRealEstateShouldNotBeFound(
        "builtUpArea.equals=" + UPDATED_BUILT_UP_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByBuiltUpAreaIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where builtUpArea in DEFAULT_BUILT_UP_AREA or
    // UPDATED_BUILT_UP_AREA
    defaultRealEstateShouldBeFound(
        "builtUpArea.in=" + DEFAULT_BUILT_UP_AREA + "," + UPDATED_BUILT_UP_AREA);

    // Get all the realEstateList where builtUpArea equals to UPDATED_BUILT_UP_AREA
    defaultRealEstateShouldNotBeFound(
        "builtUpArea.in=" + UPDATED_BUILT_UP_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByBuiltUpAreaIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where builtUpArea is not null
    defaultRealEstateShouldBeFound("builtUpArea.specified=true");

    // Get all the realEstateList where builtUpArea is null
    defaultRealEstateShouldNotBeFound("builtUpArea.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByBuiltUpAreaIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where builtUpArea is greater than or equal to
    // DEFAULT_BUILT_UP_AREA
    defaultRealEstateShouldBeFound(
        "builtUpArea.greaterThanOrEqual=" + DEFAULT_BUILT_UP_AREA);

    // Get all the realEstateList where builtUpArea is greater than or equal to
    // UPDATED_BUILT_UP_AREA
    defaultRealEstateShouldNotBeFound(
        "builtUpArea.greaterThanOrEqual=" + UPDATED_BUILT_UP_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByBuiltUpAreaIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where builtUpArea is less than or equal to
    // DEFAULT_BUILT_UP_AREA
    defaultRealEstateShouldBeFound(
        "builtUpArea.lessThanOrEqual=" + DEFAULT_BUILT_UP_AREA);

    // Get all the realEstateList where builtUpArea is less than or equal to
    // SMALLER_BUILT_UP_AREA
    defaultRealEstateShouldNotBeFound(
        "builtUpArea.lessThanOrEqual=" + SMALLER_BUILT_UP_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByBuiltUpAreaIsLessThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where builtUpArea is less than
    // DEFAULT_BUILT_UP_AREA
    defaultRealEstateShouldNotBeFound(
        "builtUpArea.lessThan=" + DEFAULT_BUILT_UP_AREA);

    // Get all the realEstateList where builtUpArea is less than
    // UPDATED_BUILT_UP_AREA
    defaultRealEstateShouldBeFound(
        "builtUpArea.lessThan=" + UPDATED_BUILT_UP_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByBuiltUpAreaIsGreaterThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where builtUpArea is greater than
    // DEFAULT_BUILT_UP_AREA
    defaultRealEstateShouldNotBeFound(
        "builtUpArea.greaterThan=" + DEFAULT_BUILT_UP_AREA);

    // Get all the realEstateList where builtUpArea is greater than
    // SMALLER_BUILT_UP_AREA
    defaultRealEstateShouldBeFound(
        "builtUpArea.greaterThan=" + SMALLER_BUILT_UP_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesBySealtAreaIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where sealtArea equals to DEFAULT_SEALT_AREA
    defaultRealEstateShouldBeFound("sealtArea.equals=" + DEFAULT_SEALT_AREA);

    // Get all the realEstateList where sealtArea equals to UPDATED_SEALT_AREA
    defaultRealEstateShouldNotBeFound("sealtArea.equals=" + UPDATED_SEALT_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesBySealtAreaIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where sealtArea in DEFAULT_SEALT_AREA or
    // UPDATED_SEALT_AREA
    defaultRealEstateShouldBeFound(
        "sealtArea.in=" + DEFAULT_SEALT_AREA + "," + UPDATED_SEALT_AREA);

    // Get all the realEstateList where sealtArea equals to UPDATED_SEALT_AREA
    defaultRealEstateShouldNotBeFound("sealtArea.in=" + UPDATED_SEALT_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesBySealtAreaIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where sealtArea is not null
    defaultRealEstateShouldBeFound("sealtArea.specified=true");

    // Get all the realEstateList where sealtArea is null
    defaultRealEstateShouldNotBeFound("sealtArea.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesBySealtAreaIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where sealtArea is greater than or equal to
    // DEFAULT_SEALT_AREA
    defaultRealEstateShouldBeFound(
        "sealtArea.greaterThanOrEqual=" + DEFAULT_SEALT_AREA);

    // Get all the realEstateList where sealtArea is greater than or equal to
    // UPDATED_SEALT_AREA
    defaultRealEstateShouldNotBeFound(
        "sealtArea.greaterThanOrEqual=" + UPDATED_SEALT_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesBySealtAreaIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where sealtArea is less than or equal to
    // DEFAULT_SEALT_AREA
    defaultRealEstateShouldBeFound(
        "sealtArea.lessThanOrEqual=" + DEFAULT_SEALT_AREA);

    // Get all the realEstateList where sealtArea is less than or equal to
    // SMALLER_SEALT_AREA
    defaultRealEstateShouldNotBeFound(
        "sealtArea.lessThanOrEqual=" + SMALLER_SEALT_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesBySealtAreaIsLessThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where sealtArea is less than DEFAULT_SEALT_AREA
    defaultRealEstateShouldNotBeFound(
        "sealtArea.lessThan=" + DEFAULT_SEALT_AREA);

    // Get all the realEstateList where sealtArea is less than UPDATED_SEALT_AREA
    defaultRealEstateShouldBeFound("sealtArea.lessThan=" + UPDATED_SEALT_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesBySealtAreaIsGreaterThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where sealtArea is greater than DEFAULT_SEALT_AREA
    defaultRealEstateShouldNotBeFound(
        "sealtArea.greaterThan=" + DEFAULT_SEALT_AREA);

    // Get all the realEstateList where sealtArea is greater than SMALLER_SEALT_AREA
    defaultRealEstateShouldBeFound(
        "sealtArea.greaterThan=" + SMALLER_SEALT_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByUndevelopedAreaIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where undevelopedArea equals to
    // DEFAULT_UNDEVELOPED_AREA
    defaultRealEstateShouldBeFound(
        "undevelopedArea.equals=" + DEFAULT_UNDEVELOPED_AREA);

    // Get all the realEstateList where undevelopedArea equals to
    // UPDATED_UNDEVELOPED_AREA
    defaultRealEstateShouldNotBeFound(
        "undevelopedArea.equals=" + UPDATED_UNDEVELOPED_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByUndevelopedAreaIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where undevelopedArea in DEFAULT_UNDEVELOPED_AREA
    // or UPDATED_UNDEVELOPED_AREA
    defaultRealEstateShouldBeFound(
        "undevelopedArea.in=" +
            DEFAULT_UNDEVELOPED_AREA +
            "," +
            UPDATED_UNDEVELOPED_AREA);

    // Get all the realEstateList where undevelopedArea equals to
    // UPDATED_UNDEVELOPED_AREA
    defaultRealEstateShouldNotBeFound(
        "undevelopedArea.in=" + UPDATED_UNDEVELOPED_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByUndevelopedAreaIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where undevelopedArea is not null
    defaultRealEstateShouldBeFound("undevelopedArea.specified=true");

    // Get all the realEstateList where undevelopedArea is null
    defaultRealEstateShouldNotBeFound("undevelopedArea.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByUndevelopedAreaIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where undevelopedArea is greater than or equal to
    // DEFAULT_UNDEVELOPED_AREA
    defaultRealEstateShouldBeFound(
        "undevelopedArea.greaterThanOrEqual=" + DEFAULT_UNDEVELOPED_AREA);

    // Get all the realEstateList where undevelopedArea is greater than or equal to
    // UPDATED_UNDEVELOPED_AREA
    defaultRealEstateShouldNotBeFound(
        "undevelopedArea.greaterThanOrEqual=" + UPDATED_UNDEVELOPED_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByUndevelopedAreaIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where undevelopedArea is less than or equal to
    // DEFAULT_UNDEVELOPED_AREA
    defaultRealEstateShouldBeFound(
        "undevelopedArea.lessThanOrEqual=" + DEFAULT_UNDEVELOPED_AREA);

    // Get all the realEstateList where undevelopedArea is less than or equal to
    // SMALLER_UNDEVELOPED_AREA
    defaultRealEstateShouldNotBeFound(
        "undevelopedArea.lessThanOrEqual=" + SMALLER_UNDEVELOPED_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByUndevelopedAreaIsLessThanSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where undevelopedArea is less than
    // DEFAULT_UNDEVELOPED_AREA
    defaultRealEstateShouldNotBeFound(
        "undevelopedArea.lessThan=" + DEFAULT_UNDEVELOPED_AREA);

    // Get all the realEstateList where undevelopedArea is less than
    // UPDATED_UNDEVELOPED_AREA
    defaultRealEstateShouldBeFound(
        "undevelopedArea.lessThan=" + UPDATED_UNDEVELOPED_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByUndevelopedAreaIsGreaterThanSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where undevelopedArea is greater than
    // DEFAULT_UNDEVELOPED_AREA
    defaultRealEstateShouldNotBeFound(
        "undevelopedArea.greaterThan=" + DEFAULT_UNDEVELOPED_AREA);

    // Get all the realEstateList where undevelopedArea is greater than
    // SMALLER_UNDEVELOPED_AREA
    defaultRealEstateShouldBeFound(
        "undevelopedArea.greaterThan=" + SMALLER_UNDEVELOPED_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoSmokeDetectorsIsEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noSmokeDetectors equals to
    // DEFAULT_NO_SMOKE_DETECTORS
    defaultRealEstateShouldBeFound(
        "noSmokeDetectors.equals=" + DEFAULT_NO_SMOKE_DETECTORS);

    // Get all the realEstateList where noSmokeDetectors equals to
    // UPDATED_NO_SMOKE_DETECTORS
    defaultRealEstateShouldNotBeFound(
        "noSmokeDetectors.equals=" + UPDATED_NO_SMOKE_DETECTORS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoSmokeDetectorsIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noSmokeDetectors in
    // DEFAULT_NO_SMOKE_DETECTORS or UPDATED_NO_SMOKE_DETECTORS
    defaultRealEstateShouldBeFound(
        "noSmokeDetectors.in=" +
            DEFAULT_NO_SMOKE_DETECTORS +
            "," +
            UPDATED_NO_SMOKE_DETECTORS);

    // Get all the realEstateList where noSmokeDetectors equals to
    // UPDATED_NO_SMOKE_DETECTORS
    defaultRealEstateShouldNotBeFound(
        "noSmokeDetectors.in=" + UPDATED_NO_SMOKE_DETECTORS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoSmokeDetectorsIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noSmokeDetectors is not null
    defaultRealEstateShouldBeFound("noSmokeDetectors.specified=true");

    // Get all the realEstateList where noSmokeDetectors is null
    defaultRealEstateShouldNotBeFound("noSmokeDetectors.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoSmokeDetectorsIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noSmokeDetectors is greater than or equal to
    // DEFAULT_NO_SMOKE_DETECTORS
    defaultRealEstateShouldBeFound(
        "noSmokeDetectors.greaterThanOrEqual=" + DEFAULT_NO_SMOKE_DETECTORS);

    // Get all the realEstateList where noSmokeDetectors is greater than or equal to
    // UPDATED_NO_SMOKE_DETECTORS
    defaultRealEstateShouldNotBeFound(
        "noSmokeDetectors.greaterThanOrEqual=" + UPDATED_NO_SMOKE_DETECTORS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoSmokeDetectorsIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noSmokeDetectors is less than or equal to
    // DEFAULT_NO_SMOKE_DETECTORS
    defaultRealEstateShouldBeFound(
        "noSmokeDetectors.lessThanOrEqual=" + DEFAULT_NO_SMOKE_DETECTORS);

    // Get all the realEstateList where noSmokeDetectors is less than or equal to
    // SMALLER_NO_SMOKE_DETECTORS
    defaultRealEstateShouldNotBeFound(
        "noSmokeDetectors.lessThanOrEqual=" + SMALLER_NO_SMOKE_DETECTORS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoSmokeDetectorsIsLessThanSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noSmokeDetectors is less than
    // DEFAULT_NO_SMOKE_DETECTORS
    defaultRealEstateShouldNotBeFound(
        "noSmokeDetectors.lessThan=" + DEFAULT_NO_SMOKE_DETECTORS);

    // Get all the realEstateList where noSmokeDetectors is less than
    // UPDATED_NO_SMOKE_DETECTORS
    defaultRealEstateShouldBeFound(
        "noSmokeDetectors.lessThan=" + UPDATED_NO_SMOKE_DETECTORS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoSmokeDetectorsIsGreaterThanSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noSmokeDetectors is greater than
    // DEFAULT_NO_SMOKE_DETECTORS
    defaultRealEstateShouldNotBeFound(
        "noSmokeDetectors.greaterThan=" + DEFAULT_NO_SMOKE_DETECTORS);

    // Get all the realEstateList where noSmokeDetectors is greater than
    // SMALLER_NO_SMOKE_DETECTORS
    defaultRealEstateShouldBeFound(
        "noSmokeDetectors.greaterThan=" + SMALLER_NO_SMOKE_DETECTORS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoUnitsIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noUnits equals to DEFAULT_NO_UNITS
    defaultRealEstateShouldBeFound("noUnits.equals=" + DEFAULT_NO_UNITS);

    // Get all the realEstateList where noUnits equals to UPDATED_NO_UNITS
    defaultRealEstateShouldNotBeFound("noUnits.equals=" + UPDATED_NO_UNITS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoUnitsIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noUnits in DEFAULT_NO_UNITS or
    // UPDATED_NO_UNITS
    defaultRealEstateShouldBeFound(
        "noUnits.in=" + DEFAULT_NO_UNITS + "," + UPDATED_NO_UNITS);

    // Get all the realEstateList where noUnits equals to UPDATED_NO_UNITS
    defaultRealEstateShouldNotBeFound("noUnits.in=" + UPDATED_NO_UNITS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoUnitsIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noUnits is not null
    defaultRealEstateShouldBeFound("noUnits.specified=true");

    // Get all the realEstateList where noUnits is null
    defaultRealEstateShouldNotBeFound("noUnits.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoUnitsIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noUnits is greater than or equal to
    // DEFAULT_NO_UNITS
    defaultRealEstateShouldBeFound(
        "noUnits.greaterThanOrEqual=" + DEFAULT_NO_UNITS);

    // Get all the realEstateList where noUnits is greater than or equal to
    // UPDATED_NO_UNITS
    defaultRealEstateShouldNotBeFound(
        "noUnits.greaterThanOrEqual=" + UPDATED_NO_UNITS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoUnitsIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noUnits is less than or equal to
    // DEFAULT_NO_UNITS
    defaultRealEstateShouldBeFound(
        "noUnits.lessThanOrEqual=" + DEFAULT_NO_UNITS);

    // Get all the realEstateList where noUnits is less than or equal to
    // SMALLER_NO_UNITS
    defaultRealEstateShouldNotBeFound(
        "noUnits.lessThanOrEqual=" + SMALLER_NO_UNITS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoUnitsIsLessThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noUnits is less than DEFAULT_NO_UNITS
    defaultRealEstateShouldNotBeFound("noUnits.lessThan=" + DEFAULT_NO_UNITS);

    // Get all the realEstateList where noUnits is less than UPDATED_NO_UNITS
    defaultRealEstateShouldBeFound("noUnits.lessThan=" + UPDATED_NO_UNITS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoUnitsIsGreaterThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noUnits is greater than DEFAULT_NO_UNITS
    defaultRealEstateShouldNotBeFound(
        "noUnits.greaterThan=" + DEFAULT_NO_UNITS);

    // Get all the realEstateList where noUnits is greater than SMALLER_NO_UNITS
    defaultRealEstateShouldBeFound("noUnits.greaterThan=" + SMALLER_NO_UNITS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoFloorsIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noFloors equals to DEFAULT_NO_FLOORS
    defaultRealEstateShouldBeFound("noFloors.equals=" + DEFAULT_NO_FLOORS);

    // Get all the realEstateList where noFloors equals to UPDATED_NO_FLOORS
    defaultRealEstateShouldNotBeFound("noFloors.equals=" + UPDATED_NO_FLOORS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoFloorsIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noFloors in DEFAULT_NO_FLOORS or
    // UPDATED_NO_FLOORS
    defaultRealEstateShouldBeFound(
        "noFloors.in=" + DEFAULT_NO_FLOORS + "," + UPDATED_NO_FLOORS);

    // Get all the realEstateList where noFloors equals to UPDATED_NO_FLOORS
    defaultRealEstateShouldNotBeFound("noFloors.in=" + UPDATED_NO_FLOORS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoFloorsIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noFloors is not null
    defaultRealEstateShouldBeFound("noFloors.specified=true");

    // Get all the realEstateList where noFloors is null
    defaultRealEstateShouldNotBeFound("noFloors.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoFloorsIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noFloors is greater than or equal to
    // DEFAULT_NO_FLOORS
    defaultRealEstateShouldBeFound(
        "noFloors.greaterThanOrEqual=" + DEFAULT_NO_FLOORS);

    // Get all the realEstateList where noFloors is greater than or equal to
    // UPDATED_NO_FLOORS
    defaultRealEstateShouldNotBeFound(
        "noFloors.greaterThanOrEqual=" + UPDATED_NO_FLOORS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoFloorsIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noFloors is less than or equal to
    // DEFAULT_NO_FLOORS
    defaultRealEstateShouldBeFound(
        "noFloors.lessThanOrEqual=" + DEFAULT_NO_FLOORS);

    // Get all the realEstateList where noFloors is less than or equal to
    // SMALLER_NO_FLOORS
    defaultRealEstateShouldNotBeFound(
        "noFloors.lessThanOrEqual=" + SMALLER_NO_FLOORS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoFloorsIsLessThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noFloors is less than DEFAULT_NO_FLOORS
    defaultRealEstateShouldNotBeFound("noFloors.lessThan=" + DEFAULT_NO_FLOORS);

    // Get all the realEstateList where noFloors is less than UPDATED_NO_FLOORS
    defaultRealEstateShouldBeFound("noFloors.lessThan=" + UPDATED_NO_FLOORS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoFloorsIsGreaterThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noFloors is greater than DEFAULT_NO_FLOORS
    defaultRealEstateShouldNotBeFound(
        "noFloors.greaterThan=" + DEFAULT_NO_FLOORS);

    // Get all the realEstateList where noFloors is greater than SMALLER_NO_FLOORS
    defaultRealEstateShouldBeFound("noFloors.greaterThan=" + SMALLER_NO_FLOORS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoPowerOutletsIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noPowerOutlets equals to
    // DEFAULT_NO_POWER_OUTLETS
    defaultRealEstateShouldBeFound(
        "noPowerOutlets.equals=" + DEFAULT_NO_POWER_OUTLETS);

    // Get all the realEstateList where noPowerOutlets equals to
    // UPDATED_NO_POWER_OUTLETS
    defaultRealEstateShouldNotBeFound(
        "noPowerOutlets.equals=" + UPDATED_NO_POWER_OUTLETS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoPowerOutletsIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noPowerOutlets in DEFAULT_NO_POWER_OUTLETS
    // or UPDATED_NO_POWER_OUTLETS
    defaultRealEstateShouldBeFound(
        "noPowerOutlets.in=" +
            DEFAULT_NO_POWER_OUTLETS +
            "," +
            UPDATED_NO_POWER_OUTLETS);

    // Get all the realEstateList where noPowerOutlets equals to
    // UPDATED_NO_POWER_OUTLETS
    defaultRealEstateShouldNotBeFound(
        "noPowerOutlets.in=" + UPDATED_NO_POWER_OUTLETS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoPowerOutletsIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noPowerOutlets is not null
    defaultRealEstateShouldBeFound("noPowerOutlets.specified=true");

    // Get all the realEstateList where noPowerOutlets is null
    defaultRealEstateShouldNotBeFound("noPowerOutlets.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoPowerOutletsIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noPowerOutlets is greater than or equal to
    // DEFAULT_NO_POWER_OUTLETS
    defaultRealEstateShouldBeFound(
        "noPowerOutlets.greaterThanOrEqual=" + DEFAULT_NO_POWER_OUTLETS);

    // Get all the realEstateList where noPowerOutlets is greater than or equal to
    // UPDATED_NO_POWER_OUTLETS
    defaultRealEstateShouldNotBeFound(
        "noPowerOutlets.greaterThanOrEqual=" + UPDATED_NO_POWER_OUTLETS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoPowerOutletsIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noPowerOutlets is less than or equal to
    // DEFAULT_NO_POWER_OUTLETS
    defaultRealEstateShouldBeFound(
        "noPowerOutlets.lessThanOrEqual=" + DEFAULT_NO_POWER_OUTLETS);

    // Get all the realEstateList where noPowerOutlets is less than or equal to
    // SMALLER_NO_POWER_OUTLETS
    defaultRealEstateShouldNotBeFound(
        "noPowerOutlets.lessThanOrEqual=" + SMALLER_NO_POWER_OUTLETS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoPowerOutletsIsLessThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noPowerOutlets is less than
    // DEFAULT_NO_POWER_OUTLETS
    defaultRealEstateShouldNotBeFound(
        "noPowerOutlets.lessThan=" + DEFAULT_NO_POWER_OUTLETS);

    // Get all the realEstateList where noPowerOutlets is less than
    // UPDATED_NO_POWER_OUTLETS
    defaultRealEstateShouldBeFound(
        "noPowerOutlets.lessThan=" + UPDATED_NO_POWER_OUTLETS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoPowerOutletsIsGreaterThanSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noPowerOutlets is greater than
    // DEFAULT_NO_POWER_OUTLETS
    defaultRealEstateShouldNotBeFound(
        "noPowerOutlets.greaterThan=" + DEFAULT_NO_POWER_OUTLETS);

    // Get all the realEstateList where noPowerOutlets is greater than
    // SMALLER_NO_POWER_OUTLETS
    defaultRealEstateShouldBeFound(
        "noPowerOutlets.greaterThan=" + SMALLER_NO_POWER_OUTLETS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoNetworkSocketsIsEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noNetworkSockets equals to
    // DEFAULT_NO_NETWORK_SOCKETS
    defaultRealEstateShouldBeFound(
        "noNetworkSockets.equals=" + DEFAULT_NO_NETWORK_SOCKETS);

    // Get all the realEstateList where noNetworkSockets equals to
    // UPDATED_NO_NETWORK_SOCKETS
    defaultRealEstateShouldNotBeFound(
        "noNetworkSockets.equals=" + UPDATED_NO_NETWORK_SOCKETS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoNetworkSocketsIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noNetworkSockets in
    // DEFAULT_NO_NETWORK_SOCKETS or UPDATED_NO_NETWORK_SOCKETS
    defaultRealEstateShouldBeFound(
        "noNetworkSockets.in=" +
            DEFAULT_NO_NETWORK_SOCKETS +
            "," +
            UPDATED_NO_NETWORK_SOCKETS);

    // Get all the realEstateList where noNetworkSockets equals to
    // UPDATED_NO_NETWORK_SOCKETS
    defaultRealEstateShouldNotBeFound(
        "noNetworkSockets.in=" + UPDATED_NO_NETWORK_SOCKETS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoNetworkSocketsIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noNetworkSockets is not null
    defaultRealEstateShouldBeFound("noNetworkSockets.specified=true");

    // Get all the realEstateList where noNetworkSockets is null
    defaultRealEstateShouldNotBeFound("noNetworkSockets.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoNetworkSocketsIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noNetworkSockets is greater than or equal to
    // DEFAULT_NO_NETWORK_SOCKETS
    defaultRealEstateShouldBeFound(
        "noNetworkSockets.greaterThanOrEqual=" + DEFAULT_NO_NETWORK_SOCKETS);

    // Get all the realEstateList where noNetworkSockets is greater than or equal to
    // UPDATED_NO_NETWORK_SOCKETS
    defaultRealEstateShouldNotBeFound(
        "noNetworkSockets.greaterThanOrEqual=" + UPDATED_NO_NETWORK_SOCKETS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoNetworkSocketsIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noNetworkSockets is less than or equal to
    // DEFAULT_NO_NETWORK_SOCKETS
    defaultRealEstateShouldBeFound(
        "noNetworkSockets.lessThanOrEqual=" + DEFAULT_NO_NETWORK_SOCKETS);

    // Get all the realEstateList where noNetworkSockets is less than or equal to
    // SMALLER_NO_NETWORK_SOCKETS
    defaultRealEstateShouldNotBeFound(
        "noNetworkSockets.lessThanOrEqual=" + SMALLER_NO_NETWORK_SOCKETS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoNetworkSocketsIsLessThanSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noNetworkSockets is less than
    // DEFAULT_NO_NETWORK_SOCKETS
    defaultRealEstateShouldNotBeFound(
        "noNetworkSockets.lessThan=" + DEFAULT_NO_NETWORK_SOCKETS);

    // Get all the realEstateList where noNetworkSockets is less than
    // UPDATED_NO_NETWORK_SOCKETS
    defaultRealEstateShouldBeFound(
        "noNetworkSockets.lessThan=" + UPDATED_NO_NETWORK_SOCKETS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoNetworkSocketsIsGreaterThanSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noNetworkSockets is greater than
    // DEFAULT_NO_NETWORK_SOCKETS
    defaultRealEstateShouldNotBeFound(
        "noNetworkSockets.greaterThan=" + DEFAULT_NO_NETWORK_SOCKETS);

    // Get all the realEstateList where noNetworkSockets is greater than
    // SMALLER_NO_NETWORK_SOCKETS
    defaultRealEstateShouldBeFound(
        "noNetworkSockets.greaterThan=" + SMALLER_NO_NETWORK_SOCKETS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoLightSwitchesIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noLightSwitches equals to
    // DEFAULT_NO_LIGHT_SWITCHES
    defaultRealEstateShouldBeFound(
        "noLightSwitches.equals=" + DEFAULT_NO_LIGHT_SWITCHES);

    // Get all the realEstateList where noLightSwitches equals to
    // UPDATED_NO_LIGHT_SWITCHES
    defaultRealEstateShouldNotBeFound(
        "noLightSwitches.equals=" + UPDATED_NO_LIGHT_SWITCHES);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoLightSwitchesIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noLightSwitches in DEFAULT_NO_LIGHT_SWITCHES
    // or UPDATED_NO_LIGHT_SWITCHES
    defaultRealEstateShouldBeFound(
        "noLightSwitches.in=" +
            DEFAULT_NO_LIGHT_SWITCHES +
            "," +
            UPDATED_NO_LIGHT_SWITCHES);

    // Get all the realEstateList where noLightSwitches equals to
    // UPDATED_NO_LIGHT_SWITCHES
    defaultRealEstateShouldNotBeFound(
        "noLightSwitches.in=" + UPDATED_NO_LIGHT_SWITCHES);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoLightSwitchesIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noLightSwitches is not null
    defaultRealEstateShouldBeFound("noLightSwitches.specified=true");

    // Get all the realEstateList where noLightSwitches is null
    defaultRealEstateShouldNotBeFound("noLightSwitches.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoLightSwitchesIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noLightSwitches is greater than or equal to
    // DEFAULT_NO_LIGHT_SWITCHES
    defaultRealEstateShouldBeFound(
        "noLightSwitches.greaterThanOrEqual=" + DEFAULT_NO_LIGHT_SWITCHES);

    // Get all the realEstateList where noLightSwitches is greater than or equal to
    // UPDATED_NO_LIGHT_SWITCHES
    defaultRealEstateShouldNotBeFound(
        "noLightSwitches.greaterThanOrEqual=" + UPDATED_NO_LIGHT_SWITCHES);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoLightSwitchesIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noLightSwitches is less than or equal to
    // DEFAULT_NO_LIGHT_SWITCHES
    defaultRealEstateShouldBeFound(
        "noLightSwitches.lessThanOrEqual=" + DEFAULT_NO_LIGHT_SWITCHES);

    // Get all the realEstateList where noLightSwitches is less than or equal to
    // SMALLER_NO_LIGHT_SWITCHES
    defaultRealEstateShouldNotBeFound(
        "noLightSwitches.lessThanOrEqual=" + SMALLER_NO_LIGHT_SWITCHES);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoLightSwitchesIsLessThanSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noLightSwitches is less than
    // DEFAULT_NO_LIGHT_SWITCHES
    defaultRealEstateShouldNotBeFound(
        "noLightSwitches.lessThan=" + DEFAULT_NO_LIGHT_SWITCHES);

    // Get all the realEstateList where noLightSwitches is less than
    // UPDATED_NO_LIGHT_SWITCHES
    defaultRealEstateShouldBeFound(
        "noLightSwitches.lessThan=" + UPDATED_NO_LIGHT_SWITCHES);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoLightSwitchesIsGreaterThanSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noLightSwitches is greater than
    // DEFAULT_NO_LIGHT_SWITCHES
    defaultRealEstateShouldNotBeFound(
        "noLightSwitches.greaterThan=" + DEFAULT_NO_LIGHT_SWITCHES);

    // Get all the realEstateList where noLightSwitches is greater than
    // SMALLER_NO_LIGHT_SWITCHES
    defaultRealEstateShouldBeFound(
        "noLightSwitches.greaterThan=" + SMALLER_NO_LIGHT_SWITCHES);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoAntennasIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noAntennas equals to DEFAULT_NO_ANTENNAS
    defaultRealEstateShouldBeFound("noAntennas.equals=" + DEFAULT_NO_ANTENNAS);

    // Get all the realEstateList where noAntennas equals to UPDATED_NO_ANTENNAS
    defaultRealEstateShouldNotBeFound(
        "noAntennas.equals=" + UPDATED_NO_ANTENNAS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoAntennasIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noAntennas in DEFAULT_NO_ANTENNAS or
    // UPDATED_NO_ANTENNAS
    defaultRealEstateShouldBeFound(
        "noAntennas.in=" + DEFAULT_NO_ANTENNAS + "," + UPDATED_NO_ANTENNAS);

    // Get all the realEstateList where noAntennas equals to UPDATED_NO_ANTENNAS
    defaultRealEstateShouldNotBeFound("noAntennas.in=" + UPDATED_NO_ANTENNAS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoAntennasIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noAntennas is not null
    defaultRealEstateShouldBeFound("noAntennas.specified=true");

    // Get all the realEstateList where noAntennas is null
    defaultRealEstateShouldNotBeFound("noAntennas.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoAntennasIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noAntennas is greater than or equal to
    // DEFAULT_NO_ANTENNAS
    defaultRealEstateShouldBeFound(
        "noAntennas.greaterThanOrEqual=" + DEFAULT_NO_ANTENNAS);

    // Get all the realEstateList where noAntennas is greater than or equal to
    // UPDATED_NO_ANTENNAS
    defaultRealEstateShouldNotBeFound(
        "noAntennas.greaterThanOrEqual=" + UPDATED_NO_ANTENNAS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoAntennasIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noAntennas is less than or equal to
    // DEFAULT_NO_ANTENNAS
    defaultRealEstateShouldBeFound(
        "noAntennas.lessThanOrEqual=" + DEFAULT_NO_ANTENNAS);

    // Get all the realEstateList where noAntennas is less than or equal to
    // SMALLER_NO_ANTENNAS
    defaultRealEstateShouldNotBeFound(
        "noAntennas.lessThanOrEqual=" + SMALLER_NO_ANTENNAS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoAntennasIsLessThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noAntennas is less than DEFAULT_NO_ANTENNAS
    defaultRealEstateShouldNotBeFound(
        "noAntennas.lessThan=" + DEFAULT_NO_ANTENNAS);

    // Get all the realEstateList where noAntennas is less than UPDATED_NO_ANTENNAS
    defaultRealEstateShouldBeFound(
        "noAntennas.lessThan=" + UPDATED_NO_ANTENNAS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoAntennasIsGreaterThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noAntennas is greater than
    // DEFAULT_NO_ANTENNAS
    defaultRealEstateShouldNotBeFound(
        "noAntennas.greaterThan=" + DEFAULT_NO_ANTENNAS);

    // Get all the realEstateList where noAntennas is greater than
    // SMALLER_NO_ANTENNAS
    defaultRealEstateShouldBeFound(
        "noAntennas.greaterThan=" + SMALLER_NO_ANTENNAS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoShutterSwitchesIsEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noShutterSwitches equals to
    // DEFAULT_NO_SHUTTER_SWITCHES
    defaultRealEstateShouldBeFound(
        "noShutterSwitches.equals=" + DEFAULT_NO_SHUTTER_SWITCHES);

    // Get all the realEstateList where noShutterSwitches equals to
    // UPDATED_NO_SHUTTER_SWITCHES
    defaultRealEstateShouldNotBeFound(
        "noShutterSwitches.equals=" + UPDATED_NO_SHUTTER_SWITCHES);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoShutterSwitchesIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noShutterSwitches in
    // DEFAULT_NO_SHUTTER_SWITCHES or UPDATED_NO_SHUTTER_SWITCHES
    defaultRealEstateShouldBeFound(
        "noShutterSwitches.in=" +
            DEFAULT_NO_SHUTTER_SWITCHES +
            "," +
            UPDATED_NO_SHUTTER_SWITCHES);

    // Get all the realEstateList where noShutterSwitches equals to
    // UPDATED_NO_SHUTTER_SWITCHES
    defaultRealEstateShouldNotBeFound(
        "noShutterSwitches.in=" + UPDATED_NO_SHUTTER_SWITCHES);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoShutterSwitchesIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noShutterSwitches is not null
    defaultRealEstateShouldBeFound("noShutterSwitches.specified=true");

    // Get all the realEstateList where noShutterSwitches is null
    defaultRealEstateShouldNotBeFound("noShutterSwitches.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoShutterSwitchesIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noShutterSwitches is greater than or equal
    // to DEFAULT_NO_SHUTTER_SWITCHES
    defaultRealEstateShouldBeFound(
        "noShutterSwitches.greaterThanOrEqual=" + DEFAULT_NO_SHUTTER_SWITCHES);

    // Get all the realEstateList where noShutterSwitches is greater than or equal
    // to UPDATED_NO_SHUTTER_SWITCHES
    defaultRealEstateShouldNotBeFound(
        "noShutterSwitches.greaterThanOrEqual=" + UPDATED_NO_SHUTTER_SWITCHES);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoShutterSwitchesIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noShutterSwitches is less than or equal to
    // DEFAULT_NO_SHUTTER_SWITCHES
    defaultRealEstateShouldBeFound(
        "noShutterSwitches.lessThanOrEqual=" + DEFAULT_NO_SHUTTER_SWITCHES);

    // Get all the realEstateList where noShutterSwitches is less than or equal to
    // SMALLER_NO_SHUTTER_SWITCHES
    defaultRealEstateShouldNotBeFound(
        "noShutterSwitches.lessThanOrEqual=" + SMALLER_NO_SHUTTER_SWITCHES);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoShutterSwitchesIsLessThanSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noShutterSwitches is less than
    // DEFAULT_NO_SHUTTER_SWITCHES
    defaultRealEstateShouldNotBeFound(
        "noShutterSwitches.lessThan=" + DEFAULT_NO_SHUTTER_SWITCHES);

    // Get all the realEstateList where noShutterSwitches is less than
    // UPDATED_NO_SHUTTER_SWITCHES
    defaultRealEstateShouldBeFound(
        "noShutterSwitches.lessThan=" + UPDATED_NO_SHUTTER_SWITCHES);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoShutterSwitchesIsGreaterThanSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noShutterSwitches is greater than
    // DEFAULT_NO_SHUTTER_SWITCHES
    defaultRealEstateShouldNotBeFound(
        "noShutterSwitches.greaterThan=" + DEFAULT_NO_SHUTTER_SWITCHES);

    // Get all the realEstateList where noShutterSwitches is greater than
    // SMALLER_NO_SHUTTER_SWITCHES
    defaultRealEstateShouldBeFound(
        "noShutterSwitches.greaterThan=" + SMALLER_NO_SHUTTER_SWITCHES);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoRadiatorsIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noRadiators equals to DEFAULT_NO_RADIATORS
    defaultRealEstateShouldBeFound(
        "noRadiators.equals=" + DEFAULT_NO_RADIATORS);

    // Get all the realEstateList where noRadiators equals to UPDATED_NO_RADIATORS
    defaultRealEstateShouldNotBeFound(
        "noRadiators.equals=" + UPDATED_NO_RADIATORS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoRadiatorsIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noRadiators in DEFAULT_NO_RADIATORS or
    // UPDATED_NO_RADIATORS
    defaultRealEstateShouldBeFound(
        "noRadiators.in=" + DEFAULT_NO_RADIATORS + "," + UPDATED_NO_RADIATORS);

    // Get all the realEstateList where noRadiators equals to UPDATED_NO_RADIATORS
    defaultRealEstateShouldNotBeFound("noRadiators.in=" + UPDATED_NO_RADIATORS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoRadiatorsIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noRadiators is not null
    defaultRealEstateShouldBeFound("noRadiators.specified=true");

    // Get all the realEstateList where noRadiators is null
    defaultRealEstateShouldNotBeFound("noRadiators.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoRadiatorsIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noRadiators is greater than or equal to
    // DEFAULT_NO_RADIATORS
    defaultRealEstateShouldBeFound(
        "noRadiators.greaterThanOrEqual=" + DEFAULT_NO_RADIATORS);

    // Get all the realEstateList where noRadiators is greater than or equal to
    // UPDATED_NO_RADIATORS
    defaultRealEstateShouldNotBeFound(
        "noRadiators.greaterThanOrEqual=" + UPDATED_NO_RADIATORS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoRadiatorsIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noRadiators is less than or equal to
    // DEFAULT_NO_RADIATORS
    defaultRealEstateShouldBeFound(
        "noRadiators.lessThanOrEqual=" + DEFAULT_NO_RADIATORS);

    // Get all the realEstateList where noRadiators is less than or equal to
    // SMALLER_NO_RADIATORS
    defaultRealEstateShouldNotBeFound(
        "noRadiators.lessThanOrEqual=" + SMALLER_NO_RADIATORS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoRadiatorsIsLessThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noRadiators is less than
    // DEFAULT_NO_RADIATORS
    defaultRealEstateShouldNotBeFound(
        "noRadiators.lessThan=" + DEFAULT_NO_RADIATORS);

    // Get all the realEstateList where noRadiators is less than
    // UPDATED_NO_RADIATORS
    defaultRealEstateShouldBeFound(
        "noRadiators.lessThan=" + UPDATED_NO_RADIATORS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoRadiatorsIsGreaterThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noRadiators is greater than
    // DEFAULT_NO_RADIATORS
    defaultRealEstateShouldNotBeFound(
        "noRadiators.greaterThan=" + DEFAULT_NO_RADIATORS);

    // Get all the realEstateList where noRadiators is greater than
    // SMALLER_NO_RADIATORS
    defaultRealEstateShouldBeFound(
        "noRadiators.greaterThan=" + SMALLER_NO_RADIATORS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoParkingsIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noParkings equals to DEFAULT_NO_PARKINGS
    defaultRealEstateShouldBeFound("noParkings.equals=" + DEFAULT_NO_PARKINGS);

    // Get all the realEstateList where noParkings equals to UPDATED_NO_PARKINGS
    defaultRealEstateShouldNotBeFound(
        "noParkings.equals=" + UPDATED_NO_PARKINGS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoParkingsIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noParkings in DEFAULT_NO_PARKINGS or
    // UPDATED_NO_PARKINGS
    defaultRealEstateShouldBeFound(
        "noParkings.in=" + DEFAULT_NO_PARKINGS + "," + UPDATED_NO_PARKINGS);

    // Get all the realEstateList where noParkings equals to UPDATED_NO_PARKINGS
    defaultRealEstateShouldNotBeFound("noParkings.in=" + UPDATED_NO_PARKINGS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoParkingsIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noParkings is not null
    defaultRealEstateShouldBeFound("noParkings.specified=true");

    // Get all the realEstateList where noParkings is null
    defaultRealEstateShouldNotBeFound("noParkings.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoParkingsIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noParkings is greater than or equal to
    // DEFAULT_NO_PARKINGS
    defaultRealEstateShouldBeFound(
        "noParkings.greaterThanOrEqual=" + DEFAULT_NO_PARKINGS);

    // Get all the realEstateList where noParkings is greater than or equal to
    // UPDATED_NO_PARKINGS
    defaultRealEstateShouldNotBeFound(
        "noParkings.greaterThanOrEqual=" + UPDATED_NO_PARKINGS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoParkingsIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noParkings is less than or equal to
    // DEFAULT_NO_PARKINGS
    defaultRealEstateShouldBeFound(
        "noParkings.lessThanOrEqual=" + DEFAULT_NO_PARKINGS);

    // Get all the realEstateList where noParkings is less than or equal to
    // SMALLER_NO_PARKINGS
    defaultRealEstateShouldNotBeFound(
        "noParkings.lessThanOrEqual=" + SMALLER_NO_PARKINGS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoParkingsIsLessThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noParkings is less than DEFAULT_NO_PARKINGS
    defaultRealEstateShouldNotBeFound(
        "noParkings.lessThan=" + DEFAULT_NO_PARKINGS);

    // Get all the realEstateList where noParkings is less than UPDATED_NO_PARKINGS
    defaultRealEstateShouldBeFound(
        "noParkings.lessThan=" + UPDATED_NO_PARKINGS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoParkingsIsGreaterThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noParkings is greater than
    // DEFAULT_NO_PARKINGS
    defaultRealEstateShouldNotBeFound(
        "noParkings.greaterThan=" + DEFAULT_NO_PARKINGS);

    // Get all the realEstateList where noParkings is greater than
    // SMALLER_NO_PARKINGS
    defaultRealEstateShouldBeFound(
        "noParkings.greaterThan=" + SMALLER_NO_PARKINGS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoGaragesIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noGarages equals to DEFAULT_NO_GARAGES
    defaultRealEstateShouldBeFound("noGarages.equals=" + DEFAULT_NO_GARAGES);

    // Get all the realEstateList where noGarages equals to UPDATED_NO_GARAGES
    defaultRealEstateShouldNotBeFound("noGarages.equals=" + UPDATED_NO_GARAGES);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoGaragesIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noGarages in DEFAULT_NO_GARAGES or
    // UPDATED_NO_GARAGES
    defaultRealEstateShouldBeFound(
        "noGarages.in=" + DEFAULT_NO_GARAGES + "," + UPDATED_NO_GARAGES);

    // Get all the realEstateList where noGarages equals to UPDATED_NO_GARAGES
    defaultRealEstateShouldNotBeFound("noGarages.in=" + UPDATED_NO_GARAGES);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoGaragesIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noGarages is not null
    defaultRealEstateShouldBeFound("noGarages.specified=true");

    // Get all the realEstateList where noGarages is null
    defaultRealEstateShouldNotBeFound("noGarages.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoGaragesIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noGarages is greater than or equal to
    // DEFAULT_NO_GARAGES
    defaultRealEstateShouldBeFound(
        "noGarages.greaterThanOrEqual=" + DEFAULT_NO_GARAGES);

    // Get all the realEstateList where noGarages is greater than or equal to
    // UPDATED_NO_GARAGES
    defaultRealEstateShouldNotBeFound(
        "noGarages.greaterThanOrEqual=" + UPDATED_NO_GARAGES);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoGaragesIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noGarages is less than or equal to
    // DEFAULT_NO_GARAGES
    defaultRealEstateShouldBeFound(
        "noGarages.lessThanOrEqual=" + DEFAULT_NO_GARAGES);

    // Get all the realEstateList where noGarages is less than or equal to
    // SMALLER_NO_GARAGES
    defaultRealEstateShouldNotBeFound(
        "noGarages.lessThanOrEqual=" + SMALLER_NO_GARAGES);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoGaragesIsLessThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noGarages is less than DEFAULT_NO_GARAGES
    defaultRealEstateShouldNotBeFound(
        "noGarages.lessThan=" + DEFAULT_NO_GARAGES);

    // Get all the realEstateList where noGarages is less than UPDATED_NO_GARAGES
    defaultRealEstateShouldBeFound("noGarages.lessThan=" + UPDATED_NO_GARAGES);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoGaragesIsGreaterThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noGarages is greater than DEFAULT_NO_GARAGES
    defaultRealEstateShouldNotBeFound(
        "noGarages.greaterThan=" + DEFAULT_NO_GARAGES);

    // Get all the realEstateList where noGarages is greater than SMALLER_NO_GARAGES
    defaultRealEstateShouldBeFound(
        "noGarages.greaterThan=" + SMALLER_NO_GARAGES);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoCarportsIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noCarports equals to DEFAULT_NO_CARPORTS
    defaultRealEstateShouldBeFound("noCarports.equals=" + DEFAULT_NO_CARPORTS);

    // Get all the realEstateList where noCarports equals to UPDATED_NO_CARPORTS
    defaultRealEstateShouldNotBeFound(
        "noCarports.equals=" + UPDATED_NO_CARPORTS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoCarportsIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noCarports in DEFAULT_NO_CARPORTS or
    // UPDATED_NO_CARPORTS
    defaultRealEstateShouldBeFound(
        "noCarports.in=" + DEFAULT_NO_CARPORTS + "," + UPDATED_NO_CARPORTS);

    // Get all the realEstateList where noCarports equals to UPDATED_NO_CARPORTS
    defaultRealEstateShouldNotBeFound("noCarports.in=" + UPDATED_NO_CARPORTS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoCarportsIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noCarports is not null
    defaultRealEstateShouldBeFound("noCarports.specified=true");

    // Get all the realEstateList where noCarports is null
    defaultRealEstateShouldNotBeFound("noCarports.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoCarportsIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noCarports is greater than or equal to
    // DEFAULT_NO_CARPORTS
    defaultRealEstateShouldBeFound(
        "noCarports.greaterThanOrEqual=" + DEFAULT_NO_CARPORTS);

    // Get all the realEstateList where noCarports is greater than or equal to
    // UPDATED_NO_CARPORTS
    defaultRealEstateShouldNotBeFound(
        "noCarports.greaterThanOrEqual=" + UPDATED_NO_CARPORTS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoCarportsIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noCarports is less than or equal to
    // DEFAULT_NO_CARPORTS
    defaultRealEstateShouldBeFound(
        "noCarports.lessThanOrEqual=" + DEFAULT_NO_CARPORTS);

    // Get all the realEstateList where noCarports is less than or equal to
    // SMALLER_NO_CARPORTS
    defaultRealEstateShouldNotBeFound(
        "noCarports.lessThanOrEqual=" + SMALLER_NO_CARPORTS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoCarportsIsLessThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noCarports is less than DEFAULT_NO_CARPORTS
    defaultRealEstateShouldNotBeFound(
        "noCarports.lessThan=" + DEFAULT_NO_CARPORTS);

    // Get all the realEstateList where noCarports is less than UPDATED_NO_CARPORTS
    defaultRealEstateShouldBeFound(
        "noCarports.lessThan=" + UPDATED_NO_CARPORTS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoCarportsIsGreaterThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noCarports is greater than
    // DEFAULT_NO_CARPORTS
    defaultRealEstateShouldNotBeFound(
        "noCarports.greaterThan=" + DEFAULT_NO_CARPORTS);

    // Get all the realEstateList where noCarports is greater than
    // SMALLER_NO_CARPORTS
    defaultRealEstateShouldBeFound(
        "noCarports.greaterThan=" + SMALLER_NO_CARPORTS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoWindowsIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noWindows equals to DEFAULT_NO_WINDOWS
    defaultRealEstateShouldBeFound("noWindows.equals=" + DEFAULT_NO_WINDOWS);

    // Get all the realEstateList where noWindows equals to UPDATED_NO_WINDOWS
    defaultRealEstateShouldNotBeFound("noWindows.equals=" + UPDATED_NO_WINDOWS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoWindowsIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noWindows in DEFAULT_NO_WINDOWS or
    // UPDATED_NO_WINDOWS
    defaultRealEstateShouldBeFound(
        "noWindows.in=" + DEFAULT_NO_WINDOWS + "," + UPDATED_NO_WINDOWS);

    // Get all the realEstateList where noWindows equals to UPDATED_NO_WINDOWS
    defaultRealEstateShouldNotBeFound("noWindows.in=" + UPDATED_NO_WINDOWS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoWindowsIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noWindows is not null
    defaultRealEstateShouldBeFound("noWindows.specified=true");

    // Get all the realEstateList where noWindows is null
    defaultRealEstateShouldNotBeFound("noWindows.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoWindowsIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noWindows is greater than or equal to
    // DEFAULT_NO_WINDOWS
    defaultRealEstateShouldBeFound(
        "noWindows.greaterThanOrEqual=" + DEFAULT_NO_WINDOWS);

    // Get all the realEstateList where noWindows is greater than or equal to
    // UPDATED_NO_WINDOWS
    defaultRealEstateShouldNotBeFound(
        "noWindows.greaterThanOrEqual=" + UPDATED_NO_WINDOWS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoWindowsIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noWindows is less than or equal to
    // DEFAULT_NO_WINDOWS
    defaultRealEstateShouldBeFound(
        "noWindows.lessThanOrEqual=" + DEFAULT_NO_WINDOWS);

    // Get all the realEstateList where noWindows is less than or equal to
    // SMALLER_NO_WINDOWS
    defaultRealEstateShouldNotBeFound(
        "noWindows.lessThanOrEqual=" + SMALLER_NO_WINDOWS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoWindowsIsLessThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noWindows is less than DEFAULT_NO_WINDOWS
    defaultRealEstateShouldNotBeFound(
        "noWindows.lessThan=" + DEFAULT_NO_WINDOWS);

    // Get all the realEstateList where noWindows is less than UPDATED_NO_WINDOWS
    defaultRealEstateShouldBeFound("noWindows.lessThan=" + UPDATED_NO_WINDOWS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoWindowsIsGreaterThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noWindows is greater than DEFAULT_NO_WINDOWS
    defaultRealEstateShouldNotBeFound(
        "noWindows.greaterThan=" + DEFAULT_NO_WINDOWS);

    // Get all the realEstateList where noWindows is greater than SMALLER_NO_WINDOWS
    defaultRealEstateShouldBeFound(
        "noWindows.greaterThan=" + SMALLER_NO_WINDOWS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByWindowAreaIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where windowArea equals to DEFAULT_WINDOW_AREA
    defaultRealEstateShouldBeFound("windowArea.equals=" + DEFAULT_WINDOW_AREA);

    // Get all the realEstateList where windowArea equals to UPDATED_WINDOW_AREA
    defaultRealEstateShouldNotBeFound(
        "windowArea.equals=" + UPDATED_WINDOW_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByWindowAreaIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where windowArea in DEFAULT_WINDOW_AREA or
    // UPDATED_WINDOW_AREA
    defaultRealEstateShouldBeFound(
        "windowArea.in=" + DEFAULT_WINDOW_AREA + "," + UPDATED_WINDOW_AREA);

    // Get all the realEstateList where windowArea equals to UPDATED_WINDOW_AREA
    defaultRealEstateShouldNotBeFound("windowArea.in=" + UPDATED_WINDOW_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByWindowAreaIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where windowArea is not null
    defaultRealEstateShouldBeFound("windowArea.specified=true");

    // Get all the realEstateList where windowArea is null
    defaultRealEstateShouldNotBeFound("windowArea.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByWindowAreaIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where windowArea is greater than or equal to
    // DEFAULT_WINDOW_AREA
    defaultRealEstateShouldBeFound(
        "windowArea.greaterThanOrEqual=" + DEFAULT_WINDOW_AREA);

    // Get all the realEstateList where windowArea is greater than or equal to
    // UPDATED_WINDOW_AREA
    defaultRealEstateShouldNotBeFound(
        "windowArea.greaterThanOrEqual=" + UPDATED_WINDOW_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByWindowAreaIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where windowArea is less than or equal to
    // DEFAULT_WINDOW_AREA
    defaultRealEstateShouldBeFound(
        "windowArea.lessThanOrEqual=" + DEFAULT_WINDOW_AREA);

    // Get all the realEstateList where windowArea is less than or equal to
    // SMALLER_WINDOW_AREA
    defaultRealEstateShouldNotBeFound(
        "windowArea.lessThanOrEqual=" + SMALLER_WINDOW_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByWindowAreaIsLessThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where windowArea is less than DEFAULT_WINDOW_AREA
    defaultRealEstateShouldNotBeFound(
        "windowArea.lessThan=" + DEFAULT_WINDOW_AREA);

    // Get all the realEstateList where windowArea is less than UPDATED_WINDOW_AREA
    defaultRealEstateShouldBeFound(
        "windowArea.lessThan=" + UPDATED_WINDOW_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByWindowAreaIsGreaterThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where windowArea is greater than
    // DEFAULT_WINDOW_AREA
    defaultRealEstateShouldNotBeFound(
        "windowArea.greaterThan=" + DEFAULT_WINDOW_AREA);

    // Get all the realEstateList where windowArea is greater than
    // SMALLER_WINDOW_AREA
    defaultRealEstateShouldBeFound(
        "windowArea.greaterThan=" + SMALLER_WINDOW_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoElevatorsIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noElevators equals to DEFAULT_NO_ELEVATORS
    defaultRealEstateShouldBeFound(
        "noElevators.equals=" + DEFAULT_NO_ELEVATORS);

    // Get all the realEstateList where noElevators equals to UPDATED_NO_ELEVATORS
    defaultRealEstateShouldNotBeFound(
        "noElevators.equals=" + UPDATED_NO_ELEVATORS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoElevatorsIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noElevators in DEFAULT_NO_ELEVATORS or
    // UPDATED_NO_ELEVATORS
    defaultRealEstateShouldBeFound(
        "noElevators.in=" + DEFAULT_NO_ELEVATORS + "," + UPDATED_NO_ELEVATORS);

    // Get all the realEstateList where noElevators equals to UPDATED_NO_ELEVATORS
    defaultRealEstateShouldNotBeFound("noElevators.in=" + UPDATED_NO_ELEVATORS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoElevatorsIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noElevators is not null
    defaultRealEstateShouldBeFound("noElevators.specified=true");

    // Get all the realEstateList where noElevators is null
    defaultRealEstateShouldNotBeFound("noElevators.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoElevatorsIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noElevators is greater than or equal to
    // DEFAULT_NO_ELEVATORS
    defaultRealEstateShouldBeFound(
        "noElevators.greaterThanOrEqual=" + DEFAULT_NO_ELEVATORS);

    // Get all the realEstateList where noElevators is greater than or equal to
    // UPDATED_NO_ELEVATORS
    defaultRealEstateShouldNotBeFound(
        "noElevators.greaterThanOrEqual=" + UPDATED_NO_ELEVATORS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoElevatorsIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noElevators is less than or equal to
    // DEFAULT_NO_ELEVATORS
    defaultRealEstateShouldBeFound(
        "noElevators.lessThanOrEqual=" + DEFAULT_NO_ELEVATORS);

    // Get all the realEstateList where noElevators is less than or equal to
    // SMALLER_NO_ELEVATORS
    defaultRealEstateShouldNotBeFound(
        "noElevators.lessThanOrEqual=" + SMALLER_NO_ELEVATORS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoElevatorsIsLessThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noElevators is less than
    // DEFAULT_NO_ELEVATORS
    defaultRealEstateShouldNotBeFound(
        "noElevators.lessThan=" + DEFAULT_NO_ELEVATORS);

    // Get all the realEstateList where noElevators is less than
    // UPDATED_NO_ELEVATORS
    defaultRealEstateShouldBeFound(
        "noElevators.lessThan=" + UPDATED_NO_ELEVATORS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoElevatorsIsGreaterThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noElevators is greater than
    // DEFAULT_NO_ELEVATORS
    defaultRealEstateShouldNotBeFound(
        "noElevators.greaterThan=" + DEFAULT_NO_ELEVATORS);

    // Get all the realEstateList where noElevators is greater than
    // SMALLER_NO_ELEVATORS
    defaultRealEstateShouldBeFound(
        "noElevators.greaterThan=" + SMALLER_NO_ELEVATORS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByCorridorAreaIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where corridorArea equals to DEFAULT_CORRIDOR_AREA
    defaultRealEstateShouldBeFound(
        "corridorArea.equals=" + DEFAULT_CORRIDOR_AREA);

    // Get all the realEstateList where corridorArea equals to UPDATED_CORRIDOR_AREA
    defaultRealEstateShouldNotBeFound(
        "corridorArea.equals=" + UPDATED_CORRIDOR_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByCorridorAreaIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where corridorArea in DEFAULT_CORRIDOR_AREA or
    // UPDATED_CORRIDOR_AREA
    defaultRealEstateShouldBeFound(
        "corridorArea.in=" + DEFAULT_CORRIDOR_AREA + "," + UPDATED_CORRIDOR_AREA);

    // Get all the realEstateList where corridorArea equals to UPDATED_CORRIDOR_AREA
    defaultRealEstateShouldNotBeFound(
        "corridorArea.in=" + UPDATED_CORRIDOR_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByCorridorAreaIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where corridorArea is not null
    defaultRealEstateShouldBeFound("corridorArea.specified=true");

    // Get all the realEstateList where corridorArea is null
    defaultRealEstateShouldNotBeFound("corridorArea.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByCorridorAreaIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where corridorArea is greater than or equal to
    // DEFAULT_CORRIDOR_AREA
    defaultRealEstateShouldBeFound(
        "corridorArea.greaterThanOrEqual=" + DEFAULT_CORRIDOR_AREA);

    // Get all the realEstateList where corridorArea is greater than or equal to
    // UPDATED_CORRIDOR_AREA
    defaultRealEstateShouldNotBeFound(
        "corridorArea.greaterThanOrEqual=" + UPDATED_CORRIDOR_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByCorridorAreaIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where corridorArea is less than or equal to
    // DEFAULT_CORRIDOR_AREA
    defaultRealEstateShouldBeFound(
        "corridorArea.lessThanOrEqual=" + DEFAULT_CORRIDOR_AREA);

    // Get all the realEstateList where corridorArea is less than or equal to
    // SMALLER_CORRIDOR_AREA
    defaultRealEstateShouldNotBeFound(
        "corridorArea.lessThanOrEqual=" + SMALLER_CORRIDOR_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByCorridorAreaIsLessThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where corridorArea is less than
    // DEFAULT_CORRIDOR_AREA
    defaultRealEstateShouldNotBeFound(
        "corridorArea.lessThan=" + DEFAULT_CORRIDOR_AREA);

    // Get all the realEstateList where corridorArea is less than
    // UPDATED_CORRIDOR_AREA
    defaultRealEstateShouldBeFound(
        "corridorArea.lessThan=" + UPDATED_CORRIDOR_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByCorridorAreaIsGreaterThanSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where corridorArea is greater than
    // DEFAULT_CORRIDOR_AREA
    defaultRealEstateShouldNotBeFound(
        "corridorArea.greaterThan=" + DEFAULT_CORRIDOR_AREA);

    // Get all the realEstateList where corridorArea is greater than
    // SMALLER_CORRIDOR_AREA
    defaultRealEstateShouldBeFound(
        "corridorArea.greaterThan=" + SMALLER_CORRIDOR_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoBasementRoomsIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noBasementRooms equals to
    // DEFAULT_NO_BASEMENT_ROOMS
    defaultRealEstateShouldBeFound(
        "noBasementRooms.equals=" + DEFAULT_NO_BASEMENT_ROOMS);

    // Get all the realEstateList where noBasementRooms equals to
    // UPDATED_NO_BASEMENT_ROOMS
    defaultRealEstateShouldNotBeFound(
        "noBasementRooms.equals=" + UPDATED_NO_BASEMENT_ROOMS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoBasementRoomsIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noBasementRooms in DEFAULT_NO_BASEMENT_ROOMS
    // or UPDATED_NO_BASEMENT_ROOMS
    defaultRealEstateShouldBeFound(
        "noBasementRooms.in=" +
            DEFAULT_NO_BASEMENT_ROOMS +
            "," +
            UPDATED_NO_BASEMENT_ROOMS);

    // Get all the realEstateList where noBasementRooms equals to
    // UPDATED_NO_BASEMENT_ROOMS
    defaultRealEstateShouldNotBeFound(
        "noBasementRooms.in=" + UPDATED_NO_BASEMENT_ROOMS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoBasementRoomsIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noBasementRooms is not null
    defaultRealEstateShouldBeFound("noBasementRooms.specified=true");

    // Get all the realEstateList where noBasementRooms is null
    defaultRealEstateShouldNotBeFound("noBasementRooms.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoBasementRoomsIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noBasementRooms is greater than or equal to
    // DEFAULT_NO_BASEMENT_ROOMS
    defaultRealEstateShouldBeFound(
        "noBasementRooms.greaterThanOrEqual=" + DEFAULT_NO_BASEMENT_ROOMS);

    // Get all the realEstateList where noBasementRooms is greater than or equal to
    // UPDATED_NO_BASEMENT_ROOMS
    defaultRealEstateShouldNotBeFound(
        "noBasementRooms.greaterThanOrEqual=" + UPDATED_NO_BASEMENT_ROOMS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoBasementRoomsIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noBasementRooms is less than or equal to
    // DEFAULT_NO_BASEMENT_ROOMS
    defaultRealEstateShouldBeFound(
        "noBasementRooms.lessThanOrEqual=" + DEFAULT_NO_BASEMENT_ROOMS);

    // Get all the realEstateList where noBasementRooms is less than or equal to
    // SMALLER_NO_BASEMENT_ROOMS
    defaultRealEstateShouldNotBeFound(
        "noBasementRooms.lessThanOrEqual=" + SMALLER_NO_BASEMENT_ROOMS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoBasementRoomsIsLessThanSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noBasementRooms is less than
    // DEFAULT_NO_BASEMENT_ROOMS
    defaultRealEstateShouldNotBeFound(
        "noBasementRooms.lessThan=" + DEFAULT_NO_BASEMENT_ROOMS);

    // Get all the realEstateList where noBasementRooms is less than
    // UPDATED_NO_BASEMENT_ROOMS
    defaultRealEstateShouldBeFound(
        "noBasementRooms.lessThan=" + UPDATED_NO_BASEMENT_ROOMS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByNoBasementRoomsIsGreaterThanSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where noBasementRooms is greater than
    // DEFAULT_NO_BASEMENT_ROOMS
    defaultRealEstateShouldNotBeFound(
        "noBasementRooms.greaterThan=" + DEFAULT_NO_BASEMENT_ROOMS);

    // Get all the realEstateList where noBasementRooms is greater than
    // SMALLER_NO_BASEMENT_ROOMS
    defaultRealEstateShouldBeFound(
        "noBasementRooms.greaterThan=" + SMALLER_NO_BASEMENT_ROOMS);
  }

  @Test
  @Transactional
  void getAllRealEstatesByMonumentProtectedIsEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where monumentProtected equals to
    // DEFAULT_MONUMENT_PROTECTED
    defaultRealEstateShouldBeFound(
        "monumentProtected.equals=" + DEFAULT_MONUMENT_PROTECTED);

    // Get all the realEstateList where monumentProtected equals to
    // UPDATED_MONUMENT_PROTECTED
    defaultRealEstateShouldNotBeFound(
        "monumentProtected.equals=" + UPDATED_MONUMENT_PROTECTED);
  }

  @Test
  @Transactional
  void getAllRealEstatesByMonumentProtectedIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where monumentProtected in
    // DEFAULT_MONUMENT_PROTECTED or UPDATED_MONUMENT_PROTECTED
    defaultRealEstateShouldBeFound(
        "monumentProtected.in=" +
            DEFAULT_MONUMENT_PROTECTED +
            "," +
            UPDATED_MONUMENT_PROTECTED);

    // Get all the realEstateList where monumentProtected equals to
    // UPDATED_MONUMENT_PROTECTED
    defaultRealEstateShouldNotBeFound(
        "monumentProtected.in=" + UPDATED_MONUMENT_PROTECTED);
  }

  @Test
  @Transactional
  void getAllRealEstatesByMonumentProtectedIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where monumentProtected is not null
    defaultRealEstateShouldBeFound("monumentProtected.specified=true");

    // Get all the realEstateList where monumentProtected is null
    defaultRealEstateShouldNotBeFound("monumentProtected.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByHeatingTypeIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where heatingType equals to DEFAULT_HEATING_TYPE
    defaultRealEstateShouldBeFound(
        "heatingType.equals=" + DEFAULT_HEATING_TYPE);

    // Get all the realEstateList where heatingType equals to UPDATED_HEATING_TYPE
    defaultRealEstateShouldNotBeFound(
        "heatingType.equals=" + UPDATED_HEATING_TYPE);
  }

  @Test
  @Transactional
  void getAllRealEstatesByHeatingTypeIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where heatingType in DEFAULT_HEATING_TYPE or
    // UPDATED_HEATING_TYPE
    defaultRealEstateShouldBeFound(
        "heatingType.in=" + DEFAULT_HEATING_TYPE + "," + UPDATED_HEATING_TYPE);

    // Get all the realEstateList where heatingType equals to UPDATED_HEATING_TYPE
    defaultRealEstateShouldNotBeFound("heatingType.in=" + UPDATED_HEATING_TYPE);
  }

  @Test
  @Transactional
  void getAllRealEstatesByHeatingTypeIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where heatingType is not null
    defaultRealEstateShouldBeFound("heatingType.specified=true");

    // Get all the realEstateList where heatingType is null
    defaultRealEstateShouldNotBeFound("heatingType.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByRoofPitchIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where roofPitch equals to DEFAULT_ROOF_PITCH
    defaultRealEstateShouldBeFound("roofPitch.equals=" + DEFAULT_ROOF_PITCH);

    // Get all the realEstateList where roofPitch equals to UPDATED_ROOF_PITCH
    defaultRealEstateShouldNotBeFound("roofPitch.equals=" + UPDATED_ROOF_PITCH);
  }

  @Test
  @Transactional
  void getAllRealEstatesByRoofPitchIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where roofPitch in DEFAULT_ROOF_PITCH or
    // UPDATED_ROOF_PITCH
    defaultRealEstateShouldBeFound(
        "roofPitch.in=" + DEFAULT_ROOF_PITCH + "," + UPDATED_ROOF_PITCH);

    // Get all the realEstateList where roofPitch equals to UPDATED_ROOF_PITCH
    defaultRealEstateShouldNotBeFound("roofPitch.in=" + UPDATED_ROOF_PITCH);
  }

  @Test
  @Transactional
  void getAllRealEstatesByRoofPitchIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where roofPitch is not null
    defaultRealEstateShouldBeFound("roofPitch.specified=true");

    // Get all the realEstateList where roofPitch is null
    defaultRealEstateShouldNotBeFound("roofPitch.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByRoofPitchIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where roofPitch is greater than or equal to
    // DEFAULT_ROOF_PITCH
    defaultRealEstateShouldBeFound(
        "roofPitch.greaterThanOrEqual=" + DEFAULT_ROOF_PITCH);

    // Get all the realEstateList where roofPitch is greater than or equal to
    // UPDATED_ROOF_PITCH
    defaultRealEstateShouldNotBeFound(
        "roofPitch.greaterThanOrEqual=" + UPDATED_ROOF_PITCH);
  }

  @Test
  @Transactional
  void getAllRealEstatesByRoofPitchIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where roofPitch is less than or equal to
    // DEFAULT_ROOF_PITCH
    defaultRealEstateShouldBeFound(
        "roofPitch.lessThanOrEqual=" + DEFAULT_ROOF_PITCH);

    // Get all the realEstateList where roofPitch is less than or equal to
    // SMALLER_ROOF_PITCH
    defaultRealEstateShouldNotBeFound(
        "roofPitch.lessThanOrEqual=" + SMALLER_ROOF_PITCH);
  }

  @Test
  @Transactional
  void getAllRealEstatesByRoofPitchIsLessThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where roofPitch is less than DEFAULT_ROOF_PITCH
    defaultRealEstateShouldNotBeFound(
        "roofPitch.lessThan=" + DEFAULT_ROOF_PITCH);

    // Get all the realEstateList where roofPitch is less than UPDATED_ROOF_PITCH
    defaultRealEstateShouldBeFound("roofPitch.lessThan=" + UPDATED_ROOF_PITCH);
  }

  @Test
  @Transactional
  void getAllRealEstatesByRoofPitchIsGreaterThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where roofPitch is greater than DEFAULT_ROOF_PITCH
    defaultRealEstateShouldNotBeFound(
        "roofPitch.greaterThan=" + DEFAULT_ROOF_PITCH);

    // Get all the realEstateList where roofPitch is greater than SMALLER_ROOF_PITCH
    defaultRealEstateShouldBeFound(
        "roofPitch.greaterThan=" + SMALLER_ROOF_PITCH);
  }

  @Test
  @Transactional
  void getAllRealEstatesByRoofTypeIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where roofType equals to DEFAULT_ROOF_TYPE
    defaultRealEstateShouldBeFound("roofType.equals=" + DEFAULT_ROOF_TYPE);

    // Get all the realEstateList where roofType equals to UPDATED_ROOF_TYPE
    defaultRealEstateShouldNotBeFound("roofType.equals=" + UPDATED_ROOF_TYPE);
  }

  @Test
  @Transactional
  void getAllRealEstatesByRoofTypeIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where roofType in DEFAULT_ROOF_TYPE or
    // UPDATED_ROOF_TYPE
    defaultRealEstateShouldBeFound(
        "roofType.in=" + DEFAULT_ROOF_TYPE + "," + UPDATED_ROOF_TYPE);

    // Get all the realEstateList where roofType equals to UPDATED_ROOF_TYPE
    defaultRealEstateShouldNotBeFound("roofType.in=" + UPDATED_ROOF_TYPE);
  }

  @Test
  @Transactional
  void getAllRealEstatesByRoofTypeIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where roofType is not null
    defaultRealEstateShouldBeFound("roofType.specified=true");

    // Get all the realEstateList where roofType is null
    defaultRealEstateShouldNotBeFound("roofType.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByGableAlignmentIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where gableAlignment equals to
    // DEFAULT_GABLE_ALIGNMENT
    defaultRealEstateShouldBeFound(
        "gableAlignment.equals=" + DEFAULT_GABLE_ALIGNMENT);

    // Get all the realEstateList where gableAlignment equals to
    // UPDATED_GABLE_ALIGNMENT
    defaultRealEstateShouldNotBeFound(
        "gableAlignment.equals=" + UPDATED_GABLE_ALIGNMENT);
  }

  @Test
  @Transactional
  void getAllRealEstatesByGableAlignmentIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where gableAlignment in DEFAULT_GABLE_ALIGNMENT or
    // UPDATED_GABLE_ALIGNMENT
    defaultRealEstateShouldBeFound(
        "gableAlignment.in=" +
            DEFAULT_GABLE_ALIGNMENT +
            "," +
            UPDATED_GABLE_ALIGNMENT);

    // Get all the realEstateList where gableAlignment equals to
    // UPDATED_GABLE_ALIGNMENT
    defaultRealEstateShouldNotBeFound(
        "gableAlignment.in=" + UPDATED_GABLE_ALIGNMENT);
  }

  @Test
  @Transactional
  void getAllRealEstatesByGableAlignmentIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where gableAlignment is not null
    defaultRealEstateShouldBeFound("gableAlignment.specified=true");

    // Get all the realEstateList where gableAlignment is null
    defaultRealEstateShouldNotBeFound("gableAlignment.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByRoofAreaIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where roofArea equals to DEFAULT_ROOF_AREA
    defaultRealEstateShouldBeFound("roofArea.equals=" + DEFAULT_ROOF_AREA);

    // Get all the realEstateList where roofArea equals to UPDATED_ROOF_AREA
    defaultRealEstateShouldNotBeFound("roofArea.equals=" + UPDATED_ROOF_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByRoofAreaIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where roofArea in DEFAULT_ROOF_AREA or
    // UPDATED_ROOF_AREA
    defaultRealEstateShouldBeFound(
        "roofArea.in=" + DEFAULT_ROOF_AREA + "," + UPDATED_ROOF_AREA);

    // Get all the realEstateList where roofArea equals to UPDATED_ROOF_AREA
    defaultRealEstateShouldNotBeFound("roofArea.in=" + UPDATED_ROOF_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByRoofAreaIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where roofArea is not null
    defaultRealEstateShouldBeFound("roofArea.specified=true");

    // Get all the realEstateList where roofArea is null
    defaultRealEstateShouldNotBeFound("roofArea.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByRoofAreaIsGreaterThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where roofArea is greater than or equal to
    // DEFAULT_ROOF_AREA
    defaultRealEstateShouldBeFound(
        "roofArea.greaterThanOrEqual=" + DEFAULT_ROOF_AREA);

    // Get all the realEstateList where roofArea is greater than or equal to
    // UPDATED_ROOF_AREA
    defaultRealEstateShouldNotBeFound(
        "roofArea.greaterThanOrEqual=" + UPDATED_ROOF_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByRoofAreaIsLessThanOrEqualToSomething()
      throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where roofArea is less than or equal to
    // DEFAULT_ROOF_AREA
    defaultRealEstateShouldBeFound(
        "roofArea.lessThanOrEqual=" + DEFAULT_ROOF_AREA);

    // Get all the realEstateList where roofArea is less than or equal to
    // SMALLER_ROOF_AREA
    defaultRealEstateShouldNotBeFound(
        "roofArea.lessThanOrEqual=" + SMALLER_ROOF_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByRoofAreaIsLessThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where roofArea is less than DEFAULT_ROOF_AREA
    defaultRealEstateShouldNotBeFound("roofArea.lessThan=" + DEFAULT_ROOF_AREA);

    // Get all the realEstateList where roofArea is less than UPDATED_ROOF_AREA
    defaultRealEstateShouldBeFound("roofArea.lessThan=" + UPDATED_ROOF_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByRoofAreaIsGreaterThanSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where roofArea is greater than DEFAULT_ROOF_AREA
    defaultRealEstateShouldNotBeFound(
        "roofArea.greaterThan=" + DEFAULT_ROOF_AREA);

    // Get all the realEstateList where roofArea is greater than SMALLER_ROOF_AREA
    defaultRealEstateShouldBeFound("roofArea.greaterThan=" + SMALLER_ROOF_AREA);
  }

  @Test
  @Transactional
  void getAllRealEstatesByLongitudeIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where longitude equals to DEFAULT_LONGITUDE
    defaultRealEstateShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

    // Get all the realEstateList where longitude equals to UPDATED_LONGITUDE
    defaultRealEstateShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
  }

  @Test
  @Transactional
  void getAllRealEstatesByLongitudeIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where longitude in DEFAULT_LONGITUDE or
    // UPDATED_LONGITUDE
    defaultRealEstateShouldBeFound(
        "longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

    // Get all the realEstateList where longitude equals to UPDATED_LONGITUDE
    defaultRealEstateShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
  }

  @Test
  @Transactional
  void getAllRealEstatesByLongitudeIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where longitude is not null
    defaultRealEstateShouldBeFound("longitude.specified=true");

    // Get all the realEstateList where longitude is null
    defaultRealEstateShouldNotBeFound("longitude.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByLongitudeContainsSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where longitude contains DEFAULT_LONGITUDE
    defaultRealEstateShouldBeFound("longitude.contains=" + DEFAULT_LONGITUDE);

    // Get all the realEstateList where longitude contains UPDATED_LONGITUDE
    defaultRealEstateShouldNotBeFound(
        "longitude.contains=" + UPDATED_LONGITUDE);
  }

  @Test
  @Transactional
  void getAllRealEstatesByLongitudeNotContainsSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where longitude does not contain DEFAULT_LONGITUDE
    defaultRealEstateShouldNotBeFound(
        "longitude.doesNotContain=" + DEFAULT_LONGITUDE);

    // Get all the realEstateList where longitude does not contain UPDATED_LONGITUDE
    defaultRealEstateShouldBeFound(
        "longitude.doesNotContain=" + UPDATED_LONGITUDE);
  }

  @Test
  @Transactional
  void getAllRealEstatesByLatitudeIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where latitude equals to DEFAULT_LATITUDE
    defaultRealEstateShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

    // Get all the realEstateList where latitude equals to UPDATED_LATITUDE
    defaultRealEstateShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
  }

  @Test
  @Transactional
  void getAllRealEstatesByLatitudeIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where latitude in DEFAULT_LATITUDE or
    // UPDATED_LATITUDE
    defaultRealEstateShouldBeFound(
        "latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

    // Get all the realEstateList where latitude equals to UPDATED_LATITUDE
    defaultRealEstateShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
  }

  @Test
  @Transactional
  void getAllRealEstatesByLatitudeIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where latitude is not null
    defaultRealEstateShouldBeFound("latitude.specified=true");

    // Get all the realEstateList where latitude is null
    defaultRealEstateShouldNotBeFound("latitude.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByLatitudeContainsSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where latitude contains DEFAULT_LATITUDE
    defaultRealEstateShouldBeFound("latitude.contains=" + DEFAULT_LATITUDE);

    // Get all the realEstateList where latitude contains UPDATED_LATITUDE
    defaultRealEstateShouldNotBeFound("latitude.contains=" + UPDATED_LATITUDE);
  }

  @Test
  @Transactional
  void getAllRealEstatesByLatitudeNotContainsSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where latitude does not contain DEFAULT_LATITUDE
    defaultRealEstateShouldNotBeFound(
        "latitude.doesNotContain=" + DEFAULT_LATITUDE);

    // Get all the realEstateList where latitude does not contain UPDATED_LATITUDE
    defaultRealEstateShouldBeFound(
        "latitude.doesNotContain=" + UPDATED_LATITUDE);
  }

  @Test
  @Transactional
  void getAllRealEstatesByCommentIsEqualToSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where comment equals to DEFAULT_COMMENT
    defaultRealEstateShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

    // Get all the realEstateList where comment equals to UPDATED_COMMENT
    defaultRealEstateShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
  }

  @Test
  @Transactional
  void getAllRealEstatesByCommentIsInShouldWork() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where comment in DEFAULT_COMMENT or
    // UPDATED_COMMENT
    defaultRealEstateShouldBeFound(
        "comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

    // Get all the realEstateList where comment equals to UPDATED_COMMENT
    defaultRealEstateShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
  }

  @Test
  @Transactional
  void getAllRealEstatesByCommentIsNullOrNotNull() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where comment is not null
    defaultRealEstateShouldBeFound("comment.specified=true");

    // Get all the realEstateList where comment is null
    defaultRealEstateShouldNotBeFound("comment.specified=false");
  }

  @Test
  @Transactional
  void getAllRealEstatesByCommentContainsSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where comment contains DEFAULT_COMMENT
    defaultRealEstateShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

    // Get all the realEstateList where comment contains UPDATED_COMMENT
    defaultRealEstateShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
  }

  @Test
  @Transactional
  void getAllRealEstatesByCommentNotContainsSomething() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    // Get all the realEstateList where comment does not contain DEFAULT_COMMENT
    defaultRealEstateShouldNotBeFound(
        "comment.doesNotContain=" + DEFAULT_COMMENT);

    // Get all the realEstateList where comment does not contain UPDATED_COMMENT
    defaultRealEstateShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
  }

  @Test
  @Transactional
  void getAllRealEstatesByApplicationUserIsEqualToSomething() throws Exception {
    ApplicationUser applicationUser;
    if (TestUtil.findAll(em, ApplicationUser.class).isEmpty()) {
      realEstateRepository.saveAndFlush(realEstate);
      applicationUser = ApplicationUserResourceIT.createEntity(em);
    } else {
      applicationUser = TestUtil.findAll(em, ApplicationUser.class).get(0);
    }
    em.persist(applicationUser);
    em.flush();
    realEstate.setApplicationUser(applicationUser);
    realEstateRepository.saveAndFlush(realEstate);
    Long applicationUserId = applicationUser.getId();

    // Get all the realEstateList where applicationUser equals to applicationUserId
    defaultRealEstateShouldBeFound(
        "applicationUserId.equals=" + applicationUserId);

    // Get all the realEstateList where applicationUser equals to (applicationUserId
    // + 1)
    defaultRealEstateShouldNotBeFound(
        "applicationUserId.equals=" + (applicationUserId + 1));
  }

  @Test
  @Transactional
  void getAllRealEstatesByGroupTypeIsEqualToSomething() throws Exception {
    GroupType groupType;
    if (TestUtil.findAll(em, GroupType.class).isEmpty()) {
      realEstateRepository.saveAndFlush(realEstate);
      groupType = GroupTypeResourceIT.createEntity(em);
    } else {
      groupType = TestUtil.findAll(em, GroupType.class).get(0);
    }
    em.persist(groupType);
    em.flush();
    realEstate.setGroupType(groupType);
    realEstateRepository.saveAndFlush(realEstate);
    Long groupTypeId = groupType.getId();

    // Get all the realEstateList where groupType equals to groupTypeId
    defaultRealEstateShouldBeFound("groupTypeId.equals=" + groupTypeId);

    // Get all the realEstateList where groupType equals to (groupTypeId + 1)
    defaultRealEstateShouldNotBeFound(
        "groupTypeId.equals=" + (groupTypeId + 1));
  }

  @Test
  @Transactional
  void getAllRealEstatesByContractRealestatesIsEqualToSomething()
      throws Exception {
    ContractRealestate contractRealestates;
    if (TestUtil.findAll(em, ContractRealestate.class).isEmpty()) {
      realEstateRepository.saveAndFlush(realEstate);
      contractRealestates = ContractRealestateResourceIT.createEntity(em);
    } else {
      contractRealestates = TestUtil.findAll(em, ContractRealestate.class).get(0);
    }
    em.persist(contractRealestates);
    em.flush();
    realEstate.addContractRealestates(contractRealestates);
    realEstateRepository.saveAndFlush(realEstate);
    Long contractRealestatesId = contractRealestates.getId();

    // Get all the realEstateList where contractRealestates equals to
    // contractRealestatesId
    defaultRealEstateShouldBeFound(
        "contractRealestatesId.equals=" + contractRealestatesId);

    // Get all the realEstateList where contractRealestates equals to
    // (contractRealestatesId + 1)
    defaultRealEstateShouldNotBeFound(
        "contractRealestatesId.equals=" + (contractRealestatesId + 1));
  }

  /**
   * Executes the search, and checks that the default entity is returned.
   */
  private void defaultRealEstateShouldBeFound(String filter) throws Exception {
    restRealEstateMockMvc
        .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(
            jsonPath("$.[*].id").value(hasItem(realEstate.getId().intValue())))
        .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
        .andExpect(
            jsonPath("$.[*].streetNumber").value(hasItem(DEFAULT_STREET_NUMBER)))
        .andExpect(
            jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
        .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
        .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
        .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
        .andExpect(
            jsonPath("$.[*].propertyWidth")
                .value(hasItem(DEFAULT_PROPERTY_WIDTH.doubleValue())))
        .andExpect(
            jsonPath("$.[*].propertyLength")
                .value(hasItem(DEFAULT_PROPERTY_LENGTH.doubleValue())))
        .andExpect(
            jsonPath("$.[*].propertyArea")
                .value(hasItem(DEFAULT_PROPERTY_AREA.doubleValue())))
        .andExpect(
            jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA.doubleValue())))
        .andExpect(jsonPath("$.[*].noHouses").value(hasItem(DEFAULT_NO_HOUSES)))
        .andExpect(
            jsonPath("$.[*].constructionYear")
                .value(hasItem(DEFAULT_CONSTRUCTION_YEAR)))
        .andExpect(
            jsonPath("$.[*].designType").value(hasItem(DEFAULT_DESIGN_TYPE)))
        .andExpect(
            jsonPath("$.[*].designTypeClass")
                .value(hasItem(DEFAULT_DESIGN_TYPE_CLASS.toString())))
        .andExpect(
            jsonPath("$.[*].builtUpArea")
                .value(hasItem(DEFAULT_BUILT_UP_AREA.doubleValue())))
        .andExpect(
            jsonPath("$.[*].sealtArea")
                .value(hasItem(DEFAULT_SEALT_AREA.doubleValue())))
        .andExpect(
            jsonPath("$.[*].undevelopedArea")
                .value(hasItem(DEFAULT_UNDEVELOPED_AREA.doubleValue())))
        .andExpect(
            jsonPath("$.[*].noSmokeDetectors")
                .value(hasItem(DEFAULT_NO_SMOKE_DETECTORS)))
        .andExpect(jsonPath("$.[*].noUnits").value(hasItem(DEFAULT_NO_UNITS)))
        .andExpect(jsonPath("$.[*].noFloors").value(hasItem(DEFAULT_NO_FLOORS)))
        .andExpect(
            jsonPath("$.[*].noPowerOutlets")
                .value(hasItem(DEFAULT_NO_POWER_OUTLETS)))
        .andExpect(
            jsonPath("$.[*].noNetworkSockets")
                .value(hasItem(DEFAULT_NO_NETWORK_SOCKETS)))
        .andExpect(
            jsonPath("$.[*].noLightSwitches")
                .value(hasItem(DEFAULT_NO_LIGHT_SWITCHES)))
        .andExpect(
            jsonPath("$.[*].noAntennas").value(hasItem(DEFAULT_NO_ANTENNAS)))
        .andExpect(
            jsonPath("$.[*].noShutterSwitches")
                .value(hasItem(DEFAULT_NO_SHUTTER_SWITCHES)))
        .andExpect(
            jsonPath("$.[*].noRadiators").value(hasItem(DEFAULT_NO_RADIATORS)))
        .andExpect(
            jsonPath("$.[*].noParkings").value(hasItem(DEFAULT_NO_PARKINGS)))
        .andExpect(jsonPath("$.[*].noGarages").value(hasItem(DEFAULT_NO_GARAGES)))
        .andExpect(
            jsonPath("$.[*].noCarports").value(hasItem(DEFAULT_NO_CARPORTS)))
        .andExpect(jsonPath("$.[*].noWindows").value(hasItem(DEFAULT_NO_WINDOWS)))
        .andExpect(
            jsonPath("$.[*].windowArea")
                .value(hasItem(DEFAULT_WINDOW_AREA.doubleValue())))
        .andExpect(
            jsonPath("$.[*].noElevators").value(hasItem(DEFAULT_NO_ELEVATORS)))
        .andExpect(
            jsonPath("$.[*].corridorArea")
                .value(hasItem(DEFAULT_CORRIDOR_AREA.doubleValue())))
        .andExpect(
            jsonPath("$.[*].noBasementRooms")
                .value(hasItem(DEFAULT_NO_BASEMENT_ROOMS)))
        .andExpect(
            jsonPath("$.[*].monumentProtected")
                .value(hasItem(DEFAULT_MONUMENT_PROTECTED.booleanValue())))
        .andExpect(
            jsonPath("$.[*].heatingType")
                .value(hasItem(DEFAULT_HEATING_TYPE.toString())))
        .andExpect(
            jsonPath("$.[*].roofPitch")
                .value(hasItem(DEFAULT_ROOF_PITCH.doubleValue())))
        .andExpect(
            jsonPath("$.[*].roofType").value(hasItem(DEFAULT_ROOF_TYPE.toString())))
        .andExpect(
            jsonPath("$.[*].gableAlignment")
                .value(hasItem(DEFAULT_GABLE_ALIGNMENT.toString())))
        .andExpect(
            jsonPath("$.[*].roofArea")
                .value(hasItem(DEFAULT_ROOF_AREA.doubleValue())))
        .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
        .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
        .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));

    // Check, that the count call also returns 1
    restRealEstateMockMvc
        .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().string("1"));
  }

  /**
   * Executes the search, and checks that the default entity is not returned.
   */
  private void defaultRealEstateShouldNotBeFound(String filter)
      throws Exception {
    restRealEstateMockMvc
        .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$").isEmpty());

    // Check, that the count call also returns 0
    restRealEstateMockMvc
        .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().string("0"));
  }

  @Test
  @Transactional
  void getNonExistingRealEstate() throws Exception {
    // Get the realEstate
    restRealEstateMockMvc
        .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
        .andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewRealEstate() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    int databaseSizeBeforeUpdate = realEstateRepository.findAll().size();

    // Update the realEstate
    RealEstate updatedRealEstate = realEstateRepository
        .findById(realEstate.getId())
        .orElseThrow();
    // Disconnect from session so that the updates on updatedRealEstate are not
    // directly saved in db
    em.detach(updatedRealEstate);
    updatedRealEstate
        .street(UPDATED_STREET)
        .streetNumber(UPDATED_STREET_NUMBER)
        .postalCode(UPDATED_POSTAL_CODE)
        .city(UPDATED_CITY)
        .state(UPDATED_STATE)
        .country(UPDATED_COUNTRY)
        .propertyWidth(UPDATED_PROPERTY_WIDTH)
        .propertyLength(UPDATED_PROPERTY_LENGTH)
        .propertyArea(UPDATED_PROPERTY_AREA)
        .area(UPDATED_AREA)
        .noHouses(UPDATED_NO_HOUSES)
        .constructionYear(UPDATED_CONSTRUCTION_YEAR)
        .designType(UPDATED_DESIGN_TYPE)
        .designTypeClass(UPDATED_DESIGN_TYPE_CLASS)
        .builtUpArea(UPDATED_BUILT_UP_AREA)
        .sealtArea(UPDATED_SEALT_AREA)
        .undevelopedArea(UPDATED_UNDEVELOPED_AREA)
        .noSmokeDetectors(UPDATED_NO_SMOKE_DETECTORS)
        .noUnits(UPDATED_NO_UNITS)
        .noFloors(UPDATED_NO_FLOORS)
        .noPowerOutlets(UPDATED_NO_POWER_OUTLETS)
        .noNetworkSockets(UPDATED_NO_NETWORK_SOCKETS)
        .noLightSwitches(UPDATED_NO_LIGHT_SWITCHES)
        .noAntennas(UPDATED_NO_ANTENNAS)
        .noShutterSwitches(UPDATED_NO_SHUTTER_SWITCHES)
        .noRadiators(UPDATED_NO_RADIATORS)
        .noParkings(UPDATED_NO_PARKINGS)
        .noGarages(UPDATED_NO_GARAGES)
        .noCarports(UPDATED_NO_CARPORTS)
        .noWindows(UPDATED_NO_WINDOWS)
        .windowArea(UPDATED_WINDOW_AREA)
        .noElevators(UPDATED_NO_ELEVATORS)
        .corridorArea(UPDATED_CORRIDOR_AREA)
        .noBasementRooms(UPDATED_NO_BASEMENT_ROOMS)
        .monumentProtected(UPDATED_MONUMENT_PROTECTED)
        .heatingType(UPDATED_HEATING_TYPE)
        .roofPitch(UPDATED_ROOF_PITCH)
        .roofType(UPDATED_ROOF_TYPE)
        .gableAlignment(UPDATED_GABLE_ALIGNMENT)
        .roofArea(UPDATED_ROOF_AREA)
        .longitude(UPDATED_LONGITUDE)
        .latitude(UPDATED_LATITUDE);

    restRealEstateMockMvc
        .perform(
            put(ENTITY_API_URL_ID, updatedRealEstate.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(updatedRealEstate)))
        .andExpect(status().isOk());

    // Validate the RealEstate in the database
    List<RealEstate> realEstateList = realEstateRepository.findAll();
    assertThat(realEstateList).hasSize(databaseSizeBeforeUpdate);
    RealEstate testRealEstate = realEstateList.get(realEstateList.size() - 1);
    assertThat(testRealEstate.getStreet()).isEqualTo(UPDATED_STREET);
    assertThat(testRealEstate.getStreetNumber())
        .isEqualTo(UPDATED_STREET_NUMBER);
    assertThat(testRealEstate.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
    assertThat(testRealEstate.getCity()).isEqualTo(UPDATED_CITY);
    assertThat(testRealEstate.getState()).isEqualTo(UPDATED_STATE);
    assertThat(testRealEstate.getCountry()).isEqualTo(UPDATED_COUNTRY);
    assertThat(testRealEstate.getPropertyWidth())
        .isEqualTo(UPDATED_PROPERTY_WIDTH);
    assertThat(testRealEstate.getPropertyLength())
        .isEqualTo(UPDATED_PROPERTY_LENGTH);
    assertThat(testRealEstate.getPropertyArea())
        .isEqualTo(UPDATED_PROPERTY_AREA);
    assertThat(testRealEstate.getArea()).isEqualTo(UPDATED_AREA);
    assertThat(testRealEstate.getNoHouses()).isEqualTo(UPDATED_NO_HOUSES);
    assertThat(testRealEstate.getConstructionYear())
        .isEqualTo(UPDATED_CONSTRUCTION_YEAR);
    assertThat(testRealEstate.getDesignType()).isEqualTo(UPDATED_DESIGN_TYPE);
    assertThat(testRealEstate.getDesignTypeClass())
        .isEqualTo(UPDATED_DESIGN_TYPE_CLASS);
    assertThat(testRealEstate.getBuiltUpArea())
        .isEqualTo(UPDATED_BUILT_UP_AREA);
    assertThat(testRealEstate.getSealtArea()).isEqualTo(UPDATED_SEALT_AREA);
    assertThat(testRealEstate.getUndevelopedArea())
        .isEqualTo(UPDATED_UNDEVELOPED_AREA);
    assertThat(testRealEstate.getNoSmokeDetectors())
        .isEqualTo(UPDATED_NO_SMOKE_DETECTORS);
    assertThat(testRealEstate.getNoUnits()).isEqualTo(UPDATED_NO_UNITS);
    assertThat(testRealEstate.getNoFloors()).isEqualTo(UPDATED_NO_FLOORS);
    assertThat(testRealEstate.getNoPowerOutlets())
        .isEqualTo(UPDATED_NO_POWER_OUTLETS);
    assertThat(testRealEstate.getNoNetworkSockets())
        .isEqualTo(UPDATED_NO_NETWORK_SOCKETS);
    assertThat(testRealEstate.getNoLightSwitches())
        .isEqualTo(UPDATED_NO_LIGHT_SWITCHES);
    assertThat(testRealEstate.getNoAntennas()).isEqualTo(UPDATED_NO_ANTENNAS);
    assertThat(testRealEstate.getNoShutterSwitches())
        .isEqualTo(UPDATED_NO_SHUTTER_SWITCHES);
    assertThat(testRealEstate.getNoRadiators()).isEqualTo(UPDATED_NO_RADIATORS);
    assertThat(testRealEstate.getNoParkings()).isEqualTo(UPDATED_NO_PARKINGS);
    assertThat(testRealEstate.getNoGarages()).isEqualTo(UPDATED_NO_GARAGES);
    assertThat(testRealEstate.getNoCarports()).isEqualTo(UPDATED_NO_CARPORTS);
    assertThat(testRealEstate.getNoWindows()).isEqualTo(UPDATED_NO_WINDOWS);
    assertThat(testRealEstate.getWindowArea()).isEqualTo(UPDATED_WINDOW_AREA);
    assertThat(testRealEstate.getNoElevators()).isEqualTo(UPDATED_NO_ELEVATORS);
    assertThat(testRealEstate.getCorridorArea())
        .isEqualTo(UPDATED_CORRIDOR_AREA);
    assertThat(testRealEstate.getNoBasementRooms())
        .isEqualTo(UPDATED_NO_BASEMENT_ROOMS);
    assertThat(testRealEstate.getMonumentProtected())
        .isEqualTo(UPDATED_MONUMENT_PROTECTED);
    assertThat(testRealEstate.getHeatingType()).isEqualTo(UPDATED_HEATING_TYPE);
    assertThat(testRealEstate.getRoofPitch()).isEqualTo(UPDATED_ROOF_PITCH);
    assertThat(testRealEstate.getRoofType()).isEqualTo(UPDATED_ROOF_TYPE);
    assertThat(testRealEstate.getGableAlignment())
        .isEqualTo(UPDATED_GABLE_ALIGNMENT);
    assertThat(testRealEstate.getRoofArea()).isEqualTo(UPDATED_ROOF_AREA);
    assertThat(testRealEstate.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
    assertThat(testRealEstate.getLatitude()).isEqualTo(UPDATED_LATITUDE);
    assertThat(testRealEstate.getComment()).isEqualTo(UPDATED_COMMENT);
  }

  @Test
  @Transactional
  void putNonExistingRealEstate() throws Exception {
    int databaseSizeBeforeUpdate = realEstateRepository.findAll().size();
    realEstate.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restRealEstateMockMvc
        .perform(
            put(ENTITY_API_URL_ID, realEstate.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(realEstate)))
        .andExpect(status().isBadRequest());

    // Validate the RealEstate in the database
    List<RealEstate> realEstateList = realEstateRepository.findAll();
    assertThat(realEstateList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchRealEstate() throws Exception {
    int databaseSizeBeforeUpdate = realEstateRepository.findAll().size();
    realEstate.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restRealEstateMockMvc
        .perform(
            put(ENTITY_API_URL_ID, count.incrementAndGet())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(realEstate)))
        .andExpect(status().isBadRequest());

    // Validate the RealEstate in the database
    List<RealEstate> realEstateList = realEstateRepository.findAll();
    assertThat(realEstateList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamRealEstate() throws Exception {
    int databaseSizeBeforeUpdate = realEstateRepository.findAll().size();
    realEstate.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restRealEstateMockMvc
        .perform(
            put(ENTITY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(realEstate)))
        .andExpect(status().isMethodNotAllowed());

    // Validate the RealEstate in the database
    List<RealEstate> realEstateList = realEstateRepository.findAll();
    assertThat(realEstateList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateRealEstateWithPatch() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    int databaseSizeBeforeUpdate = realEstateRepository.findAll().size();

    // Update the realEstate using partial update
    RealEstate partialUpdatedRealEstate = new RealEstate();
    partialUpdatedRealEstate.setId(realEstate.getId());

    partialUpdatedRealEstate
        .street(UPDATED_STREET)
        .country(UPDATED_COUNTRY)
        .designType(UPDATED_DESIGN_TYPE)
        .designTypeClass(UPDATED_DESIGN_TYPE_CLASS)
        .builtUpArea(UPDATED_BUILT_UP_AREA)
        .sealtArea(UPDATED_SEALT_AREA)
        .undevelopedArea(UPDATED_UNDEVELOPED_AREA)
        .noSmokeDetectors(UPDATED_NO_SMOKE_DETECTORS)
        .noFloors(UPDATED_NO_FLOORS)
        .noShutterSwitches(UPDATED_NO_SHUTTER_SWITCHES)
        .noRadiators(UPDATED_NO_RADIATORS)
        .noCarports(UPDATED_NO_CARPORTS)
        .noWindows(UPDATED_NO_WINDOWS)
        .corridorArea(UPDATED_CORRIDOR_AREA)
        .gableAlignment(UPDATED_GABLE_ALIGNMENT)
        .roofArea(UPDATED_ROOF_AREA)
        .latitude(UPDATED_LATITUDE);

    restRealEstateMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, partialUpdatedRealEstate.getId())
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRealEstate)))
        .andExpect(status().isOk());

    // Validate the RealEstate in the database
    List<RealEstate> realEstateList = realEstateRepository.findAll();
    assertThat(realEstateList).hasSize(databaseSizeBeforeUpdate);
    RealEstate testRealEstate = realEstateList.get(realEstateList.size() - 1);
    assertThat(testRealEstate.getStreet()).isEqualTo(UPDATED_STREET);
    assertThat(testRealEstate.getStreetNumber())
        .isEqualTo(DEFAULT_STREET_NUMBER);
    assertThat(testRealEstate.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
    assertThat(testRealEstate.getCity()).isEqualTo(DEFAULT_CITY);
    assertThat(testRealEstate.getState()).isEqualTo(DEFAULT_STATE);
    assertThat(testRealEstate.getCountry()).isEqualTo(UPDATED_COUNTRY);
    assertThat(testRealEstate.getPropertyWidth())
        .isEqualTo(DEFAULT_PROPERTY_WIDTH);
    assertThat(testRealEstate.getPropertyLength())
        .isEqualTo(DEFAULT_PROPERTY_LENGTH);
    assertThat(testRealEstate.getPropertyArea())
        .isEqualTo(DEFAULT_PROPERTY_AREA);
    assertThat(testRealEstate.getArea()).isEqualTo(DEFAULT_AREA);
    assertThat(testRealEstate.getNoHouses()).isEqualTo(DEFAULT_NO_HOUSES);
    assertThat(testRealEstate.getConstructionYear())
        .isEqualTo(DEFAULT_CONSTRUCTION_YEAR);
    assertThat(testRealEstate.getDesignType()).isEqualTo(UPDATED_DESIGN_TYPE);
    assertThat(testRealEstate.getDesignTypeClass())
        .isEqualTo(UPDATED_DESIGN_TYPE_CLASS);
    assertThat(testRealEstate.getBuiltUpArea())
        .isEqualTo(UPDATED_BUILT_UP_AREA);
    assertThat(testRealEstate.getSealtArea()).isEqualTo(UPDATED_SEALT_AREA);
    assertThat(testRealEstate.getUndevelopedArea())
        .isEqualTo(UPDATED_UNDEVELOPED_AREA);
    assertThat(testRealEstate.getNoSmokeDetectors())
        .isEqualTo(UPDATED_NO_SMOKE_DETECTORS);
    assertThat(testRealEstate.getNoUnits()).isEqualTo(DEFAULT_NO_UNITS);
    assertThat(testRealEstate.getNoFloors()).isEqualTo(UPDATED_NO_FLOORS);
    assertThat(testRealEstate.getNoPowerOutlets())
        .isEqualTo(DEFAULT_NO_POWER_OUTLETS);
    assertThat(testRealEstate.getNoNetworkSockets())
        .isEqualTo(DEFAULT_NO_NETWORK_SOCKETS);
    assertThat(testRealEstate.getNoLightSwitches())
        .isEqualTo(DEFAULT_NO_LIGHT_SWITCHES);
    assertThat(testRealEstate.getNoAntennas()).isEqualTo(DEFAULT_NO_ANTENNAS);
    assertThat(testRealEstate.getNoShutterSwitches())
        .isEqualTo(UPDATED_NO_SHUTTER_SWITCHES);
    assertThat(testRealEstate.getNoRadiators()).isEqualTo(UPDATED_NO_RADIATORS);
    assertThat(testRealEstate.getNoParkings()).isEqualTo(DEFAULT_NO_PARKINGS);
    assertThat(testRealEstate.getNoGarages()).isEqualTo(DEFAULT_NO_GARAGES);
    assertThat(testRealEstate.getNoCarports()).isEqualTo(UPDATED_NO_CARPORTS);
    assertThat(testRealEstate.getNoWindows()).isEqualTo(UPDATED_NO_WINDOWS);
    assertThat(testRealEstate.getWindowArea()).isEqualTo(DEFAULT_WINDOW_AREA);
    assertThat(testRealEstate.getNoElevators()).isEqualTo(DEFAULT_NO_ELEVATORS);
    assertThat(testRealEstate.getCorridorArea())
        .isEqualTo(UPDATED_CORRIDOR_AREA);
    assertThat(testRealEstate.getNoBasementRooms())
        .isEqualTo(DEFAULT_NO_BASEMENT_ROOMS);
    assertThat(testRealEstate.getMonumentProtected())
        .isEqualTo(DEFAULT_MONUMENT_PROTECTED);
    assertThat(testRealEstate.getHeatingType()).isEqualTo(DEFAULT_HEATING_TYPE);
    assertThat(testRealEstate.getRoofPitch()).isEqualTo(DEFAULT_ROOF_PITCH);
    assertThat(testRealEstate.getRoofType()).isEqualTo(DEFAULT_ROOF_TYPE);
    assertThat(testRealEstate.getGableAlignment())
        .isEqualTo(UPDATED_GABLE_ALIGNMENT);
    assertThat(testRealEstate.getRoofArea()).isEqualTo(UPDATED_ROOF_AREA);
    assertThat(testRealEstate.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
    assertThat(testRealEstate.getLatitude()).isEqualTo(UPDATED_LATITUDE);
    assertThat(testRealEstate.getComment()).isEqualTo(DEFAULT_COMMENT);
  }

  @Test
  @Transactional
  void fullUpdateRealEstateWithPatch() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    int databaseSizeBeforeUpdate = realEstateRepository.findAll().size();

    // Update the realEstate using partial update
    RealEstate partialUpdatedRealEstate = new RealEstate();
    partialUpdatedRealEstate.setId(realEstate.getId());

    partialUpdatedRealEstate
        .street(UPDATED_STREET)
        .streetNumber(UPDATED_STREET_NUMBER)
        .postalCode(UPDATED_POSTAL_CODE)
        .city(UPDATED_CITY)
        .state(UPDATED_STATE)
        .country(UPDATED_COUNTRY)
        .propertyWidth(UPDATED_PROPERTY_WIDTH)
        .propertyLength(UPDATED_PROPERTY_LENGTH)
        .propertyArea(UPDATED_PROPERTY_AREA)
        .area(UPDATED_AREA)
        .noHouses(UPDATED_NO_HOUSES)
        .constructionYear(UPDATED_CONSTRUCTION_YEAR)
        .designType(UPDATED_DESIGN_TYPE)
        .designTypeClass(UPDATED_DESIGN_TYPE_CLASS)
        .builtUpArea(UPDATED_BUILT_UP_AREA)
        .sealtArea(UPDATED_SEALT_AREA)
        .undevelopedArea(UPDATED_UNDEVELOPED_AREA)
        .noSmokeDetectors(UPDATED_NO_SMOKE_DETECTORS)
        .noUnits(UPDATED_NO_UNITS)
        .noFloors(UPDATED_NO_FLOORS)
        .noPowerOutlets(UPDATED_NO_POWER_OUTLETS)
        .noNetworkSockets(UPDATED_NO_NETWORK_SOCKETS)
        .noLightSwitches(UPDATED_NO_LIGHT_SWITCHES)
        .noAntennas(UPDATED_NO_ANTENNAS)
        .noShutterSwitches(UPDATED_NO_SHUTTER_SWITCHES)
        .noRadiators(UPDATED_NO_RADIATORS)
        .noParkings(UPDATED_NO_PARKINGS)
        .noGarages(UPDATED_NO_GARAGES)
        .noCarports(UPDATED_NO_CARPORTS)
        .noWindows(UPDATED_NO_WINDOWS)
        .windowArea(UPDATED_WINDOW_AREA)
        .noElevators(UPDATED_NO_ELEVATORS)
        .corridorArea(UPDATED_CORRIDOR_AREA)
        .noBasementRooms(UPDATED_NO_BASEMENT_ROOMS)
        .monumentProtected(UPDATED_MONUMENT_PROTECTED)
        .heatingType(UPDATED_HEATING_TYPE)
        .roofPitch(UPDATED_ROOF_PITCH)
        .roofType(UPDATED_ROOF_TYPE)
        .gableAlignment(UPDATED_GABLE_ALIGNMENT)
        .roofArea(UPDATED_ROOF_AREA)
        .longitude(UPDATED_LONGITUDE)
        .latitude(UPDATED_LATITUDE);

    restRealEstateMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, partialUpdatedRealEstate.getId())
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRealEstate)))
        .andExpect(status().isOk());

    // Validate the RealEstate in the database
    List<RealEstate> realEstateList = realEstateRepository.findAll();
    assertThat(realEstateList).hasSize(databaseSizeBeforeUpdate);
    RealEstate testRealEstate = realEstateList.get(realEstateList.size() - 1);
    assertThat(testRealEstate.getStreet()).isEqualTo(UPDATED_STREET);
    assertThat(testRealEstate.getStreetNumber())
        .isEqualTo(UPDATED_STREET_NUMBER);
    assertThat(testRealEstate.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
    assertThat(testRealEstate.getCity()).isEqualTo(UPDATED_CITY);
    assertThat(testRealEstate.getState()).isEqualTo(UPDATED_STATE);
    assertThat(testRealEstate.getCountry()).isEqualTo(UPDATED_COUNTRY);
    assertThat(testRealEstate.getPropertyWidth())
        .isEqualTo(UPDATED_PROPERTY_WIDTH);
    assertThat(testRealEstate.getPropertyLength())
        .isEqualTo(UPDATED_PROPERTY_LENGTH);
    assertThat(testRealEstate.getPropertyArea())
        .isEqualTo(UPDATED_PROPERTY_AREA);
    assertThat(testRealEstate.getArea()).isEqualTo(UPDATED_AREA);
    assertThat(testRealEstate.getNoHouses()).isEqualTo(UPDATED_NO_HOUSES);
    assertThat(testRealEstate.getConstructionYear())
        .isEqualTo(UPDATED_CONSTRUCTION_YEAR);
    assertThat(testRealEstate.getDesignType()).isEqualTo(UPDATED_DESIGN_TYPE);
    assertThat(testRealEstate.getDesignTypeClass())
        .isEqualTo(UPDATED_DESIGN_TYPE_CLASS);
    assertThat(testRealEstate.getBuiltUpArea())
        .isEqualTo(UPDATED_BUILT_UP_AREA);
    assertThat(testRealEstate.getSealtArea()).isEqualTo(UPDATED_SEALT_AREA);
    assertThat(testRealEstate.getUndevelopedArea())
        .isEqualTo(UPDATED_UNDEVELOPED_AREA);
    assertThat(testRealEstate.getNoSmokeDetectors())
        .isEqualTo(UPDATED_NO_SMOKE_DETECTORS);
    assertThat(testRealEstate.getNoUnits()).isEqualTo(UPDATED_NO_UNITS);
    assertThat(testRealEstate.getNoFloors()).isEqualTo(UPDATED_NO_FLOORS);
    assertThat(testRealEstate.getNoPowerOutlets())
        .isEqualTo(UPDATED_NO_POWER_OUTLETS);
    assertThat(testRealEstate.getNoNetworkSockets())
        .isEqualTo(UPDATED_NO_NETWORK_SOCKETS);
    assertThat(testRealEstate.getNoLightSwitches())
        .isEqualTo(UPDATED_NO_LIGHT_SWITCHES);
    assertThat(testRealEstate.getNoAntennas()).isEqualTo(UPDATED_NO_ANTENNAS);
    assertThat(testRealEstate.getNoShutterSwitches())
        .isEqualTo(UPDATED_NO_SHUTTER_SWITCHES);
    assertThat(testRealEstate.getNoRadiators()).isEqualTo(UPDATED_NO_RADIATORS);
    assertThat(testRealEstate.getNoParkings()).isEqualTo(UPDATED_NO_PARKINGS);
    assertThat(testRealEstate.getNoGarages()).isEqualTo(UPDATED_NO_GARAGES);
    assertThat(testRealEstate.getNoCarports()).isEqualTo(UPDATED_NO_CARPORTS);
    assertThat(testRealEstate.getNoWindows()).isEqualTo(UPDATED_NO_WINDOWS);
    assertThat(testRealEstate.getWindowArea()).isEqualTo(UPDATED_WINDOW_AREA);
    assertThat(testRealEstate.getNoElevators()).isEqualTo(UPDATED_NO_ELEVATORS);
    assertThat(testRealEstate.getCorridorArea())
        .isEqualTo(UPDATED_CORRIDOR_AREA);
    assertThat(testRealEstate.getNoBasementRooms())
        .isEqualTo(UPDATED_NO_BASEMENT_ROOMS);
    assertThat(testRealEstate.getMonumentProtected())
        .isEqualTo(UPDATED_MONUMENT_PROTECTED);
    assertThat(testRealEstate.getHeatingType()).isEqualTo(UPDATED_HEATING_TYPE);
    assertThat(testRealEstate.getRoofPitch()).isEqualTo(UPDATED_ROOF_PITCH);
    assertThat(testRealEstate.getRoofType()).isEqualTo(UPDATED_ROOF_TYPE);
    assertThat(testRealEstate.getGableAlignment())
        .isEqualTo(UPDATED_GABLE_ALIGNMENT);
    assertThat(testRealEstate.getRoofArea()).isEqualTo(UPDATED_ROOF_AREA);
    assertThat(testRealEstate.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
    assertThat(testRealEstate.getLatitude()).isEqualTo(UPDATED_LATITUDE);
    assertThat(testRealEstate.getComment()).isEqualTo(UPDATED_COMMENT);
  }

  @Test
  @Transactional
  void patchNonExistingRealEstate() throws Exception {
    int databaseSizeBeforeUpdate = realEstateRepository.findAll().size();
    realEstate.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restRealEstateMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, realEstate.getId())
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(realEstate)))
        .andExpect(status().isBadRequest());

    // Validate the RealEstate in the database
    List<RealEstate> realEstateList = realEstateRepository.findAll();
    assertThat(realEstateList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchRealEstate() throws Exception {
    int databaseSizeBeforeUpdate = realEstateRepository.findAll().size();
    realEstate.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restRealEstateMockMvc
        .perform(
            patch(ENTITY_API_URL_ID, count.incrementAndGet())
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(realEstate)))
        .andExpect(status().isBadRequest());

    // Validate the RealEstate in the database
    List<RealEstate> realEstateList = realEstateRepository.findAll();
    assertThat(realEstateList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamRealEstate() throws Exception {
    int databaseSizeBeforeUpdate = realEstateRepository.findAll().size();
    realEstate.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restRealEstateMockMvc
        .perform(
            patch(ENTITY_API_URL)
                .contentType("application/merge-patch+json")
                .content(TestUtil.convertObjectToJsonBytes(realEstate)))
        .andExpect(status().isMethodNotAllowed());

    // Validate the RealEstate in the database
    List<RealEstate> realEstateList = realEstateRepository.findAll();
    assertThat(realEstateList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteRealEstate() throws Exception {
    // Initialize the database
    realEstateRepository.saveAndFlush(realEstate);

    int databaseSizeBeforeDelete = realEstateRepository.findAll().size();

    // Delete the realEstate
    restRealEstateMockMvc
        .perform(
            delete(ENTITY_API_URL_ID, realEstate.getId())
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<RealEstate> realEstateList = realEstateRepository.findAll();
    assertThat(realEstateList).hasSize(databaseSizeBeforeDelete - 1);
  }
}
