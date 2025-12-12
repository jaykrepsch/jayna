# PowerShell-Skript zum Korrigieren aller contract i18n-Dateien nach dem korrekten Schema

# Funktion zum Korrigieren einer i18n-Datei
function Fix-I18nFile {
    param(
        [string]$FilePath,
        [string]$FormDefinitionPath
    )
    
    Write-Host "Überprüfe: $FilePath"
    
    # Prüfe ob Form-Definition existiert
    if (-not (Test-Path $FormDefinitionPath)) {
        Write-Host "  Form-Definition nicht gefunden: $FormDefinitionPath"
        return
    }
    
    # Lese aktuelle i18n-Datei
    $currentContent = Get-Content $FilePath -Raw | ConvertFrom-Json
    
    # Extrahiere den Basis-Pfad aus dem Dateinamen
    $fileName = [System.IO.Path]::GetFileNameWithoutExtension($FilePath)
    $basePath = "jaynaApp.$fileName"
    
    # Erstelle neue Struktur
    $newContent = @{}
    
    # Füge createTitle, updateTitle, viewTitle hinzu
    $newContent["$basePath.createTitle"] = "Telekommunikationsvertrag erstellen"
    $newContent["$basePath.updateTitle"] = "Telekommunikationsvertrag bearbeiten"
    $newContent["$basePath.viewTitle"] = "Telekommunikationsvertrag anzeigen"
    
    # Lese Form-Definition um spezifische Abschnitte zu finden
    $formDef = Get-Content $FormDefinitionPath -Raw | ConvertFrom-Json
    
    # Finde spezifische Abschnitte (mit i18nSource)
    foreach ($part in $formDef.parts) {
        if ($part.i18nSource -and $part.title -like "*$basePath*") {
            $sectionName = $part.title -replace "jaynaApp\.$fileName\.", ""
            $sectionName = $sectionName -replace "\.title$", ""
            
            # Füge Abschnittstitel hinzu
            $newContent["$basePath.$sectionName.title"] = "Abschnitt $sectionName"
            
            # Füge Felder hinzu
            foreach ($field in $part.fields) {
                $fieldName = $field.name
                $newContent["$basePath.$sectionName.fields.$fieldName"] = $fieldName
            }
        }
    }
    
    # Füge relations-title hinzu
    $newContent["$basePath.relations-title"] = "Verknüpfungen"
    
    # Konvertiere zu JSON mit korrekter Formatierung
    $jsonContent = $newContent | ConvertTo-Json -Depth 10
    
    # Schreibe zurück in Datei
    $jsonContent | Set-Content $FilePath -Encoding UTF8
    
    Write-Host "  Datei korrigiert: $FilePath"
}

# Liste aller contract i18n-Dateien
$contractI18nFiles = Get-ChildItem "src/main/webapp/i18n/de/contract" -Filter "i18n-*.json" | Where-Object { $_.Name -notlike "*base*" }

foreach ($file in $contractI18nFiles) {
    $i18nPath = $file.FullName
    $fileName = $file.BaseName
    
    # Erstelle Form-Definition Pfad
    $formDefPath = "src/main/webapp/app/shared/form-definitions/contract/$fileName.json"
    
    Fix-I18nFile -FilePath $i18nPath -FormDefinitionPath $formDefPath
}

Write-Host "Alle contract i18n-Dateien wurden überprüft und korrigiert!" 