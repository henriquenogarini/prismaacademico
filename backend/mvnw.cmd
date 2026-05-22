@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF)
@REM Maven Wrapper startup batch script - Prisma Acadêmico
@REM ----------------------------------------------------------------------------
@IF "%__MVNW_ARG0_NAME__%"=="" (SET __MVNW_ARG0_NAME__=%~nx0)
@SET ___MVNW_UNWRAPPED_FILE__=%~f0
@SET ___MVNW_UNWRAPPED_DIR__=%~dp0
@SET MAVEN_PROJECTBASEDIR=%___MVNW_UNWRAPPED_DIR__%
@ECHO OFF

SET MAVEN_WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.jar"
SET MAVEN_WRAPPER_PROPERTIES="%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.properties"
SET DOWNLOAD_URL=https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar

IF "%JAVA_HOME%"=="" (
  SET JAVA_EXEC=java
) ELSE (
  SET JAVA_EXEC="%JAVA_HOME%\bin\java"
)

FOR /F "usebackq tokens=1,2 delims==" %%A IN (%MAVEN_WRAPPER_PROPERTIES%) DO (
    IF "%%A"=="wrapperUrl" SET DOWNLOAD_URL=%%B
)

@REM Download wrapper jar if not present
IF NOT EXIST %MAVEN_WRAPPER_JAR% (
    ECHO Downloading Maven Wrapper JAR...
    powershell -Command "& { [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; (New-Object System.Net.WebClient).DownloadFile('%DOWNLOAD_URL%', %MAVEN_WRAPPER_JAR%) }"
)

@REM Find Maven distribution from properties
SET MAVEN_DIST_URL=
FOR /F "usebackq tokens=1,2 delims==" %%A IN (%MAVEN_WRAPPER_PROPERTIES%) DO (
    IF "%%A"=="distributionUrl" SET MAVEN_DIST_URL=%%B
)

@REM Use installed Maven if available, otherwise use wrapper
WHERE mvn >nul 2>nul
IF "%ERRORLEVEL%"=="0" (
    mvn %*
) ELSE IF EXIST "C:\tools\maven\bin\mvn.cmd" (
    "C:\tools\maven\bin\mvn.cmd" %*
) ELSE (
    %JAVA_EXEC% -jar %MAVEN_WRAPPER_JAR% %*
)


