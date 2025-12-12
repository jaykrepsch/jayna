const fs = require('fs');
const path = require('path');

// Konfiguration
const FORM_DEFINITIONS_PATH = './src/main/webapp/app/shared/form-definitions';
const OUTPUT_PATH = './scripts/form-mapping-report.json';

// Entity-Verzeichnisse
const ENTITIES = ['contract', 'contact', 'realestate', 'mobility', 'financeaccount', 'document'];

// Scannt alle Form-Definitionen für eine Entity
function scanFormDefinitions(entityName) {
  const entityPath = path.join(FORM_DEFINITIONS_PATH, entityName);
  
  if (!fs.existsSync(entityPath)) {
    console.log(`⚠️  Verzeichnis nicht gefunden: ${entityPath}`);
    return [];
  }

  const files = fs.readdirSync(entityPath);
  const formDefinitions = [];

  files.forEach(file => {
    if (file.endsWith('.json') && !file.startsWith('base')) {
      const formName = file.replace('.json', '');
      formDefinitions.push(formName);
    }
  });

  return formDefinitions.sort();
}

// Analysiert die Struktur einer Form-Definition
function analyzeFormStructure(entityName, formName) {
  const filePath = path.join(FORM_DEFINITIONS_PATH, entityName, `${formName}.json`);
  
  if (!fs.existsSync(filePath)) {
    return null;
  }

  try {
    const content = fs.readFileSync(filePath, 'utf8');
    const formDefinition = JSON.parse(content);
    
    return {
      formName,
      filePath: `@/shared/form-definitions/${entityName}/${formName}.json`,
      parts: formDefinition.parts?.length || 0,
      hasRelations: !!formDefinition.relations,
      relationsCount: formDefinition.relations?.length || 0,
      formTitle: formDefinition.formTitle,
      comment: formDefinition._comment
    };
  } catch (error) {
    console.error(`❌ Fehler beim Analysieren von ${filePath}:`, error.message);
    return null;
  }
}

// Generiert eine Mapping-Tabelle basierend auf Namenskonventionen
function generateMappingTable(entityName, formDefinitions) {
  const mapping = {};
  
  formDefinitions.forEach(formName => {
    const parts = formName.split('-');
    
    if (parts.length >= 4) {
      const [, subCategory, subCategoryGroup, groupType] = parts;
      
      // Erstelle verschiedene Mapping-Schlüssel
      const keys = [
        groupType,
        `${subCategoryGroup}-${groupType}`,
        `${subCategory}-${subCategoryGroup}-${groupType}`,
        formName
      ];
      
      keys.forEach(key => {
        if (!mapping[key]) {
          mapping[key] = [];
        }
        if (!mapping[key].includes(formName)) {
          mapping[key].push(formName);
        }
      });
    }
  });
  
  return mapping;
}

// Hauptfunktion
function generateFormMappingReport() {
  console.log('🔍 Generiere Form-Definition Mapping-Report...\n');
  
  const report = {
    generatedAt: new Date().toISOString(),
    summary: {},
    entities: {},
    globalMapping: {}
  };

  let totalForms = 0;
  let totalValidForms = 0;

  ENTITIES.forEach(entityName => {
    console.log(`📁 Scanne ${entityName}...`);
    
    const formDefinitions = scanFormDefinitions(entityName);
    const entityReport = {
      formDefinitions: [],
      mapping: {},
      statistics: {
        total: formDefinitions.length,
        valid: 0,
        invalid: 0
      }
    };

    // Analysiere jede Form-Definition
    formDefinitions.forEach(formName => {
      const analysis = analyzeFormStructure(entityName, formName);
      
      if (analysis) {
        entityReport.formDefinitions.push(analysis);
        entityReport.statistics.valid++;
        totalValidForms++;
      } else {
        entityReport.statistics.invalid++;
      }
    });

    // Generiere Mapping-Tabelle
    entityReport.mapping = generateMappingTable(entityName, formDefinitions);
    
    // Füge zur globalen Mapping-Tabelle hinzu
    Object.keys(entityReport.mapping).forEach(key => {
      if (!report.globalMapping[key]) {
        report.globalMapping[key] = {};
      }
      report.globalMapping[key][entityName] = entityReport.mapping[key];
    });

    report.entities[entityName] = entityReport;
    totalForms += formDefinitions.length;
    
    console.log(`   ✅ ${formDefinitions.length} Form-Definitionen gefunden`);
    console.log(`   📊 ${entityReport.statistics.valid} gültig, ${entityReport.statistics.invalid} ungültig`);
  });

  // Zusammenfassung
  report.summary = {
    totalEntities: ENTITIES.length,
    totalFormDefinitions: totalForms,
    totalValidFormDefinitions: totalValidForms,
    totalInvalidFormDefinitions: totalForms - totalValidForms,
    entitiesWithForms: Object.keys(report.entities).filter(entity => 
      report.entities[entity].statistics.total > 0
    ).length
  };

  // Speichere Report
  fs.writeFileSync(OUTPUT_PATH, JSON.stringify(report, null, 2));
  
  console.log('\n📊 Zusammenfassung:');
  console.log(`   📁 Entities: ${report.summary.totalEntities}`);
  console.log(`   📄 Form-Definitionen: ${report.summary.totalFormDefinitions}`);
  console.log(`   ✅ Gültig: ${report.summary.totalValidFormDefinitions}`);
  console.log(`   ❌ Ungültig: ${report.summary.totalInvalidFormDefinitions}`);
  console.log(`   📂 Entities mit Formularen: ${report.summary.entitiesWithForms}`);
  console.log(`\n💾 Report gespeichert: ${OUTPUT_PATH}`);

  // Zeige Top-Mappings
  console.log('\n🔝 Top-Mappings:');
  const topMappings = Object.entries(report.globalMapping)
    .filter(([key, mappings]) => Object.values(mappings).some(forms => forms.length > 0))
    .sort((a, b) => {
      const aCount = Object.values(a[1]).reduce((sum, forms) => sum + forms.length, 0);
      const bCount = Object.values(b[1]).reduce((sum, forms) => sum + forms.length, 0);
      return bCount - aCount;
    })
    .slice(0, 10);

  topMappings.forEach(([key, mappings]) => {
    const totalCount = Object.values(mappings).reduce((sum, forms) => sum + forms.length, 0);
    console.log(`   ${key}: ${totalCount} Formulare`);
  });

  return report;
}

// Führe das Skript aus
if (require.main === module) {
  try {
    generateFormMappingReport();
  } catch (error) {
    console.error('❌ Fehler beim Generieren des Reports:', error);
    process.exit(1);
  }
}

module.exports = {
  generateFormMappingReport,
  scanFormDefinitions,
  analyzeFormStructure,
  generateMappingTable
};
