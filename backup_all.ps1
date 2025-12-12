$ErrorActionPreference = "Stop"

# Zeitstempel und Pfade
$ts = Get-Date -Format "yyyy-MM-dd_HH-mm-ss"
$projectRoot = (Resolve-Path $PSScriptRoot).Path
$projectName = Split-Path $projectRoot -Leaf
$home = [Environment]::GetFolderPath("UserProfile")
$backupRoot = Join-Path $home ("Backups\" + $projectName)
$dest = Join-Path $backupRoot $ts
New-Item -ItemType Directory -Force -Path $dest | Out-Null

Write-Host "Backup-Ziel: $dest"

# DB-Parameter (via ENV überschreibbar)
$dbHost = $env:JAYNA_DB_HOST
if ([string]::IsNullOrWhiteSpace($dbHost)) { $dbHost = "localhost" }
$dbName = $env:JAYNA_DB_NAME
if ([string]::IsNullOrWhiteSpace($dbName)) { $dbName = "jayna" }
$dbUser = $env:JAYNA_DB_USER
if ([string]::IsNullOrWhiteSpace($dbUser)) { $dbUser = "jayna" }
$dbPassword = $env:JAYNA_DB_PASSWORD
if ([string]::IsNullOrWhiteSpace($dbPassword)) { $dbPassword = "geheim" }

# Uploads-Pfad (via ENV überschreibbar)
$uploadsPath = $env:JAYNA_UPLOADS_PATH
if ([string]::IsNullOrWhiteSpace($uploadsPath)) { $uploadsPath = "C:\\Users\\jaykr\\volumes\\jayna\\documents" }

function Backup-Database {
  try {
    $dumpFile = Join-Path $dest ("db_" + $ts + ".dump")

    $docker = Get-Command docker -ErrorAction SilentlyContinue
    $pgContainer = $null
    if ($docker) {
      $containers = docker ps --format "{{.Names}} {{.Image}}"
      foreach ($c in $containers) {
        if ($c -match "postgres") { $pgContainer = $c.Split()[0]; break }
      }
    }

    if ($pgContainer) {
      Write-Host "PostgreSQL in Docker erkannt: $pgContainer"
      docker exec $pgContainer sh -c "PGPASSWORD='$dbPassword' pg_dump -U $dbUser -d $dbName -Fc -f /tmp/db_$ts.dump"
      docker cp "$pgContainer:/tmp/db_$ts.dump" "$dumpFile"
      docker exec $pgContainer rm "/tmp/db_$ts.dump" | Out-Null
    } else {
      Write-Host "Versuche lokalen pg_dump..."
      $env:PGPASSWORD = $dbPassword
      & pg_dump -h $dbHost -U $dbUser -d $dbName -Fc -f $dumpFile
    }
    Write-Host "DB-Dump erstellt: $dumpFile"
  } catch {
    Write-Warning "DB-Backup fehlgeschlagen: $_"
  }
}

function Backup-Uploads {
  try {
    if (Test-Path $uploadsPath) {
      $zip = Join-Path $dest ("uploads_" + $ts + ".zip")
      Compress-Archive -Path (Join-Path $uploadsPath "*") -DestinationPath $zip -Force
      Write-Host "Uploads gesichert: $zip"
    } else {
      Write-Host "Uploads-Pfad nicht gefunden: $uploadsPath (übersprungen)"
    }

    $docker = Get-Command docker -ErrorAction SilentlyContinue
    if ($docker) {
      docker volume inspect file_storage 2>$null | Out-Null
      if ($LASTEXITCODE -eq 0) {
        Write-Host "Docker-Volume 'file_storage' gefunden, sichere..."
        docker run --rm -v file_storage:/data -v "$dest:/backup" alpine sh -c "cd /data && tar czf /backup/file_storage_$ts.tgz ."
        Write-Host ("Docker-Uploads gesichert: " + (Join-Path $dest ("file_storage_" + $ts + ".tgz")))
      }
    }
  } catch {
    Write-Warning "Uploads-Backup fehlgeschlagen: $_"
  }
}

function Backup-ProjectFolder {
  try {
    $zip = Join-Path $dest ($projectName + "_full_" + $ts + ".zip")
    Write-Host "Komprimiere Projektordner $projectRoot..."
    Compress-Archive -Path $projectRoot -DestinationPath $zip -Force
    Write-Host "Projektordner gesichert: $zip"
  } catch {
    Write-Warning "Projektordner-Backup fehlgeschlagen: $_"
  }
}

Backup-Database
Backup-Uploads
Backup-ProjectFolder

Write-Host "Fertig. Alle Backups unter: $dest"


