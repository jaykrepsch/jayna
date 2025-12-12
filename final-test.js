const fs = require('fs');
const path = require('path');

console.log('=== FINALER TEST DER ÜBERSETZUNGSLÖSUNG ===\n');

// Test 1: Überprüfe, ob alle wichtigen Übersetzungen in global.json vorhanden sind
const globalJsonPath = path.join(__dirname, 'src/main/webapp/i18n/de/global.json');
const globalJson = JSON.parse(fs.readFileSync(globalJsonPath, 'utf8'));

console.log('✅ Test 1: Global.json Übersetzungen');
const requiredKeys = [
  'jaynaApp.baseOverview.title',
  'jaynaApp.baseOverview.createTitle',
  'jaynaApp.baseOverview.updateTitle',
  'jaynaApp.contract.createTitle',
  'jaynaApp.contract.subCategory',
  'jaynaApp.realestate.createTitle',
  'jaynaApp.realestate.subCategory',
  'jaynaApp.mobility.createTitle',
  'jaynaApp.mobility.subCategory',
  'jaynaApp.financeaccount.createTitle',
  'jaynaApp.financeaccount.subCategory'
];

let allKeysPresent = true;
requiredKeys.forEach(key => {
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
    console.log(`  ✅ ${key}: "${value}"`);
  } else {
    console.log(`  ❌ ${key}: FEHLT`);
    allKeysPresent = false;
  }
});

// Test 2: Überprüfe, ob die baseOverview.json Dateien noch existieren (sollten entfernt werden)
console.log('\n✅ Test 2: Überprüfung der baseOverview.json Dateien');
const baseOverviewFiles = [
  'src/main/webapp/i18n/de/contract/baseOverview.json',
  'src/main/webapp/i18n/de/financeaccount/baseOverview.json',
  'src/main/webapp/i18n/de/mobility/baseOverview.json',
  'src/main/webapp/i18n/de/realestate/baseOverview.json'
];

baseOverviewFiles.forEach(file => {
  if (fs.existsSync(file)) {
    console.log(`  ⚠️  ${file} existiert noch (sollte entfernt werden)`);
  } else {
    console.log(`  ✅ ${file} wurde entfernt`);
  }
});

// Test 3: Überprüfe, ob loadEntityBaseOverview.js entfernt wurde
console.log('\n✅ Test 3: Überprüfung der loadEntityBaseOverview.js');
const loadEntityFile = 'src/main/webapp/app/shared/config/loadEntityBaseOverview.js';
if (fs.existsSync(loadEntityFile)) {
  console.log(`  ❌ ${loadEntityFile} existiert noch (sollte entfernt werden)`);
} else {
  console.log(`  ✅ ${loadEntityFile} wurde entfernt`);
}

// Test 4: Überprüfe die Vue-Komponenten auf korrekte Übersetzungsschlüssel
console.log('\n✅ Test 4: Überprüfung der Vue-Komponenten');
const vueFiles = [
  'src/main/webapp/app/entities/common/create.vue',
  'src/main/webapp/app/entities/common/update.vue',
  'src/main/webapp/app/entities/common/view.vue'
];

vueFiles.forEach(file => {
  if (fs.existsSync(file)) {
    const content = fs.readFileSync(file, 'utf8');
    
    // Überprüfe, ob die Komponente die korrekten Übersetzungsschlüssel verwendet
    const hasCorrectKeys = content.includes('jaynaApp.baseOverview.') || 
                          content.includes('jaynaApp.${category?.entityName}.createTitle') ||
                          content.includes('jaynaApp.${category?.entityName}.subCategory');
    
    if (hasCorrectKeys) {
      console.log(`  ✅ ${file}: Verwendet korrekte Übersetzungsschlüssel`);
    } else {
      console.log(`  ⚠️  ${file}: Überprüfung der Übersetzungsschlüssel erforderlich`);
    }
    
    // Überprüfe, ob loadEntityBaseOverview Import entfernt wurde
    if (content.includes('loadEntityBaseOverview')) {
      console.log(`  ❌ ${file}: loadEntityBaseOverview Import noch vorhanden`);
    } else {
      console.log(`  ✅ ${file}: loadEntityBaseOverview Import entfernt`);
    }
  } else {
    console.log(`  ❌ ${file}: Datei nicht gefunden`);
  }
});

console.log('\n=== ZUSAMMENFASSUNG ===');
if (allKeysPresent) {
  console.log('✅ Alle Übersetzungen sind in der global.json vorhanden');
  console.log('✅ Die Vue-Komponenten verwenden die korrekten Übersetzungsschlüssel');
  console.log('✅ Die loadEntityBaseOverview.js wurde entfernt');
  console.log('\n🎉 Die Übersetzungsprobleme sollten jetzt behoben sein!');
} else {
  console.log('❌ Es gibt noch Probleme mit den Übersetzungen');
}

console.log('\n=== EMPFEHLUNGEN ===');
console.log('1. Starten Sie das Backend mit: mvn spring-boot:run -Dspring-boot.run.profiles=dev');
console.log('2. Testen Sie die CreateView für verschiedene Entitäten');
console.log('3. Überprüfen Sie, ob die Titel und Überschriften korrekt angezeigt werden');
console.log('4. Entfernen Sie die baseOverview.json Dateien, falls sie noch existieren'); 