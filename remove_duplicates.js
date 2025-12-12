const fs = require('fs');
const path = require('path');

console.log('=== ENTFERNUNG DER DUPLIKATE AUS DER FORM_NAME-LISTE ===\n');

const phase1File = path.join(__dirname, 'phase1_analysis.json');
const phase1Data = JSON.parse(fs.readFileSync(phase1File, 'utf-8'));

// Alle erwarteten form_name sammeln (korrekte Struktur)
const allFormNames = [];
for (const [category, categoryData] of Object.entries(phase1Data.summary.stats)) {
    if (categoryData.items) {
        categoryData.items.forEach(item => {
            allFormNames.push({
                formName: item.correctFormName,
                category: category
            });
        });
    }
}

// Duplikate entfernen
const uniqueFormNames = [];
const seen = new Set();
allFormNames.forEach(item => {
    if (!seen.has(item.formName)) {
        uniqueFormNames.push(item);
        seen.add(item.formName);
    }
});

console.log(`Vorher: ${allFormNames.length} form_name`);
console.log(`Nachher: ${uniqueFormNames.length} eindeutige form_name`);

// Speichern
fs.writeFileSync('unique_form_names.json', JSON.stringify(uniqueFormNames, null, 2), 'utf-8');
console.log('Bereinigte Liste gespeichert als unique_form_names.json'); 