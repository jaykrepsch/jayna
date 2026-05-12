@echo off
echo ========================================
echo   Jayna - Development Environment
echo ========================================

echo.
echo [1/5] Docker Desktop starten...
start "" "C:\Program Files\Docker\Docker\Docker Desktop.exe"

echo [1/5] Warte bis Docker bereit ist (30 Sekunden)...
timeout /t 30 /nobreak > nul

echo.
echo [2/5] PostgreSQL Datenbank starten...
docker-compose -f docker/jayna-postgresql-1/docker-compose.yml up -d
if errorlevel 1 (
  echo FEHLER: Docker konnte nicht gestartet werden!
  echo Bitte Docker Desktop manuell starten und nochmal versuchen.
  pause
  exit /b 1
)

echo.
echo [3/5] Warte bis Datenbank bereit ist (10 Sekunden)...
timeout /t 10 /nobreak > nul

echo.
echo [4/5] Backend starten (Spring Boot auf Port 8080)...
start "Jayna Backend" cmd /k "mvnw.cmd"

echo.
echo [5/5] Warte auf Backend (20 Sekunden), dann Frontend starten...
timeout /t 20 /nobreak > nul

echo.
echo [5/5] Frontend starten (Port 9000)...
start "Jayna Frontend" cmd /k "npm install && npm start"

echo.
echo ========================================
echo   Alles gestartet!
echo ========================================
echo   Datenbank : localhost:5432
echo   Backend   : http://localhost:8080
echo   Website   : http://localhost:9000
echo ========================================
echo.
echo Warte kurz, dann oeffnet sich die Website...
timeout /t 15 /nobreak > nul
start http://localhost:9000