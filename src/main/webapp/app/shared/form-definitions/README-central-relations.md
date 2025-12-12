# Zentrale Verknüpfungsstruktur

## Übersicht

Die zentrale Verknüpfungsstruktur ersetzt die redundanten bidirektionalen Verknüpfungen durch eine einzige, zentrale Definition aller möglichen Beziehungen zwischen Entitäten.

## Vorteile

### Vorher (Redundante Struktur)
```json
// In realestate-withdevelopment-farmland-farmland.json
{
  "relations": [
    {
      "entityName": "contact",
      "relationName": "realestateContacts"
    },
    {
      "entityName": "contract", 
      "relationName": "realestateContracts"
    }
  ]
}

// In contact-single-single-singlecontact.json
{
  "relations": [
    {
      "entityName": "realestate",
      "relationName": "contactRealestates"
    },
    {
      "entityName": "contract",
      "relationName": "contactContracts" 
    }
  ]
}
```

### Nachher (Zentrale Struktur)
```json
// central-relations.json
{
  "relations": [
    {
      "id": "contact-realestate",
      "sourceEntity": "contact",
      "targetEntity": "realestate", 
      "relationType": "OWNER",
      "displayName": "Kontakt - Immobilie",
      "description": "Verknüpfung zwischen Kontakten und Immobilien",
      "bidirectional": true,
      "sourceRelationName": "contactRealestates",
      "targetRelationName": "realestateContacts"
    }
  ]
}

// In Formulardefinitionen
{
  "relations": [
    {
      "entityName": "contact",
      "relationName": "realestateContacts",
      "relationConfig": {
        "id": "contact-realestate",
        "sourceEntity": "contact",
        "targetEntity": "realestate",
        "relationType": "OWNER",
        "displayName": "Kontakt - Immobilie",
        "description": "Verknüpfung zwischen Kontakten und Immobilien",
        "bidirectional": true
      }
    }
  ]
}
```

## Verknüpfungstypen

| Typ | Beschreibung | Beispiel |
|-----|-------------|----------|
| `OWNER` | Besitzer einer Entität | Kontakt besitzt Immobilie |
| `CONTRACTOR` | Vertragspartner | Kontakt ist Vertragspartner |
| `ACCOUNT_HOLDER` | Kontoinhaber | Kontakt ist Kontoinhaber |
| `COVERS` | Vertrag deckt ab | Vertrag deckt Immobilie ab |
| `PAYMENT_ACCOUNT` | Zahlungskonto | Vertrag nutzt Finanzkonto |
| `INSURES` | Versichert | Vertrag versichert Mobilität |
| `FINANCED_BY` | Finanziert durch | Immobilie finanziert durch Konto |
| `STORES` | Lagert | Immobilie lagert Mobilität |
| `FINANCES` | Finanziert | Konto finanziert Mobilität |
| `RELATED_TO` | Verwandt mit | Entität ist verwandt mit sich selbst |

## Entitätstypen

- `contact` - Kontakte
- `contract` - Verträge  
- `realestate` - Immobilien
- `financeaccount` - Finanzkonten
- `mobility` - Mobilitätsobjekte

## Verwendung

### 1. Service verwenden

```javascript
import centralRelationService from '@/services/central-relation.service.js';

// Alle Verknüpfungen für eine Entität
const relations = centralRelationService.getRelationsForEntity('realestate');

// Spezifische Verknüpfung
const relation = centralRelationService.getRelationConfig('contact', 'realestate');

// RelationName für eine Entität
const relationName = centralRelationService.getRelationName('realestate', 'contact');
```

### 2. Komponente verwenden

```vue
<template>
  <CentralRelationList 
    :form-config="formConfig"
    :data="data"
    :entity-type="entityType"
    @delete="handleDelete"
    @add-relationship="handleAdd"
  />
</template>

<script>
import CentralRelationList from '@/components/CentralRelationList.vue';

export default {
  components: {
    CentralRelationList
  }
};
</script>
```

## Migration

Die Migration wurde bereits durchgeführt. Alle Formulardefinitionen wurden automatisch auf die neue Struktur umgestellt.

### Migration Status

- ✅ Alle Formulardefinitionen migriert
- ✅ Zentrale Verknüpfungsdefinition erstellt
- ✅ Service für zentrale Verknüpfungen erstellt
- ✅ Neue Komponente erstellt
- ⏳ Frontend-Integration (in Arbeit)

## Nächste Schritte

1. **Frontend-Integration**: Ersetzen Sie die alte `RelationList.vue` durch `CentralRelationList.vue`
2. **API-Anpassungen**: Passen Sie die Backend-APIs an die neue Struktur an
3. **Tests**: Testen Sie die neue Verknüpfungsstruktur
4. **Dokumentation**: Aktualisieren Sie die Entwicklerdokumentation

## Dateistruktur

```
src/main/webapp/app/shared/form-definitions/
├── central-relations.json          # Zentrale Verknüpfungsdefinition
├── contact/                        # Kontakt-Formulardefinitionen
├── contract/                       # Vertrag-Formulardefinitionen  
├── realestate/                     # Immobilien-Formulardefinitionen
├── financeaccount/                 # Finanzkonto-Formulardefinitionen
└── mobility/                       # Mobilität-Formulardefinitionen

src/main/webapp/app/services/
└── central-relation.service.js     # Service für zentrale Verknüpfungen

src/main/webapp/app/components/
└── CentralRelationList.vue         # Neue Komponente
```

## Vorteile der neuen Struktur

1. **Weniger Redundanz**: Jede Verknüpfung wird nur einmal definiert
2. **Bessere Konsistenz**: Zentrale Definition verhindert Inkonsistenzen
3. **Einfachere Wartung**: Änderungen nur an einer Stelle nötig
4. **Bessere Performance**: Weniger Daten, schnellere Verarbeitung
5. **Flexibilität**: Einfache Erweiterung um neue Verknüpfungstypen
6. **Validierung**: Automatische Validierung von Verknüpfungen
7. **Dokumentation**: Bessere Übersicht über alle möglichen Beziehungen

## Beispiele

### Verknüpfung hinzufügen

```javascript
// Automatische Validierung
const validation = centralRelationService.validateRelation('realestate', 'contact');
if (validation.isValid) {
  // Verknüpfung ist gültig
  const relationName = centralRelationService.getRelationName('realestate', 'contact');
  // relationName = "realestateContacts"
}
```

### Alle möglichen Verknüpfungen

```javascript
// Für eine Realestate-Entität
const possibleRelations = centralRelationService.getPossibleRelationsForEntity('realestate');
// Gibt alle möglichen Verknüpfungen zurück:
// - contact (realestateContacts)
// - contract (realestateContracts)  
// - financeaccount (realestateFinanceaccounts)
// - mobility (realestateMobilities)
``` 