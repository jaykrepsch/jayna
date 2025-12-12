-- Prüfe den aktuellen formName für Geländewagen
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
WHERE gt.name = 'off-road-vehicle'
   OR gt.name LIKE '%Geländewagen%'
   OR gt.name LIKE '%offroad%'; 