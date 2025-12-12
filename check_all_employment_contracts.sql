-- SQL-Skript zum Prüfen aller employment-contract Referenzen
-- Zeigt alle GroupTypes, die noch den falschen form_name haben

SELECT 
    gt.id,
    scg.name as gruppierung,
    gt.name as sparte_bezeichnung,
    gt.form_name,
    gt.abbreviation,
    gt.translation_key
FROM group_type gt
JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id
WHERE gt.form_name = 'employment-contract'
ORDER BY scg.id, gt.id;

-- Zähle alle employment-contract Referenzen
SELECT 
    'Anzahl employment-contract Referenzen:' as info,
    COUNT(*) as anzahl
FROM group_type 
WHERE form_name = 'employment-contract';

-- Zeige alle GroupTypes mit employment-contract in der form_name
SELECT 
    gt.id,
    scg.name as gruppierung,
    gt.name as sparte_bezeichnung,
    gt.form_name
FROM group_type gt
JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id
WHERE gt.form_name LIKE '%employment%'
ORDER BY scg.id, gt.id; 