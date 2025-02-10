# Build stage
FROM maven:3.9-amazoncorretto-21 AS builder
WORKDIR /build
COPY pom.xml .
COPY payment-service payment-service/
COPY notification-processor notification-processor/
RUN mvn clean package -DskipTests -pl payment-service

# Run stage
FROM bellsoft/liberica-openjdk-alpine:21
WORKDIR /app
COPY --from=builder /build/payment-service/target/*.jar /app/payment-service.jar
EXPOSE 8765
EXPOSE 5005

ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "payment-service.jar"]