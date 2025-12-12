-- SQL-Skript zum Korrigieren der verbleibenden FormNames
-- Alle Werte werden auf die korrekten FormNames aus Spalte 10 der CSV gesetzt

-- Immobilien
UPDATE group_type SET form_name = 'realestate-withdevelopment-garden-allotmentgarden' WHERE form_name = 'allotment-garden';
UPDATE group_type SET form_name = 'realestate-withoutdevelopment-ground-buildingplot' WHERE form_name = 'building-plot';

-- Mobilität - Wohnmobile
UPDATE group_type SET form_name = 'mobility-passengervehicle-camper-alkoven' WHERE form_name = 'camper' AND name = 'mit Alkoven';
UPDATE group_type SET form_name = 'mobility-passengervehicle-camper-semiintegrated' WHERE form_name = 'camper' AND name = 'teilintegriert';
UPDATE group_type SET form_name = 'mobility-passengervehicle-camper-fullyintegrated' WHERE form_name = 'camper' AND name = 'vollintegriert';

-- Mobilität - Autos
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-pickup' WHERE form_name = 'car' AND name = 'Pickup';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-roadstar' WHERE form_name = 'car' AND name = 'Roadstar';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-suv' WHERE form_name = 'car' AND name = 'SUV';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-transporter' WHERE form_name = 'car' AND name = 'Transporter';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-smallcar' WHERE form_name = 'car' AND name = 'Kleinwagen';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-van' WHERE form_name = 'car' AND name = 'Van';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-stationwagon' WHERE form_name = 'car' AND name = 'Kombi';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-limousine' WHERE form_name = 'car' AND name = 'Limousine';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-minivan' WHERE form_name = 'car' AND name = 'Minivan';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-convertible' WHERE form_name = 'car' AND name = 'Cabrio';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-coupe' WHERE form_name = 'car' AND name = 'Coupé';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-cuv' WHERE form_name = 'car' AND name = 'CUV';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-offroadvehicle' WHERE form_name = 'car' AND name = 'Geländewagen';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-minibus' WHERE form_name = 'car' AND name = 'Kleinbus';
UPDATE group_type SET form_name = 'mobility-passengervehicle-car-smalltransporter' WHERE form_name = 'car' AND name = 'Kleintransporter';

-- Mobilität - Wohnwagen
UPDATE group_type SET form_name = 'mobility-commercialvehicle-caravan-sport' WHERE form_name = 'caravan' AND name = 'Sport';
UPDATE group_type SET form_name = 'mobility-commercialvehicle-caravan-luxury' WHERE form_name = 'caravan' AND name = 'Luxus';
UPDATE group_type SET form_name = 'mobility-commercialvehicle-caravan-family' WHERE form_name = 'caravan' AND name = 'Familien';
UPDATE group_type SET form_name = 'mobility-commercialvehicle-caravan-tent' WHERE form_name = 'caravan' AND name = 'Zelt';
UPDATE group_type SET form_name = 'mobility-commercialvehicle-caravan-twopersons' WHERE form_name = 'caravan' AND name = 'Zwei-Personen';

-- Immobilien - Parkplätze
UPDATE group_type SET form_name = 'realestate-withdevelopment-parkingspace-carport' WHERE form_name = 'carport';
UPDATE group_type SET form_name = 'realestate-withdevelopment-parkingspace-parkinghouse' WHERE form_name = 'parking-house';
UPDATE group_type SET form_name = 'realestate-withdevelopment-parkingspace-parkingspace' WHERE form_name = 'parking-space';

-- Mobilität - Motorräder
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-chopper' WHERE form_name = 'chopper';
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-cruiser' WHERE form_name = 'cruiser';
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-dirtbike' WHERE form_name = 'dirt-bike';
UPDATE group_type SET form_name = 'mobility-passengervevehicle-motorcycle-motocross' WHERE form_name = 'motocross';
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-naked' WHERE form_name = 'naked';
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-srambler' WHERE form_name = 'srambler';
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-streetfighter' WHERE form_name = 'streetfighter';
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-stuntbike' WHERE form_name = 'stuntbike';
UPDATE group_type SET form_name = 'mobility-passengervehicle-motorcycle-trial' WHERE form_name = 'trial';

-- Boote
UPDATE group_type SET form_name = 'mobility-boat-motorized-motorboat' WHERE form_name = 'motorboat';
UPDATE group_type SET form_name = 'mobility-boat-hybrid-motorglider' WHERE form_name = 'motorsailer';
UPDATE group_type SET form_name = 'mobility-boat-sailing-sailingyacht' WHERE form_name = 'sailboat' AND name = 'Segelyacht';
UPDATE group_type SET form_name = 'mobility-boat-sailing-sailingboat' WHERE form_name = 'sailboat' AND name = 'Segelboot';

-- Immobilien - Gebäude
UPDATE group_type SET form_name = 'realestate-withdevelopment-residentialbuilding-detachedhouse' WHERE form_name = 'commercial-residential-property' AND name = 'Einfamilienhaus';
UPDATE group_type SET form_name = 'realestate-withdevelopment-commercialbuilding-commercialunit' WHERE form_name = 'commercial-residential-property' AND name = 'Gewerbeeinheit';
UPDATE group_type SET form_name = 'realestate-withdevelopment-commercialbuilding-commercialbuilding' WHERE form_name = 'commercial-residential-property' AND name = 'Gewerbehaus';
UPDATE group_type SET form_name = 'realestate-withdevelopment-hall-hall' WHERE form_name = 'commercial-residential-property' AND name = 'Halle';
UPDATE group_type SET form_name = 'realestate-withdevelopment-residentialbuilding-semitdetachedhouse' WHERE form_name = 'commercial-residential-property' AND name = 'Doppelhaushälfte';
UPDATE group_type SET form_name = 'realestate-withdevelopment-residentialbuilding-vacationhouse' WHERE form_name = 'commercial-residential-property' AND name = 'Ferienhaus';
UPDATE group_type SET form_name = 'realestate-withdevelopment-residentialbuilding-apartmentbuilding' WHERE form_name = 'commercial-residential-property' AND name = 'Mehrfamilienhaus';
UPDATE group_type SET form_name = 'realestate-withdevelopment-residentialbuilding-carefacility' WHERE form_name = 'commercial-residential-property' AND name = 'Pflegeeinrichtung';
UPDATE group_type SET form_name = 'realestate-withdevelopment-residentialbuilding-terracedendhouse' WHERE form_name = 'commercial-residential-property' AND name = 'Reihenendhaus';
UPDATE group_type SET form_name = 'realestate-withdevelopment-residentialbuilding-terracedmidhouse' WHERE form_name = 'commercial-residential-property' AND name = 'Reihenmittelhaus';
UPDATE group_type SET form_name = 'realestate-withdevelopment-residentialbuilding-twofamilyhouse' WHERE form_name = 'commercial-residential-property' AND name = 'Zweifamilienhaus';
UPDATE group_type SET form_name = 'realestate-withdevelopment-commercialresidentialbuilding-apartmentscommercialmix' WHERE form_name = 'commercial-residential-property' AND name = 'Wohnungen & Gewerbe';

-- Kontakte
UPDATE group_type SET form_name = 'contact-contact-contact-singlecontact' WHERE form_name = 'single-contact';

-- Immobilien - Bewirtschaftung
UPDATE group_type SET form_name = 'realestate-withdevelopment-farmland-forest' WHERE form_name = 'forest';
UPDATE group_type SET form_name = 'realestate-withdevelopment-farmland-fruit' WHERE form_name = 'fruit';
UPDATE group_type SET form_name = 'realestate-withdevelopment-parkingspace-garage' WHERE form_name = 'garage';
UPDATE group_type SET form_name = 'realestate-withdevelopment-farmland-pasture' WHERE form_name = 'pasture';

-- Verträge - Energie
UPDATE group_type SET form_name = 'contract-energy-electric-electricity' WHERE form_name = 'energy-electric-strom';

-- Verträge - Versicherung
UPDATE group_type SET form_name = 'contract-insurance-object-fire' WHERE form_name = 'insurance-fire';

-- Verträge - Leasing
UPDATE group_type SET form_name = 'contract-leasing-car-car' WHERE form_name = 'leasing';

-- Verträge - Service
UPDATE group_type SET form_name = 'contract-service-maintenance-chimneysweeper' WHERE form_name = 'chimney-sweeper';
UPDATE group_type SET form_name = 'contract-service-service-propertymanagement' WHERE form_name = 'property-management';

-- Traktoren
UPDATE group_type SET form_name = 'mobility-agriculture-tractor-tractor' WHERE form_name = 'tractor'; 