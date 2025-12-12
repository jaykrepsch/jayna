-- Korrigiere alle formName Werte für Arbeitsverträge
-- Alle Arbeitsverträge sollten den gleichen formName haben

UPDATE group_type 
SET form_name = 'employment-contract'
WHERE form_name = 'contract-employment-employee-terminationAgreement'
AND name = 'Aufhebungsvertrag';

-- Überprüfe die Änderung
SELECT id, name, form_name, sub_category_group_id 
FROM group_type 
WHERE name = 'Aufhebungsvertrag'; 