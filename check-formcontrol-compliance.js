const fs = require('fs');
const path = require('path');

// Prüfe ob eine Formulardefinition dem formControl-Konzept entspricht
function checkFormControlCompliance(filePath) {
  try {
    const content = fs.readFileSync(filePath, 'utf8');
    const formDef = JSON.parse(content);
    
    const issues = [];
    
    // 1. Prüfe _comment
    if (!formDef._comment || !formDef._comment.includes('formControl-Konzept')) {
      issues.push('Fehlender oder falscher _comment (sollte "formControl-Konzept" enthalten)');
    }
    
    // 2. Prüfe formTitle
    if (!formDef.formTitle) {
      issues.push('Fehlender formTitle');
    }
    
    // 3. Prüfe relationsTitle
    if (!formDef.relationsTitle) {
      issues.push('Fehlender relationsTitle');
    }
    
    // 4. Prüfe parts Array
    if (!formDef.parts || !Array.isArray(formDef.parts)) {
      issues.push('Fehlendes oder ungültiges parts Array');
      return { compliant: false, issues };
    }
    
    // 5. Prüfe Abschnittsreihenfolge
    const firstSection = formDef.parts[0];
    const lastSection = formDef.parts[formDef.parts.length - 1];
    
    if (!firstSection.title || !firstSection.title.includes('baseContract.general')) {
      issues.push('Erster Abschnitt sollte "jaynaApp.baseContract.general.title" sein');
    }
    
    if (!lastSection.title || !lastSection.title.includes('baseContract.weiteres')) {
      issues.push('Letzter Abschnitt sollte "jaynaApp.baseContract.weiteres.title" sein');
    }
    
    // 6. Prüfe spezifische Abschnitte
    let hasSpecificSection = false;
    for (const section of formDef.parts) {
      if (section.title && section.title.includes('baseContract.')) {
        // BaseContract-Abschnitt - sollte kein i18nSource haben
        if (section.i18nSource) {
          issues.push(`BaseContract-Abschnitt "${section.title}" sollte kein i18nSource haben`);
        }
      } else if (section.title && !section.title.includes('baseContract.')) {
        // Spezifischer Abschnitt - sollte i18nSource haben
        hasSpecificSection = true;
        if (!section.i18nSource) {
          issues.push(`Spezifischer Abschnitt "${section.title}" sollte i18nSource haben`);
        }
      }
    }
    
    // 7. Prüfe relations Array
    if (!formDef.relations || !Array.isArray(formDef.relations)) {
      issues.push('Fehlendes oder ungültiges relations Array');
    }
    
    return {
      compliant: issues.length === 0,
      issues,
      hasSpecificSection
    };
    
  } catch (error) {
    return {
      compliant: false,
      issues: [`Parse-Fehler: ${error.message}`]
    };
  }
}

// Hauptfunktion
function checkAllFormDefinitions() {
  console.log('🔍 Prüfe formControl-Konzept-Konformität aller Formulardefinitionen...\n');
  
  const formDefinitionsDir = './src/main/webapp/app/shared/form-definitions/contract';
  const files = fs.readdirSync(formDefinitionsDir);
  
  let compliantCount = 0;
  let nonCompliantCount = 0;
  let emptyCount = 0;
  
  console.log('📊 Status der Formulardefinitionen:\n');
  
  for (const file of files) {
    if (!file.endsWith('.json')) continue;
    
    const filePath = path.join(formDefinitionsDir, file);
    const fileSize = fs.statSync(filePath).size;
    
    if (fileSize <= 10) {
      console.log(`❌ ${file} (leer)`);
      emptyCount++;
      continue;
    }
    
    const result = checkFormControlCompliance(filePath);
    
    if (result.compliant) {
      console.log(`✅ ${file} (konform)`);
      compliantCount++;
    } else {
      console.log(`❌ ${file} (nicht konform)`);
      if (result.issues.length > 0) {
        result.issues.forEach(issue => {
          console.log(`   - ${issue}`);
        });
      }
      nonCompliantCount++;
    }
  }
  
  console.log(`\n📊 Zusammenfassung:`);
  console.log(`✅ Konform: ${compliantCount}`);
  console.log(`❌ Nicht konform: ${nonCompliantCount}`);
  console.log(`📄 Leer: ${emptyCount}`);
  console.log(`📄 Gesamt: ${compliantCount + nonCompliantCount + emptyCount}`);
}

// Skript ausführen
checkAllFormDefinitions(); 