# Datenbank Setup - Wichtige Hinweise

## Problem mit Liquibase Changelog-Reihenfolge

### Problem
Wenn die Datenbank neu aufgesetzt wird, kann es zu Fehlern kommen, weil Liquibase-Changelogs in der falschen Reihenfolge ausgeführt werden.

### Spezifisches Problem: Document Entity
Das `20250105_added_entity_Document.xml` Changelog (das die `document` Tabelle erstellt) wurde nach dem `20241201000000_added_document_relations.xml` Changelog (das Foreign Key Constraints erstellt) ausgeführt.

**Fehler:** `ERROR: relation "public.document" does not exist`

### Lösung
1. **Changelog-Dateiname ändern:** `20250105_added_entity_Document.xml` → `20241130_added_entity_Document.xml`
2. **ChangeSet-IDs ändern:** Alle IDs von `20250105-X` zu `20241130-X` ändern

### Automatische Ausführung
Liquibase führt Changelogs alphabetisch nach Dateinamen aus. Daher muss die `document` Tabelle vor den `document_relations` erstellt werden.

### Schritte für zukünftige Datenbankeinrichtung
1. Datenbank zurücksetzen: `docker-compose -f src/main/docker/postgresql.yml down -v`
2. Datenbank neu starten: `docker-compose -f src/main/docker/postgresql.yml up -d`
3. Backend starten: `mvn spring-boot:run "-Dspring-boot.run.profiles=dev"`

### Wichtige Dateien
- `src/main/resources/config/liquibase/changelog/20241130_added_entity_Document.xml` - Document Tabelle
- `src/main/resources/config/liquibase/changelog/20241201000000_added_document_relations.xml` - Document Relations

### Schema-Setup
Falls Liquibase Probleme mit dem Schema hat:
```sql
CREATE SCHEMA IF NOT EXISTS public;
SET search_path TO public;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO public;
ALTER DATABASE jayna SET search_path TO public;
```
