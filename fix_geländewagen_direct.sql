-- SQL-Skript zum Korrigieren des formName für Geländewagen
-- Auszuführen über ein SQL-Tool (pgAdmin, DBeaver, etc.)

-- 1. Zeige alle Mobility GroupTypes
SELECT id, name, form_name, entity_name 
FROM group_type 
WHERE entity_name = 'mobility' 
ORDER BY name;

-- 2. Finde den Geländewagen-Eintrag
SELECT id, name, form_name, entity_name 
FROM group_type 
WHERE name LIKE '%Geländewagen%' OR name LIKE '%offroad%' OR name LIKE '%off-road%';

-- 3. Korrigiere den formName für Geländewagen
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-offroadvehicle' 
WHERE name = 'Geländewagen' AND entity_name = 'mobility';

-- 4. Überprüfe das Ergebnis
SELECT id, name, form_name, entity_name 
FROM group_type 
WHERE name = 'Geländewagen' AND entity_name = 'mobility';

-- 5. Zeige alle Mobility GroupTypes nach der Korrektur
SELECT id, name, form_name, entity_name 
FROM group_type 
WHERE entity_name = 'mobility' 
ORDER BY name; 