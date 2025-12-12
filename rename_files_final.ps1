# Final rename script for all files with uppercase letters
$baseDir = "src\main\webapp\app\shared\form-definitions"

Write-Host "Starting final rename of all files with uppercase letters..."

# Get all JSON files with uppercase letters
$filesWithUppercase = Get-ChildItem -Path $baseDir -Recurse -Filter "*.json" | Where-Object { $_.Name -cmatch "[A-Z]" }

$renamedCount = 0

foreach ($file in $filesWithUppercase) {
    $oldName = $file.Name
    $newName = $oldName.ToLower()
    
    Write-Host "Renaming: $oldName -> $newName"
    
    try {
        Rename-Item -Path $file.FullName -NewName $newName -Force
        $renamedCount++
    }
    catch {
        Write-Host "Error renaming $oldName : $_"
    }
}

Write-Host "✅ Finale Umbenennung abgeschlossen! $renamedCount Dateien wurden umbenannt." 