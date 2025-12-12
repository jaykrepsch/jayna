# Rename all form definition files to use lowercase (including path parts)
$baseDir = "src\main\webapp\app\shared\form-definitions"

# Function to rename files in a directory
function Rename-FilesInDirectory {
    param($directory)
    
    Write-Host "Processing directory: $directory"
    
    # Get all JSON files in the directory
    $files = Get-ChildItem -Path $directory -Filter "*.json"
    
    foreach ($file in $files) {
        $oldName = $file.Name
        $newName = $oldName.ToLower()
        
        if ($oldName -ne $newName) {
            $oldPath = Join-Path $directory $oldName
            $newPath = Join-Path $directory $newName
            
            Write-Host "Renaming: $oldName -> $newName"
            Rename-Item -Path $oldPath -NewName $newName -Force
        }
    }
}

# Process all entity directories
$entities = @("contract", "mobility", "contact", "realestate", "financeaccount")

foreach ($entity in $entities) {
    $entityDir = Join-Path $baseDir $entity
    if (Test-Path $entityDir) {
        Rename-FilesInDirectory -directory $entityDir
    }
}

Write-Host "✅ Alle Form-Definitionen wurden umbenannt!" 