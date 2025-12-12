const fs = require('fs');
const path = require('path');

const entities = ['contract', 'contact', 'realestate', 'mobility', 'financeaccount'];

function checkConsistency() {
  let errors = [];
  let warnings = [];
  let okCount = 0;

  entities.forEach(entityType => {
    const formDefDir = `./src/main/webapp/app/shared/form-definitions/${entityType}`;
    const i18nDir = `./src/main/webapp/i18n/de/${entityType}`;
    if (!fs.existsSync(formDefDir)) return;
    if (!fs.existsSync(i18nDir)) {
      warnings.push(`⚠️  Kein i18n-Verzeichnis für ${entityType}`);
      return;
    }

    const formDefFiles = fs.readdirSync(formDefDir).filter(f => f.endsWith('.json'));
    const i18nFiles = fs.readdirSync(i18nDir).filter(f => f.endsWith('.json'));
    const i18nFileSet = new Set(i18nFiles);

    formDefFiles.forEach(formDefFile => {
      const formName = path.basename(formDefFile, '.json');
      const i18nFileName = `i18n-${formName}.json`;
      const i18nPath = path.join(i18nDir, i18nFileName);
      const formDefPath = path.join(formDefDir, formDefFile);

      if (!fs.existsSync(i18nPath)) {
        errors.push(`❌ Keine i18n-Datei für ${entityType}/${formDefFile}`);
        return;
      }

      // Plausibilitätsprüfung: Felder und Abschnitte
      try {
        const formDef = JSON.parse(fs.readFileSync(formDefPath, 'utf8'));
        const i18n = JSON.parse(fs.readFileSync(i18nPath, 'utf8'));
        // Prüfe Titel
        if (!i18n[`jaynaApp.${formName}.createTitle`]) {
          warnings.push(`⚠️  Kein createTitle in ${i18nFileName}`);
        }
        // Prüfe spezifische Abschnitte
        formDef.parts.forEach(part => {
          if (part.i18nSource === formName) {
            const sectionKey = `jaynaApp.${formName}.${part.title.split('.').pop().replace('.title','')}.title`;
            if (!Object.keys(i18n).some(k => k.includes(sectionKey))) {
              warnings.push(`⚠️  Fehlender Abschnittstitel ${sectionKey} in ${i18nFileName}`);
            }
            part.fields.forEach(field => {
              const fieldKey = `jaynaApp.${formName}.${part.title.split('.').pop().replace('.title','')}.fields.${field.name}`;
              if (!Object.keys(i18n).some(k => k.includes(fieldKey))) {
                warnings.push(`⚠️  Fehlende Feldübersetzung ${fieldKey} in ${i18nFileName}`);
              }
            });
          }
        });
        // Prüfe relationsTitle
        if (formDef.relationsTitle && !i18n[formDef.relationsTitle]) {
          warnings.push(`⚠️  Fehlende relationsTitle-Übersetzung (${formDef.relationsTitle}) in ${i18nFileName}`);
        }
        okCount++;
      } catch (e) {
        errors.push(`❌ Fehler beim Parsen von ${formDefFile} oder zugehöriger i18n: ${e.message}`);
      }
    });

    // Überflüssige i18n-Dateien?
    formDefFiles.forEach(formDefFile => {
      const formName = path.basename(formDefFile, '.json');
      const i18nFileName = `i18n-${formName}.json`;
      i18nFileSet.delete(i18nFileName);
    });
    i18nFileSet.forEach(rest => {
      warnings.push(`⚠️  Überflüssige i18n-Datei: ${entityType}/${rest}`);
    });
  });

  // Zusammenfassung
  if (errors.length === 0 && warnings.length === 0) {
    console.log('✅ Alle Formulardefinitionen und i18n-Dateien sind plausibel und konsistent!');
    console.log(`Geprüft: ${okCount} Formulare.`);
  } else {
    if (errors.length > 0) {
      console.log('Fehler:');
      errors.forEach(e => console.log(e));
    }
    if (warnings.length > 0) {
      console.log('\nWarnungen:');
      warnings.forEach(w => console.log(w));
    }
    console.log(`\nGeprüft: ${okCount} Formulare.`);
  }
}

checkConsistency(); 