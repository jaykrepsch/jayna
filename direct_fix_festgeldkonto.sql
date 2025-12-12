-- Direkte SQL-Korrektur für das Festgeldkonto
-- Führen Sie dieses Skript direkt in der PostgreSQL-Datenbank aus

-- 1. Zeige den aktuellen Zustand
SELECT 'AKTUELLER ZUSTAND:' as info;
SELECT id, name, form_name, translation_key, entity_name
FROM group_type 
WHERE name = 'Festgeldkonto';

-- 2. Korrigiere den form_name
UPDATE group_type 
SET form_name = 'contract-savings-savings-fixedtermcontract'
WHERE name = 'Festgeldkonto';

-- 3. Zeige das Ergebnis
SELECT 'NACH DER KORREKTUR:' as info;
SELECT id, name, form_name, translation_key, entity_name
FROM group_type 
WHERE name = 'Festgeldkonto';

-- 4. Überprüfe, ob die korrekte Formulardefinition existiert
SELECT 'ÜBERPRÜFUNG:' as info;
SELECT 'Die Datei contract-savings-savings-fixedtermcontract.json sollte existieren' as expected_file; 