const fs = require('fs');
const path = require('path');

// Konfiguration
const config = {
  i18nPath: 'src/main/webapp/i18n/de',
  formDefinitionsPath: 'src/main/webapp/app/shared/form-definitions'
};

// Lade eine JSON-Datei
function loadJsonFile(filePath) {
  try {
    const content = fs.readFileSync(filePath, 'utf8');
    return JSON.parse(content);
  } catch (error) {
    return null;
  }
}

// Speichere eine JSON-Datei
function saveJsonFile(filePath, data) {
  try {
    fs.writeFileSync(filePath, JSON.stringify(data, null, 2), 'utf8');
    return true;
  } catch (error) {
    console.error(`❌ Fehler beim Speichern von ${filePath}:`, error.message);
    return false;
  }
}

// Generiere Übersetzungen für eine Formulardefinition
function generateTranslationsForFormDefinition(formDefPath, entity) {
  const formDef = loadJsonFile(formDefPath);
  if (!formDef) return;
  
  const fileName = path.basename(formDefPath, '.json');
  const i18nFileName = `i18n-${fileName}.json`;
  const i18nPath = path.join(config.i18nPath, entity, i18nFileName);
  
  console.log(`  📝 Generiere Übersetzungen für ${fileName}...`);
  
  // Lade existierende Übersetzungen oder erstelle neue
  let translations = {};
  if (fs.existsSync(i18nPath)) {
    translations = loadJsonFile(i18nPath) || {};
  }
  
  // Generiere Standard-Übersetzungen
  const standardTranslations = {
    [`jaynaApp.${fileName}.createTitle`]: `${fileName} erstellen`,
    [`jaynaApp.${fileName}.updateTitle`]: `${fileName} bearbeiten`,
    [`jaynaApp.${fileName}.viewTitle`]: `${fileName} anzeigen`,
    [`jaynaApp.${fileName}.relations-title`]: "Verknüpfungen"
  };
  
  // Füge Standard-Übersetzungen hinzu, falls sie fehlen
  Object.keys(standardTranslations).forEach(key => {
    if (!translations[key]) {
      translations[key] = standardTranslations[key];
    }
  });
  
  // Generiere Übersetzungen für spezielle Abschnitte
  if (formDef.sections) {
    formDef.sections.forEach((section, index) => {
      if (section.title && !section.title.startsWith('jaynaApp.base')) {
        const sectionKey = section.title.split('.').pop();
        const sectionTitleKey = `jaynaApp.${fileName}.${sectionKey}.title`;
        
        if (!translations[sectionTitleKey]) {
          translations[sectionTitleKey] = sectionKey.charAt(0).toUpperCase() + sectionKey.slice(1);
        }
        
        if (section.fields) {
          section.fields.forEach(field => {
            if (field.label && !field.label.startsWith('jaynaApp.base')) {
              const fieldKey = field.label.split('.').pop();
              const fieldTitleKey = `jaynaApp.${fileName}.${sectionKey}.fields.${fieldKey}`;
              
              if (!translations[fieldTitleKey]) {
                translations[fieldTitleKey] = fieldKey.charAt(0).toUpperCase() + fieldKey.slice(1).replace(/([A-Z])/g, ' $1').trim();
              }
            }
          });
        }
      }
    });
  }
  
  // Speichere die Übersetzungsdatei
  if (saveJsonFile(i18nPath, translations)) {
    console.log(`  ✅ Übersetzungen für ${fileName} generiert/aktualisiert`);
  }
}

// Generiere alle fehlenden Übersetzungen
function generateAllMissingTranslations() {
  console.log('🚀 Generiere fehlende Übersetzungen...\n');
  
  const entities = ['contract', 'contact', 'mobility', 'realestate', 'financeaccount'];
  
  entities.forEach(entity => {
    console.log(`\n🔍 Generiere Übersetzungen für ${entity}...`);
    
    const formDefPath = path.join(config.formDefinitionsPath, entity);
    if (!fs.existsSync(formDefPath)) {
      console.log(`  ⚠️  Pfad nicht gefunden: ${formDefPath}`);
      return;
    }
    
    const formDefFiles = fs.readdirSync(formDefPath).filter(file => file.endsWith('.json'));
    
    formDefFiles.forEach(formDefFile => {
      const formDefPath = path.join(config.formDefinitionsPath, entity, formDefFile);
      generateTranslationsForFormDefinition(formDefPath, entity);
    });
  });
  
  console.log('\n✅ Generierung aller fehlenden Übersetzungen abgeschlossen!');
}

// Führe die Generierung aus
generateAllMissingTranslations(); 