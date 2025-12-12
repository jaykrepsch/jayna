-- SQL-Skript zum Hinzufügen der vollständigen Dokumenten-Kategorie-Struktur

-- 1. Dokumenten-Kategorie erstellen (falls nicht vorhanden)
INSERT INTO category (id, name, created_date, translation_key, entity_name) VALUES
(6, 'Dokumente', CURRENT_DATE, 'document', 'document')
ON CONFLICT (id) DO NOTHING;

-- 2. Sub-Kategorien (Hauptkategorien) für Dokumente erstellen
INSERT INTO sub_category (id, name, created_date, translation_key, category_id) VALUES
(28, 'Identität & Personenstand', CURRENT_DATE, 'jaynaApp.document.main.identity-personal-status', 6),
(29, 'Arbeit & Bildung', CURRENT_DATE, 'jaynaApp.document.main.work-education', 6),
(30, 'Finanzen & Versicherungen', CURRENT_DATE, 'jaynaApp.document.main.finances-insurance', 6),
(31, 'Wohnen & Eigentum', CURRENT_DATE, 'jaynaApp.document.main.housing-property', 6),
(32, 'Gesundheit', CURRENT_DATE, 'jaynaApp.document.main.health', 6),
(33, 'Kommunikation & Alltag', CURRENT_DATE, 'jaynaApp.document.main.communication-everyday', 6),
(34, 'Recht & Verwaltung', CURRENT_DATE, 'jaynaApp.document.main.law-administration', 6),
(35, 'Energie & Versorgung', CURRENT_DATE, 'jaynaApp.document.main.energy-utilities', 6)
ON CONFLICT (id) DO NOTHING;

-- 3. Sub-Category-Groups (Gruppierungen) erstellen
INSERT INTO sub_category_group (id, name, created_date, translation_key, sub_category_id) VALUES
-- Identität & Personenstand
(61, 'Ausweise', CURRENT_DATE, 'jaynaApp.document.group.ids', 28),
(62, 'Personenstandsdokumente', CURRENT_DATE, 'jaynaApp.document.group.civil-status-certificates', 28),
(63, 'Vollmachten', CURRENT_DATE, 'jaynaApp.document.group.directives', 28),

-- Arbeit & Bildung
(64, 'Arbeitsdokumente', CURRENT_DATE, 'jaynaApp.document.group.work-documents', 29),
(65, 'Bildung', CURRENT_DATE, 'jaynaApp.document.group.education', 29),
(66, 'Bewerbungen', CURRENT_DATE, 'jaynaApp.document.group.application', 29),

-- Finanzen & Versicherungen
(67, 'Bankwesen', CURRENT_DATE, 'jaynaApp.document.group.banking', 30),
(68, 'Buchhaltung', CURRENT_DATE, 'jaynaApp.document.group.accounting', 30),
(69, 'Versicherungen', CURRENT_DATE, 'jaynaApp.document.group.insurances', 30),

-- Wohnen & Eigentum
(70, 'Mietdokumente', CURRENT_DATE, 'jaynaApp.document.group.rental-docs', 31),
(71, 'Immobilien', CURRENT_DATE, 'jaynaApp.document.group.real-estate', 31),
(72, 'Fahrzeuge', CURRENT_DATE, 'jaynaApp.document.group.vehicles', 31),

-- Gesundheit
(73, 'Medizinische Dokumente', CURRENT_DATE, 'jaynaApp.document.group.medical-documents', 32),
(74, 'Pflege & Unterstützung', CURRENT_DATE, 'jaynaApp.document.group.care-support', 32),

-- Kommunikation & Alltag
(75, 'Korrespondenz', CURRENT_DATE, 'jaynaApp.document.group.correspondence', 33),
(76, 'Protokolle', CURRENT_DATE, 'jaynaApp.document.group.protocols', 33),
(77, 'Digitale Kommunikation', CURRENT_DATE, 'jaynaApp.document.group.digital-communication', 33),

-- Recht & Verwaltung
(78, 'Gerichtsdokumente', CURRENT_DATE, 'jaynaApp.document.group.court-documents', 34),
(79, 'Verträge & Bedingungen', CURRENT_DATE, 'jaynaApp.document.group.contracts-terms', 34),

-- Energie & Versorgung
(80, 'Rechnungen', CURRENT_DATE, 'jaynaApp.document.group.invoices', 35)
ON CONFLICT (id) DO NOTHING;

-- 4. Group-Types (Dokumententypen) erstellen
INSERT INTO group_type (id, name, created_date, translation_key, sub_category_group_id, entity_name, abbreviation, form_name) VALUES
-- Ausweise
(262, 'Personalausweis', CURRENT_DATE, 'jaynaApp.document.type.identity-card', 61, 'document', 'ID', 'document'),
(263, 'Reisepass', CURRENT_DATE, 'jaynaApp.document.type.passport', 61, 'document', 'PASS', 'document'),
(264, 'Führerschein', CURRENT_DATE, 'jaynaApp.document.type.drivers-license', 61, 'document', 'FS', 'document'),
(265, 'Schwerbehindertenausweis', CURRENT_DATE, 'jaynaApp.document.type.disability-id', 61, 'document', 'SBA', 'document'),
(266, 'Aufenthaltstitel', CURRENT_DATE, 'jaynaApp.document.type.residence-permit', 61, 'document', 'AT', 'document'),

-- Personenstandsdokumente
(267, 'Geburtsurkunde', CURRENT_DATE, 'jaynaApp.document.type.birth-certificate', 62, 'document', 'GEB', 'document'),
(268, 'Heiratsurkunde', CURRENT_DATE, 'jaynaApp.document.type.marriage-certificate', 62, 'document', 'HEI', 'document'),
(269, 'Scheidungsurteil', CURRENT_DATE, 'jaynaApp.document.type.divorce-decree', 62, 'document', 'SCH', 'document'),

-- Vollmachten
(270, 'Testament', CURRENT_DATE, 'jaynaApp.document.type.will', 63, 'document', 'TEST', 'document'),
(271, 'Patientenverfügung', CURRENT_DATE, 'jaynaApp.document.type.patient-directive', 63, 'document', 'PV', 'document'),
(272, 'Vollmacht', CURRENT_DATE, 'jaynaApp.document.type.power-of-attorney', 63, 'document', 'VM', 'document'),

-- Arbeitsdokumente
(273, 'Arbeitsvertrag', CURRENT_DATE, 'jaynaApp.document.type.employment-contract', 64, 'document', 'AV', 'document'),
(274, 'Kündigung', CURRENT_DATE, 'jaynaApp.document.type.termination', 64, 'document', 'KUN', 'document'),
(275, 'Abmahnung', CURRENT_DATE, 'jaynaApp.document.type.warning', 64, 'document', 'ABM', 'document'),
(276, 'Gehaltsabrechnung', CURRENT_DATE, 'jaynaApp.document.type.payslip', 64, 'document', 'GA', 'document'),
(277, 'Lohnsteuerbescheinigung', CURRENT_DATE, 'jaynaApp.document.type.income-tax-certificate', 64, 'document', 'LSB', 'document'),

-- Bildung
(278, 'Zeugnis', CURRENT_DATE, 'jaynaApp.document.type.certificate', 65, 'document', 'ZEU', 'document'),
(279, 'Zwischenzeugnis', CURRENT_DATE, 'jaynaApp.document.type.intermediate-certificate', 65, 'document', 'ZZ', 'document'),
(280, 'Abschlusszeugnis', CURRENT_DATE, 'jaynaApp.document.type.final-certificate', 65, 'document', 'AZ', 'document'),
(281, 'Studienbescheinigung', CURRENT_DATE, 'jaynaApp.document.type.study-certificate', 65, 'document', 'SB', 'document'),
(282, 'Hochschulabschluss', CURRENT_DATE, 'jaynaApp.document.type.university-degree', 65, 'document', 'HA', 'document'),

-- Bewerbungen
(283, 'Lebenslauf', CURRENT_DATE, 'jaynaApp.document.type.cv', 66, 'document', 'CV', 'document'),
(284, 'Anschreiben', CURRENT_DATE, 'jaynaApp.document.type.cover-letter', 66, 'document', 'AS', 'document'),
(285, 'Referenzen', CURRENT_DATE, 'jaynaApp.document.type.references', 66, 'document', 'REF', 'document'),

-- Bankwesen
(286, 'Kontoauszug', CURRENT_DATE, 'jaynaApp.document.type.account-statement', 67, 'document', 'KA', 'document'),
(287, 'Darlehensvertrag', CURRENT_DATE, 'jaynaApp.document.type.loan-contract', 67, 'document', 'DV', 'document'),
(288, 'Kreditkartenauszug', CURRENT_DATE, 'jaynaApp.document.type.credit-card-statement', 67, 'document', 'KKA', 'document'),
(289, 'SEPA-Mandat', CURRENT_DATE, 'jaynaApp.document.type.sepa-mandate', 67, 'document', 'SEPA', 'SEPA-Mandat'),

-- Buchhaltung
(290, 'Rechnung', CURRENT_DATE, 'jaynaApp.document.type.invoice', 68, 'document', 'RECH', 'document'),
(291, 'Quittung', CURRENT_DATE, 'jaynaApp.document.type.receipt', 68, 'document', 'QUIT', 'document'),
(292, 'Angebot', CURRENT_DATE, 'jaynaApp.document.type.quotation', 68, 'document', 'ANGE', 'document'),
(293, 'Mahnung', CURRENT_DATE, 'jaynaApp.document.type.dunning-letter', 68, 'document', 'MAHN', 'document'),
(294, 'Zahlungsnachweis', CURRENT_DATE, 'jaynaApp.document.type.payment-proof', 68, 'document', 'ZN', 'document'),

-- Versicherungen
(295, 'Versicherungspolice', CURRENT_DATE, 'jaynaApp.document.type.insurance-policy', 69, 'document', 'VP', 'document'),
(296, 'Schadensmeldung', CURRENT_DATE, 'jaynaApp.document.type.claim-report', 69, 'document', 'SM', 'document'),
(297, 'Leistungsbescheid', CURRENT_DATE, 'jaynaApp.document.type.benefit-statement', 69, 'document', 'LB', 'document'),

-- Mietdokumente
(298, 'Mietvertrag', CURRENT_DATE, 'jaynaApp.document.type.rental-contract', 70, 'document', 'MV', 'document'),
(299, 'Nebenkostenabrechnung', CURRENT_DATE, 'jaynaApp.document.type.utility-bill', 70, 'document', 'NKA', 'document'),
(300, 'Übergabeprotokoll', CURRENT_DATE, 'jaynaApp.document.type.handover-report', 70, 'document', 'UP', 'document'),

-- Immobilien
(301, 'Bauplan', CURRENT_DATE, 'jaynaApp.document.type.building-plan', 71, 'document', 'BP', 'document'),
(302, 'Baugenehmigung', CURRENT_DATE, 'jaynaApp.document.type.building-permit', 71, 'document', 'BG', 'document'),
(303, 'Kaufvertrag Haus', CURRENT_DATE, 'jaynaApp.document.type.purchase-contract-house', 71, 'document', 'KVH', 'document'),

-- Fahrzeuge
(304, 'Fahrzeugbrief', CURRENT_DATE, 'jaynaApp.document.type.vehicle-title', 72, 'document', 'FB', 'document'),
(305, 'Fahrzeugschein', CURRENT_DATE, 'jaynaApp.document.type.vehicle-registration', 72, 'document', 'FS', 'document'),
(306, 'TÜV-Bescheinigung', CURRENT_DATE, 'jaynaApp.document.type.tuv-certificate', 72, 'document', 'TUEV', 'document'),
(307, 'Wartungsheft', CURRENT_DATE, 'jaynaApp.document.type.maintenance-record', 72, 'document', 'WH', 'document'),
(308, 'Werkstattrechnung', CURRENT_DATE, 'jaynaApp.document.type.workshop-invoice', 72, 'document', 'WR', 'document'),

-- Medizinische Dokumente
(309, 'Impfpass', CURRENT_DATE, 'jaynaApp.document.type.vaccination-card', 73, 'document', 'IMP', 'document'),
(310, 'Arztbericht', CURRENT_DATE, 'jaynaApp.document.type.medical-report', 73, 'document', 'AB', 'document'),
(311, 'Laborergebnis', CURRENT_DATE, 'jaynaApp.document.type.lab-result', 73, 'document', 'LAB', 'document'),
(312, 'Rezept', CURRENT_DATE, 'jaynaApp.document.type.prescription', 73, 'document', 'REZ', 'document'),
(313, 'Röntgenbilder', CURRENT_DATE, 'jaynaApp.document.type.xray-images', 73, 'document', 'ROE', 'document'),

-- Pflege & Unterstützung
(314, 'Pflegegradbescheid', CURRENT_DATE, 'jaynaApp.document.type.care-level-notice', 74, 'document', 'PGB', 'document'),
(315, 'Betreuungsurkunde', CURRENT_DATE, 'jaynaApp.document.type.guardianship-certificate', 74, 'document', 'BU', 'document'),

-- Korrespondenz
(316, 'Einladung', CURRENT_DATE, 'jaynaApp.document.type.invitation', 75, 'document', 'EIN', 'document'),
(317, 'Glückwunschkarte', CURRENT_DATE, 'jaynaApp.document.type.greeting-card', 75, 'document', 'GK', 'document'),
(318, 'Dankschreiben', CURRENT_DATE, 'jaynaApp.document.type.thank-you-letter', 75, 'document', 'DS', 'document'),
(319, 'Allgemeine Korrespondenz', CURRENT_DATE, 'jaynaApp.document.type.general-correspondence', 75, 'document', 'AK', 'document'),

-- Protokolle
(320, 'Sitzungsprotokoll', CURRENT_DATE, 'jaynaApp.document.type.meeting-minutes', 76, 'document', 'SP', 'document'),
(321, 'Vereinsprotokoll', CURRENT_DATE, 'jaynaApp.document.type.club-minutes', 76, 'document', 'VP', 'document'),

-- Digitale Kommunikation
(322, 'E-Mail-Ausdruck', CURRENT_DATE, 'jaynaApp.document.type.email-printout', 77, 'document', 'EMA', 'document'),
(323, 'Chat-Protokoll', CURRENT_DATE, 'jaynaApp.document.type.chat-log', 77, 'document', 'CP', 'document'),
(324, 'Social-Media-Post', CURRENT_DATE, 'jaynaApp.document.type.social-media-post', 77, 'document', 'SMP', 'document'),

-- Gerichtsdokumente
(325, 'Urteil', CURRENT_DATE, 'jaynaApp.document.type.judgment', 78, 'document', 'URT', 'document'),
(326, 'Ladung', CURRENT_DATE, 'jaynaApp.document.type.summons', 78, 'document', 'LAD', 'document'),
(327, 'Bußgeldbescheid', CURRENT_DATE, 'jaynaApp.document.type.penalty-order', 78, 'document', 'BB', 'document'),
(328, 'Vollstreckungsbescheid', CURRENT_DATE, 'jaynaApp.document.type.enforcement-order', 78, 'document', 'VB', 'document'),

-- Verträge & Bedingungen
(329, 'Allgemeine Geschäftsbedingungen', CURRENT_DATE, 'jaynaApp.document.type.terms-and-conditions', 79, 'document', 'AGB', 'document'),
(330, 'Vertragsbedingungen', CURRENT_DATE, 'jaynaApp.document.type.contract-terms', 79, 'document', 'VT', 'document'),
(331, 'Datenschutzerklärung', CURRENT_DATE, 'jaynaApp.document.type.privacy-notice', 79, 'document', 'DSGVO', 'document'),

-- Rechnungen
(332, 'Stromrechnung', CURRENT_DATE, 'jaynaApp.document.type.electricity-bill', 80, 'document', 'SR', 'document'),
(333, 'Wasserrechnung', CURRENT_DATE, 'jaynaApp.document.type.water-bill', 80, 'document', 'WR', 'document'),
(334, 'Gasrechnung', CURRENT_DATE, 'jaynaApp.document.type.gas-bill', 80, 'document', 'GR', 'document'),
(335, 'Internet/Telefon-Rechnung', CURRENT_DATE, 'jaynaApp.document.type.internet-phone-bill', 80, 'document', 'ITR', 'document')
ON CONFLICT (id) DO NOTHING;

-- Bestätigung der erstellten Datensätze
SELECT 'Dokumenten-Kategorie erstellt:' as info, COUNT(*) as anzahl FROM category WHERE entity_name = 'document'
UNION ALL
SELECT 'Dokumenten-Sub-Kategorien erstellt:', COUNT(*) FROM sub_category WHERE category_id = 6
UNION ALL
SELECT 'Dokumenten-Sub-Category-Groups erstellt:', COUNT(*) FROM sub_category_group WHERE sub_category_id IN (28,29,30,31,32,33,34,35)
UNION ALL
SELECT 'Dokumenten-Group-Types erstellt:', COUNT(*) FROM group_type WHERE sub_category_group_id IN (61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80);
