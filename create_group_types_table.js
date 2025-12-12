const fs = require('fs');

// Deutsche Übersetzungen für die Form-Name-Teile
const formNameTranslations = {
  'contract': 'Vertrag',
  'employment': 'Arbeitsvertrag',
  'employee': 'Angestellter',
  'official': 'Beamter',
  'public-service': 'Öffentlicher Dienst',
  'fulltime': 'Vollzeit',
  'part-time': 'Teilzeit',
  'mini-job': 'Mini-Job',
  'internship': 'Praktikum',
  'terminationcontract': 'Aufhebungsvertrag',
  'work-contract': 'Werkvertrag',
  'employmentcontract': 'Arbeitsvertrag',
  'publicservice': 'Öffentlicher Dienst',
  'energy': 'Energie',
  'energycontract2': 'Energievertrag 2',
  'loan': 'Darlehen',
  'loancontract': 'Darlehensvertrag',
  'realestate': 'Immobilie',
  'annuityloan': 'Annuitätendarlehen',
  'lease': 'Leasing',
  'leasecontract': 'Leasingvertrag',
  'media': 'Medien',
  'mediacontract': 'Medienvertrag',
  'insurance': 'Versicherung',
  'automobile': 'Kraftfahrzeug',
  'automobilecontract': 'Kfz-Versicherungsvertrag',
  'savings': 'Sparkonto',
  'buildingsavings': 'Bausparvertrag',
  'savingscontract': 'Sparkontovertrag',
  'overnightcontract': 'Tagesgeldvertrag',
  'creditcard': 'Kreditkarte',
  'credit': 'Kredit',
  'creditcardcontract': 'Kreditkartenvertrag',
  'propertymanagement': 'Hausverwaltung',
  'propertymanagementcontract': 'Hausverwaltungsvertrag',
  'prepaid': 'Prepaid',
  'revolving': 'Revolving',
  'depositcontract': 'Festgeldvertrag',
  'bankaccount': 'Bankkonto',
  'bank': 'Bank',
  'overnight': 'Tagesgeld',
  'fixedtermcontract': 'Festgeldvertrag',
  'fees': 'Gebühren',
  'municipal': 'Gemeinde',
  'municipalcontract': 'Gemeindevertrag',
  'purchase': 'Kauf',
  'purchasecontract': 'Kaufvertrag',
  'service': 'Dienstleistung',
  'gardener': 'Gärtner',
  'gardenercontract': 'Gärtnervertrag',
  'gardenercontract2': 'Gärtnervertrag 2',
  'caretaker': 'Hausmeister',
  'caretakerservice': 'Hausmeisterservice',
  'broker': 'Makler',
  'insurancebroker': 'Versicherungsmakler',
  'mobility': 'Mobilität',
  'car-inspection': 'KFZ-Inspektion',
  'rental': 'Miete',
  'rentalcontract': 'Mietvertrag',
  'notary': 'Notar',
  'notarycontract': 'Notarvertrag',
  'cleaning': 'Reinigung',
  'cleaningcontract': 'Reinigungsvertrag',
  'telecommunication': 'Telekommunikation',
  'telecommunicationcontract': 'Telekommunikationsvertrag',
  'telecommunicationcontract2': 'Telekommunikationsvertrag 2',
  'chimney-sweeper': 'Schornsteinfeger',
  'chimneysweeper': 'Schornsteinfeger',
  'stromcontract': 'Stromvertrag',
  'electric': 'Elektrik',
  'strom': 'Strom',
  'sale': 'Verkauf',
  'salecontract': 'Verkaufsvertrag',
  'streaming': 'Streaming',
  'streamingcontract': 'Streamingvertrag',
  'lease-realestate': 'Immobilienleasing',
  'domanager': 'Hausverwalter',
  'domanagercontract': 'Hausverwaltervertrag',
  'residential': 'Wohngebäude',
  'residentialcontract': 'Wohngebäudevertrag',
  'legalprotection': 'Rechtsschutz',
  'legalprotectioncontract': 'Rechtsschutzvertrag',
  'inthealth': 'Auslandskrankenversicherung',
  'inthealthcontract': 'Auslandskrankenversicherungsvertrag',
  'travel': 'Reise',
  'travelcontract': 'Reisevertrag',
  'health': 'Krankenversicherung',
  'healthcontract': 'Krankenversicherungsvertrag',
  'dailyhospital': 'Krankenhaustagegeld',
  'dailyhospitalcontract': 'Krankenhaustagegeldvertrag',
  'dailysickness': 'Krankentagegeld',
  'dailysicknesscontract': 'Krankentagegeldvertrag',
  'prisickfull': 'Privat Krankenvoll',
  'prisickfullcontract': 'Privat Krankenvollvertrag',
  'priaddout': 'Privat Zusatz Ambulant',
  'priaddoutcontract': 'Privat Zusatz Ambulantvertrag',
  'priaddstat': 'Privat Zusatz Stationär',
  'priaddstatcontract': 'Privat Zusatz Stationärvertrag',
  'priaddtooth': 'Privat Zusatz Zahn',
  'priaddtoothcontract': 'Privat Zusatz Zahnvertrag',
  'disability': 'Berufsunfähigkeit',
  'disabilitycontract': 'Berufsunfähigkeitsvertrag',
  'longtermcaredaily': 'Pflegetagegeld',
  'longtermcaredailycontract': 'Pflegetagegeldvertrag',
  'longtermcare': 'Pflegeversicherung',
  'longtermcarecontract': 'Pflegeversicherungsvertrag',
  'pensionfund': 'Rentenversicherung Fonds',
  'pensionfundcontract': 'Rentenversicherung Fondsvertrag',
  'classicpension': 'Rentenversicherung Klassisch',
  'classicpensioncontract': 'Rentenversicherung Klassischvertrag',
  'risklife': 'Risiko Lebensversicherung',
  'risklifecontract': 'Risiko Lebensversicherungsvertrag',
  'builderliability': 'Bauherrenhaftpflicht',
  'builderliabilitycontract': 'Bauherrenhaftpflichtvertrag',
  'bike': 'Fahrrad',
  'bikecontract': 'Fahrradvertrag',
  'fire': 'Feuer',
  'firecontract': 'Feuerversicherungsvertrag',
  'furniture': 'Hausrat',
  'furniturecontract': 'Hausratvertrag',
  'liability': 'Haftpflicht',
  'liabilitycontract': 'Haftpflichtvertrag',
  'expatriates': 'Expatriates',
  'expatriatescontract': 'Expatriatesvertrag',
  'rental': 'Mietausfall',
  'rentalcontract': 'Mietausfallvertrag',
  'photovoltaic': 'Photovoltaik',
  'photovoltaiccontract': 'Photovoltaikvertrag',
  'liabilitypet': 'Tierhalterhaftpflicht',
  'liabilitypetcontract': 'Tierhalterhaftpflichtvertrag',
  'pet': 'Tier OP',
  'petcontract': 'Tier OP Vertrag',
  'environmentaldamage': 'Umweltschadenhaftpflicht',
  'environmentaldamagecontract': 'Umweltschadenhaftpflichtvertrag',
  'accident': 'Unfall',
  'accidentcontract': 'Unfallversicherungsvertrag',
  'financeaccount': 'Finanzkonto',
  'systems': 'Systeme',
  'onlinevirtual': 'Online Virtuell',
  'paypal': 'PayPal',
  'samsungpay': 'Samsung Pay',
  'googlepay': 'Google Pay',
  'applepay': 'Apple Pay',
  'skrill': 'Skrill',
  'stripe': 'Stripe',
  'contact': 'Kontakt',
  'mobility': 'Mobilität',
  'passenger': 'Personenfahrzeug',
  'car': 'Auto',
  'single': 'Einzel',
  'singlemobility': 'Einzelmobilität',
  'trailer': 'Anhänger',
  'bicycle': 'Fahrrad',
  'ebike': 'E-Bike',
  'pedelec': 'Pedelec',
  'motorsailer': 'Motorsegler',
  'boxvan': 'Kastenwagen',
  'motorboat': 'Motorboot',
  'chopper': 'Chopper',
  'cruiser': 'Cruiser',
  'dirt-bike': 'Dirt Bike',
  'motocross': 'Motocross',
  'naked': 'Naked',
  'srambler': 'Srambler',
  'streetfighter': 'Streetfighter',
  'stuntbike': 'Stuntbike',
  'trial': 'Trial',
  'quad': 'Quad',
  'sailboat': 'Segelboot',
  'tractor': 'Traktor',
  'camper': 'Wohnmobil',
  'caravan': 'Wohnwagen'
};

// Funktion zum Aufteilen des form_name in Teile und Übersetzung
function parseFormName(formName) {
  if (!formName) return ['', '', '', ''];
  
  const parts = formName.split('-');
  const translatedParts = parts.map(part => formNameTranslations[part] || part);
  
  // Fülle auf 4 Teile auf
  while (translatedParts.length < 4) {
    translatedParts.push('');
  }
  
  return translatedParts.slice(0, 4);
}

// Daten aus der Datenbankabfrage (vereinfacht)
const groupTypesData = [
  // Finanzkonten
  { kategorie: 'Finanzkonten', art: 'Bank', gruppierung: 'Bankkonto', sparte: 'Girokonto', form_name: 'contract-savings-realestate-buildingsavings' },
  { kategorie: 'Finanzkonten', art: 'Bank', gruppierung: 'Bankkonto', sparte: 'Sparkonto', form_name: 'contract-savings-savings-savingscontract' },
  { kategorie: 'Finanzkonten', art: 'Kredit', gruppierung: 'Kreditkarte', sparte: 'Charge', form_name: 'contract-savings-savings-overnightcontract' },
  { kategorie: 'Finanzkonten', art: 'Kredit', gruppierung: 'Kreditkarte', sparte: 'Debit', form_name: 'contract-creditcard-credit-creditcard' },
  { kategorie: 'Finanzkonten', art: 'Kredit', gruppierung: 'Kreditkarte', sparte: 'Online Virtuell', form_name: 'contract-propertymanagement-propertymanagement-propertymanagementcontract' },
  { kategorie: 'Finanzkonten', art: 'Kredit', gruppierung: 'Kreditkarte', sparte: 'Prepaid', form_name: 'contract-creditcard-credit-creditcardcontract' },
  { kategorie: 'Finanzkonten', art: 'Kredit', gruppierung: 'Kreditkarte', sparte: 'Revolving', form_name: 'contract-savings-savings-depositcontract' },
  { kategorie: 'Finanzkonten', art: 'Kredit', gruppierung: 'Kreditkarte', sparte: 'Samsung', form_name: 'contract-employment-official-work-contract' },
  { kategorie: 'Finanzkonten', art: 'Systeme', gruppierung: 'Bezahlsysteme', sparte: 'Apple Pay', form_name: 'contract-employment-official-mini-job' },
  { kategorie: 'Finanzkonten', art: 'Systeme', gruppierung: 'Bezahlsysteme', sparte: 'Google Pay', form_name: 'contract-employment-official-part-time' },
  { kategorie: 'Finanzkonten', art: 'Systeme', gruppierung: 'Bezahlsysteme', sparte: 'Klarna', form_name: 'contract-employment-public-service-fulltime' },
  { kategorie: 'Finanzkonten', art: 'Systeme', gruppierung: 'Bezahlsysteme', sparte: 'Paypal', form_name: 'contract-rentalmanagement-rentalmanagement-rentalmanagementcontract' },
  { kategorie: 'Finanzkonten', art: 'Systeme', gruppierung: 'Bezahlsysteme', sparte: 'Skrill', form_name: 'contract-employment-public-service-mini-job' },
  { kategorie: 'Finanzkonten', art: 'Systeme', gruppierung: 'Bezahlsysteme', sparte: 'Stripe', form_name: 'contract-employment-public-service-part-time' },
  
  // Mobilität (Beispiele)
  { kategorie: 'Mobilität', art: 'Boot', gruppierung: 'Hybrid', sparte: 'Motorsegler', form_name: 'contract-insurance-automobile-automobilecontract' },
  { kategorie: 'Mobilität', art: 'Boot', gruppierung: 'Motorbetrieben', sparte: 'Jollenkreuzer', form_name: 'contract-insurance-automobile-automobilecontract' },
  { kategorie: 'Mobilität', art: 'Boot', gruppierung: 'Motorbetrieben', sparte: 'Motorboot', form_name: 'contract-insurance-automobile-automobilecontract' },
  { kategorie: 'Mobilität', art: 'Boot', gruppierung: 'Motorbetrieben', sparte: 'Sportboot', form_name: 'contract-insurance-automobile-automobilecontract' },
  { kategorie: 'Mobilität', art: 'Boot', gruppierung: 'Motorbetrieben', sparte: 'Yacht', form_name: 'contract-insurance-automobile-automobilecontract' },
  { kategorie: 'Mobilität', art: 'Boot', gruppierung: 'Segeln', sparte: 'Segelboot', form_name: 'contract-insurance-automobile-automobilecontract' },
  { kategorie: 'Mobilität', art: 'Boot', gruppierung: 'Segeln', sparte: 'Segelyacht', form_name: 'contract-insurance-automobile-automobilecontract' },
  
  // Verträge (Beispiele)
  { kategorie: 'Verträge', art: 'Arbeitsvertrag', gruppierung: 'Angestellter', sparte: 'Aufhebungsvertrag', form_name: 'contract-employmentContract-employee-terminationAgreement' },
  { kategorie: 'Verträge', art: 'Arbeitsvertrag', gruppierung: 'Angestellter', sparte: 'Aushilfe', form_name: 'contract-employment-employee-mini-job' },
  { kategorie: 'Verträge', art: 'Arbeitsvertrag', gruppierung: 'Angestellter', sparte: 'Mini', form_name: 'contract-employment-employee-mini-job' },
  { kategorie: 'Verträge', art: 'Arbeitsvertrag', gruppierung: 'Angestellter', sparte: 'Praktikumsvertrag', form_name: 'contract-employment-employee-internship' },
  { kategorie: 'Verträge', art: 'Arbeitsvertrag', gruppierung: 'Angestellter', sparte: 'Teilzeit', form_name: 'contract-employment-employee-part-time' },
  { kategorie: 'Verträge', art: 'Arbeitsvertrag', gruppierung: 'Angestellter', sparte: 'Vollzeit', form_name: 'contract-employment-employee-fulltime' },
  { kategorie: 'Verträge', art: 'Arbeitsvertrag', gruppierung: 'Angestellter', sparte: 'Werkvertrag', form_name: 'contract-employment-employment-employmentcontract' },
  
  // Immobilien (Beispiele)
  { kategorie: 'Immobilien', art: 'mit Bebauung', gruppierung: 'Bewirtschaftung', sparte: 'Ackerland', form_name: 'contract-service-gardener-gardenercontract2' },
  { kategorie: 'Immobilien', art: 'mit Bebauung', gruppierung: 'Bewirtschaftung', sparte: 'Forst', form_name: 'contract-employment-employee-work-contract' },
  { kategorie: 'Immobilien', art: 'mit Bebauung', gruppierung: 'Bewirtschaftung', sparte: 'Frucht', form_name: 'contract-employment-official-fulltime' },
  { kategorie: 'Immobilien', art: 'mit Bebauung', gruppierung: 'Bewirtschaftung', sparte: 'Weide', form_name: 'contract-employment-official-internship' },
  { kategorie: 'Immobilien', art: 'mit Bebauung', gruppierung: 'Garten', sparte: 'Schrebergarten', form_name: 'contract-service-franchise-franchisecontract' },
  
  // Kontakte
  { kategorie: 'Kontakte', art: 'Kontakte', gruppierung: 'Kontakt', sparte: 'Einzelkontakt', form_name: 'contract-service-distribution-distributioncontract' },
  { kategorie: 'Kontakte', art: 'Kontakte', gruppierung: 'Kontakt', sparte: 'Kontaktgruppe', form_name: 'contract-service-consulting-consultingcontract' }
];

// CSV-Header
const csvHeader = 'Kategorie,Art,Gruppierung,Sparte,Form_Name,Teil1_Deutsch,Teil2_Deutsch,Teil3_Deutsch,Teil4_Deutsch\n';

// CSV-Daten generieren
let csvContent = csvHeader;

groupTypesData.forEach(item => {
  const [teil1, teil2, teil3, teil4] = parseFormName(item.form_name);
  const row = [
    item.kategorie,
    item.art,
    item.gruppierung,
    item.sparte,
    item.form_name,
    teil1,
    teil2,
    teil3,
    teil4
  ].map(field => `"${field}"`).join(',');
  
  csvContent += row + '\n';
});

// Datei schreiben
fs.writeFileSync('group_types_complete_table.csv', csvContent, 'utf8');

console.log('Tabelle wurde in group_types_complete_table.csv gespeichert');
console.log(`Anzahl Einträge: ${groupTypesData.length}`); 