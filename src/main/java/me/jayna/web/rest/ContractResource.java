package me.jayna.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import me.jayna.domain.*;
import me.jayna.repository.ContractRepository;
import me.jayna.repository.RealEstateRepository;
import me.jayna.repository.ContactRepository;
import me.jayna.repository.FinanceAccountRepository;
import me.jayna.repository.MobilityRepository;
import me.jayna.service.*;
import me.jayna.service.criteria.ContractCriteria;
import me.jayna.service.dto.NewRelationDTO;
import me.jayna.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
@Transactional
public class ContractResource {

    private final Logger log = LoggerFactory.getLogger(ContractResource.class);
    private static final String ENTITY_NAME = "contract";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContractService contractService;
    private final ContractRepository contractRepository;
    private final ContractQueryService contractQueryService;
    private final UserService userService;
    private final ContractRealestateService contractRealestateService;
    private final AuthorityConfigService authorityConfigService;
    private final ContactContractService contactContractService;
    private final ContractFinanceAccountService contractFinanceAccountService;
    private final ContractMobilityService contractMobilityService;
    private final GroupTypeService groupTypeService;
    private final ContractContractService contractContractService;
    private final ObjectMapper objectMapper;
    private final RealEstateRepository realestateRepository;
    private final ContactRepository contactRepository;
    private final FinanceAccountRepository financeAccountRepository;
    private final MobilityRepository mobilityRepository;

    public ContractResource(
        ContractService contractService,
        ContractRepository contractRepository,
        ContractQueryService contractQueryService,
        UserService userService,
        ContractRealestateService contractRealestateService,
        AuthorityConfigService authorityConfigService,
        ContactContractService contactContractService,
        ContractFinanceAccountService contractFinanceAccountService,
        ContractMobilityService contractMobilityService,
        GroupTypeService groupTypeService,
        ContractContractService contractContractService,
        ObjectMapper objectMapper,
        RealEstateRepository realestateRepository,
        ContactRepository contactRepository,
        FinanceAccountRepository financeAccountRepository,
        MobilityRepository mobilityRepository
    ) {
        this.contractService = contractService;
        this.contractRepository = contractRepository;
        this.contractQueryService = contractQueryService;
        this.userService = userService;
        this.contractRealestateService = contractRealestateService;
        this.authorityConfigService = authorityConfigService;
        this.contactContractService = contactContractService;
        this.contractFinanceAccountService = contractFinanceAccountService;
        this.contractMobilityService = contractMobilityService;
        this.groupTypeService = groupTypeService;
        this.contractContractService = contractContractService;
        this.objectMapper = objectMapper;
        this.realestateRepository = realestateRepository;
        this.contactRepository = contactRepository;
        this.financeAccountRepository = financeAccountRepository;
        this.mobilityRepository = mobilityRepository;
    }

    @PostMapping("/contracts")
    public ResponseEntity<Contract> createContract(@RequestBody Contract contract) throws URISyntaxException {
        log.debug("REST request to save Contract : {}", contract);
        if (contract.getId() != null) {
            throw new BadRequestAlertException("A new contract cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (!authorityConfigService.isUserAllowedToAddContract()) {
            throw new BadRequestAlertException("The user reached the maximum entities to add.", ENTITY_NAME, "insufficient_role");
        }
        final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
        contract.setApplicationUser(applicationUser);
        if (contract.getGroupType() != null && contract.getGroupType().getId() != null) {
            groupTypeService.findOne(contract.getGroupType().getId()).ifPresent(contract::setGroupType);
        } else {
            contract.setGroupType(null);
        }
        final Contract result = contractService.save(contract);
        contractRealestateService.saveContractRealestateRelationships(contract.getContractRealestates(), result);
        contactContractService.saveContactContractRelationships(contract.getContactContracts(), result);
        contractFinanceAccountService.saveContractFinanceAccountRelationships(contract.getContractFinanceAccounts(), result);
        contractMobilityService.saveContractMobilityRelationships(contract.getContractMobilities(), result);
        contractContractService.saveContractContractRelationships(contract.getContractContracts(), result);
        return ResponseEntity
            .created(new URI("/api/contracts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/contracts/{id}")
    public ResponseEntity<Contract> updateEmploymentContract(
        @PathVariable(value = "id") final Long id,
        @RequestBody Contract contractDetails
    )
        throws URISyntaxException {
        log.debug("REST request to update Contract : {}, {}", id, contractDetails);
        if (contractDetails.getId() == null || !Objects.equals(id, contractDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        Contract existingContract = contractRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));

        final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
        if (!existingContract.getApplicationUser().getId().equals(applicationUser.getId())) {
            throw new BadRequestAlertException("Not allowed", ENTITY_NAME, "notallowed");
        }

        existingContract.setLabel(contractDetails.getLabel());
        existingContract.setDescription(contractDetails.getDescription());

        if (contractDetails.getGroupType() != null && contractDetails.getGroupType().getId() != null) {
            groupTypeService.findOne(contractDetails.getGroupType().getId()).ifPresent(existingContract::setGroupType);
        }

        // Nur Verknüpfungen aktualisieren, wenn sie explizit gesendet werden UND nicht leer sind
        if (contractDetails.getContractRealestates() != null && !contractDetails.getContractRealestates().isEmpty()) {
            contractRealestateService.updateContractRealestateRelationships(contractDetails.getContractRealestates(), existingContract);
        }
        if (contractDetails.getContactContracts() != null && !contractDetails.getContactContracts().isEmpty()) {
            contactContractService.updateContactContractRelationships(contractDetails.getContactContracts(), existingContract);
        }
        if (contractDetails.getContractFinanceAccounts() != null && !contractDetails.getContractFinanceAccounts().isEmpty()) {
            contractFinanceAccountService.updateContractFinanceAccountRelationships(
                contractDetails.getContractFinanceAccounts(),
                existingContract
            );
        }
        if (contractDetails.getContractMobilities() != null && !contractDetails.getContractMobilities().isEmpty()) {
            contractMobilityService.updateContractMobilityRelationships(contractDetails.getContractMobilities(), existingContract);
        }
        if (contractDetails.getContractContracts() != null && !contractDetails.getContractContracts().isEmpty()) {
            contractContractService.updateContractContractRelationships(contractDetails.getContractContracts(), existingContract);
        }

        Contract result = contractService.save(existingContract);
        return getContract(result.getId(), true);
    }

    @PostMapping("/contracts/{id}/relate")
    public ResponseEntity<Contract> addRelation(@PathVariable(value = "id") final Long id, @RequestBody NewRelationDTO newRelation)
        throws URISyntaxException {
        log.debug("REST request to add relation to Contract : {}, {}", id, newRelation);

        final Contract contract = contractRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull"));

        String entityName = newRelation.getEntityName();
        Object relationData = newRelation.getRelationData();

        switch (entityName) {
            case "realestate":
                ContractRealestate cr = objectMapper.convertValue(relationData, ContractRealestate.class);
                cr.setContract(contract);
                // Stelle sicher, dass das realestate gesetzt ist
                if (cr.getRealestate() != null && cr.getRealestate().getId() != null) {
                    RealEstate realestate = realestateRepository.findById(cr.getRealestate().getId())
                        .orElseThrow(() -> new BadRequestAlertException("Realestate not found", ENTITY_NAME, "realestatenotfound"));
                    cr.setRealestate(realestate);
                } else {
                    throw new BadRequestAlertException("Realestate ID is required", ENTITY_NAME, "realestateidrequired");
                }
                contractRealestateService.save(cr);
                break;
            case "contact":
                ContactContract cc = objectMapper.convertValue(relationData, ContactContract.class);
                cc.setContract(contract);
                // Stelle sicher, dass das contact gesetzt ist
                if (cc.getContact() != null && cc.getContact().getId() != null) {
                    Contact contact = contactRepository.findById(cc.getContact().getId())
                        .orElseThrow(() -> new BadRequestAlertException("Contact not found", ENTITY_NAME, "contactnotfound"));
                    cc.setContact(contact);
                } else {
                    throw new BadRequestAlertException("Contact ID is required", ENTITY_NAME, "contactidrequired");
                }
                contactContractService.save(cc);
                break;
            case "financeaccount":
                ContractFinanceAccount cfa = objectMapper.convertValue(relationData, ContractFinanceAccount.class);
                cfa.setContract(contract);
                // Stelle sicher, dass das financeaccount gesetzt ist
                if (cfa.getFinanceaccount() != null && cfa.getFinanceaccount().getId() != null) {
                    FinanceAccount financeaccount = financeAccountRepository.findById(cfa.getFinanceaccount().getId())
                        .orElseThrow(() -> new BadRequestAlertException("FinanceAccount not found", ENTITY_NAME, "financeaccountnotfound"));
                    cfa.setFinanceaccount(financeaccount);
                } else {
                    throw new BadRequestAlertException("FinanceAccount ID is required", ENTITY_NAME, "financeaccountidrequired");
                }
                contractFinanceAccountService.save(cfa);
                break;
            case "mobility":
                ContractMobility cm = objectMapper.convertValue(relationData, ContractMobility.class);
                cm.setContract(contract);
                // Stelle sicher, dass das mobility gesetzt ist
                if (cm.getMobility() != null && cm.getMobility().getId() != null) {
                    Mobility mobility = mobilityRepository.findById(cm.getMobility().getId())
                        .orElseThrow(() -> new BadRequestAlertException("Mobility not found", ENTITY_NAME, "mobilitynotfound"));
                    cm.setMobility(mobility);
                } else {
                    throw new BadRequestAlertException("Mobility ID is required", ENTITY_NAME, "mobilityidrequired");
                }
                contractMobilityService.save(cm);
                break;
            case "contract":
                ContractContract ccon = objectMapper.convertValue(relationData, ContractContract.class);
                ccon.setContract(contract);
                // Stelle sicher, dass das relatedContract gesetzt ist
                if (ccon.getRelatedContract() != null && ccon.getRelatedContract().getId() != null) {
                    Contract relatedContract = contractRepository.findById(ccon.getRelatedContract().getId())
                        .orElseThrow(() -> new BadRequestAlertException("Related contract not found", ENTITY_NAME, "relatedcontractnotfound"));
                    ccon.setRelatedContract(relatedContract);
                } else {
                    throw new BadRequestAlertException("Related contract ID is required", ENTITY_NAME, "relatedcontractidrequired");
                }
                contractContractService.save(ccon);
                break;
            default:
                throw new BadRequestAlertException("Unknown relation entity name: " + entityName, ENTITY_NAME, "invalidrelation");
        }

        return getContract(id, true);
    }

    @DeleteMapping("/contracts/{id}/relate")
    public ResponseEntity<Contract> deleteRelation(@PathVariable(value = "id") final Long id, @RequestBody NewRelationDTO deleteRelation)
        throws URISyntaxException {
        log.debug("REST request to delete relation for Contract : {}", id);
        
        final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
        Contract contract = contractRepository.findByIdAndApplicationUser(id, applicationUser)
            .orElseThrow(() -> new BadRequestAlertException("Contract not found", ENTITY_NAME, "contractnotfound"));

        String entityName = deleteRelation.getEntityName();
        Object relationData = deleteRelation.getRelationData();

        try {
            switch (entityName) {
                case "realestate":
                    ContractRealestate cr = objectMapper.convertValue(relationData, ContractRealestate.class);
                    if (cr.getId() != null) {
                        try {
                            contractRealestateService.delete(cr.getId());
                            log.debug("Successfully deleted realestate relation with ID: {}", cr.getId());
                        } catch (Exception e) {
                            log.warn("Failed to delete realestate relation with ID: {} - may not exist: {}", cr.getId(), e.getMessage());
                        }
                    }
                    break;
                case "contact":
                    ContactContract cc = objectMapper.convertValue(relationData, ContactContract.class);
                    if (cc.getId() != null) {
                        try {
                            contactContractService.delete(cc.getId());
                            log.debug("Successfully deleted contact relation with ID: {}", cc.getId());
                        } catch (Exception e) {
                            log.warn("Failed to delete contact relation with ID: {} - may not exist: {}", cc.getId(), e.getMessage());
                        }
                    }
                    break;
                case "financeaccount":
                    ContractFinanceAccount cfa = objectMapper.convertValue(relationData, ContractFinanceAccount.class);
                    if (cfa.getId() != null) {
                        try {
                            contractFinanceAccountService.delete(cfa.getId());
                            log.debug("Successfully deleted financeaccount relation with ID: {}", cfa.getId());
                        } catch (Exception e) {
                            log.warn("Failed to delete financeaccount relation with ID: {} - may not exist: {}", cfa.getId(), e.getMessage());
                        }
                    }
                    break;
                case "mobility":
                    ContractMobility cm = objectMapper.convertValue(relationData, ContractMobility.class);
                    if (cm.getId() != null) {
                        try {
                            contractMobilityService.delete(cm.getId());
                            log.debug("Successfully deleted mobility relation with ID: {}", cm.getId());
                        } catch (Exception e) {
                            log.warn("Failed to delete mobility relation with ID: {} - may not exist: {}", cm.getId(), e.getMessage());
                        }
                    }
                    break;
                case "contract":
                    ContractContract ccon = objectMapper.convertValue(relationData, ContractContract.class);
                    if (ccon.getId() != null) {
                        try {
                            contractContractService.delete(ccon.getId());
                            log.debug("Successfully deleted contract relation with ID: {}", ccon.getId());
                        } catch (Exception e) {
                            log.warn("Failed to delete contract relation with ID: {} - may not exist: {}", ccon.getId(), e.getMessage());
                        }
                    }
                    break;
                default:
                    log.warn("Unknown relation entity name: {}", entityName);
                    // Nicht mehr eine Exception werfen, sondern nur loggen
                    break;
            }
        } catch (Exception e) {
            log.error("Error during relation deletion: {}", e.getMessage());
            // Trotz Fehler fortfahren und die aktualisierten Daten zurückgeben
        }

        return getContract(id, true);
    }

    @GetMapping("/contracts/all")
    public ResponseEntity<List<Contract>> getAllContractsWithRelations() {
        log.debug("REST request to get all Contracts with full relationships for current user");
        final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
        List<Contract> contracts = contractQueryService.findAllWithCategoryRelationsByApplicationUser(applicationUser);
        return ResponseEntity.ok().body(contracts);
    }
    
    @GetMapping("/contracts/check")
    public ResponseEntity<Boolean> checkIfUserIsAllowedToAdd() {
        log.debug("REST request to check if user is allowed to add contracts");
        boolean isAllowed = authorityConfigService.isUserAllowedToAddContract();
        return ResponseEntity.ok().body(isAllowed);
    }

    @GetMapping("/contracts")
    public ResponseEntity<List<Contract>> getAllContracts(ContractCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Contracts by criteria: {}", criteria);
        criteria.setApplicationUserId(userService.getApplicationUserFilter());
        Page<Contract> page = contractQueryService.findByCriteria(criteria, pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    @GetMapping("/contracts/{id}")
    public ResponseEntity<Contract> getContract(@PathVariable Long id, @RequestParam(defaultValue = "true") boolean relations) {
        log.debug("REST request to get Contract : {}", id);
        final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
        Optional<Contract> contractOpt;
        
        if (relations) {
            contractOpt = contractRepository.findByIdAndApplicationUserWithGraph(id, applicationUser);
        } else {
            contractOpt = contractRepository.findByIdAndApplicationUser(id, applicationUser);
        }

        if (contractOpt.isPresent() && relations) {
            Contract contract = contractOpt.orElseThrow();
            try {
                // Lade Verknüpfungen mit vollständigen Entitätsdaten
                Set<ContractRealestate> contractRealestates = contractRealestateService.findAllByContract(contract);
                for (ContractRealestate cr : contractRealestates) {
                    if (cr.getRealestate() != null && cr.getRealestate().getId() != null) {
                        // Lade vollständige RealEstate-Daten
                        cr.setRealestate(realestateRepository.findById(cr.getRealestate().getId()).orElse(cr.getRealestate()));
                    }
                }
                contract.setContractRealestates(contractRealestates);

                Set<ContactContract> contactContracts = contactContractService.findAllByContract(contract);
                for (ContactContract cc : contactContracts) {
                    if (cc.getContact() != null && cc.getContact().getId() != null) {
                        // Lade vollständige Contact-Daten
                        cc.setContact(contactRepository.findById(cc.getContact().getId()).orElse(cc.getContact()));
                    }
                }
                contract.setContactContracts(contactContracts);

                Set<ContractFinanceAccount> contractFinanceAccounts = contractFinanceAccountService.findAllByContract(contract);
                for (ContractFinanceAccount cfa : contractFinanceAccounts) {
                    if (cfa.getFinanceaccount() != null && cfa.getFinanceaccount().getId() != null) {
                        // Lade vollständige FinanceAccount-Daten
                        cfa.setFinanceaccount(financeAccountRepository.findById(cfa.getFinanceaccount().getId()).orElse(cfa.getFinanceaccount()));
                    }
                }
                contract.setContractFinanceAccounts(contractFinanceAccounts);

                Set<ContractMobility> contractMobilities = contractMobilityService.findAllByContract(contract);
                for (ContractMobility cm : contractMobilities) {
                    if (cm.getMobility() != null && cm.getMobility().getId() != null) {
                        // Lade vollständige Mobility-Daten
                        cm.setMobility(mobilityRepository.findById(cm.getMobility().getId()).orElse(cm.getMobility()));
                    }
                }
                contract.setContractMobilities(contractMobilities);

                List<ContractContract> contractContracts = contractContractService.findAllByContract(contract);
                for (ContractContract cc : contractContracts) {
                    if (cc.getRelatedContract() != null && cc.getRelatedContract().getId() != null) {
                        // Lade vollständige Contract-Daten
                        cc.setRelatedContract(contractRepository.findById(cc.getRelatedContract().getId()).orElse(cc.getRelatedContract()));
                    }
                }
                contract.setContractContracts(new HashSet<>(contractContracts));
            } catch (Exception e) {
                log.error("Error while loading relations for Contract {}: {}", id, e.getMessage(), e);
            }
            return ResponseEntity.ok().body(contract);
        }
        return ResponseUtil.wrapOrNotFound(contractOpt);
    }

    @DeleteMapping("/contracts/{id}")
    public ResponseEntity<Void> deleteEmploymentContract(@PathVariable Long id) {
        log.debug("REST request to delete Contract : {}", id);
        Contract contract = contractRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
        final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
        if (!contract.getApplicationUser().getId().equals(applicationUser.getId())) {
            throw new BadRequestAlertException("Not allowed", ENTITY_NAME, "notallowed");
        }
        contractRealestateService.deleteRelationships(contract);
        contactContractService.deleteRelationships(contract);
        contractFinanceAccountService.deleteRelationships(contract);
        contractMobilityService.deleteRelationships(contract);
        contractContractService.deleteRelationships(contract);
        contractService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}