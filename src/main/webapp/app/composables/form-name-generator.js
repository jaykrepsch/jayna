import { extractEnglishNameFromTranslationKey } from '@/composables/entity-helper';

// Cache für verfügbare Form-Definitionen
const formDefinitionCache = new Map();

// Lade verfügbare Form-Definitionen für eine Entity
const loadAvailableFormDefinitions = async (entityName) => {
  if (formDefinitionCache.has(entityName)) {
    return formDefinitionCache.get(entityName);
  }

  try {
    // Dynamisch alle verfügbaren Form-Definitionen laden
    const formDefinitions = [];
    
    // Verwende require.context für Webpack (kompatibel mit JHipster)
    const formDefinitionModules = require.context('@/shared/form-definitions', true, /\.json$/);
    
    formDefinitionModules.keys().forEach(path => {
      if (path.includes(`/${entityName}/`) && !path.includes('base')) {
        const formName = path.split('/').pop().replace('.json', '');
        formDefinitions.push(formName);
      }
    });
    
    formDefinitionCache.set(entityName, formDefinitions);
    return formDefinitions;
  } catch (error) {
    console.warn(`Fehler beim Laden der Form-Definitionen für ${entityName}:`, error);
    return [];
  }
};

// Normalisiert Namen für Datei-Namenskonvention (kebab-case)
const normalizeName = (name) => {
  if (!name) return '';
  
  return name
    .toLowerCase()
    .replace(/\s+/g, '-') // Leerzeichen zu Bindestrichen
    .replace(/[^a-z0-9-]/g, '') // Entferne Sonderzeichen
    .replace(/-+/g, '-') // Mehrere Bindestriche zu einem
    .replace(/^-|-$/g, ''); // Entferne führende/abschließende Bindestriche
};

// Generiert den formName basierend auf der Hierarchie-Kombination
const generateFormNameFromHierarchy = (category, subCategory, subCategoryGroup, groupType) => {
  console.log('generateFormNameFromHierarchy called with:', {
    category: category?.name,
    subCategory: subCategory?.name,
    subCategoryGroup: subCategoryGroup?.name,
    groupType: groupType?.name
  });

  if (!groupType) {
    console.log('No groupType provided, returning null');
    return null;
  }

  // Verwende IMMER den formName aus der Datenbank, wenn er verfügbar ist
  if (groupType.formName && groupType.formName !== 'document') {
    console.log('Using formName from database:', groupType.formName);
    return groupType.formName;
  }

  // Fallback: Generiere formName aus der Hierarchie mit translationKeys
  const entityName = groupType.entityName || 'document';
  let formName = entityName;
  
  console.log('Starting with entityName:', entityName);
  
  if (subCategory) {
    // Verwende translationKey für englischen Namen
    let subCategoryName = extractEnglishNameFromTranslationKey(subCategory.translationKey) || normalizeName(subCategory.name);
    // Alias-Korrekturen für fehlende Bindestriche
    const subAliases = {
      'financesinsurance': 'finances-insurance',
      'workeducation': 'work-education',
      'housingproperty': 'housing-property',
      'energyutilities': 'energy-utilities',
      'communicationeveryday': 'communication-everyday',
      'lawadministration': 'law-administration',
    };
    subCategoryName = subAliases[subCategoryName] || subCategoryName;
    formName += `-${subCategoryName}`;
    console.log('Added subCategory:', subCategoryName, 'formName now:', formName);
  }
  
  if (subCategoryGroup) {
    // Verwende translationKey für englischen Namen
    const subCategoryGroupName = extractEnglishNameFromTranslationKey(subCategoryGroup.translationKey) || normalizeName(subCategoryGroup.name);
    formName += `-${subCategoryGroupName}`;
    console.log('Added subCategoryGroup:', subCategoryGroupName, 'formName now:', formName);
  }
  
  if (groupType) {
    // Verwende translationKey für englischen Namen
    const groupTypeName = extractEnglishNameFromTranslationKey(groupType.translationKey) || normalizeName(groupType.name);
    formName += `-${groupTypeName}`;
    console.log('Added groupType:', groupTypeName, 'formName now:', formName);
  }
  
  console.log('Final generated formName:', formName);
  return formName;
};

// Generische Funktion zur Bestimmung des korrekten Form-Namens
const getCorrectFormName = async (groupType, entityName) => {
  if (!groupType?.translationKey) return null;

  // Lade verfügbare Formulardefinitionen
  const availableForms = await loadAvailableFormDefinitions(entityName);
  console.log(`Verfügbare Formulardefinitionen für ${entityName}:`, availableForms);
  
  // Prüfe, ob der formName aus der Datenbank generisch ist (z.B. "document", "contract", etc.)
  const isGenericFormName = groupType.formName && 
    (groupType.formName === entityName || 
     groupType.formName === 'document' || 
     groupType.formName === 'contract' || 
     groupType.formName === 'contact' ||
     groupType.formName === 'realestate' ||
     groupType.formName === 'mobility' ||
     groupType.formName === 'financeaccount');
  
  // Mappe veraltete/abweichende DB-formNames auf vorhandene Dateien
  const dbFormNameAliases = {
    'employment-contract': 'contract-employment-employee-terminationcontract',
    'termination-agreement': 'contract-employment-employee-terminationagreement'
  };

  const aliasedDbFormName = dbFormNameAliases[groupType.formName] || groupType.formName;

  // Verwende den formName aus der Datenbank (inkl. Alias), wenn er nicht generisch ist
  if (aliasedDbFormName && !isGenericFormName) {
    console.log(`Prüfe formName aus Datenbank: ${groupType.formName}`);
    
    // Prüfe, ob der formName aus der Datenbank existiert
    if (availableForms.includes(aliasedDbFormName)) {
      console.log(`Verwende formName aus Datenbank: ${aliasedDbFormName}`);
      return aliasedDbFormName;
    }
    
    // Wenn nicht, versuche eine ähnliche zu finden
    const similarForm = availableForms.find(form => 
      form.includes(aliasedDbFormName.split('-').slice(-1)[0]) || // Letzter Teil
      form.includes(aliasedDbFormName.split('-').slice(-2).join('-')) // Letzte zwei Teile
    );
    
    if (similarForm) {
      console.warn(`Form-Definition ${groupType.formName} nicht gefunden, verwende ähnliche: ${similarForm}`);
      return similarForm;
    }
  }

  // Wenn der formName generisch ist oder nicht existiert, generiere aus der Hierarchie
  console.log(`Generischer oder fehlender formName (${groupType.formName}), generiere aus Hierarchie`);
  
  // Spezielle Behandlung für bekannte groupTypes VOR der Hierarchie-Generierung
  if (groupType.name === 'Zahlungsnachweis' || groupType.name === 'payment-proof') {
    const paymentProofForm = availableForms.find(form => form.includes('payment-proof'));
    if (paymentProofForm) {
      console.log(`Verwende spezielle Form für Zahlungsnachweis: ${paymentProofForm}`);
      return paymentProofForm;
    }
  }

  if (groupType.name === 'SEPA-Mandat' || groupType.name === 'sepa-mandate') {
    const sepaForm = availableForms.find(form => form.includes('sepa-mandate'));
    if (sepaForm) {
      console.log(`Verwende spezielle Form für SEPA-Mandat: ${sepaForm}`);
      return sepaForm;
    }
  }

  // Spezielle Behandlung für Gasrechnungen
  if (groupType.name === 'Gasrechnung' || groupType.name === 'gasbill') {
    const gasBillForm = availableForms.find(form => form.includes('gas-bill'));
    if (gasBillForm) {
      console.log(`Verwende spezielle Form für Gasrechnung: ${gasBillForm}`);
      return gasBillForm;
    }
  }
  
  const generatedFormName = generateFormNameFromHierarchy(
    groupType.subCategoryGroup?.subCategory?.category,
    groupType.subCategoryGroup?.subCategory,
    groupType.subCategoryGroup,
    groupType
  );
  
  console.log(`Generierter formName aus Hierarchie: ${generatedFormName}`);

  // Validiere, ob die generierte Form-Definition existiert
  if (availableForms.includes(generatedFormName)) {
    console.log(`Verwende generierten formName: ${generatedFormName}`);
    return generatedFormName;
  }

  // Fallback: Suche nach ähnlichen Form-Namen basierend auf dem groupType
  const groupTypeName = extractEnglishNameFromTranslationKey(groupType.translationKey);
  console.log(`GroupType Name aus translationKey: ${groupTypeName}`);
  
  if (groupTypeName) {
    const similarForm = availableForms.find(form => 
      form.includes(groupTypeName) || // Suche nach groupType Name
      form.includes(groupTypeName.replace('-', '')) // Suche auch ohne Bindestriche
    );
    
    if (similarForm) {
      console.warn(`Form-Definition ${generatedFormName} nicht gefunden, verwende ähnliche: ${similarForm}`);
      return similarForm;
    }
  }

  // Spezielle Behandlung für bekannte groupTypes
  if (groupType.name === 'Zahlungsnachweis' || groupType.name === 'payment-proof') {
    const paymentProofForm = availableForms.find(form => form.includes('payment-proof'));
    if (paymentProofForm) {
      console.log(`Verwende spezielle Form für Zahlungsnachweis: ${paymentProofForm}`);
      return paymentProofForm;
    }
  }

  if (groupType.name === 'SEPA-Mandat' || groupType.name === 'sepa-mandate') {
    const sepaForm = availableForms.find(form => form.includes('sepa-mandate'));
    if (sepaForm) {
      console.log(`Verwende spezielle Form für SEPA-Mandat: ${sepaForm}`);
      return sepaForm;
    }
  }

  // Letzter Fallback: Verwende die erste verfügbare Form
  if (availableForms.length > 0) {
    console.warn(`Keine passende Form-Definition gefunden für ${generatedFormName}, verwende Standard: ${availableForms[0]}`);
    return availableForms[0];
  }

  return null;
};

// Validiert, ob eine Form-Definition existiert
const validateFormDefinition = async (formName, entityName) => {
  const availableForms = await loadAvailableFormDefinitions(entityName);
  return availableForms.includes(formName);
};

// Generiert eine Liste aller verfügbaren Form-Namen für eine Entity
const getAvailableFormNames = async (entityName) => {
  return await loadAvailableFormDefinitions(entityName);
};

export {
  getCorrectFormName,
  generateFormNameFromHierarchy,
  validateFormDefinition,
  getAvailableFormNames,
  normalizeName
};
