const fs = require('fs');
const path = require('path');

// Funktion zum Entfernen von baseContract-Übersetzungen
function removeBaseContractTranslations(translations) {
  const filtered = {};
  
  Object.keys(translations).forEach(key => {
    // Behalte nur spezifische Übersetzungen, nicht baseContract
    if (!key.startsWith('jaynaApp.baseContract.')) {
      filtered[key] = translations[key];
    }
  });
  
  return filtered;
}

// Funktion zum Formatieren der JSON-Datei mit Leerzeilen
function formatI18nJson(translations) {
  const lines = [];
  
  // Gruppiere Übersetzungen nach Präfix
  const groups = {};
  Object.keys(translations).forEach(key => {
    const prefix = key.split('.').slice(0, 3).join('.');
    if (!groups[prefix]) groups[prefix] = {};
    groups[prefix][key] = translations[key];
  });
  
  // Erstelle formatierte Ausgabe
  lines.push('{');
  
  const groupKeys = Object.keys(groups).sort();
  groupKeys.forEach((groupKey, groupIndex) => {
    const group = groups[groupKey];
    const groupLines = Object.keys(group).sort().map((key, keyIndex) => {
      const isLastInGroup = keyIndex === Object.keys(group).length - 1;
      const isLastGroup = groupIndex === groupKeys.length - 1;
      const comma = (!isLastInGroup || !isLastGroup) ? ',' : '';
      return `  "${key}": "${group[key]}"${comma}`;
    });
    
    if (groupIndex > 0) lines.push('');
    lines.push(...groupLines);
  });
  
  lines.push('}');
  
  return lines.join('\n');
}

// Hauptfunktion
function fixI18nDuplicates() {
  console.log('🔧 Entferne redundante baseContract-Übersetzungen...\n');
  
  const contractDir = './src/main/webapp/i18n/de/contract';
  let totalFixed = 0;
  
  if (!fs.existsSync(contractDir)) {
    console.log('❌ Contract-Verzeichnis nicht gefunden');
    return;
  }
  
  const i18nFiles = fs.readdirSync(contractDir).filter(file => 
    file.startsWith('i18n-') && file.endsWith('.json')
  );
  
  i18nFiles.forEach(fileName => {
    const filePath = path.join(contractDir, fileName);
    
    try {
      // Lese aktuelle Datei
      const content = fs.readFileSync(filePath, 'utf8');
      const translations = JSON.parse(content);
      
      // Entferne baseContract-Übersetzungen
      const cleanedTranslations = removeBaseContractTranslations(translations);
      
      // Formatiere und speichere
      const formattedContent = formatI18nJson(cleanedTranslations);
      fs.writeFileSync(filePath, formattedContent, 'utf8');
      
      console.log(`✅ ${fileName} (bereinigt)`);
      totalFixed++;
      
    } catch (error) {
      console.log(`❌ Fehler bei ${fileName}: ${error.message}`);
    }
  });
  
  console.log(`\n🎉 ${totalFixed} i18n-Dateien bereinigt!`);
  console.log('📝 baseContract-Übersetzungen sind jetzt nur noch in baseContract.json definiert');
}

// Skript ausführen
fixI18nDuplicates(); 