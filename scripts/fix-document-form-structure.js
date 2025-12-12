const fs = require('fs');
const path = require('path');

const formDefinitionsDir = path.join(__dirname, '../src/main/webapp/app/shared/form-definitions/document');

// Standard-Abschnitte in der gewünschten Reihenfolge
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
  weiteres: {
    title: "jaynaApp.baseDocument.weiteres.title",
    fields: [
      { name: "tags", type: "text", label: "jaynaApp.document.tags" },
      { name: "keywords", type: "textarea", label: "jaynaApp.document.keywords" },
      { name: "isPublic", type: "checkbox", label: "jaynaApp.document.isPublic" },
      { name: "expiryDate", type: "datetime", label: "jaynaApp.document.expiryDate" }
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
  }
};

function fixFormDefinition(filePath) {
  try {
    const content = fs.readFileSync(filePath, 'utf8');
    const formDef = JSON.parse(content);
    
    console.log(`🔧 Korrigiere Form-Definition: ${path.basename(filePath)}`);
    
    const newParts = [];
    const seenTitles = new Set();
    
    // 1. Allgemeiner Abschnitt (General + DocumentData)
    newParts.push(standardSections.general);
    seenTitles.add(standardSections.general.title);
    
    newParts.push(standardSections.documentData);
    seenTitles.add(standardSections.documentData.title);
    
    // 2. Sammle alle spezifischen Abschnitte (die nicht zu den Standard-Abschnitten gehören)
    const specificParts = [];
    const specificFields = [];
    
    formDef.parts.forEach(part => {
      const standardTitles = Object.values(standardSections).map(section => section.title);
      
      if (!standardTitles.includes(part.title) && !seenTitles.has(part.title)) {
        // Prüfe, ob es sich um einen spezifischen Abschnitt handelt
        if (part.title.includes('specificData') || 
            part.title.includes('creditorDetails') || 
            part.title.includes('debtorDetails') ||
            part.title.includes('bankingDetails') ||
            part.title.includes('legalDetails') ||
            part.title.includes('amountDetails') ||
            part.title.includes('invoice') ||
            part.title.includes('contract') ||
            part.title.includes('medical') ||
            part.title.includes('vehicle') ||
            part.title.includes('real-estate') ||
            part.title.includes('education') ||
            part.title.includes('work') ||
            part.title.includes('identity') ||
            part.title.includes('communication') ||
            part.title.includes('law') ||
            part.title.includes('energy') ||
            part.title.includes('health')) {
          
          specificParts.push(part);
          seenTitles.add(part.title);
        }
      }
    });
    
    // Sammle spezifische Felder aus dem ursprünglichen general-Abschnitt
    if (formDef.parts.length > 0) {
      const originalGeneral = formDef.parts[0];
      if (originalGeneral.fields) {
        originalGeneral.fields.forEach(field => {
          // Felder, die nicht zu den Standard-General-Feldern gehören
          if (!['title', 'description', 'entityState'].includes(field.name)) {
            specificFields.push(field);
          }
        });
      }
    }
    
    // Füge spezifische Felder zu den spezifischen Abschnitten hinzu
    if (specificFields.length > 0) {
      const formName = path.basename(filePath, '.json');
      specificParts.unshift({
        title: `jaynaApp.${formName}.specificData.title`,
        i18nSource: formName,
        fields: specificFields
      });
    }
    
    newParts.push(...specificParts);
    
    // 3. Weiteres-Abschnitt
    newParts.push(standardSections.weiteres);
    seenTitles.add(standardSections.weiteres.title);
    
    // 4. Dateiinformationen-Abschnitt (nur einmal)
    newParts.push(standardSections.fileInfo);
    seenTitles.add(standardSections.fileInfo.title);
    
    // Aktualisiere die Form-Definition
    formDef.parts = newParts;
    
    // Stelle sicher, dass relationsTitle gesetzt ist
    if (!formDef.relationsTitle) {
      formDef.relationsTitle = 'jaynaApp.baseDocument.relations.title';
    }
    
    // Schreibe die korrigierte Form-Definition zurück
    fs.writeFileSync(filePath, JSON.stringify(formDef, null, 2));
    console.log(`✅ Form-Definition korrigiert: ${path.basename(filePath)}`);
    
  } catch (error) {
    console.error(`❌ Fehler beim Korrigieren von ${path.basename(filePath)}:`, error.message);
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
  
  console.log('\n✅ Alle Form-Definitionen korrigiert!');
  console.log('\n📋 Finale Struktur für alle Dokument-Form-Definitionen:');
  console.log('1. Allgemeiner Abschnitt (General + DocumentData)');
  console.log('2. Spezifische Abschnitte (je nach Dokumententyp)');
  console.log('3. Weiteres (Tags, Keywords, etc.)');
  console.log('4. Dateiinformationen (Dateiname, Größe, etc.)');
  console.log('5. Verknüpfungen (wird dynamisch hinzugefügt)');
}

if (require.main === module) {
  main();
}

module.exports = { fixFormDefinition, standardSections };
