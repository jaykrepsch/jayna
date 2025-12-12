const fs = require('fs');
const path = require('path');

// Test aller Übersetzungen basierend auf den Logs
console.log('=== Test aller Übersetzungen ===\n');

// Lade baseCategories.json
const baseCategoriesPath = path.join(__dirname, 'src/main/webapp/i18n/de/common/baseCategories.json');
const baseCategories = JSON.parse(fs.readFileSync(baseCategoriesPath, 'utf8'));

// Alle TranslationKeys aus den Logs
const translationKeysFromLogs = [
  'jaynaApp.contract.group-type.mini-job',
  'jaynaApp.contract.group-type.half-time',
  'jaynaApp.contract.group-type.full-time',
  'jaynaApp.contract.group-type.work-contract'
];

console.log('Prüfe TranslationKeys aus den Logs:');
translationKeysFromLogs.forEach(key => {
  const keyParts = key.split('.');
  const entityName = keyParts[2];
  const category = keyParts[3];
  const translationKey = keyParts[4];
  
  if (baseCategories.jaynaApp && 
      baseCategories.jaynaApp[entityName] && 
      baseCategories.jaynaApp[entityName][category] && 
      baseCategories.jaynaApp[entityName][category][translationKey]) {
    console.log(`  ✅ ${key} → "${baseCategories.jaynaApp[entityName][category][translationKey]}"`);
  } else {
    console.log(`  ❌ ${key} → FEHLT`);
  }
});

console.log('\n=== Test abgeschlossen ===');

// Prüfe auch die alten Keys, die möglicherweise noch in der Datenbank stehen
console.log('\n=== Prüfe alternative Keys ===');
const alternativeKeys = [
  'jaynaApp.contract.group-type.minijob',
  'jaynaApp.contract.group-type.parttime', 
  'jaynaApp.contract.group-type.fulltime',
  'jaynaApp.contract.group-type.work'
];

alternativeKeys.forEach(key => {
  const keyParts = key.split('.');
  const entityName = keyParts[2];
  const category = keyParts[3];
  const translationKey = keyParts[4];
  
  if (baseCategories.jaynaApp && 
      baseCategories.jaynaApp[entityName] && 
      baseCategories.jaynaApp[entityName][category] && 
      baseCategories.jaynaApp[entityName][category][translationKey]) {
    console.log(`  ✅ ${key} → "${baseCategories.jaynaApp[entityName][category][translationKey]}"`);
  } else {
    console.log(`  ❌ ${key} → FEHLT`);
  }
}); 