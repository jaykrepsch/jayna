const fs = require('fs');
const path = require('path');

// Funktion um alle konkreten Formulardefinitionen zu finden
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

// Funktion um die Reihenfolge der Abschnitte in Formulardefinitionen zu korrigieren
function fixFormDefinitionOrder(filePath) {
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

    // Wenn alle drei Abschnitte gefunden wurden und die Reihenfolge falsch ist
    if (relationsIndex !== -1 && organisationIndex !== -1 && documentsIndex !== -1) {
      // Korrekte Reihenfolge: relations, organisation, documents
      if (!(relationsIndex < organisationIndex && organisationIndex < documentsIndex)) {
        // Sammle alle Abschnitte außer den drei spezifischen
        const otherParts = formDef.parts.filter((_, index) =>
          index !== relationsIndex && index !== organisationIndex && index !== documentsIndex
        );

        // Erstelle neue Reihenfolge
        const relationsPart = formDef.parts[relationsIndex];
        const organisationPart = formDef.parts[organisationIndex];
        const documentsPart = formDef.parts[documentsIndex];

        // Finde die beste Position für die Verknüpfungen (normalerweise ganz am Ende)
        const relationsPosition = otherParts.length;
        otherParts.splice(relationsPosition, 0, relationsPart);
        otherParts.splice(relationsPosition + 1, 0, organisationPart);
        otherParts.splice(relationsPosition + 2, 0, documentsPart);

        formDef.parts = otherParts;
        updated = true;
      }
    }

    if (updated) {
      fs.writeFileSync(filePath, JSON.stringify(formDef, null, 2), 'utf8');
      console.log(`✓ Reihenfolge korrigiert: ${filePath}`);
      return true;
    } else {
      console.log(`- Reihenfolge bereits korrekt: ${filePath}`);
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
  const jsonFiles = findConcreteFormDefs(formDefsDir);

  console.log(`📝 Gefunden: ${jsonFiles.length} Dateien`);
  console.log('');

  let fixedCount = 0;
  for (const file of jsonFiles) {
    if (fixFormDefinitionOrder(file)) {
      fixedCount++;
    }
  }

  console.log('');
  console.log(`✅ Fertig! ${fixedCount} Formulardefinitionen korrigiert.`);
}

// Skript ausführen
main();
