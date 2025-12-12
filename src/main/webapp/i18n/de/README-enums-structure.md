# Einheitliche Enum-Struktur in Jayna

## Übersicht

Alle Entitäten verwenden jetzt den **lokalen JSON-Ansatz** für Enum-Übersetzungen. Dies bietet eine transparente, wartbare und konsistente Struktur.

## Struktur

### Dateien
Jede Entität hat ihre eigene `enums.json` Datei:

```
src/main/webapp/i18n/de/
├── contract/
│   └── enums.json          # Contract-spezifische Enums
├── contact/
│   └── enums.json          # Contact-spezifische Enums
├── realestate/
│   └── enums.json          # RealEstate-spezifische Enums
├── financeaccount/
│   └── enums.json          # FinanceAccount-spezifische Enums
└── mobility/
    └── enums.json          # Mobility-spezifische Enums
```

## Enum-Definitionen

### Contract (`contract/enums.json`)
```json
{
  "paymentPattern": {
    "ONETIME": "Einmalig",
    "WEEKLY": "Wöchentlich",
    "DAILY": "Täglich",
    "MONTHLY": "Monatlich",
    "TWOMONTHLY": "Zweimonatlich",
    "QUARTERLY": "Vierteljährlich",
    "SEMIANNUAL": "Halbjährlich",
    "ANNUAL": "Jährlich"
  },
  "paymentType": {
    "TRANSFER": "Überweisung",
    "SEPA_DEBIT": "SEPA-Lastschrift",
    "CASH": "Bargeld"
  },
  "entityState": {
    "ACTIVE": "Aktiv",
    "ARCHIVE": "Archiviert"
  }
}
```

### Contact (`contact/enums.json`)
```json
{
  "title": {
    "DR": "Dr.",
    "PROF": "Prof.",
    "DR_MED": "Dr. med."
  },
  "salutation": {
    "MR": "Herr",
    "MRS": "Frau"
  },
  "rank": {
    "COLONEL": "Oberst",
    "LIEUTENANT": "Leutnant"
  },
  "gender": {
    "MALE": "Männlich",
    "FEMALE": "Weiblich",
    "DIVERS": "Divers"
  },
  "maritalStatus": {
    "SINGLE": "Ledig",
    "MARRIED": "Verheiratet",
    "DIVORCED": "Geschieden",
    "WIDOWED": "Verwitwet",
    "PARTNERSHIP": "Partnerschaft"
  },
  "addressType": {
    "PRIVATE": "Privat",
    "BUSINESS": "Geschäftlich"
  },
  "communicationType": {
    "PHONE": "Telefon",
    "FAX": "Fax",
    "MOBILE": "Mobil",
    "PAGER": "Pager",
    "EMAIL": "E-Mail",
    "CHAT": "Chat",
    "HOMEPAGE": "Homepage",
    "SOCIAL_MEDIA": "Soziale Medien"
  },
  "entityState": {
    "ACTIVE": "Aktiv",
    "ARCHIVE": "Archiviert"
  }
}
```

### RealEstate (`realestate/enums.json`)
```json
{
  "propertyType": {
    "RESIDENTIAL_BUILDING": "Wohngebäude",
    "COMMERCIAL_BUILDING": "Gewerbegebäude",
    "GROUND": "Grundstück",
    "FARMLAND": "Landwirtschaft",
    "GARDEN": "Garten",
    "HALL": "Halle",
    "PARKING_SPACE": "Parkfläche",
    "COMMERCIAL_RESIDENTIAL_BUILDING": "Wohn- und Gewerbegebäude"
  },
  "designTypeClass": {
    "BAK1": "BAK1",
    "BAK2": "BAK2",
    "BAK3": "BAK3",
    "BAK4": "BAK4",
    "BAK5": "BAK5"
  },
  "heatingType": {
    "GAS": "Gas",
    "PELLET": "Pellets",
    "ELECTRIC": "Elektrisch",
    "HEATPUMP": "Wärmepumpe"
  },
  "roofType": {
    "FLAT": "Flachdach",
    "HIPPED": "Walmdach",
    "CROSS": "Kreuzdach",
    // ... weitere Dachtypen
  },
  "direction": {
    "NORTH": "Nord",
    "EAST": "Ost",
    "SOUTH": "Süd",
    "WEST": "West"
  },
  "houseType": {
    "DETACHED_HOUSE": "Einfamilienhaus",
    "SEMIDETACHED_HOUSE": "Doppelhaus",
    "TERRACED_HOUSE": "Reihenhaus",
    "APARTMENT_BUILDING": "Mehrfamilienhaus",
    "CARE_FACILITY": "Pflegeeinrichtung",
    "VACATION_HOUSE": "Ferienhaus",
    "TWO_FAMILY_HOUSE": "Zweifamilienhaus"
  },
  "ownershipType": {
    "OWNER_OCCUPIED": "Eigennutzung",
    "RENTAL": "Vermietet",
    "PARTIAL_OWNERSHIP": "Teileigentum",
    "COMMON_OWNERSHIP": "Gemeinschaftseigentum",
    "LEASEHOLD": "Erbpacht",
    "COOPERATIVE": "Genossenschaft"
  },
  "energyEfficiencyClass": {
    "A_PLUS": "A+",
    "A": "A",
    "B": "B",
    "C": "C",
    "D": "D",
    "E": "E",
    "F": "F",
    "G": "G",
    "H": "H"
  },
  "plotType": {
    "BUILDING_PLOT": "Bauland",
    "MEADOW": "Wiese",
    "FOREST": "Wald",
    "FARMLAND": "Ackerland",
    "PASTURE": "Weide",
    "GARDEN": "Garten"
  },
  "zoningType": {
    "RESIDENTIAL": "Wohngebiet",
    "COMMERCIAL": "Gewerbegebiet",
    "MIXED": "Mischgebiet",
    "INDUSTRIAL": "Industriegebiet",
    "AGRICULTURAL": "Landwirtschaft",
    "RECREATION": "Erholungsgebiet"
  },
  "soilQuality": {
    "EXCELLENT": "Sehr gut",
    "GOOD": "Gut",
    "AVERAGE": "Durchschnittlich",
    "POOR": "Schlecht",
    "VERY_POOR": "Sehr schlecht"
  },
  "officeType": {
    "OFFICE": "Büro",
    "OPEN_PLAN_OFFICE": "Großraumbüro",
    "EXECUTIVE_OFFICE": "Chefbüro",
    "MEETING_ROOM": "Besprechungsraum",
    "RECEPTION": "Empfang",
    "COWORKING": "Coworking-Space"
  },
  "tenantType": {
    "PRIVATE": "Privat",
    "COMMERCIAL": "Gewerblich",
    "PUBLIC": "Öffentlich",
    "NON_PROFIT": "Gemeinnützig"
  }
}
```

### FinanceAccount (`financeaccount/enums.json`)
```json
{
  "accountType": {
    "CHECKING": "Girokonto",
    "SAVINGS": "Sparkonto",
    "CREDIT": "Kreditkonto",
    "INVESTMENT": "Anlagekonto",
    "BUSINESS": "Geschäftskonto",
    "STUDENT": "Studentenkonto",
    "SENIOR": "Seniorenkonto",
    "PAYMENT_SYSTEM": "Bezahlsystem"
  },
  "checkingType": {
    "STANDARD": "Standard",
    "PREMIUM": "Premium",
    "STUDENT": "Student",
    "SENIOR": "Senior",
    "BUSINESS": "Geschäft",
    "BASIC": "Basis"
  },
  "savingsType": {
    "REGULAR": "Regulär",
    "HIGH_YIELD": "Hochverzinslich",
    "FIXED_TERM": "Festgeld",
    "ONLINE": "Online",
    "STUDENT": "Student",
    "SENIOR": "Senior"
  },
  "creditCardType": {
    "CHARGE": "Charge Card",
    "DEBIT": "Debit Card",
    "REVOLVING": "Revolving Credit",
    "PREPAID": "Prepaid",
    "ONLINE_VIRTUAL": "Online Virtuell",
    "SAMSUNG": "Samsung Pay"
  },
  "paymentSystemType": {
    "PAYPAL": "PayPal",
    "GOOGLE_PAY": "Google Pay",
    "APPLE_PAY": "Apple Pay",
    "SKRILL": "Skrill",
    "STRIPE": "Stripe",
    "KLARNA": "Klarna"
  },
  "interestCalculation": {
    "DAILY": "Täglich",
    "MONTHLY": "Monatlich",
    "QUARTERLY": "Vierteljährlich",
    "ANNUALLY": "Jährlich"
  },
  "cardNetwork": {
    "VISA": "Visa",
    "MASTERCARD": "Mastercard",
    "AMEX": "American Express",
    "DISCOVER": "Discover",
    "JCB": "JCB",
    "UNIONPAY": "UnionPay"
  },
  "accountStatus": {
    "ACTIVE": "Aktiv",
    "SUSPENDED": "Gesperrt",
    "LIMITED": "Eingeschränkt",
    "CLOSED": "Geschlossen",
    "PENDING": "Ausstehend"
  },
  "verificationStatus": {
    "UNVERIFIED": "Nicht verifiziert",
    "VERIFIED": "Verifiziert",
    "LIMITED": "Eingeschränkt verifiziert",
    "BUSINESS": "Geschäft verifiziert"
  },
  "currency": {
    "EUR": "Euro (EUR)",
    "USD": "US-Dollar (USD)",
    "GBP": "Britische Pfund (GBP)",
    "CHF": "Schweizer Franken (CHF)",
    "JPY": "Japanische Yen (JPY)",
    "CNY": "Chinesische Yuan (CNY)"
  },
  "entityState": {
    "ACTIVE": "Aktiv",
    "SUSPENDED": "Gesperrt",
    "LIMITED": "Eingeschränkt",
    "CLOSED": "Geschlossen",
    "PENDING": "Ausstehend"
  }
}
```

### Mobility (`mobility/enums.json`)
```json
{
  "vehicleType": {
    "CAR": "Auto",
    "MOTORCYCLE": "Motorrad",
    "TRUCK": "LKW",
    "VAN": "Transporter",
    "BOAT": "Boot",
    "PLANE": "Flugzeug",
    "BIKE": "Fahrrad",
    "TRACTOR": "Traktor",
    "CARAVAN": "Wohnwagen",
    "CAMPER": "Wohnmobil"
  },
  "fuelType": {
    "PETROL": "Benzin",
    "DIESEL": "Diesel",
    "ELECTRIC": "Elektro",
    "HYBRID": "Hybrid",
    "HYDROGEN": "Wasserstoff",
    "LPG": "Autogas",
    "CNG": "Erdgas",
    "BIOFUEL": "Biokraftstoff",
    "NONE": "Kein Kraftstoff"
  },
  "entityState": {
    "ACTIVE": "Aktiv",
    "INACTIVE": "Inaktiv",
    "SUSPENDED": "Gesperrt",
    "CLOSED": "Geschlossen",
    "PENDING": "Ausstehend"
  },
  "carType": {
    "STANDARD": "Standard",
    "LUXURY": "Luxus",
    "SPORTS": "Sport",
    "FAMILY": "Familie",
    "COMPACT": "Kompakt",
    "SUV": "SUV",
    "CONVERTIBLE": "Cabrio",
    "COUPE": "Coupé"
  },
  "transmission": {
    "MANUAL": "Schaltgetriebe",
    "AUTOMATIC": "Automatik",
    "CVT": "Stufenloses Getriebe",
    "SEMI_AUTOMATIC": "Halbautomatik",
    "DUAL_CLUTCH": "Doppelkupplung"
  },
  "motorcycleType": {
    "NAKED": "Naked Bike",
    "SPORT": "Sportbike",
    "CRUISER": "Cruiser",
    "TOURING": "Touring",
    "ADVENTURE": "Adventure",
    "SCOOTER": "Roller",
    "CHOPPER": "Chopper",
    "STREETFIGHTER": "Streetfighter",
    "SUPERMOTO": "Supermoto",
    "TRIAL": "Trial",
    "ENDURO": "Enduro",
    "MOTOCROSS": "Motocross"
  }
}
```

## Vorteile der neuen Struktur

### ✅ **Transparenz**
- Alle Enum-Übersetzungen sind direkt in JSON-Dateien sichtbar
- Einfach zu debuggen und zu warten
- Keine Abhängigkeit vom Backend-Service

### ✅ **Konsistenz**
- Einheitliche Struktur für alle Entitäten
- Folgt dem gleichen Muster wie andere i18n-Dateien
- Flat key pattern für bessere Übersichtlichkeit

### ✅ **Wartbarkeit**
- Bereichsspezifische Organisation
- Einfache Erweiterung neuer Enum-Werte
- Gute Versionierung und Git-Tracking

### ✅ **Flexibilität**
- Jeder Bereich kann seine eigenen Enums haben
- Unabhängige Entwicklung und Wartung
- Keine zentrale Abhängigkeit

## Migration von Backend-zu Frontend-Enums

### Vorher (Backend-zentral)
```javascript
// Backend-basiert
ConfigService.getEnums().then((response) => {
  response.data.forEach((item) => {
    const itemName = toLowerCase(item.name);
    state[itemName] = item.items;
  });
});
```

### Nachher (Frontend-lokal)
```json
// Lokale JSON-Dateien
{
  "paymentPattern": {
    "ONETIME": "Einmalig",
    "WEEKLY": "Wöchentlich"
  }
}
```

## Verwendung in Formularen

```json
{
  "name": "paymentPattern",
  "type": "dropdown",
  "enumName": "paymentPattern"
}
```

Die Enum-Werte werden automatisch aus den entsprechenden `enums.json` Dateien geladen und übersetzt.

## Wartung

### Neue Enum-Werte hinzufügen
1. Enum in der entsprechenden `enums.json` Datei hinzufügen
2. Deutsche Übersetzung ergänzen
3. Formular-Definition aktualisieren (falls erforderlich)

### Neue Entität mit Enums
1. `enums.json` Datei im entsprechenden Verzeichnis erstellen
2. Alle relevanten Enums mit Übersetzungen definieren
3. Formular-Definitionen mit `enumName` referenzieren

## Fazit

Die neue einheitliche Enum-Struktur bietet eine **saubere, transparente und wartbare Lösung** für alle Entitäten in Jayna. Sie folgt bewährten Praktiken der Frontend-Entwicklung und ermöglicht eine effiziente Wartung und Erweiterung des Systems.
