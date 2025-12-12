$Log = "monitor.log"
Add-Content -Path $Log -Value ("--- Monitor start " + (Get-Date).ToString("o"))
while ($true) {
  $b = "ERR"
  try {
    $r = Invoke-WebRequest -UseBasicParsing -TimeoutSec 5 'http://localhost:8080/management/health'
    if ($r.StatusCode) { $b = $r.StatusCode }
  } catch {}

  $f = "ERR"
  try {
    $r = Invoke-WebRequest -UseBasicParsing -TimeoutSec 5 'http://localhost:9001/'
    if ($r.StatusCode) { $f = $r.StatusCode }
  } catch {}

  $p = "ERR"
  try {
    $r = Invoke-WebRequest -UseBasicParsing -TimeoutSec 5 'http://localhost:9060/api/account'
    if ($r.StatusCode) { $p = $r.StatusCode }
  } catch {}

  Add-Content -Path $Log -Value ((Get-Date).ToString("o") + " | 8080:" + $b + " | 9001:" + $f + " | 9060:" + $p)
  Start-Sleep -Seconds 30
}


