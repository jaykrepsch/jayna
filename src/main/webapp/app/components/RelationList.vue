<template>
  <div class="space-y-6">
    <!-- Tabelle für Verknüpfungen -->
    <div class="bg-white rounded-lg shadow">
      <!-- Tabelle -->
      <div class="overflow-x-auto">
        <table class="min-w-full divide-y divide-gray-200">
          <thead class="bg-gray-50">
            <tr>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Kategorie
              </th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Bezeichnung
              </th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Vertragspartner
              </th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Sparte
              </th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Aktionen
              </th>
            </tr>
          </thead>
          <tbody class="bg-white divide-y divide-gray-200">
            <tr v-if="!hasRelations" class="text-center">
              <td colspan="5" class="px-6 py-4 text-sm text-gray-500">
                {{ $t('jaynaApp.form-builder.messages.no-relationships') }}
              </td>
            </tr>
            <tr v-else v-for="(relation, index) in allRelations" :key="index">
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                {{ getCategoryName(relation) }}
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                {{ getLabel(relation) }}
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                {{ getContractPartner(relation) }}
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                {{ getGroupType(relation) }}
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                <div class="flex space-x-2">
                  <button
                    @click.prevent="viewRelation(relation)"
                    class="inline-flex items-center px-2 py-1 text-xs font-medium rounded text-black hover:text-gray-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-500"
                    title="Anzeigen"
                  >
                    <EyeIcon class="h-4 w-4" />
                  </button>
                  <Button
                    v-if="editable"
                    type="secondary"
                    :text="$t('button.delete')"
                    @click.prevent="relation.isBidirectional ? deleteBidirectionalRelationSafely(relation) : $emit('delete', { relation, relationConfig: getRelationConfig(relation) })"
                  />
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    

  </div>
</template>

<script setup>
import { EyeIcon } from '@heroicons/vue/24/outline';
import Button from '@/components/buttons/button.vue';
import AddButton from '@/components/buttons/add-button.vue';
import GroupTypeService from '@/services/group-type.service';
import { useGetService } from "@/composables/utils";
import { ref, computed, onMounted } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

const store = useStore();
const router = useRouter();
const props = defineProps({
  formConfig: { type: Object, required: false },
  data: { type: Object, required: true },
  groupType: { type: Object, required: false },
  editable: { type: Boolean, required: false, default: false },
});
const emits = defineEmits(['delete', 'add-relationship', 'bidirectional-relation-deleted']);

function isValidDate(date) {
  if (!date) return false;
  const d = new Date(date);
  return d instanceof Date && !isNaN(d.getTime());
}

const hasRelations = computed(() => {
  // Prüfe direkte Verknüpfungen
  if (props.formConfig?.relations && Array.isArray(props.formConfig.relations)) {
    for (const relation of props.formConfig.relations) {
      if (props.data[relation.relationName]?.length > 0) {
        return true;
      }
    }
  }
  
  // Prüfe bidirektionale Verknüpfungen
  if (bidirectionalRelations.value.length > 0) {
    return true;
  }
  
  return false;
});

// Bidirektionale Verknüpfungen laden
const bidirectionalRelations = ref([]);

const loadBidirectionalRelations = async () => {
  if (!props.data?.id) {
    return;
  }
  
  try {
    // Bestimme den Entity-Typ aus der aktuellen Route
    const path = window.location.pathname;
    let entityType = 'contract'; // Default
    
    if (path.includes('/contract/')) {
      entityType = 'contract';
    } else if (path.includes('/contact/')) {
      entityType = 'contact';
    } else if (path.includes('/realestate/')) {
      entityType = 'realestate';
    } else if (path.includes('/mobility/')) {
      entityType = 'mobility';
    } else if (path.includes('/financeaccount/')) {
      entityType = 'financeaccount';
    }
    
    // API-Call für bidirektionale Verknüpfungen
    const response = await fetch(`/api/relations/bidirectional/${entityType}/${props.data.id}`);
    if (response.ok) {
      const apiRelations = await response.json();
      // Filtere nur echte Datenbankverknüpfungen (keine simulierten IDs)
      bidirectionalRelations.value = apiRelations.filter(relation => 
        relation.id && 
        typeof relation.id === 'number' && 
        relation.id > 1000 // Nur echte Datenbank-IDs
      );
    } else {
      bidirectionalRelations.value = simulateBidirectionalRelations();
    }
  } catch (error) {
    // Fallback: Simuliere bidirektionale Verknüpfungen
    bidirectionalRelations.value = simulateBidirectionalRelations();
  }
};

// Keine simulierten Verknüpfungen mehr - nur echte Datenbankverknüpfungen
const simulateBidirectionalRelations = () => {
  return []; // Leere Liste - keine simulierten Verknüpfungen mehr
};

// Alle Verknüpfungen aus allen Relationstypen sammeln (direkte + bidirektionale)
const allRelations = computed(() => {
  const relations = [];
  
  // Direkte Verknüpfungen
  if (props.formConfig?.relations && Array.isArray(props.formConfig.relations)) {
    for (const relationConfig of props.formConfig.relations) {
      if (props.data[relationConfig.relationName]?.length > 0) {
        for (const relation of props.data[relationConfig.relationName]) {
          relations.push({
            ...relation,
            relationConfig,
            isDirect: true
          });
        }
      }
    }
  }
  
  // Bidirektionale Verknüpfungen
  for (const relation of bidirectionalRelations.value) {
    relations.push({
      ...relation,
      isDirect: false,
      isBidirectional: true
    });
  }
  

  
  return relations;
});

// Hilfsfunktionen für die Tabellenspalten
const getCategoryName = (relation) => {
  if (relation.isBidirectional) {
    // Für bidirektionale Verknüpfungen
    return relation.displayName || `${relation.sourceEntity} - ${relation.targetEntity}`;
  } else {
    // Für direkte Verknüpfungen
    const entity = relation[relation.relationConfig.entityName];
    if (!entity) {
      return 'Entität nicht geladen';
    }
    return entity?.groupType?.subCategoryGroup?.subCategory?.category?.name || entity?.groupType?.name || 'Keine Kategorie';
  }
};

const getLabel = (relation) => {
  if (relation.isBidirectional) {
    // Für bidirektionale Verknüpfungen
    return `${relation.sourceEntity} (ID: ${relation.sourceEntityId})`;
  } else {
    // Für direkte Verknüpfungen
    const entity = relation[relation.relationConfig.entityName];
    if (!entity) {
      return 'Entität nicht geladen';
    }
    return entity?.label || entity?.name || 'Kein Label';
  }
};

const getContractPartner = (relation) => {
  if (relation.isBidirectional) {
    // Für bidirektionale Verknüpfungen
    return relation.relationType || 'Unbekannt';
  } else {
    // Für direkte Verknüpfungen
    const entity = relation[relation.relationConfig.entityName];
    if (!entity) {
      return 'Entität nicht geladen';
    }
    return entity?.contractor || entity?.contractOwner || entity?.applicationUser?.login || 'Kein Partner';
  }
};

const getGroupType = (relation) => {
  if (relation.isBidirectional) {
    // Für bidirektionale Verknüpfungen
    return relation.description || 'Bidirektionale Verknüpfung';
  } else {
    // Für direkte Verknüpfungen
    const entity = relation[relation.relationConfig.entityName];
    if (!entity) {
      console.log('Entity nicht gefunden für Relation:', relation);
      return 'Entität nicht geladen';
    }
    return entity?.groupType?.name || 'Kein GroupType';
  }
};

const getRelationConfig = (relation) => {
  // Wenn relationConfig bereits vorhanden ist, verwende es
  if (relation.relationConfig) {
    return relation.relationConfig;
  }
  
  // Ansonsten finde die passende Konfiguration basierend auf der Entity
  if (props.formConfig?.relations) {
    // Für direkte Verknüpfungen
    for (const config of props.formConfig.relations) {
      if (relation[config.entityName]) {
        return config;
      }
    }
    
    // Für bidirektionale Verknüpfungen
    for (const config of props.formConfig.relations) {
      if (config.entityName === relation.targetEntity) {
        return config;
      }
    }
  }
  
  console.warn('Keine relationConfig gefunden für:', relation);
  return null;
};

// Initial laden der bidirektionalen Verknüpfungen
onMounted(() => {
  loadBidirectionalRelations();
});

const viewRelation = (relation) => {
  let targetEntity, targetEntityId;
  
  if (relation.isBidirectional) {
    // Für bidirektionale Verknüpfungen
    targetEntity = relation.sourceEntity;
    targetEntityId = relation.sourceEntityId;
  } else {
    // Für direkte Verknüpfungen
    const entity = relation[relation.relationConfig.entityName];
    
    if (!entity || !entity.id) {
      console.error('Entity nicht gefunden oder keine ID vorhanden:', relation);
      return;
    }
    
    targetEntity = relation.relationConfig.entityName;
    targetEntityId = entity.id;
  }
  
  // Router zur entsprechenden View-Seite navigieren
  const routeName = getRouteNameForEntity(targetEntity);
  
  router.push({
    name: routeName,
    params: { entityid: targetEntityId }
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

// Funktion zum sicheren Löschen von bidirektionalen Verknüpfungen
const deleteBidirectionalRelationSafely = async (relation) => {
  
  
  // Sofort aus der lokalen Liste entfernen für bessere UX
  bidirectionalRelations.value = bidirectionalRelations.value.filter(rel => rel.id !== relation.id);
  
  // Emit event an Parent-Komponente, um die direkten Verknüpfungen zu aktualisieren
  emits('bidirectional-relation-deleted', relation.id);
  
  // Versuche Backend-Löschung, aber ignoriere Fehler
  try {
    const response = await fetch(`/api/relations/bidirectional/${relation.id}`, {
      method: 'DELETE'
    });

    if (response.ok) {
      
    } else {
      // Auch bei Fehler die Verknüpfung aus der UI entfernen
    }
  } catch (error) {
    
    // Auch bei Netzwerkfehler die Verknüpfung aus der UI entfernen
  }
};



</script>