const fs = require('fs');
const path = require('path');

// Funktion um alle Formulardefinitionen zu finden
function findAllFormDefs(dir) {
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

// Funktion um die Reihenfolge der Abschnitte zu korrigieren
function fixSectionOrder(filePath) {
  try {
    const content = fs.readFileSync(filePath, 'utf8');
    const formDef = JSON.parse(content);

    if (!formDef.parts || !Array.isArray(formDef.parts)) {
      return false;
    }

    let updated = false;

    // Finde die verschiedenen Abschnitte
    const relationsIndex = formDef.parts.findIndex(part =>
      part.title && part.title.includes('relations.title')
    );
    const organisationIndex = formDef.parts.findIndex(part =>
      part.title && part.title.includes('organisation.title')
    );
    const documentsIndex = formDef.parts.findIndex(part =>
      part.title && part.title.includes('documents.title') && !part.title.includes('documentRelations')
    );

    // Für Document-Formulardefinitionen: entferne den Dokumente-Abschnitt
    if (filePath.includes('/document/') && documentsIndex !== -1) {
      formDef.parts.splice(documentsIndex, 1);
      updated = true;
      console.log(`✓ Dokumente-Abschnitt entfernt: ${filePath}`);
    }

    // Korrekte Reihenfolge: relations, organisation, documents (falls nicht Document-Datei)
    if (relationsIndex !== -1 && organisationIndex !== -1) {
      // Wenn documentsIndex vorhanden ist (nicht in Document-Dateien)
      const targetDocumentsIndex = filePath.includes('/document/') ? -1 : documentsIndex;

      // Prüfe, ob die Reihenfolge korrekt ist
      let correctOrder = true;
      if (relationsIndex > organisationIndex) correctOrder = false;
      if (targetDocumentsIndex !== -1 && organisationIndex > targetDocumentsIndex) correctOrder = false;

      if (!correctOrder) {
        // Sammle alle Abschnitte außer den spezifischen
        const otherParts = formDef.parts.filter((_, index) =>
          index !== relationsIndex && index !== organisationIndex &&
          (targetDocumentsIndex === -1 || index !== targetDocumentsIndex)
        );

        // Erstelle neue Reihenfolge
        const relationsPart = formDef.parts[relationsIndex];
        const organisationPart = formDef.parts[organisationIndex];

        // Füge Verknüpfungen hinzu
        otherParts.splice(otherParts.length, 0, relationsPart);
        // Füge Organisation hinzu
        otherParts.splice(otherParts.length, 0, organisationPart);
        // Füge Dokumente hinzu (falls vorhanden)
        if (targetDocumentsIndex !== -1) {
          const documentsPart = formDef.parts[targetDocumentsIndex];
          otherParts.splice(otherParts.length, 0, documentsPart);
        }

        formDef.parts = otherParts;
        updated = true;
        console.log(`✓ Reihenfolge korrigiert: ${filePath}`);
      }
    }

    if (updated) {
      fs.writeFileSync(filePath, JSON.stringify(formDef, null, 2), 'utf8');
      return true;
    } else {
      console.log(`- Bereits korrekt: ${filePath}`);
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

  console.log('🔍 Suche nach Formulardefinitionen...');
  const jsonFiles = findAllFormDefs(formDefsDir);

  console.log(`📝 Gefunden: ${jsonFiles.length} Dateien`);
  console.log('');

  let fixedCount = 0;
  for (const file of jsonFiles) {
    if (fixSectionOrder(file)) {
      fixedCount++;
    }
  }

  console.log('');
  console.log(`✅ Fertig! ${fixedCount} Formulardefinitionen korrigiert.`);
}

// Skript ausführen
main();
