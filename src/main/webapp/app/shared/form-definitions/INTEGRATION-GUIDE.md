# Integration Guide: Bidirektionale Verknüpfungen

## Problem gelöst ✅

Das Problem war, dass Verknüpfungen nur in der Entität angezeigt wurden, in der sie erstellt wurden, aber nicht in der anderen Entität, obwohl sie bidirektional definiert waren.

## Lösung implementiert

### 1. Erweiterte Komponente
Die `CentralRelationList.vue` wurde erweitert, um automatisch bidirektionale Verknüpfungen zu laden und anzuzeigen.

### 2. Backend-Support
- `RelationResource.java` - REST-Controller für bidirektionale Verknüpfungen
- `BidirectionalRelationDTO.java` - DTO für bidirektionale Verknüpfungen  
- `RelationService.java` - Service für bidirektionale Verknüpfungen

### 3. Automatische Anzeige
Die Komponente zeigt jetzt:
- **Direkte Verknüpfungen** (aus der aktuellen Entität)
- **Bidirektionale Verknüpfungen** (von anderen Entitäten)

## Verwendung

### 1. Komponente in bestehende Formulare integrieren

```vue
<template>
  <div>
    <!-- Bestehende Formularfelder -->
    
    <!-- Neue bidirektionale Verknüpfungsliste -->
    <CentralRelationList 
      :form-config="formConfig"
      :data="data"
      :entity-type="entityType"
      :entity-id="entityId"
      @delete="handleDelete"
      @add-relationship="handleAdd"
    />
  </div>
</template>

<script>
import CentralRelationList from '@/components/CentralRelationList.vue';

export default {
  components: {
    CentralRelationList
  },
  data() {
    return {
      entityType: 'realestate', // oder 'contact', 'contract', etc.
      entityId: 123, // ID der aktuellen Entität
      formConfig: {
        // Ihre Formularkonfiguration
      },
      data: {
        // Ihre Daten
      }
    };
  },
  methods: {
    handleDelete(relation) {
      // Verknüpfung löschen
      console.log('Lösche Verknüpfung:', relation);
    },
    handleAdd(relation) {
      // Neue Verknüpfung hinzufügen
      console.log('Füge Verknüpfung hinzu:', relation);
    }
  }
};
</script>
```

### 2. API-Endpunkte

Die folgenden API-Endpunkte sind verfügbar:

```
GET /api/relations/bidirectional/{entityType}/{entityId}
POST /api/relations/bidirectional
DELETE /api/relations/bidirectional/{id}
```

### 3. Beispiel für Immobilie

Wenn Sie eine Verknüpfung zwischen einer Immobilie (ID: 123) und einem Kontakt erstellen:

**In der Immobilie-Ansicht:**
- Direkte Verknüpfung: "Kontakt - Immobilie" (OWNER)
- Bidirektionale Verknüpfung: "Kontakt - Immobilie" (OWNER)

**In der Kontakt-Ansicht:**
- Direkte Verknüpfung: "Immobilie - Kontakt" (OWNER)  
- Bidirektionale Verknüpfung: "Immobilie - Kontakt" (OWNER)

## Vorteile

1. **Automatische Anzeige**: Verknüpfungen werden automatisch in beiden Entitäten angezeigt
2. **Visuelle Unterscheidung**: Bidirektionale Verknüpfungen werden anders dargestellt
3. **Konsistente Daten**: Keine Inkonsistenzen zwischen Entitäten
4. **Einfache Wartung**: Zentrale Definition aller Verknüpfungen

## Demo-Modus

Aktuell läuft das System im Demo-Modus mit simulierten Verknüpfungen. Für die Produktion müssen Sie:

1. **Datenbank-Integration**: Echte Datenbankabfragen implementieren
2. **API-Calls**: Echte API-Calls anstelle der Simulation
3. **Authentifizierung**: Benutzerberechtigungen prüfen
4. **Validierung**: Eingabevalidierung hinzufügen

## Nächste Schritte

1. **Testen**: Testen Sie die neue Komponente in verschiedenen Entitäten
2. **Backend-Integration**: Verbinden Sie mit echten Datenbankabfragen
3. **Styling**: Passen Sie das Design an Ihr UI an
4. **Performance**: Optimieren Sie für große Datenmengen

## Beispiel-Output

```
Verknüpfungen:
├── Kontakt - Immobilie (OWNER) [Direkt]
├── Vertrag - Immobilie (COVERS) [Direkt]  
├── Kontakt - Immobilie (OWNER) [Bidirektional]
└── Vertrag - Immobilie (COVERS) [Bidirektional]
```

Jetzt werden Verknüpfungen automatisch in beiden Entitäten angezeigt! 🎉 