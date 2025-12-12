package me.jayna.service;

import java.util.List;
import java.util.Optional;
import me.jayna.domain.RealEstate;
import me.jayna.domain.ApplicationUser;
import me.jayna.repository.RealEstateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RealEstate}.
 */
@Service
@Transactional
public class RealEstateService {

  private final Logger log = LoggerFactory.getLogger(RealEstateService.class);

  private final RealEstateRepository realEstateRepository;

  public RealEstateService(RealEstateRepository realEstateRepository) {
    this.realEstateRepository = realEstateRepository;
  }

  /**
   * Save a realEstate.
   *
   * @param realEstate the entity to save.
   * @return the persisted entity.
   */
  public RealEstate save(RealEstate realEstate) {
    log.debug("Request to save RealEstate : {}", realEstate);
    return realEstateRepository.save(realEstate);
  }

  /**
   * Update a realEstate.
   *
   * @param realEstate the entity to save.
   * @return the persisted entity.
   */
  public RealEstate update(RealEstate realEstate) {
    log.debug("Request to save RealEstate : {}", realEstate);
    return realEstateRepository.save(realEstate);
  }

  /**
   * Partially update a realEstate.
   *
   * @param realEstate the entity to update partially.
   * @return the persisted entity.
   */
  public Optional<RealEstate> partialUpdate(RealEstate realEstate) {
    log.debug("Request to partially update RealEstate : {}", realEstate);

    return realEstateRepository
      .findById(realEstate.getId())
      .map(existingRealEstate -> {
        if (realEstate.getStreet() != null) {
          existingRealEstate.setStreet(realEstate.getStreet());
        }
        if (realEstate.getStreetNumber() != null) {
          existingRealEstate.setStreetNumber(realEstate.getStreetNumber());
        }
        if (realEstate.getPostalCode() != null) {
          existingRealEstate.setPostalCode(realEstate.getPostalCode());
        }
        if (realEstate.getCity() != null) {
          existingRealEstate.setCity(realEstate.getCity());
        }
        if (realEstate.getState() != null) {
          existingRealEstate.setState(realEstate.getState());
        }
        if (realEstate.getCountry() != null) {
          existingRealEstate.setCountry(realEstate.getCountry());
        }
        if (realEstate.getPropertyWidth() != null) {
          existingRealEstate.setPropertyWidth(realEstate.getPropertyWidth());
        }
        if (realEstate.getPropertyLength() != null) {
          existingRealEstate.setPropertyLength(realEstate.getPropertyLength());
        }
        if (realEstate.getPropertyArea() != null) {
          existingRealEstate.setPropertyArea(realEstate.getPropertyArea());
        }
        if (realEstate.getArea() != null) {
          existingRealEstate.setArea(realEstate.getArea());
        }
        if (realEstate.getNoHouses() != null) {
          existingRealEstate.setNoHouses(realEstate.getNoHouses());
        }
        if (realEstate.getConstructionYear() != null) {
          existingRealEstate.setConstructionYear(
            realEstate.getConstructionYear()
          );
        }
        if (realEstate.getDesignType() != null) {
          existingRealEstate.setDesignType(realEstate.getDesignType());
        }
        if (realEstate.getDesignTypeClass() != null) {
          existingRealEstate.setDesignTypeClass(
            realEstate.getDesignTypeClass()
          );
        }
        if (realEstate.getBuiltUpArea() != null) {
          existingRealEstate.setBuiltUpArea(realEstate.getBuiltUpArea());
        }
        if (realEstate.getSealtArea() != null) {
          existingRealEstate.setSealtArea(realEstate.getSealtArea());
        }
        if (realEstate.getUndevelopedArea() != null) {
          existingRealEstate.setUndevelopedArea(
            realEstate.getUndevelopedArea()
          );
        }
        if (realEstate.getNoSmokeDetectors() != null) {
          existingRealEstate.setNoSmokeDetectors(
            realEstate.getNoSmokeDetectors()
          );
        }
        if (realEstate.getNoUnits() != null) {
          existingRealEstate.setNoUnits(realEstate.getNoUnits());
        }
        if (realEstate.getNoFloors() != null) {
          existingRealEstate.setNoFloors(realEstate.getNoFloors());
        }
        if (realEstate.getNoPowerOutlets() != null) {
          existingRealEstate.setNoPowerOutlets(realEstate.getNoPowerOutlets());
        }
        if (realEstate.getNoNetworkSockets() != null) {
          existingRealEstate.setNoNetworkSockets(
            realEstate.getNoNetworkSockets()
          );
        }
        if (realEstate.getNoLightSwitches() != null) {
          existingRealEstate.setNoLightSwitches(
            realEstate.getNoLightSwitches()
          );
        }
        if (realEstate.getNoAntennas() != null) {
          existingRealEstate.setNoAntennas(realEstate.getNoAntennas());
        }
        if (realEstate.getNoShutterSwitches() != null) {
          existingRealEstate.setNoShutterSwitches(
            realEstate.getNoShutterSwitches()
          );
        }
        if (realEstate.getNoRadiators() != null) {
          existingRealEstate.setNoRadiators(realEstate.getNoRadiators());
        }
        if (realEstate.getNoParkings() != null) {
          existingRealEstate.setNoParkings(realEstate.getNoParkings());
        }
        if (realEstate.getNoGarages() != null) {
          existingRealEstate.setNoGarages(realEstate.getNoGarages());
        }
        if (realEstate.getNoCarports() != null) {
          existingRealEstate.setNoCarports(realEstate.getNoCarports());
        }
        if (realEstate.getNoWindows() != null) {
          existingRealEstate.setNoWindows(realEstate.getNoWindows());
        }
        if (realEstate.getWindowArea() != null) {
          existingRealEstate.setWindowArea(realEstate.getWindowArea());
        }
        if (realEstate.getNoElevators() != null) {
          existingRealEstate.setNoElevators(realEstate.getNoElevators());
        }
        if (realEstate.getCorridorArea() != null) {
          existingRealEstate.setCorridorArea(realEstate.getCorridorArea());
        }
        if (realEstate.getNoBasementRooms() != null) {
          existingRealEstate.setNoBasementRooms(
            realEstate.getNoBasementRooms()
          );
        }
        if (realEstate.getMonumentProtected() != null) {
          existingRealEstate.setMonumentProtected(
            realEstate.getMonumentProtected()
          );
        }
        if (realEstate.getHeatingType() != null) {
          existingRealEstate.setHeatingType(realEstate.getHeatingType());
        }
        if (realEstate.getRoofPitch() != null) {
          existingRealEstate.setRoofPitch(realEstate.getRoofPitch());
        }
        if (realEstate.getRoofType() != null) {
          existingRealEstate.setRoofType(realEstate.getRoofType());
        }
        if (realEstate.getGableAlignment() != null) {
          existingRealEstate.setGableAlignment(realEstate.getGableAlignment());
        }
        if (realEstate.getRoofArea() != null) {
          existingRealEstate.setRoofArea(realEstate.getRoofArea());
        }
        if (realEstate.getLongitude() != null) {
          existingRealEstate.setLongitude(realEstate.getLongitude());
        }
        if (realEstate.getLatitude() != null) {
          existingRealEstate.setLatitude(realEstate.getLatitude());
        }
        if (realEstate.getComment() != null) {
          existingRealEstate.setComment(realEstate.getComment());
        }

        return existingRealEstate;
      })
      .map(realEstateRepository::save);
  }

  /**
   * Get all the realEstates.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Page<RealEstate> findAll(Pageable pageable) {
    log.debug("Request to get all RealEstates");
    return realEstateRepository.findAll(pageable);
  }

  /**
   * Get one realEstate by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Optional<RealEstate> findOne(Long id) {
    log.debug("Request to get RealEstate : {}", id);
    return realEstateRepository.findById(id);
  }

  /**
   * Delete the realEstate by id.
   *
   * @param id the id of the entity.
   */
  public void delete(Long id) {
    log.debug("Request to delete RealEstate : {}", id);
    realEstateRepository.deleteById(id);
  }

  /**
   * Get all realEstates for an application user with full relationships.
   *
   * @param applicationUser the application user.
   * @return the list of realEstates with full relationships.
   */
  @Transactional(readOnly = true)
  public List<RealEstate> findAllByApplicationUser(ApplicationUser applicationUser) {
    log.debug("Request to get all RealEstates for ApplicationUser : {}", applicationUser.getId());
    return realEstateRepository.findAllByApplicationUser(applicationUser);
  }

  /**
   * Get all realEstates with category relations for the given application user.
   *
   * @param applicationUser the application user.
   * @return the realEstates with category relations.
   */
  @Transactional(readOnly = true)
  public List<RealEstate> findAllWithCategoryRelationsByApplicationUser(ApplicationUser applicationUser) {
    log.debug("find all realEstates with category relations for application user: {}", applicationUser.getId());
    return realEstateRepository.findAllWithCategoryRelationsByApplicationUser(applicationUser);
  }
}
