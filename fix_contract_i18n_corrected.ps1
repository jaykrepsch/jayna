# PowerShell-Skript zum Korrigieren aller contract i18n-Dateien nach dem korrekten Schema

# Funktion zum Korrigieren einer i18n-Datei
function Fix-ContractI18nFile {
    param(
        [string]$FilePath
    )
    
    Write-Host "Überprüfe: $FilePath"
    
    # Extrahiere den Basis-Pfad aus dem Dateinamen (ohne i18n- Präfix)
    $fileName = [System.IO.Path]::GetFileNameWithoutExtension($FilePath)
    $basePath = $fileName -replace "^i18n-", ""
    $fullPath = "jaynaApp.$basePath"
    
    # Erstelle neue Struktur
    $newContent = @{}
    
    # Füge createTitle, updateTitle, viewTitle hinzu (oben)
    $contractType = Get-ContractType $basePath
    $newContent["$fullPath.createTitle"] = "$contractType erstellen"
    $newContent["$fullPath.updateTitle"] = "$contractType bearbeiten"
    $newContent["$fullPath.viewTitle"] = "$contractType anzeigen"
    
    # Lese Form-Definition um spezifische Abschnitte zu finden
    $formDefPath = "src/main/webapp/app/shared/form-definitions/contract/$basePath.json"
    if (Test-Path $formDefPath) {
        $formDef = Get-Content $formDefPath -Raw | ConvertFrom-Json
        
        # Finde spezifische Abschnitte (mit i18nSource)
        foreach ($part in $formDef.parts) {
            if ($part.i18nSource -and $part.title -like "*$basePath*") {
                $sectionName = $part.title -replace "jaynaApp\.$basePath\.", ""
                $sectionName = $sectionName -replace "\.title$", ""
                
                # Füge Abschnittstitel hinzu
                $sectionTitle = Get-SectionTitle $sectionName
                $newContent["$fullPath.$sectionName.title"] = $sectionTitle
                
                # Füge Felder hinzu
                foreach ($field in $part.fields) {
                    $fieldName = $field.name
                    $fieldTitle = Get-FieldTitle $fieldName
                    $newContent["$fullPath.$sectionName.fields.$fieldName"] = $fieldTitle
                }
            }
        }
    }
    
    # Füge relations-title hinzu (ganz unten)
    $newContent["$fullPath.relations-title"] = "Verknüpfungen"
    
    # Konvertiere zu JSON mit korrekter Formatierung
    $jsonContent = $newContent | ConvertTo-Json -Depth 10
    
    # Schreibe zurück in Datei
    $jsonContent | Set-Content $FilePath -Encoding UTF8
    
    Write-Host "  Datei korrigiert: $FilePath"
}

# Funktion zum Bestimmen des Vertragstyps
function Get-ContractType {
    param([string]$fileName)
    
    if ($fileName -like "*insurance*") { return "Versicherungsvertrag" }
    if ($fileName -like "*service*") { return "Dienstleistungsvertrag" }
    if ($fileName -like "*employment*") { return "Arbeitsvertrag" }
    if ($fileName -like "*loan*") { return "Kreditvertrag" }
    if ($fileName -like "*savings*") { return "Sparvertrag" }
    if ($fileName -like "*rental*") { return "Mietvertrag" }
    if ($fileName -like "*lease*") { return "Leasingvertrag" }
    if ($fileName -like "*sale*") { return "Kaufvertrag" }
    if ($fileName -like "*energy*") { return "Energievertrag" }
    if ($fileName -like "*media*") { return "Medienvertrag" }
    if ($fileName -like "*fees*") { return "Gebührenvertrag" }
    if ($fileName -like "*tax*") { return "Steuervertrag" }
    if ($fileName -like "*mobility*") { return "Mobilitätsvertrag" }
    if ($fileName -like "*property*") { return "Immobilienvertrag" }
    if ($fileName -like "*purchase*") { return "Kaufvertrag" }
    if ($fileName -like "*creditcard*") { return "Kreditkartenvertrag" }
    if ($fileName -like "*bankaccount*") { return "Bankkontovertrag" }
    if ($fileName -like "*telecommunication*") { return "Telekommunikationsvertrag" }
    
    return "Vertrag"
}

# Funktion zum Bestimmen des Abschnittstitels
function Get-SectionTitle {
    param([string]$sectionName)
    
    switch ($sectionName) {
        "parties" { return "Vertragsparteien" }
        "serviceData" { return "Dienstleistungsdaten" }
        "terms" { return "Bedingungen" }
        "cardData" { return "Kartendaten" }
        "limitData" { return "Kreditlimits" }
        "termsData" { return "Konditionen" }
        "personData" { return "Personendaten" }
        "coverage" { return "Versicherungsschutz" }
        "risks" { return "Versicherte Risiken" }
        "exclusions" { return "Ausschlüsse" }
        "telecomData" { return "Telekommunikationsdaten" }
        "employmentData" { return "Arbeitsdaten" }
        "loanData" { return "Kreditdaten" }
        "savingsData" { return "Spardaten" }
        "rentalData" { return "Mietdaten" }
        "energyData" { return "Energiedaten" }
        "mediaData" { return "Mediendaten" }
        "propertyData" { return "Immobiliendaten" }
        default { return "Abschnitt $sectionName" }
    }
}

# Funktion zum Bestimmen des Feldtitels
function Get-FieldTitle {
    param([string]$fieldName)
    
    # Einfache Übersetzungen für häufige Felder
    $translations = @{
        "name" = "Name"
        "address" = "Adresse"
        "phone" = "Telefon"
        "email" = "E-Mail"
        "website" = "Website"
        "description" = "Beschreibung"
        "type" = "Typ"
        "amount" = "Betrag"
        "date" = "Datum"
        "number" = "Nummer"
        "location" = "Ort"
        "duration" = "Dauer"
        "hours" = "Stunden"
        "method" = "Methode"
        "schedule" = "Plan"
        "reports" = "Berichte"
        "meetings" = "Gespräche"
        "followUp" = "Nachbetreuung"
    }
    
    foreach ($key in $translations.Keys) {
        if ($fieldName -like "*$key*") {
            return $translations[$key]
        }
    }
    
    # Fallback: Erste Buchstabe groß, Rest klein
    return (Get-Culture).TextInfo.ToTitleCase($fieldName.ToLower())
}

# Liste aller contract i18n-Dateien (außer bereits korrigierten)
$contractI18nFiles = Get-ChildItem "src/main/webapp/i18n/de/contract" -Filter "i18n-*.json" | Where-Object { 
    $_.Name -notlike "*base*" -and 
    $_.Name -notlike "*test*" -and
    $_.Name -notlike "*termination*" -and
    $_.Name -notlike "*employment-publicservice-work*" -and
    $_.Name -notlike "*employment-publicservice-parttime*" -and
    $_.Name -notlike "*service-consulting-consultingcontract*" -and
    $_.Name -notlike "*creditcard-credit-creditcardcontract*" -and
    $_.Name -notlike "*telecommunication-telecommunication-telecommunicationcontract*" -and
    $_.Name -notlike "*employment-employee-help*"
}

foreach ($file in $contractI18nFiles) {
    Fix-ContractI18nFile -FilePath $file.FullName
}

Write-Host "Alle contract i18n-Dateien wurden überprüft und korrigiert!" 