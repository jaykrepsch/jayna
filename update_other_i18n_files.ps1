# PowerShell-Skript zum Aktualisieren aller anderen i18n-Dateien
# Struktur: createTitle, updateTitle, viewTitle oben, dann spezifische Abschnitte, relations-title unten

# Financeaccount-Dateien
$financeaccountFiles = @(
    "apple-pay",
    "charge",
    "checking-account",
    "debit",
    "google-pay",
    "klarna",
    "online-virtual",
    "paypal",
    "prepaid",
    "revolving",
    "samsung",
    "saving-account",
    "skrill",
    "stripe"
)

# Realestate-Dateien
$realestateFiles = @(
    "allotment-garden",
    "building-plot",
    "carport",
    "commercial-residential-property",
    "farmland",
    "forest",
    "fruit",
    "garage",
    "meadow",
    "parking-house",
    "parking-space",
    "pasture"
)

# Contact-Dateien
$contactFiles = @(
    "contact-company-business-businesscontact",
    "contact-group-extended-extendedcontact",
    "contact-group-group-groupcontact",
    "contact-single-contact-contact",
    "contact-single-family-familycontact",
    "contact-single-simple-simplecontact",
    "contact-single-single-singlecontact"
)

# Mobility-Dateien
$mobilityFiles = @(
    "car",
    "mobility"
)

# Aktualisiere Financeaccount-Dateien
foreach ($file in $financeaccountFiles) {
    $i18nFile = "src/main/webapp/i18n/de/financeaccount/i18n-$file.json"
    
    if (Test-Path $i18nFile) {
        Write-Host "Aktualisiere Financeaccount: $i18nFile"
        
        # Erstelle neue Struktur
        $newContent = @{}
        
        # Füge createTitle, updateTitle, viewTitle hinzu
        $newContent["jaynaApp.$file.createTitle"] = "$file anlegen"
        $newContent["jaynaApp.$file.updateTitle"] = "$file bearbeiten"
        $newContent["jaynaApp.$file.viewTitle"] = "$file anzeigen"
        
        # Füge relations-title am Ende hinzu
        $newContent["jaynaApp.$file.relations-title"] = "Verknüpfungen"
        
        # Konvertiere zurück zu JSON und speichere
        $newContent | ConvertTo-Json -Depth 10 | Set-Content $i18nFile
    }
}

# Aktualisiere Realestate-Dateien
foreach ($file in $realestateFiles) {
    $i18nFile = "src/main/webapp/i18n/de/realestate/i18n-$file.json"
    
    if (Test-Path $i18nFile) {
        Write-Host "Aktualisiere Realestate: $i18nFile"
        
        # Erstelle neue Struktur
        $newContent = @{}
        
        # Füge createTitle, updateTitle, viewTitle hinzu
        $newContent["jaynaApp.$file.createTitle"] = "$file anlegen"
        $newContent["jaynaApp.$file.updateTitle"] = "$file bearbeiten"
        $newContent["jaynaApp.$file.viewTitle"] = "$file anzeigen"
        
        # Füge relations-title am Ende hinzu
        $newContent["jaynaApp.$file.relations-title"] = "Verknüpfungen"
        
        # Konvertiere zurück zu JSON und speichere
        $newContent | ConvertTo-Json -Depth 10 | Set-Content $i18nFile
    }
}

# Aktualisiere Contact-Dateien
foreach ($file in $contactFiles) {
    $i18nFile = "src/main/webapp/i18n/de/contact/i18n-$file.json"
    
    if (Test-Path $i18nFile) {
        Write-Host "Aktualisiere Contact: $i18nFile"
        
        # Erstelle neue Struktur
        $newContent = @{}
        
        # Füge createTitle, updateTitle, viewTitle hinzu
        $newContent["jaynaApp.$file.createTitle"] = "$file anlegen"
        $newContent["jaynaApp.$file.updateTitle"] = "$file bearbeiten"
        $newContent["jaynaApp.$file.viewTitle"] = "$file anzeigen"
        
        # Füge relations-title am Ende hinzu
        $newContent["jaynaApp.$file.relations-title"] = "Verknüpfungen"
        
        # Konvertiere zurück zu JSON und speichere
        $newContent | ConvertTo-Json -Depth 10 | Set-Content $i18nFile
    }
}

# Aktualisiere Mobility-Dateien
foreach ($file in $mobilityFiles) {
    $i18nFile = "src/main/webapp/i18n/de/mobility/i18n-$file.json"
    
    if (Test-Path $i18nFile) {
        Write-Host "Aktualisiere Mobility: $i18nFile"
        
        # Erstelle neue Struktur
        $newContent = @{}
        
        # Füge createTitle, updateTitle, viewTitle hinzu
        $newContent["jaynaApp.$file.createTitle"] = "$file anlegen"
        $newContent["jaynaApp.$file.updateTitle"] = "$file bearbeiten"
        $newContent["jaynaApp.$file.viewTitle"] = "$file anzeigen"
        
        # Füge relations-title am Ende hinzu
        $newContent["jaynaApp.$file.relations-title"] = "Verknüpfungen"
        
        # Konvertiere zurück zu JSON und speichere
        $newContent | ConvertTo-Json -Depth 10 | Set-Content $i18nFile
    }
}

Write-Host "Alle anderen i18n-Dateien wurden aktualisiert!" 