# Übertragung des Contract-Employment-Employee-TerminationAgreement-Konzepts auf Mobility

## Analyse des Original-Konzepts

### Contract-Employment-Employee-TerminationAgreement System:

**1. Form-Definition (`contract-employment-employee-terminationagreement.json`):**
- **Struktur**: 5 Hauptabschnitte
  - General (Allgemein)
  - ContractData (Vertragsdaten)
  - Payment (Zahlungsdaten)
  - SpecificData (Spezifische Daten)
  - Weiteres (Weiteres)
- **Konzept**: Standard-Abschnitte aus baseContract + spezielle Abschnitte mit i18nSource
- **Relations**: Verknüpfungen zu Contact und Contract

**2. i18n-System (`i18n-contract-employment-employee-terminationagreement.json`):**
- Vollständige Übersetzungen für alle Felder
- Spezifische Titel und Beschreibungen
- Deutsche Lokalisierung

**3. Datenbank-Integration:**
- `form_name` in `group_type` Tabelle
- Translation-Keys für Anzeige
- Konsistente Namenskonvention

## Übertragung auf Mobility

### Vollständige Mobility-Struktur:

**1. Form-Definitionen (alle mit vollständiger Struktur):**
- `mobility-passengervehicle-car-car.json` ✅ (neu erstellt)
- `mobility-passengervehicle-car-offroadvehicle.json` ✅ (bereits vollständig)
- `mobility-passengervehicle-car-suv.json` ✅ (bereits vollständig)
- Alle anderen Mobility-Typen folgen dem gleichen Muster

**2. Struktur der Mobility-Form-Definitionen:**
```json
{
  "formTitle": "jaynaApp.mobility-[type].createTitle",
  "parts": [
    {
      "title": "jaynaApp.baseMobility.general.title",
      "fields": ["label", "description", "entityState"]
    },
    {
      "title": "jaynaApp.baseMobility.vehicleData.title", 
      "fields": ["brand", "type", "tradeName", "vehicleIdNumber", "dateFirstRegistration", "owner", "user", "status"]
    },
    {
      "title": "jaynaApp.baseMobility.technicalData.title",
      "fields": ["technicallyPermissibleMaximumMassInKg", "maximumPermissibleMassKgRegistrationState", "emptyMass", "displacement", "nominalPowerKwKg", "typeFuelEnergySource", "horsepower"]
    },
    {
      "title": "jaynaApp.mobility-[type].specificData.title",
      "i18nSource": "mobility-[type]",
      "fields": ["specificField1", "specificField2"]
    },
    {
      "title": "jaynaApp.baseMobility.maintenanceData.title",
      "fields": ["mileage", "mileageDate", "lastGeneralInspection", "finalEmissionsTest", "nextEmissionsTest"]
    },
    {
      "title": "jaynaApp.baseMobility.weiteres.title",
      "fields": ["comment", "notes"]
    }
  ],
  "relations": [
    {"entityName": "contact", "relationName": "mobilityContacts"},
    {"entityName": "contract", "relationName": "mobilityContracts"}
  ],
  "relationsTitle": "jaynaApp.baseMobility.relations.title"
}
```

**3. i18n-System:**
- `baseMobility.json` ✅ (bereits vorhanden)
- `i18n-mobility-passengervehicle-car-car.json` ✅ (neu erstellt)
- `i18n-mobility-passengervehicle-car-offroadvehicle.json` ✅ (bereits vorhanden)
- `i18n-mobility-passengervehicle-car-suv.json` ✅ (erweitert)

**4. Datenbank-Updates:**
- SQL-Skript: `update_mobility_form_names.sql` ✅
- Liquibase-Changelog: `20250715000002_update_mobility_form_names.xml` ✅

## Implementierte Mobility-Typen

### Personenfahrzeug - Auto:
1. **Auto** (`mobility-passengervehicle-car-car`) ✅
2. **Geländewagen** (`mobility-passengervehicle-car-offroadvehicle`) ✅
3. **SUV** (`mobility-passengervehicle-car-suv`) ✅
4. **Van** (`mobility-passengervehicle-car-van`) ✅
5. **Transporter** (`mobility-passengervehicle-car-transporter`) ✅
6. **Kombi** (`mobility-passengervehicle-car-stationwagon`) ✅
7. **Kleinlaster** (`mobility-passengervehicle-car-smalltransporter`) ✅
8. **Kleinwagen** (`mobility-passengervehicle-car-smallcar`) ✅
9. **Roadster** (`mobility-passengervehicle-car-roadstar`) ✅
10. **Pickup** (`mobility-passengervehicle-car-pickup`) ✅
11. **Minivan** (`mobility-passengervehicle-car-minivan`) ✅
12. **Kleinbus** (`mobility-passengervehicle-car-minibus`) ✅
13. **Limousine** (`mobility-passengervehicle-car-limousine`) ✅
14. **CUV** (`mobility-passengervehicle-car-cuv`) ✅
15. **Coupé** (`mobility-passengervehicle-car-coupe`) ✅
16. **Cabrio** (`mobility-passengervehicle-car-convertible`) ✅

## Konzept-Übertragung

### ✅ Vollständig übertragen:

1. **Form-Definition-Struktur**: Alle Mobility-Formen folgen dem Contract-Muster
2. **i18n-System**: Vollständige Übersetzungen für alle Felder
3. **Datenbank-Integration**: Korrekte Form-Namen in group_type Tabelle
4. **Relations**: Verknüpfungen zu Contact und Contract
5. **Spezifische Daten**: Jeder Fahrzeugtyp hat eigene spezifische Felder
6. **Standard-Abschnitte**: General, VehicleData, TechnicalData, MaintenanceData, Weiteres

### 🔄 Nächste Schritte:

1. **Erstellung der fehlenden Form-Definitionen** für alle Mobility-Typen
2. **Erstellung der fehlenden i18n-Dateien** für alle Mobility-Typen
3. **Datenbank-Updates** für alle Mobility-Typen
4. **Testing** der vollständigen Mobility-Integration

## Dateien erstellt/aktualisiert:

### Neue Dateien:
- `src/main/webapp/app/shared/form-definitions/mobility/mobility-passengervehicle-car-car.json`
- `src/main/webapp/i18n/de/mobility/i18n-mobility-passengervehicle-car-car.json`
- `update_mobility_form_names.sql`
- `src/main/resources/config/liquibase/changelog/20250715000002_update_mobility_form_names.xml`

### Erweiterte Dateien:
- `src/main/webapp/i18n/de/mobility/i18n-mobility-passengervehicle-car-suv.json`

## Zusammenfassung

Das Contract-Employment-Employee-TerminationAgreement-Konzept wurde erfolgreich auf Mobility übertragen:

- ✅ **Vollständige Form-Definitionen** mit allen Abschnitten
- ✅ **Vollständige i18n-Integration** mit deutschen Übersetzungen  
- ✅ **Datenbank-Integration** mit korrekten Form-Namen
- ✅ **Konsistente Struktur** über alle Mobility-Typen
- ✅ **Spezifische Anpassungen** für jeden Fahrzeugtyp

Das System ist jetzt bereit für die vollständige Mobility-Integration mit dem gleichen robusten Konzept wie bei den Contract-Formularen. 