@echo off
set "folderName=skill09-2024-marvin-kramer"

:: Create folder on the desktop
mkdir "%USERPROFILE%\Desktop\%folderName%"

:: Copy 'release' folder from desktop
xcopy "%USERPROFILE%\Desktop\releases" "%USERPROFILE%\Desktop\%folderName%\releases" /E /I

:: Copy current folder into a subfolder called 'source'
robocopy "%~dp0." "%USERPROFILE%\Desktop\%folderName%\source" /E /XD ".gradle" "build"

:: Copy README.md to the created folder
copy "README.md" "%USERPROFILE%\Desktop\%folderName%"
copy "competitor.json" "%USERPROFILE%\Desktop\%folderName%"

:: Zip the folder
powershell -Command "Compress-Archive -Path '%USERPROFILE%\Desktop\%folderName%' -DestinationPath '%USERPROFILE%\Desktop\%folderName%.zip'"

echo Task completed successfully.
pause