-- Korrigiere den formName für Geländewagen von "car" auf den korrekten Wert
-- Das Problem liegt in der group_type Tabelle, nicht in der mobility Tabelle

-- 1. Zeige den aktuellen Zustand
SELECT id, name, form_name, entity_name 
FROM group_type 
WHERE name = 'Geländewagen' AND entity_name = 'mobility';

-- 2. Korrigiere den formName für Geländewagen
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-offroadvehicle' 
WHERE name = 'Geländewagen' AND entity_name = 'mobility';

-- 3. Zeige das Ergebnis zur Bestätigung
SELECT id, name, form_name, entity_name 
FROM group_type 
WHERE name = 'Geländewagen' AND entity_name = 'mobility'; 