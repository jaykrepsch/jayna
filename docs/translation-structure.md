# Übersetzungsstruktur für Dokument-Form-Definitionen

## Übersicht

Die Dokument-Form-Definitionen verwenden eine schlanke Struktur ohne redundante `label`-Felder. Die Übersetzungen werden zentral in den entsprechenden i18n-Dateien verwaltet.

## Struktur

### 1. Allgemeine Abschnitte (Standard für alle Dokumente)

**Übersetzungen in:** `src/main/resources/i18n/messages_de.properties`

#### Allgemein-Abschnitt
```json
{
  "title": "jaynaApp.baseDocument.general.title",
  "fields": [
    { "name": "title", "type": "text", "required": true },
    { "name": "description", "type": "textarea" },
    { "name": "entityState", "type": "dropdown", "enumName": "entityState" }
  ]
}
```

#### Dokumentdaten-Abschnitt
```json
{
  "title": "jaynaApp.baseDocument.documentData.title",
  "fields": [
    { "name": "documentNumber", "type": "text" },
    { "name": "customerNumber", "type": "text" },
    { "name": "partner", "type": "text" },
    { "name": "contractNumber", "type": "text" },
    { "name": "realEstateNumber", "type": "text" },
    { "name": "financeAccountNumber", "type": "text" },
    { "name": "mobilityNumber", "type": "text" },
    { "name": "status", "type": "dropdown", "enumName": "documentStatus" }
  ]
}
```

#### Weiteres-Abschnitt
```json
{
  "title": "jaynaApp.baseDocument.weiteres.title",
  "fields": [
    { "name": "tags", "type": "text" },
    { "name": "keywords", "type": "textarea" },
    { "name": "isPublic", "type": "checkbox" },
    { "name": "expiryDate", "type": "datetime" }
  ]
}
```

#### Dateiinformationen-Abschnitt
```json
{
  "title": "jaynaApp.baseDocument.fileInfo.title",
  "fields": [
    { "name": "fileName", "type": "text", "readonly": true },
    { "name": "fileType", "type": "text", "readonly": true },
    { "name": "fileSize", "type": "number", "readonly": true },
    { "name": "uploadDate", "type": "datetime", "readonly": true },
    { "name": "lastModified", "type": "datetime", "readonly": true }
  ]
}
```

### 2. Spezifische Abschnitte (je nach Dokumententyp)

**Übersetzungen in:** `src/main/webapp/i18n/de/document/i18n-{formName}.json`

#### Beispiel: SEPA-Mandat
```json
{
  "title": "jaynaApp.document-finance-insurance-banking-sepa-mandate.specificData.title",
  "i18nSource": "document-finance-insurance-banking-sepa-mandate",
  "fields": [
    { "name": "documentType", "type": "text", "required": true, "readonly": true },
    { "name": "mandateReference", "type": "text", "required": true }
  ]
}
```

## Übersetzungsschlüssel

### Allgemeine Felder (messages_de.properties)

```properties
# Abschnitt-Titel
jaynaApp.baseDocument.general.title=Allgemein
jaynaApp.baseDocument.documentData.title=Dokumentdaten
jaynaApp.baseDocument.weiteres.title=Weiteres
jaynaApp.baseDocument.fileInfo.title=Dateiinformationen
jaynaApp.baseDocument.relations.title=Verknüpfungen

# Feld-Labels
jaynaApp.document.title=Bezeichnung
jaynaApp.document.description=Beschreibung
jaynaApp.document.documentNumber=Dokumentennummer
jaynaApp.document.customerNumber=Kundennummer
jaynaApp.document.partner=Partner
jaynaApp.document.contractNumber=Vertragsnummer
jaynaApp.document.realEstateNumber=Immobiliennummer
jaynaApp.document.financeAccountNumber=Finanzkontonummer
jaynaApp.document.mobilityNumber=Mobilitätsnummer
jaynaApp.document.tags=Tags
jaynaApp.document.keywords=Schlüsselwörter
jaynaApp.document.isPublic=Öffentlich
jaynaApp.document.expiryDate=Ablaufdatum
jaynaApp.document.fileName=Dateiname
jaynaApp.document.fileType=Dateityp
jaynaApp.document.fileSize=Dateigröße
jaynaApp.document.uploadDate=Uploaddatum
jaynaApp.document.lastModified=Zuletzt geändert
```

### Spezifische Felder (i18n-{formName}.json)

#### Beispiel: SEPA-Mandat
```json
{
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.createTitle": "SEPA-Mandat erstellen",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.updateTitle": "SEPA-Mandat bearbeiten",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.viewTitle": "SEPA-Mandat anzeigen",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.specificData.title": "Mandatsdaten",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.creditorDetails.title": "Gläubiger-Details",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.debtorDetails.title": "Schuldner-Details",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.bankingDetails.title": "Bankdaten",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.legalDetails.title": "Rechtliche Details",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.amountDetails.title": "Betragsdetails",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.fields.documentType": "Dokumenttyp",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.fields.mandateReference": "Mandatsreferenz",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.fields.creditorName": "Name des Gläubigers",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.fields.creditorAddress": "Adresse des Gläubigers",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.fields.creditorPostalCode": "PLZ des Gläubigers",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.fields.creditorCity": "Ort des Gläubigers",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.fields.creditorIdentifier": "Gläubiger-Identifikationsnummer",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.fields.debtorName": "Name des Kontoinhabers",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.fields.debtorStreet": "Straße und Hausnummer",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.fields.debtorPostalCode": "PLZ",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.fields.debtorCity": "Ort",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.fields.bankName": "Kreditinstitut",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.fields.iban": "IBAN",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.fields.bic": "BIC",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.fields.refundPeriod": "Rückerstattungsfrist (Wochen)",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.fields.refundStartPoint": "Startpunkt für Rückerstattung",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.fields.authorizationText": "Autorisierungstext",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.fields.bankInstruction": "Bankanweisung",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.fields.amount": "Betrag (EUR)",
  "jaynaApp.document-finance-insurance-banking-sepa-mandate.fields.currency": "Währung"
}
```

## Vorteile der schlanken Struktur

1. **Weniger Redundanz**: Keine doppelten Übersetzungsschlüssel in den Form-Definitionen
2. **Zentrale Verwaltung**: Alle Übersetzungen an einem Ort
3. **Einfachere Wartung**: Änderungen nur in den i18n-Dateien
4. **Kleinere Dateien**: Form-Definitionen sind kompakter
5. **Bessere Performance**: Weniger Daten müssen geladen werden

## Reihenfolge der Abschnitte

1. **Allgemein** (Titel, Beschreibung, Status)
2. **Dokumentdaten** (Nummern, Partner, etc.)
3. **Spezifische Abschnitte** (je nach Dokumententyp)
4. **Weiteres** (Tags, Keywords, etc.)
5. **Dateiinformationen** (Dateiname, Größe, etc.)
6. **Verknüpfungen** (wird dynamisch hinzugefügt)
