-- Skript zur Überprüfung und Korrektur der Contract-Daten
-- Führe dies in der PostgreSQL-Datenbank aus

-- 1. Zeige alle Contracts mit ihren Kategorie-Beziehungen
SELECT 
    'Contract-Daten:' as info,
    c.id as contract_id,
    c.label as contract_label,
    c.contractor,
    c.contract_number,
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

-- 2. Zeige die erwarteten Daten für Arbeitsvertrag -> Angestellter -> Aufhebungsvertrag
SELECT 
    'Erwartete Daten:' as info,
    'Arbeitsvertrag' as sub_category,
    'Angestellter' as sub_category_group,
    'Aufhebungsvertrag' as group_type
UNION ALL
SELECT 
    'Tatsächliche Daten in DB:' as info,
    sc.name as sub_category,
    scg.name as sub_category_group,
    gt.name as group_type
FROM contract c
JOIN group_type gt ON c.group_type_id = gt.id
JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id
JOIN sub_category sc ON scg.sub_category_id = sc.id
WHERE c.id = (SELECT MAX(id) FROM contract);

-- 3. Zeige alle GroupTypes für Contracts
SELECT 
    'GroupTypes für Contracts:' as info,
    gt.id,
    gt.name as group_type_name,
    scg.name as sub_category_group_name,
    sc.name as sub_category_name
FROM group_type gt
JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id
JOIN sub_category sc ON scg.sub_category_id = sc.id
JOIN category cat ON sc.category_id = cat.id
WHERE cat.entity_name = 'contract'
ORDER BY gt.id;

-- 4. Suche nach der korrekten GroupType-ID für Aufhebungsvertrag
SELECT 
    'Korrekte GroupType für Aufhebungsvertrag:' as info,
    gt.id,
    gt.name,
    scg.name as sub_category_group_name,
    sc.name as sub_category_name
FROM group_type gt
JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id
JOIN sub_category sc ON scg.sub_category_id = sc.id
WHERE sc.name = 'Arbeitsvertrag' 
  AND scg.name = 'Angestellter' 
  AND gt.name LIKE '%Aufhebungsvertrag%';

-- 5. Suche nach der falschen GroupType-ID (Mietvertrag -> Abstellplatz -> Garage)
SELECT 
    'Falsche GroupType (Mietvertrag -> Abstellplatz -> Garage):' as info,
    gt.id,
    gt.name,
    scg.name as sub_category_group_name,
    sc.name as sub_category_name
FROM group_type gt
JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id
JOIN sub_category sc ON scg.sub_category_id = sc.id
WHERE sc.name = 'Mietvertrag' 
  AND scg.name = 'Abstellplatz' 
  AND gt.name = 'Garage'; 