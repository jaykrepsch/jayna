-- Test der GroupType-Hierarchie
-- Zeige die neuesten Contracts mit vollständiger Kategorie-Hierarchie
SELECT 
    c.id as contract_id,
    c.label as contract_label,
    gt.id as group_type_id,
    gt.name as group_type_name,
    scg.name as sub_category_group_name,
    sc.name as sub_category_name
FROM contract c
LEFT JOIN group_type gt ON c.group_type_id = gt.id
LEFT JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id
LEFT JOIN sub_category sc ON scg.sub_category_id = sc.id
ORDER BY c.id DESC
LIMIT 5; 