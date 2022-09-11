FROM openjdk:8
ADD target/workflow-system-backend-0.0.1-SNAPSHOT.jar workflow-system-backend-0.0.1-SNAPSHOT.jar
ADD runServer.sh runServer.sh
EXPOSE 8080
EXPOSE 5005
ENTRYPOINT ["/bin/bash", "runServer.sh"]
