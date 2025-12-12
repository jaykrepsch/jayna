Param(
  [int]$MaxAttempts = 5,
  [int]$WaitSeconds = 60
)

Set-StrictMode -Version Latest
$ErrorActionPreference = 'SilentlyContinue'

function Test-Port([int]$Port) {
  return Test-NetConnection -ComputerName 127.0.0.1 -Port $Port -InformationLevel Quiet
}

function Get-HttpStatus([string]$Url) {
  try {
    $resp = Invoke-WebRequest -Uri $Url -UseBasicParsing -TimeoutSec 5
    return [int]$resp.StatusCode
  } catch {
    $er = $_.Exception
    if ($er -and $er.Response) {
      try { return [int]$er.Response.StatusCode.value__ } catch { return 0 }
    }
    return 0
  }
}

function Ensure-DB() {
  if (-not (Test-Port 5432)) {
    Write-Host "[DB] Starte Docker-Postgres..."
    docker-compose -f docker\jayna-postgresql-1\docker-compose.yml up -d | Out-Null
    Start-Sleep -Seconds 5
  } else {
    Write-Host "[DB] Port 5432 ist erreichbar."
  }
}

function Ensure-Backend() {
  if (-not (Test-Port 8080)) {
    Write-Host "[BE] Starte Backend..."
    cmd /c taskkill /F /IM java.exe >NUL 2>&1
    Start-Process cmd.exe -ArgumentList '/k','mvn spring-boot:run "-Dspring-boot.run.profiles=dev"' -WorkingDirectory $PWD
  } else {
    Write-Host "[BE] Port 8080 ist erreichbar."
  }
}

function Ensure-Frontend() {
  if (-not (Test-Port 9000)) {
    Write-Host "[FE] Starte Frontend..."
    cmd /c taskkill /F /IM node.exe >NUL 2>&1
    Start-Process cmd.exe -ArgumentList '/k','npm start' -WorkingDirectory $PWD
  } else {
    Write-Host "[FE] Port 9000 ist erreichbar."
  }
}

Ensure-DB
Ensure-Backend
Ensure-Frontend

for ($i = 1; $i -le $MaxAttempts; $i++) {
  Write-Host "[CHECK] Versuch $i/$MaxAttempts - warte $WaitSeconds s..."
  Start-Sleep -Seconds $WaitSeconds

  $dbUp = Test-Port 5432
  $beUp = Test-Port 8080
  $feUp = Test-Port 9000

  $health = Get-HttpStatus 'http://127.0.0.1:8080/management/health/readiness'
  $accountDirect = Get-HttpStatus 'http://127.0.0.1:8080/api/account'
  $accountProxy = Get-HttpStatus 'http://127.0.0.1:9000/api/account'

  Write-Host "[STATUS] DB:$dbUp BE:$beUp FE:$feUp health:$health accountDirect:$accountDirect accountProxy:$accountProxy"

  if ($dbUp -and $beUp -and $feUp -and ($health -eq 200 -or $health -eq 204) -and ($accountDirect -in 200,204,401) -and ($accountProxy -in 200,204,401)) {
    Write-Host "[OK] Stack ist betriebsbereit."
    exit 0
  }

  if (-not $dbUp) { Ensure-DB }
  if (-not $beUp -or $health -eq 500) { Ensure-Backend }
  if (-not $feUp) { Ensure-Frontend }
}

Write-Host "[FAIL] Stack nicht stabil. Bitte Logs prüfen (backend.log, Browser-Konsole)."
exit 1


