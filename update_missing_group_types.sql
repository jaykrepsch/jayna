-- Aktualisiere Contracts ohne group_type_id
-- Verwende die erste verfügbare GroupType für Contracts (ID 1 = Garage)

-- Zeige zuerst die Contracts ohne group_type_id
SELECT 
    'Contracts ohne group_type_id:' as info,
    COUNT(*) as anzahl
FROM contract 
WHERE group_type_id IS NULL;

-- Aktualisiere alle Contracts ohne group_type_id
UPDATE contract 
SET group_type_id = 1 
WHERE group_type_id IS NULL;

-- Zeige die aktualisierten Contracts
SELECT 
    'Contracts nach Update:' as info,
    COUNT(*) as anzahl
FROM contract 
WHERE group_type_id IS NOT NULL; 