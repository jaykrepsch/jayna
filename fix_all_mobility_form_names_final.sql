-- Umfassendes SQL-Skript zur Korrektur aller Mobility Form-Namen
-- Basierend auf den tatsächlich existierenden Form-Definitionen

-- 1. Zeige alle aktuellen group_type Einträge für Mobility
SELECT id, name, form_name, entity_name 
FROM group_type 
WHERE entity_name = 'mobility' 
ORDER BY name;

-- 2. Korrigiere alle formName-Einträge basierend auf den existierenden Form-Definitionen

-- Passenger Vehicle - Car Kategorie
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-car' WHERE name = 'Auto' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-convertible' WHERE name = 'Cabrio' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-coupe' WHERE name = 'Coupé' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-cuv' WHERE name = 'CUV' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-limousine' WHERE name = 'Limousine' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-minibus' WHERE name = 'Kleinbus' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-minivan' WHERE name = 'Minivan' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-pickup' WHERE name = 'Pickup' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-roadstar' WHERE name = 'Roadstar' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-smallcar' WHERE name = 'Kleinwagen' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-smalltransporter' WHERE name = 'Kleintransporter' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-stationwagon' WHERE name = 'Kombi' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-transporter' WHERE name = 'Transporter' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-van' WHERE name = 'Van' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-suv' WHERE name = 'SUV' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-offroadvehicle' WHERE name = 'Geländewagen' AND entity_name = 'mobility';

-- Passenger Vehicle - Motorcycle Kategorie
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-chopper' WHERE name = 'Chopper' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-cruiser' WHERE name = 'Cruiser' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-dirtbike' WHERE name = 'Dirt Bike' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-motocross' WHERE name = 'Motocross' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-naked' WHERE name = 'Naked' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-srambler' WHERE name = 'Srambler' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-streetfighter' WHERE name = 'Streetfighter' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-stuntbike' WHERE name = 'Stuntbike' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-trial' WHERE name = 'Trial' AND entity_name = 'mobility';

-- Passenger Vehicle - Quad Kategorie
UPDATE group_type SET form_name = 'mobility-passengervehicle-quad-quad4x4' WHERE name = '4x4' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-quad-quadc' WHERE name = 'C' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-quad-quadchildren' WHERE name = 'Kinder' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-quad-quadelectro' WHERE name = 'Elektro' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-quad-quadoffroad' WHERE name = 'Offroad' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-quad-quadpocket' WHERE name = 'Pocket' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-quad-quadrace' WHERE name = 'Renn' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-quad-quadroad' WHERE name = 'Road' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-quad-quadsidebyside' WHERE name = 'Side by Side' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-quad-quadtricycle' WHERE name = 'Tricycle' AND entity_name = 'mobility';

-- Passenger Vehicle - Camper Kategorie
UPDATE group_type SET form_name = 'mobility-passengervehicle-camper-alkoven' WHERE name = 'mit Alkoven' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-camper-fullyintegrated' WHERE name = 'vollintegriert' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-passengervehicle-camper-semiintegrated' WHERE name = 'teilintegriert' AND entity_name = 'mobility';

-- Commercial Vehicle Kategorie
UPDATE group_type SET form_name = 'mobility-commercialvehicle-box-boxvan' WHERE name = 'A' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-commercialvehicle-box-boxvan' WHERE name = 'B' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-commercialvehicle-box-boxvan' WHERE name = 'C' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-commercialvehicle-caravan-family' WHERE name = 'Familien' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-commercialvehicle-caravan-luxury' WHERE name = 'Luxus' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-commercialvehicle-caravan-sport' WHERE name = 'Sport' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-commercialvehicle-caravan-tent' WHERE name = 'Zelt' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-commercialvehicle-caravan-twopersons' WHERE name = 'Zweipersonen' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-commercialvehicle-trailer-a' WHERE name = 'A' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-commercialvehicle-trailer-b' WHERE name = 'B' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-commercialvehicle-trailer-c' WHERE name = 'C' AND entity_name = 'mobility';

-- Bike Kategorie
UPDATE group_type SET form_name = 'mobility-bike-bike-cargo' WHERE name = 'Lasten' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-bike-city' WHERE name = 'City' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-bike-cross' WHERE name = 'Cross' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-bike-foldingbike' WHERE name = 'Falt' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-bike-gravel' WHERE name = 'Gravel' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-bike-lifestyle' WHERE name = 'Lifestyle' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-bike-mtb' WHERE name = 'Mountainbike' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-bike-racingbike' WHERE name = 'Rennrad' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-bike-speedbike' WHERE name = 'Speed' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-bike-suv' WHERE name = 'SUV' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-bike-trekkingbike' WHERE name = 'Trekking' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-bike-tricycle' WHERE name = 'Dreirad' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-bike-xxl' WHERE name = 'XXL' AND entity_name = 'mobility';

-- E-Bike Kategorie
UPDATE group_type SET form_name = 'mobility-bike-ebike-ecargo' WHERE name = 'Lasten' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-ebike-ecity' WHERE name = 'City' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-ebike-ecross' WHERE name = 'Cross' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-ebike-efoldingbike' WHERE name = 'Falt' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-ebike-egravel' WHERE name = 'Gravel' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-ebike-elifestyle' WHERE name = 'Lifestyle' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-ebike-emtb' WHERE name = 'Mountainbike' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-ebike-eracingbike' WHERE name = 'Rennrad' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-ebike-espeedbike' WHERE name = 'Speed' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-ebike-esuv' WHERE name = 'SUV' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-ebike-etrekkingbike' WHERE name = 'Trekking' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-ebike-etricycle' WHERE name = 'Dreirad' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-ebike-exxl' WHERE name = 'XXL' AND entity_name = 'mobility';

-- Pedelec Kategorie
UPDATE group_type SET form_name = 'mobility-bike-pedelec-pcargo' WHERE name = 'Lasten' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-pedelec-pcity' WHERE name = 'City' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-pedelec-pcross' WHERE name = 'Cross' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-pedelec-pfoldingbike' WHERE name = 'Falt' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-pedelec-pgravel' WHERE name = 'Gravel' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-pedelec-plifestyle' WHERE name = 'Lifestyle' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-pedelec-pmtb' WHERE name = 'Mountainbike' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-pedelec-pracingbike' WHERE name = 'Rennrad' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-pedelec-pspeedbike' WHERE name = 'Speed' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-pedelec-psuv' WHERE name = 'SUV' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-pedelec-ptrekkingbike' WHERE name = 'Trekking' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-pedelec-ptricycle' WHERE name = 'Dreirad' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-bike-pedelec-pxxl' WHERE name = 'XXL' AND entity_name = 'mobility';

-- Boat Kategorie
UPDATE group_type SET form_name = 'mobility-boat-hybrid-motorglider' WHERE name = 'Motorsegler' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-boat-motorized-motorboat' WHERE name = 'Motorboot' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-boat-motorized-motorboat' WHERE name = 'Sportboot' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-boat-motorized-motorboat' WHERE name = 'Jollenkreuzer' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-boat-sailing-sailingboat' WHERE name = 'Segelboot' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-boat-sailing-sailingyacht' WHERE name = 'Segelyacht' AND entity_name = 'mobility';

-- Agriculture Kategorie
UPDATE group_type SET form_name = 'mobility-agriculture-tractor-tractor' WHERE name = 'Traktor' AND entity_name = 'mobility';
UPDATE group_type SET form_name = 'mobility-agriculture-tractor-tractor' WHERE name = 'Träker' AND entity_name = 'mobility';

-- 3. Zeige das Ergebnis zur Bestätigung
SELECT id, name, form_name, entity_name 
FROM group_type 
WHERE entity_name = 'mobility' 
ORDER BY name; 