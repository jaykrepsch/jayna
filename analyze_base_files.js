const fs = require('fs');
const path = require('path');

console.log('=== DETAILLIERTE BASE-DATEIEN ANALYSE ===\n');

const i18nPath = 'src/main/webapp/i18n/de';

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

const allI18n = getAllFiles(i18nPath, '.json');
const baseFiles = allI18n.filter(f => path.basename(f).toLowerCase().startsWith('base'));

console.log('📁 ALLE BASE-DATEIEN:');
baseFiles.forEach((file, index) => {
    const relativePath = path.relative(i18nPath, file);
    console.log(`   ${index + 1}. ${relativePath}`);
});

console.log(`\n📊 ZÄHLUNG: ${baseFiles.length} base-Dateien`);

// Nach Kategorien gruppieren
const baseFilesByCategory = {};
baseFiles.forEach(file => {
    const relativePath = path.relative(i18nPath, file);
    const category = relativePath.split(path.sep)[0];
    if (!baseFilesByCategory[category]) {
        baseFilesByCategory[category] = [];
    }
    baseFilesByCategory[category].push(path.basename(file, '.json'));
});

console.log('\n📂 BASE-DATEIEN NACH KATEGORIEN:');
Object.entries(baseFilesByCategory).forEach(([category, files]) => {
    console.log(`   ${category}: ${files.length} Dateien`);
    files.forEach(file => {
        console.log(`     - ${file}`);
    });
});

// Erwartete vs. tatsächliche base-Dateien
console.log('\n🔍 ERWARTETE VS. TATSÄCHLICHE BASE-DATEIEN:');
const categories = ['contact', 'contract', 'financeaccount', 'mobility', 'realestate'];
let expectedTotal = 0;
categories.forEach(category => {
    const files = baseFilesByCategory[category] || [];
    const baseEntityCount = files.filter(f => f.toLowerCase().includes('entity')).length;
    const baseOverviewCount = files.filter(f => f.toLowerCase().includes('overview')).length;
    const otherBaseCount = files.length - baseEntityCount - baseOverviewCount;
    
    console.log(`   ${category}:`);
    console.log(`     - base*entity*: ${baseEntityCount}`);
    console.log(`     - *baseOverview*: ${baseOverviewCount}`);
    console.log(`     - andere base*: ${otherBaseCount}`);
    console.log(`     - Gesamt: ${files.length}`);
    
    expectedTotal += 2; // Erwartet: 1 baseEntity + 1 baseOverview pro Kategorie
});

console.log(`\n📝 ZUSAMMENFASSUNG:`);
console.log(`   Erwartet: ${expectedTotal} base-Dateien (5 Kategorien × 2)`);
console.log(`   Tatsächlich: ${baseFiles.length} base-Dateien`);
console.log(`   Differenz: ${baseFiles.length - expectedTotal} zusätzliche base-Dateien`);

// Alle i18n-Dateien zählen
const allI18nFiles = getAllFiles(i18nPath, '.json');
const nonBaseI18n = allI18nFiles.filter(f => !path.basename(f).toLowerCase().startsWith('base'));

console.log(`\n🌐 I18N-DATEIEN ANALYSE:`);
console.log(`   Alle i18n-Dateien: ${allI18nFiles.length}`);
console.log(`   Base-Dateien: ${baseFiles.length}`);
console.log(`   Nicht-Base-Dateien: ${nonBaseI18n.length}`);
console.log(`   Erwartet: ${nonBaseI18n.length} + ${expectedTotal} = ${nonBaseI18n.length + expectedTotal}`); 