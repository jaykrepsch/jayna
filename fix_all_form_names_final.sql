-- Korrigiere alle fehlerhaften form_name-Werte in der group_type Tabelle
-- Basierend auf den tatsächlich existierenden Formulardefinitionen

-- Sparkonto (ID 146)
UPDATE group_type SET form_name = 'contract-savings-savings-savingscontract' WHERE id = 146;

-- Tagesgeldkonto (ID 147)  
UPDATE group_type SET form_name = 'contract-savings-savings-overnightcontract' WHERE id = 147;

-- Kreditkarte (ID 149)
UPDATE group_type SET form_name = 'contract-creditcard-credit-creditcardcontract' WHERE id = 149;

-- Debitkarte (ID 150)
UPDATE group_type SET form_name = 'contract-creditcard-credit-creditcard' WHERE id = 150;

-- Hausverwaltung (ID 151)
UPDATE group_type SET form_name = 'contract-propertymanagement-propertymanagement-propertymanagementcontract' WHERE id = 151;

-- Mietverwaltung (ID 152)
UPDATE group_type SET form_name = 'contract-rentalmanagement-rentalmanagement-rentalmanagementcontract' WHERE id = 152;

-- Haus- und Grundbesitzerhaftpflicht (ID 97)
UPDATE group_type SET form_name = 'contract-insurance-liability-liabilitycontract' WHERE id = 97;

-- Elementar (Wohngebäude) (ID 104)
UPDATE group_type SET form_name = 'contract-insurance-fire-firecontract' WHERE id = 104;

-- Versicherungsmakler (ID 105)
UPDATE group_type SET form_name = 'contract-insurance-broker-brokercontract' WHERE id = 105;

-- Hausmeisterservice (ID 106)
UPDATE group_type SET form_name = 'contract-service-caretaker-caretakerservicecontract' WHERE id = 106;

-- Reinigung (ID 107)
UPDATE group_type SET form_name = 'contract-service-cleaning-cleaningservicecontract' WHERE id = 107;

-- Immobilienmakler (ID 108)
UPDATE group_type SET form_name = 'contract-service-broker-brokercontract' WHERE id = 108;

-- Gärtner (ID 109)
UPDATE group_type SET form_name = 'contract-service-gardener-gardenerservicecontract' WHERE id = 109;

-- Zeige die Ergebnisse
SELECT id, name, form_name FROM group_type WHERE id IN (146, 147, 149, 150, 151, 152, 97, 104, 105, 106, 107, 108, 109) ORDER BY id; 