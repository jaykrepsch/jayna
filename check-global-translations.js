const fs = require('fs');
const path = require('path');

// Lade die global.json
const globalPath = path.join(__dirname, 'src/main/webapp/i18n/de/global.json');
const globalData = JSON.parse(fs.readFileSync(globalPath, 'utf8'));

// Lade alle baseOverview.json Dateien
const baseOverviewFiles = [
  'src/main/webapp/i18n/de/contract/baseOverview.json',
  'src/main/webapp/i18n/de/realestate/baseOverview.json',
  'src/main/webapp/i18n/de/mobility/baseOverview.json',
  'src/main/webapp/i18n/de/financeaccount/baseOverview.json'
];

console.log('=== VERGLEICH: GLOBAL.JSON vs BASEOVERVIEW.JSON ===\n');

// Prüfe, welche baseOverview Keys in der global.json fehlen
const globalBaseOverview = globalData.jaynaApp?.baseOverview || {};

baseOverviewFiles.forEach(filePath => {
  if (fs.existsSync(filePath)) {
    const entityName = path.basename(path.dirname(filePath));
    console.log(`\n=== ${entityName.toUpperCase()} ===`);
    
    const baseOverviewData = JSON.parse(fs.readFileSync(filePath, 'utf8'));
    
    // Extrahiere die baseOverview Keys (ohne jaynaApp.baseOverview. Präfix)
    const baseOverviewKeys = Object.keys(baseOverviewData)
      .filter(key => key.startsWith('jaynaApp.baseOverview.'))
      .map(key => key.replace('jaynaApp.baseOverview.', ''));
    
    console.log('Keys in baseOverview.json:');
    baseOverviewKeys.forEach(key => {
      const globalValue = globalBaseOverview[key];
      const baseOverviewValue = baseOverviewData[`jaynaApp.baseOverview.${key}`];
      
      if (!globalValue) {
        console.log(`  ❌ FEHLT: ${key} = "${baseOverviewValue}"`);
      } else if (globalValue !== baseOverviewValue) {
        console.log(`  ⚠️  UNTERSCHIED: ${key}`);
        console.log(`     Global: "${globalValue}"`);
        console.log(`     BaseOverview: "${baseOverviewValue}"`);
      } else {
        console.log(`  ✅ OK: ${key} = "${globalValue}"`);
      }
    });
  }
});

console.log('\n=== EMPFOHLENE ERGÄNZUNGEN FÜR GLOBAL.JSON ===\n');

// Zeige alle fehlenden Keys
const allMissingKeys = new Set();

baseOverviewFiles.forEach(filePath => {
  if (fs.existsSync(filePath)) {
    const baseOverviewData = JSON.parse(fs.readFileSync(filePath, 'utf8'));
    
    const baseOverviewKeys = Object.keys(baseOverviewData)
      .filter(key => key.startsWith('jaynaApp.baseOverview.'))
      .map(key => key.replace('jaynaApp.baseOverview.', ''));
    
    baseOverviewKeys.forEach(key => {
      if (!globalBaseOverview[key]) {
        allMissingKeys.add(key);
      }
    });
  }
});

console.log('Fehlende Keys in global.json:');
Array.from(allMissingKeys).sort().forEach(key => {
  console.log(`  "${key}": "WERT",`);
}); 