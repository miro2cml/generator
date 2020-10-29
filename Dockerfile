FROM openjdk:11

MAINTAINER miro2cml SA @ HSR
EXPOSE 8080 
COPY ./build/libs/*.jar /
ENTRYPOINT ["java","-jar","/miro2cml-0.0.1-SNAPSHOT.jar", "--spring.config.location=file:/config/"]
