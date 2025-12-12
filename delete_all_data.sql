-- SQL-Skript zum Löschen aller Datensätze
-- WARNUNG: Dies löscht ALLE Daten aus der Datenbank!
-- Führen Sie dieses Skript nur aus, wenn Sie sicher sind, dass Sie alle Daten löschen möchten!

-- Deaktiviere Foreign Key Constraints temporär
SET session_replication_role = replica;

-- Lösche alle Beziehungstabellen zuerst (wegen Foreign Key Constraints)
DELETE FROM contract_realestate;
DELETE FROM contract_finance_account;
DELETE FROM contract_mobility;
DELETE FROM contact_contract;
DELETE FROM contact_finance_account;
DELETE FROM contact_mobility;
DELETE FROM contact_realestate;
DELETE FROM finance_account_mobility;
DELETE FROM finance_account_realestate;
DELETE FROM mobility_realestate;

-- Lösche alle Hauptentitäten
DELETE FROM contract;
DELETE FROM contact;
DELETE FROM finance_account;
DELETE FROM mobility;
DELETE FROM real_estate;

-- Lösche auch die Beziehungstabellen zwischen gleichen Entitäten
DELETE FROM contract_contract;
DELETE FROM contact_contact;
DELETE FROM finance_account_finance_account;
DELETE FROM mobility_mobility;
DELETE FROM real_estate_real_estate;

-- Aktiviere Foreign Key Constraints wieder
SET session_replication_role = DEFAULT;

-- Bestätige die Änderungen
COMMIT;

-- Zeige eine Bestätigung
SELECT 'Alle Datensätze wurden erfolgreich gelöscht!' AS message; 