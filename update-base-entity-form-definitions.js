const fs = require('fs');
const path = require('path');

// Funktion um alle baseEntity Formulardefinitionen zu finden
function findBaseEntityFormDefs(dir) {
  const files = [];

  function traverse(currentDir) {
    const items = fs.readdirSync(currentDir);

    for (const item of items) {
      const fullPath = path.join(currentDir, item);
      const stat = fs.statSync(fullPath);

      if (stat.isDirectory()) {
        traverse(fullPath);
      } else if (item.includes('base') && item.endsWith('.json') && !item.includes('central')) {
        files.push(fullPath);
      }
    }
  }

  traverse(dir);
  return files;
}

// Funktion um baseEntity Formulardefinitionen zu aktualisieren
function updateBaseEntityFormDef(filePath) {
  try {
    const content = fs.readFileSync(filePath, 'utf8');
    const formDef = JSON.parse(content);

    // Bestimme Entity-Typ aus dem Pfad
    let entityType = 'Contract'; // Default
    if (filePath.includes('/contact/')) entityType = 'Contact';
    if (filePath.includes('/mobility/')) entityType = 'Mobility';
    if (filePath.includes('/realestate/')) entityType = 'Realestate';
    if (filePath.includes('/financeaccount/')) entityType = 'Financeaccount';
    if (filePath.includes('/document/')) entityType = 'Document';

    let updated = false;

    // Organisation-Abschnitt aktualisieren
    if (formDef.organisation && !formDef.organisation.fields) {
      formDef.organisation = {
        "title": `jaynaApp.base${entityType}.organisation.title`,
        "fields": [
          {
            "name": "organisationTable",
            "type": "organisation-table",
            "label": "Kalender, Aufgaben & Notizen"
          }
        ]
      };
      updated = true;
    }

    // Dokumente-Abschnitt aktualisieren
    if (formDef.documents && !formDef.documents.fields) {
      formDef.documents = {
        "title": `jaynaApp.base${entityType}.documents.title`,
        "fields": [
          {
            "name": "documentsTable",
            "type": "documents-table",
            "label": "Verknüpfte Dokumente"
          }
        ]
      };
      updated = true;
    }

    if (updated) {
      fs.writeFileSync(filePath, JSON.stringify(formDef, null, 2), 'utf8');
      console.log(`✓ Aktualisiert: ${filePath}`);
      return true;
    } else {
      console.log(`- Bereits aktuell: ${filePath}`);
      return false;
    }

  } catch (error) {
    console.error(`✗ Fehler bei ${filePath}:`, error.message);
    return false;
  }
}

// Hauptfunktion
function main() {
  const formDefsDir = 'src/main/webapp/app/shared/form-definitions';

  if (!fs.existsSync(formDefsDir)) {
    console.error(`Verzeichnis ${formDefsDir} nicht gefunden!`);
    return;
  }

  console.log('🔍 Suche nach baseEntity Formulardefinitionen...');
  const jsonFiles = findBaseEntityFormDefs(formDefsDir);

  console.log(`📝 Gefunden: ${jsonFiles.length} Dateien`);
  console.log('');

  let updatedCount = 0;
  for (const file of jsonFiles) {
    if (updateBaseEntityFormDef(file)) {
      updatedCount++;
    }
  }

  console.log('');
  console.log(`✅ Fertig! ${updatedCount} baseEntity Formulardefinitionen aktualisiert.`);
}

// Skript ausführen
main();
