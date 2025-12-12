-- Einfaches Skript zur Überprüfung der Contract-Daten
-- Führe dies in der PostgreSQL-Datenbank aus

-- 1. Zeige alle Contracts mit ihren Kategorie-Beziehungen
SELECT 
    c.id as contract_id,
    c.label as contract_label,
    c.contractor,
    c.contract_number,
    gt.id as group_type_id,
    gt.name as group_type_name,
    gt.translation_key as group_type_translation_key,
    scg.id as sub_category_group_id,
    scg.name as sub_category_group_name,
    scg.translation_key as sub_category_group_translation_key,
    sc.id as sub_category_id,
    sc.name as sub_category_name,
    sc.translation_key as sub_category_translation_key
FROM contract c
LEFT JOIN group_type gt ON c.group_type_id = gt.id
LEFT JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id
LEFT JOIN sub_category sc ON scg.sub_category_id = sc.id
ORDER BY c.id DESC
LIMIT 10;

-- 2. Zeige die neuesten Contracts
SELECT 
    'Neueste Contracts:' as info,
    COUNT(*) as anzahl
FROM contract;

-- 3. Überprüfe die GroupType-Hierarchie für Contracts
SELECT 
    gt.id,
    gt.name as group_type_name,
    scg.name as sub_category_group_name,
    sc.name as sub_category_name,
    cat.name as category_name
FROM group_type gt
JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id
JOIN sub_category sc ON scg.sub_category_id = sc.id
JOIN category cat ON sc.category_id = cat.id
WHERE cat.entity_name = 'contract'
ORDER BY gt.id; 