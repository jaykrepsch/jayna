-- Kategorien
INSERT INTO category (id, name, created_date, translation_key, entity_name) VALUES
  (1, 'Verträge', now(), 'contract', 'contract'),
  (2, 'Immobilien', now(), 'realestate', 'realestate'),
  (3, 'Kontakte', now(), 'contact', 'contact'),
  (4, 'Finanzkonten', now(), 'financeaccount', 'financeaccount'),
  (5, 'Mobilität', now(), 'mobility', 'mobility')
ON CONFLICT (id) DO NOTHING;

-- Subkategorien (Beispiele)
INSERT INTO sub_category (id, name, created_date, translation_key, category_id) VALUES
  (1, 'Arbeitsvertrag', now(), 'jaynaApp.contract.sub-category.employment-contract', 1),
  (2, 'Wohnung', now(), 'jaynaApp.realestate.sub-category.apartment', 2),
  (3, 'Person', now(), 'jaynaApp.contact.sub-category.person', 3),
  (4, 'Girokonto', now(), 'jaynaApp.financeaccount.sub-category.checking-account', 4),
  (5, 'Auto', now(), 'jaynaApp.mobility.sub-category.car', 5)
ON CONFLICT (id) DO NOTHING;

-- Gruppentypen (Beispiele, falls benötigt)
INSERT INTO group_type (id, name, created_date, translation_key, entity_name, form_name, abbreviation) VALUES
  (1, 'Standard', now(), 'jaynaApp.contact.group-type.standard', 'contact', 'contact', 'STD'),
  (2, 'Standard', now(), 'jaynaApp.financeaccount.group-type.standard', 'financeaccount', 'financeaccount', 'STD'),
  (3, 'Standard', now(), 'jaynaApp.mobility.group-type.standard', 'mobility', 'mobility', 'STD')
ON CONFLICT (id) DO NOTHING; 