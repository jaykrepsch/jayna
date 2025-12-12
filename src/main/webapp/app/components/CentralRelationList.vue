<template>
  <div class="central-relation-list">
    <div v-if="hasRelations" class="relations-section">
      <h3>{{ relationsTitle }}</h3>
      
      <div class="relations-grid">
        <div 
          v-for="relation in allRelations" 
          :key="`${relation.id}-${relation.relationConfig?.id}`"
          class="relation-card"
        >
          <div class="relation-header">
            <h4>{{ getDisplayName(relation) }}</h4>
            <span class="relation-type">{{ getRelationType(relation) }}</span>
          </div>
          
          <div class="relation-content">
            <div class="entity-info">
              <strong>{{ getEntityLabel(relation) }}</strong>
              <p>{{ getEntityDescription(relation) }}</p>
            </div>
            
            <div class="relation-actions">
              <button 
                @click="editRelation(relation)"
                class="btn btn-sm btn-primary"
              >
                Bearbeiten
              </button>
              <button 
                @click="deleteRelation(relation)"
                class="btn btn-sm btn-danger"
              >
                Löschen
              </button>
            </div>
          </div>
        </div>
      </div>
      
      <div class="add-relation-section">
        <button 
          @click="showAddRelationModal = true"
          class="btn btn-success"
        >
          Verknüpfung hinzufügen
        </button>
      </div>
    </div>
    
    <div v-else class="no-relations">
      <p>Keine Verknüpfungen vorhanden.</p>
      <button 
        @click="showAddRelationModal = true"
        class="btn btn-success"
      >
        Erste Verknüpfung hinzufügen
      </button>
    </div>
    
    <!-- Modal für neue Verknüpfung -->
    <div v-if="showAddRelationModal" class="modal-overlay">
      <div class="modal-content">
        <h3>Neue Verknüpfung hinzufügen</h3>
        
        <div class="form-group">
          <label>Entitätstyp:</label>
          <select v-model="newRelation.targetEntity" class="form-control">
            <option value="">Bitte wählen...</option>
            <option 
              v-for="entityType in availableEntityTypes" 
              :key="entityType"
              :value="entityType"
            >
              {{ getEntityTypeDisplayName(entityType) }}
            </option>
          </select>
        </div>
        
        <div class="form-group">
          <label>Verknüpfungstyp:</label>
          <select v-model="newRelation.relationType" class="form-control">
            <option value="">Bitte wählen...</option>
            <option 
              v-for="relationType in availableRelationTypes" 
              :key="relationType"
              :value="relationType"
            >
              {{ getRelationTypeDisplayName(relationType) }}
            </option>
          </select>
        </div>
        
        <div class="form-group">
          <label>Entität auswählen:</label>
          <select v-model="newRelation.entityId" class="form-control">
            <option value="">Bitte wählen...</option>
            <option 
              v-for="entity in availableEntities" 
              :key="entity.id"
              :value="entity.id"
            >
              {{ entity.label || entity.name }}
            </option>
          </select>
        </div>
        
        <div class="modal-actions">
          <button 
            @click="addRelation"
            :disabled="!isNewRelationValid"
            class="btn btn-primary"
          >
            Hinzufügen
          </button>
          <button 
            @click="showAddRelationModal = false"
            class="btn btn-secondary"
          >
            Abbrechen
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted, watch } from 'vue';
import centralRelationService from '@/services/central-relation.service.js';

export default {
  name: 'CentralRelationList',
  props: {
    formConfig: {
      type: Object,
      required: true
    },
    data: {
      type: Object,
      required: true
    },
    entityType: {
      type: String,
      required: true
    },
    entityId: {
      type: [String, Number],
      required: true
    }
  },
  emits: ['delete', 'add-relationship', 'update'],
  setup(props, { emit }) {
    const showAddRelationModal = ref(false);
    const newRelation = ref({
      targetEntity: '',
      relationType: '',
      entityId: ''
    });
    
    const availableEntities = ref([]);
    const bidirectionalRelations = ref([]);
    
    // Zentrale Verknüpfungsdefinition laden
    const centralConfig = centralRelationService.getCentralRelationsConfig();
    
    // Verfügbare Entitätstypen (ohne aktuelle Entität)
    const availableEntityTypes = computed(() => {
      return centralConfig.entityTypes.filter(type => type !== props.entityType);
    });
    
    // Verfügbare Verknüpfungstypen
    const availableRelationTypes = computed(() => {
      return centralConfig.relationTypes;
    });
    
    // Alle bidirektionalen Verknüpfungen laden
    const loadBidirectionalRelations = async () => {
      if (!props.entityId) return;
      
      try {
        // Hier würde normalerweise ein API-Call gemacht werden
        // um alle Verknüpfungen zu laden, die diese Entität als Ziel haben
        const response = await fetch(`/api/relations/bidirectional/${props.entityType}/${props.entityId}`);
        if (response.ok) {
          bidirectionalRelations.value = await response.json();
        }
      } catch (error) {
        console.error('Fehler beim Laden der bidirektionalen Verknüpfungen:', error);
        // Fallback: Simuliere bidirektionale Verknüpfungen
        bidirectionalRelations.value = simulateBidirectionalRelations();
      }
    };
    
    // Fallback für bidirektionale Verknüpfungen (für Demo-Zwecke)
    const simulateBidirectionalRelations = () => {
      const relations = [];
      const possibleRelations = centralRelationService.getPossibleRelationsForEntity(props.entityType);
      
      for (const relation of possibleRelations) {
        // Simuliere, dass es Verknüpfungen gibt
        if (Math.random() > 0.7) { // 30% Chance für Demo
          relations.push({
            id: `bidirectional-${relation.entityName}-${props.entityId}`,
            sourceEntity: relation.entityName,
            targetEntity: props.entityType,
            relationType: relation.relationConfig.relationType,
            sourceEntityId: Math.floor(Math.random() * 100) + 1,
            targetEntityId: props.entityId,
            relationConfig: relation.relationConfig,
            isBidirectional: true
          });
        }
      }
      
      return relations;
    };
    
    // Prüfen, ob Verknüpfungen vorhanden sind (inkl. bidirektionaler)
    const hasRelations = computed(() => {
      // Direkte Verknüpfungen
      if (props.formConfig?.relations) {
        for (const relation of props.formConfig.relations) {
          if (props.data[relation.relationName]?.length > 0) {
            return true;
          }
        }
      }
      
      // Bidirektionale Verknüpfungen
      return bidirectionalRelations.value.length > 0;
    });
    
    // Alle Verknüpfungen sammeln (direkte + bidirektionale)
    const allRelations = computed(() => {
      const relations = [];
      
      // Direkte Verknüpfungen aus der aktuellen Entität
      if (props.formConfig?.relations) {
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
      
      // Bidirektionale Verknüpfungen (von anderen Entitäten)
      for (const relation of bidirectionalRelations.value) {
        relations.push({
          ...relation,
          isDirect: false,
          isBidirectional: true
        });
      }
      
      return relations;
    });
    
    // Titel für Verknüpfungen
    const relationsTitle = computed(() => {
      return props.formConfig?.relationsTitle || 'Verknüpfungen';
    });
    
    // Hilfsfunktionen
    const getDisplayName = (relation) => {
      if (relation.isBidirectional) {
        // Für bidirektionale Verknüpfungen
        const relationConfig = centralRelationService.getRelationConfig(
          relation.sourceEntity, 
          relation.targetEntity
        );
        return relationConfig?.displayName || `${relation.sourceEntity} - ${relation.targetEntity}`;
      } else {
        // Für direkte Verknüpfungen
        const config = relation.relationConfig;
        if (config?.relationConfig?.displayName) {
          return config.relationConfig.displayName;
        }
        return `${relation.relationConfig?.entityName || 'Unbekannt'} - ${props.entityType}`;
      }
    };
    
    const getRelationType = (relation) => {
      if (relation.isBidirectional) {
        return relation.relationType || 'UNKNOWN';
      } else {
        return relation.relationConfig?.relationConfig?.relationType || 'UNKNOWN';
      }
    };
    
    const getEntityLabel = (relation) => {
      if (relation.isBidirectional) {
        // Für bidirektionale Verknüpfungen: Zeige die Quell-Entität
        return `${relation.sourceEntity} (ID: ${relation.sourceEntityId})`;
      } else {
        // Für direkte Verknüpfungen
        const entity = relation[relation.relationConfig.entityName];
        return entity?.label || entity?.name || 'Unbekannt';
      }
    };
    
    const getEntityDescription = (relation) => {
      if (relation.isBidirectional) {
        return `Bidirektionale Verknüpfung von ${relation.sourceEntity}`;
      } else {
        const entity = relation[relation.relationConfig.entityName];
        return entity?.description || '';
      }
    };
    
    const getEntityTypeDisplayName = (entityType) => {
      const displayNames = {
        contact: 'Kontakt',
        contract: 'Vertrag',
        realestate: 'Immobilie',
        financeaccount: 'Finanzkonto',
        mobility: 'Mobilität'
      };
      return displayNames[entityType] || entityType;
    };
    
    const getRelationTypeDisplayName = (relationType) => {
      const displayNames = {
        OWNER: 'Besitzer',
        CONTRACTOR: 'Vertragspartner',
        ACCOUNT_HOLDER: 'Kontoinhaber',
        COVERS: 'Abdeckt',
        PAYMENT_ACCOUNT: 'Zahlungskonto',
        INSURES: 'Versichert',
        FINANCED_BY: 'Finanziert durch',
        STORES: 'Lagert',
        FINANCES: 'Finanziert',
        RELATED_TO: 'Verwandt mit'
      };
      return displayNames[relationType] || relationType;
    };
    
    // Validierung für neue Verknüpfung
    const isNewRelationValid = computed(() => {
      return newRelation.value.targetEntity && 
             newRelation.value.relationType && 
             newRelation.value.entityId;
    });
    
         // Methoden
     const editRelation = (relation) => {
       emit('edit', relation);
     };
    
    const deleteRelation = (relation) => {
      emit('delete', relation);
    };
    
    const addRelation = () => {
      if (!isNewRelationValid.value) return;
      
      const relationConfig = centralRelationService.getRelationConfig(
        props.entityType, 
        newRelation.value.targetEntity
      );
      
      if (relationConfig) {
        emit('add-relationship', {
          targetEntity: newRelation.value.targetEntity,
          relationType: newRelation.value.relationType,
          entityId: newRelation.value.entityId,
          relationConfig
        });
        
        // Modal schließen und Formular zurücksetzen
        showAddRelationModal.value = false;
        newRelation.value = {
          targetEntity: '',
          relationType: '',
          entityId: ''
        };
        
        // Bidirektionale Verknüpfungen neu laden
        loadBidirectionalRelations();
      }
    };
    
    // Entitäten für das Dropdown laden
    const loadAvailableEntities = async () => {
      if (newRelation.value.targetEntity) {
        // Hier würde normalerweise ein API-Call gemacht werden
        // um die verfügbaren Entitäten zu laden
        availableEntities.value = [
          { id: 1, label: 'Beispiel Entität 1', name: 'Entity 1' },
          { id: 2, label: 'Beispiel Entität 2', name: 'Entity 2' }
        ];
      }
    };
    
    // Watch für Änderungen im Target Entity
    watch(() => newRelation.value.targetEntity, () => {
      if (newRelation.value.targetEntity) {
        loadAvailableEntities();
      } else {
        availableEntities.value = [];
      }
    });
    
    // Watch für Änderungen in entityId
    watch(() => props.entityId, () => {
      if (props.entityId) {
        loadBidirectionalRelations();
      }
    });
    
    // Initial laden
    onMounted(() => {
      if (props.entityId) {
        loadBidirectionalRelations();
      }
    });
    
    return {
      showAddRelationModal,
      newRelation,
      availableEntities,
      availableEntityTypes,
      availableRelationTypes,
      hasRelations,
      allRelations,
      relationsTitle,
      isNewRelationValid,
      getDisplayName,
      getRelationType,
      getEntityLabel,
      getEntityDescription,
             getEntityTypeDisplayName,
       getRelationTypeDisplayName,
       editRelation,
       deleteRelation,
       addRelation
    };
  }
};
</script>

<style scoped>
.central-relation-list {
  margin: 1rem 0;
}

.relations-section {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 1rem;
}

.relations-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 1rem;
  margin: 1rem 0;
}

.relation-card {
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  padding: 1rem;
  background: #f9f9f9;
}

.relation-card.bidirectional {
  border-left: 4px solid #007bff;
  background: #f0f8ff;
}

.relation-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.5rem;
}

.relation-header h4 {
  margin: 0;
  color: #333;
}

.relation-type {
  background: #007bff;
  color: white;
  padding: 0.2rem 0.5rem;
  border-radius: 4px;
  font-size: 0.8rem;
}

.relation-type.bidirectional {
  background: #28a745;
}

.relation-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.entity-info {
  flex: 1;
}

.entity-info strong {
  color: #555;
}

.entity-info p {
  margin: 0.2rem 0 0 0;
  color: #666;
  font-size: 0.9rem;
}

.relation-actions {
  display: flex;
  gap: 0.5rem;
}

.add-relation-section {
  margin-top: 1rem;
  text-align: center;
}

.no-relations {
  text-align: center;
  padding: 2rem;
  color: #666;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 2rem;
  border-radius: 8px;
  min-width: 400px;
  max-width: 600px;
}

.form-group {
  margin-bottom: 1rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
}

.form-control {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.modal-actions {
  display: flex;
  gap: 1rem;
  justify-content: flex-end;
  margin-top: 1rem;
}

.btn {
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 0.9rem;
}

.btn-primary {
  background: #007bff;
  color: white;
}

.btn-secondary {
  background: #6c757d;
  color: white;
}

.btn-success {
  background: #28a745;
  color: white;
}

.btn-danger {
  background: #dc3545;
  color: white;
}

.btn-sm {
  padding: 0.25rem 0.5rem;
  font-size: 0.8rem;
}

.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}
</style> 