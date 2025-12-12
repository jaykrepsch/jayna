package me.jayna.service;

import me.jayna.domain.Contract;
import me.jayna.domain.ContractContract;
import me.jayna.repository.ContractContractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContractContractService {

    private final Logger log = LoggerFactory.getLogger(ContractContractService.class);

    private final ContractContractRepository contractContractRepository;

    public ContractContractService(ContractContractRepository contractContractRepository) {
        this.contractContractRepository = contractContractRepository;
    }

    public ContractContract save(ContractContract contractContract) {
        log.debug("Request to save ContractContract : {}", contractContract);
        return contractContractRepository.save(contractContract);
    }

    public List<ContractContract> findAllByContract(Contract contract) {
        log.debug("Request to get all ContractContracts for Contract : {}", contract);
        return contractContractRepository.findAllByContract(contract);
    }

    public List<ContractContract> findAllByRelatedContract(Contract contract) {
        log.debug("Request to get all ContractContracts for Related Contract : {}", contract);
        return contractContractRepository.findAllByRelatedContract(contract);
    }

    public Optional<ContractContract> findOne(Long id) {
        log.debug("Request to get ContractContract : {}", id);
        return contractContractRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete ContractContract : {}", id);
        contractContractRepository.deleteById(id);
    }

    public void deleteAllByContract(Contract contract) {
        log.debug("Request to delete all ContractContracts for Contract : {}", contract);
        contractContractRepository.deleteAllByContract(contract);
    }

    public void deleteAllByRelatedContract(Contract contract) {
        log.debug("Request to delete all ContractContracts for Related Contract : {}", contract);
        contractContractRepository.deleteAllByRelatedContract(contract);
    }

    public void saveContractContractRelationships(Set<ContractContract> contractContracts, Object entity) {
        contractContracts
            .forEach(cc -> {
                if (entity instanceof Contract) {
                    final Contract contract = (Contract) entity;
                    cc.setContract(contract);
                    contract.getContractContracts().add(cc);
                }
                save(cc);
            });
    }

    public void updateContractContractRelationships(Set<ContractContract> requestContractContracts, Object entity) {
        final Set<ContractContract> contractContracts = new HashSet<>();

        if (entity instanceof Contract) {
            final Contract contract = (Contract) entity;
            contractContracts.addAll(findAllByContract(contract));
        }

        final Set<ContractContract> contractContractsToDelete = new HashSet<>();

        contractContractsToDelete.addAll(
            contractContracts.stream()
                .filter(cc -> requestContractContracts.stream().filter(item -> cc.getId().equals(item.getId()))
                    .count() == 0)
                .collect(Collectors.toSet()));

        if (contractContractsToDelete.size() > 0) {
            deleteAll(contractContractsToDelete);
        }

        requestContractContracts
            .forEach(cc -> {
                if (cc.getId() == null) {
                    if (entity instanceof Contract) {
                        final Contract contract = (Contract) entity;
                        cc.setContract(contract);
                    }
                    final ContractContract result = save(cc);
                    cc.setId(result.getId());
                }
            });
    }

    public void deleteRelationships(Object entity) {
        if (entity instanceof Contract) {
            final Contract contract = (Contract) entity;
            deleteAllByContract(contract);
        }
    }

    public void deleteAll(Collection<ContractContract> collection) {
        log.debug("Request to delete all ContractContracts : {}", collection);
        contractContractRepository.deleteAll(collection);
    }
} 