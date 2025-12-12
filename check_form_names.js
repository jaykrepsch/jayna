const axios = require('axios');

// API Base URL
const API_BASE = 'http://localhost:8080/api';

// Funktion zum Extrahieren des letzten Teils eines Translation Keys
function extractLastPart(translationKey) {
  if (!translationKey || translationKey === '-') return '-';
  const parts = translationKey.split('.');
  return parts[parts.length - 1] || '-';
}

// Funktion zum Erstellen des korrekten Form-Namens
function createCorrectFormName(categoryEntityName, subCategoryKey, subCategoryGroupKey, groupTypeKey) {
  return `${categoryEntityName}-${subCategoryKey}-${subCategoryGroupKey}-${groupTypeKey}`;
}

// Funktion zum Laden aller Daten
async function checkFormNames() {
  try {
    console.log('Lade alle Daten...');
    
    // 1. Alle Kategorien laden
    const categoriesResponse = await axios.get(`${API_BASE}/categories`);
    const categories = categoriesResponse.data;
    
    // 2. Alle SubCategories laden
    const subCategoriesResponse = await axios.get(`${API_BASE}/sub-categories`);
    const subCategories = subCategoriesResponse.data;
    
    // 3. Alle SubCategoryGroups laden
    const subCategoryGroupsResponse = await axios.get(`${API_BASE}/sub-category-groups`);
    const subCategoryGroups = subCategoryGroupsResponse.data;
    
    // 4. Alle GroupTypes laden
    const groupTypesResponse = await axios.get(`${API_BASE}/group-types`);
    const groupTypes = groupTypesResponse.data;
    
    console.log(`Geladen: ${categories.length} Kategorien, ${subCategories.length} SubCategories, ${subCategoryGroups.length} SubCategoryGroups, ${groupTypes.length} GroupTypes`);
    
    // Erstelle die Liste der falschen Form-Namen
    const incorrectFormNames = [];
    
    for (const groupType of groupTypes) {
      // Die API gibt bereits verknüpfte Daten zurück
      const subCategoryGroup = groupType.subCategoryGroup;
      const subCategory = subCategoryGroup?.subCategory;
      const category = subCategory?.category;
      
      if (category && subCategory && subCategoryGroup && groupType.formName) {
        const categoryEntityName = category.entityName || '-';
        const subCategoryKey = extractLastPart(subCategory.translationKey || '-');
        const subCategoryGroupKey = extractLastPart(subCategoryGroup.translationKey || '-');
        const groupTypeKey = extractLastPart(groupType.translationKey || '-');
        
        // Erstelle den korrekten Form-Namen
        const correctFormName = createCorrectFormName(categoryEntityName, subCategoryKey, subCategoryGroupKey, groupTypeKey);
        
        // Vergleiche mit dem aktuellen Form-Namen
        if (groupType.formName !== correctFormName) {
          incorrectFormNames.push({
            Kategorie: category.name,
            Art: subCategory.name,
            Unterkategorie: subCategoryGroup.name,
            Sparte: groupType.name,
            AktuellerFormName: groupType.formName,
            KorrekterFormName: correctFormName,
            CategoryEntityName: categoryEntityName,
            SubCategoryKey: subCategoryKey,
            SubCategoryGroupKey: subCategoryGroupKey,
            GroupTypeKey: groupTypeKey
          });
        }
      }
    }
    
    console.log(`\n📊 Gefunden: ${incorrectFormNames.length} falsche Form-Namen von ${groupTypes.length} insgesamt`);
    
    if (incorrectFormNames.length > 0) {
      console.log('\n❌ FALSCHE FORM-NAMEN:');
      console.log('Kategorie | Art | Unterkategorie | Sparte | Aktueller FormName | Korrekter FormName');
      console.log('----------|-----|----------------|--------|-------------------|-------------------');
      
      incorrectFormNames.forEach(item => {
        console.log(`${item.Kategorie} | ${item.Art} | ${item.Unterkategorie} | ${item.Sparte} | ${item.AktuellerFormName} | ${item.KorrekterFormName}`);
      });
      
      // Erstelle CSV-Datei
      const fs = require('fs');
      const csvHeader = 'Kategorie,Art,Unterkategorie,Sparte,AktuellerFormName,KorrekterFormName,CategoryEntityName,SubCategoryKey,SubCategoryGroupKey,GroupTypeKey\n';
      const csvRows = incorrectFormNames.map(item => 
        `"${item.Kategorie}","${item.Art}","${item.Unterkategorie}","${item.Sparte}","${item.AktuellerFormName}","${item.KorrekterFormName}","${item.CategoryEntityName}","${item.SubCategoryKey}","${item.SubCategoryGroupKey}","${item.GroupTypeKey}"`
      ).join('\n');
      
      const csvContent = csvHeader + csvRows;
      fs.writeFileSync('falsche_form_namen.csv', csvContent, 'utf8');
      console.log('\n✅ CSV-Datei erstellt: falsche_form_namen.csv');
    } else {
      console.log('\n✅ Alle Form-Namen sind korrekt!');
    }
    
  } catch (error) {
    console.error('❌ Fehler beim Laden der Daten:', error.message);
    if (error.response) {
      console.error('API Response:', error.response.data);
    }
  }
}

// Führe das Skript aus
checkFormNames(); 