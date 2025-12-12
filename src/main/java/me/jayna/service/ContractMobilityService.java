package me.jayna.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import me.jayna.domain.Contract;
import me.jayna.domain.Mobility;
import me.jayna.domain.ContractMobility;
import me.jayna.repository.ContractMobilityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ContractMobility}.
 */
@Service
@Transactional
public class ContractMobilityService {

  private final Logger log = LoggerFactory.getLogger(ContractMobilityService.class);

  private final ContractMobilityRepository contractMobilityRepository;

  public ContractMobilityService(ContractMobilityRepository contractMobilityRepository) {
    this.contractMobilityRepository = contractMobilityRepository;
  }

  public ContractMobility save(ContractMobility contractMobility) {
    log.debug("Request to save ContractMobility : {}", contractMobility);
    return contractMobilityRepository.save(contractMobility);
  }

  public ContractMobility update(ContractMobility contractMobility) {
    log.debug("Request to save ContractMobility : {}", contractMobility);
    return contractMobilityRepository.save(contractMobility);
  }

  @Transactional(readOnly = true)
  public Page<ContractMobility> findAll(Pageable pageable) {
    log.debug("Request to get all ContractMobilities");
    return contractMobilityRepository.findAll(pageable);
  }

  @Transactional(readOnly = true)
  public Optional<ContractMobility> findByContract(Contract contract) {
    log.debug("Request to get ContractMobility by contract");
    return contractMobilityRepository.findOneByContract(contract);
  }

  @Transactional(readOnly = true)
  public Optional<ContractMobility> findByMobility(Mobility mobility) {
    log.debug("Request to get ContractMobility by finmobility");
    return contractMobilityRepository.findOneByMobility(mobility);
  }

  @Transactional(readOnly = true)
  public Set<ContractMobility> findAllByContract(Contract contract) {
    log.debug("Request to get ContractMobility by contract");
    return contractMobilityRepository.findAllByContract(contract);
  }

  @Transactional(readOnly = true)
  public Set<ContractMobility> findAllByMobility(Mobility mobility) {
    log.debug("Request to get ContractMobility by mobility");
    return contractMobilityRepository.findAllByMobility(mobility);
  }

  @Transactional(readOnly = true)
  public Optional<ContractMobility> findOne(Long id) {
    log.debug("Request to get ContractMobility : {}", id);
    return contractMobilityRepository.findById(id);
  }

  public void delete(Long id) {
    log.debug("Request to delete ContractMobility : {}", id);
    contractMobilityRepository.deleteById(id);
  }

  public void deleteAll(Iterable<ContractMobility> collection) {
    log.debug("Request to delete all ContractMobilities : {}", collection);
    contractMobilityRepository.deleteAll(collection);
  }

  public void saveContractMobilityRelationships(Set<ContractMobility> contractMobilities, Object entity) {
    contractMobilities
        .forEach(cr -> {
          if (entity instanceof Contract) {
            final Contract contract = (Contract) entity;
            cr.setContract(contract);
            contract.getContractMobilities().add(cr);
          } else if (entity instanceof Mobility) {
            final Mobility mobility = (Mobility) entity;
            cr.setMobility(mobility);
            mobility.getContractMobilities().add(cr);
          }
          save(cr);
        });
  }

  public void updateContractMobilityRelationships(Set<ContractMobility> requestContractMobilities,
      Object entity) {
    final Set<ContractMobility> contractMobilities = new HashSet<>();

    if (entity instanceof Contract) {
      final Contract contract = (Contract) entity;
      contractMobilities.addAll(findAllByContract(contract));
    } else if (entity instanceof Mobility) {
      final Mobility mobility = (Mobility) entity;
      contractMobilities.addAll(findAllByMobility(mobility));
    }

    final Set<ContractMobility> contractMobilitiesToDelete = new HashSet<>();

    contractMobilitiesToDelete.addAll(
        contractMobilities.stream()
            .filter(cr -> requestContractMobilities.stream().filter(item -> cr.getId().equals(item.getId()))
                .count() == 0)
            .collect(Collectors.toSet()));

    if (contractMobilitiesToDelete.size() > 0) {
      deleteAll(contractMobilitiesToDelete);
    }

    requestContractMobilities
        .forEach(cr -> {
          if (cr.getId() == null) {
            if (entity instanceof Contract) {
              final Contract contract = (Contract) entity;
              cr.setContract(contract);
            } else if (entity instanceof Mobility) {
              final Mobility mobility = (Mobility) entity;
              cr.setMobility(mobility);
            }
            final ContractMobility result = save(cr);
            cr.setId(result.getId());
          }
        });
  }

  public void deleteRelationships(Object entity) {
    if (entity instanceof Contract) {
      final Contract contract = (Contract) entity;
      deleteAll(findAllByContract(contract));
    } else if (entity instanceof Mobility) {
      final Mobility mobility = (Mobility) entity;
      deleteAll(findAllByMobility(mobility));
    }
  }
}
