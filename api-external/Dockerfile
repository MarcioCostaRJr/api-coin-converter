FROM maven:3.5-jdk-8 AS build
COPY src /usr/src/api-external/src
COPY pom.xml /usr/src/api-external
RUN mvn -f /usr/src/api-external/pom.xml clean package

FROM openjdk:8-jdk-alpine
COPY --from=build /usr/src/api-external/target/api-external.jar /usr/app/api-external.jar
EXPOSE 8090
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /usr/app/api-external.jar" ]