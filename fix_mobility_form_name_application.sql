-- SQL-Skript zum Korrigieren des formName für Geländewagen
-- Auszuführen über H2 Console oder pgAdmin

-- 1. Zeige alle Mobility GroupTypes
SELECT id, name, form_name, entity_name 
FROM group_type 
WHERE entity_name = 'mobility' 
ORDER BY name;

-- 2. Finde den Geländewagen-Eintrag
SELECT id, name, form_name, entity_name 
FROM group_type 
WHERE name LIKE '%Geländewagen%' OR name LIKE '%offroad%' OR name LIKE '%off-road%';

-- 3. Korrigiere den formName (ersetze die ID durch die tatsächliche ID aus Schritt 2)
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