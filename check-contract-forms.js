const fs = require('fs');
const path = require('path');

// Pfade zu den Verzeichnissen
const formDefsPath = 'src/main/webapp/app/shared/form-definitions/contract';
const i18nPath = 'src/main/webapp/i18n/de/contract';

// Funktion zum Überprüfen und Korrigieren einer Form-Definition
function checkFormDefinition(filePath) {
  try {
    const content = fs.readFileSync(filePath, 'utf8');
    const data = JSON.parse(content);
    const fileName = path.basename(filePath, '.json');
    
    let needsUpdate = false;
    let updates = [];
    
    // Überprüfe formTitle
    if (data.formTitle && !data.formTitle.startsWith('jaynaApp.')) {
      const correctFormTitle = `jaynaApp.${fileName}.createTitle`;
      updates.push(`formTitle: "${data.formTitle}" -> "${correctFormTitle}"`);
      data.formTitle = correctFormTitle;
      needsUpdate = true;
    }
    
    // Überprüfe i18nSource in parts
    if (data.parts) {
      data.parts.forEach((part, index) => {
        if (part.i18nSource && part.i18nSource !== fileName) {
          updates.push(`parts[${index}].i18nSource: "${part.i18nSource}" -> "${fileName}"`);
          part.i18nSource = fileName;
          needsUpdate = true;
        }
        
        // Überprüfe title in parts
        if (part.title && part.title.includes('specificData.title') && !part.title.includes(fileName)) {
          const correctTitle = `jaynaApp.${fileName}.specificData.title`;
          updates.push(`parts[${index}].title: "${part.title}" -> "${correctTitle}"`);
          part.title = correctTitle;
          needsUpdate = true;
        }
      });
    }
    
    if (needsUpdate) {
      console.log(`\n📝 ${fileName}.json:`);
      updates.forEach(update => console.log(`  - ${update}`));
      
      // Schreibe die korrigierte Datei
      fs.writeFileSync(filePath, JSON.stringify(data, null, 2) + '\n');
      return true;
    }
    
    return false;
  } catch (error) {
    console.error(`❌ Fehler beim Überprüfen von ${filePath}:`, error.message);
    return false;
  }
}

// Funktion zum Überprüfen einer i18n-Datei
function checkI18nFile(filePath) {
  try {
    const content = fs.readFileSync(filePath, 'utf8');
    const data = JSON.parse(content);
    const fileName = path.basename(filePath, '.json');
    
    let needsUpdate = false;
    let updates = [];
    
    // Überprüfe alle Keys
    Object.keys(data).forEach(key => {
      if (key.includes('terminationcontract') && fileName.includes('terminationagreement')) {
        const newKey = key.replace('terminationcontract', 'terminationagreement');
        const newValue = data[key];
        updates.push(`Key: "${key}" -> "${newKey}"`);
        delete data[key];
        data[newKey] = newValue;
        needsUpdate = true;
      }
    });
    
    if (needsUpdate) {
      console.log(`\n🌐 ${fileName}.json:`);
      updates.forEach(update => console.log(`  - ${update}`));
      
      // Schreibe die korrigierte Datei
      fs.writeFileSync(filePath, JSON.stringify(data, null, 2) + '\n');
      return true;
    }
    
    return false;
  } catch (error) {
    console.error(`❌ Fehler beim Überprüfen von ${filePath}:`, error.message);
    return false;
  }
}

// Hauptfunktion
function main() {
  console.log('🔍 Überprüfe alle Contract Form-Definitionen und i18n-Dateien...\n');
  
  let formDefsUpdated = 0;
  let i18nFilesUpdated = 0;
  
  // Überprüfe Form-Definitionen
  if (fs.existsSync(formDefsPath)) {
    const formDefFiles = fs.readdirSync(formDefsPath).filter(file => file.endsWith('.json'));
    
    formDefFiles.forEach(file => {
      const filePath = path.join(formDefsPath, file);
      if (checkFormDefinition(filePath)) {
        formDefsUpdated++;
      }
    });
  }
  
  // Überprüfe i18n-Dateien
  if (fs.existsSync(i18nPath)) {
    const i18nFiles = fs.readdirSync(i18nPath).filter(file => file.startsWith('i18n-') && file.endsWith('.json'));
    
    i18nFiles.forEach(file => {
      const filePath = path.join(i18nPath, file);
      if (checkI18nFile(filePath)) {
        i18nFilesUpdated++;
      }
    });
  }
  
  console.log(`\n✅ Überprüfung abgeschlossen!`);
  console.log(`📝 ${formDefsUpdated} Form-Definitionen aktualisiert`);
  console.log(`🌐 ${i18nFilesUpdated} i18n-Dateien aktualisiert`);
}

// Führe das Skript aus
main();
