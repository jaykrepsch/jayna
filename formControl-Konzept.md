# formControl-Konzept

## Übersicht
Das formControl-Konzept definiert eine standardisierte Struktur für Formulardefinitionen in der Jayna-Anwendung. Es basiert auf dem bestehenden BaseContract-System und erweitert es um spezielle Abschnitte mit eigenen Übersetzungen.

## Grundprinzipien

### 1. Namenskonventionen
- **Formulardefinitionen**: `contract-{category}-{subcategory}-{subcategorygroup}-{grouptype}.json` (z.B. `contract-employment-employee-fulltime.json`)
- **i18n-Dateien**: `i18n-contract-{category}-{subcategory}-{subcategorygroup}-{grouptype}.json` (z.B. `i18n-contract-employment-employee-fulltime.json`)
- **Übersetzungsschlüssel**: `jaynaApp.contract-{category}-{subcategory}-{subcategorygroup}-{grouptype}.{feld}`

#### WICHTIG: Korrekte englische Feldnamen verwenden
- **Dateinamen müssen die englischen Feldnamen aus der Datenbank verwenden**
- **NICHT die deutschen Bezeichnungen verwenden**
- **Beispiel**: `contract-service-broker-realestateagent.json` (nicht `contract-Dienstleistung-Makler-Immobilienmakler.json`)
- **Quelle**: Die englischen Feldnamen finden sich in der CSV-Datei `formulardefinitionen_und_auswahlmoeglichkeiten.csv` in den Spalten:
  - `SubCategory` (Art auf Englisch)
  - `SubCategoryGroup` (Gruppierung auf Englisch) 
  - `GroupType` (Sparte auf Englisch)

### 2. Abschnittsarten

#### Allgemeine Abschnitte (BaseContract)
- **Übersetzung**: Über baseContract-Übersetzungen
- **Titel-Format**: `jaynaApp.baseContract.{sectionName}.title`
- **Beispiele**: 
  - `jaynaApp.baseContract.general.title`
  - `jaynaApp.baseContract.contractData.title`
  - `jaynaApp.baseContract.payment.title`
  - `jaynaApp.baseContract.weiteres.title`
- **Kein i18nSource-Attribut**: Diese Abschnitte verwenden automatisch baseContract-Übersetzungen

#### Spezifische Abschnitte (Eigene i18n-Datei)
- **Übersetzung**: Über eigene i18n-Datei
- **Titel-Format**: `jaynaApp.{formName}.{sectionName}.title`
- **i18nSource-Attribut**: `"i18nSource": "{formName}"`
- **Beispiele**:
  - `jaynaApp.contractServiceBrokerRealestateagent.parties.title`
  - `jaynaApp.contractServiceBrokerRealestateagent.propertyData.title`
  - `jaynaApp.contractServiceBrokerRealestateagent.serviceData.title`
  - `jaynaApp.contractServiceBrokerRealestateagent.commission.title`

### 3. Strukturvorgaben

#### Standard-Abschnitte (aus BaseContract)
- **General**: Grunddaten (label, description, entityState)
- **Contract Data**: Vertragsdaten (contractor, contractOwner, etc.)
- **Payment**: Zahlungsinformationen
- **Weiteres**: Kommentare und Notizen
- **Verknüpfungen**: Überschrift wird in spezifischer i18n-Datei übersetzt

#### Spezielle Abschnitte
- Eigene Übersetzungen über `i18nSource`-Attribut
- Titel-Format: `jaynaApp.{formName}.{sectionName}.title`

### 4. Übersetzungssystem

#### Automatische Übersetzung
- **Allgemeine Abschnitte**: Übersetzungen aus BaseContract (`jaynaApp.baseContract.{sectionName}.title`)
- **Spezifische Abschnitte**: Übersetzungen aus eigener i18n-Datei (`jaynaApp.{formName}.{sectionName}.title`)
- **Verknüpfungen**: Überschrift aus spezifischer i18n-Datei (`jaynaApp.{formName}.relations-title`)

#### i18nSource-Attribut
- **Nur bei spezifischen Abschnitten verwenden**
- Verweist auf die eigene i18n-Datei
- Beispiel: `"i18nSource": "contract-service-broker-realestateagent"`

### 5. Felddefinitionen

#### Keine label-Attribute
- Felder haben keine eigenen `label`-Attribute
- Übersetzungen erfolgen automatisch über DetailViewBuilder
- Format: `jaynaApp.{formName}.{fieldName}`

#### Dropdown-Felder
- Verwenden `enumName` für Backend-Enums
- `boxLabel` für Platzhalter-Text

### 6. Verknüpfungen (Relations)
- Standard-Verknüpfungen aus BaseContract
- Überschrift der Verknüpfungen wird in spezifischer i18n-Datei übersetzt
- Format: `jaynaApp.{formName}.relations-title`

### 7. Dokumentation und Tracking
- **CSV-Datei**: `formulardefinitionen_und_auswahlmoeglichkeiten.csv` wird bei jeder Änderung aktualisiert
- **Dateinamen**: Werden in der CSV-Datei entsprechend der neuen Namenskonvention anpasst
- **Datum/Uhrzeit**: Wird nur in die "Letzte Änderung" Spalte der betroffenen Zeile eingetragen (Format: YYYY-MM-DD HH:MM:SS)

### 8. Qualitätskontrolle

#### Abschnittstitel überprüfen
- **Problem**: Abschnittstitel verwenden noch alte Namenskonvention
- **Beispiel falsch**: `jaynaApp.realEstateAgent.general.title`
- **Beispiel richtig**: `jaynaApp.baseContract.general.title` (für BaseContract-Abschnitte)
- **Prüfung**: Alle Abschnittstitel müssen korrekte Übersetzungsschlüssel verwenden
- **BaseContract-Abschnitte**: Verwenden `jaynaApp.baseContract.{sectionName}.title`
- **Spezifische Abschnitte**: Verwenden `jaynaApp.{formName}.{sectionName}.title`

#### Feldnamen überprüfen
- **Problem**: Feldnamen verwenden noch alte Namenskonvention
- **Beispiel falsch**: `jaynaApp.realEstateAgent.general.fields.label`
- **Beispiel richtig**: `jaynaApp.baseContract.general.fields.label` (für BaseContract-Felder)
- **Prüfung**: Alle Feldnamen müssen korrekte Übersetzungsschlüssel verwenden
- **BaseContract-Felder**: Verwenden `jaynaApp.baseContract.{sectionName}.fields.{fieldName}`
- **Spezifische Felder**: Verwenden `jaynaApp.{formName}.{sectionName}.fields.{fieldName}`

#### Übersetzungsschlüssel-Validierung
- **Vor dem Test**: Alle Übersetzungsschlüssel in i18n-Dateien prüfen
- **BaseContract-Abschnitte**: Müssen in `baseContract.json` existieren
- **Spezifische Abschnitte**: Müssen in eigener i18n-Datei existieren
- **Feldnamen**: Müssen in entsprechenden i18n-Dateien existieren
- **Fehlende Übersetzungen**: Sofort ergänzen, bevor getestet wird

### 9. WICHTIGE EINSCHRÄNKUNGEN

#### DetailViewBuilder-Komponente NICHT ändern
- **ABSOLUT VERBOTEN**: Änderungen an `src/main/webapp/app/components/DetailViewBuilder.vue`
- **Grund**: Die Komponente funktioniert bereits korrekt für alle bestehenden Formulare
- **Problem**: Änderungen führen zu Fehlern wie "getSectionName is not a function"
- **Regel**: Nur Formulardefinitionen und i18n-Dateien erstellen/anpassen, niemals die DetailViewBuilder-Komponente

#### Korrekte Abschnittstitel und Feldnamen
- **Abschnittstitel**: Müssen korrekte Übersetzungsschlüssel verwenden
  - Allgemeine Abschnitte: `jaynaApp.baseContract.{abschnitt}.title`
  - Spezifische Abschnitte: `jaynaApp.{formName}.{abschnitt}.title`
- **Feldnamen**: Müssen korrekte Übersetzungsschlüssel verwenden
  - Allgemeine Felder: `jaynaApp.baseContract.{abschnitt}.fields.{feldname}`
  - Spezifische Felder: `jaynaApp.{formName}.{abschnitt}.fields.{feldname}`
- **i18nSource**: Für spezifische Abschnitte muss `i18nSource` korrekt gesetzt sein

### 10. form_name Konsistenz-Regel

#### WICHTIG: form_name muss mit Dateiname übereinstimmen
- **Problem**: `form_name` in der Datenbank stimmt nicht mit dem tatsächlichen Dateinamen überein
- **Beispiel falsch**: `form_name = 'employment-contract'` aber Datei heißt `contract-employment-employee-fulltime.json`
- **Beispiel richtig**: `form_name = 'contract-employment-employee-fulltime'` und Datei heißt `contract-employment-employee-fulltime.json`
- **Regel**: Der `form_name` in der Datenbank muss exakt dem Dateinamen (ohne .json) entsprechen
- **Prüfung**: Vor jedem Test prüfen, ob `form_name` in der Datenbank korrekt ist
- **Korrektur**: Bei Inkonsistenz sofort in der Datenbank korrigieren

#### Systematische Prüfung aller form_name Werte
- **Vor Anwendung des Konzepts**: Alle `form_name` Werte in der `group_type` Tabelle prüfen
- **SQL-Abfrage**: `SELECT id, name, form_name FROM group_type WHERE form_name LIKE '%employment-contract%' OR form_name LIKE '%contract-employment%';`
- **Korrektur**: Alle falschen `form_name` Werte auf korrekte Namenskonvention setzen
- **Backend-Neustart**: Nach Korrekturen Backend neu starten, damit Änderungen wirksam werden

#### Automatische Erkennung von Inkonsistenzen
- **Fehlermeldung**: "Formulardefinition {form_name}.json nicht gefunden"
- **Ursache**: `form_name` in Datenbank stimmt nicht mit Dateiname überein
- **Lösung**: `form_name` in Datenbank korrigieren und Backend neu starten

## Beispiel-Struktur

```json
{
  "_comment": "formControl-Konzept: Allgemeine Abschnitte über baseContract, spezifische Abschnitte mit i18nSource",
  "formTitle": "Immobilienmaklervertrag",
  "relationsTitle": "jaynaApp.contract-service-broker-realestateagent.relations-title",
  "parts": [
    {
      "title": "jaynaApp.baseContract.general.title",
      "fields": [...]
    },
    {
      "title": "jaynaApp.baseContract.contractData.title",
      "fields": [...]
    },
    {
      "title": "jaynaApp.contractServiceBrokerRealestateagent.parties.title",
      "i18nSource": "contract-service-broker-realestateagent",
      "fields": [...]
    },
    {
      "title": "jaynaApp.baseContract.payment.title",
      "fields": [...]
    }
  ],
  "relations": [...]
}
```

## Implementierung

### 1. Neue Formulardefinition erstellen
1. **Korrekte englische Feldnamen aus CSV-Datei ermitteln**
2. JSON-Datei in `form-definitions/contract/` erstellen
3. **Allgemeine Abschnitte**: Verwenden `jaynaApp.baseContract.{sectionName}.title` (kein i18nSource)
4. **Spezifische Abschnitte**: Verwenden `jaynaApp.{formName}.{sectionName}.title` mit `i18nSource`
5. `relationsTitle` auf spezifische i18n-Datei setzen

### 2. i18n-Datei erstellen
1. **Dateiname mit `i18n-` Präfix beginnen**
2. JSON-Datei in `i18n/de/contract/` erstellen
3. **Nur Übersetzungen für spezifische Abschnitte und Felder**
4. **Keine Übersetzungen für allgemeine Abschnitte** (kommen aus baseContract)
5. Übersetzung für Verknüpfungsüberschrift hinzufügen

### 3. Datenbank-Update
1. Liquibase-Changelog für neue Formulardefinition
2. `form_name` in entsprechender Tabelle setzen

### 4. Dokumentation aktualisieren
1. CSV-Datei `formulardefinitionen_und_auswahlmoeglichkeiten.csv` aktualisieren
2. Dateinamen entsprechend neuer Namenskonvention anpassen
3. Datum und Uhrzeit der Änderung nur in die "Letzte Änderung" Spalte der betroffenen Zeile eintragen (Format: YYYY-MM-DD HH:MM:SS)

## Wichtige Regeln

1. **Keine Category-Abschnitte**: Diese existieren nicht im BaseContract
2. **relationsTitle**: Wird in spezifischer i18n-Datei übersetzt (`jaynaApp.{formName}.relations-title`)
3. **Keine label-Attribute**: Felder werden automatisch übersetzt
4. **i18nSource nur bei spezifischen Abschnitten**: Allgemeine Abschnitte verwenden BaseContract-Übersetzungen
5. **Verknüpfungen**: Überschrift kommt aus spezifischer i18n-Datei, nicht aus BaseContract
6. **CSV-Dokumentation**: Bei jeder Änderung wird die CSV-Datei aktualisiert
7. **Datum/Uhrzeit-Tracking**: Wird nur in die "Letzte Änderung" Spalte der betroffenen Zeile eingetragen
8. **Korrekte englische Feldnamen**: Dateinamen müssen die englischen Feldnamen aus der Datenbank verwenden
9. **i18n-Datei Präfix**: i18n-Dateien müssen mit `i18n-` beginnen
10. **Allgemeine vs. Spezifische Abschnitte**: Klare Unterscheidung zwischen baseContract-Abschnitten und eigenen Abschnitten
11. **i18nSource nur bei spezifischen Abschnitten**: Allgemeine Abschnitte haben kein i18nSource-Attribut
12. **Qualitätskontrolle**: Abschnittstitel und Feldnamen müssen korrekte Übersetzungsschlüssel verwenden
13. **Übersetzungsschlüssel-Validierung**: Vor dem Test alle Übersetzungsschlüssel prüfen
14. **form_name Konsistenz**: form_name in Datenbank muss exakt mit Dateiname übereinstimmen
15. **Systematische form_name Prüfung**: Vor jedem Test alle form_name Werte in der Datenbank prüfen

## WICHTIGE REGEL: Vollständige Konzept-Einhaltung
**ABSOLUT VERBOTEN**: Abweichungen vom Konzept. Jede Anwendung des Konzepts muss ALLE Regeln vollständig befolgen:
1. **Korrekte Dateinamen**: `contract-{category}-{subcategory}-{subcategorygroup}-{grouptype}.json` (ALLE 4 Ebenen!)
2. **Flache i18n-Struktur**: Komplett flach wie `jaynaApp.{dateiname}.{feld}` (keine verschachtelten Objekte!)
3. **Korrekte Übersetzungsschlüssel**: Für alle Abschnitte und Felder
4. **i18nSource nur bei spezifischen Abschnitten**: Allgemeine Abschnitte verwenden baseContract
5. **Verknüpfungsüberschrift**: Muss in spezifischer i18n-Datei übersetzt werden

## KRITISCHE FEHLER VERMEIDEN

### 1. Dateinamen-Regel: ALLE 4 Ebenen verwenden
**FALSCH**: `contract-service-cleaning.json` (nur 2 Ebenen)
**RICHTIG**: `contract-service-cleaning-cleaningcontract.json` (alle 4 Ebenen)
**Quelle**: CSV-Datei Spalten: category, subcategory, subcategorygroup, grouptype

### 2. i18n-Struktur-Regel: Komplett flach
**FALSCH**: Verschachtelte Objekte
```json
{
  "jaynaApp": {
    "contractServiceCleaning": {
      "propertyData": {
        "title": "Objektdaten"
      }
    }
  }
}
```

**RICHTIG**: Komplett flache Struktur
```json
{
  "jaynaApp.contract-service-cleaning-cleaningcontract.propertyData.title": "Objektdaten",
  "jaynaApp.contract-service-cleaning-cleaningcontract.propertyData.fields.propertyType": "Objekttyp"
}
```

### 3. Übersetzungsschlüssel-Regel: Dateiname mit Bindestrichen
**FALSCH**: `jaynaApp.contractServiceCleaning.propertyData.title`
**RICHTIG**: `jaynaApp.contract-service-cleaning-cleaningcontract.propertyData.title`

**WICHTIG**: Der Übersetzungsschlüssel muss exakt dem Dateinamen entsprechen (mit Bindestrichen), aber in der Formulardefinition ohne Bindestriche verwendet werden.
