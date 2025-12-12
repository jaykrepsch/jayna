package me.jayna.repository;

import me.jayna.domain.FinanceAccount;
import me.jayna.domain.FinanceAccountMobility;
import me.jayna.domain.Mobility;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FinanceAccountMobility entity.
 */
@Repository
public interface FinanceAccountMobilityRepository
    extends JpaRepository<FinanceAccountMobility, Long> {

  Optional<FinanceAccountMobility> findOneByFinanceaccount(FinanceAccount financeaccount);

  Optional<FinanceAccountMobility> findOneByMobility(Mobility mobility);

  Set<FinanceAccountMobility> findAllByFinanceaccount(FinanceAccount financeaccount);

  Set<FinanceAccountMobility> findAllByMobility(Mobility mobility);

}
