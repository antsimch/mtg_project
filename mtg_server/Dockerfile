FROM maven:3-eclipse-temurin-20 AS builder

WORKDIR /app

COPY mvnw .
COPY pom.xml .
COPY src src

RUN mvn package -Dmaven.test.skip=true

FROM openjdk:20-slim

WORKDIR /app

COPY --from=builder /app/target/mtg_server-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=8080

EXPOSE ${PORT} 

ENTRYPOINT java -jar app.jar