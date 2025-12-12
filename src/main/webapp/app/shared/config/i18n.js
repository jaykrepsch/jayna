import { createI18n } from "vue-i18n";

// Filtere spezifische dev-Warnung des vue-i18n Pakets in der Konsole
if (process.env.NODE_ENV === 'development') {
  const originalConsoleWarn = console.warn ? console.warn.bind(console) : null;
  if (originalConsoleWarn) {
    console.warn = (...args) => {
      const first = args && args[0] ? String(args[0]) : '';
      if (
        first.includes("[intlify] 'allowComposition' option will be dropped") ||
        first.startsWith('[intlify] Not found ')
      ) {
        return;
      }
      originalConsoleWarn(...args);
    };
  }
}

const numberFormats = {
  de: {
    currency: {
      style: "currency",
      currency: "EUR",
      minimumFractionDigits: 0,
      maximumFractionDigits: 0,
    },
    percent: {
      style: "percent",
      minimumFractionDigits: 0,
      maximumFractionDigits: 2,
    },
    price: {
      style: "currency",
      currency: "EUR",
      minimumFractionDigits: 2,
      maximumFractionDigits: 2,
    },
  },
};

const datetimeFormats = {
  de: {
    short: {
      year: "numeric",
      month: "2-digit",
      day: "2-digit",
    },
    medium: {
      year: "numeric",
      month: "short",
      day: "numeric",
      weekday: "short",
      hour: "numeric",
      minute: "numeric",
    },
    long: {
      year: "numeric",
      month: "long",
      day: "numeric",
      weekday: "long",
      hour: "numeric",
      minute: "numeric",
    },
  },
};

// Funktion zum Konvertieren von flachen Schlüsseln zu verschachtelten Objekten
const flattenToNested = (obj) => {
  const result = {};
  
  Object.keys(obj).forEach(key => {
    const keys = key.split('.');
    let current = result;
    
    for (let i = 0; i < keys.length - 1; i++) {
      const k = keys[i];
      if (!current[k] || typeof current[k] !== 'object') {
        current[k] = {};
      }
      current = current[k];
    }
    
    current[keys[keys.length - 1]] = obj[key];
  });
  
  return result;
};

// Lade alle Übersetzungsdateien
const loadTranslations = () => {
  const context = require.context('../../../i18n/de', true, /\.json$/);
const messages = {
  de: {
      jaynaApp: {}
    }
  };

  // Manuell spezifische Übersetzungsdateien laden
  try {
    const mobilityMotorgliderTranslations = require('../../../i18n/de/mobility/i18n-mobility-boat-hybrid-motorglider.json');
    Object.assign(messages.de.jaynaApp, mobilityMotorgliderTranslations);
  } catch (error) {
  }
  
  // Manuell Contact-spezifische Übersetzungsdateien laden
  try {
    const contactSingleContactTranslations = require('../../../i18n/de/contact/i18n-contact-contact-contact-singlecontact.json');
    Object.assign(messages.de.jaynaApp, contactSingleContactTranslations);
  } catch (error) {
    // Ignoriere Fehler - Datei existiert möglicherweise nicht
  }
  
  try {
    const contactContactGroupTranslations = require('../../../i18n/de/contact/i18n-contact-contact-contact-contactgroup.json');
    Object.assign(messages.de.jaynaApp, contactContactGroupTranslations);
  } catch (error) {
    // Ignoriere Fehler - Datei existiert möglicherweise nicht
  }
  
  // Stelle sicher, dass Dokument-Übersetzungen verfügbar sind
  try {
    const documentTranslations = require('../../../i18n/de/document.json');
    if (documentTranslations && documentTranslations.jaynaApp) {
      Object.assign(messages.de.jaynaApp, documentTranslations.jaynaApp);
    }
  } catch (error) {
    // Ignoriere Fehler - Datei existiert möglicherweise nicht
  }
  
  // Stelle sicher, dass baseRealestate-Übersetzungen verfügbar sind
  try {
    const baseRealestateTranslations = require('../../../i18n/de/realestate/baseRealestate.json');
    if (baseRealestateTranslations) {
      const nestedModule = flattenToNested(baseRealestateTranslations);
      if (nestedModule.jaynaApp) {
        Object.assign(messages.de.jaynaApp, nestedModule.jaynaApp);
      }
    }
  } catch (error) {
    console.error('Fehler beim Laden von baseRealestate.json:', error);
  }

  // Sortiere die Dateien, damit global.json zuerst geladen wird
  const sortedKeys = context.keys().sort((a, b) => {
    if (a === './global.json') return -1;
    if (b === './global.json') return 1;
    if (a === './category.json') return -1;
    if (b === './category.json') return 1;
    return a.localeCompare(b);
  });

  sortedKeys.forEach(key => {
    try {
      const module = context(key);
      const namespace = key.replace(/^\.\/(.*)\.json$/, '$1');
      

      
      // Spezielle Behandlung für global.json
      if (namespace === 'global') {
        // Die global.json hat bereits die richtige Struktur
        Object.assign(messages.de, module);
        return;
      }
      
      // Spezielle Behandlung für category.json
      if (namespace === 'category') {
        // Die category.json hat eine jaynaApp-Struktur
        if (module.jaynaApp) {
          Object.assign(messages.de.jaynaApp, module.jaynaApp);
        }
        return;
      }
      
      // Spezielle Behandlung für baseOverview.json
      if (namespace === 'baseOverview') {
        // Die baseOverview.json hat flache Schlüssel mit jaynaApp.baseOverview Präfix
        const nestedModule = flattenToNested(module);
        if (nestedModule.jaynaApp) {
          Object.assign(messages.de.jaynaApp, nestedModule.jaynaApp);
        }
        return;
      }
      
      // Spezielle Behandlung für baseOverview.json Dateien
      if (namespace.includes('/baseOverview')) {
        // Extrahiere den Entity-Namen aus dem Pfad
        const pathParts = namespace.split('/');
        const entityName = pathParts[0]; // contract, realestate, etc.
        
        // Prüfe, ob die Datei flache Schlüssel hat (mit Punkten)
        const hasFlatKeys = Object.keys(module).some(k => k.includes('.'));
        
        if (hasFlatKeys) {
          // Konvertiere flache Schlüssel zu verschachtelten Objekten
          const nestedModule = flattenToNested(module);
          
          // Wenn es ein jaynaApp-Objekt gibt, füge es direkt hinzu
          if (nestedModule.jaynaApp) {
            // Erstelle den Entity-Namespace, falls er nicht existiert
            if (!messages.de.jaynaApp[entityName]) {
              messages.de.jaynaApp[entityName] = {};
            }
            // Füge die baseOverview Übersetzungen zum Entity-Namespace hinzu
            Object.assign(messages.de.jaynaApp[entityName], nestedModule.jaynaApp);
          }
        } else {
          // Erstelle den Entity-Namespace, falls er nicht existiert
          if (!messages.de.jaynaApp[entityName]) {
            messages.de.jaynaApp[entityName] = {};
          }
          // Füge die Übersetzungen direkt zum Entity-Namespace hinzu
          Object.assign(messages.de.jaynaApp[entityName], module);
        }
        return;
      }
      
      // Spezielle Behandlung für base-Dateien (flache Schlüssel)
      if (namespace === 'contract/baseContract' || 
          namespace === 'contact/baseContact' || 
          namespace === 'realestate/baseRealestate' || 
          namespace === 'mobility/baseMobility' || 
          namespace === 'financeaccount/baseFinanceaccount' ||
          namespace === 'document/baseDocument') {
        
        // Konvertiere flache Schlüssel zu verschachtelten Objekten
        const nestedModule = flattenToNested(module);
        
        // Wenn es ein jaynaApp-Objekt gibt, füge es direkt hinzu
        if (nestedModule.jaynaApp) {
          Object.assign(messages.de.jaynaApp, nestedModule.jaynaApp);
        }
        
        return;
      }
      
      // Spezielle Behandlung für entity-spezifische Dateien (flache Schlüssel)
      if ((namespace.startsWith('contract/') && namespace !== 'contract/baseContract') ||
          (namespace.startsWith('contact/') && namespace !== 'contact/baseContact') ||
          (namespace.startsWith('realestate/') && namespace !== 'realestate/baseRealestate') ||
          (namespace.startsWith('mobility/') && namespace !== 'mobility/baseMobility') ||
          (namespace.startsWith('financeaccount/') && namespace !== 'financeaccount/baseFinanceaccount') ||
          (namespace.startsWith('document/') && namespace !== 'document/baseDocument')) {
        
        // Konvertiere flache Schlüssel zu verschachtelten Objekten
        const nestedModule = flattenToNested(module);
        
        // Wenn es ein jaynaApp-Objekt gibt, füge es direkt hinzu
        if (nestedModule.jaynaApp) {
          Object.assign(messages.de.jaynaApp, nestedModule.jaynaApp);
        }
        
        return;
      }
      
      // Spezielle Behandlung für i18n-Dateien (flache Schlüssel)
      if (namespace.startsWith('contract/i18n-') || 
          namespace.startsWith('contact/i18n-') || 
          namespace.startsWith('realestate/i18n-') || 
          namespace.startsWith('mobility/i18n-') || 
          namespace.startsWith('financeaccount/i18n-') ||
          namespace.startsWith('document/i18n-')) {
        
        // Konvertiere flache Schlüssel zu verschachtelten Objekten
        const nestedModule = flattenToNested(module);
        
        // Füge alle Übersetzungen direkt hinzu (egal ob jaynaApp oder nicht)
        Object.assign(messages.de.jaynaApp, nestedModule);
        
        return;
      }
      
      // Prüfe, ob die Datei flache Schlüssel hat (mit Punkten)
      const hasFlatKeys = Object.keys(module).some(k => k.includes('.'));
      
      if (hasFlatKeys) {
        // Konvertiere flache Schlüssel zu verschachtelten Objekten
        const nestedModule = flattenToNested(module);
        
        // Wenn es ein jaynaApp-Objekt gibt, füge es direkt hinzu
        if (nestedModule.jaynaApp) {
          Object.assign(messages.de.jaynaApp, nestedModule.jaynaApp);
        } else {
          // Füge alle anderen Übersetzungen hinzu
          Object.assign(messages.de, nestedModule);
        }
      } else {
        // Erstelle verschachtelte Struktur basierend auf Dateipfad
        const pathParts = namespace.split('/');
        let current = messages.de;
        
        for (let i = 0; i < pathParts.length - 1; i++) {
          const part = pathParts[i];
          if (!current[part]) {
            current[part] = {};
          }
          current = current[part];
        }
        
        // Füge die Übersetzungen hinzu
        if (typeof module === 'object' && module !== null) {
          // Wenn es ein jaynaApp-Objekt gibt, füge es direkt hinzu
          if (module.jaynaApp) {
            Object.assign(messages.de.jaynaApp, module.jaynaApp);
          } else {
            // Füge alle anderen Übersetzungen hinzu
            Object.assign(current, module);
        }
        }
      }
    } catch (error) {
        }
  });
  
  return messages;
};

// Lade die Übersetzungen
const messages = loadTranslations();

// Manuell spezifische Übersetzungen hinzufügen
import mobilityBoatHybridMotorgliderTranslations from '../../../i18n/de/mobility/i18n-mobility-boat-hybrid-motorglider.json';
Object.assign(messages.de.jaynaApp, mobilityBoatHybridMotorgliderTranslations);




const i18n = createI18n({
  locale: "de",
  fallbackLocale: "de",
  messages: messages,
  globalInjection: true,
  legacy: true,
  allowComposition: true,
  numberFormats,
  datetimeFormats,
  missingWarn: false,
  fallbackWarn: false,
});

// Setze die Sprache explizit auf Deutsch
i18n.global.locale = "de";

// Lade die Übersetzungen explizit in die Instanz
i18n.global.setLocaleMessage('de', messages.de);

export default i18n;
