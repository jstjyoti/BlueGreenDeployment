FROM maven:3.5.2-jdk-8-alpine AS MAVEN_TOOL_CHAIN
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn clean install


FROM openjdk:8-jdk-alpine
COPY --from=MAVEN_TOOL_CHAIN /tmp/target/springbootdemo-0.0.1-SNAPSHOT.jar /tmp/
EXPOSE 8080
WORKDIR /tmp/
ENTRYPOINT ["java", "-jar", "springbootdemo-0.0.1-SNAPSHOT.jar"]