package me.jayna.service;

import me.jayna.domain.FinanceAccount;
import me.jayna.domain.FinanceAccountFinanceAccount;
import me.jayna.repository.FinanceAccountFinanceAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FinanceAccountFinanceAccountService {

    private final Logger log = LoggerFactory.getLogger(FinanceAccountFinanceAccountService.class);

    private final FinanceAccountFinanceAccountRepository financeAccountFinanceAccountRepository;

    public FinanceAccountFinanceAccountService(FinanceAccountFinanceAccountRepository financeAccountFinanceAccountRepository) {
        this.financeAccountFinanceAccountRepository = financeAccountFinanceAccountRepository;
    }

    public FinanceAccountFinanceAccount save(FinanceAccountFinanceAccount financeAccountFinanceAccount) {
        log.debug("Request to save FinanceAccountFinanceAccount : {}", financeAccountFinanceAccount);
        return financeAccountFinanceAccountRepository.save(financeAccountFinanceAccount);
    }

    public List<FinanceAccountFinanceAccount> findAllByFinanceAccount(FinanceAccount financeAccount) {
        log.debug("Request to get all FinanceAccountFinanceAccounts for FinanceAccount : {}", financeAccount);
        return financeAccountFinanceAccountRepository.findAllByFinanceAccount(financeAccount);
    }

    public List<FinanceAccountFinanceAccount> findAllByRelatedFinanceAccount(FinanceAccount financeAccount) {
        log.debug("Request to get all FinanceAccountFinanceAccounts for Related FinanceAccount : {}", financeAccount);
        return financeAccountFinanceAccountRepository.findAllByRelatedFinanceAccount(financeAccount);
    }

    public Optional<FinanceAccountFinanceAccount> findOne(Long id) {
        log.debug("Request to get FinanceAccountFinanceAccount : {}", id);
        return financeAccountFinanceAccountRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete FinanceAccountFinanceAccount : {}", id);
        financeAccountFinanceAccountRepository.deleteById(id);
    }

    public void deleteAllByFinanceAccount(FinanceAccount financeAccount) {
        log.debug("Request to delete all FinanceAccountFinanceAccounts for FinanceAccount : {}", financeAccount);
        financeAccountFinanceAccountRepository.deleteAllByFinanceAccount(financeAccount);
    }

    public void deleteAllByRelatedFinanceAccount(FinanceAccount financeAccount) {
        log.debug("Request to delete all FinanceAccountFinanceAccounts for Related FinanceAccount : {}", financeAccount);
        financeAccountFinanceAccountRepository.deleteAllByRelatedFinanceAccount(financeAccount);
    }
} 