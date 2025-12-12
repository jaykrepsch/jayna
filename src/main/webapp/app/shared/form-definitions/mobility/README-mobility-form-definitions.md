# Mobilitäts-Form-Definitions-Konzept

## Übersicht

Das Mobilitäts-Form-Definitions-Konzept folgt dem gleichen Muster wie das Contract-, Real Estate- und Finance Account-Konzept. Es verwendet Standard-Abschnitte aus `baseMobility.json` und spezifische Abschnitte für jeden Mobilitätstyp.

## Struktur

### Standard-Abschnitte (baseMobility.json)

- **general**: Grundlegende Informationen (Bezeichnung, Beschreibung, Status)
- **vehicleData**: Fahrzeugdaten (Marke, Modell, Baujahr, Kennzeichen, etc.)
- **ownership**: Eigentumsdaten (Eigentümer, Kaufdatum, Kaufpreis, etc.)
- **financial**: Finanzdaten (monatliche Kosten, Kraftstoffkosten, etc.)
- **weiteres**: Zusätzliche Informationen (Bemerkungen, Notizen)

### Spezifische Abschnitte

Jeder Mobilitätstyp hat spezifische Felder, die in separaten Abschnitten definiert werden.

## Namenskonventionen

### Form-Definition-Dateien

```
mobility-{sub-category}-{sub-category-group}-{group-type}.json
```

Beispiele:
- `mobility-passengervehicle-car-car.json`
- `mobility-passengervehicle-motorcycle-naked.json`
- `mobility-bike-bike-mtb.json`

### i18n-Dateien

```
i18n-mobility-{sub-category}-{sub-category-group}-{group-type}.json
```

### i18nSource-Werte

Die `i18nSource`-Werte in den Form-Definitionen entsprechen den Dateinamen der i18n-Dateien (ohne `i18n-` Präfix).

## Mobilitäts-Typen

### Personenfahrzeuge (passengervehicle)
- **Auto** (car)
- **Motorrad** (motorcycle)
- **Quad** (quad)

### Fahrräder (bike)
- **Standard-Fahrrad** (bike)
- **E-Bike** (ebike)
- **Pedelec** (pedelec)

### Boote (boat)
- **Motorboot** (motorized)
- **Segelboot** (sailing)
- **Hybrid** (hybrid)

### Nutzfahrzeuge (commercialvehicle)
- **Anhänger** (trailer)
- **Wohnwagen** (caravan)
- **Kastenwagen** (box)

### Landwirtschaft (agriculture)
- **Traktor** (tractor)

## Feldtypen

### Standard-Feldtypen
- `text` - Textfelder
- `number` - Zahlenfelder
- `date` - Datumsfelder
- `textarea` - Mehrzeilige Textfelder
- `checkbox` - Checkbox-Felder
- `dropdown` - Dropdown-Menüs mit Enum-Werten

### Enum-Felder
- `vehicleType` - Fahrzeugtypen
- `fuelType` - Kraftstoffarten
- `carType` - Auto-Typen
- `transmission` - Getriebearten
- `entityState` - Status-Werte

## Verknüpfungen (Relations)

Alle Mobilitäts-Form-Definitionen verwenden die zentrale Verknüpfungsstruktur:

- **Kontakt** - Fahrzeugeigentümer
- **Vertrag** - Versicherung
- **Immobilie** - Lagerung
- **Finanzkonto** - Finanzierung
- **Mobilität** - Verwandte Fahrzeuge

## i18n-Integration

### Struktur
```
src/main/webapp/i18n/de/mobility/
├── baseMobility.json          # Standard-Übersetzungen
├── enums.json                 # Enum-Übersetzungen
├── i18n-mobility-*.json       # Spezifische Übersetzungen
└── ...
```

### Übersetzungsschlüssel
- `jaynaApp.mobility-{type}.createTitle` - Erstellen-Titel
- `jaynaApp.mobility-{type}.updateTitle` - Bearbeiten-Titel
- `jaynaApp.mobility-{type}.viewTitle` - Anzeigen-Titel
- `jaynaApp.mobility-{type}.specificData.title` - Spezifischer Abschnitt
- `jaynaApp.mobility-{type}.specificData.fields.{fieldName}` - Feldnamen

## Entwicklung

### Neue Mobilitäts-Typen hinzufügen

1. **Form-Definition erstellen**:
   - Datei: `mobility-{sub-category}-{sub-category-group}-{group-type}.json`
   - Standard-Abschnitte aus baseMobility referenzieren
   - Spezifische Felder definieren

2. **i18n-Datei erstellen**:
   - Datei: `i18n-mobility-{sub-category}-{sub-category-group}-{group-type}.json`
   - Übersetzungen für Titel und spezifische Felder

3. **Enum-Werte hinzufügen** (falls erforderlich):
   - Neue Enum-Werte in `enums.json` definieren

### Best Practices

- Verwende immer die Standard-Abschnitte aus baseMobility
- Halte spezifische Felder minimal und relevant
- Verwende konsistente Namenskonventionen
- Dokumentiere neue Felder und Enum-Werte
- Teste die Form-Definitionen vor dem Deployment
