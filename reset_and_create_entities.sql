-- SQL-Skript zum Zurücksetzen und Neuerstellen aller Entitäten, Arten, Gruppierungen und Sparten
-- Alle bestehenden Datensätze werden gelöscht und neue mit den entsprechenden Spartennamen als Bezeichnung erstellt

-- 1. Alle bestehenden Datensätze löschen (in umgekehrter Reihenfolge wegen Foreign Key Constraints)
DELETE FROM group_type;
DELETE FROM sub_category_group;
DELETE FROM sub_category;
DELETE FROM category;

-- 2. Kategorien (Entitäten) erstellen
INSERT INTO category (id, name, created_date, translation_key, entity_name) VALUES
(1, 'Verträge', CURRENT_DATE, 'contract', 'contract'),
(2, 'Immobilien', CURRENT_DATE, 'realestate', 'realestate'),
(3, 'Kontakte', CURRENT_DATE, 'contact', 'contact'),
(4, 'Finanzkonten', CURRENT_DATE, 'financeaccount', 'financeaccount'),
(5, 'Mobilität', CURRENT_DATE, 'mobility', 'mobility');

-- 3. Sub-Kategorien (Arten) erstellen - eine pro Hauptkategorie
INSERT INTO sub_category (id, name, created_date, translation_key, category_id) VALUES
(1, 'Verträge', CURRENT_DATE, 'jaynaApp.contract.sub-category.contracts', 1),
(2, 'Immobilien', CURRENT_DATE, 'jaynaApp.realestate.sub-category.realestate', 2),
(3, 'Kontakte', CURRENT_DATE, 'jaynaApp.contact.sub-category.contacts', 3),
(4, 'Finanzkonten', CURRENT_DATE, 'jaynaApp.financeaccount.sub-category.financeaccounts', 4),
(5, 'Mobilität', CURRENT_DATE, 'jaynaApp.mobility.sub-category.mobility', 5);

-- 4. Sub-Category-Groups (Gruppierungen) erstellen - eine pro Sub-Kategorie
INSERT INTO sub_category_group (id, name, created_date, translation_key, sub_category_id) VALUES
(1, 'Verträge', CURRENT_DATE, 'jaynaApp.contract.sub-category-group.contracts', 1),
(2, 'Immobilien', CURRENT_DATE, 'jaynaApp.realestate.sub-category-group.realestate', 2),
(3, 'Kontakte', CURRENT_DATE, 'jaynaApp.contact.sub-category-group.contacts', 3),
(4, 'Finanzkonten', CURRENT_DATE, 'jaynaApp.financeaccount.sub-category-group.financeaccounts', 4),
(5, 'Mobilität', CURRENT_DATE, 'jaynaApp.mobility.sub-category-group.mobility', 5);

-- 5. Group-Types (Sparten) erstellen - eine pro Sub-Category-Group
INSERT INTO group_type (id, name, created_date, translation_key, sub_category_group_id, entity_name, abbreviation, form_name) VALUES
(1, 'Verträge', CURRENT_DATE, 'jaynaApp.contract.group-type.contracts', 1, 'contract', 'VERT', 'contract'),
(2, 'Immobilien', CURRENT_DATE, 'jaynaApp.realestate.group-type.realestate', 2, 'realestate', 'IMMO', 'realestate'),
(3, 'Kontakte', CURRENT_DATE, 'jaynaApp.contact.group-type.contacts', 3, 'contact', 'KONT', 'contact'),
(4, 'Finanzkonten', CURRENT_DATE, 'jaynaApp.financeaccount.group-type.financeaccounts', 4, 'financeaccount', 'FINA', 'financeaccount'),
(5, 'Mobilität', CURRENT_DATE, 'jaynaApp.mobility.group-type.mobility', 5, 'mobility', 'MOBI', 'mobility');

-- Bestätigung der erstellten Datensätze
SELECT 'Kategorien erstellt:' as info, COUNT(*) as anzahl FROM category
UNION ALL
SELECT 'Sub-Kategorien erstellt:', COUNT(*) FROM sub_category
UNION ALL
SELECT 'Sub-Category-Groups erstellt:', COUNT(*) FROM sub_category_group
UNION ALL
SELECT 'Group-Types erstellt:', COUNT(*) FROM group_type; 