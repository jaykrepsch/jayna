-- SQL-Skript zum Korrigieren der employmentcontract-FormNames auf die echten Dateinamen

UPDATE group_type SET form_name = 'contract-employment-employee-fullTime' WHERE id IN (12, 5, 82);
UPDATE group_type SET form_name = 'contract-employment-employee-help' WHERE id IN (13, 6, 83);
UPDATE group_type SET form_name = 'contract-employment-employee-miniJob' WHERE id IN (3, 10, 80);
UPDATE group_type SET form_name = 'contract-employment-employee-internship' WHERE id IN (14, 7, 84);
UPDATE group_type SET form_name = 'contract-employment-employee-halfTime' WHERE id = 11;
UPDATE group_type SET form_name = 'contract-employment-employee-workContract' WHERE id IN (9, 16, 86);
UPDATE group_type SET form_name = 'contract-employment-employee-terminationAgreement' WHERE id IN (8, 15, 85); 