package me.jayna.repository;

import me.jayna.domain.Mobility;
import me.jayna.domain.MobilityMobility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MobilityMobilityRepository extends JpaRepository<MobilityMobility, Long> {
    List<MobilityMobility> findAllByMobility(Mobility mobility);
    List<MobilityMobility> findAllByRelatedMobility(Mobility mobility);
    void deleteAllByMobility(Mobility mobility);
    void deleteAllByRelatedMobility(Mobility mobility);
} 