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

// Teste einige Übersetzungen
console.log('🔍 Teste Übersetzungen...\n');

const testKeys = [
  'jaynaApp.document.title',
  'jaynaApp.document.description',
  'jaynaApp.document.documentNumber',
  'jaynaApp.document.customerNumber',
  'jaynaApp.document.partner',
  'jaynaApp.document.contractNumber',
  'jaynaApp.document.realEstateNumber',
  'jaynaApp.document.financeAccountNumber',
  'jaynaApp.document.mobilityNumber',
  'jaynaApp.document.entityState',
  'jaynaApp.document.status',
  'jaynaApp.document.documentType',
  'jaynaApp.document.mandateReference',
  'jaynaApp.document.creditorName',
  'jaynaApp.document.creditorAddress',
  'jaynaApp.document.creditorPostalCode',
  'jaynaApp.document.creditorCity',
  'jaynaApp.document.creditorIdentifier',
  'jaynaApp.document.debtorName',
  'jaynaApp.document.debtorStreet',
  'jaynaApp.document.debtorPostalCode',
  'jaynaApp.document.debtorCity',
  'jaynaApp.document.bankName',
  'jaynaApp.document.iban',
  'jaynaApp.document.bic',
  'jaynaApp.document.refundPeriod',
  'jaynaApp.document.refundStartPoint',
  'jaynaApp.document.authorizationText',
  'jaynaApp.document.bankInstruction',
  'jaynaApp.document.amount',
  'jaynaApp.document.currency',
  'jaynaApp.baseDocument.general.title',
  'jaynaApp.baseDocument.documentData.title',
  'jaynaApp.baseDocument.fileInfo.title',
  'jaynaApp.baseDocument.weiteres.title',
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
  console.log('\n✅ Alle Übersetzungen sind verfügbar!');
} else {
  console.log('\n⚠️  Einige Übersetzungen fehlen noch.');
}
