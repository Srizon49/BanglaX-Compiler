@echo off
title BanglaX Compiler - Mini IDE

echo =================================
echo        BanglaX Compiler
echo =================================
echo.
echo Compiling project...
echo.

if not exist out mkdir out

powershell -NoProfile -Command "javac -encoding UTF-8 -d out (Get-ChildItem src -Recurse -Filter *.java | Where-Object { $_.Name -ne 'LexerTest.Java' } | ForEach-Object { $_.FullName })"

if errorlevel 1 (
    echo.
    echo =================================
    echo       Compilation FAILED
    echo =================================
    echo.
    pause
    exit /b 1
)

echo.
echo Compilation successful.
echo.
echo Starting BanglaX Mini IDE...
echo.

java -cp out ui.CompilerUI

if errorlevel 1 (
    echo.
    echo =================================
    echo          GUI ERROR
    echo =================================
    echo.
    pause
)

exit /b