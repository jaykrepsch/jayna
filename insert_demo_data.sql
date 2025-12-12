-- Kategorien
INSERT INTO category (id, name, created_date, translation_key, entity_name) VALUES (1, 'Verträge', now(), 'contract', 'contract');

-- Subkategorien
INSERT INTO sub_category (id, name, created_date, translation_key, category_id) VALUES (1, 'Arbeitsvertrag', now(), 'jaynaApp.contract.sub-category.employment-contract', 1);

-- Beispiel-Verknüpfung (je nach Modell, z.B. contract_realestate)
-- Hier ein Dummy-Eintrag, falls die Tabelle existiert
-- INSERT INTO contract_realestate (id, contract_id, real_estate_id) VALUES (1, 1, 1);

-- Du kannst weitere Einträge nach Bedarf ergänzen!

-- Mobilität
INSERT INTO mobility (id, name, created_date) VALUES (1, 'Auto', now());

-- Kontakte
INSERT INTO contact (id, first_name, last_name, created_date) VALUES (1, 'Max', 'Mustermann', now());

-- Finanzkonten
INSERT INTO finance_account (id, name, created_date) VALUES (1, 'Girokonto', now());

-- Bereits vorhandene Einträge werden übersprungen, falls sie existieren. 