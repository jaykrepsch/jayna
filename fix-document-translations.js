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

// Funktion um falsche Übersetzungsschlüssel in Dokumenten-Definitionen zu korrigieren
function fixDocumentTranslations(filePath) {
  try {
    const content = fs.readFileSync(filePath, 'utf8');
    let updated = false;

    // Ersetze falsche Übersetzungsschlüssel
    let updatedContent = content
      .replace(/"jaynaApp\.baseContract\.organisation\.title"/g, '"jaynaApp.baseDocument.organisation.title"')
      .replace(/"jaynaApp\.baseContract\.organisation\.placeholder"/g, '"jaynaApp.baseDocument.organisation.placeholder"')
      .replace(/"jaynaApp\.baseContract\.documents\.title"/g, '"jaynaApp.baseDocument.documents.title"')
      .replace(/"jaynaApp\.baseContract\.documents\.placeholder"/g, '"jaynaApp.baseDocument.documents.placeholder"');

    if (updatedContent !== content) {
      fs.writeFileSync(filePath, updatedContent, 'utf8');
      console.log(`✓ Korrigiert: ${filePath}`);
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
  const documentsDir = 'src/main/webapp/app/shared/form-definitions/document';

  if (!fs.existsSync(documentsDir)) {
    console.error(`Verzeichnis ${documentsDir} nicht gefunden!`);
    return;
  }

  console.log('🔍 Suche nach Dokumenten-Formulardefinitionen...');
  const jsonFiles = findDocumentJsonFiles(documentsDir);

  console.log(`📝 Gefunden: ${jsonFiles.length} Dateien`);
  console.log('');

  let fixedCount = 0;
  for (const file of jsonFiles) {
    if (fixDocumentTranslations(file)) {
      fixedCount++;
    }
  }

  console.log('');
  console.log(`✅ Fertig! ${fixedCount} Dokumenten-Definitionen korrigiert.`);
}

// Skript ausführen
main();
