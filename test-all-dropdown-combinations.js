const fs = require('fs');
const path = require('path');

console.log('=== Test aller Dropdown-Kombinationen ===\n');

// Lade alle Formulardefinitionen
const formDefsDir = path.join(__dirname, 'src/main/webapp/app/shared/form-definitions');
const entities = ['contract', 'contact', 'realestate', 'financeaccount', 'mobility'];

// Sammle alle verfügbaren Formulardefinitionen
const availableForms = {};

entities.forEach(entity => {
  const entityDir = path.join(formDefsDir, entity);
  if (fs.existsSync(entityDir)) {
    const files = fs.readdirSync(entityDir).filter(f => f.endsWith('.json'));
    availableForms[entity] = files.map(file => file.replace('.json', ''));
  }
});

// Lade die CSV-Datei mit den formName-Werten
const csvPath = path.join(__dirname, 'src/main/resources/config/liquibase/data/group-type-Tabelle 1_20231128.csv');
const csvContent = fs.readFileSync(csvPath, 'utf8');

// Parse CSV und extrahiere formName-Werte
const lines = csvContent.split('\n').slice(1); // Skip header
const formNameMapping = {};

lines.forEach(line => {
  if (line.trim()) {
    const parts = line.split(';');
    if (parts.length >= 8) {
      const id = parts[0];
      const name = parts[1];
      const translationKey = parts[3];
      const entityName = parts[5];
      const formName = parts[7];
      
      if (formName && formName !== 'form_name') {
        formNameMapping[id] = {
          name,
          translationKey,
          entityName,
          formName
        };
      }
    }
  }
});

console.log('1. Prüfe formName-Zuordnung:');
let foundCount = 0;
let missingCount = 0;

Object.entries(formNameMapping).forEach(([id, data]) => {
  const { name, entityName, formName } = data;
  
  if (availableForms[entityName]) {
    const found = availableForms[entityName].some(form => form === formName);
    if (found) {
      foundCount++;
      console.log(`  ✅ ${id}: ${name} → ${formName}`);
    } else {
      missingCount++;
      console.log(`  ❌ ${id}: ${name} → ${formName} (NICHT GEFUNDEN)`);
    }
  } else {
    missingCount++;
    console.log(`  ❌ ${id}: ${name} → Entität ${entityName} nicht gefunden`);
  }
});

console.log(`\nZusammenfassung: ${foundCount} gefunden, ${missingCount} fehlen`);

console.log('\n2. Prüfe Vue-Komponenten für Dropdown-Logik:');
const components = [
  'src/main/webapp/app/entities/common/select.vue',
  'src/main/webapp/app/entities/common/create-form.vue',
  'src/main/webapp/app/composables/entity-helper.js'
];

components.forEach(comp => {
  if (fs.existsSync(comp)) {
    const content = fs.readFileSync(comp, 'utf8');
    
    // Prüfe wichtige Funktionen
    const checks = [
      { name: 'formName wird verwendet', pattern: 'formName' },
      { name: 'translationKey wird verwendet', pattern: 'translationKey' },
      { name: 'Dynamische Imports', pattern: 'import(' },
      { name: 'API-Aufrufe', pattern: 'service' }
    ];
    
    console.log(`  ${comp}:`);
    checks.forEach(check => {
      if (content.includes(check.pattern)) {
        console.log(`    ✅ ${check.name}`);
      } else {
        console.log(`    ❌ ${check.name}`);
      }
    });
  } else {
    console.log(`  ❌ ${comp} nicht gefunden`);
  }
});

console.log('\n3. Prüfe Services für API-Aufrufe:');
const services = [
  'src/main/webapp/app/services/category.service.js',
  'src/main/webapp/app/services/sub-category.service.js', 
  'src/main/webapp/app/services/sub-category-group.service.js',
  'src/main/webapp/app/services/group-type.service.js'
];

services.forEach(service => {
  if (fs.existsSync(service)) {
    console.log(`  ✅ ${service}`);
  } else {
    console.log(`  ❌ ${service}`);
  }
});

console.log('\n4. Teste spezifische Kombinationen aus den Logs:');
const testCombinations = [
  {
    category: 'Verträge',
    subCategory: 'Darlehen', 
    subCategoryGroup: 'Immobilie',
    groupType: 'Annuitätendarlehen',
    formName: 'contract-loan-realestate-annuityloan'
  },
  {
    category: 'Verträge',
    subCategory: 'Arbeitsvertrag',
    subCategoryGroup: 'Arbeitsvertrag',
    groupType: 'Vollzeit',
    formName: 'contract-employment-employment-employmentcontract'
  }
];

testCombinations.forEach((combo, index) => {
  console.log(`  Kombination ${index + 1}:`);
  console.log(`    Kategorie: ${combo.category}`);
  console.log(`    Art: ${combo.subCategory}`);
  console.log(`    Gruppierung: ${combo.subCategoryGroup}`);
  console.log(`    Sparte: ${combo.groupType}`);
  console.log(`    Formular: ${combo.formName}`);
  
  const entityName = combo.formName.split('-')[0];
  if (availableForms[entityName] && availableForms[entityName].includes(combo.formName)) {
    console.log(`    ✅ Formular gefunden`);
  } else {
    console.log(`    ❌ Formular NICHT gefunden`);
  }
  console.log('');
});

console.log('=== Test abgeschlossen ===');
console.log('\nNächste Schritte:');
console.log('1. Öffne http://localhost:9000');
console.log('2. Teste die Dropdown-Kombinationen:');
console.log('   - Verträge → Darlehen → Immobilie → Annuitätendarlehen');
console.log('   - Verträge → Arbeitsvertrag → Arbeitsvertrag → Vollzeit');
console.log('3. Prüfe, ob die Formulare korrekt geladen werden'); 