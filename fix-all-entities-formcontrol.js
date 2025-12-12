const fs = require('fs');
const path = require('path');

// Extrahiere den Formulardefinitionsnamen aus dem Dateinamen
function extractFormName(fileName) {
  return fileName.replace('.json', '');
}

// Prüfe ob ein Abschnitt ein BaseContract-Abschnitt ist
function isBaseContractSection(title, entity) {
  if (!title) return false;
  
  // Für jede Entität gibt es Standard-Abschnitte
  const baseSections = {
    'contract': ['baseContract.', 'general.title', 'contractData.title', 'payment.title', 'weiteres.title'],
    'contact': ['baseContact.', 'general.title', 'contactData.title', 'address.title', 'communication.title'],
    'realestate': ['baseRealestate.', 'general.title', 'realestateData.title', 'address.title', 'details.title'],
    'mobility': ['baseMobility.', 'general.title', 'mobilityData.title', 'vehicle.title', 'insurance.title'],
    'financeaccount': ['baseFinanceaccount.', 'general.title', 'accountData.title', 'bank.title', 'details.title']
  };
  
  const sections = baseSections[entity] || [];
  return sections.some(section => title.includes(section));
}

// Konvertiere einen Abschnittstitel zu Base-Format
function convertToBaseTitle(title, entity) {
  if (!title) return title;
  
  const sectionName = title.split('.').pop();
  if (sectionName === 'title') {
    const section = title.split('.').slice(-2, -1)[0];
    return `jaynaApp.base${entity.charAt(0).toUpperCase() + entity.slice(1)}.${section}.title`;
  }
  return title;
}

// Konvertiere einen Abschnittstitel zu spezifischem Format
function convertToSpecificTitle(title, formName) {
  if (!title) return title;
  
  const parts = title.split('.');
  if (parts.length >= 2) {
    const sectionName = parts[parts.length - 2];
    return `jaynaApp.${formName}.${sectionName}.title`;
  }
  return title;
}

// Standard-Relations für jede Entität
function getStandardRelations(entity) {
  const relations = {
    'contract': [
      { entityName: "contact", relationName: "contactContracts" },
      { entityName: "realestate", relationName: "contractRealestates" },
      { entityName: "financeaccount", relationName: "contractFinanceAccounts" },
      { entityName: "mobility", relationName: "contractMobilities" },
      { entityName: "contract", relationName: "contractContracts" }
    ],
    'contact': [
      { entityName: "contract", relationName: "contactContracts" },
      { entityName: "realestate", relationName: "contactRealestates" },
      { entityName: "financeaccount", relationName: "contactFinanceAccounts" },
      { entityName: "mobility", relationName: "contactMobilities" }
    ],
    'realestate': [
      { entityName: "contact", relationName: "realestateContacts" },
      { entityName: "contract", relationName: "realestateContracts" },
      { entityName: "financeaccount", relationName: "realestateFinanceAccounts" }
    ],
    'mobility': [
      { entityName: "contact", relationName: "mobilityContacts" },
      { entityName: "contract", relationName: "mobilityContracts" },
      { entityName: "financeaccount", relationName: "mobilityFinanceAccounts" }
    ],
    'financeaccount': [
      { entityName: "contact", relationName: "financeaccountContacts" },
      { entityName: "contract", relationName: "financeaccountContracts" },
      { entityName: "realestate", relationName: "financeaccountRealestates" },
      { entityName: "mobility", relationName: "financeaccountMobilities" }
    ]
  };
  
  return relations[entity] || [];
}

// Die Basisabschnitte für jede Entität werden jetzt explizit als Set gepflegt, damit die Unterscheidung eindeutig ist
const baseSectionsMap = {
  contract: new Set(['general', 'contractData', 'payment', 'weiteres']),
  contact: new Set(['general', 'contactData', 'address', 'communication']),
  realestate: new Set(['general', 'realestateData', 'address', 'details', 'weiteres', 'carport', 'plot']),
  mobility: new Set(['general', 'mobilityData', 'vehicle', 'insurance']),
  financeaccount: new Set(['general', 'accountData', 'bank', 'details'])
};

function getSectionKeyFromTitle(title) {
  if (!title) return '';
  const parts = title.split('.');
  // z.B. jaynaApp.baseContract.contractData.title → contractData
  if (parts.length >= 3) return parts[2];
  if (parts.length >= 2) return parts[1];
  return '';
}

// Prüfe ob ein Abschnitt ein Standard-Abschnitt ist basierend auf Inhalt
function isStandardSection(section, entity) {
  if (!section.fields || !Array.isArray(section.fields)) return false;
  
  // Adress-Felder erkennen
  const addressFields = ['street', 'streetNumber', 'postalCode', 'city', 'state', 'country', 'longitude', 'latitude'];
  const hasAddressFields = section.fields.some(field => addressFields.includes(field.name));
  
  // Allgemeine Felder erkennen
  const generalFields = ['label', 'name', 'description', 'notes'];
  const hasGeneralFields = section.fields.some(field => generalFields.includes(field.name));
  
  // Plot-Felder erkennen (für Realestate)
  const plotFields = ['area', 'builtUpArea', 'sealtArea', 'undevelopedArea'];
  const hasPlotFields = section.fields.some(field => plotFields.includes(field.name));
  
  if (entity === 'realestate') {
    if (hasAddressFields) return 'address';
    if (hasPlotFields) return 'plot';
    if (hasGeneralFields && section.fields.length <= 3) return 'general';
  }
  
  return false;
}

// Formulardefinition nach formControl-Konzept anpassen
function fixFormControlCompliance(filePath, entity) {
  try {
    const content = fs.readFileSync(filePath, 'utf8');
    const formDef = JSON.parse(content);
    const fileName = path.basename(filePath);
    const formName = extractFormName(fileName);
    let hasChanges = false;

    // 1. _comment
    if (!formDef._comment || !formDef._comment.includes('formControl-Konzept')) {
      formDef._comment = `formControl-Konzept: Standard-Abschnitte aus Base${entity.charAt(0).toUpperCase() + entity.slice(1)}, spezielle Abschnitte mit i18nSource`;
      hasChanges = true;
    }

    // 2. relationsTitle
    if (!formDef.relationsTitle) {
      formDef.relationsTitle = `jaynaApp.${formName}.relations-title`;
      hasChanges = true;
    }

    // 3. Parts prüfen und anpassen
    if (formDef.parts && Array.isArray(formDef.parts)) {
      const baseSections = baseSectionsMap[entity] || new Set();
      for (const section of formDef.parts) {
        if (section.title) {
          // Abschnittsname extrahieren
          let sectionKey = getSectionKeyFromTitle(section.title);
          // Fallback: Wenn title kein base... enthält, dann aus dem formName ableiten
          if (!sectionKey && section.name) sectionKey = section.name;
          
          // Prüfe ob es ein Standard-Abschnitt basierend auf Inhalt ist
          const standardSectionType = isStandardSection(section, entity);
          if (standardSectionType) {
            sectionKey = standardSectionType;
          }

          if (baseSections.has(sectionKey)) {
            // Allgemeiner Abschnitt
            const newTitle = `jaynaApp.base${entity.charAt(0).toUpperCase() + entity.slice(1)}.${sectionKey}.title`;
            if (section.title !== newTitle) {
              section.title = newTitle;
              hasChanges = true;
            }
            if (section.i18nSource) {
              delete section.i18nSource;
              hasChanges = true;
            }
          } else {
            // Spezifischer Abschnitt
            const newTitle = `jaynaApp.${formName}.${sectionKey}.title`;
            if (section.title !== newTitle) {
              section.title = newTitle;
              hasChanges = true;
            }
            if (!section.i18nSource) {
              section.i18nSource = formName;
              hasChanges = true;
            }
          }
        }
      }
    }

    // 4. Relations
    if (!formDef.relations || !Array.isArray(formDef.relations)) {
      formDef.relations = getStandardRelations(entity);
      hasChanges = true;
    }

    // 5. formTitle
    if (!formDef.formTitle) {
      formDef.formTitle = `jaynaApp.${formName}.title`;
      hasChanges = true;
    }

    return { hasChanges, formDef };
  } catch (error) {
    return { hasChanges: false, error: error.message };
  }
}

// Hauptfunktion für alle Entitäten
function fixAllEntitiesFormDefinitions() {
  console.log('🔧 Passe alle Formulardefinitionen aller Entitäten an formControl-Konzept an...\n');
  
  const baseDir = './src/main/webapp/app/shared/form-definitions';
  const entities = ['contract', 'contact', 'realestate', 'mobility', 'financeaccount'];
  
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
      
      const result = fixFormControlCompliance(filePath, entity);
      
      if (result.error) {
        console.log(`  ❌ ${file} (Fehler: ${result.error})`);
        entityErrors++;
      } else if (result.hasChanges) {
        // Datei speichern
        try {
          fs.writeFileSync(filePath, JSON.stringify(result.formDef, null, 2), 'utf8');
          console.log(`  ✅ ${file} (angepasst)`);
          entityFixed++;
        } catch (error) {
          console.log(`  ❌ ${file} (Speicherfehler: ${error.message})`);
          entityErrors++;
        }
      } else {
        console.log(`  ✅ ${file} (bereits konform)`);
        entitySkipped++;
      }
    }
    
    console.log(`\n  📊 ${entity}: ${entityFixed} angepasst, ${entitySkipped} bereits konform, ${entityErrors} Fehler`);
    
    totalFixed += entityFixed;
    totalErrors += entityErrors;
    totalSkipped += entitySkipped;
  }
  
  console.log(`\n🎯 GESAMT-ZUSAMMENFASSUNG:`);
  console.log(`✅ Angepasst: ${totalFixed}`);
  console.log(`✅ Bereits konform: ${totalSkipped}`);
  console.log(`❌ Fehler: ${totalErrors}`);
  console.log(`📄 Gesamt: ${totalFixed + totalSkipped + totalErrors}`);
}

// Skript ausführen
fixAllEntitiesFormDefinitions(); 