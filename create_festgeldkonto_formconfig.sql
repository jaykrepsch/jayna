-- SQL-Skript zum Erstellen des fehlenden FormConfig-Eintrags für das Festgeldkonto

-- 1. Zuerst finden wir die GroupType-ID für das Festgeldkonto
-- (Diese ID müssen wir aus der Datenbank ermitteln)

-- 2. Erstellen Sie den FormConfig-Eintrag
INSERT INTO form_config (
    id,
    form_name,
    group_type_id,
    application_user_id,
    created_date,
    last_modified_date,
    created_by,
    last_modified_by
) 
SELECT 
    (SELECT COALESCE(MAX(id), 0) + 1 FROM form_config), -- Neue ID
    'contract-savings-savings-fixedtermcontract', -- Formular-Name
    gt.id, -- GroupType-ID für Festgeldkonto
    1, -- ApplicationUser-ID (Standard-User)
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    'system',
    'system'
FROM group_type gt 
WHERE gt.name = 'Festgeldkonto' 
AND gt.form_name = 'contract-festgeldkonto-festgeldkonto'
AND NOT EXISTS (
    SELECT 1 FROM form_config fc WHERE fc.group_type_id = gt.id
);

-- 3. Überprüfen Sie das Ergebnis
SELECT 'Festgeldkonto FormConfig erstellt:' as info;
SELECT fc.id, fc.form_name, gt.name as group_type_name, fc.created_date
FROM form_config fc
JOIN group_type gt ON fc.group_type_id = gt.id
WHERE gt.name = 'Festgeldkonto';

-- 4. Zeigen Sie alle FormConfigs für Bankkonto-bezogene GroupTypes
SELECT 'Alle FormConfigs für Bankkonto-bezogene GroupTypes:' as info;
SELECT fc.id, fc.form_name, gt.name as group_type_name, scg.name as sub_category_group
FROM form_config fc
JOIN group_type gt ON fc.group_type_id = gt.id
JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id
WHERE scg.name LIKE '%Bank%' OR scg.name LIKE '%Konto%'
ORDER BY gt.name; 