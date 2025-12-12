-- SQL-Skript zum Löschen des einzigen Mobility-Datensatzes
-- Zuerst alle verknüpften Daten in Junction-Tabellen löschen

-- 1. Contact-Mobility Verknüpfungen löschen
DELETE FROM contact_mobility WHERE mobility_id IN (SELECT id FROM mobility);

-- 2. Contract-Mobility Verknüpfungen löschen
DELETE FROM contract_mobility WHERE mobility_id IN (SELECT id FROM mobility);

-- 3. FinanceAccount-Mobility Verknüpfungen löschen
DELETE FROM finance_account_mobility WHERE mobility_id IN (SELECT id FROM mobility);

-- 4. Mobility-RealEstate Verknüpfungen löschen
DELETE FROM mobility_real_estate WHERE mobility_id IN (SELECT id FROM mobility);

-- 5. Mobility-Mobility Verknüpfungen löschen (falls vorhanden)
DELETE FROM mobility_mobility WHERE mobility_id IN (SELECT id FROM mobility);
DELETE FROM mobility_mobility WHERE related_mobility_id IN (SELECT id FROM mobility);

-- 6. Schließlich den Mobility-Datensatz selbst löschen
DELETE FROM mobility;

-- Überprüfung: Anzahl der verbleibenden Mobility-Datensätze
SELECT COUNT(*) as remaining_mobility_records FROM mobility; 