-- SQL-Skript zum Korrigieren der form_name Werte für Aufhebungsverträge
-- Aktualisiert die form_name Werte basierend auf den tatsächlich vorhandenen Form-Definitionen

-- 1. Aktualisierung für "Angestellter" (sub_category_group_id = 1) - Aufhebungsvertrag
UPDATE group_type SET form_name = 'contract-employmentContract-employee-terminationAgreement' 
WHERE sub_category_group_id = 1 AND name = 'Aufhebungsvertrag';

-- 2. Aktualisierung für "öffentlicher Dienst" (sub_category_group_id = 2) - Aufhebungsvertrag
UPDATE group_type SET form_name = 'contract-employmentContract-publicService-terminationAgreement' 
WHERE sub_category_group_id = 2 AND name = 'Aufhebungsvertrag';

-- 3. Aktualisierung für "Beamter" (sub_category_group_id = 3) - Aufhebungsvertrag
UPDATE group_type SET form_name = 'contract-employmentContract-official-terminationAgreement' 
WHERE sub_category_group_id = 3 AND name = 'Aufhebungsvertrag';

-- Bestätigung der aktualisierten Datensätze
SELECT 
    'Aktualisierte Aufhebungsvertrag-Datensätze:' as info, 
    COUNT(*) as anzahl 
FROM group_type 
WHERE form_name LIKE 'contract-employmentContract-%terminationAgreement';

-- Zeige alle Aufhebungsvertrag-Datensätze
SELECT 
    gt.id,
    scg.name as gruppierung,
    gt.name as sparte_bezeichnung,
    gt.form_name,
    gt.abbreviation
FROM group_type gt
JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id
WHERE gt.name = 'Aufhebungsvertrag'
ORDER BY scg.id, gt.id;

-- Prüfe, ob noch employment-contract Referenzen existieren
SELECT 
    'Verbleibende employment-contract Referenzen:' as info,
    COUNT(*) as anzahl
FROM group_type 
WHERE form_name = 'employment-contract'; 