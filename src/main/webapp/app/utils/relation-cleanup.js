/**
 * Hilfsskript zum Löschen alter Verknüpfungen
 * 
 * Verwendung in der Browser-Konsole:
 * 1. Öffne die Browser-Konsole (F12)
 * 2. Kopiere und führe diesen Code aus:
 * 
 * // Alle bidirektionalen Verknüpfungen anzeigen
 * await showAllBidirectionalRelations();
 * 
 * // Spezifische Verknüpfung löschen (ersetze ID durch die tatsächliche ID)
 * await deleteBidirectionalRelation(5);
 * 
 * // Alle Verknüpfungen für eine Entität löschen
 * await deleteAllRelationsForEntity('contract', 1001);
 */

// Alle bidirektionalen Verknüpfungen anzeigen
async function showAllBidirectionalRelations() {
  console.log('🔍 Lade alle bidirektionalen Verknüpfungen...');
  
  const entityTypes = ['contract', 'contact', 'realestate', 'mobility', 'financeaccount'];
  const knownIds = {
    contract: [1001, 1551],
    realestate: [2051, 501],
    contact: [601]
  };
  
  const allRelations = [];
  
  for (const entityType of entityTypes) {
    const ids = knownIds[entityType] || [];
    for (const id of ids) {
      try {
        const response = await fetch(`/api/relations/bidirectional/${entityType}/${id}`);
        if (response.ok) {
          const relations = await response.json();
          allRelations.push(...relations);
          console.log(`✅ ${entityType}/${id}: ${relations.length} Verknüpfungen gefunden`);
        }
      } catch (error) {
        console.error(`❌ Fehler beim Laden von ${entityType}/${id}:`, error);
      }
    }
  }
  
  console.log('📋 Alle bidirektionalen Verknüpfungen:');
  allRelations.forEach((relation, index) => {
    console.log(`${index + 1}. ID: ${relation.id} | ${relation.sourceEntity} (${relation.sourceEntityId}) → ${relation.targetEntity} (${relation.targetEntityId})`);
  });
  
  return allRelations;
}

// Spezifische bidirektionale Verknüpfung löschen
async function deleteBidirectionalRelation(relationId) {
  console.log(`🗑️ Lösche bidirektionale Verknüpfung mit ID: ${relationId}`);
  
  try {
    const response = await fetch(`/api/relations/bidirectional/${relationId}`, {
      method: 'DELETE'
    });
    
    if (response.ok) {
      console.log(`✅ Verknüpfung ${relationId} erfolgreich gelöscht`);
      return true;
    } else {
      console.error(`❌ Fehler beim Löschen der Verknüpfung ${relationId}:`, response.status);
      return false;
    }
  } catch (error) {
    console.error(`❌ Fehler beim Löschen der Verknüpfung ${relationId}:`, error);
    return false;
  }
}

// Alle Verknüpfungen für eine spezifische Entität löschen
async function deleteAllRelationsForEntity(entityType, entityId) {
  console.log(`🗑️ Lösche alle Verknüpfungen für ${entityType}/${entityId}`);
  
  try {
    const response = await fetch(`/api/relations/bidirectional/${entityType}/${entityId}`);
    if (response.ok) {
      const relations = await response.json();
      console.log(`📋 Gefunden: ${relations.length} Verknüpfungen`);
      
      let deletedCount = 0;
      for (const relation of relations) {
        const success = await deleteBidirectionalRelation(relation.id);
        if (success) deletedCount++;
      }
      
      console.log(`✅ ${deletedCount} von ${relations.length} Verknüpfungen gelöscht`);
      return deletedCount;
    }
  } catch (error) {
    console.error(`❌ Fehler beim Laden der Verknüpfungen für ${entityType}/${entityId}:`, error);
    return 0;
  }
}

// Alle alten Test-Verknüpfungen löschen (IDs aus den Logs)
async function cleanupOldTestRelations() {
  console.log('🧹 Starte Cleanup der alten Test-Verknüpfungen...');
  
  // IDs der bekannten alten Verknüpfungen aus den Logs
  const oldRelationIds = [5, 6]; // Diese IDs aus den Console-Logs
  
  let deletedCount = 0;
  for (const id of oldRelationIds) {
    const success = await deleteBidirectionalRelation(id);
    if (success) deletedCount++;
  }
  
  console.log(`✅ Cleanup abgeschlossen: ${deletedCount} von ${oldRelationIds.length} Verknüpfungen gelöscht`);
  return deletedCount;
}

// Hilfsfunktion zum Anzeigen der aktuellen Verknüpfungen
async function showCurrentRelations() {
  console.log('📊 Aktuelle Verknüpfungen:');
  const relations = await showAllBidirectionalRelations();
  console.log(`📈 Gesamt: ${relations.length} bidirektionale Verknüpfungen`);
  return relations;
}

// Exportiere Funktionen für die Browser-Konsole
window.relationCleanup = {
  showAllBidirectionalRelations,
  deleteBidirectionalRelation,
  deleteAllRelationsForEntity,
  cleanupOldTestRelations,
  showCurrentRelations
};

if (process.env.NODE_ENV === 'development') {
  console.log('🛠️ Relation Cleanup Tools geladen!');
  console.log('Verwendung:');
  console.log('- relationCleanup.showCurrentRelations() - Zeige alle aktuellen Verknüpfungen');
  console.log('- relationCleanup.deleteBidirectionalRelation(5) - Lösche spezifische Verknüpfung');
  console.log('- relationCleanup.cleanupOldTestRelations() - Lösche alle alten Test-Verknüpfungen');
}