const axios = require('axios');

// API Base URL
const API_BASE = 'http://localhost:8080/api';

// Funktion zum Extrahieren des letzten Teils eines Translation Keys und Entfernen aller Sonderzeichen
function extractLastPartClean(translationKey) {
  if (!translationKey || translationKey === '-') return '-';
  const parts = translationKey.split('.');
  const lastPart = parts[parts.length - 1] || '-';
  // Entferne alle Sonderzeichen (Bindestriche, Unterstriche, etc.) und behalte nur Buchstaben und Zahlen
  return lastPart.replace(/[^a-zA-Z0-9]/g, '');
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
        const categoryEntityName = category.entityName || '-';
        const subCategoryKey = extractLastPartClean(subCategory.translationKey || '-');
        const subCategoryGroupKey = extractLastPartClean(subCategoryGroup.translationKey || '-');
        const groupTypeKey = extractLastPartClean(groupType.translationKey || '-');
        
        const row = {
          Kategorie: category.name,
          Art: subCategory.name,
          Unterkategorie: subCategoryGroup.name,
          Sparte: groupType.name,
          CategoryEntityName: categoryEntityName,
          SubCategoryKey: subCategoryKey,
          SubCategoryGroupKey: subCategoryGroupKey,
          GroupTypeKey: groupTypeKey,
          FormName: groupType.formName || '-',
          CombinedKey: `${categoryEntityName}-${subCategoryKey}-${subCategoryGroupKey}-${groupTypeKey}`
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
    fs.writeFileSync('erweiterte_hierarchie_nospecialchars.csv', csvContent, 'utf8');
    
    console.log('✅ CSV-Datei erstellt: erweiterte_hierarchie_nospecialchars.csv');
    console.log(`📊 Statistiken:`);
    console.log(`   - Kategorien: ${categories.length}`);
    console.log(`   - SubCategories: ${subCategories.length}`);
    console.log(`   - SubCategoryGroups: ${subCategoryGroups.length}`);
    console.log(`   - GroupTypes: ${groupTypes.length}`);
    console.log(`   - Einträge in CSV: ${completeHierarchy.length}`);
    
    // Zeige einige Beispiele der Änderungen
    console.log('\n🔍 Beispiele der Änderungen:');
    console.log('Vorher → Nachher:');
    console.log('employment-contract → employmentcontract');
    console.log('e-bike → ebike');
    console.log('passenger-vehicle → passengervehicle');
    console.log('termination-agreement → terminationagreement');
    
  } catch (error) {
    console.error('❌ Fehler beim Laden der Daten:', error.message);
    if (error.response) {
      console.error('API Response:', error.response.data);
    }
  }
}

// Führe das Skript aus
loadAllData(); 