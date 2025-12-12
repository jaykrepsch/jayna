const fs = require('fs');
const path = require('path');

// Funktion zum Konvertieren von flachen Schlüsseln zu verschachtelten Objekten
const flattenToNested = (obj) => {
  const result = {};
  
  Object.keys(obj).forEach(key => {
    const keys = key.split('.');
    let current = result;
    
    for (let i = 0; i < keys.length - 1; i++) {
      const k = keys[i];
      if (!current[k] || typeof current[k] !== 'object') {
        current[k] = {};
      }
      current = current[k];
    }
    
    current[keys[keys.length - 1]] = obj[key];
  });
  
  return result;
};

// Lade alle Übersetzungsdateien
const loadTranslations = () => {
  const messages = {
    de: {
      jaynaApp: {}
    }
  };

  // Lade global.json
  const globalPath = path.join(__dirname, 'src/main/webapp/i18n/de/global.json');
  if (fs.existsSync(globalPath)) {
    const globalData = JSON.parse(fs.readFileSync(globalPath, 'utf8'));
    Object.assign(messages.de, globalData);
    console.log('Global.json geladen');
  }

  // Lade alle anderen JSON-Dateien
  const i18nDir = path.join(__dirname, 'src/main/webapp/i18n/de');
  const loadDirectory = (dir, basePath = '') => {
    const files = fs.readdirSync(dir);
    
    files.forEach(file => {
      const filePath = path.join(dir, file);
      const stat = fs.statSync(filePath);
      
      if (stat.isDirectory()) {
        loadDirectory(filePath, basePath + '/' + file);
      } else if (file.endsWith('.json') && file !== 'global.json') {
        try {
          const data = JSON.parse(fs.readFileSync(filePath, 'utf8'));
          const namespace = basePath + '/' + file.replace('.json', '');
          
          // Prüfe, ob die Datei flache Schlüssel hat (mit Punkten)
          const hasFlatKeys = Object.keys(data).some(k => k.includes('.'));
          
          if (hasFlatKeys) {
            // Konvertiere flache Schlüssel zu verschachtelten Objekten
            const nestedData = flattenToNested(data);
            
            // Wenn es ein jaynaApp-Objekt gibt, füge es direkt hinzu
            if (nestedData.jaynaApp) {
              Object.assign(messages.de.jaynaApp, nestedData.jaynaApp);
            } else {
              // Füge alle anderen Übersetzungen hinzu
              Object.assign(messages.de, nestedData);
            }
          } else {
            // Erstelle verschachtelte Struktur basierend auf Dateipfad
            const pathParts = namespace.split('/').filter(p => p);
            let current = messages.de;
            
            for (let i = 0; i < pathParts.length - 1; i++) {
              const part = pathParts[i];
              if (!current[part]) {
                current[part] = {};
              }
              current = current[part];
            }
            
            // Füge die Übersetzungen hinzu
            if (typeof data === 'object' && data !== null) {
              // Wenn es ein jaynaApp-Objekt gibt, füge es direkt hinzu
              if (data.jaynaApp) {
                Object.assign(messages.de.jaynaApp, data.jaynaApp);
              } else {
                // Füge alle anderen Übersetzungen hinzu
                Object.assign(current, data);
              }
            }
          }
          
          console.log(`Datei geladen: ${filePath}`);
        } catch (error) {
          console.warn(`Fehler beim Laden der Datei ${filePath}:`, error);
        }
      }
    });
  };

  loadDirectory(i18nDir);
  
  return messages;
};

// Lade die Übersetzungen
const messages = loadTranslations();

// Teste spezifische Übersetzungen
console.log('\n=== Übersetzungstests ===');
console.log('Contract group-type.car:', messages.de.jaynaApp?.contract?.['group-type']?.car);
console.log('Mobility group-type.off-road-vehicle:', messages.de.jaynaApp?.mobility?.['group-type']?.['off-road-vehicle']);
console.log('Financeaccount subCategory:', messages.de.jaynaApp?.financeaccount?.subCategory);

// Teste die Übersetzungsfunktion
const testTranslation = (key) => {
  const keys = key.split('.');
  let current = messages.de;
  
  for (const k of keys) {
    if (current && current[k] !== undefined) {
      current = current[k];
    } else {
      return null;
    }
  }
  
  return current;
};

console.log('\n=== Übersetzungsfunktion Tests ===');
console.log('jaynaApp.contract.group-type.car:', testTranslation('jaynaApp.contract.group-type.car'));
console.log('jaynaApp.mobility.group-type.off-road-vehicle:', testTranslation('jaynaApp.mobility.group-type.off-road-vehicle'));
console.log('jaynaApp.financeaccount.subCategory:', testTranslation('jaynaApp.financeaccount.subCategory'));

console.log('\n=== Verfügbare Contract group-types ===');
if (messages.de.jaynaApp?.contract?.['group-type']) {
  Object.keys(messages.de.jaynaApp.contract['group-type']).forEach(key => {
    console.log(`${key}: ${messages.de.jaynaApp.contract['group-type'][key]}`);
  });
}

console.log('\n=== Verfügbare Mobility group-types ===');
if (messages.de.jaynaApp?.mobility?.['group-type']) {
  Object.keys(messages.de.jaynaApp.mobility['group-type']).forEach(key => {
    console.log(`${key}: ${messages.de.jaynaApp.mobility['group-type'][key]}`);
  });
}

console.log('\n=== Verfügbare Financeaccount group-types ===');
if (messages.de.jaynaApp?.financeaccount?.['group-type']) {
  Object.keys(messages.de.jaynaApp.financeaccount['group-type']).forEach(key => {
    console.log(`${key}: ${messages.de.jaynaApp.financeaccount['group-type'][key]}`);
  });
}

console.log('\n=== Test spezifischer Übersetzungen ===');
console.log('jaynaApp.contract.group-type.car:', testTranslation('jaynaApp.contract.group-type.car'));
console.log('jaynaApp.mobility.group-type.off-road-vehicle:', testTranslation('jaynaApp.mobility.group-type.off-road-vehicle'));
console.log('jaynaApp.financeaccount.group-type.checking:', testTranslation('jaynaApp.financeaccount.group-type.checking'));

console.log('\n=== Verfügbare jaynaApp Struktur ===');
console.log('Contract:', Object.keys(messages.de.jaynaApp?.contract || {}));
console.log('Mobility:', Object.keys(messages.de.jaynaApp?.mobility || {}));
console.log('Financeaccount:', Object.keys(messages.de.jaynaApp?.financeaccount || {}));

// Lade die global.json
const globalJsonPath = path.join(__dirname, 'src/main/webapp/i18n/de/global.json');
const globalJson = JSON.parse(fs.readFileSync(globalJsonPath, 'utf8'));

console.log('=== TEST DER ÜBERSETZUNGEN IN GLOBAL.JSON ===\n');

// Test der wichtigsten Übersetzungen
const testKeys = [
  'jaynaApp.baseOverview.title',
  'jaynaApp.baseOverview.createTitle',
  'jaynaApp.baseOverview.updateTitle',
  'jaynaApp.baseOverview.subCategory',
  'jaynaApp.baseOverview.subCategoryGroup',
  'jaynaApp.baseOverview.groupType',
  'jaynaApp.contract.createTitle',
  'jaynaApp.contract.subCategory',
  'jaynaApp.realestate.createTitle',
  'jaynaApp.realestate.subCategory',
  'jaynaApp.mobility.createTitle',
  'jaynaApp.mobility.subCategory',
  'jaynaApp.financeaccount.createTitle',
  'jaynaApp.financeaccount.subCategory',
  'jaynaApp.baseOverview.messages.delete.title',
  'jaynaApp.baseOverview.error.insufficient_role.title'
];

console.log('Test der wichtigsten Übersetzungsschlüssel:');
testKeys.forEach(key => {
  const keys = key.split('.');
  let value = globalJson;
  let exists = true;
  
  for (const k of keys) {
    if (value && value[k] !== undefined) {
      value = value[k];
    } else {
      exists = false;
      break;
    }
  }
  
  if (exists) {
    console.log(`✅ ${key}: "${value}"`);
  } else {
    console.log(`❌ ${key}: FEHLT`);
  }
});

console.log('\n=== ENTITY-SPEZIFISCHE TITEL ===');
const entities = ['contract', 'realestate', 'mobility', 'financeaccount'];
entities.forEach(entity => {
  const createTitleKey = `jaynaApp.${entity}.createTitle`;
  const subCategoryKey = `jaynaApp.${entity}.subCategory`;
  
  const keys1 = createTitleKey.split('.');
  let value1 = globalJson;
  let exists1 = true;
  for (const k of keys1) {
    if (value1 && value1[k] !== undefined) {
      value1 = value1[k];
    } else {
      exists1 = false;
      break;
    }
  }
  
  const keys2 = subCategoryKey.split('.');
  let value2 = globalJson;
  let exists2 = true;
  for (const k of keys2) {
    if (value2 && value2[k] !== undefined) {
      value2 = value2[k];
    } else {
      exists2 = false;
      break;
    }
  }
  
  console.log(`${entity}:`);
  console.log(`  createTitle: ${exists1 ? `"${value1}"` : 'FEHLT'}`);
  console.log(`  subCategory: ${exists2 ? `"${value2}"` : 'FEHLT'}`);
});

console.log('\n=== BASE OVERVIEW FELDER ===');
const baseOverviewFields = [
  'title', 'createTitle', 'updateTitle', 'createSubtitle', 'create', 'edit', 'delete',
  'search', 'filter', 'subCategory', 'subCategoryGroup', 'groupType', 'label', 'comment',
  'contractor', 'contractOwner', 'contractUser', 'contractNumber', 'startDate', 'endDate',
  'payment', 'paymentType', 'payer', 'propertyOwner', 'propertyUser', 'propertyNumber',
  'address', 'size', 'price', 'vehicleOwner', 'vehicleUser', 'vehicleNumber', 'color',
  'engine', 'fuelType', 'accountType', 'accountGiver', 'accountOwner', 'accountUser',
  'accountNumber', 'iban', 'bic', 'balance', 'currency', 'openingDate'
];

baseOverviewFields.forEach(field => {
  const key = `jaynaApp.baseOverview.${field}`;
  const keys = key.split('.');
  let value = globalJson;
  let exists = true;
  
  for (const k of keys) {
    if (value && value[k] !== undefined) {
      value = value[k];
    } else {
      exists = false;
      break;
    }
  }
  
  if (exists) {
    console.log(`✅ ${field}: "${value}"`);
  } else {
    console.log(`❌ ${field}: FEHLT`);
  }
});

console.log('\n=== TEST ABGESCHLOSSEN ==='); 