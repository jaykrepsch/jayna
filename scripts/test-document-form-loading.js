const fs = require('fs');
const path = require('path');

const formDefinitionsDir = path.join(__dirname, '../src/main/webapp/app/shared/form-definitions/document');

// Simuliere die loadFormDefinition-Logik aus der Create-Ansicht
function simulateFormLoading(mainCategory, grouping, documentType) {
  console.log(`\n🔍 Teste Form-Loading für: ${mainCategory} > ${grouping} > ${documentType}`);
  
  // Spezielle Behandlung für SEPA-Mandat
  if (documentType === 'SEPA-Mandat' || documentType === 'sepa-mandate') {
    const formName = 'document-finance-insurance-banking-sepa-mandate';
    const formPath = path.join(formDefinitionsDir, `${formName}.json`);
    if (fs.existsSync(formPath)) {
      console.log(`✅ SEPA-Mandat Form gefunden: ${formName}`);
      return true;
    } else {
      console.log(`❌ SEPA-Mandat Form nicht gefunden: ${formName}`);
      return false;
    }
  }
  
  // Build documents form name like document-<main>-<group>-<type>
  const toSlug = s => String(s)
    .toLowerCase()
    .replace(/\s+/g, '-')
    .replace(/[^a-z0-9-]/g, '')
    .replace(/-+/g, '-')
    .replace(/^-|-$|/g, '');
  
  const primaryFormName = `document-${toSlug(mainCategory)}-${toSlug(grouping)}-${toSlug(documentType)}`;
  const primaryFormPath = path.join(formDefinitionsDir, `${primaryFormName}.json`);
  
  if (fs.existsSync(primaryFormPath)) {
    console.log(`✅ Primäre Form gefunden: ${primaryFormName}`);
    return true;
  }
  
  console.log(`❌ Primäre Form nicht gefunden: ${primaryFormName}`);
  
  // Versuche alternative Namenskonventionen
  const alternativeNames = [
    `document-${mainCategory.toLowerCase().replace(/\s+/g, '-')}-${grouping.toLowerCase().replace(/\s+/g, '-')}-${documentType.toLowerCase().replace(/\s+/g, '-')}`,
    `document-${mainCategory.toLowerCase().replace(/\s+/g, '-')}-${documentType.toLowerCase().replace(/\s+/g, '-')}`,
    `document-${documentType.toLowerCase().replace(/\s+/g, '-')}`
  ];
  
  for (const altName of alternativeNames) {
    const altFormPath = path.join(formDefinitionsDir, `${altName}.json`);
    if (fs.existsSync(altFormPath)) {
      console.log(`✅ Alternative Form gefunden: ${altName}`);
      return true;
    }
  }
  
  console.log(`❌ Keine passende Form gefunden für: ${mainCategory} > ${grouping} > ${documentType}`);
  return false;
}

// Test-Fälle basierend auf den verfügbaren Form-Definitionen
const testCases = [
  // Finanzen & Versicherungen
  { main: 'Finanzen & Versicherungen', group: 'Banking', type: 'SEPA-Mandat' },
  { main: 'Finanzen & Versicherungen', group: 'Accounting', type: 'Rechnung' },
  { main: 'Finanzen & Versicherungen', group: 'Accounting', type: 'Quittung' },
  { main: 'Finanzen & Versicherungen', group: 'Versicherungen', type: 'Versicherungspolice' },
  
  // Wohnen & Eigentum
  { main: 'Wohnen & Eigentum', group: 'Mietdokumente', type: 'Mietvertrag' },
  { main: 'Wohnen & Eigentum', group: 'Immobilien', type: 'Kaufvertrag Haus' },
  { main: 'Wohnen & Eigentum', group: 'Fahrzeuge', type: 'Fahrzeugschein' },
  
  // Arbeit & Bildung
  { main: 'Arbeit & Bildung', group: 'Arbeitsdokumente', type: 'Arbeitsvertrag' },
  { main: 'Arbeit & Bildung', group: 'Bewerbung', type: 'Lebenslauf' },
  { main: 'Arbeit & Bildung', group: 'Bildung', type: 'Zeugnis' },
  
  // Gesundheit
  { main: 'Gesundheit', group: 'Medizinische Dokumente', type: 'Rezept' },
  { main: 'Gesundheit', group: 'Medizinische Dokumente', type: 'Laborergebnis' },
  
  // Identität & Persönlicher Status
  { main: 'Identität & Persönlicher Status', group: 'Ausweisdokumente', type: 'Personalausweis' },
  { main: 'Identität & Persönlicher Status', group: 'Zivilstandsurkunden', type: 'Geburtsurkunde' },
  
  // Kommunikation
  { main: 'Kommunikation', group: 'Alltägliche Korrespondenz', type: 'Allgemeine Korrespondenz' },
  { main: 'Kommunikation', group: 'Alltägliche Korrespondenz', type: 'Dankeskarte' },
  
  // Recht & Verwaltung
  { main: 'Recht & Verwaltung', group: 'Verträge & AGB', type: 'Vertragsbedingungen' },
  { main: 'Recht & Verwaltung', group: 'Gerichtsdokumente', type: 'Urteil' },
  
  // Energie & Versorgung
  { main: 'Energie & Versorgung', group: 'Rechnungen', type: 'Stromrechnung' },
  { main: 'Energie & Versorgung', group: 'Rechnungen', type: 'Gasrechnung' }
];

function main() {
  console.log('🧪 Teste Dokument-Form-Definition Loading...\n');
  
  if (!fs.existsSync(formDefinitionsDir)) {
    console.error(`❌ Verzeichnis nicht gefunden: ${formDefinitionsDir}`);
    return;
  }
  
  const files = fs.readdirSync(formDefinitionsDir)
    .filter(file => file.endsWith('.json'))
    .filter(file => !file.includes('baseDocument.json'));
  
  console.log(`📁 Verfügbare Form-Definitionen: ${files.length}`);
  
  let successCount = 0;
  let totalCount = testCases.length;
  
  testCases.forEach((testCase, index) => {
    const success = simulateFormLoading(testCase.main, testCase.group, testCase.type);
    if (success) successCount++;
  });
  
  console.log(`\n📊 Testergebnisse:`);
  console.log(`✅ Erfolgreich: ${successCount}/${totalCount} (${Math.round(successCount/totalCount*100)}%)`);
  console.log(`❌ Fehlgeschlagen: ${totalCount - successCount}/${totalCount}`);
  
  if (successCount === totalCount) {
    console.log('\n🎉 Alle Tests erfolgreich!');
  } else {
    console.log('\n⚠️  Einige Tests fehlgeschlagen. Überprüfe die Form-Definitionen.');
  }
}

if (require.main === module) {
  main();
}

module.exports = { simulateFormLoading, testCases };
