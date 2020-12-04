FROM openjdk:11

MAINTAINER miro2cml SA @ RJ-OST
EXPOSE 8080 
COPY ./build/libs/*.jar /
COPY ./src/main/resources/*.properties /
ENTRYPOINT ["java","-jar","/miro2cml-0.0.1-SNAPSHOT.jar"]
