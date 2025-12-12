-- Fix employment form names to match actual file names
-- The files are named with hyphens, but the database has them without hyphens

-- Employee forms
UPDATE group_type SET form_name = 'contract-employment-employee-fullTime' WHERE form_name = 'contract-employmentcontract-employee-fulltime';
UPDATE group_type SET form_name = 'contract-employment-employee-halfTime' WHERE form_name = 'contract-employmentcontract-employee-halftime';
UPDATE group_type SET form_name = 'contract-employment-employee-help' WHERE form_name = 'contract-employmentcontract-employee-help';
UPDATE group_type SET form_name = 'contract-employment-employee-internship' WHERE form_name = 'contract-employmentcontract-employee-internship';
UPDATE group_type SET form_name = 'contract-employment-employee-miniJob' WHERE form_name = 'contract-employmentcontract-employee-minijob';
UPDATE group_type SET form_name = 'contract-employment-employee-terminationAgreement' WHERE form_name = 'contract-employmentcontract-employee-terminationagreement';
UPDATE group_type SET form_name = 'contract-employment-employee-workContract' WHERE form_name = 'contract-employmentcontract-employee-workcontract';

-- Official forms
UPDATE group_type SET form_name = 'contract-employment-official-fullTime' WHERE form_name = 'contract-employmentcontract-official-fulltime';
UPDATE group_type SET form_name = 'contract-employment-official-halfTime' WHERE form_name = 'contract-employmentcontract-official-halftime';
UPDATE group_type SET form_name = 'contract-employment-official-help' WHERE form_name = 'contract-employmentcontract-official-help';
UPDATE group_type SET form_name = 'contract-employment-official-internship' WHERE form_name = 'contract-employmentcontract-official-internship';
UPDATE group_type SET form_name = 'contract-employment-official-miniJob' WHERE form_name = 'contract-employmentcontract-official-minijob';
UPDATE group_type SET form_name = 'contract-employment-official-terminationAgreement' WHERE form_name = 'contract-employmentcontract-official-terminationagreement';
UPDATE group_type SET form_name = 'contract-employment-official-workContract' WHERE form_name = 'contract-employmentcontract-official-workcontract';

-- Public service forms
UPDATE group_type SET form_name = 'contract-employment-publicService-fullTime' WHERE form_name = 'contract-employmentcontract-publicservice-fulltime';
UPDATE group_type SET form_name = 'contract-employment-publicService-halfTime' WHERE form_name = 'contract-employmentcontract-publicservice-halftime';
UPDATE group_type SET form_name = 'contract-employment-publicService-help' WHERE form_name = 'contract-employmentcontract-publicservice-help';
UPDATE group_type SET form_name = 'contract-employment-publicService-internship' WHERE form_name = 'contract-employmentcontract-publicservice-internship';
UPDATE group_type SET form_name = 'contract-employment-publicService-miniJob' WHERE form_name = 'contract-employmentcontract-publicservice-minijob';
UPDATE group_type SET form_name = 'contract-employment-publicService-terminationAgreement' WHERE form_name = 'contract-employmentcontract-publicservice-terminationagreement';
UPDATE group_type SET form_name = 'contract-employment-publicService-workContract' WHERE form_name = 'contract-employmentcontract-publicservice-workcontract';

-- Show results
SELECT form_name FROM group_type WHERE form_name LIKE '%employment%' ORDER BY form_name; 