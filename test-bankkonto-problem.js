const fs = require('fs');
const path = require('path');

console.log('=== Analyse des Bankkonto-Problems ===\n');

// Lade alle relevanten CSV-Dateien
const categoryCsvPath = path.join(__dirname, 'src/main/resources/config/liquibase/data/category.csv');
const subCategoryCsvPath = path.join(__dirname, 'src/main/resources/config/liquibase/data/sub-category-Tabelle 1_20231128.csv');
const subCategoryGroupCsvPath = path.join(__dirname, 'src/main/resources/config/liquibase/data/sub-category-group-Tabelle 1_20231128.csv');
const groupTypeCsvPath = path.join(__dirname, 'src/main/resources/config/liquibase/data/group-type-Tabelle 1_20231128.csv');

// Parse Kategorien
const categoryMapping = {};
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

// Parse Subkategorien
const subCategoryMapping = {};
if (fs.existsSync(subCategoryCsvPath)) {
  const subCategoryContent = fs.readFileSync(subCategoryCsvPath, 'utf8');
  const subCategoryLines = subCategoryContent.split('\n').slice(1);
  
  subCategoryLines.forEach(line => {
    if (line.trim()) {
      const parts = line.split(';');
      if (parts.length >= 5) {
        const id = parts[0];
        const name = parts[1];
        const categoryId = parts[4];
        
        subCategoryMapping[id] = {
          name,
          categoryId,
          categoryName: categoryMapping[categoryId]
        };
      }
    }
  });
}

// Parse Subkategorie-Gruppen
const subCategoryGroupMapping = {};
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
        
        if (!subCategoryGroupMapping[subCategoryId]) {
          subCategoryGroupMapping[subCategoryId] = [];
        }
        
        subCategoryGroupMapping[subCategoryId].push({
          id,
          name
        });
      }
    }
  });
}

console.log('1. Analyse der Subkategorien für "Verträge" (Kategorie ID 1):');
Object.entries(subCategoryMapping).forEach(([id, data]) => {
  if (data.categoryId === '1') { // Verträge
    const groups = subCategoryGroupMapping[id] || [];
    console.log(`  Subkategorie ${id}: ${data.name}`);
    console.log(`    Kategorie: ${data.categoryName}`);
    console.log(`    Gruppierungen: ${groups.length}`);
    
    if (groups.length === 0) {
      console.log(`    ❌ PROBLEM: Keine Gruppierungen vorhanden!`);
    } else {
      groups.forEach(group => {
        console.log(`      - ${group.name} (ID: ${group.id})`);
      });
    }
    console.log('');
  }
});

console.log('2. Spezielle Analyse für Bankkonto:');
const bankkontoSubCategory = Object.entries(subCategoryMapping).find(([id, data]) => 
  data.name === 'Bankkonto' && data.categoryId === '1'
);

if (bankkontoSubCategory) {
  const [id, data] = bankkontoSubCategory;
  console.log(`  Bankkonto gefunden:`);
  console.log(`    ID: ${id}`);
  console.log(`    Kategorie: ${data.categoryName}`);
  
  const groups = subCategoryGroupMapping[id] || [];
  console.log(`    Gruppierungen: ${groups.length}`);
  
  if (groups.length === 0) {
    console.log(`    ❌ PROBLEM: Bankkonto hat keine Gruppierungen!`);
    console.log(`    💡 LÖSUNG: Gruppierung für Bankkonto hinzufügen`);
  } else {
    groups.forEach(group => {
      console.log(`      - ${group.name} (ID: ${group.id})`);
    });
  }
} else {
  console.log(`  ❌ Bankkonto nicht gefunden`);
}

console.log('\n3. Vergleich mit anderen Subkategorien:');
const contractSubCategories = Object.entries(subCategoryMapping).filter(([id, data]) => 
  data.categoryId === '1'
);

contractSubCategories.forEach(([id, data]) => {
  const groups = subCategoryGroupMapping[id] || [];
  const status = groups.length > 0 ? '✅' : '❌';
  console.log(`  ${status} ${data.name}: ${groups.length} Gruppierungen`);
});

console.log('\n4. Empfohlene Lösung:');
console.log('  Für Bankkonto (Subkategorie ID 2) fehlt eine Gruppierung.');
console.log('  Mögliche Gruppierungen könnten sein:');
console.log('    - Girokonto');
console.log('    - Sparkonto');
console.log('    - Tagesgeldkonto');
console.log('    - Festgeldkonto');

console.log('\n=== Analyse abgeschlossen ==='); 