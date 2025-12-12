@echo off
echo ========================================
echo Starting Jayna Development Environment
echo ========================================

echo.
echo 1. Starting PostgreSQL database...
docker-compose -f docker/jayna-postgresql-1/docker-compose.yml up -d

echo.
echo 2. Waiting for database to be ready...
timeout /t 5 /nobreak > nul

echo.
echo 3. Starting Backend...
start "Jayna Backend" cmd /k "mvn spring-boot:run \"-Dspring-boot.run.profiles=dev\""

echo.
echo 4. Waiting for backend to start...
timeout /t 15 /nobreak > nul

echo.
echo 5. Starting Frontend...
start "Jayna Frontend" cmd /k "npm start"

echo.
echo ========================================
echo Development environment started!
echo ========================================
echo Backend: http://localhost:8080
echo Frontend: http://localhost:9000
echo ========================================
echo.
echo Press any key to exit this script...
pause > nul 