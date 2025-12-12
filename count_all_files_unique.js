const fs = require('fs');
const path = require('path');

console.log('=== ZÄHLUNG NACH DUPLIKAT-BEREINIGUNG ===\n');

// Eindeutige form_name laden
const uniqueFormNames = JSON.parse(fs.readFileSync('unique_form_names.json', 'utf-8'));

// Formulardefinitionen zählen
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
let allI18n = getAllFiles(i18nPath, '.json');

// Base-Dateien zählen (alle, die mit base beginnen)
const baseFiles = allI18n.filter(f => path.basename(f).toLowerCase().startsWith('base'));
const i18nWithoutBase = allI18n.length - baseFiles.length;

console.log(`Eindeutige form_name: ${uniqueFormNames.length}`);
console.log(`Formulardefinitionen: ${allFormDefs.length}`);
console.log(`i18n-Dateien (inkl. base): ${allI18n.length}`);
console.log(`i18n-Dateien (ohne base): ${i18nWithoutBase}`);
console.log(`Base-Dateien: ${baseFiles.length}`); 