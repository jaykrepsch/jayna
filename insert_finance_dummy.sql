-- Dummy-Finanzkonto
INSERT INTO finance_account (id, name, created_date) VALUES (1, 'Girokonto', now()) ON CONFLICT (id) DO NOTHING;

-- Dummy-Custom-Form-Input (Beispiel, falls Tabelle existiert)
INSERT INTO custom_form_input (id, name, category_id, created_date) VALUES (1, 'IBAN', 4, now()) ON CONFLICT (id) DO NOTHING; 