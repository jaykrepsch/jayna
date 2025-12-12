-- Korrigiere alle falschen form_name-Werte in der group_type Tabelle
-- Basierend auf den tatsächlich existierenden Formulardefinitionen

-- 1. Girokonto (ID 260) - korrigiere zu existierender Formulardefinition
UPDATE group_type SET form_name = 'contract-bankaccount-bank-bankaccount' WHERE id = 260;

-- 2. Sparkonto (ID 261) - korrigiere zu existierender Formulardefinition  
UPDATE group_type SET form_name = 'contract-savings-savings-savingscontract' WHERE id = 261;

-- 3. Tagesgeldkonto (ID 262) - korrigiere zu existierender Formulardefinition
UPDATE group_type SET form_name = 'contract-savings-savings-overnightcontract' WHERE id = 262;

-- 4. Kreditkarte (ID 264) - korrigiere zu existierender Formulardefinition
UPDATE group_type SET form_name = 'contract-creditcard-credit-creditcardcontract' WHERE id = 264;

-- 5. Debitkarte (ID 265) - korrigiere zu existierender Formulardefinition
UPDATE group_type SET form_name = 'contract-creditcard-credit-creditcard' WHERE id = 265;

-- 6. Hausverwaltung (ID 266) - korrigiere zu existierender Formulardefinition
UPDATE group_type SET form_name = 'contract-propertymanagement-propertymanagement-propertymanagementcontract' WHERE id = 266;

-- 7. Mietverwaltung (ID 267) - korrigiere zu existierender Formulardefinition
UPDATE group_type SET form_name = 'contract-rentalmanagement-rentalmanagement-rentalmanagementcontract' WHERE id = 267;

-- 8. Annuitätendarlehen (ID 31) - korrigiere zu existierender Formulardefinition
UPDATE group_type SET form_name = 'contract-loan-realestate-annuityloan' WHERE id = 31;

-- 9. Strom (ID 17) - korrigiere zu existierender Formulardefinition
UPDATE group_type SET form_name = 'contract-energy-electric-stromcontract' WHERE id = 17;

-- 10. Gas, Fernwärme, Öl (IDs 28, 29, 30) - korrigiere zu existierender Formulardefinition
UPDATE group_type SET form_name = 'contract-energy-energy-energycontract' WHERE id IN (28, 29, 30);

-- 11. Reinigung (ID 107) - korrigiere zu existierender Formulardefinition
UPDATE group_type SET form_name = 'contract-service-cleaning-cleaningcontract' WHERE id = 107;

-- 12. Gärtner (ID 109) - korrigiere zu existierender Formulardefinition
UPDATE group_type SET form_name = 'contract-service-gardener-gardenercontract' WHERE id = 109;

-- 13. Gemeindegebühren (ID 143) - korrigiere zu existierender Formulardefinition
UPDATE group_type SET form_name = 'contract-fees-municipal-municipalcontract' WHERE id = 143;

-- 14. Hausverwaltung (ID 258) - korrigiere zu existierender Formulardefinition
UPDATE group_type SET form_name = 'contract-propertymanagement-propertymanagement-propertymanagementcontract' WHERE id = 258;

-- Zeige alle korrigierten Einträge
SELECT id, name, form_name FROM group_type WHERE id IN (260, 261, 262, 264, 265, 266, 267, 31, 17, 28, 29, 30, 107, 109, 143, 258) ORDER BY id; 