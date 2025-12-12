const fs = require('fs');
const path = require('path');

console.log('=== Automatisierter Dropdown-Test ===\n');

// Lade die CSV-Datei mit allen Kombinationen
const csvPath = path.join(__dirname, 'src/main/resources/config/liquibase/data/group-type-Tabelle 1_20231128.csv');
const csvContent = fs.readFileSync(csvPath, 'utf8');

// Parse CSV und erstelle Hierarchie
const lines = csvContent.split('\n').slice(1); // Skip header
const hierarchy = {};

lines.forEach(line => {
  if (line.trim()) {
    const parts = line.split(';');
    if (parts.length >= 8) {
      const id = parts[0];
      const name = parts[1];
      const translationKey = parts[3];
      const subCategoryGroupId = parts[4];
      const entityName = parts[5];
      const formName = parts[7];
      
      if (!hierarchy[entityName]) {
        hierarchy[entityName] = {};
      }
      
      if (!hierarchy[entityName][subCategoryGroupId]) {
        hierarchy[entityName][subCategoryGroupId] = [];
      }
      
      hierarchy[entityName][subCategoryGroupId].push({
        id,
        name,
        translationKey,
        formName
      });
    }
  }
});

// Lade Subkategorie-Gruppen CSV
const subCategoryGroupCsvPath = path.join(__dirname, 'src/main/resources/config/liquibase/data/sub-category-group-Tabelle 1_20231128.csv');
let subCategoryGroupMapping = {};

if (fs.existsSync(subCategoryGroupCsvPath)) {
  const subCategoryGroupContent = fs.readFileSync(subCategoryGroupCsvPath, 'utf8');
  const subCategoryGroupLines = subCategoryGroupContent.split('\n').slice(1);
  
  subCategoryGroupLines.forEach(line => {
    if (line.trim()) {
      const parts = line.split(';');
      if (parts.length >= 5) {
        const id = parts[0];
        const name = parts[1];
        const subCategoryId = parts[4];
        
        subCategoryGroupMapping[id] = {
          name,
          subCategoryId
        };
      }
    }
  });
}

// Lade Subkategorien CSV
const subCategoryCsvPath = path.join(__dirname, 'src/main/resources/config/liquibase/data/sub-category-Tabelle 1_20231128.csv');
let subCategoryMapping = {};

if (fs.existsSync(subCategoryCsvPath)) {
  const subCategoryContent = fs.readFileSync(subCategoryCsvPath, 'utf8');
  const subCategoryLines = subCategoryContent.split('\n').slice(1);
  
  subCategoryLines.forEach(line => {
    if (line.trim()) {
      const parts = line.split(';');
      if (parts.length >= 4) {
        const id = parts[0];
        const name = parts[1];
        const categoryId = parts[3];
        
        subCategoryMapping[id] = {
          name,
          categoryId
        };
      }
    }
  });
}

// Lade Kategorien CSV
const categoryCsvPath = path.join(__dirname, 'src/main/resources/config/liquibase/data/category.csv');
let categoryMapping = {};

if (fs.existsSync(categoryCsvPath)) {
  const categoryContent = fs.readFileSync(categoryCsvPath, 'utf8');
  const categoryLines = categoryContent.split('\n').slice(1);
  
  categoryLines.forEach(line => {
    if (line.trim()) {
      const parts = line.split(';');
      if (parts.length >= 2) {
        const id = parts[0];
        const name = parts[1];
        
        categoryMapping[id] = name;
      }
    }
  });
}

console.log('1. Analysiere Dropdown-Hierarchie:');

// Erstelle vollständige Testpfade
const testPaths = [];

Object.entries(hierarchy).forEach(([entityName, subCategoryGroups]) => {
  console.log(`\n  Entität: ${entityName}`);
  
  Object.entries(subCategoryGroups).forEach(([subCategoryGroupId, groupTypes]) => {
    const subCategoryGroup = subCategoryGroupMapping[subCategoryGroupId];
    if (subCategoryGroup) {
      const subCategory = subCategoryMapping[subCategoryGroup.subCategoryId];
      if (subCategory) {
        const category = categoryMapping[subCategory.categoryId];
        
        console.log(`    Kategorie: ${category}`);
        console.log(`    Subkategorie: ${subCategory.name}`);
        console.log(`    Gruppierung: ${subCategoryGroup.name}`);
        console.log(`    Sparten: ${groupTypes.length}`);
        
        groupTypes.forEach(groupType => {
          testPaths.push({
            category: category,
            subCategory: subCategory.name,
            subCategoryGroup: subCategoryGroup.name,
            groupType: groupType.name,
            formName: groupType.formName,
            entityName: entityName,
            translationKey: groupType.translationKey
          });
        });
      }
    }
  });
});

console.log(`\n2. Gefundene Testpfade: ${testPaths.length}`);

// Prüfe Formulardefinitionen
const formDefsDir = path.join(__dirname, 'src/main/webapp/app/shared/form-definitions');
const availableForms = {};

['contract', 'contact', 'realestate', 'financeaccount', 'mobility'].forEach(entity => {
  const entityDir = path.join(formDefsDir, entity);
  if (fs.existsSync(entityDir)) {
    const files = fs.readdirSync(entityDir).filter(f => f.endsWith('.json'));
    availableForms[entity] = files.map(file => file.replace('.json', ''));
  }
});

console.log('\n3. Teste Formular-Zuordnung:');
let workingPaths = 0;
let brokenPaths = 0;

testPaths.forEach((path, index) => {
  const { category, subCategory, subCategoryGroup, groupType, formName, entityName } = path;
  
  // Prüfe ob Formular existiert
  const formExists = availableForms[entityName] && availableForms[entityName].includes(formName);
  
  if (formExists) {
    workingPaths++;
    console.log(`  ✅ ${index + 1}: ${category} → ${subCategory} → ${subCategoryGroup} → ${groupType}`);
  } else {
    brokenPaths++;
    console.log(`  ❌ ${index + 1}: ${category} → ${subCategory} → ${subCategoryGroup} → ${groupType}`);
    console.log(`     Fehlendes Formular: ${formName}`);
  }
});

console.log(`\nZusammenfassung:`);
console.log(`  ✅ Funktionierende Pfade: ${workingPaths}`);
console.log(`  ❌ Fehlerhafte Pfade: ${brokenPaths}`);
console.log(`  📊 Erfolgsrate: ${((workingPaths / testPaths.length) * 100).toFixed(1)}%`);

// Spezifische Analyse für Bankkonto-Problem
console.log('\n4. Spezielle Analyse für Bankkonto-Problem:');
const bankkontoPaths = testPaths.filter(path => 
  path.subCategory.toLowerCase().includes('bank') || 
  path.subCategory.toLowerCase().includes('konto')
);

console.log(`  Bankkonto-bezogene Pfade: ${bankkontoPaths.length}`);
bankkontoPaths.forEach(path => {
  const { category, subCategory, subCategoryGroup, groupType, formName } = path;
  const formExists = availableForms[path.entityName] && availableForms[path.entityName].includes(formName);
  
  if (formExists) {
    console.log(`    ✅ ${category} → ${subCategory} → ${subCategoryGroup} → ${groupType}`);
  } else {
    console.log(`    ❌ ${category} → ${subCategory} → ${subCategoryGroup} → ${groupType}`);
    console.log(`       Fehlendes Formular: ${formName}`);
  }
});

// Erstelle detaillierten Testbericht
const reportPath = path.join(__dirname, 'dropdown-test-report.json');
const report = {
  timestamp: new Date().toISOString(),
  summary: {
    totalPaths: testPaths.length,
    workingPaths,
    brokenPaths,
    successRate: ((workingPaths / testPaths.length) * 100).toFixed(1)
  },
  workingPaths: testPaths.filter(path => {
    const formExists = availableForms[path.entityName] && availableForms[path.entityName].includes(path.formName);
    return formExists;
  }),
  brokenPaths: testPaths.filter(path => {
    const formExists = availableForms[path.entityName] && availableForms[path.entityName].includes(path.formName);
    return !formExists;
  })
};

fs.writeFileSync(reportPath, JSON.stringify(report, null, 2));
console.log(`\n📄 Detaillierter Testbericht gespeichert: ${reportPath}`);

console.log('\n=== Test abgeschlossen ==='); 