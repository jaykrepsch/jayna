const fs = require('fs');
const path = require('path');

// Extrahiere den Formulardefinitionsnamen aus dem Dateinamen
function extractFormName(fileName) {
  return fileName.replace('.json', '');
}

// Erstelle spezifische Felder basierend auf dem Entitätstyp
function getCategoryFields(formName, entity) {
  const categoryFields = {
    // CONTACT
    'contact-company-business-businesscontact': [
      { name: "companyType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "companyType" },
      { name: "industry", type: "dropdown", boxLabel: "global.menu.choose", enumName: "industry" },
      { name: "employeeCount", type: "number" },
      { name: "taxNumber", type: "text" }
    ],
    'contact-group-extended-extendedcontact': [
      { name: "groupType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "groupType" },
      { name: "memberCount", type: "number" },
      { name: "groupPurpose", type: "text" }
    ],
    'contact-group-group-groupcontact': [
      { name: "groupType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "groupType" },
      { name: "memberCount", type: "number" }
    ],
    'contact-single-contact-contact': [
      { name: "contactType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "contactType" },
      { name: "relationship", type: "text" }
    ],
    'contact-single-family-familycontact': [
      { name: "familyType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "familyType" },
      { name: "memberCount", type: "number" },
      { name: "childrenCount", type: "number" }
    ],
    'contact-single-simple-simplecontact': [
      { name: "contactType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "contactType" }
    ],
    'contact-single-single-singlecontact': [
      { name: "contactType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "contactType" },
      { name: "relationship", type: "text" }
    ],

    // FINANCEACCOUNT
    'financeaccount-bank-charge-charge': [
      { name: "chargeType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "chargeType" },
      { name: "chargeAmount", type: "number" },
      { name: "chargeFrequency", type: "dropdown", boxLabel: "global.menu.choose", enumName: "chargeFrequency" }
    ],
    'financeaccount-bank-checkingaccount-checkingaccount': [
      { name: "accountType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "accountType" },
      { name: "overdraftLimit", type: "number" },
      { name: "monthlyFee", type: "number" }
    ],
    'financeaccount-bank-savingaccount-savingaccount': [
      { name: "savingsType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "savingsType" },
      { name: "interestRate", type: "number" },
      { name: "minimumBalance", type: "number" }
    ],
    'financeaccount-systems-paypal-paypal': [
      { name: "paypalType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "paypalType" },
      { name: "email", type: "text" },
      { name: "verificationStatus", type: "dropdown", boxLabel: "global.menu.choose", enumName: "verificationStatus" }
    ],
    'financeaccount-systems-samsungpay-samsungpay': [
      { name: "deviceType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "deviceType" },
      { name: "cardCount", type: "number" }
    ],

    // MOBILITY
    'mobility-passenger-car-car': [
      { name: "carType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "carType" },
      { name: "brand", type: "text" },
      { name: "model", type: "text" },
      { name: "year", type: "number" },
      { name: "fuelType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "fuelType" }
    ],
    'mobility-single-single-singlemobility': [
      { name: "mobilityType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "mobilityType" },
      { name: "category", type: "text" }
    ]
  };
  
  return categoryFields[formName] || [
    { name: "type", type: "dropdown", boxLabel: "global.menu.choose", enumName: `${entity}Type` },
    { name: "category", type: "text" }
  ];
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
  contact: [
    { entityName: "contract", relationName: "contactContracts" },
    { entityName: "realestate", relationName: "contactRealestates" },
    { entityName: "financeaccount", relationName: "contactFinanceAccounts" },
    { entityName: "mobility", relationName: "contactMobilities" }
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

// Formulardefinition nach formControl-Konzept strukturieren
function structureEntityForm(filePath, entity) {
  try {
    const fileName = path.basename(filePath);
    const formName = extractFormName(fileName);
    
    // Neue Struktur erstellen
    const newFormDef = {
      "_comment": `formControl-Konzept: Standard-Abschnitte aus Base${entity.charAt(0).toUpperCase() + entity.slice(1)}, spezielle Abschnitte mit i18nSource`,
      "formTitle": formName.replace(/-/g, ' ').replace(/\b\w/g, l => l.toUpperCase()),
      "parts": [
        {
          "title": `jaynaApp.base${entity.charAt(0).toUpperCase() + entity.slice(1)}.general.title`,
          "fields": standardFields.general
        }
      ],
      "relations": standardRelations[entity] || [],
      "relationsTitle": `jaynaApp.${formName}.relations-title`
    };

    // Entitätsspezifische Abschnitte hinzufügen
    const entityFields = entitySpecificFields[entity];
    if (entityFields) {
      Object.keys(entityFields).forEach(sectionKey => {
        newFormDef.parts.push({
          "title": `jaynaApp.base${entity.charAt(0).toUpperCase() + entity.slice(1)}.${sectionKey}.title`,
          "fields": entityFields[sectionKey]
        });
      });
    }

    // Weiteres-Abschnitt hinzufügen
    newFormDef.parts.push({
      "title": `jaynaApp.base${entity.charAt(0).toUpperCase() + entity.slice(1)}.weiteres.title`,
      "fields": standardFields.weiteres
    });
    
    return { hasChanges: true, formDef: newFormDef };
    
  } catch (error) {
    return { hasChanges: false, error: error.message };
  }
}

// Hauptfunktion für alle Entitäten
function fixAllEntitiesForms() {
  console.log('🔧 Strukturiere alle Formulardefinitionen nach formControl-Konzept...\n');
  
  const entities = ['contact', 'financeaccount', 'mobility'];
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
      
      const result = structureEntityForm(filePath, entity);
      
      if (result.error) {
        console.log(`  ❌ ${file} (Fehler: ${result.error})`);
        entityErrors++;
      } else if (result.hasChanges) {
        // Datei speichern
        try {
          fs.writeFileSync(filePath, JSON.stringify(result.formDef, null, 2), 'utf8');
          console.log(`  ✅ ${file} (strukturiert)`);
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
    
    console.log(`\n  📊 ${entity}: ${entityFixed} strukturiert, ${entitySkipped} bereits korrekt, ${entityErrors} Fehler`);
    
    totalFixed += entityFixed;
    totalErrors += entityErrors;
    totalSkipped += entitySkipped;
  }
  
  console.log(`\n🎯 GESAMT-ZUSAMMENFASSUNG:`);
  console.log(`✅ Strukturiert: ${totalFixed}`);
  console.log(`✅ Bereits korrekt: ${totalSkipped}`);
  console.log(`❌ Fehler: ${totalErrors}`);
  console.log(`📄 Gesamt: ${totalFixed + totalSkipped + totalErrors}`);
}

// Skript ausführen
fixAllEntitiesForms(); 