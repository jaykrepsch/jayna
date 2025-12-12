const fs = require('fs');
const path = require('path');

// Standard-Übersetzungen für BaseContract
const baseContractTranslations = {
  "jaynaApp.baseContract.general.title": "Allgemein",
  "jaynaApp.baseContract.general.fields.label": "Bezeichnung",
  "jaynaApp.baseContract.general.fields.description": "Beschreibung",
  "jaynaApp.baseContract.general.fields.entityState": "Status",
  
  "jaynaApp.baseContract.contractData.title": "Vertragsdaten",
  "jaynaApp.baseContract.contractData.fields.contractor": "Vertragspartner",
  "jaynaApp.baseContract.contractData.fields.contractOwner": "Vertragseigentümer",
  "jaynaApp.baseContract.contractData.fields.contractUser": "Vertragsnutzer",
  "jaynaApp.baseContract.contractData.fields.contractNumber": "Vertragsnummer",
  "jaynaApp.baseContract.contractData.fields.startDate": "Startdatum",
  "jaynaApp.baseContract.contractData.fields.endDate": "Enddatum",
  "jaynaApp.baseContract.contractData.fields.applicationDate": "Antragsdatum",
  "jaynaApp.baseContract.contractData.fields.confirmationDate": "Bestätigungsdatum",
  
  "jaynaApp.baseContract.payment.title": "Zahlungsdaten",
  "jaynaApp.baseContract.payment.fields.payer": "Zahlender",
  "jaynaApp.baseContract.payment.fields.paymentPattern": "Zahlungsrhythmus",
  "jaynaApp.baseContract.payment.fields.paymentType": "Zahlungsart",
  "jaynaApp.baseContract.payment.fields.amount": "Betrag",
  
  "jaynaApp.baseContract.weiteres.title": "Weiteres",
  "jaynaApp.baseContract.weiteres.fields.comment": "Kommentar",
  "jaynaApp.baseContract.weiteres.fields.notes": "Notizen"
};

// Standard-Übersetzungen für BaseContact
const baseContactTranslations = {
  "jaynaApp.baseContact.general.title": "Allgemein",
  "jaynaApp.baseContact.general.fields.label": "Bezeichnung",
  "jaynaApp.baseContact.general.fields.description": "Beschreibung",
  "jaynaApp.baseContact.general.fields.entityState": "Status",
  
  "jaynaApp.baseContact.contactData.title": "Kontaktdaten",
  "jaynaApp.baseContact.contactData.fields.firstName": "Vorname",
  "jaynaApp.baseContact.contactData.fields.lastName": "Nachname",
  "jaynaApp.baseContact.contactData.fields.email": "E-Mail",
  "jaynaApp.baseContact.contactData.fields.phone": "Telefon",
  "jaynaApp.baseContact.contactData.fields.mobile": "Mobil",
  "jaynaApp.baseContact.contactData.fields.birthDate": "Geburtsdatum",
  "jaynaApp.baseContact.contactData.fields.gender": "Geschlecht",
  
  "jaynaApp.baseContact.address.title": "Adresse",
  "jaynaApp.baseContact.address.fields.street": "Straße",
  "jaynaApp.baseContact.address.fields.streetNumber": "Hausnummer",
  "jaynaApp.baseContact.address.fields.postalCode": "PLZ",
  "jaynaApp.baseContact.address.fields.city": "Stadt",
  "jaynaApp.baseContact.address.fields.state": "Bundesland",
  "jaynaApp.baseContact.address.fields.country": "Land",
  
  "jaynaApp.baseContact.weiteres.title": "Weiteres",
  "jaynaApp.baseContact.weiteres.fields.comment": "Kommentar",
  "jaynaApp.baseContact.weiteres.fields.notes": "Notizen"
};

// Standard-Übersetzungen für BaseRealestate
const baseRealestateTranslations = {
  "jaynaApp.baseRealestate.general.title": "Allgemein",
  "jaynaApp.baseRealestate.general.fields.label": "Bezeichnung",
  "jaynaApp.baseRealestate.general.fields.description": "Beschreibung",
  "jaynaApp.baseRealestate.general.fields.entityState": "Status",
  
  "jaynaApp.baseRealestate.address.title": "Adresse",
  "jaynaApp.baseRealestate.address.fields.street": "Straße",
  "jaynaApp.baseRealestate.address.fields.streetNumber": "Hausnummer",
  "jaynaApp.baseRealestate.address.fields.postalCode": "PLZ",
  "jaynaApp.baseRealestate.address.fields.city": "Stadt",
  "jaynaApp.baseRealestate.address.fields.state": "Bundesland",
  "jaynaApp.baseRealestate.address.fields.country": "Land",
  "jaynaApp.baseRealestate.address.fields.longitude": "Längengrad",
  "jaynaApp.baseRealestate.address.fields.latitude": "Breitengrad",
  
  "jaynaApp.baseRealestate.plot.title": "Grundstück",
  "jaynaApp.baseRealestate.plot.fields.area": "Fläche (m²)",
  "jaynaApp.baseRealestate.plot.fields.builtUpArea": "Bebaute Fläche (m²)",
  "jaynaApp.baseRealestate.plot.fields.sealtArea": "Versiegelte Fläche (m²)",
  "jaynaApp.baseRealestate.plot.fields.undevelopedArea": "Unbebaute Fläche (m²)",
  
  "jaynaApp.baseRealestate.weiteres.title": "Weiteres",
  "jaynaApp.baseRealestate.weiteres.fields.comment": "Kommentar",
  "jaynaApp.baseRealestate.weiteres.fields.notes": "Notizen"
};

// Standard-Übersetzungen für BaseMobility
const baseMobilityTranslations = {
  "jaynaApp.baseMobility.general.title": "Allgemein",
  "jaynaApp.baseMobility.general.fields.label": "Bezeichnung",
  "jaynaApp.baseMobility.general.fields.description": "Beschreibung",
  "jaynaApp.baseMobility.general.fields.entityState": "Status",
  
  "jaynaApp.baseMobility.vehicleData.title": "Fahrzeugdaten",
  "jaynaApp.baseMobility.vehicleData.fields.brand": "Marke",
  "jaynaApp.baseMobility.vehicleData.fields.model": "Modell",
  "jaynaApp.baseMobility.vehicleData.fields.year": "Baujahr",
  "jaynaApp.baseMobility.vehicleData.fields.licensePlate": "Kennzeichen",
  "jaynaApp.baseMobility.vehicleData.fields.vin": "Fahrgestellnummer",
  "jaynaApp.baseMobility.vehicleData.fields.color": "Farbe",
  "jaynaApp.baseMobility.vehicleData.fields.engineSize": "Hubraum",
  "jaynaApp.baseMobility.vehicleData.fields.fuelType": "Kraftstoffart",
  "jaynaApp.baseMobility.vehicleData.fields.power": "Leistung (PS)",
  
  "jaynaApp.baseMobility.weiteres.title": "Weiteres",
  "jaynaApp.baseMobility.weiteres.fields.comment": "Kommentar",
  "jaynaApp.baseMobility.weiteres.fields.notes": "Notizen"
};

// Standard-Übersetzungen für BaseFinanceaccount
const baseFinanceaccountTranslations = {
  "jaynaApp.baseFinanceaccount.general.title": "Allgemein",
  "jaynaApp.baseFinanceaccount.general.fields.label": "Bezeichnung",
  "jaynaApp.baseFinanceaccount.general.fields.description": "Beschreibung",
  "jaynaApp.baseFinanceaccount.general.fields.entityState": "Status",
  
  "jaynaApp.baseFinanceaccount.accountData.title": "Kontodaten",
  "jaynaApp.baseFinanceaccount.accountData.fields.accountNumber": "Kontonummer",
  "jaynaApp.baseFinanceaccount.accountData.fields.iban": "IBAN",
  "jaynaApp.baseFinanceaccount.accountData.fields.bic": "BIC",
  "jaynaApp.baseFinanceaccount.accountData.fields.bankName": "Bankname",
  "jaynaApp.baseFinanceaccount.accountData.fields.accountType": "Kontotyp",
  "jaynaApp.baseFinanceaccount.accountData.fields.balance": "Kontostand",
  "jaynaApp.baseFinanceaccount.accountData.fields.currency": "Währung",
  
  "jaynaApp.baseFinanceaccount.weiteres.title": "Weiteres",
  "jaynaApp.baseFinanceaccount.weiteres.fields.comment": "Kommentar",
  "jaynaApp.baseFinanceaccount.weiteres.fields.notes": "Notizen"
};

// Spezifische Übersetzungen für verschiedene Vertragstypen
const specificTranslations = {
  // Annuity Loan
  "contract-loan-annuityloan-annuityloan": {
    "loanData.title": "Kreditdaten",
    "loanData.fields.loanAmount": "Kreditsumme (€)",
    "loanData.fields.interestRate": "Zinssatz (%)",
    "loanData.fields.annuityAmount": "Annuitätenbetrag (€)",
    "loanData.fields.remainingDebt": "Restschuld (€)",
    "loanData.fields.loanTerm": "Kreditlaufzeit (Jahre)",
    "loanData.fields.collateralType": "Sicherungsart",
    "loanData.fields.collateralValue": "Sicherungswert (€)",
    "loanData.fields.loanPurpose": "Kreditzweck"
  },
  
  // Residential Insurance
  "contract-insurance-residential-residentialcontract": {
    "propertyData.title": "Immobiliendaten",
    "propertyData.fields.propertyType": "Immobilientyp",
    "propertyData.fields.propertyAddress": "Immobilienadresse",
    "propertyData.fields.constructionYear": "Baujahr",
    "propertyData.fields.buildingType": "Gebäudetyp",
    "propertyData.fields.numberOfUnits": "Anzahl Wohneinheiten",
    "propertyData.fields.totalArea": "Gesamtfläche (m²)",
    "propertyData.fields.replacementValue": "Wiederbeschaffungswert (€)",
    "propertyData.fields.coverageType": "Versicherungsumfang",
    "propertyData.fields.deductible": "Selbstbeteiligung (€)",
    "propertyData.fields.additionalCoverage": "Zusatzversicherung"
  },
  
  // Employment Contracts
  "contract-employment-employee-fulltime": {
    "employmentData.title": "Vollzeit Daten",
    "employmentData.fields.monthlySalary": "Monatsgehalt (€)",
    "employmentData.fields.hourlyWage": "Stundenlohn (€)",
    "employmentData.fields.workingHours": "Arbeitsstunden pro Woche",
    "employmentData.fields.occupation": "Beruf",
    "employmentData.fields.activities": "Tätigkeiten",
    "employmentData.fields.workSchedule": "Arbeitszeiten",
    "employmentData.fields.workplace": "Arbeitsort",
    "employmentData.fields.vacationDays": "Urlaubstage",
    "employmentData.fields.department": "Abteilung",
    "employmentData.fields.position": "Position"
  },
  
  "contract-employment-employee-parttime": {
    "employmentData.title": "Teilzeit Daten",
    "employmentData.fields.monthlySalary": "Monatsgehalt (€)",
    "employmentData.fields.hourlyWage": "Stundenlohn (€)",
    "employmentData.fields.workingHours": "Arbeitsstunden pro Woche",
    "employmentData.fields.occupation": "Beruf",
    "employmentData.fields.activities": "Tätigkeiten",
    "employmentData.fields.workSchedule": "Arbeitszeiten",
    "employmentData.fields.workplace": "Arbeitsort",
    "employmentData.fields.vacationDays": "Urlaubstage",
    "employmentData.fields.department": "Abteilung",
    "employmentData.fields.position": "Position"
  },
  
  "contract-employment-employee-minijob": {
    "employmentData.title": "Minijob Daten",
    "employmentData.fields.monthlySalary": "Monatsgehalt (€)",
    "employmentData.fields.hourlyWage": "Stundenlohn (€)",
    "employmentData.fields.workingHours": "Arbeitsstunden pro Woche",
    "employmentData.fields.occupation": "Beruf",
    "employmentData.fields.activities": "Tätigkeiten",
    "employmentData.fields.workSchedule": "Arbeitszeiten",
    "employmentData.fields.workplace": "Arbeitsort"
  },
  
  // Insurance Contracts
  "contract-insurance-car-carcontract": {
    "insuranceData.title": "KFZ Versicherungsdaten",
    "insuranceData.fields.vehicleType": "Fahrzeugtyp",
    "insuranceData.fields.licensePlate": "Kennzeichen",
    "insuranceData.fields.vin": "Fahrgestellnummer",
    "insuranceData.fields.engineSize": "Hubraum (ccm)",
    "insuranceData.fields.power": "Leistung (PS)",
    "insuranceData.fields.fuelType": "Kraftstoffart",
    "insuranceData.fields.coverageType": "Versicherungsumfang",
    "insuranceData.fields.deductible": "Selbstbeteiligung (€)",
    "insuranceData.fields.bonusMalus": "Schadenfreiheitsrabatt"
  },
  
  "contract-insurance-health-healthcontract": {
    "insuranceData.title": "Krankenversicherungsdaten",
    "insuranceData.fields.insuranceType": "Versicherungstyp",
    "insuranceData.fields.coverageLevel": "Versicherungsumfang",
    "insuranceData.fields.deductible": "Selbstbeteiligung (€)",
    "insuranceData.fields.additionalCoverage": "Zusatzversicherungen",
    "insuranceData.fields.familyCoverage": "Familienversicherung"
  },
  
  // Bank Account
  "contract-bankaccount-bank-bankaccount": {
    "accountData.title": "Bankkontodaten",
    "accountData.fields.accountNumber": "Kontonummer",
    "accountData.fields.iban": "IBAN",
    "accountData.fields.bic": "BIC",
    "accountData.fields.bankName": "Bankname",
    "accountData.fields.accountType": "Kontotyp",
    "accountData.fields.interestRate": "Zinssatz (%)",
    "accountData.fields.overdraftLimit": "Dispo-Limit (€)"
  },
  
  // Credit Card
  "contract-creditcard-credit-creditcard": {
    "creditData.title": "Kreditkartendaten",
    "creditData.fields.cardNumber": "Kartennummer",
    "creditData.fields.cardType": "Kartentyp",
    "creditData.fields.creditLimit": "Kreditlimit (€)",
    "creditData.fields.interestRate": "Zinssatz (%)",
    "creditData.fields.annualFee": "Jahresgebühr (€)",
    "creditData.fields.cashAdvanceFee": "Bargeldabhebung Gebühr (%)"
  }
};

// Funktion zum Erstellen einer i18n-Datei
function createI18nFile(formDefPath, entityType) {
  try {
    const formDef = JSON.parse(fs.readFileSync(formDefPath, 'utf8'));
    const formName = path.basename(formDefPath, '.json');
    
    // Bestimme die Basis-Übersetzungen je nach Entity-Typ
    let baseTranslations = {};
    switch (entityType) {
      case 'contract':
        baseTranslations = baseContractTranslations;
        break;
      case 'contact':
        baseTranslations = baseContactTranslations;
        break;
      case 'realestate':
        baseTranslations = baseRealestateTranslations;
        break;
      case 'mobility':
        baseTranslations = baseMobilityTranslations;
        break;
      case 'financeaccount':
        baseTranslations = baseFinanceaccountTranslations;
        break;
    }
    
    // Erstelle die Übersetzungen
    const translations = {
      [`jaynaApp.${formName}.createTitle`]: `${formDef.formTitle} anlegen`,
      [`jaynaApp.${formName}.updateTitle`]: `${formDef.formTitle} bearbeiten`,
      [`jaynaApp.${formName}.viewTitle`]: `${formDef.formTitle} anzeigen`,
      [`jaynaApp.${formName}.relations-title`]: "Verknüpfungen"
    };
    
    // Füge Basis-Übersetzungen hinzu
    Object.assign(translations, baseTranslations);
    
    // Füge spezifische Übersetzungen hinzu
    if (specificTranslations[formName]) {
      const specific = specificTranslations[formName];
      Object.keys(specific).forEach(key => {
        translations[`jaynaApp.${formName}.${key}`] = specific[key];
      });
    }
    
    // Füge Übersetzungen für spezifische Abschnitte hinzu
    formDef.parts.forEach(part => {
      if (part.i18nSource === formName) {
        const sectionName = part.title.split('.').pop().replace('.title', '');
        translations[`jaynaApp.${formName}.${sectionName}.title`] = part.title;
        
        part.fields.forEach(field => {
          translations[`jaynaApp.${formName}.${sectionName}.fields.${field.name}`] = 
            getFieldTranslation(field.name, field.type);
        });
      }
    });
    
    return translations;
    
  } catch (error) {
    console.log(`❌ Fehler beim Lesen von ${formDefPath}: ${error.message}`);
    return null;
  }
}

// Hilfsfunktion für Feldübersetzungen
function getFieldTranslation(fieldName, fieldType) {
  const translations = {
    // Allgemeine Felder
    label: "Bezeichnung",
    description: "Beschreibung",
    entityState: "Status",
    comment: "Kommentar",
    notes: "Notizen",
    
    // Contract-spezifische Felder
    contractor: "Vertragspartner",
    contractOwner: "Vertragseigentümer",
    contractUser: "Vertragsnutzer",
    contractNumber: "Vertragsnummer",
    startDate: "Startdatum",
    endDate: "Enddatum",
    applicationDate: "Antragsdatum",
    confirmationDate: "Bestätigungsdatum",
    payer: "Zahlender",
    paymentPattern: "Zahlungsrhythmus",
    paymentType: "Zahlungsart",
    amount: "Betrag",
    
    // Contact-spezifische Felder
    firstName: "Vorname",
    lastName: "Nachname",
    email: "E-Mail",
    phone: "Telefon",
    mobile: "Mobil",
    birthDate: "Geburtsdatum",
    gender: "Geschlecht",
    
    // Realestate-spezifische Felder
    street: "Straße",
    streetNumber: "Hausnummer",
    postalCode: "PLZ",
    city: "Stadt",
    state: "Bundesland",
    country: "Land",
    longitude: "Längengrad",
    latitude: "Breitengrad",
    area: "Fläche (m²)",
    builtUpArea: "Bebaute Fläche (m²)",
    sealtArea: "Versiegelte Fläche (m²)",
    undevelopedArea: "Unbebaute Fläche (m²)",
    
    // Mobility-spezifische Felder
    brand: "Marke",
    model: "Modell",
    year: "Baujahr",
    licensePlate: "Kennzeichen",
    vin: "Fahrgestellnummer",
    color: "Farbe",
    engineSize: "Hubraum (ccm)",
    fuelType: "Kraftstoffart",
    power: "Leistung (PS)",
    
    // Financeaccount-spezifische Felder
    accountNumber: "Kontonummer",
    iban: "IBAN",
    bic: "BIC",
    bankName: "Bankname",
    accountType: "Kontotyp",
    balance: "Kontostand",
    currency: "Währung"
  };
  
  return translations[fieldName] || fieldName;
}

// Funktion zum Formatieren der JSON-Datei mit Leerzeilen
function formatI18nJson(translations) {
  const lines = [];
  
  // Gruppiere Übersetzungen nach Präfix
  const groups = {};
  Object.keys(translations).forEach(key => {
    const prefix = key.split('.').slice(0, 3).join('.');
    if (!groups[prefix]) groups[prefix] = {};
    groups[prefix][key] = translations[key];
  });
  
  // Erstelle formatierte Ausgabe
  lines.push('{');
  
  const groupKeys = Object.keys(groups).sort();
  groupKeys.forEach((groupKey, groupIndex) => {
    const group = groups[groupKey];
    const groupLines = Object.keys(group).sort().map((key, keyIndex) => {
      const isLastInGroup = keyIndex === Object.keys(group).length - 1;
      const isLastGroup = groupIndex === groupKeys.length - 1;
      const comma = (!isLastInGroup || !isLastGroup) ? ',' : '';
      return `  "${key}": "${group[key]}"${comma}`;
    });
    
    if (groupIndex > 0) lines.push('');
    lines.push(...groupLines);
  });
  
  lines.push('}');
  
  return lines.join('\n');
}

// Hauptfunktion
function updateAllI18nFiles() {
  console.log('🔧 Aktualisiere alle i18n-Dateien...\n');
  
  const entities = ['contract', 'contact', 'realestate', 'mobility', 'financeaccount'];
  let totalUpdated = 0;
  
  entities.forEach(entityType => {
    const formDefDir = `./src/main/webapp/app/shared/form-definitions/${entityType}`;
    const i18nDir = `./src/main/webapp/i18n/de/${entityType}`;
    
    if (!fs.existsSync(formDefDir)) return;
    
    const formDefFiles = fs.readdirSync(formDefDir).filter(file => file.endsWith('.json'));
    
    formDefFiles.forEach(formDefFile => {
      const formDefPath = path.join(formDefDir, formDefFile);
      const formName = path.basename(formDefFile, '.json');
      const i18nFileName = `i18n-${formName}.json`;
      const i18nPath = path.join(i18nDir, i18nFileName);
      
      try {
        // Erstelle Übersetzungen
        const translations = createI18nFile(formDefPath, entityType);
        if (!translations) return;
        
        // Formatiere und speichere
        const formattedContent = formatI18nJson(translations);
        fs.writeFileSync(i18nPath, formattedContent, 'utf8');
        
        console.log(`✅ ${i18nFileName}`);
        totalUpdated++;
        
      } catch (error) {
        console.log(`❌ Fehler bei ${i18nFileName}: ${error.message}`);
      }
    });
  });
  
  console.log(`\n🎉 ${totalUpdated} i18n-Dateien aktualisiert!`);
}

// Skript ausführen
updateAllI18nFiles(); 