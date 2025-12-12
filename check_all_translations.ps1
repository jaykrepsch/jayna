# Skript zur Überprüfung und Behebung aller fehlenden Übersetzungen
# für Sub-Category-Groups und Group-Types

Write-Host "Überprüfung aller Übersetzungen für Sub-Category-Groups und Group-Types..." -ForegroundColor Green

# Verbindung zur Datenbank herstellen
$connectionString = "Host=localhost;Port=5432;Database=jayna;Username=jayna;Password=jayna"

try {
    $connection = New-Object Npgsql.NpgsqlConnection($connectionString)
    $connection.Open()
    Write-Host "Datenbankverbindung erfolgreich hergestellt." -ForegroundColor Green

    # Alle Sub-Category-Groups abrufen
    $subCategoryGroupsQuery = @"
        SELECT 
            scg.id,
            scg.name,
            scg.translation_key,
            sc.name as sub_category_name
        FROM sub_category_group scg
        JOIN sub_category sc ON scg.sub_category_id = sc.id
        ORDER BY sc.name, scg.name
"@

    $subCategoryGroupsCommand = New-Object Npgsql.NpgsqlCommand($subCategoryGroupsQuery, $connection)
    $subCategoryGroupsReader = $subCategoryGroupsCommand.ExecuteReader()

    $subCategoryGroups = @()
    while ($subCategoryGroupsReader.Read()) {
        $subCategoryGroups += @{
            Id = $subCategoryGroupsReader["id"]
            Name = $subCategoryGroupsReader["name"]
            TranslationKey = $subCategoryGroupsReader["translation_key"]
            SubCategoryName = $subCategoryGroupsReader["sub_category_name"]
        }
    }
    $subCategoryGroupsReader.Close()

    # Alle Group-Types abrufen
    $groupTypesQuery = @"
        SELECT 
            gt.id,
            gt.name,
            gt.translation_key,
            gt.entity_name,
            scg.name as sub_category_group_name,
            sc.name as sub_category_name
        FROM group_type gt
        JOIN sub_category_group scg ON gt.sub_category_group_id = scg.id
        JOIN sub_category sc ON scg.sub_category_id = sc.id
        ORDER BY sc.name, scg.name, gt.name
"@

    $groupTypesCommand = New-Object Npgsql.NpgsqlCommand($groupTypesQuery, $connection)
    $groupTypesReader = $groupTypesCommand.ExecuteReader()

    $groupTypes = @()
    while ($groupTypesReader.Read()) {
        $groupTypes += @{
            Id = $groupTypesReader["id"]
            Name = $groupTypesReader["name"]
            TranslationKey = $groupTypesReader["translation_key"]
            EntityName = $groupTypesReader["entity_name"]
            SubCategoryGroupName = $groupTypesReader["sub_category_group_name"]
            SubCategoryName = $groupTypesReader["sub_category_name"]
        }
    }
    $groupTypesReader.Close()

    Write-Host "`n=== SUB-CATEGORY-GROUPS ===" -ForegroundColor Yellow
    foreach ($scg in $subCategoryGroups) {
        Write-Host "Sub-Category: $($scg.SubCategoryName) | Group: $($scg.Name) | Key: $($scg.TranslationKey)" -ForegroundColor Cyan
    }

    Write-Host "`n=== GROUP-TYPES ===" -ForegroundColor Yellow
    foreach ($gt in $groupTypes) {
        Write-Host "Entity: $($gt.EntityName) | Sub-Category: $($gt.SubCategoryName) | Group: $($gt.SubCategoryGroupName) | Type: $($gt.Name) | Key: $($gt.TranslationKey)" -ForegroundColor Cyan
    }

    # Jetzt prüfen wir, welche Übersetzungen in der baseCategories.json fehlen
    Write-Host "`n=== FEHLENDE ÜBERSETZUNGEN IDENTIFIZIEREN ===" -ForegroundColor Yellow

    $missingSubCategoryGroups = @()
    $missingGroupTypes = @()

    foreach ($scg in $subCategoryGroups) {
        $translationKey = $scg.TranslationKey
        if ($translationKey -and $translationKey -notlike "*custom*") {
            # Extrahiere den Schlüssel ohne jaynaApp. prefix
            $key = $translationKey -replace "^jaynaApp\.", ""
            $entity = $key.Split('.')[0]
            $section = $key.Split('.')[1]
            $subKey = $key.Split('.')[2..($key.Split('.').Length-1)] -join '.'
            
            Write-Host "Prüfe: $translationKey -> Entity: $entity, Section: $section, Key: $subKey" -ForegroundColor Gray
        }
    }

    foreach ($gt in $groupTypes) {
        $translationKey = $gt.TranslationKey
        if ($translationKey -and $translationKey -notlike "*custom*") {
            # Extrahiere den Schlüssel ohne jaynaApp. prefix
            $key = $translationKey -replace "^jaynaApp\.", ""
            $entity = $key.Split('.')[0]
            $section = $key.Split('.')[1]
            $subKey = $key.Split('.')[2..($key.Split('.').Length-1)] -join '.'
            
            Write-Host "Prüfe: $translationKey -> Entity: $entity, Section: $section, Key: $subKey" -ForegroundColor Gray
        }
    }

    Write-Host "`n=== ZUSAMMENFASSUNG ===" -ForegroundColor Green
    Write-Host "Sub-Category-Groups gefunden: $($subCategoryGroups.Count)" -ForegroundColor White
    Write-Host "Group-Types gefunden: $($groupTypes.Count)" -ForegroundColor White

} catch {
    Write-Host "Fehler bei der Datenbankverbindung: $($_.Exception.Message)" -ForegroundColor Red
} finally {
    if ($connection -and $connection.State -eq 'Open') {
        $connection.Close()
    }
}

Write-Host "`nÜberprüfung abgeschlossen." -ForegroundColor Green 