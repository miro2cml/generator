# Installationsanleitung



**Achtung:** Die Applikation benötigt mindestens die Java Version 11!

Der miro2cml Prototyp kann mit verschiedenen Mitteln gestartet werden. Diese wären: Nutzung eines StartSkripts, Starten über ein Terminal/CommandLineTool oder über Docker. Der einfachheitshalber wird hier davon ausgegangen, dass sich die Applikation auf der gleichen Machine gestartet wird, auf der sie auch genutzt werden soll. 

**Recommended:** Die einfachste Möglichkeit stellt vermutlich das StarterSkript startMiro2cml.bat für Windows dar. 

### Installation mit StarterSkript (Recommended)

Im Root Verzeichnis des Sourcecodes befinden sich zwei StarterSkripts, einmal startMiro2cml.sh für Ubuntu und einmal startMiro2cml.bat für Windows.

Wird das Skript ausgeführt, wird zuerst das die Applikation gebuilded und danach ausgeführt. Das Skript kann sowohl über die Kommandozeile als auch über das Graphische Interface des Betriebsystems gestartet werden.

1. Navigiere in das Rootverzeichnis des Sourcodes
2. Aktiviere Starterskript (Linux: startMiro2cml.sh, Windows: startMiro2cml.bat)
3. Starte Browser und besuche localhost:8080

(Auf Linux müssen möglicherweise zuerst die entsprechenden Ausführungsberechitgungen gesetzt werden)

```
chmod +x gradlew

chmod +x startMiro2cml.sh
```

### Installation mit Kommandozeilentool.

1. Navigiere in das Rootverzeichnis des Sourcodes

2. Type das folgende Kommando ein: 

   ```
   gradlew bootJar
   ```

3. Type das folgende Kommando ein: 

   Windows: 

   ```
   java -jar build\libs\miro2cml-0.3.0-SNAPSHOT.jar
   ```

   Ubuntu: 

   ```
   java -jar build/libs/miro2cml-0.3.0-SNAPSHOT.jar
   ```

4. Starte Browser und besuche localhost:8080

 (Auf Linux müssen möglicherweise zuerst die entsprechenden Ausführungsberechitgungen gesetzt werden)

```
chmod +x gradlew

chmod +x startMiro2cml.sh
```

### 

### Installation mit Docker (only Tested on Ubuntu/Debian):

1. Navigiere in das Rootverzeichnis des Sourcodes

2. Type die folgenden Kommandos ein: 

   ```
   chmod +x gradlew
   ```

   ```
   gradlew bootJar
   ```

3. ```
   docker build -t miro2cml/local-snapshot .
   ```

4. ```
   docker run -p 8080:8080 miro2cml/local-snapshot
   ```

5. Starte Browser und besuche localhost:8080

Mit Docker ist es sehr einfach, den Listening Port der Applikation umzukonfigurieren, dabei muss lediglich beim docker run Befehl das Argument {-p 8080:8080} durch das argument {-p {wunschPort]:8080} ersetzt werden

 