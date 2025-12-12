const fs = require('fs');
const path = require('path');

// Funktion um alle konkreten Formulardefinitionen zu finden (ohne base)
function findConcreteFormDefs(dir) {
  const files = [];

  function traverse(currentDir) {
    const items = fs.readdirSync(currentDir);

    for (const item of items) {
      const fullPath = path.join(currentDir, item);
      const stat = fs.statSync(fullPath);

      if (stat.isDirectory()) {
        traverse(fullPath);
      } else if (item.endsWith('.json') && !item.includes('base') && !item.includes('central')) {
        files.push(fullPath);
      }
    }
  }

  traverse(dir);
  return files;
}

// Funktion um konkrete Formulardefinitionen zu aktualisieren
function updateConcreteFormDef(filePath) {
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
    if (formDef.parts) {
      const organisationIndex = formDef.parts.findIndex(part =>
        part.title && part.title.includes('organisation')
      );

      if (organisationIndex !== -1) {
        const organisationPart = formDef.parts[organisationIndex];
        if (organisationPart.fields && organisationPart.fields[0] &&
            organisationPart.fields[0].type === 'info') {
          // Ersetze info-Feld mit table-Feld
          organisationPart.fields = [{
            "name": "organisationTable",
            "type": "organisation-table",
            "label": "Kalender, Aufgaben & Notizen"
          }];
          updated = true;
        }
      }
    }

    // Dokumente-Abschnitt aktualisieren
    if (formDef.parts) {
      const documentsIndex = formDef.parts.findIndex(part =>
        part.title && part.title.includes('documents') && !part.title.includes('documentRelations')
      );

      if (documentsIndex !== -1) {
        const documentsPart = formDef.parts[documentsIndex];
        if (documentsPart.fields && documentsPart.fields[0] &&
            documentsPart.fields[0].type === 'info') {
          // Ersetze info-Feld mit table-Feld
          documentsPart.fields = [{
            "name": "documentsTable",
            "type": "documents-table",
            "label": "Verknüpfte Dokumente"
          }];
          updated = true;
        }
      }
    }

    if (updated) {
      fs.writeFileSync(filePath, JSON.stringify(formDef, null, 2), 'utf8');
      console.log(`✓ Aktualisiert: ${filePath}`);
      return true;
    } else {
      console.log(`- Keine Änderungen: ${filePath}`);
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

  console.log('🔍 Suche nach konkreten Formulardefinitionen...');
  const jsonFiles = findConcreteFormDefs(formDefsDir);

  console.log(`📝 Gefunden: ${jsonFiles.length} Dateien`);
  console.log('');

  let updatedCount = 0;
  for (const file of jsonFiles) {
    if (updateConcreteFormDef(file)) {
      updatedCount++;
    }
  }

  console.log('');
  console.log(`✅ Fertig! ${updatedCount} konkrete Formulardefinitionen aktualisiert.`);
}

// Skript ausführen
main();
