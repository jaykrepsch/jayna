const fs = require('fs');
const path = require('path');

console.log('=== Vollständigkeitsprüfung: form_name, Formulardefinitionen und i18n ===\n');

// Pfade definieren
const formDefPath = 'src/main/webapp/app/shared/form-definitions';
const i18nPath = 'src/main/webapp/i18n/de';

// Phase 1 Ergebnisse einlesen (alle erwarteten form_name)
const phase1File = path.join(__dirname, 'phase1_analysis.json');
const phase1Data = JSON.parse(fs.readFileSync(phase1File, 'utf-8'));

// Alle erwarteten form_name sammeln
const expectedFormNames = [];
for (const [category, items] of Object.entries(phase1Data.categories)) {
    for (const item of items) {
        expectedFormNames.push({
            formName: item.correctFormName,
            category: category,
            original: item
        });
    }
}

console.log(`Erwartete form_name: ${expectedFormNames.length}`);

// Vorhandene Formulardefinitionen sammeln
const existingFormDefs = [];
for (const category of Object.keys(phase1Data.categories)) {
    const formDefDir = path.join(formDefPath, category);
    if (fs.existsSync(formDefDir)) {
        const files = fs.readdirSync(formDefDir).filter(f => f.endsWith('.json'));
        for (const file of files) {
            const formName = file.replace('.json', '');
            existingFormDefs.push({
                formName: formName,
                category: category,
                fileName: file
            });
        }
    }
}

console.log(`Vorhandene Formulardefinitionen: ${existingFormDefs.length}`);

// Vorhandene i18n-Dateien sammeln
const existingI18n = [];
for (const category of Object.keys(phase1Data.categories)) {
    const i18nDir = path.join(i18nPath, category);
    if (fs.existsSync(i18nDir)) {
        const files = fs.readdirSync(i18nDir).filter(f => f.startsWith('i18n-') && f.endsWith('.json'));
        for (const file of files) {
            const formName = file.replace('i18n-', '').replace('.json', '');
            existingI18n.push({
                formName: formName,
                category: category,
                fileName: file
            });
        }
    }
}

console.log(`Vorhandene i18n-Dateien: ${existingI18n.length}\n`);

// Analyse durchführen
const analysis = {
    complete: [],
    missingFormDef: [],
    missingI18n: [],
    extraFormDef: [],
    extraI18n: []
};

// Prüfen: Erwartete form_name vs. vorhandene Dateien
for (const expected of expectedFormNames) {
    const hasFormDef = existingFormDefs.some(fd => fd.formName === expected.formName);
    const hasI18n = existingI18n.some(i18n => i18n.formName === expected.formName);
    
    if (hasFormDef && hasI18n) {
        analysis.complete.push(expected);
    } else {
        if (!hasFormDef) {
            analysis.missingFormDef.push(expected);
        }
        if (!hasI18n) {
            analysis.missingI18n.push(expected);
        }
    }
}

// Prüfen: Zusätzliche Dateien (nicht erwartet)
for (const formDef of existingFormDefs) {
    const isExpected = expectedFormNames.some(exp => exp.formName === formDef.formName);
    if (!isExpected) {
        analysis.extraFormDef.push(formDef);
    }
}

for (const i18n of existingI18n) {
    const isExpected = expectedFormNames.some(exp => exp.formName === i18n.formName);
    if (!isExpected) {
        analysis.extraI18n.push(i18n);
    }
}

// Ergebnisse ausgeben
console.log('=== ERGEBNISSE ===');
console.log(`✅ Vollständig (FormDef + i18n): ${analysis.complete.length}`);
console.log(`❌ Fehlende Formulardefinitionen: ${analysis.missingFormDef.length}`);
console.log(`❌ Fehlende i18n-Dateien: ${analysis.missingI18n.length}`);
console.log(`⚠️  Zusätzliche Formulardefinitionen: ${analysis.extraFormDef.length}`);
console.log(`⚠️  Zusätzliche i18n-Dateien: ${analysis.extraI18n.length}`);

// Details ausgeben
if (analysis.missingFormDef.length > 0) {
    console.log('\n=== FEHLENDE FORMULARDEFINITIONEN ===');
    for (const item of analysis.missingFormDef) {
        console.log(`  ${item.formName} (${item.category})`);
    }
}

if (analysis.missingI18n.length > 0) {
    console.log('\n=== FEHLENDE I18N-DATEIEN ===');
    for (const item of analysis.missingI18n) {
        console.log(`  ${item.formName} (${item.category})`);
    }
}

if (analysis.extraFormDef.length > 0) {
    console.log('\n=== ZUSÄTZLICHE FORMULARDEFINITIONEN ===');
    for (const item of analysis.extraFormDef) {
        console.log(`  ${item.formName} (${item.category}) - ${item.fileName}`);
    }
}

if (analysis.extraI18n.length > 0) {
    console.log('\n=== ZUSÄTZLICHE I18N-DATEIEN ===');
    for (const item of analysis.extraI18n) {
        console.log(`  ${item.formName} (${item.category}) - ${item.fileName}`);
    }
}

// Nach Kategorien aufschlüsseln
console.log('\n=== NACH KATEGORIEN ===');
for (const category of Object.keys(phase1Data.categories)) {
    const categoryExpected = expectedFormNames.filter(exp => exp.category === category);
    const categoryFormDefs = existingFormDefs.filter(fd => fd.category === category);
    const categoryI18n = existingI18n.filter(i18n => i18n.category === category);
    
    console.log(`${category.toUpperCase()}:`);
    console.log(`  Erwartet: ${categoryExpected.length}`);
    console.log(`  FormDefs: ${categoryFormDefs.length}`);
    console.log(`  i18n: ${categoryI18n.length}`);
}

// Ergebnis speichern
const outputFile = path.join(__dirname, 'verification_results.json');
fs.writeFileSync(outputFile, JSON.stringify(analysis, null, 2), 'utf-8');
console.log(`\nDetaillierte Ergebnisse gespeichert in: ${outputFile}`); 