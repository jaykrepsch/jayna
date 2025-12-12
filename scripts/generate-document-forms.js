// Node-Skript: erzeugt für alle Kombinationen aus document-hierarchy Form- und i18n-Dateien
// Ausführung: node scripts/generate-document-forms.js

const fs = require('fs');
const path = require('path');

const root = path.resolve(__dirname, '..');
const hierarchyPath = path.join(root, 'src', 'main', 'webapp', 'app', 'entities', 'document', 'document-hierarchy.json');
const formsDir = path.join(root, 'src', 'main', 'webapp', 'app', 'shared', 'form-definitions', 'document');
const i18nDir = path.join(root, 'src', 'main', 'webapp', 'i18n', 'de', 'document');

function ensureDir(dir) {
  if (!fs.existsSync(dir)) fs.mkdirSync(dir, { recursive: true });
}

function createFormJson(main, group, type) {
  return {
    formTitle: `jaynaApp.document-${main}-${group}-${type}.viewTitle`,
    parts: [
      {
        title: 'jaynaApp.baseDocument.general.title',
        fields: [
          { name: 'label', type: 'text', required: true },
          { name: 'description', type: 'text' },
          { name: 'entityState', type: 'dropdown', boxLabel: 'global.menu.choose', enumName: 'entityState', defaultValue: 'ACTIVE' }
        ]
      },
      {
        title: 'jaynaApp.baseDocument.documentData.title',
        fields: [
          { name: 'documentNumber', type: 'text' },
          { name: 'partner', type: 'text' },
          { name: 'status', type: 'dropdown', boxLabel: 'global.menu.choose', enumName: 'documentStatus', defaultValue: 'DRAFT' }
        ]
      },
      {
        title: `jaynaApp.document-${main}-${group}-${type}.specificData.title`,
        i18nSource: `document-${main}-${group}-${type}`,
        fields: []
      },
      {
        title: 'jaynaApp.baseDocument.weiteres.title',
        fields: [
          { name: 'comment', type: 'textarea' },
          { name: 'notes', type: 'textarea' }
        ]
      }
    ],
    relationsTitle: 'jaynaApp.baseDocument.relations.title'
  };
}

function createI18nJson(main, group, type) {
  const baseKey = `jaynaApp.document-${main}-${group}-${type}`;
  return {
    [`${baseKey}.createTitle`]: 'Dokument erstellen',
    [`${baseKey}.updateTitle`]: 'Dokument bearbeiten',
    [`${baseKey}.viewTitle`]: 'Dokument anzeigen',
    [`${baseKey}.specificData.title`]: 'Spezifische Daten'
  };
}

function mainExec() {
  ensureDir(formsDir);
  ensureDir(i18nDir);

  const hierarchy = JSON.parse(fs.readFileSync(hierarchyPath, 'utf8'));
  const { mainCategories, groupingMap, typeMap } = hierarchy;

  let created = 0;

  for (const main of mainCategories) {
    const mainId = main.id;
    const groups = groupingMap[mainId] || [];
    for (const group of groups) {
      const groupId = group.id;
      const types = typeMap[groupId] || [];
      for (const type of types) {
        const typeId = type.id;
        const formName = `document-${mainId}-${groupId}-${typeId}`;
        const formPath = path.join(formsDir, `${formName}.json`);
        const i18nPath = path.join(i18nDir, `i18n-${formName}.json`);

        if (!fs.existsSync(formPath)) {
          fs.writeFileSync(formPath, JSON.stringify(createFormJson(mainId, groupId, typeId), null, 2), 'utf8');
          created++;
        }
        if (!fs.existsSync(i18nPath)) {
          fs.writeFileSync(i18nPath, JSON.stringify(createI18nJson(mainId, groupId, typeId), null, 2), 'utf8');
          created++;
        }
      }
    }
  }

  console.log(`Erzeugt/ergänzt ${created} Dateien.`);
}

mainExec();


