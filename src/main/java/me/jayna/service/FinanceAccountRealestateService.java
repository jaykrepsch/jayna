package me.jayna.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import me.jayna.domain.FinanceAccount;
import me.jayna.domain.RealEstate;
import me.jayna.domain.FinanceAccountRealestate;
import me.jayna.repository.FinanceAccountRealestateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FinanceAccountRealestate}.
 */
@Service
@Transactional
public class FinanceAccountRealestateService {

  private final Logger log = LoggerFactory.getLogger(FinanceAccountRealestateService.class);

  private final FinanceAccountRealestateRepository financeAccountRealestateRepository;

  public FinanceAccountRealestateService(FinanceAccountRealestateRepository financeAccountRealestateRepository) {
    this.financeAccountRealestateRepository = financeAccountRealestateRepository;
  }

  public FinanceAccountRealestate save(FinanceAccountRealestate financeAccountRealestate) {
    log.debug("Request to save FinanceAccountRealestate : {}", financeAccountRealestate);
    return financeAccountRealestateRepository.save(financeAccountRealestate);
  }

  public FinanceAccountRealestate update(FinanceAccountRealestate financeAccountRealestate) {
    log.debug("Request to save FinanceAccountRealestate : {}", financeAccountRealestate);
    return financeAccountRealestateRepository.save(financeAccountRealestate);
  }

  @Transactional(readOnly = true)
  public Page<FinanceAccountRealestate> findAll(Pageable pageable) {
    log.debug("Request to get all FinanceAccountMobilities");
    return financeAccountRealestateRepository.findAll(pageable);
  }

  @Transactional(readOnly = true)
  public Optional<FinanceAccountRealestate> findByFinanceAccount(FinanceAccount financeAccount) {
    log.debug("Request to get FinanceAccountRealestate by financeAccount");
    return financeAccountRealestateRepository.findOneByFinanceaccount(financeAccount);
  }

  @Transactional(readOnly = true)
  public Optional<FinanceAccountRealestate> findByRealestate(RealEstate realestate) {
    log.debug("Request to get FinanceAccountRealestate by finmobility");
    return financeAccountRealestateRepository.findOneByRealestate(realestate);
  }

  @Transactional(readOnly = true)
  public Set<FinanceAccountRealestate> findAllByFinanceAccount(FinanceAccount financeAccount) {
    log.debug("Request to get FinanceAccountRealestate by financeAccount");
    return financeAccountRealestateRepository.findAllByFinanceaccount(financeAccount);
  }

  @Transactional(readOnly = true)
  public Set<FinanceAccountRealestate> findAllByRealestate(RealEstate realestate) {
    log.debug("Request to get FinanceAccountRealestate by realestate");
    return financeAccountRealestateRepository.findAllByRealestate(realestate);
  }

  @Transactional(readOnly = true)
  public Optional<FinanceAccountRealestate> findOne(Long id) {
    log.debug("Request to get FinanceAccountRealestate : {}", id);
    return financeAccountRealestateRepository.findById(id);
  }

  public void delete(Long id) {
    log.debug("Request to delete FinanceAccountRealestate : {}", id);
    financeAccountRealestateRepository.deleteById(id);
  }

  public void deleteAll(Iterable<FinanceAccountRealestate> collection) {
    log.debug("Request to delete all FinanceAccountMobilities : {}", collection);
    financeAccountRealestateRepository.deleteAll(collection);
  }

  public void saveFinanceAccountRealestateRelationships(Set<FinanceAccountRealestate> financeAccountMobilities,
      Object entity) {
    financeAccountMobilities
        .forEach(cr -> {
          if (entity instanceof FinanceAccount) {
            final FinanceAccount financeAccount = (FinanceAccount) entity;
            cr.setFinanceaccount(financeAccount);
            financeAccount.getFinanceaccountRealestates().add(cr);
          } else if (entity instanceof RealEstate) {
            final RealEstate realestate = (RealEstate) entity;
            cr.setRealestate(realestate);
            realestate.getFinanceaccountRealestates().add(cr);
          }
          save(cr);
        });
  }

  public void updateFinanceAccountRealestateRelationships(Set<FinanceAccountRealestate> requestFinanceAccountMobilities,
      Object entity) {
    final Set<FinanceAccountRealestate> financeAccountMobilities = new HashSet<>();

    if (entity instanceof FinanceAccount) {
      final FinanceAccount financeAccount = (FinanceAccount) entity;
      financeAccountMobilities.addAll(findAllByFinanceAccount(financeAccount));
    } else if (entity instanceof RealEstate) {
      final RealEstate realestate = (RealEstate) entity;
      financeAccountMobilities.addAll(findAllByRealestate(realestate));
    }

    final Set<FinanceAccountRealestate> financeAccountMobilitiesToDelete = new HashSet<>();

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
            } else if (entity instanceof RealEstate) {
              final RealEstate realestate = (RealEstate) entity;
              cr.setRealestate(realestate);
            }
            final FinanceAccountRealestate result = save(cr);
            cr.setId(result.getId());
          }
        });
  }

  public void deleteRelationships(Object entity) {
    try {
      if (entity instanceof FinanceAccount) {
        final FinanceAccount financeAccount = (FinanceAccount) entity;
        Set<FinanceAccountRealestate> relationships = findAllByFinanceAccount(financeAccount);
        if (!relationships.isEmpty()) {
          deleteAll(relationships);
        }
      } else if (entity instanceof RealEstate) {
        final RealEstate realestate = (RealEstate) entity;
        Set<FinanceAccountRealestate> relationships = findAllByRealestate(realestate);
        if (!relationships.isEmpty()) {
          deleteAll(relationships);
        }
      }
    } catch (Exception e) {
      log.warn("Error deleting FinanceAccountRealestate relationships: {}", e.getMessage());
      // Versuche einzelne Löschungen, falls die Batch-Löschung fehlschlägt
      try {
        if (entity instanceof FinanceAccount) {
          final FinanceAccount financeAccount = (FinanceAccount) entity;
          Set<FinanceAccountRealestate> relationships = findAllByFinanceAccount(financeAccount);
          for (FinanceAccountRealestate relationship : relationships) {
            try {
              delete(relationship.getId());
            } catch (Exception ex) {
              log.warn("Error deleting individual FinanceAccountRealestate relationship {}: {}", relationship.getId(), ex.getMessage());
            }
          }
        } else if (entity instanceof RealEstate) {
          final RealEstate realestate = (RealEstate) entity;
          Set<FinanceAccountRealestate> relationships = findAllByRealestate(realestate);
          for (FinanceAccountRealestate relationship : relationships) {
            try {
              delete(relationship.getId());
            } catch (Exception ex) {
              log.warn("Error deleting individual FinanceAccountRealestate relationship {}: {}", relationship.getId(), ex.getMessage());
            }
          }
        }
      } catch (Exception ex) {
        log.error("Error during individual FinanceAccountRealestate relationship deletion: {}", ex.getMessage());
      }
    }
  }
}
