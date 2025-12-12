# Form-Name-Generator

## Übersicht

Der `form-name-generator.js` Composable bietet eine generische Lösung zur automatischen Generierung von Form-Namen basierend auf der Datenbank-Hierarchie. Er ersetzt die spezifischen Mapping-Tabellen in den Common-Komponenten durch eine intelligente, skalierbare Lösung.

## Funktionen

### `getCorrectFormName(groupType, entityName)`

Die Hauptfunktion, die automatisch den korrekten Form-Namen bestimmt:

1. **Primäre Quelle**: Verwendet `groupType.formName` aus der Datenbank
2. **Hierarchie-Generierung**: Generiert Form-Namen aus der Kategorie-Hierarchie
3. **Validierung**: Prüft, ob die Form-Definition existiert
4. **Fallback-Mechanismus**: Findet ähnliche oder Standard-Formulare

### `generateFormNameFromHierarchy(category, subCategory, subCategoryGroup, groupType)`

Generiert Form-Namen aus der Hierarchie-Struktur:
- Verwendet `translationKey` für englische Namen
- Folgt dem Schema: `{entity}-{subcategory}-{subcategorygroup}-{grouptype}`
- Normalisiert Namen für Datei-Konventionen

### `validateFormDefinition(formName, entityName)`

Validiert, ob eine Form-Definition existiert.

### `getAvailableFormNames(entityName)`

Gibt alle verfügbaren Form-Namen für eine Entity zurück.

## Verwendung

### In Common-Komponenten

```javascript
import { getCorrectFormName } from '@/composables/form-name-generator';

// In onMounted oder async Funktion
const formName = await getCorrectFormName(groupType, entityName);
```

### Beispiel-Integration

```javascript
// Vorher: Spezifische Mapping-Tabelle
const getCorrectFormName = (groupType, entityName) => {
  if (entityName === 'financeaccount') {
    if (translationKey.includes('apple-pay')) return 'financeaccount-systems-paymentsystems-applepay';
    // ... viele weitere spezifische Mappings
  }
};

// Nachher: Generische Lösung
const formName = await getCorrectFormName(groupType, entityName);
```

## Vorteile

### 1. Skalierbarkeit
- Keine manuellen Mapping-Tabellen erforderlich
- Automatische Unterstützung neuer Form-Definitionen
- Einheitliche Logik für alle Entities

### 2. Wartbarkeit
- Zentrale Logik in einem Composable
- Weniger Code-Duplikation
- Einfachere Updates und Bugfixes

### 3. Robustheit
- Mehrere Fallback-Mechanismen
- Validierung verfügbarer Form-Definitionen
- Intelligente Ähnlichkeitssuche

### 4. Performance
- Caching verfügbarer Form-Definitionen
- Effiziente Validierung
- Minimale API-Aufrufe

## Fallback-Mechanismus

1. **Datenbank formName**: Verwendet `groupType.formName` wenn verfügbar
2. **Hierarchie-Generierung**: Generiert aus Kategorie-Hierarchie
3. **Exakte Übereinstimmung**: Prüft, ob generierter Name existiert
4. **Ähnlichkeitssuche**: Findet ähnliche Form-Namen
5. **Standard-Formular**: Verwendet erste verfügbare Form

## Namenskonventionen

### Form-Definition-Dateien
```
{entity}-{subcategory}-{subcategorygroup}-{grouptype}.json
```

Beispiele:
- `contract-employment-employee-fulltime.json`
- `mobility-passengervehicle-car-car.json`
- `financeaccount-bank-bankaccount-checkingaccount.json`

### Translation-Keys
Verwendet `translationKey` aus der Datenbank für englische Namen:
- `jaynaApp.contract.group-type.full-time` → `fulltime`
- `jaynaApp.mobility.group-type.car` → `car`

## Migration

### Von spezifischen Mappings

1. **Entfernen**: Spezifische Mapping-Tabellen aus Common-Komponenten
2. **Importieren**: `getCorrectFormName` aus dem Composable
3. **Ersetzen**: Aufrufe mit async/await
4. **Testen**: Validierung der generierten Form-Namen

### Beispiel-Migration

```javascript
// Vorher in common-update.vue
const getCorrectFormName = (groupType, entityName) => {
  // 100+ Zeilen spezifischer Mappings
};

// Nachher
import { getCorrectFormName } from '@/composables/form-name-generator';
const formName = await getCorrectFormName(groupType, entityName);
```

## Fehlerbehandlung

### Logging
- Warnungen bei nicht gefundenen Form-Definitionen
- Fallback-Entscheidungen werden protokolliert
- Debug-Informationen für Entwicklung

### Fehlerszenarien
1. **Form-Definition nicht gefunden**: Verwendet ähnliche oder Standard-Form
2. **Ungültige Hierarchie**: Fallback auf Datenbank formName
3. **Keine verfügbaren Formen**: Null-Rückgabe mit Warnung

## Zukünftige Erweiterungen

### Mögliche Verbesserungen
1. **Intelligente Ähnlichkeitssuche**: Fuzzy-Matching für Form-Namen
2. **Konfigurierbare Fallbacks**: Benutzerdefinierte Standard-Formulare
3. **Performance-Optimierung**: Lazy-Loading von Form-Definitionen
4. **Validierung**: Erweiterte Validierung der Form-Struktur

### Integration
- Automatische Generierung fehlender Form-Definitionen
- Integration mit Build-Prozess
- Unit-Tests für alle Funktionen
