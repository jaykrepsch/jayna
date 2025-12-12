const fs = require('fs');
const path = require('path');

// Pfad zu den Form-Definitionen
const formDefinitionsPath = path.join(__dirname, 'src/main/webapp/app/shared/form-definitions');

// Aktuelle Mappings aus entity-helper.js (manuell extrahiert)
const currentMappings = {
  mobility: {
    'Geländewagen': 'mobility-passengervehicle-car-offroadvehicle',
    'SUV': 'mobility-passengervehicle-car-suv',
    'Van': 'mobility-passengervehicle-car-van',
    'Transporter': 'mobility-passengervehicle-car-transporter',
    'Kombi': 'mobility-passengervehicle-car-stationwagon',
    'Kleinlaster': 'mobility-passengervehicle-car-smalltransporter',
    'Kleinwagen': 'mobility-passengervehicle-car-smallcar',
    'Roadster': 'mobility-passengervehicle-car-roadstar',
    'Pickup': 'mobility-passengervehicle-car-pickup',
    'Minivan': 'mobility-passengervehicle-car-minivan',
    'Kleinbus': 'mobility-passengervehicle-car-minibus',
    'Limousine': 'mobility-passengervehicle-car-limousine',
    'CUV': 'mobility-passengervehicle-car-cuv',
    'Coupé': 'mobility-passengervehicle-car-coupe',
    'Cabrio': 'mobility-passengervehicle-car-convertible',
    'Auto': 'mobility-passengervehicle-car-car',
    'Trial': 'mobility-passengervehicle-motorcycle-trial',
    'Stuntbike': 'mobility-passengervehicle-motorcycle-stuntbike',
    'Streetfighter': 'mobility-passengervehicle-motorcycle-streetfighter',
    'Scrambler': 'mobility-passengervehicle-motorcycle-srambler',
    'Naked Bike': 'mobility-passengervehicle-motorcycle-naked',
    'Motocross': 'mobility-passengervehicle-motorcycle-motocross',
    'Dirtbike': 'mobility-passengervehicle-motorcycle-dirtbike',
    'Cruiser': 'mobility-passengervehicle-motorcycle-cruiser',
    'Chopper': 'mobility-passengervehicle-motorcycle-chopper',
    'Side-by-Side': 'mobility-passengervehicle-quad-quadsidebyside',
    'Road Quad': 'mobility-passengervehicle-quad-quadroad',
    'Race Quad': 'mobility-passengervehicle-quad-quadrace',
    'Pocket Quad': 'mobility-passengervehicle-quad-quadpocket',
    'Offroad Quad': 'mobility-passengervehicle-quad-quadoffroad',
    'Electro Quad': 'mobility-passengervehicle-quad-quadelectro',
    'Children Quad': 'mobility-passengervehicle-quad-quadchildren',
    'Quad C': 'mobility-passengervehicle-quad-quadc',
    '4x4 Quad': 'mobility-passengervehicle-quad-quad4x4',
    'Teilintegriert': 'mobility-passengervehicle-camper-semiintegrated',
    'Vollintegriert': 'mobility-passengervehicle-camper-fullyintegrated',
    'Alkoven': 'mobility-passengervehicle-camper-alkoven',
    'Anhänger C': 'mobility-commercialvehicle-trailer-c',
    'Anhänger B': 'mobility-commercialvehicle-trailer-b',
    'Anhänger A': 'mobility-commercialvehicle-trailer-a',
    '2-Personen': 'mobility-commercialvehicle-caravan-twopersons',
    'Zelt': 'mobility-commercialvehicle-caravan-tent',
    'Sport': 'mobility-commercialvehicle-caravan-sport',
    'Luxus': 'mobility-commercialvehicle-caravan-luxury',
    'Familie': 'mobility-commercialvehicle-caravan-family',
    'Kastenwagen': 'mobility-commercialvehicle-box-boxvan',
    'Segelyacht': 'mobility-boat-sailing-sailingyacht',
    'Segelboot': 'mobility-boat-sailing-sailingboat',
    'Motorboot': 'mobility-boat-motorized-motorboat',
    'Motorsegler': 'mobility-boat-hybrid-motorglider',
    'XXL Pedelec': 'mobility-bike-pedelec-pxxl',
    'Dreirad Pedelec': 'mobility-bike-pedelec-ptricycle',
    'Trekking Pedelec': 'mobility-bike-pedelec-ptrekkingbike',
    'SUV Pedelec': 'mobility-bike-pedelec-psuv',
    'Speed Pedelec': 'mobility-bike-pedelec-pspeedbike',
    'Rennrad Pedelec': 'mobility-bike-pedelec-pracingbike',
    'MTB Pedelec': 'mobility-bike-pedelec-pmtb',
    'Lifestyle Pedelec': 'mobility-bike-pedelec-plifestyle',
    'Gravel Pedelec': 'mobility-bike-pedelec-pgravel',
    'Faltrad Pedelec': 'mobility-bike-pedelec-pfoldingbike',
    'Cross Pedelec': 'mobility-bike-pedelec-pcross',
    'City Pedelec': 'mobility-bike-pedelec-pcity',
    'Cargo Pedelec': 'mobility-bike-pedelec-pcargo',
    'XXL E-Bike': 'mobility-bike-ebike-exxl',
    'Dreirad E-Bike': 'mobility-bike-ebike-etricycle',
    'Trekking E-Bike': 'mobility-bike-ebike-etrekkingbike',
    'SUV E-Bike': 'mobility-bike-ebike-esuv',
    'Speed E-Bike': 'mobility-bike-ebike-espeedbike',
    'Rennrad E-Bike': 'mobility-bike-ebike-eracingbike',
    'MTB E-Bike': 'mobility-bike-ebike-emtb',
    'Lifestyle E-Bike': 'mobility-bike-ebike-elifestyle',
    'Gravel E-Bike': 'mobility-bike-ebike-egravel',
    'Faltrad E-Bike': 'mobility-bike-ebike-efoldingbike',
    'Cross E-Bike': 'mobility-bike-ebike-ecross',
    'City E-Bike': 'mobility-bike-ebike-ecity',
    'Cargo E-Bike': 'mobility-bike-ebike-ecargo',
    'XXL Fahrrad': 'mobility-bike-bike-xxl',
    'Dreirad': 'mobility-bike-bike-tricycle',
    'Trekkingrad': 'mobility-bike-bike-trekkingbike',
    'SUV Fahrrad': 'mobility-bike-bike-suv',
    'Speedbike': 'mobility-bike-bike-speedbike',
    'Rennrad': 'mobility-bike-bike-racingbike',
    'MTB': 'mobility-bike-bike-mtb',
    'Lifestyle Fahrrad': 'mobility-bike-bike-lifestyle',
    'Gravel Bike': 'mobility-bike-bike-gravel',
    'Faltrad': 'mobility-bike-bike-foldingbike',
    'Cross Bike': 'mobility-bike-bike-cross',
    'City Bike': 'mobility-bike-bike-city',
    'Cargo Bike': 'mobility-bike-bike-cargo',
    'Traktor': 'mobility-agriculture-tractor-tractor'
  },
  realestate: {
    'Forst': 'realestate-withdevelopment-farmland-forest',
    'Wiese': 'realestate-withdevelopment-farmland-pasture',
    'Weide': 'realestate-withdevelopment-farmland-pasture',
    'Obst': 'realestate-withdevelopment-farmland-fruit',
    'Ackerland': 'realestate-withdevelopment-farmland-farmland',
    'Garten': 'realestate-withdevelopment-garden-allotmentgarden',
    'Halle': 'realestate-withdevelopment-hall-hall',
    'Carport': 'realestate-withdevelopment-parkingspace-carport',
    'Garage': 'realestate-withdevelopment-parkingspace-garage',
    'Parkhaus': 'realestate-withdevelopment-parkingspace-parkinghouse',
    'Parkplatz': 'realestate-withdevelopment-parkingspace-parkingspace',
    'Wohnung': 'realestate-withdevelopment-residentialbuilding-apartmentbuilding',
    'Pflegeheim': 'realestate-withdevelopment-residentialbuilding-carefacility',
    'Einfamilienhaus': 'realestate-withdevelopment-residentialbuilding-detachedhouse',
    'Doppelhaushälfte': 'realestate-withdevelopment-residentialbuilding-semitdetachedhouse',
    'Reihenendhaus': 'realestate-withdevelopment-residentialbuilding-terracedendhouse',
    'Reihenmittelhaus': 'realestate-withdevelopment-residentialbuilding-terracedmidhouse',
    'Zweifamilienhaus': 'realestate-withdevelopment-residentialbuilding-twofamilyhouse',
    'Ferienhaus': 'realestate-withdevelopment-residentialbuilding-vacationhouse',
    'Gewerbeeinheit': 'realestate-withdevelopment-commercialbuilding-commercialunit',
    'Gewerbegebäude': 'realestate-withdevelopment-commercialbuilding-commercialbuilding',
    'Wohnung mit Gewerbe': 'realestate-withdevelopment-commercialresidentialbuilding-apartmentscommercialmix',
    'Bauplatz': 'realestate-withoutdevelopment-ground-buildingplot',
    'Wiese (ohne Bebauung)': 'realestate-withoutdevelopment-ground-meadow'
  },
  financeaccount: {
    'Girokonto': 'financeaccount-bank-bankaccount-checkingaccount',
    'Sparkonto': 'financeaccount-bank-bankaccount-savingaccount',
    'Charge Card': 'financeaccount-credit-creditcard-charge',
    'Debit Card': 'financeaccount-credit-creditcard-debit',
    'Prepaid Card': 'financeaccount-credit-creditcard-prepaid',
    'Revolving Card': 'financeaccount-credit-creditcard-revolving',
    'Online Virtual Card': 'financeaccount-credit-creditcard-onlinevirtual',
    'Samsung Card': 'financeaccount-credit-creditcard-samsung',
    'Apple Pay': 'financeaccount-systems-paymentsystems-applepay',
    'Google Pay': 'financeaccount-systems-paymentsystems-googlepay',
    'Klarna': 'financeaccount-systems-paymentsystems-klarna',
    'PayPal': 'financeaccount-systems-paymentsystems-paypal',
    'Skrill': 'financeaccount-systems-paymentsystems-skrill',
    'Stripe': 'financeaccount-systems-paymentsystems-stripe'
  },
  contract: {
    // Service Contracts
    'Mietverwaltung': 'contract-service-service-rentalmanagement',
    'Immobilienmakler': 'contract-service-service-realestateagent',
    'Hausverwaltung': 'contract-service-service-propertymanagement',
    'Notar': 'contract-service-service-notary',
    'Versicherungsmakler': 'contract-service-service-insurancebroker',
    'Gärtner': 'contract-service-service-gardener',
    'Reinigung': 'contract-service-service-cleaning',
    'Fahrzeugprüfung': 'contract-service-service-carinspection',
    'Hausmeisterdienst': 'contract-service-service-caretakerservice',
    
    // Maintenance Contracts
    'Wärmepumpe': 'contract-service-maintenance-heatpump',
    'Heizung': 'contract-service-maintenance-heating',
    'Aufzug': 'contract-service-maintenance-elevator',
    'Schornsteinfeger': 'contract-service-maintenance-chimneysweeper',
    
    // Fee Contracts
    'Grundsteuer': 'contract-service-fees-realestatetax',
    'Gemeindesteuern': 'contract-service-fees-municipalfees',
    
    // Rental Management
    'Mietverwaltung': 'contract-rentalmanagement-rentalmanagement-rentalmanagement',
    
    // Rental Contracts
    'Hausmietvertrag': 'contract-rentalcontract-reside-houselease',
    'Wohnungsmietvertrag': 'contract-rentalcontract-reside-apartmentrentalagreement',
    'Zimmer': 'contract-rentalcontract-realestate-room',
    'Ferienhaus': 'contract-rentalcontract-realestate-holidayhouse',
    'Ferienwohnung': 'contract-rentalcontract-realestate-holidayapartment',
    'Gebäude': 'contract-rentalcontract-realestate-building',
    'Parkplatz': 'contract-rentalcontract-putdownspace-parking',
    'Garage': 'contract-rentalcontract-putdownspace-garage',
    'Auto': 'contract-rentalcontract-car-car',
    'Wohnmobil': 'contract-rentalcontract-car-camper',
    
    // Purchase Contracts
    'Grundsteuer Kauf': 'contract-purchase-realestate-realestatetax',
    'Haus': 'contract-purchase-realestate-house',
    'Wohnung': 'contract-purchase-realestate-apartment',
    'Tisch': 'contract-purchase-objects-table',
    'TV': 'contract-purchase-devices-tv',
    'Handy': 'contract-purchase-devices-mobile',
    'Motorrad': 'contract-purchase-car-motorcycle',
    'Auto Kauf': 'contract-purchase-car-car',
    
    // Property Management
    'Hausverwaltung': 'contract-propertymanagement-propertymanagement-propertymanagement',
    
    // Media Contracts
    'Satelliten-TV': 'contract-media-television-satellitetv',
    'Kabel-TV': 'contract-media-television-cabletv',
    'Musik Streaming': 'contract-media-streaming-music',
    'Film/Serien Streaming': 'contract-media-streaming-moviesseries',
    'Glasfaser': 'contract-media-internet-glassfiber',
    'Breitband': 'contract-media-internet-broadband',
    'Tablet': 'contract-media-cellular-tablet',
    'Handy': 'contract-media-cellular-mobile',
    'Autotelefon': 'contract-media-cellular-carphone',
    
    // Loan Contracts
    'Studentendarlehen': 'contract-loan-support-studentloans',
    'Festdarlehen': 'contract-loan-realestate-termloan',
    'Rollende Marktdarlehen': 'contract-loan-realestate-rollingmarketloans',
    'Partizipationsdarlehen': 'contract-loan-realestate-participatoryloans',
    'Modernisierungsdarlehen': 'contract-loan-realestate-modernization',
    'Kündigungsdarlehen': 'contract-loan-realestate-callloan',
    'Bullet-Darlehen': 'contract-loan-realestate-bulletloans',
    'Rentendarlehen': 'contract-loan-realestate-annuityloan',
    'Tilgungsdarlehen': 'contract-loan-realestate-amortizationloan',
    'Kreditvertrag': 'contract-loan-credit-creditcontract',
    
    // Leasing Contracts
    'Auto Leasing': 'contract-leasing-car-car',
    
    // Insurance Contracts - Object
    'Wohngebäude': 'contract-insurance-object-residentialbuilding',
    'Photovoltaik': 'contract-insurance-object-photovoltaic',
    'Privathaftpflicht': 'contract-insurance-object-personalliability',
    'Mietausfall': 'contract-insurance-object-lossofrent',
    'Rechtsschutz': 'contract-insurance-object-legalprotection',
    'Hausrat': 'contract-insurance-object-householdgoods',
    'Haus- und Grundbesitzerhaftpflicht': 'contract-insurance-object-homeownerliability',
    'Glas': 'contract-insurance-object-glas',
    'Feuer': 'contract-insurance-object-fire',
    'Umweltschaden': 'contract-insurance-object-environmentaldamage',
    'Elementar': 'contract-insurance-object-elementary',
    'E-Bike': 'contract-insurance-object-ebike',
    'Bauherrenhaftpflicht': 'contract-insurance-object-buildersliability',
    'Kunst': 'contract-insurance-object-art',
    'Tieroperation': 'contract-insurance-object-animalsurgery',
    'Tierhalterhaftpflicht': 'contract-insurance-object-animalownerliability',
    'Unfall': 'contract-insurance-object-accident',
    
    // Insurance Contracts - Life
    'Risikolebensversicherung': 'contract-insurance-life-risklifeinsurance',
    'Pensionskasse': 'contract-insurance-life-pensioninsurancefund',
    'Rentenversicherung': 'contract-insurance-life-pensioninsurance',
    'Berufsunfähigkeitsversicherung': 'contract-insurance-life-disabilityinsurance',
    'Pflegetagegeld': 'contract-insurance-life-dailycareallowance',
    'Pflegeversicherung': 'contract-insurance-life-careinsurance',
    
    // Insurance Contracts - Health
    'Gesetzliche Krankenversicherung': 'contract-insurance-health-statutoryhealthinsurance',
    'Krankengeld': 'contract-insurance-health-sickpay',
    'Krankenhaustagegeld': 'contract-insurance-health-hospitalperdiem',
    'Auslandsreise': 'contract-insurance-health-foreigntravel',
    'Zahnzusatzversicherung': 'contract-insurance-health-additionalinsuranceteeth',
    'Stationäre Zusatzversicherung': 'contract-insurance-health-additionalinsurancestationary',
    'Ambulante Zusatzversicherung': 'contract-insurance-health-additionalinsuranceoutpatient',
    'Vollzusatzversicherung': 'contract-insurance-health-additionalinsurancefull',
    'Auslandskrankenversicherung': 'contract-insurance-health-abroad',
    
    // Insurance Contracts - Car
    'Traktor': 'contract-insurance-car-tractor',
    'Motorrad': 'contract-insurance-car-motorcycle',
    'Wohnwagen': 'contract-insurance-car-caravan',
    'Wohnmobil': 'contract-insurance-car-camper',
    
    // Insurance Contracts - Business
    'Managerhaftpflicht': 'contract-insurance-business-managerliability',
    'Geschäftsrechtsschutz': 'contract-insurance-business-legalprotection',
    'Geschäftsgebäude': 'contract-insurance-business-building',
    
    // Energy Contracts
    'Photovoltaik': 'contract-energy-photovoltaic-electricity',
    'Öl': 'contract-energy-heating-oil',
    'Gas': 'contract-energy-heating-gas',
    'Fernwärme': 'contract-energy-heating-districtheating',
    'Strom': 'contract-energy-electric-electricity',
    
    // Employment Contracts
    'Öffentlicher Dienst': 'contract-employment-publicservice-workcontract',
    
    // Bank Account Contracts
    'Girokonto': 'contract-bankaccount-checkingaccount-checkingaccount',
    'Festgeldkonto': 'contract-bankaccount-fixedtermaccount-fixedtermaccount',
    'Tagesgeldkonto': 'contract-bankaccount-overnightaccount-overnightaccount',
    'Sparkonto': 'contract-bankaccount-savingsaccount-savingsaccount',
    
    // Credit Contracts
    'Bootskredit': 'contract-credit-car-boat',
    'Autokredit': 'contract-credit-car-car',
    'Wohnwagenkredit': 'contract-credit-car-caravan',
    'Frei verwendbarer Kredit': 'contract-credit-credit-freeuse',
    'Modernisierungskredit': 'contract-credit-credit-modernization',
    'Umschuldungskredit': 'contract-credit-credit-rescheduling',
    
    // Credit Card Contracts
    'Kreditkarte': 'contract-creditcard-creditcard-creditcard',
    'Debitkarte': 'contract-creditcard-debitcard-debitcard',
    
    // Employment Contracts - Employee
    'Vollzeit Angestellter': 'contract-employment-employee-fulltime',
    'Teilzeit Angestellter': 'contract-employment-employee-halftime',
    'Hilfskraft Angestellter': 'contract-employment-employee-help',
    'Praktikant Angestellter': 'contract-employment-employee-internship',
    'Minijob Angestellter': 'contract-employment-employee-minijob',
    'Kündigungsvereinbarung Angestellter': 'contract-employment-employee-terminationagreement',
    'Arbeitsvertrag Angestellter': 'contract-employment-employee-workcontract',
    
    // Employment Contracts - Official
    'Vollzeit Beamter': 'contract-employment-official-fulltime',
    'Teilzeit Beamter': 'contract-employment-official-halftime',
    'Hilfskraft Beamter': 'contract-employment-official-help',
    'Praktikant Beamter': 'contract-employment-official-internship',
    'Minijob Beamter': 'contract-employment-official-minijob',
    'Kündigungsvereinbarung Beamter': 'contract-employment-official-terminationagreement',
    'Arbeitsvertrag Beamter': 'contract-employment-official-workcontract',
    
    // Employment Contracts - Public Service
    'Vollzeit Öffentlicher Dienst': 'contract-employment-publicservice-fulltime',
    'Teilzeit Öffentlicher Dienst': 'contract-employment-publicservice-halftime',
    'Hilfskraft Öffentlicher Dienst': 'contract-employment-publicservice-help',
    'Praktikant Öffentlicher Dienst': 'contract-employment-publicservice-internship',
    'Minijob Öffentlicher Dienst': 'contract-employment-publicservice-minijob',
    'Kündigungsvereinbarung Öffentlicher Dienst': 'contract-employment-publicservice-terminationagreement',
    
    // Additional Contracts
    'Photovoltaik Versicherung': 'contract-insurance-object-photovoltaic',
    'Handy': 'contract-media-cellular-mobile',
    'Motorrad Kauf': 'contract-purchase-car-motorcycle',
    'Couchtisch': 'contract-purchase-objects-coffeetable',
    'Grundsteuer Kauf': 'contract-purchase-realestate-realestatetax',
    'Wohnmobil Miete': 'contract-rentalcontract-car-camper',
    'Mietverwaltung': 'contract-rentalmanagement-rentalmanagement-rentalmanagement',
    'Hausverwaltung': 'contract-service-service-propertymanagement'
  },
  contact: {
    'Einzelkontakt': 'contact-single-single-singlecontact',
    'Kontakt': 'contact-contact-contact-singlecontact',
    'Kontaktgruppe': 'contact-contact-contact-contactgroup'
  }
};

// Funktion zum Überprüfen der Mappings
function checkMappings() {
  console.log('🔍 Überprüfe Form-Definition Mappings...\n');

  // Alle verfügbaren Form-Definitionen lesen
  const entities = fs.readdirSync(formDefinitionsPath, { withFileTypes: true })
    .filter(dirent => dirent.isDirectory())
    .map(dirent => dirent.name);

  let totalIssues = 0;

  for (const entity of entities) {
    console.log(`📁 Entity: ${entity}`);
    
    const entityPath = path.join(formDefinitionsPath, entity);
    const files = fs.readdirSync(entityPath)
      .filter(file => file.endsWith('.json'))
      .map(file => file.replace('.json', ''));

    const entityMappings = currentMappings[entity] || {};
    const mappedFiles = Object.values(entityMappings);
    
    console.log(`   Verfügbare Dateien: ${files.length}`);
    console.log(`   Gemappte Dateien: ${mappedFiles.length}`);

    // Finde fehlende Mappings
    const unmappedFiles = files.filter(file => !mappedFiles.includes(file));
    const unmappedMappings = Object.keys(entityMappings).filter(key => !files.includes(entityMappings[key]));

    if (unmappedFiles.length > 0) {
      console.log(`   ❌ Fehlende Mappings für: ${unmappedFiles.join(', ')}`);
      totalIssues += unmappedFiles.length;
    }

    if (unmappedMappings.length > 0) {
      console.log(`   ⚠️  Ungültige Mappings: ${unmappedMappings.join(', ')}`);
      totalIssues += unmappedMappings.length;
    }

    if (unmappedFiles.length === 0 && unmappedMappings.length === 0) {
      console.log(`   ✅ Alle Mappings sind korrekt`);
    }

    console.log('');
  }

  console.log(`📊 Zusammenfassung: ${totalIssues} Probleme gefunden`);
  
  if (totalIssues === 0) {
    console.log('🎉 Alle Form-Definition Mappings sind korrekt!');
  } else {
    console.log('⚠️  Bitte überprüfen Sie die fehlenden Mappings.');
  }
}

// Skript ausführen
checkMappings(); 