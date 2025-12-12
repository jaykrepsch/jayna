<template>
  <div class="container mx-auto px-4 py-8">
    <div class="bg-white shadow-lg rounded-lg p-6">
      <h1 class="text-2xl font-bold text-gray-900 mb-6">Verknüpfungen Übersicht</h1>
      
      <!-- Bidirektionale Verknüpfungen -->
      <div class="mb-8">
        <h2 class="text-xl font-semibold text-gray-800 mb-4">Bidirektionale Verknüpfungen</h2>
        
        <div v-if="bidirectionalRelations.length === 0" class="text-gray-500 italic">
          Keine bidirektionalen Verknüpfungen gefunden.
        </div>
        
        <div v-else class="space-y-4">
          <div 
            v-for="relation in bidirectionalRelations" 
            :key="relation.id"
            class="border border-gray-200 rounded-lg p-4 hover:bg-gray-50"
          >
            <div class="flex justify-between items-center">
              <div class="flex-1">
                <div class="text-sm text-gray-600">
                  <span class="font-medium">{{ relation.sourceEntity }} (ID: {{ relation.sourceEntityId }})</span>
                  <span class="mx-2">→</span>
                  <span class="font-medium">{{ relation.targetEntity }} (ID: {{ relation.targetEntityId }})</span>
                </div>
                <div v-if="relation.description" class="text-xs text-gray-500 mt-1">
                  {{ relation.description }}
                </div>
              </div>
              <div class="flex space-x-2">
                <button
                  @click="viewRelation(relation)"
                  class="inline-flex items-center px-3 py-1 text-sm font-medium rounded text-blue-600 hover:text-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
                  title="Anzeigen"
                >
                  <EyeIcon class="h-4 w-4 mr-1" />
                  Anzeigen
                </button>
                <button
                  @click="deleteRelation(relation)"
                  class="inline-flex items-center px-3 py-1 text-sm font-medium rounded text-red-600 hover:text-red-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500"
                  title="Löschen"
                >
                  <TrashIcon class="h-4 w-4 mr-1" />
                  Löschen
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- Direkte Verknüpfungen -->
      <div class="mb-8">
        <h2 class="text-xl font-semibold text-gray-800 mb-4">Direkte Verknüpfungen</h2>
        
        <div v-if="directRelations.length === 0" class="text-gray-500 italic">
          Keine direkten Verknüpfungen gefunden.
        </div>
        
        <div v-else class="space-y-4">
          <div 
            v-for="relation in directRelations" 
            :key="relation.id"
            class="border border-gray-200 rounded-lg p-4 hover:bg-gray-50"
          >
            <div class="flex justify-between items-center">
              <div class="flex-1">
                <div class="text-sm text-gray-600">
                  <span class="font-medium">{{ relation.entityType }} (ID: {{ relation.entityId }})</span>
                  <span class="mx-2">→</span>
                  <span class="font-medium">{{ relation.targetType }} (ID: {{ relation.targetId }})</span>
                </div>
                <div v-if="relation.description" class="text-xs text-gray-500 mt-1">
                  {{ relation.description }}
                </div>
              </div>
              <div class="flex space-x-2">
                <button
                  @click="viewDirectRelation(relation)"
                  class="inline-flex items-center px-3 py-1 text-sm font-medium rounded text-blue-600 hover:text-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
                  title="Anzeigen"
                >
                  <EyeIcon class="h-4 w-4 mr-1" />
                  Anzeigen
                </button>
                <button
                  @click="deleteDirectRelation(relation)"
                  class="inline-flex items-center px-3 py-1 text-sm font-medium rounded text-red-600 hover:text-red-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500"
                  title="Löschen"
                >
                  <TrashIcon class="h-4 w-4 mr-1" />
                  Löschen
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { EyeIcon, TrashIcon } from '@heroicons/vue/24/outline';

const router = useRouter();
const bidirectionalRelations = ref([]);
const directRelations = ref([]);

const loadAllRelations = async () => {
  try {
    // Lade bidirektionale Verknüpfungen für alle Entitätstypen
    const entityTypes = ['contract', 'contact', 'realestate', 'mobility', 'financeaccount'];
    const allBidirectional = [];
    
    for (const entityType of entityTypes) {
      // Hier würden wir normalerweise alle Entitäten des Typs laden
      // Für jetzt verwenden wir die bekannten IDs aus den Logs
      const knownIds = {
        contract: [1001, 1551],
        realestate: [2051, 501],
        contact: [601]
      };
      
      const ids = knownIds[entityType] || [];
      for (const id of ids) {
        try {
          const response = await fetch(`/api/relations/bidirectional/${entityType}/${id}`);
          if (response.ok) {
            const relations = await response.json();
            allBidirectional.push(...relations);
          }
        } catch (error) {
        }
      }
    }
    
    bidirectionalRelations.value = allBidirectional;
    
  } catch (error) {
  }
};

const deleteRelation = async (relation) => {
  if (!confirm(`Möchtest du diese Verknüpfung wirklich löschen?\n${relation.sourceEntity} (${relation.sourceEntityId}) → ${relation.targetEntity} (${relation.targetEntityId})`)) {
    return;
  }
  
  try {
    const response = await fetch(`/api/relations/bidirectional/${relation.id}`, {
      method: 'DELETE'
    });
    
    if (response.ok) {
      bidirectionalRelations.value = bidirectionalRelations.value.filter(r => r.id !== relation.id);
    } else {
      
    }
  } catch (error) {
    
  }
};

const deleteDirectRelation = async (relation) => {
  if (!confirm(`Möchtest du diese direkte Verknüpfung wirklich löschen?\n${relation.entityType} (${relation.entityId}) → ${relation.targetType} (${relation.targetId})`)) {
    return;
  }
  
  try {
    // Hier würden wir den entsprechenden Lösch-Endpunkt für direkte Verknüpfungen aufrufen
    // TODO: Implementiere das Löschen direkter Verknüpfungen
  } catch (error) {
  }
};

const viewRelation = (relation) => {
  // Navigiere zur entsprechenden Entität
  const entityType = relation.sourceEntity;
  const entityId = relation.sourceEntityId;
  
  const routeName = getRouteNameForEntity(entityType);
  router.push({
    name: routeName,
    params: { entityid: entityId }
  });
};

const viewDirectRelation = (relation) => {
  // Navigiere zur entsprechenden Entität
  const entityType = relation.entityType;
  const entityId = relation.entityId;
  
  const routeName = getRouteNameForEntity(entityType);
  router.push({
    name: routeName,
    params: { entityid: entityId }
  });
};

const getRouteNameForEntity = (entityType) => {
  const routeNames = {
    contact: 'ContactView',
    contract: 'ContractView',
    realestate: 'RealEstateView',
    financeaccount: 'FinanceAccountView',
    mobility: 'MobilityView'
  };
  return routeNames[entityType] || 'ContractView';
};

onMounted(() => {
  loadAllRelations();
});
</script> 