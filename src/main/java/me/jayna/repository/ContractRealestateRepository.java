package me.jayna.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import me.jayna.domain.Contract;
import me.jayna.domain.ContractRealestate;
import me.jayna.domain.RealEstate;

/**
 * Spring Data JPA repository for the RealEstate entity.
 */
@Repository
public interface ContractRealestateRepository
    extends
    JpaRepository<ContractRealestate, Long>, JpaSpecificationExecutor<ContractRealestate> {

  Optional<ContractRealestate> findOneByContract(Contract contract);

  Optional<ContractRealestate> findOneByRealestate(RealEstate realestate);

  Set<ContractRealestate> findAllByContract(Contract contract);

  Set<ContractRealestate> findAllByRealestate(RealEstate realestate);
}
