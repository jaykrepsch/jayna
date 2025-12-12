-- Bereinige Inkonsistenzen in der contract_realestate Tabelle
-- Entferne Einträge, die auf nicht existierende RealEstate verweisen

DELETE FROM contract_realestate 
WHERE realestate_id NOT IN (SELECT id FROM real_estate);

-- Entferne Einträge, die auf nicht existierende Contract verweisen
DELETE FROM contract_realestate 
WHERE contract_id NOT IN (SELECT id FROM contract);

-- Zeige die verbleibenden Einträge
SELECT * FROM contract_realestate; 