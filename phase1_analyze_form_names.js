const fs = require('fs');
const path = require('path');

// CSV einlesen (aus group_types_camelcase.csv)
const inputFile = path.join(__dirname, 'group_types_camelcase.csv');
const outputFile = path.join(__dirname, 'phase1_analysis.json');

const lines = fs.readFileSync(inputFile, 'utf-8').split(/\r?\n/);
const categories = {
    financeaccount: [],
    realestate: [],
    mobility: [],
    contract: [],
    contact: []
};

// Datenzeilen verarbeiten
for (let i = 1; i < lines.length; i++) {
    const line = lines[i].trim();
    if (!line) continue;
    
    // CSV-Zeile parsen (mit Anführungszeichen)
    const cols = line.split('","').map(s => s.replace(/"/g, '').trim());
    if (cols.length >= 6) {
        const category = cols[0];
        const subcategory = cols[1];
        const subcategorygroup = cols[2];
        const grouptype = cols[3];
        const currentFormName = cols[4];
        const correctFormName = cols[5];
        
        // Nach Kategorie gruppieren
        if (categories[category]) {
            categories[category].push({
                category,
                subcategory,
                subcategorygroup,
                grouptype,
                currentFormName,
                correctFormName
            });
        }
    }
}

// Statistiken berechnen
const stats = {};
let totalCount = 0;

for (const [category, items] of Object.entries(categories)) {
    stats[category] = {
        count: items.length,
        uniqueFormNames: [...new Set(items.map(item => item.correctFormName))].length,
        items: items
    };
    totalCount += items.length;
}

// Ergebnis speichern
const result = {
    summary: {
        totalRecords: totalCount,
        categories: Object.keys(categories),
        stats: stats
    },
    categories: categories
};

fs.writeFileSync(outputFile, JSON.stringify(result, null, 2), 'utf-8');

// Konsolenausgabe
console.log('=== PHASE 1: Form_Name Analyse ===');
console.log(`Gesamtanzahl Datensätze: ${totalCount}`);
console.log('\nNach Kategorien:');
for (const [category, data] of Object.entries(stats)) {
    console.log(`- ${category}: ${data.count} Datensätze, ${data.uniqueFormNames} eindeutige form_name`);
}
console.log(`\nErgebnis gespeichert in: ${outputFile}`);

// Beispiel-Ausgabe für jede Kategorie
console.log('\n=== Beispiele pro Kategorie ===');
for (const [category, data] of Object.entries(stats)) {
    if (data.items.length > 0) {
        console.log(`\n${category.toUpperCase()}:`);
        console.log(`Aktuell: ${data.items[0].currentFormName}`);
        console.log(`Korrekt: ${data.items[0].correctFormName}`);
    }
} 