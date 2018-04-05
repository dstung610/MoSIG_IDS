@echo off
set JAVA="C:\Program Files\Java\jdk-9.0.1\bin\java.exe"
set JAVAC="C:\Program Files\Java\jdk-9.0.1\bin\javac.exe"

set PRJ_DIR=%CD%

set LIBDIR=%PRJ_DIR%/lib

REM set CP=.;
set CP=%CP%;%LIBDIR%/amqp-client-4.0.2.jar
set CP=%CP%;%LIBDIR%/slf4j-api-1.7.21.jar
set CP=%CP%;%LIBDIR%/slf4j-simple-1.7.22.jar;.
