const fs = require('fs');
const path = require('path');

console.log('=== ENTFERNUNG ALLER BASE-DATEIEN UND BASE-REFERENZEN ===\n');

const formDefPath = 'src/main/webapp/app/shared/form-definitions';
const i18nPath = 'src/main/webapp/i18n/de';

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

// Alle base-Dateien finden
const allFormDefFiles = getAllFiles(formDefPath, '.json');
const allI18nFiles = getAllFiles(i18nPath, '.json');

const baseFormDefs = allFormDefFiles.filter(f => path.basename(f).includes('base'));
const baseI18nFiles = allI18nFiles.filter(f => path.basename(f).includes('base'));

console.log('🗑️  ZU LÖSCHENDE BASE-DATEIEN:');

console.log('\n📄 BASE FORMULARDEFINITIONEN:');
baseFormDefs.forEach((file, index) => {
    const relativePath = file.replace(formDefPath + '\\', '');
    console.log(`   ${index + 1}. ${relativePath}`);
    try {
        fs.unlinkSync(file);
        console.log(`     ✅ Gelöscht: ${relativePath}`);
    } catch (err) {
        console.log(`     ❌ Fehler beim Löschen: ${relativePath}`);
    }
});

console.log('\n🌐 BASE I18N-DATEIEN:');
baseI18nFiles.forEach((file, index) => {
    const relativePath = file.replace(i18nPath + '\\', '');
    console.log(`   ${index + 1}. ${relativePath}`);
    try {
        fs.unlinkSync(file);
        console.log(`     ✅ Gelöscht: ${relativePath}`);
    } catch (err) {
        console.log(`     ❌ Fehler beim Löschen: ${relativePath}`);
    }
});

// Base-Referenzen aus Formulardefinitionen entfernen
console.log('\n🔧 BASE-REFERENZEN ENTFERNEN:');

const formDefsWithBaseRefs = allFormDefFiles.filter(f => {
    if (fs.existsSync(f)) {
        const content = fs.readFileSync(f, 'utf-8');
        return content.includes('base') && !path.basename(f).includes('base');
    }
    return false;
});

formDefsWithBaseRefs.forEach(file => {
    const relativePath = file.replace(formDefPath + '\\', '');
    console.log(`   Bearbeite: ${relativePath}`);
    
    try {
        let content = fs.readFileSync(file, 'utf-8');
        
        // Base-Referenzen entfernen
        content = content.replace(/"jaynaApp\.base[A-Za-z]+\./g, '"jaynaApp.');
        content = content.replace(/"formTitle": "jaynaApp\.base[A-Za-z]+\.base-title"/g, '"formTitle": "jaynaApp.title"');
        content = content.replace(/"title": "jaynaApp\.base[A-Za-z]+\./g, '"title": "jaynaApp.');
        
        fs.writeFileSync(file, content, 'utf-8');
        console.log(`     ✅ Base-Referenzen entfernt`);
    } catch (err) {
        console.log(`     ❌ Fehler beim Bearbeiten: ${err.message}`);
    }
});

console.log('\n✅ BASE-DATEIEN UND BASE-REFERENZEN ENTFERNT!'); 