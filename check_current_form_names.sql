-- SQL-Skript zum Prüfen der aktuellen form_name Werte in der Datenbank
-- Zeigt alle Arbeitsvertrag-Sparten und ihre form_name Werte an

SELECT 
    gt.id,
    gt.name as sparte_bezeichnung,
    scg.name as gruppierung,
    gt.translation_key,
    gt.abbreviation,
    gt.form_name,
    gt.entity_name
FROM group_type gt
JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id
WHERE gt.sub_category_group_id IN (1, 2, 3) AND gt.name = '1'
ORDER BY scg.id, gt.id;

-- Zähle, wie viele Datensätze noch den alten form_name 'employment-contract' haben
SELECT 
    'Datensätze mit employment-contract:' as info,
    COUNT(*) as anzahl
FROM group_type 
WHERE form_name = 'employment-contract';

-- Zeige alle Datensätze mit employment-contract
SELECT 
    gt.id,
    scg.name as gruppierung,
    gt.abbreviation,
    gt.form_name
FROM group_type gt
JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id
WHERE gt.form_name = 'employment-contract'
ORDER BY scg.id, gt.id; 