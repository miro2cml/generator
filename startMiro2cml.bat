call gradlew.bat bootJar
ECHO Build Stage finished. Press a key to start the application.
PAUSE
java -jar build\libs\miro2cml-1.0.0.jar
