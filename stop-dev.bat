@echo off
echo ========================================
echo Stopping Jayna Development Environment
echo ========================================

echo.
echo 1. Stopping Java processes (Backend)...
taskkill /f /im java.exe 2>nul
if %errorlevel% equ 0 (
    echo Backend stopped successfully.
) else (
    echo No Java processes found.
)

echo.
echo 2. Stopping Node processes (Frontend)...
taskkill /f /im node.exe 2>nul
if %errorlevel% equ 0 (
    echo Frontend stopped successfully.
) else (
    echo No Node processes found.
)

echo.
echo 3. Stopping PostgreSQL database...
docker-compose -f docker/jayna-postgresql-1/docker-compose.yml down

echo.
echo ========================================
echo All services stopped!
echo ========================================
echo.
echo Press any key to exit...
pause > nul 