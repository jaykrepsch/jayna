@echo off
set DATUM=%DATE:~6,4%-%DATE:~3,2%-%DATE:~0,2%_%TIME:~0,2%-%TIME:~3,2%
set DATUM=%DATUM: =0%
if not exist backup_datenbank mkdir backup_datenbank
docker exec -t docker-jayna-postgresql-1 pg_dump -U user jayna > backup_datenbank\backup_%DATUM%.sql 