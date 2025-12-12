// Skript zum Ausführen des SQL-Fixes über die H2 Console
const puppeteer = require('puppeteer');

async function fixFormName() {
    const browser = await puppeteer.launch({ headless: false });
    const page = await browser.newPage();
    
    // H2 Console öffnen
    await page.goto('http://localhost:8080/h2-console/');
    
    // Verbindungsdaten eingeben
    await page.waitForSelector('#driverClassName');
    await page.select('#driverClassName', 'org.h2.Driver');
    await page.type('#url', 'jdbc:h2:mem:testdb');
    await page.type('#userName', 'sa');
    await page.type('#password', '');
    
    // Verbinden
    await page.click('input[value="Connect"]');
    
    // Warten bis die SQL-Konsole geladen ist
    await page.waitForSelector('#sql');
    
    // SQL-Befehle ausführen
    const sqlCommands = [
        "SELECT id, name, form_name, entity_name FROM group_type WHERE name = 'Geländewagen' AND entity_name = 'mobility';",
        "UPDATE group_type SET form_name = 'mobility-passengervehicle-car-offroadvehicle' WHERE name = 'Geländewagen' AND entity_name = 'mobility';",
        "SELECT id, name, form_name, entity_name FROM group_type WHERE name = 'Geländewagen' AND entity_name = 'mobility';"
    ];
    
    for (const sql of sqlCommands) {
        await page.type('#sql', sql);
        await page.click('input[value="Run"]');
        await page.waitForTimeout(1000);
        await page.click('#sql');
        await page.keyboard.down('Control');
        await page.keyboard.press('A');
        await page.keyboard.up('Control');
        await page.keyboard.press('Delete');
    }
    
    console.log('SQL-Fix ausgeführt!');
    await browser.close();
}

fixFormName().catch(console.error); 