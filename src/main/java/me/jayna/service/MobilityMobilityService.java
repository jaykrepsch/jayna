package me.jayna.service;

import me.jayna.domain.Mobility;
import me.jayna.domain.MobilityMobility;
import me.jayna.repository.MobilityMobilityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MobilityMobilityService {

    private final Logger log = LoggerFactory.getLogger(MobilityMobilityService.class);

    private final MobilityMobilityRepository mobilityMobilityRepository;

    public MobilityMobilityService(MobilityMobilityRepository mobilityMobilityRepository) {
        this.mobilityMobilityRepository = mobilityMobilityRepository;
    }

    public MobilityMobility save(MobilityMobility mobilityMobility) {
        log.debug("Request to save MobilityMobility : {}", mobilityMobility);
        return mobilityMobilityRepository.save(mobilityMobility);
    }

    public List<MobilityMobility> findAllByMobility(Mobility mobility) {
        log.debug("Request to get all MobilityMobilities for Mobility : {}", mobility);
        return mobilityMobilityRepository.findAllByMobility(mobility);
    }

    public List<MobilityMobility> findAllByRelatedMobility(Mobility mobility) {
        log.debug("Request to get all MobilityMobilities for Related Mobility : {}", mobility);
        return mobilityMobilityRepository.findAllByRelatedMobility(mobility);
    }

    public Optional<MobilityMobility> findOne(Long id) {
        log.debug("Request to get MobilityMobility : {}", id);
        return mobilityMobilityRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete MobilityMobility : {}", id);
        mobilityMobilityRepository.deleteById(id);
    }

    public void deleteAllByMobility(Mobility mobility) {
        log.debug("Request to delete all MobilityMobilities for Mobility : {}", mobility);
        mobilityMobilityRepository.deleteAllByMobility(mobility);
    }

    public void deleteAllByRelatedMobility(Mobility mobility) {
        log.debug("Request to delete all MobilityMobilities for Related Mobility : {}", mobility);
        mobilityMobilityRepository.deleteAllByRelatedMobility(mobility);
    }
} 