const fs = require('fs');
const path = require('path');

console.log('=== IDENTIFIZIERUNG FEHLENDER DATEIEN UND BASE-DATEIEN ===\n');

const formDefPath = 'src/main/webapp/app/shared/form-definitions';
const i18nPath = 'src/main/webapp/i18n/de';
const phase1File = path.join(__dirname, 'phase1_analysis.json');
const phase1Data = JSON.parse(fs.readFileSync(phase1File, 'utf-8'));

// Alle erwarteten form_name sammeln
const expectedFormNames = [];
for (const [category, items] of Object.entries(phase1Data.categories)) {
    for (const item of items) {
        expectedFormNames.push({
            formName: item.correctFormName,
            category: category
        });
    }
}

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

// Alle Dateien sammeln
const allFormDefFiles = getAllFiles(formDefPath, '.json');
const allI18nFiles = getAllFiles(i18nPath, '.json').filter(f => path.basename(f).startsWith('i18n-'));

// Base-Dateien identifizieren
const baseFormDefs = allFormDefFiles.filter(f => path.basename(f).includes('base'));
const baseI18nFiles = allI18nFiles.filter(f => path.basename(f).includes('base'));

console.log('📋 BASE-DATEIEN (sollten entfernt werden):');
console.log('\n📄 BASE FORMULARDEFINITIONEN:');
baseFormDefs.forEach((file, index) => {
    const relativePath = file.replace(formDefPath + '\\', '');
    console.log(`   ${index + 1}. ${relativePath}`);
});

console.log('\n🌐 BASE I18N-DATEIEN:');
baseI18nFiles.forEach((file, index) => {
    const relativePath = file.replace(i18nPath + '\\', '');
    console.log(`   ${index + 1}. ${relativePath}`);
});

// Fehlende Dateien identifizieren
console.log('\n❌ FEHLENDE DATEIEN:');

for (const expected of expectedFormNames) {
    const formDefFile = path.join(formDefPath, expected.category, `${expected.formName}.json`);
    const i18nFile = path.join(i18nPath, expected.category, `i18n-${expected.formName}.json`);
    
    const hasFormDef = fs.existsSync(formDefFile);
    const hasI18n = fs.existsSync(i18nFile);
    
    if (!hasFormDef || !hasI18n) {
        console.log(`\n   ${expected.formName} (${expected.category}):`);
        if (!hasFormDef) {
            console.log(`     ❌ FormDef fehlt: ${expected.formName}.json`);
        }
        if (!hasI18n) {
            console.log(`     ❌ i18n fehlt: i18n-${expected.formName}.json`);
        }
    }
}

// Zusätzliche Dateien (nicht zu form_name gehörend)
console.log('\n📦 ZUSÄTZLICHE DATEIEN (nicht zu form_name gehörend):');

const expectedFormDefNames = expectedFormNames.map(f => f.formName);
const expectedI18nNames = expectedFormNames.map(f => `i18n-${f.formName}`);

const additionalFormDefs = allFormDefFiles.filter(f => {
    const fileName = path.basename(f, '.json');
    return !expectedFormDefNames.includes(fileName) && !fileName.includes('base');
});

const additionalI18n = allI18nFiles.filter(f => {
    const fileName = path.basename(f, '.json');
    return !expectedI18nNames.includes(fileName) && !fileName.includes('base');
});

console.log('\n📄 ZUSÄTZLICHE FORMULARDEFINITIONEN:');
additionalFormDefs.forEach((file, index) => {
    const relativePath = file.replace(formDefPath + '\\', '');
    console.log(`   ${index + 1}. ${relativePath}`);
});

console.log('\n🌐 ZUSÄTZLICHE I18N-DATEIEN:');
additionalI18n.forEach((file, index) => {
    const relativePath = file.replace(i18nPath + '\\', '');
    console.log(`   ${index + 1}. ${relativePath}`);
}); 