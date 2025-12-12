const axios = require('axios');

// API Base URL
const API_BASE = 'http://localhost:8080/api';

// Funktion zum Extrahieren des letzten Teils eines Translation Keys
function extractLastPart(translationKey) {
  if (!translationKey || translationKey === '-') return '-';
  const parts = translationKey.split('.');
  return parts[parts.length - 1] || '-';
}

// Funktion zum Laden aller Daten
async function loadAllData() {
  try {
    console.log('Lade alle Daten...');
    
    // 1. Alle Kategorien laden
    const categoriesResponse = await axios.get(`${API_BASE}/categories`);
    const categories = categoriesResponse.data;
    console.log(`Geladen: ${categories.length} Kategorien`);
    
    // 2. Alle SubCategories laden
    const subCategoriesResponse = await axios.get(`${API_BASE}/sub-categories`);
    const subCategories = subCategoriesResponse.data;
    console.log(`Geladen: ${subCategories.length} SubCategories`);
    
    // 3. Alle SubCategoryGroups laden
    const subCategoryGroupsResponse = await axios.get(`${API_BASE}/sub-category-groups`);
    const subCategoryGroups = subCategoryGroupsResponse.data;
    console.log(`Geladen: ${subCategoryGroups.length} SubCategoryGroups`);
    
    // 4. Alle GroupTypes laden
    const groupTypesResponse = await axios.get(`${API_BASE}/group-types`);
    const groupTypes = groupTypesResponse.data;
    console.log(`Geladen: ${groupTypes.length} GroupTypes`);
    
    // Erstelle die vollständige Hierarchie
    const completeHierarchy = [];
    
    for (const groupType of groupTypes) {
      // Die API gibt bereits verknüpfte Daten zurück
      const subCategoryGroup = groupType.subCategoryGroup;
      const subCategory = subCategoryGroup?.subCategory;
      const category = subCategory?.category;
      
      if (category && subCategory && subCategoryGroup) {
        const row = {
          Kategorie: category.name,
          Art: subCategory.name,
          Unterkategorie: subCategoryGroup.name,
          Sparte: groupType.name,
          CategoryEntityName: category.entityName || '-',
          SubCategoryKey: extractLastPart(subCategory.translationKey || '-'),
          SubCategoryGroupKey: extractLastPart(subCategoryGroup.translationKey || '-'),
          GroupTypeKey: extractLastPart(groupType.translationKey || '-'),
          FormName: groupType.formName || '-',
          CombinedKey: `${category.entityName || '-'}-${extractLastPart(subCategory.translationKey || '-')}-${extractLastPart(subCategoryGroup.translationKey || '-')}-${extractLastPart(groupType.translationKey || '-')}`
        };
        completeHierarchy.push(row);
      }
    }
    
    console.log(`Erstellt: ${completeHierarchy.length} Einträge`);
    
    // Erstelle CSV-Header
    const csvHeader = 'Kategorie,Art,Unterkategorie,Sparte,CategoryEntityName,SubCategoryKey,SubCategoryGroupKey,GroupTypeKey,FormName,CombinedKey\n';
    
    // Erstelle CSV-Zeilen
    const csvRows = completeHierarchy.map(row => 
      `"${row.Kategorie}","${row.Art}","${row.Unterkategorie}","${row.Sparte}","${row.CategoryEntityName}","${row.SubCategoryKey}","${row.SubCategoryGroupKey}","${row.GroupTypeKey}","${row.FormName}","${row.CombinedKey}"`
    ).join('\n');
    
    // Schreibe CSV-Datei
    const fs = require('fs');
    const csvContent = csvHeader + csvRows;
    fs.writeFileSync('erweiterte_hierarchie_aktuell.csv', csvContent, 'utf8');
    
    console.log('✅ CSV-Datei erstellt: erweiterte_hierarchie_aktuell.csv');
    console.log(`📊 Statistiken:`);
    console.log(`   - Kategorien: ${categories.length}`);
    console.log(`   - SubCategories: ${subCategories.length}`);
    console.log(`   - SubCategoryGroups: ${subCategoryGroups.length}`);
    console.log(`   - GroupTypes: ${groupTypes.length}`);
    console.log(`   - Einträge in CSV: ${completeHierarchy.length}`);
    
  } catch (error) {
    console.error('❌ Fehler beim Laden der Daten:', error.message);
    if (error.response) {
      console.error('API Response:', error.response.data);
    }
  }
}

// Führe das Skript aus
loadAllData(); 