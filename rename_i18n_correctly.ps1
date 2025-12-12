# PowerShell-Skript zum korrekten Umbenennen aller i18n-Dateien
# Schema: i18n-category-subcategory-subcategorygroup-grouptype.json

# Mapping der falschen Namen zu korrekten Namen basierend auf Form-Definitionen
$renameMapping = @{
    "i18n-contract-building-savings.json" = "i18n-contract-savings-realestate-buildingsavings.json"
    "i18n-contract-chimney-sweeper.json" = "i18n-contract-service-service-chimneysweeper.json"
    "i18n-contract-consulting.json" = "i18n-contract-service-consulting-consultingcontract.json"
    "i18n-contract-caretaker-service.json" = "i18n-contract-service-service-caretakerservice.json"
    "i18n-contract-distribution.json" = "i18n-contract-service-distribution-distributioncontract.json"
    "i18n-contract-employment.json" = "i18n-contract-employment-employment-employmentcontract.json"
    "i18n-contract-energy.json" = "i18n-contract-energy-energy-energycontract.json"
    "i18n-contract-fixed-term.json" = "i18n-contract-savings-savings-fixedtermcontract.json"
    "i18n-contract-franchise.json" = "i18n-contract-service-franchise-franchisecontract.json"
    "i18n-contract-gardener.json" = "i18n-contract-service-gardener-gardenercontract.json"
    "i18n-contract-insurance-broker.json" = "i18n-contract-service-broker-insurancebroker.json"
    "i18n-contract-lease.json" = "i18n-contract-lease-lease-leasecontract.json"
    "i18n-contract-loan.json" = "i18n-contract-loan-loan-loancontract.json"
    "i18n-contract-maintenance.json" = "i18n-contract-service-maintenance-maintenancecontract.json"
    "i18n-contract-mobility-car-inspection.json" = "i18n-contract-mobility-car-inspection.json"
    "i18n-contract-overnight.json" = "i18n-contract-savings-savings-overnightcontract.json"
    "i18n-contract-purchase.json" = "i18n-contract-purchase-realestate-purchasecontract.json"
    "i18n-contract-rental.json" = "i18n-contract-rental-rental-rentalcontract.json"
    "i18n-contract-savings.json" = "i18n-contract-savings-savings-savingscontract.json"
    "i18n-contract-service-caretaker-service.json" = "i18n-contract-service-service-caretakerservice.json"
    "i18n-contract-service-chimney-sweeper.json" = "i18n-contract-service-service-chimneysweeper.json"
    "i18n-contract-service.json" = "i18n-contract-service-service-servicecontract.json"
    "i18n-contract-software.json" = "i18n-contract-service-software-softwarecontract.json"
    "i18n-contract-streaming.json" = "i18n-contract-media-streaming-streamingcontract.json"
    "i18n-contract-telecom.json" = "i18n-contract-telecommunication-telecommunication-telecommunicationcontract.json"
    "i18n-employment-contract.json" = "i18n-contract-employment-employment-employmentcontract.json"
    "i18n-insurance-accident.json" = "i18n-contract-insurance-accident-accidentcontract.json"
    "i18n-insurance-animal-surgery.json" = "i18n-contract-insurance-animal-surgery-animalsurgerycontract.json"
    "i18n-insurance-art.json" = "i18n-contract-insurance-art-artcontract.json"
    "i18n-insurance-automobile.json" = "i18n-contract-insurance-automobile-automobilecontract.json"
    "i18n-insurance-bicycle.json" = "i18n-contract-insurance-bicycle-bicyclecontract.json"
    "i18n-insurance-bike.json" = "i18n-contract-insurance-bike-bikecontract.json"
    "i18n-insurance-disability.json" = "i18n-contract-insurance-disability-disabilitycontract.json"
    "i18n-insurance-fire.json" = "i18n-contract-insurance-fire-firecontract.json"
    "i18n-insurance-furniture.json" = "i18n-contract-insurance-furniture-furniturecontract.json"
    "i18n-insurance-legal-protection.json" = "i18n-contract-insurance-legalprotection-legalprotectioncontract.json"
    "i18n-insurance-legal.json" = "i18n-contract-insurance-legal-legalcontract.json"
    "i18n-insurance-loss-rent.json" = "i18n-contract-insurance-loss-rent-lossofrentcontract.json"
    "i18n-insurance-pet.json" = "i18n-contract-insurance-pet-petcontract.json"
    "i18n-insurance-photovoltaic.json" = "i18n-contract-insurance-photovoltaic-photovoltaiccontract.json"
    "i18n-insurance-pri-sick-full.json" = "i18n-contract-insurance-health-prisickfullcontract.json"
    "i18n-insurance-rental.json" = "i18n-contract-insurance-rental-rentalcontract.json"
    "i18n-insurance-residential.json" = "i18n-contract-insurance-residential-residentialcontract.json"
    "i18n-insurance-risk-life.json" = "i18n-contract-insurance-risklife-risklifecontract.json"
    "i18n-insurance-travel.json" = "i18n-contract-insurance-travel-travelcontract.json"
    "i18n-loan-credit-contract.json" = "i18n-contract-loan-credit-creditcontract.json"
    "i18n-sale-contract.json" = "i18n-contract-sale-sale-salecontract.json"
}

# Aktualisiere Contract-Dateien
foreach ($oldName in $renameMapping.Keys) {
    $newName = $renameMapping[$oldName]
    $oldPath = "src/main/webapp/i18n/de/contract/$oldName"
    $newPath = "src/main/webapp/i18n/de/contract/$newName"
    
    if (Test-Path $oldPath) {
        Write-Host "Benenne um: $oldName -> $newName"
        if (Test-Path $newPath) {
            Write-Host "  Ziel existiert bereits, lösche alte Datei"
            Remove-Item $oldPath
        } else {
            Move-Item $oldPath $newPath
        }
    }
}

Write-Host "Alle i18n-Dateien wurden korrekt umbenannt!" 