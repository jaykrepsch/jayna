# Finanzkonto-Form-Definition-Konzept

## Übersicht

Das Finanzkonto-Form-Definition-Konzept folgt dem gleichen Muster wie die Contract- und Immobilien-Form-Definitionen und bietet eine strukturierte, wiederverwendbare Lösung für alle Finanzkonto-Typen.

## Struktur

### Standard-Abschnitte (baseFinanceaccount)

Alle Finanzkonto-Form-Definitionen verwenden die folgenden Standard-Abschnitte:

1. **General** - Allgemeine Informationen
   - Bezeichnung (label)
   - Beschreibung (description)
   - Status (entityState)

2. **AccountData** - Kontodaten
   - Kontotyp (accountType)
   - Kontogeber (accountGiver)
   - Kontoeigentümer (accountOwner)
   - Kontobenutzer (accountUser)
   - Kontonummer (accountNumber)
   - IBAN
   - BIC
   - Kontostand (balance)
   - Währung (currency)
   - Eröffnungsdatum (openingDate)
   - Kreditlimit (creditLimit)
   - Verfügbarer Kredit (availableCredit)

3. **Bank** - Bankinformationen
   - Bankname (bankName)
   - Bankleitzahl (bankCode)
   - Bankadresse (bankAddress)
   - Bank-Telefon (bankPhone)
   - Bank-E-Mail (bankEmail)
   - Bank-Website (bankWebsite)

4. **Financial** - Finanzielle Details
   - Zinssatz (interestRate)
   - Dispo-Limit (overdraftLimit)
   - Monatliche Gebühr (monthlyFee)
   - Jahresgebühr (annualFee)
   - Transaktionsgebühr (transactionFee)
   - Cashback-Rate (cashbackRate)
   - Bonuspunkte (rewardPoints)

5. **Security** - Sicherheit
   - PIN-Code (pinCode)
   - Sicherheitsfragen (securityQuestions)
   - Zwei-Faktor-Authentifizierung (twoFactorAuth)
   - Betrugsschutz (fraudProtection)

6. **Weiteres** - Zusätzliche Informationen
   - Bemerkungen (comment)
   - Notizen (notes)

### Spezifische Abschnitte

Jeder Finanzkonto-Typ hat spezifische Felder, die in separaten Abschnitten definiert werden:

- **Girokonto**: checkingType, debitCard, creditCard, onlineBanking, etc.
- **Sparkonto**: savingsType, interestCalculation, withdrawalLimit, etc.
- **Kreditkarte**: creditCardType, creditLimit, cashbackRate, etc.
- **Bezahlsysteme**: paymentSystemType, integrationType, etc.

## Namenskonventionen

### Form-Definition-Dateien

```
financeaccount-{sub-category}-{sub-category-group}-{group-type}.json
```

Beispiele:
- `financeaccount-bank-bankaccount-checkingaccount.json`
- `financeaccount-credit-creditcard-charge.json`
- `financeaccount-systems-paymentsystems-paypal.json`

### i18n-Dateien

```
i18n-financeaccount-{sub-category}-{sub-category-group}-{group-type}.json
```

### i18nSource-Werte

Die `i18nSource`-Werte in den Form-Definitionen entsprechen den Dateinamen der i18n-Dateien (ohne `i18n-` Präfix).

## Finanzkonto-Typen

### Bank-Konten (bank)
- **Girokonto** (checkingaccount)
- **Sparkonto** (savingaccount)

### Kreditkarten (credit)
- **Charge Card** (charge)
- **Debit Card** (debit)
- **Revolving Credit** (revolving)
- **Prepaid** (prepaid)
- **Online Virtuell** (onlinevirtual)
- **Samsung Pay** (samsung)

### Bezahlsysteme (systems)
- **PayPal** (paypal)
- **Google Pay** (googlepay)
- **Apple Pay** (applepay)
- **Skrill** (skrill)
- **Stripe** (stripe)
- **Klarna** (klarna)

## Feldtypen

### Standard-Feldtypen
- `text` - Textfelder
- `number` - Zahlenfelder
- `date` - Datumsfelder
- `textarea` - Mehrzeilige Textfelder
- `checkbox` - Checkbox-Felder
- `dropdown` - Dropdown-Menüs mit Enum-Werten

### Enum-Felder
- `accountType` - Kontotypen
- `checkingType` - Girokonto-Typen
- `savingsType` - Sparkonto-Typen
- `creditCardType` - Kreditkarten-Typen
- `paymentSystemType` - Bezahlsystem-Typen
- `currency` - Währungen
- `entityState` - Status-Werte

## Verknüpfungen (Relations)

Alle Finanzkonto-Form-Definitionen verwenden die zentrale Verknüpfungsstruktur:

- **Kontakt** - Kontoeigentümer
- **Vertrag** - Zahlungskonto
- **Immobilie** - Finanzierungskonto
- **Finanzkonto** - Verwandte Konten
- **Mobilität** - Finanzierung

## i18n-Integration

### Struktur
```
src/main/webapp/i18n/de/financeaccount/
├── baseFinanceaccount.json          # Standard-Übersetzungen
├── enums.json                       # Enum-Übersetzungen
├── i18n-financeaccount-*.json       # Spezifische Übersetzungen
└── ...
```

### Übersetzungsschlüssel
- `jaynaApp.financeaccount-{type}.createTitle` - Erstellen-Titel
- `jaynaApp.financeaccount-{type}.updateTitle` - Bearbeiten-Titel
- `jaynaApp.financeaccount-{type}.viewTitle` - Anzeigen-Titel
- `jaynaApp.financeaccount-{type}.specificData.title` - Spezifischer Abschnitt
- `jaynaApp.financeaccount-{type}.specificData.fields.{fieldName}` - Feldnamen

## Entwicklung

### Neue Finanzkonto-Typen hinzufügen

1. **Form-Definition erstellen**:
   - Datei: `financeaccount-{sub-category}-{sub-category-group}-{group-type}.json`
   - Standard-Abschnitte aus baseFinanceaccount referenzieren
   - Spezifische Felder definieren

2. **i18n-Datei erstellen**:
   - Datei: `i18n-financeaccount-{sub-category}-{sub-category-group}-{group-type}.json`
   - Übersetzungen für Titel und spezifische Felder

3. **Enum-Werte hinzufügen** (falls erforderlich):
   - Neue Enum-Werte in `enums.json` definieren

### Best Practices

- Verwende immer die Standard-Abschnitte aus baseFinanceaccount
- Halte spezifische Felder minimal und relevant
- Verwende konsistente Namenskonventionen
- Dokumentiere neue Felder und Enum-Werte
- Teste die Form-Definitionen vor dem Deployment
