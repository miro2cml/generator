#!/bin/bash

function pause(){
   read -p "$*"
}

./gradlew bootJar

pause 'Press any key to continue...'

java -jar build/libs/miro2cml-1.0.0.jar
