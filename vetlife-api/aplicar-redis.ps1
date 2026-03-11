$RootPath = "C:\programacao\vetlife+\vetlife-api"
cd $RootPath

function Write-JavaFile ($RelativePath, $Content) {
    $FullPath = Join-Path $RootPath $RelativePath
    $Dir = Split-Path $FullPath
    if (-not (Test-Path $Dir)) { New-Item -ItemType Directory -Force $Dir | Out-Null }
    [System.IO.File]::WriteAllText($FullPath, $Content.Trim(), (New-Object System.Text.UTF8Encoding($false)))
    Write-Host "Atualizado: $RelativePath" -ForegroundColor Cyan
}

# --- 1. ATUALIZAR O POM.XML (ADD REDIS) ---
$PomContent = @"
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.3</version>
        <relativePath/>
    </parent>
    <groupId>com.vetlife</groupId>
    <artifactId>vetlife-api</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>vetlife-api</name>
    <properties>
        <java.version>21</java.version>
    </properties>
    <dependencies>
        <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-web</artifactId></dependency>
        <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-validation</artifactId></dependency>
        <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-data-jpa</artifactId></dependency>
        <dependency><groupId>org.postgresql</groupId><artifactId>postgresql</artifactId><scope>runtime</scope></dependency>
        <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-security</artifactId></dependency>
        <dependency><groupId>com.auth0</groupId><artifactId>java-jwt</artifactId><version>4.4.0</version></dependency>
        <dependency><groupId>org.springdoc</groupId><artifactId>springdoc-openapi-starter-webmvc-ui</artifactId><version>2.3.0</version></dependency>
        <dependency><groupId>org.flywaydb</groupId><artifactId>flyway-core</artifactId></dependency>
        <dependency><groupId>org.flywaydb</groupId><artifactId>flyway-database-postgresql</artifactId><version>10.8.1</version></dependency>
        
        <!-- REDIS (CACHE) -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>

        <dependency><groupId>org.projectlombok</groupId><artifactId>lombok</artifactId><optional>true</optional></dependency>
        <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-test</artifactId><scope>test</scope></dependency>
    </dependencies>
    <build><plugins><plugin><groupId>org.springframework.boot</groupId><artifactId>spring-boot-maven-plugin</artifactId><configuration><excludes><exclude><groupId>org.projectlombok</groupId><artifactId>lombok</artifactId></exclude></excludes></configuration></plugin></plugins></build>
</project>
"@