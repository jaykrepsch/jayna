-- SQL Script to update form_name in group_type table based on group_types_camelcase.csv
-- This script maps the correct form names from the CSV file

-- Update financeaccount category form names
UPDATE group_type 
SET form_name = 'financeaccount-bank-bankAccount-checkingAccount' 
WHERE name = 'checkingAccount' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-bank-bankAccount-savingAccount' 
WHERE name = 'savingAccount' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-credit-creditCard-charge' 
WHERE name = 'charge' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-credit-creditCard-debit' 
WHERE name = 'debit' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-credit-creditCard-onlineVirtual' 
WHERE name = 'onlineVirtual' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-credit-creditCard-prepaid' 
WHERE name = 'prepaid' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-credit-creditCard-revolving' 
WHERE name = 'revolving' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-credit-creditCard-samsung' 
WHERE name = 'samsung' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-systems-paymentSystems-applePay' 
WHERE name = 'applePay' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-systems-paymentSystems-googlePay' 
WHERE name = 'googlePay' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-systems-paymentSystems-klarna' 
WHERE name = 'klarna' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-systems-paymentSystems-paypal' 
WHERE name = 'paypal' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-systems-paymentSystems-skrill' 
WHERE name = 'skrill' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-systems-paymentSystems-stripe' 
WHERE name = 'stripe' AND entity_name = 'financeaccount';

-- Update realestate category form names
UPDATE group_type 
SET form_name = 'realestate-withDevelopment-farmland-farmland' 
WHERE name = 'farmland' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-farmland-forest' 
WHERE name = 'forest' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-farmland-fruit' 
WHERE name = 'fruit' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-farmland-pasture' 
WHERE name = 'pasture' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-garden-allotmentGarden' 
WHERE name = 'allotmentGarden' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-commercialBuilding-commercialUnit' 
WHERE name = 'commercialUnit' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-commercialBuilding-commercialBuilding' 
WHERE name = 'commercialBuilding' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-hall-hall' 
WHERE name = 'hall' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-parkingSpace-carport' 
WHERE name = 'carport' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-parkingSpace-garage' 
WHERE name = 'garage' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-parkingSpace-parkingHouse' 
WHERE name = 'parkingHouse' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-parkingSpace-parkingSpace' 
WHERE name = 'parkingSpace' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-commercialResidentialBuilding-apartmentsCommercialMix' 
WHERE name = 'apartmentsCommercialMix' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-residentialBuilding-semitDetachedHouse' 
WHERE name = 'semitDetachedHouse' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-residentialBuilding-detachedHouse' 
WHERE name = 'detachedHouse' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-residentialBuilding-vacationHouse' 
WHERE name = 'vacationHouse' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-residentialBuilding-apartmentBuilding' 
WHERE name = 'apartmentBuilding' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-residentialBuilding-careFacility' 
WHERE name = 'careFacility' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-residentialBuilding-terracedEndHouse' 
WHERE name = 'terracedEndHouse' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-residentialBuilding-terracedMidHouse' 
WHERE name = 'terracedMidHouse' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-residentialBuilding-twoFamilyHouse' 
WHERE name = 'twoFamilyHouse' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withoutDevelopment-ground-buildingPlot' 
WHERE name = 'buildingPlot' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withoutDevelopment-ground-meadow' 
WHERE name = 'meadow' AND entity_name = 'realestate';

-- Update contact category form names
UPDATE group_type 
SET form_name = 'contact-contact-contact-singleContact' 
WHERE name = 'singleContact' AND entity_name = 'contact';

UPDATE group_type 
SET form_name = 'contact-contact-contact-contactGroup' 
WHERE name = 'contactGroup' AND entity_name = 'contact';

-- Update mobility category form names (partial - continuing with more updates)
UPDATE group_type 
SET form_name = 'mobility-boat-hybrid-motorGlider' 
WHERE name = 'motorGlider' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-boat-motorized-motorboat' 
WHERE name = 'motorboat' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-boat-sailing-sailingBoat' 
WHERE name = 'sailingBoat' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-boat-sailing-sailingYacht' 
WHERE name = 'sailingYacht' AND entity_name = 'mobility';

-- Continue with more mobility updates...
UPDATE group_type 
SET form_name = 'mobility-bike-bike-city' 
WHERE name = 'city' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-cross' 
WHERE name = 'cross' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-tricycle' 
WHERE name = 'tricycle' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-foldingBike' 
WHERE name = 'foldingBike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-gravel' 
WHERE name = 'gravel' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-cargo' 
WHERE name = 'cargo' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-lifestyle' 
WHERE name = 'lifestyle' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-mtb' 
WHERE name = 'mtb' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-racingBike' 
WHERE name = 'racingBike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-speedBike' 
WHERE name = 'speedBike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-suv' 
WHERE name = 'suv' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-trekkingBike' 
WHERE name = 'trekkingBike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-xxl' 
WHERE name = 'xxl' AND entity_name = 'mobility';

-- eBike updates
UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eCity' 
WHERE name = 'eCity' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eCross' 
WHERE name = 'eCross' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eTricycle' 
WHERE name = 'eTricycle' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eFoldingBike' 
WHERE name = 'eFoldingBike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eGravel' 
WHERE name = 'eGravel' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eCargo' 
WHERE name = 'eCargo' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eLifestyle' 
WHERE name = 'eLifestyle' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eMtb' 
WHERE name = 'eMtb' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eRacingBike' 
WHERE name = 'eRacingBike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eSpeedBike' 
WHERE name = 'eSpeedBike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eSuv' 
WHERE name = 'eSuv' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eTrekkingBike' 
WHERE name = 'eTrekkingBike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eXxl' 
WHERE name = 'eXxl' AND entity_name = 'mobility';

-- pedelec updates
UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pCity' 
WHERE name = 'pCity' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pCross' 
WHERE name = 'pCross' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pTricycle' 
WHERE name = 'pTricycle' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pFoldingBike' 
WHERE name = 'pFoldingBike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pGravel' 
WHERE name = 'pGravel' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pCargo' 
WHERE name = 'pCargo' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pLifestyle' 
WHERE name = 'pLifestyle' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pMtb' 
WHERE name = 'pMtb' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pRacingBike' 
WHERE name = 'pRacingBike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pSpeedBike' 
WHERE name = 'pSpeedBike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pSuv' 
WHERE name = 'pSuv' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pTrekkingBike' 
WHERE name = 'pTrekkingBike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pXxl' 
WHERE name = 'pXxl' AND entity_name = 'mobility';

-- agriculture updates
UPDATE group_type 
SET form_name = 'mobility-agriculture-tractor-tractor' 
WHERE name = 'tractor' AND entity_name = 'mobility';

-- commercial vehicle updates
UPDATE group_type 
SET form_name = 'mobility-commercialVehicle-trailer-a' 
WHERE name = 'a' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-commercialVehicle-trailer-b' 
WHERE name = 'b' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-commercialVehicle-trailer-c' 
WHERE name = 'c' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-commercialVehicle-box-boxvan' 
WHERE name = 'boxvan' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-commercialVehicle-caravan-family' 
WHERE name = 'family' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-commercialVehicle-caravan-luxury' 
WHERE name = 'luxury' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-commercialVehicle-caravan-sport' 
WHERE name = 'sport' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-commercialVehicle-caravan-tent' 
WHERE name = 'tent' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-commercialVehicle-caravan-twoPersons' 
WHERE name = 'twoPersons' AND entity_name = 'mobility';

-- passenger vehicle updates
UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-convertible' 
WHERE name = 'convertible' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-coupe' 
WHERE name = 'coupe' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-cuv' 
WHERE name = 'cuv' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-offRoadVehicle' 
WHERE name = 'offRoadVehicle' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-minibus' 
WHERE name = 'minibus' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-smallTransporter' 
WHERE name = 'smallTransporter' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-smallCar' 
WHERE name = 'smallCar' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-stationWagon' 
WHERE name = 'stationWagon' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-limousine' 
WHERE name = 'limousine' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-minivan' 
WHERE name = 'minivan' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-pickup' 
WHERE name = 'pickup' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-roadstar' 
WHERE name = 'roadstar' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-suv' 
WHERE name = 'suv' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-transporter' 
WHERE name = 'transporter' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-van' 
WHERE name = 'van' AND entity_name = 'mobility';

-- motorcycle updates
UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-motorcycle-chopper' 
WHERE name = 'chopper' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-motorcycle-cruiser' 
WHERE name = 'cruiser' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-motorcycle-dirtBike' 
WHERE name = 'dirtBike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-motorcycle-motocross' 
WHERE name = 'motocross' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-motorcycle-naked' 
WHERE name = 'naked' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-motorcycle-srambler' 
WHERE name = 'srambler' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-motorcycle-streetfighter' 
WHERE name = 'streetfighter' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-motorcycle-stuntbike' 
WHERE name = 'stuntbike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-motorcycle-trial' 
WHERE name = 'trial' AND entity_name = 'mobility';

-- quad updates
UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-quad-quad4x4' 
WHERE name = 'quad4x4' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-quad-quadC' 
WHERE name = 'quadC' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-quad-quadElectro' 
WHERE name = 'quadElectro' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-quad-quadChildren' 
WHERE name = 'quadChildren' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-quad-quadOffroad' 
WHERE name = 'quadOffroad' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-quad-quadPocket' 
WHERE name = 'quadPocket' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-quad-quadRace' 
WHERE name = 'quadRace' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-quad-quadRoad' 
WHERE name = 'quadRoad' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-quad-quadSideBySide' 
WHERE name = 'quadSideBySide' AND entity_name = 'mobility';

-- camper updates
UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-camper-alkoven' 
WHERE name = 'alkoven' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-camper-semiIntegrated' 
WHERE name = 'semiIntegrated' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-camper-fullyIntegrated' 
WHERE name = 'fullyIntegrated' AND entity_name = 'mobility';

-- contract category updates (partial - continuing with more updates)
UPDATE group_type 
SET form_name = 'contract-employmentContract-employee-terminationAgreement' 
WHERE name = 'terminationAgreement' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-employmentContract-employee-help' 
WHERE name = 'help' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-employmentContract-employee-miniJob' 
WHERE name = 'miniJob' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-employmentContract-employee-internship' 
WHERE name = 'internship' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-employmentContract-employee-halfTime' 
WHERE name = 'halfTime' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-employmentContract-employee-fullTime' 
WHERE name = 'fullTime' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-employmentContract-employee-workContract' 
WHERE name = 'workContract' AND entity_name = 'contract';

-- Continue with more contract updates...
UPDATE group_type 
SET form_name = 'contract-bankAccount-fixedTermAccount-festgeldkonto' 
WHERE name = 'festgeldkonto' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-bankAccount-checkingAccount-girokonto' 
WHERE name = 'girokonto' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-bankAccount-savingsAccount-sparkonto' 
WHERE name = 'sparkonto' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-bankAccount-overnightAccount-tagesgeldkonto' 
WHERE name = 'tagesgeldkonto' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-loan-support-studentLoans' 
WHERE name = 'studentLoans' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-loan-realestate-callLoan' 
WHERE name = 'callLoan' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-loan-realestate-annuityLoan' 
WHERE name = 'annuityLoan' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-loan-realestate-bulletLoans' 
WHERE name = 'bulletLoans' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-loan-realestate-termLoan' 
WHERE name = 'termLoan' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-loan-realestate-modernization' 
WHERE name = 'modernization' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-loan-realestate-participatoryLoans' 
WHERE name = 'participatoryLoans' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-loan-realestate-rollingMarketLoans' 
WHERE name = 'rollingMarketLoans' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-loan-realestate-amortizationLoan' 
WHERE name = 'amortizationLoan' AND entity_name = 'contract';

-- Service updates
UPDATE group_type 
SET form_name = 'contract-service-fees-municipalFees' 
WHERE name = 'municipalFees' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-fees-realEstateTax' 
WHERE name = 'realEstateTax' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-service-gardener' 
WHERE name = 'gardener' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-service-caretakerService' 
WHERE name = 'caretakerService' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-service-propertyManagement' 
WHERE name = 'propertyManagement' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-service-realEstateAgent' 
WHERE name = 'realEstateAgent' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-service-carInspection' 
WHERE name = 'carInspection' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-service-rentalManagement' 
WHERE name = 'rentalManagement' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-service-notary' 
WHERE name = 'notary' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-service-cleaning' 
WHERE name = 'cleaning' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-service-insuranceBroker' 
WHERE name = 'insuranceBroker' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-maintenance-elevator' 
WHERE name = 'elevator' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-maintenance-heating' 
WHERE name = 'heating' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-maintenance-chimneySweeper' 
WHERE name = 'chimneySweeper' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-maintenance-heatPump' 
WHERE name = 'heatPump' AND entity_name = 'contract';

-- Energy updates
UPDATE group_type 
SET form_name = 'contract-energy-electric-electricity' 
WHERE name = 'electricity' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-energy-heating-districtHeating' 
WHERE name = 'districtHeating' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-energy-heating-gas' 
WHERE name = 'gas' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-energy-heating-oil' 
WHERE name = 'oil' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-energy-photovoltaic-electricity' 
WHERE name = 'electricity' AND entity_name = 'contract';

-- Property management updates
UPDATE group_type 
SET form_name = 'contract-propertyManagement-propertyManagement-hausverwaltung' 
WHERE name = 'hausverwaltung' AND entity_name = 'contract';

-- Purchase contract updates
UPDATE group_type 
SET form_name = 'contract-purchaseContract-objects-coffeeTable' 
WHERE name = 'coffeeTable' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-purchaseContract-objects-table' 
WHERE name = 'table' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-purchaseContract-devices-tv' 
WHERE name = 'tv' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-purchaseContract-devices-mobile' 
WHERE name = 'mobile' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-purchaseContract-realestate-realEstateTax' 
WHERE name = 'realEstateTax' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-purchaseContract-realestate-house' 
WHERE name = 'house' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-purchaseContract-realestate-apartment' 
WHERE name = 'apartment' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-purchaseContract-car-car' 
WHERE name = 'car' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-purchaseContract-car-motorcycle' 
WHERE name = 'motorcycle' AND entity_name = 'contract';

-- Credit updates
UPDATE group_type 
SET form_name = 'contract-credit-car-car' 
WHERE name = 'car' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-credit-car-boat' 
WHERE name = 'boat' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-credit-car-caravan' 
WHERE name = 'caravan' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-credit-credit-freeUse' 
WHERE name = 'freeUse' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-credit-credit-modernization' 
WHERE name = 'modernization' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-credit-credit-rescheduling' 
WHERE name = 'rescheduling' AND entity_name = 'contract';

-- Credit card updates
UPDATE group_type 
SET form_name = 'contract-creditCard-debitCard-debitkarte' 
WHERE name = 'debitkarte' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-creditCard-creditCard-kreditkarte' 
WHERE name = 'kreditkarte' AND entity_name = 'contract';

-- Leasing updates
UPDATE group_type 
SET form_name = 'contract-leasing-car-car' 
WHERE name = 'car' AND entity_name = 'contract';

-- Media updates
UPDATE group_type 
SET form_name = 'contract-media-television-cableTv' 
WHERE name = 'cableTv' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-media-television-satelliteTv' 
WHERE name = 'satelliteTv' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-media-internet-broadband' 
WHERE name = 'broadband' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-media-internet-glassFiber' 
WHERE name = 'glassFiber' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-media-cellular-carPhone' 
WHERE name = 'carPhone' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-media-cellular-mobile' 
WHERE name = 'mobile' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-media-cellular-tablet' 
WHERE name = 'tablet' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-media-streaming-moviesSeries' 
WHERE name = 'moviesSeries' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-media-streaming-music' 
WHERE name = 'music' AND entity_name = 'contract';

-- Rental contract updates
UPDATE group_type 
SET form_name = 'contract-rentalContract-putDownSpace-garage' 
WHERE name = 'garage' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-rentalContract-putDownSpace-parking' 
WHERE name = 'parking' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-rentalContract-realestate-holidayHouse' 
WHERE name = 'holidayHouse' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-rentalContract-realestate-holidayApartment' 
WHERE name = 'holidayApartment' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-rentalContract-realestate-building' 
WHERE name = 'building' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-rentalContract-realestate-room' 
WHERE name = 'room' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-rentalContract-car-car' 
WHERE name = 'car' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-rentalContract-car-camper' 
WHERE name = 'camper' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-rentalContract-reside-houseLease' 
WHERE name = 'houseLease' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-rentalContract-reside-apartmentRentalAgreement' 
WHERE name = 'apartmentRentalAgreement' AND entity_name = 'contract';

-- Rental management updates
UPDATE group_type 
SET form_name = 'contract-rentalManagement-rentalManagement-mietverwaltung' 
WHERE name = 'mietverwaltung' AND entity_name = 'contract';

-- Insurance updates (partial - continuing with more updates)
UPDATE group_type 
SET form_name = 'contract-insurance-business-managerLiability' 
WHERE name = 'managerLiability' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-business-building' 
WHERE name = 'building' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-business-legalProtection' 
WHERE name = 'legalProtection' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-car-motorcycle' 
WHERE name = 'motorcycle' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-car-tractor' 
WHERE name = 'tractor' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-car-camper' 
WHERE name = 'camper' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-car-caravan' 
WHERE name = 'caravan' AND entity_name = 'contract';

-- Health insurance updates
UPDATE group_type 
SET form_name = 'contract-insurance-health-abroad' 
WHERE name = 'abroad' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-health-foreignTravel' 
WHERE name = 'foreignTravel' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-health-statutoryhealthinsurance'
WHERE name = 'statutoryHealthInsurance' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-health-hospitalPerDiem' 
WHERE name = 'hospitalPerDiem' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-health-sickPay' 
WHERE name = 'sickPay' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-health-additionalInsuranceFull' 
WHERE name = 'additionalInsuranceFull' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-health-additionalInsuranceOutpatient' 
WHERE name = 'additionalInsuranceOutpatient' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-health-additionalInsuranceStationary' 
WHERE name = 'additionalInsuranceStationary' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-health-additionalInsuranceTeeth' 
WHERE name = 'additionalInsuranceTeeth' AND entity_name = 'contract';

-- Life insurance updates
UPDATE group_type 
SET form_name = 'contract-insurance-life-disabilityInsurance' 
WHERE name = 'disabilityInsurance' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-life-dailyCareAllowance' 
WHERE name = 'dailyCareAllowance' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-life-careInsurance' 
WHERE name = 'careInsurance' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-life-pensionInsuranceFund' 
WHERE name = 'pensionInsuranceFund' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-life-pensionInsurance' 
WHERE name = 'pensionInsurance' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-life-riskLifeInsurance' 
WHERE name = 'riskLifeInsurance' AND entity_name = 'contract';

-- Object insurance updates
UPDATE group_type 
SET form_name = 'contract-insurance-object-buildersLiability' 
WHERE name = 'buildersLiability' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-ebike' 
WHERE name = 'ebike' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-elementary' 
WHERE name = 'elementary' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-fire' 
WHERE name = 'fire' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-glas' 
WHERE name = 'glas' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-householdGoods' 
WHERE name = 'householdGoods' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-homeownerLiability' 
WHERE name = 'homeownerLiability' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-art' 
WHERE name = 'art' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-lossOfRent' 
WHERE name = 'lossOfRent' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-photovoltaic' 
WHERE name = 'photovoltaic' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-personalLiability' 
WHERE name = 'personalLiability' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-legalProtection' 
WHERE name = 'legalProtection' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-animalOwnerLiability' 
WHERE name = 'animalOwnerLiability' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-animalSurgery' 
WHERE name = 'animalSurgery' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-environmentalDamage' 
WHERE name = 'environmentalDamage' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-accident' 
WHERE name = 'accident' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-residentialBuilding' 
WHERE name = 'residentialBuilding' AND entity_name = 'contract';

-- Verify the updates
SELECT COUNT(*) as updated_records FROM group_type WHERE form_name LIKE '%-%-%-%'; 