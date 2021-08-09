FROM maven:3.5-jdk-8 AS build
COPY src /usr/src/api-coin-converter/src
COPY pom.xml /usr/src/api-coin-converter
RUN mvn -f /usr/src/api-coin-converter/pom.xml clean package

FROM openjdk:8-jdk-alpine
COPY --from=build /usr/src/api-coin-converter/target/api-coin-converter.jar /usr/app/api-coin-converter.jar
EXPOSE 8091
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /usr/app/api-coin-converter.jar" ]