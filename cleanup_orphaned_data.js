const axios = require('axios');

async function cleanupOrphanedData() {
  try {
    console.log('🧹 Starte Bereinigung verwaister Datensätze...\n');

    // 1. Alle Entitäten abrufen
    const [contracts, realestates, contacts, financeAccounts, mobilities] = await Promise.all([
      axios.get('http://localhost:8080/api/contracts?size=1000').then(r => r.data),
      axios.get('http://localhost:8080/api/realestates?size=1000').then(r => r.data),
      axios.get('http://localhost:8080/api/contacts?size=1000').then(r => r.data),
      axios.get('http://localhost:8080/api/finance-accounts?size=1000').then(r => r.data),
      axios.get('http://localhost:8080/api/mobilities?size=1000').then(r => r.data)
    ]);

    console.log(`📊 Gefundene Datensätze:`);
    console.log(`   - Contracts: ${contracts.length}`);
    console.log(`   - RealEstate: ${realestates.length}`);
    console.log(`   - Contacts: ${contacts.length}`);
    console.log(`   - FinanceAccounts: ${financeAccounts.length}`);
    console.log(`   - Mobilities: ${mobilities.length}\n`);

    // 2. IDs sammeln
    const validIds = {
      contracts: contracts.map(c => c.id),
      realestates: realestates.map(r => r.id),
      contacts: contacts.map(c => c.id),
      financeAccounts: financeAccounts.map(f => f.id),
      mobilities: mobilities.map(m => m.id)
    };

    // 3. Verwaiste Referenzen finden
    const orphanedReferences = [];

    // Contract-Referenzen überprüfen
    for (const contract of contracts) {
      // Contract -> RealEstate
      if (contract.contractRealestates && contract.contractRealestates.length > 0) {
        for (const realestateRef of contract.contractRealestates) {
          if (!validIds.realestates.includes(realestateRef.id)) {
            orphanedReferences.push({
              type: 'Contract -> RealEstate',
              sourceId: contract.id,
              sourceLabel: contract.label,
              orphanedId: realestateRef.id
            });
          }
        }
      }

      // Contract -> Contact
      if (contract.contactContracts && contract.contactContracts.length > 0) {
        for (const contactRef of contract.contactContracts) {
          if (!validIds.contacts.includes(contactRef.id)) {
            orphanedReferences.push({
              type: 'Contract -> Contact',
              sourceId: contract.id,
              sourceLabel: contract.label,
              orphanedId: contactRef.id
            });
          }
        }
      }

      // Contract -> FinanceAccount
      if (contract.contractFinanceAccounts && contract.contractFinanceAccounts.length > 0) {
        for (const financeAccountRef of contract.contractFinanceAccounts) {
          if (!validIds.financeAccounts.includes(financeAccountRef.id)) {
            orphanedReferences.push({
              type: 'Contract -> FinanceAccount',
              sourceId: contract.id,
              sourceLabel: contract.label,
              orphanedId: financeAccountRef.id
            });
          }
        }
      }

      // Contract -> Mobility
      if (contract.contractMobilities && contract.contractMobilities.length > 0) {
        for (const mobilityRef of contract.contractMobilities) {
          if (!validIds.mobilities.includes(mobilityRef.id)) {
            orphanedReferences.push({
              type: 'Contract -> Mobility',
              sourceId: contract.id,
              sourceLabel: contract.label,
              orphanedId: mobilityRef.id
            });
          }
        }
      }
    }

    // 4. Problematic Datensätze finden (z.B. leere oder ungültige)
    const problematicData = [];

    // Leere oder ungültige Contracts
    contracts.forEach(contract => {
      if (!contract.label || contract.label.trim() === '') {
        problematicData.push({
          type: 'Contract',
          id: contract.id,
          reason: 'Leerer Label',
          data: contract
        });
      }
    });

    // Leere oder ungültige Mobilities
    mobilities.forEach(mobility => {
      if (!mobility.label || mobility.label.trim() === '') {
        problematicData.push({
          type: 'Mobility',
          id: mobility.id,
          reason: 'Leerer Label',
          data: mobility
        });
      }
    });

    // 5. Ergebnisse anzeigen
    if (orphanedReferences.length === 0 && problematicData.length === 0) {
      console.log('✅ Keine verwaisten oder problematischen Datensätze gefunden!');
      return;
    }

    if (orphanedReferences.length > 0) {
      console.log(`❌ Gefundene verwaiste Referenzen (${orphanedReferences.length}):`);
      orphanedReferences.forEach(ref => {
        console.log(`   - ${ref.type}: ${ref.sourceLabel} (ID: ${ref.sourceId}) -> ungültige ID: ${ref.orphanedId}`);
      });
    }

    if (problematicData.length > 0) {
      console.log(`⚠️  Gefundene problematische Datensätze (${problematicData.length}):`);
      problematicData.forEach(item => {
        console.log(`   - ${item.type} ID ${item.id}: ${item.reason}`);
      });
    }

    // 6. Automatische Bereinigung
    console.log('\n🧹 Starte automatische Bereinigung...\n');

    let deletedCount = 0;

    // Verwaiste Referenzen entfernen
    for (const ref of orphanedReferences) {
      try {
        console.log(`🗑️  Entferne verwaiste Referenz: ${ref.type} (${ref.orphanedId})`);
        
        // Contract aktualisieren und verwaiste Referenz entfernen
        const contract = contracts.find(c => c.id === ref.sourceId);
        if (contract) {
          let updatedContract = { ...contract };
          
          // Entsprechende Referenz entfernen
          if (ref.type.includes('RealEstate')) {
            updatedContract.contractRealestates = contract.contractRealestates?.filter(r => r.id !== ref.orphanedId) || [];
          } else if (ref.type.includes('Contact')) {
            updatedContract.contactContracts = contract.contactContracts?.filter(c => c.id !== ref.orphanedId) || [];
          } else if (ref.type.includes('FinanceAccount')) {
            updatedContract.contractFinanceAccounts = contract.contractFinanceAccounts?.filter(f => f.id !== ref.orphanedId) || [];
          } else if (ref.type.includes('Mobility')) {
            updatedContract.contractMobilities = contract.contractMobilities?.filter(m => m.id !== ref.orphanedId) || [];
          }

          await axios.put(`http://localhost:8080/api/contracts/${contract.id}`, updatedContract);
          console.log(`✅ Referenz entfernt`);
          deletedCount++;
        }
      } catch (error) {
        console.log(`❌ Fehler beim Entfernen der Referenz: ${error.response?.data?.detail || error.message}`);
      }
    }

    // Problematic Datensätze löschen
    for (const item of problematicData) {
      try {
        console.log(`🗑️  Lösche problematischen Datensatz: ${item.type} ID ${item.id}`);
        
        if (item.type === 'Contract') {
          await axios.delete(`http://localhost:8080/api/contracts/${item.id}`);
        } else if (item.type === 'Mobility') {
          await axios.delete(`http://localhost:8080/api/mobilities/${item.id}`);
        }
        
        console.log(`✅ ${item.type} ID ${item.id} gelöscht`);
        deletedCount++;
      } catch (error) {
        console.log(`❌ Fehler beim Löschen: ${error.response?.data?.detail || error.message}`);
      }
    }

    console.log(`\n✅ Bereinigung abgeschlossen! ${deletedCount} Datensätze/Referenzen bereinigt.`);

  } catch (error) {
    console.error('❌ Fehler:', error.response?.data || error.message);
  }
}

// Funktion zum manuellen Löschen spezifischer IDs
async function deleteSpecificData(entityType, id) {
  try {
    console.log(`🗑️  Lösche ${entityType} mit ID ${id}...`);
    
    const endpoints = {
      'contract': 'contracts',
      'mobility': 'mobilities',
      'realestate': 'realestates',
      'contact': 'contacts',
      'financeaccount': 'finance-accounts'
    };

    const endpoint = endpoints[entityType.toLowerCase()];
    if (!endpoint) {
      console.log(`❌ Unbekannter Entitätstyp: ${entityType}`);
      return;
    }

    await axios.delete(`http://localhost:8080/api/${endpoint}/${id}`);
    console.log(`✅ ${entityType} ID ${id} erfolgreich gelöscht`);
  } catch (error) {
    console.log(`❌ Fehler beim Löschen: ${error.response?.data?.detail || error.message}`);
  }
}

// Hauptfunktion ausführen
if (process.argv.length > 2) {
  const command = process.argv[2];
  const entityType = process.argv[3];
  const id = process.argv[4];

  if (command === 'delete' && entityType && id) {
    deleteSpecificData(entityType, parseInt(id));
  } else {
    console.log('Verwendung: node cleanup_orphaned_data.js [delete <entityType> <id>]');
    console.log('Beispiel: node cleanup_orphaned_data.js delete mobility 1851');
  }
} else {
  cleanupOrphanedData();
} 