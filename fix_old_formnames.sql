-- SQL-Skript zur Korrektur veralteter formNames in der Datenbank
-- Diese Updates stellen sicher, dass alle formNames der neuen Hierarchie-Struktur entsprechen

-- Mobility Updates
UPDATE group_type SET form_name = 'mobility-bike-bike-city' WHERE form_name = 'bicycle' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-ebike-ecity' WHERE form_name = 'ebike' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-pedelec-pcity' WHERE form_name = 'pedelec' AND entity_name = 'mobility';

-- Contract Updates
UPDATE group_type SET form_name = 'contract-employment-employee-fulltime' WHERE form_name = 'employment-contract' AND entity_name = 'contract';
UPDATE group_type SET form_name = 'contract-energy-photovoltaic-electricity' WHERE form_name = 'energy' AND entity_name = 'contract';

-- Real Estate Updates
UPDATE group_type SET form_name = 'realestate-withoutdevelopment-ground-meadow' WHERE form_name = 'meadow' AND entity_name = 'realestate';

-- Finance Account Updates (falls vorhanden)
UPDATE group_type SET form_name = 'financeaccount-bank-bankaccount-checkingaccount' WHERE form_name = 'checkingaccount' AND entity_name = 'financeaccount';
UPDATE group_type SET form_name = 'financeaccount-bank-bankaccount-savingaccount' WHERE form_name = 'savingaccount' AND entity_name = 'financeaccount';

-- Zusätzliche Mobility Updates für häufige Probleme
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-car' WHERE form_name = 'car' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-naked' WHERE form_name = 'motorcycle' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-bike-mtb' WHERE form_name = 'mountainbike' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-bike-racingbike' WHERE form_name = 'racingbike' AND entity_name = 'mobility';

-- Kontaktiere Updates
UPDATE group_type SET form_name = 'contact-contact-contact-singlecontact' WHERE form_name = 'singlecontact' AND entity_name = 'contact';
UPDATE group_type SET form_name = 'contact-contact-contact-contactgroup' WHERE form_name = 'contactgroup' AND entity_name = 'contact';

-- Zeige alle verbleibenden problematischen formNames
SELECT 
    gt.name as group_type_name,
    gt.form_name as current_form_name,
    gt.entity_name,
    c.name as category_name,
    sc.name as subcategory_name,
    scg.name as subcategorygroup_name
FROM group_type gt
JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id
JOIN sub_category sc ON scg.sub_category_id = sc.id
JOIN category c ON sc.category_id = c.id
WHERE gt.form_name NOT LIKE CONCAT(gt.entity_name, '-%')
ORDER BY gt.entity_name, gt.name;
