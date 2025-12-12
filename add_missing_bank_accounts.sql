-- SQL-Skript zum Hinzufügen der fehlenden Bankkonto-Einträge
-- Ausgeführt am: $(Get-Date)

-- 1. Girokonto (checkingAccount) - Korrektur des FormNames
UPDATE group_type 
SET form_name = 'contract-bankAccount-checkingAccount-checkingAccount'
WHERE id = 145; -- Girokonto

-- 2. Festgeldkonto (fixedTermAccount) - Neuer Eintrag
INSERT INTO group_type (
    name,
    translation_key,
    sub_category_group_id,
    form_name
) VALUES (
    'Festgeldkonto',
    'financeaccount.bank.bankaccount.fixedtermaccount',
    146, -- Bankkonto sub_category_group_id
    'contract-bankAccount-fixedTermAccount-fixedTermAccount'
);

-- 3. Tagesgeldkonto (overnightAccount) - Neuer Eintrag
INSERT INTO group_type (
    name,
    translation_key,
    sub_category_group_id,
    form_name
) VALUES (
    'Tagesgeldkonto',
    'financeaccount.bank.bankaccount.overnightaccount',
    146, -- Bankkonto sub_category_group_id
    'contract-bankAccount-overnightAccount-overnightAccount'
);

-- Überprüfung der Änderungen
SELECT 
    gt.id,
    gt.name,
    gt.form_name,
    scg.name as sub_category_group_name
FROM group_type gt 
JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id 
WHERE scg.name = 'Bankkonto'
ORDER BY gt.name; 