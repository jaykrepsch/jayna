const axios = require('axios');

// API Base URL
const API_BASE = 'http://localhost:8080/api';

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
    
    return { categories, subCategories, subCategoryGroups, groupTypes };
  } catch (error) {
    console.error('Fehler beim Laden der Daten:', error.message);
    throw error;
  }
}

// Funktion zum Erstellen der vollständigen Liste
function createCompleteList(data) {
  const { categories, subCategories, subCategoryGroups, groupTypes } = data;
  
  console.log('Erstelle vollständige Liste...');
  
  const completeList = [];
  
  // Für jede Kategorie
  categories.forEach(category => {
    // SubCategories für diese Kategorie finden
    const categorySubCategories = subCategories.filter(sc => sc.category?.id === category.id);
    
    if (categorySubCategories.length === 0) {
      // Wenn keine SubCategories, trotzdem einen Eintrag erstellen
      completeList.push({
        category: category.name,
        subCategory: '-',
        subCategoryGroup: '-',
        groupType: '-'
      });
    } else {
      // Für jede SubCategory
      categorySubCategories.forEach(subCategory => {
        // SubCategoryGroups für diese SubCategory finden
        const subCategoryGroupsForSub = subCategoryGroups.filter(scg => scg.subCategory?.id === subCategory.id);
        
        if (subCategoryGroupsForSub.length === 0) {
          // Wenn keine SubCategoryGroups, trotzdem einen Eintrag erstellen
          completeList.push({
            category: category.name,
            subCategory: subCategory.name,
            subCategoryGroup: '-',
            groupType: '-'
          });
        } else {
          // Für jede SubCategoryGroup
          subCategoryGroupsForSub.forEach(subCategoryGroup => {
            // GroupTypes für diese SubCategoryGroup finden
            const groupTypesForGroup = groupTypes.filter(gt => gt.subCategoryGroup?.id === subCategoryGroup.id);
            
            if (groupTypesForGroup.length === 0) {
              // Wenn keine GroupTypes, trotzdem einen Eintrag erstellen
              completeList.push({
                category: category.name,
                subCategory: subCategory.name,
                subCategoryGroup: subCategoryGroup.name,
                groupType: '-'
              });
            } else {
              // Für jeden GroupType einen Eintrag erstellen
              groupTypesForGroup.forEach(groupType => {
                completeList.push({
                  category: category.name,
                  subCategory: subCategory.name,
                  subCategoryGroup: subCategoryGroup.name,
                  groupType: groupType.name
                });
              });
            }
          });
        }
      });
    }
  });
  
  return completeList;
}

// Funktion zum Speichern als CSV
function saveAsCSV(list, filename) {
  const csvHeader = 'Kategorie,Art,Unterkategorie,Sparte\n';
  const csvContent = list.map(item => 
    `"${item.category}","${item.subCategory}","${item.subCategoryGroup}","${item.groupType}"`
  ).join('\n');
  
  const fs = require('fs');
  fs.writeFileSync(filename, csvHeader + csvContent, 'utf8');
  console.log(`Liste gespeichert als: ${filename}`);
}

// Funktion zum Speichern als JSON
function saveAsJSON(list, filename) {
  const fs = require('fs');
  fs.writeFileSync(filename, JSON.stringify(list, null, 2), 'utf8');
  console.log(`Liste gespeichert als: ${filename}`);
}

// Hauptfunktion
async function main() {
  try {
    // Daten laden
    const data = await loadAllData();
    
    // Vollständige Liste erstellen
    const completeList = createCompleteList(data);
    
    console.log(`\nVollständige Liste erstellt: ${completeList.length} Einträge`);
    
    // Statistiken ausgeben
    const uniqueCategories = [...new Set(completeList.map(item => item.category))];
    const uniqueSubCategories = [...new Set(completeList.map(item => item.subCategory).filter(item => item !== '-'))];
    const uniqueSubCategoryGroups = [...new Set(completeList.map(item => item.subCategoryGroup).filter(item => item !== '-'))];
    const uniqueGroupTypes = [...new Set(completeList.map(item => item.groupType).filter(item => item !== '-'))];
    
    console.log(`\nStatistiken:`);
    console.log(`- Eindeutige Kategorien: ${uniqueCategories.length}`);
    console.log(`- Eindeutige Arten: ${uniqueSubCategories.length}`);
    console.log(`- Eindeutige Unterkategorien: ${uniqueSubCategoryGroups.length}`);
    console.log(`- Eindeutige Sparten: ${uniqueGroupTypes.length}`);
    
    // Als CSV und JSON speichern
    saveAsCSV(completeList, 'vollständige_hierarchie.csv');
    saveAsJSON(completeList, 'vollständige_hierarchie.json');
    
    // Erste 10 Einträge anzeigen
    console.log('\nErste 10 Einträge:');
    console.log('Kategorie | Art | Unterkategorie | Sparte');
    console.log('----------|-----|----------------|--------');
    completeList.slice(0, 10).forEach(item => {
      console.log(`${item.category} | ${item.subCategory} | ${item.subCategoryGroup} | ${item.groupType}`);
    });
    
    if (completeList.length > 10) {
      console.log(`... und ${completeList.length - 10} weitere Einträge`);
    }
    
  } catch (error) {
    console.error('Fehler:', error.message);
  }
}

// Skript ausführen
main(); 