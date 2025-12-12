# PowerShell-Skript zum Aktualisieren aller form_name-Werte in der group_type-Tabelle
# Führt alle SQL-Update-Statements aus, um die form_name-Werte auf die korrekten Dateinamen zu setzen

Write-Host "=== Aktualisiere form_name-Werte für alle Entitäten ===" -ForegroundColor Green
Write-Host ""

# Bankkonto (FinanceAccount) Updates
Write-Host "--- Bankkonto Updates ---" -ForegroundColor Yellow
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'financeaccount-bank-savingaccount-savingaccount' WHERE name = 'Sparkonto';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'financeaccount-bank-checkingaccount-checkingaccount' WHERE name = 'Girokonto';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'financeaccount-bank-savingaccount-savingaccount' WHERE name = 'Festgeldkonto';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'financeaccount-bank-savingaccount-savingaccount' WHERE name = 'Tagesgeldkonto';"

# Energie Updates
Write-Host "--- Energie Updates ---" -ForegroundColor Yellow
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-energy-electric-stromcontract' WHERE name = 'Strom';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-energy-energy-energycontract' WHERE name = 'Fernwärme';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-energy-energy-energycontract' WHERE name = 'Gas';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-energy-energy-energycontract' WHERE name = 'Öl';"

# Arbeitsvertrag Updates
Write-Host "--- Arbeitsvertrag Updates ---" -ForegroundColor Yellow
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-employment-employee-mini-job' WHERE name = 'Aushilfe';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-employment-employee-fulltime' WHERE name = 'Vollzeit';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-employment-employee-part-time' WHERE name = 'Teilzeit';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-employment-employee-mini-job' WHERE name = 'Mini';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-employment-employee-internship' WHERE name = 'Praktikumsvertrag';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-employment-employment-employmentcontract' WHERE name = 'Werkvertrag';"

# Immobilien Updates
Write-Host "--- Immobilien Updates ---" -ForegroundColor Yellow
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-loan-realestate-annuityloan' WHERE name = 'Annuitätendarlehen';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-rental-rental-rentalcontract' WHERE name = 'Mietvertrag';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-purchase-realestate-purchasecontract' WHERE name = 'Kaufvertrag';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-lease-realestate-leasecontract' WHERE name = 'Pachtvertrag';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-propertymanagement-propertymanagement-propertymanagementcontract' WHERE name = 'Hausverwaltung';"

# Mobilität Updates
Write-Host "--- Mobilität Updates ---" -ForegroundColor Yellow
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'mobility-passenger-car-car' WHERE name = 'Auto';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'mobility-single-single-singlemobility' WHERE name = 'Einzelmobilität';"

# Versicherung Updates
Write-Host "--- Versicherung Updates ---" -ForegroundColor Yellow
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-insurance-automobile-automobilecontract' WHERE name = 'KFZ-Versicherung';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-insurance-health-healthcontract' WHERE name = 'Krankenversicherung';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-insurance-liability-liabilitycontract' WHERE name = 'Haftpflichtversicherung';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-insurance-fire-firecontract' WHERE name = 'Feuerversicherung';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-insurance-residential-residentialcontract' WHERE name = 'Wohngebäudeversicherung';"

# Kreditkarte Updates
Write-Host "--- Kreditkarte Updates ---" -ForegroundColor Yellow
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-creditcard-credit-creditcardcontract' WHERE name = 'Kreditkarte';"

# Kredit Updates
Write-Host "--- Kredit Updates ---" -ForegroundColor Yellow
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-loan-loan-loancontract' WHERE name = 'Kredit';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-loan-credit-creditcontract' WHERE name = 'Kreditkarte';"

# Service Updates
Write-Host "--- Service Updates ---" -ForegroundColor Yellow
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-service-maintenance-maintenancecontract' WHERE name = 'Wartung';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-service-cleaning-cleaningcontract' WHERE name = 'Reinigung';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-service-gardener-gardenercontract' WHERE name = 'Gärtner';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-service-caretaker-service' WHERE name = 'Hausmeister';"

# Telekommunikation Updates
Write-Host "--- Telekommunikation Updates ---" -ForegroundColor Yellow
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-telecommunication-telecommunication-telecommunicationcontract' WHERE name = 'Telefon';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-media-streaming-streamingcontract' WHERE name = 'Streaming';"
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-media-media-mediacontract' WHERE name = 'Medien';"

# Gebühren Updates
Write-Host "--- Gebühren Updates ---" -ForegroundColor Yellow
docker exec docker-db-1 psql -U jayna -d jayna -c "UPDATE group_type SET form_name = 'contract-fees-municipal-municipalcontract' WHERE name = 'Gemeindegebühren';"

Write-Host ""
Write-Host "=== Alle Updates abgeschlossen ===" -ForegroundColor Green
Write-Host "Die form_name-Werte wurden auf die korrekten Dateinamen aktualisiert." -ForegroundColor Cyan
Write-Host "Das Frontend sollte jetzt alle Formulare korrekt laden können." -ForegroundColor Cyan 