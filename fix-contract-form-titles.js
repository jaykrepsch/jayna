const fs = require('fs');
const path = require('path');

// Funktion zum Konvertieren von CamelCase zu kebab-case
function toKebabCase(str) {
  return str
    .replace(/([a-z])([A-Z])/g, '$1-$2')
    .toLowerCase()
    .replace(/\s+/g, '-')
    .replace(/[^a-z0-9-]/g, '');
}

// Funktion zum Korrigieren der formTitle
function fixFormTitle(filePath) {
  try {
    const content = fs.readFileSync(filePath, 'utf8');
    const data = JSON.parse(content);
    
    // Extrahiere den Dateinamen ohne Erweiterung
    const fileName = path.basename(filePath, '.json');
    
    // Erstelle den korrekten formTitle
    const correctFormTitle = `jaynaApp.${fileName}.createTitle`;
    
    // Prüfe, ob die formTitle korrigiert werden muss
    if (data.formTitle && data.formTitle.startsWith('Contract ')) {
      console.log(`Korrigiere ${filePath}:`);
      console.log(`  Alt: ${data.formTitle}`);
      console.log(`  Neu: ${correctFormTitle}`);
      
      data.formTitle = correctFormTitle;
      
      // Schreibe die Datei zurück
      fs.writeFileSync(filePath, JSON.stringify(data, null, 2));
      return true;
    }
    
    return false;
  } catch (error) {
    console.error(`Fehler beim Bearbeiten von ${filePath}:`, error.message);
    return false;
  }
}

// Funktion zum Korrigieren der i18nSource
function fixI18nSource(filePath) {
  try {
    const content = fs.readFileSync(filePath, 'utf8');
    const data = JSON.parse(content);
    
    // Extrahiere den Dateinamen ohne Erweiterung
    const fileName = path.basename(filePath, '.json');
    
    let changed = false;
    
    // Durchsuche alle parts nach i18nSource
    if (data.parts) {
      data.parts.forEach(part => {
        if (part.i18nSource && part.i18nSource.includes('Contract ')) {
          const oldI18nSource = part.i18nSource;
          part.i18nSource = fileName;
          
          console.log(`  Korrigiere i18nSource: ${oldI18nSource} -> ${part.i18nSource}`);
          changed = true;
        }
      });
    }
    
    if (changed) {
      fs.writeFileSync(filePath, JSON.stringify(data, null, 2));
      return true;
    }
    
    return false;
  } catch (error) {
    console.error(`Fehler beim Bearbeiten von ${filePath}:`, error.message);
    return false;
  }
}

// Hauptfunktion
function fixContractFormDefinitions() {
  const contractDir = 'src/main/webapp/app/shared/form-definitions/contract';
  
  if (!fs.existsSync(contractDir)) {
    console.error(`Verzeichnis ${contractDir} existiert nicht!`);
    return;
  }
  
  const files = fs.readdirSync(contractDir).filter(file => file.endsWith('.json'));
  
  console.log(`Gefundene Contract Form-Definitionen: ${files.length}`);
  
  let fixedCount = 0;
  
  files.forEach(file => {
    const filePath = path.join(contractDir, file);
    
    const formTitleFixed = fixFormTitle(filePath);
    const i18nSourceFixed = fixI18nSource(filePath);
    
    if (formTitleFixed || i18nSourceFixed) {
      fixedCount++;
    }
  });
  
  console.log(`\nInsgesamt ${fixedCount} Dateien korrigiert.`);
}

// Führe das Script aus
fixContractFormDefinitions();
