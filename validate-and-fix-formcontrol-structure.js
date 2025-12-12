const fs = require('fs');
const path = require('path');

// Extrahiere den Formulardefinitionsnamen aus dem Dateinamen
function extractFormName(fileName) {
  return fileName.replace('.json', '');
}

// Standard-Felder für jeden Abschnitt
const standardFields = {
  general: [
    { name: "label", type: "text", rules: "required" },
    { name: "description", type: "text" },
    { name: "entityState", type: "dropdown", boxLabel: "global.menu.choose", enumName: "entityState", defaultValue: "ACTIVE" }
  ],
  weiteres: [
    { name: "comment", type: "textarea" },
    { name: "notes", type: "textarea" }
  ]
};

// Entitätsspezifische Standard-Felder
const entitySpecificFields = {
  contract: {
    contractData: [
      { name: "contractor", type: "text" },
      { name: "contractOwner", type: "text" },
      { name: "contractUser", type: "text" },
      { name: "contractNumber", type: "text" },
      { name: "startDate", type: "date", enableTimePicker: false, autoApply: true, textInput: true, format: "dd.MM.yyyy" },
      { name: "endDate", type: "date", enableTimePicker: false, autoApply: true, textInput: true, format: "dd.MM.yyyy" },
      { name: "applicationDate", type: "date", enableTimePicker: false, autoApply: true, textInput: true, format: "dd.MM.yyyy" },
      { name: "confirmationDate", type: "date", enableTimePicker: false, autoApply: true, textInput: true, format: "dd.MM.yyyy" }
    ],
    payment: [
      { name: "payer", type: "text" },
      { name: "paymentPattern", type: "dropdown", boxLabel: "global.menu.choose", enumName: "paymentPattern" },
      { name: "paymentType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "paymentType" },
      { name: "amount", type: "number" }
    ]
  },
  contact: {
    contactData: [
      { name: "firstName", type: "text" },
      { name: "lastName", type: "text" },
      { name: "email", type: "text" },
      { name: "phone", type: "text" },
      { name: "mobile", type: "text" }
    ],
    address: [
      { name: "street", type: "text" },
      { name: "streetNumber", type: "text" },
      { name: "postalCode", type: "text" },
      { name: "city", type: "text" },
      { name: "state", type: "text" },
      { name: "country", type: "text" }
    ],
    communication: [
      { name: "preferredContact", type: "dropdown", boxLabel: "global.menu.choose", enumName: "preferredContact" },
      { name: "newsletter", type: "checkbox" },
      { name: "marketing", type: "checkbox" }
    ]
  },
  realestate: {
    address: [
      { name: "street", type: "text" },
      { name: "streetNumber", type: "text" },
      { name: "postalCode", type: "text" },
      { name: "city", type: "text" },
      { name: "state", type: "text" },
      { name: "country", type: "text" },
      { name: "longitude", type: "text" },
      { name: "latitude", type: "text" }
    ],
    plot: [
      { name: "area", type: "number" },
      { name: "builtUpArea", type: "number" },
      { name: "sealtArea", type: "number" },
      { name: "undevelopedArea", type: "number" }
    ]
  },
  financeaccount: {
    accountData: [
      { name: "accountNumber", type: "text" },
      { name: "iban", type: "text" },
      { name: "bic", type: "text" },
      { name: "accountHolder", type: "text" }
    ],
    bank: [
      { name: "bankName", type: "text" },
      { name: "bankCode", type: "text" },
      { name: "branch", type: "text" }
    ],
    details: [
      { name: "balance", type: "number" },
      { name: "currency", type: "dropdown", boxLabel: "global.menu.choose", enumName: "currency" },
      { name: "status", type: "dropdown", boxLabel: "global.menu.choose", enumName: "accountStatus" }
    ]
  },
  mobility: {
    mobilityData: [
      { name: "vehicleType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "vehicleType" },
      { name: "licensePlate", type: "text" },
      { name: "vin", type: "text" }
    ],
    vehicle: [
      { name: "brand", type: "text" },
      { name: "model", type: "text" },
      { name: "year", type: "number" },
      { name: "color", type: "text" }
    ],
    insurance: [
      { name: "insuranceType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "insuranceType" },
      { name: "insuranceCompany", type: "text" },
      { name: "policyNumber", type: "text" }
    ]
  }
};

// Standard-Relations für jede Entität
const standardRelations = {
  contract: [
    { entityName: "contact", relationName: "contactContracts" },
    { entityName: "realestate", relationName: "contractRealestates" },
    { entityName: "financeaccount", relationName: "contractFinanceAccounts" },
    { entityName: "mobility", relationName: "contractMobilities" },
    { entityName: "contract", relationName: "contractContracts" }
  ],
  contact: [
    { entityName: "contract", relationName: "contactContracts" },
    { entityName: "realestate", relationName: "contactRealestates" },
    { entityName: "financeaccount", relationName: "contactFinanceAccounts" },
    { entityName: "mobility", relationName: "contactMobilities" }
  ],
  realestate: [
    { entityName: "contact", relationName: "contactRealestates" },
    { entityName: "contract", relationName: "contractRealestates" },
    { entityName: "financeaccount", relationName: "financeaccountRealestates" }
  ],
  financeaccount: [
    { entityName: "contact", relationName: "financeaccountContacts" },
    { entityName: "contract", relationName: "financeaccountContracts" },
    { entityName: "realestate", relationName: "financeaccountRealestates" },
    { entityName: "mobility", relationName: "financeaccountMobilities" }
  ],
  mobility: [
    { entityName: "contact", relationName: "mobilityContacts" },
    { entityName: "contract", relationName: "mobilityContracts" },
    { entityName: "financeaccount", relationName: "mobilityFinanceAccounts" }
  ]
};

// Prüfe und korrigiere Formulardefinition
function validateAndFixFormDefinition(filePath, entity) {
  try {
    const content = fs.readFileSync(filePath, 'utf8');
    const formDef = JSON.parse(content);
    const fileName = path.basename(filePath);
    const formName = extractFormName(fileName);
    
    let hasChanges = false;
    const issues = [];
    
    // 1. _comment prüfen
    const expectedComment = `formControl-Konzept: Standard-Abschnitte aus Base${entity.charAt(0).toUpperCase() + entity.slice(1)}, spezielle Abschnitte mit i18nSource`;
    if (!formDef._comment || !formDef._comment.includes('formControl-Konzept')) {
      formDef._comment = expectedComment;
      hasChanges = true;
      issues.push('_comment korrigiert');
    }
    
    // 2. relationsTitle nach unten verschieben (falls oben)
    const relationsTitle = `jaynaApp.${formName}.relations-title`;
    if (formDef.relationsTitle !== relationsTitle) {
      formDef.relationsTitle = relationsTitle;
      hasChanges = true;
      issues.push('relationsTitle korrigiert');
    }
    
    // 3. Korrekte Struktur erstellen
    const correctParts = [];
    
    // 3.1 General-Abschnitt (immer zuerst)
    correctParts.push({
      "title": `jaynaApp.base${entity.charAt(0).toUpperCase() + entity.slice(1)}.general.title`,
      "fields": standardFields.general
    });
    
    // 3.2 Entitätsspezifische Standard-Abschnitte
    const entityFields = entitySpecificFields[entity];
    if (entityFields) {
      Object.keys(entityFields).forEach(sectionKey => {
        correctParts.push({
          "title": `jaynaApp.base${entity.charAt(0).toUpperCase() + entity.slice(1)}.${sectionKey}.title`,
          "fields": entityFields[sectionKey]
        });
      });
    }
    
    // 3.3 Spezifische Abschnitte (nur bei Contract)
    if (entity === 'contract' && formDef.parts) {
      formDef.parts.forEach(section => {
        if (section.i18nSource && section.title && !section.title.includes('baseContract')) {
          correctParts.push(section);
        }
      });
    }
    
    // 3.4 Weiteres-Abschnitt (vor Relations)
    correctParts.push({
      "title": `jaynaApp.base${entity.charAt(0).toUpperCase() + entity.slice(1)}.weiteres.title`,
      "fields": standardFields.weiteres
    });
    
    // 3.5 Parts vergleichen und korrigieren
    if (JSON.stringify(formDef.parts) !== JSON.stringify(correctParts)) {
      formDef.parts = correctParts;
      hasChanges = true;
      issues.push('Parts-Struktur korrigiert');
    }
    
    // 4. Relations prüfen
    if (!formDef.relations || JSON.stringify(formDef.relations) !== JSON.stringify(standardRelations[entity])) {
      formDef.relations = standardRelations[entity];
      hasChanges = true;
      issues.push('Relations korrigiert');
    }
    
    // 5. relationsTitle ganz nach unten verschieben
    const newFormDef = {
      "_comment": formDef._comment,
      "formTitle": formDef.formTitle,
      "parts": formDef.parts,
      "relations": formDef.relations,
      "relationsTitle": formDef.relationsTitle
    };
    
    return { hasChanges, formDef: newFormDef, issues };
    
  } catch (error) {
    return { hasChanges: false, error: error.message };
  }
}

// Hauptfunktion für alle Entitäten
function validateAndFixAllEntities() {
  console.log('🔍 Prüfe und korrigiere alle Formulardefinitionen nach formControl-Konzept...\n');
  
  const entities = ['contract', 'contact', 'realestate', 'mobility', 'financeaccount'];
  const baseDir = './src/main/webapp/app/shared/form-definitions';
  
  let totalFixed = 0;
  let totalErrors = 0;
  let totalSkipped = 0;
  
  for (const entity of entities) {
    const entityDir = path.join(baseDir, entity);
    
    if (!fs.existsSync(entityDir)) {
      console.log(`⚠️  Verzeichnis ${entity} existiert nicht, überspringe...`);
      continue;
    }
    
    console.log(`\n📁 Verarbeite Entität: ${entity.toUpperCase()}`);
    console.log('─'.repeat(50));
    
    const files = fs.readdirSync(entityDir);
    let entityFixed = 0;
    let entityErrors = 0;
    let entitySkipped = 0;
    
    for (const file of files) {
      if (!file.endsWith('.json')) continue;
      
      const filePath = path.join(entityDir, file);
      const fileSize = fs.statSync(filePath).size;
      
      if (fileSize <= 10) {
        console.log(`  ⚠️  ${file} (übersprungen - leer)`);
        entitySkipped++;
        continue;
      }
      
      const result = validateAndFixFormDefinition(filePath, entity);
      
      if (result.error) {
        console.log(`  ❌ ${file} (Fehler: ${result.error})`);
        entityErrors++;
      } else if (result.hasChanges) {
        // Datei speichern
        try {
          fs.writeFileSync(filePath, JSON.stringify(result.formDef, null, 2), 'utf8');
          console.log(`  ✅ ${file} (korrigiert: ${result.issues.join(', ')})`);
          entityFixed++;
        } catch (error) {
          console.log(`  ❌ ${file} (Speicherfehler: ${error.message})`);
          entityErrors++;
        }
      } else {
        console.log(`  ✅ ${file} (bereits korrekt)`);
        entitySkipped++;
      }
    }
    
    console.log(`\n  📊 ${entity}: ${entityFixed} korrigiert, ${entitySkipped} bereits korrekt, ${entityErrors} Fehler`);
    
    totalFixed += entityFixed;
    totalErrors += entityErrors;
    totalSkipped += entitySkipped;
  }
  
  console.log(`\n🎯 GESAMT-ZUSAMMENFASSUNG:`);
  console.log(`✅ Korrigiert: ${totalFixed}`);
  console.log(`✅ Bereits korrekt: ${totalSkipped}`);
  console.log(`❌ Fehler: ${totalErrors}`);
  console.log(`📄 Gesamt: ${totalFixed + totalSkipped + totalErrors}`);
}

// Skript ausführen
validateAndFixAllEntities(); 