-- Detaillierte Überprüfung der Contract-Daten
SELECT 
    c.id as contract_id,
    c.label as contract_label,
    c.contractor,
    c.contract_number,
    c.start_date,
    c.end_date,
    c.group_type_id,
    gt.id as group_type_id_from_join,
    gt.name as group_type_name,
    gt.translation_key as group_type_translation_key,
    scg.id as sub_category_group_id,
    scg.name as sub_category_group_name,
    scg.translation_key as sub_category_group_translation_key,
    sc.id as sub_category_id,
    sc.name as sub_category_name,
    sc.translation_key as sub_category_translation_key,
    cat.id as category_id,
    cat.name as category_name,
    cat.translation_key as category_translation_key
FROM contract c
LEFT JOIN group_type gt ON c.group_type_id = gt.id
LEFT JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id
LEFT JOIN sub_category sc ON scg.sub_category_id = sc.id
LEFT JOIN category cat ON sc.category_id = cat.id
ORDER BY c.id DESC; 