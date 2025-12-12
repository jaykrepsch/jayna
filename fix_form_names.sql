-- Korrigiere falsche formName Werte in der group_type Tabelle
-- Basierend auf den Logs, die zeigen, dass die formName Werte nicht mit den Dateinamen übereinstimmen

-- Mobility Korrekturen
UPDATE group_type 
SET form_name = 'mobility-boat-hybrid-motorglider' 
WHERE form_name = 'mobility-boat-hybrid-motorglider' 
AND translation_key = 'jaynaApp.mobility.group-type.motor-glider';

UPDATE group_type 
SET form_name = 'mobility-boat-motorized-motorboat' 
WHERE form_name = 'motorboat' 
AND translation_key = 'jaynaApp.mobility.group-type.motorboat';

-- FinanceAccount Korrekturen
UPDATE group_type 
SET form_name = 'financeaccount-systems-paymentsystems-applepay' 
WHERE form_name = 'contract-creditcard-credit-creditcard' 
AND translation_key = 'jaynaApp.financeaccount.group-type.apple-pay';

UPDATE group_type 
SET form_name = 'financeaccount-bank-bankaccount-checkingaccount' 
WHERE form_name = 'checking-account' 
AND translation_key = 'jaynaApp.financeaccount.group-type.checking-account';

-- RealEstate Korrekturen
UPDATE group_type 
SET form_name = 'realestate-withdevelopment-garden-allotmentgarden' 
WHERE form_name = 'allotment-garden' 
AND translation_key = 'jaynaApp.realestate.group-type.allotment-garden';

-- Contract Korrekturen
UPDATE group_type 
SET form_name = 'contract-loan-support-studentloans' 
WHERE form_name = 'contract-loan-support-studentloans' 
AND translation_key = 'jaynaApp.contract.group-type.student-loans';

-- Zeige alle group_type Einträge mit ihren form_name Werten
SELECT 
    id,
    name,
    form_name,
    translation_key,
    entity_name
FROM group_type 
WHERE entity_name IN ('mobility', 'financeaccount', 'realestate', 'contract')
ORDER BY entity_name, name;
