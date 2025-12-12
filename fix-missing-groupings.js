const fs = require('fs');
const path = require('path');

console.log('=== Behebung fehlender Gruppierungen ===\n');

// Lade die aktuelle Subkategorie-Gruppen CSV
const subCategoryGroupCsvPath = path.join(__dirname, 'src/main/resources/config/liquibase/data/sub-category-group-Tabelle 1_20231128.csv');
const subCategoryGroupContent = fs.readFileSync(subCategoryGroupCsvPath, 'utf8');

// Finde die höchste ID
const lines = subCategoryGroupContent.split('\n').slice(1);
let maxId = 0;
lines.forEach(line => {
  if (line.trim()) {
    const parts = line.split(';');
    if (parts.length >= 1) {
      const id = parseInt(parts[0]);
      if (id > maxId) maxId = id;
    }
  }
});

console.log(`Höchste vorhandene ID: ${maxId}`);

// Definiere die fehlenden Gruppierungen
const missingGroupings = [
  {
    subCategoryId: '2', // Bankkonto
    name: 'Girokonto',
    translationKey: 'jaynaApp.contract.sub-category-group.checking-account'
  },
  {
    subCategoryId: '2', // Bankkonto
    name: 'Sparkonto',
    translationKey: 'jaynaApp.contract.sub-category-group.savings-account'
  },
  {
    subCategoryId: '2', // Bankkonto
    name: 'Tagesgeldkonto',
    translationKey: 'jaynaApp.contract.sub-category-group.overnight-account'
  },
  {
    subCategoryId: '2', // Bankkonto
    name: 'Festgeldkonto',
    translationKey: 'jaynaApp.contract.sub-category-group.fixed-term-account'
  },
  {
    subCategoryId: '3', // Kreditkarte
    name: 'Kreditkarte',
    translationKey: 'jaynaApp.contract.sub-category-group.credit-card'
  },
  {
    subCategoryId: '3', // Kreditkarte
    name: 'Debitkarte',
    translationKey: 'jaynaApp.contract.sub-category-group.debit-card'
  },
  {
    subCategoryId: '14', // Hausverwaltung
    name: 'Hausverwaltung',
    translationKey: 'jaynaApp.contract.sub-category-group.property-management'
  },
  {
    subCategoryId: '15', // Mietverwaltung
    name: 'Mietverwaltung',
    translationKey: 'jaynaApp.contract.sub-category-group.rental-management'
  }
];

console.log('Fehlende Gruppierungen:');
missingGroupings.forEach((grouping, index) => {
  console.log(`  ${index + 1}. ${grouping.name} (Subkategorie ID: ${grouping.subCategoryId})`);
});

// Erstelle neue CSV-Zeilen
const newLines = [];
missingGroupings.forEach((grouping, index) => {
  const newId = maxId + index + 1;
  const newLine = `${newId};${grouping.name};2023-06-13 00:00:00;${grouping.translationKey};${grouping.subCategoryId};system`;
  newLines.push(newLine);
});

// Erstelle die neue CSV-Datei
const header = 'id;name;created_date;translation_key;sub_category_id;created_by';
const newContent = header + '\n' + subCategoryGroupContent.split('\n').slice(1).join('\n') + '\n' + newLines.join('\n');

// Speichere die neue Datei
const newCsvPath = path.join(__dirname, 'src/main/resources/config/liquibase/data/sub-category-group-Tabelle 1_20231128_fixed.csv');
fs.writeFileSync(newCsvPath, newContent);

console.log(`\nNeue CSV-Datei erstellt: ${newCsvPath}`);
console.log(`Hinzugefügte Zeilen: ${newLines.length}`);

// Erstelle auch die entsprechenden GroupType-Einträge
console.log('\nErstelle entsprechende GroupType-Einträge...');

const groupTypeCsvPath = path.join(__dirname, 'src/main/resources/config/liquibase/data/group-type-Tabelle 1_20231128.csv');
const groupTypeContent = fs.readFileSync(groupTypeCsvPath, 'utf8');

// Finde die höchste GroupType ID
const groupTypeLines = groupTypeContent.split('\n').slice(1);
let maxGroupTypeId = 0;
groupTypeLines.forEach(line => {
  if (line.trim()) {
    const parts = line.split(';');
    if (parts.length >= 1) {
      const id = parseInt(parts[0]);
      if (id > maxGroupTypeId) maxGroupTypeId = id;
    }
  }
});

console.log(`Höchste GroupType ID: ${maxGroupTypeId}`);

// Erstelle GroupType-Einträge für die neuen Gruppierungen
const newGroupTypes = [];
let currentGroupTypeId = maxGroupTypeId + 1;

missingGroupings.forEach((grouping, index) => {
  const groupId = maxId + index + 1;
  
  // Erstelle entsprechende GroupTypes basierend auf der Gruppierung
  const groupTypes = [];
  
  if (grouping.name === 'Girokonto') {
    groupTypes.push('Girokonto');
  } else if (grouping.name === 'Sparkonto') {
    groupTypes.push('Sparkonto');
  } else if (grouping.name === 'Tagesgeldkonto') {
    groupTypes.push('Tagesgeldkonto');
  } else if (grouping.name === 'Festgeldkonto') {
    groupTypes.push('Festgeldkonto');
  } else if (grouping.name === 'Kreditkarte') {
    groupTypes.push('Kreditkarte');
  } else if (grouping.name === 'Debitkarte') {
    groupTypes.push('Debitkarte');
  } else if (grouping.name === 'Hausverwaltung') {
    groupTypes.push('Hausverwaltung');
  } else if (grouping.name === 'Mietverwaltung') {
    groupTypes.push('Mietverwaltung');
  }
  
  groupTypes.forEach(groupTypeName => {
    const formName = `contract-${grouping.name.toLowerCase().replace(/\s+/g, '-')}-${groupTypeName.toLowerCase().replace(/\s+/g, '-')}`;
    const translationKey = `jaynaApp.contract.group-type.${groupTypeName.toLowerCase().replace(/\s+/g, '-')}`;
    
    const newGroupTypeLine = `${currentGroupTypeId};${groupTypeName};2023-06-13 00:00:00;${translationKey};${groupId};contract;;${formName};system`;
    newGroupTypes.push(newGroupTypeLine);
    currentGroupTypeId++;
  });
});

// Erstelle die neue GroupType CSV-Datei
const groupTypeHeader = 'id;name;created_date;translation_key;sub_category_group_id;entity_name;abbreviation;form_name;created_by';
const newGroupTypeContent = groupTypeHeader + '\n' + groupTypeContent.split('\n').slice(1).join('\n') + '\n' + newGroupTypes.join('\n');

const newGroupTypeCsvPath = path.join(__dirname, 'src/main/resources/config/liquibase/data/group-type-Tabelle 1_20231128_fixed.csv');
fs.writeFileSync(newGroupTypeCsvPath, newGroupTypeContent);

console.log(`Neue GroupType CSV-Datei erstellt: ${newGroupTypeCsvPath}`);
console.log(`Hinzugefügte GroupTypes: ${newGroupTypes.length}`);

console.log('\n=== Behebung abgeschlossen ===');
console.log('\nNächste Schritte:');
console.log('1. Ersetze die ursprünglichen CSV-Dateien mit den neuen:');
console.log(`   - ${subCategoryGroupCsvPath} → ${newCsvPath}`);
console.log(`   - ${groupTypeCsvPath} → ${newGroupTypeCsvPath}`);
console.log('2. Starte die Datenbank neu');
console.log('3. Teste die Dropdowns erneut'); 