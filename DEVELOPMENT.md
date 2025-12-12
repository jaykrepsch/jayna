# Jayna Development Guide

## Schnellstart

### Einfachste Methode (Empfohlen)
```bash
# Alles starten
start-dev.bat

# Alles stoppen
stop-dev.bat
```

## Manuelle Befehle

### 1. Datenbank starten
```bash
docker-compose -f docker/jayna-postgresql-1/docker-compose.yml up -d
```

### 2. Backend starten
```bash
mvn spring-boot:run "-Dspring-boot.run.profiles=dev"
```
**Wichtig:** Die Anführungszeichen um den Parameter sind entscheidend!

### 3. Frontend starten
```bash
npm start
```

## Wichtige Informationen

### Container-Name
- **Datenbank-Container:** `jayna-postgresql-1` (nicht `db-1`!)
- **Datenbank-Name:** `jayna`
- **Benutzer:** `jayna`
- **Passwort:** `geheim`

### Ports
- **Backend:** http://localhost:8080
- **Frontend:** http://localhost:9000
- **Browsersync:** http://localhost:9001

### Bei Liquibase-Fehlern
Falls der Fehler "duplicate key value violates unique constraint" auftritt:
```bash
docker exec jayna-postgresql-1 psql -U jayna -d jayna -c "DELETE FROM databasechangelog WHERE id='20250117000001-1' AND author='jayna';"
```

## Troubleshooting

### Backend startet nicht
1. Prüfe ob Port 8080 frei ist
2. Verwende die Anführungszeichen: `mvn spring-boot:run "-Dspring-boot.run.profiles=dev"`
3. Prüfe ob die Datenbank läuft

### Frontend kann Backend nicht erreichen
1. Prüfe ob Backend auf Port 8080 läuft
2. Prüfe die Proxy-Konfiguration in webpack

### Datenbank-Probleme
1. Container stoppen: `docker-compose -f docker/jayna-postgresql-1/docker-compose.yml down`
2. Container neu starten: `docker-compose -f docker/jayna-postgresql-1/docker-compose.yml up -d` 