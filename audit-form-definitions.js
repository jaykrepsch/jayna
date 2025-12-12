const fs = require('fs');
const path = require('path');

// Referenz-Standards (Feuerversicherung und Aufhebungsvertrag)
const REFERENCE_STRUCTURE = {
  requiredSections: ['general', 'contractData', 'payment', 'weiteres'],
  requiredFields: {
    general: ['label', 'description', 'entityState'],
    contractData: ['contractor', 'contractOwner', 'contractUser', 'contractNumber', 'startDate', 'endDate'],
    payment: ['payer', 'paymentPattern', 'paymentType', 'amount'],
    weiteres: ['comment', 'notes']
  },
  fieldTypes: {
    label: 'text',
    description: 'text',
    entityState: 'dropdown',
    contractor: 'text',
    contractOwner: 'text',
    contractUser: 'text',
    contractNumber: 'text',
    startDate: 'date',
    endDate: 'date',
    payer: 'text',
    paymentPattern: 'dropdown',
    paymentType: 'dropdown',
    amount: 'number',
    comment: 'textarea',
    notes: 'textarea'
  },
  requiredRelations: [
    { entityName: 'contact', relationName: 'contactContracts' },
    { entityName: 'realestate', relationName: 'contractRealestates' },
    { entityName: 'financeaccount', relationName: 'contractFinanceAccounts' },
    { entityName: 'mobility', relationName: 'contractMobilities' },
    { entityName: 'contract', relationName: 'contractContracts' }
  ]
};

// Hilfsfunktion: Entity-Name aus Dateiname extrahieren
function extractEntityName(fileName) {
  const baseName = fileName.replace('.json', '');
  // Konvertiere kebab-case zu camelCase
  return baseName.replace(/-([a-z])/g, (match, letter) => letter.toUpperCase());
}

// Hilfsfunktion: Übersetzungsdatei laden
function loadTranslationFile(entityName) {
  const translationPath = `./src/main/webapp/i18n/de/contract/${entityName}.json`;
  try {
    if (fs.existsSync(translationPath)) {
      const content = fs.readFileSync(translationPath, 'utf8');
      return JSON.parse(content);
    }
  } catch (error) {
    console.log(`⚠️  Übersetzungsdatei nicht gefunden oder ungültig: ${translationPath}`);
  }
  return null;
}

// Audit-Funktionen
function auditFormDefinition(filePath) {
  const fileName = path.basename(filePath);
  const fileSize = fs.statSync(filePath).size;
  const entityName = extractEntityName(fileName);
  
  console.log(`\n🔍 Auditing: ${fileName} (${fileSize} bytes)`);
  
  // Schnellprüfung: Dateigröße
  if (fileSize <= 10) {
    console.log(`❌ Datei ist zu klein (${fileSize} bytes) - wahrscheinlich leer`);
    return { fileName, status: 'EMPTY', issues: ['Datei ist leer oder zu klein'] };
  }
  
  try {
    const content = fs.readFileSync(filePath, 'utf8');
    const formDef = JSON.parse(content);
    
    const issues = [];
    
    // 1. Grundstruktur prüfen
    if (!formDef._comment) issues.push('Fehlender _comment');
    if (!formDef.formTitle) issues.push('Fehlender formTitle');
    if (!formDef.parts || !Array.isArray(formDef.parts)) issues.push('Fehlendes oder ungültiges parts Array');
    if (!formDef.relations || !Array.isArray(formDef.relations)) issues.push('Fehlendes oder ungültiges relations Array');
    
    // 2. Abschnittsreihenfolge prüfen
    if (formDef.parts && formDef.parts.length > 0) {
      const firstSection = formDef.parts[0];
      if (!firstSection.title || !firstSection.title.includes('general')) {
        issues.push('Erster Abschnitt sollte "general" sein');
      }
      
      const lastSection = formDef.parts[formDef.parts.length - 1];
      if (!lastSection.title || !lastSection.title.includes('weiteres')) {
        issues.push('Letzter Abschnitt sollte "weiteres" sein');
      }
    }
    
    // 3. Standardfelder prüfen
    if (formDef.parts) {
      const generalSection = formDef.parts.find(p => p.title && p.title.includes('general'));
      if (generalSection) {
        const hasLabel = generalSection.fields && generalSection.fields.some(f => f.name === 'label' && f.rules === 'required');
        if (!hasLabel) issues.push('General-Abschnitt sollte ein required "label" Feld haben');
        
        const hasEntityState = generalSection.fields && generalSection.fields.some(f => f.name === 'entityState' && f.type === 'dropdown');
        if (!hasEntityState) issues.push('General-Abschnitt sollte ein entityState Dropdown haben');
      }
    }
    
    // 4. Relations prüfen
    if (formDef.relations) {
      const requiredRelationNames = REFERENCE_STRUCTURE.requiredRelations.map(r => r.relationName);
      const missingRelations = requiredRelationNames.filter(name => 
        !formDef.relations.some(r => r.relationName === name)
      );
      if (missingRelations.length > 0) {
        issues.push(`Fehlende Relations: ${missingRelations.join(', ')}`);
      }
    }
    
    // 5. Datumsfelder prüfen
    if (formDef.parts) {
      formDef.parts.forEach(section => {
        if (section.fields) {
          section.fields.forEach(field => {
            if (field.type === 'date') {
              if (field.enableTimePicker !== false) issues.push(`Datumfeld ${field.name}: enableTimePicker sollte false sein`);
              if (field.autoApply !== true) issues.push(`Datumfeld ${field.name}: autoApply sollte true sein`);
              if (field.textInput !== true) issues.push(`Datumfeld ${field.name}: textInput sollte true sein`);
              if (field.format !== 'dd.MM.yyyy') issues.push(`Datumfeld ${field.name}: format sollte 'dd.MM.yyyy' sein`);
            }
          });
        }
      });
    }
    
    // 6. Dropdown-Konfiguration prüfen
    if (formDef.parts) {
      formDef.parts.forEach(section => {
        if (section.fields) {
          section.fields.forEach(field => {
            if (field.type === 'dropdown') {
              if (!field.boxLabel || field.boxLabel !== 'global.menu.choose') {
                issues.push(`Dropdown ${field.name}: boxLabel sollte 'global.menu.choose' sein`);
              }
              if (!field.enumName) {
                issues.push(`Dropdown ${field.name}: enumName fehlt`);
              }
            }
          });
        }
      });
    }
    
    // 7. Übersetzungen prüfen (NEU)
    const translations = loadTranslationFile(entityName);
    if (translations) {
      // 7.1 Verknüpfungsüberschriften prüfen
      if (!translations[`jaynaApp.${entityName}.relations-title`]) {
        issues.push('Fehlende Verknüpfungsüberschrift: relations-title');
      }
      
      // 7.2 Seitenüberschriften prüfen
      if (!translations[`jaynaApp.${entityName}.create.title`]) {
        issues.push('Fehlende Erstellungsüberschrift: create.title');
      }
      if (!translations[`jaynaApp.${entityName}.update.title`]) {
        issues.push('Fehlende Bearbeitungsüberschrift: update.title');
      }
      if (!translations[`jaynaApp.${entityName}.title`]) {
        issues.push('Fehlende Übersichtsüberschrift: title');
      }
      
      // 7.3 Abschnittsüberschriften prüfen
      if (formDef.parts) {
        formDef.parts.forEach(section => {
          if (section.title) {
            const sectionKey = section.title.replace('jaynaApp.' + entityName + '.', '').replace('.title', '');
            if (!translations[`jaynaApp.${entityName}.${sectionKey}.title`]) {
              issues.push(`Fehlende Abschnittsüberschrift: ${sectionKey}.title`);
            }
          }
        });
      }
      
      // 7.4 Feldübersetzungen prüfen
      if (formDef.parts) {
        formDef.parts.forEach(section => {
          if (section.title && section.fields) {
            const sectionKey = section.title.replace('jaynaApp.' + entityName + '.', '').replace('.title', '');
            section.fields.forEach(field => {
              if (!translations[`jaynaApp.${entityName}.${sectionKey}.fields.${field.name}`]) {
                issues.push(`Fehlende Feldübersetzung: ${sectionKey}.fields.${field.name}`);
              }
            });
          }
        });
      }
    } else {
      issues.push('Übersetzungsdatei nicht gefunden oder ungültig');
    }
    
    const status = issues.length === 0 ? 'PASS' : 'FAIL';
    console.log(`Status: ${status === 'PASS' ? '✅' : '❌'} ${status}`);
    
    if (issues.length > 0) {
      console.log('Probleme:');
      issues.forEach(issue => console.log(`  - ${issue}`));
    }
    
    return { fileName, status, issues, formDef, entityName };
    
  } catch (error) {
    console.log(`❌ JSON Parse Error: ${error.message}`);
    return { fileName, status: 'ERROR', issues: [`JSON Parse Error: ${error.message}`] };
  }
}

// Hauptfunktion
function runAudit() {
  const formDefinitionsDir = './src/main/webapp/app/shared/form-definitions/contract';
  
  console.log('🚀 Form-Definition Audit gestartet');
  console.log('=' .repeat(50));
  
  if (!fs.existsSync(formDefinitionsDir)) {
    console.log(`❌ Verzeichnis nicht gefunden: ${formDefinitionsDir}`);
    return;
  }
  
  const files = fs.readdirSync(formDefinitionsDir)
    .filter(file => file.endsWith('.json'))
    .sort();
  
  console.log(`📁 Gefundene Dateien: ${files.length}`);
  
  const results = [];
  
  files.forEach(file => {
    const filePath = path.join(formDefinitionsDir, file);
    const result = auditFormDefinition(filePath);
    results.push(result);
  });
  
  // Zusammenfassung
  console.log('\n' + '=' .repeat(50));
  console.log('📊 AUDIT ZUSAMMENFASSUNG');
  console.log('=' .repeat(50));
  
  const passed = results.filter(r => r.status === 'PASS').length;
  const failed = results.filter(r => r.status === 'FAIL').length;
  const errors = results.filter(r => r.status === 'ERROR').length;
  const empty = results.filter(r => r.status === 'EMPTY').length;
  
  console.log(`✅ Bestanden: ${passed}`);
  console.log(`❌ Fehler: ${failed}`);
  console.log(`⚠️  Parse-Fehler: ${errors}`);
  console.log(`📄 Leer: ${empty}`);
  
  if (failed > 0) {
    console.log('\n❌ Dateien mit Problemen:');
    results.filter(r => r.status === 'FAIL').forEach(result => {
      console.log(`  - ${result.fileName} (${result.issues.length} Probleme)`);
    });
  }
  
  if (empty > 0) {
    console.log('\n📄 Leere Dateien:');
    results.filter(r => r.status === 'EMPTY').forEach(result => {
      console.log(`  - ${result.fileName}`);
    });
  }
}

// Skript ausführen
if (require.main === module) {
  runAudit();
}

module.exports = { auditFormDefinition, runAudit }; 