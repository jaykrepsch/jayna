const fs = require('fs');

// CSV-Datei lesen
const csvContent = fs.readFileSync('erweiterte_hierarchie_nospecialchars.csv', 'utf8');
const lines = csvContent.split('\n');

// Header überspringen
const dataLines = lines.slice(1);

const updates = [];

dataLines.forEach((line, index) => {
  if (line.trim()) {
    const columns = line.split(',').map(col => col.replace(/"/g, ''));
    if (columns.length >= 10) {
      const oldFormName = columns[8]; // Spalte 9 (0-basiert = 8)
      const newFormName = columns[9]; // Spalte 10 (0-basiert = 9)
      
      if (oldFormName && newFormName && oldFormName !== newFormName) {
        updates.push({
          old: oldFormName,
          new: newFormName
        });
      }
    }
  }
});

console.log('=== SQL UPDATE STATEMENTS ===');
console.log('-- Update FormNames von alten Namen auf neue Namen');
console.log('');

updates.forEach((update, index) => {
  console.log(`-- Update ${index + 1}: ${update.old} -> ${update.new}`);
  console.log(`UPDATE form_definition SET form_name = '${update.new}' WHERE form_name = '${update.old}';`);
  console.log('');
});

console.log(`-- Insgesamt ${updates.length} Updates generiert`);

// SQL-Datei schreiben
let sqlContent = '-- Update FormNames von alten Namen auf neue Namen\n\n';
updates.forEach((update, index) => {
  sqlContent += `-- Update ${index + 1}: ${update.old} -> ${update.new}\n`;
  sqlContent += `UPDATE form_definition SET form_name = '${update.new}' WHERE form_name = '${update.old}';\n\n`;
});

fs.writeFileSync('update_form_names_generated.sql', sqlContent);
console.log('✅ SQL-Datei erstellt: update_form_names_generated.sql'); 