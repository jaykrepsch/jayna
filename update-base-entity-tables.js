const fs = require('fs');
const path = require('path');

// Funktion um alle baseEntity.json Dateien zu finden
function findBaseEntityFiles(dir) {
  const files = [];

  function traverse(currentDir) {
    const items = fs.readdirSync(currentDir);

    for (const item of items) {
      const fullPath = path.join(currentDir, item);
      const stat = fs.statSync(fullPath);

      if (stat.isDirectory()) {
        traverse(fullPath);
      } else if (item.includes('base') && item.endsWith('.json') && !item.includes('global')) {
        files.push(fullPath);
      }
    }
  }

  traverse(dir);
  return files;
}

// Funktion um baseEntity.json Dateien zu aktualisieren
function updateBaseEntityFile(filePath) {
  try {
    const content = fs.readFileSync(filePath, 'utf8');

    // Bestimme Entity-Typ aus dem Pfad
    let entityType = 'Contract'; // Default
    if (filePath.includes('/contact/')) entityType = 'Contact';
    if (filePath.includes('/mobility/')) entityType = 'Mobility';
    if (filePath.includes('/realestate/')) entityType = 'Realestate';
    if (filePath.includes('/financeaccount/')) entityType = 'Financeaccount';
    if (filePath.includes('/document/')) entityType = 'Document';

    let updatedContent = content;

    // Organisation-Abschnitte hinzufügen
    if (!updatedContent.includes(`jaynaApp.base${entityType}.organisation.table.category`)) {
      const organisationSection = `,
  "jaynaApp.base${entityType}.organisation.table.category": "Typ",
  "jaynaApp.base${entityType}.organisation.table.title": "Titel",
  "jaynaApp.base${entityType}.organisation.table.date": "Datum",
  "jaynaApp.base${entityType}.organisation.table.status": "Status",
  "jaynaApp.base${entityType}.organisation.table.actions": "Aktionen",
  "jaynaApp.base${entityType}.organisation.table.empty": "Keine Kalender-Einträge, Aufgaben oder Notizen vorhanden"`;

      updatedContent = updatedContent.replace(
        `"jaynaApp.base${entityType}.organisation.placeholder": "Hier werden später Kalender, Aufgaben und Notizen angezeigt"`,
        `"jaynaApp.base${entityType}.organisation.placeholder": "Hier werden später Kalender, Aufgaben und Notizen angezeigt"${organisationSection}`
      );
    }

    // Dokumente-Abschnitte hinzufügen
    if (!updatedContent.includes(`jaynaApp.base${entityType}.documents.table.category`)) {
      const documentsSection = `,
  "jaynaApp.base${entityType}.documents.table.category": "Kategorie",
  "jaynaApp.base${entityType}.documents.table.title": "Titel",
  "jaynaApp.base${entityType}.documents.table.date": "Datum",
  "jaynaApp.base${entityType}.documents.table.type": "Typ",
  "jaynaApp.base${entityType}.documents.table.actions": "Aktionen",
  "jaynaApp.base${entityType}.documents.table.empty": "Keine verknüpften Dokumente vorhanden"`;

      updatedContent = updatedContent.replace(
        `"jaynaApp.base${entityType}.documents.placeholder": "Hier werden später die verknüpften Dokumente angezeigt"`,
        `"jaynaApp.base${entityType}.documents.placeholder": "Hier werden später die verknüpften Dokumente angezeigt"${documentsSection}`
      );
    }

    if (updatedContent !== content) {
      fs.writeFileSync(filePath, updatedContent, 'utf8');
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
  const i18nDir = 'src/main/webapp/i18n/de';

  if (!fs.existsSync(i18nDir)) {
    console.error(`Verzeichnis ${i18nDir} nicht gefunden!`);
    return;
  }

  console.log('🔍 Suche nach baseEntity.json Dateien...');
  const jsonFiles = findBaseEntityFiles(i18nDir);

  console.log(`📝 Gefunden: ${jsonFiles.length} Dateien`);
  console.log('');

  let updatedCount = 0;
  for (const file of jsonFiles) {
    if (updateBaseEntityFile(file)) {
      updatedCount++;
    }
  }

  console.log('');
  console.log(`✅ Fertig! ${updatedCount} baseEntity.json Dateien aktualisiert.`);
}

// Skript ausführen
main();
