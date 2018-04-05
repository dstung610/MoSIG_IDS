call 0_SetEnv.bat
cd src
%JAVA% -cp ../%LIB_RMQ% GameClient %1%
cd %CUR_DIR%