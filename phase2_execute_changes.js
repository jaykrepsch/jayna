const fs = require('fs');
const path = require('path');

// Mapping einlesen
const mappingFile = path.join(__dirname, 'phase2_mapping.json');
const mapping = JSON.parse(fs.readFileSync(mappingFile, 'utf-8'));

// Phase 1 Ergebnisse einlesen
const phase1File = path.join(__dirname, 'phase1_analysis.json');
const phase1Data = JSON.parse(fs.readFileSync(phase1File, 'utf-8'));

// Pfade definieren
const formDefPath = 'src/main/webapp/app/shared/form-definitions';
const i18nPath = 'src/main/webapp/i18n/de';

console.log('=== PHASE 2: Ausführung der Änderungen ===');

// 1. Dateien umbenennen
console.log('\n1. Umbenennen der Dateien...');
let renamedCount = 0;

for (const [category, data] of Object.entries(mapping)) {
    // Formulardefinitionen umbenennen
    for (const item of data.formDefinitions) {
        if (item.needsRename) {
            const oldPath = path.join(formDefPath, category, item.fileName);
            const newFileName = item.newName + '.json';
            const newPath = path.join(formDefPath, category, newFileName);
            
            if (fs.existsSync(oldPath)) {
                fs.renameSync(oldPath, newPath);
                console.log(`  Umbenannt: ${item.fileName} → ${newFileName}`);
                renamedCount++;
            }
        }
    }
    
    // i18n-Dateien umbenennen
    for (const item of data.i18nFiles) {
        if (item.needsRename) {
            const oldPath = path.join(i18nPath, category, item.fileName);
            const newFileName = 'i18n-' + item.newName + '.json';
            const newPath = path.join(i18nPath, category, newFileName);
            
            if (fs.existsSync(oldPath)) {
                fs.renameSync(oldPath, newPath);
                console.log(`  Umbenannt: ${item.fileName} → ${newFileName}`);
                renamedCount++;
            }
        }
    }
}

console.log(`\n${renamedCount} Dateien umbenannt.`);

// 2. Fehlende Dateien erstellen
console.log('\n2. Erstellen fehlender Dateien...');

// Template für Formulardefinitionen
function createFormDefinitionTemplate(formName, category) {
    const baseTemplate = category === 'contract' ? 'baseContract' : `base${category.charAt(0).toUpperCase() + category.slice(1)}`;
    
    return {
        "_comment": `formControl-Konzept: Standard-Abschnitte aus ${baseTemplate}, spezielle Abschnitte mit i18nSource`,
        "formTitle": formName.replace(/-/g, ' ').replace(/\b\w/g, l => l.toUpperCase()),
        "parts": [
            {
                "title": `jaynaApp.${baseTemplate}.general.title`,
                "fields": [
                    {
                        "name": "label",
                        "type": "text",
                        "rules": "required"
                    },
                    {
                        "name": "description",
                        "type": "text"
                    },
                    {
                        "name": "entityState",
                        "type": "dropdown",
                        "boxLabel": "global.menu.choose",
                        "enumName": "entityState",
                        "defaultValue": "ACTIVE"
                    }
                ]
            },
            {
                "title": `jaynaApp.${formName}.specificData.title`,
                "i18nSource": formName,
                "fields": [
                    {
                        "name": "specificField1",
                        "type": "text"
                    },
                    {
                        "name": "specificField2",
                        "type": "number"
                    }
                ]
            },
            {
                "title": `jaynaApp.${baseTemplate}.weiteres.title`,
                "fields": [
                    {
                        "name": "comment",
                        "type": "textarea"
                    },
                    {
                        "name": "notes",
                        "type": "textarea"
                    }
                ]
            }
        ],
        "relations": [
            {
                "entityName": "contact",
                "relationName": `${category}Contacts`
            },
            {
                "entityName": "contract",
                "relationName": `${category}Contracts`
            }
        ],
        "relationsTitle": `jaynaApp.${formName}.relations-title`
    };
}

// Template für i18n-Dateien
function createI18nTemplate(formName) {
    const displayName = formName.split('-').slice(1).join(' ').replace(/\b\w/g, l => l.toUpperCase());
    
    return {
        [`jaynaApp.${formName}.createTitle`]: `${displayName} erstellen`,
        [`jaynaApp.${formName}.updateTitle`]: `${displayName} bearbeiten`,
        [`jaynaApp.${formName}.viewTitle`]: `${displayName} anzeigen`,
        
        [`jaynaApp.${formName}.specificData.title`]: "Spezifische Daten",
        [`jaynaApp.${formName}.specificData.fields.specificField1`]: "Spezifisches Feld 1",
        [`jaynaApp.${formName}.specificData.fields.specificField2`]: "Spezifisches Feld 2",
        
        [`jaynaApp.${formName}.relations-title`]: "Verknüpfungen"
    };
}

let createdFormDefs = 0;
let createdI18n = 0;

for (const [category, items] of Object.entries(phase1Data.categories)) {
    const formDefDir = path.join(formDefPath, category);
    const i18nDir = path.join(i18nPath, category);
    
    // Verzeichnisse erstellen falls nicht vorhanden
    if (!fs.existsSync(formDefDir)) {
        fs.mkdirSync(formDefDir, { recursive: true });
    }
    if (!fs.existsSync(i18nDir)) {
        fs.mkdirSync(i18nDir, { recursive: true });
    }
    
    for (const item of items) {
        const formName = item.correctFormName;
        
        // Formulardefinition erstellen falls nicht vorhanden
        const formDefFile = path.join(formDefDir, formName + '.json');
        if (!fs.existsSync(formDefFile)) {
            const template = createFormDefinitionTemplate(formName, category);
            fs.writeFileSync(formDefFile, JSON.stringify(template, null, 2), 'utf-8');
            console.log(`  Formulardefinition erstellt: ${formName}.json`);
            createdFormDefs++;
        }
        
        // i18n-Datei erstellen falls nicht vorhanden
        const i18nFile = path.join(i18nDir, 'i18n-' + formName + '.json');
        if (!fs.existsSync(i18nFile)) {
            const template = createI18nTemplate(formName);
            fs.writeFileSync(i18nFile, JSON.stringify(template, null, 2), 'utf-8');
            console.log(`  i18n-Datei erstellt: i18n-${formName}.json`);
            createdI18n++;
        }
    }
}

console.log(`\n${createdFormDefs} Formulardefinitionen erstellt.`);
console.log(`${createdI18n} i18n-Dateien erstellt.`);

// 3. SQL-Update-Befehle generieren
console.log('\n3. SQL-Update-Befehle generieren...');
const sqlFile = path.join(__dirname, 'phase2_database_updates.sql');
const sqlLines = [
    '-- PHASE 2: Datenbank-Updates für neue form_name',
    '-- Ausführen nach der Überprüfung der generierten Dateien',
    ''
];

for (const [category, items] of Object.entries(phase1Data.categories)) {
    sqlLines.push(`-- ${category.toUpperCase()}`);
    for (const item of items) {
        sqlLines.push(`UPDATE group_type SET form_name = '${item.correctFormName}' WHERE id = (SELECT gt.id FROM group_type gt JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id JOIN sub_category sc ON scg.sub_category_id = sc.id JOIN category c ON sc.category_id = c.id WHERE c.entity_name = '${item.category}' AND sc.name = '${item.subcategory}' AND scg.name = '${item.subcategorygroup}' AND gt.name = '${item.grouptype}');`);
    }
    sqlLines.push('');
}

fs.writeFileSync(sqlFile, sqlLines.join('\n'), 'utf-8');
console.log(`SQL-Datei erstellt: ${sqlFile}`);

console.log('\n=== PHASE 2: Abgeschlossen ===');
console.log(`- ${renamedCount} Dateien umbenannt`);
console.log(`- ${createdFormDefs} Formulardefinitionen erstellt`);
console.log(`- ${createdI18n} i18n-Dateien erstellt`);
console.log(`- SQL-Updates generiert`);
console.log('\nNächste Schritte:');
console.log('1. Überprüfen der erstellten Dateien');
console.log('2. SQL-Updates in der Datenbank ausführen');
console.log('3. Frontend testen'); 