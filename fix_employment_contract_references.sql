-- SQL-Skript zum Beheben aller employment-contract Referenzen
-- Aktualisiert alle Datensätze, die noch 'employment-contract' als form_name haben

-- 1. Aktualisierung für "Angestellter" (sub_category_group_id = 1)
UPDATE group_type SET form_name = 'contract-employment-employee-minijob' 
WHERE sub_category_group_id = 1 AND abbreviation = 'MINI' AND form_name = 'employment-contract';

UPDATE group_type SET form_name = 'contract-employment-employee-parttime' 
WHERE sub_category_group_id = 1 AND abbreviation = 'TEIL' AND form_name = 'employment-contract';

UPDATE group_type SET form_name = 'contract-employment-employee-fulltime' 
WHERE sub_category_group_id = 1 AND abbreviation = 'VOLL' AND form_name = 'employment-contract';

UPDATE group_type SET form_name = 'contract-employment-employee-help' 
WHERE sub_category_group_id = 1 AND abbreviation = 'AUSH' AND form_name = 'employment-contract';

UPDATE group_type SET form_name = 'contract-employment-employee-internship' 
WHERE sub_category_group_id = 1 AND abbreviation = 'PRAK' AND form_name = 'employment-contract';

UPDATE group_type SET form_name = 'contract-employment-employee-work' 
WHERE sub_category_group_id = 1 AND abbreviation = 'WERK' AND form_name = 'employment-contract';

-- 2. Aktualisierung für "öffentlicher Dienst" (sub_category_group_id = 2)
UPDATE group_type SET form_name = 'contract-employment-publicservice-minijob' 
WHERE sub_category_group_id = 2 AND abbreviation = 'MINI' AND form_name = 'employment-contract';

UPDATE group_type SET form_name = 'contract-employment-publicservice-parttime' 
WHERE sub_category_group_id = 2 AND abbreviation = 'TEIL' AND form_name = 'employment-contract';

UPDATE group_type SET form_name = 'contract-employment-publicservice-fulltime' 
WHERE sub_category_group_id = 2 AND abbreviation = 'VOLL' AND form_name = 'employment-contract';

UPDATE group_type SET form_name = 'contract-employment-publicservice-help' 
WHERE sub_category_group_id = 2 AND abbreviation = 'AUSH' AND form_name = 'employment-contract';

UPDATE group_type SET form_name = 'contract-employment-publicservice-internship' 
WHERE sub_category_group_id = 2 AND abbreviation = 'PRAK' AND form_name = 'employment-contract';

UPDATE group_type SET form_name = 'contract-employment-publicservice-work' 
WHERE sub_category_group_id = 2 AND abbreviation = 'WERK' AND form_name = 'employment-contract';

-- 3. Aktualisierung für "Beamter" (sub_category_group_id = 3)
UPDATE group_type SET form_name = 'contract-employment-official-minijob' 
WHERE sub_category_group_id = 3 AND abbreviation = 'MINI' AND form_name = 'employment-contract';

UPDATE group_type SET form_name = 'contract-employment-official-parttime' 
WHERE sub_category_group_id = 3 AND abbreviation = 'TEIL' AND form_name = 'employment-contract';

UPDATE group_type SET form_name = 'contract-employment-official-fulltime' 
WHERE sub_category_group_id = 3 AND abbreviation = 'VOLL' AND form_name = 'employment-contract';

UPDATE group_type SET form_name = 'contract-employment-official-help' 
WHERE sub_category_group_id = 3 AND abbreviation = 'AUSH' AND form_name = 'employment-contract';

UPDATE group_type SET form_name = 'contract-employment-official-internship' 
WHERE sub_category_group_id = 3 AND abbreviation = 'PRAK' AND form_name = 'employment-contract';

UPDATE group_type SET form_name = 'contract-employment-official-work' 
WHERE sub_category_group_id = 3 AND abbreviation = 'WERK' AND form_name = 'employment-contract';

-- Bestätigung der aktualisierten Datensätze
SELECT 
    'Aktualisierte Datensätze:' as info, 
    COUNT(*) as anzahl 
FROM group_type 
WHERE form_name LIKE 'contract-employment-%';

-- Prüfe, ob noch employment-contract Referenzen existieren
SELECT 
    'Verbleibende employment-contract Referenzen:' as info,
    COUNT(*) as anzahl
FROM group_type 
WHERE form_name = 'employment-contract';

-- Zeige alle aktualisierten Sparten
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