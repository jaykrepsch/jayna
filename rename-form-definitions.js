const fs = require('fs');
const path = require('path');

// CSV-Daten parsen
function parseCSV() {
  const csvContent = fs.readFileSync('formulardefinitionen_und_auswahlmoeglichkeiten.csv', 'utf8');
  const lines = csvContent.split('\n');
  const headers = lines[0].split(';');
  
  const mappings = [];
  
  for (let i = 1; i < lines.length; i++) {
    const line = lines[i].trim();
    if (!line) continue;
    
    const values = line.split(';');
    if (values.length < 10) continue;
    
    const mapping = {
      nr: values[0],
      category: values[1],
      art: values[2],
      subCategory: values[3],
      gruppierung: values[4],
      subCategoryGroup: values[5],
      sparte: values[6],
      groupType: values[7],
      formDefExists: values[8],
      dateiname: values[9],
      uebersetzung: values[10],
      letzteAenderung: values[11]
    };
    
    mappings.push(mapping);
  }
  
  return mappings;
}

// Datei umbenennen
function renameFile(oldPath, newPath) {
  try {
    if (fs.existsSync(oldPath) && !fs.existsSync(newPath)) {
      fs.renameSync(oldPath, newPath);
      console.log(`✅ ${path.basename(oldPath)} → ${path.basename(newPath)}`);
      return true;
    } else if (fs.existsSync(oldPath) && fs.existsSync(newPath)) {
      console.log(`⚠️  ${path.basename(oldPath)} → ${path.basename(newPath)} (Zieldatei existiert bereits)`);
      return false;
    } else if (!fs.existsSync(oldPath)) {
      //console.log(`❌ ${path.basename(oldPath)} (Quelldatei existiert nicht)`);
      return false;
    }
  } catch (error) {
    console.log(`❌ Fehler beim Umbenennen von ${path.basename(oldPath)}: ${error.message}`);
    return false;
  }
}

// Hauptfunktion
function renameFormDefinitions() {
  console.log('🚀 Formulardefinitionen für alle Entitäten umbenennen...\n');
  
  const mappings = parseCSV();
  const baseDir = './src/main/webapp/app/shared/form-definitions/';
  const entityDirs = fs.readdirSync(baseDir).filter(f => fs.statSync(path.join(baseDir, f)).isDirectory());

  let successCount = 0;
  let errorCount = 0;
  let skipCount = 0;

  for (const entity of entityDirs) {
    const entityDir = path.join(baseDir, entity);
    const files = fs.readdirSync(entityDir);
    
    for (const mapping of mappings) {
      if (mapping.formDefExists !== 'ok') continue;
      const expectedFileName = mapping.dateiname;
      if (!expectedFileName || expectedFileName === '-') continue;
      // Prüfe, ob die Datei zu dieser Entität gehört
      if (!expectedFileName.startsWith(entity)) continue;

      // Suche nach der aktuellen Datei
      let currentFile = null;
      for (const file of files) {
        // Entferne .json Erweiterung für Vergleich
        const fileWithoutExt = file.replace('.json', '');
        const expectedWithoutExt = expectedFileName.replace('.json', '');
        // Verschiedene mögliche Namensvarianten prüfen
        if (fileWithoutExt === expectedWithoutExt ||
            fileWithoutExt === mapping.groupType ||
            fileWithoutExt === `${mapping.subCategory}-${mapping.groupType}` ||
            fileWithoutExt === `${mapping.subCategoryGroup}-${mapping.groupType}` ||
            fileWithoutExt === `${entity}-${mapping.subCategory}-${mapping.groupType}` ||
            fileWithoutExt === `${entity}-${mapping.subCategoryGroup}-${mapping.groupType}`) {
          currentFile = file;
          break;
        }
      }
      if (currentFile) {
        const oldPath = path.join(entityDir, currentFile);
        const newPath = path.join(entityDir, expectedFileName);
        if (currentFile !== expectedFileName) {
          const success = renameFile(oldPath, newPath);
          if (success) {
            successCount++;
          } else {
            errorCount++;
          }
        } else {
          //console.log(`✅ ${currentFile} (bereits korrekt benannt)`);
          skipCount++;
        }
      } else {
        //console.log(`❌ Keine Datei gefunden für: ${mapping.groupType} (erwartet: ${expectedFileName})`);
        errorCount++;
      }
    }
  }
  
  console.log(`\n📊 Zusammenfassung:`);
  console.log(`✅ Erfolgreich umbenannt: ${successCount}`);
  console.log(`⚠️  Übersprungen (bereits korrekt): ${skipCount}`);
  console.log(`❌ Fehler: ${errorCount}`);
}

// Skript ausführen
renameFormDefinitions(); 