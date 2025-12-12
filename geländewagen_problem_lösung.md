# Geländewagen Problem - Lösung

## Problem
Der Titel wurde als "Geländewagen *Car*" angezeigt, obwohl es "Geländewagen *mobility-passengervehicle-car-offroadvehicle*" sein sollte.

## Ursache
Der `formName` in der Datenbank war falsch:
- **Aktuell**: `form_name = 'car'`
- **Sollte sein**: `form_name = 'mobility-passengervehicle-car-offroadvehicle'`

## Lösung

### 1. Datenbank-Korrektur
**SQL-Skript**: `fix_geländewagen_formname_final.sql`
```sql
UPDATE group_type 
SET form_name = 'mobility-passengervehicle-car-offroadvehicle' 
WHERE name = 'Geländewagen' AND entity_name = 'mobility';
```

### 2. Liquibase-Changelog
**Datei**: `src/main/resources/config/liquibase/changelog/20250715000003_fix_geländewagen_formname_final.xml`

Dieser Changelog wird automatisch beim nächsten Start der Anwendung ausgeführt.

### 3. Code-Korrekturen bereits vorhanden
- ✅ `view.vue`: `getGroupTypeKeyFromFormName` korrigiert
- ✅ `baseMobility.json`: GroupType-Übersetzungen korrigiert

## Ergebnis
Nach der Ausführung sollte der Titel korrekt angezeigt werden:
- **Vorher**: `Geländewagen *Car*`
- **Nachher**: `Geländewagen *mobility-passengervehicle-car-offroadvehicle*`

## Ausführung
1. **Manuell**: SQL-Skript in der Datenbank ausführen
2. **Automatisch**: Anwendung neu starten (Liquibase-Changelog wird ausgeführt)

## Verifikation
```sql
SELECT id, name, form_name, entity_name 
FROM group_type 
WHERE name = 'Geländewagen' AND entity_name = 'mobility';
```

**Erwartetes Ergebnis**:
- `name`: 'Geländewagen'
- `form_name`: 'mobility-passengervehicle-car-offroadvehicle' 