-- SQL-Skript zum Korrigieren des formName für Geländewagen in der Mobility
-- Das System versucht "car.json" zu laden, aber es sollte "mobility-passengervehicle-car-offroadvehicle.json" sein

-- 1. Zuerst schauen wir uns an, welche GroupTypes für Geländewagen existieren
SELECT id, name, form_name, entity_name 
FROM group_type 
WHERE name LIKE '%Geländewagen%' OR name LIKE '%offroad%' OR name LIKE '%off-road%';

-- 2. Korrigiere den formName für Geländewagen
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-offroadvehicle' 
WHERE name = 'Geländewagen' AND entity_name = 'mobility';

-- 3. Überprüfe das Ergebnis
SELECT id, name, form_name, entity_name 
FROM group_type 
WHERE name = 'Geländewagen' AND entity_name = 'mobility';

-- 4. Zeige alle Mobility GroupTypes mit ihren formNames
SELECT id, name, form_name, entity_name 
FROM group_type 
WHERE entity_name = 'mobility' 
ORDER BY name; 