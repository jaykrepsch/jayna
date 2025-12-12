const fs = require('fs');
const path = require('path');

// Hilfsfunktion: form_name in deutsche Teile aufteilen
function splitFormName(formName) {
    if (!formName) return '';
    const parts = formName.split('-');
    const dict = {
        contract: 'Vertrag',
        employment: 'Beschäftigung',
        employee: 'Angestellter',
        official: 'Beamter',
        public: 'Öffentlich',
        service: 'Dienst',
        work: 'Arbeit',
        fulltime: 'Vollzeit',
        parttime: 'Teilzeit',
        minijob: 'Minijob',
        internship: 'Praktikum',
        termination: 'Kündigung',
        savings: 'Sparen',
        realestate: 'Immobilie',
        buildingsavings: 'Bausparen',
        overnight: 'Tagesgeld',
        creditcard: 'Kreditkarte',
        credit: 'Kredit',
        propertymanagement: 'Hausverwaltung',
        deposit: 'Einlage',
        bankaccount: 'Bankkonto',
        loan: 'Darlehen',
        loancontract: 'Darlehensvertrag',
        annuityloan: 'Annuitätendarlehen',
        energy: 'Energie',
        electric: 'Elektrik',
        stromcontract: 'Stromvertrag',
        energycontract: 'Energievertrag',
        energycontract2: 'Energievertrag2',
        insurance: 'Versicherung',
        automobile: 'Kraftfahrzeug',
        automobilecontract: 'KFZ-Vertrag',
        lease: 'Leasing',
        leasecontract: 'Leasingvertrag',
        media: 'Medien',
        mediacontract: 'Medienvertrag',
        streaming: 'Streaming',
        streamingcontract: 'Streamingvertrag',
        health: 'Gesundheit',
        healthcontract: 'Gesundheitsvertrag',
        residential: 'Wohngebäude',
        residentialcontract: 'Wohngebäudevertrag',
        liability: 'Haftpflicht',
        liabilitycontract: 'Haftpflichtvertrag',
        fire: 'Feuer',
        firecontract: 'Feuerversicherung',
        furniture: 'Hausrat',
        furniturecontract: 'Hausratvertrag',
        accident: 'Unfall',
        accidentcontract: 'Unfallversicherung',
        disability: 'Berufsunfähigkeit',
        disabilitycontract: 'Berufsunfähigkeitsversicherung',
        legalprotection: 'Rechtsschutz',
        legalprotectioncontract: 'Rechtsschutzversicherung',
        travel: 'Reise',
        travelcontract: 'Reiseversicherung',
        dailyhospital: 'Krankenhaustagegeld',
        dailyhospitalcontract: 'Krankenhaustagegeldversicherung',
        dailysickness: 'Krankentagegeld',
        dailysicknesscontract: 'Krankentagegeldversicherung',
        priaddout: 'Privat Zusatz ambulant',
        priaddoutcontract: 'Privat Zusatz ambulant Vertrag',
        priaddstat: 'Privat Zusatz stationär',
        priaddstatcontract: 'Privat Zusatz stationär Vertrag',
        priaddtooth: 'Privat Zusatz Zahn',
        priaddtoothcontract: 'Privat Zusatz Zahn Vertrag',
        prisickfull: 'Privat Kranken voll',
        prisickfullcontract: 'Privat Kranken voll Vertrag',
        longtermcare: 'Pflegeversicherung',
        longtermcarecontract: 'Pflegeversicherungsvertrag',
        longtermcaredaily: 'Pflegetagegeld',
        longtermcaredailycontract: 'Pflegetagegeldversicherung',
        pensionfund: 'Rentenversicherung Fonds',
        pensionfundcontract: 'Rentenversicherung Fonds Vertrag',
        classicpension: 'Rentenversicherung klassisch',
        classicpensioncontract: 'Rentenversicherung klassisch Vertrag',
        risklife: 'Risiko Lebensversicherung',
        risklifecontract: 'Risiko Lebensversicherungsvertrag',
        builderliability: 'Bauherrenhaftpflicht',
        builderliabilitycontract: 'Bauherrenhaftpflichtvertrag',
        bike: 'Fahrrad',
        bikecontract: 'Fahrradversicherung',
        photovoltaic: 'Photovoltaik',
        photovoltaiccontract: 'Photovoltaikversicherung',
        rental: 'Mietausfall',
        rentalcontract: 'Mietausfallversicherung',
        liabilitypet: 'Tierhalterhaftpflicht',
        liabilitypetcontract: 'Tierhalterhaftpflichtvertrag',
        pet: 'Tier OP',
        petcontract: 'Tier OP Versicherung',
        environmentaldamage: 'Umweltschadenhaftpflicht',
        environmentaldamagecontract: 'Umweltschadenhaftpflichtvertrag',
        inthealth: 'Ausland',
        inthealthcontract: 'Auslandskrankenversicherung',
        domanager: 'D&O Managerhaftpflicht',
        domanagercontract: 'D&O Managerhaftpflichtvertrag',
        expatriates: 'Kunst',
        expatriatescontract: 'Kunstversicherung',
        sale: 'Verkauf',
        salecontract: 'Kaufvertrag',
        purchase: 'Kauf',
        purchasecontract: 'Kaufvertrag',
        fees: 'Gebühren',
        municipal: 'Gemeinde',
        municipalcontract: 'Gemeindevertrag',
        gardener: 'Gärtner',
        gardenercontract: 'Gärtnervertrag',
        gardenercontract2: 'Gärtnervertrag2',
        caretaker: 'Hausmeister',
        service: 'Service',
        broker: 'Makler',
        realestateagent: 'Immobilienmakler',
        insurancebroker: 'Versicherungsmakler',
        notary: 'Notar',
        notarycontract: 'Notarvertrag',
        cleaning: 'Reinigung',
        cleaningcontract: 'Reinigungsvertrag',
        brokercontract: 'Maklervertrag',
        elevator: 'Aufzug',
        telecommunication: 'Telekommunikation',
        telecommunicationcontract: 'Telekommunikationsvertrag',
        telecommunicationcontract2: 'Telekommunikationsvertrag2',
        heating: 'Heizung',
        caretakerservice: 'Hausmeisterservice',
        chimney: 'Schornsteinfeger',
        sweeper: 'Feger',
        chimneysweeper: 'Schornsteinfeger',
        heatpump: 'Wärmepumpe',
        maintenance: 'Wartung',
        maintenancecontract: 'Wartungsvertrag',
        security: 'Sicherheit',
        securitycontract: 'Sicherheitsvertrag',
        software: 'Software',
        softwarecontract: 'Softwarevertrag',
        tax: 'Steuer',
        realestatetaxcontract: 'Immobiliensteuervertrag',
        distribution: 'Vertrieb',
        distributioncontract: 'Vertriebsvertrag',
        consulting: 'Beratung',
        consultingcontract: 'Beratungsvertrag',
        franchise: 'Franchise',
        franchisecontract: 'Franchisevertrag',
        rentalmanagement: 'Mietverwaltung',
        rentalmanagementcontract: 'Mietverwaltungsvertrag',
        employment: 'Beschäftigung',
        employmentcontract: 'Beschäftigungsvertrag',
        publicservice: 'Öffentlicher Dienst',
        fulltime: 'Vollzeit',
        parttime: 'Teilzeit',
        minijob: 'Minijob',
        internship: 'Praktikum',
        workcontract: 'Arbeitsvertrag',
        terminationcontract: 'Kündigungsvertrag',
        fixedtermcontract: 'Festgeldvertrag',
        savingscontract: 'Sparvertrag',
        creditcardcontract: 'Kreditkartenvertrag',
        propertymanagementcontract: 'Hausverwaltungsvertrag',
        depositcontract: 'Einlagenvertrag',
        loanagreementcontract: 'Darlehensvereinbarungsvertrag',
        leasecontract: 'Leasingvertrag',
        rentalcontract: 'Mietvertrag',
    };
    return parts.map(p => dict[p] || p).join(' → ');
}

// CSV einlesen (aus group_types_complete.csv)
const inputFile = path.join(__dirname, 'group_types_complete.csv');
const outputFile = path.join(__dirname, 'group_types_complete_fixed.csv');

const lines = fs.readFileSync(inputFile, 'utf-8').split(/\r?\n/);
const outLines = [];

// Header erstellen
outLines.push('Kategorie,Art,Gruppierung,Sparte,Form_Name,Form_Name_Teil1,Form_Name_Teil2,Form_Name_Teil3,Form_Name_Teil4');

// Datenzeilen verarbeiten
for (let i = 1; i < lines.length; i++) {
    const line = lines[i].trim();
    if (!line || line.startsWith('-') || line.startsWith('|')) continue;
    
    // Zeile mit | aufteilen und Leerzeichen entfernen
    const cols = line.split('|').map(s => s.trim()).filter(s => s);
    
    if (cols.length >= 5) {
        const kategorie = cols[0];
        const art = cols[1];
        const gruppierung = cols[2];
        const sparte = cols[3];
        const formName = cols[4];
        const parts = formName.split('-');
        // Bis zu 4 Teile, Rest leer
        const p1 = parts[0] ? splitFormName(parts[0]) : '';
        const p2 = parts[1] ? splitFormName(parts[1]) : '';
        const p3 = parts[2] ? splitFormName(parts[2]) : '';
        const p4 = parts[3] ? splitFormName(parts[3]) : '';
        // CSV-Zeile mit Kommas erstellen
        const csvLine = `"${kategorie}","${art}","${gruppierung}","${sparte}","${formName}","${p1}","${p2}","${p3}","${p4}"`;
        outLines.push(csvLine);
    }
}

fs.writeFileSync(outputFile, outLines.join('\n'), 'utf-8');
console.log('Vollständige CSV-Tabelle gespeichert als', outputFile);
console.log('Anzahl Einträge:', outLines.length - 1); // -1 für Header 