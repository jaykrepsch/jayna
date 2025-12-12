const fs = require('fs');
const path = require('path');

const formDefinitionsDir = path.join(__dirname, '../src/main/webapp/app/shared/form-definitions/document');

function cleanupFormDefinition(filePath) {
  try {
    const content = fs.readFileSync(filePath, 'utf8');
    const formDef = JSON.parse(content);
    
    console.log(`🧹 Bereinige Form-Definition: ${path.basename(filePath)}`);
    
    let changes = 0;
    
    // Entferne label-Felder aus allen Feldern
    formDef.parts.forEach(part => {
      if (part.fields) {
        part.fields.forEach(field => {
          if (field.label) {
            delete field.label;
            changes++;
          }
        });
      }
    });
    
    // Korrigiere das Feld "label" zu "title" im general-Abschnitt
    formDef.parts.forEach(part => {
      if (part.title === "jaynaApp.baseDocument.general.title" && part.fields) {
        part.fields.forEach(field => {
          if (field.name === "label") {
            field.name = "title";
            changes++;
          }
          if (field.name === "description" && field.type === "text") {
            field.type = "textarea";
            changes++;
          }
        });
      }
    });
    
    if (changes > 0) {
      // Schreibe die bereinigte Form-Definition zurück
      fs.writeFileSync(filePath, JSON.stringify(formDef, null, 2));
      console.log(`✅ ${changes} Änderungen in ${path.basename(filePath)}`);
    } else {
      console.log(`✅ ${path.basename(filePath)} bereits bereinigt`);
    }
    
    return changes;
    
  } catch (error) {
    console.error(`❌ Fehler beim Bereinigen von ${path.basename(filePath)}:`, error.message);
    return 0;
  }
}

function main() {
  console.log('🧹 Bereinige Dokument-Form-Definitionen...\n');
  
  if (!fs.existsSync(formDefinitionsDir)) {
    console.error(`❌ Verzeichnis nicht gefunden: ${formDefinitionsDir}`);
    return;
  }
  
  const files = fs.readdirSync(formDefinitionsDir)
    .filter(file => file.endsWith('.json'))
    .filter(file => !file.includes('baseDocument.json')); // Überspringe baseDocument.json
  
  console.log(`📁 Gefundene Form-Definitionen: ${files.length}\n`);
  
  let totalChanges = 0;
  
  files.forEach(file => {
    const filePath = path.join(formDefinitionsDir, file);
    const changes = cleanupFormDefinition(filePath);
    totalChanges += changes;
  });
  
  console.log(`\n📊 Zusammenfassung:`);
  console.log(`- Bereinigte Dateien: ${files.length}`);
  console.log(`- Gesamte Änderungen: ${totalChanges}`);
  
  if (totalChanges === 0) {
    console.log('\n✅ Alle Form-Definitionen sind bereits bereinigt!');
  } else {
    console.log('\n✅ Alle Form-Definitionen wurden bereinigt!');
  }
  
  console.log('\n📋 Übersetzungsstruktur:');
  console.log('- Allgemeine Felder: Übersetzungen in messages_de.properties');
  console.log('- Spezifische Felder: Übersetzungen in i18n-{formName}.json Dateien');
  console.log('- Abschnitt-Titel: Übersetzungen in messages_de.properties (baseDocument)');
}

if (require.main === module) {
  main();
}

module.exports = { cleanupFormDefinition };
