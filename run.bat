@echo off
REM Script para configurar JAVA_HOME y ejecutar Spring Boot

setx JAVA_HOME "C:\Program Files\Java\jdk-21.0.10"
set JAVA_HOME=C:\Program Files\Java\jdk-21.0.10

cls
echo.
echo ========================================
echo   Iniciando Spring Boot...
echo ========================================
echo.
echo JAVA_HOME configurado en:
echo %JAVA_HOME%
echo.

call mvnw.cmd clean spring-boot:run

pause
