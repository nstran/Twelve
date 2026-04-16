Set-StrictMode -Version Latest
$ErrorActionPreference = "Continue"

$SrcDir = "d:\Twelve\reference\review_assets\verified_semantic\offline_by_id_or_name"
$DstBase = "d:\Twelve\client\assets\equipment_legacy"

# Clean and recreate
if (Test-Path $DstBase) { Remove-Item $DstBase -Recurse -Force }

# ============================================================
# Helper: copy a band of files from offline
# ============================================================
function Copy-Band {
    param(
        [string]$BandPrefix,
        [string]$DestFolder
    )
    if (-not (Test-Path $DestFolder)) {
        New-Item -ItemType Directory -Path $DestFolder -Force | Out-Null
    }
    $count = 0
    $files = Get-ChildItem $SrcDir -Filter "$BandPrefix*.png" -File -ErrorAction SilentlyContinue
    if ($files) {
        foreach ($f in $files) {
            Copy-Item $f.FullName $DestFolder
            $count++
        }
    }
    return $count
}

# ============================================================
# 00_body_base — base body sprites (990xx)
# CODE REF: mb.java line 108: Image image = mb.a(n2 + 99000, lh2.W)
# This is the naked body sheet, always the bottom layer
# ============================================================
$bodyDir = Join-Path $DstBase "00_body_base"
$sub = Join-Path $bodyDir "body_990xx"
$c = Copy-Band -BandPrefix "9900" -DestFolder $sub
Copy-Item (Join-Path $SrcDir "99099.png") $sub -ErrorAction SilentlyContinue
Write-Output "[OK] 00_body_base: $c files"

# ============================================================
# 01_default_overlays — defaults when no equipment worn
# CODE REF: mb.java line 712-715
#   weapon default: lh2.f==1 ? 79999 : 79899
#   helmet default: 89999
# ============================================================
$defDir = Join-Path $DstBase "01_default_overlays"

$sub = Join-Path $defDir "weapon_male_default_799xx"
$c = Copy-Band -BandPrefix "7990" -DestFolder $sub
Copy-Item (Join-Path $SrcDir "79998.png") $sub -ErrorAction SilentlyContinue
Write-Output "[OK] weapon_male_default: $c files"

$sub = Join-Path $defDir "weapon_female_default_798xx"
$c = Copy-Band -BandPrefix "7980" -DestFolder $sub
Copy-Item (Join-Path $SrcDir "79898.png") $sub -ErrorAction SilentlyContinue
Write-Output "[OK] weapon_female_default: $c files"

$sub = Join-Path $defDir "helmet_default_899xx"
$c = Copy-Band -BandPrefix "8990" -DestFolder $sub
Write-Output "[OK] helmet_default: $c files"

# ============================================================
# 02_armor_e0 — Giap/Ao body overlays (ll.e == 0)
# CODE REF: mb.java line 700: nArray[0] = n3 - n3 % 10
#           mb.java line 713: mbArray[0] = nArray[0] > 0 ? new mb(nArray[0]+99) : new mb(n4)
# These are the body armor overlays, composited as the 3rd layer
# Bands: 500xx-505xx (heavy), 600xx-609xx (light)
# Also: meta-backed armors 920xx,924xx,925xx,980xx,981xx,982xx,999xx
# ============================================================
$armorDir = Join-Path $DstBase "02_armor_e0"

# Heavy armor 500xx-505xx (no icon files)
$heavyBands = @(
    @("500", "heavy_tier01_500xx"),
    @("501", "heavy_tier02_501xx"),
    @("502", "heavy_tier03_502xx"),
    @("503", "heavy_tier04_503xx"),
    @("504", "heavy_tier05_504xx"),
    @("505", "heavy_tier06_505xx")
)
foreach ($b in $heavyBands) {
    $sub = Join-Path $armorDir $b[1]
    $c = Copy-Band -BandPrefix "$($b[0])0" -DestFolder $sub
    $iconFile = Join-Path $SrcDir "$($b[0])98.png"
    if (Test-Path $iconFile) { Copy-Item $iconFile $sub }
    Write-Output "[OK] $($b[1]): $c files"
}

# Light armor 600xx-609xx (no icon files)
for ($i = 0; $i -le 9; $i++) {
    $bandStr = "60${i}"
    $folderName = "light_tier{0:D2}_60{1}xx" -f ($i + 1), $i
    $sub = Join-Path $armorDir $folderName
    $c = Copy-Band -BandPrefix "${bandStr}0" -DestFolder $sub
    $iconFile = Join-Path $SrcDir "${bandStr}98.png"
    if (Test-Path $iconFile) { Copy-Item $iconFile $sub -ErrorAction SilentlyContinue }
    if ($c -gt 0) {
        Write-Output "[OK] $folderName : $c files"
    } else {
        Remove-Item $sub -Recurse -Force -ErrorAction SilentlyContinue
    }
}

# Meta-backed armor candidates (body overlay families from character_meta_parsed.csv)
$metaArmors = @(
    @("920", "meta_armor_920xx"),
    @("924", "meta_armor_924xx"),
    @("925", "meta_armor_925xx"),
    @("980", "meta_armor_980xx"),
    @("981", "meta_armor_981xx"),
    @("982", "meta_armor_982xx"),
    @("999", "meta_armor_fullbody_999xx")
)
foreach ($entry in $metaArmors) {
    $band = $entry[0]
    $folderName = $entry[1]
    $sub = Join-Path $armorDir $folderName
    $c = Copy-Band -BandPrefix "${band}0" -DestFolder $sub
    $iconFile = Join-Path $SrcDir "${band}98.png"
    if (Test-Path $iconFile) { Copy-Item $iconFile $sub }
    if ($c -gt 0) {
        Write-Output "[OK] $folderName : $c files"
    } else {
        Remove-Item $sub -Recurse -Force -ErrorAction SilentlyContinue
    }
}

# ============================================================
# 03_weapon_e1 — Vu Khi weapon overlays (ll.e == 1)
# CODE REF: mb.java line 714: mbArray[2] = nArray[1] > 0 ? new mb(nArray[1]+99) : new mb(n2)
# Composited as the top weapon layer
# Bands: 7xxxx (not 798xx/799xx which are defaults)
# Also: meta-backed weapons 971xx, 974xx, 975xx
# ============================================================
$wpnDir = Join-Path $DstBase "03_weapon_e1"

$weaponBands = @(
    "701","703","707",
    "712","713","714","716","717","718",
    "720","724","725",
    "740","741","742","743",
    "751","753","754","756","758",
    "762","764","765","766","767",
    "771","774","775",
    "780","781","782","783"
)
$wpnIdx = 1
foreach ($band in $weaponBands) {
    $folderName = "weapon_{0:D2}_{1}xx" -f $wpnIdx, $band
    $sub = Join-Path $wpnDir $folderName
    $c = Copy-Band -BandPrefix "${band}0" -DestFolder $sub
    $iconFile = Join-Path $SrcDir "${band}98.png"
    if (Test-Path $iconFile) { Copy-Item $iconFile $sub }
    if ($c -gt 0) {
        Write-Output "[OK] $folderName : $c files"
        $wpnIdx++
    } else {
        Remove-Item $sub -Recurse -Force -ErrorAction SilentlyContinue
    }
}

# Meta-backed weapons
$metaWeapons = @(
    @("971", "meta_weapon_971xx"),
    @("974", "meta_weapon_974xx"),
    @("975", "meta_weapon_975xx")
)
foreach ($entry in $metaWeapons) {
    $band = $entry[0]
    $folderName = $entry[1]
    $sub = Join-Path $wpnDir $folderName
    $c = Copy-Band -BandPrefix "${band}0" -DestFolder $sub
    $iconFile = Join-Path $SrcDir "${band}98.png"
    if (Test-Path $iconFile) { Copy-Item $iconFile $sub }
    if ($c -gt 0) {
        Write-Output "[OK] $folderName : $c files"
    } else {
        Remove-Item $sub -Recurse -Force -ErrorAction SilentlyContinue
    }
}

# ============================================================
# 04_helmet_e2 — Non/Mu helmet overlays (ll.e == 2)
# CODE REF: mb.java line 715: mbArray[3] = nArray[2] > 0 ? new mb(nArray[2]+99) : new mb(89999)
# Composited as the head layer
# Bands: 80xxx-83xxx
# Also: meta-backed headgear 940xx-943xx, 983xx
# ============================================================
$helmDir = Join-Path $DstBase "04_helmet_e2"

$helmBands = @(
    "800","802","804","806","807","808",
    "810","811","818",
    "820","823","826","827","828",
    "831","832"
)
$helmIdx = 1
foreach ($band in $helmBands) {
    $folderName = "helmet_{0:D2}_{1}xx" -f $helmIdx, $band
    $sub = Join-Path $helmDir $folderName
    $c = Copy-Band -BandPrefix "${band}0" -DestFolder $sub
    $iconFile = Join-Path $SrcDir "${band}98.png"
    if (Test-Path $iconFile) { Copy-Item $iconFile $sub }
    if ($c -gt 0) {
        Write-Output "[OK] $folderName : $c files"
        $helmIdx++
    } else {
        Remove-Item $sub -Recurse -Force -ErrorAction SilentlyContinue
    }
}

# Meta-backed headgear
$metaHelms = @(
    @("940", "meta_helmet_940xx"),
    @("941", "meta_helmet_941xx"),
    @("942", "meta_helmet_942xx"),
    @("943", "meta_helmet_943xx"),
    @("983", "meta_helmet_983xx")
)
foreach ($entry in $metaHelms) {
    $band = $entry[0]
    $folderName = $entry[1]
    $sub = Join-Path $helmDir $folderName
    $c = Copy-Band -BandPrefix "${band}0" -DestFolder $sub
    $iconFile = Join-Path $SrcDir "${band}98.png"
    if (Test-Path $iconFile) { Copy-Item $iconFile $sub }
    if ($c -gt 0) {
        Write-Output "[OK] $folderName : $c files"
    } else {
        Remove-Item $sub -Recurse -Force -ErrorAction SilentlyContinue
    }
}

# ============================================================
# 05_boot_e3 — Giay/Ung boot sprites (ll.e == 3)
# CODE REF: mb.java line 698: if (mbArray.e < 4) nArray[mbArray.e] = ...
#   Boots (e=3) are STORED in nArray[3] but NOT used for compositor visual
#   They only appear as inventory icons.
# Bands: 890xx-893xx
# ============================================================
$bootDir = Join-Path $DstBase "05_boot_e3"

$bootBands = @("890","891","892","893")
$bootIdx = 1
foreach ($band in $bootBands) {
    $folderName = "boot_{0:D2}_{1}xx" -f $bootIdx, $band
    $sub = Join-Path $bootDir $folderName
    $c = Copy-Band -BandPrefix "${band}0" -DestFolder $sub
    $iconFile = Join-Path $SrcDir "${band}98.png"
    if (Test-Path $iconFile) { Copy-Item $iconFile $sub }
    if ($c -gt 0) {
        Write-Output "[OK] $folderName : $c files"
        $bootIdx++
    } else {
        Remove-Item $sub -Recurse -Force -ErrorAction SilentlyContinue
    }
}

# ============================================================
# 06_mount_e4 — Khien/Ngua mount/shield (ll.e == 4)
# CODE REF: ll.java line 35: nArray[4] = 5
#   mb.java line 698: if (mbArray.e < 4) — NOT composited in body
#   But lh.ad field is affected by mount equipment
# Bands: 904xx, 908xx (candidate)
# ============================================================
$mountDir = Join-Path $DstBase "06_mount_e4"

$mountBands = @(
    @("904", "mount_candidate_904xx"),
    @("908", "mount_candidate_908xx")
)
foreach ($entry in $mountBands) {
    $band = $entry[0]
    $folderName = $entry[1]
    $sub = Join-Path $mountDir $folderName
    $c = Copy-Band -BandPrefix "${band}0" -DestFolder $sub
    $iconFile = Join-Path $SrcDir "${band}98.png"
    if (Test-Path $iconFile) { Copy-Item $iconFile $sub }
    if ($c -gt 0) {
        Write-Output "[OK] $folderName : $c files"
    } else {
        Remove-Item $sub -Recurse -Force -ErrorAction SilentlyContinue
    }
}

# ============================================================
# 07_accessory_e5_e7_e8 — Ring/Bua/Accessory (ll.e == 5,7,8)
# CODE REF: ll.java line 36-38: nArray[5]=9, nArray[7]=6, nArray[8]=10
#   These have NO visual layer in compositor
#   They only provide stat bonuses
#   Aura effects (902xx, 903xx) may be visual extras for
#   legendary accessories — placed here as candidates
# ============================================================
$accDir = Join-Path $DstBase "07_accessory_e5_e7_e8"

$accBands = @(
    @("902", "aura_candidate_902xx"),
    @("903", "aura_candidate_903xx")
)
foreach ($entry in $accBands) {
    $band = $entry[0]
    $folderName = $entry[1]
    $sub = Join-Path $accDir $folderName
    $c = Copy-Band -BandPrefix "${band}0" -DestFolder $sub
    $iconFile = Join-Path $SrcDir "${band}98.png"
    if (Test-Path $iconFile) { Copy-Item $iconFile $sub }
    if ($c -gt 0) {
        Write-Output "[OK] $folderName : $c files"
    } else {
        Remove-Item $sub -Recurse -Force -ErrorAction SilentlyContinue
    }
}

# ============================================================
# 08_premium_sets — Full premium equipment sets
# These bands have full sprite sheets (10 frames + icon)
# Strong candidate for premium full-body equipment sets
# Cannot confirm exact slot from code alone — server decides
# Bands: 952xx-963xx
# ============================================================
$premDir = Join-Path $DstBase "08_premium_sets"

$premBands = @(
    @("952", "premium_01_952xx"),
    @("954", "premium_02_954xx"),
    @("955", "premium_03_955xx"),
    @("957", "premium_04_957xx"),
    @("958", "premium_05_958xx"),
    @("959", "premium_06_959xx"),
    @("960", "premium_07_960xx"),
    @("961", "premium_08_961xx"),
    @("962", "premium_09_962xx"),
    @("963", "premium_10_963xx")
)
foreach ($entry in $premBands) {
    $band = $entry[0]
    $folderName = $entry[1]
    $sub = Join-Path $premDir $folderName
    $c = Copy-Band -BandPrefix "${band}0" -DestFolder $sub
    $iconFile = Join-Path $SrcDir "${band}98.png"
    if (Test-Path $iconFile) { Copy-Item $iconFile $sub }
    if ($c -gt 0) {
        Write-Output "[OK] $folderName : $c files"
    } else {
        Remove-Item $sub -Recurse -Force -ErrorAction SilentlyContinue
    }
}

# ============================================================
# 09_ui_icons — Equipment-related UI components
# CODE REF: dc.java line 13: Image i = f.d("/broken_heart") — broken equip icon
#           dc.java line 70-71: rank 4/7/8 star effect
#           id.java — blacksmith/upgrade UI
# ============================================================
$uiDir = Join-Path $DstBase "09_ui_icons"
New-Item -ItemType Directory -Path $uiDir -Force | Out-Null

$rootAssets = "d:\Twelve\client\assets"
Copy-Item (Join-Path $rootAssets "broken_heart.png") $uiDir -ErrorAction SilentlyContinue
Copy-Item (Join-Path $rootAssets "star.png") $uiDir -ErrorAction SilentlyContinue
Copy-Item (Join-Path $rootAssets "slotlock.png") $uiDir -ErrorAction SilentlyContinue
Copy-Item (Join-Path $rootAssets "blacksmith.png") $uiDir -ErrorAction SilentlyContinue
Copy-Item (Join-Path $rootAssets "effblacksmith.png") $uiDir -ErrorAction SilentlyContinue
$uiCount = (Get-ChildItem $uiDir -File).Count
Write-Output "[OK] 09_ui_icons: $uiCount files"

# ============================================================
# Summary
# ============================================================
$totalFiles = (Get-ChildItem $DstBase -Recurse -File).Count
$totalDirs = (Get-ChildItem $DstBase -Recurse -Directory).Count
Write-Output ""
Write-Output "============================================"
Write-Output "[OK] Equipment-ONLY reorganize complete!"
Write-Output "[OK] Total: $totalFiles files in $totalDirs folders"
Write-Output "[OK] Output: $DstBase"
Write-Output ""
Write-Output "[INFO] NOT included (not equipment):"
Write-Output "  - Hair/appearance (909xx,910xx,912xx) -> createcs_legacy"
Write-Output "  - Special effects (911xx,913xx) -> unrelated"
Write-Output "  - Consumable items (100xxx) -> separate item system"
Write-Output "  - Map/NPC icons (110xxx-140xxx) -> map system"
Write-Output "  - Skill effects (1M/2M/4Mxxxxxxx) -> skill system"
Write-Output "============================================"
