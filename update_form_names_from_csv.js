const fs = require('fs');
const csv = require('csv-parser');

// CSV-Datei lesen und SQL-Updates generieren
const results = [];
const updates = [];

fs.createReadStream('erweiterte_hierarchie_nospecialchars.csv')
  .pipe(csv())
  .on('data', (data) => {
    const oldFormName = data.FormName;
    const newFormName = data.CombinedKey;
    
    if (oldFormName && newFormName && oldFormName !== newFormName) {
      updates.push({
        old: oldFormName,
        new: newFormName
      });
    }
  })
  .on('end', () => {
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
  }); 