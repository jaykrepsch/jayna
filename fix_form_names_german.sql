-- SQL Script to update form_name in group_type table using German names
-- This script maps the correct form names based on the actual German names in the database

-- Update financeaccount category form names
UPDATE group_type 
SET form_name = 'financeaccount-bank-bankAccount-checkingAccount' 
WHERE name = 'Girokonto' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-bank-bankAccount-savingAccount' 
WHERE name = 'Sparkonto' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-credit-creditCard-charge' 
WHERE name = 'Charge' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-credit-creditCard-debit' 
WHERE name = 'Debit' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-credit-creditCard-onlineVirtual' 
WHERE name = 'Online Virtuell' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-credit-creditCard-prepaid' 
WHERE name = 'Prepaid' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-credit-creditCard-revolving' 
WHERE name = 'Revolving' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-credit-creditCard-samsung' 
WHERE name = 'Samsung' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-systems-paymentSystems-applePay' 
WHERE name = 'Apple Pay' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-systems-paymentSystems-googlePay' 
WHERE name = 'Google Pay' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-systems-paymentSystems-klarna' 
WHERE name = 'Klarna' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-systems-paymentSystems-paypal' 
WHERE name = 'Paypal' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-systems-paymentSystems-skrill' 
WHERE name = 'Skrill' AND entity_name = 'financeaccount';

UPDATE group_type 
SET form_name = 'financeaccount-systems-paymentSystems-stripe' 
WHERE name = 'Stripe' AND entity_name = 'financeaccount';

-- Update realestate category form names
UPDATE group_type 
SET form_name = 'realestate-withDevelopment-farmland-farmland' 
WHERE name = 'Ackerland' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-farmland-forest' 
WHERE name = 'Forst' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-farmland-fruit' 
WHERE name = 'Frucht' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-farmland-pasture' 
WHERE name = 'Weide' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-garden-allotmentGarden' 
WHERE name = 'Schrebergarten' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-commercialBuilding-commercialUnit' 
WHERE name = 'Gewerbeeinheit' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-commercialBuilding-commercialBuilding' 
WHERE name = 'Gewerbehaus' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-hall-hall' 
WHERE name = 'Halle' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-parkingSpace-carport' 
WHERE name = 'Carport' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-parkingSpace-garage' 
WHERE name = 'Garage' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-parkingSpace-parkingHouse' 
WHERE name = 'Parkhaus' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-parkingSpace-parkingSpace' 
WHERE name = 'Parkplatz' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-commercialResidentialBuilding-apartmentsCommercialMix' 
WHERE name = 'Wohnungen & Gewerbe' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-residentialBuilding-semitDetachedHouse' 
WHERE name = 'Doppelhaushälfte' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-residentialBuilding-detachedHouse' 
WHERE name = 'Einfamilienhaus' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-residentialBuilding-vacationHouse' 
WHERE name = 'Ferienhaus' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-residentialBuilding-apartmentBuilding' 
WHERE name = 'Mehrfamilienhaus' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-residentialBuilding-terracedEndHouse' 
WHERE name = 'Reihenendhaus' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-residentialBuilding-terracedMidHouse' 
WHERE name = 'Reihenmittelhaus' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withDevelopment-residentialBuilding-twoFamilyHouse' 
WHERE name = 'Zweifamilienhaus' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withoutDevelopment-ground-buildingPlot' 
WHERE name = 'Bebauungsgrundstück' AND entity_name = 'realestate';

UPDATE group_type 
SET form_name = 'realestate-withoutDevelopment-ground-meadow' 
WHERE name = 'Wiese' AND entity_name = 'realestate';

-- Update contact category form names
UPDATE group_type 
SET form_name = 'contact-contact-contact-singleContact' 
WHERE name = 'Einzelkontakt' AND entity_name = 'contact';

UPDATE group_type 
SET form_name = 'contact-contact-contact-contactGroup' 
WHERE name = 'Kontaktgruppe' AND entity_name = 'contact';

-- Update mobility category form names
UPDATE group_type 
SET form_name = 'mobility-boat-hybrid-motorGlider' 
WHERE name = 'Motorsegler' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-boat-motorized-motorboat' 
WHERE name = 'Motorboot' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-boat-sailing-sailingBoat' 
WHERE name = 'Segelboot' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-boat-sailing-sailingYacht' 
WHERE name = 'Segelyacht' AND entity_name = 'mobility';

-- Bike updates
UPDATE group_type 
SET form_name = 'mobility-bike-bike-city' 
WHERE name = 'City' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-cross' 
WHERE name = 'Cross' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-tricycle' 
WHERE name = 'Dreirad' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-foldingBike' 
WHERE name = 'Falt' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-gravel' 
WHERE name = 'Gravel' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-cargo' 
WHERE name = 'Lasten' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-lifestyle' 
WHERE name = 'Lifestyle' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-mtb' 
WHERE name = 'Mountainbike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-racingBike' 
WHERE name = 'Rennrad' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-speedBike' 
WHERE name = 'Speed' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-suv' 
WHERE name = 'SUV' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-trekkingBike' 
WHERE name = 'Trekking' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-xxl' 
WHERE name = 'XXL' AND entity_name = 'mobility';

-- eBike updates
UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eCity' 
WHERE name = 'City' AND entity_name = 'mobility' AND form_name LIKE '%ebike%';

UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eCross' 
WHERE name = 'Cross' AND entity_name = 'mobility' AND form_name LIKE '%ebike%';

UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eTricycle' 
WHERE name = 'Dreirad' AND entity_name = 'mobility' AND form_name LIKE '%ebike%';

UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eFoldingBike' 
WHERE name = 'Falt' AND entity_name = 'mobility' AND form_name LIKE '%ebike%';

UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eGravel' 
WHERE name = 'Gravel' AND entity_name = 'mobility' AND form_name LIKE '%ebike%';

UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eCargo' 
WHERE name = 'Lasten' AND entity_name = 'mobility' AND form_name LIKE '%ebike%';

UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eLifestyle' 
WHERE name = 'Lifestyle' AND entity_name = 'mobility' AND form_name LIKE '%ebike%';

UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eMtb' 
WHERE name = 'Mountainbike' AND entity_name = 'mobility' AND form_name LIKE '%ebike%';

UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eRacingBike' 
WHERE name = 'Rennrad' AND entity_name = 'mobility' AND form_name LIKE '%ebike%';

UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eSpeedBike' 
WHERE name = 'Speed' AND entity_name = 'mobility' AND form_name LIKE '%ebike%';

UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eSuv' 
WHERE name = 'SUV' AND entity_name = 'mobility' AND form_name LIKE '%ebike%';

UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eTrekkingBike' 
WHERE name = 'Trekking' AND entity_name = 'mobility' AND form_name LIKE '%ebike%';

UPDATE group_type 
SET form_name = 'mobility-bike-eBike-eXxl' 
WHERE name = 'XXL' AND entity_name = 'mobility' AND form_name LIKE '%ebike%';

-- pedelec updates
UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pCity' 
WHERE name = 'City' AND entity_name = 'mobility' AND form_name LIKE '%pedelec%';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pCross' 
WHERE name = 'Cross' AND entity_name = 'mobility' AND form_name LIKE '%pedelec%';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pTricycle' 
WHERE name = 'Dreirad' AND entity_name = 'mobility' AND form_name LIKE '%pedelec%';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pFoldingBike' 
WHERE name = 'Falt' AND entity_name = 'mobility' AND form_name LIKE '%pedelec%';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pGravel' 
WHERE name = 'Gravel' AND entity_name = 'mobility' AND form_name LIKE '%pedelec%';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pCargo' 
WHERE name = 'Lasten' AND entity_name = 'mobility' AND form_name LIKE '%pedelec%';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pLifestyle' 
WHERE name = 'Lifestyle' AND entity_name = 'mobility' AND form_name LIKE '%pedelec%';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pMtb' 
WHERE name = 'Mountainbike' AND entity_name = 'mobility' AND form_name LIKE '%pedelec%';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pRacingBike' 
WHERE name = 'Rennrad' AND entity_name = 'mobility' AND form_name LIKE '%pedelec%';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pSpeedBike' 
WHERE name = 'Speed' AND entity_name = 'mobility' AND form_name LIKE '%pedelec%';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pSuv' 
WHERE name = 'SUV' AND entity_name = 'mobility' AND form_name LIKE '%pedelec%';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pTrekkingBike' 
WHERE name = 'Trekking' AND entity_name = 'mobility' AND form_name LIKE '%pedelec%';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pXxl' 
WHERE name = 'XXL' AND entity_name = 'mobility' AND form_name LIKE '%pedelec%';

-- agriculture updates
UPDATE group_type 
SET form_name = 'mobility-agriculture-tractor-tractor' 
WHERE name = 'Träker' AND entity_name = 'mobility';

-- commercial vehicle updates
UPDATE group_type 
SET form_name = 'mobility-commercialVehicle-trailer-a' 
WHERE name = 'A' AND entity_name = 'mobility' AND form_name LIKE '%trailer%';

UPDATE group_type 
SET form_name = 'mobility-commercialVehicle-trailer-b' 
WHERE name = 'B' AND entity_name = 'mobility' AND form_name LIKE '%trailer%';

UPDATE group_type 
SET form_name = 'mobility-commercialVehicle-trailer-c' 
WHERE name = 'C' AND entity_name = 'mobility' AND form_name LIKE '%trailer%';

UPDATE group_type 
SET form_name = 'mobility-commercialVehicle-box-boxvan' 
WHERE name = 'A' AND entity_name = 'mobility' AND form_name LIKE '%boxvan%';

UPDATE group_type 
SET form_name = 'mobility-commercialVehicle-box-boxvan' 
WHERE name = 'B' AND entity_name = 'mobility' AND form_name LIKE '%boxvan%';

UPDATE group_type 
SET form_name = 'mobility-commercialVehicle-box-boxvan' 
WHERE name = 'C' AND entity_name = 'mobility' AND form_name LIKE '%boxvan%';

UPDATE group_type 
SET form_name = 'mobility-commercialVehicle-caravan-family' 
WHERE name = 'Familien' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-commercialVehicle-caravan-luxury' 
WHERE name = 'Luxus' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-commercialVehicle-caravan-sport' 
WHERE name = 'Sport' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-commercialVehicle-caravan-tent' 
WHERE name = 'Zelt' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-commercialVehicle-caravan-twoPersons' 
WHERE name = 'Zwei-Personen' AND entity_name = 'mobility';

-- passenger vehicle updates
UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-convertible' 
WHERE name = 'Cabrio' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-coupe' 
WHERE name = 'Coupé' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-cuv' 
WHERE name = 'CUV' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-offRoadVehicle' 
WHERE name = 'Geländewagen' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-minibus' 
WHERE name = 'Kleinbus' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-smallTransporter' 
WHERE name = 'Kleintransporter' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-smallCar' 
WHERE name = 'Kleinwagen' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-stationWagon' 
WHERE name = 'Kombi' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-limousine' 
WHERE name = 'Limousine' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-minivan' 
WHERE name = 'Minivan' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-pickup' 
WHERE name = 'Pickup' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-roadstar' 
WHERE name = 'Roadstar' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-suv' 
WHERE name = 'SUV' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-transporter' 
WHERE name = 'Transporter' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-car-van' 
WHERE name = 'Van' AND entity_name = 'mobility';

-- motorcycle updates
UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-motorcycle-chopper' 
WHERE name = 'Chopper' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-motorcycle-cruiser' 
WHERE name = 'Cruiser' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-motorcycle-dirtBike' 
WHERE name = 'Dirt Bike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-motorcycle-motocross' 
WHERE name = 'Motocross' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-motorcycle-naked' 
WHERE name = 'Naked' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-motorcycle-srambler' 
WHERE name = 'Srambler' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-motorcycle-streetfighter' 
WHERE name = 'Streetfighter' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-motorcycle-stuntbike' 
WHERE name = 'Stuntbike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-motorcycle-trial' 
WHERE name = 'Trial' AND entity_name = 'mobility';

-- quad updates
UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-quad-quad4x4' 
WHERE name = '4x4' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-quad-quadC' 
WHERE name = 'C' AND entity_name = 'mobility' AND form_name LIKE '%quad%';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-quad-quadElectro' 
WHERE name = 'Elektro' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-quad-quadChildren' 
WHERE name = 'Kinder' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-quad-quadOffroad' 
WHERE name = 'Offroad' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-quad-quadPocket' 
WHERE name = 'Pocket' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-quad-quadRace' 
WHERE name = 'Renn' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-quad-quadRoad' 
WHERE name = 'Road' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-quad-quadSideBySide' 
WHERE name = 'Side by Side' AND entity_name = 'mobility';

-- camper updates
UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-camper-alkoven' 
WHERE name = 'mit Alkoven' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-camper-semiIntegrated' 
WHERE name = 'teilintegriert' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengerVehicle-camper-fullyIntegrated' 
WHERE name = 'vollintegriert' AND entity_name = 'mobility';

-- contract category updates (partial - continuing with more updates)
UPDATE group_type 
SET form_name = 'contract-employmentContract-employee-terminationAgreement' 
WHERE name = 'Aufhebungsvertrag' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-employmentContract-employee-help' 
WHERE name = 'Aushilfe' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-employmentContract-employee-miniJob' 
WHERE name = 'Mini' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-employmentContract-employee-internship' 
WHERE name = 'Praktikumsvertrag' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-employmentContract-employee-halfTime' 
WHERE name = 'Teilzeit' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-employmentContract-employee-fullTime' 
WHERE name = 'Vollzeit' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-employmentContract-employee-workContract' 
WHERE name = 'Werkvertrag' AND entity_name = 'contract';

-- Continue with more contract updates...
UPDATE group_type 
SET form_name = 'contract-bankAccount-fixedTermAccount-festgeldkonto' 
WHERE name = 'Festgeldkonto' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-bankAccount-checkingAccount-girokonto' 
WHERE name = 'Girokonto' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-bankAccount-savingsAccount-sparkonto' 
WHERE name = 'Sparkonto' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-bankAccount-overnightAccount-tagesgeldkonto' 
WHERE name = 'Tagesgeldkonto' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-loan-support-studentLoans' 
WHERE name = 'Bafög' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-loan-realestate-callLoan' 
WHERE name = 'Abrufdarlehen' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-loan-realestate-annuityLoan' 
WHERE name = 'Annuitätendarlehen' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-loan-realestate-bulletLoans' 
WHERE name = 'Endfällige Darlehen' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-loan-realestate-termLoan' 
WHERE name = 'Laufzeitzinsdarlehen' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-loan-realestate-modernization' 
WHERE name = 'Modernisierung' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-loan-realestate-participatoryLoans' 
WHERE name = 'Partiarische Darlehen' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-loan-realestate-rollingMarketLoans' 
WHERE name = 'Rollierende Geldmarktdarlehen' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-loan-realestate-amortizationLoan' 
WHERE name = 'Tilgungsdarlehen' AND entity_name = 'contract';

-- Service updates
UPDATE group_type 
SET form_name = 'contract-service-fees-municipalFees' 
WHERE name = 'Gemeindegebühren' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-fees-realEstateTax' 
WHERE name = 'Grunderwerbssteuer' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-service-gardener' 
WHERE name = 'Gärtner' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-service-caretakerService' 
WHERE name = 'Hausmeisterservice' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-service-propertyManagement' 
WHERE name = 'Hausverwaltung' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-service-realEstateAgent' 
WHERE name = 'Immobilienmakler' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-service-carInspection' 
WHERE name = 'KFZ-Inspektion' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-service-rentalManagement' 
WHERE name = 'Mietverwaltung' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-service-notary' 
WHERE name = 'Notar' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-service-cleaning' 
WHERE name = 'Reinigung' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-service-insuranceBroker' 
WHERE name = 'Versicherungsmakler' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-maintenance-elevator' 
WHERE name = 'Aufzug' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-maintenance-heating' 
WHERE name = 'Heizung' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-maintenance-chimneySweeper' 
WHERE name = 'Schornsteinfeger' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-service-maintenance-heatPump' 
WHERE name = 'Wärmepumpe' AND entity_name = 'contract';

-- Energy updates
UPDATE group_type 
SET form_name = 'contract-energy-electric-electricity' 
WHERE name = 'Strom' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-energy-heating-districtHeating' 
WHERE name = 'Fernwärme' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-energy-heating-gas' 
WHERE name = 'Gas' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-energy-heating-oil' 
WHERE name = 'Öl' AND entity_name = 'contract';

-- Property management updates
UPDATE group_type 
SET form_name = 'contract-propertyManagement-propertyManagement-hausverwaltung' 
WHERE name = 'Hausverwaltung' AND entity_name = 'contract';

-- Purchase contract updates
UPDATE group_type 
SET form_name = 'contract-purchaseContract-objects-coffeeTable' 
WHERE name = 'Couch' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-purchaseContract-objects-table' 
WHERE name = 'Tisch' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-purchaseContract-devices-tv' 
WHERE name = 'Fernseher' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-purchaseContract-devices-mobile' 
WHERE name = 'Handy' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-purchaseContract-realestate-realEstateTax' 
WHERE name = 'Grunderwerbssteuer' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-purchaseContract-realestate-house' 
WHERE name = 'Haus' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-purchaseContract-realestate-apartment' 
WHERE name = 'Wohnung' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-purchaseContract-car-car' 
WHERE name = 'Auto' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-purchaseContract-car-motorcycle' 
WHERE name = 'Motorrad' AND entity_name = 'contract';

-- Credit updates
UPDATE group_type 
SET form_name = 'contract-credit-car-car' 
WHERE name = 'Auto' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-credit-car-boat' 
WHERE name = 'Motorboot' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-credit-car-caravan' 
WHERE name = 'Wohnwagen' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-credit-credit-freeUse' 
WHERE name = 'freie Verwendung' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-credit-credit-modernization' 
WHERE name = 'Modernisierung' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-credit-credit-rescheduling' 
WHERE name = 'Umschuldung' AND entity_name = 'contract';

-- Credit card updates
UPDATE group_type 
SET form_name = 'contract-creditCard-debitCard-debitkarte' 
WHERE name = 'Debitkarte' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-creditCard-creditCard-kreditkarte' 
WHERE name = 'Kreditkarte' AND entity_name = 'contract';

-- Leasing updates
UPDATE group_type 
SET form_name = 'contract-leasing-car-car' 
WHERE name = 'Auto' AND entity_name = 'contract';

-- Media updates
UPDATE group_type 
SET form_name = 'contract-media-television-cableTv' 
WHERE name = 'Kabelfernsehen' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-media-television-satelliteTv' 
WHERE name = 'Satellitenfernsehen' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-media-internet-broadband' 
WHERE name = 'DSL' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-media-internet-glassFiber' 
WHERE name = 'Glasfaser' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-media-cellular-carPhone' 
WHERE name = 'Autotelefon' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-media-cellular-mobile' 
WHERE name = 'Handy' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-media-cellular-tablet' 
WHERE name = 'Tablet' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-media-streaming-moviesSeries' 
WHERE name = 'Filme / Serien' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-media-streaming-music' 
WHERE name = 'Musik' AND entity_name = 'contract';

-- Rental contract updates
UPDATE group_type 
SET form_name = 'contract-rentalContract-putDownSpace-garage' 
WHERE name = 'Garage' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-rentalContract-putDownSpace-parking' 
WHERE name = 'Parkplatz' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-rentalContract-realestate-holidayHouse' 
WHERE name = 'Ferienhaus' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-rentalContract-realestate-holidayApartment' 
WHERE name = 'Ferienwohnung' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-rentalContract-realestate-building' 
WHERE name = 'Gebäude' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-rentalContract-realestate-room' 
WHERE name = 'Raum' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-rentalContract-car-car' 
WHERE name = 'Auto' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-rentalContract-car-camper' 
WHERE name = 'Wohnmobil' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-rentalContract-reside-houseLease' 
WHERE name = 'Hausmietvertrag' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-rentalContract-reside-apartmentRentalAgreement' 
WHERE name = 'Wohnungsmietvertrag' AND entity_name = 'contract';

-- Rental management updates
UPDATE group_type 
SET form_name = 'contract-rentalManagement-rentalManagement-mietverwaltung' 
WHERE name = 'Mietverwaltung' AND entity_name = 'contract';

-- Insurance updates (partial - continuing with more updates)
UPDATE group_type 
SET form_name = 'contract-insurance-business-managerLiability' 
WHERE name = 'D&O Managerhaftpflicht' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-business-building' 
WHERE name = 'Gebäude' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-business-legalProtection' 
WHERE name = 'Rechtsschutz' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-car-motorcycle' 
WHERE name = 'Motorrad' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-car-tractor' 
WHERE name = 'Traktor' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-car-camper' 
WHERE name = 'Wohnmobil' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-car-caravan' 
WHERE name = 'Wohnwagen' AND entity_name = 'contract';

-- Health insurance updates
UPDATE group_type 
SET form_name = 'contract-insurance-health-abroad' 
WHERE name = 'Ausland' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-health-foreignTravel' 
WHERE name = 'Auslandsreise' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-health-statutoryhealthinsurance' 
WHERE name = 'gesetzliche Krankenversicherung' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-health-hospitalPerDiem' 
WHERE name = 'Krankenhaustagegeld' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-health-sickPay' 
WHERE name = 'Krankentagegeld' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-health-additionalInsuranceFull' 
WHERE name = 'Privat Kranken voll' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-health-additionalInsuranceOutpatient' 
WHERE name = 'Privat Zusatz ambulant' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-health-additionalInsuranceStationary' 
WHERE name = 'Privat Zusatz stationär' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-health-additionalInsuranceTeeth' 
WHERE name = 'Privat Zusatz Zahn' AND entity_name = 'contract';

-- Life insurance updates
UPDATE group_type 
SET form_name = 'contract-insurance-life-disabilityInsurance' 
WHERE name = 'Berufsunfähigkeit' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-life-dailyCareAllowance' 
WHERE name = 'Pflegetagegeld' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-life-careInsurance' 
WHERE name = 'Pflegeversicherung' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-life-pensionInsuranceFund' 
WHERE name = 'Rentenversicherung Fonds' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-life-pensionInsurance' 
WHERE name = 'Rentenversicherung klassisch' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-life-riskLifeInsurance' 
WHERE name = 'Risiko Lebensversicherung' AND entity_name = 'contract';

-- Object insurance updates
UPDATE group_type 
SET form_name = 'contract-insurance-object-buildersLiability' 
WHERE name = 'Bauherrenhaftpflicht' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-ebike' 
WHERE name = 'E-Bike & Pedeleg' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-elementary' 
WHERE name = 'Elementar (Wohngebäude)' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-fire' 
WHERE name = 'Feuer' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-glas' 
WHERE name = 'Glas' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-householdGoods' 
WHERE name = 'Hausrat' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-homeownerLiability' 
WHERE name = 'Haus- und Grundbesitzerhaftpflicht' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-art' 
WHERE name = 'Kunst' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-lossOfRent' 
WHERE name = 'Mietausfall' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-photovoltaic' 
WHERE name = 'Photovoltaik' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-personalLiability' 
WHERE name = 'Privathaftpflicht' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-legalProtection' 
WHERE name = 'Rechtsschutz' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-animalOwnerLiability' 
WHERE name = 'Tierhalterhaftpflicht' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-animalSurgery' 
WHERE name = 'Tier OP' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-environmentalDamage' 
WHERE name = 'Umweltschadenhaftpflicht' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-accident' 
WHERE name = 'Unfall' AND entity_name = 'contract';

UPDATE group_type 
SET form_name = 'contract-insurance-object-residentialBuilding' 
WHERE name = 'Wohngebäude' AND entity_name = 'contract';

-- Verify the updates
SELECT COUNT(*) as updated_records FROM group_type WHERE form_name LIKE '%-%-%-%'; 