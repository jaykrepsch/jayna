const fs = require('fs');
const path = require('path');

// Funktion um alle JSON-Dateien im Dokumenten-Verzeichnis rekursiv zu finden
function findDocumentJsonFiles(dir) {
  const files = [];

  function traverse(currentDir) {
    const items = fs.readdirSync(currentDir);

    for (const item of items) {
      const fullPath = path.join(currentDir, item);
      const stat = fs.statSync(fullPath);

      if (stat.isDirectory()) {
        traverse(fullPath);
      } else if (item.endsWith('.json') && !item.includes('base')) {
        files.push(fullPath);
      }
    }
  }

  traverse(dir);
  return files;
}

// Funktion um eine Dokumenten-Formulardefinition zu aktualisieren
function updateDocumentDefinition(filePath) {
  try {
    const content = fs.readFileSync(filePath, 'utf8');
    const formDef = JSON.parse(content);

    // Prüfe ob bereits aktualisiert
    if (formDef.parts && formDef.parts.some(part =>
      part.title && part.title.includes('organisation')
    )) {
      console.log(`✓ Bereits aktualisiert: ${filePath}`);
      return;
    }

    // Finde die Position von "fileInfo" Abschnitt (Dateien)
    let fileInfoIndex = -1;
    if (formDef.parts) {
      fileInfoIndex = formDef.parts.findIndex(part =>
        part.title && part.title.includes('fileInfo')
      );
    }

    if (fileInfoIndex === -1) {
      console.log(`⚠️ Kein fileInfo Abschnitt gefunden in: ${filePath}`);
      return;
    }

    // Neue Abschnitte erstellen
    const organisationSection = {
      "title": "jaynaApp.baseDocument.organisation.title",
      "fields": [
        {
          "name": "organisationPlaceholder",
          "type": "info",
          "text-key": "jaynaApp.baseDocument.organisation.placeholder"
        }
      ]
    };

    const documentsSection = {
      "title": "jaynaApp.baseDocument.documents.title",
      "fields": [
        {
          "name": "documentsPlaceholder",
          "type": "info",
          "text-key": "jaynaApp.baseDocument.documents.placeholder"
        }
      ]
    };

    // Abschnitte vor dem fileInfo Abschnitt einfügen
    formDef.parts.splice(fileInfoIndex, 0, organisationSection);
    formDef.parts.splice(fileInfoIndex + 1, 0, documentsSection);

    // JSON-Datei speichern
    fs.writeFileSync(filePath, JSON.stringify(formDef, null, 2), 'utf8');
    console.log(`✓ Aktualisiert: ${filePath}`);

  } catch (error) {
    console.error(`✗ Fehler bei ${filePath}:`, error.message);
  }
}

// Hauptfunktion
function main() {
  const documentsDir = 'src/main/webapp/app/shared/form-definitions/document';

  if (!fs.existsSync(documentsDir)) {
    console.error(`Verzeichnis ${documentsDir} nicht gefunden!`);
    return;
  }

  console.log('🔍 Suche nach Dokumenten-Formulardefinitionen...');
  const jsonFiles = findDocumentJsonFiles(documentsDir);

  console.log(`📝 Gefunden: ${jsonFiles.length} Dateien`);
  console.log('');

  let updatedCount = 0;
  for (const file of jsonFiles) {
    updateDocumentDefinition(file);
    updatedCount++;
  }

  console.log('');
  console.log(`✅ Fertig! ${updatedCount} Dokumenten-Definitionen aktualisiert.`);
}

// Skript ausführen
main();
