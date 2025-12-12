const fs = require('fs');
const path = require('path');

// Konfiguration
const config = {
  i18nPath: 'src/main/webapp/i18n/de',
  backendI18nPath: 'src/main/resources/i18n',
  formDefinitionsPath: 'src/main/webapp/app/shared/form-definitions'
};

// Standard-Felder für jede Entität
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

// Lade eine JSON-Datei
function loadJsonFile(filePath) {
  try {
    const content = fs.readFileSync(filePath, 'utf8');
    return JSON.parse(content);
  } catch (error) {
    console.error(`❌ Fehler beim Laden von ${filePath}:`, error.message);
    return null;
  }
}

// Speichere eine JSON-Datei
function saveJsonFile(filePath, data) {
  try {
    fs.writeFileSync(filePath, JSON.stringify(data, null, 2), 'utf8');
    return true;
  } catch (error) {
    console.error(`❌ Fehler beim Speichern von ${filePath}:`, error.message);
    return false;
  }
}

// Validiere Base-Übersetzungen
function validateBaseTranslations(entity) {
  console.log(`\n🔍 Validiere Base-Übersetzungen für ${entity}...`);
  
  const basePath = path.join(config.i18nPath, entity);
  const baseFile = path.join(basePath, `base${entity.charAt(0).toUpperCase() + entity.slice(1)}.json`);
  const baseOverviewFile = path.join(basePath, 'baseOverview.json');
  
  let hasChanges = false;
  
  // Validiere Base-Entitäts-Datei
  if (fs.existsSync(baseFile)) {
    const baseData = loadJsonFile(baseFile);
    if (baseData) {
      const entityFields = entitySpecificFields[entity];
      if (entityFields) {
        Object.keys(entityFields).forEach(sectionKey => {
          const sectionFields = entityFields[sectionKey];
          sectionFields.forEach(field => {
            const key = `jaynaApp.base${entity.charAt(0).toUpperCase() + entity.slice(1)}.${sectionKey}.fields.${field.name}`;
            if (!baseData[key]) {
              console.log(`  ➕ Füge fehlenden Schlüssel hinzu: ${key}`);
              baseData[key] = field.name.charAt(0).toUpperCase() + field.name.slice(1).replace(/([A-Z])/g, ' $1').trim();
              hasChanges = true;
            }
          });
        });
      }
      
      if (hasChanges) {
        saveJsonFile(baseFile, baseData);
        console.log(`  ✅ Base-Übersetzungen für ${entity} aktualisiert`);
      } else {
        console.log(`  ✅ Base-Übersetzungen für ${entity} sind vollständig`);
      }
    }
  }
  
  // Validiere Base-Overview-Datei
  if (fs.existsSync(baseOverviewFile)) {
    const overviewData = loadJsonFile(baseOverviewFile);
    if (overviewData) {
      const requiredKeys = [
        'title', 'updateTitle', 'createTitle', 'createSubtitle', 'create', 'edit', 'delete',
        'search', 'filter', 'subCategory', 'subCategoryGroup', 'groupType', 'label', 'comment',
        'messages.delete.title', 'messages.delete.content', 'error.insufficient_role.title', 'error.insufficient_role.message'
      ];
      
      requiredKeys.forEach(key => {
        const fullKey = `jaynaApp.baseOverview.${key}`;
        if (!overviewData[fullKey]) {
          console.log(`  ➕ Füge fehlenden Schlüssel hinzu: ${fullKey}`);
          overviewData[fullKey] = key.includes('title') ? 'Titel' : key.includes('content') ? 'Inhalt' : key.includes('message') ? 'Nachricht' : key;
          hasChanges = true;
        }
      });
      
      if (hasChanges) {
        saveJsonFile(baseOverviewFile, overviewData);
        console.log(`  ✅ Base-Overview-Übersetzungen für ${entity} aktualisiert`);
      } else {
        console.log(`  ✅ Base-Overview-Übersetzungen für ${entity} sind vollständig`);
      }
    }
  }
}

// Validiere spezifische Übersetzungsdateien
function validateSpecificTranslations(entity) {
  console.log(`\n🔍 Validiere spezifische Übersetzungen für ${entity}...`);
  
  const entityPath = path.join(config.i18nPath, entity);
  const formDefPath = path.join(config.formDefinitionsPath, entity);
  
  if (!fs.existsSync(entityPath) || !fs.existsSync(formDefPath)) {
    console.log(`  ⚠️  Pfad nicht gefunden für ${entity}`);
    return;
  }
  
  const i18nFiles = fs.readdirSync(entityPath).filter(file => file.startsWith('i18n-') && file.endsWith('.json'));
  const formDefFiles = fs.readdirSync(formDefPath).filter(file => file.endsWith('.json'));
  
  i18nFiles.forEach(i18nFile => {
    const i18nPath = path.join(entityPath, i18nFile);
    const i18nData = loadJsonFile(i18nPath);
    
    if (i18nData) {
      // Extrahiere den Formulardefinitionsnamen aus dem i18n-Dateinamen
      const formDefName = i18nFile.replace('i18n-', '').replace('.json', '');
      const formDefFile = formDefFiles.find(file => file.startsWith(formDefName));
      
      if (formDefFile) {
        const formDefPath = path.join(config.formDefinitionsPath, entity, formDefFile);
        const formDefData = loadJsonFile(formDefPath);
        
        if (formDefData) {
          // Validiere, dass alle in der Formulardefinition verwendeten Übersetzungsschlüssel vorhanden sind
          validateFormDefinitionTranslations(formDefData, i18nData, formDefName);
        }
      }
    }
  });
}

// Validiere Übersetzungen in Formulardefinitionen
function validateFormDefinitionTranslations(formDef, i18nData, formName) {
  let hasIssues = false;
  
  // Prüfe formTitle
  if (formDef.formTitle && !i18nData[formDef.formTitle]) {
    console.log(`  ⚠️  Fehlender Übersetzungsschlüssel: ${formDef.formTitle} in ${formName}`);
    hasIssues = true;
  }
  
  // Prüfe sections
  if (formDef.sections) {
    formDef.sections.forEach(section => {
      if (section.title && !i18nData[section.title]) {
        console.log(`  ⚠️  Fehlender Übersetzungsschlüssel: ${section.title} in ${formName}`);
        hasIssues = true;
      }
      
      if (section.fields) {
        section.fields.forEach(field => {
          if (field.label && !i18nData[field.label]) {
            console.log(`  ⚠️  Fehlender Übersetzungsschlüssel: ${field.label} in ${formName}`);
            hasIssues = true;
          }
        });
      }
    });
  }
  
  // Prüfe relationsTitle
  if (formDef.relationsTitle && !i18nData[formDef.relationsTitle]) {
    console.log(`  ⚠️  Fehlender Übersetzungsschlüssel: ${formDef.relationsTitle} in ${formName}`);
    hasIssues = true;
  }
  
  if (!hasIssues) {
    console.log(`  ✅ Alle Übersetzungen für ${formName} sind vorhanden`);
  }
}

// Validiere globale Übersetzungen
function validateGlobalTranslations() {
  console.log('\n🔍 Validiere globale Übersetzungen...');
  
  const globalFile = path.join(config.i18nPath, 'global.json');
  const globalData = loadJsonFile(globalFile);
  
  if (globalData) {
    // Prüfe, ob alle benötigten globalen Schlüssel vorhanden sind
    const requiredGlobalKeys = [
      'global.title', 'global.menu.choose', 'global.menu.home', 'global.menu.account.login',
      'global.menu.account.register', 'global.menu.account.settings', 'global.menu.account.password',
      'global.menu.account.logout', 'global.label.yes', 'global.label.no', 'global.item-count',
      'login.form.username.label', 'login.form.password', 'login.form.rememberme', 'login.form.button',
      'login.password.forgot', 'login.messages.error.authentication.header', 'login.messages.error.authentication.message',
      'button.close', 'button.cancel', 'button.save', 'button.delete', 'button.show', 'button.edit',
      'button.add', 'button.continue', 'button.login', 'button.register', 'button.confirm',
      'entity.action.back', 'entity.action.edit', 'entity.action.save', 'entity.action.cancel',
      'entity.detail.createdDate', 'error.modal.title', 'error.modal.content', 'error.http.500',
      'error.http.403', 'error.http.404', 'error.title'
    ];
    
    let hasChanges = false;
    requiredGlobalKeys.forEach(key => {
      if (!getNestedValue(globalData, key)) {
        console.log(`  ➕ Füge fehlenden globalen Schlüssel hinzu: ${key}`);
        setNestedValue(globalData, key, key.split('.').pop().replace(/([A-Z])/g, ' $1').trim());
        hasChanges = true;
      }
    });
    
    if (hasChanges) {
      saveJsonFile(globalFile, globalData);
      console.log('  ✅ Globale Übersetzungen aktualisiert');
    } else {
      console.log('  ✅ Globale Übersetzungen sind vollständig');
    }
  }
}

// Hilfsfunktion zum Abrufen verschachtelter Werte
function getNestedValue(obj, path) {
  return path.split('.').reduce((current, key) => current && current[key], obj);
}

// Hilfsfunktion zum Setzen verschachtelter Werte
function setNestedValue(obj, path, value) {
  const keys = path.split('.');
  const lastKey = keys.pop();
  const target = keys.reduce((current, key) => {
    if (!current[key]) current[key] = {};
    return current[key];
  }, obj);
  target[lastKey] = value;
}

// Hauptfunktion
function validateAllTranslations() {
  console.log('🚀 Starte Validierung aller Übersetzungen...\n');
  
  const entities = ['contract', 'contact', 'mobility', 'realestate', 'financeaccount'];
  
  // Validiere globale Übersetzungen
  validateGlobalTranslations();
  
  // Validiere Base-Übersetzungen für jede Entität
  entities.forEach(entity => {
    validateBaseTranslations(entity);
  });
  
  // Validiere spezifische Übersetzungen für jede Entität
  entities.forEach(entity => {
    validateSpecificTranslations(entity);
  });
  
  console.log('\n✅ Validierung aller Übersetzungen abgeschlossen!');
}

// Führe die Validierung aus
validateAllTranslations(); 