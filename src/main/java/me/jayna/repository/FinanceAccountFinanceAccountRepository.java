package me.jayna.repository;

import me.jayna.domain.FinanceAccount;
import me.jayna.domain.FinanceAccountFinanceAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinanceAccountFinanceAccountRepository extends JpaRepository<FinanceAccountFinanceAccount, Long> {
    List<FinanceAccountFinanceAccount> findAllByFinanceAccount(FinanceAccount financeAccount);
    List<FinanceAccountFinanceAccount> findAllByRelatedFinanceAccount(FinanceAccount financeAccount);
    void deleteAllByFinanceAccount(FinanceAccount financeAccount);
    void deleteAllByRelatedFinanceAccount(FinanceAccount financeAccount);
} 