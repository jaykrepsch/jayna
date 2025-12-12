const fs = require('fs');

console.log('=== Einfacher Übersetzungstest ===\n');

const baseCategories = JSON.parse(fs.readFileSync('src/main/webapp/i18n/de/common/baseCategories.json', 'utf8'));

const testKeys = [
  'mini-job',
  'half-time', 
  'full-time',
  'work-contract',
  'annuity-loan',
  'electricity'
];

console.log('Teste Keys:');
testKeys.forEach(key => {
  const value = baseCategories.jaynaApp.contract['group-type'][key];
  if (value) {
    console.log(`  ✅ ${key} → "${value}"`);
  } else {
    console.log(`  ❌ ${key} → FEHLT`);
  }
});

console.log('\n=== Test abgeschlossen ==='); 