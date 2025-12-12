-- Umfassendes SQL-Skript zur Korrektur aller formName-Einträge
-- Basierend auf den tatsächlich existierenden Form-Definitionen

-- 1. Zeige alle aktuellen group_type Einträge für Mobility
SELECT id, name, form_name, entity_name 
FROM group_type 
WHERE entity_name = 'mobility' 
ORDER BY name;

-- 2. Korrigiere alle formName-Einträge basierend auf den existierenden Form-Definitionen

-- Geländewagen
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-offroadvehicle' 
WHERE name = 'Geländewagen' AND entity_name = 'mobility';

-- SUV
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-suv' 
WHERE name = 'SUV' AND entity_name = 'mobility';

-- Van
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-van' 
WHERE name = 'Van' AND entity_name = 'mobility';

-- Transporter
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-transporter' 
WHERE name = 'Transporter' AND entity_name = 'mobility';

-- Kombi
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-stationwagon' 
WHERE name = 'Kombi' AND entity_name = 'mobility';

-- Kleinlaster
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-smalltransporter' 
WHERE name = 'Kleinlaster' AND entity_name = 'mobility';

-- Kleinwagen
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-smallcar' 
WHERE name = 'Kleinwagen' AND entity_name = 'mobility';

-- Roadster
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-roadstar' 
WHERE name = 'Roadster' AND entity_name = 'mobility';

-- Pickup
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-pickup' 
WHERE name = 'Pickup' AND entity_name = 'mobility';

-- Minivan
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-minivan' 
WHERE name = 'Minivan' AND entity_name = 'mobility';

-- Kleinbus
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-minibus' 
WHERE name = 'Kleinbus' AND entity_name = 'mobility';

-- Limousine
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-limousine' 
WHERE name = 'Limousine' AND entity_name = 'mobility';

-- CUV
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-cuv' 
WHERE name = 'CUV' AND entity_name = 'mobility';

-- Coupé
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-coupe' 
WHERE name = 'Coupé' AND entity_name = 'mobility';

-- Cabrio
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-convertible' 
WHERE name = 'Cabrio' AND entity_name = 'mobility';

-- Motorräder
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-motorcycle-trial' 
WHERE name = 'Trial' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengervehicle-motorcycle-stuntbike' 
WHERE name = 'Stuntbike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengervehicle-motorcycle-streetfighter' 
WHERE name = 'Streetfighter' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengervehicle-motorcycle-srambler' 
WHERE name = 'Scrambler' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengervehicle-motorcycle-naked' 
WHERE name = 'Naked Bike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengervehicle-motorcycle-motocross' 
WHERE name = 'Motocross' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengervehicle-motorcycle-dirtbike' 
WHERE name = 'Dirtbike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengervehicle-motorcycle-cruiser' 
WHERE name = 'Cruiser' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengervehicle-motorcycle-chopper' 
WHERE name = 'Chopper' AND entity_name = 'mobility';

-- Quads
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-quad-quadsidebyside' 
WHERE name = 'Side-by-Side' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengervehicle-quad-quadroad' 
WHERE name = 'Road Quad' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengervehicle-quad-quadrace' 
WHERE name = 'Race Quad' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengervehicle-quad-quadpocket' 
WHERE name = 'Pocket Quad' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengervehicle-quad-quadoffroad' 
WHERE name = 'Offroad Quad' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengervehicle-quad-quadelectro' 
WHERE name = 'Electro Quad' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengervehicle-quad-quadchildren' 
WHERE name = 'Children Quad' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengervehicle-quad-quadc' 
WHERE name = 'Quad C' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengervehicle-quad-quad4x4' 
WHERE name = '4x4 Quad' AND entity_name = 'mobility';

-- Wohnmobile
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-camper-semiintegrated' 
WHERE name = 'Teilintegriert' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengervehicle-camper-fullyintegrated' 
WHERE name = 'Vollintegriert' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-passengervehicle-camper-alkoven' 
WHERE name = 'Alkoven' AND entity_name = 'mobility';

-- Anhänger
UPDATE group_type 
SET form_name = 'mobility-commercialvehicle-trailer-c' 
WHERE name = 'Anhänger C' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-commercialvehicle-trailer-b' 
WHERE name = 'Anhänger B' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-commercialvehicle-trailer-a' 
WHERE name = 'Anhänger A' AND entity_name = 'mobility';

-- Wohnwagen
UPDATE group_type 
SET form_name = 'mobility-commercialvehicle-caravan-twopersons' 
WHERE name = '2-Personen' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-commercialvehicle-caravan-tent' 
WHERE name = 'Zelt' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-commercialvehicle-caravan-sport' 
WHERE name = 'Sport' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-commercialvehicle-caravan-luxury' 
WHERE name = 'Luxus' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-commercialvehicle-caravan-family' 
WHERE name = 'Familie' AND entity_name = 'mobility';

-- Kastenwagen
UPDATE group_type 
SET form_name = 'mobility-commercialvehicle-box-boxvan' 
WHERE name = 'Kastenwagen' AND entity_name = 'mobility';

-- Boote
UPDATE group_type 
SET form_name = 'mobility-boat-sailing-sailingyacht' 
WHERE name = 'Segelyacht' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-boat-sailing-sailingboat' 
WHERE name = 'Segelboot' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-boat-motorized-motorboat' 
WHERE name = 'Motorboot' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-boat-hybrid-motorglider' 
WHERE name = 'Motorsegler' AND entity_name = 'mobility';

-- Fahrräder (Pedelec)
UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pxxl' 
WHERE name = 'XXL Pedelec' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-ptricycle' 
WHERE name = 'Dreirad Pedelec' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-ptrekkingbike' 
WHERE name = 'Trekking Pedelec' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-psuv' 
WHERE name = 'SUV Pedelec' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pspeedbike' 
WHERE name = 'Speed Pedelec' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pracingbike' 
WHERE name = 'Rennrad Pedelec' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pmtb' 
WHERE name = 'MTB Pedelec' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-plifestyle' 
WHERE name = 'Lifestyle Pedelec' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pgravel' 
WHERE name = 'Gravel Pedelec' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pfoldingbike' 
WHERE name = 'Faltrad Pedelec' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pcross' 
WHERE name = 'Cross Pedelec' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pcity' 
WHERE name = 'City Pedelec' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-pedelec-pcargo' 
WHERE name = 'Cargo Pedelec' AND entity_name = 'mobility';

-- Fahrräder (E-Bike)
UPDATE group_type 
SET form_name = 'mobility-bike-ebike-exxl' 
WHERE name = 'XXL E-Bike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-ebike-etricycle' 
WHERE name = 'Dreirad E-Bike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-ebike-etrekkingbike' 
WHERE name = 'Trekking E-Bike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-ebike-esuv' 
WHERE name = 'SUV E-Bike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-ebike-espeedbike' 
WHERE name = 'Speed E-Bike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-ebike-eracingbike' 
WHERE name = 'Rennrad E-Bike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-ebike-emtb' 
WHERE name = 'MTB E-Bike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-ebike-elifestyle' 
WHERE name = 'Lifestyle E-Bike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-ebike-egravel' 
WHERE name = 'Gravel E-Bike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-ebike-efoldingbike' 
WHERE name = 'Faltrad E-Bike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-ebike-ecross' 
WHERE name = 'Cross E-Bike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-ebike-ecity' 
WHERE name = 'City E-Bike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-ebike-ecargo' 
WHERE name = 'Cargo E-Bike' AND entity_name = 'mobility';

-- Fahrräder (Normal)
UPDATE group_type 
SET form_name = 'mobility-bike-bike-xxl' 
WHERE name = 'XXL Fahrrad' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-tricycle' 
WHERE name = 'Dreirad' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-trekkingbike' 
WHERE name = 'Trekkingrad' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-suv' 
WHERE name = 'SUV Fahrrad' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-speedbike' 
WHERE name = 'Speedbike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-racingbike' 
WHERE name = 'Rennrad' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-mtb' 
WHERE name = 'MTB' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-lifestyle' 
WHERE name = 'Lifestyle Fahrrad' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-gravel' 
WHERE name = 'Gravel Bike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-foldingbike' 
WHERE name = 'Faltrad' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-cross' 
WHERE name = 'Cross Bike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-city' 
WHERE name = 'City Bike' AND entity_name = 'mobility';

UPDATE group_type 
SET form_name = 'mobility-bike-bike-cargo' 
WHERE name = 'Cargo Bike' AND entity_name = 'mobility';

-- Traktor
UPDATE group_type 
SET form_name = 'mobility-agriculture-tractor-tractor' 
WHERE name = 'Traktor' AND entity_name = 'mobility';

-- 3. Zeige das Ergebnis zur Bestätigung
SELECT id, name, form_name, entity_name 
FROM group_type 
WHERE entity_name = 'mobility' 
ORDER BY name; 