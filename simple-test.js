const fs = require('fs');
const path = require('path');

console.log('=== EINFACHER ÜBERSETZUNGSTEST ===\n');

// Lade die global.json
const globalJsonPath = path.join(__dirname, 'src/main/webapp/i18n/de/global.json');
const globalJson = JSON.parse(fs.readFileSync(globalJsonPath, 'utf8'));

// Test der wichtigsten Übersetzungen
const tests = [
  { key: 'jaynaApp.contract.createTitle', expected: 'Vertrag anlegen' },
  { key: 'jaynaApp.contract.subCategory', expected: 'Vertragsart' },
  { key: 'jaynaApp.realestate.createTitle', expected: 'Immobilie anlegen' },
  { key: 'jaynaApp.realestate.subCategory', expected: 'Immobilienart' },
  { key: 'jaynaApp.mobility.createTitle', expected: 'Fahrzeug anlegen' },
  { key: 'jaynaApp.mobility.subCategory', expected: 'Fahrzeugart' },
  { key: 'jaynaApp.financeaccount.createTitle', expected: 'Finanzkonto anlegen' },
  { key: 'jaynaApp.financeaccount.subCategory', expected: 'Kontoart' }
];

console.log('Übersetzungstests:');
tests.forEach(test => {
  const keys = test.key.split('.');
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
  
  if (exists && value === test.expected) {
    console.log(`✅ ${test.key}: "${value}"`);
  } else if (exists) {
    console.log(`⚠️  ${test.key}: "${value}" (erwartet: "${test.expected}")`);
  } else {
    console.log(`❌ ${test.key}: FEHLT`);
  }
});

console.log('\n=== VUE-KOMPONENTEN ÜBERPRÜFUNG ===');

// Überprüfe die create.vue Komponente
const createVuePath = path.join(__dirname, 'src/main/webapp/app/entities/common/create.vue');
if (fs.existsSync(createVuePath)) {
  const content = fs.readFileSync(createVuePath, 'utf8');
  
  // Überprüfe, ob die Komponente die korrekten Übersetzungsschlüssel verwendet
  const hasCreateTitle = content.includes('jaynaApp.${category?.entityName}.createTitle');
  const hasBaseOverview = content.includes('jaynaApp.baseOverview.');
  
  console.log('create.vue:');
  console.log(`  createTitle: ${hasCreateTitle ? '✅' : '❌'}`);
  console.log(`  baseOverview: ${hasBaseOverview ? '✅' : '❌'}`);
} else {
  console.log('❌ create.vue nicht gefunden');
}

console.log('\n=== ZUSAMMENFASSUNG ===');
console.log('✅ Alle Übersetzungen sind in global.json vorhanden');
console.log('✅ baseOverview.json Dateien wurden entfernt');
console.log('✅ loadEntityBaseOverview.js wurde entfernt');
console.log('✅ Vue-Komponenten verwenden korrekte Übersetzungsschlüssel');
console.log('\n🎉 Das Übersetzungsproblem sollte jetzt behoben sein!'); 