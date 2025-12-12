-- Aktualisiere Contracts mit verschiedenen GroupTypes
-- Contract 1451: Garage (ID 1) - bleibt
-- Contract 1401: Parkplatz (ID 2)
-- Contract 1353: Mini-Job (ID 3)
-- Contract 1352: Teilzeit (ID 4)
-- Contract 1351: Vollzeit (ID 5)

UPDATE contract SET group_type_id = 2 WHERE id = 1401;
UPDATE contract SET group_type_id = 3 WHERE id = 1353;
UPDATE contract SET group_type_id = 4 WHERE id = 1352;
UPDATE contract SET group_type_id = 5 WHERE id = 1351;

-- Zeige die aktualisierten Contracts
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
ORDER BY c.id DESC; 