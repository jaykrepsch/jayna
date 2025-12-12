# PowerShell-Skript zur Korrektur aller contract i18n-Dateien
$contractDir = "src/main/webapp/i18n/de/contract"

# Alle i18n-Dateien im contract-Verzeichnis finden
$i18nFiles = Get-ChildItem -Path $contractDir -Filter "i18n-*.json" | Sort-Object Name

Write-Host "Gefundene i18n-Dateien: $($i18nFiles.Count)" -ForegroundColor Green

foreach ($file in $i18nFiles) {
    Write-Host "`nÜberprüfe: $($file.Name)" -ForegroundColor Yellow
    
    $content = Get-Content $file.FullName -Raw | ConvertFrom-Json
    
    # Prüfe ob die Datei die richtige Struktur hat
    $hasCreateTitle = $content.PSObject.Properties.Name -contains ($content.PSObject.Properties.Name | Where-Object { $_ -like "*createTitle" })
    $hasUpdateTitle = $content.PSObject.Properties.Name -contains ($content.PSObject.Properties.Name | Where-Object { $_ -like "*updateTitle" })
    $hasViewTitle = $content.PSObject.Properties.Name -contains ($content.PSObject.Properties.Name | Where-Object { $_ -like "*viewTitle" })
    $hasRelationsTitle = $content.PSObject.Properties.Name -contains ($content.PSObject.Properties.Name | Where-Object { $_ -like "*relations-title" })
    
    # Prüfe auf englische/generische Übersetzungen
    $englishValues = $content.PSObject.Properties | Where-Object { 
        $_.Value -match "^[A-Z][a-z]+$" -or 
        $_.Value -match "^[A-Za-z]+$" -or
        $_.Value -match "^[A-Z][a-z]+[A-Z][a-z]+$" -or
        $_.Value -match "^[A-Z][a-z]+[A-Z][a-z]+[A-Z][a-z]+$"
    }
    
    if (-not $hasCreateTitle -or -not $hasUpdateTitle -or -not $hasViewTitle -or -not $hasRelationsTitle) {
        Write-Host "  ❌ Fehlende Titel-Einträge" -ForegroundColor Red
    }
    
    if ($englishValues.Count -gt 0) {
        Write-Host "  ⚠️  $($englishValues.Count) englische/generische Übersetzungen gefunden" -ForegroundColor Yellow
        foreach ($eng in $englishValues) {
            Write-Host "    - $($eng.Name): '$($eng.Value)'" -ForegroundColor Gray
        }
    } else {
        Write-Host "  ✅ Alle Übersetzungen scheinen korrekt" -ForegroundColor Green
    }
}

Write-Host "`nÜberprüfung abgeschlossen!" -ForegroundColor Green 