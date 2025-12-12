const { Pool } = require('pg');

const pool = new Pool({
  host: 'localhost',
  port: 5432,
  database: 'jayna',
  user: 'jayna',
  password: 'jayna'
});

async function checkBafoeg() {
  try {
    console.log('=== BAFÖG ÜBERPRÜFUNG ===\n');
    
    // Suche nach BAföG-Einträgen
    const result = await pool.query(`
      SELECT id, name, form_name, translation_key 
      FROM group_type 
      WHERE name LIKE '%Bafög%' 
         OR name LIKE '%BAföG%' 
         OR translation_key LIKE '%student-loans%'
    `);
    
    console.log('Gefundene BAföG-Einträge:');
    result.rows.forEach(row => {
      console.log(`  ID: ${row.id}, Name: ${row.name}, form_name: ${row.form_name}, translation_key: ${row.translation_key}`);
    });
    
    // Überprüfe alle Darlehen-Einträge
    console.log('\n=== ALLE DARLEHEN-EINTRÄGE ===');
    const loanResult = await pool.query(`
      SELECT gt.id, gt.name, gt.form_name, gt.translation_key,
             sc.name as subcategory_name
      FROM group_type gt
      JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id
      JOIN sub_category sc ON scg.sub_category_id = sc.id
      WHERE sc.name = 'Darlehen'
      ORDER BY gt.name
    `);
    
    console.log('Alle Darlehen-Einträge:');
    loanResult.rows.forEach(row => {
      console.log(`  ID: ${row.id}, Name: ${row.name}, form_name: ${row.form_name}, Subcategory: ${row.subcategory_name}`);
    });
    
  } catch (err) {
    console.error('Fehler:', err);
  } finally {
    await pool.end();
  }
}

checkBafoeg(); 