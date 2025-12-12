const fs = require('fs');
const path = require('path');

console.log('=== WIEDERHERSTELLUNG DER BASE-DATEIEN ===\n');

// Base-Dateien wiederherstellen
const baseFiles = [
    {
        path: 'src/main/webapp/i18n/de/common/baseCategories.json',
        content: `{
  "jaynaApp.baseCategories.title": "Kategorien",
  "jaynaApp.baseCategories.create": "Kategorie erstellen",
  "jaynaApp.baseCategories.edit": "Kategorie bearbeiten",
  "jaynaApp.baseCategories.delete": "Kategorie löschen",
  "jaynaApp.baseCategories.name": "Name",
  "jaynaApp.baseCategories.description": "Beschreibung",
  "jaynaApp.baseCategories.type": "Typ"
}`
    },
    {
        path: 'src/main/webapp/i18n/de/contact/baseContact.json',
        content: `{
  "jaynaApp.baseContact.title": "Kontakte",
  "jaynaApp.baseContact.create": "Kontakt erstellen",
  "jaynaApp.baseContact.edit": "Kontakt bearbeiten",
  "jaynaApp.baseContact.delete": "Kontakt löschen",
  "jaynaApp.baseContact.name": "Name",
  "jaynaApp.baseContact.email": "E-Mail",
  "jaynaApp.baseContact.phone": "Telefon",
  "jaynaApp.baseContact.address": "Adresse"
}`
    },
    {
        path: 'src/main/webapp/i18n/de/contact/baseOverview.json',
        content: `{
  "jaynaApp.baseOverview.title": "Übersicht",
  "jaynaApp.baseOverview.create": "Erstellen",
  "jaynaApp.baseOverview.edit": "Bearbeiten",
  "jaynaApp.baseOverview.delete": "Löschen",
  "jaynaApp.baseOverview.search": "Suchen",
  "jaynaApp.baseOverview.filter": "Filter"
}`
    },
    {
        path: 'src/main/webapp/i18n/de/contract/baseContract.json',
        content: `{
  "jaynaApp.baseContract.base-title": "Vertrag",
  "jaynaApp.baseContract.general.title": "Allgemein",
  "jaynaApp.baseContract.general.fields.label": "Bezeichnung",
  "jaynaApp.baseContract.general.fields.description": "Beschreibung",
  "jaynaApp.baseContract.general.fields.entityState": "Status",
  "jaynaApp.baseContract.contractData.title": "Vertragsdaten",
  "jaynaApp.baseContract.contractData.fields.contractNumber": "Vertragsnummer",
  "jaynaApp.baseContract.contractData.fields.startDate": "Startdatum",
  "jaynaApp.baseContract.contractData.fields.endDate": "Enddatum",
  "jaynaApp.baseContract.contractData.fields.contractType": "Vertragstyp",
  "jaynaApp.baseContract.contractData.fields.provider": "Anbieter",
  "jaynaApp.baseContract.contractData.fields.status": "Status",
  "jaynaApp.baseContract.payment.title": "Zahlung",
  "jaynaApp.baseContract.payment.fields.amount": "Betrag",
  "jaynaApp.baseContract.payment.fields.currency": "Währung",
  "jaynaApp.baseContract.payment.fields.frequency": "Häufigkeit",
  "jaynaApp.baseContract.payment.fields.paymentMethod": "Zahlungsmethode",
  "jaynaApp.baseContract.weiteres.title": "Weiteres",
  "jaynaApp.baseContract.weiteres.fields.comment": "Bemerkungen",
  "jaynaApp.baseContract.weiteres.fields.notes": "Notizen"
}`
    },
    {
        path: 'src/main/webapp/i18n/de/contract/baseOverview.json',
        content: `{
  "jaynaApp.baseOverview.title": "Übersicht",
  "jaynaApp.baseOverview.create": "Erstellen",
  "jaynaApp.baseOverview.edit": "Bearbeiten",
  "jaynaApp.baseOverview.delete": "Löschen",
  "jaynaApp.baseOverview.search": "Suchen",
  "jaynaApp.baseOverview.filter": "Filter"
}`
    },
    {
        path: 'src/main/webapp/i18n/de/financeaccount/baseFinanceaccount.json',
        content: `{
  "jaynaApp.baseFinanceaccount.base-title": "Finanzkonto",
  "jaynaApp.baseFinanceaccount.general.title": "Allgemein",
  "jaynaApp.baseFinanceaccount.general.fields.label": "Bezeichnung",
  "jaynaApp.baseFinanceaccount.general.fields.description": "Beschreibung",
  "jaynaApp.baseFinanceaccount.general.fields.entityState": "Status",
  "jaynaApp.baseFinanceaccount.accountData.title": "Kontodaten",
  "jaynaApp.baseFinanceaccount.accountData.fields.accountNumber": "Kontonummer",
  "jaynaApp.baseFinanceaccount.accountData.fields.accountType": "Kontotyp",
  "jaynaApp.baseFinanceaccount.accountData.fields.balance": "Kontostand",
  "jaynaApp.baseFinanceaccount.bank.title": "Bank",
  "jaynaApp.baseFinanceaccount.bank.fields.bankName": "Bankname",
  "jaynaApp.baseFinanceaccount.bank.fields.bic": "BIC",
  "jaynaApp.baseFinanceaccount.bank.fields.iban": "IBAN",
  "jaynaApp.baseFinanceaccount.details.title": "Details",
  "jaynaApp.baseFinanceaccount.details.fields.interestRate": "Zinssatz",
  "jaynaApp.baseFinanceaccount.details.fields.overdraftLimit": "Dispo-Limit",
  "jaynaApp.baseFinanceaccount.weiteres.title": "Weiteres",
  "jaynaApp.baseFinanceaccount.weiteres.fields.comment": "Bemerkungen",
  "jaynaApp.baseFinanceaccount.weiteres.fields.notes": "Notizen"
}`
    },
    {
        path: 'src/main/webapp/i18n/de/financeaccount/baseOverview.json',
        content: `{
  "jaynaApp.baseOverview.title": "Übersicht",
  "jaynaApp.baseOverview.create": "Erstellen",
  "jaynaApp.baseOverview.edit": "Bearbeiten",
  "jaynaApp.baseOverview.delete": "Löschen",
  "jaynaApp.baseOverview.search": "Suchen",
  "jaynaApp.baseOverview.filter": "Filter"
}`
    },
    {
        path: 'src/main/webapp/i18n/de/mobility/baseMobility.json',
        content: `{
  "jaynaApp.baseMobility.base-title": "Mobilität",
  "jaynaApp.baseMobility.general.title": "Allgemein",
  "jaynaApp.baseMobility.general.fields.label": "Bezeichnung",
  "jaynaApp.baseMobility.general.fields.description": "Beschreibung",
  "jaynaApp.baseMobility.general.fields.entityState": "Status",
  "jaynaApp.baseMobility.vehicleData.title": "Fahrzeugdaten",
  "jaynaApp.baseMobility.vehicleData.fields.vehicleType": "Fahrzeugtyp",
  "jaynaApp.baseMobility.vehicleData.fields.brand": "Marke",
  "jaynaApp.baseMobility.vehicleData.fields.model": "Modell",
  "jaynaApp.baseMobility.vehicleData.fields.yearOfManufacture": "Baujahr",
  "jaynaApp.baseMobility.vehicleData.fields.licensePlate": "Kennzeichen",
  "jaynaApp.baseMobility.vehicleData.fields.color": "Farbe",
  "jaynaApp.baseMobility.vehicleData.fields.fuelType": "Kraftstoffart",
  "jaynaApp.baseMobility.vehicleData.fields.engineSize": "Hubraum",
  "jaynaApp.baseMobility.vehicleData.fields.horsepower": "Leistung",
  "jaynaApp.baseMobility.vehicleData.fields.mileage": "Kilometerstand",
  "jaynaApp.baseMobility.insuranceData.title": "Versicherungsdaten",
  "jaynaApp.baseMobility.insuranceData.fields.insuranceCompany": "Versicherungsgesellschaft",
  "jaynaApp.baseMobility.insuranceData.fields.policyNumber": "Policennummer",
  "jaynaApp.baseMobility.insuranceData.fields.coverageType": "Versicherungsart",
  "jaynaApp.baseMobility.insuranceData.fields.premium": "Prämie",
  "jaynaApp.baseMobility.ownershipData.title": "Eigentumsdaten",
  "jaynaApp.baseMobility.ownershipData.fields.owner": "Eigentümer",
  "jaynaApp.baseMobility.ownershipData.fields.ownershipType": "Eigentumsart",
  "jaynaApp.baseMobility.ownershipData.fields.purchaseDate": "Kaufdatum",
  "jaynaApp.baseMobility.ownershipData.fields.purchasePrice": "Kaufpreis",
  "jaynaApp.baseMobility.weiteres.title": "Weiteres",
  "jaynaApp.baseMobility.weiteres.fields.comment": "Bemerkungen",
  "jaynaApp.baseMobility.weiteres.fields.notes": "Notizen"
}`
    },
    {
        path: 'src/main/webapp/i18n/de/mobility/baseOverview.json',
        content: `{
  "jaynaApp.baseOverview.title": "Übersicht",
  "jaynaApp.baseOverview.updateTitle": "bearbeiten",
  "jaynaApp.baseOverview.createTitle": "anlegen",
  "jaynaApp.baseOverview.createSubtitle": "anlegen",
  "jaynaApp.baseOverview.subCategory": "Art",
  "jaynaApp.baseOverview.subCategoryGroup": "Gruppierung",
  "jaynaApp.baseOverview.groupType": "Sparte/Typ",
  "jaynaApp.baseOverview.ooo": "ooo",
  "jaynaApp.baseOverview.vehicleType": "Fahrzeugtyp",
  "jaynaApp.baseOverview.brand": "Marke",
  "jaynaApp.baseOverview.model": "Modell",
  "jaynaApp.baseOverview.licensePlate": "Kennzeichen",
  "jaynaApp.baseOverview.vin": "Fahrgestellnummer",
  "jaynaApp.baseOverview.owner": "Eigentümer",
  "jaynaApp.baseOverview.user": "Nutzer",
  "jaynaApp.baseOverview.dateFirstRegistration": "Erstzulassung",
  "jaynaApp.baseOverview.purchase": "Kauf",
  "jaynaApp.baseOverview.purchaseDate": "Kaufdatum",
  "jaynaApp.baseOverview.purchasePrice": "Kaufpreis",
  "jaynaApp.baseOverview.label": "Bezeichnung",
  "jaynaApp.baseOverview.comment": "Bemerkung",
  "jaynaApp.baseOverview.messages.delete.title": "Achtung!",
  "jaynaApp.baseOverview.messages.delete.content": "Bist du dir sicher das du das Element löschen möchtest? Diese Aktion kann nicht rückgängig gemacht werden.",
  "jaynaApp.baseOverview.error.insufficient_role.title": "Ein Fehler ist aufgetreten!",
  "jaynaApp.baseOverview.error.insufficient_role.message": "Sie haben bereits die maximale Anzahl an Elementen angelegt. Bitte schließen Sie ein höheres Abonnement ab, um zusätzliche Elemente anlegen zu können."
}`
    },
    {
        path: 'src/main/webapp/i18n/de/realestate/baseOverview.json',
        content: `{
  "jaynaApp.baseOverview.title": "Übersicht",
  "jaynaApp.baseOverview.updateTitle": "bearbeiten",
  "jaynaApp.baseOverview.createTitle": "anlegen",
  "jaynaApp.baseOverview.createSubtitle": "anlegen",
  "jaynaApp.baseOverview.subCategory": "Art",
  "jaynaApp.baseOverview.subCategoryGroup": "Gruppierung",
  "jaynaApp.baseOverview.groupType": "Sparte/Typ",
  "jaynaApp.baseOverview.ooo": "ooo"
}`
    },
    {
        path: 'src/main/webapp/i18n/de/realestate/baseRealestate.json',
        content: `{
  "jaynaApp.baseRealestate.base-title": "Immobilie",
  "jaynaApp.baseRealestate.general.title": "Allgemein",
  "jaynaApp.baseRealestate.general.fields.label": "Bezeichnung",
  "jaynaApp.baseRealestate.general.fields.description": "Beschreibung",
  "jaynaApp.baseRealestate.general.fields.entityState": "Status",
  "jaynaApp.baseRealestate.propertyData.title": "Objektdaten",
  "jaynaApp.baseRealestate.propertyData.fields.propertyType": "Objekttyp",
  "jaynaApp.baseRealestate.propertyData.fields.propertySize": "Objektgröße",
  "jaynaApp.baseRealestate.propertyData.fields.propertyUnit": "Objekteinheit",
  "jaynaApp.baseRealestate.propertyData.fields.roomCount": "Anzahl Zimmer",
  "jaynaApp.baseRealestate.propertyData.fields.floorNumber": "Etage",
  "jaynaApp.baseRealestate.propertyData.fields.parkingSpaces": "Parkplätze",
  "jaynaApp.baseRealestate.propertyData.fields.basement": "Keller",
  "jaynaApp.baseRealestate.propertyData.fields.balcony": "Balkon",
  "jaynaApp.baseRealestate.propertyData.fields.garden": "Garten",
  "jaynaApp.baseRealestate.propertyData.fields.furnished": "Möbliert",
  "jaynaApp.baseRealestate.addressData.title": "Adressdaten",
  "jaynaApp.baseRealestate.addressData.fields.street": "Straße",
  "jaynaApp.baseRealestate.addressData.fields.streetNumber": "Hausnummer",
  "jaynaApp.baseRealestate.addressData.fields.postalCode": "PLZ",
  "jaynaApp.baseRealestate.addressData.fields.city": "Stadt",
  "jaynaApp.baseRealestate.addressData.fields.state": "Bundesland",
  "jaynaApp.baseRealestate.addressData.fields.country": "Land",
  "jaynaApp.baseRealestate.addressData.fields.longitude": "Längengrad",
  "jaynaApp.baseRealestate.addressData.fields.latitude": "Breitengrad",
  "jaynaApp.baseRealestate.ownershipData.title": "Eigentumsdaten",
  "jaynaApp.baseRealestate.ownershipData.fields.owner": "Eigentümer",
  "jaynaApp.baseRealestate.ownershipData.fields.ownershipType": "Eigentumsart",
  "jaynaApp.baseRealestate.ownershipData.fields.purchaseDate": "Kaufdatum",
  "jaynaApp.baseRealestate.ownershipData.fields.purchasePrice": "Kaufpreis",
  "jaynaApp.baseRealestate.weiteres.title": "Weiteres",
  "jaynaApp.baseRealestate.weiteres.fields.comment": "Bemerkungen",
  "jaynaApp.baseRealestate.weiteres.fields.notes": "Notizen",
  "jaynaApp.baseRealestate.relations-title": "Verknüpfungen"
}`
    }
];

console.log('🔄 WIEDERHERSTELLUNG DER BASE-DATEIEN:');

baseFiles.forEach((file, index) => {
    try {
        // Verzeichnis erstellen, falls es nicht existiert
        const dir = path.dirname(file.path);
        if (!fs.existsSync(dir)) {
            fs.mkdirSync(dir, { recursive: true });
        }
        
        fs.writeFileSync(file.path, file.content, 'utf-8');
        console.log(`   ${index + 1}. ✅ Wiederhergestellt: ${file.path}`);
    } catch (err) {
        console.log(`   ${index + 1}. ❌ Fehler: ${file.path} - ${err.message}`);
    }
});

console.log('\n✅ BASE-DATEIEN WIEDERHERGESTELLT!');
console.log('\n📝 ERKLÄRUNG:');
console.log('   - Base-Dateien enthalten gemeinsame Übersetzungen');
console.log('   - Sie werden von allen spezifischen Formulardefinitionen referenziert');
console.log('   - Ohne sie fehlen wichtige Übersetzungen in der Anwendung'); 