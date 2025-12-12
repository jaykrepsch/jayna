const fs = require('fs');
const path = require('path');

const formDefinitionsDir = path.join(__dirname, '../src/main/webapp/app/shared/form-definitions/document');
const i18nDir = path.join(__dirname, '../src/main/webapp/i18n/de/document');

function verifyFormDefinition(filePath) {
  try {
    const content = fs.readFileSync(filePath, 'utf8');
    const formDef = JSON.parse(content);
    
    console.log(`🔍 Überprüfe Form-Definition: ${path.basename(filePath)}`);
    
    const issues = [];
    
    // Überprüfe alle Felder auf hardcodierte deutsche Texte
    formDef.parts.forEach((part, partIndex) => {
      if (part.fields) {
        part.fields.forEach((field, fieldIndex) => {
          if (field.label && !field.label.startsWith('jaynaApp.')) {
            issues.push({
              type: 'hardcoded_label',
              part: part.title,
              field: field.name,
              value: field.label,
              location: `parts[${partIndex}].fields[${fieldIndex}].label`
            });
          }
        });
      }
    });
    
    // Überprüfe, ob die entsprechende i18n-Datei existiert
    const formName = path.basename(filePath, '.json');
    const i18nFilePath = path.join(i18nDir, `i18n-${formName}.json`);
    
    if (!fs.existsSync(i18nFilePath)) {
      issues.push({
        type: 'missing_i18n_file',
        file: `i18n-${formName}.json`
      });
    } else {
      // Überprüfe, ob alle Übersetzungsschlüssel in der i18n-Datei vorhanden sind
      const i18nContent = fs.readFileSync(i18nFilePath, 'utf8');
      const i18nData = JSON.parse(i18nContent);
      
      formDef.parts.forEach((part, partIndex) => {
        if (part.fields) {
          part.fields.forEach((field, fieldIndex) => {
            if (field.label && field.label.startsWith('jaynaApp.')) {
              const translationKey = field.label;
              if (!i18nData[translationKey]) {
                issues.push({
                  type: 'missing_translation',
                  key: translationKey,
                  location: `parts[${partIndex}].fields[${fieldIndex}].label`
                });
              }
            }
          });
        }
      });
    }
    
    if (issues.length > 0) {
      console.log(`❌ Probleme gefunden in ${path.basename(filePath)}:`);
      issues.forEach(issue => {
        if (issue.type === 'hardcoded_label') {
          console.log(`  - Hardcodierter Text: "${issue.value}" in ${issue.location}`);
        } else if (issue.type === 'missing_i18n_file') {
          console.log(`  - Fehlende i18n-Datei: ${issue.file}`);
        } else if (issue.type === 'missing_translation') {
          console.log(`  - Fehlende Übersetzung: ${issue.key}`);
        }
      });
    } else {
      console.log(`✅ ${path.basename(filePath)} ist korrekt`);
    }
    
    return issues;
    
  } catch (error) {
    console.error(`❌ Fehler beim Überprüfen von ${path.basename(filePath)}:`, error.message);
    return [{ type: 'error', message: error.message }];
  }
}

function main() {
  console.log('🔍 Überprüfe Dokument-Form-Definitionen auf Übersetzungen...\n');
  
  if (!fs.existsSync(formDefinitionsDir)) {
    console.error(`❌ Verzeichnis nicht gefunden: ${formDefinitionsDir}`);
    return;
  }
  
  const files = fs.readdirSync(formDefinitionsDir)
    .filter(file => file.endsWith('.json'))
    .filter(file => !file.includes('baseDocument.json')); // Überspringe baseDocument.json
  
  console.log(`📁 Gefundene Form-Definitionen: ${files.length}\n`);
  
  let totalIssues = 0;
  
  files.forEach(file => {
    const filePath = path.join(formDefinitionsDir, file);
    const issues = verifyFormDefinition(filePath);
    totalIssues += issues.length;
  });
  
  console.log(`\n📊 Zusammenfassung:`);
  console.log(`- Überprüfte Dateien: ${files.length}`);
  console.log(`- Gefundene Probleme: ${totalIssues}`);
  
  if (totalIssues === 0) {
    console.log('\n✅ Alle Form-Definitionen verwenden korrekte Übersetzungsschlüssel!');
  } else {
    console.log('\n⚠️  Bitte beheben Sie die gefundenen Probleme.');
  }
}

if (require.main === module) {
  main();
}

module.exports = { verifyFormDefinition };
