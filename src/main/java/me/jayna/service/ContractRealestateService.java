package me.jayna.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import me.jayna.domain.Contract;
import me.jayna.domain.ContractRealestate;
import me.jayna.domain.RealEstate;
import me.jayna.repository.ContractRealestateRepository;
import me.jayna.repository.RealEstateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ContractRealestate}.
 */
@Service
@Transactional
public class ContractRealestateService {

  private final Logger log = LoggerFactory.getLogger(ContractRealestateService.class);

  private final ContractRealestateRepository contractRealestateRepository;
  private final RealEstateRepository realEstateRepository;

  public ContractRealestateService(ContractRealestateRepository contractRealestateRepository, RealEstateRepository realEstateRepository) {
    this.contractRealestateRepository = contractRealestateRepository;
    this.realEstateRepository = realEstateRepository;
  }

  public ContractRealestate save(ContractRealestate contractRealestate) {
    log.debug("Request to save ContractRealestate : {}", contractRealestate);
    return contractRealestateRepository.save(contractRealestate);
  }

  public ContractRealestate update(ContractRealestate contractRealestate) {
    log.debug("Request to save ContractRealestate : {}", contractRealestate);
    return contractRealestateRepository.save(contractRealestate);
  }

  @Transactional(readOnly = true)
  public Page<ContractRealestate> findAll(Pageable pageable) {
    log.debug("Request to get all ContractRealestates");
    return contractRealestateRepository.findAll(pageable);
  }

  @Transactional(readOnly = true)
  public Optional<ContractRealestate> findByContract(Contract contract) {
    log.debug("Request to get ContractRealestate by contract");
    return contractRealestateRepository.findOneByContract(contract);
  }

  @Transactional(readOnly = true)
  public Optional<ContractRealestate> findByRealEstate(RealEstate realEstate) {
    log.debug("Request to get ContractRealestate by realestate");
    return contractRealestateRepository.findOneByRealestate(realEstate);
  }

  @Transactional(readOnly = true)
  public Set<ContractRealestate> findAllByContract(Contract contract) {
    log.debug("Request to get ContractRealestate by contract");
    return contractRealestateRepository.findAllByContract(contract);
  }

  @Transactional(readOnly = true)
  public Set<ContractRealestate> findAllByRealEstate(RealEstate realEstate) {
    log.debug("Request to get ContractRealestate by realestate");
    return contractRealestateRepository.findAllByRealestate(realEstate);
  }

  @Transactional(readOnly = true)
  public Optional<ContractRealestate> findOne(Long id) {
    log.debug("Request to get ContractRealestate : {}", id);
    return contractRealestateRepository.findById(id);
  }

  public void delete(Long id) {
    log.debug("Request to delete ContractRealestate : {}", id);
    contractRealestateRepository.deleteById(id);
  }

  public void deleteAll(Iterable<ContractRealestate> collection) {
    log.debug("Request to delete all ContractRealestates : {}", collection);
    contractRealestateRepository.deleteAll(collection);
  }

  public void saveContractRealestateRelationships(Set<ContractRealestate> contractRealestates, Object entity) {
    contractRealestates
        .forEach(cr -> {
          if (entity instanceof Contract) {
            final Contract contract = (Contract) entity;
            cr.setContract(contract);
            contract.getContractRealestates().add(cr);
          } else if (entity instanceof RealEstate) {
            final RealEstate realEstate = (RealEstate) entity;
            cr.setRealestate(realEstate);
            realEstate.getContractRealestates().add(cr);
          }
          save(cr);
        });
  }

  public void updateContractRealestateRelationships(Set<ContractRealestate> requestContractRealestates, Object entity) {
    final Set<ContractRealestate> contractRealestates = new HashSet<>();

    if (entity instanceof Contract) {
      final Contract contract = (Contract) entity;
      contractRealestates.addAll(findAllByContract(contract));
    } else if (entity instanceof RealEstate) {
      final RealEstate realEstate = (RealEstate) entity;
      contractRealestates.addAll(findAllByRealEstate(realEstate));
    }

    final Set<ContractRealestate> contractRealestatesToDelete = new HashSet<>();

    contractRealestatesToDelete.addAll(
        contractRealestates.stream()
            .filter(cr -> requestContractRealestates.stream().filter(item -> cr.getId().equals(item.getId()))
                .count() == 0)
            .collect(Collectors.toSet()));

    if (contractRealestatesToDelete.size() > 0) {
      deleteAll(contractRealestatesToDelete);
    }

    requestContractRealestates
        .forEach(cr -> {
          if (cr.getId() == null) {
            if (entity instanceof Contract) {
              final Contract contract = (Contract) entity;
              cr.setContract(contract);
              // Stelle sicher, dass die realestate Referenz korrekt gesetzt wird
              if (cr.getRealestate() != null && cr.getRealestate().getId() != null) {
                // Die realestate Referenz ist bereits gesetzt vom Frontend
                log.debug("Setting realestate reference for new ContractRealestate: {}", cr.getRealestate().getId());
                // Lade die RealEstate Entity, wenn nur die ID gesetzt ist
                if (cr.getRealestate().getApplicationUser() == null) {
                  // Wenn nur die ID gesetzt ist, müssen wir die Referenz korrekt laden
                  log.debug("Realestate reference has only ID, loading full entity");
                  realEstateRepository.findById(cr.getRealestate().getId()).ifPresent(cr::setRealestate);
                }
              }
            } else if (entity instanceof RealEstate) {
              final RealEstate realEstate = (RealEstate) entity;
              cr.setRealestate(realEstate);
            }
            final ContractRealestate result = save(cr);
            cr.setId(result.getId());
          }
        });
  }

  public void deleteRelationships(Object entity) {
    try {
      if (entity instanceof Contract) {
        final Contract contract = (Contract) entity;
        Set<ContractRealestate> relationships = findAllByContract(contract);
        if (!relationships.isEmpty()) {
          deleteAll(relationships);
        }
      } else if (entity instanceof RealEstate) {
        final RealEstate realEstate = (RealEstate) entity;
        Set<ContractRealestate> relationships = findAllByRealEstate(realEstate);
        if (!relationships.isEmpty()) {
          deleteAll(relationships);
        }
      }
    } catch (Exception e) {
      log.warn("Error deleting ContractRealestate relationships: {}", e.getMessage());
      // Versuche einzelne Löschungen, falls die Batch-Löschung fehlschlägt
      try {
        if (entity instanceof Contract) {
          final Contract contract = (Contract) entity;
          Set<ContractRealestate> relationships = findAllByContract(contract);
          for (ContractRealestate relationship : relationships) {
            try {
              delete(relationship.getId());
            } catch (Exception ex) {
              log.warn("Error deleting individual ContractRealestate relationship {}: {}", relationship.getId(), ex.getMessage());
            }
          }
        } else if (entity instanceof RealEstate) {
          final RealEstate realEstate = (RealEstate) entity;
          Set<ContractRealestate> relationships = findAllByRealEstate(realEstate);
          for (ContractRealestate relationship : relationships) {
            try {
              delete(relationship.getId());
            } catch (Exception ex) {
              log.warn("Error deleting individual ContractRealestate relationship {}: {}", relationship.getId(), ex.getMessage());
            }
          }
        }
      } catch (Exception ex) {
        log.error("Error during individual ContractRealestate relationship deletion: {}", ex.getMessage());
      }
    }
  }
}
