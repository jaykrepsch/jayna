import { watch } from "vue";
import { useRoute } from "vue-router";
import { useGetService } from "@/composables/utils";
import CategoryService from "@/services/category.service";
import SubCategoryService from "@/services/sub-category.service";
import SubCategoryGroupService from "@/services/sub-category-group.service";
import GroupTypeService from "@/services/group-type.service";
import FormConfigService from "@/services/form-config.service";

const usePrepare = (
  category,
  subCategory,
  subCategoryGroup,
  groupType,
  formConfig,
  service,
  entityData
) => {
  const route = useRoute();

  retrieve(
    route,
    category,
    subCategory,
    subCategoryGroup,
    groupType,
    formConfig,
    service,
    entityData
  );
};

const retrieve = (
  route,
  category,
  subCategory,
  subCategoryGroup,
  groupType,
  formConfig,
  service,
  entityData
) => {
  
  
  CategoryService.find(route.params.id).then((data) => {
    category.value = data;
    
  });

  if (subCategory) {
    SubCategoryService.find(route.params.subid).then((data) => {
      subCategory.value = data;

    });
  }

  if (subCategoryGroup && route.params.subgrpid) {
    SubCategoryGroupService.find(route.params.subgrpid).then((data) => {
      subCategoryGroup.value = data;

    }).catch((err) => {
      console.warn('SubCategoryGroup not found:', err);
    });
  }

  if (groupType && route.params.grptpid) {
    GroupTypeService.find(route.params.grptpid).then((data) => {
      groupType.value = data;

    }).catch((err) => {
      console.warn('GroupType not found:', err);
    });

    watch(groupType, () => {
      if (!groupType.value) {
        return;
      }
      if (groupType.value?.creationType === "CUSTOM") {
        console.log('Loading CUSTOM GroupType:', groupType.value);
        
        FormConfigService.get(
          `/api/group-types/${groupType.value.id}/form-configs`
        )
          .then((res) => {
            console.log('FormConfig loaded for CUSTOM GroupType:', res);
            formConfig.value = res;
          })
          .catch((err) => {
            console.error('FormConfigService Fehler:', err);
            // Fallback: Versuche eine Standard-Formulardefinition zu laden
            const entity = groupType.value?.entityName || 'contract';
            import(`@/shared/form-definitions/${entity}/${entity}.json`)
              .then((fallback) => {
                formConfig.value = fallback.default || fallback;
              })
              .catch(() => {
                console.error('Fallback form definition loading failed');
              });
          });

        useGetService("custom-form-input").then((data) => {
          console.log('CustomFormInput service loaded:', data);
          service.value = data.default;
          if (entityData != null && route.params.entityid) {
            service.value.find(route.params.entityid).then((data) => {
              console.log('Entity data loaded for CUSTOM GroupType:', data);
              entityData.value = data.payload;
            }).catch((err) => {
              console.error('Error loading entity data for CUSTOM GroupType:', err);
            });
          }
        }).catch((err) => {
          console.error('Error loading CustomFormInput service:', err);
        });
      } else {
        // Generiere IMMER den Formnamen aus der Hierarchie und lade die passende Formdefinition
        const formDefinitionName = generateFormNameFromHierarchy(
          groupType.value?.subCategoryGroup?.subCategory?.category,
          groupType.value?.subCategoryGroup?.subCategory,
          groupType.value?.subCategoryGroup,
          groupType.value
        );

        if (formDefinitionName) {
          // Formname für konsistente Titel/Anzeige auch in groupType setzen
          groupType.value.formName = formDefinitionName;

          import(
            `@/shared/form-definitions/${groupType.value?.entityName}/${formDefinitionName}.json`
          )
            .then((data) => {
              formConfig.value = data.default || data;
            })
            .catch(() => {
              // Fallback: generisches Entity-Formular laden
              const entity = groupType.value?.entityName || 'contract';
              import(`@/shared/form-definitions/${entity}/${entity}.json`)
                .then((fallback) => {
                  formConfig.value = fallback.default || fallback;
                })
                .catch(() => {
                  // Als letzte Instanz: leer lassen; UI zeigt Lade-/Fehlerzustand
                });
            });
        } else {
          // Kein Name generierbar → generisches Entity-Formular
          const entity = groupType.value?.entityName || 'contract';
          import(`@/shared/form-definitions/${entity}/${entity}.json`).then((data) => {
            formConfig.value = data.default || data;
          }).catch(() => {
            // no-op
          });
        }

        if (groupType.value?.entityName) {
          useGetService(groupType.value.entityName).then((data) => {
            service.value = data.default;
            if (entityData != null && route.params.entityid) {
              service.value.find(route.params.entityid).then((data) => {
                entityData.value = data;
              }).catch((err) => {
                console.error('Error loading entity data:', err);
              });
            }
          }).catch((err) => {
            console.error('Error loading service for entity:', groupType.value?.entityName, err);
          });
        } else {
          console.warn('Skipped loading service: groupType.entityName is undefined');
        }
      }
    }, { immediate: true });
  } else {
    watch(category, () => {
      if (category.value?.entityName) {
        import(
          `@/shared/form-definitions/${category.value.entityName}/${category.value.entityName}.json`
        ).then((data) => {
          formConfig.value = data.default || data;
        }).catch((err) => {
          // Fallback: Versuche eine Standard-Formulardefinition zu laden
          if (category.value.entityName === 'contact') {
            import('@/shared/form-definitions/contact/contact.json').then((data) => {
              formConfig.value = data.default || data;
            }).catch((fallbackErr) => {
              
            });
          }
        });
        useGetService(category.value.entityName).then((data) => {
          service.value = data.default;
          if (entityData != null && route.params.entityid) {
            service.value.find(route.params.entityid).then((data) => {
              entityData.value = data;
            });
          }
        });
      }
    });
  }
};

// Generiert den formName basierend auf der Hierarchie-Kombination
const generateFormNameFromHierarchy = (category, subCategory, subCategoryGroup, groupType) => {
  if (!groupType) {
    return null;
  }

  // IGNORIERE das formName aus der Datenbank und generiere IMMER aus der Hierarchie
  // Das stellt sicher, dass die englische Hierarchie-Struktur verwendet wird
  

  // Generiere formName aus der Hierarchie mit translationKeys
  const entityName = groupType.entityName || 'contract';
  let formName = entityName;
  
  if (subCategory) {
    // Verwende translationKey für englischen Namen
    const subCategoryName = extractEnglishNameFromTranslationKey(subCategory.translationKey) || normalizeName(subCategory.name, 'subcategory');
    formName += `-${subCategoryName}`;
  }
  
  if (subCategoryGroup) {
    // Verwende translationKey für englischen Namen
    const subCategoryGroupName = extractEnglishNameFromTranslationKey(subCategoryGroup.translationKey) || normalizeName(subCategoryGroup.name, 'subcategorygroup');
    formName += `-${subCategoryGroupName}`;
  }
  
  if (groupType) {
    // Verwende translationKey für englischen Namen
    const groupTypeName = extractEnglishNameFromTranslationKey(groupType.translationKey) || normalizeName(groupType.name, 'grouptype');
    formName += `-${groupTypeName}`;
  }
  
  return formName;
};

// Normalisiert Namen für Datei-Namenskonvention (kebab-case)
const normalizeName = (name, type = 'default') => {
  if (!name) return '';
  
  // Konvertiere zu kebab-case und normalisiere
  return name
    .toLowerCase()
    .replace(/\s+/g, '-') // Leerzeichen zu Bindestrichen
    .replace(/[^a-z0-9-]/g, '') // Entferne Sonderzeichen
    .replace(/-+/g, '-') // Mehrere Bindestriche zu einem
    .replace(/^-|-$/g, ''); // Entferne führende/abschließende Bindestriche
};

// Extrahiert den englischen Namen aus dem translationKey
const extractEnglishNameFromTranslationKey = (translationKey) => {
  if (!translationKey) return null;
  
  
  
  // Beispiele für translationKeys:
  // "jaynaApp.contract.sub-category.employment-contract" -> "employment"
  // "jaynaApp.realestate.sub-category-group.farmland" -> "farmland"
  // "jaynaApp.contract.group-type.termination-agreement" -> "termination-agreement"
  
  const parts = translationKey.split('.');
  
  if (parts.length >= 4) {
    // Nehme den letzten Teil des translationKeys
    const lastPart = parts[parts.length - 1];
    
    
    
    // Spezielle Behandlung für employment-contract -> employment
    if (lastPart === 'employment-contract') {
      return 'employment';
    }
    
    // Spezielle Behandlung für termination-agreement -> terminationagreement
    if (lastPart === 'termination-agreement') {
      return 'terminationagreement';
    }
    
    // Spezielle Behandlung für terminationcontract -> terminationagreement
    if (lastPart === 'terminationcontract') {
      return 'terminationagreement';
    }
    
    // Spezielle Behandlung für motor-glider -> motorglider
    if (lastPart === 'motor-glider') {
      return 'motorglider';
    }
    
    // Spezielle Behandlung für FinanceAccount
    if (lastPart === 'apple-pay') {
      return 'systems-paymentsystems-applepay';
    }
    if (lastPart === 'google-pay') {
      return 'systems-paymentsystems-googlepay';
    }
    if (lastPart === 'paypal') {
      return 'systems-paymentsystems-paypal';
    }
    if (lastPart === 'klarna') {
      return 'systems-paymentsystems-klarna';
    }
    if (lastPart === 'skrill') {
      return 'systems-paymentsystems-skrill';
    }
    if (lastPart === 'stripe') {
      return 'systems-paymentsystems-stripe';
    }
    if (lastPart === 'samsung') {
      return 'credit-creditcard-samsung';
    }
    if (lastPart === 'prepaid') {
      return 'credit-creditcard-prepaid';
    }
    if (lastPart === 'online-virtual') {
      return 'credit-creditcard-onlinevirtual';
    }
    if (lastPart === 'revolving') {
      return 'credit-creditcard-revolving';
    }
    if (lastPart === 'charge') {
      return 'credit-creditcard-charge';
    }
    if (lastPart === 'debit') {
      return 'credit-creditcard-debit';
    }
    if (lastPart === 'checkingaccount') {
      return 'bank-bankaccount-checkingaccount';
    }
    if (lastPart === 'savingaccount') {
      return 'bank-bankaccount-savingaccount';
    }
    
    // Entferne Bindestriche für Formulardefinitionen
    const normalizedName = lastPart.replace(/-/g, '');
    
    return normalizedName;
  }
  
  return null;
};

export { usePrepare, extractEnglishNameFromTranslationKey };
