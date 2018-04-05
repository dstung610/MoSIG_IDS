call 0_SetEnv.bat
cd src
REM dir
REM @echo on
%JAVA% -cp "%CP%" GameServer %1%
echo %1%
cd %PRJ_DIR%