-- Einfaches Test-Skript für Contract-Daten
-- Führe dies in der PostgreSQL-Datenbank aus

-- 1. Zeige die neuesten 3 Contracts
SELECT 
    'Neueste Contracts:' as info,
    c.id,
    c.label,
    c.contractor,
    c.contract_number,
    c.group_type_id
FROM contract c
ORDER BY c.id DESC
LIMIT 3;

-- 2. Zeige die GroupType-Daten für die neuesten Contracts
SELECT 
    'GroupType-Daten:' as info,
    c.id as contract_id,
    gt.id as group_type_id,
    gt.name as group_type_name,
    scg.name as sub_category_group_name,
    sc.name as sub_category_name
FROM contract c
JOIN group_type gt ON c.group_type_id = gt.id
JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id
JOIN sub_category sc ON scg.sub_category_id = sc.id
ORDER BY c.id DESC
LIMIT 3;

-- 3. Zeige alle verfügbaren GroupTypes für Contracts
SELECT 
    'Verfügbare GroupTypes:' as info,
    gt.id,
    gt.name,
    scg.name as sub_category_group_name,
    sc.name as sub_category_name
FROM group_type gt
JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id
JOIN sub_category sc ON scg.sub_category_id = sc.id
JOIN category cat ON sc.category_id = cat.id
WHERE cat.entity_name = 'contract'
ORDER BY gt.id; 