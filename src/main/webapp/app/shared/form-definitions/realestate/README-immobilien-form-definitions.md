# Immobilien-Form-Definitionen Konzept

## Übersicht

Das Immobilien-Form-Definition-Konzept folgt dem gleichen Muster wie die Contract-Form-Definitionen und bietet eine strukturierte, wiederverwendbare und erweiterbare Lösung für Immobilien-Formulare.

## Struktur

### 1. baseRealestate.json
Zentrale Datei mit Standard-Abschnitten für alle Immobilien-Form-Definitionen:

- **general**: Grundlegende Informationen (Label, Beschreibung, EntityState)
- **realestateData**: Immobilienspezifische Daten (Adresse, Größe, Wert, etc.)
- **ownership**: Eigentümerinformationen
- **financial**: Finanzielle Aspekte (Miete, Kosten, etc.)
- **weiteres**: Zusätzliche Informationen (Kommentare, Notizen)

### 2. Spezifische Immobilien-Definitionen
Jede Immobilienart hat ihre eigene JSON-Datei mit:
- Standard-Abschnitten aus `baseRealestate`
- Spezifischen Abschnitten mit `i18nSource`
- Zentrale Verknüpfungsstruktur

## Namenskonvention

Dateien folgen dem Muster:
```
realestate-{developmentType}-{category}-{subcategory}.json
```

Beispiele:
- `realestate-withdevelopment-residentialbuilding-detachedhouse.json`
- `realestate-withdevelopment-commercialbuilding-office.json`
- `realestate-withoutdevelopment-ground-buildingplot.json`

## Entwicklungstypen

### withDevelopment
Immobilien mit bestehender Bebauung:
- **residentialBuilding**: Wohngebäude
  - detachedHouse (Einfamilienhaus)
  - semidetachedHouse (Doppelhaus)
  - terracedHouse (Reihenhaus)
  - apartmentBuilding (Mehrfamilienhaus)
  - etc.
- **commercialBuilding**: Gewerbegebäude
  - office (Büro)
  - retail (Einzelhandel)
  - warehouse (Lager)
  - etc.
- **farmland**: Landwirtschaft
  - farmland (Ackerland)
  - forest (Wald)
  - pasture (Weide)
  - etc.

### withoutDevelopment
Unbebaute Grundstücke:
- **ground**: Grundstücke
  - buildingPlot (Bauland)
  - meadow (Wiese)
  - etc.

## Felder-Typen

### Standard-Felder (baseRealestate)
- `propertyNumber`: Immobiliennummer
- `acquisitionDate`: Erwerbsdatum
- `propertyType`: Immobilientyp (Enum)
- `address`, `postalCode`, `city`, `country`: Adressdaten
- `purchasePrice`, `currentValue`: Wertangaben
- `squareMeters`, `rooms`: Größenangaben
- `constructionYear`: Baujahr
- `energyEfficiencyClass`: Energieeffizienzklasse

### Spezifische Felder
Jede Immobilienart hat ihre eigenen spezifischen Felder:

#### Wohngebäude (detachedHouse)
- `houseType`: Haustyp
- `plotSize`: Grundstücksgröße
- `gardenSize`: Gartengröße
- `basement`, `attic`, `garage`: Ausstattung
- `parkingSpaces`: Stellplätze
- `heatingType`: Heizungstyp
- `renovationYear`: Renovierungsjahr

#### Bürogebäude (office)
- `officeType`: Bürotyp
- `floorNumber`, `totalFloors`: Stockwerke
- `officeSpaces`, `meetingRooms`: Räume
- `elevator`, `airConditioning`, `securitySystem`: Ausstattung
- `accessibility`: Barrierefreiheit
- `businessHours`: Geschäftszeiten
- `tenantType`: Mieterart

#### Bauland (buildingPlot)
- `plotType`: Grundstückstyp
- `plotSize`, `buildableArea`: Größenangaben
- `zoningType`: Bebauungsart
- `buildingHeight`, `floorAreaRatio`: Bebauungsvorgaben
- `accessRoad`, `utilities`: Infrastruktur
- `waterConnection`, `electricityConnection`, `sewageConnection`: Anschlüsse
- `soilQuality`: Bodenqualität
- `developmentCosts`: Erschließungskosten

## Verknüpfungen (Relations)

Alle Immobilien-Form-Definitionen verwenden die gleiche zentrale Verknüpfungsstruktur:

- **contact**: Kontakte (Eigentümer, Mieter, etc.)
- **contract**: Verträge (Kauf, Miete, Versicherung, etc.)
- **financeaccount**: Finanzkonten (Finanzierung, etc.)
- **mobility**: Mobilitätsobjekte (Fahrzeuge, etc.)
- **realestate**: Verwandte Immobilien

## i18n-Integration

### Übersetzungsschlüssel
Folgen dem Muster:
```
jaynaApp.realestate-{developmentType}-{category}-{subcategory}.{section}.{field}
```

Beispiele:
- `jaynaApp.realestate-withdevelopment-residentialbuilding-detachedhouse.specificData.title`
- `jaynaApp.realestate-withdevelopment-commercialbuilding-office.specificData.officeType`

### i18nSource
Spezifische Abschnitte verwenden `i18nSource` für die Übersetzung:
```json
{
  "title": "jaynaApp.realestate-withdevelopment-residentialbuilding-detachedhouse.specificData.title",
  "i18nSource": "realestate-withDevelopment-residentialBuilding-detachedHouse"
}
```

## Vorteile des Konzepts

1. **Wiederverwendbarkeit**: Standard-Abschnitte in `baseRealestate`
2. **Konsistenz**: Einheitliche Struktur für alle Immobilien
3. **Erweiterbarkeit**: Einfache Hinzufügung neuer Immobilienarten
4. **Wartbarkeit**: Zentrale Verwaltung von Standard-Feldern
5. **Flexibilität**: Spezifische Felder für jede Immobilienart
6. **Internationalisierung**: Vollständige i18n-Unterstützung

## Verwendung

1. **Neue Immobilienart hinzufügen**:
   - JSON-Datei nach Namenskonvention erstellen
   - Standard-Abschnitte aus `baseRealestate` referenzieren
   - Spezifische Felder definieren
   - i18n-Übersetzungen hinzufügen

2. **Bestehende Definition erweitern**:
   - Neue Felder im spezifischen Abschnitt hinzufügen
   - Entsprechende i18n-Übersetzungen ergänzen

3. **Standard-Felder ändern**:
   - Änderung in `baseRealestate.json`
   - Betrifft alle Immobilien-Definitionen

## Beispiel-Implementierung

```json
{
  "_comment": "formControl-Konzept: Standard-Abschnitte aus baseRealestate, spezielle Abschnitte mit i18nSource",
  "formTitle": "jaynaApp.realestate-withdevelopment-residentialbuilding-detachedhouse.createTitle",
  "parts": [
    {
      "title": "jaynaApp.baseRealestate.general.title",
      "fields": [...]
    },
    {
      "title": "jaynaApp.baseRealestate.realestateData.title",
      "fields": [...]
    },
    {
      "title": "jaynaApp.realestate-withdevelopment-residentialbuilding-detachedhouse.specificData.title",
      "i18nSource": "realestate-withDevelopment-residentialBuilding-detachedHouse",
      "fields": [...]
    }
  ],
  "relations": [...],
  "relationsTitle": "jaynaApp.baseRealestate.relations.title"
}
```
