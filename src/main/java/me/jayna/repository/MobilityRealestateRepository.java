package me.jayna.repository;

import me.jayna.domain.Mobility;
import me.jayna.domain.MobilityRealestate;
import me.jayna.domain.RealEstate;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MobilityRealEstate entity.
 */
@Repository
public interface MobilityRealestateRepository
    extends JpaRepository<MobilityRealestate, Long> {

  Optional<MobilityRealestate> findOneByMobility(Mobility mobility);

  Optional<MobilityRealestate> findOneByRealestate(RealEstate realestate);

  Set<MobilityRealestate> findAllByMobility(Mobility mobility);

  Set<MobilityRealestate> findAllByRealestate(RealEstate realestate);

}
