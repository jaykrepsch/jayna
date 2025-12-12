package me.jayna.repository;

import me.jayna.domain.FinanceAccount;
import me.jayna.domain.FinanceAccountRealestate;
import me.jayna.domain.RealEstate;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FinanceAccountRealEstate entity.
 */
@Repository
public interface FinanceAccountRealestateRepository
    extends JpaRepository<FinanceAccountRealestate, Long> {

  Optional<FinanceAccountRealestate> findOneByFinanceaccount(FinanceAccount financeaccount);

  Optional<FinanceAccountRealestate> findOneByRealestate(RealEstate realestate);

  Set<FinanceAccountRealestate> findAllByFinanceaccount(FinanceAccount financeaccount);

  Set<FinanceAccountRealestate> findAllByRealestate(RealEstate realestate);

}
