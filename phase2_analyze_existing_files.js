const fs = require('fs');
const path = require('path');

// Phase 1 Ergebnisse einlesen
const phase1File = path.join(__dirname, 'phase1_analysis.json');
const phase1Data = JSON.parse(fs.readFileSync(phase1File, 'utf-8'));

// Pfade definieren
const formDefPath = 'src/main/webapp/app/shared/form-definitions';
const i18nPath = 'src/main/webapp/i18n/de';

// Ergebnisse sammeln
const analysis = {};

// Für jede Kategorie analysieren
for (const category of Object.keys(phase1Data.categories)) {
    console.log(`\n=== Analysiere ${category.toUpperCase()} ===`);
    
    analysis[category] = {
        formDefinitions: {
            existing: [],
            missing: [],
            total: 0
        },
        i18nFiles: {
            existing: [],
            missing: [],
            total: 0
        }
    };
    
    // Formulardefinitionen prüfen
    const formDefDir = path.join(formDefPath, category);
    if (fs.existsSync(formDefDir)) {
        const files = fs.readdirSync(formDefDir).filter(f => f.endsWith('.json'));
        analysis[category].formDefinitions.existing = files.map(f => f.replace('.json', ''));
        console.log(`Formulardefinitionen gefunden: ${files.length}`);
    } else {
        console.log(`Formulardefinitionen Verzeichnis nicht gefunden: ${formDefDir}`);
    }
    
    // i18n-Dateien prüfen
    const i18nDir = path.join(i18nPath, category);
    if (fs.existsSync(i18nDir)) {
        const files = fs.readdirSync(i18nDir).filter(f => f.startsWith('i18n-') && f.endsWith('.json'));
        analysis[category].i18nFiles.existing = files.map(f => f.replace('i18n-', '').replace('.json', ''));
        console.log(`i18n-Dateien gefunden: ${files.length}`);
    } else {
        console.log(`i18n Verzeichnis nicht gefunden: ${i18nDir}`);
    }
    
    // Fehlende Dateien identifizieren
    const expectedFormNames = phase1Data.categories[category].map(item => item.correctFormName);
    analysis[category].formDefinitions.total = expectedFormNames.length;
    analysis[category].formDefinitions.missing = expectedFormNames.filter(formName => 
        !analysis[category].formDefinitions.existing.includes(formName)
    );
    
    analysis[category].i18nFiles.total = expectedFormNames.length;
    analysis[category].i18nFiles.missing = expectedFormNames.filter(formName => 
        !analysis[category].i18nFiles.existing.includes(formName)
    );
    
    console.log(`Erwartete form_name: ${expectedFormNames.length}`);
    console.log(`Fehlende Formulardefinitionen: ${analysis[category].formDefinitions.missing.length}`);
    console.log(`Fehlende i18n-Dateien: ${analysis[category].i18nFiles.missing.length}`);
}

// Ergebnis speichern
const outputFile = path.join(__dirname, 'phase2_analysis.json');
fs.writeFileSync(outputFile, JSON.stringify(analysis, null, 2), 'utf-8');

// Zusammenfassung ausgeben
console.log('\n=== PHASE 2: Zusammenfassung ===');
let totalFormDefs = 0;
let totalMissingFormDefs = 0;
let totalI18n = 0;
let totalMissingI18n = 0;

for (const [category, data] of Object.entries(analysis)) {
    totalFormDefs += data.formDefinitions.total;
    totalMissingFormDefs += data.formDefinitions.missing.length;
    totalI18n += data.i18nFiles.total;
    totalMissingI18n += data.i18nFiles.missing.length;
    
    console.log(`\n${category.toUpperCase()}:`);
    console.log(`  Formulardefinitionen: ${data.formDefinitions.existing.length}/${data.formDefinitions.total} vorhanden`);
    console.log(`  i18n-Dateien: ${data.i18nFiles.existing.length}/${data.i18nFiles.total} vorhanden`);
}

console.log(`\nGESAMT:`);
console.log(`  Formulardefinitionen: ${totalFormDefs - totalMissingFormDefs}/${totalFormDefs} vorhanden (${totalMissingFormDefs} fehlen)`);
console.log(`  i18n-Dateien: ${totalI18n - totalMissingI18n}/${totalI18n} vorhanden (${totalMissingI18n} fehlen)`);
console.log(`\nErgebnis gespeichert in: ${outputFile}`); 