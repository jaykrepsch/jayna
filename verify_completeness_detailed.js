const fs = require('fs');
const path = require('path');

console.log('=== DETAILLIERTE VOLLSTÄNDIGKEITSPRÜFUNG ===\n');

// Eindeutige form_name laden
const uniqueFormNames = JSON.parse(fs.readFileSync('unique_form_names.json', 'utf-8'));

// Alle Formulardefinitionen finden
function getAllFiles(dir, ext) {
    let results = [];
    if (!fs.existsSync(dir)) return results;
    const list = fs.readdirSync(dir);
    list.forEach(file => {
        const filePath = path.join(dir, file);
        const stat = fs.statSync(filePath);
        if (stat && stat.isDirectory()) {
            results = results.concat(getAllFiles(filePath, ext));
        } else if (file.endsWith(ext)) {
            results.push(filePath);
        }
    });
    return results;
}

const formDefPath = 'src/main/webapp/app/shared/form-definitions';
const i18nPath = 'src/main/webapp/i18n/de';

const allFormDefs = getAllFiles(formDefPath, '.json');
const allI18n = getAllFiles(i18nPath, '.json');

// Formulardefinitionen nach Kategorien gruppieren
const formDefsByCategory = {};
allFormDefs.forEach(file => {
    const relativePath = path.relative(formDefPath, file);
    const category = relativePath.split(path.sep)[0];
    if (!formDefsByCategory[category]) {
        formDefsByCategory[category] = [];
    }
    formDefsByCategory[category].push(path.basename(file, '.json'));
});

// i18n-Dateien nach Kategorien gruppieren
const i18nByCategory = {};
allI18n.forEach(file => {
    const relativePath = path.relative(i18nPath, file);
    const category = relativePath.split(path.sep)[0];
    if (!i18nByCategory[category]) {
        i18nByCategory[category] = [];
    }
    i18nByCategory[category].push(path.basename(file, '.json'));
});

console.log('📊 GESAMTZÄHLUNG:');
console.log(`   Eindeutige form_name: ${uniqueFormNames.length}`);
console.log(`   Formulardefinitionen: ${allFormDefs.length}`);
console.log(`   i18n-Dateien (gesamt): ${allI18n.length}`);

// Base-Dateien analysieren
const baseFiles = allI18n.filter(f => path.basename(f).toLowerCase().startsWith('base'));
const baseEntityFiles = baseFiles.filter(f => path.basename(f).toLowerCase().includes('entity'));
const baseOverviewFiles = baseFiles.filter(f => path.basename(f).toLowerCase().includes('overview'));

console.log('\n📁 BASE-DATEIEN:');
console.log(`   Alle base-Dateien: ${baseFiles.length}`);
console.log(`   base*entity*: ${baseEntityFiles.length}`);
console.log(`   *baseOverview*: ${baseOverviewFiles.length}`);

// Prüfung: Hat jeder form_name eine Formulardefinition?
console.log('\n🔍 PRÜFUNG 1: form_name → Formulardefinition');
const missingFormDefs = [];
uniqueFormNames.forEach(item => {
    const category = item.category;
    const formName = item.formName;
    const formDefs = formDefsByCategory[category] || [];
    if (!formDefs.includes(formName)) {
        missingFormDefs.push({ category, formName });
    }
});

if (missingFormDefs.length === 0) {
    console.log('   ✅ Alle form_name haben eine Formulardefinition');
} else {
    console.log(`   ❌ ${missingFormDefs.length} form_name fehlen Formulardefinitionen:`);
    missingFormDefs.forEach(item => {
        console.log(`      - ${item.formName} (${item.category})`);
    });
}

// Prüfung: Hat jede Formulardefinition eine i18n-Datei?
console.log('\n🔍 PRÜFUNG 2: Formulardefinition → i18n-Datei');
const missingI18n = [];
Object.entries(formDefsByCategory).forEach(([category, formDefs]) => {
    const i18nFiles = i18nByCategory[category] || [];
    formDefs.forEach(formDef => {
        const expectedI18n = `i18n-${formDef}`;
        if (!i18nFiles.includes(expectedI18n)) {
            missingI18n.push({ category, formDef, expectedI18n });
        }
    });
});

if (missingI18n.length === 0) {
    console.log('   ✅ Alle Formulardefinitionen haben eine i18n-Datei');
} else {
    console.log(`   ❌ ${missingI18n.length} Formulardefinitionen fehlen i18n-Dateien:`);
    missingI18n.forEach(item => {
        console.log(`      - ${item.formDef} (${item.category}) → ${item.expectedI18n}`);
    });
}

// Prüfung: Base-Dateien nach Kategorien
console.log('\n🔍 PRÜFUNG 3: Base-Dateien nach Kategorien');
const categories = ['contact', 'contract', 'financeaccount', 'mobility', 'realestate'];
categories.forEach(category => {
    const categoryI18n = i18nByCategory[category] || [];
    const baseEntityCount = categoryI18n.filter(f => f.toLowerCase().startsWith('base') && f.toLowerCase().includes('entity')).length;
    const baseOverviewCount = categoryI18n.filter(f => f.toLowerCase().includes('baseoverview')).length;
    console.log(`   ${category}: ${baseEntityCount} base*entity*, ${baseOverviewCount} *baseOverview*`);
});

console.log('\n📝 ZUSAMMENFASSUNG:');
console.log(`   form_name: ${uniqueFormNames.length}`);
console.log(`   Formulardefinitionen: ${allFormDefs.length}`);
console.log(`   i18n-Dateien: ${allI18n.length}`);
console.log(`   Fehlende FormDefs: ${missingFormDefs.length}`);
console.log(`   Fehlende i18n: ${missingI18n.length}`); 