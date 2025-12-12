-- Korrigiere den formName für den Aufhebungsvertrag
-- Der formName sollte mit dem tatsächlichen Dateinamen übereinstimmen

UPDATE group_type 
SET form_name = 'contract-employmentContract-employee-terminationAgreement'
WHERE form_name = 'contract-employment-employee-terminationAgreement'
AND name = 'Aufhebungsvertrag';

-- Überprüfe die Änderung
SELECT id, name, form_name, sub_category_group_id 
FROM group_type 
WHERE name = 'Aufhebungsvertrag'; 