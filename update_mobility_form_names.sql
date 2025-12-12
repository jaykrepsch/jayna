-- SQL-Skript zur Aktualisierung aller Mobility Form-Namen
-- Basierend auf dem Contract-Employment-Employee-TerminationAgreement-Konzept

-- 1. Zeige alle aktuellen Mobility GroupTypes
SELECT id, name, form_name, entity_name 
FROM group_type 
WHERE entity_name = 'mobility' 
ORDER BY name;

-- 2. Aktualisiere alle Mobility Form-Namen basierend auf den existierenden Form-Definitionen

-- Auto (Car)
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-car' 
WHERE name = 'Auto' AND entity_name = 'mobility';

-- Geländewagen (Off-Road Vehicle)
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-offroadvehicle' 
WHERE name = 'Geländewagen' AND entity_name = 'mobility';

-- SUV
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-suv' 
WHERE name = 'SUV' AND entity_name = 'mobility';

-- Van
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-van' 
WHERE name = 'Van' AND entity_name = 'mobility';

-- Transporter
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-transporter' 
WHERE name = 'Transporter' AND entity_name = 'mobility';

-- Kombi (Station Wagon)
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-stationwagon' 
WHERE name = 'Kombi' AND entity_name = 'mobility';

-- Kleinlaster (Small Transporter)
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-smalltransporter' 
WHERE name = 'Kleinlaster' AND entity_name = 'mobility';

-- Kleinwagen (Small Car)
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-smallcar' 
WHERE name = 'Kleinwagen' AND entity_name = 'mobility';

-- Roadster
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-roadstar' 
WHERE name = 'Roadster' AND entity_name = 'mobility';

-- Pickup
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-pickup' 
WHERE name = 'Pickup' AND entity_name = 'mobility';

-- Minivan
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-minivan' 
WHERE name = 'Minivan' AND entity_name = 'mobility';

-- Kleinbus (Minibus)
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-minibus' 
WHERE name = 'Kleinbus' AND entity_name = 'mobility';

-- Limousine
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-limousine' 
WHERE name = 'Limousine' AND entity_name = 'mobility';

-- CUV
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-cuv' 
WHERE name = 'CUV' AND entity_name = 'mobility';

-- Coupé
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-coupe' 
WHERE name = 'Coupé' AND entity_name = 'mobility';

-- Cabrio (Convertible)
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-convertible' 
WHERE name = 'Cabrio' AND entity_name = 'mobility';

-- 3. Überprüfe das Ergebnis
SELECT id, name, form_name, entity_name 
FROM group_type 
WHERE entity_name = 'mobility' 
ORDER BY name;

-- 4. Zeige alle aktualisierten Einträge
SELECT 
    gt.id,
    gt.name,
    gt.form_name,
    scg.name as sub_category_group_name,
    sc.name as sub_category_name,
    c.name as category_name
FROM group_type gt
JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id
JOIN sub_category sc ON scg.sub_category_id = sc.id
JOIN category c ON sc.category_id = c.id
WHERE gt.entity_name = 'mobility'
ORDER BY gt.name; 