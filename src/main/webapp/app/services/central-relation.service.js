import centralRelationsConfig from '@/shared/form-definitions/central-relations.json';

class CentralRelationService {
  constructor() {
    this.relationsConfig = centralRelationsConfig;
  }

  /**
   * Lädt die zentrale Verknüpfungsdefinition
   */
  getCentralRelationsConfig() {
    return this.relationsConfig;
  }

  /**
   * Gibt alle Verknüpfungen für eine bestimmte Entität zurück
   */
  getRelationsForEntity(entityType) {
    return this.relationsConfig.relations.filter(relation => 
      relation.sourceEntity === entityType || relation.targetEntity === entityType
    );
  }

  /**
   * Gibt die Verknüpfungskonfiguration für eine spezifische Beziehung zurück
   */
  getRelationConfig(sourceEntity, targetEntity) {
    return this.relationsConfig.relations.find(relation => 
      (relation.sourceEntity === sourceEntity && relation.targetEntity === targetEntity) ||
      (relation.sourceEntity === targetEntity && relation.targetEntity === sourceEntity)
    );
  }

  /**
   * Gibt den korrekten relationName für eine Entität zurück
   */
  getRelationName(sourceEntity, targetEntity) {
    const relation = this.getRelationConfig(sourceEntity, targetEntity);
    if (!relation) return null;

    if (relation.sourceEntity === sourceEntity) {
      return relation.sourceRelationName;
    } else {
      return relation.targetRelationName;
    }
  }

  /**
   * Gibt alle verfügbaren Verknüpfungstypen zurück
   */
  getAvailableRelationTypes() {
    return this.relationsConfig.relationTypes;
  }

  /**
   * Gibt alle Entitätstypen zurück
   */
  getAvailableEntityTypes() {
    return this.relationsConfig.entityTypes;
  }

  /**
   * Prüft, ob eine Verknüpfung bidirektional ist
   */
  isBidirectional(sourceEntity, targetEntity) {
    const relation = this.getRelationConfig(sourceEntity, targetEntity);
    return relation ? relation.bidirectional : false;
  }

  /**
   * Gibt die Anzeigenamen für eine Verknüpfung zurück
   */
  getDisplayName(sourceEntity, targetEntity) {
    const relation = this.getRelationConfig(sourceEntity, targetEntity);
    return relation ? relation.displayName : null;
  }

  /**
   * Gibt die Beschreibung für eine Verknüpfung zurück
   */
  getDescription(sourceEntity, targetEntity) {
    const relation = this.getRelationConfig(sourceEntity, targetEntity);
    return relation ? relation.description : null;
  }

  /**
   * Konvertiert die alte Verknüpfungsstruktur in die neue zentrale Struktur
   */
  convertLegacyRelations(legacyRelations, entityType) {
    const convertedRelations = [];
    
    for (const legacyRelation of legacyRelations) {
      const relationConfig = this.getRelationConfig(entityType, legacyRelation.entityName);
      if (relationConfig) {
        convertedRelations.push({
          ...legacyRelation,
          relationConfig,
          displayName: relationConfig.displayName,
          description: relationConfig.description,
          bidirectional: relationConfig.bidirectional
        });
      }
    }
    
    return convertedRelations;
  }

  /**
   * Gibt alle möglichen Verknüpfungen für eine Entität zurück
   */
  getPossibleRelationsForEntity(entityType) {
    const relations = [];
    
    for (const targetEntity of this.relationsConfig.entityTypes) {
      if (targetEntity !== entityType) {
        const relationConfig = this.getRelationConfig(entityType, targetEntity);
        if (relationConfig) {
          relations.push({
            entityName: targetEntity,
            relationName: this.getRelationName(entityType, targetEntity),
            relationConfig,
            displayName: relationConfig.displayName,
            description: relationConfig.description
          });
        }
      }
    }
    
    return relations;
  }

  /**
   * Validiert eine Verknüpfung
   */
  validateRelation(sourceEntity, targetEntity) {
    const relation = this.getRelationConfig(sourceEntity, targetEntity);
    return {
      isValid: !!relation,
      relation,
      error: relation ? null : `Keine gültige Verknüpfung zwischen ${sourceEntity} und ${targetEntity}`
    };
  }
}

// Singleton-Instanz erstellen
const centralRelationService = new CentralRelationService();

export default centralRelationService; 