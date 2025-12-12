const fs = require('fs');
const path = require('path');

const formDefinitionsDir = path.join(__dirname, '../src/main/webapp/app/shared/form-definitions/document');

function fixFormDefinition(filePath) {
  try {
    const content = fs.readFileSync(filePath, 'utf8');
    const formDef = JSON.parse(content);
    
    console.log(`🔧 Korrigiere Form-Definition: ${path.basename(filePath)}`);
    
    let changes = 0;
    
    // Korrigiere alle parts
    formDef.parts.forEach(part => {
      if (part.fields) {
        part.fields.forEach(field => {
          // Korrigiere "title" zu "label" im general-Abschnitt
          if (part.title === "jaynaApp.baseDocument.general.title" && field.name === "title") {
            field.name = "label";
            changes++;
          }
          
          // Stelle sicher, dass description ein text ist
          if (field.name === "description" && field.type === "textarea") {
            field.type = "text";
            changes++;
          }
        });
      }
    });
    
    if (changes > 0) {
      fs.writeFileSync(filePath, JSON.stringify(formDef, null, 2));
      console.log(`✅ ${changes} Änderungen in ${path.basename(filePath)}`);
    } else {
      console.log(`✅ ${path.basename(filePath)} bereits korrekt`);
    }
    
    return changes;
    
  } catch (error) {
    console.error(`❌ Fehler beim Korrigieren von ${path.basename(filePath)}:`, error.message);
    return 0;
  }
}

function main() {
  console.log('🔧 Korrigiere Feldnamen in Dokument-Form-Definitionen...\n');
  
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
    const changes = fixFormDefinition(filePath);
    totalChanges += changes;
  });
  
  console.log(`\n📊 Zusammenfassung:`);
  console.log(`- Bearbeitete Dateien: ${files.length}`);
  console.log(`- Gesamte Änderungen: ${totalChanges}`);
  
  if (totalChanges === 0) {
    console.log('\n✅ Alle Form-Definitionen sind bereits korrekt!');
  } else {
    console.log('\n✅ Alle Form-Definitionen wurden korrigiert!');
  }
}

if (require.main === module) {
  main();
}

module.exports = { fixFormDefinition };
