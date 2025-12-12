package me.jayna.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.jayna.domain.ApplicationUser;
import me.jayna.repository.ContractRepository;
import me.jayna.repository.FinanceAccountRepository;
import me.jayna.repository.RealEstateRepository;
import me.jayna.repository.MobilityRepository;

@Service
@Transactional
public class AuthorityConfigService {

  private final UserService userService;

  private final RealEstateRepository realEstateRepository;

  private final ContractRepository contractRepository;

  private final FinanceAccountRepository financeAccountRepository;

  private final MobilityRepository mobilityRepository;

  public AuthorityConfigService(UserService userService, RealEstateRepository realEstateRepository,
      ContractRepository contractRepository, FinanceAccountRepository financeAccountRepository,
      MobilityRepository mobilityRepository) {
    this.userService = userService;
    this.realEstateRepository = realEstateRepository;
    this.contractRepository = contractRepository;
    this.financeAccountRepository = financeAccountRepository;
    this.mobilityRepository = mobilityRepository;
  }

  public boolean isUserAllowedToAddRealEstate() {
    try {
      ApplicationUser currentApplicationUser = userService.getApplicationUser().orElse(null);
      if (currentApplicationUser == null) {
        return false;
      }
      
      // Prüfe die Anzahl der bereits erstellten Immobilien des Benutzers
      long userRealEstateCount = realEstateRepository.countByApplicationUser(currentApplicationUser);
      
      // Für jetzt: Erlaube bis zu 10 Immobilien pro Benutzer
      return userRealEstateCount < 10;
    } catch (Exception e) {
      // Bei Fehlern erlaube das Hinzufügen
      return true;
    }
  }

  public boolean isUserAllowedToAddContract() {
    try {
      ApplicationUser currentApplicationUser = userService.getApplicationUser().orElse(null);
      if (currentApplicationUser == null) {
        return false;
      }
      
      // Prüfe die Anzahl der bereits erstellten Verträge des Benutzers
      long userContractCount = contractRepository.countByApplicationUser(currentApplicationUser);
      
      // Für jetzt: Erlaube bis zu 20 Verträge pro Benutzer
      return userContractCount < 20;
    } catch (Exception e) {
      // Bei Fehlern erlaube das Hinzufügen
      return true;
    }
  }

  public boolean isUserAllowedToAddFinanceAccount() {
    try {
      ApplicationUser currentApplicationUser = userService.getApplicationUser().orElse(null);
      if (currentApplicationUser == null) {
        return false;
      }
      
      // Prüfe die Anzahl der bereits erstellten Konten des Benutzers
      long userFinanceAccountCount = financeAccountRepository.countByApplicationUser(currentApplicationUser);
      
      // Für jetzt: Erlaube bis zu 15 Konten pro Benutzer
      return userFinanceAccountCount < 15;
    } catch (Exception e) {
      // Bei Fehlern erlaube das Hinzufügen
      return true;
    }
  }

  public boolean isUserAllowedToAddMobility() {
    try {
      ApplicationUser currentApplicationUser = userService.getApplicationUser().orElse(null);
      if (currentApplicationUser == null) {
        return false;
      }
      
      // Prüfe die Anzahl der bereits erstellten Fahrzeuge des Benutzers
      long userMobilityCount = mobilityRepository.countByApplicationUser(currentApplicationUser);
      
      // Für jetzt: Erlaube bis zu 8 Fahrzeuge pro Benutzer
      return userMobilityCount < 8;
    } catch (Exception e) {
      // Bei Fehlern erlaube das Hinzufügen
      return true;
    }
  }
}
