-- Fix für Verträge ohne group_type_id
-- Aktualisiere alle Verträge, die keine group_type_id haben

-- Zeige zuerst die Verträge ohne group_type_id
SELECT 
    'Verträge ohne group_type_id:' as info,
    COUNT(*) as anzahl
FROM contract 
WHERE group_type_id IS NULL;

-- Aktualisiere alle Verträge ohne group_type_id mit der ersten verfügbaren group_type_id (ID 5 = Vollzeit)
UPDATE contract 
SET group_type_id = 5 
WHERE group_type_id IS NULL;

-- Zeige die aktualisierten Verträge
SELECT 
    'Verträge nach Update:' as info,
    COUNT(*) as anzahl
FROM contract 
WHERE group_type_id IS NOT NULL;

-- Zeige eine Übersicht der aktualisierten Verträge
SELECT 
    c.id as contract_id,
    c.label as contract_label,
    gt.name as group_type_name,
    scg.name as sub_category_group_name,
    sc.name as sub_category_name
FROM contract c
LEFT JOIN group_type gt ON c.group_type_id = gt.id
LEFT JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id
LEFT JOIN sub_category sc ON scg.sub_category_id = sc.id
ORDER BY c.id DESC
LIMIT 10; 