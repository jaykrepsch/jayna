# Vollständige Analyse aller Übersetzungen für Categories, Sub-Categories, Groups und Types
# Basierend auf der dropdown-test-report.json

Write-Host "=== VOLLSTÄNDIGE ÜBERSETZUNGSANALYSE ===" -ForegroundColor Green

# Contract Entity - Sub-Category-Groups
Write-Host "`n=== CONTRACT SUB-CATEGORY-GROUPS ===" -ForegroundColor Yellow
$contractSubCategoryGroups = @(
    "employee", "public-service", "official", "realestate", "service", "maintenance", 
    "electric", "photovoltaic", "heating", "car", "devices", "objects", "television", 
    "internet", "cellular", "streaming", "put-down-space", "reside", "health", "life", 
    "object", "business", "support", "fees", "credit-card", "debit-card"
)

# Contract Entity - Group-Types (Arbeitsvertrag)
Write-Host "`n=== CONTRACT GROUP-TYPES (ARBEITSVERTRAG) ===" -ForegroundColor Yellow
$contractEmploymentTypes = @(
    "mini-job", "half-time", "full-time", "help", "internship", "termination-agreement", "work-contract"
)

# Contract Entity - Group-Types (Darlehen)
Write-Host "`n=== CONTRACT GROUP-TYPES (DARLEHEN) ===" -ForegroundColor Yellow
$contractLoanTypes = @(
    "annuity-loan", "modernization", "amortization-loan", "term-loan", "participatory-loans",
    "call-loan", "rolling-market-loans", "bullet-loans", "student-loans"
)

# Contract Entity - Group-Types (Dienstleistung)
Write-Host "`n=== CONTRACT GROUP-TYPES (DIENSTLEISTUNG) ===" -ForegroundColor Yellow
$contractServiceTypes = @(
    "car-inspection", "notary", "property-management", "rental-management", "heating", 
    "heat-pump", "chimney-sweeper", "elevator", "municipal-fees", "real-estate-tax"
)

# Contract Entity - Group-Types (Energie)
Write-Host "`n=== CONTRACT GROUP-TYPES (ENERGIE) ===" -ForegroundColor Yellow
$contractEnergyTypes = @(
    "electricity", "gas", "district-heating", "oil"
)

# Contract Entity - Group-Types (Kaufvertrag)
Write-Host "`n=== CONTRACT GROUP-TYPES (KAUFVERTRAG) ===" -ForegroundColor Yellow
$contractSaleTypes = @(
    "car", "motorcycle", "mobile", "tv", "coffee-table", "table", "apartment", "house", "real-estate-tax"
)

# Contract Entity - Group-Types (Kredit)
Write-Host "`n=== CONTRACT GROUP-TYPES (KREDIT) ===" -ForegroundColor Yellow
$contractCreditTypes = @(
    "car", "caravan", "boat", "rescheduling", "modernization", "free-use"
)

# Contract Entity - Group-Types (Leasing)
Write-Host "`n=== CONTRACT GROUP-TYPES (LEASING) ===" -ForegroundColor Yellow
$contractLeaseTypes = @(
    "car"
)

# Contract Entity - Group-Types (Medien)
Write-Host "`n=== CONTRACT GROUP-TYPES (MEDIEN) ===" -ForegroundColor Yellow
$contractMediaTypes = @(
    "cable-tv", "satellite-tv", "broadband", "glass-fiber", "car-phone", "mobile", 
    "tablet", "movies-series", "music"
)

# Contract Entity - Group-Types (Mietvertrag)
Write-Host "`n=== CONTRACT GROUP-TYPES (MIETVERTRAG) ===" -ForegroundColor Yellow
$contractRentalTypes = @(
    "garage", "parking", "holiday-apartment", "holiday-house", "room", "building", 
    "car", "camper", "apartment-rental-agreement", "house-lease"
)

# Contract Entity - Group-Types (Versicherung)
Write-Host "`n=== CONTRACT GROUP-TYPES (VERSICHERUNG) ===" -ForegroundColor Yellow
$contractInsuranceTypes = @(
    "statutory-health-insurance", "abroad", "additional-insurance-teeth", "additional-insurance-stationary",
    "additional-insurance-outpatient", "additional-insurance-full", "sick-pay", "hospital-per-diem",
    "foreign-travel", "risk-life-insurance", "pension-insurance-fund", "disability-insurance",
    "care-insurance", "pension-insurance", "daily-care-allowance", "accident", "household-goods",
    "residential-building", "legal-protection", "personal-liability", "builders-liability",
    "ebike", "fire", "loss-of-rent", "photovoltaic", "animal-surgery", "animal-owner-liability",
    "environmental-damage", "building", "manager-liability"
)

# Real Estate Entity - Group-Types
Write-Host "`n=== REAL ESTATE GROUP-TYPES ===" -ForegroundColor Yellow
$realEstateTypes = @(
    "commercial-unit", "commercial-building", "hall", "semit-detached-house", "detached-house",
    "vacation-house", "apartment-building", "care-facility", "terraced-end-house", "terraced-mid-house",
    "two-family-house", "apartments-commercial-mix", "carport", "garage", "parking-house", "parking-space",
    "farmland", "forest", "fruit", "pasture", "allotment-garden", "building-plot", "meadow"
)

# Finance Account Entity - Group-Types
Write-Host "`n=== FINANCE ACCOUNT GROUP-TYPES ===" -ForegroundColor Yellow
$financeAccountTypes = @(
    "checking-account", "saving-account", "charge", "revolving", "prepaid", "debit",
    "online-virtual", "samsung", "paypal", "google-pay", "apple-pay", "skrill", "stripe", "klarna"
)

# Contact Entity - Group-Types
Write-Host "`n=== CONTACT GROUP-TYPES ===" -ForegroundColor Yellow
$contactTypes = @(
    "single-contact", "contact-group"
)

# Mobility Entity - Group-Types (Personenfahrzeug - Auto)
Write-Host "`n=== MOBILITY GROUP-TYPES (PERSONENFAHRZEUG - AUTO) ===" -ForegroundColor Yellow
$mobilityCarTypes = @(
    "convertible", "coupe", "cuv", "off-road-vehicle", "minibus", "small-transporter", "small-car",
    "station-wagon", "limousine", "minivan", "pickup", "roadstar", "suv", "transporter", "van"
)

# Mobility Entity - Group-Types (Personenfahrzeug - Motorrad)
Write-Host "`n=== MOBILITY GROUP-TYPES (PERSONENFAHRZEUG - MOTORRAD) ===" -ForegroundColor Yellow
$mobilityMotorcycleTypes = @(
    "chopper", "cruiser", "dirt-bike", "motocross", "naked", "srambler", "streetfighter", "stuntbike", "trial"
)

# Mobility Entity - Group-Types (Fahrrad - Bike)
Write-Host "`n=== MOBILITY GROUP-TYPES (FAHRRAD - BIKE) ===" -ForegroundColor Yellow
$mobilityBikeTypes = @(
    "city", "cross", "tricycle", "folding-bike", "gravel", "cargo", "lifestyle", "mtb", "racing-bike",
    "speed-bike", "suv", "trekking-bike", "xxl"
)

# Mobility Entity - Group-Types (Fahrrad - E-Bike)
Write-Host "`n=== MOBILITY GROUP-TYPES (FAHRRAD - E-BIKE) ===" -ForegroundColor Yellow
$mobilityEBikeTypes = @(
    "e-city", "e-cross", "e-tricycle", "e-folding-bike", "e-gravel", "e-cargo", "e-lifestyle", "e-mtb",
    "e-racing-bike", "e-speed-bike", "e-suv", "e-trekking-bike", "e-xxl"
)

# Mobility Entity - Group-Types (Fahrrad - Pedelec)
Write-Host "`n=== MOBILITY GROUP-TYPES (FAHRRAD - PEDELEC) ===" -ForegroundColor Yellow
$mobilityPedelecTypes = @(
    "p-city", "p-cross", "p-tricycle", "p-folding-bike", "p-gravel", "p-cargo", "p-lifestyle", "p-mtb",
    "p-racing-bike", "p-speed-bike", "p-suv", "p-trekking-bike", "p-xxl"
)

# Mobility Entity - Group-Types (Personenfahrzeug - Quad)
Write-Host "`n=== MOBILITY GROUP-TYPES (PERSONENFAHRZEUG - QUAD) ===" -ForegroundColor Yellow
$mobilityQuadTypes = @(
    "quad-4x4", "quad-c", "quad-electro", "quad-children", "quad-offroad", "quad-pocket", "quad-race", "quad-road", "quad-side-by-side"
)

# Mobility Entity - Group-Types (Boot)
Write-Host "`n=== MOBILITY GROUP-TYPES (BOOT) ===" -ForegroundColor Yellow
$mobilityBoatTypes = @(
    "motor-glider", "motorboat", "sailing-boat", "sailing-yacht"
)

# Mobility Entity - Group-Types (Nutzfahrzeug)
Write-Host "`n=== MOBILITY GROUP-TYPES (NUTZFAHRZEUG) ===" -ForegroundColor Yellow
$mobilityCommercialTypes = @(
    "a", "b", "c", "boxvan"
)

# Mobility Entity - Group-Types (Landwirtschaft)
Write-Host "`n=== MOBILITY GROUP-TYPES (LANDWIRTSCHAFT) ===" -ForegroundColor Yellow
$mobilityAgricultureTypes = @(
    "tractor"
)

# Mobility Entity - Group-Types (Personenfahrzeug - Wohnmobil)
Write-Host "`n=== MOBILITY GROUP-TYPES (PERSONENFAHRZEUG - WOHNMOBIL) ===" -ForegroundColor Yellow
$mobilityCamperTypes = @(
    "alkoven", "semi-integrated", "fully-integrated"
)

# Mobility Entity - Group-Types (Nutzfahrzeug - Wohnwagen)
Write-Host "`n=== MOBILITY GROUP-TYPES (NUTZFAHRZEUG - WOHNWAGEN) ===" -ForegroundColor Yellow
$mobilityCaravanTypes = @(
    "family", "luxury", "sport", "tent", "two-persons"
)

Write-Host "`n=== ANALYSE ABGESCHLOSSEN ===" -ForegroundColor Green
Write-Host "Alle Übersetzungsschlüssel wurden identifiziert und kategorisiert." -ForegroundColor Green 