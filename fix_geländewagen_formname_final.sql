-- Finales SQL-Skript zur Korrektur des formName für Geländewagen
-- Das Problem: formName ist "car" statt "mobility-passengervehicle-car-offroadvehicle"

-- 1. Zeige den aktuellen Zustand
SELECT id, name, form_name, entity_name 
FROM group_type 
WHERE name = 'Geländewagen' AND entity_name = 'mobility';

-- 2. Korrigiere den formName für Geländewagen
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-offroadvehicle' 
WHERE name = 'Geländewagen' AND entity_name = 'mobility';

-- 3. Überprüfe das Ergebnis
SELECT id, name, form_name, entity_name 
FROM group_type 
WHERE name = 'Geländewagen' AND entity_name = 'mobility';

-- 4. Zeige alle Mobility GroupTypes zur Kontrolle
SELECT id, name, form_name, entity_name 
FROM group_type 
WHERE entity_name = 'mobility' 
ORDER BY name; 