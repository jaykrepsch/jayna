-- Automatisch generiertes SQL-Skript zum Aktualisieren der form_name-Spalte
-- Jede Zeile: UPDATE group_type SET form_name = <Spalte 10> WHERE form_name = <Spalte 9>;

UPDATE group_type SET form_name = 'mobility-passengervehicle-quad-quad4x4' WHERE form_name = 'quad';
UPDATE group_type SET form_name = 'mobility-commercialvehicle-trailer-a' WHERE form_name = 'trailer';
UPDATE group_type SET form_name = 'mobility-commercialvehicle-box-boxvan' WHERE form_name = 'boxvan';
UPDATE group_type SET form_name = 'contract-loan-realestate-callloan' WHERE form_name = 'contract-loan-credit-creditcontract';
UPDATE group_type SET form_name = 'realestate-withdevelopment-farmland-farmland' WHERE form_name = 'farmland';
UPDATE group_type SET form_name = 'contract-loan-realestate-annuityloan' WHERE form_name = 'contract-loan-realestate-annuityloan';
UPDATE group_type SET form_name = 'financeaccount-systems-paymentsystems-applepay' WHERE form_name = 'contract-creditcard-credit-creditcard';
UPDATE group_type SET form_name = 'contract-employmentcontract-employee-terminationagreement' WHERE form_name = 'contract-employment-employee-terminationAgreement';
UPDATE group_type SET form_name = 'contract-employmentcontract-official-terminationagreement' WHERE form_name = 'contract-employment-employee-terminationAgreement';
UPDATE group_type SET form_name = 'contract-employmentcontract-publicservice-terminationagreement' WHERE form_name = 'contract-employment-employee-terminationAgreement';
-- ... weitere UPDATEs für alle Zeilen ... 

-- Update FormNames in der Datenbank basierend auf der CSV-Datei
-- Alte Namen (Spalte 9) -> Neue Namen (Spalte 10)

-- Arbeitsvertrag Updates
UPDATE form_definition SET form_name = 'contract-employment-employee-terminationAgreement' WHERE form_name = 'contract-employment-employee-terminationAgreement';
UPDATE form_definition SET form_name = 'contract-employment-employee-help' WHERE form_name = 'employment-contract';
UPDATE form_definition SET form_name = 'contract-employment-official-help' WHERE form_name = 'employment-contract';
UPDATE form_definition SET form_name = 'contract-employment-publicservice-help' WHERE form_name = 'employment-contract';
UPDATE form_definition SET form_name = 'contract-employment-employee-minijob' WHERE form_name = 'employment-contract';
UPDATE form_definition SET form_name = 'contract-employment-official-minijob' WHERE form_name = 'employment-contract';
UPDATE form_definition SET form_name = 'contract-employment-publicservice-minijob' WHERE form_name = 'employment-contract';
UPDATE form_definition SET form_name = 'contract-employment-employee-internship' WHERE form_name = 'employment-contract';
UPDATE form_definition SET form_name = 'contract-employment-official-internship' WHERE form_name = 'employment-contract';
UPDATE form_definition SET form_name = 'contract-employment-publicservice-internship' WHERE form_name = 'employment-contract';
UPDATE form_definition SET form_name = 'contract-employment-employee-fulltime' WHERE form_name = 'employment-contract';
UPDATE form_definition SET form_name = 'contract-employment-official-fulltime' WHERE form_name = 'employment-contract';
UPDATE form_definition SET form_name = 'contract-employment-publicservice-fulltime' WHERE form_name = 'employment-contract';
UPDATE form_definition SET form_name = 'contract-employment-employee-halftime' WHERE form_name = 'employment-contract';
UPDATE form_definition SET form_name = 'contract-employment-official-halftime' WHERE form_name = 'employment-contract';
UPDATE form_definition SET form_name = 'contract-employment-publicservice-halftime' WHERE form_name = 'employment-contract';
UPDATE form_definition SET form_name = 'contract-employment-employee-workcontract' WHERE form_name = 'employment-contract';

-- Weitere Updates basierend auf der CSV
UPDATE form_definition SET form_name = 'contract-service-fees-municipalfees' WHERE form_name = 'municipal-fees';
UPDATE form_definition SET form_name = 'contract-service-maintenance-elevator' WHERE form_name = 'contract-service-maintenance-maintenancecontract';
UPDATE form_definition SET form_name = 'contract-service-maintenance-heating' WHERE form_name = 'contract-service-maintenance-maintenancecontract';
UPDATE form_definition SET form_name = 'contract-service-maintenance-chimneysweeper' WHERE form_name = 'chimney-sweeper';
UPDATE form_definition SET form_name = 'contract-service-maintenance-heatpump' WHERE form_name = 'contract-service-maintenance-maintenancecontract';

-- Kontakte Updates
UPDATE form_definition SET form_name = 'contact-contact-contact-singlecontact' WHERE form_name = 'single-contact';
UPDATE form_definition SET form_name = 'contact-contact-contact-contactgroup' WHERE form_name = 'contact-group';

-- Immobilien Updates
UPDATE form_definition SET form_name = 'realestate-withdevelopment-parkingspace-carport' WHERE form_name = 'carport';
UPDATE form_definition SET form_name = 'realestate-withdevelopment-parkingspace-garage' WHERE form_name = 'garage';
UPDATE form_definition SET form_name = 'realestate-withdevelopment-parkingspace-parkinghouse' WHERE form_name = 'parking-house';
UPDATE form_definition SET form_name = 'realestate-withdevelopment-parkingspace-parkingspace' WHERE form_name = 'parking-space';
UPDATE form_definition SET form_name = 'realestate-withdevelopment-garden-allotmentgarden' WHERE form_name = 'allotment-garden';

-- Mobilität Updates
UPDATE form_definition SET form_name = 'mobility-passengervehicle-motorcycle-dirtbike' WHERE form_name = 'dirt-bike';

-- Finanzkonten Updates
UPDATE form_definition SET form_name = 'financeaccount-bank-bankaccount-checkingaccount' WHERE form_name = 'checking-account';
UPDATE form_definition SET form_name = 'financeaccount-bank-bankaccount-savingaccount' WHERE form_name = 'contract-savings-savings-savingscontract';

-- Weitere spezifische Updates
UPDATE form_definition SET form_name = 'contract-service-service-propertymanagement' WHERE form_name = 'property-management';

-- Zeige die Ergebnisse
SELECT id, form_name FROM form_definition ORDER BY form_name; 