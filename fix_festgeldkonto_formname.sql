-- SQL-Skript zum Korrigieren des form_name für das Festgeldkonto

-- 1. Zeige den aktuellen Zustand
SELECT 'Aktueller Zustand des Festgeldkontos:' as info;
SELECT id, name, form_name, translation_key, entity_name
FROM group_type 
WHERE name = 'Festgeldkonto';

-- 2. Korrigiere den form_name
UPDATE group_type 
SET form_name = 'contract-savings-savings-fixedtermcontract'
WHERE name = 'Festgeldkonto' 
AND form_name = 'financeaccount-bank-savingaccount-savingaccount';

-- 3. Zeige das Ergebnis
SELECT 'Festgeldkonto nach der Korrektur:' as info;
SELECT id, name, form_name, translation_key, entity_name
FROM group_type 
WHERE name = 'Festgeldkonto';

-- 4. Überprüfe, ob die korrekte Formulardefinition existiert
SELECT 'Überprüfe Formulardefinition:' as info;
SELECT 'contract-savings-savings-fixedtermcontract.json sollte existieren' as expected_file;

-- 5. Zeige alle Bankkonto-bezogenen GroupTypes zur Überprüfung
SELECT 'Alle Bankkonto-bezogenen GroupTypes:' as info;
SELECT gt.id, gt.name, gt.form_name, gt.translation_key, scg.name as sub_category_group
FROM group_type gt 
JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id 
WHERE scg.name LIKE '%Bank%' OR scg.name LIKE '%Konto%' OR gt.name LIKE '%Festgeld%'
ORDER BY gt.name; 