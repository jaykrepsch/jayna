const fs = require('fs');
const path = require('path');

const formDefinitionsDir = path.join(__dirname, '../src/main/webapp/app/shared/form-definitions/document');
const i18nDir = path.join(__dirname, '../src/main/webapp/i18n/de/document');
const messagesDePath = path.join(__dirname, '../src/main/resources/i18n/messages_de.properties');

function loadPropertiesFile(filePath) {
  const content = fs.readFileSync(filePath, 'utf8');
  const translations = {};
  
  content.split('\n').forEach(line => {
    line = line.trim();
    if (line && !line.startsWith('#') && line.includes('=')) {
      const [key, value] = line.split('=', 2);
      translations[key.trim()] = value.trim();
    }
  });
  
  return translations;
}

function checkFormDefinition(filePath, messagesDe, i18nFiles) {
  try {
    const content = fs.readFileSync(filePath, 'utf8');
    const formDef = JSON.parse(content);
    
    console.log(`🔍 Überprüfe Form-Definition: ${path.basename(filePath)}`);
    
    const issues = [];
    
    // Überprüfe alle Abschnitt-Titel
    formDef.parts.forEach((part, partIndex) => {
      if (part.title && !messagesDe[part.title]) {
        issues.push({
          type: 'missing_section_title',
          title: part.title,
          location: `parts[${partIndex}].title`
        });
      }
    });
    
    // Überprüfe alle Feld-Namen (sollten in messages_de.properties sein)
    formDef.parts.forEach((part, partIndex) => {
      if (part.fields) {
        part.fields.forEach((field, fieldIndex) => {
          const translationKey = `jaynaApp.document.${field.name}`;
          if (!messagesDe[translationKey]) {
            issues.push({
              type: 'missing_field_translation',
              field: field.name,
              key: translationKey,
              location: `parts[${partIndex}].fields[${fieldIndex}].name`
            });
          }
        });
      }
    });
    
    // Überprüfe spezifische Übersetzungen in i18n-Dateien
    const formName = path.basename(filePath, '.json');
    const i18nFilePath = path.join(i18nDir, `i18n-${formName}.json`);
    
    if (fs.existsSync(i18nFilePath)) {
      const i18nContent = fs.readFileSync(i18nFilePath, 'utf8');
      const i18nData = JSON.parse(i18nContent);
      
      // Überprüfe, ob alle spezifischen Abschnitt-Titel übersetzt sind
      formDef.parts.forEach((part, partIndex) => {
        if (part.title && part.title.includes(formName) && !i18nData[part.title]) {
          issues.push({
            type: 'missing_specific_section_title',
            title: part.title,
            location: `parts[${partIndex}].title`
          });
        }
      });
    }
    
    if (issues.length > 0) {
      console.log(`❌ Probleme gefunden in ${path.basename(filePath)}:`);
      issues.forEach(issue => {
        if (issue.type === 'missing_section_title') {
          console.log(`  - Fehlender Abschnitt-Titel: ${issue.title}`);
        } else if (issue.type === 'missing_field_translation') {
          console.log(`  - Fehlende Feld-Übersetzung: ${issue.key} für Feld "${issue.field}"`);
        } else if (issue.type === 'missing_specific_section_title') {
          console.log(`  - Fehlender spezifischer Abschnitt-Titel: ${issue.title}`);
        }
      });
    } else {
      console.log(`✅ ${path.basename(filePath)} ist korrekt übersetzt`);
    }
    
    return issues;
    
  } catch (error) {
    console.error(`❌ Fehler beim Überprüfen von ${path.basename(filePath)}:`, error.message);
    return [{ type: 'error', message: error.message }];
  }
}

function main() {
  console.log('🔍 Überprüfe Übersetzungen für Dokument-Form-Definitionen...\n');
  
  if (!fs.existsSync(formDefinitionsDir)) {
    console.error(`❌ Verzeichnis nicht gefunden: ${formDefinitionsDir}`);
    return;
  }
  
  // Lade Übersetzungen
  const messagesDe = loadPropertiesFile(messagesDePath);
  console.log(`📚 Geladene Übersetzungen aus messages_de.properties: ${Object.keys(messagesDe).length}`);
  
  // Lade verfügbare i18n-Dateien
  const i18nFiles = {};
  if (fs.existsSync(i18nDir)) {
    const files = fs.readdirSync(i18nDir).filter(file => file.endsWith('.json'));
    files.forEach(file => {
      const formName = file.replace('i18n-', '').replace('.json', '');
      i18nFiles[formName] = file;
    });
  }
  console.log(`📚 Verfügbare i18n-Dateien: ${Object.keys(i18nFiles).length}\n`);
  
  const files = fs.readdirSync(formDefinitionsDir)
    .filter(file => file.endsWith('.json'))
    .filter(file => !file.includes('baseDocument.json')); // Überspringe baseDocument.json
  
  console.log(`📁 Gefundene Form-Definitionen: ${files.length}\n`);
  
  let totalIssues = 0;
  
  files.forEach(file => {
    const filePath = path.join(formDefinitionsDir, file);
    const issues = checkFormDefinition(filePath, messagesDe, i18nFiles);
    totalIssues += issues.length;
  });
  
  console.log(`\n📊 Zusammenfassung:`);
  console.log(`- Überprüfte Dateien: ${files.length}`);
  console.log(`- Gefundene Probleme: ${totalIssues}`);
  
  if (totalIssues === 0) {
    console.log('\n✅ Alle Form-Definitionen sind korrekt übersetzt!');
  } else {
    console.log('\n⚠️  Bitte beheben Sie die gefundenen Übersetzungsprobleme.');
  }
}

if (require.main === module) {
  main();
}

module.exports = { checkFormDefinition, loadPropertiesFile };
