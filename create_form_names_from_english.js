const fs = require('fs');
const path = require('path');

// Hilfsfunktion: letzten Teil nach dem letzten Punkt extrahieren
function extractKey(str) {
    if (!str) return '';
    const parts = str.split('.');
    return parts[parts.length - 1].toLowerCase();
}

// Hilfsfunktion: Bindestrich-getrennte Wörter zu CamelCase konvertieren
function toCamelCase(str) {
    if (!str) return '';
    return str.split('-').map((word, index) => {
        if (index === 0) return word; // Erstes Wort bleibt klein
        return word.charAt(0).toUpperCase() + word.slice(1); // Reste werden groß geschrieben
    }).join('');
}

// CSV einlesen (aus group_types_english_raw.csv)
const inputFile = path.join(__dirname, 'group_types_english_raw.csv');
const outputFile = path.join(__dirname, 'group_types_camelcase.csv');

const lines = fs.readFileSync(inputFile, 'utf-8').split(/\r?\n/);
const outLines = [];

// Header erstellen
outLines.push('Category,SubCategory,SubCategoryGroup,GroupType,Current_Form_Name,Correct_Form_Name');

// Datenzeilen verarbeiten
for (let i = 1; i < lines.length; i++) {
    const line = lines[i].trim();
    if (!line || line.startsWith('-') || line.startsWith('|')) continue;
    
    // Zeile mit | aufteilen und Leerzeichen entfernen
    const cols = line.split('|').map(s => s.trim());
    if (cols.length >= 5) {
        const category = cols[0];
        const subcategory = cols[1];
        const subcategorygroup = cols[2];
        const grouptype = cols[3];
        const currentFormName = cols[4];
        
        // Englische Namen extrahieren und zu CamelCase konvertieren
        const catKey = category.toLowerCase();
        const subKey = toCamelCase(extractKey(subcategory));
        const groupKey = toCamelCase(extractKey(subcategorygroup));
        const typeKey = toCamelCase(extractKey(grouptype));
        
        // form_name zusammensetzen (nur zwischen Ebenen Bindestriche)
        const correctFormName = `${catKey}-${subKey}-${groupKey}-${typeKey}`;
        
        // CSV-Zeile mit Kommas erstellen
        const csvLine = `"${catKey}","${subKey}","${groupKey}","${typeKey}","${currentFormName}","${correctFormName}"`;
        outLines.push(csvLine);
    }
}

fs.writeFileSync(outputFile, outLines.join('\n'), 'utf-8');
console.log('CSV mit korrekten Form-Namen (CamelCase) gespeichert als', outputFile);
console.log('Anzahl Einträge:', outLines.length - 1); // -1 für Header 