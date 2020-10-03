FROM openjdk:8-jdk-alpine

# This Dockerfile is optimized for go binaries, change it as much as necessary
# for your language of choice.

#RUN apk --no-cache add ca-certificates=20190108-r0 libc6-compat=1.1.19-r10

EXPOSE 9091

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Dserver.port=9091", "-jar", "/app.jar"]
