# Script para ejecutar Spring Boot con JAVA_HOME configurado

Write-Host "========================================" -ForegroundColor Green
Write-Host "Configurando y ejecutando Spring Boot..." -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""

# Configurar JAVA_HOME
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21.0.10"

Write-Host "✅ JAVA_HOME configurado:" -ForegroundColor Green
Write-Host "   $env:JAVA_HOME" -ForegroundColor Cyan
Write-Host ""

Write-Host "Iniciando compilación y ejecución..." -ForegroundColor Yellow
Write-Host ""

# Ejecutar Maven
& ".\mvnw.cmd" clean spring-boot:run

Write-Host ""
Write-Host "Aplicación detenida." -ForegroundColor Yellow
