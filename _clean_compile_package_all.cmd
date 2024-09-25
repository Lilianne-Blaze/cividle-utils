@echo off
set "d=%~dp0"
set "d=%d:~0,-1%"

echo Current dir: %d%
echo JAVA_HOME: %JAVA_HOME%
echo GRAALVM_HOME: %GRAALVM_HOME%

pushd %d%

call ./mvnw.cmd clean compile package

pause
popd