@echo off
REM Batch-Skript zum Löschen aller Datensätze
REM Voraussetzung: cURL muss installiert sein

set BASE_URL=http://localhost:8080/api

echo Starte Löschvorgang für alle Datensätze...

REM Liste aller Entitäten
set ENTITIES=contracts contacts finance-accounts mobilities real-estates

for %%e in (%ENTITIES%) do (
    echo Lösche alle Datensätze für Entität: %%e
    
    REM 1. Hole alle Datensätze für diese Entität
    curl -s -X GET "%BASE_URL%/%%e?size=1000&sort=id,desc" -H "Accept: application/json" > temp_response.json
    
    if %ERRORLEVEL% EQU 0 (
        REM Extrahiere IDs aus der JSON-Antwort (vereinfacht)
        findstr /C:"\"id\":" temp_response.json > temp_ids.txt
        
        REM Zähle die gefundenen IDs
        set /a count=0
        for /f %%i in (temp_ids.txt) do set /a count+=1
        
        echo Gefunden %count% Datensätze für %%e
        
        REM 2. Lösche jeden Datensatz (vereinfacht - hier würden wir die IDs parsen)
        REM In der Praxis würden wir die IDs aus der JSON-Antwort extrahieren
        echo Hinweis: Für eine vollständige Implementierung wäre ein JSON-Parser erforderlich
        echo Die IDs müssen manuell aus temp_response.json extrahiert werden
        
    ) else (
        echo Fehler beim Abrufen der Datensätze für %%e
    )
    
    REM Aufräumen
    if exist temp_response.json del temp_response.json
    if exist temp_ids.txt del temp_ids.txt
)

echo Löschvorgang abgeschlossen!
pause 