# PowerShell-Skript zum korrekten Umbenennen aller i18n-Dateien in anderen Entitäten
# Schema: i18n-category-subcategory-subcategorygroup-grouptype.json

# Financeaccount-Mapping
$financeaccountMapping = @{
    "i18n-apple-pay.json" = "i18n-financeaccount-systems-applepay-applepay.json"
    "i18n-charge.json" = "i18n-financeaccount-bank-charge-charge.json"
    "i18n-checking-account.json" = "i18n-financeaccount-bank-checkingaccount-checkingaccount.json"
    "i18n-debit.json" = "i18n-financeaccount-bank-debit-debit.json"
    "i18n-google-pay.json" = "i18n-financeaccount-systems-googlepay-googlepay.json"
    "i18n-klarna.json" = "i18n-financeaccount-systems-klarna-klarna.json"
    "i18n-online-virtual.json" = "i18n-financeaccount-systems-onlinevirtual-onlinevirtual.json"
    "i18n-paypal.json" = "i18n-financeaccount-systems-paypal-paypal.json"
    "i18n-prepaid.json" = "i18n-financeaccount-bank-prepaid-prepaid.json"
    "i18n-revolving.json" = "i18n-financeaccount-bank-revolving-revolving.json"
    "i18n-samsung.json" = "i18n-financeaccount-systems-samsungpay-samsungpay.json"
    "i18n-saving-account.json" = "i18n-financeaccount-bank-savingaccount-savingaccount.json"
    "i18n-skrill.json" = "i18n-financeaccount-systems-skrill-skrill.json"
    "i18n-stripe.json" = "i18n-financeaccount-systems-stripe-stripe.json"
}

# Realestate-Mapping
$realestateMapping = @{
    "i18n-allotment-garden.json" = "i18n-realestate-plot-allotmentgarden-allotmentgarden.json"
    "i18n-building-plot.json" = "i18n-realestate-plot-buildingplot-buildingplot.json"
    "i18n-carport.json" = "i18n-realestate-parking-carport-carport.json"
    "i18n-commercial-residential-property.json" = "i18n-realestate-property-commercialresidential-commercialresidentialproperty.json"
    "i18n-farmland.json" = "i18n-realestate-plot-farmland-farmland.json"
    "i18n-forest.json" = "i18n-realestate-plot-forest-forest.json"
    "i18n-fruit.json" = "i18n-realestate-plot-fruit-fruit.json"
    "i18n-garage.json" = "i18n-realestate-parking-garage-garage.json"
    "i18n-meadow.json" = "i18n-realestate-plot-meadow-meadow.json"
    "i18n-parking-house.json" = "i18n-realestate-parking-parkinghouse-parkinghouse.json"
    "i18n-parking-space.json" = "i18n-realestate-parking-parkingspace-parkingspace.json"
    "i18n-pasture.json" = "i18n-realestate-plot-pasture-pasture.json"
}

# Contact-Mapping
$contactMapping = @{
    "i18n-contact-company-business-businesscontact.json" = "i18n-contact-company-business-businesscontact.json"
    "i18n-contact-group-extended-extendedcontact.json" = "i18n-contact-group-extended-extendedcontact.json"
    "i18n-contact-group-group-groupcontact.json" = "i18n-contact-group-group-groupcontact.json"
    "i18n-contact-single-contact-contact.json" = "i18n-contact-single-contact-contact.json"
    "i18n-contact-single-family-familycontact.json" = "i18n-contact-single-family-familycontact.json"
    "i18n-contact-single-simple-simplecontact.json" = "i18n-contact-single-simple-simplecontact.json"
    "i18n-contact-single-single-singlecontact.json" = "i18n-contact-single-single-singlecontact.json"
}

# Mobility-Mapping
$mobilityMapping = @{
    "i18n-car.json" = "i18n-mobility-passenger-car-car.json"
    "i18n-mobility.json" = "i18n-mobility-single-single-singlemobility.json"
}

# Aktualisiere Financeaccount-Dateien
Write-Host "Aktualisiere Financeaccount-Dateien..."
foreach ($oldName in $financeaccountMapping.Keys) {
    $newName = $financeaccountMapping[$oldName]
    $oldPath = "src/main/webapp/i18n/de/financeaccount/$oldName"
    $newPath = "src/main/webapp/i18n/de/financeaccount/$newName"
    
    if (Test-Path $oldPath) {
        Write-Host "Benenne um: $oldName -> $newName"
        if (Test-Path $newPath) {
            Write-Host "  Ziel existiert bereits, lösche alte Datei"
            Remove-Item $oldPath
        } else {
            Move-Item $oldPath $newPath
        }
    }
}

# Aktualisiere Realestate-Dateien
Write-Host "Aktualisiere Realestate-Dateien..."
foreach ($oldName in $realestateMapping.Keys) {
    $newName = $realestateMapping[$oldName]
    $oldPath = "src/main/webapp/i18n/de/realestate/$oldName"
    $newPath = "src/main/webapp/i18n/de/realestate/$newName"
    
    if (Test-Path $oldPath) {
        Write-Host "Benenne um: $oldName -> $newName"
        if (Test-Path $newPath) {
            Write-Host "  Ziel existiert bereits, lösche alte Datei"
            Remove-Item $oldPath
        } else {
            Move-Item $oldPath $newPath
        }
    }
}

# Aktualisiere Contact-Dateien
Write-Host "Aktualisiere Contact-Dateien..."
foreach ($oldName in $contactMapping.Keys) {
    $newName = $contactMapping[$oldName]
    $oldPath = "src/main/webapp/i18n/de/contact/$oldName"
    $newPath = "src/main/webapp/i18n/de/contact/$newName"
    
    if (Test-Path $oldPath) {
        Write-Host "Benenne um: $oldName -> $newName"
        if (Test-Path $newPath) {
            Write-Host "  Ziel existiert bereits, lösche alte Datei"
            Remove-Item $oldPath
        } else {
            Move-Item $oldPath $newPath
        }
    }
}

# Aktualisiere Mobility-Dateien
Write-Host "Aktualisiere Mobility-Dateien..."
foreach ($oldName in $mobilityMapping.Keys) {
    $newName = $mobilityMapping[$oldName]
    $oldPath = "src/main/webapp/i18n/de/mobility/$oldName"
    $newPath = "src/main/webapp/i18n/de/mobility/$newName"
    
    if (Test-Path $oldPath) {
        Write-Host "Benenne um: $oldName -> $newName"
        if (Test-Path $newPath) {
            Write-Host "  Ziel existiert bereits, lösche alte Datei"
            Remove-Item $oldPath
        } else {
            Move-Item $oldPath $newPath
        }
    }
}

Write-Host "Alle i18n-Dateien in anderen Entitäten wurden korrekt umbenannt!" 