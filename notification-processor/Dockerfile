# Build stage
FROM maven:3.9-amazoncorretto-21 AS builder
WORKDIR /build
COPY pom.xml .
COPY payment-service payment-service/
COPY notification-processor notification-processor/
RUN mvn clean package -DskipTests -pl notification-processor

# Run stage
FROM bellsoft/liberica-openjdk-alpine:21
WORKDIR /app
COPY --from=builder /build/notification-processor/target/*.jar /app/notification-processor.jar
EXPOSE 8766
EXPOSE 5006

ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5006", "-jar", "notification-processor.jar"]