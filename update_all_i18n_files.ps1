# PowerShell-Skript zum Aktualisieren aller i18n-Dateien
# Struktur: createTitle, updateTitle, viewTitle oben, dann spezifische Abschnitte, relations-title unten

$contractFiles = @(
    "contract-insurance-bicycle-bicyclecontract",
    "contract-insurance-bike-bikecontract",
    "contract-insurance-broker-brokercontract",
    "contract-insurance-builderliability-builderliabilitycontract",
    "contract-insurance-classicpension-classicpensioncontract",
    "contract-insurance-dailyhospital-dailyhospitalcontract",
    "contract-insurance-dailysickness-dailysicknesscontract",
    "contract-insurance-disability-disabilitycontract",
    "contract-insurance-domanager-domanagercontract",
    "contract-insurance-environmentaldamage-environmentaldamagecontract",
    "contract-insurance-expatriates-expatriatescontract",
    "contract-insurance-fire-firecontract",
    "contract-insurance-furniture-furniturecontract",
    "contract-insurance-health-healthcontract",
    "contract-insurance-health-prisickfullcontract",
    "contract-insurance-inthealth-inthealthcontract",
    "contract-insurance-legal-legalcontract",
    "contract-insurance-legalprotection-legalprotectioncontract",
    "contract-insurance-liability-liabilitycontract",
    "contract-insurance-liabilitypet-liabilitypetcontract",
    "contract-insurance-longtermcare-longtermcarecontract",
    "contract-insurance-longtermcaredaily-longtermcaredailycontract",
    "contract-insurance-pensionfund-pensionfundcontract",
    "contract-insurance-pet-petcontract",
    "contract-insurance-photovoltaic-photovoltaiccontract",
    "contract-insurance-priaddout-priaddoutcontract",
    "contract-insurance-priaddstat-priaddstatcontract",
    "contract-insurance-priaddtooth-priaddtoothcontract",
    "contract-insurance-rental-rentalcontract",
    "contract-insurance-residential-residentialcontract",
    "contract-insurance-risklife-risklifecontract",
    "contract-insurance-travel-travelcontract",
    "contract-employment-employee-internship",
    "contract-employment-employee-mini-job",
    "contract-employment-employee-part-time",
    "contract-employment-employee-terminationcontract",
    "contract-employment-employee-work-contract",
    "contract-employment-employment-employmentcontract",
    "contract-employment-official-fulltime",
    "contract-employment-official-internship",
    "contract-employment-official-mini-job",
    "contract-employment-official-part-time",
    "contract-employment-official-work-contract",
    "contract-employment-public-service-fulltime",
    "contract-employment-public-service-mini-job",
    "contract-employment-public-service-part-time",
    "contract-employment-public-service-work-contract",
    "contract-employment-publicservice-internship",
    "contract-energy-energy-energycontract",
    "contract-energy-energy-energycontract2",
    "contract-fees-municipal-municipalcontract",
    "contract-lease-lease-leasecontract",
    "contract-lease-realestate-leasecontract",
    "contract-loan-annuityloan-annuityloan",
    "contract-loan-credit-creditcontract",
    "contract-loan-loan-loanagreementcontract",
    "contract-loan-loan-loancontract",
    "contract-loan-realestate-annuityloan",
    "contract-media-media-mediacontract",
    "contract-media-streaming-streamingcontract",
    "contract-mobility-car-inspection",
    "contract-propertymanagement-propertymanagement-propertymanagementcontract",
    "contract-purchase-realestate-purchasecontract",
    "contract-rental-rental-rentalcontract",
    "contract-rentalmanagement-rentalmanagement-rentalmanagementcontract",
    "contract-sale-sale-salecontract",
    "contract-savings-realestate-buildingsavings",
    "contract-savings-savings-depositcontract",
    "contract-savings-savings-fixedtermcontract",
    "contract-savings-savings-overnightcontract",
    "contract-savings-savings-savingscontract",
    "contract-service-broker-insurancebroker",
    "contract-service-broker-realestateagent",
    "contract-service-caretaker-service",
    "contract-service-chimney-sweeper",
    "contract-service-cleaning-cleaningcontract",
    "contract-service-consulting-consultingcontract",
    "contract-service-distribution-distributioncontract",
    "contract-service-franchise-franchisecontract",
    "contract-service-gardener-gardenercontract",
    "contract-service-gardener-gardenercontract2",
    "contract-service-maintenance-maintenancecontract",
    "contract-service-notary-notarycontract",
    "contract-service-security-securitycontract",
    "contract-service-service-caretakerservice",
    "contract-service-service-chimneysweeper",
    "contract-service-service-servicecontract",
    "contract-service-software-softwarecontract",
    "contract-tax-realestate-realestatetaxcontract",
    "contract-telecommunication-telecommunication-telecommunicationcontract",
    "contract-telecommunication-telecommunication-telecommunicationcontract2"
)

foreach ($file in $contractFiles) {
    $i18nFile = "src/main/webapp/i18n/de/contract/i18n-$file.json"
    
    if (Test-Path $i18nFile) {
        Write-Host "Aktualisiere: $i18nFile"
        
        # Lese die bestehende Datei
        $content = Get-Content $i18nFile -Raw | ConvertFrom-Json
        
        # Erstelle neue Struktur
        $newContent = @{}
        
        # Füge createTitle, updateTitle, viewTitle hinzu
        $newContent["jaynaApp.$file.createTitle"] = "$file anlegen"
        $newContent["jaynaApp.$file.updateTitle"] = "$file bearbeiten"
        $newContent["jaynaApp.$file.viewTitle"] = "$file anzeigen"
        
        # Füge relations-title am Ende hinzu
        $newContent["jaynaApp.$file.relations-title"] = "Verknüpfungen"
        
        # Konvertiere zurück zu JSON und speichere
        $newContent | ConvertTo-Json -Depth 10 | Set-Content $i18nFile
    } else {
        Write-Host "Datei nicht gefunden: $i18nFile"
    }
}

Write-Host "Alle i18n-Dateien wurden aktualisiert!" 