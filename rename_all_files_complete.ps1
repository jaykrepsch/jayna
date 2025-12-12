# Complete rename of all form definition files to lowercase
$baseDir = "src\main\webapp\app\shared\form-definitions"

Write-Host "Starting complete rename of all form definition files..."

# Get all JSON files recursively
$allFiles = Get-ChildItem -Path $baseDir -Recurse -Filter "*.json"

$renamedCount = 0

foreach ($file in $allFiles) {
    $oldName = $file.Name
    $newName = $oldName.ToLower()
    
    if ($oldName -ne $newName) {
        $oldPath = $file.FullName
        $newPath = Join-Path $file.DirectoryName $newName
        
        Write-Host "Renaming: $oldName -> $newName"
        Rename-Item -Path $oldPath -NewName $newName -Force
        $renamedCount++
    }
}

Write-Host "✅ Umbenennung abgeschlossen! $renamedCount Dateien wurden umbenannt." 