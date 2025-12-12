package me.jayna.repository;

import me.jayna.domain.Contract;
import me.jayna.domain.ContractContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractContractRepository extends JpaRepository<ContractContract, Long> {
    List<ContractContract> findAllByContract(Contract contract);
    List<ContractContract> findAllByRelatedContract(Contract contract);
    void deleteAllByContract(Contract contract);
    void deleteAllByRelatedContract(Contract contract);
} 