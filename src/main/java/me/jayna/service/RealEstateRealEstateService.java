package me.jayna.service;

import me.jayna.domain.RealEstate;
import me.jayna.domain.RealEstateRealEstate;
import me.jayna.repository.RealEstateRealEstateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RealEstateRealEstateService {

    private final Logger log = LoggerFactory.getLogger(RealEstateRealEstateService.class);

    private final RealEstateRealEstateRepository realEstateRealEstateRepository;

    public RealEstateRealEstateService(RealEstateRealEstateRepository realEstateRealEstateRepository) {
        this.realEstateRealEstateRepository = realEstateRealEstateRepository;
    }

    public RealEstateRealEstate save(RealEstateRealEstate realEstateRealEstate) {
        log.debug("Request to save RealEstateRealEstate : {}", realEstateRealEstate);
        return realEstateRealEstateRepository.save(realEstateRealEstate);
    }

    public List<RealEstateRealEstate> findAllByRealEstate(RealEstate realEstate) {
        log.debug("Request to get all RealEstateRealEstates for RealEstate : {}", realEstate);
        return realEstateRealEstateRepository.findAllByRealestate(realEstate);
    }

    public List<RealEstateRealEstate> findAllByRelatedRealEstate(RealEstate realEstate) {
        log.debug("Request to get all RealEstateRealEstates for Related RealEstate : {}", realEstate);
        return realEstateRealEstateRepository.findAllByRelatedRealEstate(realEstate);
    }

    public Optional<RealEstateRealEstate> findOne(Long id) {
        log.debug("Request to get RealEstateRealEstate : {}", id);
        return realEstateRealEstateRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete RealEstateRealEstate : {}", id);
        realEstateRealEstateRepository.deleteById(id);
    }

    public void deleteAllByRealEstate(RealEstate realEstate) {
        log.debug("Request to delete all RealEstateRealEstates for RealEstate : {}", realEstate);
        try {
            realEstateRealEstateRepository.deleteAllByRealestate(realEstate);
        } catch (Exception e) {
            log.warn("Error deleting RealEstateRealEstates for RealEstate {}: {}", realEstate.getId(), e.getMessage());
            // Versuche einzelne Löschungen, falls die Batch-Löschung fehlschlägt
            try {
                List<RealEstateRealEstate> relationships = findAllByRealEstate(realEstate);
                for (RealEstateRealEstate relationship : relationships) {
                    try {
                        delete(relationship.getId());
                    } catch (Exception ex) {
                        log.warn("Error deleting individual RealEstateRealEstate relationship {}: {}", relationship.getId(), ex.getMessage());
                    }
                }
            } catch (Exception ex) {
                log.error("Error during individual RealEstateRealEstate deletion: {}", ex.getMessage());
            }
        }
    }

    public void deleteAllByRelatedRealEstate(RealEstate realEstate) {
        log.debug("Request to delete all RealEstateRealEstates for Related RealEstate : {}", realEstate);
        try {
            realEstateRealEstateRepository.deleteAllByRelatedRealEstate(realEstate);
        } catch (Exception e) {
            log.warn("Error deleting related RealEstateRealEstates for RealEstate {}: {}", realEstate.getId(), e.getMessage());
            // Versuche einzelne Löschungen, falls die Batch-Löschung fehlschlägt
            try {
                List<RealEstateRealEstate> relationships = findAllByRelatedRealEstate(realEstate);
                for (RealEstateRealEstate relationship : relationships) {
                    try {
                        delete(relationship.getId());
                    } catch (Exception ex) {
                        log.warn("Error deleting individual related RealEstateRealEstate relationship {}: {}", relationship.getId(), ex.getMessage());
                    }
                }
            } catch (Exception ex) {
                log.error("Error during individual related RealEstateRealEstate deletion: {}", ex.getMessage());
            }
        }
    }
} 