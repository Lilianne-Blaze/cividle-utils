@echo off
setlocal

echo This will purge the following directories:
echo %USERPROFILE%\.m2\repository\net\lbcode
echo %USERPROFILE%\.m2\repository\com\example\cividleutils

choice /c yn /m "Are you sure?"

if %errorlevel% equ 1 (
  rem continueing
) else (
  echo Exiting...
  goto :eof
)

rd %USERPROFILE%\.m2\repository\net\lbcode /s /q
rd %USERPROFILE%\.m2\repository\com\example\cividleutils /s /q

pause
