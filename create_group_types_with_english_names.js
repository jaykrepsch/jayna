const fs = require('fs');
const path = require('path');

// CSV einlesen (aus group_types_complete.csv)
const inputFile = path.join(__dirname, 'group_types_complete.csv');
const outputFile = path.join(__dirname, 'group_types_english_names.csv');

const lines = fs.readFileSync(inputFile, 'utf-8').split(/\r?\n/);
const outLines = [];

// Header erstellen
outLines.push('Category,SubCategory,SubCategoryGroup,GroupType,Form_Name,Form_Name_Teil1,Form_Name_Teil2,Form_Name_Teil3,Form_Name_Teil4,Korrektur_Notwendig,Korrekter_Form_Name');

// Datenzeilen verarbeiten
for (let i = 1; i < lines.length; i++) {
    const line = lines[i].trim();
    if (!line || line.startsWith('-') || line.startsWith('|')) continue;
    
    // Zeile mit | aufteilen und Leerzeichen entfernen
    const cols = line.split('|').map(s => s.trim()).filter(s => s);
    
    if (cols.length >= 5) {
        const kategorie = cols[0];
        const art = cols[1];
        const gruppierung = cols[2];
        const sparte = cols[3];
        const formName = cols[4];
        const parts = formName.split('-');
        // Bis zu 4 Teile, Rest leer
        const p1 = parts[0] || '';
        const p2 = parts[1] || '';
        const p3 = parts[2] || '';
        const p4 = parts[3] || '';
        
        // CSV-Zeile mit Kommas erstellen
        const csvLine = `"${kategorie}","${art}","${gruppierung}","${sparte}","${formName}","${p1}","${p2}","${p3}","${p4}","",""`;
        outLines.push(csvLine);
    }
}

fs.writeFileSync(outputFile, outLines.join('\n'), 'utf-8');
console.log('CSV mit englischen Namen gespeichert als', outputFile);
console.log('Anzahl Einträge:', outLines.length - 1); // -1 für Header 