const fs = require('fs');
const path = require('path');

// Test der Dropdown-Funktionalität
console.log('=== Test der Dropdown-Funktionalität ===\n');

// 1. Prüfe Formulardefinitionen
console.log('1. Prüfe Formulardefinitionen:');
const formDefsDir = path.join(__dirname, 'src/main/webapp/app/shared/form-definitions');
const entities = ['contract', 'contact', 'realestate', 'financeaccount', 'mobility'];

entities.forEach(entity => {
  const entityDir = path.join(formDefsDir, entity);
  if (fs.existsSync(entityDir)) {
    const files = fs.readdirSync(entityDir).filter(f => f.endsWith('.json'));
    console.log(`  ${entity}: ${files.length} Formulardefinitionen gefunden`);
    
    // Prüfe erste Datei auf Struktur
    if (files.length > 0) {
      const firstFile = path.join(entityDir, files[0]);
      const content = JSON.parse(fs.readFileSync(firstFile, 'utf8'));
      console.log(`    - Erste Datei: ${files[0]}`);
      console.log(`    - Hat sections: ${content.sections ? 'Ja' : 'Nein'}`);
      console.log(`    - Hat relationsTitle: ${content.relationsTitle ? 'Ja' : 'Nein'}`);
    }
  } else {
    console.log(`  ${entity}: Verzeichnis nicht gefunden`);
  }
});

console.log('\n2. Prüfe i18n-Übersetzungen:');
const i18nDir = path.join(__dirname, 'src/main/webapp/i18n/de');
entities.forEach(entity => {
  const entityDir = path.join(i18nDir, entity);
  if (fs.existsSync(entityDir)) {
    const files = fs.readdirSync(entityDir).filter(f => f.endsWith('.json'));
    console.log(`  ${entity}: ${files.length} Übersetzungsdateien gefunden`);
  } else {
    console.log(`  ${entity}: Verzeichnis nicht gefunden`);
  }
});

console.log('\n3. Prüfe Vue-Komponenten:');
const components = [
  'src/main/webapp/app/entities/common/select.vue',
  'src/main/webapp/app/entities/common/create-form.vue',
  'src/main/webapp/app/composables/entity-helper.js'
];

components.forEach(comp => {
  if (fs.existsSync(comp)) {
    console.log(`  ${comp}: Gefunden`);
  } else {
    console.log(`  ${comp}: Nicht gefunden`);
  }
});

console.log('\n4. Prüfe Services:');
const services = [
  'src/main/webapp/app/services/category.service.js',
  'src/main/webapp/app/services/sub-category.service.js',
  'src/main/webapp/app/services/sub-category-group.service.js',
  'src/main/webapp/app/services/group-type.service.js'
];

services.forEach(service => {
  if (fs.existsSync(service)) {
    console.log(`  ${service}: Gefunden`);
  } else {
    console.log(`  ${service}: Nicht gefunden`);
  }
});

console.log('\n=== Dropdown-Test abgeschlossen ===');
console.log('\nDas System sollte jetzt bereit sein für die Dropdown-Tests.');
console.log('Öffne http://localhost:9000 und navigiere zu einer Kategorie, um die Dropdowns zu testen.'); 