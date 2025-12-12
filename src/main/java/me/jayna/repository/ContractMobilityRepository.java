package me.jayna.repository;

import me.jayna.domain.Contract;
import me.jayna.domain.ContractMobility;
import me.jayna.domain.Mobility;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ContractMobility entity.
 */
@Repository
public interface ContractMobilityRepository
    extends JpaRepository<ContractMobility, Long> {

  Optional<ContractMobility> findOneByContract(Contract contract);

  Optional<ContractMobility> findOneByMobility(Mobility mobility);

  Set<ContractMobility> findAllByContract(Contract contract);

  Set<ContractMobility> findAllByMobility(Mobility mobility);

}
