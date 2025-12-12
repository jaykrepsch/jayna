const fs = require('fs');
const path = require('path');

// Mapping von alten form_name zu korrekten form_name basierend auf den Formulardefinitionen
const formNameMapping = {
  // Contract - Employment
  'employment-contract': 'contract-employment-employment-employmentcontract',
  'employment-employee-fulltime': 'contract-employment-employee-fulltime',
  'employment-employee-parttime': 'contract-employment-employee-part-time',
  'employment-employee-minijob': 'contract-employment-employee-mini-job',
  'employment-employee-internship': 'contract-employment-employee-internship',
  'employment-employee-terminationcontract': 'contract-employment-employee-terminationagreement',
  'employment-official-fulltime': 'contract-employment-official-fulltime',
  'employment-official-parttime': 'contract-employment-official-part-time',
  'employment-official-internship': 'contract-employment-official-internship',
  'employment-official-minijob': 'contract-employment-official-mini-job',
  'employment-public-service-work-contract': 'contract-employment-public-service-work-contract',
  
  // Contract - Insurance
  'insurance-automobile': 'contract-insurance-car-carcontract',
  'insurance-bike': 'contract-insurance-bike-bikecontract',
  'insurance-bicycle': 'contract-insurance-bicycle-bicyclecontract',
  'insurance-fire': 'contract-insurance-fire-firecontract',
  'insurance-furniture': 'contract-insurance-furniture-furniturecontract',
  'insurance-health': 'contract-insurance-health-healthcontract',
  'insurance-legal-protection': 'contract-insurance-legalprotection-legalprotectioncontract',
  'insurance-liability': 'contract-insurance-liability-liabilitycontract',
  'insurance-liability-pet': 'contract-insurance-liabilitypet-liabilitypetcontract',
  'insurance-residential': 'contract-insurance-residential-residentialcontract',
  'insurance-risk-life': 'contract-insurance-risklife-risklifecontract',
  'insurance-travel': 'contract-insurance-travel-travelcontract',
  'insurance-accident': 'contract-insurance-accident-accidentcontract',
  'insurance-animal-surgery': 'contract-insurance-pet-petcontract',
  'insurance-art': 'contract-insurance-art-artcontract',
  'insurance-builder-liability': 'contract-insurance-builderliability-builderliabilitycontract',
  'insurance-classic-pension': 'contract-insurance-classicpension-classicpensioncontract',
  'insurance-daily-hospital': 'contract-insurance-dailyhospital-dailyhospitalcontract',
  'insurance-daily-sickness': 'contract-insurance-dailysickness-dailysicknesscontract',
  'insurance-disability': 'contract-insurance-disability-disabilitycontract',
  'insurance-domanager': 'contract-insurance-domanager-domanagercontract',
  'insurance-environmental-damage': 'contract-insurance-environmentaldamage-environmentaldamagecontract',
  'insurance-expatriates': 'contract-insurance-expatriates-expatriatescontract',
  'insurance-int-health': 'contract-insurance-inthealth-inthealthcontract',
  'insurance-legal': 'contract-insurance-legal-legalcontract',
  'insurance-long-term-care': 'contract-insurance-longtermcare-longtermcarecontract',
  'insurance-long-term-care-daily': 'contract-insurance-longtermcaredaily-longtermcaredailycontract',
  'insurance-loss-rent': 'contract-insurance-rental-rentalcontract',
  'insurance-pensionfund': 'contract-insurance-pensionfund-pensionfundcontract',
  'insurance-photovoltaic': 'contract-insurance-photovoltaic-photovoltaiccontract',
  'insurance-pri-add-health': 'contract-insurance-priaddtooth-priaddtoothcontract',
  'insurance-pri-add-stat': 'contract-insurance-priaddstat-priaddstatcontract',
  'insurance-pri-add-out': 'contract-insurance-priaddout-priaddoutcontract',
  'insurance-pri-sick-full': 'contract-insurance-health-prisickfullcontract',
  
  // Contract - Loan
  'loan-credit-contract': 'contract-loan-credit-creditcontract',
  'loan-loan-loancontract': 'contract-loan-loan-loancontract',
  'loan-loan-loanagreementcontract': 'contract-loan-loan-loanagreementcontract',
  'loan-realestate-annuityloan': 'contract-loan-realestate-annuityloan',
  'loan-annuityloan-annuityloan': 'contract-loan-annuityloan-annuityloan',
  
  // Contract - Bank & Credit
  'bankaccount-bank-bankaccount': 'contract-bankaccount-bank-bankaccount',
  'creditcard-credit-creditcard': 'contract-creditcard-credit-creditcard',
  'creditcard-credit-creditcardcontract': 'contract-creditcard-credit-creditcardcontract',
  
  // Contract - Lease & Sale
  'lease': 'contract-lease-lease-leasecontract',
  'lease-realestate-leasecontract': 'contract-lease-realestate-leasecontract',
  'sale': 'contract-sale-sale-salecontract',
  'sale-contract': 'contract-sale-sale-salecontract',
  'purchase-realestate-purchasecontract': 'contract-purchase-realestate-purchasecontract',
  
  // Contract - Rental
  'rental': 'contract-rental-rental-rentalcontract',
  'rentalmanagement-rentalmanagement-rentalmanagementcontract': 'contract-rentalmanagement-rentalmanagement-rentalmanagementcontract',
  
  // Contract - Savings
  'savings-realestate-buildingsavings': 'contract-savings-realestate-buildingsavings',
  'savings-savings-depositcontract': 'contract-savings-savings-depositcontract',
  'savings-savings-fixedtermcontract': 'contract-savings-savings-fixedtermcontract',
  'savings-savings-overnightcontract': 'contract-savings-savings-overnightcontract',
  'savings-savings-savingscontract': 'contract-savings-savings-savingscontract',
  
  // Contract - Service
  'service-service-servicecontract': 'contract-service-service-servicecontract',
  'service-consulting-consultingcontract': 'contract-service-consulting-consultingcontract',
  'service-distribution-distributioncontract': 'contract-service-distribution-distributioncontract',
  'service-franchise-franchisecontract': 'contract-service-franchise-franchisecontract',
  'service-gardener-gardenercontract': 'contract-service-gardener-gardenercontract',
  'service-maintenance-maintenancecontract': 'contract-service-maintenance-maintenancecontract',
  'service-notary-notarycontract': 'contract-service-notary-notarycontract',
  'service-security-securitycontract': 'contract-service-security-securitycontract',
  'service-software-softwarecontract': 'contract-service-software-softwarecontract',
  'service-broker-insurancebroker': 'contract-service-broker-insurancebroker',
  'service-broker-realestateagent': 'contract-service-broker-realestateagent',
  'service-caretaker-service': 'contract-service-caretaker-service',
  'service-chimney-sweeper': 'contract-service-chimney-sweeper',
  'service-cleaning-cleaningcontract': 'contract-service-cleaning-cleaningcontract',
  'service-gardener-gardenercontract2': 'contract-service-gardener-gardenercontract2',
  'service-service-caretakerservice': 'contract-service-service-caretakerservice',
  'service-service-chimneysweeper': 'contract-service-service-chimneysweeper',
  
  // Contract - Energy & Media
  'energy': 'contract-energy-energy-energycontract',
  'media': 'contract-media-media-mediacontract',
  'medien': 'contract-media-media-mediacontract',
  'streaming': 'contract-media-streaming-streamingcontract',
  'telecommunication': 'contract-telecommunication-telecommunication-telecommunicationcontract',
  'telecommunication-telecommunication-telecommunicationcontract2': 'contract-telecommunication-telecommunication-telecommunicationcontract2',
  
  // Contract - Property Management & Tax
  'propertymanagement-propertymanagement-propertymanagementcontract': 'contract-propertymanagement-propertymanagement-propertymanagementcontract',
  'property-management': 'contract-propertymanagement-propertymanagement-propertymanagementcontract',
  'tax-realestate-realestatetaxcontract': 'contract-tax-realestate-realestatetaxcontract',
  'real-estate-tax': 'contract-tax-realestate-realestatetaxcontract',
  'municipal-fees': 'contract-tax-realestate-realestatetaxcontract',
  'notary': 'contract-service-notary-notarycontract',
  
  // Contract - Mobility
  'mobility-car-inspection': 'contract-mobility-car-inspection',
  'car-inspection': 'contract-mobility-car-inspection',
  
  // Contract - Credit & Leasing
  'credit-contract': 'contract-creditcard-credit-creditcard',
  'leasing': 'contract-lease-lease-leasecontract',
  
  // Contract - Insurance Glass (fehlt noch)
  'insurance-glass': 'contract-insurance-glass-glasscontract',
  
  // Contract - Heating & Maintenance
  'heating': 'contract-service-maintenance-maintenancecontract',
  'heat-pump': 'contract-service-maintenance-maintenancecontract',
  'chimney-sweeper': 'contract-service-chimney-sweeper',
  'elevator': 'contract-service-maintenance-maintenancecontract',
  
  // Contract - Travel & Rental Management
  'insurance-travel-sickness': 'contract-insurance-travel-travelcontract',
  'rental-management': 'contract-rentalmanagement-rentalmanagement-rentalmanagementcontract',
  
  // Realestate
  'commercial-residential-property': 'realestate-property-commercialresidential-commercialresidentialproperty',
  'carport': 'realestate-parking-carport-carport',
  'garage': 'realestate-parking-garage-garage',
  'parking-house': 'realestate-parking-parkinghouse-parkinghouse',
  'parking-space': 'realestate-parking-parkingspace-parkingspace',
  'allotment-garden': 'realestate-plot-allotmentgarden-allotmentgarden',
  'building-plot': 'realestate-plot-buildingplot-buildingplot',
  'farmland': 'realestate-plot-farmland-farmland',
  'forest': 'realestate-plot-forest-forest',
  'fruit': 'realestate-plot-fruit-fruit',
  'meadow': 'realestate-plot-meadow-meadow',
  'pasture': 'realestate-plot-pasture-pasture',
  
  // Mobility
  'car': 'mobility-passenger-car-car',
  'single-single-singlemobility': 'mobility-single-single-singlemobility',
  'trailer': 'mobility-trailer-trailer-trailer',
  'bicycle': 'mobility-bicycle-bicycle-bicycle',
  'ebike': 'mobility-ebike-ebike-ebike',
  'pedelec': 'mobility-pedelec-pedelec-pedelec',
  'motorsailer': 'mobility-motorsailer-motorsailer-motorsailer',
  'boxvan': 'mobility-boxvan-boxvan-boxvan',
  'motorboat': 'mobility-motorboat-motorboat-motorboat',
  'chopper': 'mobility-chopper-chopper-chopper',
  'cruiser': 'mobility-cruiser-cruiser-cruiser',
  'dirt-bike': 'mobility-dirt-bike-dirt-bike-dirt-bike',
  'motocross': 'mobility-motocross-motocross-motocross',
  'naked': 'mobility-naked-naked-naked',
  'srambler': 'mobility-srambler-srambler-srambler',
  'streetfighter': 'mobility-streetfighter-streetfighter-streetfighter',
  'stuntbike': 'mobility-stuntbike-stuntbike-stuntbike',
  'trial': 'mobility-trial-trial-trial',
  'quad': 'mobility-quad-quad-quad',
  'sailboat': 'mobility-sailboat-sailboat-sailboat',
  'tractor': 'mobility-tractor-tractor-tractor',
  'camper': 'mobility-camper-camper-camper',
  'caravan': 'mobility-caravan-caravan-caravan',
  
  // Financeaccount
  'checking-account': 'financeaccount-bank-checkingaccount-checkingaccount',
  'saving-account': 'financeaccount-bank-savingaccount-savingaccount',
  'charge': 'financeaccount-bank-charge-charge',
  'revolving': 'financeaccount-bank-revolving-revolving',
  'prepaid': 'financeaccount-bank-prepaid-prepaid',
  'debit': 'financeaccount-bank-debit-debit',
  'online-virtual': 'financeaccount-systems-onlinevirtual-onlinevirtual',
  'paypal': 'financeaccount-systems-paypal-paypal',
  'samsung': 'financeaccount-systems-samsungpay-samsungpay',
  'google-pay': 'financeaccount-systems-googlepay-googlepay',
  'apple-pay': 'financeaccount-systems-applepay-applepay',
  'skrill': 'financeaccount-systems-skrill-skrill',
  'stripe': 'financeaccount-systems-stripe-stripe',
  'klarna': 'financeaccount-systems-klarna-klarna',
  
  // Contact
  'single-contact': 'contact-single-contact-contact',
  'single-family-familycontact': 'contact-single-family-familycontact',
  'single-simple-simplecontact': 'contact-single-simple-simplecontact',
  'single-single-singlecontact': 'contact-single-single-singlecontact',
  'group-group-groupcontact': 'contact-group-group-groupcontact',
  'group-extended-extendedcontact': 'contact-group-extended-extendedcontact',
  'company-business-businesscontact': 'contact-company-business-businesscontact',
  'contact-group': 'contact-group-group-groupcontact'
};

function fixFormNames() {
  console.log('🔧 Korrigiere form_name-Werte in der CSV-Datei...\n');
  
  const csvPath = './src/main/resources/config/liquibase/data/group-type-Tabelle 1_20231128.csv';
  
  try {
    // Lese CSV-Datei
    const content = fs.readFileSync(csvPath, 'utf8');
    const lines = content.split('\n');
    const header = lines[0];
    const dataLines = lines.slice(1);
    
    let fixedCount = 0;
    let totalCount = 0;
    
    const fixedLines = dataLines.map(line => {
      if (!line.trim()) return line;
      
      const columns = line.split(';');
      if (columns.length < 8) return line;
      
      const oldFormName = columns[7];
      totalCount++;
      
      if (formNameMapping[oldFormName]) {
        columns[7] = formNameMapping[oldFormName];
        fixedCount++;
        console.log(`✅ ${oldFormName} → ${formNameMapping[oldFormName]}`);
      } else if (oldFormName && oldFormName !== '') {
        console.log(`⚠️  Kein Mapping gefunden für: ${oldFormName}`);
      }
      
      return columns.join(';');
    });
    
    // Schreibe korrigierte Datei
    const fixedContent = [header, ...fixedLines].join('\n');
    fs.writeFileSync(csvPath, fixedContent, 'utf8');
    
    console.log(`\n🎉 ${fixedCount} von ${totalCount} form_name-Werten korrigiert!`);
    console.log(`📄 Datei gespeichert: ${csvPath}`);
    
  } catch (error) {
    console.log(`❌ Fehler: ${error.message}`);
  }
}

// Skript ausführen
fixFormNames(); 