-- SQL-Skript zum Anlegen von Arbeitsvertrag-Sparten mit Bezeichnung "1"
-- Alle Sparten für die Arbeitsvertrag-Gruppierungen werden mit der Bezeichnung "1" erstellt

-- 1. Sparten für "Angestellter" (sub_category_group_id = 1)
INSERT INTO group_type (name, created_date, translation_key, sub_category_group_id, entity_name, abbreviation, form_name) VALUES
('1', CURRENT_DATE, 'jaynaApp.contract.group-type.mini-job', 1, 'contract', 'MINI', 'contract-employment-employee-minijob'),
('1', CURRENT_DATE, 'jaynaApp.contract.group-type.half-time', 1, 'contract', 'TEIL', 'contract-employment-employee-parttime'),
('1', CURRENT_DATE, 'jaynaApp.contract.group-type.full-time', 1, 'contract', 'VOLL', 'contract-employment-employee-fulltime'),
('1', CURRENT_DATE, 'jaynaApp.contract.group-type.help', 1, 'contract', 'AUSH', 'contract-employment-employee-help'),
('1', CURRENT_DATE, 'jaynaApp.contract.group-type.internship', 1, 'contract', 'PRAK', 'contract-employment-employee-internship'),
('1', CURRENT_DATE, 'jaynaApp.contract.group-type.termination-agreement', 1, 'contract', 'AUFH', 'contract-termination'),
('1', CURRENT_DATE, 'jaynaApp.contract.group-type.work-contract', 1, 'contract', 'WERK', 'contract-employment-employee-work');

-- 2. Sparten für "öffentlicher Dienst" (sub_category_group_id = 2)
INSERT INTO group_type (name, created_date, translation_key, sub_category_group_id, entity_name, abbreviation, form_name) VALUES
('1', CURRENT_DATE, 'jaynaApp.contract.group-type.mini-job', 2, 'contract', 'MINI', 'contract-employment-publicservice-minijob'),
('1', CURRENT_DATE, 'jaynaApp.contract.group-type.half-time', 2, 'contract', 'TEIL', 'contract-employment-publicservice-parttime'),
('1', CURRENT_DATE, 'jaynaApp.contract.group-type.full-time', 2, 'contract', 'VOLL', 'contract-employment-publicservice-fulltime'),
('1', CURRENT_DATE, 'jaynaApp.contract.group-type.help', 2, 'contract', 'AUSH', 'contract-employment-publicservice-help'),
('1', CURRENT_DATE, 'jaynaApp.contract.group-type.internship', 2, 'contract', 'PRAK', 'contract-employment-publicservice-internship'),
('1', CURRENT_DATE, 'jaynaApp.contract.group-type.termination-agreement', 2, 'contract', 'AUFH', 'contract-termination'),
('1', CURRENT_DATE, 'jaynaApp.contract.group-type.work-contract', 2, 'contract', 'WERK', 'contract-employment-publicservice-work');

-- 3. Sparten für "Beamter" (sub_category_group_id = 3)
INSERT INTO group_type (name, created_date, translation_key, sub_category_group_id, entity_name, abbreviation, form_name) VALUES
('1', CURRENT_DATE, 'jaynaApp.contract.group-type.mini-job', 3, 'contract', 'MINI', 'contract-employment-official-minijob'),
('1', CURRENT_DATE, 'jaynaApp.contract.group-type.half-time', 3, 'contract', 'TEIL', 'contract-employment-official-parttime'),
('1', CURRENT_DATE, 'jaynaApp.contract.group-type.full-time', 3, 'contract', 'VOLL', 'contract-employment-official-fulltime'),
('1', CURRENT_DATE, 'jaynaApp.contract.group-type.help', 3, 'contract', 'AUSH', 'contract-employment-official-help'),
('1', CURRENT_DATE, 'jaynaApp.contract.group-type.internship', 3, 'contract', 'PRAK', 'contract-employment-official-internship'),
('1', CURRENT_DATE, 'jaynaApp.contract.group-type.termination-agreement', 3, 'contract', 'AUFH', 'contract-termination'),
('1', CURRENT_DATE, 'jaynaApp.contract.group-type.work-contract', 3, 'contract', 'WERK', 'contract-employment-official-work');

-- Bestätigung der erstellten Datensätze
SELECT 
    'Arbeitsvertrag-Sparten erstellt:' as info, 
    COUNT(*) as anzahl 
FROM group_type 
WHERE sub_category_group_id IN (1, 2, 3) AND name = '1';

-- Anzeige der erstellten Sparten
SELECT 
    gt.id,
    gt.name as sparte_bezeichnung,
    scg.name as gruppierung,
    gt.translation_key,
    gt.abbreviation,
    gt.form_name
FROM group_type gt
JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id
WHERE gt.sub_category_group_id IN (1, 2, 3) AND gt.name = '1'
ORDER BY scg.id, gt.id; 