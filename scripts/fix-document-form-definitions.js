const fs = require('fs');
const path = require('path');

const formDefinitionsDir = path.join(__dirname, '../src/main/webapp/app/shared/form-definitions/document');

// Standard-Abschnitte aus baseDocument.json
const standardSections = {
  general: {
    title: "jaynaApp.baseDocument.general.title",
    fields: [
      { name: "title", type: "text", required: true, label: "jaynaApp.document.title" },
      { name: "description", type: "textarea", label: "jaynaApp.document.description" },
      { name: "entityState", type: "dropdown", boxLabel: "global.menu.choose", enumName: "entityState", defaultValue: "ACTIVE" }
    ]
  },
  documentData: {
    title: "jaynaApp.baseDocument.documentData.title",
    fields: [
      { name: "documentNumber", type: "text", label: "jaynaApp.document.documentNumber" },
      { name: "customerNumber", type: "text", label: "jaynaApp.document.customerNumber" },
      { name: "partner", type: "text", label: "jaynaApp.document.partner" },
      { name: "contractNumber", type: "text", label: "jaynaApp.document.contractNumber" },
      { name: "realEstateNumber", type: "text", label: "jaynaApp.document.realEstateNumber" },
      { name: "financeAccountNumber", type: "text", label: "jaynaApp.document.financeAccountNumber" },
      { name: "mobilityNumber", type: "text", label: "jaynaApp.document.mobilityNumber" },
      { name: "status", type: "dropdown", boxLabel: "global.menu.choose", enumName: "documentStatus", defaultValue: "DRAFT" }
    ]
  },
  fileInfo: {
    title: "jaynaApp.baseDocument.fileInfo.title",
    fields: [
      { name: "fileName", type: "text", label: "jaynaApp.document.fileName", readonly: true },
      { name: "fileType", type: "text", label: "jaynaApp.document.fileType", readonly: true },
      { name: "fileSize", type: "number", label: "jaynaApp.document.fileSize", readonly: true },
      { name: "uploadDate", type: "datetime", label: "jaynaApp.document.uploadDate", readonly: true },
      { name: "lastModified", type: "datetime", label: "jaynaApp.document.lastModified", readonly: true }
    ]
  },
  weiteres: {
    title: "jaynaApp.baseDocument.weiteres.title",
    fields: [
      { name: "tags", type: "text", label: "jaynaApp.document.tags" },
      { name: "keywords", type: "textarea", label: "jaynaApp.document.keywords" },
      { name: "isPublic", type: "checkbox", label: "jaynaApp.document.isPublic" },
      { name: "expiryDate", type: "datetime", label: "jaynaApp.document.expiryDate" }
    ]
  }
};

function fixFormDefinition(filePath) {
  try {
    const content = fs.readFileSync(filePath, 'utf8');
    const formDef = JSON.parse(content);
    
    // Überspringe spezielle Form-Definitionen wie SEPA-Mandat
    if (filePath.includes('sepa-mandate') || filePath.includes('document-basic.json') || filePath.includes('document.json')) {
      console.log(`Überspringe spezielle Form-Definition: ${path.basename(filePath)}`);
      return;
    }
    
    console.log(`Prüfe Form-Definition: ${path.basename(filePath)}`);
    
    let needsUpdate = false;
    const newParts = [];
    
    // Füge Standard-Abschnitte hinzu, falls sie fehlen
    const existingTitles = formDef.parts.map(part => part.title);
    
    // General-Abschnitt
    if (!existingTitles.includes(standardSections.general.title)) {
      newParts.push(standardSections.general);
      needsUpdate = true;
    } else {
      newParts.push(formDef.parts.find(part => part.title === standardSections.general.title));
    }
    
    // DocumentData-Abschnitt
    if (!existingTitles.includes(standardSections.documentData.title)) {
      newParts.push(standardSections.documentData);
      needsUpdate = true;
    } else {
      newParts.push(formDef.parts.find(part => part.title === standardSections.documentData.title));
    }
    
    // Spezifische Abschnitte beibehalten
    const specificParts = formDef.parts.filter(part => 
      !Object.values(standardSections).some(standard => standard.title === part.title)
    );
    newParts.push(...specificParts);
    
    // Weiteres-Abschnitt am Ende
    if (!existingTitles.includes(standardSections.weiteres.title)) {
      newParts.push(standardSections.weiteres);
      needsUpdate = true;
    } else {
      newParts.push(formDef.parts.find(part => part.title === standardSections.weiteres.title));
    }
    
    // Korrigiere Feldnamen von 'label' zu 'title' im general-Abschnitt
    const generalPart = newParts.find(part => part.title === standardSections.general.title);
    if (generalPart) {
      const titleField = generalPart.fields.find(field => field.name === 'label');
      if (titleField) {
        titleField.name = 'title';
        titleField.label = 'jaynaApp.document.title';
        needsUpdate = true;
      }
    }
    
    if (needsUpdate) {
      formDef.parts = newParts;
      
      // Stelle sicher, dass relationsTitle gesetzt ist
      if (!formDef.relationsTitle) {
        formDef.relationsTitle = 'jaynaApp.baseDocument.relations.title';
      }
      
      // Schreibe die korrigierte Form-Definition zurück
      fs.writeFileSync(filePath, JSON.stringify(formDef, null, 2));
      console.log(`✅ Form-Definition korrigiert: ${path.basename(filePath)}`);
    } else {
      console.log(`✅ Form-Definition bereits korrekt: ${path.basename(filePath)}`);
    }
    
  } catch (error) {
    console.error(`❌ Fehler beim Verarbeiten von ${path.basename(filePath)}:`, error.message);
  }
}

function main() {
  console.log('🔧 Korrigiere Dokument-Form-Definitionen...\n');
  
  if (!fs.existsSync(formDefinitionsDir)) {
    console.error(`❌ Verzeichnis nicht gefunden: ${formDefinitionsDir}`);
    return;
  }
  
  const files = fs.readdirSync(formDefinitionsDir)
    .filter(file => file.endsWith('.json'))
    .filter(file => !file.includes('baseDocument.json')); // Überspringe baseDocument.json
  
  console.log(`📁 Gefundene Form-Definitionen: ${files.length}\n`);
  
  files.forEach(file => {
    const filePath = path.join(formDefinitionsDir, file);
    fixFormDefinition(filePath);
  });
  
  console.log('\n✅ Alle Form-Definitionen überprüft und korrigiert!');
}

if (require.main === module) {
  main();
}

module.exports = { fixFormDefinition, standardSections };
