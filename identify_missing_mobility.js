const fs = require('fs');
const path = require('path');

console.log('=== IDENTIFIZIERUNG FEHLENDER MOBILITY FORM_NAME ===\n');

const phase1File = path.join(__dirname, 'phase1_analysis.json');
const phase1Data = JSON.parse(fs.readFileSync(phase1File, 'utf-8'));

// Alle erwarteten form_name sammeln (korrekte Struktur)
const expectedFormNames = [];
for (const [category, categoryData] of Object.entries(phase1Data.summary.stats)) {
    if (categoryData.items) {
        categoryData.items.forEach(item => {
            expectedFormNames.push({
                formName: item.correctFormName,
                category: category
            });
        });
    }
}

// Nur MOBILITY form_name filtern
const mobilityFormNames = expectedFormNames.filter(item => item.category === 'mobility');

// Vorhandene Formulardefinitionen finden
const formDefPath = 'src/main/webapp/app/shared/form-definitions/mobility';
const existingFormDefs = [];
if (fs.existsSync(formDefPath)) {
    const files = fs.readdirSync(formDefPath);
    files.forEach(file => {
        if (file.endsWith('.json')) {
            existingFormDefs.push(file.replace('.json', ''));
        }
    });
}

// Vorhandene i18n-Dateien finden
const i18nPath = 'src/main/webapp/i18n/de/mobility';
const existingI18n = [];
if (fs.existsSync(i18nPath)) {
    const files = fs.readdirSync(i18nPath);
    files.forEach(file => {
        if (file.startsWith('i18n-') && file.endsWith('.json')) {
            existingI18n.push(file.replace('i18n-', '').replace('.json', ''));
        }
    });
}

console.log('📊 MOBILITY ANALYSE:');
console.log(`   Erwartete form_name: ${mobilityFormNames.length}`);
console.log(`   Vorhandene FormDefs: ${existingFormDefs.length}`);
console.log(`   Vorhandene i18n: ${existingI18n.length}`);

// Fehlende identifizieren
const missingFormDefs = [];
const missingI18n = [];

mobilityFormNames.forEach(item => {
    if (!existingFormDefs.includes(item.formName)) {
        missingFormDefs.push(item.formName);
    }
    if (!existingI18n.includes(item.formName)) {
        missingI18n.push(item.formName);
    }
});

console.log('\n❌ FEHLENDE FORMULARDEFINITIONEN:');
if (missingFormDefs.length === 0) {
    console.log('   Keine fehlenden Formulardefinitionen');
} else {
    missingFormDefs.forEach((formName, index) => {
        console.log(`   ${index + 1}. ${formName}`);
    });
}

console.log('\n❌ FEHLENDE I18N-DATEIEN:');
if (missingI18n.length === 0) {
    console.log('   Keine fehlenden i18n-Dateien');
} else {
    missingI18n.forEach((formName, index) => {
        console.log(`   ${index + 1}. ${formName}`);
    });
}

// Duplikate in der ursprünglichen Liste finden
const formNameCounts = {};
mobilityFormNames.forEach(item => {
    formNameCounts[item.formName] = (formNameCounts[item.formName] || 0) + 1;
});

const duplicates = Object.entries(formNameCounts).filter(([name, count]) => count > 1);

console.log('\n🔄 DUPLIKATE IN DER FORM_NAME LISTE:');
if (duplicates.length === 0) {
    console.log('   Keine Duplikate gefunden');
} else {
    duplicates.forEach(([name, count]) => {
        console.log(`   ${name}: ${count}x`);
    });
}

console.log('\n📝 ZUSAMMENFASSUNG:');
console.log(`   Fehlende FormDefs: ${missingFormDefs.length}`);
console.log(`   Fehlende i18n: ${missingI18n.length}`);
console.log(`   Duplikate: ${duplicates.length}`);

// Alle erwarteten form_name auflisten
console.log('\n📋 ALLE ERWARTETEN MOBILITY FORM_NAME:');
mobilityFormNames.forEach((item, index) => {
    console.log(`   ${index + 1}. ${item.formName}`);
}); 