-- Update alle form_name Felder auf Kleinbuchstaben
-- Betroffene Tabellen: group_type, form_definition

-- Alle Form_Names in group_type auf Kleinbuchstaben setzen
UPDATE group_type
SET form_name = LOWER(form_name)
WHERE form_name ~ '[A-Z]';

-- Alle Form_Names in form_definition auf Kleinbuchstaben setzen
UPDATE form_definition
SET form_name = LOWER(form_name)
WHERE form_name ~ '[A-Z]';

-- Zeige die Anzahl der aktualisierten Einträge
SELECT 'group_type' as table_name, COUNT(*) as updated_count
FROM group_type
WHERE form_name ~ '[a-z]' AND form_name !~ '[A-Z]'
UNION ALL
SELECT 'form_definition' as table_name, COUNT(*) as updated_count
FROM form_definition
WHERE form_name ~ '[a-z]' AND form_name !~ '[A-Z]'; 