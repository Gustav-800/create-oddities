param(
    [Parameter(Mandatory = $true)][string]$MinecraftDir,
    [string]$ModsDir = "",
    [string]$JavaHome = "$env:APPDATA\.minecraft\runtime\java-runtime-delta"
)

$ErrorActionPreference = 'Stop'
$root = $PSScriptRoot
$buildDir = Join-Path $root 'build'
$classesDir = Join-Path $buildDir 'classes'
$libsDir = Join-Path $buildDir 'libs'
$jarName = 'create-oddities-0.2.1.jar'
$jarPath = Join-Path $libsDir $jarName

$javac = Join-Path $JavaHome 'bin\javac.exe'
$jarTool = Join-Path $JavaHome 'bin\jar.exe'
if (-not (Test-Path $javac)) { throw "javac not found: $javac" }

$libraryRoot = Join-Path $MinecraftDir 'libraries'
$srgJar = Get-ChildItem (Join-Path $libraryRoot 'net\minecraft\client') -Recurse -Filter 'client-1.21.1-*-srg.jar' |
    Select-Object -First 1
if (-not $srgJar) { throw "Minecraft 1.21.1 client jar not found under $libraryRoot" }

$classpath = @(
    $srgJar.FullName,
    (Join-Path $libraryRoot 'net\neoforged\neoforge\21.1.262\neoforge-21.1.262-universal.jar'),
    (Join-Path $libraryRoot 'net\neoforged\fancymodloader\loader\4.0.45\loader-4.0.45.jar'),
    (Join-Path $libraryRoot 'net\neoforged\bus\8.0.5\bus-8.0.5.jar'),
    (Join-Path $libraryRoot 'net\neoforged\mergetool\2.0.3\mergetool-2.0.3-api.jar'),
    (Join-Path $libraryRoot 'net\fabricmc\sponge-mixin\0.16.4+mixin.0.8.7\sponge-mixin-0.16.4+mixin.0.8.7.jar'),
    (Join-Path $libraryRoot 'org\ow2\asm\asm\9.7.1\asm-9.7.1.jar'),
    (Join-Path $libraryRoot 'org\ow2\asm\asm-tree\9.7.1\asm-tree-9.7.1.jar'),
    (Join-Path $libraryRoot 'com\mojang\brigadier\1.3.10\brigadier-1.3.10.jar'),
    (Join-Path $libraryRoot 'org\slf4j\slf4j-api\2.0.9\slf4j-api-2.0.9.jar')
)

# Optional integration APIs (Create / Simulated / Ponder) for compiling modules
# that directly reference their public classes. The release jar does not bundle
# these jars; they are only compile-time inputs.
$depsDir = Join-Path $buildDir 'deps'
New-Item -ItemType Directory -Force -Path $depsDir | Out-Null
if ($ModsDir -and (Test-Path $ModsDir)) {
    Add-Type -AssemblyName System.IO.Compression.FileSystem
    $createJar = Get-ChildItem $ModsDir -Filter '*create-1.21.1-*.jar' |
        Where-Object { $_.Name -notmatch 'aeronautics|oddities' } |
        Select-Object -First 1
    $integrationJars = @(
        $createJar,
        (Get-ChildItem $ModsDir -Filter 'create-aeronautics-bundled-*.jar' | Select-Object -First 1)
    ) | Where-Object { $_ }
    foreach ($integrationJar in $integrationJars) {
        $classpath += $integrationJar.FullName
        $zip = [System.IO.Compression.ZipFile]::OpenRead($integrationJar.FullName)
        try {
            foreach ($entry in $zip.Entries) {
                if ($entry.FullName -like 'META-INF/jarjar/*.jar') {
                    $target = Join-Path $depsDir $entry.Name
                    [System.IO.Compression.ZipFileExtensions]::ExtractToFile($entry, $target, $true)
                    $classpath += $target
                }
            }
        } finally {
            $zip.Dispose()
        }
    }
}

$missing = $classpath | Where-Object { -not (Test-Path -LiteralPath $_) }
if ($missing) { throw "Missing required jars: $($missing -join '; ')" }

if (Test-Path $classesDir) { Remove-Item $classesDir -Recurse -Force }
New-Item -ItemType Directory -Force -Path $classesDir | Out-Null
New-Item -ItemType Directory -Force -Path $libsDir | Out-Null

$sources = Get-ChildItem (Join-Path $root 'src\main\java') -Recurse -Filter *.java | ForEach-Object FullName
$cpString = $classpath -join ';'
Write-Host "Compiling $($sources.Count) source file(s)..."
& $javac -encoding UTF-8 -source 21 -target 21 -proc:none -classpath $cpString -d $classesDir $sources
if ($LASTEXITCODE -ne 0) { throw "javac failed with exit code $LASTEXITCODE" }

Copy-Item (Join-Path $root 'src\main\resources\*') $classesDir -Recurse -Force

$manifest = Join-Path $buildDir 'MANIFEST.MF'
@(
    'Manifest-Version: 1.0',
    'Implementation-Title: Create Oddities',
    'Implementation-Version: 0.2.1'
) | Set-Content -Path $manifest -Encoding ASCII

if (Test-Path $jarPath) { Remove-Item $jarPath -Force }
& $jarTool --create --file $jarPath --manifest $manifest --date=2026-01-01T00:00:00Z -C $classesDir .
if ($LASTEXITCODE -ne 0) { throw "jar failed with exit code $LASTEXITCODE" }

$distDir = Join-Path $root 'dist'
New-Item -ItemType Directory -Force -Path $distDir | Out-Null
Copy-Item $jarPath (Join-Path $distDir $jarName) -Force

$hash = (Get-FileHash $jarPath -Algorithm SHA256).Hash
Write-Host "Built: $jarPath"
Write-Host "Size:  $((Get-Item $jarPath).Length) bytes"
Write-Host "SHA256: $hash"
