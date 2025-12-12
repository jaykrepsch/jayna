-- SQL-Skript zum Hinzufügen der Dokumenten-Kategorie zur bestehenden Struktur

-- 1. Dokumenten-Kategorie erstellen
INSERT INTO category (id, name, created_date, translation_key, entity_name) VALUES
(6, 'Dokumente', CURRENT_DATE, 'document', 'document');

-- 2. Sub-Kategorie für Dokumente erstellen
INSERT INTO sub_category (id, name, created_date, translation_key, category_id) VALUES
(6, 'Dokumente', CURRENT_DATE, 'jaynaApp.document.sub-category.documents', 6);

-- 3. Sub-Category-Group für Dokumente erstellen
INSERT INTO sub_category_group (id, name, created_date, translation_key, sub_category_id) VALUES
(6, 'Dokumente', CURRENT_DATE, 'jaynaApp.document.sub-category-group.documents', 6);

-- 4. Group-Type für Dokumente erstellen
INSERT INTO group_type (id, name, created_date, translation_key, sub_category_group_id, entity_name, abbreviation, form_name) VALUES
(6, 'Dokumente', CURRENT_DATE, 'jaynaApp.document.group-type.documents', 6, 'document', 'DOKU', 'document');

-- Bestätigung der erstellten Datensätze
SELECT 'Dokumenten-Kategorie erstellt:' as info, COUNT(*) as anzahl FROM category WHERE entity_name = 'document'
UNION ALL
SELECT 'Dokumenten-Sub-Kategorie erstellt:', COUNT(*) FROM sub_category WHERE category_id = 6
UNION ALL
SELECT 'Dokumenten-Sub-Category-Group erstellt:', COUNT(*) FROM sub_category_group WHERE sub_category_id = 6
UNION ALL
SELECT 'Dokumenten-Group-Type erstellt:', COUNT(*) FROM group_type WHERE sub_category_group_id = 6;
