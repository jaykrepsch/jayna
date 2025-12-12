-- Diagnose-Skript für das Festgeldkonto-Problem
-- Führe dieses Skript in der PostgreSQL-Datenbank aus

-- 1. Überprüfe, ob das Festgeldkonto in der group_type Tabelle vorhanden ist
SELECT 'Festgeldkonto in group_type:' as info;
SELECT gt.id, gt.name, gt.form_name, gt.translation_key, scg.name as sub_category_group 
FROM group_type gt 
JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id 
WHERE gt.name LIKE '%Festgeld%' OR gt.name LIKE '%fixed%' OR gt.form_name LIKE '%fixedterm%';

-- 2. Überprüfe alle Bankkonto-bezogenen Einträge
SELECT 'Alle Bankkonto-bezogenen Einträge:' as info;
SELECT gt.id, gt.name, gt.form_name, gt.translation_key, scg.name as sub_category_group 
FROM group_type gt 
JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id 
WHERE scg.name LIKE '%Bank%' OR scg.name LIKE '%Konto%'
ORDER BY gt.name;

-- 3. Überprüfe, ob es FormConfig-Einträge für das Festgeldkonto gibt
SELECT 'FormConfig-Einträge für Festgeldkonto:' as info;
SELECT fc.id, fc.form_name, gt.name as group_type_name
FROM form_config fc
JOIN group_type gt ON fc.group_type_id = gt.id
WHERE gt.name LIKE '%Festgeld%' OR gt.name LIKE '%fixed%' OR fc.form_name LIKE '%fixedterm%';

-- 4. Überprüfe die Sub-Category-Group für Bankkonto
SELECT 'Sub-Category-Group für Bankkonto:' as info;
SELECT scg.id, scg.name, scg.translation_key, sc.name as sub_category_name
FROM sub_category_group scg
JOIN sub_category sc ON scg.sub_category_id = sc.id
WHERE scg.name LIKE '%Bank%' OR scg.name LIKE '%Konto%' OR sc.name LIKE '%Bank%';

-- 5. Zähle alle GroupTypes
SELECT 'Anzahl aller GroupTypes:' as info, COUNT(*) as count FROM group_type;

-- 6. Zeige die letzten 10 GroupTypes
SELECT 'Letzte 10 GroupTypes:' as info;
SELECT id, name, form_name, translation_key 
FROM group_type 
ORDER BY id DESC 
LIMIT 10; 