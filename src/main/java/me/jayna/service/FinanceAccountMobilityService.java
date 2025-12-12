package me.jayna.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import me.jayna.domain.FinanceAccount;
import me.jayna.domain.Mobility;
import me.jayna.domain.FinanceAccountMobility;
import me.jayna.repository.FinanceAccountMobilityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FinanceAccountMobility}.
 */
@Service
@Transactional
public class FinanceAccountMobilityService {

  private final Logger log = LoggerFactory.getLogger(FinanceAccountMobilityService.class);

  private final FinanceAccountMobilityRepository financeAccountMobilityRepository;

  public FinanceAccountMobilityService(FinanceAccountMobilityRepository financeAccountMobilityRepository) {
    this.financeAccountMobilityRepository = financeAccountMobilityRepository;
  }

  public FinanceAccountMobility save(FinanceAccountMobility financeAccountMobility) {
    log.debug("Request to save FinanceAccountMobility : {}", financeAccountMobility);
    return financeAccountMobilityRepository.save(financeAccountMobility);
  }

  public FinanceAccountMobility update(FinanceAccountMobility financeAccountMobility) {
    log.debug("Request to save FinanceAccountMobility : {}", financeAccountMobility);
    return financeAccountMobilityRepository.save(financeAccountMobility);
  }

  @Transactional(readOnly = true)
  public Page<FinanceAccountMobility> findAll(Pageable pageable) {
    log.debug("Request to get all FinanceAccountMobilities");
    return financeAccountMobilityRepository.findAll(pageable);
  }

  @Transactional(readOnly = true)
  public Optional<FinanceAccountMobility> findByFinanceAccount(FinanceAccount financeAccount) {
    log.debug("Request to get FinanceAccountMobility by financeAccount");
    return financeAccountMobilityRepository.findOneByFinanceaccount(financeAccount);
  }

  @Transactional(readOnly = true)
  public Optional<FinanceAccountMobility> findByMobility(Mobility mobility) {
    log.debug("Request to get FinanceAccountMobility by finmobility");
    return financeAccountMobilityRepository.findOneByMobility(mobility);
  }

  @Transactional(readOnly = true)
  public Set<FinanceAccountMobility> findAllByFinanceAccount(FinanceAccount financeAccount) {
    log.debug("Request to get FinanceAccountMobility by financeAccount");
    return financeAccountMobilityRepository.findAllByFinanceaccount(financeAccount);
  }

  @Transactional(readOnly = true)
  public Set<FinanceAccountMobility> findAllByMobility(Mobility mobility) {
    log.debug("Request to get FinanceAccountMobility by mobility");
    return financeAccountMobilityRepository.findAllByMobility(mobility);
  }

  @Transactional(readOnly = true)
  public Optional<FinanceAccountMobility> findOne(Long id) {
    log.debug("Request to get FinanceAccountMobility : {}", id);
    return financeAccountMobilityRepository.findById(id);
  }

  public void delete(Long id) {
    log.debug("Request to delete FinanceAccountMobility : {}", id);
    financeAccountMobilityRepository.deleteById(id);
  }

  public void deleteAll(Iterable<FinanceAccountMobility> collection) {
    log.debug("Request to delete all FinanceAccountMobilities : {}", collection);
    financeAccountMobilityRepository.deleteAll(collection);
  }

  public void saveFinanceAccountMobilityRelationships(Set<FinanceAccountMobility> financeAccountMobilities,
      Object entity) {
    financeAccountMobilities
        .forEach(cr -> {
          if (entity instanceof FinanceAccount) {
            final FinanceAccount financeAccount = (FinanceAccount) entity;
            cr.setFinanceaccount(financeAccount);
            financeAccount.getFinanceaccountMobilities().add(cr);
          } else if (entity instanceof Mobility) {
            final Mobility mobility = (Mobility) entity;
            cr.setMobility(mobility);
            mobility.getFinanceaccountMobilities().add(cr);
          }
          save(cr);
        });
  }

  public void updateFinanceAccountMobilityRelationships(Set<FinanceAccountMobility> requestFinanceAccountMobilities,
      Object entity) {
    final Set<FinanceAccountMobility> financeAccountMobilities = new HashSet<>();

    if (entity instanceof FinanceAccount) {
      final FinanceAccount financeAccount = (FinanceAccount) entity;
      financeAccountMobilities.addAll(findAllByFinanceAccount(financeAccount));
    } else if (entity instanceof Mobility) {
      final Mobility mobility = (Mobility) entity;
      financeAccountMobilities.addAll(findAllByMobility(mobility));
    }

    final Set<FinanceAccountMobility> financeAccountMobilitiesToDelete = new HashSet<>();

    financeAccountMobilitiesToDelete.addAll(
        financeAccountMobilities.stream()
            .filter(cr -> requestFinanceAccountMobilities.stream().filter(item -> cr.getId().equals(item.getId()))
                .count() == 0)
            .collect(Collectors.toSet()));

    if (financeAccountMobilitiesToDelete.size() > 0) {
      deleteAll(financeAccountMobilitiesToDelete);
    }

    requestFinanceAccountMobilities
        .forEach(cr -> {
          if (cr.getId() == null) {
            if (entity instanceof FinanceAccount) {
              final FinanceAccount financeAccount = (FinanceAccount) entity;
              cr.setFinanceaccount(financeAccount);
            } else if (entity instanceof Mobility) {
              final Mobility mobility = (Mobility) entity;
              cr.setMobility(mobility);
            }
            final FinanceAccountMobility result = save(cr);
            cr.setId(result.getId());
          }
        });
  }

  public void deleteRelationships(Object entity) {
    if (entity instanceof FinanceAccount) {
      final FinanceAccount financeAccount = (FinanceAccount) entity;
      deleteAll(findAllByFinanceAccount(financeAccount));
    } else if (entity instanceof Mobility) {
      final Mobility mobility = (Mobility) entity;
      deleteAll(findAllByMobility(mobility));
    }
  }
}
