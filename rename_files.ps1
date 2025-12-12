# Rename employment form definition files to use lowercase
$contractDir = "src\main\webapp\app\shared\form-definitions\contract"

# Employee files
Rename-Item "$contractDir\contract-employment-employee-fullTime.json" "contract-employment-employee-fulltime.json"
Rename-Item "$contractDir\contract-employment-employee-halfTime.json" "contract-employment-employee-halftime.json"
Rename-Item "$contractDir\contract-employment-employee-miniJob.json" "contract-employment-employee-minijob.json"
Rename-Item "$contractDir\contract-employment-employee-terminationAgreement.json" "contract-employment-employee-terminationagreement.json"
Rename-Item "$contractDir\contract-employment-employee-workContract.json" "contract-employment-employee-workcontract.json"

# Official files
Rename-Item "$contractDir\contract-employment-official-fullTime.json" "contract-employment-official-fulltime.json"
Rename-Item "$contractDir\contract-employment-official-halfTime.json" "contract-employment-official-halftime.json"
Rename-Item "$contractDir\contract-employment-official-miniJob.json" "contract-employment-official-minijob.json"
Rename-Item "$contractDir\contract-employment-official-terminationAgreement.json" "contract-employment-official-terminationagreement.json"
Rename-Item "$contractDir\contract-employment-official-workContract.json" "contract-employment-official-workcontract.json"

# Public service files
Rename-Item "$contractDir\contract-employment-publicService-fullTime.json" "contract-employment-publicservice-fulltime.json"
Rename-Item "$contractDir\contract-employment-publicService-halfTime.json" "contract-employment-publicservice-halftime.json"
Rename-Item "$contractDir\contract-employment-publicService-miniJob.json" "contract-employment-publicservice-minijob.json"
Rename-Item "$contractDir\contract-employment-publicService-terminationAgreement.json" "contract-employment-publicservice-terminationagreement.json"
Rename-Item "$contractDir\contract-employment-publicService-workContract.json" "contract-employment-publicservice-workcontract.json"

Write-Host "✅ Alle employment-Dateien wurden umbenannt!" 