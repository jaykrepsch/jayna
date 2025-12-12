package me.jayna.repository;

import me.jayna.domain.Contract;
import me.jayna.domain.ContractFinanceAccount;
import me.jayna.domain.FinanceAccount;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ContractFinanceAccount entity.
 */
@Repository
public interface ContractFinanceAccountRepository
    extends JpaRepository<ContractFinanceAccount, Long> {

  Optional<ContractFinanceAccount> findOneByContract(Contract contract);

  Optional<ContractFinanceAccount> findOneByFinanceaccount(FinanceAccount financeaccount);

  Set<ContractFinanceAccount> findAllByContract(Contract contract);

  Set<ContractFinanceAccount> findAllByFinanceaccount(FinanceAccount financeaccount);

}
