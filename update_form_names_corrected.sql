-- Update FormNames von alten Namen auf neue Namen

-- Update 1: quad -> mobility-passengervehicle-quad-quad4x4
UPDATE group_type SET form_name = 'mobility-passengervehicle-quad-quad4x4' WHERE form_name = 'quad';

-- Update 2: trailer -> mobility-commercialvehicle-trailer-a
UPDATE group_type SET form_name = 'mobility-commercialvehicle-trailer-a' WHERE form_name = 'trailer';

-- Update 3: boxvan -> mobility-commercialvehicle-box-boxvan
UPDATE group_type SET form_name = 'mobility-commercialvehicle-box-boxvan' WHERE form_name = 'boxvan';

-- Update 4: contract-loan-credit-creditcontract -> contract-loan-realestate-callloan
UPDATE group_type SET form_name = 'contract-loan-realestate-callloan' WHERE form_name = 'contract-loan-credit-creditcontract';

-- Update 5: farmland -> realestate-withdevelopment-farmland-farmland
UPDATE group_type SET form_name = 'realestate-withdevelopment-farmland-farmland' WHERE form_name = 'farmland';

-- Update 6: contract-creditcard-credit-creditcard -> financeaccount-systems-paymentsystems-applepay
UPDATE group_type SET form_name = 'financeaccount-systems-paymentsystems-applepay' WHERE form_name = 'contract-creditcard-credit-creditcard';

-- Update 7: contract-employment-employee-terminationAgreement -> contract-employmentcontract-publicservice-terminationagreement
UPDATE group_type SET form_name = 'contract-employmentcontract-publicservice-terminationagreement' WHERE form_name = 'contract-employment-employee-terminationAgreement';

-- Update 8: contract-employment-employee-terminationAgreement -> contract-employmentcontract-employee-terminationagreement
UPDATE group_type SET form_name = 'contract-employmentcontract-employee-terminationagreement' WHERE form_name = 'contract-employment-employee-terminationAgreement';

-- Update 9: contract-employment-employee-terminationAgreement -> contract-employmentcontract-official-terminationagreement
UPDATE group_type SET form_name = 'contract-employmentcontract-official-terminationagreement' WHERE form_name = 'contract-employment-employee-terminationAgreement';

-- Update 10: contract-service-maintenance-maintenancecontract -> contract-service-maintenance-elevator
UPDATE group_type SET form_name = 'contract-service-maintenance-elevator' WHERE form_name = 'contract-service-maintenance-maintenancecontract';

-- Update 11: employment-contract -> contract-employmentcontract-official-help
UPDATE group_type SET form_name = 'contract-employmentcontract-official-help' WHERE form_name = 'employment-contract';

-- Update 12: employment-contract -> contract-employmentcontract-publicservice-help
UPDATE group_type SET form_name = 'contract-employmentcontract-publicservice-help' WHERE form_name = 'employment-contract';

-- Update 13: employment-contract -> contract-employmentcontract-employee-help
UPDATE group_type SET form_name = 'contract-employmentcontract-employee-help' WHERE form_name = 'employment-contract';

-- Update 14: contract-insurance-inthealth-inthealthcontract -> contract-insurance-health-abroad
UPDATE group_type SET form_name = 'contract-insurance-health-abroad' WHERE form_name = 'contract-insurance-inthealth-inthealthcontract';

-- Update 15: contract-insurance-travel-travelcontract -> contract-insurance-health-foreigntravel
UPDATE group_type SET form_name = 'contract-insurance-health-foreigntravel' WHERE form_name = 'contract-insurance-travel-travelcontract';

-- Update 16: contract-sale-sale-salecontract -> contract-purchasecontract-car-car
UPDATE group_type SET form_name = 'contract-purchasecontract-car-car' WHERE form_name = 'contract-sale-sale-salecontract';

-- Update 17: contract-loan-credit-creditcontract -> contract-credit-car-car
UPDATE group_type SET form_name = 'contract-credit-car-car' WHERE form_name = 'contract-loan-credit-creditcontract';

-- Update 18: leasing -> contract-leasing-car-car
UPDATE group_type SET form_name = 'contract-leasing-car-car' WHERE form_name = 'leasing';

-- Update 19: contract-lease-lease-leasecontract -> contract-rentalcontract-car-car
UPDATE group_type SET form_name = 'contract-rentalcontract-car-car' WHERE form_name = 'contract-lease-lease-leasecontract';

-- Update 20: contract-media-media-mediacontract -> contract-media-cellular-carphone
UPDATE group_type SET form_name = 'contract-media-cellular-carphone' WHERE form_name = 'contract-media-media-mediacontract';

-- Update 21: tractor -> mobility-agriculture-tractor-tractor
UPDATE group_type SET form_name = 'mobility-agriculture-tractor-tractor' WHERE form_name = 'tractor';

-- Update 22: boxvan -> mobility-commercialvehicle-box-boxvan
UPDATE group_type SET form_name = 'mobility-commercialvehicle-box-boxvan' WHERE form_name = 'boxvan';

-- Update 23: trailer -> mobility-commercialvehicle-trailer-b
UPDATE group_type SET form_name = 'mobility-commercialvehicle-trailer-b' WHERE form_name = 'trailer';

-- Update 24: contract-loan-credit-creditcontract -> contract-loan-support-studentloans
UPDATE group_type SET form_name = 'contract-loan-support-studentloans' WHERE form_name = 'contract-loan-credit-creditcontract';

-- Update 25: contract-insurance-builderliability-builderliabilitycontract -> contract-insurance-object-buildersliability
UPDATE group_type SET form_name = 'contract-insurance-object-buildersliability' WHERE form_name = 'contract-insurance-builderliability-builderliabilitycontract';

-- Update 26: building-plot -> realestate-withoutdevelopment-ground-buildingplot
UPDATE group_type SET form_name = 'realestate-withoutdevelopment-ground-buildingplot' WHERE form_name = 'building-plot';

-- Update 27: contract-insurance-disability-disabilitycontract -> contract-insurance-life-disabilityinsurance
UPDATE group_type SET form_name = 'contract-insurance-life-disabilityinsurance' WHERE form_name = 'contract-insurance-disability-disabilitycontract';

-- Update 28: trailer -> mobility-commercialvehicle-trailer-c
UPDATE group_type SET form_name = 'mobility-commercialvehicle-trailer-c' WHERE form_name = 'trailer';

-- Update 29: quad -> mobility-passengervehicle-quad-quadc
UPDATE group_type SET form_name = 'mobility-passengervehicle-quad-quadc' WHERE form_name = 'quad';

-- Update 30: boxvan -> mobility-commercialvehicle-box-boxvan
UPDATE group_type SET form_name = 'mobility-commercialvehicle-box-boxvan' WHERE form_name = 'boxvan';

-- Update 31: tractor -> mobility-agriculture-tractor-tractor
UPDATE group_type SET form_name = 'mobility-agriculture-tractor-tractor' WHERE form_name = 'tractor';

-- Update 32: car -> mobility-passengervehicle-car-convertible
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-convertible' WHERE form_name = 'car';

-- Update 33: carport -> realestate-withdevelopment-parkingspace-carport
UPDATE group_type SET form_name = 'realestate-withdevelopment-parkingspace-carport' WHERE form_name = 'carport';

-- Update 34: contract-savings-savings-overnightcontract -> financeaccount-credit-creditcard-charge
UPDATE group_type SET form_name = 'financeaccount-credit-creditcard-charge' WHERE form_name = 'contract-savings-savings-overnightcontract';

-- Update 35: chopper -> mobility-passengervehicle-motorcycle-chopper
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-chopper' WHERE form_name = 'chopper';

-- Update 36: bicycle -> mobility-bike-bike-city
UPDATE group_type SET form_name = 'mobility-bike-bike-city' WHERE form_name = 'bicycle';

-- Update 37: pedelec -> mobility-bike-pedelec-pcity
UPDATE group_type SET form_name = 'mobility-bike-pedelec-pcity' WHERE form_name = 'pedelec';

-- Update 38: ebike -> mobility-bike-ebike-ecity
UPDATE group_type SET form_name = 'mobility-bike-ebike-ecity' WHERE form_name = 'ebike';

-- Update 39: contract-sale-sale-salecontract -> contract-purchasecontract-objects-coffeetable
UPDATE group_type SET form_name = 'contract-purchasecontract-objects-coffeetable' WHERE form_name = 'contract-sale-sale-salecontract';

-- Update 40: car -> mobility-passengervehicle-car-coupe
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-coupe' WHERE form_name = 'car';

-- Update 41: ebike -> mobility-bike-ebike-ecross
UPDATE group_type SET form_name = 'mobility-bike-ebike-ecross' WHERE form_name = 'ebike';

-- Update 42: pedelec -> mobility-bike-pedelec-pcross
UPDATE group_type SET form_name = 'mobility-bike-pedelec-pcross' WHERE form_name = 'pedelec';

-- Update 43: bicycle -> mobility-bike-bike-cross
UPDATE group_type SET form_name = 'mobility-bike-bike-cross' WHERE form_name = 'bicycle';

-- Update 44: cruiser -> mobility-passengervehicle-motorcycle-cruiser
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-cruiser' WHERE form_name = 'cruiser';

-- Update 45: car -> mobility-passengervehicle-car-cuv
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-cuv' WHERE form_name = 'car';

-- Update 46: contract-creditcard-credit-creditcard -> financeaccount-credit-creditcard-debit
UPDATE group_type SET form_name = 'financeaccount-credit-creditcard-debit' WHERE form_name = 'contract-creditcard-credit-creditcard';

-- Update 47: dirt-bike -> mobility-passengervehicle-motorcycle-dirtbike
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-dirtbike' WHERE form_name = 'dirt-bike';

-- Update 48: contract-insurance-domanager-domanagercontract -> contract-insurance-business-managerliability
UPDATE group_type SET form_name = 'contract-insurance-business-managerliability' WHERE form_name = 'contract-insurance-domanager-domanagercontract';

-- Update 49: commercial-residential-property -> realestate-withdevelopment-residentialbuilding-semitdetachedhouse
UPDATE group_type SET form_name = 'realestate-withdevelopment-residentialbuilding-semitdetachedhouse' WHERE form_name = 'commercial-residential-property';

-- Update 50: pedelec -> mobility-bike-pedelec-ptricycle
UPDATE group_type SET form_name = 'mobility-bike-pedelec-ptricycle' WHERE form_name = 'pedelec';

-- Update 51: bicycle -> mobility-bike-bike-tricycle
UPDATE group_type SET form_name = 'mobility-bike-bike-tricycle' WHERE form_name = 'bicycle';

-- Update 52: ebike -> mobility-bike-ebike-etricycle
UPDATE group_type SET form_name = 'mobility-bike-ebike-etricycle' WHERE form_name = 'ebike';

-- Update 53: contract-media-media-mediacontract -> contract-media-internet-broadband
UPDATE group_type SET form_name = 'contract-media-internet-broadband' WHERE form_name = 'contract-media-media-mediacontract';

-- Update 54: contract-insurance-bike-bikecontract -> contract-insurance-object-ebike
UPDATE group_type SET form_name = 'contract-insurance-object-ebike' WHERE form_name = 'contract-insurance-bike-bikecontract';

-- Update 55: commercial-residential-property -> realestate-withdevelopment-residentialbuilding-detachedhouse
UPDATE group_type SET form_name = 'realestate-withdevelopment-residentialbuilding-detachedhouse' WHERE form_name = 'commercial-residential-property';

-- Update 56: single-contact -> contact-contact-contact-singlecontact
UPDATE group_type SET form_name = 'contact-contact-contact-singlecontact' WHERE form_name = 'single-contact';

-- Update 57: quad -> mobility-passengervehicle-quad-quadelectro
UPDATE group_type SET form_name = 'mobility-passengervehicle-quad-quadelectro' WHERE form_name = 'quad';

-- Update 58: contract-insurance-fire-firecontract -> contract-insurance-object-elementary
UPDATE group_type SET form_name = 'contract-insurance-object-elementary' WHERE form_name = 'contract-insurance-fire-firecontract';

-- Update 59: contract-loan-credit-creditcontract -> contract-loan-realestate-bulletloans
UPDATE group_type SET form_name = 'contract-loan-realestate-bulletloans' WHERE form_name = 'contract-loan-credit-creditcontract';

-- Update 60: ebike -> mobility-bike-ebike-efoldingbike
UPDATE group_type SET form_name = 'mobility-bike-ebike-efoldingbike' WHERE form_name = 'ebike';

-- Update 61: pedelec -> mobility-bike-pedelec-pfoldingbike
UPDATE group_type SET form_name = 'mobility-bike-pedelec-pfoldingbike' WHERE form_name = 'pedelec';

-- Update 62: bicycle -> mobility-bike-bike-foldingbike
UPDATE group_type SET form_name = 'mobility-bike-bike-foldingbike' WHERE form_name = 'bicycle';

-- Update 63: caravan -> mobility-commercialvehicle-caravan-family
UPDATE group_type SET form_name = 'mobility-commercialvehicle-caravan-family' WHERE form_name = 'caravan';

-- Update 64: commercial-residential-property -> realestate-withdevelopment-residentialbuilding-vacationhouse
UPDATE group_type SET form_name = 'realestate-withdevelopment-residentialbuilding-vacationhouse' WHERE form_name = 'commercial-residential-property';

-- Update 65: contract-lease-lease-leasecontract -> contract-rentalcontract-realestate-holidayhouse
UPDATE group_type SET form_name = 'contract-rentalcontract-realestate-holidayhouse' WHERE form_name = 'contract-lease-lease-leasecontract';

-- Update 66: contract-lease-lease-leasecontract -> contract-rentalcontract-realestate-holidayapartment
UPDATE group_type SET form_name = 'contract-rentalcontract-realestate-holidayapartment' WHERE form_name = 'contract-lease-lease-leasecontract';

-- Update 67: contract-sale-sale-salecontract -> contract-purchasecontract-devices-tv
UPDATE group_type SET form_name = 'contract-purchasecontract-devices-tv' WHERE form_name = 'contract-sale-sale-salecontract';

-- Update 68: energy -> contract-energy-heating-districtheating
UPDATE group_type SET form_name = 'contract-energy-heating-districtheating' WHERE form_name = 'energy';

-- Update 69: insurance-fire -> contract-insurance-object-fire
UPDATE group_type SET form_name = 'contract-insurance-object-fire' WHERE form_name = 'insurance-fire';

-- Update 70: contract-media-media-mediacontract -> contract-media-streaming-moviesseries
UPDATE group_type SET form_name = 'contract-media-streaming-moviesseries' WHERE form_name = 'contract-media-media-mediacontract';

-- Update 71: forest -> realestate-withdevelopment-farmland-forest
UPDATE group_type SET form_name = 'realestate-withdevelopment-farmland-forest' WHERE form_name = 'forest';

-- Update 72: contract-loan-credit-creditcontract -> contract-credit-credit-freeuse
UPDATE group_type SET form_name = 'contract-credit-credit-freeuse' WHERE form_name = 'contract-loan-credit-creditcontract';

-- Update 73: fruit -> realestate-withdevelopment-farmland-fruit
UPDATE group_type SET form_name = 'realestate-withdevelopment-farmland-fruit' WHERE form_name = 'fruit';

-- Update 74: contract-lease-lease-leasecontract -> contract-rentalcontract-putdownspace-garage
UPDATE group_type SET form_name = 'contract-rentalcontract-putdownspace-garage' WHERE form_name = 'contract-lease-lease-leasecontract';

-- Update 75: garage -> realestate-withdevelopment-parkingspace-garage
UPDATE group_type SET form_name = 'realestate-withdevelopment-parkingspace-garage' WHERE form_name = 'garage';

-- Update 76: contract-service-gardener-gardenerservicecontract -> contract-service-service-gardener
UPDATE group_type SET form_name = 'contract-service-service-gardener' WHERE form_name = 'contract-service-gardener-gardenerservicecontract';

-- Update 77: energy -> contract-energy-heating-gas
UPDATE group_type SET form_name = 'contract-energy-heating-gas' WHERE form_name = 'energy';

-- Update 78: contract-lease-lease-leasecontract -> contract-rentalcontract-realestate-building
UPDATE group_type SET form_name = 'contract-rentalcontract-realestate-building' WHERE form_name = 'contract-lease-lease-leasecontract';

-- Update 79: contract-insurance-residential-residentialcontract -> contract-insurance-business-building
UPDATE group_type SET form_name = 'contract-insurance-business-building' WHERE form_name = 'contract-insurance-residential-residentialcontract';

-- Update 80: car -> mobility-passengervehicle-car-offroadvehicle
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-offroadvehicle' WHERE form_name = 'car';

-- Update 81: municipal-fees -> contract-service-fees-municipalfees
UPDATE group_type SET form_name = 'contract-service-fees-municipalfees' WHERE form_name = 'municipal-fees';

-- Update 82: contract-insurance-health-healthcontract -> contract-insurance-health-statutoryhealthinsurance
UPDATE group_type SET form_name = 'contract-insurance-health-statutoryhealthinsurance' WHERE form_name = 'contract-insurance-health-healthcontract';

-- Update 83: commercial-residential-property -> realestate-withdevelopment-commercialbuilding-commercialunit
UPDATE group_type SET form_name = 'realestate-withdevelopment-commercialbuilding-commercialunit' WHERE form_name = 'commercial-residential-property';

-- Update 84: commercial-residential-property -> realestate-withdevelopment-commercialbuilding-commercialbuilding
UPDATE group_type SET form_name = 'realestate-withdevelopment-commercialbuilding-commercialbuilding' WHERE form_name = 'commercial-residential-property';

-- Update 85: checking-account -> financeaccount-bank-bankaccount-checkingaccount
UPDATE group_type SET form_name = 'financeaccount-bank-bankaccount-checkingaccount' WHERE form_name = 'checking-account';

-- Update 86: contract-insurance-fire-firecontract -> contract-insurance-object-glas
UPDATE group_type SET form_name = 'contract-insurance-object-glas' WHERE form_name = 'contract-insurance-fire-firecontract';

-- Update 87: contract-media-media-mediacontract -> contract-media-internet-glassfiber
UPDATE group_type SET form_name = 'contract-media-internet-glassfiber' WHERE form_name = 'contract-media-media-mediacontract';

-- Update 88: contract-creditcard-credit-creditcard -> financeaccount-systems-paymentsystems-googlepay
UPDATE group_type SET form_name = 'financeaccount-systems-paymentsystems-googlepay' WHERE form_name = 'contract-creditcard-credit-creditcard';

-- Update 89: bicycle -> mobility-bike-bike-gravel
UPDATE group_type SET form_name = 'mobility-bike-bike-gravel' WHERE form_name = 'bicycle';

-- Update 90: ebike -> mobility-bike-ebike-egravel
UPDATE group_type SET form_name = 'mobility-bike-ebike-egravel' WHERE form_name = 'ebike';

-- Update 91: pedelec -> mobility-bike-pedelec-pgravel
UPDATE group_type SET form_name = 'mobility-bike-pedelec-pgravel' WHERE form_name = 'pedelec';

-- Update 92: contract-tax-realestate-realestatetaxcontract -> contract-service-fees-realestatetax
UPDATE group_type SET form_name = 'contract-service-fees-realestatetax' WHERE form_name = 'contract-tax-realestate-realestatetaxcontract';

-- Update 93: contract-sale-sale-salecontract -> contract-purchasecontract-realestate-realestatetax
UPDATE group_type SET form_name = 'contract-purchasecontract-realestate-realestatetax' WHERE form_name = 'contract-sale-sale-salecontract';

-- Update 94: commercial-residential-property -> realestate-withdevelopment-hall-hall
UPDATE group_type SET form_name = 'realestate-withdevelopment-hall-hall' WHERE form_name = 'commercial-residential-property';

-- Update 95: contract-sale-sale-salecontract -> contract-purchasecontract-devices-mobile
UPDATE group_type SET form_name = 'contract-purchasecontract-devices-mobile' WHERE form_name = 'contract-sale-sale-salecontract';

-- Update 96: contract-media-media-mediacontract -> contract-media-cellular-mobile
UPDATE group_type SET form_name = 'contract-media-cellular-mobile' WHERE form_name = 'contract-media-media-mediacontract';

-- Update 97: contract-sale-sale-salecontract -> contract-purchasecontract-realestate-house
UPDATE group_type SET form_name = 'contract-purchasecontract-realestate-house' WHERE form_name = 'contract-sale-sale-salecontract';

-- Update 98: contract-service-caretaker-caretakerservicecontract -> contract-service-service-caretakerservice
UPDATE group_type SET form_name = 'contract-service-service-caretakerservice' WHERE form_name = 'contract-service-caretaker-caretakerservicecontract';

-- Update 99: contract-lease-lease-leasecontract -> contract-rentalcontract-reside-houselease
UPDATE group_type SET form_name = 'contract-rentalcontract-reside-houselease' WHERE form_name = 'contract-lease-lease-leasecontract';

-- Update 100: contract-insurance-furniture-furniturecontract -> contract-insurance-object-householdgoods
UPDATE group_type SET form_name = 'contract-insurance-object-householdgoods' WHERE form_name = 'contract-insurance-furniture-furniturecontract';

-- Update 101: contract-insurance-liability-liabilitycontract -> contract-insurance-object-homeownerliability
UPDATE group_type SET form_name = 'contract-insurance-object-homeownerliability' WHERE form_name = 'contract-insurance-liability-liabilitycontract';

-- Update 102: property-management -> contract-service-service-propertymanagement
UPDATE group_type SET form_name = 'contract-service-service-propertymanagement' WHERE form_name = 'property-management';

-- Update 103: contract-service-maintenance-maintenancecontract -> contract-service-maintenance-heating
UPDATE group_type SET form_name = 'contract-service-maintenance-heating' WHERE form_name = 'contract-service-maintenance-maintenancecontract';

-- Update 104: contract-service-broker-realestateagent -> contract-service-service-realestateagent
UPDATE group_type SET form_name = 'contract-service-service-realestateagent' WHERE form_name = 'contract-service-broker-realestateagent';

-- Update 105: motorboat -> mobility-boat-motorized-motorboat
UPDATE group_type SET form_name = 'mobility-boat-motorized-motorboat' WHERE form_name = 'motorboat';

-- Update 106: contract-media-media-mediacontract -> contract-media-television-cabletv
UPDATE group_type SET form_name = 'contract-media-television-cabletv' WHERE form_name = 'contract-media-media-mediacontract';

-- Update 107: contract-mobility-car-inspection -> contract-service-service-carinspection
UPDATE group_type SET form_name = 'contract-service-service-carinspection' WHERE form_name = 'contract-mobility-car-inspection';

-- Update 108: quad -> mobility-passengervehicle-quad-quadchildren
UPDATE group_type SET form_name = 'mobility-passengervehicle-quad-quadchildren' WHERE form_name = 'quad';

-- Update 109: contract-creditcard-credit-creditcard -> financeaccount-systems-paymentsystems-klarna
UPDATE group_type SET form_name = 'financeaccount-systems-paymentsystems-klarna' WHERE form_name = 'contract-creditcard-credit-creditcard';

-- Update 110: car -> mobility-passengervehicle-car-minibus
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-minibus' WHERE form_name = 'car';

-- Update 111: car -> mobility-passengervehicle-car-smalltransporter
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-smalltransporter' WHERE form_name = 'car';

-- Update 112: car -> mobility-passengervehicle-car-smallcar
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-smallcar' WHERE form_name = 'car';

-- Update 113: car -> mobility-passengervehicle-car-stationwagon
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-stationwagon' WHERE form_name = 'car';

-- Update 114: contact-group -> contact-contact-contact-contactgroup
UPDATE group_type SET form_name = 'contact-contact-contact-contactgroup' WHERE form_name = 'contact-group';

-- Update 115: contract-insurance-dailyhospital-dailyhospitalcontract -> contract-insurance-health-hospitalperdiem
UPDATE group_type SET form_name = 'contract-insurance-health-hospitalperdiem' WHERE form_name = 'contract-insurance-dailyhospital-dailyhospitalcontract';

-- Update 116: contract-insurance-dailysickness-dailysicknesscontract -> contract-insurance-health-sickpay
UPDATE group_type SET form_name = 'contract-insurance-health-sickpay' WHERE form_name = 'contract-insurance-dailysickness-dailysicknesscontract';

-- Update 117: contract-insurance-fire-firecontract -> contract-insurance-object-art
UPDATE group_type SET form_name = 'contract-insurance-object-art' WHERE form_name = 'contract-insurance-fire-firecontract';

-- Update 118: pedelec -> mobility-bike-pedelec-pcargo
UPDATE group_type SET form_name = 'mobility-bike-pedelec-pcargo' WHERE form_name = 'pedelec';

-- Update 119: ebike -> mobility-bike-ebike-ecargo
UPDATE group_type SET form_name = 'mobility-bike-ebike-ecargo' WHERE form_name = 'ebike';

-- Update 120: bicycle -> mobility-bike-bike-cargo
UPDATE group_type SET form_name = 'mobility-bike-bike-cargo' WHERE form_name = 'bicycle';

-- Update 121: contract-loan-credit-creditcontract -> contract-loan-realestate-termloan
UPDATE group_type SET form_name = 'contract-loan-realestate-termloan' WHERE form_name = 'contract-loan-credit-creditcontract';

-- Update 122: pedelec -> mobility-bike-pedelec-plifestyle
UPDATE group_type SET form_name = 'mobility-bike-pedelec-plifestyle' WHERE form_name = 'pedelec';

-- Update 123: ebike -> mobility-bike-ebike-elifestyle
UPDATE group_type SET form_name = 'mobility-bike-ebike-elifestyle' WHERE form_name = 'ebike';

-- Update 124: bicycle -> mobility-bike-bike-lifestyle
UPDATE group_type SET form_name = 'mobility-bike-bike-lifestyle' WHERE form_name = 'bicycle';

-- Update 125: car -> mobility-passengervehicle-car-limousine
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-limousine' WHERE form_name = 'car';

-- Update 126: caravan -> mobility-commercialvehicle-caravan-luxury
UPDATE group_type SET form_name = 'mobility-commercialvehicle-caravan-luxury' WHERE form_name = 'caravan';

-- Update 127: commercial-residential-property -> realestate-withdevelopment-residentialbuilding-apartmentbuilding
UPDATE group_type SET form_name = 'realestate-withdevelopment-residentialbuilding-apartmentbuilding' WHERE form_name = 'commercial-residential-property';

-- Update 128: contract-insurance-rental-rentalcontract -> contract-insurance-object-lossofrent
UPDATE group_type SET form_name = 'contract-insurance-object-lossofrent' WHERE form_name = 'contract-insurance-rental-rentalcontract';

-- Update 129: contract-rentalmanagement-rentalmanagement-rentalmanagementcontract -> contract-service-service-rentalmanagement
UPDATE group_type SET form_name = 'contract-service-service-rentalmanagement' WHERE form_name = 'contract-rentalmanagement-rentalmanagement-rentalmanagementcontract';

-- Update 130: employment-contract -> contract-employmentcontract-official-minijob
UPDATE group_type SET form_name = 'contract-employmentcontract-official-minijob' WHERE form_name = 'employment-contract';

-- Update 131: employment-contract -> contract-employmentcontract-employee-minijob
UPDATE group_type SET form_name = 'contract-employmentcontract-employee-minijob' WHERE form_name = 'employment-contract';

-- Update 132: employment-contract -> contract-employmentcontract-publicservice-minijob
UPDATE group_type SET form_name = 'contract-employmentcontract-publicservice-minijob' WHERE form_name = 'employment-contract';

-- Update 133: car -> mobility-passengervehicle-car-minivan
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-minivan' WHERE form_name = 'car';

-- Update 134: camper -> mobility-passengervehicle-camper-alkoven
UPDATE group_type SET form_name = 'mobility-passengervehicle-camper-alkoven' WHERE form_name = 'camper';

-- Update 135: contract-loan-credit-creditcontract -> contract-credit-credit-modernization
UPDATE group_type SET form_name = 'contract-credit-credit-modernization' WHERE form_name = 'contract-loan-credit-creditcontract';

-- Update 136: contract-loan-credit-creditcontract -> contract-loan-realestate-modernization
UPDATE group_type SET form_name = 'contract-loan-realestate-modernization' WHERE form_name = 'contract-loan-credit-creditcontract';

-- Update 137: motocross -> mobility-passengervehicle-motorcycle-motocross
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-motocross' WHERE form_name = 'motocross';

-- Update 138: motorboat -> mobility-boat-motorized-motorboat
UPDATE group_type SET form_name = 'mobility-boat-motorized-motorboat' WHERE form_name = 'motorboat';

-- Update 139: contract-loan-credit-creditcontract -> contract-credit-car-boat
UPDATE group_type SET form_name = 'contract-credit-car-boat' WHERE form_name = 'contract-loan-credit-creditcontract';

-- Update 140: contract-sale-sale-salecontract -> contract-purchasecontract-car-motorcycle
UPDATE group_type SET form_name = 'contract-purchasecontract-car-motorcycle' WHERE form_name = 'contract-sale-sale-salecontract';

-- Update 141: contract-insurance-automobile-automobilecontract -> contract-insurance-car-motorcycle
UPDATE group_type SET form_name = 'contract-insurance-car-motorcycle' WHERE form_name = 'contract-insurance-automobile-automobilecontract';

-- Update 142: motorsailer -> mobility-boat-hybrid-motorglider
UPDATE group_type SET form_name = 'mobility-boat-hybrid-motorglider' WHERE form_name = 'motorsailer';

-- Update 143: pedelec -> mobility-bike-pedelec-pmtb
UPDATE group_type SET form_name = 'mobility-bike-pedelec-pmtb' WHERE form_name = 'pedelec';

-- Update 144: ebike -> mobility-bike-ebike-emtb
UPDATE group_type SET form_name = 'mobility-bike-ebike-emtb' WHERE form_name = 'ebike';

-- Update 145: bicycle -> mobility-bike-bike-mtb
UPDATE group_type SET form_name = 'mobility-bike-bike-mtb' WHERE form_name = 'bicycle';

-- Update 146: contract-media-media-mediacontract -> contract-media-streaming-music
UPDATE group_type SET form_name = 'contract-media-streaming-music' WHERE form_name = 'contract-media-media-mediacontract';

-- Update 147: naked -> mobility-passengervehicle-motorcycle-naked
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-naked' WHERE form_name = 'naked';

-- Update 148: contract-service-notary-notarycontract -> contract-service-service-notary
UPDATE group_type SET form_name = 'contract-service-service-notary' WHERE form_name = 'contract-service-notary-notarycontract';

-- Update 149: quad -> mobility-passengervehicle-quad-quadoffroad
UPDATE group_type SET form_name = 'mobility-passengervehicle-quad-quadoffroad' WHERE form_name = 'quad';

-- Update 150: energy -> contract-energy-heating-oil
UPDATE group_type SET form_name = 'contract-energy-heating-oil' WHERE form_name = 'energy';

-- Update 151: contract-propertymanagement-propertymanagement-propertymanagementcontract -> financeaccount-credit-creditcard-onlinevirtual
UPDATE group_type SET form_name = 'financeaccount-credit-creditcard-onlinevirtual' WHERE form_name = 'contract-propertymanagement-propertymanagement-propertymanagementcontract';

-- Update 152: parking-house -> realestate-withdevelopment-parkingspace-parkinghouse
UPDATE group_type SET form_name = 'realestate-withdevelopment-parkingspace-parkinghouse' WHERE form_name = 'parking-house';

-- Update 153: contract-lease-lease-leasecontract -> contract-rentalcontract-putdownspace-parking
UPDATE group_type SET form_name = 'contract-rentalcontract-putdownspace-parking' WHERE form_name = 'contract-lease-lease-leasecontract';

-- Update 154: parking-space -> realestate-withdevelopment-parkingspace-parkingspace
UPDATE group_type SET form_name = 'realestate-withdevelopment-parkingspace-parkingspace' WHERE form_name = 'parking-space';

-- Update 155: contract-loan-credit-creditcontract -> contract-loan-realestate-participatoryloans
UPDATE group_type SET form_name = 'contract-loan-realestate-participatoryloans' WHERE form_name = 'contract-loan-credit-creditcontract';

-- Update 156: contract-rentalmanagement-rentalmanagement-rentalmanagementcontract -> financeaccount-systems-paymentsystems-paypal
UPDATE group_type SET form_name = 'financeaccount-systems-paymentsystems-paypal' WHERE form_name = 'contract-rentalmanagement-rentalmanagement-rentalmanagementcontract';

-- Update 157: commercial-residential-property -> realestate-withdevelopment-residentialbuilding-carefacility
UPDATE group_type SET form_name = 'realestate-withdevelopment-residentialbuilding-carefacility' WHERE form_name = 'commercial-residential-property';

-- Update 158: contract-insurance-longtermcaredaily-longtermcaredailycontract -> contract-insurance-life-dailycareallowance
UPDATE group_type SET form_name = 'contract-insurance-life-dailycareallowance' WHERE form_name = 'contract-insurance-longtermcaredaily-longtermcaredailycontract';

-- Update 159: contract-insurance-longtermcare-longtermcarecontract -> contract-insurance-life-careinsurance
UPDATE group_type SET form_name = 'contract-insurance-life-careinsurance' WHERE form_name = 'contract-insurance-longtermcare-longtermcarecontract';

-- Update 160: contract-insurance-photovoltaic-photovoltaiccontract -> contract-insurance-object-photovoltaic
UPDATE group_type SET form_name = 'contract-insurance-object-photovoltaic' WHERE form_name = 'contract-insurance-photovoltaic-photovoltaiccontract';

-- Update 161: car -> mobility-passengervehicle-car-pickup
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-pickup' WHERE form_name = 'car';

-- Update 162: quad -> mobility-passengervehicle-quad-quadpocket
UPDATE group_type SET form_name = 'mobility-passengervehicle-quad-quadpocket' WHERE form_name = 'quad';

-- Update 163: employment-contract -> contract-employmentcontract-employee-internship
UPDATE group_type SET form_name = 'contract-employmentcontract-employee-internship' WHERE form_name = 'employment-contract';

-- Update 164: employment-contract -> contract-employmentcontract-official-internship
UPDATE group_type SET form_name = 'contract-employmentcontract-official-internship' WHERE form_name = 'employment-contract';

-- Update 165: employment-contract -> contract-employmentcontract-publicservice-internship
UPDATE group_type SET form_name = 'contract-employmentcontract-publicservice-internship' WHERE form_name = 'employment-contract';

-- Update 166: contract-creditcard-credit-creditcardcontract -> financeaccount-credit-creditcard-prepaid
UPDATE group_type SET form_name = 'financeaccount-credit-creditcard-prepaid' WHERE form_name = 'contract-creditcard-credit-creditcardcontract';

-- Update 167: contract-insurance-liability-liabilitycontract -> contract-insurance-object-personalliability
UPDATE group_type SET form_name = 'contract-insurance-object-personalliability' WHERE form_name = 'contract-insurance-liability-liabilitycontract';

-- Update 168: contract-insurance-health-prisickfullcontract -> contract-insurance-health-additionalinsurancefull
UPDATE group_type SET form_name = 'contract-insurance-health-additionalinsurancefull' WHERE form_name = 'contract-insurance-health-prisickfullcontract';

-- Update 169: contract-insurance-priaddout-priaddoutcontract -> contract-insurance-health-additionalinsuranceoutpatient
UPDATE group_type SET form_name = 'contract-insurance-health-additionalinsuranceoutpatient' WHERE form_name = 'contract-insurance-priaddout-priaddoutcontract';

-- Update 170: contract-insurance-priaddstat-priaddstatcontract -> contract-insurance-health-additionalinsurancestationary
UPDATE group_type SET form_name = 'contract-insurance-health-additionalinsurancestationary' WHERE form_name = 'contract-insurance-priaddstat-priaddstatcontract';

-- Update 171: contract-insurance-health-prisickfullcontract -> contract-insurance-health-additionalinsuranceteeth
UPDATE group_type SET form_name = 'contract-insurance-health-additionalinsuranceteeth' WHERE form_name = 'contract-insurance-health-prisickfullcontract';

-- Update 172: contract-lease-lease-leasecontract -> contract-rentalcontract-realestate-room
UPDATE group_type SET form_name = 'contract-rentalcontract-realestate-room' WHERE form_name = 'contract-lease-lease-leasecontract';

-- Update 173: contract-insurance-legalprotection-legalprotectioncontract -> contract-insurance-object-legalprotection
UPDATE group_type SET form_name = 'contract-insurance-object-legalprotection' WHERE form_name = 'contract-insurance-legalprotection-legalprotectioncontract';

-- Update 174: contract-insurance-legalprotection-legalprotectioncontract -> contract-insurance-business-legalprotection
UPDATE group_type SET form_name = 'contract-insurance-business-legalprotection' WHERE form_name = 'contract-insurance-legalprotection-legalprotectioncontract';

-- Update 175: commercial-residential-property -> realestate-withdevelopment-residentialbuilding-terracedendhouse
UPDATE group_type SET form_name = 'realestate-withdevelopment-residentialbuilding-terracedendhouse' WHERE form_name = 'commercial-residential-property';

-- Update 176: commercial-residential-property -> realestate-withdevelopment-residentialbuilding-terracedmidhouse
UPDATE group_type SET form_name = 'realestate-withdevelopment-residentialbuilding-terracedmidhouse' WHERE form_name = 'commercial-residential-property';

-- Update 177: contract-service-cleaning-cleaningcontract -> contract-service-service-cleaning
UPDATE group_type SET form_name = 'contract-service-service-cleaning' WHERE form_name = 'contract-service-cleaning-cleaningcontract';

-- Update 178: quad -> mobility-passengervehicle-quad-quadrace
UPDATE group_type SET form_name = 'mobility-passengervehicle-quad-quadrace' WHERE form_name = 'quad';

-- Update 179: ebike -> mobility-bike-ebike-eracingbike
UPDATE group_type SET form_name = 'mobility-bike-ebike-eracingbike' WHERE form_name = 'ebike';

-- Update 180: bicycle -> mobility-bike-bike-racingbike
UPDATE group_type SET form_name = 'mobility-bike-bike-racingbike' WHERE form_name = 'bicycle';

-- Update 181: pedelec -> mobility-bike-pedelec-pracingbike
UPDATE group_type SET form_name = 'mobility-bike-pedelec-pracingbike' WHERE form_name = 'pedelec';

-- Update 182: contract-insurance-pensionfund-pensionfundcontract -> contract-insurance-life-pensioninsurancefund
UPDATE group_type SET form_name = 'contract-insurance-life-pensioninsurancefund' WHERE form_name = 'contract-insurance-pensionfund-pensionfundcontract';

-- Update 183: contract-insurance-classicpension-classicpensioncontract -> contract-insurance-life-pensioninsurance
UPDATE group_type SET form_name = 'contract-insurance-life-pensioninsurance' WHERE form_name = 'contract-insurance-classicpension-classicpensioncontract';

-- Update 184: contract-creditcard-credit-creditcardcontract -> financeaccount-credit-creditcard-revolving
UPDATE group_type SET form_name = 'financeaccount-credit-creditcard-revolving' WHERE form_name = 'contract-creditcard-credit-creditcardcontract';

-- Update 185: contract-insurance-risklife-risklifecontract -> contract-insurance-life-risklifeinsurance
UPDATE group_type SET form_name = 'contract-insurance-life-risklifeinsurance' WHERE form_name = 'contract-insurance-risklife-risklifecontract';

-- Update 186: quad -> mobility-passengervehicle-quad-quadroad
UPDATE group_type SET form_name = 'mobility-passengervehicle-quad-quadroad' WHERE form_name = 'quad';

-- Update 187: car -> mobility-passengervehicle-car-roadstar
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-roadstar' WHERE form_name = 'car';

-- Update 188: contract-loan-credit-creditcontract -> contract-loan-realestate-rollingmarketloans
UPDATE group_type SET form_name = 'contract-loan-realestate-rollingmarketloans' WHERE form_name = 'contract-loan-credit-creditcontract';

-- Update 189: contract-creditcard-credit-creditcard -> financeaccount-credit-creditcard-samsung
UPDATE group_type SET form_name = 'financeaccount-credit-creditcard-samsung' WHERE form_name = 'contract-creditcard-credit-creditcard';

-- Update 190: contract-media-media-mediacontract -> contract-media-television-satellitetv
UPDATE group_type SET form_name = 'contract-media-television-satellitetv' WHERE form_name = 'contract-media-media-mediacontract';

-- Update 191: chimney-sweeper -> contract-service-maintenance-chimneysweeper
UPDATE group_type SET form_name = 'contract-service-maintenance-chimneysweeper' WHERE form_name = 'chimney-sweeper';

-- Update 192: allotment-garden -> realestate-withdevelopment-garden-allotmentgarden
UPDATE group_type SET form_name = 'realestate-withdevelopment-garden-allotmentgarden' WHERE form_name = 'allotment-garden';

-- Update 193: sailboat -> mobility-boat-sailing-sailingboat
UPDATE group_type SET form_name = 'mobility-boat-sailing-sailingboat' WHERE form_name = 'sailboat';

-- Update 194: sailboat -> mobility-boat-sailing-sailingyacht
UPDATE group_type SET form_name = 'mobility-boat-sailing-sailingyacht' WHERE form_name = 'sailboat';

-- Update 195: quad -> mobility-passengervehicle-quad-quadsidebyside
UPDATE group_type SET form_name = 'mobility-passengervehicle-quad-quadsidebyside' WHERE form_name = 'quad';

-- Update 196: contract-creditcard-credit-creditcard -> financeaccount-systems-paymentsystems-skrill
UPDATE group_type SET form_name = 'financeaccount-systems-paymentsystems-skrill' WHERE form_name = 'contract-creditcard-credit-creditcard';

-- Update 197: contract-savings-savings-savingscontract -> financeaccount-bank-bankaccount-savingaccount
UPDATE group_type SET form_name = 'financeaccount-bank-bankaccount-savingaccount' WHERE form_name = 'contract-savings-savings-savingscontract';

-- Update 198: bicycle -> mobility-bike-bike-speedbike
UPDATE group_type SET form_name = 'mobility-bike-bike-speedbike' WHERE form_name = 'bicycle';

-- Update 199: ebike -> mobility-bike-ebike-espeedbike
UPDATE group_type SET form_name = 'mobility-bike-ebike-espeedbike' WHERE form_name = 'ebike';

-- Update 200: pedelec -> mobility-bike-pedelec-pspeedbike
UPDATE group_type SET form_name = 'mobility-bike-pedelec-pspeedbike' WHERE form_name = 'pedelec';

-- Update 201: caravan -> mobility-commercialvehicle-caravan-sport
UPDATE group_type SET form_name = 'mobility-commercialvehicle-caravan-sport' WHERE form_name = 'caravan';

-- Update 202: motorboat -> mobility-boat-motorized-motorboat
UPDATE group_type SET form_name = 'mobility-boat-motorized-motorboat' WHERE form_name = 'motorboat';

-- Update 203: srambler -> mobility-passengervehicle-motorcycle-srambler
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-srambler' WHERE form_name = 'srambler';

-- Update 204: streetfighter -> mobility-passengervehicle-motorcycle-streetfighter
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-streetfighter' WHERE form_name = 'streetfighter';

-- Update 205: contract-creditcard-credit-creditcard -> financeaccount-systems-paymentsystems-stripe
UPDATE group_type SET form_name = 'financeaccount-systems-paymentsystems-stripe' WHERE form_name = 'contract-creditcard-credit-creditcard';

-- Update 206: energy -> contract-energy-photovoltaic-electricity
UPDATE group_type SET form_name = 'contract-energy-photovoltaic-electricity' WHERE form_name = 'energy';

-- Update 207: energy-electric-strom -> contract-energy-electric-electricity
UPDATE group_type SET form_name = 'contract-energy-electric-electricity' WHERE form_name = 'energy-electric-strom';

-- Update 208: stuntbike -> mobility-passengervehicle-motorcycle-stuntbike
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-stuntbike' WHERE form_name = 'stuntbike';

-- Update 209: bicycle -> mobility-bike-bike-suv
UPDATE group_type SET form_name = 'mobility-bike-bike-suv' WHERE form_name = 'bicycle';

-- Update 210: ebike -> mobility-bike-ebike-esuv
UPDATE group_type SET form_name = 'mobility-bike-ebike-esuv' WHERE form_name = 'ebike';

-- Update 211: pedelec -> mobility-bike-pedelec-psuv
UPDATE group_type SET form_name = 'mobility-bike-pedelec-psuv' WHERE form_name = 'pedelec';

-- Update 212: car -> mobility-passengervehicle-car-suv
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-suv' WHERE form_name = 'car';

-- Update 213: contract-media-media-mediacontract -> contract-media-cellular-tablet
UPDATE group_type SET form_name = 'contract-media-cellular-tablet' WHERE form_name = 'contract-media-media-mediacontract';

-- Update 214: camper -> mobility-passengervehicle-camper-semiintegrated
UPDATE group_type SET form_name = 'mobility-passengervehicle-camper-semiintegrated' WHERE form_name = 'camper';

-- Update 215: employment-contract -> contract-employmentcontract-official-halftime
UPDATE group_type SET form_name = 'contract-employmentcontract-official-halftime' WHERE form_name = 'employment-contract';

-- Update 216: employment-contract -> contract-employmentcontract-publicservice-halftime
UPDATE group_type SET form_name = 'contract-employmentcontract-publicservice-halftime' WHERE form_name = 'employment-contract';

-- Update 217: employment-contract -> contract-employmentcontract-employee-halftime
UPDATE group_type SET form_name = 'contract-employmentcontract-employee-halftime' WHERE form_name = 'employment-contract';

-- Update 218: contract-insurance-liabilitypet-liabilitypetcontract -> contract-insurance-object-animalownerliability
UPDATE group_type SET form_name = 'contract-insurance-object-animalownerliability' WHERE form_name = 'contract-insurance-liabilitypet-liabilitypetcontract';

-- Update 219: contract-insurance-pet-petcontract -> contract-insurance-object-animalsurgery
UPDATE group_type SET form_name = 'contract-insurance-object-animalsurgery' WHERE form_name = 'contract-insurance-pet-petcontract';

-- Update 220: contract-loan-credit-creditcontract -> contract-loan-realestate-amortizationloan
UPDATE group_type SET form_name = 'contract-loan-realestate-amortizationloan' WHERE form_name = 'contract-loan-credit-creditcontract';

-- Update 221: contract-sale-sale-salecontract -> contract-purchasecontract-objects-table
UPDATE group_type SET form_name = 'contract-purchasecontract-objects-table' WHERE form_name = 'contract-sale-sale-salecontract';

-- Update 222: tractor -> mobility-agriculture-tractor-tractor
UPDATE group_type SET form_name = 'mobility-agriculture-tractor-tractor' WHERE form_name = 'tractor';

-- Update 223: contract-insurance-automobile-automobilecontract -> contract-insurance-car-tractor
UPDATE group_type SET form_name = 'contract-insurance-car-tractor' WHERE form_name = 'contract-insurance-automobile-automobilecontract';

-- Update 224: car -> mobility-passengervehicle-car-transporter
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-transporter' WHERE form_name = 'car';

-- Update 225: bicycle -> mobility-bike-bike-trekkingbike
UPDATE group_type SET form_name = 'mobility-bike-bike-trekkingbike' WHERE form_name = 'bicycle';

-- Update 226: ebike -> mobility-bike-ebike-etrekkingbike
UPDATE group_type SET form_name = 'mobility-bike-ebike-etrekkingbike' WHERE form_name = 'ebike';

-- Update 227: pedelec -> mobility-bike-pedelec-ptrekkingbike
UPDATE group_type SET form_name = 'mobility-bike-pedelec-ptrekkingbike' WHERE form_name = 'pedelec';

-- Update 228: trial -> mobility-passengervehicle-motorcycle-trial
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-trial' WHERE form_name = 'trial';

-- Update 229: contract-loan-credit-creditcontract -> contract-credit-credit-rescheduling
UPDATE group_type SET form_name = 'contract-credit-credit-rescheduling' WHERE form_name = 'contract-loan-credit-creditcontract';

-- Update 230: contract-insurance-environmentaldamage-environmentaldamagecontract -> contract-insurance-object-environmentaldamage
UPDATE group_type SET form_name = 'contract-insurance-object-environmentaldamage' WHERE form_name = 'contract-insurance-environmentaldamage-environmentaldamagecontract';

-- Update 231: contract-insurance-accident-accidentcontract -> contract-insurance-object-accident
UPDATE group_type SET form_name = 'contract-insurance-object-accident' WHERE form_name = 'contract-insurance-accident-accidentcontract';

-- Update 232: car -> mobility-passengervehicle-car-van
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-van' WHERE form_name = 'car';

-- Update 233: contract-insurance-broker-brokercontract -> contract-service-service-insurancebroker
UPDATE group_type SET form_name = 'contract-service-service-insurancebroker' WHERE form_name = 'contract-insurance-broker-brokercontract';

-- Update 234: camper -> mobility-passengervehicle-camper-fullyintegrated
UPDATE group_type SET form_name = 'mobility-passengervehicle-camper-fullyintegrated' WHERE form_name = 'camper';

-- Update 235: employment-contract -> contract-employmentcontract-employee-fulltime
UPDATE group_type SET form_name = 'contract-employmentcontract-employee-fulltime' WHERE form_name = 'employment-contract';

-- Update 236: employment-contract -> contract-employmentcontract-official-fulltime
UPDATE group_type SET form_name = 'contract-employmentcontract-official-fulltime' WHERE form_name = 'employment-contract';

-- Update 237: employment-contract -> contract-employmentcontract-publicservice-fulltime
UPDATE group_type SET form_name = 'contract-employmentcontract-publicservice-fulltime' WHERE form_name = 'employment-contract';

-- Update 238: contract-service-maintenance-maintenancecontract -> contract-service-maintenance-heatpump
UPDATE group_type SET form_name = 'contract-service-maintenance-heatpump' WHERE form_name = 'contract-service-maintenance-maintenancecontract';

-- Update 239: pasture -> realestate-withdevelopment-farmland-pasture
UPDATE group_type SET form_name = 'realestate-withdevelopment-farmland-pasture' WHERE form_name = 'pasture';

-- Update 240: employment-contract -> contract-employmentcontract-employee-workcontract
UPDATE group_type SET form_name = 'contract-employmentcontract-employee-workcontract' WHERE form_name = 'employment-contract';

-- Update 241: employment-contract -> contract-employmentcontract-publicservice-workcontract
UPDATE group_type SET form_name = 'contract-employmentcontract-publicservice-workcontract' WHERE form_name = 'employment-contract';

-- Update 242: employment-contract -> contract-employmentcontract-official-workcontract
UPDATE group_type SET form_name = 'contract-employmentcontract-official-workcontract' WHERE form_name = 'employment-contract';

-- Update 243: meadow -> realestate-withoutdevelopment-ground-meadow
UPDATE group_type SET form_name = 'realestate-withoutdevelopment-ground-meadow' WHERE form_name = 'meadow';

-- Update 244: contract-insurance-residential-residentialcontract -> contract-insurance-object-residentialbuilding
UPDATE group_type SET form_name = 'contract-insurance-object-residentialbuilding' WHERE form_name = 'contract-insurance-residential-residentialcontract';

-- Update 245: contract-insurance-automobile-automobilecontract -> contract-insurance-car-camper
UPDATE group_type SET form_name = 'contract-insurance-car-camper' WHERE form_name = 'contract-insurance-automobile-automobilecontract';

-- Update 246: contract-lease-lease-leasecontract -> contract-rentalcontract-car-camper
UPDATE group_type SET form_name = 'contract-rentalcontract-car-camper' WHERE form_name = 'contract-lease-lease-leasecontract';

-- Update 247: contract-sale-sale-salecontract -> contract-purchasecontract-realestate-apartment
UPDATE group_type SET form_name = 'contract-purchasecontract-realestate-apartment' WHERE form_name = 'contract-sale-sale-salecontract';

-- Update 248: commercial-residential-property -> realestate-withdevelopment-commercialresidentialbuilding-apartmentscommercialmix
UPDATE group_type SET form_name = 'realestate-withdevelopment-commercialresidentialbuilding-apartmentscommercialmix' WHERE form_name = 'commercial-residential-property';

-- Update 249: contract-lease-lease-leasecontract -> contract-rentalcontract-reside-apartmentrentalagreement
UPDATE group_type SET form_name = 'contract-rentalcontract-reside-apartmentrentalagreement' WHERE form_name = 'contract-lease-lease-leasecontract';

-- Update 250: contract-loan-credit-creditcontract -> contract-credit-car-caravan
UPDATE group_type SET form_name = 'contract-credit-car-caravan' WHERE form_name = 'contract-loan-credit-creditcontract';

-- Update 251: contract-insurance-automobile-automobilecontract -> contract-insurance-car-caravan
UPDATE group_type SET form_name = 'contract-insurance-car-caravan' WHERE form_name = 'contract-insurance-automobile-automobilecontract';

-- Update 252: pedelec -> mobility-bike-pedelec-pxxl
UPDATE group_type SET form_name = 'mobility-bike-pedelec-pxxl' WHERE form_name = 'pedelec';

-- Update 253: ebike -> mobility-bike-ebike-exxl
UPDATE group_type SET form_name = 'mobility-bike-ebike-exxl' WHERE form_name = 'ebike';

-- Update 254: bicycle -> mobility-bike-bike-xxl
UPDATE group_type SET form_name = 'mobility-bike-bike-xxl' WHERE form_name = 'bicycle';

-- Update 255: motorboat -> mobility-boat-motorized-motorboat
UPDATE group_type SET form_name = 'mobility-boat-motorized-motorboat' WHERE form_name = 'motorboat';

-- Update 256: caravan -> mobility-commercialvehicle-caravan-tent
UPDATE group_type SET form_name = 'mobility-commercialvehicle-caravan-tent' WHERE form_name = 'caravan';

-- Update 257: commercial-residential-property -> realestate-withdevelopment-residentialbuilding-twofamilyhouse
UPDATE group_type SET form_name = 'realestate-withdevelopment-residentialbuilding-twofamilyhouse' WHERE form_name = 'commercial-residential-property';

-- Update 258: caravan -> mobility-commercialvehicle-caravan-twopersons
UPDATE group_type SET form_name = 'mobility-commercialvehicle-caravan-twopersons' WHERE form_name = 'caravan';

