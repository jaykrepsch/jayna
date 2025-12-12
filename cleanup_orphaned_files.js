const fs = require('fs');
const path = require('path');

console.log('=== Aufräumen: Löschen aller Altlasten (FormDef/i18n) ===\n');

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

// Erlaubte FormDef-Dateinamen
const allowedFormDef = new Set(expectedFormNames.map(e => path.join(formDefPath, e.category, e.formName + '.json')));
// Erlaubte i18n-Dateinamen
const allowedI18n = new Set(expectedFormNames.map(e => path.join(i18nPath, e.category, 'i18n-' + e.formName + '.json')));

// Zu löschende FormDef-Dateien
const orphanedFormDef = allFormDefFiles.filter(f => !allowedFormDef.has(f));
// Zu löschende i18n-Dateien
const orphanedI18n = allI18nFiles.filter(f => !allowedI18n.has(f));

console.log(`Zu löschende Formulardefinitionen: ${orphanedFormDef.length}`);
console.log(`Zu löschende i18n-Dateien: ${orphanedI18n.length}`);

for (const file of orphanedFormDef) {
    fs.unlinkSync(file);
    console.log('Gelöscht:', file);
}
for (const file of orphanedI18n) {
    fs.unlinkSync(file);
    console.log('Gelöscht:', file);
}

console.log('\nAufräumen abgeschlossen!'); 