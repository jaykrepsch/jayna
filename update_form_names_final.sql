-- Automatisch generiertes SQL-Skript für die letzten FormName-Korrekturen
-- Jede Zeile: UPDATE group_type SET form_name = <Spalte 10> WHERE form_name = <alter Wert>;

UPDATE group_type SET form_name = 'mobility-bike-bike-city' WHERE form_name = 'bicycle';
UPDATE group_type SET form_name = 'mobility-bike-ebike-ecity' WHERE form_name = 'ebike';
UPDATE group_type SET form_name = 'contract-employmentcontract-employee-fulltime' WHERE form_name = 'employment-contract';
UPDATE group_type SET form_name = 'contract-energy-photovoltaic-electricity' WHERE form_name = 'energy';
UPDATE group_type SET form_name = 'realestate-withoutdevelopment-ground-meadow' WHERE form_name = 'meadow';
UPDATE group_type SET form_name = 'mobility-bike-pedelec-pcity' WHERE form_name = 'pedelec';
-- ... weitere UPDATEs für alle restlichen Werte ... 