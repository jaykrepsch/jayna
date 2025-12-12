const fs = require('fs');
const path = require('path');

console.log('=== ZÄHLUNG ALLER DATEIEN ===\n');

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

// FormDef: Alle .json-Dateien unter formDefPath
const allFormDefFiles = getAllFiles(formDefPath, '.json');
// i18n: Alle i18n-*.json-Dateien unter i18nPath
const allI18nFiles = getAllFiles(i18nPath, '.json').filter(f => path.basename(f).startsWith('i18n-'));

console.log(`📊 GESAMTZÄHLUNG:`);
console.log(`   form_name (erwartet): ${expectedFormNames.length}`);
console.log(`   Formulardefinitionen: ${allFormDefFiles.length}`);
console.log(`   i18n-Dateien: ${allI18nFiles.length}`);

console.log(`\n📁 NACH KATEGORIEN:`);
for (const category of Object.keys(phase1Data.categories)) {
    const categoryFormNames = expectedFormNames.filter(exp => exp.category === category);
    const categoryFormDefs = allFormDefFiles.filter(f => f.includes(`\\${category}\\`));
    const categoryI18n = allI18nFiles.filter(f => f.includes(`\\${category}\\`));
    
    console.log(`   ${category.toUpperCase()}:`);
    console.log(`     form_name: ${categoryFormNames.length}`);
    console.log(`     FormDefs: ${categoryFormDefs.length}`);
    console.log(`     i18n: ${categoryI18n.length}`);
}

console.log(`\n📋 ALLE FORM_NAME:`);
expectedFormNames.forEach((item, index) => {
    console.log(`   ${index + 1}. ${item.formName} (${item.category})`);
});

console.log(`\n📄 ALLE FORMULARDEFINITIONEN:`);
allFormDefFiles.forEach((file, index) => {
    const relativePath = file.replace(formDefPath + '\\', '');
    console.log(`   ${index + 1}. ${relativePath}`);
});

console.log(`\n🌐 ALLE I18N-DATEIEN:`);
allI18nFiles.forEach((file, index) => {
    const relativePath = file.replace(i18nPath + '\\', '');
    console.log(`   ${index + 1}. ${relativePath}`);
}); 