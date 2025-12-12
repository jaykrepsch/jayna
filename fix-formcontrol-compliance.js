const fs = require('fs');
const path = require('path');

// Extrahiere den Formulardefinitionsnamen aus dem Dateinamen
function extractFormName(fileName) {
  return fileName.replace('.json', '');
}

// Konvertiere camelCase zu kebab-case für i18nSource
function toKebabCase(str) {
  return str.replace(/([a-z0-9]|(?=[A-Z]))([A-Z])/g, '$1-$2').toLowerCase();
}

// Prüfe ob ein Abschnitt ein BaseContract-Abschnitt ist
function isBaseContractSection(title) {
  if (!title) return false;
  return title.includes('baseContract.') || 
         title.includes('general.title') ||
         title.includes('contractData.title') ||
         title.includes('payment.title') ||
         title.includes('weiteres.title');
}

// Konvertiere einen Abschnittstitel zu BaseContract-Format
function convertToBaseContractTitle(title) {
  if (!title) return title;
  
  // Entferne den spezifischen Präfix und ersetze durch baseContract
  const sectionName = title.split('.').pop();
  if (sectionName === 'title') {
    const section = title.split('.').slice(-2, -1)[0];
    return `jaynaApp.baseContract.${section}.title`;
  }
  return title;
}

// Konvertiere einen Abschnittstitel zu spezifischem Format
function convertToSpecificTitle(title, formName) {
  if (!title) return title;
  
  // Extrahiere den Abschnittsnamen
  const parts = title.split('.');
  if (parts.length >= 2) {
    const sectionName = parts[parts.length - 2];
    return `jaynaApp.${formName}.${sectionName}.title`;
  }
  return title;
}

// Formulardefinition nach formControl-Konzept anpassen
function fixFormControlCompliance(filePath) {
  try {
    const content = fs.readFileSync(filePath, 'utf8');
    const formDef = JSON.parse(content);
    const fileName = path.basename(filePath);
    const formName = extractFormName(fileName);
    
    let hasChanges = false;
    
    // 1. _comment korrigieren
    if (!formDef._comment || !formDef._comment.includes('formControl-Konzept')) {
      formDef._comment = 'formControl-Konzept: Standard-Abschnitte aus BaseContract, spezielle Abschnitte mit i18nSource';
      hasChanges = true;
    }
    
    // 2. relationsTitle korrigieren
    if (!formDef.relationsTitle) {
      formDef.relationsTitle = `jaynaApp.${formName}.relations-title`;
      hasChanges = true;
    }
    
    // 3. Parts durchgehen und korrigieren
    if (formDef.parts && Array.isArray(formDef.parts)) {
      for (const section of formDef.parts) {
        if (section.title) {
          const isBaseContract = isBaseContractSection(section.title);
          
          if (isBaseContract) {
            // BaseContract-Abschnitt
            const newTitle = convertToBaseContractTitle(section.title);
            if (newTitle !== section.title) {
              section.title = newTitle;
              hasChanges = true;
            }
            // i18nSource entfernen falls vorhanden
            if (section.i18nSource) {
              delete section.i18nSource;
              hasChanges = true;
            }
          } else {
            // Spezifischer Abschnitt
            const newTitle = convertToSpecificTitle(section.title, formName);
            if (newTitle !== section.title) {
              section.title = newTitle;
              hasChanges = true;
            }
            // i18nSource hinzufügen falls fehlend
            if (!section.i18nSource) {
              section.i18nSource = formName;
              hasChanges = true;
            }
          }
        }
      }
    }
    
    // 4. Relations prüfen
    if (!formDef.relations || !Array.isArray(formDef.relations)) {
      formDef.relations = [
        { entityName: "contact", relationName: "contactContracts" },
        { entityName: "realestate", relationName: "contractRealestates" },
        { entityName: "financeaccount", relationName: "contractFinanceAccounts" },
        { entityName: "mobility", relationName: "contractMobilities" },
        { entityName: "contract", relationName: "contractContracts" }
      ];
      hasChanges = true;
    }
    
    // 5. formTitle prüfen
    if (!formDef.formTitle) {
      formDef.formTitle = `jaynaApp.${formName}.title`;
      hasChanges = true;
    }
    
    return { hasChanges, formDef };
    
  } catch (error) {
    return { hasChanges: false, error: error.message };
  }
}

// Hauptfunktion
function fixAllFormDefinitions() {
  console.log('🔧 Passe alle Formulardefinitionen an formControl-Konzept an...\n');
  
  const formDefinitionsDir = './src/main/webapp/app/shared/form-definitions/contract';
  const files = fs.readdirSync(formDefinitionsDir);
  
  let fixedCount = 0;
  let errorCount = 0;
  let skipCount = 0;
  
  for (const file of files) {
    if (!file.endsWith('.json')) continue;
    
    const filePath = path.join(formDefinitionsDir, file);
    const fileSize = fs.statSync(filePath).size;
    
    if (fileSize <= 10) {
      console.log(`⚠️  ${file} (übersprungen - leer)`);
      skipCount++;
      continue;
    }
    
    const result = fixFormControlCompliance(filePath);
    
    if (result.error) {
      console.log(`❌ ${file} (Fehler: ${result.error})`);
      errorCount++;
    } else if (result.hasChanges) {
      // Datei speichern
      try {
        fs.writeFileSync(filePath, JSON.stringify(result.formDef, null, 2), 'utf8');
        console.log(`✅ ${file} (angepasst)`);
        fixedCount++;
      } catch (error) {
        console.log(`❌ ${file} (Speicherfehler: ${error.message})`);
        errorCount++;
      }
    } else {
      console.log(`✅ ${file} (bereits konform)`);
      skipCount++;
    }
  }
  
  console.log(`\n📊 Zusammenfassung:`);
  console.log(`✅ Angepasst: ${fixedCount}`);
  console.log(`✅ Bereits konform: ${skipCount}`);
  console.log(`❌ Fehler: ${errorCount}`);
  console.log(`📄 Gesamt: ${fixedCount + skipCount + errorCount}`);
}

// Skript ausführen
fixAllFormDefinitions(); 