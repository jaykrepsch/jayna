const fs = require('fs');
const path = require('path');

// Phase 1 Ergebnisse einlesen
const phase1File = path.join(__dirname, 'phase1_analysis.json');
const phase1Data = JSON.parse(fs.readFileSync(phase1File, 'utf-8'));

// Pfade definieren
const formDefPath = 'src/main/webapp/app/shared/form-definitions';
const i18nPath = 'src/main/webapp/i18n/de';

// Mapping sammeln
const mapping = {};

// Hilfsfunktion: Bindestrich-getrennte Wörter zu CamelCase konvertieren
function toCamelCase(str) {
    if (!str) return '';
    return str.split('-').map((word, index) => {
        if (index === 0) return word; // Erstes Wort bleibt klein
        return word.charAt(0).toUpperCase() + word.slice(1); // Reste werden groß geschrieben
    }).join('');
}

// Für jede Kategorie analysieren
for (const category of Object.keys(phase1Data.categories)) {
    console.log(`\n=== Analysiere ${category.toUpperCase()} ===`);
    
    mapping[category] = {
        formDefinitions: [],
        i18nFiles: []
    };
    
    // Formulardefinitionen analysieren
    const formDefDir = path.join(formDefPath, category);
    if (fs.existsSync(formDefDir)) {
        const files = fs.readdirSync(formDefDir).filter(f => f.endsWith('.json'));
        console.log(`Formulardefinitionen gefunden: ${files.length}`);
        
        for (const file of files) {
            const oldFormName = file.replace('.json', '');
            const parts = oldFormName.split('-');
            
            if (parts.length >= 4) {
                const cat = parts[0];
                const sub = toCamelCase(parts[1]);
                const group = toCamelCase(parts[2]);
                const type = toCamelCase(parts[3]);
                
                const newFormName = `${cat}-${sub}-${group}-${type}`;
                
                mapping[category].formDefinitions.push({
                    oldName: oldFormName,
                    newName: newFormName,
                    fileName: file,
                    needsRename: oldFormName !== newFormName
                });
            }
        }
    }
    
    // i18n-Dateien analysieren
    const i18nDir = path.join(i18nPath, category);
    if (fs.existsSync(i18nDir)) {
        const files = fs.readdirSync(i18nDir).filter(f => f.startsWith('i18n-') && f.endsWith('.json'));
        console.log(`i18n-Dateien gefunden: ${files.length}`);
        
        for (const file of files) {
            const oldFormName = file.replace('i18n-', '').replace('.json', '');
            const parts = oldFormName.split('-');
            
            if (parts.length >= 4) {
                const cat = parts[0];
                const sub = toCamelCase(parts[1]);
                const group = toCamelCase(parts[2]);
                const type = toCamelCase(parts[3]);
                
                const newFormName = `${cat}-${sub}-${group}-${type}`;
                
                mapping[category].i18nFiles.push({
                    oldName: oldFormName,
                    newName: newFormName,
                    fileName: file,
                    needsRename: oldFormName !== newFormName
                });
            }
        }
    }
}

// Ergebnis speichern
const outputFile = path.join(__dirname, 'phase2_mapping.json');
fs.writeFileSync(outputFile, JSON.stringify(mapping, null, 2), 'utf-8');

// Zusammenfassung ausgeben
console.log('\n=== PHASE 2: Mapping Zusammenfassung ===');
let totalFormDefs = 0;
let totalFormDefsToRename = 0;
let totalI18n = 0;
let totalI18nToRename = 0;

for (const [category, data] of Object.entries(mapping)) {
    const formDefsToRename = data.formDefinitions.filter(f => f.needsRename).length;
    const i18nToRename = data.i18nFiles.filter(f => f.needsRename).length;
    
    totalFormDefs += data.formDefinitions.length;
    totalFormDefsToRename += formDefsToRename;
    totalI18n += data.i18nFiles.length;
    totalI18nToRename += i18nToRename;
    
    console.log(`\n${category.toUpperCase()}:`);
    console.log(`  Formulardefinitionen: ${data.formDefinitions.length} (${formDefsToRename} müssen umbenannt werden)`);
    console.log(`  i18n-Dateien: ${data.i18nFiles.length} (${i18nToRename} müssen umbenannt werden)`);
    
    // Beispiele für Umbenennungen
    if (formDefsToRename > 0) {
        console.log(`  Beispiel Formulardefinition:`);
        const example = data.formDefinitions.find(f => f.needsRename);
        console.log(`    ${example.oldName} → ${example.newName}`);
    }
}

console.log(`\nGESAMT:`);
console.log(`  Formulardefinitionen: ${totalFormDefs} (${totalFormDefsToRename} müssen umbenannt werden)`);
console.log(`  i18n-Dateien: ${totalI18n} (${totalI18nToRename} müssen umbenannt werden)`);
console.log(`\nMapping gespeichert in: ${outputFile}`);

// CSV für einfache Bearbeitung erstellen
const csvLines = ['Category,Type,OldName,NewName,FileName,NeedsRename'];
for (const [category, data] of Object.entries(mapping)) {
    for (const item of data.formDefinitions) {
        csvLines.push(`${category},formDef,${item.oldName},${item.newName},${item.fileName},${item.needsRename}`);
    }
    for (const item of data.i18nFiles) {
        csvLines.push(`${category},i18n,${item.oldName},${item.newName},${item.fileName},${item.needsRename}`);
    }
}

const csvFile = path.join(__dirname, 'phase2_mapping.csv');
fs.writeFileSync(csvFile, csvLines.join('\n'), 'utf-8');
console.log(`CSV-Mapping gespeichert in: ${csvFile}`); 