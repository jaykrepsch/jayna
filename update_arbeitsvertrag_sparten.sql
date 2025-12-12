-- SQL-Skript zum Aktualisieren bestehender Arbeitsvertrag-Sparten
-- Aktualisiert die form_name Felder auf die neuen spezifischen Formulardefinitionen

-- 1. Aktualisierung für "Angestellter" (sub_category_group_id = 1)
UPDATE group_type SET form_name = 'contract-employment-employee-minijob' 
WHERE sub_category_group_id = 1 AND abbreviation = 'MINI';

UPDATE group_type SET form_name = 'contract-employment-employee-parttime' 
WHERE sub_category_group_id = 1 AND abbreviation = 'TEIL';

UPDATE group_type SET form_name = 'contract-employment-employee-fulltime' 
WHERE sub_category_group_id = 1 AND abbreviation = 'VOLL';

UPDATE group_type SET form_name = 'contract-employment-employee-help' 
WHERE sub_category_group_id = 1 AND abbreviation = 'AUSH';

UPDATE group_type SET form_name = 'contract-employment-employee-internship' 
WHERE sub_category_group_id = 1 AND abbreviation = 'PRAK';

UPDATE group_type SET form_name = 'contract-termination' 
WHERE sub_category_group_id = 1 AND abbreviation = 'AUFH';

UPDATE group_type SET form_name = 'contract-employment-employee-work' 
WHERE sub_category_group_id = 1 AND abbreviation = 'WERK';

-- 2. Aktualisierung für "öffentlicher Dienst" (sub_category_group_id = 2)
UPDATE group_type SET form_name = 'contract-employment-publicservice-minijob' 
WHERE sub_category_group_id = 2 AND abbreviation = 'MINI';

UPDATE group_type SET form_name = 'contract-employment-publicservice-parttime' 
WHERE sub_category_group_id = 2 AND abbreviation = 'TEIL';

UPDATE group_type SET form_name = 'contract-employment-publicservice-fulltime' 
WHERE sub_category_group_id = 2 AND abbreviation = 'VOLL';

UPDATE group_type SET form_name = 'contract-employment-publicservice-help' 
WHERE sub_category_group_id = 2 AND abbreviation = 'AUSH';

UPDATE group_type SET form_name = 'contract-employment-publicservice-internship' 
WHERE sub_category_group_id = 2 AND abbreviation = 'PRAK';

UPDATE group_type SET form_name = 'contract-termination' 
WHERE sub_category_group_id = 2 AND abbreviation = 'AUFH';

UPDATE group_type SET form_name = 'contract-employment-publicservice-work' 
WHERE sub_category_group_id = 2 AND abbreviation = 'WERK';

-- 3. Aktualisierung für "Beamter" (sub_category_group_id = 3)
UPDATE group_type SET form_name = 'contract-employment-official-minijob' 
WHERE sub_category_group_id = 3 AND abbreviation = 'MINI';

UPDATE group_type SET form_name = 'contract-employment-official-parttime' 
WHERE sub_category_group_id = 3 AND abbreviation = 'TEIL';

UPDATE group_type SET form_name = 'contract-employment-official-fulltime' 
WHERE sub_category_group_id = 3 AND abbreviation = 'VOLL';

UPDATE group_type SET form_name = 'contract-employment-official-help' 
WHERE sub_category_group_id = 3 AND abbreviation = 'AUSH';

UPDATE group_type SET form_name = 'contract-employment-official-internship' 
WHERE sub_category_group_id = 3 AND abbreviation = 'PRAK';

UPDATE group_type SET form_name = 'contract-termination' 
WHERE sub_category_group_id = 3 AND abbreviation = 'AUFH';

UPDATE group_type SET form_name = 'contract-employment-official-work' 
WHERE sub_category_group_id = 3 AND abbreviation = 'WERK';

-- Bestätigung der aktualisierten Datensätze
SELECT 
    'Aktualisierte Arbeitsvertrag-Sparten:' as info, 
    COUNT(*) as anzahl 
FROM group_type 
WHERE sub_category_group_id IN (1, 2, 3) AND name = '1';

-- Anzeige der aktualisierten Sparten
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