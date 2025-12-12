const fs = require('fs');
const path = require('path');

// CSV-Daten parsen
function parseCSV() {
  try {
    const csvContent = fs.readFileSync('formulardefinitionen_und_auswahlmoeglichkeiten.csv', 'utf8');
    const lines = csvContent.split('\n');
    
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
    
    console.log(`📄 ${mappings.length} Einträge aus CSV gelesen`);
    return mappings;
  } catch (error) {
    console.error(`❌ Fehler beim Lesen der CSV-Datei: ${error.message}`);
    return [];
  }
}

// Hauptfunktion
function checkFormDefinitionNames() {
  console.log('🔍 Prüfe Namensstruktur der Formulardefinitionen...\n');
  console.log('📋 Korrekte Namensstruktur: contract-{subCategory}-{subCategoryGroup}-{groupType}.json\n');
  
  const mappings = parseCSV();
  if (mappings.length === 0) {
    console.log('❌ Keine Daten gefunden, beende Prüfung.');
    return;
  }
  
  const formDefinitionsDir = './src/main/webapp/app/shared/form-definitions/contract';
  
  // Prüfe ob Verzeichnis existiert
  if (!fs.existsSync(formDefinitionsDir)) {
    console.error(`❌ Verzeichnis nicht gefunden: ${formDefinitionsDir}`);
    return;
  }
  
  // Alle Dateien im Verzeichnis auflisten
  const files = fs.readdirSync(formDefinitionsDir);
  console.log(`📁 ${files.length} Dateien im Verzeichnis gefunden\n`);
  
  let correctCount = 0;
  let incorrectCount = 0;
  let missingCount = 0;
  
  console.log('📊 Status der Formulardefinitionen:\n');
  
  for (const mapping of mappings) {
    if (mapping.formDefExists !== 'ok') continue;
    
    const expectedFileName = mapping.dateiname;
    if (!expectedFileName || expectedFileName === '-') continue;
    
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
          fileWithoutExt === `contract-${mapping.subCategory}-${mapping.groupType}` ||
          fileWithoutExt === `contract-${mapping.subCategoryGroup}-${mapping.groupType}`) {
        currentFile = file;
        break;
      }
    }
    
    if (currentFile) {
      if (currentFile === expectedFileName) {
        console.log(`✅ ${currentFile} (korrekt)`);
        correctCount++;
      } else {
        console.log(`❌ ${currentFile} → ${expectedFileName} (falsch benannt)`);
        incorrectCount++;
      }
    } else {
      console.log(`❌ ${expectedFileName} (fehlt)`);
      missingCount++;
    }
  }
  
  console.log(`\n📊 Zusammenfassung:`);
  console.log(`✅ Korrekt benannt: ${correctCount}`);
  console.log(`❌ Falsch benannt: ${incorrectCount}`);
  console.log(`❌ Fehlend: ${missingCount}`);
  console.log(`📄 Gesamt: ${correctCount + incorrectCount + missingCount}`);
  
  // Zeige Beispiele für korrekte Namensstruktur
  console.log(`\n📝 Beispiele für korrekte Namensstruktur:`);
  console.log(`   contract-employment-employee-fulltime.json`);
  console.log(`   contract-service-broker-realestateagent.json`);
  console.log(`   contract-insurance-fire-firecontract.json`);
  console.log(`   contract-savings-savings-savingscontract.json`);
}

// Skript ausführen
checkFormDefinitionNames(); 