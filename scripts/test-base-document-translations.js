const fs = require('fs');
const path = require('path');

// Simuliere die Vue i18n Übersetzungsfunktion
function t(key) {
  // Lade die Übersetzungen aus messages_de.properties
  const messagesDePath = path.join(__dirname, '../src/main/resources/i18n/messages_de.properties');
  const content = fs.readFileSync(messagesDePath, 'utf8');
  const translations = {};
  
  content.split('\n').forEach(line => {
    line = line.trim();
    if (line && !line.startsWith('#') && line.includes('=')) {
      const [k, v] = line.split('=', 2);
      translations[k.trim()] = v.trim();
    }
  });
  
  return translations[key] || key;
}

// Teste baseDocument-Übersetzungen
console.log('🔍 Teste baseDocument-Übersetzungen...\n');

const testKeys = [
  // Allgemeine Felder
  'jaynaApp.baseDocument.label',
  'jaynaApp.baseDocument.description',
  'jaynaApp.baseDocument.entityState',
  
  // Dokumentdaten
  'jaynaApp.baseDocument.documentNumber',
  'jaynaApp.baseDocument.customerNumber',
  'jaynaApp.baseDocument.partner',
  'jaynaApp.baseDocument.contractNumber',
  'jaynaApp.baseDocument.realEstateNumber',
  'jaynaApp.baseDocument.financeAccountNumber',
  'jaynaApp.baseDocument.mobilityNumber',
  'jaynaApp.baseDocument.status',
  
  // Weiteres
  'jaynaApp.baseDocument.tags',
  'jaynaApp.baseDocument.keywords',
  'jaynaApp.baseDocument.isPublic',
  'jaynaApp.baseDocument.expiryDate',
  
  // Dateiinformationen
  'jaynaApp.baseDocument.fileName',
  'jaynaApp.baseDocument.fileType',
  'jaynaApp.baseDocument.fileSize',
  'jaynaApp.baseDocument.uploadDate',
  'jaynaApp.baseDocument.lastModified',
  
  // Abschnitt-Titel
  'jaynaApp.baseDocument.general.title',
  'jaynaApp.baseDocument.documentData.title',
  'jaynaApp.baseDocument.weiteres.title',
  'jaynaApp.baseDocument.fileInfo.title',
  'jaynaApp.baseDocument.relations.title'
];

testKeys.forEach(key => {
  const translation = t(key);
  if (translation === key) {
    console.log(`❌ ${key} -> KEINE ÜBERSETZUNG GEFUNDEN`);
  } else {
    console.log(`✅ ${key} -> ${translation}`);
  }
});

console.log('\n📊 Zusammenfassung:');
const totalKeys = testKeys.length;
const foundTranslations = testKeys.filter(key => t(key) !== key).length;
const missingTranslations = totalKeys - foundTranslations;

console.log(`- Getestete Schlüssel: ${totalKeys}`);
console.log(`- Gefundene Übersetzungen: ${foundTranslations}`);
console.log(`- Fehlende Übersetzungen: ${missingTranslations}`);

if (missingTranslations === 0) {
  console.log('\n✅ Alle baseDocument-Übersetzungen sind verfügbar!');
} else {
  console.log('\n⚠️  Einige baseDocument-Übersetzungen fehlen noch.');
}
