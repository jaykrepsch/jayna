package me.jayna.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import me.jayna.domain.Mobility;
import me.jayna.domain.RealEstate;
import me.jayna.domain.MobilityRealestate;
import me.jayna.repository.MobilityRealestateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MobilityRealestate}.
 */
@Service
@Transactional
public class MobilityRealestateService {

  private final Logger log = LoggerFactory.getLogger(MobilityRealestateService.class);

  private final MobilityRealestateRepository mobilityRealestateRepository;

  public MobilityRealestateService(MobilityRealestateRepository mobilityRealestateRepository) {
    this.mobilityRealestateRepository = mobilityRealestateRepository;
  }

  public MobilityRealestate save(MobilityRealestate mobilityRealestate) {
    log.debug("Request to save MobilityRealestate : {}", mobilityRealestate);
    return mobilityRealestateRepository.save(mobilityRealestate);
  }

  public MobilityRealestate update(MobilityRealestate mobilityRealestate) {
    log.debug("Request to save MobilityRealestate : {}", mobilityRealestate);
    return mobilityRealestateRepository.save(mobilityRealestate);
  }

  @Transactional(readOnly = true)
  public Page<MobilityRealestate> findAll(Pageable pageable) {
    log.debug("Request to get all MobilityMobilities");
    return mobilityRealestateRepository.findAll(pageable);
  }

  @Transactional(readOnly = true)
  public Optional<MobilityRealestate> findByMobility(Mobility mobility) {
    log.debug("Request to get MobilityRealestate by mobility");
    return mobilityRealestateRepository.findOneByMobility(mobility);
  }

  @Transactional(readOnly = true)
  public Optional<MobilityRealestate> findByRealestate(RealEstate realestate) {
    log.debug("Request to get MobilityRealestate by finmobility");
    return mobilityRealestateRepository.findOneByRealestate(realestate);
  }

  @Transactional(readOnly = true)
  public Set<MobilityRealestate> findAllByMobility(Mobility mobility) {
    log.debug("Request to get MobilityRealestate by mobility");
    return mobilityRealestateRepository.findAllByMobility(mobility);
  }

  @Transactional(readOnly = true)
  public Set<MobilityRealestate> findAllByRealestate(RealEstate realestate) {
    log.debug("Request to get MobilityRealestate by realestate");
    return mobilityRealestateRepository.findAllByRealestate(realestate);
  }

  @Transactional(readOnly = true)
  public Optional<MobilityRealestate> findOne(Long id) {
    log.debug("Request to get MobilityRealestate : {}", id);
    return mobilityRealestateRepository.findById(id);
  }

  public void delete(Long id) {
    log.debug("Request to delete MobilityRealestate : {}", id);
    mobilityRealestateRepository.deleteById(id);
  }

  public void deleteAll(Iterable<MobilityRealestate> collection) {
    log.debug("Request to delete all MobilityMobilities : {}", collection);
    mobilityRealestateRepository.deleteAll(collection);
  }

  public void saveMobilityRealestateRelationships(Set<MobilityRealestate> mobilityMobilities,
      Object entity) {
    mobilityMobilities
        .forEach(cr -> {
          if (entity instanceof Mobility) {
            final Mobility mobility = (Mobility) entity;
            cr.setMobility(mobility);
            mobility.getMobilityRealestates().add(cr);
          } else if (entity instanceof RealEstate) {
            final RealEstate realestate = (RealEstate) entity;
            cr.setRealestate(realestate);
            realestate.getMobilityRealestates().add(cr);
          }
          save(cr);
        });
  }

  public void updateMobilityRealestateRelationships(Set<MobilityRealestate> requestMobilityMobilities,
      Object entity) {
    final Set<MobilityRealestate> mobilityMobilities = new HashSet<>();

    if (entity instanceof Mobility) {
      final Mobility mobility = (Mobility) entity;
      mobilityMobilities.addAll(findAllByMobility(mobility));
    } else if (entity instanceof RealEstate) {
      final RealEstate realestate = (RealEstate) entity;
      mobilityMobilities.addAll(findAllByRealestate(realestate));
    }

    final Set<MobilityRealestate> mobilityMobilitiesToDelete = new HashSet<>();

    mobilityMobilitiesToDelete.addAll(
        mobilityMobilities.stream()
            .filter(cr -> requestMobilityMobilities.stream().filter(item -> cr.getId().equals(item.getId()))
                .count() == 0)
            .collect(Collectors.toSet()));

    if (mobilityMobilitiesToDelete.size() > 0) {
      deleteAll(mobilityMobilitiesToDelete);
    }

    requestMobilityMobilities
        .forEach(cr -> {
          if (cr.getId() == null) {
            if (entity instanceof Mobility) {
              final Mobility mobility = (Mobility) entity;
              cr.setMobility(mobility);
            } else if (entity instanceof RealEstate) {
              final RealEstate realestate = (RealEstate) entity;
              cr.setRealestate(realestate);
            }
            final MobilityRealestate result = save(cr);
            cr.setId(result.getId());
          }
        });
  }

  public void deleteRelationships(Object entity) {
    try {
      if (entity instanceof Mobility) {
        final Mobility mobility = (Mobility) entity;
        Set<MobilityRealestate> relationships = findAllByMobility(mobility);
        if (!relationships.isEmpty()) {
          deleteAll(relationships);
        }
      } else if (entity instanceof RealEstate) {
        final RealEstate realestate = (RealEstate) entity;
        Set<MobilityRealestate> relationships = findAllByRealestate(realestate);
        if (!relationships.isEmpty()) {
          deleteAll(relationships);
        }
      }
    } catch (Exception e) {
      log.warn("Error deleting MobilityRealestate relationships: {}", e.getMessage());
      // Versuche einzelne Löschungen, falls die Batch-Löschung fehlschlägt
      try {
        if (entity instanceof Mobility) {
          final Mobility mobility = (Mobility) entity;
          Set<MobilityRealestate> relationships = findAllByMobility(mobility);
          for (MobilityRealestate relationship : relationships) {
            try {
              delete(relationship.getId());
            } catch (Exception ex) {
              log.warn("Error deleting individual MobilityRealestate relationship {}: {}", relationship.getId(), ex.getMessage());
            }
          }
        } else if (entity instanceof RealEstate) {
          final RealEstate realestate = (RealEstate) entity;
          Set<MobilityRealestate> relationships = findAllByRealestate(realestate);
          for (MobilityRealestate relationship : relationships) {
            try {
              delete(relationship.getId());
            } catch (Exception ex) {
              log.warn("Error deleting individual MobilityRealestate relationship {}: {}", relationship.getId(), ex.getMessage());
            }
          }
        }
      } catch (Exception ex) {
        log.error("Error during individual MobilityRealestate relationship deletion: {}", ex.getMessage());
      }
    }
  }
}
