const fs = require('fs');
const path = require('path');

// Standard-Felder für jeden Abschnitt
const standardFields = {
  general: [
    { name: "label", type: "text", rules: "required" },
    { name: "description", type: "text" },
    { name: "entityState", type: "dropdown", boxLabel: "global.menu.choose", enumName: "entityState", defaultValue: "ACTIVE" }
  ],
  weiteres: [
    { name: "comment", type: "textarea" },
    { name: "notes", type: "textarea" }
  ]
};

// Entitätsspezifische Standard-Felder für Financeaccount
const financeaccountFields = {
  accountData: [
    { name: "accountNumber", type: "text" },
    { name: "iban", type: "text" },
    { name: "bic", type: "text" },
    { name: "accountHolder", type: "text" }
  ],
  bank: [
    { name: "bankName", type: "text" },
    { name: "bankCode", type: "text" },
    { name: "branch", type: "text" }
  ],
  details: [
    { name: "balance", type: "number" },
    { name: "currency", type: "dropdown", boxLabel: "global.menu.choose", enumName: "currency" },
    { name: "status", type: "dropdown", boxLabel: "global.menu.choose", enumName: "accountStatus" }
  ]
};

// Spezifische Felder für jede leere Datei
const specificFields = {
  'financeaccount-bank-debit-debit': [
    { name: "debitType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "debitType" },
    { name: "dailyLimit", type: "number" },
    { name: "monthlyLimit", type: "number" },
    { name: "overdraftFee", type: "number" }
  ],
  'financeaccount-bank-prepaid-prepaid': [
    { name: "prepaidType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "prepaidType" },
    { name: "cardNumber", type: "text" },
    { name: "expiryDate", type: "date", enableTimePicker: false, autoApply: true, textInput: true, format: "dd.MM.yyyy" },
    { name: "reloadable", type: "checkbox" }
  ],
  'financeaccount-bank-revolving-revolving': [
    { name: "revolvingType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "revolvingType" },
    { name: "creditLimit", type: "number" },
    { name: "interestRate", type: "number" },
    { name: "minimumPayment", type: "number" }
  ],
  'financeaccount-systems-applepay-applepay': [
    { name: "deviceType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "deviceType" },
    { name: "appleId", type: "text" },
    { name: "cardCount", type: "number" },
    { name: "verificationStatus", type: "dropdown", boxLabel: "global.menu.choose", enumName: "verificationStatus" }
  ],
  'financeaccount-systems-googlepay-googlepay': [
    { name: "deviceType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "deviceType" },
    { name: "googleAccount", type: "text" },
    { name: "cardCount", type: "number" },
    { name: "verificationStatus", type: "dropdown", boxLabel: "global.menu.choose", enumName: "verificationStatus" }
  ],
  'financeaccount-systems-klarna-klarna': [
    { name: "klarnaType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "klarnaType" },
    { name: "email", type: "text" },
    { name: "phoneNumber", type: "text" },
    { name: "creditLimit", type: "number" }
  ],
  'financeaccount-systems-onlinevirtual-onlinevirtual': [
    { name: "virtualType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "virtualType" },
    { name: "accountName", type: "text" },
    { name: "currency", type: "dropdown", boxLabel: "global.menu.choose", enumName: "currency" },
    { name: "isAnonymous", type: "checkbox" }
  ],
  'financeaccount-systems-skrill-skrill': [
    { name: "skrillType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "skrillType" },
    { name: "email", type: "text" },
    { name: "verificationStatus", type: "dropdown", boxLabel: "global.menu.choose", enumName: "verificationStatus" },
    { name: "accountBalance", type: "number" }
  ],
  'financeaccount-systems-stripe-stripe': [
    { name: "stripeType", type: "dropdown", boxLabel: "global.menu.choose", enumName: "stripeType" },
    { name: "accountId", type: "text" },
    { name: "verificationStatus", type: "dropdown", boxLabel: "global.menu.choose", enumName: "verificationStatus" },
    { name: "processingVolume", type: "number" }
  ]
};

// Standard-Relations für Financeaccount
const standardRelations = [
  { entityName: "contact", relationName: "financeaccountContacts" },
  { entityName: "contract", relationName: "financeaccountContracts" },
  { entityName: "realestate", relationName: "financeaccountRealestates" },
  { entityName: "mobility", relationName: "financeaccountMobilities" }
];

// Erstelle Formulardefinition für leere Datei
function createFormDefinition(formName) {
  const specificFieldsForForm = specificFields[formName] || [
    { name: "type", type: "dropdown", boxLabel: "global.menu.choose", enumName: "financeaccountType" },
    { name: "category", type: "text" }
  ];

  return {
    "_comment": "formControl-Konzept: Standard-Abschnitte aus BaseFinanceaccount, spezielle Abschnitte mit i18nSource",
    "formTitle": formName.replace(/-/g, ' ').replace(/\b\w/g, l => l.toUpperCase()),
    "parts": [
      {
        "title": "jaynaApp.baseFinanceaccount.general.title",
        "fields": standardFields.general
      },
      {
        "title": "jaynaApp.baseFinanceaccount.accountData.title",
        "fields": financeaccountFields.accountData
      },
      {
        "title": "jaynaApp.baseFinanceaccount.bank.title",
        "fields": financeaccountFields.bank
      },
      {
        "title": "jaynaApp.baseFinanceaccount.details.title",
        "fields": financeaccountFields.details
      },
      {
        "title": "jaynaApp.baseFinanceaccount.weiteres.title",
        "fields": standardFields.weiteres
      }
    ],
    "relations": standardRelations,
    "relationsTitle": `jaynaApp.${formName}.relations-title`
  };
}

// Hauptfunktion
function fillEmptyFormDefinitions() {
  console.log('🔧 Befülle leere Formulardefinitionen...\n');
  
  const financeaccountDir = './src/main/webapp/app/shared/form-definitions/financeaccount';
  const emptyFiles = [
    'financeaccount-bank-debit-debit.json',
    'financeaccount-bank-prepaid-prepaid.json',
    'financeaccount-bank-revolving-revolving.json',
    'financeaccount-systems-applepay-applepay.json',
    'financeaccount-systems-googlepay-googlepay.json',
    'financeaccount-systems-klarna-klarna.json',
    'financeaccount-systems-onlinevirtual-onlinevirtual.json',
    'financeaccount-systems-skrill-skrill.json',
    'financeaccount-systems-stripe-stripe.json'
  ];
  
  let filledCount = 0;
  let errorCount = 0;
  
  for (const file of emptyFiles) {
    const filePath = path.join(financeaccountDir, file);
    const formName = file.replace('.json', '');
    
    try {
      // Prüfe ob Datei leer ist
      const fileSize = fs.statSync(filePath).size;
      if (fileSize > 10) {
        console.log(`⚠️  ${file} (nicht leer, überspringe)`);
        continue;
      }
      
      // Erstelle neue Formulardefinition
      const formDef = createFormDefinition(formName);
      
      // Speichere Datei
      fs.writeFileSync(filePath, JSON.stringify(formDef, null, 2), 'utf8');
      console.log(`✅ ${file} (befüllt)`);
      filledCount++;
      
    } catch (error) {
      console.log(`❌ ${file} (Fehler: ${error.message})`);
      errorCount++;
    }
  }
  
  console.log(`\n📊 Zusammenfassung:`);
  console.log(`✅ Befüllt: ${filledCount}`);
  console.log(`❌ Fehler: ${errorCount}`);
  console.log(`📄 Gesamt: ${filledCount + errorCount}`);
}

// Skript ausführen
fillEmptyFormDefinitions(); 