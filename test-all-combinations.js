const fs = require('fs');
const path = require('path');

console.log('=== Test aller Kombinationen: Art → Gruppierung → Sparte ===\n');

// Lade baseCategories.json für Übersetzungen
const baseCategoriesPath = path.join(__dirname, 'src/main/webapp/i18n/de/common/baseCategories.json');
const baseCategories = JSON.parse(fs.readFileSync(baseCategoriesPath, 'utf8'));

// Lade alle Formulardefinitionen
const formDefsDir = path.join(__dirname, 'src/main/webapp/app/shared/form-definitions');
const entities = ['contract', 'contact', 'realestate', 'financeaccount', 'mobility'];

console.log('1. Prüfe Formulardefinitionen für alle Entitäten:');
entities.forEach(entity => {
  const entityDir = path.join(formDefsDir, entity);
  if (fs.existsSync(entityDir)) {
    const files = fs.readdirSync(entityDir).filter(f => f.endsWith('.json'));
    console.log(`  ${entity}: ${files.length} Formulardefinitionen`);
    
    // Zeige erste paar Dateien als Beispiel
    files.slice(0, 3).forEach(file => {
      console.log(`    - ${file}`);
    });
    if (files.length > 3) {
      console.log(`    - ... und ${files.length - 3} weitere`);
    }
  } else {
    console.log(`  ${entity}: Verzeichnis nicht gefunden`);
  }
});

console.log('\n2. Prüfe Übersetzungen für alle Kategorien:');
entities.forEach(entity => {
  if (baseCategories.jaynaApp && baseCategories.jaynaApp[entity]) {
    const subCategories = baseCategories.jaynaApp[entity]['sub-category'] || {};
    const subCategoryGroups = baseCategories.jaynaApp[entity]['sub-category-group'] || {};
    const groupTypes = baseCategories.jaynaApp[entity]['group-type'] || {};
    
    console.log(`  ${entity}:`);
    console.log(`    Subkategorien: ${Object.keys(subCategories).length}`);
    console.log(`    Gruppierungen: ${Object.keys(subCategoryGroups).length}`);
    console.log(`    Sparten: ${Object.keys(groupTypes).length}`);
  } else {
    console.log(`  ${entity}: Keine Übersetzungen gefunden`);
  }
});

console.log('\n3. Prüfe Vue-Komponenten für Dropdown-Logik:');
const components = [
  'src/main/webapp/app/entities/common/select.vue',
  'src/main/webapp/app/entities/common/create-form.vue',
  'src/main/webapp/app/composables/entity-helper.js'
];

components.forEach(comp => {
  if (fs.existsSync(comp)) {
    console.log(`  ✅ ${comp}`);
  } else {
    console.log(`  ❌ ${comp}`);
  }
});

console.log('\n4. Prüfe Services für API-Aufrufe:');
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

console.log('\n=== Test abgeschlossen ===');
console.log('\nNächste Schritte:');
console.log('1. Öffne http://localhost:9000');
console.log('2. Teste jede Kategorie (Verträge, Immobilien, Kontakte, etc.)');
console.log('3. Wähle verschiedene Kombinationen: Art → Gruppierung → Sparte');
console.log('4. Prüfe, ob die Formulare korrekt geladen werden'); 