package me.jayna.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import me.jayna.domain.Contract;
import me.jayna.domain.FinanceAccount;
import me.jayna.domain.ContractFinanceAccount;
import me.jayna.repository.ContractFinanceAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ContractFinanceAccount}.
 */
@Service
@Transactional
public class ContractFinanceAccountService {

  private final Logger log = LoggerFactory.getLogger(ContractFinanceAccountService.class);

  private final ContractFinanceAccountRepository contactFinanceAccountRepository;

  public ContractFinanceAccountService(ContractFinanceAccountRepository contactFinanceAccountRepository) {
    this.contactFinanceAccountRepository = contactFinanceAccountRepository;
  }

  public ContractFinanceAccount save(ContractFinanceAccount contractFinanceAccount) {
    log.debug("Request to save ContractFinanceAccount : {}", contractFinanceAccount);
    return contactFinanceAccountRepository.save(contractFinanceAccount);
  }

  public ContractFinanceAccount update(ContractFinanceAccount contractFinanceAccount) {
    log.debug("Request to save ContractFinanceAccount : {}", contractFinanceAccount);
    return contactFinanceAccountRepository.save(contractFinanceAccount);
  }

  @Transactional(readOnly = true)
  public Page<ContractFinanceAccount> findAll(Pageable pageable) {
    log.debug("Request to get all ContractFinanceAccounts");
    return contactFinanceAccountRepository.findAll(pageable);
  }

  @Transactional(readOnly = true)
  public Optional<ContractFinanceAccount> findByContract(Contract contact) {
    log.debug("Request to get ContractFinanceAccount by contact");
    return contactFinanceAccountRepository.findOneByContract(contact);
  }

  @Transactional(readOnly = true)
  public Optional<ContractFinanceAccount> findByFinanceAccount(FinanceAccount financeAccount) {
    log.debug("Request to get ContractFinanceAccount by finfinanceAccount");
    return contactFinanceAccountRepository.findOneByFinanceaccount(financeAccount);
  }

  @Transactional(readOnly = true)
  public Set<ContractFinanceAccount> findAllByContract(Contract contact) {
    log.debug("Request to get ContractFinanceAccount by contact");
    return contactFinanceAccountRepository.findAllByContract(contact);
  }

  @Transactional(readOnly = true)
  public Set<ContractFinanceAccount> findAllByFinanceAccount(FinanceAccount financeAccount) {
    log.debug("Request to get ContractFinanceAccount by financeAccount");
    return contactFinanceAccountRepository.findAllByFinanceaccount(financeAccount);
  }

  @Transactional(readOnly = true)
  public Optional<ContractFinanceAccount> findOne(Long id) {
    log.debug("Request to get ContractFinanceAccount : {}", id);
    return contactFinanceAccountRepository.findById(id);
  }

  public void delete(Long id) {
    log.debug("Request to delete ContractFinanceAccount : {}", id);
    contactFinanceAccountRepository.deleteById(id);
  }

  public void deleteAll(Iterable<ContractFinanceAccount> collection) {
    log.debug("Request to delete all ContractFinanceAccounts : {}", collection);
    contactFinanceAccountRepository.deleteAll(collection);
  }

  public void saveContractFinanceAccountRelationships(Set<ContractFinanceAccount> contractFinanceAccounts,
      Object entity) {
    contractFinanceAccounts
        .forEach(cr -> {
          if (entity instanceof Contract) {
            final Contract contact = (Contract) entity;
            cr.setContract(contact);
            contact.getContractFinanceAccounts().add(cr);
          } else if (entity instanceof FinanceAccount) {
            final FinanceAccount financeAccount = (FinanceAccount) entity;
            cr.setFinanceaccount(financeAccount);
            financeAccount.getContractFinanceAccounts().add(cr);
          }
          save(cr);
        });
  }

  public void updateContractFinanceAccountRelationships(Set<ContractFinanceAccount> requestContractFinanceAccounts,
      Object entity) {
    final Set<ContractFinanceAccount> contractFinanceAccounts = new HashSet<>();

    if (entity instanceof Contract) {
      final Contract contract = (Contract) entity;
      contractFinanceAccounts.addAll(findAllByContract(contract));
    } else if (entity instanceof FinanceAccount) {
      final FinanceAccount financeAccount = (FinanceAccount) entity;
      contractFinanceAccounts.addAll(findAllByFinanceAccount(financeAccount));
    }

    final Set<ContractFinanceAccount> contractFinanceAccountsToDelete = new HashSet<>();

    contractFinanceAccountsToDelete.addAll(
        contractFinanceAccounts.stream()
            .filter(cr -> requestContractFinanceAccounts.stream().filter(item -> cr.getId().equals(item.getId()))
                .count() == 0)
            .collect(Collectors.toSet()));

    if (contractFinanceAccountsToDelete.size() > 0) {
      deleteAll(contractFinanceAccountsToDelete);
    }

    requestContractFinanceAccounts
        .forEach(cr -> {
          if (cr.getId() == null) {
            if (entity instanceof Contract) {
              final Contract contract = (Contract) entity;
              cr.setContract(contract);
            } else if (entity instanceof FinanceAccount) {
              final FinanceAccount financeAccount = (FinanceAccount) entity;
              cr.setFinanceaccount(financeAccount);
            }
            final ContractFinanceAccount result = save(cr);
            cr.setId(result.getId());
          }
        });
  }

  public void deleteRelationships(Object entity) {
    if (entity instanceof Contract) {
      final Contract contract = (Contract) entity;
      deleteAll(findAllByContract(contract));
    } else if (entity instanceof FinanceAccount) {
      final FinanceAccount financeAccount = (FinanceAccount) entity;
      deleteAll(findAllByFinanceAccount(financeAccount));
    }
  }
}
