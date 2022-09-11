#!/bin/sh

if [[ -z "${DEBUG}" ]]; then
  echo "in normal mode"
  java -jar workflow-system-backend-0.0.1-SNAPSHOT.jar
else
  echo "in debug mode"
  java -Xdebug -Xrunjdwp:transport=dt_socket,address=5005,server=y,suspend=n -jar workflow-system-backend-0.0.1-SNAPSHOT.jar
fi
