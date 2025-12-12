const fs = require('fs');
const path = require('path');

// Extrahiere den Formulardefinitionsnamen aus dem Dateinamen
function extractFormName(fileName) {
  return fileName.replace('.json', '');
}

// Erstelle spezifische Felder basierend auf dem Realestate-Typ
function getCategoryFields(formName) {
  const categoryFields = {
    'realestate-parking-carport-carport': [
      { name: "carportType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "carportType" },
      { name: "constructionMaterial", type: "dropdown", boxLabel: "global.menu.choose", enumName: "constructionMaterial" },
      { name: "roofType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "roofType" },
      { name: "parkingSpaces", type: "number" }
    ],
    'realestate-parking-garage-garage': [
      { name: "garageType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "garageType" },
      { name: "constructionMaterial", type: "dropdown", boxLabel: "global.menu.choose", enumName: "constructionMaterial" },
      { name: "parkingSpaces", type: "number" },
      { name: "hasElectricDoor", type: "checkbox" }
    ],
    'realestate-parking-parkinghouse-parkinghouse': [
      { name: "parkingHouseType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "parkingHouseType" },
      { name: "parkingSpaces", type: "number" },
      { name: "floor", type: "number" },
      { name: "hasSecurity", type: "checkbox" }
    ],
    'realestate-parking-parkingspace-parkingspace': [
      { name: "parkingSpaceType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "parkingSpaceType" },
      { name: "spaceNumber", type: "text" },
      { name: "hasCover", type: "checkbox" }
    ],
    'realestate-plot-allotmentgarden-allotmentgarden': [
      { name: "gardenType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "gardenType" },
      { name: "plotNumber", type: "text" },
      { name: "hasWaterConnection", type: "checkbox" },
      { name: "hasElectricity", type: "checkbox" }
    ],
    'realestate-plot-buildingplot-buildingplot': [
      { name: "plotType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "plotType" },
      { name: "zoningType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "zoningType" },
      { name: "buildingPermission", type: "checkbox" },
      { name: "developmentStatus", type: "dropdown", boxLabel: "global.menu.choose", enumName: "developmentStatus" }
    ],
    'realestate-plot-farmland-farmland': [
      { name: "farmlandType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "farmlandType" },
      { name: "soilQuality", type: "dropdown", boxLabel: "global.menu.choose", enumName: "soilQuality" },
      { name: "irrigation", type: "checkbox" },
      { name: "cropType", type: "text" }
    ],
    'realestate-plot-forest-forest': [
      { name: "forestType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "forestType" },
      { name: "treeSpecies", type: "text" },
      { name: "harvestingRights", type: "checkbox" },
      { name: "conservationStatus", type: "dropdown", boxLabel: "global.menu.choose", enumName: "conservationStatus" }
    ],
    'realestate-plot-fruit-fruit': [
      { name: "fruitType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "fruitType" },
      { name: "treeCount", type: "number" },
      { name: "harvestSeason", type: "text" },
      { name: "irrigation", type: "checkbox" }
    ],
    'realestate-plot-meadow-meadow': [
      { name: "meadowType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "meadowType" },
      { name: "grassType", type: "text" },
      { name: "mowingFrequency", type: "number" },
      { name: "fertilization", type: "checkbox" }
    ],
    'realestate-plot-pasture-pasture': [
      { name: "pastureType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "pastureType" },
      { name: "animalCapacity", type: "number" },
      { name: "fencing", type: "checkbox" },
      { name: "waterSupply", type: "checkbox" }
    ],
    'realestate-property-commercialresidential-commercialresidentialproperty': [
      { name: "propertyType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "propertyType" },
      { name: "usageType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "usageType" },
      { name: "floors", type: "number" },
      { name: "commercialArea", type: "number" },
      { name: "residentialArea", type: "number" }
    ],
    'realestate-loan-annuityloan-annuityloan': [
      { name: "loanType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "loanType" },
      { name: "loanAmount", type: "number" },
      { name: "interestRate", type: "number" },
      { name: "term", type: "number" }
    ]
  };
  
  return categoryFields[formName] || [
    { name: "propertyType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "propertyType" },
    { name: "category", type: "text" }
  ];
}

// Standard-Felder für jeden Abschnitt
const standardFields = {
  general: [
    { name: "label", type: "text", rules: "required" },
    { name: "description", type: "text" },
    { name: "entityState", type: "dropdown", boxLabel: "global.menu.choose", enumName: "entityState", defaultValue: "ACTIVE" }
  ],
  address: [
    { name: "street", type: "text" },
    { name: "streetNumber", type: "text" },
    { name: "postalCode", type: "text" },
    { name: "city", type: "text" },
    { name: "state", type: "text" },
    { name: "country", type: "text" },
    { name: "longitude", type: "text" },
    { name: "latitude", type: "text" }
  ],
  plot: [
    { name: "area", type: "number" },
    { name: "builtUpArea", type: "number" },
    { name: "sealtArea", type: "number" },
    { name: "undevelopedArea", type: "number" }
  ],
  weiteres: [
    { name: "comment", type: "textarea" },
    { name: "notes", type: "textarea" }
  ]
};

// Standard-Relations für Realestate
const standardRelations = [
  { entityName: "contact", relationName: "contactRealestates" },
  { entityName: "contract", relationName: "contractRealestates" },
  { entityName: "financeaccount", relationName: "financeaccountRealestates" },
  { entityName: "mobility", relationName: "mobilityRealestates" },
  { entityName: "realestate", relationName: "realestateRealestates" }
];

// Formulardefinition nach formControl-Konzept strukturieren
function structureRealestateForm(filePath) {
  try {
    const fileName = path.basename(filePath);
    const formName = extractFormName(fileName);
    
    // Neue Struktur erstellen
    const newFormDef = {
      "_comment": "formControl-Konzept: Standard-Abschnitte aus BaseRealestate, spezielle Abschnitte mit i18nSource",
      "formTitle": formName.replace(/-/g, ' ').replace(/\b\w/g, l => l.toUpperCase()),
      "parts": [
        {
          "title": "jaynaApp.baseRealestate.general.title",
          "fields": standardFields.general
        },
        {
          "title": "jaynaApp.baseRealestate.address.title",
          "fields": standardFields.address
        },
        {
          "title": "jaynaApp.baseRealestate.plot.title",
          "fields": standardFields.plot
        },
        {
          "title": "jaynaApp.baseRealestate.weiteres.title",
          "fields": standardFields.weiteres
        }
      ],
      "relations": standardRelations,
      "relationsTitle": `jaynaApp.${formName}.relations-title`
    };
    
    return { hasChanges: true, formDef: newFormDef };
    
  } catch (error) {
    return { hasChanges: false, error: error.message };
  }
}

// Hauptfunktion
function fixAllRealestateForms() {
  console.log('🔧 Strukturiere alle Realestate-Formulardefinitionen nach formControl-Konzept...\n');
  
  const realestateDir = './src/main/webapp/app/shared/form-definitions/realestate';
  const files = fs.readdirSync(realestateDir);
  
  let fixedCount = 0;
  let errorCount = 0;
  let skipCount = 0;
  
  for (const file of files) {
    if (!file.endsWith('.json')) continue;
    
    const filePath = path.join(realestateDir, file);
    const fileSize = fs.statSync(filePath).size;
    
    if (fileSize <= 10) {
      console.log(`⚠️  ${file} (übersprungen - leer)`);
      skipCount++;
      continue;
    }
    
    const result = structureRealestateForm(filePath);
    
    if (result.error) {
      console.log(`❌ ${file} (Fehler: ${result.error})`);
      errorCount++;
    } else if (result.hasChanges) {
      // Datei speichern
      try {
        fs.writeFileSync(filePath, JSON.stringify(result.formDef, null, 2), 'utf8');
        console.log(`✅ ${file} (strukturiert)`);
        fixedCount++;
      } catch (error) {
        console.log(`❌ ${file} (Speicherfehler: ${error.message})`);
        errorCount++;
      }
    } else {
      console.log(`✅ ${file} (bereits korrekt)`);
      skipCount++;
    }
  }
  
  console.log(`\n📊 Zusammenfassung:`);
  console.log(`✅ Strukturiert: ${fixedCount}`);
  console.log(`✅ Bereits korrekt: ${skipCount}`);
  console.log(`❌ Fehler: ${errorCount}`);
  console.log(`📄 Gesamt: ${fixedCount + skipCount + errorCount}`);
}

// Skript ausführen
fixAllRealestateForms(); 