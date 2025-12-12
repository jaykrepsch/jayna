-- Überprüfe verfügbare GroupTypes für Contracts
SELECT 
    gt.id,
    gt.name as group_type_name,
    gt.translation_key,
    scg.name as sub_category_group_name,
    sc.name as sub_category_name,
    cat.name as category_name
FROM group_type gt
JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id
JOIN sub_category sc ON scg.sub_category_id = sc.id
JOIN category cat ON sc.category_id = cat.id
WHERE cat.entity_name = 'contract'
ORDER BY gt.id; 