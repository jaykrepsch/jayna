# PowerShell-Skript zum Löschen aller Datensätze
# Voraussetzung: cURL muss installiert sein

$baseUrl = "http://localhost:8080/api"

# Liste aller Entitäten
$entities = @(
    "contracts",
    "contacts", 
    "finance-accounts",
    "mobilities",
    "real-estates"
)

Write-Host "Starte Löschvorgang für alle Datensätze..." -ForegroundColor Green

foreach ($entity in $entities) {
    Write-Host "Lösche alle Datensätze für Entität: $entity" -ForegroundColor Yellow
    
    try {
        # 1. Hole alle Datensätze für diese Entität
        $getUrl = "$baseUrl/$entity?size=1000&sort=id,desc"
        $response = curl -s -X GET $getUrl -H "Accept: application/json"
        
        if ($LASTEXITCODE -eq 0) {
            # Extrahiere IDs aus der JSON-Antwort
            $ids = $response | Select-String -Pattern '"id":\s*(\d+)' | ForEach-Object { $_.Matches.Groups[1].Value }
            
            Write-Host "Gefunden $($ids.Count) Datensätze für $entity" -ForegroundColor Cyan
            
            # 2. Lösche jeden Datensatz
            foreach ($id in $ids) {
                $deleteUrl = "$baseUrl/$entity/$id"
                $deleteResponse = curl -s -X DELETE $deleteUrl
                
                if ($LASTEXITCODE -eq 0) {
                    Write-Host "Datensatz $id für $entity erfolgreich gelöscht" -ForegroundColor Green
                } else {
                    Write-Host "Fehler beim Löschen von Datensatz $id für $entity" -ForegroundColor Red
                }
            }
            
            Write-Host "Alle $($ids.Count) Datensätze für $entity wurden gelöscht." -ForegroundColor Green
            
        } else {
            Write-Host "Fehler beim Abrufen der Datensätze für $entity" -ForegroundColor Red
        }
        
    } catch {
        Write-Host "Fehler beim Löschen der Datensätze für $entity`: $($_.Exception.Message)" -ForegroundColor Red
    }
}

Write-Host "Löschvorgang abgeschlossen!" -ForegroundColor Green 